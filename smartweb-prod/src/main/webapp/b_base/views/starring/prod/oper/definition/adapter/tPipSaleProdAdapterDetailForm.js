var resourceData = [{value:"",label:"请选择"}];
var action = "<a href='javascript:;' onclick='reviceRow(this)'>详情</a>";
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出id*/
	id = $.session.get('id');
	prodLineCode = $.session.get('prodLineCode');
	/*从session中移除id*/
	$.session.remove('id');
	$("#id").val(id);
	
	/* table初始化 */
	tableClick("table");
	prodLineList();
	
	$("#prodLineCode").multiselect("select",prodLineCode).multiselect('rebuild');
	getSaleProdList(prodLineCode);
	$("#saleProdCode").multiselect("select",id).multiselect('rebuild');
	$("#prodLineCode").multiselect('disable');
	$("#saleProdCode").multiselect('disable');
	
	$("#vslFlg").on("change", function(){
		if($(this).val() == "N") {
			$("#defaKv").attr("readonly","readonly");
			$("#defaKv").removeAttr("check-empty");
			$("#defaKvErr").hide();
		}else {
			$("#defaKv").removeAttr("readonly");
			$("#defaKv").attr("check-empty","true");
		}
	});
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
	getDetail(id);
	
	/*保存按钮*/
	$('#settingBtn').click(function(){
		$("#keyDiv").hide();
	});
});


/**
 * 获取产品线下拉框
 */
function prodLineList() {
	datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("prodLineCode", "/prod/oper/line/tPipLineProd/list", "prodLineCode", "prodLineName", datass, "nulls", false, false);
	$("#prodLineCode").on("change", function(){
		var prodLineCode = $(this).val();
		$("#saleProdCode").multiselect("dataprovider",resourceData);
		if (prodLineCode!=""){
			getSaleProdList(prodLineCode);
		}
		
	});
}

/**
 * 获取可售产品下拉框
 */
function getSaleProdList(prodLineCode) {
	datass = {prodLineCode:prodLineCode, start:'0', pageSize:'0'};
	setSelect2("saleProdCode", "/prod/oper/definition/saleprod/tPipSaleProd/list", "saleProdCode", "saleProdDesc", datass, "nulls", false, false);
	
	$("#saleProdCode").on("change", function(){
		var saleProdCode = $(this).val();
		getKeys(saleProdCode);
	});
	
}


function getKeys(saleProdCode) {
	$.post(ctx + "/prod/oper/definition/adapter/tPipSaleProdAdapter/getKeyList", {saleProdCode:saleProdCode}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			var list = data.dataSetResult[0].data;
			if (undefined != list) {
				for(var i=0;i<list.length;i++){
					list[i].action = action;
				}
			}
			$("#table").bootstrapTable('load', list);
		}
	},
    "json");
}


/*
 * 上移
 */
function moveUp(serName ,callBackFunc) {
//	var current = $(obj).parent().parent(); //获取当前<tr>
//	var prev = current.prev(); //获取当前<tr>前一个元素
//	if (current.index() > 0) {
//		current.insertBefore(prev); //插入到当前<tr>前一个元素前
//	}
//	console.log(current);
	var tableId = "table";
	tableUpSort(tableId, serName, callBackFunc);
}

// 下移
function moveDown(serName ,callBackFunc) {
//	var current = $(obj).parent().parent(); //获取当前<tr>
//	var next = current.next(); //获取当前<tr>后面一个元素
//	if (next) {
//		current.insertAfter(next); //插入到当前<tr>后面一个元素后面
//	}
	var tableId = "table";
	tableDownSort(tableId, serName, callBackFunc);
}


//tale修改返回数据
function reviceRow(obj){
	var $reviceRowData= reviceRowData(obj);
	// 根据数据设置值
	setReviceRowData($reviceRowData);
}


