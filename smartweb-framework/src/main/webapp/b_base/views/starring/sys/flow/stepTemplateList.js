$(document).ready(function(){
	parent.window.$("#iframe_list").show();

	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#stepTemplateTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#stepTemplateTable").bootstrapTable('refresh');
	});

	//加载成功监听事件
	$("#stepTemplateTable").on('load-success.bs.table', function (event, data) {
        /*初始化表格显示列长度20*/
        console.info(data);
		SmartWeb.swJS.index.init({id:'stepTemplateTable'},{dftlen:20});
	});
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		flowTmplId: (typeof(formData.flowTmplId)==undefined)?'':formData.flowTmplId,
		name: (typeof(formData.name)==undefined)?'':formData.name,
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
function update(id){
	console.info("open Update tab");
	$.session.set('id',id);
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/sys/flow/stepTemplate/stepTemplateUpdate?id=" + id);
	parent.window.$("a[href^='#tab_update']").click();
}

/*跳转明细页面*/
function detail(id){
	console.info("open detail tab");
	$.session.set('id',id);
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/sys/flow/stepTemplate/stepTemplateDetail?id=" + id);
	parent.window.$("a[href^='#tab_detail']").click();
}

/*删除操作*/
function del(id){
	confirmx("是否确定删除该步骤模板信息", function(){
		var url = ctx + "/sys/flow/stepTemplate/delete";
		//向后台发送参数
		$.post(url,
				{ 
					id : id
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
