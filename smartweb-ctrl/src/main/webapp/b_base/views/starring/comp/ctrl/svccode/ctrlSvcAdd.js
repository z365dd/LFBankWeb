$(function(){
	
	/*加载模型下拉框的数据*/
	setSelect1("modl","/comp/ctrl/oper/svccode/getModlNo","COMP_NO","COMP_NAME");
	
	/*点击取消按钮 不保存页面数据并 返回主页面*/
	$("#cancelBtn").click(function(){
		cancle();
	});
	
	/*点击确认按钮 保存页面录入的数据并返回主页面*/
	$("#addBtn").click(function(){
		ctrlSvcAdd();
	});
	/*startJudge('addBtn');
	cOpt('modl');
	cOpt('svcCode');
	cOpt('svcName');
	endJudge(ctrlSvcAdd);*/
	
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

function ctrlSvcAdd(){
	if(proof()){
		var modlno = $('#modl option:selected').val();
		var svccode = $("#svcCode").val();
		var svcname = $("#svcName").val();
		$.ajax({
			url:ctx+"/comp/ctrl/oper/svccode/CtrlSvcAdd",
			type:"POST",
			dataType:"json",
			data:{
				COMP_NO:modlno,
				SVC_CODE:svccode,
				SVC_DESC:svcname
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

function checkAddForm(){
	var modlno = $('#modl option:selected').val();
	if(modlno==''){
		alert("请选择模型...");
		return false;
	}else if(checkNull("svcCode")){
		alert("请输入服务码...");
		return false;
	}else if(checkNull("scvName")){
		alert("请输入服务名称");
		return false;
	}else{
		return true;
	}
}
