console.log('downLoadGet');

//数组fileArr 用于存储后台保存的文件路径
//每新增一行给数组加空字符串，上传成功返回替换成文件路径
//移除文件把数组对应元素替换为空字符串
/*var fileArr=[];*/


$(function(){
	/*关闭按钮*/
	$('#cancelBtn').on('click',cancel);	
	
	//提交
	$('#addBtn').on('click',downLoadGetSend);
	
	//删除所在文件div
	$('button[name="delBtn"]').click(function() {
		$(this).parents('div[name="fileDivName"]').eq(0).remove();
	}); 
	
	/*加载服务器标识符下拉框的数据*/
	setSelect3("FILE_SVR_ID","/comp/fsvr/tec/tfsvrSvrPara/getList","fileSvrId","svrDesc",false,false);
	//增加对账文件
	$('#addFile').click(function(){
		var newFile = $('#commonFile').children().clone(true);
		/*//初始化文件上传插件
		resetFile(newFile);*/
		//初始化下拉框
		newFile.find('select').multiselect('rebuild');
		
		$('#fileDiv').append(newFile);
		//文件路径数组增加元素
		/*fileArr.push("");*/
	});
	$('#addFile').click();
	
	/*加载渠道下拉框的数据*/
	//setSelect1("CHNL_NO","/comp/ctrl/oper/channel/getChnl","CHNL_NO","CHNL_NAME",undefined,false);
});


//文件上传插件初始化
/*function resetFile(obj){
	// 业务新增文件上传初始化
	obj.find('#FILE_NAME').fileinput({
		showUpload: true,//是否显示上传按钮 
		language:'zh',//设置语言  
		uploadAsync:true,//默认异步上传  
		//dropZoneEnabled:true,//是否显示拖拽区域  
		uploadUrl:ctx+'/comp/fsvr/test/upload',//上传的地址  
		maxFileCount: 1,//表示允许同时上传的最大文件个数 
		//maxImageWidth: 600,//图片的最大宽度 
		resizeImage: true,
		showCaption: true,//是否显示标题  
		showPreview: false,//是否显示预览  
		browseClass: "btn btn-primary btn-lg",//按钮样式
		 allowedFileExtensions : ['txt'],//接收的文件后缀
		previewFileIcon: ""
		//dropZoneTitle:"拖拽文件到这里<br>仅支持单文件上传" //拖拽区标题
	});
	obj.find('#FILE_NAME').on("fileuploaded", function(event, data) {
		var data = data.response;
		if(data.returnCode!==undefined && "0000"!=data.returnCode){
			var errMsg = "错误信息["+data.message+"]"; 
			showTip(errMsg,"error");
			return;
		}else{
		    showTip(data.message,"success");
		    var dataVal = data.dataSetResult[0].data[0];
		  //文件上传成功把文件路径保存数组
		    fileArr[obj.index()] = dataVal.fileName+"."+dataVal.type;
		}
	}).on("fileremoved", function(id, ind){ //只是你删除重新选择的图片才会触发，而删除原图片不会触发。
		console.info("移出单个文件");
		$("#hidden_SRC_FILE_NAME").val("");
		//文件移除，把数组对应元素赋值为空
		fileArr[obj.index()] = "";
	}).on('filesuccessremove', function(event, id) { //图片上传成功后，点击删除按钮的回调函数
	    
	});
}*/


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
	/*for(var a=0;a<fileArr.length;a++){
		if(fileArr[a]==""){
			showTip("请先上传文件","error");	
			return;
		}
	}*/
	if(proof()){
		//文件信息形成json数组
		var fileObjArr = [];
		for(var a=0;a<$('[name="FILE_NAME"]').length-1;a++){
			/*fileObjArr[a] = new fileObj(fileArr[a],$('[name="ALIAS_FILE_NAME"]').eq(a+1).val(),$('[name="SUB_FILE_PATH"]').eq(a+1).val());*/
			fileObjArr[a] = new fileObj($('[name="FILE_NAME"]').eq(a+1).val(),$('[name="ALIAS_FILE_NAME"]').eq(a+1).val(),$('[name="SUB_FILE_PATH"]').eq(a+1).val());
		}
		$.get(ctx+"/comp/fsvr/test/downLoadGetSend",{
			//标志：1表示  （远程、维度）获取并下载（本地，头ip，组件号），ftp上传到  远程路径  给文件传输获取
			//    0表示  上传并推送，ftp上传到 本地路径  给文件传输拿到推送出去
			FLG:1,
			
		
			//TODO
			FILE_TRANS_TP:getS("FILE_TRANS_TP"),
			FILE_SVR_ID:getS("FILE_SVR_ID"),
		/*	companyName:getI("companyName"),
			ENTR_NO:getS("ENTR_NO"),
			CHNL_NO:getS("CHNL_NO"),
			LEGA_NO:getS("LEGA_NO"),
			TRAN_CODE:getI("TRAN_CODE"),
			DEF_VAL:getI("CHK_DEF_DIM_VAL"),
			DIM_FLG:((getI("companyName")=="")?("0"):("1"))
			+((getS("ENTR_NO")=="")?("0"):("1"))
					+((getS("CHNL_NO")=="")?("0"):("1"))
							+((getS("LEGA_NO")=="")?("0"):("1"))
									+((getI("TRAN_CODE")=="")?("0"):("1"))
											+((getI("CHK_DEF_DIM_VAL")=="")?("0"):("1")),*/
			LOCAL_FULL_FILE_PATH:getI('LOCAL_FULL_FILE_PATH'),
			RMT_FULL_FILE_PATH:getI('RMT_FULL_FILE_PATH'),
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
			//data = exchangeRes(data);
			
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "文件获取并下载["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{	
				 var dataVal = data.dataSetResult[0].data[0];
				 setI("REQ_SEQ",dataVal.rEQ_SEQ);
				 disabledI("REQ_SEQ");
				 //设置文件请求流水号
				 setI("FILE_SET_SEQ",dataVal.fILE_SET_SEQ);
				 disabledI("FILE_SET_SEQ");
				 //判断成功失败
				 if("F"==dataVal.tRAN_STAT || "f"==dataVal.tRAN_STAT){
					 //失败
					 var errorMsg = dataVal.tRAN_MSG; 
					showContent(errorMsg,"error");
				 }else{
					var successMsg = "文件获取并下载成功"; 
					showContent(successMsg,"success");
//					var $fileList = JSON.parse(decodeURIComponent(dataVal.LIST));
					var $fileList = dataVal.lIST;
					for(var i=0;i<$fileList.length;i++){
						$($("#fileDiv").find("a[name=downLoadBtnFile]")[i]).attr("style", "display:block");
						$($("#fileDiv").find("a[name=downLoadBtnFile]")[i]).attr("href",ctx+"/comp/fsvr/test/downLoad?file="+encodeURIComponent($fileList[i].fULL_FILE_PATH));
						
						/*var downloadUrl = ctx+"/comp/fsvr/test/downLoad?file="+encodeURIComponent($fileList[i].FULL_FILE_PATH);
						location.href = downloadUrl;*/
					}
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





