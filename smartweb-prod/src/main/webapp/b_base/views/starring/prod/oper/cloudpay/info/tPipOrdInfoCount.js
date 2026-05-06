console.log("tPipOrdInfoCount.js")
$(function() {
	setSelect2("saleProdCode", "/prod/oper/cloudpay/pay/listSaleProd",
			"saleProdCode", "saleProdDesc", {
				compNo : "999301",
				start : '0',
				pageSize : '0'
			}, "nulls", false, true, true);

	$("#listBtn").click(function() {
		if (proof()) {
			$("#tPipOrdInfoCountTable").bootstrapTable('refresh');
		}
	});

	$("#expBtn")
			.click(
					function() {
						if (!proof()) {
							return;
						}
						setI("countTypeStr", getST("countType"));
						setI("saleProdDesc", getST("saleProdCode"));
						var formData = $("#countListForm").serializeObject();

						top.$.jBox
								.confirm(
										"确认要导出数据吗？",
										"系统提示",
										function(v, h, f) {
											if (v == "ok") {
												$("#countListForm")
														.attr(
																"action",
																ctx
																		+ "/prod/oper/cloudpay/info/exportStatsData");
												$("#countListForm").submit();
											}
										}, {
											buttonsFocus : 1
										});
						top.$('.jbox-body .jbox-icon').css('top', '55px');
					});
});

/* 分页查询传参方法 */
function queryParams(params) {
	var formData = $("#countListForm").serializeObject();
	console.info("countListForm.data");
	var paramList = {
		payNo : (typeof (formData.payNo) == undefined) ? '' : formData.payNo,
		saleProdCode : (typeof (formData.saleProdCode) == undefined) ? ''
				: formData.saleProdCode,
		saleProdDesc : (typeof (formData.saleProdCode) == undefined) ? ''
				: getST("saleProdCode"),
		strTime : (typeof (formData.str_time) == undefined) ? ''
				: formData.str_time,
		endTime : (typeof (formData.end_time) == undefined) ? ''
				: formData.end_time,
		countType : (typeof (formData.countType) == undefined) ? ''
				: formData.countType,
		pgside : 'server',/* 服务器分页 */
		pageSize : 0,
		start : 0,
		pageNo : getPage(params),
		sort : params.sort,
		order : params.order
	};
	return paramList;
}
/*跳转至缴费订单查询界面*/
function toOrdList(date, saleProdCode, payNo) {
     $.session.set("dateStats",date);
     $.session.set("saleProdCodeStats",saleProdCode);
     $.session.set("payNoStats",payNo);
	 parent.window.openMenu("云缴费->商户运营->缴费订单查询");
}

function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		return params.offset / params.limit + 1;
	}
}