console.log('quotaRuleForm.js');
$(function(){
	/*返回按钮*/
	$('#cancelBtn').on('click',cancel);	
	
	/*提交按钮*/
	if(SAVE_OR_REV=="0"){
		//新增
		$('#saveBtn').on('click',save);	
		
		/*加载模型号下拉框*/
		modelNoS('MODL_NO');
	}else if(SAVE_OR_REV=="1"){
		//修改
		$('#saveBtn').on('click',revice);	
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
		$('#myModal').modal('show');
		ifr1('ruleCommon','comp/ctrl/oper/switches/ruleCommon');
	});
	/*模态框点击确定*/
	$('#mNextBtn').click(function(){
		$('#myModal').modal('hide');
		//TODO 
		//获取规则表达式返回值到主页面
		var rule = $("#ruleCommon").find('iframe')[0].contentWindow.getRULE_EXP();
		var ruleZh = $("#ruleCommon").find('iframe')[0].contentWindow.getEXP_DESC();
		$('#RULE_EXP').val(rule);	
		$('#EXP_DESC').val(ruleZh);
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
	var backS = [{label:"暂无返回中文",value:Data.MODL_NO}];
	$("select[name='MODL_NO']").multiselect('dataprovider', backS).multiselect('disable');
	
	backS = [{label:"暂无返回中文",value:Data.SVC_CODE}];
	$("select[name='SVC_CODE']").multiselect('dataprovider', backS).multiselect('disable');
	
	backS = [{label:"暂无返回中文",value:Data.SUB_SVC}];
	$("select[name='SUB_SVC']").multiselect('dataprovider', backS).multiselect('disable');
	
	$('#RULE_EXP').val(Data.RULE_EXP);
	$('#EXP_DESC').val(Data.EXP_DESC);
	
	$('#AMT_LMT_DAY').val(Data.AMT_LMT_DAY);
	$('#AMT_LMT_MONTH').val(Data.AMT_LMT_MONTH);
	$('#AMT_LMT_SEASON').val(Data.AMT_LMT_SEASON);
	$('#AMT_LMT_YEAR').val(Data.AMT_LMT_YEAR);
	$('#AMT_LMT_SIGL').val(Data.AMT_LMT_SIGL);
	
	$('#CNT_LMT_DAY').val(Data.CNT_LMT_DAY);
	$('#CNT_LMT_MONTH').val(Data.CNT_LMT_MONTH);
	$('#CNT_LMT_SEASON').val(Data.CNT_LMT_SEASON);
	$('#CNT_LMT_YEAR').val(Data.CNT_LMT_YEAR);

	//执行序号全局变量
	EXCT_SER=Data.EXCT_SER;
	//规则表达式序号全局变量
	SER_NO=Data.SER_NO;

}


/*提交执行*/
function save(){
	
	
	$.post(ctx+"/comp/ctrl/oper/quota/save",{
//		TODO
		MODL_NO:getS('MODL_NO'),
		SVC_CODE:getS('SVC_CODE'),
		SUB_SVC:getS('SUB_SVC'),
		RULE_EXP:getI('RULE_EXP'),
		EXP_DESC:getI('EXP_DESC'),
		
		AMT_LMT_DAY:getI('AMT_LMT_DAY'),
		AMT_LMT_MONTH:getI('AMT_LMT_MONTH'),
		AMT_LMT_SEASON:getI('AMT_LMT_SEASON'),
		AMT_LMT_YEAR:getI('AMT_LMT_YEAR'),
		AMT_LMT_SIGL:getI('AMT_LMT_SIGL'),
		
		CNT_LMT_DAY:getI('CNT_LMT_DAY'),
		CNT_LMT_MONTH:getI('CNT_LMT_MONTH'),
		CNT_LMT_SEASON:getI('CNT_LMT_SEASON'),
		CNT_LMT_YEAR:getI('CNT_LMT_YEAR')

	},function(data){ 
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else{	
			console.log(data.message);
			var successMsg = "规则新增["+data.message+"]"; 
			showContent(successMsg,"success");
			/*var valData = data.dataSetResult[0].data[0];*/
//			TODO
			parent.tab1();
		}
	},"json");
}

/*修改执行*/
function revice(){
	
	$.post(ctx+"/comp/ctrl/oper/empower/revice",{
		//执行序号全局变量
		EXCT_SER:EXCT_SER,
		//规则表达式序号全局变量
		SER_NO:SER_NO,
		
	
//		TODO
		MODL_NO:getS('MODL_NO'),
		SVC_CODE:getS('SVC_CODE'),
		SUB_SVC:getS('SUB_SVC'),
		RULE_EXP:getI('RULE_EXP'),
		EXP_DESC:getI('EXP_DESC'),
		
		AMT_LMT_DAY:getI('AMT_LMT_DAY'),
		AMT_LMT_MONTH:getI('AMT_LMT_MONTH'),
		AMT_LMT_SEASON:getI('AMT_LMT_SEASON'),
		AMT_LMT_YEAR:getI('AMT_LMT_YEAR'),
		AMT_LMT_SIGL:getI('AMT_LMT_SIGL'),
		
		CNT_LMT_DAY:getI('CNT_LMT_DAY'),
		CNT_LMT_MONTH:getI('CNT_LMT_MONTH'),
		CNT_LMT_SEASON:getI('CNT_LMT_SEASON'),
		CNT_LMT_YEAR:getI('CNT_LMT_YEAR')
		
	},function(data){ 
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else{	
			console.log(data.message);
			var successMsg = "规则修改["+data.message+"]"; 
			showContent(successMsg,"success");
			/*var valData = data.dataSetResult[0].data[0];*/
//			TODO
			parent.tab1();
		}
	},"json");
}

