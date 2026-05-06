
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出busiNo*/
	var HID_busiNo = $.session.get('HID_busiNo');
	/*从session中移除busiNo*/
	$.session.remove('HID_busiNo');
	$("#HID_busiNo").val(HID_busiNo);
	/*从session中拿出chnlNo*/
	var HID_chnlNo = $.session.get('HID_chnlNo');
	/*从session中移除chnlNo*/
	$.session.remove('HID_chnlNo');
 
	getDetail(HID_busiNo, HID_chnlNo);
	
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
	confirmx('是否更新业务渠道开通', function(){
		save();
	});
}

/**
 * 保存函数--保存业务渠道开通修改
 * @returns
 */
function save(){
	var formData = $("#ctrlTPipBusiChnlOpenForm").serializeObject();
	
	/*向后台发送参数*/ 
	$.post(ctx + "/comp/ctrl/oper/ctrlTPipBusiChnlOpen/update", formData,
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
function getDetail(HID_busiNo, HID_chnlNo){
	console.info('update ctrlTPipBusiChnlOpen info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTPipBusiChnlOpen/get", {busiNo:HID_busiNo, chnlNo:HID_chnlNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#busiNo').multiselect("select", [jsonObj.busiNo]).multiselect('rebuild');
					$('#busiNo').multiselect("disable");
					$('#HID_busiNo').val(jsonObj.busiNo);
					$('#chnlNo').multiselect("select", [jsonObj.chnlNo]).multiselect('rebuild');
					$('#ORI_chnlNo').val(jsonObj.chnlNo); 
					$('#flg').val(jsonObj.flg); 
				}
			}
			 
			/*触发校验*/
			$('#busiNo').blur();
			$('#chnlNo').blur();

		}
	},
    "json");
}