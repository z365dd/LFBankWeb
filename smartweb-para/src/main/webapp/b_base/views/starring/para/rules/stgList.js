$(document).ready(function(){
	parent.window.$("#iframe_list").show();

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
	
	getCenters();
	
	// getTenancies();
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		tabName: (typeof(formData.tabName)==undefined)?'':formData.tabName,
		engName: (typeof(formData.engName)==undefined)?'':formData.engName,
		syncFlg: (typeof(formData.syncFlg)==undefined)?'':formData.syncFlg,
        pgside: 'server',/*服务器分页*/
        pageSize: params.limit,
        start: params.offset + 1,
        pageNo: getPage(params),
        sort: params.sort,
        order: params.order
	};
	return paramList;
}

function getCenters() {
	var datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("cacheCentrId", "/para/center/list", "id", "chName", datass, "nulls", true, false);
}

function getTenancies() {
	var datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("tntNo", "/para/rules/stg/auth/getTenanices", "engName", "name", datass, "nulls", true, false);
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
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/para/rules/stg/updatePage?id=" + id);
	parent.window.$("a[href^='#tab_update']").click();
}

/*跳转明细页面*/
function detail(id){
	console.info("open detail tab");
	$.session.set('id',id);
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/para/rules/stg/detailPage?id=" + id);
	parent.window.$("a[href^='#tab_detail']").click();
}

/*授权操作*/
function grant(id){
	console.info("open Grant tab");
	$.session.set('id',id);
	parent.window.$("a[href^='#tab_grant']").attr("url", ctx + "/para/rules/stg/grantPage?id=" + id);
	parent.window.$("a[href^='#tab_grant']").click();
}

/*删除操作*/
function delStg(obj){
	var $reviceRowData= reviceRowData(obj);
	confirmx("删除存储规则将会清空缓存数据，是否继续？", function(){
	console.info("delete rules");
	$.post(ctx + "/para/rules/stg/delStg", $reviceRowData,
			function(data){ 
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				    var errMsg = "错误信息["+data.message+"]"; 
					showContent(errMsg,"error");
					return '0';
				}else if(data.msg_type == "success"){
					var successMsg = "删除存储规则["+data.message+"]"; 
					showContent(successMsg,"success");
					$("#listBtn").click();
				}
		}, "json");
	});
}

/*停用操作*/
function stop(id){
	confirmx("是否停用此存储规则", function(){
	console.info("stop rules");
	$.post(ctx + "/para/rules/stg/stop", {id:id},
			function(data){ 
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				    var errMsg = "错误信息["+data.message+"]"; 
					showContent(errMsg,"error");
					return '0';
				}else if(data.msg_type == "success"){
					var successMsg = "停用存储规则["+data.message+"]"; 
					showContent(successMsg,"success");
					$("#listBtn").click();
				}
		}, "json");
	});
}

/*同步存储规则*/
function syncStgs(id){
	$.post(ctx + "/para/cfg/syncStgs", {id:id},
			function(data){ 
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 	
				    var errMsg = "错误信息["+data.message+"]"; 
					showContent(errMsg,"error");
					return '0';
				}else if(data.msg_type == "success"){
					var successMsg = "同步存储规则["+data.message+"]"; 
					showContent(successMsg,"success");
					$("#listBtn").click();
				}
		}, "json");
}
