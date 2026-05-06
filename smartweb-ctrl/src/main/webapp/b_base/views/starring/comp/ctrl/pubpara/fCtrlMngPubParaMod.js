console.info("公共参数维护...");
var ModArr;
$(document).ready(function(){
	
	//进入先查询公共参数
	pubParaQry();
	
	//提交按钮事件
	startJudge('updateBtn');
	cOpt('AUTH_TYPE');
	cOpt('CTRL_MODE');
	cOpt('DAYS');
	endJudge(update);
	
	/*关闭按钮*/
	$('#closeBtn').on('click',cancel);	
	
});

function pubParaQry(){
	var Key = $("#Key").val();
	
	$.ajax({
		type:"POST",
		url:ctx+"/comp/ctrl/oper/pubpara/pubParaQry",
		dataType:"json",
		data:{
			KEY:Key
		},
		success:function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]";
				showTip(errMsg,"error");
			}else{
				var valData = data.dataSetResult[0].data;
				valData = eval(valData);
				ModArr = valData;
				//为输出元素赋值
				if(valData.length>0){
					$("input:radio[name='AUTH_TYPE'][value='" + valData[0].KV + "']").prop("checked", "checked");
					$("input:radio[name='CTRL_MODE'][value='" + valData[1].KV + "']").prop("checked", "checked");
					$("#DAYS").val(valData[2].KV);
				}
			}
		}
	});
}

function update(){
	var KV1 = $("input[name='AUTH_TYPE']:checked").val();
	var KV2 = $("input[name='CTRL_MODE']:checked").val();
	var KV3 = $("#DAYS").val();
	if(ModArr==undefined){
		showTip("系统出错,请刷新后再尝试!", "success");
		return;
	}else{
		ModArr[0].KV = KV1;
		ModArr[1].KV = KV2;
		ModArr[2].KV = KV3;
	}
	var KEY_LIST = JSON.stringify(ModArr);
	$.ajax({
		type:"POST",
		url:ctx+"/comp/ctrl/oper/pubpara/pubParaMod",
		dataType:"json",
		data:{
			KEY_LIST:KEY_LIST
		},
		success:function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]";
				showTip(errMsg,"error");
			}else{
				/*var valData = data.dataSetResult[0].data;
				valData = eval(valData);*/
				var successMsg = "参数维护["+data.message+"]"; 
				showContent(successMsg,"success");
			}
		}
	});
}

/*返回执行*/
function cancel(){
	Ewin.confirm({
		title : "操作提示",
		message : "将会关闭此页面，确定关闭吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		top.$.fn.jerichoTab.closeCurrentTab(); 
	});
}