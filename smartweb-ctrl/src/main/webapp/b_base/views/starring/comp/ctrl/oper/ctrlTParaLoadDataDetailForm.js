
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出paraName*/
	var HID_paraName = $.session.get('HID_paraName');
	/*从session中移除paraName*/
	$.session.remove('HID_paraName');
	$("#HID_paraName").val(HID_paraName);

	getDetail(HID_paraName);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(paraName){
	console.info('get ctrlTParaLoadData info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaLoadData/get", {paraName:paraName}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#paraName').val(jsonObj.paraName);	
					$('#tabName').val(jsonObj.tabName);	
					$('#srcTabDesc').val(jsonObj.srcTabDesc);	
					$('#sameDbFlg').multiselect("select", [jsonObj.sameDbFlg]).multiselect('rebuild');
					$('#sameDbFlg').multiselect("disable");
					$('#srcDataSrc').multiselect("select", [jsonObj.srcDataSrc]).multiselect('rebuild');
					$('#srcDataSrc').multiselect("disable");
				}
			}

		}
	},
    "json");
}