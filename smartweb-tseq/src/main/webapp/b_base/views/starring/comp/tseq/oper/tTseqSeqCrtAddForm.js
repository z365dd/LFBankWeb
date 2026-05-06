console.log("tTseqSeqCrtAddForm.js");
$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_add").show();
	$("[data-toggle='popover']").popover();
	getMaxNodeLen();
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
			$('#addForm').find('span.warnBlock:first').prev().parent().find('input:first').focus();
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
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
	$('#outSys,#outSubSys').change(function(){
		setSeqCrtId();
	})
});

function submit(){
	confirmx('是否新增流水号生成器', function(){
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
		async : true,
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
/*设置流水号生成器ID*/
function setSeqCrtId(){
	var outSys = getS('outSys');
	var outSubSys = getI('outSubSys');
	if (outSubSys!=""){
		outSys = outSys + "_" + outSubSys;
	}
	setI('seqCrtId',outSys);
	
}
/**
 * 保存函数--保存流水号生成器新增
 * @returns
 */
function save(){
	var formData = $("#addForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/comp/tseq/oper/tTseqSeqCrt/insert", formData,
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

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}