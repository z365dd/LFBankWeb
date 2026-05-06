
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出id*/
	var HID_id = $.session.get('HID_id');
	/*从session中移除id*/
	$.session.remove('HID_id');
	$("#HID_id").val(HID_id);

	getDetail(HID_id);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(id){
	console.info('get sysReqIpMsg info......');
	$.post(ctx + "/sys/modules/sysReqIpMsg/get", {id:id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
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

		}
	},
    "json");
}