console.log("fileFmtDetail.js");

var tranTp = 0;//文件格式转换模板类型

$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	setSelect1("compNo",
			"/comp/fsvr/tec/tfsvrFileTotPara/selecList",
			"compNo", "compName", true, false);
	setSelect1("tempFmtNo","/comp/fsvr/tec/tfsvrFileTotPara/selectListTwo","fmtNo", "longRmrk", true, false);
	


	/* 格式类型控制输入框的显隐，
	 * 格式类型为定长（1）时显示字段长度和对齐方式，隐藏分隔符 
	 * 格式类型为非定长（2）时隐藏字段长度和对齐方式，显示分隔符*/
	sCi('fileFmt', 'colLen_head', [ '1' ]);
	sCs('fileFmt', 'alignMeth_head', [ '1' ]);
	sCi('fileFmt', 'fmtDltSym', [ '2' ]);
	sCi('fileFmt', 'colLen_body', [ '1' ]);
	sCs('fileFmt', 'alignMeth_body', [ '1' ]);
	
	
	/* 格式类型控制 */
	$('#fileFmt').change(
		function() {
			
			if (getS('fileFmt') == '1') {
				$('#table_head,#table_body')
						.bootstrapTable('showColumn',
								'colLen');
				$('#table_head,#table_body')
						.bootstrapTable('showColumn',
								'alignMethStr');
			} else {
				$('#table_head,#table_body')
						.bootstrapTable('hideColumn',
								'colLen');
				$('#table_head,#table_body')
						.bootstrapTable('hideColumn',
								'alignMethStr');
			}
			
			if (getS('fileFmt') == '3'){
				/*启用弹出框*/
				 $('#colName_head,#colName_body').popover();
			} else {
				/*销毁弹出框*/
				$('#colName_head,#colName_body').popover('destroy'); 
			}
		
	});
	/* 值转换拟态框控制 */
	$('#chgFlg_head').change(function() {
		if (getS('chgFlg_head') == "Y") {
			$("#modalDiv").modal("show");
			setI('targetFlg', 'head');
			$('#chgBtnDiv_head').show();
		} else {
			$('#chgBtnDiv_head').hide();
		}
	});
	$('#chgFlg_body').change(function() {
		if (getS('chgFlg_body') == "Y") {
			$("#modalDiv").modal("show")
			setI('targetFlg', 'body');
			$('#chgBtnDiv_body').show();
		} else {
			$('#chgBtnDiv_body').hide();
		}
	});
	/* 值转换模拟框隐藏时，重置 */
	$('#modalDiv').on('hide.bs.modal', function() {
		resetForm('modalDiv');
	});
	/* 修改转换值按钮点击 */
	$('#reviceChgBtn').click(function() {
		getChgModDetail('head');
	});
	$('#reviceChgBtnBody').click(function() {
		getChgModDetail('body');
	});
	
	/* 表格选中初始化 */
	tableClick("table_head");
	tableClick("table_body");
	tableClick("table_modal");
	/*从session中拿出id*/
	chgNo = $.session.get('HID_chgNo');
	/*从session中移除id*/
	$.session.remove('HID_chgNo');
	$("#chgNo").val(chgNo);
	getDetail(chgNo);
	disDiv("detailForm");
	
	$('#cancelBtn').click(function() {
		cancle();
	})
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}
//模板修改一行
function reviceRowTmpl(obj) {
	/*
	 * rev_index = $(obj).parent().parent().attr("data-index") //
	 * 拿到当前点击修改按钮所在的行索引 var arrData =
	 * $('#tmplTable_head').bootstrapTable('getData');
	 */
	var data = reviceRowData(obj);
	if (data.flg == '2') {
		setData_tmplTab(data, 'body');
	} else {
		setData_tmplTab(data, 'head');
	}
}
//修改一行--报文头
function reviceRow(obj) {
	var data = reviceRowData(obj);
	rev_index = $(obj).parent().parent().attr("data-index");
	setData_Tab(data, 'head');
}

