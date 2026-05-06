var num;
$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	chgHeight(100);
	/*从session中拿出id*/
	id = $.session.get('id');
	/*从session中移除id*/
	$.session.remove('id');
	$("#id").val(id);
	
	checkbox();
	getCenters();
	/* table初始化 */
	tableClick("table");
	
	$(".addParam").on("click" ,function(){
		tableActionRow('table',"add");
	});
	
	/* table确认修改 */
	$('.editParam').click(function(){
		tableActionRow('table',"revice");
	});
	
	$(".genUniqueKey").click(function(){
		genUniqueKey();
	});
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
	$(".divcacheCertrId").hide();
	$(".divstorgRuleTp").hide();
	$(".readAuthLevel").hide();
	$(".divCheckbox").hide();
	$("#dataTp").bind('change', function(){
		// lengthErr
		$("#length").parent("div").siblings("#lengthErr").remove();
		if ($(this).val() == 'Object' || $(this).val() == '' ) {
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
		} else if ($(this).val() == 'String' || $(this).val() == 'Byte'){
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

	getDetail(id);
	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(portion("colForm")){
			if (genUniqueKey() != 0){
				save();
			}
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

		var re = /(0|([1-9]\d*))\.\d{1}$/;
		var reg = /^[1-9]\d*$/;
		// console.log(_length.match(re));
		// console.log(_length.match(reg));
		
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
				action:"<a onclick='reviceRow(this)'>修改</a> <a onclick='deleteRow(this)'>删除</a>"
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
			tableAddRow(tableId,Data);
			clearInput();
		}else if(ifAddRevice=="revice"){
			/*修改行*/
			tableReviceRow(tableId,Data);
			clearInput();
		}
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
	console.log(obj);
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
		$("#length").removeAttr("check-lengthFormat");
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
	uniqueKey = uniqueKey.substring(0,uniqueKey.length-1);
	$("#uniqKey").val(uniqueKey);
	return 1;
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
	$("#cacheCentrId").find("option").selected(false);
}

/*获取缓存中心详情信息*/
function getDetail(id){
	console.info('get rules info......');
	$.post(ctx + "/para/rules/stg/get", {id:id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			var list = data.dataSetResult[1].data;
			console.log(list);
			if (undefined != list) {
				for(var i=0;i<list.length;i++){
					list[i].action = "<a href='javascript:;' onclick='reviceRow(this)'>修改</a> <a href='javascript:;' onclick='deleteRow(this)'>删除</a>";
				}
			}
			$("#table").bootstrapTable('load', list);
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				var dataSetName = data.dataSetResult[i].dataSetName;
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					if (dataSetName === "stgDs") {
						stgInfo(jsonObj);
					}
				}
			}
		}
	},
    "json");
};

/*存储规则-基本信息*/
function stgInfo(jsonObj) {
	
	$('#cacheCentrId').multiselect("select", jsonObj.cacheCentrId).multiselect('rebuild');
	$('#readAuthLevel').multiselect("select", jsonObj.readAuthLvl).multiselect('rebuild');
	$('#readAuthLevel').multiselect("disable")
	$("#readAuthLvl").val(jsonObj.readAuthLvl);
	$("#tntNo").val(jsonObj.tntNo);
	$("#chName").val(jsonObj.chName);
	$("#engName").val(jsonObj.engName);
	$("#tabName").val(jsonObj.tabName);
	$('#sameDbFlg').multiselect("select",jsonObj.sameDbFlg).multiselect('rebuild');
	$("#srcDataSrc").val(jsonObj.srcDataSrc);
	$('#busiNoFlg').multiselect("select",jsonObj.busiNoFlg).multiselect('rebuild');
	$("#uniqKey").val(jsonObj.uniqKey);
	$('#storgRuleType').multiselect("select", jsonObj.storgRuleTp).multiselect('rebuild');
	$('#storgRuleType').multiselect('disable');
	if (jsonObj.storgRuleTp == "01") {
		$('.readAuthLevel').hide();
	}
	$('#storgRuleTp').val(jsonObj.storgRuleTp);
	
	if(jsonObj.sameDbFlg == "N") {
		$(".divSrcDataSrc").show();
		$("#srcDataSrc").attr("min","1");
		$("#srcDataSrc").attr("max","19");
		$("#srcDataSrc").attr("check-empty","true");
	} else {
		$(".divSrcDataSrc").hide();
	}
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
		$.post(ctx + "/para/rules/stg/update", formData,
			function(data){ 
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				    var errMsg = "错误信息["+data.message+"]"; 
					showContent(errMsg,"error");
					return '0';
				}else if(data.msg_type == "success"){
					var successMsg = "修改存储规则["+data.message+"]"; 
					showContent(successMsg,"success");
					console.info("修改存储规则成功");
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
	$(window.parent.document).find("#tab_update").find('iframe').height(Height);
}

