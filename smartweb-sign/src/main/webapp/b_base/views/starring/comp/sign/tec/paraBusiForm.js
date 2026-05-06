/*父页面变量$A_M_D
A-新增，M-修改，D-详细
从父页面获取*/
var $A_M_D  = parent.$A_M_D;
$(function(){
	
	/*返回按钮*/
	$('#retuBtn').on('click',cancel);	
	
	/*判断是新增还是修改*/
	if($A_M_D=="A"){
		$('#addBtn').on('click',add);
	}else if($A_M_D=="M"){
		$('#addBtn').on('click',mod);
	}
	
	if($A_M_D != "D"){
		//新增和修改页面:开通状态没有删除状态
		$("#OPEN_STAT option[value='3']").remove();
		$("#OPEN_STAT").multiselect('rebuild').multiselect('refresh');
	}
	
	/*组件号改变，重新生成新的业务编号*/
	$("#COMP_NO").change(function(){
		if(getS('COMP_NO')==""){
			$("#BUSI_NO").val("");
		}else{
			createBusiNo(getS('COMP_NO'),'BUSI_NO');
		}
	});
	
	if($A_M_D =="A"){
		getCompNo("COMP_NO");
		getLegaNo("LEGA_NO");
	}else{
		getCompNo1("COMP_NO");
		getLegaNo1("LEGA_NO");
	}
	
});

//返回操作
function cancel(){
	parent.goTop();
	if($A_M_D == "D"){
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

//业务新增
function add(){
	if(portion("chkEmpty")){
		var COMP_NO = getS("COMP_NO");
		var BUSI_NO = getI("BUSI_NO");
		var BUSI_NAME = getI("BUSI_NAME");
		var OPEN_STAT = getS("OPEN_STAT");
		var LEGA_NO = getS("LEGA_NO");
		var SIGN_FLG = getS("SIGN_FLG");
		var FLG = getS("FLG");
		$.ajax({
			type : "POST",
			url : ctx+"/comp/sign/tec/parabusi/paraBusiAdd",
			data : {
				COMP_NO:COMP_NO,
				BUSI_NO:BUSI_NO,
				BUSI_NAME:BUSI_NAME,
				OPEN_STAT:OPEN_STAT,
				LEGA_NO:LEGA_NO,
				SIGN_FLG:SIGN_FLG,
				FLG:FLG
			},
			dataType : "json",
			success : function(data) {
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var Msg = "错误信息["+data.message+"]";
					showContent(Msg,"error");
				}else{
					var Msg = data.message; 
				    showContent(Msg,"success");
				    parent.tab1();
				}
			 }
		});
	}
}

//详细页面 数据回显
function setDetailVal(Data){
	$('#BUSI_NO').val(Data.BUSI_NO).attr('readonly','true');
	$('#BUSI_NAME').val(Data.BUSI_NAME).attr('readonly','true');

	/*下拉框禁用*/
	disabledS('COMP_NO');
	setS('COMP_NO',Data.COMP_NO);
	
	disabledS('LEGA_NO');
	setS('LEGA_NO',Data.LEGA_NO);
	
	disabledS('OPEN_STAT');
	setS('OPEN_STAT',Data.OPEN_STAT);
	
	disabledS('SIGN_FLG');
	setS('SIGN_FLG',Data.SIGN_FLG);
	
	disabledS('FLG');
	setS('FLG',Data.FLG);
	/*隐藏提交按钮*/
	$("#addBtn").hide();
}

//修改页面 数据回显
function setModVal(Data){
	/*组件号设为只读*/
	disabledS('COMP_NO');
	$("#BUSI_NO,#BUSI_NAME").prop('disabled',true);
	setS('COMP_NO',Data.COMP_NO);
	
	/*设置数据回显*/
	setI('BUSI_NO',Data.BUSI_NO);
	setI('BUSI_NAME',Data.BUSI_NAME);
	setS('OPEN_STAT',Data.OPEN_STAT);
	setS('LEGA_NO',Data.LEGA_NO);
	setS('SIGN_FLG',Data.SIGN_FLG);
	setS('FLG',Data.FLG);
}

//业务修改
function mod(){
	if(portion("chkEmpty")){
		var COMP_NO = getS("COMP_NO");
		var BUSI_NO = getI("BUSI_NO");
		var BUSI_NAME = getI("BUSI_NAME");
		var OPEN_STAT = getS("OPEN_STAT");
		var LEGA_NO = getS("LEGA_NO");
		var SIGN_FLG = getS("SIGN_FLG");
		var FLG = getS("FLG");
		$.ajax({
			type : "POST",
			url : ctx+"/comp/sign/tec/parabusi/paraBusiMod",
			data : {
				COMP_NO:COMP_NO,
				BUSI_NO:BUSI_NO,
				BUSI_NAME:BUSI_NAME,
				OPEN_STAT:OPEN_STAT,
				LEGA_NO:LEGA_NO,
				SIGN_FLG:SIGN_FLG,
				FLG:FLG
			},
			dataType : "json",
			success : function(data) {
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var Msg = "错误信息["+data.message+"]";
					showContent(Msg,"error");
				}else{
					var Msg = data.message; 
					showContent(Msg,"success");
					parent.tab1("M");
				}
			}
		});
	}
}

/*根据组件号生成业务编号*/
function createBusiNo(COMP_NO,selectId){
	$.ajax({
		type : "POST",
		url : ctx+"/comp/sign/tec/parabusi/creatBusiNo",
		data : {
			COMP_NO:COMP_NO
		},
		dataType : "json",
		success : function(data) {
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var Msg = "错误信息["+data.message+"]";
				showContent(Msg,"error");
			}else{
				var Msg = data.message; 
//			    showContent(Msg,"success");
				var valData = data.dataSetResult[0].data[0];
				var busiNo = valData.BUSI_NO;
				$('#'+selectId).val(busiNo);
			}
		 }
	});
}