console.log("forceOutNoti.js");

var operFlg = 'N';
$(document).ready(function(){
	parent.window.$("#iframe_forcOut").show();
	$("#SYS_TP").multiselect("disable");
	hideSelect("OPER_TP2");
	$("#RELAT_SYS").change(function(){
		getSysData();
	});
	
	$("#submitBtn").click(function(){
			if (proof()) {
				forceExit();
			}
	});

	$("#cancelBtn").click(function(){
		closeBtnToDo();
	});
});
function getSysData(){
	$.post(ctx + "/comp/ctrl/oper/relatTran/getSingleData", {
		"RELAT_SYS" : getS("RELAT_SYS")
	}, function(data) { 
		if (data.retCode == "0000") { 
			var listData = data.list;
			var str = listData.dimFlg;
			if (str.charAt(0) == 'Y') {
				resetSelect("OPER_TP");
				hideSelect("OPER_TP");
				showSelect("OPER_TP2");
				operFlg = 'Y';
			} else {
				resetSelect("OPER_TP2");
				hideSelect("OPER_TP2");
				showSelect("OPER_TP");
				operFlg = 'N';
			} 
			setS("SYS_TP",listData.sysTp);
			setI("RELAT_SYS_NAME",listData.sysName); 
			
		} else {
			showContent(data.retCode,"error");
		}
	}, "json")
}
/*强制退出*/
function forceExit(){
	var operTp = getS("OPER_TP");
	if (operFlg == "Y") {
		operTp = getS("OPER_TP2");
	}
	$.post(ctx + "/comp/ctrl/oper/relatTran/forceLogoutNoti", {
		"USER_NAME":getI("USER_NAME"),
		"PWD":getI("PWD"),
		"REASN_DESC" : getI("REASN_DESC"),
		"RELAT_SYS" : getS("RELAT_SYS"),
		"SYS_TP" : getS("SYS_TP"),
		"OPER_TP" : operTp
	}, function(data) { 
		if (data.msg_type == "success") {
		var DATA_VAL=data.dataSetResult[0].data[0]
		showContent(data.message,"success");
		} else {
			showContent(data.message,"error");
		}
	}, "json")
}

