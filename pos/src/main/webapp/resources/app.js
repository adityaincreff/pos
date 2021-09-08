
//HELPER METHOD
function toJson($form){
    var serialized = $form.serializeArray();
    console.log(serialized);
    var s = '';
    var data = {};
    for(s in serialized){
        data[serialized[s]['name']] = serialized[s]['value']
    }
    var json = JSON.stringify(data);
    return json;
}
function successMessageDisplay(successMessage){
    nativeToast({
          message: successMessage ,
          position: 'north',
          timeout: 2000,
          type: 'success',
          closeOnClick: true
        });
}
function handleFrontendError(errorMessage){
    nativeToast({
          message: errorMessage ,
          position: 'north-east',
          timeout: 0,
          type: 'error',
          closeOnClick: true
        });
}


function handleAjaxError(response){
var response = JSON.parse(response.responseText);
console.log(response);
nativeToast({
      message: response.message ,
      position: 'north-east',
      timeout: 0,
      type: 'error',
      closeOnClick: true
    });
}
function ajaxRequest(url,method,data,successFx,messageDisplay){
    console.log(messageDisplay);
    $.ajax({
    	url: url,
    	type: method,
    	data: data,
    	headers: {
            'Content-Type': 'application/json'
        },
    	success: function(response) {
            if(messageDisplay == '1'){
                successMessageDisplay("Successful");
             }
    		successFx(response);
    	},
    	error: handleAjaxError
    });
}

function readFileData(file, callback){
	var config = {
		header: true,
		delimiter: "\t",
		skipEmptyLines: "greedy",
		complete: function(results) {
			callback(results);
	  	}
	}
	Papa.parse(file, config);
}


function writeFileData(arr){
	var config = {
		quoteChar: '',
		escapeChar: '',
		delimiter: "\t"
	};

	var data = Papa.unparse(arr, config);
    var blob = new Blob([data], {type: 'text/tsv;charset=utf-8;'});
    var fileUrl =  null;

    if (navigator.msSaveBlob) {
        fileUrl = navigator.msSaveBlob(blob, 'download.tsv');
    } else {
        fileUrl = window.URL.createObjectURL(blob);
    }
    var tempLink = document.createElement('a');
    tempLink.href = fileUrl;
    tempLink.setAttribute('download', 'download.tsv');
    tempLink.click();
}
