console.log('fsvrFilePut.js');

//数组fileArr 用于存储后台保存的文件路径
//每新增一行给数组加空字符串，上传成功返回替换成文件路径
//移除文件把数组对应元素替换为空字符串
var fileArr=[];


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

//提交
function upLoadPutSend(){
	if(proof()){
		$.get(ctx+"/comp/fsvr/test/filePush",{
			FILE_TRANS_TP:getS("FILE_TRANS_TP"),
			FILE_SVR_ID:getS("FILE_SVR_ID"),
			PUB_FILE_PATH:getI('PUB_FILE_PATH'),
			//文件请求流水号
			FILE_SET_SEQ:getI("FILE_SET_SEQ")
		},function(data){ 
			/*console.log(data.message);
			var successMsg = "文件上传推送["+data.message+"]"; 
			showContent(successMsg,"success");*/
			/*parent.tab1();*/
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "文件推送["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{
				data = exchangeRes(data);
				console.log(data);
				 var dataVal = data.dataSetResult[0].data[0];
				 setI("REQ_SEQ",dataVal.REQ_SEQ);
				 disabledI("REQ_SEQ");
				 
				 //判断成功失败
				 if("F"==dataVal.TRAN_STAT || "f"==dataVal.TRAN_STAT){
					 //失败
					 var errorMsg = dataVal.TRAN_MSG; 
					showContent(errorMsg,"error");
				 }else{
					var successMsg = "文件推送成功"; 
					showContent(successMsg,"success");
				 }
			}
			
		},"json");
	}
	
}

function exchangeRes(data){
	var str = JSON.stringify(data)
	
	var reg=/\\/g;
	str = str.replace(reg,"")
	str = str.replace("\"{\"","{\"");
	str = str.replace("}\"","}");
	var json =  JSON.parse(str);
	return json;
}


