console.log('fileFmtUpdate.js');
var arr = []; // 数据暂存于此
var headSer = 0;
var bodySer = 0;
var endSer = 0;
var chgNum = 0;
var origChgFlg = "N";//原转换标志
var rev_index = -1;
var tranTp = 0;//文件格式转换模板类型

$(document)
		.ready(
				function() {
					/* 把新增页面内容显示出来 */
					parent.window.$("#iframe_update").show();
					setSelect1("compNo",
							"/comp/fsvr/tec/tfsvrFileTotPara/selecList",
							"compNo", "compName", true, false);

					setSelect1("tempFmtNo","/comp/fsvr/tec/tfsvrFileTotPara/selectListTwo","fmtNo", "longRmrk", true, false);
					
					/* 模板名称不为空时，显示配置输入区域 */
					$('#tempFmtNo').change(function() {
						var tempFmtNo = getS('tempFmtNo')
						if (tempFmtNo != "") {
							getTempFmt(tempFmtNo);
							$('#confDiv').show();
						} else {
							$('#confDiv').hide();
						}
					});
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
								if ( tranTp == 0){
									showTip('请选择模板号');
									setS('fileFmt','');
									return;
								}
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
								/* 移除原来的数据 */
								$('#table_head,#table_body').bootstrapTable(
										'removeAll'); 
								headSer = 0;
								bodySer = 0;
								endSer = 0;
							});
					/*表格新增按钮点击*/
					$('#addRowBtn').click(function() {
						if (checkEmpty('head')) {
							tableActionRow('head', "add");
						}
					});
					$('#addRowBtnBody').click(function() {
						if (checkEmpty('body')) {
							tableActionRow('body', "add");
						}
					});
					/*表格修改按钮点击*/
					$('#reviceRowBtn').click(function() {
						if (checkEmpty('head')) {
							tableActionRow('head', "revice");
						}
					});

					$('#reviceRowBtnBody').click(function() {
						if (checkEmpty('body')) {
							tableActionRow('body', "revice");
						}
					});
					/*取消选中按钮点击*/
					$('#cleanInputBtn').click(function() {
						cleanTabDiv('head');
					});
					
					$('#cleanInputBtnBody').click(function() {
						cleanTabDiv('body');
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
					/* 值转换拟态框表格新增 */
					$('#addRowBtnModal').click(function() {
						if (getI("inKv") && getI("outKv")) {
							tableActionRowModal('table_modal', "add");
						} else {
							showTip('内部值和外部值不能为空');
						}
					});

					$('#modalAddBtn').click(function() {
						getChgKvList();
					});
					/* 值转换模拟框隐藏时，重置 */
					$('#modalDiv').on('hide.bs.modal', function() {
						var rowArr = $('#table_modal').bootstrapTable('getData');
						if (rowArr == 0) {
							var flg = getI('targetFlg');
							setS("chgFlg_" + flg, 'N');
							$('#chgFlg_' + flg).change();
						} 
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
					
					/* 模板位置标志不为空时，不允许修改字段位置 */
					$('#tempFlg_head').change(function() {
						if (getS("tempFlg_head") == "") {
							$('#flg_head').multiselect('enable');
						} else {
							$('#flg_head').multiselect('disable');
						}
					});
					
					/* 模板字段下拉框变化时，点击模板表对应字段*/
					$('#tmplTableRows_head').change(function (){
						var index = parseInt(getS('tmplTableRows_head'));
						$('#tmplTable_head').find('input').eq(index).click();
					});
					
					$('#tmplTableRows_body').change(function (){
						var index = parseInt(getS('tmplTableRows_body'));
						$('#tmplTable_body').find('input').eq(index).click();
					});
					
					/* 保存按钮 */
					$('#saveBtn').click(function() {
						if (portion('commDiv')) {
							save();
						} else {
							goTop();
						}
					});

					/* 取消按钮 */
					$('#cancelBtn').click(function() {
						cancel();
					});

					$("#showBtn").click(function() {
						showDemo();
					});
					
					
					/* 设置下拉框初始值 */
					setS("clobFlg_body", '0');
					setS("chgFlg_body", "N");
					setS("clobFlg_head", '0');
					setS("chgFlg_head", "N");
				});

/* 操作表格 增加/修改 */
function tableActionRow(flg, tp) {
	var Data = {};
	var position = getS("flg_" + flg); //报文位置
	var ser = 0;
	var tempSer;
	if (position == "1") {
		Data = {
			flgStr : "头",
			ser : headSer + 1, // 序号
			fileFlg : position,
			alignMethStr : getS('alignMeth_' + flg) == "" ? ""
					: getST('alignMeth_' + flg),
			alignMeth : getS('alignMeth_' + flg),
			colName : getI('colName_' + flg),
			fileColTp : getS('colTp_' + flg),
			colTpStr : getST('colTp_' + flg),
			colLen : getI('colLen_' + flg),
			chgFlg : getS('chgFlg_' + flg),
			chgFlgStr : getST('chgFlg_' + flg),
			list : getI('chgList_' + flg),
			tempFlgStr : getST("tempFlg_" + flg),
			tempFlg : getS("tempFlg_" + flg),
			tempSer : getI("tempSer_" + flg),
			clobFlg : getS("clobFlg_" + flg),
			tempColName : getI("tempColName_" + flg),
			action : "<a onclick='reviceRow(this)'>详情</a> <a onclick='delRow(this)'>删除</a> <a onclick='rowMoveUp(this, \"head\")'>上移</a> <a onclick='rowMoveDown(this, \"head\")'>下移</a>"
		}
	} else if (position == "2") {
		Data = {
			flgStr : "体",
			ser : bodySer + 1, // 序号
			fileFlg : position,
			alignMethStr : getS('alignMeth_' + flg) == "" ? ""
					: getST('alignMeth_' + flg),
			alignMeth : getS('alignMeth_' + flg),
			colName : getI('colName_' + flg),
			fileColTp : getS('colTp_' + flg),
			colTpStr : getST('colTp_' + flg),
			colLen : getI('colLen_' + flg),
			chgFlg : getS('chgFlg_' + flg),
			chgFlgStr : getST('chgFlg_' + flg),
			list : getI('chgList_' + flg),
			tempFlgStr : getS("tempFlg_" + flg) == "" ? "" : getST('tempFlg_'
					+ flg),
			tempFlg : getS("tempFlg_" + flg),
			tempSer : getI("tempSer_" + flg),
			clobFlg : getS("clobFlg_" + flg),
			tempColName : getI("tempColName_" + flg),
			action : "<a onclick='reviceRowBody(this)'>详情</a> <a onclick='delRow(this)'>删除</a> <a onclick='rowMoveUp(this, \"body\")'>上移</a> <a onclick='rowMoveDown(this, \"body\")'>下移</a>"
		}
	} else {
		Data = {
			flgStr : "尾",
			ser : endSer + 1, // 序号
			fileFlg : position,
			alignMethStr : getS('alignMeth_' + flg) == "" ? ""
					: getST('alignMeth_' + flg),
			alignMeth : getS('alignMeth_' + flg),
			colName : getI('colName_' + flg),
			fileColTp : getS('colTp_' + flg),
			colTpStr : getST('colTp_' + flg),
			colLen : getI('colLen_' + flg),
			chgFlg : getS('chgFlg_' + flg),
			chgFlgStr : getST('chgFlg_' + flg),
			list : getI('chgList_' + flg),
			tempFlgStr : getS("tempFlg_" + flg) == "" ? "" : getST('tempFlg_'
					+ flg),
			tempFlg : getS("tempFlg_" + flg),
			tempSer : getI("tempSer_" + flg),
			clobFlg : getS("clobFlg_" + flg),
			tempColName : getI("tempColName_" + flg),
			action : "<a onclick='reviceRow(this)'>详情</a> <a onclick='delRow(this)'>删除</a> <a onclick='rowMoveUp(this, \"head\")'>上移</a> <a onclick='rowMoveDown(this, \"head\")'>下移</a>"
		}
	}
	

	
	if (tp == "add") {
		//新增行
		var rowData = getData_Tab(flg);
		switch (position) {
		case "1":
			tableAddRowTwo("table_" + flg, Data, headSer);
			headSer = headSer + 1;
			cleanTabDiv(flg);
			break;
		case "2":
			tableAddRowTwo("table_" + flg, Data, bodySer);
			bodySer = bodySer + 1;
			cleanTabDiv(flg);
			break;
		case "3":
			// table增加行
			tableAddRow("table_" + flg, Data);
			endSer = endSer + 1;
			cleanTabDiv(flg);
			break;
		default:
			showTip("位置不能为空");
		}
		if (Data.chgFlg == "Y") {
			chgNum++;
		}
		$('#table_' + flg).bootstrapTable('resetView');
	} else if (tp == "revice") {
		// 修改行
		if (rev_index == -1) {
			showTip("请先点击要修改字段的详情");
		} else {
			var rowData = getData_Tab(flg);
			var tabList = $('#table_' + flg).bootstrapTable('getData');
			Data.ser = tabList[rev_index].ser;
			tableReviceRow("table_" + flg, Data); // 每修改一行,就替换掉一个
			rev_index = -1;
			cleanTabDiv(flg);
			/*转换标志是否改变*/
			if (origChgFlg == "N" && Data.chgFlg == "Y") {
				chgNum++;
			} else if (origChgFlg == "Y" && Data.chgFlg == "N"){
				chgNum--;
			}
		}
	}
}
/* 必输字段校验 */
function checkEmpty(flg) {
	var fileFmt = getS('fileFmt');
	if (fileFmt == '') {
		showTip("请先选择格式类型！");
		return false;
	} else {
		if (portion(flg + "Div")) {
			return true;
		} else {
			return false;
		}
	}
}

//清空表格输入框、选择框
function cleanTabDiv(flg) {
	setI("colName_" + flg, "");
	setS("colTp_" + flg, "");
	setS("flg_" + flg, "");
	setS("alignMeth_" + flg, "");
	setI("colLen_" + flg, "");
	setI("chgList_" + flg, "");
	setS("chgFlg_" + flg, "N");
	$('#chgBtnDiv_' + flg).hide();
	$('#colChgDiv_' + flg).hide();
	$('#tempSerDiv_' + flg).hide();
	setS("tempFlg_" + flg, "");
	$('#tempFlg_' + flg).change();
	setI("tempSer_" + flg, "");
	setI("tempColName_" + flg, "");
	setS("clobFlg_" + flg, '0');
	setS("tmplTableRows_" + flg,"");
	$('input:radio[name="getTemp"]').prop('checked',false);

}

// 模板修改一行
function reviceRowTmpl(obj, flg) {
	/*
	 * rev_index = $(obj).parent().parent().attr("data-index") //
	 * 拿到当前点击修改按钮所在的行索引 var arrData =
	 * $('#tmplTable_head').bootstrapTable('getData');
	 */
	var data = reviceRowData(obj);
	if (data.fileFlg == '2') {
		setData_tmplTab(data, 'body');
	} else {
		setData_tmplTab(data, 'head');
	}
	
	var rev_index = $(obj).parent().parent().attr("data-index");
	setS("tmplTableRows_" + flg,rev_index);
}

// 修改一行--报文头
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

// 删除行
function delRow(obj) {
	var index = $(obj).parent().parent().attr("data-index") // 拿到当前点击删除按钮所在的行索引
	var data = reviceRowData(obj);
	var position = data.fileFlg;
	var flg;
	switch (position) {
	case "1":
		flg = "head";
		headSer = headSer - 1;
		break;
	case "2":
		flg = "body";
		bodySer = bodySer - 1;
		break;
	default:
		flg = "head";
		endSer = endSer - 1;
	}
	if (data.chgFlg == 'Y') {
		chgNum--;
	}
	deleteRow(obj); // formcheck.js 删除表格里的数据

	// 修改表格里索引后面所有数据的ser
	var tableArr = $('#table_' + flg).bootstrapTable('getData');
	for (var i = index; i < tableArr.length; i++) {
		if (position == tableArr[i].fileFlg) {
			tableArr[i].ser = tableArr[i].ser - 1; 
		} else {
			break;
		}
	}
	// 刷新一下表格
	$("#table_" + flg).bootstrapTable('load', tableArr);
}

//上移
function rowMoveUp(obj, flg) {
	var data = reviceRowData(obj);
	if (data.ser == '1') {
		return;
	}
	
	// 上移
	tableUpSort("table_" + flg, "ser");
}

//下移
function rowMoveDown(obj, flg) {
	var data = reviceRowData(obj);
	if (data.flg == '1' && data.ser == headSer) {
		return;
	}
	// 下移
	tableDownSort("table_" + flg, "ser");
}


/* 取值，转换值table新增一行 */
function tableActionRowModal(tableId, ifAddRevice) {
	var Data = {
		inKv : getI('inKv'),
		outKv : getI('outKv'),
		action : "<a onclick='deleteRow(this)'>删除</a>"
	}
	var rowArr = $('#' + tableId).bootstrapTable('getData');
	var num = rowArr.length;
	var i = 0;
	for (; i < num; i++) {
		if (rowArr[i].inKv == Data.inKv && rowArr[i].outKv == Data.outKv)
			break;
	}
	if (i >= num) {
		// table增加行
		tableAddRow(tableId, Data);
		resetInput('inKv');
		resetInput('outKv');
	} else {
		showTip("表格中存在相同数据！", "error");
	}
}

/* 转换值tale返回数据 */
function getChgKvList() {
	var rowArr = $('#table_modal').bootstrapTable('getData');
	if (rowArr == 0) {
		showTip('列表不能为空');
	} else {
		var row = JSON.stringify(rowArr);
		var flg = getI('targetFlg');
		setI("chgList_" + flg, row);
		$('#modalDiv').modal('hide');
	}
}
/* 转换值模拟框数据回显 */
function getChgModDetail(flg) {
	setI('targetFlg', flg);
	var chgStr = getI('chgList_' + flg);
	var chgList = JSON.parse(chgStr);
	$('#table_modal').bootstrapTable('load', chgList);
	$('#modalDiv').modal('show');
}
/**
 * 保存函数--保存文件转换格式信息新增
 * 
 * @returns
 */
function save() {
	var formData = getData();
	if (formData == null) {
		showTip("报文体格式配置列表不能为空", "error");
	} else {
		/* 向后台发送参数 */
		$.post(ctx + "/comp/fsvr/tec/fsvrFileFmt/update", formData, function(data) {
			if (data.returnCode !== undefined && "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
				return '0';
			} else if (data.msg_type == "success") {
				var successMsg = "修改交易[" + data.message + "]";
				showContent(successMsg, "success");
				console.info("修改交易成功");
				cancel();
			}
		}, "json");
	}

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
					$('#compNo').multiselect("disable");
					setI("fmtName",jsonObj.fmtName);
					tranTp = jsonObj.fileTranTp;
					setS("fileCode",jsonObj.fileCode);
					setS("fileFmt",jsonObj.fileFmt); 
					$('#fileFmt').change();
					setI("fmtDltSym",jsonObj.fmtDltSym);
					setS("tempFmtNo",jsonObj.tempFmtNo);
					$('#tempFmtNo').multiselect("disable");
					headSer = jsonObj.headNum;
					bodySer = jsonObj.bodyNum;
					endSer = jsonObj.tailNum;
					chgNum = jsonObj.enumNum;
					/*加载模板数据*/
					var tempData = jsonObj.tempData;
					$("#tempFileFmtShow").val(tempData.temStr);
					var bodyArr = [];
					var headArr = [];
					/* 下拉框数据数组 */
					var tmplHeadSelectArr = [];
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
							headArr[a].action = "<input type='radio' name='getTemp' onclick='reviceRowTmpl(this,\"head\")'> ";
							
							tmplHeadSelectArr[a] = {
									value : a,
									label : headArr[a].colName
								}
						}
						$('#tmplTable_head').bootstrapTable(
								'load', headArr);
					} else {
						$('#tmplTable_head').bootstrapTable(
								'removeAll');
						//$('#headDiv').hide();
					}
					
					tmplHeadSelectArr.unshift({
						label : '请选择',
						value : ''
					});
					$("select[name='tmplTableRows_head']").multiselect('dataprovider',
							tmplHeadSelectArr).multiselect('rebuild').multiselect('refresh');
					
					/* 下拉框数据数组 */
					var tmplBodySelectArr = [];
					if (tempData.bodyList != undefined
							&& tempData.bodyList != "") {
						bodyArr = tempData.bodyList;
						for (var a = 0; a < bodyArr.length; a++) {
							var flg = bodyArr[a].fileFlg;
							if (flg == '2') {
								bodyArr[a].flgStr = '体';
							}
							bodyArr[a].action = "<input type='radio' name='getTemp' onclick='reviceRowTmpl(this, \"body\")'> ";
							
							tmplBodySelectArr[a] = {
									value : a,
									label : bodyArr[a].colName
								}
						}
						$('#tmplTable_body').bootstrapTable(
								'load', bodyArr);
					} else {
						$('#tmplTable_body').bootstrapTable(
								'removeAll');
						//$('#bodyDiv').hide();
					}
					tmplBodySelectArr.unshift({
						label : '请选择',
						value : ''
					});
					$("select[name='tmplTableRows_body']").multiselect('dataprovider',
							tmplBodySelectArr).multiselect('rebuild').multiselect('refresh');
					
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
							} else if(fileHeadArr[a].chgFlg == "Y"){
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
							fileHeadArr[a].action = "<a onclick='reviceRow(this)'>详情</a> <a onclick='delRow(this)'>删除</a> <a onclick='rowMoveUp(this, \"head\")'>上移</a> <a onclick='rowMoveDown(this, \"head\")'>下移</a>";
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
							} else if(fileBodyArr[a].chgFlg == "Y"){
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
							fileBodyArr[a].action = "<a onclick='reviceRowBody(this)'>详情</a> <a onclick='delRow(this)'>删除</a> <a onclick='rowMoveUp(this, \"body\")'>上移</a> <a onclick='rowMoveDown(this, \"body\")'>下移</a>";
						}
						$('#table_body').bootstrapTable(
								'load', fileBodyArr);
					} 
				}
			}
         
		}
	},
    "json");
}
/* 获取页面请求数据 */
function getData() {
	var headArr = $('#table_head').bootstrapTable('getData');
	var bodyArr = $('#table_body').bootstrapTable('getData');
	if (bodyArr == null || bodyArr == "") {
		return null;
	}
	var rowArr = headArr.concat(bodyArr);
	/* headArr.push(...bodyArr); */
	console.log(rowArr)
	var row = JSON.stringify(rowArr);
	var data = {
		chgNo : getI('chgNo'),
		tempFmtNo : getS('tempFmtNo'),
		fmtName : getI('fmtName'),
		fileCode : getS('fileCode'),
		fileFmt : getS('fileFmt'),
		fmtDltSym : getI('fmtDltSym'),
		isHaveHead : headSer == 0 ? '2' : '1',
		isHaveTail : endSer == 0 ? '2' : '1',
		/* 头尾行数 */
		headNum : headSer == 0 ? 0 : 1,
		tailNum : endSer == 0 ? 0 : 1,
		isHaveEnumConv : chgNum == 0 ? '2' : '1',
		list : row
	}

	return data;
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
	$("#tempFlg_" + flg).change();
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
	origChgFlg = data.chgFlg;
	setS("tempFlg_" + flg, data.tempFlg);
	$('#tempFlg_' + flg).change();
	setI("tempSer_" + flg, data.tempSer);
	if (data.tempSer.indexOf(".") != -1){
		$('#colChgDiv_' + flg).show();
	}
	
}
/*获取表格输入框信息*/
function getData_Tab(flg) {
	var rowData = {
		colName : getI("colName_" + flg),
		fileColTp : getS("colTp_" + flg),
		fileFlg : getS("flg_" + flg),
		alighMeth : getS("alignMeth_" + flg),
		colLen : getI("colLen_" + flg),
	}
	return rowData;
}

