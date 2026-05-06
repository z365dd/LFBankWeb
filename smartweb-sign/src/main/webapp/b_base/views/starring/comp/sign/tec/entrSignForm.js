console.log('entrSignForm.js');

var $A_M_D  = parent.$A_M_D;  /*A-新增,M-修改,D-详细,C-解约*/
var $allData = null;
var $AddData = null;
var $ModData = null;
var $DetailData = null;
var $DelData = null;
var $CtrlData = null;
var $ChnlData = null;
/*修改table中数据需要传入 行号 、列号作参数*/
var $RowNo = -1;
var $ColNo = -1;

/*单位签约 新增页面js*/

$(function(){
	
	$("#busiMsg").hide();
	$("#upBtn").attr('disabled',"true");
	
	/*A:异步加载下拉框   M/D:同步加载下拉框*/
	getCompNo1("COMP_NO");
	getLegaNo1("LEGA_NO");
	/*if($A_M_D == "A"){
		getLegaNo("LEGA_NO");
	}else{
	}*/
	
	/*法人号改变重新加载单位编号*/
	$('#LEGA_NO').change(function(){
		$("#ENTR_NO").val("");
		entrInfoQry();
	});
	
	$('#ENTR_NO').click(function(){
		var legaNo = getS("LEGA_NO");
		if(legaNo == ""){
			var $url = "/comp/sign/pub/entrData?stat=11";  //stat=11随便赋值的(只有不为""、1、2、3即可)
		}else{
			var $url = "/comp/sign/pub/entrData?legaNo="+legaNo+"&stat=1";
		}
		$("#ENTR_NO").attr("search_url",$url);
		entrClick("ENTR_NO","ENTR_NAME",entrInfoQry);
	});
	
	/*组件号改变重新加载业务编号*/
	$("#COMP_NO").change(function(){
		$("#BUSI_NO").val("");
		resBusiNo();
		resetS("SUB_BUSI_NO");
	});
	
	$("#BUSI_NO").click(function(){
		var $compNo = getS("COMP_NO");
		var $url = "/comp/sign/pub/busiData?state=1";
		//筛选签约状态为Y的业务编号
		if($compNo != ""){
			$url += ("&compNo="+$compNo+"&signFlg=Y");
		}else{
			$url = "/comp/sign/pub/busiData?state=111";
		}
		
		$("#BUSI_NO").attr("search_url", $url);
		busiClick("BUSI_NO", "BUSI_NAME", null, resBusiNo);
	});
	
	/*子业务编号改变则重新加载 检查项*/
	$("#SUB_BUSI_NO").change(function(){
		if(getS("SUB_BUSI_NO")!=""){
			entrItemQry();
			signChnlQry();
		}
	});

	
	disabledS("CERT_TP");
	$("#FEE_TP").change(function(){
		chkFee_Acct();
	});
	$("#INTRM_ACCT_FLG").change(function(){
		chkFee_Acct();
	});
	
	/*上一页*/
	$("#upBtn").click(function(){
		$("#mainForm").show();
		$("#busiMsg").hide();
		$("#upBtn").attr('disabled',"true");
		$("#nextBtn").removeAttr('disabled');
		ifrChildAut("panel2");
	});
	
	/*下一页*/
	$("#nextBtn").click(function(){
		if(portion("entrDiv")){
			$("#mainForm").hide();
			$("#busiMsg").show();
			$("#nextBtn").attr('disabled',"true");
			$("#upBtn").removeAttr('disabled');
			ifrChildAut("panel2");
		}
		if($A_M_D == "A"){
			//生成检验数据
			createChk();
		}
	});
	
	
	/*新增按钮click触发事件*/
//	$('#addBtn').on('click',addRowData);
	$('#addBtn').click(function(){
		if(portion("busiForm")){
			addRowData();
		}else{
			parent.goTop();
		}
	});
	/*修改按钮click触发事件*/
	$('#modBtn').on('click',modRowData);
	/*$("#modBtn").click(function(){
		if(portion("busiForm")){
			modRowData();
		}
	});*/
	
	/*提交按钮*/
	if($A_M_D == "C"){
		$('#saveBtn').click(function(){
			entrCan(getI("ENTR_NO"),"","");
		});
	}else{
		$('#saveBtn').on('click',commitFun);
	}
	/*返回按钮*/
	$('#closeBtn').on('click',closeFun);
	
});

