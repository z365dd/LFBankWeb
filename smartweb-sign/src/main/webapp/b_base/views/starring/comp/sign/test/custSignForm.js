console.log('custSignForm.js');

/*父页面变量$A_M_D
A-新增，M-修改，D-详细
从父页面获取*/
var $A_M_D  = parent.$A_M_D;
var $AddData = null;
var $DetailData = null;
var $DelData = null;
var $ModData = null;
var $ModData_O = null;
var $CtrlData = null;
/*修改table中数据需要传入 行号 、列号作参数*/
var $RowNo = -1;
var $ColNo = -1;

$(function(){
	
	$("#busiMsg").hide();
	$("#upBtn").attr('disabled',"true");
	
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
		if(portion("mainForm")){
			$("#mainForm").hide();
			$("#busiMsg").show();
			$("#nextBtn").attr('disabled',"true");
			$("#upBtn").removeAttr('disabled');
			ifrChildAut("panel2");
		}
		if($A_M_D == "A"){
			//动态检验数据
			createChk();
		}
	});
	
	/*A:异步加载下拉框   M/D/C:同步加载下拉框*/
	getLegaNo1("LEGA_NO");
	/*getCompNo1("COMP_NO");*/
	/*if($A_M_D == "A"){
		getLegaNo("LEGA_NO");
		getCompNo("COMP_NO");
	}else{
		getLegaNo1("LEGA_NO");
		getCompNo1("COMP_NO");
	}*/
	
	/*   ------ 业务信息部分 --------  */
	
	/*组件号改变重新加载业务编号*/
	/*$("#COMP_NO").change(function(){
		$("#BUSI_NO").val("");
		resBusiNo();
		resetS("SUB_BUSI_NO");
	});*/
	
	$("#BUSI_NO").click(function(){
		var $entrNo = getI("ENTR_NO");
		if($entrNo == ""){
			showTip("请先选择单位编号信息!", "error");
			return;
		}
		
		var $url = "/comp/sign/pub/entrSignBusiList?state=0";
		//筛选签约状态为Y的业务编号
		$url += ("&entrNo="+$entrNo);
		
		$("#BUSI_NO").attr("search_url", $url);
		//busiClick("BUSI_NO", "BUSI_NAME", null, resBusiNo);
		showJboxView("BUSI_NO", "BUSI_NAME", resBusiNo);
	});
	
	//修改子业务事件
	$("#SUB_BUSI_NO").on('change',SignCustItemQry);
	
	/*法人号改变重新加载单位编号*/
	$('#LEGA_NO').change(function(){
		$("#ENTR_NO").val("");
		resEntrNo();
	});
	
	$('#ENTR_NO').click(function(){
		var legaNo = getS("LEGA_NO");
		if(legaNo == ""){
			/*var $url = "/comp/sign/pub/entrData?stat=11";*/  //stat=11随便赋值的(只有不为""、1、2、3即可)
			showTip("请先选择法人信息!", "error");
			return;
		}else{
			var $url = "/comp/sign/pub/entrData?legaNo="+legaNo+"&stat=1";
		}
		$("#ENTR_NO").attr("search_url",$url);
		entrClick("ENTR_NO","ENTR_NAME",resEntrNo);
	});
	
	/*点击生成检验按钮
	$("#ctrlBtn").on('click',SignCustItemQry);*/
	
	/*点击新增按钮*/
