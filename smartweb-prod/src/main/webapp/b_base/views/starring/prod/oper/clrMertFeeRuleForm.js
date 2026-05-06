console.log("clrMertFeeRuleForm.js");
$(function(){
	$("#submit_btn").click(function(){
		if(proof()){
			submit();
		};
	})
	
	$("#close_btn").click(function(){
		  parent.goTop();
		  Ewin.confirm({
			  title : "操作提示",
			  message : "确定关闭吗？"
			 }).on(function(e) {
			  if (!e) {
			   return;
			  }
			  parent.$("#tab1").click();
			  })
	})

	/*table控件处理*/
	$("#addTabBtn").on("click" ,function(){
		tableActionRow('table',"add");
	});
	$("#setTabBtn").on("click" ,function(){
		tableActionRow('table',"revice");
	});
	

	/*div块控制*/
	divCtrl();

})

function divCtrl(){
	$("#clrMeth").change(function(){
		var clrMeth=$(this).val();
		if("00"==clrMeth){//不清算
			$("#clrCtrlDiv").hide();
			$("#acctDiv").hide();
			$("#caltDiv").hide();
		}else{
			$("#clrCtrlDiv").show();
			$("#acctDiv").show();
			$("#caltDiv").show();
		}
	})
	
	$("#clrDimTp").change(function(){
		var clrDimTp = $("#clrDimTp").val();
		if (clrDimTp == "01") {
			setI("mertNo","0");
			$("#mertNo").attr('disabled', 'disabled');
		} else {
			setI("mertNo","");
			$("#mertNo").removeAttr('disabled');
		}
	});
	$("#clrDimTp").change();
	
	$("#feeCaltMeth").change(function(){
		var feeCaltMeth=$(this).val();
		if("1"==feeCaltMeth ||"2"==feeCaltMeth){//按笔数
			$("#numDiv").show();
			$("#amtDiv").hide();
			$("#numLvlDiv").hide();
			$("#amtLvlDiv").hide();
		}else if("3"==feeCaltMeth ||"4"==feeCaltMeth){//金额比例
			$("#numDiv").hide();
			$("#amtDiv").show();
			$("#numLvlDiv").hide();
			$("#amtLvlDiv").hide();
		}else if("5"==feeCaltMeth ||"6"==feeCaltMeth){//笔数档次
			$("#numDiv").hide();
			$("#amtDiv").hide();
			$("#numLvlDiv").show();
			$("#amtLvlDiv").hide();
		}else if("7"==feeCaltMeth ||"8"==feeCaltMeth){//金额档次
			$("#numDiv").hide();
			$("#amtDiv").hide();
			$("#numLvlDiv").hide();
			$("#amtLvlDiv").show();
		}
	})

	/* table初始化 */
	tableClick("table");
}
//提交事件前检查
function preSubmit(){
	var startTime = getDateValue("strTime");
	var endTime = getDateValue("endTime");
	 if(startTime > endTime){
		showTip("清算开始时间要小于清算终止时间", "error");
		return false;
	}
	
	var clrDimTp = getS("clrDimTp");
	if (clrDimTp == "01") {//单商户清算
		var rowArr = $('#table').bootstrapTable('getData');
		if (rowArr.length > 1) {//清算账户列表大于1
			showTip("清算维度类型为单商户清算的情况下，只能存在一个清算账号参数","erroe");
			return false;
		}
	}
	
	return true;
}
function submit(){
	if(!preSubmit()){
		return false;
	}
	
	$.post(ctx+"/prod/oper/clrMertFeeRule/update",getFormData(),function(data){
		if (data.returnCode !== undefined
				&& "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
		} else {
			console.log(data.message);
			var successMsg = "["+data.message+"]"; 
			showContent(successMsg,"success");
			parent.goTop();
			parent.$('#tab1').click();
			parent.$("#qryBtn").click();
		}
	},"json")
}

