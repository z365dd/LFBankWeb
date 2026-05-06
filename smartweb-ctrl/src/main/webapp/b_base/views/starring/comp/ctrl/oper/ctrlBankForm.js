console.log('ctrlBankForm.js');

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
				BANK:getI('BANK'),
				BANK_NAME:getI('BANK_NAME'),
				APP_NAME:getI('APP_NAME'),
		} ;
		
		if(SAVE_OR_REV=="add"){
			sendData.OPER_TP = ADD;
		}else if(SAVE_OR_REV=="revice"){
			sendData.OPER_TP = REV;
		}
		$.ajax({
			url:ctx+"/comp/ctrl/oper/ctrlBank/action", 
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
function setData(BANK_NAME,BANK,APP_NAME){
	setI('BANK_NAME',BANK_NAME);
	setI('BANK',BANK);
	setI('APP_NAME',APP_NAME);
	disabledI("BANK");
	disabledI("BANK_NAME");
}



