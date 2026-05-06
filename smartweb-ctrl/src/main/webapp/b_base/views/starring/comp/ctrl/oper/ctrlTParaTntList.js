$(document).ready(function(){
	parent.window.$("#iframe_list").show();

	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#ctrlTParaTntTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#ctrlTParaTntTable").bootstrapTable('refresh');
	});

	/*初始化表格显示列长度20*/
	$("#ctrlTParaTntTable").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'ctrlTParaTntTable'},{dftlen:20});
	});
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		tntNo: (typeof(formData.tntNo)==undefined)?'':formData.tntNo,
		tntName: (typeof(formData.tntName)==undefined)?'':formData.tntName,
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
function update(tntNo){
	console.info("open Update tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_tntNo',tntNo);
	para = para + "tntNo="+tntNo;
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/comp/ctrl/oper/ctrlTParaTnt/ctrlTParaTntUpdate" + para);
	parent.window.$("a[href^='#tab_update']").click();
}

/*跳转明细页面*/
function detail(tntNo){
	console.info("open detail tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_tntNo',tntNo);
	para = para + "tntNo="+tntNo;
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/comp/ctrl/oper/ctrlTParaTnt/ctrlTParaTntDetail" + para);
	parent.window.$("a[href^='#tab_detail']").click();
}

/*删除操作*/
function del(tntNo){
	confirmx("是否确定删除该租户管理信息", function(){
		var url = ctx + "/comp/ctrl/oper/ctrlTParaTnt/delete";
		//向后台发送参数
		$.post(url,
				{ 
					tntNo:tntNo

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