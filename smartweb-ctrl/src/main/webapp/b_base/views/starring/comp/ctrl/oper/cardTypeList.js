console.log('cardTypeList.js');

/*主页面全局变量SAVE_OR_REV
默认add为新增，revice为修改，detail为详细
用于子页面判断*/
var SAVE_OR_REV = "add";

$(function(){
	/*查询*/
	$("#qryBtn").click(function(){
		if(proof()){
			freshTable("table");
		}
	});
	
	/*tab1点击*/
	$('#tab1').click(function(){
		$('#tab2').text('卡种类新增');
		SAVE_OR_REV = "add";
	});
	
	/*tab2点击*/
	$('#tab2').click(function(){
		$('#tab2').text('卡种类新增');
		SAVE_OR_REV = "add";
		ifr('panel2','comp/ctrl/oper/cardType/cardTypeForm',600);
	});
});


/*tab1点击执行函数*/
function tab1(){
	$('#tab1').click();
	goTop();
	$("#qryBtn").click();
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
//		TODO
		CARD_BIN_NO:getI('CARD_BIN_NO'),
		//CARD_TP:getS('CARD_TP'),
		//ACCT_CARD_FLG:getS('ACCT_CARD_FLG'),
		//NET_NO:getI('NET_NO'),
		LEGA_NO:getS('LEGA_NO'),
		OPER_TP:QRY,
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

/*修改*/
function Revice(CARD_BIN_NO,CARD_TP,ACCT_CARD_FLG,NET_NO,LEGA_NO){
	$('#tab2').click();
	$('#tab2').text('卡种类修改');
	SAVE_OR_REV = "revice";
	$("#panel2").find('iframe').load(function() {
		$("#panel2").find('iframe')[0].contentWindow.setData(CARD_BIN_NO,CARD_TP,ACCT_CARD_FLG,NET_NO,LEGA_NO);
	});
}

//删除
function Delete(CARD_BIN_NO,CARD_TP,ACCT_CARD_FLG,NET_NO,LEGA_NO){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
	$.ajax({
		url:ctx+"/comp/ctrl/oper/cardType/action", 
		type:"POST",
		dataType:"json",
		data:{
			CARD_BIN_NO:CARD_BIN_NO, 
			CARD_TP:CARD_TP,
			ACCT_CARD_FLG:ACCT_CARD_FLG,
			NET_NO:NET_NO,
			LEGA_NO:LEGA_NO,
			OPER_TP:DEL
		},
		async:true,
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var successMsg = "删除["+data.message+"]"; 
				showContent(successMsg,"success");
				$("#qryBtn").click();
			}
		}
	});
	});
}