/*大字段转换 --删*/
function bigColChg(flg) {
	if (getS('colChgFlg_'+flg) == "1") {
		$('#tempSerDiv_' + flg).show();
		var row = $('#table_' + flg).bootstrapTable("getData");
		var subSer = 0;
		var tempSer = getI('tempSer_'+flg);
		var tempFlg = getI('tempFlg_'+flg);
		for (var i = 0; i < row.length; i++) {
			if (row[i].tempFlg == tempFlg && row[i].tempSer.substr(0, 1) == tempSer) {
				if (row[i].tempSer.indexOf(".")!= -1){
					var temp = row[i].tempSer.split(".")[1];
					if (temp > subSer) {
						subSer = temp;
					}
				}
			}
		}
		subSer = parseInt(subSer) + 1;
		tempSer = tempSer + "." + subSer;
		setI('bigColtempSer_' + flg,tempSer);
	} else {
		setI('bigColtempSer_' + flg,"");
		$('#tempSerDiv_' + flg).hide();
	}
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

/**
 * table新增一行 传入数据json
 */
function tableAddRowTwo(tableId, data, index, serName) {
	console.info('新增行');
	if (index == null || index == undefined) {
		index = $('#' + tableId).bootstrapTable('getData').length;
	}
	if (serName != null && serName != undefined) {
		data[serName] = index + 1;
	}
	$('#' + tableId).bootstrapTable('insertRow', {
		index : index,
		row : data
	});
	$('#' + tableId).bootstrapTable('resetView');
	// showTip("新增成功!", "success");
}

function cancel() {
	parent.window.$("a[href^='#tab_list']").click();
}
/*获取模板信息*/
function getTempFmt(tempFmtNo) {
	$
			.post(
					ctx + "/comp/fsvr/tec/fsvrFileFmt/getTemPlate",
					{
						fmtNo : tempFmtNo
					},
					function(data) {
						if (data.returnCode !== undefined
								&& "0000" != data.returnCode) {
							var errMsg = "错误信息[" + data.message + "]";
							showContent(errMsg, "error");
						} else {
							$('#table_head').bootstrapTable(
							'removeAll');
							$('#table_body').bootstrapTable(
							'removeAll');
							headSer = 0;
							bodySer = 0;
							endSer = 0;
							cleanTabDiv('head');
							cleanTabDiv('body');
							tranTp = getTranTp(tempFmtNo);
							for (var i = 0; i < data.dataSetResult.length; i++) {
								for (var j = 0; j < data.dataSetResult[i].data.length; j++) {
									var jsonObj = data.dataSetResult[i].data[j];
									console.info(jsonObj);
									$("#tempFileFmtShow").val(jsonObj.temStr);
									var bodyArr = [];
									var headArr = [];
									if (jsonObj.headList != undefined
											&& jsonObj.headList != "") {
										headArr = jsonObj.headList;
										for (var a = 0; a < headArr.length; a++) {
											var flg = headArr[a].fileFlg;
											if (flg == '1') {
												headArr[a].flgStr = '头';
											} else if (flg == '3') {
												headArr[a].flgStr = '尾';
											}
											headArr[a].action = "<input type='radio' name='getTemp' onclick='reviceRowTmpl(this,\"head\")'> ";
										}
										$('#tmplTable_head').bootstrapTable(
												'load', headArr);
									} else {
										$('#tmplTable_head').bootstrapTable(
												'removeAll');
										$('#headDiv').hide();
									}

									if (jsonObj.bodyList != undefined
											&& jsonObj.bodyList != "") {
										bodyArr = jsonObj.bodyList;
										for (var a = 0; a < bodyArr.length; a++) {
											var flg = bodyArr[a].fileFlg;
											if (flg == '2') {
												bodyArr[a].flgStr = '体';
											}
											bodyArr[a].action = "<input type='radio' name='getTemp' onclick='reviceRowTmpl(this,\"body\")'> ";
										}
										$('#tmplTable_body').bootstrapTable(
												'load', bodyArr);
									} else {
										$('#tmplTable_body').bootstrapTable(
												'removeAll');
										$('#bodyDiv').hide();
									}
									
								}
							}
						}
					}, "json");
}

function getTranTp(tempFmtNo){
	$
	.post(
			ctx + "/comp/fsvr/tec/tfsvrFileTotPara/get",
			{
				fmtNo : tempFmtNo
			},
			function(data) {
				if (data.returnCode !== undefined
						&& "0000" != data.returnCode) { 
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					for (var i = 0; i < data.dataSetResult.length; i++) {
						for (var j = 0; j < data.dataSetResult[i].data.length; j++) {
							var jsonObj = data.dataSetResult[i].data[j];
							console.info(jsonObj);
							tranTp = jsonObj.fileTranTp;
						}
					}
				}
			}, "json");
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

