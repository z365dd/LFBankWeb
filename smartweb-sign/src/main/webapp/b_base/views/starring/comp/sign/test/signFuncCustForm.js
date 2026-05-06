console.log('signFuncCust.js');

/*
 * 父页面变量SAVE_OR_REV add新增，revice修改
 */
var SAVE_OR_REV = parent.SAVE_OR_REV;
// 下拉框加载 同步or异步 参数
var $IF_ASYNC = true;
if (SAVE_OR_REV != "add") {
	$IF_ASYNC = false;
}
var ACCT_FLG = true;
//日期校验标志
var flg=0;
//修改时，原限额信息
var limFlg = 0;
var chnlNo = 0;
//初始化组件
// 初始化组件
$(function() {

	/* 签约协议类型控制签约类型 */
	$('#SIGN_PROT_TP_ID').on("change", signTpFunc);
	/* 签约协议类型控制客户签约提交*/
	$("#SIGN_TP").on("change", signTPAdd);

	/* 账卡号反显账户名 */  
	$("#ACCT").change(function() {
		if (getI("ACCT")) {
			getAcctInfo();
		} else {
			ACCT_FLG = true;
			resetInput("ACCT_NAME");
			resetInput("CERT_NO");
			resetSelect("CERT_TP");
		}

	});
	/*限额类型为单笔时，默认笔数为1*/
	$("#LIM_FLG").change(function(){ 
		if (getS("LIM_FLG")=="00") {
			$("#LIM_NUM").attr("readonly","readonly");
			setI("LIM_NUM","1");
		} else {
			$("#LIM_NUM").removeAttr("readonly");
			resetInput("LIM_NUM");
		}
	}); 
	// 渠道号
	/*
	 * setSelect1("CHNL_NO1", "/comp/ctrl/oper/channel/getChnl", "CHNL_NO",
	 * "CHNL_NAME", null, $IF_ASYNC); setSelect1("CHNL_NO2",
	 * "/comp/ctrl/oper/channel/getChnl", "CHNL_NO", "CHNL_NAME", null,
	 * $IF_ASYNC);
	 */

	/* 提交按钮 */
	$('#sendData').on('click', sendData);

	/* 返回按钮 */
	$('#cancelBtn').on('click', cancel);

	/* table初始化 */
	tableClick("table");

	/* 增加btn，table增加一行 */
	$('#addRowBtn').click(function() {
		tableActionRow('table', "add");
	});

	/* table确认修改 */
	$('#reviceRowBtn').click(function() {
		tableActionRow('table', "revice");
	});
	/* 失效日期大于当前日期控制 */
	$("#PROT_END_DATE").blur(function() {
		var MyDate = new Date();
		var startTime_one = Date.parse($("[check-startTime-own]").val());
		/* 年月日 */
		var year = MyDate.getFullYear();
		var month = MyDate.getMonth() + 1;
		var ri = MyDate.getDate();
		var slash = "/";
		var my_Date = year + slash + month + slash + ri;
		var myDate = Date.parse(my_Date);
		if (isNaN(startTime_one) || myDate <= startTime_one) {
			var startTimeId = $("[check-startTime-own]").attr("id");
			$("#" + startTimeId).addClass('trueInput');
			$("#" + startTimeId).parent().siblings('span.warnBlock').remove();
			flg = 0;
		} else {
			var tipVal = '失效日期要大于当前日期';
			var nameId = $("[check-startTime-own]").attr("id");
			$("#" + nameId).removeClass('trueInput');
			Prompt(tipVal, nameId);
			flg = 1;
		}

	});
});
// 不同签约类型显示
function signTpFunc() {
	var signProtTpid = getS("SIGN_PROT_TP_ID")
	if (signProtTpid == "") {
		portionSelects("custSignDiv");
		return;
	}
	var Data = {
		SIGN_PROT_TP_ID : signProtTpid
	}
	/* 银税需求：签约协议号为第三方客户号+账号 */
	if (Data.SIGN_PROT_TP_ID == "4010000000002" && SAVE_OR_REV == "add") {
		hideInput("SIGN_PROT_NO");
		$("#OTH_CUST_NO").attr("check-empty", "true");
	} else {
		showInput("SIGN_PROT_NO");
		$("#OTH_CUST_NO").removeAttr("check-empty");
	}
	$.ajax({
		url : ctx + "/comp/sign/test/signFuncCust/getSignTp",
		type : "POST",
		dataType : "json",
		data : Data,
		async : true,
		success : function(data) {
			var signTp = data;
			if ("01" == signTp) {
				setS("SIGN_TP", "01");
				disabledS("SIGN_TP");
			} else if ("02" == signTp) {
				setS("SIGN_TP", "02");
				disabledS("SIGN_TP"); 
			} else if ("03" == signTp) {
				setS("SIGN_TP", "03");
				disabledS("SIGN_TP");
			} else {
				var errMsg = "错误信息[签约类型无法获取]";
				showContent(errMsg, "error");
				setS("SIGN_TP", "");
				enableS("SIGN_TP");
			}
			$("#SIGN_TP").change();
		}
	});
}