function getFormData(){
	var formData = $("#tpipClrMertFeeRuleForm").serializeObject();
	formData.clrSndGrpFlg=getS("clrSndGrpFlg");
	
	//手续费计算表
	var feeCaltMeth=$("#feeCaltMeth").val();
	if("1"==feeCaltMeth ||"2"==feeCaltMeth){//按笔数
		var feeRuleRec="||"+getI('amtPerNum')+"|";
		formData.feeRuleExpr=feeRuleRec;
		
	}else if("3"==feeCaltMeth ||"4"==feeCaltMeth){//金额比例
		var feeRuleRec="|||"+getI('percentAmt');
		formData.feeRuleExpr=feeRuleRec;
	}else if("5"==feeCaltMeth ||"6"==feeCaltMeth){//笔数档次
		var feeRuleRec="";
		for(i=0;i<8;i++){
			if(getI('strNum'+(i+1))){
				feeRuleRec +=getI('strNum'+(i+1))+"|"+getI('endNum'+(i+1))+"|"+getI('amtPerNum'+(i+1))+"|"+"|";
				feeRuleRec +="@#@";
			}
		}
		formData.feeRuleExpr=feeRuleRec;
	}else if("7"==feeCaltMeth ||"8"==feeCaltMeth){//金额档次
		var feeRuleRec="";
		for(i=0;i<8;i++){
			if(getI('strAmt'+(i+1))){
				feeRuleRec +=getI('strAmt'+(i+1))+"|"+getI('endAmt'+(i+1))+"|"+"|"+getI('percentAmt'+(i+1))+"|";
				feeRuleRec +="@#@";
			}
		}
		formData.feeRuleExpr=feeRuleRec;
	}
	
	//转换表格控件值格式
	var acctList = $('#table').bootstrapTable('getData');
	formData.acctList = JSON.stringify(acctList);
	
	return formData ;
}

function setData(RULE_ID){
	$.post(ctx+"/prod/oper/clrMertFeeRule/getDetail",{RULE_ID:RULE_ID},function(data){
		if (data.returnCode !== undefined
				&& "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
		} else {
			console.log(data.message);
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				if (data.dataSetResult[i].dataSetName == "tpipClrMertFeeRuleDOResDs") {
					var sData = data.dataSetResult[i].data[0];
						setI("ruleId",sData.ruleId);
						setI("ruleName",sData.ruleName);
						setS("clrMeth",sData.clrMeth);
						setS("clrDimTp",sData.clrDimTp);
						setS("batProcFlg",sData.batProcFlg);
						setS("clrSndGrpFlg",sData.clrSndGrpFlg);
						setS("clrCycTp",sData.clrCycTp);
						setS("clrCyc",sData.clrCyc);
						setDateValue("strTime",sData.strTime);
						setDateValue("endTime",sData.endTime);
						setI('bean',sData.bean);
						setI('autoClrChnlNo',sData.autoClrChnlNo);
						
				}else if (data.dataSetResult[i].dataSetName == "tpipClrMertAcctDOResDs") {
					var list = data.dataSetResult[i].data;
                    if (undefined != list) {
                        for(var j=0;j<list.length;j++){
                            list[j].action = "<a onclick='reviceRow(this)'>修改</a> <a onclick='deleteRow(this)'>删除</a>";
                        }
                    }
                    $('#table').bootstrapTable('load', list);
				}else if (data.dataSetResult[i].dataSetName == "tpipClrMertFeeCaltDOResDs") {
					var sData = data.dataSetResult[i].data[0];
						setS("feeCaltMeth",sData.feeCaltMeth);
						setI('minFee',sData.minFee);
						setI('maxFee',sData.maxFee);
						setS('feePrcsnTp',sData.feePrcsnTp);
						
						var feeRuleRecArray = sData.feeRuleExpr.split("@#@");
						var feeCaltMeth = sData.feeCaltMeth;
						if("1"==feeCaltMeth ||"2"==feeCaltMeth){//按笔数
							var feeRuleRecColArray = feeRuleRecArray[0].split("|");
							setI('amtPerNum',feeRuleRecColArray[2]);
							
						}else if("3"==feeCaltMeth ||"4"==feeCaltMeth){//金额比例
							var feeRuleRecColArray = feeRuleRecArray[0].split("|");
							setI('percentAmt',feeRuleRecColArray[3]);
						}else if("5"==feeCaltMeth ||"6"==feeCaltMeth){//笔数档次
							for(i=0;i<feeRuleRecArray.length;i++){
								var feeRuleRecColArray = feeRuleRecArray[i].split("|");

								setI('strNum'+(i+1),feeRuleRecColArray[0]);
								setI('endNum'+(i+1),feeRuleRecColArray[1]);
								setI('amtPerNum'+(i+1),feeRuleRecColArray[2]);
							}
						}else if("7"==feeCaltMeth ||"8"==feeCaltMeth){//金额档次
							for(i=0;i<feeRuleRecArray.length;i++){
								var feeRuleRecColArray = feeRuleRecArray[i].split("|");

								setI('strAmt'+(i+1),feeRuleRecColArray[0]);
								setI('endAmt'+(i+1),feeRuleRecColArray[1]);
								setI('percentAmt'+(i+1),feeRuleRecColArray[3]);
							}
						}
				}
			}
			//触发事件
			$('#clrMeth').change();
			$('#intrmAcctFlg').change();
			$('#feeCaltMeth').change();
		}
	},"json")	
}

