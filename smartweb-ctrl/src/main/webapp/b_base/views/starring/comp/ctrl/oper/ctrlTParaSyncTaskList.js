$(document).ready(function(){
	parent.window.$("#iframe_list").show();

	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#ctrlTParaSyncTaskTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#ctrlTParaSyncTaskTable").bootstrapTable('refresh');
	});

	/*初始化表格显示列长度20*/
	$("#ctrlTParaSyncTaskTable").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'ctrlTParaSyncTaskTable'},{dftlen:20});
	});
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		stepNo: (typeof(formData.stepNo)==undefined)?'':formData.stepNo,
		platDate: (typeof(formData.platDate)==undefined)?'':formData.platDate,
        pgside: 'server',/*服务器分页*/
        pageSize: params.limit,
        start: params.offset + 1,
        pageNo: getPage(params),
        sort: params.sort,
        order: params.order
	};
	return paramList;
}

function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

/*跳转更新页面*/
function update(stepNo, platDate){
	console.info("open Update tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_stepNo',stepNo);
	para = para + "stepNo="+stepNo;
	$.session.set('HID_platDate',platDate);
	para = para + "platDate="+platDate;
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/comp/ctrl/oper/ctrlTParaSyncTask/ctrlTParaSyncTaskUpdate" + para);
	parent.window.$("a[href^='#tab_update']").click();
}

/*跳转明细页面*/
function detail(stepNo, platDate){
	console.info("open detail tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_stepNo',stepNo);
	para = para + "stepNo="+stepNo;
	$.session.set('HID_platDate',platDate);
	para = para + "platDate="+platDate;
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/comp/ctrl/oper/ctrlTParaSyncTask/ctrlTParaSyncTaskDetail" + para);
	parent.window.$("a[href^='#tab_detail']").click();
}

/*处理操作*/
function del(stepNo, platDate){
	confirmx("是否确定处理该数据同步任务", function(){
		var url = ctx + "/comp/ctrl/oper/ctrlTParaSyncTask/delDayStep";
		//向后台发送参数
		$.post(url,
				{ 
					stepNo:stepNo, 
					platDate:platDate

				},
				function(data){ 
					if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
						var errMsg = "错误信息["+data.message+"]"; 
						showContent(errMsg,"error");
						return '0';
					}else if(data.msg_type == "success"){
						var successMsg = "处理信息["+data.message+"]"; 
						showContent(successMsg,"success");
						$("#listBtn").click();
					}
				}, "json");
	});
}