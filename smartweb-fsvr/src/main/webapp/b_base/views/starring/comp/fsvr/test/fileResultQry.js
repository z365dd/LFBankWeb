console.log('fileResultQry');


$(function(){
	/*关闭按钮*/
	$('#cancelBtn').on('click',cancel);	
	
	//提交
	$('#addBtn').on('click',fileResultQrySend);

	//业务编号
//	$("#companyName").click(function(){
//		busiClick("companyName",undefined,"ENTR_NO");
//	});
	
});




/*关闭执行*/
function cancel(){
	/*parent.goTop();*/
	Ewin.confirm({
		title : "操作提示",
		message : "数据会清空，确定关闭吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		top.$.fn.jerichoTab.closeCurrentTab(); 
	});
}

//文件对象构造函数
function fileObj(FILE_NAME,ALIAS_FILE_NAME,SUB_FILE_PATH){
	//文件名
	this.FILE_NAME = FILE_NAME;
	//文件别名
	this.ALIAS_FILE_NAME = ALIAS_FILE_NAME;
	//文件子目录
	this.SUB_FILE_PATH = SUB_FILE_PATH;
}

//提交
function fileResultQrySend(){
	if(proof()){
		$.get(ctx+"/comp/fsvr/test/fileResultQrySend",{
			//TODO
			SEQ_NO:getI("FILE_SET_SEQ")
		},function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "传输结果查询["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{	
				console.log(data.message);
				var successMsg = "传输结果查询["+data.message+"]"; 
				showContent(successMsg,"success");
				/*parent.tab1();*/
				
				
				 var dataVal = data.dataSetResult[0].data[0];
				 Data = JSON.parse(dataVal.TfmngTranResultQueryRes);
				 //设置值
				 setVal(Data);
				 
				 
			}
		},"json");
	}
	
}


//设置值
function setVal(Data){
//	setS("FILE_TRAN_STAT",Data.FILE_TRAN_STAT);
	$('#FILE_TRAN_STAT').multiselect("select", Data.FILE_TRAN_STAT).multiselect('rebuild');
	$('#FILE_TRAN_STAT').multiselect("disable");
	setI("SRC_PUB_FILE_PATH",Data.SRC_PUB_FILE_PATH);
	setI("RET_MSG",Data.RET_MSG);
	if (Data.LIST != undefined && Data.LIST.length > 0) {
		$('#table').bootstrapTable('load', Data.LIST);
	}
}




