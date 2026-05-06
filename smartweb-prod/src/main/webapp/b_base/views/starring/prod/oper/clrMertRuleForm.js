console.log("clrMertRuleForm.js");
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
		var clrMeth=$("#clrMeth").val();
		if("00"==clrMeth){//不清算
			$("#clrCtrlDiv").hide();
		}else{
			$("#clrCtrlDiv").show();
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
//	$("#intrmAcctFlg").change(function(){
//		var intrmAcctFlg=$("#intrmAcctFlg").val();
//		var clrMeth=$("#clrMeth").val();
//		if("11"==clrMeth){//清算
//			if("Y"==intrmAcctFlg){
//				$("#intrmAcct").attr("check-empty",true);
//				$("#intrmAcctName").attr("check-empty",true);
//				$("#postingSumCode").attr("check-empty",true);
//			}else{
//				$("#intrmAcct").attr("check-empty",false);
//				$("#intrmAcctName").attr("check-empty",false);
//				$("#postingSumCode").attr("check-empty",false);
//			}
//		}
//	})

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
	
	$.post(ctx+"/prod/oper/clrMertRule/update",getFormData(),function(data){
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
	var formData = $("#tpipClrMertRuleForm").serializeObject();
	formData.clrSndGrpFlg=getS("clrSndGrpFlg");
	//转换表格控件值格式
	var acctList = $('#table').bootstrapTable('getData');
	formData.acctList = JSON.stringify(acctList);
	
	return formData ;
}

function setData(RULE_ID){
	$.post(ctx+"/prod/oper/clrMertRule/getDetail",{RULE_ID:RULE_ID},function(data){
		if (data.returnCode !== undefined
				&& "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
		} else {
			console.log(data.message);
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				if (data.dataSetResult[i].dataSetName == "tpipClrMertRuleDOResDs") {
					var sData = data.dataSetResult[0].data[0];
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
				}
			}
			//触发事件
			$('#clrMeth').change();
			$('#intrmAcctFlg').change();
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
	setI("entrAcct",data.entrAcct);
	setI("entrAcctName",data.entrAcctName);
	setS("entrAcctBankFlg",data.entrAcctBankFlg);
	setI("entrAcctBank",data.entrAcctBank);
	setI('entrAcctBankName',data.entrAcctBankName);
	setS("intrmAcctFlg",data.intrmAcctFlg);
	setI("intrmAcct",data.intrmAcct);
	setI("intrmAcctName",data.intrmAcctName);
	setI("sumCode",data.sumCode);
	setI("sumDesc",data.sumDesc);
	setI("postingSumCode",data.postingSumCode);
	setI("postingSumDesc",data.postingSumDesc);
}

/* 取值，table新增一行 */
function tableActionRow(tableId,ifAddRevice){
	if(portion("acctDiv") && tableActionRowCheck()){
		var Data={
				mertNo:getI("mertNo"),
				mertName:getI("mertName"),
				upMertNo:getI("upMertNo"),
				entrAcct:getI("entrAcct"),
				entrAcctName:getI("entrAcctName"),
				entrAcctBankFlg:getS("entrAcctBankFlg"),
				entrAcctBank:getI("entrAcctBank"),
				entrAcctBankName:getI('entrAcctBankName'),
				intrmAcctFlg:getS("intrmAcctFlg"),
				intrmAcct:getI("intrmAcct"),
				intrmAcctName:getI("intrmAcctName"),
				sumCode:getI("sumCode"),
				sumDesc:getI("sumDesc"),
				postingSumCode:getI("postingSumCode"),
				postingSumDesc:getI("postingSumDesc"),
				action : "<a onclick='reviceRow(this)'>修改</a> <a onclick='deleteRow(this)'>删除</a>"
		}
		var rowArr = $('#'+tableId).bootstrapTable('getData');
		if(ifAddRevice=="add"){
			/*table增加行*/
			var clrDimTp = getS("clrDimTp");
			if (clrDimTp == "01") {//单商户清算
				if (rowArr.length >= 1) {//清算账户列表不为空
					showTip("清算维度类型为单商户清算的情况下，只能存在一个清算账号参数","erroe");
					return false;
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
	if (!(getI("mertNo") && getI("entrAcct") && getS("intrmAcctFlg"))) {
		showTip("商户号、单位账号、过渡账户标志不能为空", "error");
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
	setI("entrAcct","");
	setI("entrAcctName","");
	setS("entrAcctBankFlg","");
	setI("entrAcctBank","");
	setI('entrAcctBankName',"");
	setS("intrmAcctFlg","");
	setI("intrmAcct","");
	setI("intrmAcctName","");
	setI("sumCode","");
	setI("sumDesc","");
	setI("postingSumCode","");
	setI("postingSumDesc","");
}
