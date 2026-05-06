console.log('prodSalePageForm.js');

/*
 * 父页面变量SAVE_OR_REV
 * add新增，revice修改，detail详细
 */
var SAVE_OR_REV = parent.SAVE_OR_REV;

//暂存全部数据
//key为原子产品码ATOM_PROD_CODE，val为属性list
var ATOM_KEY_DATA = {};

//上次选择的原子产品
var BACK_ATOM = "";

//下拉框加载 同步or异步 参数
var IF_ASYNC=true;
if(SAVE_OR_REV !="add"){
	IF_ASYNC=false;
}

//设置回显数据的全局变量
var SET_DATA = {};


$(function(){
	$("#ifAddDiv").hide();
	$("#setDiv").hide();
	
	//产品线
	setSelect1("PROD_LINE_CODE","/prod/oper/prodLine/qry","PROD_LINE_CODE","PROD_LINE_NAME",null,IF_ASYNC);
	
	//产品线改变加载可售产品
	$("#PROD_LINE_CODE").change(function(){
		var data={
				PROD_LINE_CODE:getS("PROD_LINE_CODE")
		}
		setSelect2("SALE_PROD_CODE","/prod/oper/FProdSaleProd/qry","SALE_PROD_CODE","SALE_PROD_DESC",data,data.PROD_LINE_CODE,null,IF_ASYNC);
	});
	
	//选择可售产品加载原子产品
	$("#SALE_PROD_CODE").change(function(){
		var data={
				SALE_PROD_CODE:getS("SALE_PROD_CODE")
		}
		//同步加载，完成进行详细查询，暂存数据
		setSelect2("ATOM_PROD_CODE","/prod/oper/prodSalePage/qryAtom","ATOM_PROD_CODE","ATOM_PROD_DESC",data,data.SALE_PROD_CODE,null,false,true);
		//查询全部详细数据进行数据暂存回显
		detail();
	});
	
	//改变原子产品，根据暂存数据加载列表
	$("#ATOM_PROD_CODE").on("change",backTable);
	
	/*查询,可售产品页面配置详细*/
	/*$("#qryBtn").click(function(){
		if(portion("qryDiv")){
			detail();
		}
	});*/
	
	/* table初始化 */
	tableClick("table1");
	/* 确认设置 */
	$('#setBtn').click(function(){
		tableActionRow('table1');
	});
	
	/*table上移*/
	$('#upBtn').click(function(){
		tableUpSort("table1",null)
	});
	
	
	/*table下移*/
	$('#downBtn').click(function(){
		tableDownSort('table1',null);
	});
	
	//合并，不合并
	$("#mergeBtn").on("click",merge);
	$("#nmergeBtn").on("click",nmerge);
	
	/*返回按钮*/
	$('#cancelBtn').on('click',cancel);	
	
	/*提交按钮*/
	$('#addBtn').on('click',sendData);	
	
});
//改变原子产品，根据暂存数据回显
//这个改变旨在初始化过后，界面选择才调用
function backTable(){
	//获取此时列表数据跟新暂存
	freshKeyData();
	
	//设置当前原子产品全局变量
	//用于下一次重新取值（列表可能会全部删除，需要知道上一次的原子产品）
	BACK_ATOM = getS("ATOM_PROD_CODE");
	
	//根据此时原子产品获取暂存数据回显列表
	backTableData(BACK_ATOM,1);
}

//根据选择的原子产品获取暂存中的数据回显列表
//tp==1，获取数据加载列表
//tp==2，获取数据arr
function backTableData(ATOM_PROD_CODE,tp){
	var tableArr = [];
	if(ATOM_PROD_CODE == ""){
		for(key in ATOM_KEY_DATA){
			tableArr = tableArr.concat(ATOM_KEY_DATA[key]);
		}
	}else{
		if(ATOM_KEY_DATA[ATOM_PROD_CODE]!=undefined){
			tableArr = ATOM_KEY_DATA[ATOM_PROD_CODE];
		}
	}
	if(tp==1){
		$('#table1').bootstrapTable('load',tableArr);
		ifrChildAut('panel2',600);
	}else if(tp==2){
		return tableArr;
	}
	
}

