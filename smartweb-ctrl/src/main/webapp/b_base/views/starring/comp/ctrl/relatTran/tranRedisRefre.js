console.log('tranRedisRefre.js');

$(function(){
	parent.window.$("#iframe_refre").show();
	
	/*操作类型为4或5时，显示参数名称下拉框*/
	sCs("OPER_TP", "PARA_NAME", [ '4','5' ]);
	
	$("#submitBtn").click(function() {
			if (proof()) {
				sysParaUpdate();
			}	
			
	});
	$("#cancelBtn").click(function() {
		closeBtnToDo();
	});
});

/*系统参数刷新*/
function sysParaUpdate(){
	$.post(ctx + "/comp/ctrl/oper/relatTran/sysParaUpdate", {
		"OPER_TP" : getS("OPER_TP"),
		"PARA_NAME" : getS("PARA_NAME")
	}, function(data) { 
		if (data.msg_type == "success") {
		var DATA_VAL=data.dataSetResult[0].data[0]
		showContent(data.message,"success");
		} else {
			showContent(data.message,"error");
		}
	}, "json")
}
