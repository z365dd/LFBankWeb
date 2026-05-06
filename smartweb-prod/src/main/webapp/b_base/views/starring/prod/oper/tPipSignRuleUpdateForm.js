
$(document).ready(function(){
	//初始化操作
	initPageCtrlData();
	
	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(proof()){
			submit();
		};
	});
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
	/*table控件处理*/
	$("#addTabBtn").on("click" ,function(){
		tableActionRow('limTable',"add");
	});
	$("#setTabBtn").on("click" ,function(){
		tableActionRow('limTable',"revice");
	});
	/*金额格式化处理*/
	$("#limAmt").on("change" ,function(){
		formatAmt(this);
	});
	
	/*签约标志控制*/
	$("#signFlg").on("change" ,function(){
		var signFlg=$("#signFlg").val();
		if("N"==signFlg){
			$("#signDiv").hide();
		}else{
			$("#signDiv").show();
		}
	});
	/*账户信息校验控制*/
	$("#vrfyAcctInfoFlg").on("change" ,function(){
		var vrfyAcctInfoFlg=$("#vrfyAcctInfoFlg").val();
		if("N"==vrfyAcctInfoFlg){
			$("#signAcctCtrlDiv").hide();
		}else{
			$("#signAcctCtrlDiv").show();
		}
	});
	/*签约类型控制*/
	$("input[name='signTp']").on("change" ,function(){
		var signTp=$('input[name="signTp"]:checked').val();
		if("01"==signTp){//开通签约
			$("#limParaDiv").hide();
		}else{
			$("#limParaDiv").show();
		}
	});
	/*限额类型控制*/
	$("#limTp").on("change" ,function(){
		var limTp=$("#limTp").val();
		if("00"==limTp){
			$("#limNum").attr("readonly","readonly");
			setI("limNum","1");
		} else {
			$("#limNum").removeAttr("readonly");
			resetInput("limNum");
		}
	});
	
	/*账户流水通知控制*/
	$("#acctJrnlNoteFlg").on("change" ,function(){
		var acctJrnlNoteFlg=$("#acctJrnlNoteFlg").val();
		if("N"==acctJrnlNoteFlg){
			$("#acctJrnlNotCtrlDiv").hide();
		}else{
			$("#acctJrnlNotCtrlDiv").show();
		}
	});
	
});

function initPageCtrlData(){
	getDetail();
	setSelect2("chnlNo", "/prod/oper/busiDemo/getChnl", "chnlNo", "chnlName", {}, "nulls", false, false);
	setSelect2("compNo", "/prod/comp/tPipComp/list", "compNo", "compName", {pageSize:0,start:0}, "nulls", false, false);
	$("#compNo").on("change" ,function(){
		setSelect2("modlSvcCode", "/prod/oper/tPipSvc/list", "svcCode", "svcName", {compNo:getS('compNo'),pageSize:0,start:0}, "nulls", false, false);
	}); 
	/* table初始化 */
	tableClick("limTable");   

}

function submit(){
	
//	confirmx('是否更新签约规则参数', function(){
//	});
	save();
}

/**
 * 保存函数--保存签约规则参数修改
 * @returns
 */
