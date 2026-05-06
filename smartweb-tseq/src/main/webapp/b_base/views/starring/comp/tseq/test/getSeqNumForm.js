console.log('getSeqNumForm.js');


$(function(){
	//获取流水号id下拉框
	//setSelect1("SEQ_CRT_ID", "/comp/tseq/oper/tTseqSeqCrt/list", "seqCrtId", "seqCrtName");

	
	/*获取缓存流水按钮*/
	$('#getCacheSeqBtn').on('click',getCacheSeq);	
	
	/*关闭按钮*/
	$('#closeBtn').on('click',closeBtnToDo);	
	
	/*提交按钮*/
	$('#submitBtn').on('click',getSeqNo);	
	
});

/*设置时间格式*/
function formatDate(str){	
	var date = new Date();
	var Y = date.getFullYear()+"";
	var M = date.getMonth() + 1;
		M = M < 10 ? '0' + M : M;/*不够两位补充0*/
	var D = date.getDate();
		D = D < 10 ? '0' + D : D;
	var H = date.getHours();
		H = H < 10 ? '0' + H : H;
	var Mi = date.getMinutes();
		Mi = Mi < 10 ? '0' + Mi : Mi;
	var S = date.getSeconds();
		S = S < 10 ? '0' + S : S;
	var MS = date.getMilliseconds(); 
	str = str.replace(/YYYY/g, Y);
	str = str.replace(/YY/g, Y.slice(2));
	str = str.replace(/MM/g, M);
	str = str.replace(/DD/g, D);
	str = str.replace(/hh/g, H);
	str = str.replace(/mm/g, Mi);
	str = str.replace(/ss/g, S);
	str = str.replace(/SSS/g, MS);
	return str; 		
}

/*提交执行*/
function sendData(){
	if(proof()){
		var $url = ctx + "/comp/tseq/test/getSeqNum/add" ;
		var $ifasync = true;
		setI("RESP_SEQ","");
		$.ajax({
			url:$url, 
			type:"POST",
			dataType:"json",
			data:{
				SEQ_CRT_ID:getS("SEQ_CRT_ID"), 
			},
			async:$ifasync,
			success:function(data) {
				if (data.returnCode !== undefined
						&& "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					var successMsg = "提交["+data.message+"]"; 
					showContent(successMsg,"success");
					
					var Data = data.dataSetResult[0].data[0];
					Data = eval(Data);
					var RESP_SEQ = "";
					if (Data.EFFT_FLG == "Y"){
						RESP_SEQ = formatDate(Data.STAR_EXPR)
						RESP_SEQ = RESP_SEQ.replace(/SEQ/g, Data.SEQ_NODE_NO+Data.STR_SEQ);
					} else {
						RESP_SEQ = Data.SEQ_NODE_NO+Data.STR_SEQ;
					}
					setI("RESP_SEQ",RESP_SEQ);
				}
			}
		});
	}
}

/*获取流水号*/
function getSeqNo(){
	if(proof()){
		var $url = ctx + "/comp/tseq/test/getSeqNum/getSeqNo" ;
		var $ifasync = true;
		setI("RESP_SEQ","");
		$.ajax({
			url:$url, 
			type:"POST",
			dataType:"json",
			data:{
				SEQ_CRT_ID:getS("SEQ_CRT_ID"), 
			},
			async:$ifasync,
			success:function(data) {
				if (data.returnCode !== undefined
						&& "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					var successMsg = "获取流水号["+data.message+"]"; 
					showContent(successMsg,"success");
					
					var Data = data.dataSetResult[0].data[0];
					Data = eval(Data);
					setI("RESP_SEQ",Data.serNo);
				}
			}
		});
	}
}


/* 查询明细 */
function getCacheSeq(HID_seqCrtId) {
	console.info('get cacheSeq info......');
	$('#table').bootstrapTable('removeAll');
	$.post(ctx + "/comp/tseq/test/getSeqNum/getCacheSeq", {
		SEQ_CRT_ID:getS("SEQ_CRT_ID"),
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
					$('#table').bootstrapTable(
							'load', jsonData);
				}
			}
		}
	}, "json");
}