/* 取值，table修改一行 */
function tableActionRow(tableId, ifAddRevice){
	var rowArr = $('#table').bootstrapTable('getData');
	var keyTypeStr = $("#keyType option:selected").text();
	var keyTpStr = $("#keyTp option:selected").text();
	var Data={
			keyTypeStr: keyTypeStr,
			keyNo:$("#keyNo").val(),
			keyName:$("#keyName").val(),
			keyTpStr:keyTpStr,
			vslFlg:$("#vslFlg").val(),
			defaKv:$("#defaKv").val(),
			valLen:$("#valLen").val(),
			keyTp:$("#keyType").val(),
			enterTp:$("#keyTp").val(),
			valTp:$("#valTp").val(),
			action:action,
	}
	if(ifAddRevice=="add"){
		
	}else if(ifAddRevice=="revice"){
		/*修改行*/
		tableReviceRow(tableId, Data);
		$("#keyDiv").hide();
	}
}

//点击修改回显值
function setReviceRowData(data){
	$("#keyDiv").show();
	$("#keyType").multiselect("select", data.keyTp).multiselect('rebuild');
	$("#keyNo").val(data.keyNo);
	$("#keyName").val(data.keyName);
	if (data.vslFlg == "N" || data.vslFlg == "") {
		$("#defaKv").attr("readonly","readonly");
		$("#vslFlg").multiselect("select", "N").multiselect('rebuild');
	}else {
		$("#defaKv").attr("check-empty","true");
		$("#vslFlg").multiselect("select", "Y").multiselect('rebuild');
	}
	$("#defaKv").val(data.defaKv);
	$("#valLen").val(data.valLen);
	$("#vslFlg").multiselect('disable');
	$("#keyTp").multiselect("select", data.enterTp).multiselect('rebuild');
	$("#valTp").multiselect("select", data.valTp).multiselect('rebuild');
	$("#valTp").multiselect('disable');
	$("#keyType").multiselect('disable');
	$("#keyTp").multiselect('disable');
	if (data.enterTp != "01") {
		$("#tableDiv").show();
		getKeyCtrl(data.keyNo);
	} else {
		$("#tableDiv").hide();
	}
	
}

function getKeyCtrl(keyNo){
	console.info('get rules info......');
	$.post(ctx + "/prod/oper/definition/adapter/tPipSaleProdAdapter/getKeyCtrlList", {keyNo:keyNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			var list = data.dataSetResult[0].data;
			$("#keyTable").bootstrapTable('load', list);
		}
	},
    "json");
}


function genKeyJson(){
	var rowArr = $('#table').bootstrapTable('getData');
	for (var i=0;i<rowArr.length;i++){
		var rowData = rowArr[i];
		rowData.ser = i;
		if (rowData.vslFlg == ""){
			rowData.vslFlg = "N"
		}
	}
	var rowArrIndex = $('#table').bootstrapTable('getData');
	var row = JSON.stringify(rowArrIndex);
	$("#keyJson").val(row);
}


function submit(){
	confirmx('是否更新可售产品包装', function(){
		save();
	});
}

/**
 * 保存函数--保存可售产品包装修改
 * @returns
 */
function save(){
	genKeyJson();
	var formData = $("#tPipSaleProdAdapterForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/prod/oper/definition/adapter/tPipSaleProdAdapter/update", formData,
		function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			    var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
				return '0';
			}else if(data.msg_type == "success"){
				var successMsg = "修改交易["+data.message+"]"; 
				showContent(successMsg,"success");
				console.info("修改交易成功");
				cancle();
			}
	}, "json");
	
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(id){

	$.post(ctx + "/prod/oper/definition/adapter/tPipSaleProdAdapter/getKeyList", {saleProdCode:id, detailFlag:"1"}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			var list = data.dataSetResult[0].data;
			if (undefined != list) {
				for(var i=0;i<list.length;i++){
					list[i].action = action;
				}
			}
			$("#table").bootstrapTable('load', list);
		}
	},
    "json");

	
	console.info('update tPipSaleProdAdapter info......');
}