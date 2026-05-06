console.log("chkRuleList.js");
$(function(){
	/*tab2点击*/
	$('#tab2').click(function(){
		$("#tab2").show();
		ifr('panel2','/prod/oper/chkRule/chkRuleForm',1000);
	});
	
	$("#tab1").click(function(){
		$("#tab2").hide();
	})
	
	$("#qryBtn").click(function(){
		freshTable("table");
	})
	
	$("#tab1").click();

	//setSelect1("busiNo", "/prod/oper/busiDemo/qry","busiNo","busiName", null, false); 
})


function update(RULE_ID){
	$('#tab2').click();
	$("#panel2").find('iframe').on("load",function(){
		$("#panel2").find('iframe')[0].contentWindow.setData(RULE_ID);
	});
}


/*查询table*/
function queryParams(params){
	var paramList = { 
		pgside:'server',//服务器分页
		pageSize:params.limit,
		start:params.offset+1,
		pageNo:getPage(params),
		sort:params.sort,
		order:params.order,
		busiName:getI('busiName'),
		busiNo:getS('busiNo'),
		ruleTp:"101"
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}