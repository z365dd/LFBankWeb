console.log("chkRuleForm.js");
$(function(){
	$("[data-toggle='popover']").popover();
	
	$("#submit_btn").click(function(){
		if(proof()){
			submit();
		}
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
	
	/*div块控制*/
	divCtrl();
})

function divCtrl(){
	$("#chkTp").change(function(){
		var chkTp=$("#chkTp").val();
		if("0"==chkTp){//不对账
			$("#chkCtrlDiv").hide();
			$("#BEAN").attr("check-empty",false);
			$("#chkNo").attr("check-empty",false);
			$("#chkRuleId").attr("check-empty",false);
		}else{
			$("#chkCtrlDiv").show();
			$("#BEAN").attr("check-empty",true);
			$("#chkNo").attr("check-empty",true);
			$("#chkRuleId").attr("check-empty",true);
			if("1"==chkTp){//两方对账
				$("#twoChkDiv").show();
				$("#threeChkDiv").hide();
				var temp = getS("chkSndGrpFlg");
				//对账发起方式
				var optgroups = [
					{label:'自动对账',value:'1'},
					{label:'柜面发起对账',value:'2'}
				]
				$('#chkSndGrpFlg').multiselect('dataprovider', optgroups);
				setS("chkSndGrpFlg",temp);
			}else if("2"==chkTp){//三方对账
				$("#threeChkDiv").show();
				$("#twoChkDiv").hide();
				var temp = getS("chkSndGrpFlg");
				//对账发起方式
				var optgroups = [
					{label:'自动对账',value:'1'},
					{label:'柜面发起对账',value:'2'},
					{label:'第三方发起对账',value:'3'}
				]
				$('#chkSndGrpFlg').multiselect('dataprovider', optgroups);
				setS("chkSndGrpFlg",temp);
			}
		}
	})
	$("#chkSndGrpFlg").change(function(){
	})
}

//提交事件前检查
function preSubmit(){
	var isTimeTrue = true;
	$("#chkSndGrpFlg option:selected").each(function(){
		var selectedVal=$(this).val();
		if("1"==selectedVal){
			var startTime = $("#strTime").val();
			var endTime = $("#endTime").val();
			if(""==startTime || ""==endTime){
				showTip("自动对账时：开始对账时间 或 终止对账时间 不能为空", "error");
				isTimeTrue = false;
				return ;
			}else{
				startTime = getDateValue("strTime");
				endTime = getDateValue("endTime");
				if(startTime > endTime){
					showTip("自动对账时：开始对账时间要小于终止对账时间", "error");
					isTimeTrue = false;
					return ;
				}
			}
		}
	});
	return isTimeTrue;
}
function submit(){
	if(!preSubmit()){
		return false;
	}
	$.post(ctx+"/prod/oper/chkRule/update",getData(),function(data){
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

function getData(){
	var Data ={
			ruleId:getI("ruleId"),
			ruleName:getI("ruleName"),
			chkTp:getS("chkTp"),
			chkSndGrpFlg:getS("chkSndGrpFlg"),
			chkCycTp:getS("chkCycTp"),
			chkCyc:getS("chkCyc"),
			strTime:getDateValue("strTime"),
			endTime:getDateValue("endTime"),
			chkSwitchFlg:getS("chkSwitchFlg"),
			errSwitchFlg:getS("errSwitchFlg"),
			chkRsltPushFlg:getS("chkRsltPushFlg"),
			chkRsltFileName:getI("chkRsltFileName"),
			chkFileDownloadFlg:getS("chkFileDownloadFlg"),
			othChkFileName:getI("othChkFileName"),
			bean:getI("bean"),
			chkNo:getI("chkNo"),
			chkRuleId:getI("chkRuleId"),
			upRuleId:getI("upRuleId")
	} 
	return Data ;
}

function setData(RULE_ID){
	$.post(ctx+"/prod/oper/chkRule/getDetail",{RULE_ID:RULE_ID},function(data){
		if (data.returnCode !== undefined
				&& "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
		} else {
			console.log(data.message);
			var sData = data.dataSetResult[0].data[0];
				setI("ruleId",sData.ruleId);
				setI("ruleName",sData.ruleName);
				setS("chkTp",sData.chkTp);
				setS("chkSndGrpFlg",sData.chkSndGrpFlg);
				setS("chkCycTp",sData.chkCycTp);
				setS("chkCyc",sData.chkCyc);
				setDateValue("strTime",sData.strTime);
				setDateValue("endTime",sData.endTime);
				
				setS("chkSwitchFlg",sData.chkSwitchFlg);
				setS("errSwitchFlg",sData.errSwitchFlg);
				setS("chkRsltPushFlg",sData.chkRsltPushFlg);
				setI("chkRsltFileName",sData.chkRsltFileName);
				setS("chkFileDownloadFlg",sData.chkFileDownloadFlg);
				setI("othChkFileName",sData.othChkFileName);
				setI("bean",sData.bean);
				setI("chkNo",sData.chkNo);
				setI("chkRuleId",sData.chkRuleId);
				setI("upRuleId",sData.upRuleId);
				
				$("#chkTp").change();
		}
	},"json")	
}


function getMultiple(id){
	var arr = [];
	var value = "";
	$("#"+id+" option:selected").each(function(){
		arr.push($(this).val());
	});
	value = arr.join("|");
	return value;
}

function setMultiple(id,str){
	if(str==undefined){
		return;
	}
	var arr = [];
	arr = str.split("|");
	$("#"+id).multiselect('select',arr);
}