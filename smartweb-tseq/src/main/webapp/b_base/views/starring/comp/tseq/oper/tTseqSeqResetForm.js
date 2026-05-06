console.log("tTseqSeqResetForm.js");
var jsonData ={};
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_add").show();
	/*从session中拿出seqCrtId*/
	var HID_seqCrtId = $.session.get('HID_seqCrtId');
	/*从session中移除seqCrtId*/
	$.session.remove('HID_seqCrtId');
	/*从session中拿出seqCrtId*/
	var maxSeqNum = $.session.get('maxSeqNum');
	/*从session中移除seqCrtId*/
	$.session.remove('maxSeqNum');
	getDetail(HID_seqCrtId,maxSeqNum);
	
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
	confirmx('是否更新流水号生成器', function(){
		save();
	});
}

/**
 * 保存函数--保存流水号生成器修改
 * @returns
 */
function save(){
	var formData = getData();
	console.log("***********");
	console.log(formData);
	console.log(jsonData);
	/*向后台发送参数*/
	$.post(ctx + "/comp/tseq/oper/tTseqSeqReset/update", formData,
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

function getData(){
	var data = {
				outSys: getS('outSys'),
				seqCrtId: getI('seqCrtId'),
				seqCrtName: getI('seqCrtName'),
				outSubSys: getI('outSubSys'),
				seqNoLen: getI('seqNoLen'),
				respSeqLen: getI('respSeqLen'),
				seqLen: getI('seqLen'),
				maxVal: getI('maxVal'),
				minVal: getI('minVal'),
				efftFlg: getS('efftFlg'),
				starExpr: getI('starExpr'),
				reptInsptStat:  getR('reptInsptStat'),
				resetCyc: getI('resetCyc'),
				digitFlg:  getR('digitFlg'),
				resetVal : getI('resetVal')
	};
	return data;
}
function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(HID_seqCrtId, maxSeqNum){
	console.info('update tTseqSeqReset info......');
	$.post(ctx + "/comp/tseq/oper/tTseqSeqReset/get", {seqCrtId:HID_seqCrtId}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					jsonData = jsonObj;
					$('#outSys').multiselect("select", [jsonObj.outSys]).multiselect('rebuild');
					$('#outSubSys').val(jsonObj.outSubSys);	
					$('#outSys').multiselect("disable");
					$('#seqCrtName').val(jsonObj.seqCrtName);	
					$('#minVal').val(jsonObj.minVal);	
					$('#maxVal').val(jsonObj.maxVal);	
					$('#seqLen').val(jsonObj.seqLen);	
					$('#respSeqLen').val(jsonObj.respSeqLen);	
					setRadioVal('digitFlg',jsonObj.digitFlg);
					setRadioVal('reptInsptStat',jsonObj.reptInsptStat);
					$('#seqCrtId').val(jsonObj.seqCrtId);	
					$('#efftFlg').multiselect("select", [jsonObj.efftFlg]).multiselect('rebuild');
					$('#efftFlg').multiselect("disable");
					$('#starExpr').val(jsonObj.starExpr);	
					$('#resetCyc').val(jsonObj.resetCyc);	
					$('#maxSeqNum').val(maxSeqNum);	
				}
			}
			
			/*触发校验*/
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
	},
    "json");
}