/*主页点击 详情/修改 数据回显(mainForm 和  table)*/
function setVal(tp,Data){
	var allData = JSON.parse(decodeURIComponent(Data.replace(/\+/g, '%20')));
	if(tp == "D"){
		$DetailData = allData;
		$("#mainForm,#busiForm").find("select").multiselect("disable");
		$("#mainForm,#busiForm").find("input").prop("disabled", true);
		$("#addBtn,#modBtn,#saveBtn,#ctrlBtn").hide();
	}else if(tp == "M"){
		$ModData = allData;
		$("#mainForm").find("select").multiselect("disable");
		$("#mainForm").find("input").prop("disabled", true);
		$("#ctrlBtn").hide();
		$("#addBtn").attr('disabled',true);
		/*业务信息  组件号  业务编号不可修改*/
		$("#COMP_NO").multiselect("disable");
		$("#BUSI_NO").prop('disabled',true);
	}else if(tp == "C"){
		$DelData = allData;
		$("#mainForm,#busiForm").find("select").multiselect("disable");
		$("#mainForm,#busiForm").find("input").prop("disabled", true);
		$("#addBtn,#modBtn,#ctrlBtn,#saveBtn").hide();
	}
	
	//公共数据回显
	setMainForm(allData);
	
	//table数据回显
	setTable(allData);
}

/*查询单位信息*/
function entrInfoQry(){
	var entrNo = getI("ENTR_NO");
	if(entrNo == ""){
//		resetForm("mainForm", false, false);
		return;
	}
	$("input[id=ENTR_NO]").parent().siblings('span.warnBlock').remove();
	$.ajax({
		url: ctx + "/comp/sign/tec/signParaEntr/qry",
		type:"POST",
		dataType:"json",
		data:{
			LEGA_NO:getS("LEGA_NO"),
			ENTR_NO:entrNo,
			start:1,
			limit:10
		}, 
//		async:true,
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showTip(errMsg, "error");
			} else {
				console.log(data.message);
				showTip("查询单位信息成功", "success");
				var mainData = data.dataSetResult[0].data[0];
				setMainForm(mainData);
			}
		}
	});
}

/*根据业务编号查询子业务编号(只返回可被签约的)*/
function resBusiNo(){
	var busiNo = $("#BUSI_NO").val();
	if(busiNo == ""){
		$("select[name=SUB_BUSI_NO]").empty();
	}else{
		$("input[id=BUSI_NO]").parent().siblings('span.warnBlock').remove();
		var Data = {
				BUSI_NO:busiNo,
				signFlg:"Y"
			}
			setSelect2("SUB_BUSI_NO","/comp/sign/tec/signSubBusi/qrySubBusiNo","SUB_BUSI_NO","SUB_BUSI_NAME",Data,busiNo,"",false);
	}
}

/*查询动态检验数据*/
function createChk(){
	/*entrItemQry();
	signChnlQry();*/
	ifrChildAut("panel2");
}

/*查询单位检查项信息*/
function entrItemQry(){
	$.ajax({
		url: ctx + "/comp/sign/pub/entrItemQry",
		type:"POST",
		dataType:"json",
		data:{
			BUSI_NO:"0",
			SUB_BUSI_NO:"0",
			ENTR_NO:"0"
		}, 
		async:true,
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showTip(errMsg, "error");
			} else {
				console.log(data.message);
				/*showTip("查询单位检查项信息成功", "success");*/
				madeCtrlHtml(data.dataSetResult[0].data);
			}
		}
	});
}

/*查询签约渠道信息*/
function signChnlQry(){
	$.ajax({
		url: ctx + "/comp/sign/pub/signChnlQry",
		type:"POST",
		dataType:"json",
		data:{
			BUSI_NO:"0",
			SUB_BUSI_NO:"0",
			ENTR_NO:"0"
		}, 
		async:true,
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showTip(errMsg, "error");
			} else {
				console.log(data.message);
				/*showTip("查询签约渠道信息成功", "success");*/
				madeChnlHtml(data.dataSetResult[0].data);
			}
		}
	});
}

