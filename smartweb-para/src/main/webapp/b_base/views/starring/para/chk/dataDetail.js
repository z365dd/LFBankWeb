$(document).ready(function(){
	parent.window.$("#iframe_detail").show();
	/*从session中拿出id*/
	redisKey = $.session.get('redisKey');
	/*从session中移除id*/
	$.session.remove('redisKey');
	var enname = redisKey.split("/")[2];
	$("#redisKey").val(redisKey);
	$("h3").html("数据详情："+redisKey);
	$("#detailTable").bootstrapTable('refresh');
	
	/*取消按钮*/
	$("button[id^='cancle']").click(function(){
		cancle();
	});
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		redisKey: (typeof(formData.redisKey)==undefined)?'':formData.redisKey,
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
	parent.window.$("a[href^='#tab_rules']").click();
}

/*改变内容高度*/
function chgHeight(h) {
	var Height =$(document.body).height() + h;
	$(window.parent.document).find("#tab_detail").find('iframe').height(Height);
}