function save(){
	var formData = $("#tPipSignRuleForm").serializeObject();

	
	formData.ruleName=$("#ruleDesc").val();
	//转换下拉控件值格式
	if ($("#acctStatList").val() != null) {
		formData.acctStatList = $("#acctStatList").val().join("|");
	}
	//转换表格控件值格式
	var limParaList = $('#limTable').bootstrapTable('getData');
	formData.limParaList = JSON.stringify(limParaList);

	
	
	/*向后台发送参数*/
	$.post(ctx + "/prod/oper/tPipSignRule/update", formData,
		function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			    var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
				return '0';
			}else if(data.msg_type == "success"){
				var successMsg = "修改交易["+data.message+"]"; 
				showContent(successMsg,"success");
				console.info("修改交易成功");
				cancle();
			}
	}, "json");
	
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出ruleId*/
	var HID_ruleId = $.session.get('HID_ruleId');
	/*从session中移除ruleId*/
	$.session.remove('HID_ruleId');
	
	console.info('update tPipSignRule info......');
	$.post(ctx + "/prod/oper/tPipSignRule/get", {ruleId:HID_ruleId}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				if (data.dataSetResult[i].dataSetName == "TPipSignRuleDO") {
					for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
						var jsonObj = data.dataSetResult[i].data[j];
						console.info(jsonObj);
						$('#ruleId').val(jsonObj.ruleId);	
						$('#ruleDesc').val(jsonObj.ruleDesc);
						
						$('#signFlg').multiselect("select", [jsonObj.signFlg]).multiselect('rebuild');
						$('#signFlg').change();
						$("input[name='signTp'][value='"+jsonObj.signTp+"']").attr("checked",true);//单选框
						$("input[name='signTp']").change();
						$("input[name='noteTp'][value='"+jsonObj.noteTp+"']").attr("checked",true);//单选框
						
						if(jsonObj.acctStatList!=null ||jsonObj.acctStatList.length>0 ){
							var acctStatList = jsonObj.acctStatList.split("|");
							$("#acctStatList").multiselect("select",acctStatList).multiselect('rebuild');
						}
						
						$('#vrfyAcctInfoFlg').multiselect("select", [jsonObj.vrfyAcctInfoFlg]).multiselect('rebuild');
						$('#vrfyAcctInfoFlg').change();
						$('#vrfyAcctNameFlg').multiselect("select", [jsonObj.vrfyAcctNameFlg]).multiselect('rebuild');
						$('#vrfyCertFlg').multiselect("select", [jsonObj.vrfyCertFlg]).multiselect('rebuild');
						$('#vrfyPhoneFlg').multiselect("select", [jsonObj.vrfyPhoneFlg]).multiselect('rebuild');
						$('#vrfyOpenAcctBrchFlg').multiselect("select", [jsonObj.vrfyOpenAcctBrchFlg]).multiselect('rebuild');
						$('#vrfyModBrchFlg').multiselect("select", [jsonObj.vrfyModBrchFlg]).multiselect('rebuild');
						$('#vrfyCanclBrchFlg').multiselect("select", [jsonObj.vrfyCanclBrchFlg]).multiselect('rebuild');
						$('#custDefLimFlg').multiselect("select", [jsonObj.custDefLimFlg]).multiselect('rebuild');
						$('#acctJrnlNoteFlg').multiselect("select", [jsonObj.acctJrnlNoteFlg]).multiselect('rebuild');
						$('#acctJrnlNoteFlg').change();
						$('#compNo').multiselect("select", [jsonObj.compNo]).multiselect('rebuild');
						$('#compNo').change();
						$('#modlSvcCode').multiselect("select", [jsonObj.modlSvcCode]).multiselect('rebuild');
					}
					
				}

                if (data.dataSetResult[i].dataSetName == "TPipSignRuleLimDOList") {
                    var list = data.dataSetResult[i].data;
                    if (undefined != list) {
                        for(var j=0;j<list.length;j++){
                        	/*渠道和限额类型中文反显*/
                        	changeZh(list[j]);
                        	list[j].chnlNoFlg = $("#chnlNo").find("option[value= "+list[j].chnlNo+"]").text();
                            list[j].ACTION = "<a href='javascript:;' onclick='reviceRow(this)'>修改</a> <a href='javascript:;' onclick='deleteRow(this)'>删除</a>";
                        }
                    }
                    $('#limTable').bootstrapTable('load', list);
                }
			}

		}
	},
    "json");
}
/* 取值，table新增一行 */
function tableActionRow(tableId,ifAddRevice){
	var rowArr = $('#limTable').bootstrapTable('getData');
	var chnlNo = $('#chnlNo option:selected').val();
	var limTp = $('#limTp').val();
	var limTpStr = getST("limTp");
	var chnlNoFlg = getST("chnlNo");
	var limNum = $('#limNum').val();
	var limAmt = $('#limAmt').val();
	if(portion("limDiv") && tableActionRowCheck()){
		var Data={
				chnlNo : chnlNo,
				chnlNoFlg : chnlNoFlg,
				limTp : limTp,
				limTpStr : limTpStr,
				limNum : limNum,
				limAmt : limAmt,
				ACTION:"<a href='javascript:;' onclick='reviceRow(this)'>修改</a> <a href='javascript:;' onclick='deleteRow(this)'>删除</a>",
		}
		if(ifAddRevice=="add"){
			/*table增加行*/
			for (var i=0;i<rowArr.length;i++) {
				var data = rowArr[i];
				if (data.chnlNo == chnlNo && data.limTp == limTp) {
					showTip("已存在相同渠道限额参数["+chnlNo+","+limTp+"]", "error");
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
/*限额类型中文转换*/
function changeZh(data) {
	if (data.limTp == "00") {
		data.limTpStr = "单笔";
	} else if (data.limTp == "01") {
		data.limTpStr = "日";
	} else if (data.limTp == "02") {
		data.limTpStr = "旬";
	} else if (data.limTp == "03") {
		data.limTpStr = "月";
	} else if (data.limTp == "04") {
		data.limTpStr = "季";
	} else if (data.limTp == "05") {
		data.limTpStr = "年";
	}
}
/*清空表格输入字段*/
function clearTableInput(){
	$("#limTp").multiselect("select", "").multiselect('rebuild');
	$("#limNum").val("");
	$("#limAmt").val("");
}

//tale修改返回数据
function reviceRow(obj){
	var data= reviceRowData(obj);
	// 点击修改回显值
	$("#chnlNo").multiselect("select", data.chnlNo).multiselect('rebuild');
	$("#limTp").multiselect("select", data.limTp).multiselect('rebuild');
	$("#limNum").val(data.limNum);
	$("#limAmt").val(data.limAmt);
}
//table操作前校验
function tableActionRowCheck(){
	var chnlNo = $('#chnlNo option:selected').val();
	var limTp = $('#limTp').val();
	var limNum = $('#limNum').val();
	var limAmt = $('#limAmt').val();
	if ("" == chnlNo ||
		"" == limTp||
		"" == limNum||
		"" == limAmt) {
		showTip("限额参数 均不能为空", "error");
		return 0;
	}
	if(isInputInt(limNum)){
		showTip("最大限制笔数 非有效正整数", "error");
		return 0;
	}
	if(isInputAmt(limAmt)){
		showTip("最大限制额度 非有效金额", "error");
		return 0;
	}
	return 1;
}
//判断控件值是否为正整数
function isInputInt(str){
	var g = /^[1-9]*[1-9][0-9]*$/;
    return !g.test(str);
}
//判断控件值是否为金额
function isInputAmt(str){
	var g = /^((([1-9]([,\d]*))|\d)(\.\d{1,2})?)$/;
    return !g.test(str);
}
//格式化输入值为金额
function formatAmt(element,digits) {
    var money = element.value;
    digits = digits > 0 && digits <= 20 ? digits : 2;
    money = parseFloat((money + "").replace(/[^\d\.-]/g, "")).toFixed(digits) + "";
    var l = money.split(".")[0].split("").reverse(),
        r = money.split(".")[1];
    t = "";
    for (i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 === 0 && (i + 1) !== l.length ? "," : "");
    }
    element.value = t.split("").reverse().join("") + "." + r;
}
