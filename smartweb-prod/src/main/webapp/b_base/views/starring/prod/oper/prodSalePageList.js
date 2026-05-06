console.log('prodSalePageList.js');

/*主页面全局变量SAVE_OR_REV
默认add为新增，revice为修改，detail为详细
用于子页面判断*/
var SAVE_OR_REV = "add";


$(function(){
	$("#tab3").hide();
	//产品线
	setSelect1("PROD_LINE_CODE","/prod/oper/prodLine/qry","PROD_LINE_CODE","PROD_LINE_NAME",null,true);
	
	//产品线改变加载可售产品
	$("#PROD_LINE_CODE").change(function(){
		var data={
				PROD_LINE_CODE:getS("PROD_LINE_CODE")
		}
		setSelect2("SALE_PROD_CODE","/prod/oper/FProdSaleProd/qry","SALE_PROD_CODE","SALE_PROD_DESC",data,data.PROD_LINE_CODE,null,true);
	});
	
	/*查询*/
	$("#qryBtn").click(function(){
		if(proof()){
			freshTable("table");
		}
	});
	
	/*tab1点击*/
	$('#tab1').click(function(){
		$('#tab2').text('可售产品页面配置新增');
		SAVE_OR_REV = "add";
		$("#tab3").hide();
	});
	
	/*tab2点击*/
	$('#tab2').click(function(){
		$('#tab2').text('可售产品页面配置新增');
		SAVE_OR_REV = "add";
		ifr('panel2','prod/oper/prodSalePage/prodSalePageForm',2000);
		$("#tab3").hide();
	});

});

/*tab1点击执行函数*/
function tab1(SALE_PROD_CODE){
	goTop();
	$("#qryBtn").click();
	if(undefined == SALE_PROD_CODE){
		$('#tab1').click();
	}else{
		PerPage(SALE_PROD_CODE);
	}
	
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
		PROD_LINE_CODE:getS('PROD_LINE_CODE'),
		SALE_PROD_CODE:getS("SALE_PROD_CODE")
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

/*详细*/
function Detail(PROD_LINE_CODE,SALE_PROD_CODE){
	getDetail(PROD_LINE_CODE,SALE_PROD_CODE,"detail");
} 

/*修改*/
function Revice(PROD_LINE_CODE,SALE_PROD_CODE){
	getDetail(PROD_LINE_CODE,SALE_PROD_CODE,"revice");
}

/*获取详细数据,打开tab2传入数据*/
function getDetail(PROD_LINE_CODE,SALE_PROD_CODE,tp){
	$('#tab2').click();
	
	if(tp=="detail"){
		$('#tab2').text('可售产品页面配置详细');
		SAVE_OR_REV = "detail";
	}else if(tp=="revice"){
		$('#tab2').text('可售产品页面配置修改');
		SAVE_OR_REV = "revice";
	}
	
	$("#panel2").find('iframe').on("load",function(){
		$("#panel2").find('iframe')[0].contentWindow.backVal(PROD_LINE_CODE,SALE_PROD_CODE);
	});
}


//删除
function Delete(SALE_PROD_CODE){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
	$.ajax({
		url:ctx+"/prod/oper/prodSalePage/delete", 
		type:"POST",
		dataType:"json",
		data:{
			SALE_PROD_CODE:SALE_PROD_CODE
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

//预览
function PerPage(SALE_PROD_CODE){
	getPerData(SALE_PROD_CODE,"1");
}

