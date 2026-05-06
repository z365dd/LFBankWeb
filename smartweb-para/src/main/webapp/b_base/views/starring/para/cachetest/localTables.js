$(document).ready(function(){
	parent.window.$("#iframe_tables").show();
	/*从session中拿出id*/
	rulesKey = $.session.get('rulesKey');
	/*从session中移除id*/
	$.session.remove('rulesKey');
	getRules();
	if (rulesKey !== undefined) {
		$('#rules').multiselect("select", rulesKey).multiselect('rebuild');
		var unixKey = rulesKey.split("&@&");
		$("#localTable").bootstrapTable('refresh');
		getUnixKey(unixKey[1]);
	}
	$("#rules").change(function(){
		if ($(this).val() != "") {
			var unixKey = $(this).val().split("&@&");
			getUnixKey(unixKey[1]);
		}else {
			getUnixKey("");
		}
	});

	$("#uniqueValue").change(function(){
		if ($(this).val() != ""){
			$("#uniqueKey").attr("checkbtn","checkbtn");
			$("#uniqueKey").multiselect('rebuild');
		}else {
			$("#uniqueKey").removeAttr("checkbtn");
			$("#uniqueKey").multiselect('rebuild');
			$("#key").find("#checkbtnErr").remove();
		}
	});
	
	$("#uniqueKey").on('change', function(){
		if ($(this).val() != ""){
			$("#uniqueValue").attr("check-empty","true");
		}else {
			$("#uniqueValue").removeAttr("check-empty");
			$("#value").siblings("#uniqueValueErr").remove();
		}
	});
	
	$("#listBtn").click(function(){
		if (chkExpr()) {
			/*重置搜索下标，跳转到第一页*/
			var $preClick = $("#localTable").parent().parent().find(".page-pre");
			if($preClick.siblings().length>1){
				$preClick.next().click();
			}
			queryList();
		}
	});

	/*初始化表格显示列长度20*/
	$("#localTable").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'localTable'},{dftlen:50});
	});

	$("#addBtn").click(function () {
		if (portion("query")) {
			// addExpr();
			tableActionRow("coltable", "add");
		}
	});
});

function queryList() {
	$("#localTable").bootstrapTable("removeAll");
	var fRules = getS("fRulesSelect", ";");
	if (fRules == "") {
		showTip("请选择从存储规则", "error");
		return 0;
	}
	var fKey = $("#fKey").val();
	if (fKey == "") {
		showTip("外键不能为空", "error");
		return 0;
	}
	$("#fRules").val(fRules);
	var tableData = "";
	var formData = $("#listForm").serializeObject();
	$.post(ctx + "/para/test/local/tables", {
		rules: (typeof(formData.rules)==undefined)?'':formData.rules,
		fRules: (typeof(formData.fRules)==undefined)?'':formData.fRules,
		fKey: (typeof(formData.fKey)==undefined)?'':formData.fKey,
		uniqueKey: (typeof(formData.uniqueKey)==undefined)?'':formData.uniqueKey,
		uniqueValue: (typeof(formData.uniqueValue)==undefined)?'':formData.uniqueValue
			}, function(data){
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var errMsg = "错误信息["+data.message+"]"; 
					showContent(errMsg,"error");	
				}else {
					var dataVal =data.dataSetResult[0].data;
					if(dataVal!="[]"){
						for(var i=0;i<dataVal.length;i++){
							var paramType = dataVal[i].paramType;
							var uniqueKey = dataVal[i].uniqueKey;
							var unixKey = dataVal[i].unixKey;
							var action = dataVal[i].action;
							tableData += '{"paramType":"'+paramType+'","uniqueKey":"'+uniqueKey+'","unixKey":"'+unixKey+'","action":"'+action+'"},';
						}
					}
					if(tableData.length > 0){
						tableData = '['+tableData.substring(0,tableData.length-1)+']';//client分页需要这种格式
						//tableData = '{"total":'+dataVal.length+',"rows":['+tableData.substring(0,tableData.length-1)+']}';
					}else {
						tableData = '[]';
					}
					$('#localTable').bootstrapTable('load', JSON.parse(tableData));
				}
			},
    "json");
}

