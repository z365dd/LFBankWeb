$(function(){
	console.info("案例列表");
	
	/*点击查阅按钮*/
	$("#qryBtn").click(function(){
		console.info("点击查询按钮.....");
//		qryBtnClick();
	    $("#qryTable").bootstrapTable('refresh');
	});
	
	/*点击取消按钮*/
	$("#cancelBtn").click(function(){
		//返回查询案例页面(不重新加载)
		parent.tab11("N");
	});
});

/*function qryBtnClick(){
	$.ajax({
		type:"POST",
		url:ctx + "/comp/ctrl/test/tranctrlchk/qryExm",
		dataType:"json",
		data:{
			caseName:$('#caseName').val()
			},
		success:function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]";
				showTip(errMsg,"error");
			}else{
				showTip("提交成功","success");
				var valData = data.dataSetResult[0].data;
				$('#qryTable').bootstrapTable('load', valData);
			}
		}
	});
}*/


/*查询table*/
function queryParams(params){
	var paramList = { 
		pgside:'server',//服务器分页
		pageSize:params.limit,
		start:params.offset+1,
		pageNo:getPage(params),
		sort:params.sort,
		order:params.order,
		caseName:$('#caseName').val()
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

//点击获取按钮 获取某个具体的案例
function getAction(str){
	console.info(str);
	var caseNo = str.split(",")[0];
	var caseName = str.split(",")[1];
	$.ajax({
		type:"POST",
		url:ctx + "/comp/ctrl/test/tranctrlchk/getExm",
		dataType:"json",
		data:{
			caseNo:caseNo,
			caseName:caseName
			},
		success:function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]";
				showTip(errMsg,"error");
			}else{
//				showTip("提交成功","success");
				var valData = data.caseJson;
				console.info(valData);
				//把获取到的数据传到父页面
				parent.tab1(valData);
			}
		}
	});
}