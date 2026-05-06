var MAX_SER;
console.log('controllCheckForm.js');
$(function(){
	//加载控制类型下拉框
	setSelect1("TP_DESC","/comp/ctrl/oper/controllCheck/ctrlTpS","CTRL_TP","TP_DESC");
	
	
	/*返回按钮*/
	$('#cancelBtn').on('click',cancel);	
	
	/*提交按钮*/
	if(SAVE_OR_REV=="0"){
		/*加载模型号下拉框*/
		modelNoS('MODL_NO');
		
		//新增
		/*startJudge('saveBtn');
		cOpt('MODL_NO');
		cOpt('SUB_SVC');
		cOpt('SVC_CODE');
		endJudge(save);*/
		
		$('#saveBtn').on('click',save);	
		
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
	
	/*table点击行变色
	给此行增加selected属性*/
	tableClick("table");
	
	/*增加btn，table增加一行*/

	$('#addBtn').click(function(){
		if(portion("addDiv")){
			var data={
					CTRL_TP:getS('TP_DESC'),
					TP_DESC:getST('TP_DESC'),
					ACTION:"<a href=\"JavaScript:void(0);\" onclick='reviceRow(this)'>修改</a> <a href=\"JavaScript:void(0);\" onclick='deleteRow(this)'>删除</a>"
			}
			//table增加行
			tableAddRow('table',data);
			resetForm("addDiv");
//			$("#AUTH_AMT1").removeClass();
			
		}
	});

	/*startJudge('addBtn');
	cOpt('TP_DESC');
	endJudge(tableAddRow);*/

	
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
	
	
	/*table修改行*/
	$('#reviceBtn').click(function(){
		if(portion("addDiv")){
			var data={
					CTRL_TP:getS('TP_DESC'),
					TP_DESC:getST('TP_DESC'),
					ACTION:"<a href=\"JavaScript:void(0);\" onclick='reviceRow(this)'>修改</a> <a href=\"JavaScript:void(0);\" onclick='deleteRow(this)'>删除</a>"
			}
			tableReviceRow('table',data);
			resetForm("addDiv");
		}
	});

	/*startJudge('reviceBtn');
	cOpt('TP_DESC');
	endJudge(tableReviceRow);*/

	
	
});

//tale修改返回数据
function reviceRow(obj){
	var $reviceRowData= reviceRowData(obj);
	//根据数据设置值
	setReviceRowData($reviceRowData);
}

//根据table返回的数据设置值
function setReviceRowData(data){
	setS('TP_DESC',data.CTRL_TP);
	portion("addDiv");
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
	
	$('#table').bootstrapTable('load', Data.TYPE_LIST);
	
	/*获取全部数据重新设置序号*/
	var dateArr = $("#table").bootstrapTable('getData');
	
	for(var a=0;a<dateArr.length;a++){
		dateArr[a].ORDER_NO = a+1;
		/*var ids = true;*/
		/*var ModText = "<a href=\"JavaScript:void(0);\" onClick=\"updateAction('" +dateArr[a].CTRL_TP+","+dateArr[a].TP_DESC+"')\">修改</a>"
		var DelText = "<a href=\"JavaScript:void(0);\" onClick=\"tableDeleteRow("+ids+")\">删除</a>";*/
		var ACTION = "<a href=\"JavaScript:void(0);\" onclick='reviceRow(this)'>修改</a> <a href=\"JavaScript:void(0);\" onclick='deleteRow(this)'>删除</a>";
		dateArr[a].ACTION = ACTION;
	}
	$("#table").bootstrapTable('load', dateArr);

}


/*提交执行*/
function save(){
	var index = $('#table').bootstrapTable('getData').length;
	if(index==0){
		showTip("至少增加一条规则！","success");
		return;
	}
	if(portion("commonDiv")){
		var rowArr = $('#table').bootstrapTable('getData');
		var row = JSON.stringify(rowArr);
		
		$.post(ctx+"/comp/ctrl/oper/controllCheck/save",{
//			TODO
			MODL_NO:getS('MODL_NO'),
			SVC_CODE:getS('SVC_CODE'),
			SUB_SVC:getS('SUB_SVC'),
			FLOW_ARR:row
			

		},function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{	
				console.log(data.message);
				var successMsg = "新增流程["+data.message+"]"; 
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
	var index = $('#table').bootstrapTable('getData').length;
	if(index==0){
		showTip("至少增加一条规则！","success");
		return;
	}
	if(portion("commonDiv")){
		var rowArr = $('#table').bootstrapTable('getData');
		var row = JSON.stringify(rowArr);
		
		$.post(ctx+"/comp/ctrl/oper/controllCheck/revice",{
			
		
//			TODO
			MODL_NO:getS('MODL_NO'),
			SVC_CODE:getS('SVC_CODE'),
			SUB_SVC:getS('SUB_SVC'),
			FLOW_ARR:row
			

		},function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");	
			}else{	
				console.log(data.message);
				var successMsg = "修改流程["+data.message+"]"; 
				showContent(successMsg,"success");
				/*var valData = data.dataSetResult[0].data[0];*/
//				TODO
				parent.tab1("revice");
			}
		},"json");
	}
}


/*取值，table新增一行*/
/*function tableAddRow(){
	var tableId = "table";
	var index = $('#'+tableId).bootstrapTable('getData').length;
	var ORDER_NO = index+1;
	var ids = true;
	var ModText = "<a href=\"JavaScript:void(0);\" onClick=\"updateAction('" +getS('TP_DESC')+","+getST('TP_DESC')+"')\">修改</a>"
	var DelText = "<a href=\"JavaScript:void(0);\" onClick=\"tableDeleteRow("+ids+")\">删除</a>";
	var ACTION = ModText+" "+DelText;
	var Date={
			ORDER_NO:ORDER_NO,
			CTRL_TP:getS('TP_DESC'),
			TP_DESC:getST('TP_DESC'),
			ACTION:ACTION
	}
	$('#'+tableId).bootstrapTable('insertRow', {index:index, row:Date});
}*/



/*table删除一行*/
/*function tableDeleteRow(ids){
	var tableId = "table";
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		var noArr = new Array();
//		noArr[0] = Number(ORDER_NO);
		noArr[0] = ids;
		$('#table').bootstrapTable('remove', {
			field : 'state',
			values : noArr
		});
		
		获取全部数据重新设置序号
		var dateArr = $('#'+tableId).bootstrapTable('getData');
		for(var a=0;a<dateArr.length;a++){
			dateArr[a].ORDER_NO = a+1;
		}
		$('#' + tableId).bootstrapTable('load', dateArr);
	});
}*/
/*table选择一行进行修改*/
/*function tableReviceRow(){
	var tableId = "table";
	obj = $('#'+tableId);
	var selectData = obj.bootstrapTable('getSelections');
	if(selectData.length == 0){
		showTip("请选择一行修改!", "success");
		return;
	}
	var $selRow = obj.find('tbody tr[class="selected"]').index();
	//给被选择行里的列重新赋值
	var ModText = "<a href=\"JavaScript:void(0);\" onClick=\"updateAction('" +getS('TP_DESC')+","+getST('TP_DESC')+"')\">修改</a>"
	var ids = true;
	var DelText = "<a href=\"JavaScript:void(0);\" onClick=\"tableDeleteRow("+ids+")\">删除</a>";
	var action = ModText+" "+DelText;
	obj.bootstrapTable('updateRow', {index:$selRow, row:new setObj(getS('TP_DESC'),getST('TP_DESC'),action)});
	//被选择的行
	var selectRow = obj.find('tbody tr[class="selected"]');
	selectRow.find('td:eq(2)').text(getS('TP_DESC'));
	selectRow.find('td:eq(3)').text(getST('TP_DESC'));
	obj.bootstrapTable('resetView'); 
}*/

//对象构造函数
/*function setObj(ctrlTp,tpDesc,action){
	this.CTRL_TP = ctrlTp;
	this.TP_DESC  = tpDesc;
	this.ACTION = action;
}*/

/*function updateAction(modStr){
	var CTRL_TP = modStr.split(",")[0];
	var TP_DESC = modStr.split(",")[1];
	setSelect1("TP_DESC","/comp/ctrl/oper/controllCheck/ctrlTpS","CTRL_TP","TP_DESC");
	var backS = [{label:TP_DESC,value:CTRL_TP}];
	$("select[name='TP_DESC']").multiselect('dataprovider', backS);
}*/

//根据组件号、服务码、子服务码返回最大的执行序号
/*function getExecSer(){
	var MODL_NO = getS("MODL_NO");
	var SVC_CODE = getS("SVC_CODE");
	var SUB_SVC = getS("SUB_SVC");
	if(MODL_NO!=""&&SVC_CODE!=""&&SUB_SVC!=""){
		$.ajax({
			type : "POST",
			url : ctx+"/comp/ctrl/oper/controllCheck/qrySer",
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
//					var Msg = data.message; 
//				    showContent(Msg,"success");
//			        $("#table").bootstrapTable('refresh');
					var valData=data.dataSetResult[0].data[0];
					var tpNum = valData.TP_NUM;
					if(tpNum!=undefined){
						MAX_SER = tpNum;
					}else{
						MAX_SER = 0;
					}
				}
			  }
		   });
	}
}
*/