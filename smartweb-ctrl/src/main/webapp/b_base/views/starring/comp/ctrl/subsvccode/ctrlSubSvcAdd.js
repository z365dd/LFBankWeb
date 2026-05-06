$(function(){
	
	/*加载模型下拉框的数据*/
	setSelect1("modl","/comp/ctrl/oper/subsvccode/getModlNo","COMP_NO","COMP_NAME");
	
	/*模型下拉改变则重新加载服务码下拉框*/
	$('#modl').change(function(){
		svcCodeS('svcCode',getS('modl'));
	});
	
	/*点击取消按钮 不保存页面数据并 返回主页面*/
	$("#cancelBtn").click(function(){
		cancle();
	});
	
	$("#addBtn").click(function(){
		ctrlSubSvcAdd();
	});
	/*startJudge('addBtn');
	cOpt('modl');
	cOpt('svcCode');
	cOpt('subSvcCode');
	cOpt('subSvcName');
	endJudge(ctrlSubSvcAdd);*/
});

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

function ctrlSubSvcAdd(){
	if(proof()){
		var modlno = $('#modl option:selected').val();
		var svccode = $('#svcCode option:selected').val();
		var subSvcCode  = $("#subSvcCode").val();
		var subSvcName  = $("#subSvcName").val();
		$.ajax({
			url:ctx+"/comp/ctrl/oper/subsvccode/CtrlSubSvcAdd",
			type:"POST",
			dataType:"json",
			data:{
				COMP_NO:modlno,
				SVC_CODE:svccode,
				SUB_SVC_CODE:subSvcCode,
				SUB_SVC_DESC:subSvcName
			},
			success:function(data){
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var errMsg = "错误信息["+data.message+"]";
					showTip(errMsg,"error");
				}else{
					showTip("添加成功","success");
					//返回查询页面(重新加载)
					parent.tab1("Y");
				}
			}
		});
	}
}

function svcCodeS(Name,Val){
	var Data={
			COMP_NO:Val
		}
	setSelect2("svcCode","/comp/ctrl/oper/svccode/getSvcCode","SVC_CODE","SVC_DESC",Data,Val);
}