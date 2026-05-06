console.log('quotaRuleList.js');
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
		freshTable('table');
	});
	
	/*tab1点击*/
	$('#tab1').click(function(){
		$(document).scrollTop(0);
		$('#tab2').text('新增规则');
		SAVE_OR_REV = "0";
	});
	
	/*tab2点击*/
	$('#tab2').click(function(){
		SAVE_OR_REV = "0";
		$('#tab2').text('新增规则');
		ifr('panel2','/comp/ctrl/oper/quota/quotaRuleForm');
		/*$("#panel2").find('iframe').load(function(){
			$(this).height($(this).contents().find('body').height()+600);
		});*/
	});
	
	
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
默认0为新增，1为修改
用于子页面判断*/
var SAVE_OR_REV = "0"; 

/*tab1点击执行函数*/
function tab1(){
	$('#tab1').click();
	$(document).scrollTop(0);
	$('#tab2').text('新增规则');
	SAVE_OR_REV = "0";
}


//修改调用
function Revice(strJson){
	console.log(strJson);
	var Json = JSON.parse(strJson);
	$('#tab2').click().text('修改规则');
	SAVE_OR_REV = "1";
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.setVal(Json);
	});
	
	
}

//删除调用
function Delete(MODL_NO,SVC_CODE,SUB_SVC,RULE_EXP,SER_NO){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除此限额规则吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		$.post(ctx+"/comp/ctrl/oper/quota/Delete",{
//			TODO
			MODL_NO:MODL_NO,
			SVC_CODE:SVC_CODE,
			SUB_SVC:SUB_SVC,
			SER_NO:SER_NO,
			RULE_EXP:RULE_EXP
		},function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{	
				console.log(data.message);
				var successMsg = "规则删除["+data.message+"]"; 
				showContent(successMsg,"success");
				freshTable('table');
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
	
	$.post(ctx+"/comp/ctrl/oper/quota/saveSort",{
		QUOTA_ARR:JSON.stringify(Data)
		},function(data){ 
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else{	
			console.log(data.message);
			var successMsg = "保存排序["+data.message+"]"; 
			showContent(successMsg,"success");
		}
	},"json");
}

//页面回到顶部
function goTop(){
	$(document).scrollTop(0);
}