$(document).ready(function(){
	parent.window.$("#iframe_list").show();

	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#ctrlTPipBusiBrchOpenTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#ctrlTPipBusiBrchOpenTable").bootstrapTable('refresh');
	});

	/*初始化表格显示列长度20*/
	$("#ctrlTPipBusiBrchOpenTable").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'ctrlTPipBusiBrchOpenTable'},{dftlen:20});
	});
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		busiNo: (typeof(formData.busiNo)==undefined)?'':formData.busiNo,
		brch: (typeof(formData.brch)==undefined)?'':formData.brch,
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
function update(busiNo, brch){
	console.info("open Update tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_busiNo',busiNo);
	para = para + "busiNo="+busiNo;
	$.session.set('HID_brch',brch);
	para = para + "brch="+brch;
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/comp/ctrl/oper/ctrlTPipBusiBrchOpen/ctrlTPipBusiBrchOpenUpdate" + para);
	parent.window.$("a[href^='#tab_update']").click();
}

/*跳转明细页面*/
function detail(busiNo, brch){
	console.info("open detail tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_busiNo',busiNo);
	para = para + "busiNo="+busiNo;
	$.session.set('HID_brch',brch);
	para = para + "brch="+brch;
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/comp/ctrl/oper/ctrlTPipBusiBrchOpen/ctrlTPipBusiBrchOpenDetail" + para);
	parent.window.$("a[href^='#tab_detail']").click();
}

/*删除操作*/
function del(busiNo, brch){
	confirmx("是否确定删除该业务机构开通信息", function(){
		var url = ctx + "/comp/ctrl/oper/ctrlTPipBusiBrchOpen/delete";
		//向后台发送参数
		$.post(url,
				{ 
					busiNo:busiNo, 
					brch:brch

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

/*开通操作*/
function openFlg(busiNo, brch){
		var url = ctx + "/comp/ctrl/oper/ctrlTPipBusiBrchOpen/updateFlg";
		//向后台发送参数
		$.post(url,
				{ 
			busiNo:busiNo, 
			brch:brch,
			flg:"Y"
			
				},
				function(data){ 
					if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
						var errMsg = "错误信息["+data.message+"]"; 
						showContent(errMsg,"error");
						return '0';
					}else if(data.msg_type == "success"){
						var successMsg = "机构开通["+data.message+"]"; 
						showContent(successMsg,"success");
						$("#listBtn").click();
					}
				}, "json");
	
}

/*关闭操作*/
function closeFlg(busiNo, brch){
	var url = ctx + "/comp/ctrl/oper/ctrlTPipBusiBrchOpen/updateFlg"; 
	//向后台发送参数
	$.post(url,
			{ 
		busiNo:busiNo, 
		brch:brch,
		flg:"N"
			
			},
			function(data){ 
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var errMsg = "错误信息["+data.message+"]"; 
					showContent(errMsg,"error");
					return '0';
				}else if(data.msg_type == "success"){
					var successMsg = "机构关闭["+data.message+"]"; 
					showContent(successMsg,"success");
					$("#listBtn").click();
				}
			}, "json");
	
}