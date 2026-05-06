$(document).ready(function(){
	
	$("#cancelBtn").click(function(){
		cancle();
	});
	
	//点击提交按钮 
	$("#updateBtn").click(function(){
		update();
	});
	/*startJudge('updateBtn');
	cOpt('svcName');
	endJudge(update);*/
});

function setVal(Data){
	console.info(Data);
	var COMP_NO = Data.split(",")[0];
	var COMP_NAME = Data.split(",")[1];
	var SVC_CODE = Data.split(",")[2];
	var SVC_NAME = Data.split(",")[3];
	var backS = [{label:COMP_NAME,value:COMP_NO}];
	$("select[name='COMP_NO']").multiselect('dataprovider', backS).multiselect('disable');
	$("#svcCode").val(SVC_CODE);
	$("#svcName").val(SVC_NAME);
}

function cancle(){
	Ewin.confirm({
		title : "操作提示",
		message : "数据会清空，确定返回吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		//返回查询页面(不重新加载)
		parent.tab1("N");
		parent.changeName("add");
	});
}

function update(){
	if(proof()){
		var modlNo=getS("COMP_NO");
		var svcCode=getI("svcCode");
		var svcName=getI("svcName");
		console.info(modlNo+svcCode+svcName);
		$.ajax({
			type:"POST",
			url:ctx+"/comp/ctrl/oper/svccode/CtrlSvcMod",
			dataType:"json",
			data:{
				COMP_NO:modlNo,
				SVC_CODE:svcCode,
				SVC_DESC:svcName
			},
			success:function(data){
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var errMsg = "错误信息["+data.message+"]";
					showTip(errMsg,"error");
				}else{
					showTip("修改成功","success");
					//返回查询页面(重新加载)
					parent.tab1("Y");
					parent.changeName("add");
				}
			}
		});
	}
}