//动态生成校验html代码
function madeCtrlHtml(CTRL_LIST){
	/*先清空之前生成的动态html元素(如果有生成的话)*/
	$('#ctrlMsg').empty();
	if(CTRL_LIST == undefined || CTRL_LIST == null || CTRL_LIST == "[]"){
		return;
	}
	$CtrlData = CTRL_LIST;
	var $html="";
	for(var i=0;i<CTRL_LIST.length;i++){
		var keyName = CTRL_LIST[i].KEY_NAME;
		var key = CTRL_LIST[i].KEY;
		var Kv = CTRL_LIST[i].KV;
		/*组装select下的option*/
		var KeyDesc = CTRL_LIST[i].KV_DESC;
		var KeyArr = KeyDesc.split('|');
		var $opHtml = "";
		for(var j=0;j<KeyArr.length-1;j++){
			var opVal = KeyArr[j].split('-')[0];
			var opText = KeyArr[j].split('-')[1];
			if(Kv == opVal){
				$opHtml += "<option value='"+opVal+"' selected='selected'>"+opText+"</option>";
			}else{
				$opHtml += "<option value='"+opVal+"'>"+opText+"</option>";
			}
		}
		$html += "<div ravo='rainbow_fx' class='form-group'>";
		$html += "<label for='inputEmail3' class='col-sm-5 control-label'>"+keyName+"</label>";
		$html += "<div class='col-sm-3'>";
		$html += "<select data-role='multiselect' class='' id='"+key+"' name='"+key+"'  data-max-height='300'> "+ $opHtml +"</select>";
		$html += "</div>";
		$html += "</div>";
	}
	$('#ctrlMsg').append($html);
	/**/
	$('#ctrlMsg select').each(function(){
		$(this).multiselect('rebuild').multiselect('refresh');
	});
	ifrChildAut("panel2");
}
//动态生成渠道html代码
function madeChnlHtml(CHNL_LIST){
	/*先清空之前生成的动态html元素(如果有生成的话)*/
	$('#chnlMsg').empty();
	if(CHNL_LIST == undefined || CHNL_LIST == null || CHNL_LIST =="[]"){
		return;
	}
	/*外部HTML*/
	var $html ="<div ravo='rainbow_fx_checkbox rainbow_fx' class='form-group'>";
		$html+="<label for='inputEmail3' class='col-sm-3 control-label'>";
		$html+="渠道：</label><div class='col-sm-4'>";	
		
	/*内部多选框HTML*/							
	for(var i=0;i<CHNL_LIST.length;i++){
		var CHNL_NO = CHNL_LIST[i].CHNL_NO;
		var CHNL_NAME = CHNL_LIST[i].CHNL_NAME;
		var FLG = CHNL_LIST[i].FLG;
		var SIGN_FLG = CHNL_LIST[i].SIGN_FLG;
		$html+="<div class='checkbox'>";
		$html+="<label style='visibility:visible'>";
		if(SIGN_FLG == "0"){
			$html+="<input type='checkbox' value='"+CHNL_NO+"' name='SIGN_CHNL' checked='checked'>"+CHNL_NAME;
		}else{
			$html+="<input type='checkbox' value='"+CHNL_NO+"' name='SIGN_CHNL'>"+CHNL_NAME;
		}
		$html+="</label></div>";
	}
	$('#chnlMsg').append($html);
	ifrChildAut("panel2");
}

//手续费标志 和 是否使用过渡户  校验
function chkFee_Acct(){
	if(getST("FEE_TP") == "收取"){
		$("select[name='FEE_CODE']").multiselect('enable');
	}else{
		setS("FEE_CODE","");
		$("select[name='FEE_CODE']").multiselect('disable');
	}
	
	if(getS("INTRM_ACCT_FLG") == "Y"){
		$("#INTRM_ACCT,#INTRM_ACCT_NAME").removeAttr('disabled');
	}else{
		$("#INTRM_ACCT,#INTRM_ACCT_NAME").parent().siblings('span.warnBlock').remove();
		$("#INTRM_ACCT,#INTRM_ACCT_NAME").val("");
		$("#INTRM_ACCT,#INTRM_ACCT_NAME").prop("disabled", true);
	}
}

//设置table数据，用于新增、修改时重新加载数据
function setTable(Data){
	var TableData = [];
	var BUSI_LIST = Data.BUSI_LIST;
	var ENTR_NO = Data.ENTR_NO;
	var tp = "Detail";
	for(var i=0;i<BUSI_LIST.length;i++){
		var SUB_BUSI_LIST = BUSI_LIST[i].SUB_BUSI_LIST;
		for(var j=0;j<SUB_BUSI_LIST.length;j++){
			var actionStr = "";
			if($A_M_D == "A"){
				actionStr = "<a href=\"JavaScript:void(0);\" onClick=\"getRowData("+i+ "," +j+")\">修改</a>&nbsp&nbsp"
			      +"<a href=\"JavaScript:void(0);\" onClick=\"delRowData("+i+ "," +j+")\">删除</a>";
			}else if($A_M_D == "M"){
				if("1" == BUSI_LIST[i].SIGN_STAT){
					actionStr = "<a href=\"JavaScript:void(0);\" onClick=\"getRowData('"+i+"','" +j+"','"+tp+"')\">详情</a>";
				}else{
					actionStr = "<a href=\"JavaScript:void(0);\" onClick=\"getRowData("+i+ "," +j+")\">修改</a>";
				}
			}else if($A_M_D == "D"){
				actionStr = "<a href=\"JavaScript:void(0);\" onClick=\"getRowData("+i+ "," +j+")\">详情</a>";
			}else if($A_M_D == "C"){
				if("0" == SUB_BUSI_LIST[j].SIGN_STAT){
					actionStr = "<a href=\"JavaScript:void(0);\" onClick=\"getRowData("+i+ "," +j+")\">详情</a>&nbsp&nbsp"
					+"<a href=\"JavaScript:void(0);\" onClick=\"entrCan('"+ENTR_NO+ "','" +BUSI_LIST[i].BUSI_NO+ "','" +SUB_BUSI_LIST[j].SUB_BUSI_NO+"')\">解约</a>";
				}else{
					actionStr = "<a href=\"JavaScript:void(0);\" onClick=\"getRowData('"+i+"','" +j+"','"+tp+"')\">详情</a>";
				}
			}
			var tData ={
					BUSI_NO:BUSI_LIST[i].BUSI_NO,
					BUSI_NAME:BUSI_LIST[i].BUSI_NAME,
					SUB_BUSI_NO:SUB_BUSI_LIST[j].SUB_BUSI_NO,
					SUB_BUSI_NAME:SUB_BUSI_LIST[j].SUB_BUSI_NAME,
					ACTION:actionStr
			}
			TableData.push(tData);
		}
	}
	$("#busiTable").bootstrapTable('load', TableData);
}

