$(document).ready(function(){
	parent.window.$("#iframe_rules").show();

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
	
	$(".divCacheCentrId").hide();
	$(".divStorgRuleTp").hide();
	$(".divReadAuthLvl").hide();
//	getCenters();
	
	getTenancies();
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		chName: (typeof(formData.chName)==undefined)?'':formData.chName,
		engName: (typeof(formData.engName)==undefined)?'':formData.engName,
		cacheCentrId: (typeof(formData.cacheCentrId)==undefined)?'':formData.cacheCentrId,
		tntNo: (typeof(formData.tntNo)==undefined)?'':formData.tntNo,
		readAuthLvl: (typeof(formData.readAuthLvl)==undefined)?'':formData.readAuthLvl,
		storgRuleTp: (typeof(formData.storgRuleTp)==undefined)?'':formData.storgRuleTp,
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
	setSelect2("cacheCentrId", "/para/center/list", "id", "chName", datass, "nulls", false, false);
}

function getTenancies() {
	var datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("tntNo", "/para/rules/stg/auth/getTenanices", "engName", "chName", datass, "nulls", false, false);
}

function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

/*跳转查询数据页面*/
function detail(engName){
	console.info("open detail tab");
	$.session.set('engName',engName);
	parent.window.$("a[href^='#tab_data']").attr("url", ctx + "/para/chk/data/dataPage?engName=" + engName);
	parent.window.$("a[href^='#tab_data']").click();
}

