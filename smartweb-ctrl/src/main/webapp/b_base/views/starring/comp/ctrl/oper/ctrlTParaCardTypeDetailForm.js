
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出cardBinNo*/
	var HID_cardBinNo = $.session.get('HID_cardBinNo');
	/*从session中移除cardBinNo*/
	$.session.remove('HID_cardBinNo');
	$("#HID_cardBinNo").val(HID_cardBinNo);

	getDetail(HID_cardBinNo);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(cardBinNo){
	console.info('get ctrlTParaCardType info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaCardType/get", {cardBinNo:cardBinNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#cardBinNo').val(jsonObj.cardBinNo);	
					$('#cardTp').multiselect("select", [jsonObj.cardTp]).multiselect('rebuild');
					$('#cardTp').multiselect("disable");
					$('#acctCardFlg').val(jsonObj.acctCardFlg);	
					$('#intOutBankFlg').val(jsonObj.intOutBankFlg);	
					$('#clrBank').val(jsonObj.clrBank);	
					$('#clrBankName').val(jsonObj.clrBankName);	
					$('#hostSys').val(jsonObj.hostSys);	
					$('#legaNo').multiselect("select", [jsonObj.legaNo]).multiselect('rebuild');
					$('#legaNo').multiselect("disable");
					$('#stat').multiselect("select", [jsonObj.stat]).multiselect('rebuild');
					$('#stat').multiselect("disable");
					$('#cardTpName').val(jsonObj.cardTpName);	
				}
			}

		}
	},
    "json");
}