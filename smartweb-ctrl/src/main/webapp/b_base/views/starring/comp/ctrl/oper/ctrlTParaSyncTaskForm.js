console.log("tParaDataSyncExecForm.js");
$(document).ready(function(){
	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(proof()){
			save();
		};
	});
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

/**
 * 保存函数--保存平台日切新增
 * @returns
 */
function save(){
	var formData = {
			stepNo:getS('stepNo'), 
			platDate:getDateValue('platDate')
	}

	/*向后台发送参数*/
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaSyncTask/dayChgNotice", formData,
		function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			    var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
				return '0';
			}else if(data.msg_type == "success"){
				var successMsg = "数据同步["+data.message+"]"; 
				showContent(successMsg,"success");
				console.info("数据同步成功");
			}
	}, "json");
	
}
function cancle(){
	closeBtnToDo(); 
}