//tale修改返回数据
function reviceRow(obj){
	var data= reviceRowData(obj);
	// 点击修改回显值
	setI("mertNo",data.mertNo);
	setI("mertName",data.mertName);
	setI("upMertNo",data.upMertNo);

	setI("feeTfOutAcct",data.feeTfOutAcct);
	setI("feeTfOutAcctName",data.feeTfOutAcctName);
	setI("feeTfInAcct",data.feeTfInAcct);
	setI("feeTfInAcctName",data.feeTfInAcctName);
	setI("feeSumCode",data.feeSumCode);
	setI("feeSumDesc",data.feeSumDesc);
}

/* 取值，table新增一行 */
function tableActionRow(tableId,ifAddRevice){
	if(portion("acctDiv") && tableActionRowCheck()){
		var Data={
				mertNo:getI("mertNo"),
				mertName:getI("mertName"),
				upMertNo:getI("upMertNo"),
				feeTfOutAcct:getI("feeTfOutAcct"),
				feeTfOutAcctName:getI("feeTfOutAcctName"),
				feeTfInAcct:getI("feeTfInAcct"),
				feeTfInAcctName:getI("feeTfInAcctName"),
				feeSumCode:getI("feeSumCode"),
				feeSumDesc:getI("feeSumDesc"),
				action : "<a onclick='reviceRow(this)'>修改</a> <a onclick='deleteRow(this)'>删除</a>"
		}
		var rowArr = $('#'+tableId).bootstrapTable('getData');
		if(ifAddRevice=="add"){
			/*table增加行*/
			var clrDimTp = getS("clrDimTp");
			if (clrDimTp == "01") {//单商户清算
				if (rowArr.length >= 1) {//清算账户列表不为空
					showTip("清算维度类型为单商户清算的情况下，只能存在一个清算账号参数","erroe");
					return 0;
				}
			}
			for (var i=0;i<rowArr.length;i++) {
				var rowData = rowArr[i];
				if (rowData.mertNo == Data.mertNo) {
					showTip("已存在相同商户参数["+rowData.mertNo+"]", "error");
					return 0;
				}
			}
			tableAddRow(tableId,Data);
			clearTableInput();
		}else if(ifAddRevice=="revice"){
			/*修改行*/
			tableReviceRow(tableId,Data);
			clearTableInput();
		}
	}
}

//table操作前校验
function tableActionRowCheck(){
	if (!(getI("mertNo") && getI("feeTfOutAcct") && getI("feeTfInAcct"))) {
		showTip("商户号、手续费转出账号、手续费转入账号不能为空", "error");
		return 0;
	}
	return 1;
}

function clearTableInput(){
	var clrDimTp = $("#clrDimTp").val();
	if (clrDimTp == "11") {//多商户清算
		setI("mertNo","");
	}
	setI("mertName","");
	setI("upMertNo","");
	setI("feeTfOutAcct","");
	setI("feeTfOutAcctName","");
	setI("feeTfInAcct","");
	setI("feeTfInAcctName","");
	setI("feeSumCode","");
	setI("feeSumDesc","");
}

