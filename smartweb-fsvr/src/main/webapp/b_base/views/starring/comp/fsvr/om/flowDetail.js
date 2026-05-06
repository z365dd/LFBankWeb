console.log('flowDetail.js');
$(function() {
	/*加载模型下拉框的数据*/
	setSelect1("COMP_NO","/comp/fsvr/om/qryCount/getCompNo","compNo","compName",null,false);
	
	disDiv('formId_175337');
	
	// 关闭
	$('#closeBtn').click(function() {
		parent.tab2(false);
	});
});


//打开tab3页面完成从父页面获取值
function getVal(tranDate){
	$.ajax({
		url : ctx + "/comp/fsvr/om/qryCount/getFlowDetail",
		type : "POST",
		dataType : "json",
		data : {
			TRAN_SEQ : tranDate
		},
		async : false,
		success : function(data, type) {
			if (data.returnCode !== undefined && "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showTip(errMsg, "error");
			} else {
				console.log(data.message);
				var successMsg = "流水详细查询["+data.message+"]"; 
				showContent(successMsg,"success");
				var dataVal = data.dataSetResult[0].data[0];
				dataVal = JSON.parse(dataVal.TFmngMngJrnlDtlQryRes);
				//设置值
				setVal(dataVal);
			}
		}
	});
}

//设置值
function setVal(Data){
	setI('TRAN_SEQ',Data.TRAN_SEQ);
	setI('REQ_NODE_NO',Data.REQ_NODE_NO);
	setI('TRAN_CODE',Data.TRAN_CODE);
	
	setS('FILE_TRANS_TP',Data.FILE_TRANS_TP);
	setS('COMP_NO',Data.COMP_NO);
	setI('BUSI_NO',Data.BUSI_NO);
	
	setI('TRAN_DATE',dateAdd(Data.TRAN_DATE));
	setI('TRAN_TIME',timeAdd(Data.TRAN_TIME));
	setI('BRCH_NO',Data.BRCH);
	setI('TLR_NO',Data.TLR_NO);
	
	setI('RET_CODE',Data.RET_CODE);
	setI('RET_MSG',Data.RET_MSG);
	setI('FILE_NUM',Data.FILE_NUM);
	
	var dataList = Data.LIST;
	if(dataList!=undefined){
		$('#table').bootstrapTable('load', dataList);
	}
	disDiv("formId_175337");
}


