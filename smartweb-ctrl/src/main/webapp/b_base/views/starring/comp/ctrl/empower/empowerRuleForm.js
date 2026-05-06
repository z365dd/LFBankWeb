//全局变量
console.log('empowerRuleForm.js');
$(function(){
	/*返回按钮*/
	$('#cancelBtn').on('click',cancel);	
	
	/*提交按钮*/
	if(SAVE_OR_REV=="0"){
		//新增 $('#saveBtn').on('click',save);
		/*startJudge('saveBtn');
		cOpt('MODL_NO');
		cOpt('SVC_CODE');
		cOpt('SUB_SVC');
		cOpt('RULE_EXP');
		endJudge(save);*/
		$('#saveBtn').on('click',save);	
		/*加载模型号下拉框*/
		modelNoS('MODL_NO');
	}else if(SAVE_OR_REV=="1"){
		//修改
		/*startJudge('saveBtn');
		cOpt('RULE_EXP');
		endJudge(revice);*/
		
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
		/*if(getS("SUB_SVC")==""){
			showTip("组件号、服务码和子服务码都选择才可编写规则定义!", "success");
			return ;
		}*/
		if(portion("modelCommonDiv")){
			//移除开关定义非空校验
			$('#RULE_EXP').attr('check-empty','false');
			$('#myModal').modal('show');
			ifr1('ruleCommon','comp/ctrl/oper/switches/ruleCommon');
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
	
	/*授权方式发生改变，01:有金额授权   02:有金额授权
	则显示table、input，否则隐藏清空值*/
	$('#AUTH_MODE').change(function(){
		if(getS('AUTH_MODE')=="01"){
			$('#MoneyPower').show();
			$('#MoneyPowerBtn').show();
			$('#noMoneyPower').hide().find('input .form-control').val('');
			$('#noAUTH_TLR_LVL').val('');
		}else{
			$('#MoneyPower').hide().find('input .form-control').val('');
			$('#AUTH_TLR_LVL').removeClass("trueInput").val('');
			$('#MoneyPowerBtn').hide();
			$('#table').bootstrapTable('removeAll');
			$('#noMoneyPower').show();
		}
	});
	$('#AUTH_MODE').change();
	
	
	/*table点击行变色
	给此行增加selected属性*/
	tableClick("table");
	
	/*增加btn，table增加一行*/
	$('#addBtn').click(function(){
		$('#AUTH_TLR_LVL').attr('check-empty','true');
		if(portion("moneyDiv")&&checkAMT(getI('AUTH_AMT1'),getI('AUTH_AMT2'))){
			var Data={
					MIN_AMT:getI('AUTH_AMT1'),
					MAX_AMT:getI('AUTH_AMT2'),
					AUTH_TLR_LVL:getI('AUTH_TLR_LVL'),
					AUTH_TLR_NUM:getI('AUTH_TLR_NUM'),
					ACTION:"<a href=\"JavaScript:void(0);\" onclick='reviceRow(this)'>修改</a> <a href=\"JavaScript:void(0);\" onclick='deleteRow(this)'>删除</a>"
			}
			//table增加行
			tableAddRow('table',Data);
			resetForm("moneyDiv");
			$("#moneyDiv input").removeClass("trueInput");
			/*portion("moneyDiv")*/
		}
		
	});

	/*startJudge('addBtn');
	cOpt('AUTH_AMT1');
	cOpt('AUTH_AMT2');
	cOpt('AUTH_TLR_LVL');
	cOpt('AUTH_TLR_NUM');
	endJudge(tableAddRow);*/

	
	/*table修改行*/
	/*startJudge('reviceBtn');
	cOpt('AUTH_AMT1');
	cOpt('AUTH_AMT2');
	cOpt('AUTH_TLR_LVL');
	cOpt('AUTH_TLR_NUM');
	endJudge(tableReviceRow);*/
	
	/*table上移*/
	$('#upBtn').click(function(){
		upSort('table',1);
	});
	
	
	/*table下移*/
	$('#downBtn').click(function(){
		downSort('table',1);
	});
	
	/*table删除行*/
	/*$('#deleteBtn').click(function(){
		tableDeleteRow('table');
	});*/
	
	/*$('tbody tr td[class="bs-checkbox"]').on('click',showData);*/
	

	/*table修改行*/
	/*$('#reviceBtn').click(function(){
		tableReviceRow('table');
	});*/
	
	/*table修改行*/
	$('#reviceBtn').click(function(){
		if(portion("moneyDiv")&&checkAMT(getI('AUTH_AMT1'),getI('AUTH_AMT2'))){
			var Data={
					MIN_AMT:getI('AUTH_AMT1'),
					MAX_AMT:getI('AUTH_AMT2'),
					AUTH_TLR_LVL:getI('AUTH_TLR_LVL'),
					AUTH_TLR_NUM:getI('AUTH_TLR_NUM'),
					ACTION:"<a href=\"JavaScript:void(0);\" onclick='reviceRow(this)'>修改</a> <a href=\"JavaScript:void(0);\" onclick='deleteRow(this)'>删除</a>"
			}
			tableReviceRow('table',Data);
			/*resetForm("moneyDiv");*/
		}
		
	});
	
	

	/*金额数失去焦点后自动补.00*/
	$("#AUTH_AMT1").blur(function(){
		var money = getI("AUTH_AMT1");
		var num = changeNum(money);
		setI("AUTH_AMT1",num);

	});

	$("#AUTH_AMT2").blur(function(){
		var money = getI("AUTH_AMT2");
		var num = changeNum(money);
		setI("AUTH_AMT2",num);
	});
	
	/*可选级别下拉框改变时 为授权级别赋值*/
	$("#AUTH_TLR_LVL_S").change(function(){
		if(getS('AUTH_TLR_LVL_S')!=""){
			var s1 = getS('AUTH_TLR_LVL_S');
			var s2 = getST('AUTH_TLR_LVL_S');
//			setI('AUTH_TLR_LVL',getS('AUTH_TLR_LVL_S'));
			$('#AUTH_TLR_LVL').focus().val(getS('AUTH_TLR_LVL_S'));
		}
		$('#AUTH_TLR_LVL_S').multiselect('select', [""]).multiselect('refresh');
	});
	$("#noAUTH_TLR_LVL_S").change(function(){
		if(getS('noAUTH_TLR_LVL_S')!=""){
			setI('noAUTH_TLR_LVL',getS('noAUTH_TLR_LVL_S'));
			
			$('#noAUTH_TLR_LVL_S').multiselect('select', [""]).multiselect('refresh');
		}
	});
	

});


//tale修改返回数据
function reviceRow(obj){
	var $reviceRowData= reviceRowData(obj);
	//根据数据设置值
	setReviceRowData($reviceRowData);
}

//根据table返回的数据设置值
function setReviceRowData(data){
	/*MIN_AMT:getI('AUTH_AMT1'),
	MAX_AMT:getI('AUTH_AMT2'),
	AUTH_TLR_LVL:getI('AUTH_TLR_LVL'),
	AUTH_TLR_NUM:getI('AUTH_TLR_NUM'),*/
	
	setI('AUTH_AMT1',data.MIN_AMT);
	setI('AUTH_AMT2',data.MAX_AMT);
	setI('AUTH_TLR_LVL',data.AUTH_TLR_LVL);
	setI('AUTH_TLR_NUM',data.AUTH_TLR_NUM);
}


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
	
	setS('AUTH_MODE',Data.AUTH_METH);
	$('#AUTH_MODE').change();
	
	if(Data.AUTH_METH==01){
		var authArr = Data.AUTH_AMT_LIST;
		for(var i=0;i<authArr.length;i++){
			authArr[i].MIN_AMT = changeNum(authArr[i].MIN_AMT);
			authArr[i].MAX_AMT = changeNum(authArr[i].MAX_AMT);
			/*var MIN_AMT = authArr[i].MIN_AMT;
			var MAX_AMT = authArr[i].MAX_AMT;*/
			/*var AUTH_TLR_LVL = authArr[i].AUTH_TLR_LVL;
			var AUTH_TLR_NUM = authArr[i].AUTH_TLR_NUM;*/
			authArr[i].ORDER_NO = authArr[i].SER;
			/*var ORDER_NO = authArr[i].ORDER_NO*/
			var ACTION = "<a href=\"JavaScript:void(0);\" onclick='reviceRow(this)'>修改</a> <a href=\"JavaScript:void(0);\" onclick='deleteRow(this)'>删除</a>";
			authArr[i].ACTION = ACTION;
		}
		$('#table').bootstrapTable('load', authArr);
	}else if(Data.AUTH_METH==02){
		//无金额授权
		$('#noAUTH_TLR_LVL').val(Data.AUTH_AMT_LIST[0].AUTH_TLR_LVL);
		$('#noAUTH_TLR_NUM').val(Data.AUTH_AMT_LIST[0].AUTH_TLR_NUM);
	}
	
	
	//执行序号全局变量
	EXCT_SER=Data.EXEC_SER;

}

//详细页面数据回显
function getDetailVal(Data){
	var backS = [{label:Data.COMP_NAME,value:Data.COMP_NO}];
	$("select[name='MODL_NO']").multiselect('dataprovider', backS).multiselect('disable');
	
	backS = [{label:Data.SVC_DESC,value:Data.SVC_CODE}];
	$("select[name='SVC_CODE']").multiselect('dataprovider', backS).multiselect('disable');
	
	backS = [{label:Data.SUB_SVC_DESC,value:Data.SUB_SVC_CODE}];
	$("select[name='SUB_SVC']").multiselect('dataprovider', backS).multiselect('disable');
	
	//隐藏部分内容和按钮
//	$("input[name='radio名']").attr("disabled","disabled")
	
	var EXPR = Data.EXPR.replace(/&#&/g, "\"");
	var TRANL_EXPR = Data.TRANL_EXPR.replace(/&#&/g, "\"");
	var EXPR_DESC = Data.EXPR_DESC.replace(/&#&/g, "\"");
	$('#RULE_EXP').val(EXPR);
	$('#EXP_DESC').val(TRANL_EXPR);
	$('#EXPR_DESC').val(EXPR_DESC);
	
	setS('AUTH_MODE',Data.AUTH_METH);
//	$('#AUTH_MODE').attr("disabled","disabled");
	
	if(Data.AUTH_METH==01){
		var authArr = Data.AUTH_AMT_LIST;
		for(var i=0;i<authArr.length;i++){
			/*var MIN_AMT = authArr[i].MIN_AMT;
			var MAX_AMT = authArr[i].MAX_AMT;
			var AUTH_TLR_LVL = authArr[i].AUTH_TLR_LVL;
			var AUTH_TLR_NUM = authArr[i].AUTH_TLR_NUM;
			var ModText = "<a href=\"JavaScript:void(0);\" onClick=\"updateAction('" +MIN_AMT+","+MAX_AMT+","+AUTH_TLR_LVL+","+AUTH_TLR_NUM+"')\">修改</a>";
			var DelText = "<a href=\"JavaScript:void(0);\" onClick=\"tableDeleteRow('table')\">删除</a>";
			var ACTION = ModText+" "+DelText;*/
			authArr[i].ACTION = "";
			authArr[i].ORDER_NO = authArr[i].SER;
		}
		$('#table').bootstrapTable('load', authArr);
		
	}else if(Data.AUTH_METH==02){
		//无金额授权
		$('#noAUTH_TLR_LVL').val(Data.AUTH_AMT_LIST[0].AUTH_TLR_LVL).attr("readonly", "readonly");
		$('#noAUTH_TLR_NUM').val(Data.AUTH_AMT_LIST[0].AUTH_TLR_NUM).attr("readonly", "readonly");
		$('#noAUTH_TLR_LVL_S').multiselect('disable');
	}
	$('#AUTH_MODE').prop("disabled", true);
	
	
	//执行序号全局变量
	EXCT_SER=Data.EXEC_SER;
}


/*提交执行*/
function save(){
	//添加开关定义  非空校验
	$('#RULE_EXP').attr('check-empty','true');
	if(portion("commonDiv")){
		//授权方式上面公共部分
	}else{
		return;
	}
	if(getS('AUTH_MODE')=="01"){
		//有金额授权
		var index = $('#table').bootstrapTable('getData').length;
		if(index==0){
			showTip("至少增加一条规则！","success");
			return;
		}
	}else{
		//无
		$('#noAUTH_TLR_LVL').attr('check-empty','true');
		if(portion("noMoneyPower")){
			//授权级别 授权人数
		}else{
			return;
		}
	}	
	
	
		var rowArr = $('#table').bootstrapTable('getData');
		var row = JSON.stringify(rowArr);
		
		if(getS('AUTH_MODE')=="01"){
			//有金额授权
			var AUTH_AMT_LIST_NUM = rowArr.length;
			var AUTH_AMT_ARR = row;
			var AUTH_TLR_LVL = "";
			var AUTH_TLR_NUM = "";
		}else if(getS('AUTH_MODE')=="02"){
			//无金额授权
			var AUTH_AMT_LIST_NUM = 1;
			var AUTH_AMT_ARR = "";
			var AUTH_TLR_LVL = getI('noAUTH_TLR_LVL');
			var AUTH_TLR_NUM = getI('noAUTH_TLR_NUM');
		}
		
		
		$.post(ctx+"/comp/ctrl/oper/empower/save",{
//			TODO
			MODL_NO:getS('MODL_NO'),
			SVC_CODE:getS('SVC_CODE'),
			SUB_SVC:getS('SUB_SVC'),
			RULE_EXP:getI('RULE_EXP'),
			EXP_DESC:getI('EXP_DESC'),
			EXPR_DESC:getI('EXPR_DESC'),
			
			AUTH_MODE:getS('AUTH_MODE'),
			
			AUTH_AMT_LIST_NUM:AUTH_AMT_LIST_NUM,
			AUTH_AMT_ARR:AUTH_AMT_ARR,
			AUTH_TLR_LVL:AUTH_TLR_LVL,
			AUTH_TLR_NUM:AUTH_TLR_NUM
			

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

/*修改执行*/
function revice(){
	//添加开关定义非空校验
	$('#RULE_EXP').attr('check-empty','true');
	if(portion("commonDiv")){
		//授权方式上面公共部分
	}else{
		return;
	}
	if(getS('AUTH_MODE')=="01"){
		//有金额授权
		var index = $('#table').bootstrapTable('getData').length;
		if(index==0){
			showTip("至少增加一条规则！","success");
			return;
		}
	}else{
		//无
		$('#noAUTH_TLR_LVL').attr('check-empty','true');
		if(portion("nextCommonDiv")){
			//授权级别授权人数
		}else{
			return;
		}
	}	
	
	var rowArr = $('#table').bootstrapTable('getData');
	var row = JSON.stringify(rowArr);
	
	if(getS('AUTH_MODE')=="01"){
		//有金额授权
		var AUTH_AMT_LIST_NUM = rowArr.length;
		var AUTH_AMT_ARR = row;
		var AUTH_TLR_LVL = "";
		var AUTH_TLR_NUM = "";
	}else if(getS('AUTH_MODE')=="02"){
		//无金额授权
		var AUTH_AMT_LIST_NUM = 1;
		var AUTH_AMT_ARR = "";
		var AUTH_TLR_LVL = getI('noAUTH_TLR_LVL');
		var AUTH_TLR_NUM = getI('noAUTH_TLR_NUM');
	}
	$.post(ctx+"/comp/ctrl/oper/empower/revice",{
		FLG:"02",
		
	
		//执行序号全局变量
		EXCT_SER:EXCT_SER,

		MODL_NO:getS('MODL_NO'),
		SVC_CODE:getS('SVC_CODE'),
		SUB_SVC:getS('SUB_SVC'),
		RULE_EXP:getI('RULE_EXP'),
		EXP_DESC:getI('EXP_DESC'),
		EXPR_DESC:getI('EXPR_DESC'),
		
		AUTH_MODE:getS('AUTH_MODE'),
		
		AUTH_AMT_LIST_NUM:AUTH_AMT_LIST_NUM,
		AUTH_AMT_ARR:AUTH_AMT_ARR,
		AUTH_TLR_LVL:AUTH_TLR_LVL,
		AUTH_TLR_NUM:AUTH_TLR_NUM

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
			parent.tab1("revice");
		}
	},"json");
}




/*table删除一行*/
/*function tableDeleteRow(ORDER_NO){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		var noArr = new Array();
		noArr[0] = Number(ORDER_NO);
		$('#table').bootstrapTable('remove', {
			field : 'ORDER_NO',
			values : noArr
		});
		
		获取全部数据重新设置序号
		var dateArr = $('#table').bootstrapTable('getData');
		for(var a=0;a<dateArr.length;a++){
			dateArr[a].ORDER_NO = a+1;
		}
		$('#table').bootstrapTable('load', dateArr);

	});

	console.log(ids);
	if (ids.length == 0) {
		showTip("请选择一行删除!", "success");
		return;
	}
	$('#' + tableId).bootstrapTable('remove', {
		field : 'state',
		values : ids
	});
	
	获取全部数据重新设置序号
	var dateArr = $('#'+tableId).bootstrapTable('getData');
	for(var a=0;a<dateArr.length;a++){
		dateArr[a].EXCT_SER = a+1;
	}
	$('#' + tableId).bootstrapTable('load', dateArr);

}*/

/*function showData(){
	//获取被选择的行的数据
	var data  =$('#table').bootstrapTable('getSelections')[0];
	console.log(data);
	setI('AUTH_AMT1',data.MIN_AMT);
	setI('AUTH_AMT2',data.MAX_AMT);
	setI('AUTH_TLR_LVL',data.AUTH_TLR_LVL);
	setI('AUTH_TLR_NUM',data.AUTH_TLR_NUM);
}*/

//为输入框设值
/*function setI(ID,Value){
	$('#'+ID).val(Value);
}*/

//自动为金额输入框补上.00
function changeNum(num){  
     num += '';  
     num = num.replace(/[^0-9|\.]/g, ''); //清除字符串中的非数字非.字符  
        
      if(/^0+/) //清除字符串开头的0  
          num = num.replace(/^0+/, '');  
     if(!/\./.test(num)) //为整数字符串在末尾添加.00  
         num += '.00';  
     if(/^\./.test(num)) //字符以.开头时,在开头添加0  
         num = '0' + num;  
     num += '00';        //在字符串末尾补零  
     num = num.match(/\d+\.\d{2}/)[0];  
     return num;
}


//清空有金额授权方式 填写的数据
/*function clearAuthData(){
	$("#AUTH_AMT1").val("");
	$("#AUTH_AMT2").val("");
	$("#AUTH_TLR_LVL").val("");
	$("#AUTH_TLR_NUM").val("");
	$("#AUTH_TLR_LVL_S").val("");
}*/

//起始金额 不可大于 结束金额校验
function checkAMT(minAmt,MaxAmt){
	if(Number(minAmt)>=Number(MaxAmt)){
		showTip("起始金额 不可大于或等于 结束金额!", "error");
		return false;
	}else{
		return true;
	}
}

// 授权人数校验 范围(1 - 9)
function checkAuthNum(authNum){
	var num = getI(authNum);
	if(num<=0 || num>9){
		showTip("授权人数范围为1-9", "success");
		return;
	}
}