console.log('partInfoList.js');

/*主页面全局变量SAVE_OR_REV
默认add为新增，revice为修改，detail为详细
用于子页面判断*/
var SAVE_OR_REV = "add";

$(function(){
	/*加载模型下拉框的数据*/
	setCompNoS("COMP_NO",true,true);
	
	/*模型下拉改变则重新加载服务码下拉框*/
	setSvcCodeS("COMP_NO","SVC_CODE",true);
	
	/*查询*/
	$("#qryBtn").click(function(){
		if(proof()){
			freshTable("table");
		}
	});
	
	/*tab1点击*/
	$('#tab1').click(function(){
		$('#tab2').text('参与者信息新增');
		SAVE_OR_REV = "add";
	});
	
	/*tab2点击*/
	$('#tab2').click(function(){
		$('#tab2').text('参与者信息新增');
		SAVE_OR_REV = "add";
		ifr('panel2','comp/ctrl/oper/partInfo/partInfoForm',600);
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
		COMP_NO:getS('COMP_NO'),
		SVC_CODE:getS('SVC_CODE'),
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
function Revice(compNo,svcCode){
	$('#tab2').click();
	$('#tab2').text('参与者信息修改');
	SAVE_OR_REV = "revice";
	$("#panel2").find('iframe').load(function() {
		$("#panel2").find('iframe')[0].contentWindow.setData(compNo,svcCode);
	});
}

//删除
function Delete(compNo,svcCode){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
	$.ajax({
		url:ctx+"/comp/ctrl/oper/partInfo/action", 
		type:"POST",
		dataType:"json",
		data:{
			COMP_NO:compNo, 
			SVC_CODE:svcCode,
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