/*设置mianForm范围内数据  公共部分*/
function setMainForm(Data){
	if($A_M_D != "A"){
		setS("LEGA_NO",Data.LEGA_NO);
		setI("ENTR_NO", Data.ENTR_NO);
	}
	setI("ENTR_NAME", Data.ENTR_NAME);
	setI("PRT_NAME", Data.PRT_NAME);
	setS("SIGN_STAT", Data.SIGN_STAT);
	setS("CERT_TP", Data.CERT_TP);
	setI("CERT_NO", Data.CERT_NO);
	setI("CTCT_PER_NAME", Data.CTCT_PER_NAME);
	setI("PER_TEL_NO", Data.PER_TEL_NO);
	setI("ENTR_TEL_NO", Data.ENTR_TEL_NO);
	setI("COMM_ADDR", Data.COMM_ADDR);
	setI("EMAIL_ADDR", Data.EMAIL_ADDR);
	setI("POST_ECD", Data.POST_ECD);
	setS("OPEN_STAT", Data.OPEN_STAT);
}

/*设置业务信息div内  input和select 的数据*/
function setBusiForm(BusiData,SubBusiData,tp){
	var backS = [{label:SubBusiData.SUB_BUSI_NAME,value:SubBusiData.SUB_BUSI_NO}];
	if($A_M_D == "D"){
		$("select[name='SUB_BUSI_NO']").multiselect('dataprovider', backS).multiselect('disable');
		$("select[name='SUB_BUSI_NO']").multiselect('disable');
	}else if($A_M_D == "M"){
		$("select[name='SUB_BUSI_NO']").multiselect('dataprovider', backS).multiselect('disable');
		$("#ACCT_TP,#SIGN_STAT1,#SIGN_STAT2,#SUB_BUSI_NO").multiselect('disable');
		$("#ACCT").prop('disabled',true);
		$("#STR_DATE").removeAttr('check-startTime-one');
		$("#END_DATE").removeAttr('check-endTime-one');
	}else if($A_M_D == "C"){
		$("select[name='SUB_BUSI_NO']").multiselect('dataprovider', backS).multiselect('disable');
	}
	
	//在busiForm的table点击详情操作
	if(tp == "Detail"){
		$("#busiForm").find("select").multiselect("disable");
		$("#busiForm").find("input").prop("disabled", true);
//		$("#addBtn,#modBtn").attr('disabled',true);
	}
	
	if($A_M_D == "A" || $A_M_D == "M"){
		//手续费标志 和 是否使用过渡户  校验
		if(SubBusiData.FEE_TP == "Y"){
			$("select[name='FEE_CODE']").multiselect('enable');
		}else{
			$("select[name='FEE_CODE']").multiselect('disable');
		}
		
		if(SubBusiData.INTRM_ACCT_FLG == "Y"){
			$("#INTRM_ACCT,#INTRM_ACCT_NAME").removeAttr('disabled');
		}else{
			$("#INTRM_ACCT,#INTRM_ACCT_NAME").prop("disabled", true);
		}
	}
	
	/*var compNo = qryCompByBusi("COMP_NO",BusiData.BUSI_NO);
	setS('COMP_NO',compNo);*/
	qryCompByBusi("COMP_NO",BusiData.BUSI_NO);
	setI('BUSI_NO',BusiData.BUSI_NO);
	setI('BUSI_NAME',BusiData.BUSI_NAME);
	setS('SIGN_STAT1',BusiData.SIGN_STAT);
	
	setS('SIGN_STAT2',SubBusiData.SIGN_STAT);
	setI('STR_DATE',dateAdd(SubBusiData.STR_DATE));
	setI('END_DATE',dateAdd(SubBusiData.END_DATE));
	setI('BANK_CUST_NO',SubBusiData.BANK_CUST_NO);
	setS('SUB_BUSI_NO',SubBusiData.SUB_BUSI_NO);
	setS('ACCT_TP',SubBusiData.ACCT_TP);
	setI('ACCT',SubBusiData.ACCT);
	setI('ACCT_NAME',SubBusiData.ACCT_NAME);
	setI('OPEN_ACCT_BRCH',SubBusiData.OPEN_ACCT_BRCH);
	setI('BANK_NAME',SubBusiData.BANK_NAME);
	setI('SIGN_PROT_NO',SubBusiData.SIGN_PROT_NO);
	setI('BANK_SIGN_PROT_NO',SubBusiData.BANK_SIGN_PROT_NO);
	setS('FEE_TP',SubBusiData.FEE_TP);
	setS('FEE_CODE',SubBusiData.FEE_CODE);
	setI('FEE_TF_OUT_ACCT',SubBusiData.FEE_TF_OUT_ACCT);
	setI('FEE_TF_OUT_ACCT_NAME',SubBusiData.FEE_TF_OUT_ACCT_NAME);
	setI('FEE_TF_IN_ACCT',SubBusiData.FEE_TF_IN_ACCT);
	setI('FEE_TF_IN_ACCT_NAME',SubBusiData.FEE_TF_IN_ACCT_NAME);
	setI('SUM_CODE',SubBusiData.SUM_CODE);
	setI('SUM_DESC',SubBusiData.SUM_DESC);
	setS('INTRM_ACCT_FLG',SubBusiData.INTRM_ACCT_FLG);
	setI('INTRM_ACCT',SubBusiData.INTRM_ACCT);
	setI('INTRM_ACCT_NAME',SubBusiData.INTRM_ACCT_NAME);
	
	
	/*设置渠道动态多选框*/
	var CHNL_LIST = SubBusiData.CHNL_LIST;
	madeChnlHtml(CHNL_LIST);
	
	/*设置动态检验控制信息回显*/
	var CTRL_LIST = SubBusiData.CTRL_LIST;
	madeCtrlHtml(CTRL_LIST);
	
	/*动态数据不可操作*/
	if($A_M_D == "D" || $A_M_D == "C"){
		$("#ctrlMsg").find("select").multiselect("disable");
		$("#chnlMsg").find("input").prop("disabled", true);
	}
}

