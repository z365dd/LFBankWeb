$(document).ready(function(){
	//获取流水号id下拉框
	setSelect1("SEQ_CRT_ID", "/comp/flow/oper/flowNumBuild/qry", "SEQ_CRT_ID", "SEQ_CRT_NAME",null,false);
	
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

function setVal(Data){
	var CHNL_NO = Data.split(',')[0];
	var CHNL_NAME = Data.split(',')[1];
	var CHNL_TP = Data.split(',')[2];
	var SEQ_CRT_ID = Data.split(',')[3];
	setI('CHNL_NO',CHNL_NO);
	setI('CHNL_NAME',CHNL_NAME);
	setS('CHNL_TP',CHNL_TP);
	setS("SEQ_CRT_ID",SEQ_CRT_ID);
}

function update(){
	if(proof()){
		var CHNL_NO = $("#CHNL_NO").val();
		var CHNL_NAME = $("#CHNL_NAME").val();
		var CHNL_TP = getS("CHNL_TP");
		console.info(CHNL_NO+CHNL_NAME);
		$.ajax({
			type:"POST",
			url:ctx+"/comp/ctrl/oper/channel/TParaChnlMod",
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
					showTip("修改成功","success");
					//返回查询页面(重新加载)
					parent.tab1("Y");
					parent.changeName("add");
				}
			}
		});
	}
}
