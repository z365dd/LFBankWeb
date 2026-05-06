$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_add").show();
	
	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(proof()){
			if (checkPara() && checkScen()) {
				submit();
			}
		};
	});
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});

	$("#addAttrBtn").on("click" ,function(){
		tableActionAttrRow('attrTable',"add");
	});

	$("#addBtn").on("click" ,function(){
		tableActionRow('table',"add");
	});

	attrList();
});

function submit(){
	confirmx('是否新增服务', function(){
		save();
	});
}

/**
 * 保存函数--保存服务新增
 * @returns
 */
function save(){
	var formData = $("#addForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/prod/oper/tPipSvc/insert", formData,
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

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}


/**
 * 获取属性下拉框
 */
function attrList() {
	datass = {KEY_TP:"01", start:'0', pageSize:'0'};
	setSelect2("KEY_NO", "/prod/oper/prodAttrDict/qry", "KEY_NO", "KEY_NAME", datass, "nulls", false, false);
}

/*
 * 检查是否已添加属性
 */
function checkPara() {

	var rowArrIndex = $('#attrTable').bootstrapTable('getData');
	var row = JSON.stringify(rowArrIndex);
	$("#rowPara").val(row);

	if ($("#rowPara").val() == "[]") {
		// showTip("请选择属性", "error");
		// return false;
	}
	return true;
}

/*
 * 检查是否已添加场景
 */
function checkScen() {

	var rowArrIndex = $('#table').bootstrapTable('getData');
	var row = JSON.stringify(rowArrIndex);
	$("#rowScen").val(row);

	if ($("#rowScen").val() == "[]") {
		// showTip("请配置场景", "error");
		// return false;
	}
	return true;
}

/* 取值，table新增一行 */
function tableActionAttrRow(tableId,ifAddRevice){
	var rowArr = $('#attrTable').bootstrapTable('getData');
	if(portion("colAttrDiv")){
		var KEY_NO = getS('KEY_NO');
		if ("" == KEY_NO) {
			showTip("属性不能为空", "error");
			return 0;
		}
		var KEY_NAME = $('#KEY_NO option:selected').text();
		var Data={
			KEY_NO : KEY_NO,
			KEY_NAME : KEY_NAME,
			action:"<a href='javascript:;' onclick='deleteRow(this)'>删除</a>",
		}
		if(ifAddRevice=="add"){
			/*table增加行*/
			for (var i=0;i<rowArr.length;i++) {
				var data = rowArr[i];
				if (data.KEY_NO == KEY_NO) {
					showTip("已存在相同属性["+KEY_NAME+"]", "error");
					return 0;
				}
			}
			tableAddRow(tableId,Data);
			clearAttrInput();
		}else if(ifAddRevice=="revice"){
			/*修改行*/
			tableReviceRow(tableId,Data);
			clearAttrInput();
		}
	}
}

/*清空输入框*/
function clearAttrInput(){
	$("#KEY_NO").multiselect("select", "").multiselect('rebuild');
}

/* 取值，table新增一行 */
function tableActionRow(tableId,ifAddRevice){
	var rowArr = $('#table').bootstrapTable('getData');
	if(portion("colDiv")){
		var sceneNo = getI('sceneNo');
		if ("" == sceneNo) {
			showTip("场景不能为空", "error");
			return 0;
		}
		var sceneName = getI("sceneName");
		if ("" == sceneName) {
			showTip("场景名称不能为空", "error");
			return 0;
		}
		var sceneDesc = getI("sceneDesc");
		var Data={
			sceneNo : sceneNo,
			sceneName : sceneName,
			sceneDesc : sceneDesc,
			action:"<a href='javascript:;' onclick='deleteRow(this)'>删除</a>",
		}
		if(ifAddRevice=="add"){
			/*table增加行*/
			for (var i=0;i<rowArr.length;i++) {
				var data = rowArr[i];
				if (data.sceneNo == sceneNo) {
					showTip("已存在相同场景["+sceneNo+"]", "error");
					return 0;
				}
				if (data.sceneName == sceneName) {
					showTip("已存在相同场景名称["+sceneName+"]", "error");
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
	$("#sceneNo").val("");
	$("#sceneName").val("");
	$("#sceneDesc").val("");
}