function newCtrlData(){
	var i = 0;
	$('#ctrlMsg select').each(function(){
		$(this).find('option').filter(':selected').each(function() {
			$CtrlData[i].KV = $(this).val();
			i++;
		});
	});
	var Data = $CtrlData;
	if(Data == null){
		Data = [];
	}
	return Data;
}

function newChnlData(){
	if($ChnlData !=null){
		var i = 0;
		$("input[name='SIGN_CHNL']").each(function(){
			if($(this).is(":checked")){
				$ChnlData[i].SIGN_FLG = "0";
			}
			i++;
		});
	}
	var Data = $ChnlData;
	if(Data == null){
		Data = [];
	}
	return Data;
}

function newSubBusiData(){
	//动态控制数据
	var CTRL_LIST = newCtrlData();
	var CTRL_NUM = 0;
	if(CTRL_LIST!=null){
		CTRL_NUM = CTRL_LIST.length;
	}
	
	//动态渠道数据
	var CHNL_LIST = newChnlData();
	var CHNL_NUM = 0;
	if(CHNL_LIST!=null){
		CHNL_NUM = CHNL_LIST.length;
	}
	
	var Data = {
			SUB_BUSI_NO:getS("SUB_BUSI_NO"),
			SUB_BUSI_NAME:getST("SUB_BUSI_NO"),
			STR_DATE:dateDelete(getI("STR_DATE")),
			END_DATE:dateDelete(getI("END_DATE")),
			BANK_CUST_NO:getI("BANK_CUST_NO"),
			ACCT_TP:getS("ACCT_TP"),
			ACCT:getI("ACCT"),
			ACCT_NAME:getI("ACCT_NAME"),
			OPEN_ACCT_BRCH:getI("OPEN_ACCT_BRCH"),
			BANK_NAME:getI("BANK_NAME"),
			SIGN_PROT_NO:getI("SIGN_PROT_NO"),
			BANK_SIGN_PROT_NO:getI("BANK_SIGN_PROT_NO"),
			FEE_TP:getS("FEE_TP"),
			FEE_CODE:getS("FEE_CODE"),
			FEE_TF_OUT_ACCT:getI("FEE_TF_OUT_ACCT"),
			FEE_TF_OUT_ACCT_NAME:getI("FEE_TF_OUT_ACCT_NAME"),
			FEE_TF_IN_ACCT:getI("FEE_TF_IN_ACCT"),
			FEE_TF_IN_ACCT_NAME:getI("FEE_TF_IN_ACCT_NAME"),
			SUM_CODE:getI("SUM_CODE"),
			SUM_DESC:getI("SUM_DESC"),
			INTRM_ACCT_FLG:getS("INTRM_ACCT_FLG"),
			INTRM_ACCT:getI("INTRM_ACCT"),
			INTRM_ACCT_NAME:getI("INTRM_ACCT_NAME"),
			SIGN_STAT:getS("SIGN_STAT2"),
			CHNL_NUM:CHNL_NUM,
			CHNL_LIST:CHNL_LIST,
			CTRL_NUM:CTRL_NUM,
			CTRL_LIST:CTRL_LIST,
			NUM:0,
			DYN_LIST:[]
	};
	return Data;
}

