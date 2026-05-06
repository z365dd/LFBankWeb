$(function(){
	console.info("子服务码列表");
	
	/*加载模型下拉框的数据*/
	setSelect1("modl","/comp/ctrl/oper/subsvccode/getModlNo","COMP_NO","COMP_NAME");
	
	/*模型下拉改变则重新加载服务码下拉框,并清空子服务码下拉框*/
	$('#modl').change(function(){
		svcCodeS('svcCode',getS('modl'));
		resetS('subSvcCode');
	});
	
	/*服务码下拉框改变则重新加载子服务码下拉框*/
	$('#svcCode').change(function(){
		subSvcS('subSvcCode',getS('modl'),getS('svcCode'));
	});
	
	/*点击查阅按钮*/
	startJudge('qryBtn');
	cOpt('modl');
	cOpt('svcCode');
	endJudge(qryBtnFun);
	
	/*双击某行直接进入修改页面*/
	/*$("#qryTable").on('dblclick','tbody tr',function(){
		var modlArr = $(this).children();
		var modlNo = modlArr.eq(2).text();
		var svcCode = modlArr.eq(3).text();
		var subSvcCode = modlArr.eq(4).text();
		var subSvcName = modlArr.eq(5).text();
		var updataStr = modlNo+","+svcCode+","+subSvcCode+","+subSvcName;
		updateAction(updataStr);
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
		COMP_NO:getS("modl"),
		SVC_CODE:getS("svcCode"),
		SUB_SVC_CODE:getS("subSvcCode")
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
	var modlNo = delstr.split(",")[0];
	var svcCode = delstr.split(",")[1];
	var subsvcCode = delstr.split(",")[2];
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		$.ajax({
			type : "POST",
			url : ctx + "/comp/ctrl/oper/subsvccode/CtrlSubSvcDel",
			data : {
				COMP_NO:modlNo,
				SVC_CODE:svcCode,
				SUB_SVC_CODE:subsvcCode
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
	//encodeURI(encodeURI(updateStr)) 解决url后参数中文乱码
	parent.showTabs("#tab_add", ctx+"/comp/ctrl/oper/subsvccode/toUpdate");
	parent.changeName("update");
	parent.setModData(updateStr);
}

/*//查询服务码加载下拉框
function svcCodeS(Name,Val){
	var Data={
			COMP_NO:Val
		}
	setSelect2("svcCode","/comp/ctrl/oper/svccode/getSvcCode","SVC_CODE","SVC_DESC",Data,Val);
	$("#svcCode").removeAttr("disabled"); 
}

//查询子服务码加载下拉框
function subSvcS(Name,Val1,Val2){
	var Data={
			COMP_NO:Val1,
			SVC_CODE:Val2
		}
	setSelect2("subSvcCode","/comp/ctrl/oper/subsvccode/getSubSvcCode","SUB_SVC_DESC",Data,Val2);
	$("#subSvcCode").removeAttr("disabled");
}*/