// 修改一行--报文体
function reviceRowBody(obj) {
	var data = reviceRowData(obj);
	rev_index = $(obj).parent().parent().attr("data-index");
	setData_Tab(data, 'body');
}
/*反显所选中的模板表格信息*/
function setData_tmplTab(data, flg, colTp) {
	if (getS("fileFmt") == '3') {
		setI("colName_" + flg, '');
	} else {
		setI("colName_" + flg, data.colName);
	}
	setS("flg_" + flg, data.fileFlg);
	setS("tempFlg_" + flg, data.fileFlg);
	setI("tempSer_" + flg, data.ser);
	setI("tempColName_" + flg, data.colName);
	if (data.fileColTp == '9') {
		$('#colChgDiv_' + flg).show();
		setS("colTp_" + flg, '3');
		setS("clobFlg_" + flg, '1');
		if (tranTp == "1"){
			$('span[name="colChgFlgTip1"]').show();
			$('span[name="colChgFlgTip2"]').hide();
		} else {
			$('span[name="colChgFlgTip2"]').show();
			$('span[name="colChgFlgTip1"]').hide();
		}
	} else {
		$('#colChgDiv_' + flg).hide();
		setS("clobFlg_" + flg, '0');
		setS("colTp_" + flg, data.fileColTp);
	}
}
/*反显所选择的表格信息*/
function setData_Tab(data, flg) {
	setI("colName_" + flg, data.colName);
	setS("colTp_" + flg, data.fileColTp);
	setS("flg_" + flg, data.fileFlg);
	setS("alignMeth_" + flg, data.alignMeth);
	setI("colLen_" + flg, data.colLen);
	setI("chgList_" + flg, data.list);
	setS("chgFlg_" + flg, data.chgFlg);
	setI("tempColName_" + flg, data.tempColName);
	setS("clobFlg_" + flg, data.clobFlg);
	if (data.clobFlg == '1') {
		$('#colChgDiv_' + flg).show();
		if (tranTp == "1"){
			$('span[name="colChgFlgTip1"]').show();
			$('span[name="colChgFlgTip2"]').hide();
		} else {
			$('span[name="colChgFlgTip2"]').show();
			$('span[name="colChgFlgTip1"]').hide();
		}
	} else {
		$('#colChgDiv_' + flg).hide();
	}
	if (data.chgFlg == 'Y') {
		$('#chgBtnDiv_' + flg).show();
	} else {
		$('#chgBtnDiv_' + flg).hide();
	}

	setS("tempFlg_" + flg, data.tempFlg);
	setI("tempSer_" + flg, data.tempSer);
	if (data.tempSer.indexOf(".") != -1){
		$('#colChgDiv_' + flg).show();
	}
	
}
/* 转换值模拟框数据回显 */
function getChgModDetail(flg) {
	setI('targetFlg', flg);
	var chgStr = getI('chgList_' + flg);
	if(chgStr != '') {
		var chgList = JSON.parse(chgStr);
		$('#table_modal').bootstrapTable('load', chgList);
	} else {
		$('#table_modal').bootstrapTable('removeAll');
	}
	$('#modalDiv').modal('show');
}
/*查询明细*/
function getDetail(chgNo){
	console.info('get flow template info......');
	$.post(ctx + "/comp/fsvr/tec/fsvrFileFmt/get", {chgNo:chgNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					setS("compNo",jsonObj.compNo);
					setI("fmtName",jsonObj.fmtName);
					tranTp = jsonObj.fileTranTp;
					setS("fileCode",jsonObj.fileCode);
					setS("fileFmt",jsonObj.fileFmt); 
					$('#fileFmt').change();
					setI("fmtDltSym",jsonObj.fmtDltSym);
					setS("tempFmtNo",jsonObj.tempFmtNo);
					/*加载模板数据*/
					var tempData = jsonObj.tempData;
					$("#tempFileFmtShow").val(tempData.temStr);
					var bodyArr = [];
					var headArr = [];
					headSer = jsonObj.headNum;
					bodySer = jsonObj.bodyNum;
					endSer = jsonObj.tailNum;
					chgNum = jsonObj.enumNum;
					if (tempData.headList != undefined
							&& tempData.headList != "") {
						headArr = tempData.headList;
						for (var a = 0; a < headArr.length; a++) {
							var flg = headArr[a].fileFlg;
							if (flg == '1') {
								headArr[a].flgStr = '头';
							} else if (flg == '3') {
								headArr[a].flgStr = '尾';
							}
							headArr[a].action = "<input type='radio' name='getTemp' onclick='reviceRowTmpl(this)'> ";
						}
						$('#tmplTable_head').bootstrapTable(
								'load', headArr);
					} else {
						$('#tmplTable_head').bootstrapTable(
								'removeAll');
						$('#headDiv').hide();
					}
					if (tempData.bodyList != undefined
							&& tempData.bodyList != "") {
						bodyArr = tempData.bodyList;
						for (var a = 0; a < bodyArr.length; a++) {
							var flg = bodyArr[a].fileFlg;
							if (flg == '2') {
								bodyArr[a].flgStr = '体';
							}
							bodyArr[a].action = "<input type='radio' name='getTemp' onclick='reviceRowTmpl(this)'> ";
						}
						$('#tmplTable_body').bootstrapTable(
								'load', bodyArr);
					} else {
						$('#tmplTable_body').bootstrapTable(
								'removeAll');
						$('#bodyDiv').hide();
					}
					
					/*加载文件格式数据*/
					var fileData = jsonObj.fileData;
					var fileBodyArr = [];
					var fileHeadArr = [];
					if (fileData.headList != undefined
							&& fileData.headList != "") {
						fileHeadArr = fileData.headList;
						for (var a = 0; a < fileHeadArr.length; a++) {
							var flg = fileHeadArr[a].fileFlg;
							if (flg == '1') {
								fileHeadArr[a].flgStr = '头';
							} else if (flg == '3') {
								fileHeadArr[a].flgStr = '尾';
							}
							
							if (fileHeadArr[a].tempFlg != null && fileHeadArr[a].tempFlg != "") {
								fileHeadArr[a].tempFlgStr = fileHeadArr[a].flgStr;
							} else {
								fileHeadArr[a].tempFlgStr = "";
							}
							
							if (fileHeadArr[a].chgFlg == "N") {
								fileHeadArr[a].chgFlgStr = "否";
							} else if(fileHeadArr[a].chgFlg == "Y") {
								fileHeadArr[a].chgFlgStr = "是";
							} else {
								fileHeadArr[a].chgFlgStr = fileHeadArr[a].chgFlg;
							}
							if (jsonObj.fileFmt == "1"){
								var alignMeth = fileHeadArr[a].alignMeth;
								if (alignMeth == '1') {
									fileHeadArr[a].alignMethStr = "左对齐，右补0";
								}  else if (alignMeth == '2') {
									fileHeadArr[a].alignMethStr = "左对齐，右补空格";
								}  else if (alignMeth == '3') {
									fileHeadArr[a].alignMethStr = "右对齐，左补0";
								}  else if (alignMeth == '4') {
									fileHeadArr[a].alignMethStr = "右对齐，左补空格";
								}  else {
									fileHeadArr[a].alignMethStr = alignMeth;
								}
							}
							var colTp = fileHeadArr[a].fileColTp;
							if (colTp == "1") {
								fileHeadArr[a].colTpStr = "整型";
							} else if (colTp == "2") {
								fileHeadArr[a].colTpStr = "浮点型";
							} else if (colTp == "3") {
								fileHeadArr[a].colTpStr = "字符串";
							} else {
								fileHeadArr[a].colTpStr = colTp;
							} 
							fileHeadArr[a].action = "<a onclick='reviceRow(this)'>详情</a> ";
						}
						$('#table_head').bootstrapTable(
								'load', fileHeadArr);
					} 
					if (fileData.bodyList != undefined
							&& fileData.bodyList != "") {
						fileBodyArr = fileData.bodyList;
						for (var a = 0; a < fileBodyArr.length; a++) {
							var flg = fileBodyArr[a].fileFlg;
							if (flg == '2') {
								fileBodyArr[a].flgStr = '体';
							}
							
							if (fileBodyArr[a].chgFlg == "N") {
								fileBodyArr[a].chgFlgStr = "否";
							} else if(fileBodyArr[a].chgFlg == "Y") {
								fileBodyArr[a].chgFlgStr = "是";
							} else {
								fileBodyArr[a].chgFlgStr = fileBodyArr[a].chgFlg;
							}
							
					
							if (jsonObj.fileFmt == "1"){
								var alignMeth = fileBodyArr[a].alignMeth;
								if (alignMeth == '1') {
									fileBodyArr[a].alignMethStr = "左对齐，右补0";
								}  else if (alignMeth == '2') {
									fileBodyArr[a].alignMethStr = "左对齐，右补空格";
								}  else if (alignMeth == '3') {
									fileBodyArr[a].alignMethStr = "右对齐，左补0";
								}  else if (alignMeth == '4') {
									fileBodyArr[a].alignMethStr = "右对齐，左补空格";
								}  else {
									fileBodyArr[a].alignMethStr = alignMeth;
								}
							}
							var colTp = fileBodyArr[a].fileColTp;
							if (colTp == "1") {
								fileBodyArr[a].colTpStr = "整型";
							} else if (colTp == "2") {
								fileBodyArr[a].colTpStr = "浮点型";
							} else if (colTp == "3") {
								fileBodyArr[a].colTpStr = "字符串";
							} else {
								fileBodyArr[a].colTpStr = colTp;
							} 
							fileBodyArr[a].action = "<a onclick='reviceRowBody(this)'>详情</a> ";
						}
						$('#table_body').bootstrapTable(
								'load', fileBodyArr);
					} 
					/*显示文件转换格式示例*/
					showDemo();
					
				}
			}
		 
			$('#fileFmt').multiselect("disable");
			$('#fileCode').multiselect("disable");
		}
	},
    "json");
}
/* 显示文件格式示例*/
function showDemo() {
	var dataArr = $('#table_head').bootstrapTable('getData');
	var dataArrBody = $('#table_body').bootstrapTable('getData');
	var showStr = "";
	var headStr = "";
	var bodyStr = "";
	var endStr = "";
	var fileFmt = getS('fileFmt');
	if (fileFmt == '1') {
		/* 定长格式 */
		for (var i = 0; i < headSer; i++) {
			var alignMeth = dataArr[i].alignMeth;
			var len = parseInt(dataArr[i].colLen);
			var ser = dataArr[i].ser;
			headStr = headStr + formatStr(ser+"", len, alignMeth);
		}
		for (var i = 0; i < dataArrBody.length; i++) {
			var alignMeth = dataArrBody[i].alignMeth;
			var len = parseInt(dataArrBody[i].colLen);
			var ser = dataArrBody[i].ser;
			bodyStr = bodyStr + formatStr(ser+"", len, alignMeth);

		}
		for (var i = headSer; i < dataArr.length; i++) {
			var alignMeth = dataArr[i].alignMeth;
			var len = parseInt(dataArr[i].colLen);
			var ser = dataArr[i].ser;
			endStr = endStr + formatStr(ser+"", len, alignMeth);
		}
		showStr = headStr + "\n" + bodyStr + "\n" + endStr;
	} else if (fileFmt == '2') {
		/* 非定长格式 */
		var fmtDltSym = getI('fmtDltSym');
		for (var i = 0; i < headSer; i++) {
			headStr = headStr + fmtDltSym + dataArr[i].colName;
		}
		headStr = headStr.replace(fmtDltSym, "");
		for (var i = headSer; i < dataArr.length; i++) {
			endStr = endStr + fmtDltSym + dataArr[i].colName;
		}
		endStr = endStr.replace(fmtDltSym, "");
		for (var i = 0; i < dataArrBody.length; i++) {
			bodyStr = bodyStr + fmtDltSym + dataArrBody[i].colName;
		}
		bodyStr = bodyStr.replace(fmtDltSym, "");
		showStr = headStr + "\n" + bodyStr + "\n" + endStr;
	} else if (fileFmt == '3') {
		/* xml格式 */
		showStr = '<?xml version="1.0" encoding="utf-8"?>\n';
		if (headSer != 0) { // 是否有头
			var rootNote = dataArr[0].colName.split(".");
			showStr = showStr + "<" + rootNote[0] + ">\n";
			showStr = showStr + "\t<" + rootNote[1] + ">" + dataArr[0].ser
					+ "</" + rootNote[1] + ">\n";
			for (var i = 1; i < headSer; i++) {
				var temp = dataArr[i].colName.split(".");
				showStr = showStr + "\t<" + temp[1] + ">" + dataArr[i].ser
						+ "</" + temp[1] + ">\n";
			}
			if (dataArrBody.length != 0) {
				var bodyNote = dataArrBody[0].colName.split('.');
				showStr = showStr + "\t<" + bodyNote[1] + ">\n";
				showStr = showStr + "\t\t<" + bodyNote[2] + ">"
						+ dataArrBody[0].ser + "</" + bodyNote[2] + ">\n";
				for (var i = 1; i < dataArrBody.length; i++) {
					var temp = dataArrBody[i].colName.split(".");
					showStr = showStr + "\t\t<" + temp[2] + ">"
							+ dataArrBody[i].ser + "</" + temp[2] + ">\n";
				}
				showStr = showStr + "\t</" + bodyNote[1] + ">\n";
			}
			for (var i = headSer; i < dataArr.length; i++) {
				var temp = dataArr[i].colName;
				showStr = showStr + "\t<" + temp + ">" + dataArr[i].ser + "</"
						+ temp + ">\n";
			}
			showStr = showStr + "</" + rootNote[0] + ">\n";
		} else {
			if (dataArrBody.length != 0) {
				var rootNote = dataArrBody[0].colName.split('.');
				showStr = showStr + "<" + rootNote[0] + ">\n";
				showStr = showStr + "\t<" + rootNote[1] + ">\n";
				showStr = showStr + "\t\t<" + rootNote[2] + ">"
						+ dataArrBody[0].ser + "</" + rootNote[2] + ">\n";
				for (var i = 1; i < dataArrBody.length; i++) {
					var temp = dataArrBody[i].colName.split(".");
					showStr = showStr + "\t\t<" + temp[2] + ">"
							+ dataArrBody[i].ser + "</" + temp[2] + ">\n";
				}
				showStr = showStr + "\t</" + rootNote[1] + ">\n";
			}
			for (var i = headSer; i < dataArr.length; i++) {
				var temp = dataArr[i].colName;
				showStr = showStr + "\t<" + temp + ">" + dataArr[i].ser + "</"
						+ temp + ">\n";
			}
			showStr = showStr + "</" + rootNote[0] + ">\n";
		}
	}
	$("#fileFmtShow").val(showStr);

}

/*对齐方式格式化*/
function formatStr(ser, length, alignMeth) {
	if (alignMeth == '1') {
		var padLength = length - ser.length;
		var tem = "";
		for (var i = 0; i < padLength; i++) {
			tem = tem + "0";
		}
		return ser + tem;
	} else if (alignMeth == '2') {
		var padLength = length - ser.length;
		var tem = "";
		for (var i = 0; i < padLength; i++) {
			tem = tem + " ";
		}
		return ser + tem;
	} else if (alignMeth == '3') {
		var padLength = length - ser.length;
		var tem = "";
		for (var i = 0; i < padLength; i++) {
			tem = tem + "0";
		}
		return tem + ser;
	} else if (alignMeth == '4') {
		var padLength = length - ser.length;
		var tem = "";
		for (var i = 0; i < padLength; i++) {
			tem = tem + " ";
		}
		return tem + ser;
	}
}
