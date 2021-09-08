var brandata=[];
function getBrandUrl()
{
var baseUrl = $("meta[name=baseUrl]").attr("content")
console.log(baseUrl);
return baseUrl + "api/brand";
}
//BUTTON ACTION
function getBrandList()

{
	var url =getBrandUrl();
  successFx =  function(data){
 brandata=data;
 displayBrandList(data);
}
 ajaxRequest(url,'GET',1,successFx,0);
}

function getBrandData()
{
var url=getBrandUrl();
 successFx=function(data){
	brandata=data;
}
ajaxRequest(url,'GET',1,successFx,1);
}


//FILE UPLOAD METHODS
var FileData =[];
var errorData=[];
var processCount =0;
function processData(event)
{
var file = $('#brandFile')[0].files[0];
console.log(file);
readFileData(file,readFileDataCallBack)
}
function readFileDataCallBack(results)
{
fileData =results.data;
uploadRows();
}
function uploadRows()
{
//Update Progress
updateUploadDialog();
//IF EVERYTHING PROCESSED THEN RETURN
if(processCount == fileData.length)
{
return;
}
//PROCESS NEXT ROW
var row = fileData[processCount];
processCount++;
var json = JSON.stringify(row);
console.log(row);
var url = getBrandUrl();
//MAKE AJAX CALL
$.ajax({
url : url,
type: 'POST',
data :json,
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
console.log(row);
}
function downloadErrors(){
	writeFileData(errorData);
}
//UI DISPLAY METHOD

function displayBrandList(data)
{
var $tbody = $('#brand-table').find('tbody');
$tbody.empty();
for(var i in data)
{
var e =data[i];
var buttonHtml = '<button type="button" class="btn btn-group btn-sm btn-link" type="button" class="btn btn-group btn-sm btn-primary" onclick="displayEditBrand(' + e.brand_id + ')"><span class="material-icons">edit</span></button>';
var row = '<tr>'
+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function displayEditBrand(id)
{
	var url = getBrandUrl() + "/" + id;
	console.log(id);
	let successFx = function(data) {
    	    displayBrand(data);

    	}
    	ajaxRequest(url,'GET',1,successFx,0);
}
function resetUploadDialog(){
	//Reset file name
	var $file = $('#brandFile');
	$file.val('');
	$('#brandFileName').html("Choose File");
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
	var $file = $('#brandFile');
	var fileName = $file.val();
	fileName = fileName.substr(12);
	$('#brandFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-brand-modal').modal('toggle');
}
function displayBrand(data)
{
$("#brand-edit-form input[name=id]").val(data.brand_id);
	$("#brand-edit-form input[name=brand]").val(data.brand);
	$("#brand-edit-form input[name=category]").val(data.category);
	$('#edit-brand-modal').modal('toggle');

}

function updateBrand()
{	$('#edit-brand-modal').modal('toggle');
	id = $("#brand-edit-form input[name=id]").val();
	var url = getBrandUrl() + "/" + id;
//Set the values to update
	var $form = $("#brand-edit-form");
	var json = toJson($form);

    successFx = function(response){
                getBrandList();
            }

    ajaxRequest(url,'PUT',json,successFx,1);


	return false;
}
function addBrandForm(event){
    $('#add-brand-modal').modal('toggle');
}
function addBrand(event){
	//Set the values to update
	var $form = $("#brand-form");
	var json = toJson($form);
	var url = getBrandUrl();
	successFx = function(response){
		$('#add-brand-modal').modal('toggle');
                    getBrandList();
					refreshBrandForm();
                }
				
	ajaxRequest(url,'POST',json,successFx,1);


	return false;
}
function refreshBrandForm()
{
	$("#brand-form input[name=brand]").val("");
	$("#brand-form input[name=category]").val("");

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
    ReportTitle = "brandreport";
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
function downloadreport()
{
getBrandData();
jsonToCsv(brandata);
}
//INITIALIZATION CODE
function init(){
$('#add-brand').click(addBrandForm);
	$('#download-brands').click(downloadreport);
    $('#adds-brand').click(addBrand);
    $('#update-brand').click(updateBrand);
	$('#refresh-data').click(getBrandList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#brandFile').on('change', updateFileName);
}
$(document).ready(init);
$(document).ready(getBrandList);
