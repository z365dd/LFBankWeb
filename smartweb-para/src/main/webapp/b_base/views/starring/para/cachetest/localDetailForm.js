$(document).ready(function(){
	parent.window.$("#iframe_detail").show();
	chgHeight(700);
	/*从session中拿出dataKey*/
	dataKey = $.session.get('dataKey');
	$.session.remove('dataKey');
	
	getRules();
	
	var rules = dataKey.split("&@&")[0];
	var uKey = dataKey.split("&@&")[1];
	var rulesKey = rules+"&@&"+uKey;
	$('#rules').multiselect("select", rulesKey).multiselect('rebuild');
	$('#rules').multiselect("disable")
	$("#uniqueKey").val(uKey);
	$("#dataKey").val(dataKey);
	
	$("#detailTable").bootstrapTable('refresh');
	
	$(".cancleBtn").click(function(){
		cancle();
	});
});

function getRules() {
	var datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("rules", "/para/test/local/getRules", "rulesUnixKey", "chName", datass, "nulls", true, false);
}


/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
    	dataKey: (typeof(formData.dataKey)==undefined)?'':formData.dataKey,
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


function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*改变内容高度*/
function chgHeight(h){
	var Height =$(document.body).height() + h;
	$(window.parent.document).find("#tab_detail").find('iframe').height(Height);
}