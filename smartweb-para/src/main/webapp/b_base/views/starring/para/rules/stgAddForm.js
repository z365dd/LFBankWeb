$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_add").show();
	chgHeight(100);
	checkbox();

	/* table初始化 */
	tableClick("table");
//	$("#readAuthLvl_str").multiselect("disable");
	$(".addParam").on("click" ,function(){
		tableActionRow('table',"add");
	});
	
	/* table确认修改 */
	$('.editParam').click(function(){
		tableActionRow('table',"revice");
	});
	
	$("#divcacheCertrId").hide();
	$("#divstorgRuleTp").hide();
	$("#divreadAuthLvl").hide();
	$(".divCheckbox").hide();
	/*生成索引组合*/
	$(".genUniqueKey").click(function(){
		genUniqueKey();
	});
//	$(".readAuthLevel").show();
	$("#storgRuleTp").bind('change', function(){
		if ($(this).val() == "00") {
			$(".readAuthLevel").show();
		} else {
			$(".readAuthLevel").hide();
		}
	})
	$("#sameDbFlg").bind('change', function() {
		if($(this).val() == "N") {
			$(".divSrcDataSrc").show();
			$("#srcDataSrc").attr("min","1");
			$("#srcDataSrc").attr("max","19");
			$("#srcDataSrc").attr("check-empty","true");
		} else {
			$(".divSrcDataSrc").hide();
		}
	})
//	getCenters();
	/*类型为Object时，不校验长度*/
	$("#length").val("");
	$("#length").attr("readonly","readonly");
	$("#dataTp").bind('change', function(){
		// lengthErr
		$("#length").parent("div").siblings("#lengthErr").remove();
		if ($(this).val() == 'Object' || $(this).val() == '') {
			$("#length").val("");
			$("#length").attr("readonly","readonly");
			$("#length").removeAttr("min");
			$("#length").removeAttr("max");
			$("#length").removeAttr("check-empty");
			// $("#length").removeAttr("check-lengthFormat");
		} else if ($(this).val() == 'Long') {
			$("#length").val("");
			$("#length").removeAttr("readonly");
			$("#length").attr("min","1");
			$("#length").attr("max","19");
			$("#length").attr("check-empty","true");
			// $("#length").attr("check-lengthFormat","true");
		} else if ($(this).val() == 'String'  || $(this).val() == 'Byte'){
			$("#length").val("");
			$("#length").removeAttr("readonly");
			$("#length").attr("min","1");
			$("#length").attr("max","1024");
			$("#length").attr("check-empty","true");
			// $("#length").attr("check-lengthFormat","true");
		} else if ($(this).val() == 'Double') {
			$("#length").val("16");
			$("#length").attr("readonly","readonly");
			$("#length").removeAttr("check-minlength");
			$("#length").removeAttr("maxlength");
			$("#length").removeAttr("check-empty");
		}
	});
	
	
	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(portion("colForm")){
			genUniqueKey();
			save();
		};
	});
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
});


/* 取值，table新增一行 */
function tableActionRow(tableId,ifAddRevice){
	var rowArr = $('#table').bootstrapTable('getData');
	if(portion("colDiv")){
		var _type = getS('dataTp');
		var _length = $("#length").val();
		// var re = /(0|([1-9]\d*))/;
		// var re = /(0|([1-9]\d*))\.\d{1}$/;
		var reg = /^[1-9]\d*$/;

		if (_type != "Double" && _type != "Long") {
			// if (_length.match(re) != null) {
			// 	showTip("数据类型与数据长度有误", "error");
			// 	return 0;
			// }
			if (_length.indexOf(".") != -1) {
				showTip("数据长度不能有小数点", "error");
				return 0;
			}
			if (_length > 1024) {
				showTip("数据长度不能超过1024", "error");
				return 0;
			}
		}
		// else if (_type != "Object" && _type != "Long"){
		// 	if (_length.match(reg) != null) {
		// 		showTip("数据类型与数据长度有误", "error");
		// 		return 0;
		// 	}
		// }
		var Data={
			colNo:$("#colId").val(),
			colName:$("#colName").val(),
			dataTp:getS('dataTp'),
			colLen:$("#length").val(),
			nullFlg:$("#isNotNull").is(":checked")?"Y":"N",
			uniqFlg:"Y",
			action:"<a href='javascript:;'onclick='reviceRow(this)'>修改</a> <a href='javascript:;' onclick='deleteRow(this)'>删除</a>",
		}
		if(ifAddRevice=="add"){
			/*table增加行*/
			for (var i=0;i<rowArr.length;i++) {
				var data = rowArr[i];
				if (data.colNo == $("#colId").val()) {
					showTip("已存在相同字段["+$("#colId").val()+"]", "error");
					return 0;
				}
			}
			tableAddRow(tableId, Data);
		}else if(ifAddRevice=="revice"){
			/*修改行*/
			tableReviceRow(tableId, Data);
		}
		clearInput();
	}
}

