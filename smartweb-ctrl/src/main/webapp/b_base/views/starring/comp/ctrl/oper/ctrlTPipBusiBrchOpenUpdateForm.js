console.log("ctrlTPipBusiBrchOpenUpdateForm.js");


$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出busiNo*/
	var HID_busiNo = $.session.get('HID_busiNo');
	/*从session中移除busiNo*/
	$.session.remove('HID_busiNo');
	$("#HID_busiNo").val(HID_busiNo);
	/*从session中拿出brch*/
	var HID_brch = $.session.get('HID_brch');
	/*从session中移除brch*/
	$.session.remove('HID_brch');
	$("#HID_brch").val(HID_brch);

	getDetail(HID_busiNo, HID_brch);
	
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
	confirmx('是否更新业务机构开通', function(){
		save();
	});
}

/**
 * 保存函数--保存业务机构开通修改
 * @returns
 */
function save(){
	var formData = $("#ctrlTPipBusiBrchOpenForm").serializeObject();
	/*向后台发送参数*/
	$.post(ctx + "/comp/ctrl/oper/ctrlTPipBusiBrchOpen/update", formData,
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
function getDetail(HID_busiNo, HID_brch){
	console.info('update ctrlTPipBusiBrchOpen info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTPipBusiBrchOpen/get", {busiNo:HID_busiNo, brch:HID_brch}, function(data){
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
					$('#brch').multiselect("select", [jsonObj.brch]);
					$('#HID_brch').val(jsonObj.brch);
					$('#flg').val(jsonObj.flg); 
				}
			}
			
			/*触发校验*/
			$('#busiNo').blur();
			$('#brch').blur();

		}
	},
    "json");
}