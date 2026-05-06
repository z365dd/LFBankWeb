
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出id*/
	var HID_id = $.session.get('HID_id');
	/*从session中移除id*/
	$.session.remove('HID_id');
	$("#HID_id").val(HID_id);

	getDetail(HID_id);
	
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
	confirmx('是否更新请求IP信息表', function(){
		save();
	});
}

/**
 * 保存函数--保存请求IP信息表修改
 * @returns
 */
function save(){
	var formData = $("#sysReqIpMsgForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/sys/modules/sysReqIpMsg/update", formData,
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
function getDetail(HID_id){
	console.info('update sysReqIpMsg info......');
	$.post(ctx + "/sys/modules/sysReqIpMsg/get", {id:HID_id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#HID_id').val(jsonObj.id);
					$('#termIp').val(jsonObj.termIp);	
					$('#countryName').val(jsonObj.countryName);	
					$('#countryCode').val(jsonObj.countryCode);	
					$('#regionCode').val(jsonObj.regionCode);	
					$('#regionName').val(jsonObj.regionName);	
					$('#city').val(jsonObj.city);	
					$('#zipCode').val(jsonObj.zipCode);	
					$('#lat').val(jsonObj.lat);	
					$('#lon').val(jsonObj.lon);	
					$('#timezone').val(jsonObj.timezone);	
					$('#isp').val(jsonObj.isp);	
					$('#org').val(jsonObj.org);	
					$('#addrMsg').val(jsonObj.addrMsg);	
					$('#usrTp').val(jsonObj.usrTp);
					$('#usrName').val(jsonObj.usrName);
					$('#rmrk').val(jsonObj.rmrk);
				}
			}
			
			/*触发校验*/
			$('#termIp').blur();
			$('#countryName').blur();
			$('#countryCode').blur();
			$('#regionCode').blur();
			$('#regionName').blur();
			$('#city').blur();
			$('#zipCode').blur();
			$('#lat').blur();
			$('#lon').blur();
			$('#timezone').blur();
			$('#isp').blur();
			$('#org').blur();
			$('#addrMsg').blur();

		}
	},
    "json");
}