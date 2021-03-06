

function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}
function getPdfUrl(){
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/pdf";
}

function getOrderList(){
	var url = getOrderUrl();
	successFx = function(data){
	    displayOrderList(data);
	}
	ajaxRequest(url,'GET',1,successFx,0);

}


function displayOrderList(data){
	var $tbody = $('#orderhistory-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var invoice = '<a download="invoice.pdf" class="btn btn-group btn-sm btn-link" href=' + getPdfUrl()+'/' + e.order_id +
		    '><span class="material-icons">download</span></a>';
		var eye = '<button type="button" class="btn btn-sm btn-group btn-link" onclick="getItemsList('+e.order_id+')"><span class="material-icons">visibility</span></button>';
		var x = e.date.dayOfMonth+'/'+e.date.monthValue+'/'+e.date.year +' - ' + e.date.hour +':' + e.date.minute + ':' + e.date.second;   //time
		var row = '<tr>'
		+ '<td>' + e.order_id + '</td>'
		+ '<td>'  + x + '</td>'
		+ '<td>' + eye +' ' + invoice + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}
function downloadInvoice(id){
    var url = getPdfUrl()+'/'+id;
    $.ajax({
       url: url,
   	   type: 'POST',

       error: handleAjaxError
    });
}
function getItemsList(id){
    var url = getOrderUrl()+'/'+id;
    successFx = function(data){
        displayItemsList(data);
    }
    ajaxRequest(url,'GET',1,successFx,0)

}
function displayItemsList(data){
    var $tbody = $('#order-table').find('tbody');
    $tbody.empty();
    for(var i in data){
    		var e = data[i];
    		var row = '<tr>'
    		+ '<td>' + e.name + '</td>'
    		+ '<td>'  + e.quantity + '</td>'
    		+ '<td>' + e.mrp + '</td>'
    		+ '</tr>';
            $tbody.append(row);
    	}
    $('#order-display-modal').modal('toggle');
}
function init(){
	$('#refresh-data').click(getOrderList);
}

$(document).ready(init);
$(document).ready(getOrderList);