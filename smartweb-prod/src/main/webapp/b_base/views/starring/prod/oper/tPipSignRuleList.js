$(document).ready(function(){
	parent.window.$("#iframe_list").show();

	$("#listBtn").click(function(){
		freshTable("tPipSignRuleTable");
	})
	
	//setSelect1("busiNo", "/prod/oper/busiDemo/qry","busiNo","busiName", null, false); 
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
        pgside: 'server',/*服务器分页*/
        pageSize: params.limit,
        start: params.offset + 1,
        pageNo: getPage(params),
        sort: params.sort,
        order: params.order,
        busiName:getI('busiName'),
		busiNo:getS('busiNo'),
		ruleTp:"401"
	};
	return paramList;
}

function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

/*跳转更新页面*/
function update(ruleId){
	console.info("open Update tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_ruleId',ruleId);
	para = para + "ruleId="+ruleId;
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/prod/oper/tPipSignRule/tPipSignRuleUpdate" + para);
	parent.window.$("a[href^='#tab_update']").click();
}
