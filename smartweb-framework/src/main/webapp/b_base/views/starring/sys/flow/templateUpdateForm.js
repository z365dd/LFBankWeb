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
	
	/*添加超时处理切换*/
	chgTimeDeal();
	$('#flowTimeOutFlg').multiselect({
		//改变选项时，触发
		onChange: function(option, checked, select) {
			console.info("Changed option " + $(option).val() + ".");
			chgTimeDeal();
		}
	});
	
	/*添加超时用户类型切换*/
	chgUserType('timeOutProcUserId');
	$('#timeOutFlowUserTp').multiselect({
		//改变选项时，触发
		onChange: function(option, checked, select) {
			console.info("Changed option " + $(option).val() + ".");
			chgUserType('timeOutProcUserId', $(option).val());
		}
	});
	
	/*添加发起用户类型切换*/
	chgUserType('sndUserId');
	$('#sndFlowUserTp').multiselect({
		//改变选项时，触发
		onChange: function(option, checked, select) {
			console.info("Changed option " + $(option).val() + ".");
			chgUserType('sndUserId', $(option).val());
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
	
});

function submit(){
	confirmx('是否更新流程模板信息', function(){
		save();
	});
}

/**
 * 保存函数--保存流程模板信息修改
 * @returns
 */
function save(){
	var formData = $("#updateForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/sys/flow/template/update", formData,
		function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			    var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
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
	console.info('update flow template info......');
	$.post(ctx + "/sys/flow/template/get", {id:id}, function(data){
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
					$('#flowTp').multiselect("select", [jsonObj.flowTp]).multiselect('rebuild');
					$('#verNo').val(jsonObj.verNo);
					$('#dayNum').val(jsonObj.dayNum);
					$('#flowTimeOutFlg').multiselect("select", [jsonObj.flowTimeOutFlg]).multiselect('rebuild');
					$('#timeOutFlowUserTp').multiselect("select", [jsonObj.timeOutFlowUserTp]).multiselect('rebuild');
					chgUserType('timeOutProcUserId' ,jsonObj.timeOutFlowUserTp);
					$('#timeOutProcUserIdName').val(jsonObj.timeOutProcUserName);
                    $('#timeOutProcUserIdId').val(jsonObj.timeOutProcUserId);
                    $('#sndFlowUserTp').multiselect("select", [jsonObj.sndFlowUserTp]).multiselect('rebuild');
                    chgUserType('sndUserId' ,jsonObj.sndFlowUserTp);
					$('#sndUserIdName').val(jsonObj.sndUserName);
                    $('#sndUserIdId').val(jsonObj.sndUserId);
                    $('#flowProcClssTp').multiselect("select", [jsonObj.flowProcClssTp]).multiselect('rebuild');
                    $('#rmrk').val(jsonObj.rmrk);
                    $('#applyUrl').val(jsonObj.applyUrl);
					
				}
			}
            $('#flowTp').multiselect("disable");
		}
	},
    "json");
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
function chgUserType(compId ,userType){
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
