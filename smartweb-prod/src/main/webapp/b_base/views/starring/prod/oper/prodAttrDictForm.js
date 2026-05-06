console.log('prodLineForm.js');

/*
 * 父页面变量SAVE_OR_REV add新增，revice修改
 */
var SAVE_OR_REV = parent.SAVE_OR_REV;

// 下拉框加载 同步or异步 参数
var IF_ASYNC = true;
if (SAVE_OR_REV != "add") {
	IF_ASYNC = false;
}

var SELECT_TP="";

$(function() {
	// 根据输入类型显示不同界面
	sCd("ENTER_TP", "keyTpInput", ["01"]);
	sCd("ENTER_TP", "keyTpSelect", ["02","03","04"]);
	sCs("ENTER_TP","if_multiple",["02"])
	sCs("ENTER_TP","radio_arrange",["03","04"])
	sCd("ENTER_TP", "keyTpTime", ["05"]);
	$("#ENTER_TP").on("change", showKeyTp);

	/* 返回按钮 */
	$('#cancelBtn').on('click', cancel);

	/* 提交按钮 */
	$('#submitBtn').on('click', sendData);

	/* table初始化 */
	tableClick("table");

	/* 增加btn，table增加一行 */
	$('#addBtnRow').click(function() {
		tableActionRow('table', "add");
	});

	/* table确认修改 */
	$('#reviceBtnRow').click(function() {
		tableActionRow('table', "revice");
	});
	
	

});
// 根据控件类型显示不同的界面
function showKeyTp() {
	var keyTp = getS("ENTER_TP");
	SELECT_TP="";
	if (keyTp == "02") {
		//hideSelect('radio_arrange');
		SELECT_TP="select";
	}else if(keyTp == "04"){
		SELECT_TP="radio";
	}else if(keyTp == "03"){
		SELECT_TP="checkbox";
	}
	ifrChildAut("panel2",100);
}

/* 取值，table新增一行 */
function tableActionRow(tableId, ifAddRevice) {
	if (portion("rowCommonDiv")) {
		var Data = {
			key :getI("key"),
			value : getI('value'),
			action : "<a onclick='reviceRow(this)'>修改</a> <a onclick='deleteRow(this)'>删除</a>"
		}
		if (ifAddRevice == "add") {
			// table增加行
			tableAddRow(tableId, Data);
		} else if (ifAddRevice == "revice") {
			// 修改行
			tableReviceRow(tableId, Data);
		}
	}
}
//tale修改返回数据
function reviceRow(obj) {
	var $reviceRowData = reviceRowData(obj);
	// 根据数据设置值
	setReviceRowData($reviceRowData);
}
// 点击修改回显值
function setReviceRowData(data) {
	setI("key", data.key);
	setI("value", data.value);
}

/* 关闭执行 */
function cancel() {
	parent.goTop();
	Ewin.confirm({
		title : "操作提示",
		message : "确定返回吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		parent.tab1();
	});
}

