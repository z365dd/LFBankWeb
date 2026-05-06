
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出seqCrtId*/
	var HID_seqCrtId = $.session.get('HID_seqCrtId');
	/*从session中移除seqCrtId*/
	$.session.remove('HID_seqCrtId');
	$("#HID_seqCrtId").val(HID_seqCrtId);
	getMaxNodeLen();
	getDetail(HID_seqCrtId);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
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
/*查询明细*/
function getDetail(seqCrtId){
	console.info('get tTseqSeqCrt info......');
	$.post(ctx + "/comp/tseq/oper/tTseqSeqCrt/get", {seqCrtId:seqCrtId}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					var nodeLen = parseInt($('#nodeLen').val());
					$('#outSys').multiselect("select", [jsonObj.outSys]).multiselect('rebuild');
					$('#outSys').multiselect("disable");
					$('#outSubSys').val(jsonObj.outSubSys);	
					$('#seqCrtName').val(jsonObj.seqCrtName);	
					$('#minVal').val(jsonObj.minVal);	
					$('#maxVal').val(jsonObj.maxVal);	
					$('#seqLen').val(jsonObj.seqLen);	
					$('#respSeqLen').val(jsonObj.respSeqLen);	
					$('#seqNoLen').val(parseInt(jsonObj.respSeqLen) - nodeLen);
					setRadioVal('digitFlg',jsonObj.digitFlg);
					setRadioVal('reptInsptStat',jsonObj.reptInsptStat);
					$('#seqCrtId').val(jsonObj.seqCrtId);	
					$('#efftFlg').multiselect("select", [jsonObj.efftFlg]).multiselect('rebuild');
					$('#efftFlg').multiselect("disable");
					$('#starExpr').val(jsonObj.starExpr);	
					$('#resetCyc').val(jsonObj.resetCyc);	
				}
			}

		}
	},
    "json");
}