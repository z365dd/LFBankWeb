/**/
var COMP_NO;
var SVC_CODE;
var SUB_SVC_CODE;
var PUB_DIM_LIST;
var PRI_DIM_LIST;
var TRL_LIST;
var TRAN_AMT;

$(function(){
	console.info("另存为案例");
	
	/*点击保存按钮*/
	$("#saveNewBtn").click(function(){
		console.info("另存为案例.....");
		saveNewBtnFun();
	});
	
	/*点击取消按钮 不保存页面数据并 返回主页面*/
	$("#quitBtn").click(function(){
		cancle();
	});
});

function setSaveVal(Data){
	if(Data==undefined){
		
	}else{
		DataArr = Data.split("##");
		COMP_NO = DataArr[0];
		SVC_CODE = DataArr[1];
		SUB_SVC_CODE = DataArr[2];
		PUB_DIM_LIST = DataArr[3];
		PRI_DIM_LIST = DataArr[4];
		TRL_LIST = DataArr[5];
		TRAN_AMT = DataArr[6];
	}
	console.info(COMP_NO);
}

function saveNewBtnFun(){
	var caseName = $("#caseName").val();
	if(caseName==""){
		alert("请输入案例名...");
		return ;
	}
	
	$.ajax({
		type:"POST",
		url:ctx + "/comp/ctrl/test/tranctrlchk/saveNewExm",
		dataType:"json",
		data:{
			caseName:caseName,
			COMP_NO:COMP_NO,
			SVC_CODE:SVC_CODE,
			SUB_SVC_CODE:SUB_SVC_CODE,
			PUB_DIM_LIST:PUB_DIM_LIST,
			PRI_DIM_LIST:PRI_DIM_LIST,
			TRL_LIST:TRL_LIST,
			TRAN_AMT:TRAN_AMT
			},
		success:function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]";
				showTip(errMsg,"error");
			}else{
				showTip("保存成功","success");
				console.info("保存成功");
				parent.tab11();
			}
		}
	});
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}