//	$("#addBtn").on('click',addRowData)
	$("#addBtn").click(function(){
		if(portion("busiForm")){
			addRowData();
		}else{
			parent.goTop();
		}
	});
	/*点击修改按钮*/
	$("#modBtn").on('click',modRowData)
	/*$("#modBtn").click(function(){
		if(portion("busiForm")){
			modRowData();
		}
	});*/
	
	/*点击提交按钮 根据$A_M_D判断是新增还是修改*/
	if($A_M_D=='A'){
		$("#saveBtn").on('click',add);
		//密码输入框是否可输
		$("#ACCT_TP").change(function(){
			var ACCT_TP = getS("ACCT_TP");
			if(ACCT_TP=="01" || ACCT_TP=="10" || ACCT_TP=="11"){
				$("#ACCT_PASS").removeAttr('disabled')
			}else{
				$("#ACCT_PASS").prop('disabled',true);
				$("input[id=ACCT_PASS]").parent().siblings('span.warnBlock').remove();
			}
		});
	}else if($A_M_D=='M'){
		$("#saveBtn").on('click',mod);
	}
	
	/*点击返回按钮*/
	$("#closeBtn").on('click',closeFun);
	
	/*单笔金额上限转换为金额格式*/
	$('#SGL_LIM').change(function(){
		$('#SGL_LIM').val(changeNum($('#SGL_LIM').val()));
	});
	
	/*日累计金额上限转换为金额格式*/
	$('#DAY_LIM').change(function(){
		$('#DAY_LIM').val(changeNum($('#DAY_LIM').val()));
	});
	
	/*月累计金额上限转换为金额格式*/
	$('#MONTH_LIM').change(function(){
		$('#MONTH_LIM').val(changeNum($('#MONTH_LIM').val()));
	});
});

/*根据业务编号查询子业务编号(只返回可被签约的)*/
function resBusiNo(){
	var $busiNo = $("#BUSI_NO").val();
	if($busiNo == ""){
		$("select[name=SUB_BUSI_NO]").empty();
	}else{
		$("input[id=BUSI_NO]").parent().siblings('span.warnBlock').remove();
		var Data = {
				entrNo:getI("ENTR_NO"),
				busiNo:$busiNo,
				state:"0"
		}
		setSelect2("SUB_BUSI_NO","/comp/sign/pub/entrSignSubBusiList","SUB_BUSI_NO","SUB_BUSI_NAME",Data);
	}
}

/**/
function resEntrNo(){
	if(getI("ENTR_NO") != ""){
		$("input[id=ENTR_NO]").parent().siblings('span.warnBlock').remove();
	}
}

/*查询动态检验数据*/
function createChk(){
	/*SignCustItemQry();*/
	ifrChildAut("panel2");
}

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
		$ModData_O = allData;
		/*$("#mainForm").find("select").multiselect("disable");
		$("#mainForm").find("input").prop("disabled", true);*/
		$("#ctrlBtn").hide();
		/*业务信息  组件号  业务编号不可修改*/
		$("#LEGA_NO").multiselect("disable");
		$("#BUSI_NO,#ENTR_NO,#addBtn").prop('disabled',true);
	}else if(tp == "C"){
		$DelData = allData;
		$("#mainForm,#busiForm").find("select").multiselect("disable");
		$("#mainForm,#busiForm").find("input").prop("disabled", true);
		$("#addBtn,#modBtn,#saveBtn,#ctrlBtn").hide();
	}
	
	//公共数据回显
	setMainForm(allData);
	
	//table数据回显
	setTable(allData);
}

function setMainForm(Data){
	/*部分数据不可修改*/
	if($A_M_D=='A'){
		$('#ACCT').attr('readonly','true');
		$('#ACCT_NAME').attr('readonly','true');
		$('#BANK').attr('readonly','true');
		$('#BANK_NAME').attr('readonly','true');
		$("select[name='ACCT_TP']").multiselect('disable');
		$("select[name='CUST_TP']").multiselect('disable');
	}else if($A_M_D == "M"){
		$("#ACCT,#ACCT_NAME,#BANK,#BANK_NAME").prop('disabled',true);
		$("select[name='ACCT_TP']").multiselect('disable');
		$("select[name='CUST_TP']").multiselect('disable');
		$("select[name='SIGN_STAT']").multiselect('disable');
	}
	setS('ACCT_TP',Data.ACCT_TP);
	setS('CERT_TP',Data.CERT_TP);
	setS('CUST_TP',Data.CUST_TP);
	setS('SIGN_STAT',Data.SIGN_STAT);
	setI('ACCT',Data.ACCT);
	setI('ACCT_NAME',Data.ACCT_NAME);
	setI('BANK',Data.BANK);
	setI('BANK_NAME',Data.BANK_NAME);
	setI('TEL_NO',Data.TEL_NO);
	setI('WCHAT_NO',Data.WCHAT_NO);
	setI('COMM_ADDR',Data.COMM_ADDR);
	setI('POST_ECD',Data.POST_ECD);
	setI('EMAIL_ADDR',Data.EMAIL_ADDR);
	setI('CERT_NO',Data.CERT_NO);
	
}

