console.log('controllCheckList.js');
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
	/*startJudge('qryBtn');
	cOpt('MODL_NO');
	endJudge(qryBtnFun);*/
	$("#qryBtn").click(function(){
		if(proof()){
			freshTable("table");
		}
	});
	
	/*tab1点击*/
	$('#tab1').click(function(){
		$(document).scrollTop(0);
		$('#tab2').text('新增流程');
		SAVE_OR_REV = "0";
	});
	
	/*tab2点击*/
	$('#tab2').click(function(){
		SAVE_OR_REV = "0";
		$('#tab2').text('流程新增');
		ifr('panel2','/comp/ctrl/oper/controllCheck/controllCheckForm');
		$("#panel2").find('iframe').load(function(){
			$(this).height($(this).contents().find('body').height()+600);
		});
	});
	
	$('#table').on('dblclick',"tr",function(){
//		alert('双击弹出aaaaaaaaaaa'); 
	});
});


/*主页面全局变量saveOrRev
默认0为新增，1为修改
用于子页面判断*/
var SAVE_OR_REV = "0"; 

/*tab1点击执行函数*/
function tab1(tp){
	$('#tab1').click();
	$(document).scrollTop(0);
	$('#tab2').text('流程新增');
	SAVE_OR_REV = "0";
	if(tp=="revice"){
		$("#qryBtn").click();
	}
	
}


//修改调用
function Revice(strJson){
	//TODO
	var Json = JSON.parse(strJson);
	$('#tab2').click().text('流程修改');
	SAVE_OR_REV = "1";
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.setVal(Json);
	});
	
	
}

//删除调用
function Delete(MODL_NO,SVC_CODE,SUB_SVC){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		$.ajax({
			type : "POST",
			url : ctx+"/comp/ctrl/oper/controllCheck/Delete",
			data : {
				MODL_NO:MODL_NO,
				SVC_CODE:SVC_CODE,
				SUB_SVC:SUB_SVC
			},
			dataType : "json",
			success : function(data) {
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var Msg = "错误信息["+data.message+"]";
					showContent(Msg,"error");
					$("#table").bootstrapTable("removeAll");
				}else{
//					showTip("删除成功","success");
					var Msg = data.message; 
				    showContent(Msg,"success");
			        $("#table").bootstrapTable('refresh');
				}
			  }
		   });
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


//页面回到顶部
function goTop(){
	$(document).scrollTop(0);
}

/*function qryBtnFun(){
	$("#table").bootstrapTable('refresh');
}*/