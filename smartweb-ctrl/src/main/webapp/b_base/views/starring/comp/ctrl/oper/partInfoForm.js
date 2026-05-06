console.log('partInfoForm.js');

/*
 * 父页面变量SAVE_OR_REV
 * add新增，revice修改，detail详细
 */
var SAVE_OR_REV = parent.SAVE_OR_REV;

//下拉框加载 同步or异步 参数
var IF_ASYNC=true;
if(SAVE_OR_REV !="add"){
	IF_ASYNC=false;
}


$(function(){
	/*加载模型下拉框的数据*/
	setCompNoS("COMP_NO",IF_ASYNC,true);
	
	//服务码
	setSvcCodeS("COMP_NO","SVC_CODE",IF_ASYNC);
	
	/*返回按钮*/
	$('#cancelBtn').on('click',cancel);	
	
	/*提交按钮*/
	$('#addBtn').on('click',sendData);	
	
});

/*关闭执行*/
function cancel(){
	parent.goTop();
	Ewin.confirm({
		title : "操作提示",
		message : "确定返回吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		parent.tab1();
	});
}


/*提交执行*/
function sendData(){
	if(portion("formId_286247")){
		var sendData = {
				COMP_NO:getS('COMP_NO'),
				COMP_NAME:getST('COMP_NO'),
				SVC_CODE:getS('SVC_CODE'),
				SVC_DESC:getST('SVC_CODE'),
		} ;
		
		if(SAVE_OR_REV=="add"){
			sendData.OPER_TP = ADD;
		}else if(SAVE_OR_REV=="revice"){
			sendData.OPER_TP = REV;
		}
		$.ajax({
			url:ctx+"/comp/ctrl/oper/partInfo/action", 
			type:"POST",
			dataType:"json",
			data:sendData, 
			async:true,
			success:function(data) {
				if (data.returnCode !== undefined
						&& "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					var successMsg = "提交["+data.message+"]"; 
					showContent(successMsg,"success");
					parent.tab1();
				}
			}
		});
	}
}


//设置值
function setData(compNo,svcCode){
	setS('COMP_NO',compNo);
	$("#COMP_NO").change();
	setS('SVC_CODE',svcCode);
	disabledS("COMP_NO");
}



