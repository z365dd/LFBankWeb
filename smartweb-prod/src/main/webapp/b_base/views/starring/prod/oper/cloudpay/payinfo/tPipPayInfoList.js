$(document).ready(function(){
	parent.window.$("#iframe_list").show();
	getSaleProdList();
	
	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#tPipPayInfoTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#tPipPayInfoTable").bootstrapTable('refresh');
	});

	/*初始化表格显示列长度20*/
	$("#tPipPayInfoTable").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'tPipPayInfoTable'},{dftlen:20});
	});
	
	//业务编号
//	$("#BUSI_NO").click(function(){
//		busiClick("BUSI_NO",null,null);
//	});
	
});

function getSaleProdList() {
	var datass = {compNo:"999301", start:'0', pageSize:'0'};
	setSelect2("saleProdCode", "/prod/oper/cloudpay/pay/listSaleProd", "saleProdCode", "saleProdDesc", datass, "nulls", false, true, true);
}

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
        saleProdCode: (typeof(formData.saleProdCode)==undefined)?'':formData.saleProdCode,
		payNo: (typeof(formData.payNo)==undefined)?'':formData.payNo,
		strTime: (typeof(formData.str_time)==undefined)?'':formData.str_time,
		endTime: (typeof(formData.end_time)==undefined)?'':formData.end_time,
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
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/prod/oper/cloudpay/payinfo/tPipPayInfoUpdate" + para);
	parent.window.$("a[href^='#tab_update']").click();
}

/*跳转明细页面*/
function detail(busiNo,payNo){
	console.info("open detail tab");
	var para = "";
	$.session.set('HID_busiNo',busiNo);
	$.session.set('HID_payNo',payNo);
	para = para + "?";
	para = para + "busiNo="+busiNo;
	para = para + ", payNo="+payNo;
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/prod/oper/cloudpay/payinfo/tPipPayInfoDetail" + para);
	parent.window.$("a[href^='#tab_detail']").click();
}

/*删除操作*/
function del(id){
	confirmx("是否确定删除该缴费信息信息", function(){
		var url = ctx + "/prod/oper/cloudpay/payinfo/delete";
		//向后台发送参数
		$.post(url,
				{ 
					id : id

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