function newBusiData(){
	var Data = {
			BUSI_NO:getI("BUSI_NO"),
			BUSI_NAME:getI("BUSI_NAME"),
			SIGN_STAT:getS("SIGN_STAT1"),
			NUM:0,
			DYN_LIST:[],
			SUB_BUSI_NUM:0,
			SUB_BUSI_LIST:null
	};
	return Data;
}

//获取table某行的数据 业务信息回显(i代表行，j代表列)
function getRowData(i,j,tp){
	var Json = "";
	if($A_M_D=='D'){
		Json = $DetailData;
	}else if($A_M_D=='M'){
		Json = $ModData;
	}else if($A_M_D=='A'){
		Json = $AddData;
	}else if($A_M_D == 'C'){
		Json = $DelData;
	}
	
	var BUSI_LIST = Json.BUSI_LIST[i];
	var SUB_BUSI_LIST = BUSI_LIST.SUB_BUSI_LIST[j];
	if(SUB_BUSI_LIST == null){
		return;
	}
	/*业务信息 div部分赋值*/
	setBusiForm(BUSI_LIST,SUB_BUSI_LIST,tp);
	
	$RowNo = i;
	$ColNo = j;
}

/*点新增按钮添加数据到table*/
function addRowData(){
	
	//组装子业务数据
	var SUB_BUSI_NO = getS("SUB_BUSI_NO");
	var SUB_BUSI_LIST = [];
	var SUB_DATA = newSubBusiData();
	SUB_BUSI_LIST.push(SUB_DATA);
	
	//组装业务数据
	var BUSI_LIST = [];
	var BUSI_NO = getI('BUSI_NO');
	var Json;
	if($A_M_D=='A'){  //新增页面 点新增按钮
		Json = $AddData;
		if(Json==null){  //新增页面第一次点新增按钮
			Json = {};
			var BUSI_DATA = newBusiData();
			BUSI_DATA.SUB_BUSI_NUM = SUB_BUSI_LIST.length;
			BUSI_DATA.SUB_BUSI_LIST = SUB_BUSI_LIST;
			BUSI_LIST.push(BUSI_DATA);
			Json.BUSI_LIST = BUSI_LIST;
			$AddData = Json;
		}else{    //新增页面点新增按钮成功后，再次点击新增按钮
			var $rownum = -1; //行
			var $ColNo = -1; //列
			//根据BUSI_NO判断业务是否已存在，存在则只添加子业务，否则添加业务+子业务
			var index = Json.BUSI_LIST.length;
			for(var i=0;i<index;i++){
				if(BUSI_NO == Json.BUSI_LIST[i].BUSI_NO){ //业务已存在
					var SUB_BUSI_LIST = Json.BUSI_LIST[i].SUB_BUSI_LIST;
					//判断该业务下的子业务是否存在
					for(var j=0;j<SUB_BUSI_LIST.length;j++){
						if(SUB_BUSI_NO == SUB_BUSI_LIST[j].SUB_BUSI_NO){
							showTip("业务和子业务已存在!","error");
							return;
						}else{
							$rownum = i;
							$colnum = Json.BUSI_LIST[i].SUB_BUSI_LIST.length;
						}
					}
				}
			}
			if($rownum!=-1){  //业务已存在，只需添加子业务
//				Json.BUSI_LIST[$rownum].SUB_BUSI_LIST.push(SUB_DATA);
				Json.BUSI_LIST[$rownum].SUB_BUSI_LIST.splice($ColNo,0,SUB_DATA);
			}else{
				var BUSI_DATA = newBusiData();
				BUSI_DATA.SUB_BUSI_NUM = SUB_BUSI_LIST.length;
				BUSI_DATA.SUB_BUSI_LIST = SUB_BUSI_LIST;
				Json.BUSI_LIST.push(BUSI_DATA);
				$AddData = Json;
			}
		}
	}else if($A_M_D == "M"){  //修改页面点新增按钮
		Json = $ModData;
		var index = Json.BUSI_LIST.length;
		var $rownum = -1; //行
		var $colnum = -1; //列
		//根据BUSI_NO判断业务是否已存在，存在则只添加子业务，否则添加业务+子业务
		for(var i=0;i<index;i++){
			if(BUSI_NO == Json.BUSI_LIST[i].BUSI_NO){ //业务已存在
				var SUB_BUSI_LIST = Json.BUSI_LIST[i].SUB_BUSI_LIST;
				//判断该业务下的子业务是否存在
				for(var j=0;j<SUB_BUSI_LIST.length;j++){
					if(SUB_BUSI_NO == SUB_BUSI_LIST[j].SUB_BUSI_NO){
						showTip("业务和子业务已存在!","error");
						return;
					}else{
						$rownum = i;
						$colnum = Json.BUSI_LIST[i].SUB_BUSI_LIST.length;
					}
				}
			}
		}
		if($rownum!=-1){
			Json.BUSI_LIST[$rownum].SUB_BUSI_LIST.push(SUB_DATA);
//			Json.BUSI_LIST[$rownum].SUB_BUSI_LIST.splice($colnum,0,SUB_DATA);
		}else{
			var BUSI_DATA = newBusiData();
			BUSI_DATA.SUB_BUSI_NUM = SUB_BUSI_LIST.length;
			BUSI_DATA.SUB_BUSI_LIST = SUB_BUSI_LIST;
			Json.BUSI_LIST.push(BUSI_DATA);
		}
		/*把改动后的Json赋值给$ModData*/
		$ModData = Json;
	}
	
	showTip("新增成功!","success");
	/*清空busiForm内容*/
	resetForm("busiForm",false,false);
	/*resetForm("chkForm",false,false);*/
	madeCtrlHtml(null);
	madeChnlHtml(null);
	
	$("select[name='FEE_CODE']").multiselect('enable');
	$("#INTRM_ACCT,#INTRM_ACCT_NAME").removeAttr("disabled");
	/*加载table*/
	setTable(Json);
	
	parent.goTop();
}