//获取此时数据跟新暂存
function freshKeyData(Arr){
	var arr;
	if(undefined!=Arr){
		arr = Arr;
	}else{
		arr = $('#table1').bootstrapTable('getData');
	}
	//判断之前的原子产品清空对应数据
	if(BACK_ATOM==""){
		//之前是全部选项，此时列表数据全部删除，清空暂存
		ATOM_KEY_DATA = {};
	}else {
		//把此原子产品的暂存数据清除
		delete ATOM_KEY_DATA[BACK_ATOM];
	}
	if(arr.length>0){
		//获取当前列表数据存于暂存
		parseData(arr);
	}
}

//对查询的数据进行处理，初始存于全局变量
function parseData(arr){
	for(var a=0;a<arr.length;a++){
		//每一条数据
		var data = arr[a];
		//原子产品号
		var ATOM_PROD_CODE = data.ATOM_PROD_CODE;
		if(ATOM_KEY_DATA[ATOM_PROD_CODE]==undefined){
			//还未存储数据
			ATOM_KEY_DATA[ATOM_PROD_CODE] = [];
		}
		ATOM_KEY_DATA[ATOM_PROD_CODE].push(data);
	}
}


//合并
function merge(){
	//当前选中的数据
	var rowData = getSRowData("table1");
	//标志为已合并
	rowData.KEY_FLG = "02";
	
	tableReviceRow("table1",rowData);
	$("#ifAddDiv").hide();
}
//不合并
function nmerge(){
	var rowArr = $('#table2').bootstrapTable('getData');
	for(var a=0;a<rowArr.length;a++){
		var rowData  =rowArr[a];
		//标志为无
		rowData.KEY_FLG = "02";
		//操作
		if(SAVE_OR_REV=="detail"){
			rowData.ACTION  = "<a onclick='reviceRow(this)'>查看</a>";
		}else{
			rowData.ACTION  = "<a onclick='reviceRow(this)'>设置</a> <a onclick='deleteRow(this)'>删除</a>";
		}
		if(a==0){
			tableReviceRow("table1",rowData);
		}else{
			tableAddRow("table1",rowData);
		}
		ifrChildAut('panel2',600);
	}
	$("#ifAddDiv").hide();
}


//查询获取可售产品页面配置详细
function detail(){
	//上次选择的原子产品初始化
	BACK_ATOM = "";
	if(getS('SALE_PROD_CODE')==""){
		//清空暂存
		ATOM_KEY_DATA={};
		return;
	}
	$.ajax({
		url:ctx+"/prod/oper/prodSalePage/detail", 
		type:"POST",
		dataType:"json",
		data:{
			SALE_PROD_CODE:getS('SALE_PROD_CODE'), 
			//初始为全部属性，为空
			ATOM_PROD_CODE:getS("ATOM_PROD_CODE")
		},
		async:IF_ASYNC,
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
				//清空暂存
				ATOM_KEY_DATA={};
			} else {
				console.log(data.message);
				var successMsg = "查询数据["+data.message+"]"; 
				showContent(successMsg,"success");

				var Data = data.dataSetResult[0].data[0];
				if(Data.FProdSaleProdPageDtlRes=="" || Data.FProdSaleProdPageDtlRes==undefined){
					var errMsg = "配置错误！没有可售产品配置详细。";
					showContent(errMsg, "error");
				}else{
					Data = JSON.parse(Data.FProdSaleProdPageDtlRes);
					var arr = [];
					if(Data.LIST!=undefined){
						arr = Data.LIST;
					}
					
					//设置回显列表值
					setData(arr);
				}
			}
		}
	});
}