//设置table数据，用于新增、修改时重新加载数据
function setTable(Data){
	var TableData = [];
	var BUSI_LIST = Data.BUSI_LIST;
	var ACCT = getI("ACCT");
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
				if("1" == BUSI_LIST[i].SIGN_STAT){
					actionStr = "<a href=\"JavaScript:void(0);\" onClick=\"getRowData('"+i+"','" +j+"','"+tp+"')\">详情</a>";
				}else{
					actionStr = "<a href=\"JavaScript:void(0);\" onClick=\"getRowData("+i+ "," +j+")\">详情</a>&nbsp&nbsp"
					+"<a href=\"JavaScript:void(0);\" onClick=\"custCan('"+ACCT+"','"+BUSI_LIST[i].ENTR_NO+ "','" +BUSI_LIST[i].BUSI_NO+ "','" +SUB_BUSI_LIST[j].SUB_BUSI_NO+"','"+SUB_BUSI_LIST[j].OTH_CUST_NO+"')\">解约</a>";
				}
			}
			var tData ={
					BUSI_NO:BUSI_LIST[i].BUSI_NO,
					BUSI_NAME:BUSI_LIST[i].BUSI_NAME,
					SUB_BUSI_NO:SUB_BUSI_LIST[j].SUB_BUSI_NO,
					SUB_BUSI_NAME:SUB_BUSI_LIST[j].SUB_BUSI_NAME,
					OTH_CUST_NO:SUB_BUSI_LIST[j].OTH_CUST_NO,
					ACTION:actionStr
			}
			TableData.push(tData);
		}
	}
	$("#busiTable").bootstrapTable('load', TableData);
}

