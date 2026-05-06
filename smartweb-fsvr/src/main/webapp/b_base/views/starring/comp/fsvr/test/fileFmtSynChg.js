console.log('fileFmtSynChg.js');
//文件id全局变量
var FILE_ID ="";

$(function(){
	//文件上传插件初始化
	resetFile("FILE_SET_SEQ");
	/*加载转换号下拉框的数据*/
	setSelect1("CHG_NO","/comp/fsvr/tec/tfsvrFileChgPara/getList","chgNo","chgName",undefined,false);

	/*关闭按钮*/
	$('#cancelBtn').on('click',cancel);	
	
	//提交
	$('#addBtn').on('click',fileFmtSynChgSend);
});


//文件上传插件初始化
function resetFile(Id){
	// 业务新增文件上传初始化
	$('#'+Id).fileinput({
		showUpload: true,//是否显示上传按钮 
		language:'zh',//设置语言  
		uploadAsync:true,//默认异步上传  
		//dropZoneEnabled:true,//是否显示拖拽区域  
		uploadUrl:ctx+'/comp/fchk/test/fChkVoaTest/upload',//上传的地址  
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
	$('#'+Id).on("fileuploaded", function(event, data) {
		var data = data.response;
		if(data.returnCode!==undefined && "0000"!=data.returnCode){
			var errMsg = "错误信息["+data.message+"]"; 
			showTip(errMsg,"error");
			return;
		}else{
		   /* showTip(data.message,"success");*/
		    var dataVal = data.dataSetResult[0].data[0];
		  //文件上传成功把文件路径保存数组
		    FILE_ID =dataVal.saveName+"."+dataVal.type;
		   // FILE_NAME = dataVal.fileName;
		}
	}).on("fileremoved", function(id, ind){ //只是你删除重新选择的图片才会触发，而删除原图片不会触发。
		console.info("移出单个文件");
		$("#hidden_FILE_SET_SEQ").val("");
		//文件移除，文件id赋值为空
		FILE_ID = "";
		//FILE_NAME = "";
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



//提交
function fileFmtSynChgSend(){
	if(proof()){
		if(FILE_ID==""){
			showTip("请上传文件","success");
			return;
		}
		$.get(ctx+"/comp/fsvr/fileFmgChgTest/fileFmtSynChgSend",{
			//TODO
			CHG_NO:getS("CHG_NO"),
			FILE_ID:FILE_ID,
			FILE_NUM:getI("FILE_NUM")
		},function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "文件转换["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{	
				console.log(data.message);
				var successMsg = "文件转换["+data.message+"]"; 
				showContent(successMsg,"success");
				
				 var dataVal = data.dataSetResult[0].data[0];
				 //设置值
				 setVal(Data);
				 
			}
		},"json");
	}
	
}


//设置值
function setVal(Data){
	setS("FLG",Data.FLG);
	setI("SRC_PUB_FILE_PATH",Data.SRC_PUB_FILE_PATH);
	setI("RET_MSG",Data.RET_MSG);
	if (Data.LIST != undefined && Data.LIST.length > 0) {
		$('#table').bootstrapTable('load', Data.LIST);
	}
}




