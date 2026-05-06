
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出HID_id*/
	var HID_id = $.session.get('HID_id');
	/*从session中移除HID_id*/
	$.session.remove('HID_id');
	$("#HID_id").val(HID_id);

	getDetail(HID_id);
	
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
	confirmx('是否更新订单信息', function(){
		save();
	});
}

/**
 * 保存函数--保存订单信息修改
 * @returns
 */
function save(){
	var formData = $("#tPipOrdInfoForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/comp.prod.oper.cloudpay.info/tPipOrdInfo/update", formData,
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
function getDetail(HID_id){
	console.info('update tPipOrdInfo info......');
	$.post(ctx + "/comp.prod.oper.cloudpay.info/tPipOrdInfo/get", {id:HID_id}, function(data){
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
					$('#busiNo').val(jsonObj.busiNo);	
					$('#ordAmt').val(jsonObj.ordAmt);	
					$('#tranStat').val(jsonObj.tranStat);	
					$('#tranTime').val(jsonObj.tranTime);	
					$('#payAcct').val(jsonObj.payAcct);	
					$('#payAcctName').val(jsonObj.payAcctName);	
					$('#clob').val(jsonObj.clob);	
					$('#shortRmrk').val(jsonObj.shortRmrk);	
					$('#midRmrk').val(jsonObj.midRmrk);	
					$('#longRmrk').val(jsonObj.longRmrk);	
					$('#dac').val(jsonObj.dac);	
				}
			}
			
			/*触发校验*/
			$('#ordNo').blur();
			$('#custName').blur();
			$('#payNo').blur();
			$('#busiNo').blur();
			$('#ordAmt').blur();
			$('#tranStat').blur();
			$('#tranTime').blur();
			$('#payAcct').blur();
			$('#payAcctName').blur();
			$('#clob').blur();
			$('#shortRmrk').blur();
			$('#midRmrk').blur();
			$('#longRmrk').blur();
			$('#dac').blur();

		}
	},
    "json");
}