//设置业务信息div内input和select
function setBusiForm(BUSI_LIST,SUB_BUSI_LIST,tp){
	var backS = [{label:SUB_BUSI_LIST.SUB_BUSI_NAME,value:SUB_BUSI_LIST.SUB_BUSI_NO}];
	if($A_M_D == 'D'){
		$("select[name='SUB_BUSI_NO']").multiselect('dataprovider', backS).multiselect('disable');
		$("#busiForm").find("select").multiselect("disable");
		$("#busiForm").find("input").prop("disabled", true);
	}else if($A_M_D == 'M'){
		$("select[name='SUB_BUSI_NO']").multiselect('dataprovider', backS).multiselect('disable');
		$("#SIGN_STAT1,#SIGN_STAT2,#LEGA_NO,#SUB_BUSI_NO").multiselect("disable");
		$("#BUSI_NO,#ENTR_NO").prop("disabled", true);
		$("#ctrlBtn").hide();
		$("#STR_DATE").removeAttr('check-startTime-one');
		$("#END_DATE").removeAttr('check-endTime-one');
	}
	if(tp=="Detail"){
		$("#busiForm").find("select").multiselect("disable");
		$("#busiForm").find("input").prop("disabled", true);
//		$("#addBtn,#modBtn").attr('disabled',true);
	}
//	setS('LEGA_NO',BUSI_LIST.LEGA_NO);
//	setS('COMP_NO',BUSI_LIST.COMP_NO);
	qryLegaByEntr("LEGA_NO",BUSI_LIST.ENTR_NO);
	/*qryCompByBusi("COMP_NO",BUSI_LIST.BUSI_NO);*/
	setS('SIGN_STAT1',BUSI_LIST.SIGN_STAT);
	setI('BUSI_NO',BUSI_LIST.BUSI_NO);
	setI('BUSI_NAME',BUSI_LIST.BUSI_NAME);
	setI('ENTR_NO',BUSI_LIST.ENTR_NO);
	setI('ENTR_NAME',BUSI_LIST.ENTR_NAME);
	
	setS('SUB_BUSI_NO',SUB_BUSI_LIST.SUB_BUSI_NO);
	setS('SIGN_STAT2',SUB_BUSI_LIST.SIGN_STAT);
	setS('BANK_SIGN_FLG',SUB_BUSI_LIST.BANK_SIGN_FLG);
	setS('OTH_SIGN_FLG',SUB_BUSI_LIST.OTH_SIGN_FLG);
	setI('SGL_LIM',changeNum(SUB_BUSI_LIST.SGL_LIM));
	setI('DAY_LIM',changeNum(SUB_BUSI_LIST.DAY_LIM));
	setI('DAY_NUM',SUB_BUSI_LIST.DAY_NUM);
	setI('MONTH_LIM',changeNum(SUB_BUSI_LIST.MONTH_LIM));
	setI('MONTH_NUM',SUB_BUSI_LIST.MONTH_NUM);
	setI('STR_DATE',dateAdd(SUB_BUSI_LIST.STR_DATE));
	setI('END_DATE',dateAdd(SUB_BUSI_LIST.END_DATE));
	setI('OTH_CUST_NO',SUB_BUSI_LIST.OTH_CUST_NO);
	setI('OTH_ENTR_NO',SUB_BUSI_LIST.OTH_ENTR_NO);
	setI('OTH_SIGN_PROT_NO',SUB_BUSI_LIST.OTH_SIGN_PROT_NO);
	setI('OPP_ACCT',SUB_BUSI_LIST.OPP_ACCT);
	setI('OPP_ACCT_NAME',SUB_BUSI_LIST.OPP_ACCT_NAME);
	setI('OPP_BANK',SUB_BUSI_LIST.OPP_BANK);
	setI('OPP_BANK_NAME',SUB_BUSI_LIST.OPP_BANK_NAME);
	setI('SIGN_PROT_NO',SUB_BUSI_LIST.SIGN_PROT_NO);
	setI('BANK_SIGN_PROT_NO',SUB_BUSI_LIST.BANK_SIGN_PROT_NO);
	setI('BANK_CUST_NO',SUB_BUSI_LIST.BANK_CUST_NO);
	
	//设置动态校验信息
	var CTRL_LIST = SUB_BUSI_LIST.CTRL_LIST;
	madeCtrlHtml(CTRL_LIST);
	ifrChildAut("panel2");
	//动态数据不可修改
	if($A_M_D == "D" || $A_M_D == "C"){
		$("#ctrlMsg").find("select").multiselect("disable");
	}
}

