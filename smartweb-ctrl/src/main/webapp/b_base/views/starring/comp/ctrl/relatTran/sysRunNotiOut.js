console.log("sysRunNotiOut.js");

$(document).ready(function(){
	parent.window.$("#iframe_outNoti").show();
	hideSelect("OPER_TP2");
	$("#SYS_TP").multiselect("disable");
	$("#RELAT_SYS").change(function(){
		getSysData();
	});
	
	$("#submitBtn").click(function(){
		if (proof()) {
			stopRun();
		}	
	});

	$("#cancelBtn").click(function(){
		closeBtnToDo();
		
	});
});

/*停运通知*/
function stopRun(){
	var operTp = getS("OPER_TP");
	if (operFlg == "Y") {
		operTp = getS("OPER_TP2");
	}
	var str_dt = dateConvert(getI("STR_DT"));
	var end_dt = dateConvert(getI("END_DT"));
	$.post(ctx + "/comp/ctrl/oper/relatTran/runNoti", {
		"USER_NAME":getI("USER_NAME"),
		"PWD":getI("PWD"),
		"RUN_FLG" : getS("RUN_FLG"),
		"STR_DT" : str_dt,
		"END_DT" : end_dt,
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

function getSysData(){
	$.post(ctx + "/comp/ctrl/oper/relatTran/getSingleData", {
		"RELAT_SYS" : getS("RELAT_SYS")
	}, function(data) { 
		if (data.retCode == "0000") { 
			var listData = data.list;
			var str = listData.dimFlg;
			if (str.charAt(3) == 'Y') {
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
function dateConvert(dateParms){ 
   
   /* 将字符串日期转换为日期格式*/
    var date = new Date(Date.parse(dateParms.replace(/-/g,   "/")));
    
    var Y = date.getFullYear();
	var M = date.getMonth() + 1;
		M = M < 10 ? '0' + M : M;/*不够两位补充0*/
	var D = date.getDate();
		D = D < 10 ? '0' + D : D;
	var H = date.getHours();
		H = H < 10 ? '0' + H : H;
	var Mi = date.getMinutes();
		Mi = Mi < 10 ? '0' + Mi : Mi;
	var S = date.getSeconds();
		S = S < 10 ? '0' + S : S;
      
    /*返回处理结果*/
    return ""+ Y + M + D + H + Mi + S;
}
