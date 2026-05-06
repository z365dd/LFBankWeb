$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出id*/
	id = $.session.get('id');
	/*从session中移除id*/
	$.session.remove('id');
	$("#id").val(id);
	getDetail(id);
	chgHeight(500);
	/*取消按钮*/
	$("#cancleBtn").click(function(){
	    console.info("cancelBtn...");
		cancle();
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
	
	/**
	 * 追加评论信息
	 */
	$('#appendDescBtn').click(function(){
	    console.info("appendDescBtn...");
	    $("#appendModal").modal('show');
	    $('#appendRmrk').val('');
	});
	
	$('#appendSubBtn').click(function(){
		if(yPortion('appendForm')){
			confirmx("是否确定追加评论信息", function(){
				var formData = $("#appendForm").serializeObject();
				var url = ctx + "/sys/flow/flow/appendDesc";
				var id = $('#id').val();
				var userName = $('#curUserName').val();
				var nt = (new Date()).pattern("yyyy-MM-dd hh:mm:ss");
				var orgFlowDesc = $('#flowDesc').val();
				var flowDesc = (orgFlowDesc==''? orgFlowDesc:(orgFlowDesc+ '\r\n')) + nt+'【'+userName+'】:'+formData.appendRmrk;
				//向后台发送参数
				$.post(url,
						{ 
							id : id ,
							flowDesc : flowDesc.replace(/\r\n/g, '</br>')
						},
						function(data){ 
							if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
								var errMsg = "错误信息["+data.message+"]"; 
								showContent(errMsg,"error");
								return '0';
							}else if(data.msg_type == "success"){
								var successMsg = "追加评论信息["+data.message+"]"; 
								showContent(successMsg,"success");
								$('#flowDesc').val(flowDesc);
								$("#appendModal").modal('hide');
							}
						}, "json");
			});
		}
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
    $('#flowTmplId').multiselect("disable");
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
					var stepSer = jsonObj.stepSer;
                    if(stepSer>=999){
                    	/*流程结束步骤号，则设置当前步骤号为总步骤数*/
                    	stepSer = jsonObj.stepCount;
                    }
					$('#processing').val(stepSer+'/'+jsonObj.stepCount);
					
                    $('#flowDesc').val(jsonObj.flowDesc.replace(reg, '\r\n'));
					var applyUrl = jsonObj.applyUrl;
					if (applyUrl.indexOf("_")!=-1) {
						applyUrl = applyUrl.substring(0, applyUrl.indexOf("_"));
					}
                    $('#applyUrl').val(applyUrl);
                    $('#curUserId').val(jsonObj.curUserId);
                    $('#curUserName').val(jsonObj.curUserName);
                    showStepList(jsonObj.globalSeq, jsonObj.flowTmplId, stepSer, jsonObj.flowStat);
				}
			}
		}
	},
    "json");
}

/**
 * 显示步骤明细信息
 * @param globalSeq
 * @param flowTmplId
 * @param stepSer
 * @returns
 */
