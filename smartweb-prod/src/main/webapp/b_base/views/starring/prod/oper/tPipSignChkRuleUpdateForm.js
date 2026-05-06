
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出ruleId*/
	var HID_ruleId = $.session.get('HID_ruleId');
	/*从session中移除ruleId*/
	$.session.remove('HID_ruleId');

	getDetail(HID_ruleId);
	
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
	

	/*div块控制*/
	divCtrl();
});

function submit(){
	save();
}

/**
 * 保存函数--保存T_PIP_SIGN_CHK_RULE修改
 * @returns
 */
function save(){
	var formData = $("#tPipSignChkRuleForm").serializeObject();
	formData.ruleName=$("#ruleDesc").val();

	/*向后台发送参数*/
	$.post(ctx + "/prod/oper/tPipSignChkRule/update", formData,
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
function getDetail(HID_ruleId){
	console.info('update tPipSignChkRule info......');
	$.post(ctx + "/prod/oper/tPipSignChkRule/get", {ruleId:HID_ruleId}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#ruleId').val(jsonObj.ruleId);	
					$('#ruleDesc').val(jsonObj.ruleDesc);	
					$('#signChkTp').multiselect("select", [jsonObj.signChkTp]).multiselect('rebuild');
					$('#signChkTp').change();
					$('#signChkSndTp').multiselect("select", [jsonObj.signChkSndTp]).multiselect('rebuild');
					$('#signChkTp').change();
					if(jsonObj.autoChkStrTime.length==6){
						var HH=jsonObj.autoChkStrTime.substr(0,2);
						var mm=jsonObj.autoChkStrTime.substr(2,2);
						var ss=jsonObj.autoChkStrTime.substr(4,2);
						$('#autoChkStrTime').val(HH+":"+mm+":"+ss);	
						$('#val_autoChkStrTime').val(jsonObj.autoChkStrTime);	
					}
					$('#chkRsltFileFlg').multiselect("select", [jsonObj.chkRsltFileFlg]).multiselect('rebuild');
					$('#chkRsltFileName').val(jsonObj.chkRsltFileName);	
					$('#othFileGetTp').multiselect("select", [jsonObj.othFileGetTp]).multiselect('rebuild');
					$('#othChkFileName').val(jsonObj.othChkFileName);	

				}
			}
			
			/*触发校验*/
			$('#ruleId').blur();
			$('#ruleDesc').blur();
			$('#signChkTp').blur();
			$('#signChkSndTp').blur();
			$('#autoChkStrTime').blur();
			$('#chkRsltFileFlg').blur();
			$('#chkRsltFileName').blur();
			$('#othFileGetTp').blur();
			$('#othChkFileName').blur();

		}
	},
    "json");
}

function divCtrl(){
	/*签约对账类型控制*/
	$("#signChkTp").on("change" ,function(){
		var signChkTp=$("#signChkTp").val();
		var signChkSndTp=$("#signChkSndTp").val();
		if("01"==signChkTp){
			$("#chkDiv").hide();
		}else{
			$("#chkDiv").show();
			if("02"==signChkSndTp){
				$("#autoDiv").hide();
			}else{
				$("#autoDiv").show();
				if("02"==signChkTp){//自动任务-推送第三方
					$("#autoDivTwo").show();
					$("#autoDivThree").hide();
				}else if("03"==signChkTp){//自动任务-获取第三方文件
					$("#autoDivThree").show();
					$("#autoDivTwo").hide();
				}
			}
			
		}
	});
	
	/*签约对账发起方类型*/
	$("#signChkSndTp").on("change" ,function(){
		var signChkSndTp=$("#signChkSndTp").val();
		var signChkTp=$("#signChkTp").val();
		if("02"==signChkSndTp){
			$("#autoDiv").hide();
		}else{
			$("#autoDiv").show();
			if("02"==signChkTp){//自动任务-推送第三方
				$("#autoDivTwo").show();
				$("#autoDivThree").hide();
			}else if("03"==signChkTp){//自动任务-获取第三方文件
				$("#autoDivThree").show();
				$("#autoDivTwo").hide();
			}
		}
		
	});
}