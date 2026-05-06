$(function(){
	//获取流水号id下拉框
	setSelect1("SEQ_CRT_ID", "/comp/flow/oper/flowNumBuild/qry", "SEQ_CRT_ID", "SEQ_CRT_NAME",null,true);
	
	/*点击取消按钮 不保存页面数据并 返回主页面*/
	$("#cancelBtn").click(function(){
		cancle();
	});
	
	/*点击确认按钮 保存页面录入的数据并返回主页面*/
	$("#addBtn").click(function(){
		chnlAdd();
	});
	/*startJudge('addBtn');
	cOpt('CHNL_NO');
	cOpt('CHNL_NAME');
	endJudge(chnlAdd);*/
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

function chnlAdd(){
	if(proof()){
		var CHNL_NO = $("#CHNL_NO").val();
		var CHNL_NAME = $("#CHNL_NAME").val();
		var CHNL_TP = getS('CHNL_TP');
		$.ajax({
			url:ctx+"/comp/ctrl/oper/channel/TParaChnlAdd",
			type:"POST",
			dataType:"json",
			data:{
				CHNL_NO:CHNL_NO,
				CHNL_NAME:CHNL_NAME,
				CHNL_TP:CHNL_TP,
				SEQ_CRT_ID:getS("SEQ_CRT_ID")
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

