$(document).ready(function(){
	parent.window.$("#iframe_list").show();
	getSaleProdList();
	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#payFaceConfTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#payFaceConfTable").bootstrapTable('refresh');
	});

	/*初始化表格显示列长度20*/
	$("#payFaceConfTable").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'payFaceConfTable'},{dftlen:20});
	});
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
    	saleProdCode: (typeof(formData.saleProdCode)==undefined)?'':formData.saleProdCode,
		paySvcCode: (typeof(formData.paySvcCode)==undefined)?'':formData.paySvcCode,
		qrySvcCode: (typeof(formData.qrySvcCode)==undefined)?'':formData.qrySvcCode,
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
function update(id){
	console.info("open Update tab");
	var para = "";
	$.session.set('HID_id',id);
	para = para + "id="+id;
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/prod/oper/cloudpay/payconf/payFaceConf/payFaceConfUpdate");
	parent.window.$("a[href^='#tab_update']").click();
}

/*跳转明细页面*/
function detail(id){
	console.info("open detail tab");
	var para = "";
	$.session.set('HID_id',id);
	para = para + "id="+id;
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/prod/oper/cloudpay/payconf/payFaceConf/payFaceConfDetail");
	parent.window.$("a[href^='#tab_detail']").click();
}

/*删除操作*/
function del(saleProdCode){
	confirmx("是否确定删除该缴费界面配置信息", function(){
		var url = ctx + "/prod/oper/cloudpay/payconf/payFaceConf/delete";
		//向后台发送参数
		$.post(url,
				{ 
					saleProdCode : saleProdCode

				},
				function(data){ 
					if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
						var errMsg = "错误信息["+data.message+"]"; 
						showContent(errMsg,"error");
						return '0';
					}else if(data.msg_type == "success"){
						var successMsg = "删除信息["+data.message+"]"; 
						showContent(successMsg,"success");
						$("#listBtn").click();
					}
				}, "json");
	});
}



function getSaleProdList() {
    var datass = {compNo:"999301", start:'0', pageSize:'0'};
    setSelect2("saleProdCode", "/prod/oper/cloudpay/pay/listSaleProd", "saleProdCode", "saleProdDesc", datass, "nulls", false, false);
}