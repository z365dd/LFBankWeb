
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出HID_id*/
	var HID_ordNo = $.session.get('HID_ordNo');
	/*从session中移除HID_id*/
	$.session.remove('HID_ordNo');
	$("#HID_ordNo").val(HID_ordNo);

	getDetail(HID_ordNo);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(ordNo){
	console.info('get tPipOrdInfo info......');
	$.post(ctx + "/prod/oper/cloudpay/info/get", {ordNo:ordNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#ordNo').val(jsonObj.ordNo);	
					$('#custName').val(jsonObj.custName);	
					$('#payNo').val(jsonObj.payNo);	
					$('#saleProdDesc').val(jsonObj.saleProdDesc);	
					$('#ordAmt').val(jsonObj.ordAmt);	
					//$('#tranStat').multiselect("select", [jsonObj.tranStat]).multiselect('rebuild');
					//$('#tranStat').multiselect("disable");
					//$('#tranStat').val(jsonObj.tranStat);	
					$('#tranStat').val(jsonObj.tranStatStr);
					$('#tranTime').val(jsonObj.tranTime);	
					$('#payAcct').val(jsonObj.payAcct);	
					$('#payAcctName').val(jsonObj.payAcctName);	
//					$('#clob').val(jsonObj.clob);	
//					$('#shortRmrk').val(jsonObj.shortRmrk);	
//					$('#midRmrk').val(jsonObj.midRmrk);	
//					$('#longRmrk').val(jsonObj.longRmrk);	
//					$('#dac').val(jsonObj.dac);	
				}
			}
		}
	},
    "json");
}