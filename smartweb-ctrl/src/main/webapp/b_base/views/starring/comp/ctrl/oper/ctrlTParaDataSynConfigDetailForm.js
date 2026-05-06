
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出stepNo*/
	var HID_stepNo = $.session.get('HID_stepNo');
	/*从session中移除stepNo*/
	$.session.remove('HID_stepNo');
	$("#HID_stepNo").val(HID_stepNo);

	getDetail(HID_stepNo);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(stepNo){
	console.info('get ctrlTParaDataSynConfig info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaDataSynConfig/get", {stepNo:stepNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#srcTp').multiselect("select", [jsonObj.srcTp]).multiselect('rebuild');
					$('#srcTp').multiselect("disable");
					$('#uptTp').multiselect("select", [jsonObj.uptTp]).multiselect('rebuild');
					$('#uptTp').multiselect("disable");
					$('#srcTabName').val(jsonObj.srcTabName);	
					$('#srcDataSrc').multiselect("select", [jsonObj.srcDataSrc]).multiselect('rebuild');
					$('#srcDataSrc').multiselect("disable");
					$('#srcTabDesc').val(jsonObj.srcTabDesc);	
					$('#relatSys').multiselect("select", [jsonObj.relatSys]).multiselect('rebuild');
					$('#relatSys').multiselect("disable");
					$('#sameDbFlg').multiselect("select", [jsonObj.sameDbFlg]).multiselect('rebuild');
					$('#sameDbFlg').multiselect("disable");
					$('#dstDataSrc').multiselect("select", [jsonObj.dstDataSrc]).multiselect('rebuild');
					$('#dstDataSrc').multiselect("disable");
					$('#dstTabName').val(jsonObj.dstTabName);	
					$('#dstTabDesc').val(jsonObj.dstTabDesc);	
					$('#stepNo').val(jsonObj.stepNo);	
				}
			}

		}
	},
    "json");
}