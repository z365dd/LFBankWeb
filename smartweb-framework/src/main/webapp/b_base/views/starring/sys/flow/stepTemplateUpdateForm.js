$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出id*/
	id = $.session.get('id');
	/*从session中移除id*/
	$.session.remove('id');
	
	
	$("#dayNum").blur(function(){
	    //默认生成方法...
        var dayNum = $("#dayNum").val();
        if(dayNum>0){
            $('#flowTimeOutFlg').attr('checkbtn', 'flowTimeOutFlg');
        }else{
            $('#flowTimeOutFlg').removeAttr('checkbtn');
        }
        idCutOff('flowTimeOutFlg');
	});
	
	/*根据不同流程模板装载不同的步骤模板*/
	// $('#flowTmplId').multiselect({
	// 	//改变选项时，触发
	// 	onChange: function(option, checked, select) {
	// 		console.info("Changed option " + $(option).val() + ".");
	// 		loadStepTemplate();
	// 	}
	// });
	// loadStepTemplate();
	
	/*添加超时处理切换*/
	$('#flowTimeOutFlg').multiselect({
		//改变选项时，触发
		onChange: function(option, checked, select) {
			console.info("Changed option " + $(option).val() + ".");
			chgTimeDeal();
		}
	});
	chgTimeDeal();
	
	/*添加超时用户类型切换*/
	$('#timeOutFlowUserTp').multiselect({
		//改变选项时，触发
		onChange: function(option, checked, select) {
			console.info("Changed option " + $(option).val() + ".");
			chgUserType('timeOutProcUserId', $(option).val());
		}
	});
	chgUserType('timeOutProcUserId');
	
	/*添加审批用户类型切换*/
	$('#apprFlowUserTp').multiselect({
		//改变选项时，触发
		onChange: function(option, checked, select) {
			console.info("Changed option " + $(option).val() + ".");
			chgUserType('apprUserId', $(option).val());
		}
	});
	chgUserType('apprUserId');

	/*添加通过标准控制*/
	$('#flowApprFlg').multiselect({
		//改变选项时，触发
		onChange: function(option, checked, select) {
			console.info("Changed option " + $(option).val() + ".");
			chgsuccNum();
			idCutOff('succNum');
		}
	});
	chgsuccNum();
	
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
	
});

/*根据不同流程模板装载不同的步骤模板*/
// function loadStepTemplate(){
// 	var flowTmplId = $('#flowTmplId').val();
// 	/*向后台发送参数*/
// 	$.post(ctx + "/sys/flow/stepTemplate/selectData",
// 			{
// 				flowTmplId : flowTmplId,
// 				isblank : 'true',
// 				blankText : '总流程开始',
// 				blankValue : 0
// 			},
// 		function(data){
// 			if(data.returnCode!==undefined && "0000"!=data.returnCode){
// 			    var errMsg = "错误信息["+data.message+"]";
// 				console.info(errMsg);
// 				return '0';
// 			}else{
// 				console.info('list='+data.list);
// 				$('#prvStepSer').multiselect('dataprovider',data.list);
// 			}
// 	}, "json");
// }

/*改变通过用户数输入属性*/
function chgsuccNum(){
    var flowApprFlg = $('#flowApprFlg').val();
    console.info('flowApprFlg='+flowApprFlg);
    $('#succNum').hide();
    $('#succNumLabel').hide();
    // 3-部分通过
    if(flowApprFlg=='3'){
        $('#succNum').removeAttr('check-empty');
        $('#succNum').val('0');
        $('#apprFlowUserTp').multiselect('enable');
    }// 2-部分通过
     else if(flowApprFlg=='2'){
        $('#succNum').attr('check-empty','true');
        $('#succNum').show();
        $('#succNumLabel').show();
        $('#succNum').val('');
        $('#apprFlowUserTp').multiselect('enable');
    }
    else {
        // 1-单用户通过
        $('#succNum').removeAttr('check-empty');
        $('#succNum').val('1');
        // 只允许选择用户
		if ($('#apprFlowUserTp').val() != 'user') {
			chgUserType('apprUserId', 'user');
			$('#apprFlowUserTp').multiselect('select', ['user']);
		}
    }
}


/*修改超时处理用户好用户是否必输*/
function chgTimeDeal(){
    var flowTimeOutFlg = $('#flowTimeOutFlg').val();
    console.info('flowTimeOutFlg='+flowTimeOutFlg);
    if(flowTimeOutFlg=='03'){
    	$('#timeOutFlowUserTp').attr('checkbtn', 'timeOutFlowUserTp');
        $('#timeOutProcUserId').attr('check-empty','true');
    }else{
    	$('#timeOutFlowUserTp').removeAttr('checkbtn');
        $('#timeOutProcUserId').removeAttr('check-empty');
    }
}

