console.log('signFuncCustList.js');

/*主页面全局变量SAVE_OR_REV
默认add为新增，revice为修改，detail为详细
用于子页面判断*/
var SAVE_OR_REV = "add";

$(function(){
	$("#tab2").hide();
	/*查询*/
	$("#qryBtn").click(function(){
		if(proof("top1")){
			resetTable("table");
			freshTable("table");
		}
		 
	});
	/*tab1点击*/
	$('#tab1').click(function(){
		$('#tab2').text('客户签约新增');
		SAVE_OR_REV = "add";
		$("#tab2").hide();
	});
	
	/*tab2点击*/
	$('#tab2').click(function(){
		$('#tab2').text('客户签约新增');
		SAVE_OR_REV = "add";
		ifr('panel2','/comp/sign/test/signFuncCust/signFuncCustForm');
		$("#tab2").show();
	});
	
	/*新增*/
	$("#addBtn").click(function(){
		$('#tab2').text('客户签约新增');
		SAVE_OR_REV = "add";
		ifr('panel2','/comp/sign/test/signFuncCust/signFuncCustForm');
		$("#tab2").show();
		$('#tab2').click();
	});
});

/*查询table*/
function queryParams(params){
	var paramList = { 
		pgside:'server',//服务器分页
		pageSize:params.limit,
		start:params.offset+1,
		pageNo:getPage(params),
		sort:params.sort,
		order:params.order,
		limit:params.limit,
//		TODO
		ACCT:getI('ACCT'),
		SIGN_STAT:"A",
		TP:"qry"
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

/*tab1点击执行函数*/
function tab1(){
	$('#tab1').click();
	goTop();
	$("#tab2").hide();
	$("#qryBtn").click();
}

/*详细*/
function Detail(strJson){
	getDetail(strJson,"detail");
} 

/*修改      进入修改页面先查详细信息然后修改后提交数据 不然差数据*/
function Revice(strJson){
	getDetail(strJson,"revice");
}


/*获取详细数据,打开tab2传入数据*/
function getDetail(strJson,tp){
	$('#tab2').click();
	
	if(tp=="detail"){
		$('#tab2').text('客户签约详情');
		SAVE_OR_REV = "detail";
	}else if(tp=="revice"){
		$('#tab2').text('客户签约修改');
		SAVE_OR_REV = "revice";
	}
	$("#panel2").find('iframe').load(function() {
		$("#panel2").find('iframe')[0].contentWindow.setData(decodeURIComponent(strJson));
	});
}

/*解约*/
function Can(ser,signProtTpId,signProtNo){
	Ewin.confirm({
		title : "操作提示",
		message : "确定解约吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
	var setData = {
			FLG:"N",
			SER:ser,
			SIGN_PROT_TP_ID:signProtTpId,
			SIGN_PROT_NO:signProtNo
	};
	$.ajax({
		url:ctx+"/comp/sign/test/signFuncCust/Can", 
		type:"GET",
		dataType:"json",
		data:setData,
		async:true,
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var successMsg = "解约["+data.message+"]"; 
				showTip(successMsg,"success");				
				freshTable("table");	
			}
		}
	});
	});
}