//查询客户签约检查项
function SignCustItemQry(){
	var BUSI_NO = getI('BUSI_NO');
	var SUB_BUSI_NO = getS('SUB_BUSI_NO');
	var ENTR_NO = getI('ENTR_NO');
	$.ajax({
		type:"POST",
		url:ctx + "/comp/sign/pub/signCustItemQry",
		dataType:"json",
		data:{
			BUSI_NO:"0",
			SUB_BUSI_NO:"0",
			ENTR_NO:"0"
		},
		success:function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]";
				showTip(errMsg,"error");
			}else{
				var valData=data.dataSetResult[0].data;
				/*生成动态检查项html*/
				madeCtrlHtml(valData);
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
		$html += "<div class='col-md-6 column'>";
		$html += "<div ravo='rainbow_fx' class='form-group'>";
		if(i%2 == 0){
			$html += "<label for='inputEmail3' class='col-sm-5 control-label'>"+keyName+"</label>";
		}else{
			$html += "<label for='inputEmail3' class='col-sm-3 control-label'>"+keyName+"</label>";
		}
		$html += "<div class='col-sm-3'>";
		$html += "<select data-role='multiselect' class='' id='"+key+"' name='"+key+"'  data-max-height='300'> "+ $opHtml +"</select>";
		$html += "</div>";
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

/*返回动态校验Data*/
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

/*返回子业务Data*/
function newSubBusiData(){
	//动态控制数据
	var CTRL_LIST = newCtrlData();
	var CTRL_NUM = 0;
	if(CTRL_LIST!=null){
		CTRL_NUM = CTRL_LIST.length;
	}
	
	var Data = {
			SUB_BUSI_NO:getS('SUB_BUSI_NO'),
			SUB_BUSI_NAME:getST('SUB_BUSI_NO'),
			OTH_CUST_NO:getI('OTH_CUST_NO'),
			OTH_ENTR_NO:getI('OTH_ENTR_NO'),
			BANK_CUST_NO:getI('BANK_CUST_NO'),
			SIGN_PROT_NO:getI('SIGN_PROT_NO'),
			STR_DATE:dateDelete(getI('STR_DATE')),
			END_DATE:dateDelete(getI('END_DATE')),
			SIGN_STAT:getS('SIGN_STAT2'),
			BANK_SIGN_FLG:getS('BANK_SIGN_FLG'),
			OTH_SIGN_FLG:getS('OTH_SIGN_FLG'),
			SIGN_PROT_NO:getI('SIGN_PROT_NO'),
			BANK_SIGN_PROT_NO:getI('BANK_SIGN_PROT_NO'),
			OTH_SIGN_PROT_NO:getI('OTH_SIGN_PROT_NO'),
			OPP_ACCT:getI('OPP_ACCT'),
			OPP_ACCT_NAME:getI('OPP_ACCT_NAME'),
			OPP_BANK:getI('OPP_BANK'),
			OPP_BANK_NAME:getI('OPP_BANK_NAME'),
			SGL_LIM:getI('SGL_LIM'),
			DAY_NUM:getI('DAY_NUM'),
			DAY_LIM:getI('DAY_LIM'),
			MONTH_NUM:getI('MONTH_NUM'),
			MONTH_LIM:getI('MONTH_LIM'),
			CTRL_NUM:CTRL_NUM,
			CTRL_LIST:CTRL_LIST,
			NUM:0,
			DYN_LIST:[]
	}
	return Data;
}

/*返回业务Data*/
function newBusiData(){
	var Data = {
			ENTR_NO:getI("ENTR_NO"),
			ENTR_NAME:getI("ENTR_NAME"),
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

//点新增按钮，向table添加数据
function addRowData(){
	
	//组装子业务数据
	var SUB_BUSI_NO = getS("SUB_BUSI_NO");
	var SUB_BUSI_LIST = [];
	var SUB_DATA = newSubBusiData();
	SUB_BUSI_LIST.push(SUB_DATA);
	
	/*$A_M_D A-新增   M-修改*/
	/*组装业务数据*/
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
			var $colnum = -1; //列
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
			if($rownum!=-1){
//				Json.BUSI_LIST[$rownum].SUB_BUSI_LIST.push(SUB_DATA);
				Json.BUSI_LIST[$rownum].SUB_BUSI_LIST.splice($colnum,0,SUB_DATA);
			}else{
				var BUSI_DATA = newBusiData();
				BUSI_DATA.SUB_BUSI_NUM = SUB_BUSI_LIST.length;
				BUSI_DATA.SUB_BUSI_LIST = SUB_BUSI_LIST;
				Json.BUSI_LIST.push(BUSI_DATA);
				$AddData = Json;
			}
		}
	}else if($A_M_D=='M'){ //修改页面 点新增按钮
		Json = $ModData;
		var index = Json.BUSI_LIST.length;
		var $rownum = -1; //行
		var $colnum = -1; //列
		//根据BUSI_NO判断业务是否已存在，存在则只添加子业务，否则添加业务+子业务
		for(var i=0;i<index;i++){
			if(BUSI_NO==Json.BUSI_LIST[i].BUSI_NO){
				$rownum = i;
				$colnum = Json.BUSI_LIST[i].SUB_BUSI_LIST.length;
			}
		}
		if($rownum!=-1){
			Json.BUSI_LIST[$rownum].SUB_BUSI_LIST.push(SUB_DATA);
//			Json.BUSI_LIST[$rownum].SUB_BUSI_LIST.splice($colnum,0,SUB_DATA);
		}else{
			var BUSI_DATA = newBusiData();
			BUSI_DATA.SUB_BUSI_NUM = SUB_BUSI_LIST.length;
			BUSI_DATA.SUB_BUSI_LIST = SUB_BUSI_LIST;
			BUSI_LIST.push(BUSI_DATA);
			Json.BUSI_LIST = BUSI_LIST;
		}
		/*把改动后的Json赋值给$ModData*/
		$ModData = Json;
	}
	showTip("新增成功!","success");
	/*清空busiForm内容*/
	resetForm("busiForm",false,false);
	/*resetForm("ctrlMsg",false,false);*/
	madeCtrlHtml(null);
	
	/*加载table*/
	setTable(Json);
	
	parent.goTop();
}

/*修改table*/
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
		json.BUSI_LIST[$RowNo].ENTR_NO = getI("ENTR_NO");
		json.BUSI_LIST[$RowNo].ENTR_NAME = getI("ENTR_NAME");
		json.BUSI_LIST[$RowNo].BUSI_NO = getI("BUSI_NO");
		json.BUSI_LIST[$RowNo].BUSI_NAME = getI("BUSI_NAME");
		json.BUSI_LIST[$RowNo].SIGN_STAT = getS("SIGN_STAT1");
		//修改子业务
		var SUB_BUSI_LIST = newSubBusiData();
		json.BUSI_LIST[$RowNo].SUB_BUSI_LIST[$ColNo] = SUB_BUSI_LIST;
		
		showTip("修改成功","success");
		resetForm("busiForm", false, false);
		/*resetForm("ctrlMsg", false, false);*/
		madeCtrlHtml(null);
		
		//修改成功后$RowNo,$ColNo重新赋值为 -1，代表不可修改
		$RowNo = -1;
		$ColNo = -1;
		
		//修改成功后重新加载table
		setTable(json);
		
		parent.goTop();
		
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

//获取table某行的数据 业务信息回显(i代表行，j代表列)
function getRowData(i,j,tp){
	var Json = "";
	if($A_M_D=='D'){
		Json = $DetailData;
	}else if($A_M_D=='M'){
		Json = $ModData;
	}else if($A_M_D=='A'){
		Json = $AddData;
	}else if($A_M_D=="C"){
		Json = $DelData;
	}
	var BUSI_LIST = Json.BUSI_LIST[i];
	var SUB_BUSI_LIST = BUSI_LIST.SUB_BUSI_LIST[j];
	/*设置业务信息div内input和select*/
	setBusiForm(BUSI_LIST,SUB_BUSI_LIST,tp)
	
	$RowNo = i;
	$ColNo = j;
}

//删除table中某行的数据(i代表行，j代表列);
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



//客户签约新增
function add(){
	var index = $("#busiTable").bootstrapTable('getData').length;
	if(index == 0){
		showTip("请至少添加一条业务信息","error");
		return;
	}
	/*获取公共数据 并组装成json格式*/
	$AddData.ACCT = getI('ACCT');
	$AddData.ACCT_NAME = getI('ACCT_NAME');
	$AddData.ACCT_TP = getS('ACCT_TP');
	$AddData.ACCT_PASS = getI('ACCT_PASS');
	$AddData.CUST_TP = getS('CUST_TP');
	$AddData.SIGN_STAT = getS('SIGN_STAT');
	$AddData.BANK = getI('BANK');
	$AddData.BANK_NAME = getI('BANK_NAME');
	$AddData.TEL_NO = getI('TEL_NO');
	$AddData.WCHAT_NO = getI('WCHAT_NO');
	$AddData.COMM_ADDR = getI('COMM_ADDR');
	$AddData.POST_ECD = getI('POST_ECD');
	$AddData.EMAIL_ADDR = getI('EMAIL_ADDR');
	$AddData.CERT_TP = getS('CERT_TP');
	$AddData.CERT_NO = getI('CERT_NO');
	$AddData.BUSI_NUM = $AddData.BUSI_LIST.length;
	for(var i=0;i<$AddData.BUSI_NUM;i++){
		var subList = $AddData.BUSI_LIST[i].SUB_BUSI_LIST;
		var subNum = subList.length;
		$AddData.BUSI_LIST[i].SUB_BUSI_NUM = subNum;
	}
	$AddData.NUM = 0;
	$AddData.DYN_LIST = [];
	
	/*将json格式转成string格式*/
	var addDataStr = JSON.stringify($AddData);
	
	$.ajax({
		type:"POST",
		url:ctx + "/comp/sign/test/custsign/signCustSign",
		dataType:"json",
		data:{
			addDataStr:addDataStr
		},
		success:function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]";
				showTip(errMsg,"error");
			}else{
				showTip("提交成功","success");
				parent.tab1("A");
			}
		}
	});
}

