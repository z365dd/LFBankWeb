console.log('tfsvrSvrParaList.js');
$(document).ready(function(){
	parent.window.$("#iframe_list").show();

	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#tfsvrSvrParaTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#tfsvrSvrParaTable").bootstrapTable('refresh');
	});

	/*初始化表格显示列长度20*/
	$("#tfsvrSvrParaTable").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'tfsvrSvrParaTable'},{dftlen:20});
	});
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		fileSvrId: (typeof(formData.fileSvrId)==undefined)?'':formData.fileSvrId,
		ip: (typeof(formData.ip)==undefined)?'':formData.ip,
		fileSvrStat: (typeof(formData.fileSvrStat)==undefined)?'':formData.fileSvrStat,
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
function update(fileSvrId){
	console.info("open Update tab");
	var para = "?";
	$.session.set('HID_fileSvrId',fileSvrId);
	para = para + "fileSvrId="+fileSvrId;
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/comp/fsvr/tec/tfsvrSvrPara/tfsvrSvrParaUpdate" + para);
	parent.window.$("a[href^='#tab_update']").click();
}

/*跳转明细页面*/
function detail(fileSvrId){
	console.info("open detail tab");
	var para = "?";
	$.session.set('HID_fileSvrId',fileSvrId);
	para = para + "fileSvrId="+fileSvrId;
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/comp/fsvr/tec/tfsvrSvrPara/tfsvrSvrParaDetail" + para);
	parent.window.$("a[href^='#tab_detail']").click();
}

function statChange(fileSvrId, fileSvrStat) {
	var url = ctx + "/comp/fsvr/tec/tfsvrSvrPara/statChange";
	$.post(url,
			{ 
				fileSvrId:fileSvrId,
				fileSvrStat : fileSvrStat

			},
			function(data){ 
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var errMsg = "错误信息["+data.message+"]"; 
					showContent(errMsg,"error");
					return '0';
				}else if(data.msg_type == "success"){
					var successMsg = "状态修改["+data.message+"]"; 
					showContent(successMsg,"success");
					$("#listBtn").click();
				}
			}, "json");
}

/*删除操作*/
function del(fileSvrId){
	confirmx("是否确定删除该外部文件服务器信息", function(){
		var url = ctx + "/comp/fsvr/tec/tfsvrSvrPara/delete";
		//向后台发送参数
		$.post(url,
				{ 
					fileSvrId:fileSvrId

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