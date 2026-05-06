$(document).ready(function(){
	
	$("#cancelBtn").click(function(){
		cancle();
	});
	
	$("#updateBtn").click(function(){
		update();
	});
	
});

function setVal(Data){
	var COMP_NO = Data.split(",")[0];
	var COMP_NAME = Data.split(",")[1];
	var backS = [{label:COMP_NAME,value:COMP_NO}];
	$("select[name='modl']").multiselect('dataprovider', backS).multiselect('disable');
	
	var SVC_CODE = Data.split(",")[2];
	var SVC_DESC = Data.split(",")[3];
	backS = [{label:SVC_DESC,value:SVC_CODE}];
	$("select[name='svcCode']").multiselect('dataprovider', backS).multiselect('disable');
	
	var SUB_SVC_CODE = Data.split(",")[4];
	var SUB_SVC_DESC = Data.split(",")[5];
	$("#subSvcCode").val(SUB_SVC_CODE);
	$("#subSvcName").val(SUB_SVC_DESC);
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
		var modlNo=getS('modl');
		var svcCode=getS('svcCode');
		var subSvcCode=$("#subSvcCode").val();
		var subSvcName=$("#subSvcName").val();
		$.ajax({
			type:"POST",
			url:ctx+"/comp/ctrl/oper/subsvccode/CtrlSubSvcMod",
			dataType:"json",
			data:{
				COMP_NO:modlNo,
				SVC_CODE:svcCode,
				SUB_SVC_CODE:subSvcCode,
				SUB_SVC_DESC:subSvcName
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