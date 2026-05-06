console.log('tfsvrPtcptParaAddForm.js');
$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_add").show();
	/*可访问网络区域下拉框加载*/
	setSelect1("netRegion","/comp/fsvr/tec/tfsvrSvrPara/deponNetQry","netRegion","netRegion",true,false,true);
	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(proof()){
			submit();
		};
	});
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

function submit(){
	confirmx('是否新增调用方信息', function(){
		save();
	});
}

/**
 * 保存函数--保存调用方信息新增
 * @returns
 */
function save(){
	var formData = getData();
	console.log(formData);
	/*向后台发送参数*/
	$.post(ctx + "/comp/fsvr/tec/tfsvrPtcptPara/insert", formData,
		function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			    var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
				return '0';
			}else if(data.msg_type == "success"){
				var successMsg = "新增交易["+data.message+"]"; 
				showContent(successMsg,"success");
				console.info("新增交易成功");
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