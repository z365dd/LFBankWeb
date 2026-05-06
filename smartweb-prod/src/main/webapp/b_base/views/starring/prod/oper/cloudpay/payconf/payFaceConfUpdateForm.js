var action = "<a href='javascript:;' onclick='deleteRow(this)'>删除</a> <a href='javascript:;'onclick='moveUp(this)'>上移</a> <a href='javascript:;' onclick='moveDown(this)'>下移</a>";
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出HID_id*/
	var HID_id = $.session.get('HID_id');
	/*从session中移除HID_id*/
	$.session.remove('HID_id');
	$("#HID_id").val(HID_id);

	getDetail(HID_id);
	
	/* table初始化 */
	tableClick("table");
	
	getSaleProdList();
	getKeyList();
	
	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(portion("payConf") && checkCol()){
			submit();
		};
	});
	
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
	
	$("#addBtn").on("click" ,function(){
		if(portion("colDiv")){
			tableActionRow('table',"add");
		}
	});
});

function submit(){
	confirmx('是否更新缴费界面配置', function(){
		save();
	});
}

/**
 * 保存函数--保存缴费界面配置修改
 * @returns
 */
function save(){
	var formData = $("#payFaceConfForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/prod/oper/cloudpay/payconf/payFaceConf/update", formData,
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
function getDetail(HID_id){
	console.info('update payFaceConf info......');
	$.post(ctx + "/prod/oper/cloudpay/payconf/payFaceConf/get", {saleProdCode:HID_id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				var dataSetName = data.dataSetResult[i].dataSetName;
				if (dataSetName == "paraDs") {
					var list = data.dataSetResult[i].data;
					if (undefined != list) {
						for(var i=0;i<list.length;i++){
							list[i].action = action;
						}
					}
					$("#table").bootstrapTable('load', list);
				}
				if (dataSetName == "confDs") {
					for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
						var jsonObj = data.dataSetResult[i].data[j];
						console.info(jsonObj);
						$("#saleProdCode").multiselect("select", jsonObj.saleProdCode).multiselect('rebuild');
						getSvc(jsonObj.saleProdCode);
						$("#paySvcCode").multiselect("select", jsonObj.paySvcCode).multiselect('rebuild');
						$("#qrySvcCode").multiselect("select", jsonObj.qrySvcCode).multiselect('rebuild');
					}
				}
				
			}
			
			/*触发校验*/
			$('#saleProdCode').blur();
			$('#paySvcDode').blur();

		}
	},
    "json");
}
//var dataSetName = data.dataSetResult[i].dataSetName;


function getSaleProdList() {
    var datass = {compNo:"999301", start:'0', pageSize:'0'};
    setSelect2("saleProdCode", "/prod/oper/cloudpay/pay/listSaleProd", "saleProdCode", "saleProdDesc", datass, "nulls", false, false);
    $("#saleProdCode").on("change", function(){
		getSvc($(this).val());
	});
}


function getSvc(saleProdCode) {
	var $select = $("select[id$='SvcCode']");
	var datass = {saleProdCode:saleProdCode, start:'0', pageSize:'0'};
	
	$select.each(function(){
		var name = $(this).attr("name");
		setSelect2(name, "/prod/oper/cloudpay/payconf/payFaceConf/listSvc", "svcCode", "svcName", datass, "nulls", false, false);
	});
	
}

function getKeyList() {
    var datass = {KEY_TP:"04", start:'0', pageSize:'0'};
    setSelect2("keyNo", "/prod/oper/prodAttrDict/qry", "KEY_NO", "KEY_NAME", datass, "nulls", false, false);
}


/* 取值，table新增一行 */
function tableActionRow(tableId,ifAddRevice){
	var rowArr = $('#table').bootstrapTable('getData');
	var keyNo = getS('keyNo');
	var keyName = $('#keyNo option:selected').text();
	var enterFlg = getS("enterFlg");
	var trgQryFlg = getS("trgQryFlg");
	var ordAmtFlg = getS("ordAmtFlg");
	
	var Data={
			keyNo : keyNo,
			keyName : keyName,
			enterFlg : enterFlg,
			trgQryFlg : trgQryFlg,
			ordAmtFlg : ordAmtFlg,
			action  :action,
	}
	if(ifAddRevice=="add"){
		/*table增加行*/
		for (var i=0;i<rowArr.length;i++) {
			var data = rowArr[i];
			if (data.keyNo == keyNo) {
				showTip("已存在相同属性["+keyName+"]", "error");
				return 0;
			}
			if (trgQryFlg == "Y") {
				if (data.trgQryFlg == trgQryFlg) {
					showTip("只能有一个[触发查询]的属性", "error");
					return 0;
				}
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

/*
 * 上移
 */
function moveUp(serName ,callBackFunc) {
	var tableId = "table";
	tableUpSort(tableId, serName, callBackFunc);
}

// 下移
function moveDown(serName ,callBackFunc) {
	var tableId = "table";
	tableDownSort(tableId, serName, callBackFunc);
}


function flgFormat(value, row, index){
	if(value == "Y"){
		return "是";
	}else if(value == "N"){
		return "否";
	}
}

function enterFlgFormat(value, row, index){
	if(value == "01"){
		return "可输";
	}else if(value == "02"){
		return "不可输";
	}
}

function clearInput() {
	$("#colDiv").find("select").multiselect("select", "").multiselect('rebuild');
}


/*
 * 检查是否已添加属性
 */
function checkCol() {

	var rowArrIndex = $('#table').bootstrapTable('getData');
	
	if (rowArrIndex.length == 0) {
		showTip("请添加界面属性", "error");
		return false;
	}
	
	for (var i=0;i<rowArrIndex.length;i++){
		var rowData = rowArrIndex[i];
		rowData.ser = i;
	}
	
	var row = JSON.stringify(rowArrIndex);
	$("#colJson").val(row);
	
	return true;
}
