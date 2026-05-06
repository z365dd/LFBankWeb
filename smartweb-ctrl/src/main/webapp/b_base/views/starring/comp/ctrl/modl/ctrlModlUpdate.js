$(document).ready(function(){
	
	$("#cancelBtn").click(function(){
		cancle();
	});
	$("#updateBtn").click(function(){
		update();
	});
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

function update(){
	var modlNo=$("#modlNo").val();
	var modlName=$("#modlName").val();
	var modlType=$("#modlType_sel option:selected").val();
	console.info(modlNo+modlName+modlType);
	$.ajax({
		type:"POST",
		url:ctx+"/comp/ctrl/oper/CtrlCompMod",
		dataType:"json",
		data:{
			COMP_NO:modlNo,
			COMP_NAME:modlName,
			FLG:modlType
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