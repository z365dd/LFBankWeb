console.log('cardTypeForm.js');

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
				CARD_BIN_NO:getI('CARD_BIN_NO'),
				CARD_TP:getS('CARD_TP'),
				ACCT_CARD_FLG:getS('ACCT_CARD_FLG'),
				NET_NO:getI('NET_NO'),
				LEGA_NO:getS('LEGA_NO'),
		} ;
		
		if(SAVE_OR_REV=="add"){
			sendData.OPER_TP = ADD;
		}else if(SAVE_OR_REV=="revice"){
			sendData.OPER_TP = REV;
		}
		$.ajax({
			url:ctx+"/comp/ctrl/oper/cardType/action", 
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
function setData(CARD_BIN_NO,CARD_TP,ACCT_CARD_FLG,NET_NO,LEGA_NO){
	setI('CARD_BIN_NO',CARD_BIN_NO);
	setS('CARD_TP',CARD_TP);
	setS('ACCT_CARD_FLG',ACCT_CARD_FLG);
	setI('NET_NO',NET_NO);
	setS('LEGA_NO',LEGA_NO);
	disabledI("CARD_BIN_NO");
}