//获取变化页面的值形成数组
function getComData(){
	var arr = [];
	if($("#keyTpInput").is(":visible")){
		//校验
		var data = {};
		data.ELEM_KEY = "text.check";
		data.ELEM_NAME = "校验";
		data.ELEM_KV = getB("text_check");
		arr.push(data);
		//显示长度
		var data = {};
		data.ELEM_KEY = "text.len";
		data.ELEM_NAME = "显示长度";
		data.ELEM_KV = getI("text_len");
		arr.push(data);
		//输入提示
		var data = {};
		data.ELEM_KEY = "text.note";
		data.ELEM_NAME = "输入提示";
		data.ELEM_KV = getI("text_note");
		arr.push(data);
	}else if($("#keyTpSelect").is(":visible")){
		//排列方式
		if($('#radio_arrange').is(":visible")){
			var data={};
			if(SELECT_TP=="radio"){
				data.ELEM_KEY = "radio.arrange";
			}else if(SELECT_TP=="checkbox"){
				data.ELEM_KEY = "checkbox.arrange";
			}
			data.ELEM_NAME = "排列方式";
			data.ELEM_KV = getI("radio_arrange");
			arr.push(data);
		}
		//排列方式
		if($('#if_multiple').is(":visible")){
			var data = {};
			data.ELEM_KEY = "if.multiple";
			data.ELEM_NAME = "下拉设置";
			data.ELEM_KV = getS("if_multiple");
			arr.push(data);
		}
		//键值
		var data = {};
		var kvArr  = $('#table').bootstrapTable('getData');
		var kvStrArr = [];
		for (var a=0;a<kvArr.length;a++){
			var kvData = kvArr[a];
			var kv  = kvData.key+"!@#"+kvData.value;
			kvStrArr.push(kv);
		}
		if(SELECT_TP=="select"){
			data.ELEM_KEY = "select.kv";
		}else if(SELECT_TP=="radio"){
			data.ELEM_KEY = "radio.kv";
		}else if(SELECT_TP=="checkbox"){
			data.ELEM_KEY = "checkbox.kv";
		}
		data.ELEM_NAME = "键值";
		data.ELEM_KV =kvStrArr.join(";@;") ;
		arr.push(data);
	}else if($("#keyTpTime").is(":visible")){
		//显示格式
		var data = {};
		data.ELEM_KEY = "datetime.datafmt";
		data.ELEM_NAME = "显示格式";
		data.ELEM_KV = getS("datetime_datafmt");
		arr.push(data);
		//值格式
		var data = {};
		data.ELEM_KEY = "datetime.valuefmt";
		data.ELEM_NAME = "值格式";
		data.ELEM_KV = getS("datetime_valuefmt");
		arr.push(data);
	}
	return arr;
}

//如果为选择控件，获取key-val字符串
function getSelTpStr(){
	if($("#keyTpSelect").is(":visible")){
		var kvArr  = $('#table').bootstrapTable('getData');
		var kvStrArr = [];
		for (var a=0;a<kvArr.length;a++){
			var kvData = kvArr[a];
			var kv  = kvData.key+"!@#"+kvData.value;
			kvStrArr.push(kv);
		}
		return kvStrArr.join(";@;");
	}
}

// 获取后台需要的数据
function getData() {
	var data = {
		// 界面
		KEY_NO : getI('KEY_NO'),
		KEY_NAME : getI('KEY_NAME'),
		KEY_TP : getS('KEY_TP'),
		VAL_TP : getS('VAL_TP'),
		VAL_LEN : getI('VAL_LEN'),
		ENTER_TP : getS('ENTER_TP'),
		// INPUT_FLG : getS('INPUT_FLG'),
	}
	// 变化页面取值
	var comArr = getComData();
	var row = JSON.stringify(comArr);
	data.row = row;
	return data;
}

/* 提交执行 */
function sendData() {
	// 主要部分校验
	if (!(portion("commonDiv") && portion("keyTpInput") && portion("keyTpTime"))) {
		return;
	}
	if($("#keyTpSelect").is(":visible")){
		var kvArr  = $('#table').bootstrapTable('getData');
		if(kvArr.length==0){
			showTip("请增加选项键值！","success");
			return;
		}
	}
	var $url = ctx;
	var $data = getData();

	if (SAVE_OR_REV == "add") {
		$url += "/prod/oper/prodAttrDict/add";
	} else if (SAVE_OR_REV == "revice") {
		$url += "/prod/oper/prodAttrDict/revice";
	}
	$.ajax({
		url : $url,
		type : "POST",
		dataType : "json",
		data : $data,
		async : true,
		success : function(data) {
			if (data.returnCode !== undefined && "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var successMsg = "提交[" + data.message + "]";
				showContent(successMsg, "success");
				parent.tab1();
			}
		}
	});
}

