console.log('tranChangePwd.js');
var $BUSI_NO;	//其他页面跳转
var $RELAT_SYS;	//其他页面跳转
var operFlg = 'N';

$(function(){
	parent.window.$("#iframe_pwd").show();
	hideSelect("OPER_TP2");
	$("#SYS_TP").multiselect("disable");
	$("#RELAT_SYS").change(function(){
		getSysData();
	});
	
	$("#submitBtn").click(function() {
			if (proof()) {
				change();
			}	
			
	});
	$("#cancelBtn").click(function() {
		closeBtnToDo();
	});
	$BUSI_NO = $.session.get("BUSI_NO");
	$.session.remove("BUSI_NO");
	if($BUSI_NO!=undefined){
			$.post(ctx + "/comp/pubfunc/ctrlTPipBusi/get", {
				busiNo : $BUSI_NO
			}, function(data) {
				if (data.returnCode !== undefined && "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					var busiData = data.dataSetResult[0].data[0];
					$RELAT_SYS = busiData.relatSys;
					setS("RELAT_SYS",$RELAT_SYS);
					$("#RELAT_SYS").change();
				}
			}, "json")
	}
	
});

function getSysData(){
	$.post(ctx + "/comp/ctrl/oper/relatTran/getSingleData", {
		"RELAT_SYS" : getS("RELAT_SYS")
	}, function(data) { 
		if (data.retCode == "0000") { 
			var listData = data.list;
			var str = listData.dimFlg;
			if (str.charAt(5) == 'Y') {
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

/*修改密码*/
function change(){
	var operTp = getS("OPER_TP");
	if (operFlg == "Y") {
		operTp = getS("OPER_TP2");
	}
	$.post(ctx + "/comp/ctrl/oper/relatTran/change", {
		BUSI_NO :$BUSI_NO,
		"USER_NAME":getI("USER_NAME"),
		"PWD":getI("PWD"),
		"NEW_PWD":getI("NEW_PWD"),
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
