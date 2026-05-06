$(document).ready(function(){
	parent.window.$("#iframe_list").show();
	getSaleProdList();
	
	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#tPipOrdInfoTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#tPipOrdInfoTable").bootstrapTable('refresh');
	});

	/*初始化表格显示列长度20*/
	$("#tPipOrdInfoTable").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'tPipOrdInfoTable'},{dftlen:20});
	});
	
	//业务编号
//	$("#BUSI_NO").click(function(){
//		busiClick("BUSI_NO",null,null);
//	});
	
	$("#expBtn").click(function(){
		if(!proof()){
			return;
		}
	        var formData = $("#ex_importForm").serializeObject();
	        top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
	            if(v=="ok"){
	                $("#listForm").attr("action",ctx+"/prod/oper/cloudpay/info/exportData");
	                $("#listForm").submit();
	            }
	        },{buttonsFocus:1});
	        top.$('.jbox-body .jbox-icon').css('top','55px');
	    });
	
});

function getSaleProdList() {
	var datass = {compNo:"999301", start:'0', pageSize:'0'};
	setSelect2("saleProdCode", "/prod/oper/cloudpay/pay/listSaleProd", "saleProdCode", "saleProdDesc", datass, "nulls", false, false, true);
	/*统计页面跳转过来时，自动调用查询*/
	getSessionData();
}

/*统计页面跳转过来时，自动调用查询*/
function getSessionData() {
	var date = $.session.get('dateStats');
	if (date) {
		   /*默认开始时间和结束时间，用于拼接*/
		   var starTime = '20200101000000';
		   var endTime = '20201231235959';
		   /*统计类型为月份时，需获取该月份的天数*/
		   if (date.length == 6) {
			   var d = new Date(date.substring(0,4),date.substring(4,6),0);
			   var day = d.getDate();
			   setDateValue('end_time',date+day+endTime.substring(8));
		   } else {
			   setDateValue('end_time',date+endTime.substring(date.length));
		   }
		   setDateValue('str_time',date+starTime.substring(date.length));
		   setI("payNo",$.session.get('payNoStats')); 
	       setS("saleProdCode",$.session.get('saleProdCodeStats'));
	       $.session.remove('dateStats');
	       $.session.remove('payNoStats');
	       $.session.remove('saleProdCodeStats');
	       /*重置搜索下标，跳转到第一页*/
			var $preClick = $("#tPipOrdInfoTable").parent().parent().find(".page-pre");
			if($preClick.siblings().length>1){
				$preClick.next().click();
			}
	        $("#tPipOrdInfoTable").bootstrapTable('refresh');
	}
}

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
	console.info("listForm.data");
    var paramList = {
		payNo: (typeof(formData.payNo)==undefined)?'':formData.payNo,
		saleProdCode: (typeof(formData.saleProdCode)==undefined)?'':formData.saleProdCode,
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
function update(ordNo){
	console.info("open Update tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_ordNo',ordNo);
	para = para + "ordNo="+ordNo;
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/prod/oper/cloudpay/info/tPipOrdInfoUpdate" + para);
	parent.window.$("a[href^='#tab_update']").click();
}

/*跳转明细页面*/
function detail(ordNo){
	console.info("open detail tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_ordNo',ordNo);
	para = para + "ordNo="+ordNo;
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/prod/oper/cloudpay/info/tPipOrdInfoDetail" + para);
	parent.window.$("a[href^='#tab_detail']").click();
}

/*删除操作*/
function del(ordNo){
	confirmx("是否确定删除该订单信息信息", function(){
		var url = ctx + "/prod/oper/cloudpay/info/delete";
		//向后台发送参数
		$.post(url,
				{ 
					ordNo:ordNo

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