//参数：Data回显用的数据，tp详细或修改回显，ifSelect回显的选择设置是否可以修改，ENUM_VAL_STR是否用此参数回显选项参数，pageList普通地方回显还是取页面配置详细回显
function setData(Data,tp,ifSelect,ENUM_VAL_STR,pageList) {
	setI("KEY_NO", Data.KEY_NO);
	setI("KEY_NAME", Data.KEY_NAME);
	setS("KEY_TP", Data.KEY_TP);
	$("#KEY_TP").change();
	setS("VAL_TP", Data.VAL_TP);
	$("#VAL_TP").change();
	setI("VAL_LEN", Data.VAL_LEN);
	setS("ENTER_TP", Data.ENTER_TP);
	$("#ENTER_TP").change();
	setI("QUOTE", Data.QUOTE);
	// setS("INPUT_FLG", Data.INPUT_FLG);
	//setI("KEY_NAME", Data.KEY_NAME);
	
	// var arr = Data;
	// if(pageList==true){
	// 	var arr = Data.CTRL_LIST;
	// }else{
	// 	var arr = Data.LIST;
	// }
	// console.log(arr);
	var data  = Data;
	if(data.ELEM_KEY!=undefined){
		//判断是哪种控件的设置
		if($("#keyTpInput").is(":visible")){
			//输入框设置回显
			// for(var a=0;a<arr.length;a++){
			// 	var data  =arr[a];
				if(data.ELEM_KEY=="text.check"){
					setB("text_check",data.ELEM_KV);
				}else if(data.ELEM_KEY=="text.len"){
					setI("text_len",data.ELEM_KV);
				}else if(data.ELEM_KEY=="text.note"){
					setI("text_note",data.ELEM_KV);
				}
			// }
		}else if($("#keyTpSelect").is(":visible")){
			//选择类型设置回显
			// for(var a=0;a<arr.length;a++){
			// 	var data  =arr[a];
				if(data.ELEM_KEY=="radio.arrange" ||data.ELEM_KEY=="checkbox.arrange"){
					setS("radio_arrange",data.ELEM_KV);
				}else if(data.ELEM_KEY=="select.kv" ||data.ELEM_KEY=="radio.kv" ||data.ELEM_KEY=="checkbox.kv"){
					//判断是否要用新设置的枚举值来设置选择参数回显，还是默认详细参数Data中取
					if(ENUM_VAL_STR!=undefined && ENUM_VAL_STR!=""){
						var kvArr = ENUM_VAL_STR.split(";@;");
					}else{
						var kvArr  =data.ELEM_KV.split(';@;');
					}
					//var kvArr = data.ELEM_KV.split(';');
					var tableArr = [];
					for (var a=0;a<kvArr.length;a++){
						var kv = kvArr[a];
						var kvData = {
								key:kv.split("!@#")[0],
								value:kv.split("!@#")[1],
						}
						if(tp=="revice"){
							kvData.action="<a onclick='reviceRow(this)'>修改</a> <a onclick='deleteRow(this)'>删除</a>";
						}else if(tp=="detail"){
							if(ifSelect==true){
								//如果是选择设置可以改变
								kvData.action="<a onclick='reviceRow(this)'>详细</a> <a onclick='deleteRow(this)'>删除</a>"
							}else{
								//单纯的详细全部不可改
								kvData.action="<a onclick='reviceRow(this)'>详细</a>";
							}
						}
						tableArr.push(kvData);
					}
					$('#table').bootstrapTable('load', tableArr);
				}else if(data.ELEM_KEY=="if.multiple"){
					setS("if_multiple",data.ELEM_KV);
				}
			// }
		}else if($("#keyTpTime").is(":visible")){
			//时间类型回显
			// for(var a=0;a<arr.length;a++){
			// 	var data  =arr[a];
				if(data.ELEM_KEY=="datetime.datafmt"){
					setS("datetime_datafmt",data.ELEM_KV);
				}else if(data.ELEM_KEY=="datetime.valuefmt"){
					setS("datetime_valuefmt",data.ELEM_KV);
				}
			// }
		}
	}
	

	if (tp == "detail") {
		if(ifSelect!=undefined){
			//其他页面引用此页面传此参数，是否可以启用选择设置的参数
			//$('#submitBtn').hide();
			$('#cancelBtn').hide();
		}
		/*if(ifSelect==true && $("#keyTpSelect").is(":visible")){
			//选择设置允许改变
			disDiv("commonDiv");
			disDiv("keyTpInput");
			disDiv("keyTpTime");
		}else{
			disDiv("formId_65948");
			$('#submitBtn').hide();
			$('#addBtnRow').hide();
			$('#reviceBtnRow').hide();
		}*/
		disDiv("formId_65948");
		$('#submitBtn').hide();
		$('#addBtnRow').hide();
		$('#reviceBtnRow').hide();
	} else {
		$("#KEY_NO").attr("readonly","true");
		$('#KEY_TP').multiselect("disable");
		$("#KEY_NAME").attr("readonly","true");
		$("#QUOTE").attr("readonly","true");
	}
}
