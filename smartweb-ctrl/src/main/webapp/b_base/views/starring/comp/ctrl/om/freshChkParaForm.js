console.log('freshChkParaForm.js');


$(function() {
	/* 查询按钮点击进行查询 */
	$('#addBtn').click(function() {
		Ewin.confirm({
			title : "操作提示",
			message : "确定刷新业务参数吗？"
		}).on(function(e) {
			if (!e) {
				return;
			}
			fresh();
		});
	});
});


function fresh(entrNo, stat) {
	$.post(ctx + "/comp/ctrl/om/freshChkPara/fresh", {
		
	}, function(data) {
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

