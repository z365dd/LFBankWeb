console.log('switchesRuleForm.js');
$(function(){
	
	
	/*返回按钮*/
	$('#cancelBtn').on('click',cancel);	
	
	/*提交按钮*/
	if(SAVE_OR_REV=="0"){
		//新增
		/*startJudge('saveBtn');
		cOpt('MODL_NO');
		cOpt('SVC_CODE');
		cOpt('SUB_SVC');
		cOpt('RULE_EXP');
		endJudge(save);*/
		$("#saveBtn").on("click",save);
		
		
		/*加载模型号下拉框*/
		modelNoS('MODL_NO');
	}else if(SAVE_OR_REV=="1"){
		//修改
		/*startJudge('saveBtn');
		cOpt('RULE_EXP');
		endJudge(revice);*/
		
		$("#saveBtn").on("click",revice);
	}
	
	/*模型下拉改变则重新加载服务码下拉框,并清空子服务码下拉框*/
	$('#MODL_NO').change(function(){
		svcCodeS('SVC_CODE',getS('MODL_NO'));
		resetS('SUB_SVC');
	});
	
	/*服务码下拉框改变则重新加载子服务码下拉框*/
	$('#SVC_CODE').change(function(){
		subSvcS('SUB_SVC',getS('MODL_NO'),getS('SVC_CODE'));
	});
	
	/*点击开关定义输入框，弹出快捷定义模态框*/
	$('#RULE_EXP').click(function(){
		if(portion("commonDiv")){
			//移除开关定义非空校验
			$('#RULE_EXP').attr('check-empty','false');
			$('#myModal').modal('show');
			ifr1('ruleCommon','/comp/ctrl/oper/switches/ruleCommon');
			/*$("#ruleCommon").find('iframe').height(423);*/
			$("#ruleCommon").find('iframe').load(function() {
				$(this).height(500);
			});
		}
	});
	/*模态框点击确定*/
	$('#mNextBtn').click(function(){
		//TODO 
		//获取规则表达式返回值到主页面
		var rule = $("#ruleCommon").find('iframe')[0].contentWindow.getRULE_EXP();
		var ruleZh = $("#ruleCommon").find('iframe')[0].contentWindow.getEXP_DESC();
		var ruleMs = $("#ruleCommon").find('iframe')[0].contentWindow.getEXPR_DESC();
		/*if(ruleMs==null || ruleMs==""){
			$("#ruleCommon").find('iframe')[0].contentWindow.checkEXPR_DESC();
			return false;
		}*/
		$('#RULE_EXP').val(rule);	
		$('#EXP_DESC').val(ruleZh);
		$('#EXPR_DESC').val(ruleMs);
		$('#myModal').modal('hide');
//		proof();
	});
	
	/*模态框点击取消*/
	$('#mCancelBtn').click(function(){
		$('#myModal').modal('hide');
	});
});


/*方法-获取模型号*/
function getMODL_NO(){
	return getS('MODL_NO');
}
/*方法-获取开关规则*/
function getRULE_EXP(){
	return getI('RULE_EXP');
}
/*方法-获取开关规则中文*/
function getEXP_DESC(){
	return getI('EXP_DESC');
}
/*方法-获取开关规则中文描述*/
function getEXPR_DESC(){
	return getI('EXPR_DESC');
}

/*关闭规则定义ifr*/
/*function closeRuleCommon(){
	$('#ruleCommon').height(0);
	$('#ruleCommon').find('iframe').remove();
}*/

/*父页面变量saveOrRev
判断新增还是修改
0新增，1修改
从父页面获取*/
var SAVE_OR_REV = parent.SAVE_OR_REV;

/*返回执行*/
function cancel(){
	parent.goTop();
	Ewin.confirm({
		title : "操作提示",
		message : "数据会清空，确定返回吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		parent.tab1();
	});
}

/*根据传入的json设置值，用于修改回显*/
function setVal(Data){
//	TODO
	var backS = [{label:Data.COMP_NAME,value:Data.COMP_NO}];
	$("select[name='MODL_NO']").multiselect('dataprovider', backS).multiselect('disable');
	
	backS = [{label:Data.SVC_DESC,value:Data.SVC_CODE}];
	$("select[name='SVC_CODE']").multiselect('dataprovider', backS).multiselect('disable');
	
	backS = [{label:Data.SUB_SVC_DESC,value:Data.SUB_SVC_CODE}];
	$("select[name='SUB_SVC']").multiselect('dataprovider', backS).multiselect('disable');
	
	var EXPR = Data.EXPR.replace(/&#&/g, "\"");
	var TRANL_EXPR = Data.TRANL_EXPR.replace(/&#&/g, "\"");
	var EXPR_DESC = Data.EXPR_DESC.replace(/&#&/g, "\"");
	$('#RULE_EXP').val(EXPR);
	$('#EXP_DESC').val(TRANL_EXPR);
	$('#EXPR_DESC').val(EXPR_DESC);
	
	//执行序号全局变量
	EXCT_SER=Data.EXEC_SER;

}


/*提交执行*/
function save(){
	//添加开关定义非空校验
	$('#RULE_EXP').attr('check-empty','true');
	if(proof()){
		$.post(ctx+"/comp/ctrl/oper/switches/save",{
//			TODO
			MODL_NO:getS('MODL_NO'),
			SVC_CODE:getS('SVC_CODE'),
			SUB_SVC:getS('SUB_SVC'),
			//规则
			RULE_EXP:getI('RULE_EXP'),
			//规则翻译
			EXP_DESC:getI('EXP_DESC'),
			//规则中文描述
			EXPR_DESC:getI('EXPR_DESC')

		},function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{	
				console.log(data.message);
				var successMsg = "规则新增["+data.message+"]"; 
				showContent(successMsg,"success");
				/*var valData = data.dataSetResult[0].data[0];*/
//				TODO
				parent.tab1();
			}
		},"json");
	}
	
	
}

/*修改执行*/
function revice(){
	//添加开关定义非空校验
	$('#RULE_EXP').attr('check-empty','true');
	if(proof()){
		$.post(ctx+"/comp/ctrl/oper/switches/revice",{
			FLG:"02",
			
//			TODO
			MODL_NO:getS('MODL_NO'),
			SVC_CODE:getS('SVC_CODE'),
			SUB_SVC:getS('SUB_SVC'),
			//规则
			RULE_EXP:getI('RULE_EXP'),
			//规则翻译
			EXP_DESC:getI('EXP_DESC'),
			//规则中文描述
			EXPR_DESC:getI('EXPR_DESC'),
			//执行序号
			EXCT_SER:EXCT_SER

		},function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{	
				console.log(data.message);
				var successMsg = "规则修改["+data.message+"]"; 
				showContent(successMsg,"success");
				/*var valData = data.dataSetResult[0].data[0];*/
//				TODO
				parent.tab1("revice");
			}
		},"json");
	}
	
}

