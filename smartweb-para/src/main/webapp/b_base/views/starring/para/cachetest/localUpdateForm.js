var uKey = "";
var rulesKey = "";
$(document).ready(function(){
	parent.window.$("#iframe_update").show();
	chgHeight(200);
	getRules();
	/* table初始化 */
	tableClick("table");
	/*从session中拿出id*/
	dataKey = $.session.get('dataKey');
	/*从session中移除id*/
	$.session.remove('dataKey');
	var rules = dataKey.split("&@&")[0];
	uKey = dataKey.split("&@&")[1];
	rulesKey = rules+"&@&"+uKey;
	$("#rules").val(rules);
	
	$('#rulesSelect').multiselect("select", rulesKey).multiselect('rebuild');
	$('#rulesSelect').multiselect("disable")
	$("#uniqueKey").val(uKey);
	
	$("#dataKey").val(dataKey);
	detail(dataKey);
	$("#saveBtn").click(function(){
		if (proof()) {
			save();
		}
	});
	$("#cancleBtn").click(function(){
		cancle();
	});

	$(".addParam").on("click" ,function(){
		tableActionRow("table","add");
	});

	/* table确认修改 */
	$('.editParam').click(function(){
		tableActionRow("table","revice");
	});

});

function getRules() {
	var datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("rulesSelect", "/para/test/local/getRules", "rulesUnixKey", "chName", datass, "nulls", true, false);
}


function detail(dataKey) {
	var url = ctx + "/para/test/local/getDataForUpt";
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
				var list = data.dataSetResult[0].data;
				if (undefined != list) {
					for (var i = 0; i < list.length; i++) {
						var col = list[i];
						col.action = "";
						if (col.nullFlg == "Y" && col.uniqFlg == "N") {
							col.action = "<a href='javascript:;' onclick='reviceRow(this)'>修改</a>";
							col.action += " <a href='javascript:;' onclick='deleteRow(this)'>删除</a>";
						}
					}
				}
				$("#table").bootstrapTable("load", list);
			}
		}, "json");
}

function save() {
	console.log("save");
	getData("table");
	if (chkKeys) {
		var formData = $("#addForm").serializeObject();
		/*向后台发送参数*/
		$.post(ctx + "/para/test/local/setData", formData,
			function(data){
				if(data.returnCode!==undefined && "0000"!=data.returnCode){
					console.info(data);
					var errMsg = "错误信息["+data.message+"]";
					showContent(errMsg,"error");
					return '0';
				}else if(data.msg_type == "success"){
					var successMsg = "新增交易["+data.message+"]";
					showContent(successMsg,"success");
					console.info("新增交易成功");
					cancle();
				}
			}, "json");
	} else {
		showTip("非空字段与唯一索引的值不能为空", "error");
	}
}


function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*改变内容高度*/
function chgHeight(h){
	var Height =$(document.body).height() + h;
	$(window.parent.document).find("#tab_update").find('iframe').height(Height);
}


/* 取值，table新增一行 */
function tableActionRow(tableId, ifAddRevice){
	var rowArr = $('#'+tableId).bootstrapTable('getData');
	if ($("#colId").val() == "") {
		showTip("英文名称不能为空", "error");
		return ;
	}
	var uniqFlg = $("#uniqFlg").val();
	var nullFlg = $("#nullFlg").val();
	var action = "<a href='javascript:;' onclick='reviceRow(this)'>修改</a>";
	if (nullFlg == "Y" && uniqFlg == "N") {
		action += " <a href='javascript:;' onclick='deleteRow(this)'>删除</a>";
	}
	var Data = {
		colNo: $("#colId").val(),
		colName: $("#colName").val(),
		rmrk: $("#rmrk").val(),
		uniqFlg,
		nullFlg,
		action,
	}
	if (ifAddRevice == "add") {
		/*table增加行*/
		for (var i = 0; i < rowArr.length; i++) {
			var data = rowArr[i];
			if (data.colNo == $("#colId").val()) {
				showTip("已存在相同字段[" + $("#colId").val() + "]", "error");
				return 0;
			}
		}
		tableAddRow(tableId, Data);
	} else if (ifAddRevice == "revice") {
		/*修改行*/
		tableReviceRow(tableId, Data);
	}
	clearInput();
}

function clearInput() {
	$("#colId").val("");
	$("#colName").val("");
	$("#rmrk").val("");
	$("#uniqFlg").val("N");
	$("#nullFlg").val("Y");
}

//tale修改返回数据
function reviceRow(obj){
	var $reviceRowData= reviceRowData(obj);
	// 根据数据设置值
	setReviceRowData($reviceRowData);
}

//点击修改回显值
function setReviceRowData(data){
	$("#colId").val(data.colNo);
	$("#colName").val(data.colName);
	$("#rmrk").val(data.rmrk);
	$("#uniqFlg").val(data.uniqFlg);
	$("#nullFlg").val(data.nullFlg);
}

/*获取页面数据data*/
var chkKeys = true;
function getData(tableId){
	chkKeys = true;
	var rowArr = $('#'+tableId).bootstrapTable('getData');
	for (var i=0;i<rowArr.length;i++){
		var rowData = rowArr[i];
		rowData.colSer = i;
		if (rowData.nullFlg == "N" || rowData.uniqFlg == "Y") {
			if (rowData.rmrk == "") {
				chkKeys = false;
			}
		}
	}
	var rowArrIndex = $('#'+tableId).bootstrapTable('getData');
	var row = JSON.stringify(rowArrIndex);
	$("#colJson").val(row);
}