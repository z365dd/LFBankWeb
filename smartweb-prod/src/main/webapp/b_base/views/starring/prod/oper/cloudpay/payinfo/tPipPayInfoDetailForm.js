
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出HID_id*/
	var HID_busiNo = $.session.get('HID_busiNo');
	var HID_payNo = $.session.get('HID_payNo');
	/*从session中移除HID_id*/
	$.session.remove('HID_busiNo');
	$.session.remove('HID_payNo');
	$("#HID_busiNo").val(HID_busiNo);
	$("#HID_payNo").val(HID_payNo);

	getDetail(HID_busiNo,HID_payNo);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(busiNo,payNo){
	console.info('get tPipPayInfo info......');
	$.post(ctx + "/prod/oper/cloudpay/payinfo/get", {busiNo:busiNo,payNo:payNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#saleProdDesc').val(jsonObj.saleProdDesc);	
					$('#ser').val(jsonObj.ser);	
					$('#payNo').val(jsonObj.payNo);	
					$('#projNo').val(jsonObj.projNo);	
					$('#custName').val(jsonObj.custName);	
					$('#ordAmt').val(jsonObj.ordAmt);	
					$('#tranStat').val(jsonObj.tranStatStr);	
					$('#tranTime').val(jsonObj.tranTime);
					$('#infoData').val(jsonObj.infoData);		
				}
			}

		}
	},
    "json");
}