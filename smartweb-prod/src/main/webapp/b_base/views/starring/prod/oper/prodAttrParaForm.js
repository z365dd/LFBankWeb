console.log('prodAttrForm.js');

/*
 * 父页面变量SAVE_OR_REV
 * add新增，revice修改，detail详细
 */
var SAVE_OR_REV_OR_DETAIL = parent.SAVE_OR_REV_OR_DETAIL;

//下拉框加载 同步or异步 参数
var IF_ASYNC=true;

var tableData = {};	//技术通用属性数据
var dataComp = {};	//组件下拉框数据
var dataArr = [];	//表格数据

$(function(){
	//归属标志控制属性值
	sCi("BELONG_FLG", "KV", ["01"]);
	//归属控制属性下拉复选
	$("#BELONG_FLG").on("change",ifMultiple);
	
	
	//属性选择回显属性详细信息
	$("#KEY_NO").on("change",keyDetail);
	
	/* table初始化 */
	tableClick("table");
	
	/* 增加btn，table增加一行 */
	$('#addBtnRow').click(function(){
		if(getS("KEY_TP")=="02"){
			tableActionRow('table',"02",tableData);
		}else if(getS("KEY_TP")=="03"){
			tableActionRow('table',"03",null);
		}else if(getS("KEY_TP")==""){
			showTip("请先选择属性类型");
		}
	});
	
	/* table确认修改 */
	$('#reviceBtnRow').click(function(){
		tableActionRow('table',"revice");
	});
	
	/*返回按钮*/
	$('#cancelBtn').on('click',cancel);	
	
	/*提交按钮*/
	$('#addBtn').on('click',sendData);	
	
	
	$("#pan1Div").hide();
	$("#pan2Div").hide();
	$("#KEY_TP").change(function(){
		selectAttr();
	})
	
	$("#COMP_NO").change(function(){
		$("#table").bootstrapTable('load', []);//清空表格
		if(""!=getI("COMP_NO")){
		//获取组件信息
		$.post(ctx+"/prod/oper/prodAttr/qry",{COMP_NO:getI("COMP_NO")},function(data){
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				dataComp = data.dataSetResult[0].data[0];
			}
		},"json")
		
		}
		
		//加载当前属性表格数据,判断是否存在
		$.post(ctx+"/prod/oper/prodAttrPara/getPara",{COMP_NO:getI("COMP_NO")},function(data){
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
			dataArr = data.dataSetResult[0].data;
			if(dataArr=="[]"){
				dataArr = [];
			}
			} 
		},"json")
		
		
	})
	
	if(SAVE_OR_REV_OR_DETAIL=="REV"){
		disabledS("COMP_NO");
		
	}else if(SAVE_OR_REV_OR_DETAIL=="DETAIL"){
		disabledS("COMP_NO");
		disabledS("KEY_TP");
		$("#addBtnRow").attr("disabled","true");
		$("#addDiv").hide();
	}
	
	tableClick("table");
	$("#upBtn").click(function(){
		tableUpSort("table");
	})
	$("#downBtn").click(function(){
		tableDownSort("table");
	});
	
});
//属性下拉是否复选
function ifMultiple(){
	var belongFlg = getS("BELONG_FLG");
	if(belongFlg=="02"){
		//为业务属性可以复选
		$("#KEY_NO").attr("multiple",true);
	}else{
		$("#KEY_NO").attr("multiple",false);
	}
	$("#KEY_NO").multiselect('destroy').multiselect('rebuild').multiselect('refresh');
	resetForm("keyNoDiv");
	$("#KEY_NO").change();
}

//获取当前属性列表数据
function keyDetail(){
	if(getS("KEY_NO")==undefined||getS("KEY_NO")==""){
		tableData={}
		return;
	}
	$.post(ctx+"/prod/oper/prodAttrDict/getDetail",{KEY_NO:getS("KEY_NO"),KEY_TP:getS("KEY_TP")},function(data){
		if (data.returnCode !== undefined
				&& "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
		} else {
			console.log(data.message);
			tableData = data.dataSetResult[0].data[0];
		}
	},"json")
}



