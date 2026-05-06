
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出brch*/
	var HID_brch = $.session.get('HID_brch');
	/*从session中移除brch*/
	$.session.remove('HID_brch');
	$("#HID_brch").val(HID_brch);
	/*从session中拿出tlrNo*/
	var HID_tlrNo = $.session.get('HID_tlrNo');
	/*从session中移除tlrNo*/
	$.session.remove('HID_tlrNo');

	getDetail(HID_brch, HID_tlrNo);
	
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
	confirmx('是否更新柜员管理', function(){
		save();
	});
}

/**
 * 保存函数--保存柜员管理修改
 * @returns
 */
function save(){
	var formData = $("#tParaTlrForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/comp/ctrl/oper/tParaTlr/update", formData,
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
function getDetail(HID_brch, HID_tlrNo){
	console.info('update tParaTlr info......');
	$.post(ctx + "/comp/ctrl/oper/tParaTlr/get", {brch:HID_brch, tlrNo:HID_tlrNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#brch').multiselect("select", [jsonObj.brch]).multiselect('rebuild');
					$('#brch').multiselect("disable");
					$('#HID_brch').val(jsonObj.brch);
					$('#tlrNo').val(jsonObj.tlrNo);	
					$('#tlrName').val(jsonObj.tlrName);	
					$('#tlrLvl').multiselect("select", [jsonObj.tlrLvl]).multiselect('rebuild');
					$('#flg').multiselect("select", [jsonObj.flg]).multiselect('rebuild');
				}
			}
			
			/*触发校验*/
			$('#brch').blur();
			$('#tlrNo').blur();
			$('#tlrName').blur();
			$('#tlrLvl').blur();
			$('#flg').blur();

		}
	},
    "json");
}