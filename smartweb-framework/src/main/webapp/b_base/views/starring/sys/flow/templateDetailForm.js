$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出id*/
	id = $.session.get('id');
	/*从session中移除id*/
	$.session.remove('id');
	$("#id").val(id);
	getDetail(id);
	
	/*取消按钮*/
	$("#cancleBtn").click(function(){
	    console.info("cancelBtn...");
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(id){
	console.info('get flow template info......');
	$.post(ctx + "/sys/flow/template/get", {id:id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#engName').val(jsonObj.engName);
					$('#name').val(jsonObj.name);
					$('#flowTp').multiselect("select", [jsonObj.flowTp]).multiselect('rebuild');
					$('#verNo').val(jsonObj.verNo);
					$('#dayNum').val(jsonObj.dayNum);
					$('#flowTimeOutFlg').multiselect("select", [jsonObj.flowTimeOutFlg]).multiselect('rebuild');
					$('#timeOutFlowUserTp').multiselect("select", [jsonObj.timeOutFlowUserTp]).multiselect('rebuild');
					$('#timeOutProcUserIdName').val(jsonObj.timeOutProcUserName);
                    $('#timeOutProcUserIdId').val(jsonObj.timeOutProcUserId);
                    $('#sndFlowUserTp').multiselect("select", [jsonObj.sndFlowUserTp]).multiselect('rebuild');
					$('#sndUserIdName').val(jsonObj.sndUserName);
                    $('#sndUserIdId').val(jsonObj.sndUserId);
                    $('#flowProcClssTp').multiselect("select", [jsonObj.flowProcClssTp]).multiselect('rebuild');
                    $('#rmrk').val(jsonObj.rmrk);
                    $('#applyUrl').val(jsonObj.applyUrl);
					
				}
			}
            $('#flowTp').multiselect("disable");
            $('#flowTimeOutFlg').multiselect("disable");
            $('#timeOutFlowUserTp').multiselect("disable");
            $('#sndFlowUserTp').multiselect("disable");
            $('#flowProcClssTp').multiselect("disable");
		}
	},
    "json");
}

