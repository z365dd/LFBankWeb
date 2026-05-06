console.log('relatSysDet.js');



// 从父页面获取全局变量
// add新增 revice修改 detial详细
var SAVE_OR_REV = parent.SAVE_OR_REV;

$(function() {
	parent.window.$("#iframe_det").show();
	setData();
	$('#cancelBtn').on("click", cancel);
	
	$('#modBtn').click(function (){
		getRevice("revice");
	});
	
	$('#delBtn').click(function (){ 
		Delete();
	})
	
	
	
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

/* 获取详细数据,打开tab2传入数据 */
function getRevice(tp) {
	$.ajax({
		url:ctx + "/comp/ctrl/oper/relatSys/getDetail", 
		type:"GET",
		dataType:"json",
		data:{
			RELAT_SYS: getI("RELAT_SYS")
		},
		async:true, 
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var successMsg = "获取数据["+data.message+"]"; 
				showTip(successMsg,"success");
				
				var Data = data.dataSetResult[0].data[0];
				/*Data = JSON.parse(Data.FProdEntrQryDtlRes);*/
				
				parent.window.$("a[href^='#tab_mod']").click();
				var dataStr = JSON.stringify(Data);
				$.session.set("relatSysData",dataStr); 
				
			}
		}
	});
}
// 回显全部页面数据
function setData() {
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
		$("#STAT").multiselect("disable");
		$("#NODE_STAT").multiselect("disable");
		$("#LOGIN_STAT").multiselect("disable");
		$("#HLD_FLG").multiselect("disable");
	}
}
// 获取页面数据
function getData() {
	var data = {
		RELAT_SYS : getI("RELAT_SYS"),
		SYS_TP : getS("SYS_TP"),
		SYS_NAME : getI("SYS_NAME"),
		STAT : getS("STAT"),
		SYS_STAT : getI("SYS_STAT"),
		ORIG_SYS_STAT : getI("ORIG_SYS_STAT"),
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
		MEMB_ID : getI("MEMB_ID")
	}
	return data;
}

/*删除*/
function Delete(){
	goTop();
	Ewin.confirm({
		  title : "操作提示",
		  message : "确定删除吗？"
		 }).on(function(e) {
		  if (!e) {
		   return;
		  }
			$.post(ctx + "/comp/ctrl/oper/relatSys/delete", {
				RELAT_SYS : getI("RELAT_SYS")
			}, function(data) {
				if (data.returnCode !== undefined && "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					showContent(data.message, "success");
					parent.tab1();
				}
			}, "json");
		  })
}



