console.log('fsvrFileSearch.js');

$(function(){
	/*关闭按钮*/
	$('#cancelBtn').on('click',cancel);	
	
	//提交
	$('#addBtn').on('click',upLoadPutSend);
	/*加载服务器标识符下拉框的数据*/
	setSelect3("FILE_SVR_ID","/comp/fsvr/tec/tfsvrSvrPara/getList","fileSvrId","svrDesc",false,false);
	
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
function upLoadPutSend(){
	if(proof()){
		$.get(ctx+"/comp/fsvr/test/fileSearch",{
			SEARCH_TP:getS("SEARCH_TP"),
			FILE_SVR_ID :getS("FILE_SVR_ID"),
			FILE_PATH:getI('FILE_PATH'),
			EXPR:getI('EXPR'),
		},function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "文件搜索["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{
				console.log(data);
				 var dataVal = data.dataSetResult[0].data[0];
				 
				 //判断成功失败
				 if("F"==dataVal.TRAN_STAT || "f"==dataVal.TRAN_STAT){
					 //失败
					 var errorMsg = dataVal.TRAN_MSG; 
					showContent(errorMsg,"error");
				 }else{
					var successMsg = "文件搜索成功"; 
					var length = data.dataSetResult[0].totalCount;
					var searchData = "";
					for( var i=0;i<length;i++ ) {
						var tmpData = data.dataSetResult[0].data[i].FILE_NAME;
						searchData = searchData + tmpData + "\n";
					}
					
					$('#FILE_NAME').val(searchData);
					showContent(successMsg,"success");
				 }
			}
			 
				 
			
		},"json");
	}
	
}