function signTPAdd() {
	var signTp = getS("SIGN_TP");
	if ("" == signTp) {
		$("#signTp01").hide();
		resetForm("signTp01");
		$("#signTp02").hide();
		resetForm("signTp02");
	} else if ("01" == signTp) {
		$("#signTp01").show();
		$("#signTp02").hide();
		resetForm("signTp02");
	} else if ("02" == signTp) {
		$("#signTp02").show();
		$("#signTp01").show();
		resetForm("signTp01");
	} else if ("03" == signTp) {
		$("#signTp02").show();
		$("#signTp01").show();
		// resetForm("signTp01");
	}

}

/* 取值，table新增一行 */
function tableActionRow(tableId, ifAddRevice) {
	if (portion("signTp02") && getS('LIM_FLG') && getI('LIM_NUM')) {
		var Data = {
			CHNL_NO : getS('CHNL_NO2'),
			CHNL_NO_STR : getST('CHNL_NO2'),
			LIM_FLG : getS('LIM_FLG'),
			LIM_FLG_STR : getST("LIM_FLG"),
			LIM_NUM : getI('LIM_NUM'),
			LIM_AMT : getI('LIM_AMT'),
			ACTION : "<a onclick='reviceRow(this)'>修改</a> <a onclick='deleteRow(this)'>删除</a>"
		}
		var rowArr = $('#table').bootstrapTable('getData');
		var num = rowArr.length;
		if (ifAddRevice == "add") {
			var i = 0
			for (; i < num; i++) {
				if (rowArr[i].LIM_FLG==Data.LIM_FLG && rowArr[i].CHNL_NO==Data.CHNL_NO)
					break;
			}
			if (i >= num) {
				// table增加行
				tableAddRow(tableId, Data);
			} else {
				showTip("该渠道下已有相同的限额类型信息！", "error");
			}
		} else if (ifAddRevice == "revice") {
			if (Data.LIM_FLG==limFlg && Data.CHNL_NO == chnlNo) {
				// 修改行
				tableReviceRow(tableId, Data);
			} else {
				var i = 0
				for (; i < num; i++) {
					if (rowArr[i].LIM_FLG==Data.LIM_FLG && rowArr[i].CHNL_NO==Data.CHNL_NO)
						break;
				}
				if (i >= num) {
					// 修改行
					tableReviceRow(tableId, Data);
				} else {
					showTip("该渠道下已有相同的限额类型信息！", "error");
				}
			}

		}
	} else {
		showTip("请输入完整的限额信息!", "error");
	}
}

// tale修改返回数据
function reviceRow(obj) {
	console.log(obj);
	var $reviceRowData = reviceRowData(obj);
	// 根据数据设置值
	setReviceRowData($reviceRowData);
}

// 点击修改回显值
function setReviceRowData(data) {
	setS("CHNL_NO2", data.CHNL_NO);
	setS("LIM_FLG", data.LIM_FLG);
	setI("LIM_NUM", data.LIM_NUM);
	setI("LIM_AMT", data.LIM_AMT);
	limFlg = data.LIM_FLG;
	chnlNo = data.CHNL_NO;
}

