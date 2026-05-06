console.log('empowerRuleList.js');
$(function(){
	/*加载模型号下拉框*/
	modelNoS('MODL_NO');
	
	/*模型下拉改变则重新加载服务码下拉框,并清空子服务码下拉框*/
	$('#MODL_NO').change(function(){
		svcCodeS('SVC_CODE',getS('MODL_NO'));
		resetS('SUB_SVC');
	});
	
	/*服务码下拉框改变则重新加载子服务码下拉框*/
	$('#SVC_CODE').change(function(){
		subSvcS('SUB_SVC',getS('MODL_NO'),getS('SVC_CODE'));
	});
	
	/*查询按钮点击进行查询*/
	$('#qryBtn').click(function(){
		freshTable("table");
		if(getS('MODL_NO')!="" && getS('MODL_NO')!="" && getS('SUB_SVC')!=""){
			enableI("saveBtn");
			enableI("upBtn");
			enableI("downBtn");
		}else{
			disabledI("saveBtn");
			disabledI("upBtn");
			disabledI("downBtn");
		}
	});
	
	/*tab1点击*/
	$('#tab1').click(function(){
		$(document).scrollTop(0);
		$('#tab2').text('授权新增');
		SAVE_OR_REV = "0";
	});
	
	/*tab2点击*/
	$('#tab2').click(function(){
		SAVE_OR_REV = "0";
		$('#tab2').text('授权新增');
		ifr('panel2','/comp/ctrl/oper/empower/empowerRuleForm');
		$("#panel2").find('iframe').load(function(){
			$(this).height($(this).contents().find('body').height()+600);
		});
	});
	
	/*table点击行变色
	给此行增加selected属性*/
	tableClick("table");
	
	/*上移*/
	$('#upBtn').click(function(){
		upSort('table',1);
	});
	
	
	/*下移*/
	$('#downBtn').click(function(){
		downSort('table',1);
	});
	
	/*保存*/
	$('#saveBtn').on("click",saveSort);
	
});


/*主页面全局变量saveOrRev
默认0为新增，1为修改，2为详细页面
用于子页面判断*/
var SAVE_OR_REV = "0"; 

/*tab1点击执行函数*/
function tab1(tp){
	$('#tab1').click();
	$(document).scrollTop(0);
	$('#tab2').text('授权新增');
	SAVE_OR_REV = "0";
	if(tp=="revice"){
		$("#qryBtn").click();
	}
	
}


//修改调用
function Revice(strJson){
	//TODO
	var Json = JSON.parse(strJson);
	$('#tab2').click().text('授权修改');
	SAVE_OR_REV = "1";
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.setVal(Json);
	});
}
//详细调用
function Detail(strJson){
	//TODO
	var Json = JSON.parse(strJson);
	$('#tab2').click().text('授权修改');
	SAVE_OR_REV = "2";
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.getDetailVal(Json);
	});
}

//删除调用
function Delete(MODL_NO,SVC_CODE,SUB_SVC,EXCT_SER_NO){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		$.post(ctx+"/comp/ctrl/oper/empower/Delete",{
//			TODO
			MODL_NO:MODL_NO,
			SVC_CODE:SVC_CODE,
			SUB_SVC:SUB_SVC,
			EXCT_SER_NO:EXCT_SER_NO
		},function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{	
				console.log(data.message);
				var successMsg = "授权删除["+data.message+"]"; 
				showContent(successMsg,"success");
				$("#qryBtn").click();
			}
		},"json");
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
//		TODO
		MODL_NO:getS('MODL_NO'),
		SVC_CODE:getS('SVC_CODE'),
		SUB_SVC:getS('SUB_SVC')
		
		
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}



/*调完顺序点保存*/
function saveSort(){
	//获取table所有行拼成json数组
	var dataArr = $('#table').bootstrapTable('getData');
	var Data = $.map(dataArr,function(n){
		delete n.ACTION;
		return n;
	});
	
	
	//数组转字符串
	//前台会自动把发送的数组转字符串
	/*{SWITCH:JSON.stringify(Data)}*/
	
	$.post(ctx+"/comp/ctrl/oper/empower/saveSort",{
		EMPOWER_ARR:JSON.stringify(Data),
		FLG:"01"
		},function(data){ 
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else{	
			console.log(data.message);
			var successMsg = "保存排序["+data.message+"]"; 
			showContent(successMsg,"success");
			$("#qryBtn").click();
		}
	},"json");
}

//页面回到顶部
function goTop(){
	$(document).scrollTop(0);
}