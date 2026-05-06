$(document).ready(function(){
	parent.window.$("#iframe_list").show();
	
	setSelect3("compNo","/comp/fsvr/tec/tfsvrFileTotPara/selecList",
			"compNo", "compName", true, false);

	$('#compNo').change(function() {
		if (getS('compNo') != "") {
			setSelect2("tempFmtNo","/comp/fsvr/tec/tfsvrFileTotPara/selecList","fmtNo", "longRmrk", {
						compNo : getS('compNo'), chgNo : getS('chgNo')}, true, true);
		}
	});
	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#table").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#table").bootstrapTable('refresh');
	});
	$("#addBtn").click(function(){
		parent.window.$("a[href^='#tab_add']").click();
	});

	/*初始化表格显示列长度30*/ 
	$("#table").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'table'},{dftlen:30});
	});
});

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		compNo: (typeof(formData.compNo)==undefined)?'':formData.compNo,
		tempFmtName: (getS('tempFmtNo')=="")?'':getST('tempFmtNo'),
		chgName:(typeof(formData.chgName)==undefined)?'':formData.chgName,
		chgNo:(typeof(formData.chgNo)==undefined)?'':formData.chgNo,
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
function update(chgNo){
	console.info("open Update tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_chgNo',chgNo);
	para = para + "chgNo="+chgNo;
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/comp/fsvr/tec/fsvrFileFmt/fileFmtUpdate" + para);
	parent.window.$("a[href^='#tab_update']").click();
}

/*跳转明细页面*/
function detail(chgNo){ 
	console.info("open detail tab");
	var para = "";
	para = para + "?";
	$.session.set('HID_chgNo',chgNo);
	para = para + "chgNo="+chgNo;
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/comp/fsvr/tec/fsvrFileFmt/fileFmtDetail" + para);
	parent.window.$("a[href^='#tab_detail']").click();
}

/*删除操作*/
function del(chgNo){
	confirmx("是否确定删除该组件文件传输端口信息", function(){
		var url = ctx + "/comp/fsvr/tec/fsvrFileFmt/delete";
		//向后台发送参数
		$.post(url,
				{ 
			chgNo:chgNo

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