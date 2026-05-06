console.log('relatSysMod.js');



// 从父页面获取全局变量
// add新增 revice修改 detial详细
var SAVE_OR_REV = parent.SAVE_OR_REV;

$(function() {
	parent.window.$("#iframe_mod").show();
	SAVE_OR_REV = "revice";
	setData();
	$('#addBtn').on("click", {
		tp : "effect" 
	}, Save);
	$('#cancelBtn').on("click", cancel);
	
	
});

function cancel(){
	parent.goTop();
	Ewin.confirm({
		title : "操作提示",
		message : "确定返回吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		parent.tab1();
	});
}


// 回显全部页面数据
function setData(Data) {
	if (SAVE_OR_REV == "revice") {
		disabledI("RELAT_SYS");
		disabledS("SYS_TP");
	}
	
	// tab1

	var DATA_STR = $.session.get("relatSysData");
	$.session.remove("relatSysData");
	var enData = JSON.parse(DATA_STR);
	if (undefined != enData) {
		//setI("DIM_FLG", enData.dimFlg);
		setI("RELAT_SYS", enData.relatSys);	
		setS("SYS_TP", enData.sysTp);	
		setI("SYS_NAME", enData.sysName);
		setS("STAT", enData.stat);
		setI("SYS_STAT", enData.sysStat);
		setI("ORIG_SYS_STAT", enData.origSysStat);
		setI("CLR_BRCH", enData.clrBrch);
		setI("CLR_BANK", enData.clrBank);
		setI("OTH_DATE", enData.othDate);
		setI("ORIG_OTH_DATE", enData.origOthDate);
		setS("NODE_STAT", enData.nodeStat);
		setS("LOGIN_STAT", enData.loginStat);
		setS("HLD_FLG", enData.hldFlg);
		setI("MSG_SKEY", enData.msgSkey);
		setI("SEQ_CRT_ID", enData.seqCrtId);
		setI("FILE_SVR_ID", enData.fileSvrId);
		setI("LOGIN_ID", enData.loginId);
		setI("LOGIN_PWD", enData.loginPwd);
		setI("REASN_DESC", enData.reasnDesc);
		setI("MEMB_ID", enData.membId);
		$("#SYS_TP").multiselect("disable");
		
		var str = enData.dimFlg;
		if (str.charAt(0) == 'Y') {
			$("#forcOut").prop("checked", true); 
		}
		if (str.charAt(1) == 'Y') {
			$("#login").prop("checked", true); 
		}
		if (str.charAt(2) == 'Y') {
			$("#logout").prop("checked", true); 
		}
		if (str.charAt(3) == 'Y') {
			$("#outNoti").prop("checked", true); 
		}
		if (str.charAt(4) == 'Y') {
			$("#stat").prop("checked", true); 
		}
		if (str.charAt(5) == 'Y') {
			$("#pwd").prop("checked", true); 
		}
		if (str.charAt(6) == 'Y') {
			$("#comm").prop("checked", true); 
		}
	}
}
// 获取页面数据
function getData() { 
	 var dimFlgStr = "NNNNNNNNNNNNNNNNNNN";
	 $('input[name="dimFlgCheck"]:checked').each(function(){
		 var i = parseInt($(this).val());  
		 dimFlgStr = dimFlgStr.substr(0,i) + "Y" + dimFlgStr.substr(i+1);
	 });
	var data = {
		RELAT_SYS : getI("RELAT_SYS"), 
		SYS_TP : getS("SYS_TP"),
		SYS_NAME : getI("SYS_NAME"),
		STAT : getS("STAT"),
		SYS_STAT : getI("SYS_STAT"),
		ORIG_SYS_STAT : getI("ORIG_SYS_STAT"),
		CLR_BRCH : getI("CLR_BRCH"),
		CLR_BANK : getI("CLR_BANK"),
		OTH_DATE : getI("OTH_DATE"),
		ORIG_OTH_DATE : getI("ORIG_OTH_DATE"),
		NODE_STAT : getS("NODE_STAT"), 
		LOGIN_STAT : getS("LOGIN_STAT"),
		HLD_FLG : getS("HLD_FLG"),
		MSG_SKEY : getI("MSG_SKEY"),
		SEQ_CRT_ID : getI("SEQ_CRT_ID"),
		FILE_SVR_ID : getI("FILE_SVR_ID"),
		LOGIN_ID : getI("LOGIN_ID"),
		LOGIN_PWD : getI("LOGIN_PWD"),
		REASN_DESC : getI("REASN_DESC"),
		MEMB_ID : getI("MEMB_ID"),
		DIM_FLG : dimFlgStr
	}
	return data;
}

// 暂存、生效
function Save(e) {
	if(!proof()){
		return;
	}
	if(SAVE_OR_REV != 'revice'){
	//检查关联系统号
	$.post(ctx+"/comp/ctrl/oper/relatSys/qry",{RELAT_SYS:getI("RELAT_SYS")},function(data){
		if (data.returnCode !== undefined
				&& "0000" != data.returnCode) {
			var errMsg = "错误信息[关联系统号检测异常]";
			showContent(errMsg, "error");
		} else {
			var dataArr = data.dataSetResult[0].data;
			if(dataArr.length>0 && dataArr[0]!="["){
				flg=true;
				showContent("关联系统号已存在", "error")
			}else{
				var tp = e.data.tp;
				var reqData = getData();
				if (tp == "save") {
					reqData.OPER_TP = "3";
				} else if (tp == "effect") {
					if (SAVE_OR_REV == "add") {
						reqData.OPER_TP = "1";
					} else if (SAVE_OR_REV == "revice") {
						reqData.OPER_TP = "2";
					}
				}
				$.ajax({
					url : ctx + "/comp/ctrl/oper/relatSys/add",
					type : "POST",
					dataType : "json",
					data : reqData,
					async : true,
					success : function(data) {
						if (data.returnCode !== undefined && "0000" != data.returnCode) {
							var errMsg = "错误信息[" + data.message + "]";
							showContent(errMsg, "error");
						} else {
							console.log(data.message);
							var successMsg = "提交后台[" + data.message + "]";
							showContent(successMsg, "success");

							parent.tab1();
						}
				}
			
		},"json");
		
	}
		}
		},"json");
	}else{
		var tp = e.data.tp;
		var reqData = getData();
		if (tp == "save") {
			reqData.OPER_TP = "3";
		} else if (tp == "effect") {
			if (SAVE_OR_REV == "add") {
				reqData.OPER_TP = "1";
			} else if (SAVE_OR_REV == "revice") {
				reqData.OPER_TP = "2";
			}
		}
		$.ajax({
			url : ctx + "/comp/ctrl/oper/relatSys/add",
			type : "POST",
			dataType : "json",
			data : reqData,
			async : true,
			success : function(data) {
				if (data.returnCode !== undefined && "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					var successMsg = "提交后台[" + data.message + "]";
					showContent(successMsg, "success");


					parent.tab1();
				}
			} 
		}); 
	}
}



