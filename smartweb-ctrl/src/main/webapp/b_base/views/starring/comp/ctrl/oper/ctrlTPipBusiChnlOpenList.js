console.log("ctrlTPipBusiChnlOpenList.js");
$(document).ready(function(){
	parent.window.$("#iframe_list").show();

	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#ctrlTPipBusiChnlOpenTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#ctrlTPipBusiChnlOpenTable").bootstrapTable('refresh');
	});

	/*初始化表格显示列长度20*/
	$("#ctrlTPipBusiChnlOpenTable").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'ctrlTPipBusiChnlOpenTable'},{dftlen:20});
	}); 
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		busiNo: (typeof(formData.busiNo)==undefined)?'':formData.busiNo,
		chnlNo: (typeof(formData.chnlNo)==undefined)?'':formData.chnlNo,
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
function update(busiNo, chnlNo){
	console.info("open Update tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_busiNo',busiNo);
	para = para + "busiNo="+busiNo;
	$.session.set('HID_chnlNo',chnlNo);
	para = para + "chnlNo="+chnlNo;
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/comp/ctrl/oper/ctrlTPipBusiChnlOpen/ctrlTPipBusiChnlOpenUpdate" + para);
	parent.window.$("a[href^='#tab_update']").click();
}

/*跳转明细页面*/
function detail(busiNo, chnlNo){
	console.info("open detail tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_busiNo',busiNo);
	para = para + "busiNo="+busiNo;
	$.session.set('HID_chnlNo',chnlNo);
	para = para + "chnlNo="+chnlNo;
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/comp/ctrl/oper/ctrlTPipBusiChnlOpen/ctrlTPipBusiChnlOpenDetail" + para);
	parent.window.$("a[href^='#tab_detail']").click();
}

/*删除操作*/
function del(busiNo, chnlNo){
	confirmx("是否确定删除该业务渠道开通信息", function(){
		var url = ctx + "/comp/ctrl/oper/ctrlTPipBusiChnlOpen/delete";
		//向后台发送参数
		$.post(url,
				{ 
					busiNo:busiNo, 
					chnlNo:chnlNo

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
function openFlg(busiNo, chnlNo){
		var url = ctx + "/comp/ctrl/oper/ctrlTPipBusiChnlOpen/updateFlg";
		//向后台发送参数
		$.post(url,
				{ 
			busiNo:busiNo, 
			chnlNo:chnlNo,
			flg:"Y"
			
				},
				function(data){ 
					if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
						var errMsg = "错误信息["+data.message+"]"; 
						showContent(errMsg,"error");
						return '0';
					}else if(data.msg_type == "success"){
						var successMsg = "渠道开通["+data.message+"]"; 
						showContent(successMsg,"success");
						$("#listBtn").click();
					}
				}, "json");
	
}
/*关闭操作*/
function closeFlg(busiNo, chnlNo){
	var url = ctx + "/comp/ctrl/oper/ctrlTPipBusiChnlOpen/updateFlg"; 
	//向后台发送参数
	$.post(url,
			{ 
		busiNo:busiNo, 
		chnlNo:chnlNo,
		flg:"N"
			
			},
			function(data){ 
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var errMsg = "错误信息["+data.message+"]"; 
					showContent(errMsg,"error");
					return '0';
				}else if(data.msg_type == "success"){
					var successMsg = "渠道关闭["+data.message+"]"; 
					showContent(successMsg,"success");
					$("#listBtn").click();
				}
			}, "json");
	
}