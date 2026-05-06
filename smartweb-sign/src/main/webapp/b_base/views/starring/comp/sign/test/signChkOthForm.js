console.log('signChkOthForm.js');

//文件id全局变量
var FILE_ID ="";

$(function(){
	// 业务编号
	$("#BUSI_NO").click(function() {
		busiClick("BUSI_NO",null,"ENTR_NO"); 
	});
	//文件上传插件初始化
	resetFile("FILE_SET_SEQ");
	
	/*关闭按钮*/
	$('#cancelBtn').on('click',closeBtnToDo);	
	
	//提交
	$('#addBtn').on('click',add);
	
});

//文件上传插件初始化
function resetFile(Id){
	// 业务新增文件上传初始化
	$('#'+Id).fileinput({
		showUpload: true,//是否显示上传按钮 
		language:'zh',//设置语言  
		uploadAsync:true,//默认异步上传  
		//dropZoneEnabled:true,//是否显示拖拽区域  
		uploadUrl:ctx+'/comp/sign/test/signFile/upload',//上传的地址  
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
		}
	}).on("fileremoved", function(id, ind){ //只是你删除重新选择的图片才会触发，而删除原图片不会触发。
		console.info("移出单个文件");
		$("#hidden_FILE_SET_SEQ").val("");
		//文件移除，文件id赋值为空
		$FILE_ID = "";
	}).on('filesuccessremove', function(event, id) { //图片上传成功后，点击删除按钮的回调函数
	    
	});
}


//签约对账
function add(){
	if(proof()){
		if(FILE_ID==""){
			showTip("请上传文件","success");
			return;
		}
		Ewin.confirm({
			title : "操作提示",
			message : "是否确认提交？"
		}).on(function(e) {
			if (!e) {
				return;
			}
			$.post(ctx+"/comp/sign/test/signChkOth/add",{
				
	//			SIGN_PROT_TP_NO:getS("SIGN_PROT_TP_NO"),
				//TODO
				CHK_DATE:dateDelete(getI("CHK_DATE")),
				FILE_ID:FILE_ID,
				BUSI_NO : getI("BUSI_NO")
			},function(data){ 
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var errMsg = "错误信息["+data.message+"]"; 
					showContent(errMsg,"error");	
				}else{	
					console.log(data.message);
					var successMsg = "提交["+data.message+"]"; 
					showContent(successMsg,"success");
					
					 var dataVal = data.dataSetResult[0].data[0];
	//				 setI("FILE_SET_SEQ",dataVal.FILE_SET_SEQ);
				}
			},"json");
		});
	}else {
		if(getI('BUSI_NO')==""){
			$('#BUSI_NO').click();
		}
	}
}


//打开jBox 业务编号
function busiClick(busiNo, busiName, entrNo, callBackFunc) {
	// 正常打开
	top.$.jBox
			.open(
					"iframe:/smartweb/a/tag/treeselect?url="
							+ encodeURIComponent($("#" + busiNo).attr(
									"search_url")),
					"选择业务编号",
					300,
					420,
					{
						ajaxData : {
							selectIds : ""
						},
						buttons : {
							"确定" : "ok",
							"清除" : "clear",
							"关闭" : true
						},
						submit : function(v, h, f) {
							if (v == "ok") {
								var tree = h.find("iframe")[0].contentWindow.tree;// h.find("iframe").contents();
								var ids = [], names = [], nodes = [];
								if ("" == "true") {
									nodes = tree.getCheckedNodes(true);
								} else {
									nodes = tree.getSelectedNodes();
								}
								for (var i = 0; i < nodes.length; i++) {//
									ids.push(nodes[i].id1);
									names.push(nodes[i].name);//
									names.push(nodes[i].name3);//
									break; // 如果为非复选框选择，则返回第一个选择
								}

								$("#" + busiNo).val(nodes[0].BUSI_NO);
								if (undefined != busiName && null != busiName) {
									$("#" + busiName).val(nodes[0].BUSI_NAME);
								}

								if (undefined != entrNo && null != entrNo) {
									setI("ENTR_NO",nodes[0].ENTR_NO);
								}
								
								if(undefined != callBackFunc && null != callBackFunc && typeof(eval(callBackFunc)) == "function"){
									callBackFunc();
								}

							} else if (v == "clear") {
								$("#" + busiNo).val("");
								if (undefined != busiName && null != busiName) {
									$("#" + busiName).val("");
								}
								if (undefined != entrNo && null != entrNo) {
									resetS(entrNo);
								}
								if(undefined != callBackFunc && null != callBackFunc && typeof(eval(callBackFunc)) == "function"){
									callBackFunc();
								}
							}
							if (typeof companyTreeselectCallBack == 'function') {
								companyTreeselectCallBack(v, h, f);
							}
						},
						loaded : function(h) {
							$(".jbox-content", top.document).css("overflow-y",
									"hidden");
						}
					});
}

