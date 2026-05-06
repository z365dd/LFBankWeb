console.log('ruleCommon.js');

$(function(){
	//规则定义返回值
	$('#RULE_EXP').val(parent.getRULE_EXP());	
	$('#EXP_DESC').val(parent.getEXP_DESC());	
	$('#EXPR_DESC').val(parent.getEXPR_DESC());	
	
	//规则下拉框查询
	expNameS('RULE_EXP_S',parent.getMODL_NO());
	//规则下拉框改变赋值并初始化
	$('#RULE_EXP_S').change(function(){
		$('#RULE_EXP').val($('#RULE_EXP').val()+" "+getS('RULE_EXP_S'));
		$('#EXP_DESC').val($('#EXP_DESC').val()+" "+getST('RULE_EXP_S'));
		$('#RULE_EXP_S').multiselect('select', [""]).multiselect('refresh');
	});
	
	//维度下拉框查询加载
	dimNoS("DIM_NO",parent.getMODL_NO());
	//维度改变赋值
	$('#DIM_NO').change(function(){
		var str = getDIM_KEY();
		if(str!=""){
			var COMP_NO = str.split("##")[0];
			var DIM_KEY = str.split("##")[1];
			var TAB_NAME = str.split("##")[2];
			$('#RULE_EXP').val($('#RULE_EXP').val()+" "+DIM_KEY+"==");
			$('#EXP_DESC').val($('#EXP_DESC').val()+" "+getST('DIM_NO')+"==");
			//该维度存在对应得表时： 加载维度值查询下拉框
			if(TAB_NAME!=null && TAB_NAME!="null"){
				dimValS('DIM_VALUE',COMP_NO,DIM_KEY);
			}else{
//				$('#DIM_VALUE').multiselect('select', [""]).multiselect('refresh');
//				$('#DIM_VALUE').multiselect('destroy');/*销毁下拉框*/
				$('#DIM_VALUE').multiselect('disable');
			}
			
		}
		$('#DIM_NO').multiselect('select', [""]).multiselect('refresh');
	});
	
	//预算符查询加载下拉框
	oprNoS('OPR_NO',parent.getMODL_NO());
	//预算符改变赋值
	$('#OPR_NO').change(function(){
		$('#RULE_EXP').val($('#RULE_EXP').val()+" "+getS('OPR_NO'));
		$('#EXP_DESC').val($('#EXP_DESC').val()+" "+getST('OPR_NO'));
		$('#OPR_NO').multiselect('select', [""]).multiselect('refresh');
	});
	
	
	//维度值改变赋值
	$('#DIM_VALUE').change(function(){
		$('#RULE_EXP').val($('#RULE_EXP').val()+'"'+getST('DIM_VALUE')+'"');
		$('#EXP_DESC').val($('#EXP_DESC').val()+'"'+getST('DIM_VALUE')+'"');
		$('#DIM_VALUE').multiselect('select', [""]).multiselect('refresh');
	});
	
	//规则输入为空时,清空规则翻译(失去焦点时)
	
	 /*$('#RULE_EXP').focus(function(){
		 if($.trim($('#RULE_EXP').val())==""){
			 $('#EXP_DESC').val("");
		 }
     });*/
	
	$('#RULE_EXP').blur(function(){
		 if($.trim($('#RULE_EXP').val())==""){
			 $('#EXP_DESC').val("");
		 }
    });
});

//获取规则定义的值，用于父页面获取值
function getRULE_EXP(){
	return getI('RULE_EXP');
}
//中文
function getEXP_DESC(){
	return getI('EXP_DESC');
}
//中文描述
function getEXPR_DESC(){
	return getI('EXPR_DESC');
}

//获取维度选择的值
function getDIM_KEY(){
	return getS('DIM_NO');
}

//查询规则加载下拉框
function expNameS(Name,Val){
	var Data={
			MODL_NO:Val
		}
	setSelect2(Name,"/comp/ctrl/oper/switches/expNameS","EXPR","TRAN_EXPR",Data,Val);
}

//查询维度加载下拉框
function dimNoS(Name,Val){
	var Data={
			MODL_NO:Val
		}
	setSelect2(Name,"/comp/ctrl/oper/switches/dimNoS","DIM_KEY","DIM_DESC",Data,Val);
}

//查询预算符加载下拉框
function oprNoS(Name,Val){
	setSelect1(Name,"/comp/ctrl/oper/switches/oprNoS","OPR","OPR_DESC");
	/*var Data={
			MODL_NO:Val
		}
	setSelect2(Name,"/comp/ctrl/oper/switches/oprNoS","OPR","OPR_DESC",Data,Val);*/
}

//查询维度值下拉框
function dimValS(Name,Val1,Val2){
	var Data={
			MODL_NO:Val1,
			DIM_KEY:Val2
		}
	setSelect2(Name,"/comp/ctrl/oper/switches/dimValS","DIM_KEY","DIM_KV",Data,Val1);
}

function checkEXPR_DESC(){
//	$("EXPR_DESC").val("请输入规则描述"); 
	showTip("错误信息[请输入规则描述]!", "success");
	$("EXPR_DESC").val("").focus(); 
}