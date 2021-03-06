function getProductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	
	return baseUrl + "/api/product";
}

//BUTTON ACTIONS


function updateProduct(event){
	$('#edit-product-modal').modal('toggle');
	//Get the ID
	var id = $("#product-edit-form input[name=id]").val();
	var url = getProductUrl() + "/" + id;

	//Set the values to update
	var $form = $("#product-edit-form");
	var json = toJson($form);
    successFx = function(response){
    getProductList();
    }
    ajaxRequest(url,'PUT',json,successFx,1)

	return false;
}


function getProductList(){

	var url = getProductUrl();
	successFx = function(data){
        displayProductList(data);
        }
    ajaxRequest(url,'GET',1,successFx,0)

}



// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#productFile')[0].files[0];
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
	var url = getProductUrl();


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

function displayProductList(data){
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	console.log(data);
	for(var i in data){
		var e = data[i];
		var buttonHtml = ' <button type="button" class="btn btn-group btn-sm btn-link" onclick="displayEditProduct(' + e.product_id + ')"><span class="material-icons">edit</span></button>'
		var row = '<tr>'
//		+ '<td>' + e.product_id + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + e.name + '</td>'
		+ '<td>' + e.mrp + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function displayEditProduct(id){
	var url = getProductUrl() + "/" + id;
	successFx = function(data){
        displayProduct(data);
        }
        ajaxRequest(url,'GET',1,successFx,0)

}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#productFile');
	$file.val('');
	$('#productFileName').html("Choose File");
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
	var $file = $('#productFile');
	var fileName = $file.val();
	$('#productFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-product-modal').modal('toggle');
}
function addProduct(event){
	//Set the values to update
	var $form = $("#product-form");
	var json = toJson($form);
	var url = getProductUrl();
    successFx = function(response){
        getProductList();
		refreshProductForm();
		$('#add-product-modal').modal('toggle');
        }
    ajaxRequest(url,'POST',json,successFx,1)
	

	return false;
}
function refreshProductForm()
{
	$("#product-form input[name=id]").val("");
	$("#product-form input[name=barcode]").val("");
	$("#product-form input[name=brand]").val("");
	$("#product-form input[name=category]").val("");
	$("#product-form input[name=name]").val("");
	$("#product-form input[name=mrp]").val("");
	
}
function displayProduct(data){
    $("#product-edit-form input[name=id]").val(data.product_id);
	$("#product-edit-form input[name=barcode]").val(data.barcode);
	$("#product-edit-form input[name=brand]").val(data.brand);
	$("#product-edit-form input[name=category]").val(data.category);
	$("#product-edit-form input[name=name]").val(data.name);
	$("#product-edit-form input[name=mrp]").val(data.mrp);
	$('#edit-product-modal').modal('toggle');
}
function addProductForm(){
    $('#add-product-modal').modal('toggle');
}

//INITIALIZATION CODE
function init(){
	$('#add-product').click(addProductForm);
	$('#adds-product').click(addProduct);
	$('#update-product').click(updateProduct);
	$('#refresh-data').click(getProductList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
}
$(document).ready(init);
$(document).ready(getProductList);