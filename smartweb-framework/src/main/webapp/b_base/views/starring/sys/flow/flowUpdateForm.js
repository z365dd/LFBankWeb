$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出id*/
	id = $.session.get('id');
	/*从session中移除id*/
	$.session.remove('id');

	/*添加流程模板切换*/
	$('#flowTmplId').multiselect({
		//改变选项时，触发
		onChange: function(option, checked, select) {
			console.info("Changed option " + $(option).val() + ".");
			chgFlowTemplate($(option).val());
		}
	});

	$("#id").val(id);
	getDetail(id);
	
	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(proof()){
			submit();
		};
	});
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
	disableFlowTimeOutFlg();
});

/*设置超时处理可用*/
function enableFlowTimeOutFlg(){
    $('#flowTimeOutFlg').multiselect("enable");
    $('#timeOutFlowUserTp').multiselect("enable");
    
}

/*设置超时处理不可用*/
function disableFlowTimeOutFlg(){
    $('#flowTimeOutFlg').multiselect("disable");
    $('#timeOutFlowUserTp').multiselect("disable");
    
}

function submit(){
	confirmx('是否更新流程信息', function(){
		save();
	});
}

function chgFlowTemplate(flowTmplId){
	if(undefined==flowTmplId || ''==flowTmplId){
		return;
	}
    $.post(ctx + "/sys/flow/stepTemplate/getByTmpl", {flowTmplId:flowTmplId}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#flowTimeOutFlg').multiselect("select", [jsonObj.flowTimeOutFlg]).multiselect('rebuild');
					$('#timeOutFlowUserTp').multiselect("select", [jsonObj.timeOutFlowUserTp]).multiselect('rebuild');
					$('#timeOutProcUserIdName').val(jsonObj.timeOutProcUserName);
                    $('#timeOutProcUserIdId').val(jsonObj.timeOutProcUserId);
				}
			}
            disableFlowTimeOutFlg();
		}
	},
    "json");
}

/**
 * 保存函数--保存流程信息修改
 * @returns
 */
function save(){
	enableFlowTimeOutFlg();
	var formData = $("#updateForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/sys/flow/flow/update", formData,
		function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			    var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
				disableFlowTimeOutFlg();
				return '0';
			}else if(data.msg_type == "success"){
				var successMsg = "修改交易["+data.message+"]"; 
				showContent(successMsg,"success");
				console.info("修改交易成功");
				cancle();
			}
	}, "json");
	
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(id){
	console.info('update flow info......');
	$.post(ctx + "/sys/flow/flow/get", {id:id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#infoTitle').val(jsonObj.infoTitle);
					$('#flowTmplId').multiselect("select", [jsonObj.flowTmplId]).multiselect('rebuild');
					$('#sndUserNameOld').val(jsonObj.sndUserName);
					chgFlowTemplate(jsonObj.flowTmplId);
                    $('#flowDesc').val(jsonObj.flowDesc);
				}
			}
			disableFlowTimeOutFlg();
		}
	},
    "json");
}
