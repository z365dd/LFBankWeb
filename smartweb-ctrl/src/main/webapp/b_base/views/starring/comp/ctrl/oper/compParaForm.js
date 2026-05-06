console.log('compParaForm.js');

/*
 * 父页面变量SAVE_OR_REV add新增，revice修改，detail详细
 */
var SAVE_OR_REV = parent.SAVE_OR_REV;

// 下拉框加载 同步or异步 参数
var $IF_ASYNC = true;
if (SAVE_OR_REV != "add") {
	$IF_ASYNC = false;
}

$(function() {
	/* 加载模型下拉框的数据 */
	setCompNoS("COMP_NO", $IF_ASYNC, true);

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

	/* 返回按钮 */
	$('#cancelBtn').on('click', cancel);

	/* 提交按钮 */
	$('#addBtn').on('click', sendData);

});

/* 取值，table新增一行 */
function tableActionRow(tableId, ifAddRevice) {
	if (portion("tableDiv")) {
		var Data = {
			KEY_NAME : getI('KEY_NAME'),
			KEY_NO : getI('KEY_NO'),
			KV : getI('KV'),
			ACTION : "<a onclick='reviceRow(this)'>修改</a> <a onclick='deleteRow(this)'>删除</a>"
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

// tale修改返回数据
function reviceRow(obj) {
	var $reviceRowData = reviceRowData(obj);
	// 根据数据设置值
	setReviceRowData($reviceRowData);
}

// 点击修改回显值
function setReviceRowData(data) {
	setI("KEY_NAME", data.KEY_NAME);
	setI("KEY_NO", data.KEY_NO);
	setI("KV", data.KV);
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

/* 获取页面数据data */
function getData() {
	var rowArr = $('#table').bootstrapTable('getData');
	var num = rowArr.length;
	var row = JSON.stringify(rowArr);

	var data = {
		COMP_NO : getS('COMP_NO'),
		COMP_NAME : getST('COMP_NO'),

		LIST : row
	}
	return data;
}

/* 提交执行 */
function sendData() {
	if (portion("commonDiv")) {
		var sendData = getData();

		if (SAVE_OR_REV == "add") {
			sendData.OPER_TP = ADD;
		} else if (SAVE_OR_REV == "revice") {
			sendData.OPER_TP = REV;
		}
		$
				.ajax({
					url : ctx + "/comp/ctrl/oper/compPara/action",
					type : "POST",
					dataType : "json",
					data : sendData,
					async : true,
					success : function(data) {
						if (data.returnCode !== undefined
								&& "0000" != data.returnCode) {
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
}

// 设置值
function setData(data) {
	var data = JSON.parse(data);
	setS('COMP_NO', data.COMP_NO);

	var tableArr = data.DYN_LIST;
	if (undefined != tableArr) {
		for (var a = 0; a < tableArr.length; a++) {
			tableArr[a].ACTION = "<a onclick='reviceRow(this)'>修改</a> <a onclick='deleteRow(this)'>删除</a>";
		}
		disabledS("COMP_NO");

		$('#table').bootstrapTable('load', tableArr);
	}

}