/* 提交执行 */
function sendData() {
	parent.goTop();
	if (portion('custSignDiv') && flg == 0 && ACCT_FLG) {
		var info = "";
		if (SAVE_OR_REV == "add") {
			info = "是否新增签约信息？";
		} else if (SAVE_OR_REV == "revice") {
			info =  "是否确认修改？"
		}
		Ewin.confirm({
			title : "操作提示",
			message : info
		}).on(function(e) {
			if (!e) {
				return;
			} else {
				var $url = ctx;
				if (SAVE_OR_REV == "add") {
					$url += "/comp/sign/test/signFuncCust/add";
				} else if (SAVE_OR_REV == "revice") {
					$url += "/comp/sign/test/signFuncCust/revice";
				}
				var $data = getData();

				$.ajax({
					url : $url,
					type : "POST",
					dataType : "json",
					data : $data,
					async : true,
					success : function(data) {
						if (data.returnCode !== undefined
								&& "0000" != data.returnCode) {
							var errMsg = "错误信息[" + data.message + "]";
							showContent(errMsg, "error");
							parent.goTop();
						} else {
							console.log(data.message);
							var successMsg = "提交[" + data.message + "]";
							showContent(successMsg, "success");
							showTip(successMsg, "success");
							parent.tab1();
						}
					}
				});
			}
		});
	
	} else {
		$("#PROT_END_DATE").blur();
		if (!ACCT_FLG) {
			$("#ACCT").change();
		}
		$('#custSignForm').find('span.warnBlock:first').prev().parent().find('input:first').focus();
		/*if (getS('SIGN_PROT_TP_ID')=="") {
			$('#SIGN_PROT_TP_ID').focus();//select聚焦不成功
		} else {
			$('#custSignForm').find('span.warnBlock:first').prev().parent().find('input:first').focus();
		}*/
	
	}
}
function getData() {
	var rowArr = $('#table').bootstrapTable('getData');
	var num = rowArr.length;
	var row = JSON.stringify(rowArr);
	var data = {
		// 前台默认通知：00
		// FLG:"01",

		ACCT : getI("ACCT"),
		SIGN_PROT_TP_ID : getS("SIGN_PROT_TP_ID"),
		SIGN_PROT_NO : getI("SIGN_PROT_NO"),
		OTH_CUST_NO : getI("OTH_CUST_NO"),
		OTH_CUST_NAME : getI("OTH_CUST_NAME"),
		PROT_END_DATE : dateDelete(getI("PROT_END_DATE")),
		// ACCT_TP : getI("ACCT_TP"),
		PHONE_NO : getI("PHONE_NO"),
		COMM_ADDR : getI("COMM_ADDR"),
		EMAIL_ADDR : getI("EMAIL_ADDR"),
		CHNL_NO1 : getS("CHNL_NO1"),
		SIGN_CTRCT_NO : getI("SIGN_CTRCT_NO"),
		LIST : row,
		LIMIT_NUM : num
	}

	/* 银税需求：签约协议号为第三方客户号+账号 */
	if (data.SIGN_PROT_TP_ID == "4010000000002") {
		data.SIGN_PROT_NO = data.OTH_CUST_NO + data.ACCT;
	}

	if (SAVE_OR_REV == "add") {
		var addData = {
			/*
			 * SIGN_TP:getS("SIGN_TP"), SIGN_PROT_TP_NAME :
			 * getST("SIGN_PROT_TP_NAME").split("|")[0],
			 */
			ACCT_NAME : getI("ACCT_NAME"),
			CERT_TP : getS("CERT_TP"),
			CERT_NO : getI("CERT_NO"),
		};
		/*开通渠道为空时，默认为全渠道*/
		if (getS("SIGN_TP")!="02"){
			if (getS("CHNL_NO1") == "" || getS("CHNL_NO1") == null || getS("CHNL_NO1") == undefined) {
				data.CHNL_NO1 = "000000";
			}
		}
		$.extend(data, addData);
	}
	return data;
}

