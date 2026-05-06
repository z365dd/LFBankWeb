console.log('fsvrFileUpload.js');

//数组fileArr 用于存储后台保存的文件路径
//每新增一行给数组加空字符串，上传成功返回替换成文件路径
//移除文件把数组对应元素替换为空字符串
var fileArr=[];


$(function(){
	/*加载服务器标识符下拉框的数据*/
	setSelect3("FILE_SVR_ID","/comp/fsvr/tec/tfsvrSvrPara/getList","fileSvrId","svrDesc",false,false);
	/*关闭按钮*/
	$('#cancelBtn').on('click',cancel);	
	
	//提交
	$('#addBtn').on('click',upLoadPutSend);
	
	//删除所在文件div
	$('button[name="delBtn"]').click(function() {
		$(this).attr("id","del");
		var ser = 0;
		for(var a=0;a<fileArr.length;a++){
			if ($('button[name="delBtn"]').eq(a+1).attr("id") == "del") {
				ser = a;
				break;
			}
		}
		fileArr.splice(a,1);
		$(this).parents('div[name="fileDivName"]').eq(0).remove();
	}); 
	
	//增加文件
	$('#addFile').click(function(){
		var newFile = $('#commonFile').children().clone(true);
		//初始化文件上传插件
		resetFile(newFile);
		//初始化下拉框
		newFile.find('select').multiselect('rebuild');
		
		$('#fileDiv').append(newFile);
		//文件路径数组增加元素
		fileArr.push("");
	});
	$('#addFile').click();
	
});


//文件上传插件初始化
function resetFile(obj){
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
		   /* showTip(data.message,"success");*/
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
}


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
	for(var a=0;a<fileArr.length;a++){
		if(fileArr[a]==""){
			showTip("请先上传文件","error");	
			return;
		}
	}
	if(proof()){
		//文件信息形成json数组
		var fileObjArr = [];
		for(var a=0;a<fileArr.length;a++){
			fileObjArr[a] = new fileObj(fileArr[a],$('[name="ALIAS_FILE_NAME"]').eq(a+1).val(),$('[name="SUB_FILE_PATH"]').eq(a+1).val());
		}
		$.get(ctx+"/comp/fsvr/test/fileUpLoad",{
			FILE_TRANS_TP:getS("FILE_TRANS_TP"),
			PUB_FILE_PATH:getI('PUB_FILE_PATH'),
			FILE_NUM:fileObjArr.length,
			FILE_STR:JSON.stringify(fileObjArr)
		},function(data){ 
			/*console.log(data.message);
			var successMsg = "文件上传推送["+data.message+"]"; 
			showContent(successMsg,"success");*/
			/*parent.tab1();*/
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "文件上传查询["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{
				data = exchangeRes(data);
				console.log(data);
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
					var successMsg = "文件上传成功"; 
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


