
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*可访问网络区域下拉框加载*/
	setSelect1("netRegion","/comp/fsvr/tec/tfsvrSvrPara/deponNetQry","netRegion","netRegion",true,false,true);
	/*从session中拿出callerId*/
	var HID_callerId = $.session.get('HID_callerId');
	/*从session中移除callerId*/
	$.session.remove('HID_callerId');
	$("#HID_callerId").val(HID_callerId);

	getDetail(HID_callerId);
	/*调用方式控制div显隐*/
	$("#callMeth").change(function() {
		callMethCon();
	});
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});
/*调用方式控制div显隐*/
function callMethCon() {
	var callMeth = $('#callMeth').val();
	if (callMeth == '01') {
		$('#clientDiv').show();
		$('#serverDiv').hide();
	} else if (callMeth == "02") {
		$('#serverDiv').show();
		$('#clientDiv').hide();
	} else {
		$('#clientDiv').hide();
		$('#serverDiv').hide();
	}
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(callerId){
	console.info('get tfsvrPtcptPara info......');
	$.post(ctx + "/comp/fsvr/tec/tfsvrPtcptPara/get", {callerId:callerId}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#callerId').val(jsonObj.callerId);	
					$('#callerDesc').val(jsonObj.callerDesc);	
					$('#callMeth').multiselect("select", [jsonObj.callMeth]).multiselect('rebuild');
					$('#callMeth').multiselect("disable");
					$('#callMeth').change();
					$('#port').val(jsonObj.port);	
					$('#userNo').val(jsonObj.userNo);	
					$('#pwd').val(jsonObj.pwd);	
					$('#stat').multiselect("select", [jsonObj.stat]).multiselect('rebuild');
					$('#stat').multiselect("disable");
					$('#encrpFlg').multiselect("select", [jsonObj.encrpFlg]).multiselect('rebuild');
					$('#encrpFlg').multiselect("disable");
					$('#subctrctSendFileSize').val(jsonObj.subctrctSendFileSize);	
					$('#subctrctRecvFileSize').val(jsonObj.subctrctRecvFileSize);	
					$('#attestFlg').multiselect("select", [jsonObj.attestFlg]).multiselect('rebuild');
					$('#attestFlg').multiselect("disable");
					$('#reduceFlg').multiselect("select", [jsonObj.reduceFlg]).multiselect('rebuild');
					$('#reduceFlg').multiselect("disable");
					$('#resumeFlg').multiselect("select", [jsonObj.resumeFlg]).multiselect('rebuild');
					$('#resumeFlg').multiselect("disable");
					$('#speedlimFlg').multiselect("select", [jsonObj.speedlimFlg]).multiselect('rebuild');
					$('#speedlimFlg').multiselect("disable");
					$('#sndSpeedlimSize').val(jsonObj.sndSpeedlimSize);	
					$('#recvSpeedlimSize').val(jsonObj.recvSpeedlimSize);	
					$('#uploadFilePath').val(jsonObj.uploadFilePath);	
					$('#downloadFilePath').val(jsonObj.downloadFilePath);	
					if (jsonObj.deponList != undefined && jsonObj.deponList != "") {
						var deponArr = jsonObj.deponList;
						var arrS = [];
						for (var a = 0; a < deponArr.length; a++) {
							arrS.push(deponArr[a].netRegion); 
						}
						var netRegionStr = arrS.join(";");
						setS("netRegion", netRegionStr);
					}
					$('#netRegion').multiselect("disable");
				}
			}

		}
	},
    "json");
}