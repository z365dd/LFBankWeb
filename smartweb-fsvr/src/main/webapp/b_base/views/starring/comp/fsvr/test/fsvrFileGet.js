console.log('fsvrFileGet');

//数组fileArr 用于存储后台保存的文件路径
//每新增一行给数组加空字符串，上传成功返回替换成文件路径
//移除文件把数组对应元素替换为空字符串
/*var fileArr=[];*/


$(function(){
	/*关闭按钮*/
	$('#cancelBtn').on('click',cancel);	
	
	//提交
	$('#addBtn').on('click',downLoadGetSend);
	/*加载服务器标识符下拉框的数据*/
	setSelect3("FILE_SVR_ID","/comp/fsvr/tec/tfsvrSvrPara/getList","fileSvrId","svrDesc",false,false);
	//删除所在文件div
	$('button[name="delBtn"]').click(function() {
		$(this).parents('div[name="fileDivName"]').eq(0).remove();
	}); 
	//增加文件
	$('#addFile').click(function(){
		var newFile = $('#commonFile').children().clone(true);
		//初始化下拉框
		newFile.find('select').multiselect('rebuild');
		
		$('#fileDiv').append(newFile);
		//文件路径数组增加元素
		/*fileArr.push("");*/
	});
	$('#addFile').click();
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
function downLoadGetSend(){
	if(proof()){
		//文件信息形成json数组
		var fileObjArr = [];
		for(var a=0;a<$('[name="FILE_NAME"]').length-1;a++){
			/*fileObjArr[a] = new fileObj(fileArr[a],$('[name="ALIAS_FILE_NAME"]').eq(a+1).val(),$('[name="SUB_FILE_PATH"]').eq(a+1).val());*/
			fileObjArr[a] = new fileObj($('[name="FILE_NAME"]').eq(a+1).val(),$('[name="ALIAS_FILE_NAME"]').eq(a+1).val(),$('[name="SUB_FILE_PATH"]').eq(a+1).val());
		}
		$.get(ctx+"/comp/fsvr/test/fileGetSend",{
			FILE_TRANS_TP:getS("FILE_TRANS_TP"),
			FILE_SVR_ID:getS("FILE_SVR_ID"),
			PUB_FILE_PATH:getI('PUB_FILE_PATH'),
			FILE_NUM:fileObjArr.length,
			FILE_STR:JSON.stringify(fileObjArr)
		},function(data){ 
			/*if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{	
				console.log(data.message);
				var successMsg = "文件获取并下载["+data.message+"]"; 
				showContent(successMsg,"success");
				parent.tab1();
				 var dataVal = data.dataSetResult[0].data[0];
				 //设置文件流水号
				 setS("FILE_SET_SEQ",dataVal.FILE_SET_SEQ);
			}*/
			
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "文件获取["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{	
				 var dataVal = data.dataSetResult[0].data[0];
				 setI("REQ_SEQ",dataVal.REQ_SEQ);
				 disabledI("REQ_SEQ");				
				 //设置文件请求流水号
				 setI("FILE_SET_SEQ",dataVal.FILE_SET_SEQ);
				 disabledI("FILE_SET_SEQ");
				 
				 //判断成功失败
				 if("F"==dataVal.TRAN_STAT || "f"==dataVal.TRAN_STAT){
					 //失败
					 var errorMsg = dataVal.TRAN_MSG; 
					showContent(errorMsg,"error");
				 }else{
					var successMsg = "文件获取成功"; 
					showContent(successMsg,"success");
				 }
			}
			
		},"json");
	}
	
}