/*跳转详细页面*/
function getData(dataKey){
	console.info("open Update tab");
	$.session.set('dataKey',dataKey);
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/para/test/local/detailPage?dataKey=" + dataKey);
	parent.window.$("a[href^='#tab_detail']").click();
}

/*跳转详细页面*/
function setData(dataKey){
	console.info("open Update tab");
	$.session.set('dataKey',dataKey);
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/para/test/local/updatePage?dataKey=" + dataKey);
	parent.window.$("a[href^='#tab_update']").click();
}

/*删除缓存*/
function delData(dataKey){
	confirmx("是否删除此数据", function(){
	var url = ctx + "/para/test/local/delete";
	//向后台发送参数
	$.post(url,
			{ 
				dataKey : dataKey,
			},
			function(data){ 
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var errMsg = "错误信息["+data.message+"]"; 
					showContent(errMsg,"error");
					return '0';
				}else if(data.msg_type == "success"){
					var successMsg = "删除["+data.message+"]"; 
					showContent(successMsg,"success");
					$("#listBtn").click();
				}
			}, "json");

	});
}

/*获取存储规则*/
function getRules() {
	var datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("rules", "/para/test/local/getRules", "rulesUnixKey", "chName", datass, "nulls", true, false, false);
	setSelect2("fRulesSelect", "/para/test/local/getRules", "rulesUnixKey", "chName", datass, "nulls", true, false, false);
}


function getUnixKey(unixKey){
	var str = [];
	var keys = unixKey.split(",");
	for (var i=0;i<keys.length;i++) {
//		str += "<opion value='"+keys[i]+"'>"+keys[i]+"</option>";
		str[i] = {
			label : keys[i],
			value : keys[i]
		}
		console.log(str);
	};
	str.unshift({
		label : '请选择',
		value : ''
	});
	$("#uniqueKey").multiselect('dataprovider', str);
	$("#uniqueKey").multiselect('rebuild');
}


function chkExpr() {
	var rules = $("#rules").val();
	if (rules == ""){
		showTip("存储规则不能为空", "error");
		return false;
	}
	return true;
}



/* 取值，table新增一行 */
function tableActionRow(tableId, ifAddRevice){
	var rowArr = $('#'+tableId).bootstrapTable('getData');
	if ($("#uniqueKey").val() == "") {
		showTip("查询条件不能为空", "error");
		return ;
	}
		action = " <a href='javascript:;' onclick='deleteRow(this, reset())'>删除</a>";
	var Data = {
		colNo: $("#uniqueKey").val(),
		oper: $("#oper").val(),
		value: $("#uniqueValue").val(),
		action,
	}
	if (ifAddRevice == "add") {

		/*table增加行*/
		for (var i = 0; i < rowArr.length; i++) {
			var data = rowArr[i];
			if (data.colNo == $("#uniqueKey").val()) {
				showTip("已存在相同查询条件[" + $("#uniqueKey").val() + "]", "error");
				return 0;
			}
		}

		/*table增加行*/
		tableAddRow(tableId, Data);
	}
	clearInput();
	$("#custom").multiselect("disable");
	$("#rules").multiselect("disable");
}

function clearInput() {
	$("#colId").val("");
	$("#colName").val("");
	$("#rmrk").val("");
	$("#uniqFlg").val("N");
	$("#nullFlg").val("Y");
}

function reset() {
	var rowArr = $('#coltable').bootstrapTable('getData');
	console.log(rowArr)
	if (rowArr.length <= 1) {
		$("#custom").multiselect("rebuild");
		$("#rules").multiselect("rebuild");
	}
}


function genData(){
	var rowArrIndex = $('#coltable').bootstrapTable('getData');
	var row = JSON.stringify(rowArrIndex);
	$("#colJson").val(row);
}