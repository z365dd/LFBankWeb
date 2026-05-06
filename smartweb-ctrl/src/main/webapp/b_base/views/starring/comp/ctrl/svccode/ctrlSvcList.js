$(function(){
	console.info("服务码列表");
	
	/*加载模型下拉框的数据*/
	setSelect1("modlNo","/comp/ctrl/oper/svccode/getModlNo","COMP_NO","COMP_NAME");
	
	/*模型下拉改变则重新加载服务码下拉框*/
	$('#modlNo').change(function(){
		svcCodeS('svcCode',getS('modlNo'));
	});
	
	/*点击查阅按钮*/
	startJudge('qryBtn');
	cOpt('modlNo');
	endJudge(qryBtnFun);
	
	/*双击某行直接进入修改页面*/
	/*$("#qryTable").on('dblclick','tbody tr',function(){
		var modlArr = $(this).children();
		var modlNo = modlArr.eq(2).text();
		var svcName = modlArr.eq(3).text();
		var svcCode = modlArr.eq(4).text();
		var updataStr = modlNo+","+svcName+","+svcCode;
		if(modlNo!=""){
			updateAction(updataStr);
		}
	});*/
});

function qryBtnFun(){
	$("#qryTable").bootstrapTable('refresh');
}

/*查询table*/
function queryParams(params){
	var formData = $("#qryForm").serializeObject();
	var paramList = { 
		pgside:'server',//服务器分页
		pageSize:params.limit,
		start:params.offset+1,
		pageNo:getPage(params),
		sort:params.sort,
		order:params.order,
		COMP_NO:getS("modlNo"),
		SVC_CODE:getS("svcCode")
//		MODL_TYPE:formData.modlTypeSel
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

function delAction(delstr){
	var modlno = delstr.split(",")[0];
	var svcCode = delstr.split(",")[1];
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
	$.ajax({
		type : "POST",
		url : ctx + "/comp/ctrl/oper/svccode/CtrlSvcDel",
		data : {
			COMP_NO:modlno,
			SVC_CODE:svcCode
		},
		dataType : "json",
		success : function(data) {
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var Msg = "错误信息["+data.message+"]";
				showContent(Msg,"error");
			}else{
				var Msg = data.message; 
			    showContent(Msg,"success");
		        $("#qryTable").bootstrapTable('refresh');
			}
		  }
	   });
  });
}

function updateAction(updateStr){
	console.info(updateStr);
	parent.showTabs("#tab_add", ctx+"/comp/ctrl/oper/svccode/toUpdate");
	parent.changeName("update");
	parent.setModData(updateStr);
}

function svcCodeS(Name,Val){
	var Data={
			COMP_NO:Val
		}
	setSelect2("svcCode","/comp/ctrl/oper/svccode/getSvcCode","SVC_CODE","SVC_DESC",Data,Val);
}