//添加一行
function tableActionRow(tableId,tp,data) {
	var Date;
	if(tp=="02"){
		//判断是否存在
		for(var i = 0 ;i<dataArr.length;i++){
			if(getI("COMP_NO")==dataArr[i].COMP_NO && getS("KEY_TP")==dataArr[i].KEY_TP && getS("KEY_NO")==dataArr[i].KEY_NO){
				showTip("当前属性已存在");
				return;
			}
		}
		if(getI("COMP_NO")==""||getI("COMP_NO")==undefined){
			showTip("请先输入组件编号","error");
			return;
		}
		
		if(getS("KEY_NO")==""||getS("KEY_NO")==undefined){
			showTip("请先选择属性列表","error");
			return;
		}
		var KEY_TP_STR="技术通用属性";
		
		var ENTER_TP_STR=""
		if("01" ==data.ENTER_TP){
			ENTER_TP_STR="输入框";
		}else if("02" ==data.ENTER_TP){
			ENTER_TP_STR="下拉框";
		}else if("03" ==data.ENTER_TP){
			ENTER_TP_STR="多选框";
		}else if("url" ==data.ENTER_TP){
			ENTER_TP_STR="url";
		}
		
		Data = {
				COMP_NO:getI("COMP_NO"),
				KEY_NO : getS("KEY_NO"),
				KEY_NAME : data.KEY_NAME,
//				KEY_TP : data.KEY_TP, // 属性类型
				KEY_TP : "02", 
				KEY_TP_STR : KEY_TP_STR,
				ENTER_TP : data.ENTER_TP,	//控件类型
				ENTER_TP_STR : ENTER_TP_STR,
//				KV : data.ELEM_KV,	状态为02的不要值了
				KV : "",
				FLG : dataComp.FLG,
				ACTION : "<a onclick='delRow(this)'>删除</a>"
			}
		dataArr.push(Data);		
	}else if(tp=="03"){
		if(getI("KEY_NAME")==""||getI("KV")==""){
			showTip("请先填写属性名称和url");
			return;
		}
		
		Data={
				COMP_NO:getI("COMP_NO"),
				KEY_NAME : getI("KEY_NAME"),
				ENTER_TP : "url",
				ENTER_TP_STR : "url",
				KEY_TP : "03",
				KEY_TP_STR :"技术关联属性",
				KV : getI("KV"),
				FLG : dataComp.FLG,
				KEY_DESC:getI("KEY_DESC"),
				ACTION : "<a onclick='delRow(this)'>删除</a>"
		}
	}
	
	tableAddRow(tableId, Data);
	console.log(Data);
}

/*关闭执行*/
function cancel(){
	parent.goTop();
	Ewin.confirm({
		title : "操作提示",
		message : "确定返回吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		parent.tab1();
	});
}

/*提交执行*/
function sendData(){
	if(!proof()){
		return;
	}
	
	var addORupdate = "" ;
	if(SAVE_OR_REV_OR_DETAIL=="ADD"){
		addORupdate = "add";
	}else if(SAVE_OR_REV_OR_DETAIL=="REV"){
		addORupdate = "update";
	}
	
	var COMP_NAME = $("#COMP_NO").next().find(".multiselect-selected-text").text().split("--")[1];
	var arrData = $('#table').bootstrapTable('getData'); 
	$.post(ctx+"/prod/oper/prodAttrPara/add",
		{
			addORupdate : addORupdate,
			COMP_NO:getI("COMP_NO"),
			COMP_NAME:COMP_NAME,
			DATA_ARR:JSON.stringify(arrData)
		},
		function(data){
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				var errMsg = "[" + data.message + "]";
				showContent(errMsg, "success");
				parent.goTop();
				parent.tab1();
			}
	},"json")
}


function selectAttr(){
	if(getS("KEY_TP")=="02"){
		$("#pan1Div").show();
		$("#pan2Div").hide();
	}else if(getS("KEY_TP")=="03"){
		$("#pan1Div").hide();
		$("#pan2Div").show();
	}
} 

//页面初始化加载数据
function setData(compNo){
	$.post(ctx+"/prod/oper/prodAttrPara/getPara",{COMP_NO:compNo},function(data){
		if (data.returnCode !== undefined
				&& "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
		} else {
			 dataArr = data.dataSetResult[0].data;
			for(var i = 0;i<dataArr.length;i++){
				if(dataArr[i].KEY_TP == "02"){
					dataArr[i].KEY_TP_STR = "技术通用属性";
				}else if(dataArr[i].KEY_TP == "03"){
					dataArr[i].KEY_TP_STR = "技术关联属性";
				}
	
				if(dataArr[i].ENTER_TP == "01"){
					dataArr[i].ENTER_TP_STR = "输入框";
				}else if(dataArr[i].ENTER_TP == "02"){
					dataArr[i].ENTER_TP_STR = "下拉框";
				}else if(dataArr[i].ENTER_TP == "03"){
					dataArr[i].ENTER_TP_STR = "多选框";
				}else if(dataArr[i].ENTER_TP == ""){//为技术关联属性(类型没存入数据库 所以为空)
					dataArr[i].ENTER_TP_STR = "url";
				}
				
				dataArr[i].ACTION ="<a onclick='delRow(this)'>删除</a>";
				
			}
			setS("COMP_NO",compNo);
			$('#COMP_NO').trigger("change");
			$("#table").bootstrapTable('load', dataArr);
		}
	},"json")
}


function delRow(obj){
	$table = $(obj).parents('table');
	// 此函数的对象所在行的序号
	var $rowIndex = $(obj).parents('tbody tr').index();
	// 获取table所有数据返回json数组
	var arr = $table.bootstrapTable('getData');
	var delData = arr[$rowIndex];
	for(var i = 0 ;i<dataArr.length;i++){
		if(delData.COMP_NO==dataArr[i].COMP_NO && delData.KEY_TP==dataArr[i].KEY_TP && delData.KEY_NO==dataArr[i].KEY_NO){
			dataArr.splice(i, 1);
		}
	}
	deleteRow(obj);
}