//详细查询回显列表
function setData(tableArr){
	if(undefined != tableArr){
		for(var a=0;a<tableArr.length;a++){
			var data = tableArr[a];
			if(SAVE_OR_REV=="detail"){
				data.ACTION  = "<a onclick='reviceRow(this)'>查看</a>";
			}else{
				data.ACTION  = "<a onclick='reviceRow(this)'>设置</a> <a onclick='deleteRow(this)'>删除</a>";
			}	
		}
		
		//获取传入数据存于暂存
		freshKeyData(tableArr);
		
		//根据暂存数据设置table
		backTableData("",1);
		
		if(SAVE_OR_REV=="detail"){
			$("#setBtn").hide();
			disDiv("formId_286247");
		}else if (SAVE_OR_REV =="revice"){
			//disDiv("qryDiv");
			disabledS("PROD_LINE_CODE");
			disabledS("SALE_PROD_CODE");
		}
		//$('#table1').bootstrapTable('load',tableArr);
	}
}


/*table确认设置 */
function tableActionRow(tableId){
	if(portion("setDiv")){
		SET_DATA.LINE_SER = getI("LINE_SER");
		SET_DATA.COL_SER = getI("COL_SER");
		SET_DATA.FLG = getS("FLG");
		tableReviceRow(tableId,SET_DATA);
	}
}

//tale修改返回数据
function reviceRow(obj){
	SET_DATA= reviceRowData(obj);
	// 根据数据设置值
	setReviceRowData(SET_DATA);
}

//点击设置回显值
function setReviceRowData(data){
	if(data.KEY_FLG=="01"){
		//待合并
		$("#ifAddDiv").show();
		$("#setDiv").hide();
		var SUB_LIST = eval(data.SUB_LIST);
		if(SUB_LIST!=undefined){
			/*for(var a=0;a<SUB_LIST.length;a++){
				var subData = SUB_LIST[a];
				//var CTRL_LIST_STR = JSON.stringify(subData.CTRL_LIST);
				//subData.CTRL_LIST_STR = CTRL_LIST_STR;
				SUB_LIST[a] = subData;
			}*/
			$('#table2').bootstrapTable('load',SUB_LIST);
			ifrChildAut('panel2',600);
		}
	}else {
		$("#setDiv").show();
		$("#ifAddDiv").hide();
		setI("LINE_SER",data.LINE_SER);
		setI("COL_SER",data.COL_SER);
		setS("FLG",data.FLG);
		
		ifr("prodAttrDictDiv",'prod/oper/prodAttrDict/prodAttrDictForm',600);
		$("#prodAttrDictDiv").find('iframe').on("load",function(){
			$("#prodAttrDictDiv").find('iframe')[0].contentWindow.setData(data,"detail",false,"",true);
			ifrChildAut('panel2',600);
		});
	}
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

/*获取页面数据data*/
function getData(){
	//获取此时数据刷新暂存
	freshKeyData();
	//获取暂存值
	var rowArr = backTableData("",2);
	var row = JSON.stringify(rowArr);
	
	var data= {
			SALE_PROD_CODE:getS("SALE_PROD_CODE"),
			LIST:row
	}
	return data;
}

/*提交执行*/
function sendData(){
	if(portion("commonDiv")){
		var url = ctx ;
		var Data = getData();
		
		if(SAVE_OR_REV=="add"){
			url += "/prod/oper/prodSalePage/add";
		}else if(SAVE_OR_REV=="revice"){
			url += "/prod/oper/prodSalePage/revice";
		}
		$.ajax({
			url:url, 
			type:"POST",
			dataType:"json",
			data:Data, 
			async:true,
			success:function(data) {
				if (data.returnCode !== undefined
						&& "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					var successMsg = "提交["+data.message+"]"; 
					showContent(successMsg,"success");
					parent.tab1(Data.SALE_PROD_CODE);
				}
			}
		});
	}
}


//回显值再作查询
function backVal(PROD_LINE_CODE,SALE_PROD_CODE){
	setS("PROD_LINE_CODE",PROD_LINE_CODE);
	$("#PROD_LINE_CODE").change();
	setS("SALE_PROD_CODE",SALE_PROD_CODE);
	$("#SALE_PROD_CODE").change();
	//获取详细
	detail();
}