//客户签约修改
function mod(){
	var index = $("#busiTable").bootstrapTable('getData').length;
	if(index == 0){
		showTip("请至少添加一条业务信息","error");
		return;
	}
	/*获取公共数据 并组装成json格式*/
	$ModData.ACCT = getI('ACCT');
	$ModData.ACCT_NAME = getI('ACCT_NAME');
	$ModData.ACCT_TP = getS('ACCT_TP');
	$ModData.ACCT_PASS = getI('ACCT_PASS');
	$ModData.CUST_TP = getS('CUST_TP');
	$ModData.SIGN_STAT = getS('SIGN_STAT');
	$ModData.BANK = getI('BANK');
	$ModData.BANK_NAME = getI('BANK_NAME');
	$ModData.TEL_NO = getI('TEL_NO');
	$ModData.WCHAT_NO = getI('WCHAT_NO');
	$ModData.COMM_ADDR = getI('COMM_ADDR');
	$ModData.POST_ECD = getI('POST_ECD');
	$ModData.EMAIL_ADDR = getI('EMAIL_ADDR');
	$ModData.CERT_TP = getS('CERT_TP');
	$ModData.CERT_NO = getI('CERT_NO');
	$ModData.BUSI_NUM = $ModData.BUSI_LIST.length;
	for(var i=0;i<$ModData.BUSI_NUM;i++){
		var subList = $ModData.BUSI_LIST[i].SUB_BUSI_LIST;
		var subNum = subList.length;
		$ModData.BUSI_LIST[i].SUB_BUSI_NUM = subNum;
	}
	$ModData.NUM = 0;
	$ModData.DYN_LIST = [];
	
	/*将json格式转成string格式*/
	var modDataStr = JSON.stringify($ModData);
	
	$.ajax({
		type:"POST",
		url:ctx + "/comp/sign/test/custsign/signCustMod",
		dataType:"json",
		data:{
			modDataStr:modDataStr
		},
		success:function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]";
				showTip(errMsg,"error");
				//修改失败,$ModData等于第一次进入修改页面的值
				$ModData = $ModData_O;
			}else{
				showTip("提交成功","success");
				parent.tab1("M");
			}
		}
	});
}

