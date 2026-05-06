
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出cardBinNo*/
	var HID_cardBinNo = $.session.get('HID_cardBinNo');
	/*从session中移除cardBinNo*/
	$.session.remove('HID_cardBinNo');

	getDetail(HID_cardBinNo);
	
	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(proof()){
			submit();
		};
	});
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function submit(){
	confirmx('是否更新卡种类管理', function(){
		save();
	});
}

/**
 * 保存函数--保存卡种类管理修改
 * @returns
 */
function save(){
	var formData = $("#ctrlTParaCardTypeForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaCardType/update", formData,
		function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			    var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
				return '0';
			}else if(data.msg_type == "success"){
				var successMsg = "修改交易["+data.message+"]"; 
				showContent(successMsg,"success");
				console.info("修改交易成功");
				cancle();
			}
	}, "json");
	
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(HID_cardBinNo){
	console.info('update ctrlTParaCardType info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaCardType/get", {cardBinNo:HID_cardBinNo}, function(data){
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
					$('#acctCardFlg').val(jsonObj.acctCardFlg);	
					$('#intOutBankFlg').val(jsonObj.intOutBankFlg);	
					$('#clrBank').val(jsonObj.clrBank);	
					$('#clrBankName').val(jsonObj.clrBankName);	
					$('#hostSys').val(jsonObj.hostSys);	
					$('#legaNo').multiselect("select", [jsonObj.legaNo]).multiselect('rebuild');
					$('#stat').multiselect("select", [jsonObj.stat]).multiselect('rebuild');
					$('#cardTpName').val(jsonObj.cardTpName);	
				}
			}
			
			/*触发校验*/
			$('#cardBinNo').blur();
			$('#cardTp').blur();
			$('#acctCardFlg').blur();
			$('#intOutBankFlg').blur();
			$('#clrBank').blur();
			$('#clrBankName').blur();
			$('#hostSys').blur();
			$('#legaNo').blur();
			$('#stat').blur();
			$('#cardTpName').blur();

		}
	},
    "json");
}