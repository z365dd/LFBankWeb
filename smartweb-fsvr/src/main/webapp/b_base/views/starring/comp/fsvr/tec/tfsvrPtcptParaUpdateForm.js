console.log('tfsvrPtcptParaUpdateForm.js');

$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*可访问网络区域下拉框加载*/
	setSelect1("netRegion","/comp/fsvr/tec/tfsvrSvrPara/deponNetQry","netRegion","netRegion",true,false,true);
	/*从session中拿出callerId*/
	var HID_callerId = $.session.get('HID_callerId');
	/*从session中移除callerId*/
	$.session.remove('HID_callerId');

	getDetail(HID_callerId);
	/*调用方式控制div显隐*/
	$("#callMeth").change(function() {
		callMethCon();
	});
	
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

function submit(){
	confirmx('是否更新调用方信息', function(){
		save();
	});
}

/**
 * 保存函数--保存调用方信息修改
 * @returns
 */
function save(){
	var formData = getData();

	/*向后台发送参数*/
	$.post(ctx + "/comp/fsvr/tec/tfsvrPtcptPara/update", formData,
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

function getData() {
	var Data = {
			callerId : getI('callerId'),
			callerDesc : getI('callerDesc'),
			callMeth : getS('callMeth'),
			port : getI('port'),
			userNo : getI('userNo'),
			pwd : getI('pwd'),
			stat :getS('stat'),
			encrpFlg : getS('encrpFlg'),
			subctrvtSendFileSize : getI('subctrctSendFileSize'),
			subctrctRecvFileSize : getI('subctrctRecvFileSize'),
			attestFlg : getS('attestFlg'),
			reduceFlg : getS('reduceFlg'),
			resumeFlg : getS('resumeFlg'),
			speedlimFlg : getS('speedlimFlg'),
			sndSpeedlimSize : getI('sndSpeedlimSize'),
			recvSpeedlimSize : getI('recvSpeedlimSize'),
			uploadFilePath : getI('uploadFilePath'),
			downloadFilePath : getI('downloadFilePath'),
			netRegion : getS('netRegion')
	}
	return Data;
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(HID_callerId){
	console.info('update tfsvrPtcptPara info......');
	$.post(ctx + "/comp/fsvr/tec/tfsvrPtcptPara/get", {callerId:HID_callerId}, function(data){
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
					$('#callMeth').change();
					$('#callMeth').multiselect("disable");
					$('#port').val(jsonObj.port);	
					$('#userNo').val(jsonObj.userNo);	
					$('#pwd').val(jsonObj.pwd);	
					$('#stat').multiselect("select", [jsonObj.stat]).multiselect('rebuild');
					$('#encrpFlg').multiselect("select", [jsonObj.encrpFlg]).multiselect('rebuild');
					$('#subctrctSendFileSize').val(jsonObj.subctrctSendFileSize);	
					$('#subctrctRecvFileSize').val(jsonObj.subctrctRecvFileSize);	
					$('#attestFlg').multiselect("select", [jsonObj.attestFlg]).multiselect('rebuild');
					$('#reduceFlg').multiselect("select", [jsonObj.reduceFlg]).multiselect('rebuild');
					$('#resumeFlg').multiselect("select", [jsonObj.resumeFlg]).multiselect('rebuild');
					$('#speedlimFlg').multiselect("select", [jsonObj.speedlimFlg]).multiselect('rebuild');
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
				}
			}
			
			/*触发校验*/
			$('#callerId').blur();
			$('#callerDesc').blur();
			$('#callMeth').blur();
			$('#port').blur();
			$('#userNo').blur();
			$('#pwd').blur();
			$('#stat').blur();
			$('#encrpFlg').blur();
			$('#subctrctSendFileSize').blur();
			$('#subctrctRecvFileSize').blur();
			$('#attestFlg').blur();
			$('#ip').blur();
			$('#reduceFlg').blur();
			$('#resumeFlg').blur();
			$('#speedlimFlg').blur();
			$('#sndSpeedlimSize').blur();
			$('#recvSpeedlimSize').blur();
			$('#uploadFilePath').blur();
			$('#downloadFilePath').blur();

		}
	},
    "json");
}