$(document).ready(
		function() {
			parent.window.$("#iframe_list").show();

			$("#listBtn").click(
					function() {
						/* 重置搜索下标，跳转到第一页 */
						var $preClick = $("#tfsvrPtcptParaTable").parent()
								.parent().find(".page-pre");
						if ($preClick.siblings().length > 1) {
							$preClick.next().click();
						}
						$("#tfsvrPtcptParaTable").bootstrapTable('refresh');
					});

			/* 初始化表格显示列长度20 */
			$("#tfsvrPtcptParaTable").on('load-success.bs.table',
					function(data) {
						SmartWeb.swJS.index.init({
							id : 'tfsvrPtcptParaTable'
						}, {
							dftlen : 20
						});
					});
		});

/* 分页查询传参方法 */
function queryParams(params) {
	var formData = $("#listForm").serializeObject();
	var paramList = {
		callerId : (typeof (formData.callerId) == undefined) ? ''
				: formData.callerId,
		stat : (typeof (formData.stat) == undefined) ? '' : formData.stat,
		pgside : 'server',/* 服务器分页 */
		pageSize : params.limit,
		start : params.offset + 1,
		pageNo : getPage(params),
		sort : params.sort,
		order : params.order
	};
	return paramList;
}

function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		return params.offset / params.limit + 1;
	}
}

/* 跳转更新页面 */
function update(callerId) {
	console.info("open Update tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_callerId', callerId);
	para = para + "callerId=" + callerId;
	parent.window.$("a[href^='#tab_update']").attr("url",
			ctx + "/comp/fsvr/tec/tfsvrPtcptPara/tfsvrPtcptParaUpdate" + para);
	parent.window.$("a[href^='#tab_update']").click();
}

/* 跳转明细页面 */
function detail(callerId) {
	console.info("open detail tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_callerId', callerId);
	para = para + "callerId=" + callerId;
	parent.window.$("a[href^='#tab_detail']").attr("url",
			ctx + "/comp/fsvr/tec/tfsvrPtcptPara/tfsvrPtcptParaDetail" + para);
	parent.window.$("a[href^='#tab_detail']").click();
}
/* 改变状态 */
function statChange(callerId, stat) {
	var url = ctx + "/comp/fsvr/tec/tfsvrPtcptPara/statChange";
	$.post(url, {
		callerId : callerId,
		stat : stat

	}, function(data) {
		if (data.returnCode !== undefined && "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
			return '0';
		} else if (data.msg_type == "success") {
			var successMsg = "状态修改[" + data.message + "]";
			showContent(successMsg, "success");
			$("#listBtn").click();
		}
	}, "json");
}
/* 删除操作 */
function del(callerId) {
	confirmx("是否确定删除该调用方信息信息", function() {
		var url = ctx + "/comp/fsvr/tec/tfsvrPtcptPara/delete";
		// 向后台发送参数
		$.post(url, {
			callerId : callerId

		}, function(data) {
			if (data.returnCode !== undefined && "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
				return '0';
			} else if (data.msg_type == "success") {
				var successMsg = "删除信息[" + data.message + "]";
				showContent(successMsg, "success");
				$("#listBtn").click();
			}
		}, "json");
	});
}