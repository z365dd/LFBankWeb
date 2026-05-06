$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_add").show();
	
	/*添加流程模板切换*/
	$('#flowTmplId').multiselect({
		//改变选项时，触发
		onChange: function(option, checked, select) {
			console.info("Changed option " + $(option).val() + ".");
			chgFlowTemplate($(option).val());
		}
	});

	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(proof()){
			save();
		};
	});
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	disableFlowTimeOutFlg();
	
	

	/***
	* 自定义组件渲
	* 一般用于对CST文件中定义的UI组件渲染到当前页面指定的布局中的列中，在页面的装载成功后事件
	* sourceId：CST中定义的UI组件的DIV标签的id
	* destId：页面中布局中列的id，或DIV标签有column样式修饰的id
	* 
	*/
	SmartWeb.swJS.render.renderTo("test", "testDiv");
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
 * 保存函数--保存流程模板信息新增
 * @returns
 */
function save(){
    enableFlowTimeOutFlg();
	var formData = $("#addForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/sys/flow/flow/insert", formData,
		function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			    var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
				disableFlowTimeOutFlg();
				return '0';
			}else if(data.msg_type == "success"){
				var successMsg = "新增交易["+data.message+"]"; 
				showContent(successMsg,"success");
				console.info("新增交易成功");
				cancle();
			}
	}, "json");
	
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

