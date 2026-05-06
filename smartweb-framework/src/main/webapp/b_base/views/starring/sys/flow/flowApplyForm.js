$(document).ready(function(){

    $("#stat_02").click(function(){
	    //默认生成方法...
        $('#appMsg').removeAttr('check-empty');
	});
	
	$("#stat_03").click(function(){
	    //默认生成方法...
        $('#appMsg').attr('check-empty', 'true');
	});
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_apply").show();
	/*从session中拿出id*/
	id = $.session.get('id');
	/*从session中移除id*/
	$.session.remove('id');
	/*默认：审批通过*/
	setRadioVal('flowStat', '02');
	$("#id").val(id);
	getDetail(id);
	
	/*取消按钮*/
	$("#cancleBtn").click(function(){
	    console.info("cancelBtn...");
		cancle();
	});

	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(proof()){
			save();
		};
	});
	
	/*申请详情*/
	$("#applyDetailBtn").click(function(){
	    console.info("applyDetailBtn...");
	    var applyUrl = $('#applyUrl').val();
	    if(undefined!=applyUrl && null!=applyUrl && ''!=applyUrl){
	    	windowOpen(applyUrl, '申请内容', $(window).width(), $(window).height());
        }else{
        	showTip('该流程模板没有配置【审批申请内容URL】');
        }
	});
	
	disableFlowTimeOutFlg();
});

/**
 * 保存函数--审批流程
 * @returns
 */
function save(){
	var formData = $("#applyForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/sys/flow/flow/apply", formData,
		function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			    var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
				disableFlowTimeOutFlg();
				return '0';
			}else if(data.msg_type == "success"){
				var successMsg = "审批交易["+data.message+"]"; 
				showContent(successMsg,"success");
				console.info("审批交易成功");
				cancle();
			}
	}, "json");
	
}

/*设置超时处理可用*/
function enableFlowTimeOutFlg(){
    $('#flowTimeOutFlg').multiselect("enable");
    $('#timeOutFlowUserTp').multiselect("enable");
    
}

/*设置超时处理不可用*/
function disableFlowTimeOutFlg(){
    $('#flowTimeOutFlg').multiselect("disable");
    $('#timeOutFlowUserTp').multiselect("disable");
    $('#flowTmplId').multiselect("disable");
}

function chgFlowTemplate(flowTmplId){
	if(undefined==flowTmplId || ''==flowTmplId){
		return;
	}
    $.post(ctx + "/sys/flow/template/get", {id:flowTmplId}, function(data){
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

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(id){
	console.info('show flow info......');
	$.post(ctx + "/sys/flow/flow/get", {id:id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			var reg = new RegExp('</br>', 'g');
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#infoTitle').val(jsonObj.infoTitle);
					$('#flowTmplId').multiselect("select", [jsonObj.flowTmplId]).multiselect('rebuild');
					$('#sndUserName').val(jsonObj.sndUserName);
					chgFlowTemplate(jsonObj.flowTmplId);
					setDateValue('strTime', jsonObj.strTime);
					$('#strTime').attr("disabled",true);
					setDateValue('endTime', jsonObj.endTime);
					$('#endTime').attr("disabled",true);
					$('#statStr').val(jsonObj.statStr);
					$('#processing').val(jsonObj.stepSer+'/'+jsonObj.stepCount);
					$('#flowDesc').val(jsonObj.flowDesc.replace(reg, '\r\n'));
                    $('#flowDesc').val(jsonObj.flowDesc);
                    $('#applyUrl').val(jsonObj.applyUrl);
                    getStep(jsonObj.globalSeq, jsonObj.flowTmplId, jsonObj.stepSer);
				}
			}
		}
	},
    "json");
}

/**
 * 获取当前审批步骤信息
 * @param globalSeq
 * @param flowTmplId
 * @param stepSer
 * @returns
 */
function getStep(globalSeq, flowTmplId, stepSer){
	console.info('get flowStep info......');
	if(stepSer<=0){
		stepSer = 1; /* 如果为流程开始步骤节点则设置为第一步骤 */
	}
	$.post(ctx + "/sys/flow/flowStep/get", {
		globalSeq: globalSeq,
		flowTmplId: flowTmplId,
		stepSer: stepSer
	}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#stepTitle').val(jsonObj.infoTitle);
					$('#flowApprFlg').multiselect("select", [jsonObj.flowApprFlg]).multiselect('rebuild');
					$('#flowStepSer').val('第'+jsonObj.stepSer+'步');
					$('#succNumRate').val(jsonObj.succNumRate);
					$('#flowId').val(jsonObj.id);
				}
			}
			$('#flowApprFlg').multiselect("disable");
		}
	},
    "json");
}




