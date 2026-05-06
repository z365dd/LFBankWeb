console.log('paraEntrForm.js');

/*
 * 父页面变量$A_M_D
 * A新增，M修改 ，D详细
 */
var $A_M_D = parent.$A_M_D;
var $ModData = null;

$(document).ready(function(){
	
//	setSelect1("LEGA_NO","/comp/sign/pub/getLegaNo","LEGA_NO","LEGA_NAME",false,true);
	if($A_M_D =="A"){
		getLegaNo("LEGA_NO");
	}else{
		getLegaNo1("LEGA_NO");
	}
	
	if($A_M_D != "D"){
		//新增和修改页面:开通状态没有删除状态
		$("#OPEN_STAT option[value='3']").remove();
		$("#OPEN_STAT").multiselect('rebuild').multiselect('refresh');
	}
	
	/*单位编码生成*/
	$("#crtBtn").click(function(){
		crtEntrNo();
	});
	
	/*证件类型改变时,证件号码检验*/
	$("#CERT_TP").change(function(){
		if(getS("CERT_TP")=="P003" || getS("CERT_TP")=="P004" || getS("CERT_TP")=="P005"){
			$("#CERT_NO").attr("check-idcard","false").attr("maxlength",60);
		}else{
			$("#CERT_NO").attr("check-idcard","true");
		}
	});
	
	/*返回按钮*/
	$('#closeBtn').on('click',close);
	
	/*提交按钮*/
	$('#submitBtn').on('click',submit);

});

function checkCERT_TP(){
	if(getS("CERT_TP")=="P003" || getS("CERT_TP")=="P004" || getS("CERT_TP")=="P005"){
		$("#CERT_NO").attr("check-idcard","false").attr("maxlength",60);
	}else{
		$("#CERT_NO").attr("check-idcard","true");
	}
}

function crtEntrNo(){
	var legaNo = getS("LEGA_NO");
	if(legaNo == ""){
		showTip("法人号不能为空!", "error");
		return;
	}
	$.ajax({
		url: ctx + "/comp/sign/pub/getBusiNo",
		type:"POST",
		dataType:"json",
		data:{
			LEGA_NO:legaNo
		}, 
		async:true,
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showTip(errMsg, "error");
			} else {
				console.log(data.message);
				showTip("单位编号生成成功", "success");
				setI("ENTR_NO", data.dataSetResult[0].data[0].ENTR_NO);
				$("#crtBtn").attr("disabled", "disabled");
			}
		}
	});
}

/*关闭执行*/
function close(){
	parent.goTop();
	if($A_M_D == "D"){
		parent.tab1();
	}else{
		Ewin.confirm({
			title : "操作提示",
			message : "数据会清空，确定关闭吗？"
		}).on(function(e) {
			if (!e) {
				return;
			}
			parent.tab1();
		});
	}
}

/*提交执行*/
function submit(){
	var $url = ctx ;
	if($A_M_D == "A"){
		$url += "/comp/sign/tec/signParaEntr/add";
	}else if($A_M_D == "M"){
		$url += "/comp/sign/tec/signParaEntr/mod";
	}
	if(proof()){
		$.ajax({
			url:$url, 
			type:"POST",
			dataType:"json",
			data:{
				LEGA_NO:getS("LEGA_NO"),
				ENTR_NO:getI("ENTR_NO"),
				ENTR_NAME:getI("ENTR_NAME"),
				PRT_NAME:getI("PRT_NAME"),
				CERT_TP:getS("CERT_TP"),
				CERT_NO:getI("CERT_NO"),
				OPEN_STAT:getS("OPEN_STAT"),
				CTCT_PER_NAME:getI("CTCT_PER_NAME"),
				PER_TEL_NO:getI("PER_TEL_NO"),
				ENTR_TEL_NO:getI("ENTR_TEL_NO"),
				POST_ECD:getI("POST_ECD"),
				COMM_ADDR:getI("COMM_ADDR"),
				EMAIL_ADDR:getI("EMAIL_ADDR"),
				BANK_CUST_NO:getI("BANK_CUST_NO")
				
			}, 
			async:true,
			success:function(data) {
				if (data.returnCode !== undefined
						&& "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					var successMsg = "提交成功"; 
					showContent(successMsg,"success");
					if($A_M_D == "M"){
						parent.tab1("M");
					}else{
						parent.tab1();
					}
				}
			}
		});
	}
}


/*设置值*/
function setVal(DataStr){
	var Data = JSON.parse(decodeURIComponent(DataStr.replace(/\+/g, '%20')));
	if($A_M_D == "M"){
		$ModData = Data;
	}else if($A_M_D == "D"){
		
		$("#infoDiv").find("select").multiselect("disable");
		$("#infoDiv").find("input").prop("disabled", true);
		$("#submitBtn").hide();
	}
	
	//生成按钮不可用
	$("#crtBtn").attr("style", "display: none");
	$("#LEGA_NO").multiselect("disable");
	setS("LEGA_NO",Data.LEGA_NO);
	setI("ENTR_NO",Data.ENTR_NO);
	setI("ENTR_NAME",Data.ENTR_NAME);
	setI("PRT_NAME",Data.PRT_NAME);
	setS("CERT_TP",Data.CERT_TP);
	setI("CERT_NO",Data.CERT_NO);
	checkCERT_TP();
	setS("OPEN_STAT",Data.OPEN_STAT);
	
	var CTCT_PER_NAME = Data.CTCT_PER_NAME;
	if(CTCT_PER_NAME == '"null"'){
		CTCT_PER_NAME = "";
	}
	setI("CTCT_PER_NAME",CTCT_PER_NAME);
	
	var PER_TEL_NO = Data.PER_TEL_NO
	if(Data.PER_TEL_NO == '"null"'){
		PER_TEL_NO = "";
	}
	setI("PER_TEL_NO",PER_TEL_NO);
	
	var ENTR_TEL_NO = Data.ENTR_TEL_NO;
	if(ENTR_TEL_NO == '"null"'){
		ENTR_TEL_NO = "";
	}
	setI("ENTR_TEL_NO",ENTR_TEL_NO);
	
	var COMM_ADDR = Data.COMM_ADDR;
	if(COMM_ADDR == '"null"'){
		COMM_ADDR = "";
	}
	setI("COMM_ADDR",COMM_ADDR);
	
	var POST_ECD = Data.POST_ECD;
	if(POST_ECD == '"null"'){
		POST_ECD = "";
	}
	setI("POST_ECD",POST_ECD);
	
	var EMAIL_ADDR = Data.EMAIL_ADDR;
	if(EMAIL_ADDR == '"null"'){
		EMAIL_ADDR = "";
	}
	setI("EMAIL_ADDR",EMAIL_ADDR);
	
	var BANK_CUST_NO = Data.BANK_CUST_NO;
	if(BANK_CUST_NO == '"null"'){
		BANK_CUST_NO = "";
	}
	setI("BANK_CUST_NO",BANK_CUST_NO);
}


