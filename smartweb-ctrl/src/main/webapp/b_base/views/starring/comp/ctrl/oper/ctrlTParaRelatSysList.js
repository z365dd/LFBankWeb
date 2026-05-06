console.log('ctrlTParaRelatSysList.js');
$(document).ready(function(){
	parent.window.$("#iframe_list").show();

	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#ctrlTParaRelatSysTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#ctrlTParaRelatSysTable").bootstrapTable('refresh');
	});
	
	var HID_relatSys = $.session.get('HID_relatSys');
	/*从session中移除relatSys*/
	$.session.remove('HID_relatSys');
	if (HID_relatSys != undefined) {
		$("#relatSys").val(HID_relatSys);
	}
	$("#listBtn").click();
	/*初始化表格显示列长度20*/
	$("#ctrlTParaRelatSysTable").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'ctrlTParaRelatSysTable'},{dftlen:20});
	});
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		relatSys: (typeof(formData.relatSys)==undefined)?'':formData.relatSys,
		sysTp: (typeof(formData.sysTp)==undefined)?'':formData.sysTp,
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
function update(relatSys){
	console.info("open Update tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_relatSys',relatSys);
	para = para + "relatSys="+relatSys;
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/comp/ctrl/oper/ctrlTParaRelatSys/ctrlTParaRelatSysUpdate" + para);
	parent.window.$("a[href^='#tab_update']").click();
}

/*跳转明细页面*/
function detail(relatSys){
	console.info("open detail tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_relatSys',relatSys);
	para = para + "relatSys="+relatSys;
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/comp/ctrl/oper/ctrlTParaRelatSys/ctrlTParaRelatSysDetail" + para);
	parent.window.$("a[href^='#tab_detail']").click();
}

/*删除操作*/
function del(relatSys){
	confirmx("是否确定删除该关联系统管理信息", function(){
		var url = ctx + "/comp/ctrl/oper/ctrlTParaRelatSys/delete";
		//向后台发送参数
		$.post(url,
				{ 
					relatSys:relatSys

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