//客户解约
function custCan(acct,entrNo,busiNo,subBusiNo,othCustNo){
	parent.goTop();
	Ewin.confirm({
		title : "操作提示",
		message : "是否要解约？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		var SUB_BUSI_LIST = [];
		var subData = {
				SUB_BUSI_NO:subBusiNo,
				OTH_CUST_NO:othCustNo
		}
		SUB_BUSI_LIST.push(subData);
		
		var BUSI_LIST = [];
		var busiData = {
				BUSI_NO:busiNo,
				ENTR_NO:entrNo,
				SUB_BUSI_LIST:SUB_BUSI_LIST,
				SUB_BUSI_NUM:SUB_BUSI_LIST.length
		}
		BUSI_LIST.push(busiData);
		
		var $ACCT_LIST = {};
		$ACCT_LIST.ACCT = acct;
		$ACCT_LIST.BUSI_NUM = BUSI_LIST.length;
		$ACCT_LIST.BUSI_LIST = BUSI_LIST;
		
		/*将json格式转成string格式*/
		var delDataStr = JSON.stringify($ACCT_LIST);
		
		$.ajax({
			type:"POST",
			url:ctx + "/comp/sign/test/custsign/signCustDel",
			dataType:"json",
			data:{
				delDataStr:delDataStr
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