/* 关闭执行 */
function cancel() {
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

// 设置值
function setData(data) {
	// var data =JSON.parse(data.replace(/[\\][\"]/g,"\""));
	var data = JSON.parse(data);
	setS('SIGN_PROT_TP_ID', data.SIGN_PROT_TP_ID);
	$("#SIGN_PROT_TP_ID").change();
	setI('OTH_CUST_NO', data.OTH_CUST_NO);
	// setI('OTH_CUST_NAME', data.OTH_CUST_NAME);
	setI('SIGN_PROT_NO', data.SIGN_PROT_NO);
	setI('SIGN_CTRCT_NO', data.SIGN_CTRCT_NO);
	setI('PROT_END_DATE', dateAdd(data.PROT_END_DATE));

	var acctNote = data.ACCT_NODE[0];
	setI('ACCT', acctNote.ACCT);
	setI('ACCT_NAME', acctNote.ACCT_NAME);
	setS('CERT_TP', acctNote.CERT_TP);
	setI('CERT_NO', acctNote.CERT_NO);
	setI('PHONE_NO', acctNote.PHONE_NO);
	setI('COMM_ADDR', acctNote.COMM_ADDR);
	setI('EMAIL_ADDR', acctNote.EMAIL_ADDR);

	if (data.CHNL_LIST != undefined) {
		var chnlArr = data.CHNL_LIST;
		var arrS = [];
		for (var a = 0; a < chnlArr.length; a++) {
			arrS.push(chnlArr[a].CHNL_NO);
		}
		var chnl1 = arrS.join(";");
		setS("CHNL_NO1", chnl1);
	}

	var limitArr = [];
	if (data.LIMIT_LIST != undefined) {
		limitArr = data.LIMIT_LIST;
		if (SAVE_OR_REV == "detail") {
			for (var a = 0; a < limitArr.length; a++) {
				limitArr[a].ACTION = "<a onclick='reviceRow(this)'>详情</a>";
				limitArr[a].CHNL_NO_STR = $("#CHNL_NO2").find("option[value= "+limitArr[a].CHNL_NO+"]").text();	
				changeZh(limitArr[a]);
			}
			$("#rowDiv").hide();
			disDiv("signTp02");
		} else if (SAVE_OR_REV == "revice") {
			for (var a = 0; a < limitArr.length; a++) {
				limitArr[a].ACTION = "<a onclick='reviceRow(this)'>修改</a> <a onclick='deleteRow(this)'>删除</a>";
				limitArr[a].CHNL_NO_STR = $("#CHNL_NO2").find("option[value= "+limitArr[a].CHNL_NO+"]").text();
				changeZh(limitArr[a]);
			}
		}
	}
	$('#table').bootstrapTable('load', limitArr);

	if (SAVE_OR_REV == "detail") {
		disDiv("custSignForm");
		$("#sendData").hide();
	} else if (SAVE_OR_REV == "revice") {
		disabledS("SIGN_PROT_TP_ID");
		disabledI("OTH_CUST_NO");
		disabledI("ACCT");
		disabledI("ACCT_NAME");
		disabledS("CERT_TP");
		disabledI("CERT_NO");
		disabledI("SIGN_PROT_NO");
	}

}

function changeZh(data) {
	if (data.LIM_FLG == "00") {
		data.LIM_FLG_STR = "单笔";
	} else if (data.LIM_FLG == "01") {
		data.LIM_FLG_STR = "日";
	} else if (data.LIM_FLG == "02") {
		data.LIM_FLG_STR = "旬";
	} else if (data.LIM_FLG == "03") {
		data.LIM_FLG_STR = "月";
	} else if (data.LIM_FLG == "04") {
		data.LIM_FLG_STR = "季";
	} else if (data.LIM_FLG == "05") {
		data.LIM_FLG_STR = "年";
	}
}


// 账户信息回显，记账组件，账户综合信息查询
function getAcctInfo(tp) {
	$.ajax({
		type : "post",
		url : ctx + "/comp/sign/test/signFuncCust/FactQryAcctInfo",
		data : {
			ACCT : getI("ACCT")
		},
		dataType : "json",
		/* async:false, */
		success : function(data) {
			if (data.returnCode !== undefined && "0000" != data.returnCode) {
				showContent(data.message, "error");
			} else {
				var Data = data.dataSetResult[0].data[0];
				if (typeof Data == "string") {
					showTip("该账/卡号信息不存在！");
					resetInput("ACCT_NAME");
					resetInput("CERT_NO");
					resetSelect("CERT_TP");
					ACCT_FLG = false;
				} else {
					setI('ACCT_NAME', Data.ACCT_NAME);
					setS('CERT_TP', Data.CERT_TP);
					setI('CERT_NO', Data.CERT_NO);
					ACCT_FLG = true;
				}
			}
		}
	});
}