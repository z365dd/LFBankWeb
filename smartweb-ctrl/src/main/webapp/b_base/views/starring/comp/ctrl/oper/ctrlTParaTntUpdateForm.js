
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出tntNo*/
	var HID_tntNo = $.session.get('HID_tntNo');
	/*从session中移除tntNo*/
	$.session.remove('HID_tntNo');

	getDetail(HID_tntNo);
	
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
	confirmx('是否更新租户管理', function(){
		save();
	});
}

/**
 * 保存函数--保存租户管理修改
 * @returns
 */
function save(){
	var formData = $("#ctrlTParaTntForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaTnt/update", formData,
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
function getDetail(HID_tntNo){
	console.info('update ctrlTParaTnt info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaTnt/get", {tntNo:HID_tntNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#tntNo').val(jsonObj.tntNo);	
					$('#tntName').val(jsonObj.tntName);	
				}
			}
			
			/*触发校验*/
			$('#tntNo').blur();
			$('#tntName').blur();

		}
	},
    "json");
}