$(document).ready(function(){
	parent.window.$("#iframe_rules").show();

	var agentKey = $.session.get('agentKey');/*tenant_partId_partVersion_agentSeq*/
	/*从session中移除id*/
	$.session.remove('agentKey');
	$("#agentKey").val(agentKey);
	
	$("#rulesTable").bootstrapTable('refresh');
	
	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#rulesTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#rulesTable").bootstrapTable('refresh');
	});

	$("#resetBtn").click(function(){
		clearForm("#listForm");
	});
	
	/*初始化表格显示列长度20*/
	$("#rulesTable").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'rulesTable'},{dftlen:20});
	});
	
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		name: (typeof(formData.name)==undefined)?'':formData.name,
		enname: (typeof(formData.enname)==undefined)?'':formData.enname,
		agentKey: (typeof(formData.agentKey)==undefined)?'':formData.agentKey,
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

/*跳转查询数据页面*/
function detail(key){
	console.info("open detail tab");
	$.session.set('key',key);
	parent.window.$("a[href^='#tab_agentData']").attr("url", ctx + "/para/chk/agent/agentDataPage?key=" + key);
	parent.window.$("a[href^='#tab_agentData']").click();
}

