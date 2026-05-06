console.log('sdkUpload');

//数组fileArr 用于存储后台保存的文件路径
//每新增一行给数组加空字符串，上传成功返回替换成文件路径
//移除文件把数组对应元素替换为空字符串
var fileArr=[];


$(function(){
	/*关闭按钮*/
	$('#cancelBtn').on('click',cancel);	
	
	//提交
	$('#addBtn').on('click',submit);
	
	//增加对账文件
	$('#addFile').click(function(){
		addFile();
	});
	addFile();
	
	/*加载文件服务器下拉框的数据*/
	//setSelect1("SVR_NO","/comp/fsvr/tec/fileTec/qry?COMM_PROT_GRP_TP=SDK&start=0&pageSize=0","FILE_SVR_NO","SVR_DESC",undefined,false);
	
});

function addFile(){
	var newFile = $('#commonFile').children().clone(true);
	//初始化文件上传插件
	resetFile(newFile);
	//初始化下拉框
	newFile.find('select').multiselect('rebuild');
	
	$('#fileDiv').append(newFile);
	//文件路径数组增加元素
	fileArr.push("");
}

//文件上传插件初始化
function resetFile(obj){
	// 业务新增文件上传初始化
	obj.find('#FILE_NAME').fileinput({
		showUpload: true,//是否显示上传按钮 
		language:'zh',//设置语言  
		uploadAsync:true,//默认异步上传  
		//dropZoneEnabled:true,//是否显示拖拽区域  
		uploadUrl:ctx+'/comp/fsvr/testSdk/upload',//上传的地址  
		maxFileCount: 1,//表示允许同时上传的最大文件个数 
		//maxImageWidth: 600,//图片的最大宽度 
		resizeImage: true,
		showCaption: true,//是否显示标题  
		showPreview: false,//是否显示预览  
		browseClass: "btn btn-primary btn-lg",//按钮样式
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
function fileObj(FILE_NAME){
	//文件名
	this.FILE_NAME = FILE_NAME;
}

//提交
function submit(){
	for(var a=0;a<fileArr.length;a++){
		if(fileArr[a]==""){
			showTip("请先上传文件","error");	
			return;
		}
	}
	if(proof()){
		var $tranTp = getS("FILE_TRANS_TP");
		
		//文件信息形成json数组
		var fileObjArr = [];
		for(var a=0;a<fileArr.length;a++){
			fileObjArr[a] = new fileObj(fileArr[a]);
		}
		$.get(ctx+"/comp/fsvr/testSdk/upLoadFile",{
			FILE_TRANS_TP:$tranTp,
			SVR_NO:getS("SVR_NO"),
			FILE_STR:JSON.stringify(fileObjArr)
		},function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "文件上传失败["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{	
				 var dataVal = data.dataSetResult[0].data[0];
				 setI("REQ_SEQ",dataVal.seqNo);
				 disabledI("REQ_SEQ");
				 var successMsg = "文件上传提交成功"; 
				 showContent(successMsg,"success");
				 $("#addBtn").attr("disabled", true);
				 $("#progressbar").attr("style", "display:block");
				 
				 if($tranTp == "02"){
					 scheduleQry();
				 }else{
					 procSchedule = 100;
					 progress();
				 }
			}
			
		},"json");
	}
}

var procSchedule = 0;

//提交
function scheduleQry(){
	var $seqNo = getI("REQ_SEQ");
	if($seqNo != ""){
		//关闭屏幕层
		$.session.set('$HIDE_LOADING', 'true');
		$.get(ctx+"/comp/fsvr/testSdk/scheduleQry",{
			SEQ_NO:$seqNo
		},function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			}else{	
				 var dataVal = data.dataSetResult[0].data[0];
				 var cmpleSize = Number(dataVal.cmpleSize);
				 var totSize = Number(dataVal.totSize);
				 if(totSize != 0){
					 procSchedule = Math.floor((cmpleSize/totSize)*100);
				 }
			}
			//开启屏蔽层
			$.session.remove('$HIDE_LOADING');
			progress();
		},"json");
	}
}

function progress() {
	var progressbar = $("#progressbar");
	var progressLabel = $(".progress-label");

	progressbar.progressbar({
		change : function() {
			progressLabel.text(progressbar.progressbar( "value" ) + "%");
		},
		complete : function() {
			progressLabel.text("完成！");
		}
	});

	function progress() {
		var val = procSchedule || 0;

		progressbar.progressbar("value", val);

		if (val < 99) {
			setTimeout(scheduleQry, 100);
		}
	}

	setTimeout(progress, 2000);
	
}




