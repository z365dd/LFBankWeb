console.log('sdkDownload');

//数组fileArr 用于存储后台保存的文件路径
//每新增一行给数组加空字符串，上传成功返回替换成文件路径
//移除文件把数组对应元素替换为空字符串
var fileArr=[];


$(function(){
	/*关闭按钮*/
	$('#cancelBtn').on('click',cancel);	
	
	//提交
	$('#addBtn').on('click',submit);
	
	$('#addFile').click(function(){
		addFile();
	});
	addFile();
	
	/*加载文件服务器下拉框的数据*/
	//setSelect1("SVR_NO","/comp/fsvr/tec/fileTec/qry?COMM_PROT_GRP_TP=SDK&start=0&pageSize=0","FILE_SVR_NO","SVR_DESC",undefined,false);
});


function addFile(){
	var newFile = $('#commonFile').children().clone(true);
	/*//初始化文件上传插件
	resetFile(newFile);*/
	//初始化下拉框
	newFile.find('select').multiselect('rebuild');
	
	$('#fileDiv').append(newFile);
	//文件路径数组增加元素
	/*fileArr.push("");*/
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
function fileObj(FILE_NAME, SUB_FILE_PATH){
	//文件名
	this.FILE_NAME = FILE_NAME;
	//文件子目录
	//this.SUB_FILE_PATH = SUB_FILE_PATH;
}

//提交
function submit(){
	if(proof()){
		var $tranTp = getS("FILE_TRANS_TP");
		//文件信息形成json数组
		var fileObjArr = [];
		for(var a=0;a<$('[name="FILE_NAME"]').length-1;a++){
			fileObjArr[a] = new fileObj($('[name="FILE_NAME"]').eq(a+1).val());
		}
		$.get(ctx+"/comp/fsvr/testSdk/downLoadFile",{
			FILE_TRANS_TP:$tranTp,
			SVR_NO:getS("SVR_NO"),
			FILE_STR:JSON.stringify(fileObjArr)
		},function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "文件下载失败["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{	
				 var dataVal = data.dataSetResult[0].data[0];
				 setI("REQ_SEQ",dataVal.seqNo);
				 disabledI("REQ_SEQ");
				 var successMsg = "文件下载提交成功"; 
				 showContent(successMsg,"success");
				 $("#addBtn").attr("disabled", true);
				 
				 if($tranTp == "02"){
					 $("#progressbar").attr("style", "display:block");
					 scheduleQry();
				 }else{
					 $($("#fileDiv").find("a[id=downLoadBtnFile]")[0]).attr("style", "display:block");
				 }
				 $($("#fileDiv").find("a[id=downLoadBtnFile]")[0]).attr("href",ctx+"/comp/fsvr/testSdk/downLoad?file="+encodeURIComponent(dataVal.fileName));
				 
				 /*var $fileList = JSON.parse(decodeURIComponent(dataVal.FILE_LIST));
				 for(var i=0;i<$fileList.length;i++){
					 $($("#fileDiv").find("a[id=downLoadBtnFile]")[i]).attr("style", "display:block");
					 $($("#fileDiv").find("a[id=downLoadBtnFile]")[i]).attr("href",ctx+"/comp/fsvr/testSdk/downLoad?file="+encodeURIComponent($fileList[i].FULL_FILE_PATH));
				 }*/
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
			$($("#fileDiv").find("a[id=downLoadBtnFile]")[0]).attr("style", "display:block");
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


