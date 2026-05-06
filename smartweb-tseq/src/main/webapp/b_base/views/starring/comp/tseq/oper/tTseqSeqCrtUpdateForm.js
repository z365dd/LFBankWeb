console.log("tTseqSeqCrtUpdateForm.js");
var jsonData = {};
$(document).ready(function() {
	/* 把修改页面内容显示出来 */
	parent.window.$("#iframe_update").show();
	$("[data-toggle='popover']").popover();
	/* 从session中拿出seqCrtId */
	var HID_seqCrtId = $.session.get('HID_seqCrtId');
	/* 从session中移除seqCrtId */
	$.session.remove('HID_seqCrtId');
	getMaxNodeLen();
	getDetail(HID_seqCrtId);
	
	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(proof()){
			if (parseInt(getI('maxVal')) <= parseInt(getI('minVal'))) {
				showTip("最大值要大于最小值！","error");
			} else if (parseInt(getI('seqNoLen')) < $('#maxVal').val().length) {
				showTip("最大值的长度不能大于流水号长度！","error");
			} else if ( parseInt(getI('seqLen')) >= parseInt(getI('maxVal'))) {
				showTip("最大值要大于步长！","error");
			} else {
				submit();
			}
		} else {
			parent.goTop();
			$('#tTseqSeqCrtForm').find('span.warnBlock:first').prev().parent().find('input:first').focus();
		};
	});
	$('#minVal').change(function() {
		if (getI('maxVal')&&getI('minVal')&&(parseInt(getI('maxVal')) <= parseInt(getI('minVal')))) {	
			showTip("最大值要大于最小值！","error");
		}
	});
	$('#maxVal').change(function() {
		if (getI('maxVal')&&getI('minVal')&&(parseInt(getI('maxVal')) <= parseInt(getI('minVal')))) {	
			showTip("最大值要大于最小值！","error");
		} else if (getI('maxVal') && getI('seqNoLen')) { 
			var seqNoLen = parseInt(getI('seqNoLen'));
			var maxVal = getI("maxVal");
			if ( seqNoLen < maxVal.length) {
				showTip("最大值的长度不能大于流水号长度！","error");
			}  
		} else if (getI('maxVal') && getI('seqLen')) {
			if ( parseInt(getI('seqLen')) >= parseInt(getI('maxVal'))) {
				showTip("最大值要大于步长！","error");
			}  
		}
	});
	$('#seqNoLen').change(function() {
		var seqNoLen = parseInt(getI('seqNoLen'));
		if ( seqNoLen!= "") {
			var nodeLen = parseInt(getI('nodeLen'));
			setI('respSeqLen',seqNoLen + nodeLen);
			var maxVal = getI("maxVal");
			if ( seqNoLen < maxVal.length) {
				showTip("流水号长度不能小于最大值的长度！","error");
			}
		}
	});
	

	/* 取消按钮 */
	$('#cancleBtn').click(function() {
		cancle();
	});

});

function submit() {
	confirmx('是否更新流水号生成器', function() {
		save();
	});
}
/*获取节点号长度*/
function getMaxNodeLen() {
	$.ajax({
		url : ctx + "/comp/tseq/oper/tTseqSeqCrt/getMaxNodeLen",
		type : "POST",
		dataType : "json",
		data : "",
		async : false,
		success : function(data) {
			var nodeLen = data;
			if ("" == nodeLen) {
				var errMsg = "错误信息[获取流水号长度失败]";
				showContent(errMsg, "error");
			} else {
				setI("nodeLen", nodeLen);
			}
		}
	});
}
/**
 * 保存函数--保存流水号生成器修改
 * 
 * @returns
 */
function save() {
	var formData = $("#tTseqSeqCrtForm").serializeObject();
	/* 判断数据时否变化 */
	if (formData.minVal == jsonData.minVal
			&& formData.maxVal == jsonData.maxVal
			&& formData.seqLen == jsonData.seqLen
			&& formData.respSeqLen == jsonData.respSeqLen
			&& formData.digitFlg == jsonData.digitFlg
			&& formData.efftFlg == jsonData.efftFlg
			&& formData.reptInsptStat == jsonData.reptInsptStat
			&& formData.starExpr == jsonData.starExpr
			&& formData.resetCyc == jsonData.resetCyc) {
		var successMsg = "修改交易[交易成功]";
		showContent(successMsg, "success");
		console.info("修改交易成功,数据没有变化");
		cancle();
	} else {
		/* 向后台发送参数 */
		$.post(ctx + "/comp/tseq/oper/tTseqSeqCrt/update", formData, function(
				data) {
			if (data.returnCode !== undefined && "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
				return '0';
			} else if (data.msg_type == "success") {
				var successMsg = "修改交易[" + data.message + "]";
				showContent(successMsg, "success");
				console.info("修改交易成功");
				cancle();
			}
		}, "json");
	}
}

function cancle() {
	parent.window.$("a[href^='#tab_list']").click();
}

/* 查询明细 */
function getDetail(HID_seqCrtId) {
	console.info('update tTseqSeqCrt info......');
	$.post(ctx + "/comp/tseq/oper/tTseqSeqCrt/get", {
		seqCrtId : HID_seqCrtId
	}, function(data) {
		if (data.returnCode !== undefined && "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
		} else {
			for (var i = 0; i < data.dataSetResult.length; i++) {
				for (var j = 0; j < data.dataSetResult[i].data.length; j++) {
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					jsonData = jsonObj;
					var nodeLen = parseInt($('#nodeLen').val());
					$('#outSys').multiselect("select", [ jsonObj.outSys ])
							.multiselect('rebuild');
					$('#outSubSys').val(jsonObj.outSubSys);
					$('#HID_outSys').val(jsonObj.outSys);
					$('#outSys').multiselect("disable");
					$('#seqCrtName').val(jsonObj.seqCrtName);
					$('#minVal').val(jsonObj.minVal);
					$('#maxVal').val(jsonObj.maxVal);
					$('#seqLen').val(jsonObj.seqLen);
					$('#respSeqLen').val(jsonObj.respSeqLen);
					$('#seqNoLen').val(parseInt(jsonObj.respSeqLen) - nodeLen);
					setRadioVal('digitFlg', jsonObj.digitFlg);
					setRadioVal('reptInsptStat', jsonObj.reptInsptStat);
					$('#seqCrtId').val(jsonObj.seqCrtId);
					$('#efftFlg').multiselect("select", [ jsonObj.efftFlg ])
							.multiselect('rebuild');
					$('#starExpr').val(jsonObj.starExpr);
					$('#resetCyc').val(jsonObj.resetCyc);
				}
			}

			/* 触发校验 */
			$('#outSys').blur();
			$('#outSubSys').blur();
			$('#seqCrtName').blur();
			$('#minVal').blur();
			$('#maxVal').blur();
			$('#seqLen').blur();
			$('#respSeqLen').blur();
			$('#digitFlg').blur();
			$('#seqCrtId').blur();
			$('#efftFlg').blur();
			$('#starExpr').blur();

		}
	}, "json");
}