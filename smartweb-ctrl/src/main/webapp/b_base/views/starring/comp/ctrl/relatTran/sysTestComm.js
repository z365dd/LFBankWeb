var $BUSI_NO;
var $RELAT_SYS;	
console.log("sysTestComm.js");

$(function(){
	parent.window.$("#iframe_comm").show();

	$("#SYS_TP").multiselect("disable");
	$("#RELAT_SYS").change(function(){
		getSysData();
	});
	
	$("#cancelBtn").click(function() {
		cancle();
	});
	$("#submitBtn").click(function() {
		if (proof()) {
			conn_btn();
		}
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


function cancle(){
	closeBtnToDo();
};
function getSysData(){
	$.post(ctx + "/comp/ctrl/oper/relatTran/getSingleData", {
		"RELAT_SYS" : getS("RELAT_SYS")
	}, function(data) { 
		if (data.retCode == "0000") { 
			var listData = data.list;
			setS("SYS_TP",listData.sysTp);
		} else {
			showContent(data.retCode,"error");
		}
	}, "json")
}

function conn_btn(){
		$.post(ctx + "/comp/ctrl/oper/relatTran/testconn", {	
			BUSI_NO :$BUSI_NO,
			//BUSI_NO:"0300030001",
			"RELAT_SYS" : getS("RELAT_SYS"),
			"SYS_TP" : getS("SYS_TP")
		}, function(data) {
			if (data.msg_type == "success") { 
				showContent(data.message,"success");
				
			} else {
				showContent(data.message,"error");
			}
		}, "json");
}