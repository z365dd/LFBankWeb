console.log('signVerifyForm.js');
$(function() {
	/*$("#BUSI_NO").click(function() {
		busiClick("BUSI_NO", "BUSI_NAME", "ENTR_NO");
	});*/

	// 渠道号
	/*setSelect1("CHNL_NO", "/comp/ctrl/oper/channel/getChnl", "CHNL_NO",
			"CHNL_NAME", null, true);*/
	
	/*账卡号反显账户名*/
	$("#ACCT").change(function(){ 
		getAcctInfo();
	});
	/* 关闭按钮 */
	$('#cancelBtn').on('click', closeBtnToDo);

	// 提交
	$('#addBtn').on('click', add);

});

// 签约校验提交
function add() {
	if (proof()) {
		Ewin.confirm({
			title : "操作提示",
			message : "是否确认提交？"
		}).on(function(e) {
			if (!e) {
				return;
			}
			$.get(ctx + "/comp/sign/test/signVerify/add", {
				SIGN_PROT_NO : getI("SIGN_PROT_NO"),
				OTH_CUST_NO : getI("OTH_CUST_NO"),
				ACCT : getI("ACCT"),
				ACCT_NAME : getI("ACCT_NAME"),
				CERT_TP : getS("CERT_TP"),
				CERT_NO : getI("CERT_NO"),
				SIGN_PROT_TP_ID : getI("SIGN_PROT_TP_ID"),
	
			}, function(data) {
				if (data.returnCode !== undefined && "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
					setS('STAT', "");
					disabledS("STAT");
					resetTable("table");
				} else {
					console.log(data.message);
					var successMsg = "提交[" + data.message + "]";
					showContent(successMsg, "success");
	
					var dataVal = data.dataSetResult[0].data[0];
					dataVal = JSON.parse(dataVal.FSignVerifSignVerifyRes);
					console.log(dataVal);
					// 设置值
					setVal(dataVal);
				}
			}, "json");
		});
	}

}

// 同步对账提交成功返回设置值
function setVal(Data) {
	if(Data.LIST==undefined){
		return;
	}
	var data = Data.LIST[0];
	setS('STAT', data.STAT);
	disabledS("STAT");
	resetTable("table");
	var dataLimitArr = data.LIMIT_LIST;
	if (dataLimitArr!=undefined) {
		for(var a=0;a<dataLimitArr.length;a++){
			dataLimitArr[a].CHNL_NO = $("#CHNL_NO2").find("option[value= "+dataLimitArr[a].CHNL_NO+"]").text();	
			var tp = dataLimitArr[a].LIM_FLG;
			if(tp=="00"){
				dataLimitArr[a].LIM_FLG="单笔";
			}else if (tp=="01"){
				dataLimitArr[a].LIM_FLG="日";
			}else if (tp=="02"){
				dataLimitArr[a].LIM_FLG="旬"; 
			}else if (tp=="03"){
				dataLimitArr[a].LIM_FLG="月";
			}else if (tp=="04"){ 
				dataLimitArr[a].LIM_FLG="季";
			}else if (tp=="05"){
				dataLimitArr[a].LIM_FLG="年";
			}
		}
		$('#table').bootstrapTable('load', dataLimitArr);
	}
}

//账户信息回显，记账组件，账户综合信息查询
function getAcctInfo(tp){
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
					setI('ACCT_NAME', Data.ACCT_NAME);
					setS('CERT_TP', Data.CERT_TP);
					setI('CERT_NO', Data.CERT_NO);
				}
			}
		});
}