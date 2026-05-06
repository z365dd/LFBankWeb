console.log('freshParaForm.js');


$(function() {
	//getBusiInfo();
	/* 查询按钮点击进行查询 */
	$('#addBtn').click(function() {
		var busiName = getI("busiName");
		if(busiName==""){
			showTip("请选择业务后再刷新","error");
			return;
		}
		if (proof()) {
			Ewin.confirm({
				title : "操作提示",
				message : "确定刷新单位业务参数吗？"
			}).on(function(e) {
				if (!e) {
					return;
				}
				fresh();
			});
		}
	});
	
	//双击事件
	$("#busiListTable").on('dbl-click-row.bs.table', function (evnet, row, $element) {
		setI("busiNo",row.busiNo);
		setI("busiName",row.busiNo+"-"+row.busiName);
		$('#modalBusiList').modal('hide');
	});
	
});


function fresh(entrNo, stat) {
	var formData = $("#addForm").serializeObject();
	$.post(ctx + "/prod/om/freshPara/fresh",formData, function(data) {
		if (data.returnCode !== undefined && "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
		} else {
			console.log(data.message);
			var successMsg='';
			successMsg = "刷新[" + data.message + "]";
			showContent(successMsg, "success");
		}
	}, "json");
}

function getBusiInfo() {
	setSelect2("busiNo", "/prod/om/freshPara/getBusiInfo", "busiNo", "busiName", {}, "nulls", false, false);
}

