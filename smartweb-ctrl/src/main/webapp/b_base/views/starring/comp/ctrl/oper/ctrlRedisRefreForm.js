console.log('ctrlRedisRefreForm.js');

$(function(){
	/*操作类型为6时，显示参数名称下拉框*/
/*	sCs("OPER_TP", "TABLE_NAME", [ '4' ]);
	sCs("OPER_TP", "BUSI_NO", [ '2' ]);*/
	$("#busiNameDiv").hide();
	$("#tabNameDiv").hide();
	
	$("#OPER_TP").change(function() {
		var operTp = getS("OPER_TP");
		if (operTp == '2') {
			$("#busiNameDiv").show();
			$("#tabNameDiv").hide();
		} else if (operTp == '4') {
			$("#busiNameDiv").hide();
			$("#tabNameDiv").show();
		} else {
			$("#busiNameDiv").hide();
			$("#tabNameDiv").hide();
		}
	});
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
	var operTp = getS("OPER_TP");
	var paraName = "";
	if (operTp == "4") {
		paraName = getS("TABLE_NAME")
	} else if (operTp == "2") {
		paraName = getS("BUSI_NO"); 
	} 
	$.post(ctx + "/comp/ctrl/oper/ctrlRedisRefre/sysParaUpdate", {
		"OPER_TP" : operTp,
		"PARA_NAME" : paraName
	}, function(data) { 
		if (data.msg_type == "success") {
		var DATA_VAL=data.dataSetResult[0].data[0]
		showContent(data.message,"success");
		//setS("OPER_TP","0"); 
		//$('#TABLE_NAME').multiselect('dataprovider', []);
		//$('#BUSI_NO').multiselect('dataprovider', []);
		} else {
			showContent(data.message,"error");
		}
	}, "json")
}
