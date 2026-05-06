console.log('prodAttrDictList.js');

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
		$('#tab2').text('属性新增');
		SAVE_OR_REV = "add";
	});
	
	/*tab2点击*/
	$('#tab2').click(function(){
		$('#tab2').text('属性新增');
		SAVE_OR_REV = "add";
		ifr('panel2','prod/oper/prodAttrDict/prodAttrDictForm');
		setIfrHeight1('panel2',600);
	});
	
	/*tab3点击*/
	$('#tab3').click(function(){
		$('#tab2').text('属性新增');
		SAVE_OR_REV = "add";
		ifr('panel2','prod/oper/prodAttrDict/prodAttrDictFileForm');
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
		KEY_NO:getI("KEY_NO"),
		KEY_TP:getS("KEY_TP"),
		KEY_NAME:getI("KEY_NAME")
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

/*详细*/
function Detail(KEY_NO){
	getDetail(KEY_NO,"detail");
} 

/*修改*/
function Revice(KEY_NO){
	getDetail(KEY_NO,"revice");
}

//删除
function Delete(KEY_NO, KEY_TP){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
	$.ajax({
		url:ctx+"/prod/oper/prodAttrDict/delete", 
		type:"POST",
		dataType:"json",
		data:{
			KEY_NO:KEY_NO,
			KEY_TP:KEY_TP
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