function showStepList(globalSeq, flowTmplId, stepSer, stat){
	console.info('show flowStep info......');
	if(stepSer<=0){
		stepSer = 1; /* 如果为流程开始步骤节点则设置为第一步骤 */
	}
	$.post(ctx + "/sys/flow/flowStep/show", {
		globalSeq: globalSeq,
		flowTmplId: flowTmplId,
		stepSer: stepSer
	}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			var stepList = data.dataSetResult[0].data;
			console.info("stepList="+stepList);
			if(undefined!=stepList && null!=stepList){
				var stepListHtml = '';
				var idx = 0;
				for(var j=0;j<stepSer;j++){
					// 生成步骤列表数量
					var infoTitle = '';
					var stepStat = '';
					var strTime = '';
					var endTime = '';
					var stepTable = '';
					var flowApprFlg = '';
					var flowApprFlgStr = '';
					var succNum = '';
					for(var i=idx;i<stepList.length;i++){
						var step = stepList[i];
						if(step.stepSer!=(j+1)){
							/*步骤号与当前要处理的步骤号不一致时跳出循环*/
							idx = i;
							break;
						}
						if(i==idx){
							/*首次时获取步骤标题,开始时间*/
							infoTitle = '<span ravo="rainbow_fx_bj" class="">步骤'+(j+1)+'-'+step.infoTitle+'</span>';
							strTime = step.strTime;
							flowApprFlg = step.flowApprFlg;
							flowApprFlgStr = step.flowApprFlgStr;
							succNum = step.succNum;
						}
						stepTable += '<tr>';
						stepTable += '<td>'+step.curProcUserName+'</td>';
						stepTable += '<td>'+step.statStr+'</td>';
						stepTable += '<td>'+step.appMsg+'</td>';
						stepTable += '</tr>';
						/*设置结束时间*/
						if(undefined!=step.endTime && ''!=$.trim(step.endTime)){
							endTime = step.endTime;
						}
					}
					
					if((j+1)<stepSer){
						stepStat = '<span style="color:green;" class="">（已处理）</span>'					
					}else{
						if(undefined!=stat && ('00'==stat || '01'==stat)){
							/*当前步骤为最后一步且流程步骤为处理中的，则步骤状态设置为未处理*/
							stepStat = '<span style="color:orange;" class="">（未处理）</span>'	
						}else{
							/*当前步骤为最后一步且流程步骤为完成的，则步骤状态设置为已处理*/
							stepStat = '<span style="color:green;" class="">（已处理）</span>'			
						}					
					}
					
					stepListHtml += '<div ravo="rainbow_fx_layout_panel" class="panel panel-info" style="margin-left:15%;margin-right:15%;  height:300px; overflow-y: scroll; overflow-x: scroll;">';
					stepListHtml += '<div class="panel-heading">';
					stepListHtml += '<p class="">';
					stepListHtml += '<strong>';
					stepListHtml += infoTitle;
					stepListHtml += stepStat;
					stepListHtml += '</strong>';
					stepListHtml += '</p>';
					stepListHtml += '</div>';
					stepListHtml += '<div class="panel-body" contenteditable="false">';					
					stepListHtml += '<div ravo="rainbow_fx_layout" class="row clearfix">';					
					stepListHtml += '<div class="col-md-6 column">';
					stepListHtml += '<div ravo="rainbow_fx" class="form-group">';
					stepListHtml += '<label class="control-label col-sm-4 control-label">';
					stepListHtml += '通过标准';
					stepListHtml += '</label>';
					stepListHtml += '<div class="input-group-sm   col-sm-8">';
					stepListHtml += '<input class="form-control" type="text" readonly="readonly" value="'+flowApprFlgStr+'" />';
					stepListHtml += '</div>';
					stepListHtml += '</div>';
					stepListHtml += '</div>';
					stepListHtml += '<div class="col-md-6 column">';
					if(flowApprFlg=='2'){
						/*2-部分通过*/
						stepListHtml += '<div ravo="rainbow_fx" class="form-group">';
						stepListHtml += '<label class="control-label col-sm-4 control-label">';
						stepListHtml += '通过用户比例';
						stepListHtml += '</label>';
						stepListHtml += '<div class="input-group-sm  col-sm-8">';
						stepListHtml += '<input class="form-control" type="text" readonly="readonly" value="'+succNum+'%" />';
						stepListHtml += '</div>';
						stepListHtml += '</div>';
					}					
					stepListHtml += '</div>';
					stepListHtml += '</div>';					
					stepListHtml += '<div ravo="rainbow_fx_layout" class="row clearfix">';					
					stepListHtml += '<div class="col-md-6 column">';
					stepListHtml += '<div ravo="rainbow_fx" class="form-group">';
					stepListHtml += '<label class="control-label col-sm-4 control-label">';
					stepListHtml += '开始时间';
					stepListHtml += '</label>';
					stepListHtml += '<div class="input-group-sm   col-sm-8">';
					stepListHtml += '<input class="form-control" type="text" readonly="readonly" value="'+strTime+'" />';
					stepListHtml += '</div>';
					stepListHtml += '</div>';
					stepListHtml += '</div>';
					stepListHtml += '<div class="col-md-6 column">';
					stepListHtml += '<div ravo="rainbow_fx" class="form-group">';
					stepListHtml += '<label class="control-label col-sm-4 control-label">';
					stepListHtml += '结束时间';
					stepListHtml += '</label>';
					stepListHtml += '<div class="input-group-sm  col-sm-8">';
					stepListHtml += '<input class="form-control" type="text" readonly="readonly" value="'+endTime+'" />';
					stepListHtml += '</div>';
					stepListHtml += '</div>';
					stepListHtml += '</div>';
					stepListHtml += '</div>';
					stepListHtml += '<div ravo="rainbow_fx_layout" class="row clearfix">';
					stepListHtml += '<div class="col-md-12 column">';
					stepListHtml += '<table class="table table-striped table-bordered table-condensed table-hover" style="width: 100%;">';
					stepListHtml += '<thead>';
					stepListHtml += '<tr>';
					stepListHtml += '<th style="" data-field="curProcUserName" tabindex="0">';
					stepListHtml += '<div class="th-inner ">处理用户</div><div class="fht-cell"></div>';
					stepListHtml += '</th>';
					stepListHtml += '<th style="" data-field="statStr" tabindex="0">';
					stepListHtml += '<div class="th-inner ">处理结果</div><div class="fht-cell"></div>';
					stepListHtml += '</th>';
					stepListHtml += '<th style="" data-field="appMsg" tabindex="0">';
					stepListHtml += '<div class="th-inner ">处理信息</div><div class="fht-cell"></div>';
					stepListHtml += '</th>';
					stepListHtml += '</tr>';
					stepListHtml += '</thead>';
					stepListHtml += stepTable;
					stepListHtml += '</table>';
					stepListHtml += '</div>';
					stepListHtml += '</div>';
					stepListHtml += '</div>';
					stepListHtml += '</div>';
					
				}
				$('#stepDiv').html(stepListHtml);
			}			
		}
	},
    "json");
}

/*改变内容高度*/
function chgHeight(h){
	let Height =$(document.body).height() + h;
	$(window.parent.document).find("#tab_detail").find('iframe').height(Height);
}