/*清空输入框*/
function clearInput(){
	$("#colId").val("");
	$("#colName").val("");
	$("#dataTp").multiselect("select", "").multiselect('rebuild');
	$("#isNotNull").prop("checked", false);
	$("#isUnique").prop("checked", false);

	$("#length").val("");
	$("#length").attr("readonly", "readonly");
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
	$("#dataTp").multiselect("select", data.dataTp).multiselect('rebuild');
	var _type = getS('dataTp');
	if (_type == "Object") {
		$("#length").attr("readonly","readonly");
		$("#length").removeAttr("check-minlength");
		$("#length").removeAttr("maxlength");
		$("#length").removeAttr("check-empty");
		// $("#length").removeAttr("check-lengthFormat");
	} else if (_type != "Double") {
		$("#length").removeAttr("readonly");
	}
	$("#length").val(data.colLen);
	
	if (data.nullFlg == "Y") {
		$("#isNotNull").prop("checked", true);
		$("#isUnique").prop("checked", false);
	}
	if (data.uniqFlg == "Y") {
		$("#isUnique").prop("checked", true);
		$("#isNotNull").prop("checked", false);
	}
}

/*生成唯一索引组合*/
function genUniqueKey() {
	var checkNum = 0;
	var uniqueKey = "";
	var rowArr = $('#table').bootstrapTable('getData');
	for (var i=0;i<rowArr.length;i++) {
		var data = rowArr[i];
		if (data.uniqFlg == "Y") {
			if(data.dataTp != "Object") {
				uniqueKey += data.colNo;
				checkNum++;
				uniqueKey += ",";
			}else {
				showTip("唯一索引组合不能有Object类型", "error");
				return 0;
			}
		}
	}
	if (checkNum==0){
		var errMsg = "错误信息[没有唯一索引]"; 
		showContent(errMsg,"error");
		return '0';
	}
	uniqueKey = uniqueKey.substring(0, uniqueKey.length-1);
	$("#uniqKey").val(uniqueKey);
}

/*获取页面数据data*/
function getData(){
	var rowArr = $('#table').bootstrapTable('getData');
	for (var i=0;i<rowArr.length;i++){
		var rowData = rowArr[i];
		rowData.colSer = i;
	}
	var rowArrIndex = $('#table').bootstrapTable('getData');
	var row = JSON.stringify(rowArrIndex);
	$("#colJson").val(row);
}

/*勾选"唯一索引"同时勾选"不为空"*/
function checkbox() {
	$("#isUnique").bind('click', function(){
		if (this.checked) {
			$("#isNotNull").prop("checked", false);
		}
	});
	
	/*勾选"唯一索引"时，不能取消勾选"不为空"*/
	$("#isNotNull").bind('click', function(){
		if (this.checked) {
			$("#isUnique").prop("checked", false);
		}
	});
}

/*获取缓存中心*/
function getCenters() {
	datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("cacheCentrId", "/para/center/list", "id", "chName", datass, "nulls", false, false);
}

/**
 * 保存函数--保存缓存中心信息新增
 * @returns
 */
function save(){
	if ($("#uniqKey").val() != "") {
		getData();
		var formData = $("#addForm").serializeObject();
		/*向后台发送参数*/
		$.post(ctx + "/para/rules/stg/insert", formData,
			function(data){ 
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				    var errMsg = "错误信息["+data.message+"]"; 
					showContent(errMsg,"error");
					return '0';
				}else if(data.msg_type == "success"){
					var successMsg = "新增存储规则["+data.message+"]"; 
					showContent(successMsg,"success");
					console.info("新增存储规则成功");
					cancle();
				}
		}, "json");
	} else {
		var errMsg = "错误信息[至少要一个唯一索引字段]";
		showContent(errMsg,"error");
		return '0';
	}
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*改变内容高度*/
function chgHeight(h) {
	var Height =$(document.body).height() + h;
	$(window.parent.document).find("#tab_add").find('iframe').height(Height);
}