
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出dataSrc*/
	var HID_dataSrc = $.session.get('HID_dataSrc');
	/*从session中移除dataSrc*/
	$.session.remove('HID_dataSrc');
	$("#HID_dataSrc").val(HID_dataSrc);

	getDetail(HID_dataSrc);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(dataSrc){
	console.info('get ctrlTParaDataSource info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaDataSource/get", {dataSrc:dataSrc}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#dataSrc').val(jsonObj.dataSrc);	
					$('#dataSrcName').val(jsonObj.dataSrcName);	
					$('#dbTp').multiselect("select", [jsonObj.dbTp]).multiselect('rebuild');
					$('#dbTp').multiselect("disable");
					$('#dataSrcAddr').val(jsonObj.dataSrcAddr);	
					$('#dbUserName').val(jsonObj.dbUserName);	
					$('#dbPwd').val(jsonObj.dbPwd);	
				}
			}

		}
	},
    "json");
}