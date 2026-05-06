$(document).ready(function(){
	parent.window.$("#iframe_list").show();

	$("#listForm").hide();
	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		let $preClick = $("#centerTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#centerTable").bootstrapTable('refresh');
	});

	$("#resetBtn").click(function(){
		clearForm("listForm");
	});

	/*初始化表格显示列长度20*/
	$("#centerTable").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'centerTable'},{dftlen:50});
	});
});

/*分页查询传参方法*/
function queryParams(params){
	let formData = $("#listForm").serializeObject();
    let paramList = {
		chName: (typeof(formData.chName)==undefined)?'':formData.chName,
		engName: (typeof(formData.engName)==undefined)?'':formData.engName,
		runStat: (typeof(formData.runStat)==undefined)?'':formData.runStat,
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
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/para/center/updatePage?id=" + id);
	parent.window.$("a[href^='#tab_update']").click();
}

/*跳转明细页面*/
function detail(id){
	console.info("open detail tab");
	$.session.set('id',id);
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/para/center/detailPage?id=" + id);
	parent.window.$("a[href^='#tab_detail']").click();
}

/*同步配置信息操作*/
function infoRelease(id){
	confirmx("是否确定将缓存中心信息同步至配置中心", function(){
		let url = ctx + "/para/cfg/infoRelease";
		//向后台发送参数
		$.post(url,
				{
					id : id,
				},
				function(data){
					if(data.returnCode!==undefined && "0000"!=data.returnCode){
						let errMsg = "错误信息["+data.message+"]";
						showContent(errMsg,"error");
						return '0';
					}else if(data.msg_type == "success"){
						let successMsg = "同步信息["+data.message+"]";
						showContent(successMsg,"success");
						$("#listBtn").click();
					}
				}, "json");
	});
}

/*导出配置文件*/
function expCfgFile(id) {
	confirmx("是否导出配置文件", function(){
		let url = ctx + "/para/center/export";
		window.open(url+"?id="+id);
	});
}

function stop(id){
	confirmx("是否停用缓存中心", function(){
		let url = ctx + "/para/center/stop";
		//向后台发送参数
		$.post(url,
				{
					id : id,
				},
				function(data){
					if(data.returnCode!==undefined && "0000"!=data.returnCode){
						let errMsg = "错误信息["+data.message+"]";
						showContent(errMsg,"error");
						return '0';
					}else if(data.msg_type == "success"){
						let successMsg = "停用["+data.message+"]";
						showContent(successMsg,"success");
						$("#listBtn").click();
					}
				}, "json");
	});
}

//function del(id){
//	confirmx("此操作可能会删除缓存数据，是否继续？", function(){
//		let url = ctx + "/para/center/del";
//		//向后台发送参数
//		$.post(url,
//				{
//					id : id,
//				},
//				function(data){
//					if(data.returnCode!==undefined && "0000"!=data.returnCode){
//						let errMsg = "错误信息["+data.message+"]";
//						showContent(errMsg,"error");
//						return '0';
//					}else if(data.msg_type == "success"){
//						let successMsg = "删除缓存中心["+data.message+"]";
//						showContent(successMsg,"success");
//						$("#listBtn").click();
//					}
//				}, "json");
//	});
//}