/*修改table中某行数据(根据行号、列号判断修改规则)*/
function modRowData(){
	if($RowNo == -1 || $ColNo == -1){
		return;
	}
	if(portion("busiForm")){
		var json = null;
		if($A_M_D == "A"){
			json = $AddData;
		}else if($A_M_D == "M"){
			json = $ModData;
		}
		
		//修改业务
		json.BUSI_LIST[$RowNo].BUSI_NO = getI("BUSI_NO");
		json.BUSI_LIST[$RowNo].BUSI_NAME = getI("BUSI_NAME");
		json.BUSI_LIST[$RowNo].SIGN_STAT = getS("SIGN_STAT1");
		//修改子业务
		var SUB_BUSI_LIST = newSubBusiData();
		json.BUSI_LIST[$RowNo].SUB_BUSI_LIST[$ColNo] = SUB_BUSI_LIST;
		
		showTip("修改成功","success");
		resetForm("busiForm", false, false);
		/*resetForm("chkForm", false, false);*/
		madeCtrlHtml(null);
		madeChnlHtml(null);
		
		$("select[name='FEE_CODE']").multiselect('enable');
		$("#INTRM_ACCT,#INTRM_ACCT_NAME").removeAttr("disabled");
		
		parent.goTop();
		
		//修改成功后$RowNo,$ColNo重新赋值为 -1，代表不可修改
		$RowNo = -1;
		$ColNo = -1;
		
		//修改成功后重新加载table
		setTable(json);
		
		//把修改后的数据重新赋值到 $AddData 或者 $ModData
		if($A_M_D == "A"){
			$AddData = json;
		}else if($A_M_D == "M"){
			$ModData = json;
		}
	}else{
		parent.goTop();
	}
}

function delRowData(i,j){
	parent.goTop();
	Ewin.confirm({
		title : "操作提示",
		message : "是否要删除？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		
		var Json;
		if($A_M_D=='A'){
			Json = $AddData;
		}else if($A_M_D=='M'){
			Json = $ModData;
		}
		var BUSI_LIST = Json.BUSI_LIST;
		var SUB_BUSI_LIST = BUSI_LIST[i].SUB_BUSI_LIST;
		/*当业务下只有一个子业务时,直接删除该业务
		 *当业务下有多个子业务时,只删除该业务下的某个子业务 */
		if(SUB_BUSI_LIST.length==1){
			Json.BUSI_LIST.splice(i,1);
		}else{
			Json.BUSI_LIST[i].SUB_BUSI_LIST.splice(j,1);
		}
		//把改动到的数据 赋值到全局变量 $ModData或者$AddData
		if($A_M_D=='A'){
			$AddData = Json;
		}else if($A_M_D=='M'){
			$ModData = Json;
		}
		/*重新加载table*/
		setTable(Json);
		showTip("删除成功!","success");
	});
}


/*获取日期控件转换值*/
function getCompDateVal(id){
	var $val = getDateValue(id);
	if($val == undefined){
		return "";
	}
	return $val;
}

