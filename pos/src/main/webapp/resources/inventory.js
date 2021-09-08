var inventorydata=[];
function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}

//BUTTON ACTIONS
function addInventory(event){
	//Set the values to update
	
	var $form = $("#inventory-form");
	var json = toJson($form);
	var url = getInventoryUrl();
    successFx = function(response){
		$('#add-inventory-modal').modal('toggle');
            getInventoryList();
			refreshInventoryForm();
        }
    ajaxRequest(url,'POST',json,successFx,1);

	return false;
}
function refreshInventoryForm()
{
$("#inventory-form input[name=quantity]").val("");
$("#inventory-form input[name=barcode]").val("");


}

function updateInventory(event){
	$('#edit-inventory-modal').modal('toggle');

	id = $("#inventory-edit-form input[name=id]").val();
	
	//Get the ID
	var url = getInventoryUrl() + "/" + id;

	//Set the values to update
	var $form = $("#inventory-edit-form");
	var json = toJson($form);
	console.log(json);
    successFx = function(response){
        getInventoryList();
		refreshInventoryForm();
    }
    ajaxRequest(url,'PUT',json,successFx,1);

	return false;
}


function getInventoryList(){
	var url = getInventoryUrl();
	successFx = function(data){
			inventorydata=data;
            displayInventoryList(data);
        }
    ajaxRequest(url,'GET',1,successFx,0);
}

function getInventoryData()
{
	var url=getInventoryUrl();
	successFx=function(data)
	{
		inventorydata=data;
	}
	ajaxRequest(url,'GET',1,successFx,1);
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(event){
	var file = $('#inventoryFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}

	//Process next row
	var row = fileData[processCount];
	processCount++;

	var json = JSON.stringify(row);
	var url = getInventoryUrl();

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
		successMessageDisplay("Successful");
	   		uploadRows();
	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayInventoryList(data){
	var $tbody = $('#inventory-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		console.log(e.product_id);
		var buttonHtml = ' <button type="button" class="btn btn-group btn-sm btn-link" onclick="displayEditInventory(\'' + e.product_id + '\')"><span class="material-icons">edit</span></button>'
		var row = '<tr>'
		+ '<td>'  + e.barcode + '</td>'
		+ '<td>'  + e.name + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function displayEditInventory(id){
	var url = getInventoryUrl() + "/" + id;
	console.log(url);
	console.log(id);
    successFx = function(data){
            displayInventory(data);
        }
    ajaxRequest(url,'GET',1,successFx,0);

}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#InventoryFile');
	$file.val('');
	$('#inventoryFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#inventoryFile');
	var fileName = $file.val();
	$('#inventoryFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-inventory-modal').modal('toggle');
}

function displayInventory(data){
console.log(data.barcode);
	$("#inventory-edit-form input[name=id]").val(data.product_id);
	$('#edit-inventory-modal').modal('toggle');
}

function addInventoryModal(event){
    $('#add-inventory-modal').modal('toggle')
}

function jsonToCsv(json){
    const items = json
	console.log(items)
    const replacer = (key, value) => value === null ? '' : value // specify how you want to handle null values here
    const header = Object.keys(items[0])
    const csv = [
    header.join('\t'), // header row first
    ...items.map(row => header.map(fieldName => JSON.stringify(row[fieldName], replacer)).join('\t'))
    ].join('\r\n')
    blob = new Blob([csv], { type: 'text/csv' });
    var csvUrl = window.webkitURL.createObjectURL(blob);
    ReportTitle = "inventoryreport";
    var filename =  (ReportTitle || 'UserExport') + '.csv';
    //this trick will generate a temp "a" tag
    var link = document.createElement("a");
    link.id = "lnkDwnldLnk";
    //this part will append the anchor tag and remove it after automatic click
    document.body.appendChild(link);
    $("#lnkDwnldLnk")
        .attr({
            'download': filename,
            'href': csvUrl
        });
    $('#lnkDwnldLnk')[0].click();
    document.body.removeChild(link);
    console.log(csv)
}
function downloadreport(){
	getInventoryData();
	jsonToCsv(inventorydata);
}
//INITIALIZATION CODEF
function init(){
	$('#downloads-inventory').click(downloadreport);
    $('#add-inventory').click(addInventoryModal);
	$('#adds-inventory').click(addInventory);
	$('#update-inventory').click(updateInventory);
	$('#refresh-data').click(getInventoryList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#inventoryFile').on('change', updateFileName);
}

$(document).ready(init);
$(document).ready(getInventoryList);

