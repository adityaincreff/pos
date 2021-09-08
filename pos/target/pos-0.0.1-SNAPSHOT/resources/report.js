function getReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content");
	console.log(baseUrl);
	return baseUrl + "/api/report/sales";
}
function HideTable()
{
  $("#sales-table").hide();
}
function SalesReport(event){

	var $form = $("#report-form");
	var json = toJson($form);
	console.log(json);
	var url = getReportUrl();
    $("#sales-table").show();
    successFx = function(data){
        displayReportData(data);
    }
    ajaxRequest(url,'POST',json,successFx,1);


	return false;
}
function displayReportData(data){

	var $tbody = $('#sales-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		console.log(data[i]);
		console.log(i);
		var row = '<tr>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.revenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function defaultDate()
{
	$('#inputstartdate').val(new Date().toJSON().slice(0,19));
	$('#inputenddate').val(new Date().toJSON().slice(0,19));

}
function init(){
	$('#sales-report').click(SalesReport);
	defaultDate();
}

$(document).ready(init);
$(document).ready(HideTable);