//点击提交按钮，根据$A_M_D判断是新增还是修改
function commitFun(){
	var $url = "";
	var $ENTR_LIST = "";
	var index = $("#busiTable").bootstrapTable('getData').length;
	if(index == 0){
		showTip("请至少添加一条业务信息","error");
		return;
	}
	if($A_M_D == "A"){
		$AddData.ENTR_NO = getI('ENTR_NO');
		$AddData.ENTR_NAME = getI('ENTR_NAME');
		$AddData.PRT_NAME = getI('PRT_NAME');
		$AddData.CERT_TP = getS('CERT_TP');
		$AddData.CERT_NO = getI('CERT_NO');
		$AddData.CTCT_PER_NAME = getI('CTCT_PER_NAME');
		$AddData.PER_TEL_NO = getI('PER_TEL_NO');
		$AddData.LEGA_NO = getS('LEGA_NO');
		$AddData.LEGA_NAME = getST('LEGA_NO');
		$AddData.ENTR_TEL_NO = getI('ENTR_TEL_NO');
		$AddData.COMM_ADDR = getI('COMM_ADDR');
		$AddData.POST_ECD = getI('POST_ECD');
		$AddData.EMAIL_ADDR = getI('EMAIL_ADDR');
		$AddData.SIGN_STAT = getS('SIGN_STAT');
		$AddData.OPEN_STAT = getS('OPEN_STAT');
		$AddData.BUSI_NUM = $AddData.BUSI_LIST.length;
		for(var i=0;i<$AddData.BUSI_NUM;i++){
			var subList = $AddData.BUSI_LIST[i].SUB_BUSI_LIST;
			var subNum = subList.length;
			$AddData.BUSI_LIST[i].SUB_BUSI_NUM = subNum;
		}
		
		/*模拟动态数据*/
		/*$AddData.BUSI_LIST[0].SUB_BUSI_LIST[0].CTRL_LIST = [{"KEY":"INTE_ENTR_SYS","KEY_NAME":"单位签约是否需要上行内系统打标记","KV":"N","KV_DESC":"Y-是|N-否|"}];
		$AddData.BUSI_LIST[0].SUB_BUSI_LIST[0].CTRL_NUM = 1;
		$AddData.BUSI_LIST[0].SUB_BUSI_LIST[0].CHNL_LIST = [{"CHNL_NO":"0001","CHNL_NAME":"柜面","SIGN_FLG":"0"}];
		$AddData.BUSI_LIST[0].SUB_BUSI_LIST[0].CHNL_NUM = 1;*/
		
		/*将json格式转成string格式*/
		$ENTR_LIST = JSON.stringify($AddData);
		$url = "/comp/sign/tec/entrsign/entrSignAdd";
	}else{
		$ModData.BUSI_NUM = $ModData.BUSI_LIST.length;
		for(var i=0;i<$ModData.BUSI_NUM;i++){
			var subList = $ModData.BUSI_LIST[i].SUB_BUSI_LIST;
			var subNum = subList.length;
			$ModData.BUSI_LIST[i].SUB_BUSI_NUM = subNum;
		}
		
		/*将json格式转成string格式*/
		$ENTR_LIST = JSON.stringify($ModData);
		$url = "/comp/sign/tec/entrsign/entrSignMod";
	}
	
	$.ajax({
		type:"POST",
		url:ctx + $url,
		dataType:"json",
		data:{
			ENTR_LIST:$ENTR_LIST
		},
		success:function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]";
				showTip(errMsg,"error");
			}else{
				showTip("提交成功","success");
				parent.tab1($A_M_D);
			}
		}
	});
}

//单位解约
function entrCan(entrNo,busiNo,subBusiNo){
	parent.goTop();
	Ewin.confirm({
		title : "操作提示",
		message : "是否要解约？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		var SUB_BUSI_LIST = [];
		var BUSI_LIST = [];
		var BUSI_NUM = 0;
		var ENTR_LIST = {};
		if(busiNo!="" && subBusiNo!=""){  //解约该单位编号下 某个业务
			var subData = {
					SUB_BUSI_NO:subBusiNo
			}
			SUB_BUSI_LIST.push(subData);
			
			var busiData = {
					BUSI_NO:busiNo,
					SUB_BUSI_LIST:SUB_BUSI_LIST,
					SUB_BUSI_NUM:SUB_BUSI_LIST.length
			}
			BUSI_LIST.push(busiData);
			BUSI_NUM = BUSI_LIST.length;
		}
		
		ENTR_LIST.BUSI_LIST = BUSI_LIST;
		ENTR_LIST.BUSI_NUM = BUSI_NUM;
		ENTR_LIST.ENTR_NO = entrNo;
		
		/*将json格式转成string格式*/
		var $ENTR_LIST = JSON.stringify(ENTR_LIST);
		$.ajax({
			type:"POST",
			url:ctx + "/comp/sign/tec/entrsign/entrSignCan",
			dataType:"json",
			data:{
				delDataStr:$ENTR_LIST
			},
			success:function(data){
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var errMsg = "错误信息["+data.message+"]";
					showTip(errMsg,"error");
				}else{
					showTip("提交成功","success");
					parent.tab1("C");
				}
			}
		});
	});
}

function closeFun(){
	parent.goTop();
	if($A_M_D == "D" || $A_M_D == "C"){
		parent.tab1();
	}else{
		Ewin.confirm({
			title : "操作提示",
			message : "数据会清空，确定返回吗？"
		}).on(function(e) {
			if (!e) {
				return;
			}
			parent.tab1();
		});
	}
}

