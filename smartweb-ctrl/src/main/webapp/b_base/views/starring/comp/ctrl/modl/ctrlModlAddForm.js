$(function(){
	
	/*点击取消按钮 不保存页面数据并 返回主页面*/
	$("#cancelBtn").click(function(){
		cancle();
	});

	/*新增按钮*/
	$("#addBtn").click(function(){
		ctrlModlAdd();
	});
	/*startJudge('addBtn');
	cOpt('modlNo');
	cOpt('modlName');
	cOpt('modlType');
	endJudge(ctrlModlAdd);*/
	
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

function ctrlModlAdd(){
//	var formData = $("#CtrlModlAddForm").serializeObject();
	if(proof()){
		$.ajax({
			type:"POST",
			url:ctx + "/comp/ctrl/oper/CtrlCompAdd",
			dataType:"json",
			data:{
				COMP_NO:$("#modlNo").val(),
				COMP_NAME:$("#modlName").val(),
				FLG:$('#modlType option:selected').val()
			},
			success:function(data){
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var errMsg = "错误信息["+data.message+"]";
					showTip(errMsg,"error");
				}else{
					showTip("提交成功","success");
					//返回查询页面(重新加载)
					parent.tab1("Y");
//					parent.window.$("a[href^='#tab_list']").click();
				}
			}
		});
	}
}
	
