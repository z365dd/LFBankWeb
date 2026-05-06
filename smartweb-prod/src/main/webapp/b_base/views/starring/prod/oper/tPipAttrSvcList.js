$(document).ready(function(){
	parent.window.$("#iframe_list").show();

	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#tPipAttrSvcTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#tPipAttrSvcTable").bootstrapTable('refresh');
	});

	/*初始化表格显示列长度20*/
	$("#tPipAttrSvcTable").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'tPipAttrSvcTable'},{dftlen:20});
	});
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		compNo: (typeof(formData.compNo)==undefined)?'':formData.compNo,
		svcCode: (typeof(formData.svcCode)==undefined)?'':formData.svcCode,
        pgside: 'server',/*服务器分页*/
        pageSize: params.limit,
        start: params.offset + 1,
        pageNo: getPage(params),
        sort: params.sort,
        order: params.order
	};
	return paramList;
}

function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

/*跳转更新页面*/
function update(compNo, svcCode){
	console.info("open Update tab");
	$.session.set('compNo',compNo);
	$.session.set('svcCode',svcCode);
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/prod/oper/tPipAttrSvc/tPipAttrSvcUpdate?compNo=" + compNo+ "&svcCode=" + svcCode);
	parent.window.$("a[href^='#tab_update']").click();
}

/*跳转明细页面*/
function detail(compNo, svcCode){
	console.info("open detail tab");
	$.session.set('compNo',compNo);
	$.session.set('svcCode',svcCode);
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/prod/oper/tPipAttrSvc/tPipAttrSvcDetail?compNo=" + compNo+ "&svcCode=" + svcCode);
	parent.window.$("a[href^='#tab_detail']").click();
}

/*删除操作*/
function del(compNo, svcCode){
	confirmx("是否确定删除该服务属性信息", function(){
		var url = ctx + "/prod/oper/tPipAttrSvc/delete";
		//向后台发送参数
		$.post(url,
				{
					compNo : compNo,
					svcCode : svcCode,
				},
				function(data){ 
					if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
						var errMsg = "错误信息["+data.message+"]"; 
						showContent(errMsg,"error");
						return '0';
					}else if(data.msg_type == "success"){
						var successMsg = "删除信息["+data.message+"]"; 
						showContent(successMsg,"success");
						$("#listBtn").click();
					}
				}, "json");
	});
}