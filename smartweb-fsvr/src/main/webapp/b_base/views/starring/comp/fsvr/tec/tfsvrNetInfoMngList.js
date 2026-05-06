$(document).ready(function(){
	parent.window.$("#iframe_list").show();

	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#tfsvrNetInfoMngTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#tfsvrNetInfoMngTable").bootstrapTable('refresh');
	});

	/*初始化表格显示列长度20*/
	$("#tfsvrNetInfoMngTable").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'tfsvrNetInfoMngTable'},{dftlen:20});
	});
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		netRegion: (typeof(formData.netRegion)==undefined)?'':formData.netRegion,
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
function update(netRegion){
	console.info("open Update tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_netRegion',netRegion);
	para = para + "netRegion="+netRegion;
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/comp/fsvr/tec/tfsvrNetInfoMng/tfsvrNetInfoMngUpdate" + para);
	parent.window.$("a[href^='#tab_update']").click();
}

/*跳转明细页面*/
function detail(netRegion){
	console.info("open detail tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_netRegion',netRegion);
	para = para + "netRegion="+netRegion;
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/comp/fsvr/tec/tfsvrNetInfoMng/tfsvrNetInfoMngDetail" + para);
	parent.window.$("a[href^='#tab_detail']").click();
}

/*删除操作*/
function del(netRegion){
	confirmx("是否确定删除该网络信息管理信息", function(){
		var url = ctx + "/comp/fsvr/tec/tfsvrNetInfoMng/delete";
		//向后台发送参数
		$.post(url,
				{ 
					netRegion:netRegion

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