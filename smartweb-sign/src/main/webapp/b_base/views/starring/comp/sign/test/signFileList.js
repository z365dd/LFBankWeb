console.log('signFileList.js');
$(function(){
	disabledS("BAT_STAT");
	
	//提交
	$('#addBtn').on('click',batQry);
	
	$("#loadBtn").attr('disabled', 'true');
});

//对账结果查询提交
function batQry(){
	if(proof()){
		$.get(ctx+"/comp/sign/test/signFile/signFileQry",{
			ORIG_REQ_SEQ:getI("ORIG_REQ_SEQ")
		},function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{	
				console.log(data.message);
				var successMsg = "批量结果查询["+data.message+"]"; 
				showContent(successMsg,"success");
				
				 var dataVal = data.dataSetResult[0].data[0];
				
				 //设置值
				 setVal(dataVal);
			}
		},"json");
	}

}

//同步对账提交成功返回设置值
function setVal(Data){
	setS('BAT_STAT',Data.BAT_STAT);
	/*setI('RET_MSG',Data.RET_MSG);*/
	setI('FILE_SET_SEQ',Data.FILE_SET_SEQ);
	if(""==Data.FILE_SET_SEQ || undefined==Data.FILE_SET_SEQ ){
		$("#downLoadBtnFile").attr("href","");
		$("#loadBtn").attr('disabled', 'true');
	}else{
		$("#downLoadBtnFile").attr("href",ctx+"/comp/sign/test/signFile/downLoad?file="+encodeURIComponent(Data.FILE_SET_SEQ));
		$("#loadBtn").removeAttr('disabled');
	}
}



