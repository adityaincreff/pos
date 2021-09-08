var data = [];

function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content");
	console.log(baseUrl);
	return baseUrl + "/api/order";
}
function createOrder(event){
    url = getOrderUrl();
    console.log(data);
    var json = JSON.stringify(data);
    console.log(json);
    data = [];
    successFx = function(response){
        successMessageDisplay("Order Successfully Created");
        displayOrderList();
    }
    ajaxRequest(url,'POST',json,successFx,0);
    var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
    hideCreate();
	return false;
}

function addItem(event){
    var b,q;
        
    b = $("#order-form input[name=barcode]").val();
    q = $("#order-form input[name=quantity]").val();
    if(q==0)
    {
        handleFrontendError("Quantity cannot be 0")
        return false;
    }
    if(b=="" || q == ""){
        handleFrontendError("One or more fields are empty.");
        return false;
    }
    for(var i=0;i<data.length;i++)
    {   if(data[i].barcode ==b)
        {   
            data[i].quantity =parseInt(data[i].quantity)+parseInt(q);
            $("#order-form input[name=barcode]").val("");
            $("#order-form input[name=quantity]").val("");
            $('#create-order').show();

            displayOrderList();
            return false;
        }

    }
    
    
    var itembody = {
        barcode : b,
        quantity : q
    };
    data.push(itembody);
    
    $("#order-form input[name=barcode]").val("");
    $("#order-form input[name=quantity]").val("");
   
    $('#create-order').show();

    displayOrderList();

}

function displayOrderList(){
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	var j = 1;
	for(var i in data){
		var e = data[i];
		var f= j-1;
		var buttonHtml = '<button type="button" class="btn btn-group btn-sm btn-link" onclick="deleteItem(' + f + ')"><span class="btn btn-sm input-group-text material-icons pointer text-danger">delete</span></button>';
//		var buttonHtml = ' <button onclick="displayEditBrand(' + e.brandId + ')">edit</button>'
		var row = '<tr>'
		+ '<td>' + j + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		+ '<td>' + buttonHtml +'</td>'
		+ '</tr>';
		j++;
        $tbody.append(row);
	}
}
function deleteItem(id){
    data.splice(id,1);
    if(data.length == 0)
    {
        hideCreate();
    }
    
    displayOrderList();
}
function init(){
    $('#create-order').click(createOrder);
	$('#add-item').click(addItem);
	$('#refresh-data').click(displayOrderList);
}
function hideCreate()
{
    $('#create-order').hide();

}

$(document).ready(init);
$(document).ready(displayOrderList);
$(document).ready(hideCreate);
