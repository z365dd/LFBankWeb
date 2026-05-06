
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出stepNo*/
	var HID_stepNo = $.session.get('HID_stepNo');
	/*从session中移除stepNo*/
	$.session.remove('HID_stepNo');

	getDetail(HID_stepNo);
	
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
	confirmx('是否更新数据同步', function(){
		save();
	});
}

/**
 * 保存函数--保存数据同步修改
 * @returns
 */
function save(){
	var formData = $("#ctrlTParaDataSynConfigForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaDataSynConfig/update", formData,
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
function getDetail(HID_stepNo){
	console.info('update ctrlTParaDataSynConfig info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaDataSynConfig/get", {stepNo:HID_stepNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#srcTp').multiselect("select", [jsonObj.srcTp]).multiselect('rebuild');
					$('#uptTp').multiselect("select", [jsonObj.uptTp]).multiselect('rebuild');
					$('#srcTabName').val(jsonObj.srcTabName);	
					$('#srcDataSrc').multiselect("select", [jsonObj.srcDataSrc]).multiselect('rebuild');
					$('#srcTabDesc').val(jsonObj.srcTabDesc);	
					$('#relatSys').multiselect("select", [jsonObj.relatSys]).multiselect('rebuild');
					$('#sameDbFlg').multiselect("select", [jsonObj.sameDbFlg]).multiselect('rebuild');
					$('#dstDataSrc').multiselect("select", [jsonObj.dstDataSrc]).multiselect('rebuild');
					$('#dstTabName').val(jsonObj.dstTabName);	
					$('#dstTabDesc').val(jsonObj.dstTabDesc);	
					$('#stepNo').val(jsonObj.stepNo);	
				}
			}
			
			/*触发校验*/
			$('#srcTp').blur();
			$('#uptTp').blur();
			$('#srcTabName').blur();
			$('#srcDataSrc').blur();
			$('#srcTabDesc').blur();
			$('#relatSys').blur();
			$('#sameDbFlg').blur();
			$('#dstDataSrc').blur();
			$('#dstTabName').blur();
			$('#dstTabDesc').blur();
			$('#stepNo').blur();

		}
	},
    "json");
}