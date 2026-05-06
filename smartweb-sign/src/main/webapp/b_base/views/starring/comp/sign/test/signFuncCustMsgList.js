console.log('signFuncCustMsgList.js');

/*
 * 主页面全局变量SAVE_OR_REV detail为详细 用于子页面判断
 */
var SAVE_OR_REV="detail";

$(function() {
	$("#tab2").hide();
	
	/* 签约协议类型 */
	/*setSelect1("SIGN_PROT_TP_NO", "/comp/prod/oper/entrManage/signProtTp",
			"SIGN_PROT_TP_NO", "SIGN_PROT_TP_NAME", null, true);*/
 
	/* 查询 */
	$("#qryBtn").click(function() {
		if (proof("top1")) {
			resetTable("table");
			freshTable("table");
		} 

	});

	/* tab1点击 */
	$('#tab1').click(function() {
		$('#tab2').hide();
	});
	/* tab2点击 */
	$('#tab2').click(function() {
		ifr('panel2', '/comp/sign/test/signFuncCust/signFuncCustForm');
		$("#tab2").show();
	});
});

/* 查询table */
function queryParams(params) {
	var paramList = {
		pgside : 'server',// 服务器分页
		pageSize : params.limit,
		start : params.offset + 1,
		pageNo : getPage(params),
		sort : params.sort,
		order : params.order,
		limit : params.limit,
		// TODO
		SIGN_PROT_TP_ID:getI("SIGN_PROT_TP_ID"), 
		OTH_CUST_NO:getI("OTH_CUST_NO"),
		ACCT:getI("ACCT"), 
		SIGN_PROT_NO:getI("SIGN_PROT_NO"),
		SIGN_STAT:getI("SIGN_STAT"),
		/*SIGN_STR_DATE:getI("SIGN_STR_DATE"),
		SIGN_END_DATE:getI("SIGN_END_DATE"),*/
		TP:"detailQry"
	};
	return paramList;
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		return params.offset / params.limit + 1;
	}
}

/* tab1点击执行函数 */
function tab1() {
	$('#tab1').click();
	goTop();
	$("#tab2").hide();
}

/* 详细 */
function Detail(strJson) { 
	getDetail(strJson);
}

/* 确认 */
function Confirm(ser,signProtTpId,signProtNo,othCustNo,acct) { 
	Ewin.confirm({
		title : "操作提示",
		message : "确定签约吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
	var setData = {
			SER:ser,
			SIGN_PROT_TP_ID:signProtTpId,
			SIGN_PROT_NO:signProtNo,
			OTH_CUST_NO:othCustNo,
			ACCT:acct
	};
	$.ajax({
		url:ctx+"/comp/sign/test/signFuncCust/Confirm", 
		type:"GET",
		dataType:"json",
		data:setData,
		async:true,
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var successMsg = "确认["+data.message+"]"; 
				showTip(successMsg,"success");				
				freshTable("table");	
			}
		}
	});
	});
}

/*解约*/
function Can(ser,signProtTpId,signProtNo){
	Ewin.confirm({
		title : "操作提示",
		message : "确定解约吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
	var setData = {
			FLG:"N",
			SER:ser,
			SIGN_PROT_TP_ID:signProtTpId,
			SIGN_PROT_NO:signProtNo
	};
	$.ajax({
		url:ctx+"/comp/sign/test/signFuncCust/Can", 
		type:"GET",
		dataType:"json",
		data:setData,
		async:true,
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var successMsg = "解约["+data.message+"]"; 
				showTip(successMsg,"success");				
				freshTable("table");	
			}
		}
	});
	});
}

/* 获取详细数据,打开tab2传入数据 */
function getDetail(strJson, tp) {
	ifr('panel2', '/comp/sign/test/signFuncCust/signFuncCustForm'); 
	$('#tab2').click();
	$("#panel2").find('iframe').load(function() {
		$("#panel2").find('iframe')[0].contentWindow.setData(decodeURIComponent(strJson),SAVE_OR_REV);
	}); 
}