/*用户类型切换*/
function chgUserType(compId , userType){
	var url = '/sys/office/treeData?type=3';
    var title = '用户';
    $('#'+compId+'Name').attr('not_allow_select_root', 'true');
    $('#'+compId+'Name').attr('not_allow_select_parent', 'true');
    if('user'==userType){
        url = '/sys/office/treeData?type=3';
        title = '用户';
        $('#'+compId+'Name').attr('not_allow_select_root', 'true');
        $('#'+compId+'Name').attr('not_allow_select_parent', 'true');
    }else if('office'==userType){
        url = '/sys/office/treeData?type=2';
        title = '机构';
        $('#'+compId+'Name').attr('not_allow_select_root', 'false');
        $('#'+compId+'Name').attr('not_allow_select_parent', 'false');
    }else if('role'==userType){
        url = '/sys/office/treeData?type=4';
        title = '角色';
        $('#'+compId+'Name').attr('not_allow_select_root', 'false');
        $('#'+compId+'Name').attr('not_allow_select_parent', 'true');
    }
    if(undefined==userType || ''==userType){
        $('#'+compId+'Id').removeAttr('check-empty');
    }else{
        $('#'+compId+'Id').attr('check-empty','true');
    }
    $('#'+compId+'Name').attr('placeholder', '请选择'+title);
    $('#'+compId+'Name').attr('search_title', title);
    $('#'+compId+'Name').attr('search_url', url);
    $('#'+compId+'Name').val('');
    $('#'+compId+'Id').val('');
}

/**
 * 保存函数--保存步骤模板信息修改
 * @returns
 */
function save(){
	$('#apprFlowUserTp').multiselect('enable');
	$('#flowTmplId').multiselect("enable");
    $('#prvStepSer').multiselect("enable");
	var formData = $("#updateForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/sys/flow/stepTemplate/update", formData,
		function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			    var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
				$('#flowTmplId').multiselect("disable");
	            $('#prvStepSer').multiselect("disable");
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

function submit(){
	confirmx('是否更新步骤模板信息', function(){
		save();
	});
}

/*查询明细*/
function getDetail(id){
	console.info('update flow stepTemplate info......');
	$.post(ctx + "/sys/flow/stepTemplate/get", {id:id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#engName').val(jsonObj.engName);
					$('#name').val(jsonObj.name);
					$('#flowTmplId').multiselect("select", [jsonObj.flowTmplId]).multiselect('rebuild');
					$('#prvStepSer').multiselect("select", [jsonObj.prvStepSer]).multiselect('rebuild');
					$('#flowApprFlg').multiselect("select", [jsonObj.flowApprFlg]).multiselect('rebuild');
					if(jsonObj.flowApprFlg=='2'){
						$('#succNum').show();
					    $('#succNumLabel').show();
					}else{
						$('#succNum').hide();
					    $('#succNumLabel').hide();
					}
					$('#succNum').val(jsonObj.succNum);
					$('#dayNum').val(jsonObj.dayNum);
					$('#flowTimeOutFlg').multiselect("select", [jsonObj.flowTimeOutFlg]).multiselect('rebuild');
					$('#timeOutFlowUserTp').multiselect("select", [jsonObj.timeOutFlowUserTp]).multiselect('rebuild');
					chgUserType('timeOutProcUserId' ,jsonObj.timeOutFlowUserTp);
					$('#timeOutProcUserIdName').val(jsonObj.timeOutProcUserName);
                    $('#timeOutProcUserIdId').val(jsonObj.timeOutProcUserId);
                    $('#apprFlowUserTp').multiselect("select", [jsonObj.apprFlowUserTp]).multiselect('rebuild');
                    chgUserType('apprUserId' ,jsonObj.apprFlowUserTp);
					$('#apprUserIdName').val(jsonObj.apprUserName);
                    $('#apprUserIdId').val(jsonObj.apprUserId);
                    $('#flowProcClssTp').multiselect("select", [jsonObj.flowProcClssTp]).multiselect('rebuild');
                    $('#rmrk').val(jsonObj.rmrk);
					
				}
			}
            $('#flowTmplId').multiselect("disable");
            $('#prvStepSer').multiselect("disable");
		}
	},
    "json");
}


