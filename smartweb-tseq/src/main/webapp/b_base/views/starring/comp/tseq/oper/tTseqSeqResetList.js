$(document).ready(function(){
	parent.window.$("#iframe_list").show();

	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#tTseqSeqCrtTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#tTseqSeqCrtTable").bootstrapTable('refresh');
	});

	/*初始化表格显示列长度20*/
	$("#tTseqSeqCrtTable").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'tTseqSeqCrtTable'},{dftlen:20});
	});
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		seqCrtId: (typeof(formData.seqCrtId)==undefined)?'':formData.seqCrtId,
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

/*跳转修改页面*/
function update(seqCrtId,maxSeqNum){
	console.info("open Update tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_seqCrtId',seqCrtId);
	$.session.set('maxSeqNum',maxSeqNum);
	para = para + "seqCrtId="+seqCrtId;
	parent.window.$("a[href^='#tab_add']").attr("url", ctx + "/comp/tseq/oper/tTseqSeqReset/tTseqSeqResetForm" + para);
	parent.window.$("a[href^='#tab_add']").click();
}

/*跳转明细页面*/
function detail(seqCrtId){
	console.info("open detail tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_seqCrtId',seqCrtId);
	para = para + "seqCrtId="+seqCrtId;
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/comp/tseq/oper/tTseqSeqReset/tTseqSeqCrtDetail" + para);
	parent.window.$("a[href^='#tab_detail']").click();
}

/*删除操作*/
function del(seqCrtId){
	confirmx("是否确定删除该流水号生成器信息", function(){
		var url = ctx + "/comp/tseq/oper/tTseqSeqCrt/delete";
		//向后台发送参数
		$.post(url,
				{ 
					seqCrtId:seqCrtId

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