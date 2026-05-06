$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_add").show();
	
	/* table初始化 */
	tableClick("table");
	
	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(portion("descDiv")){
			if (checkBrch() && checkAtomProd() && checkImg()) {
				submit();
			}
			
		};
	});
	
	$("#addBtn").on("click" ,function(){
		tableActionRow('table',"add");
	});
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	prodLineList();
	getCompList();
});

/* 取值，table新增一行 */
function tableActionRow(tableId,ifAddRevice){
	var rowArr = $('#table').bootstrapTable('getData');
	if(portion("colDiv")){
		var compNo = getS('compNo');
		var compName = $('#compNo option:selected').text();
		var atomProdCode = getS("atomProdCode");
		var atomProdDesc = $('#atomProdCode option:selected').text();
		var Data={
				compNo : compNo,
				compName : compName,
				atomProdCode : atomProdCode,
				atomProdDesc : atomProdDesc,
				action:"<a href='javascript:;' onclick='deleteRow(this)'>删除</a>",
		}
		if(ifAddRevice=="add"){
			/*table增加行*/
			for (var i=0;i<rowArr.length;i++) {
				var data = rowArr[i];
				if (data.compNo == compNo) {
					showTip("已存在相同组件["+compName+"]", "error");
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
	$("#compNo").multiselect("select", "").multiselect('rebuild');
	$("#atomProdCode").multiselect("select", "").multiselect('rebuild');
}


function submit(){
	confirmx('是否新增可售产品', function(){
		save();
	});
}

/**
 * 保存函数--保存可售产品新增
 * @returns
 */
function save(){
	var prodLineName = $('#prodLineCode option:selected').text();
	$("#prodLineName").val(prodLineName);
	var formData = $("#addForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/prod/oper/definition/saleprod/tPipSaleProd/insert", formData,
		function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
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
	
}

/*
 * 检查是否已添加原子产品
 */
function checkAtomProd() {
	
	var rowArr = $('#table').bootstrapTable('getData');
	for (var i=0;i<rowArr.length;i++){
		var rowData = rowArr[i];
		rowData.remark1 = i;
	}
	var rowArrIndex = $('#table').bootstrapTable('getData');
	var row = JSON.stringify(rowArrIndex);
	$("#atomProdJson").val(row);

	if ($("#atomProdJson").val() == "[]") {
		showTip("请添加原子产品", "error");
		return false;
	} 
	return true;
}

/*
 * 检查是否已上传图片
 */
function checkImg() {
	var url = $("[name=url]").val();
//	var omUrl = $("[name=omUrl]").val();
	if (url == "") {
		showTip("请添加图片", "error");
		return false;
	} 
	return true;
}


function checkBrch() {
	var brchId = $("[name=brchId]").val();
	if (brchId == "brchIdValue") {
		showTip("请选择主管机构", "error");
		return false;
	}
	return true;
}



/**
 * 获取产品线下拉框
 */
function prodLineList() {
	datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("prodLineCode", "/prod/oper/line/tPipLineProd/list", "prodLineCode", "prodLineName", datass, "nulls", false, false);
}
/**
 * 获取模型下拉框
 */
function getCompList() {
	datass = {flg:"1", start:'0', pageSize:'0'};
	setSelect2("compNo", "/prod/comp/tPipComp/list", "compNo", "compName", datass, "nulls", false, false);
	$("#compNo").on('change', function(){
		compNo = $(this).val();
		getAtomProdList(compNo);
	});
}
/**
 * 根据模型号获取原子产品
 * @param compNo
 */
function getAtomProdList(compNo) {
	datass = {compNo:compNo, start:'0', pageSize:'0'};
	setSelect2("atomProdCode", "/prod/oper/definition/atom/tPipAtomProd/list", "atomProdCode", "atomProdDesc", datass, "nulls", false, false);
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}