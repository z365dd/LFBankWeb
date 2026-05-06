$(document).ready(function(){
	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(proof()){
			submit();
		};
	});
	
	getDetail(getI("userId"));
	
});

function submit(){
	confirmx('是否保存授权信息', function(){
		save();
	});
}

/**
 * 保存函数--保存框架定义新增
 * @returns
 */
function save(){
	console.log("--save--");
	var formData = $("#addForm").serializeObject();
	if($("#tntId").val()!=""&&$("#tntId").val()!=null){
		$.extend(formData,{"tntId":$("#tntId").val().join(',')});
	}
	/*向后台发送参数*/
	$.post(ctx + "/sys/userTnt/saveUserTnt", formData,
		function(data){ 
			console.log(data);
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			    var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
				return '0';
			}else if(data.msg_type == "success"){
				var successMsg = "授权交易["+data.message+"]"; 
				showContent(successMsg,"success");
				$("#btnCancel").click();
			}
	}, "json");
}

function getDetail(userId){
	console.info('get detail info......');
	$.post(ctx + "/sys/userTnt/list", {userId:userId}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			var tntArr = new Array();
			for (var i = 0; i < data.dataSetResult[0].data.length; i++) {
				tntArr[i] = data.dataSetResult[0].data[i].tntId;
			}
			$("#tntId").multiselect("select", tntArr).multiselect("refresh");
		}
	},
    "json");
}