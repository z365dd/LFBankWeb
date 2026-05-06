$(document).ready(function(){
	parent.window.$("#iframe_list").show();
	/* table初始化 */
	tableClick("table");
	qryList();
	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#agentTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        // $("#agentTable").bootstrapTable('refresh');
        qryList();
        console.info($("#agentTable"));
	});
	
	getTenancies();
	// getParts("");
	
	$("#resetBtn").click(function(){
		clearForm("listForm");
	});
	
	/*初始化表格显示列长度20*/
	$("#agentTable").on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:'agentTable'},{dftlen:20});
	});
	
});




function qryList(){
	$("#agentTable").bootstrapTable("removeAll");
	var tableData = "";
	// ${ctx}/para/chk/data/dataList
	var formData = $("#listForm").serializeObject();
	$.post(ctx + "/para/chk/agent/list", {
		tenant: (typeof(formData.tenant)==undefined)?'':formData.tenant,
		partId: (typeof(formData.partId)==undefined)?'':formData.partId
			}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			var dataVal =data.dataSetResult[0].data;
			if(dataVal!="[]"){
				for(var i=0;i<dataVal.length;i++){
					var access = dataVal[i].access;
					if (access == "0") {
						continue;
					}
					var agentSeq = dataVal[i].agentSeq;
					var partInstSeq = dataVal[i].partInstSeq
					var addr = dataVal[i].addr
					var tenant_str = dataVal[i].tenant_str
					var partId_str = dataVal[i].partId_str
					var token = dataVal[i].token
					var partVersion = dataVal[i].partVersion
					var status_str = dataVal[i].status_str
					var agentKey = dataVal[i].agentKey
//					var action = "<a href='javascript:;' onClick='getRules('"+agentKey+"','"+token+"')'>详细数据</a>"
					var action = dataVal[i].action;
					var redisKey = dataVal[i].redisKey;
					var unixKey = dataVal[i].unixKey;

					// "	<a href=\"JavaScript:void(0);\" onClick=\"getData('" + dataset.getString("redisKey") + "')\" >详细数据</a>"
//					var action = '<a href=\'JavaScript:void(0);\' onClick=\'getData(' + dataVal[i].redisKey + ')\' data-id>详细数据</a>';
//					var action = dataVal[i].action;
					tableData += '{"agentSeq":"'+agentSeq+'","partInstSeq":"'+partInstSeq+'","addr":"'+addr+'","tenant_str":"'+tenant_str+'","partId_str":"'+partId_str+'","token":"'+token+'","partVersion":"'+partVersion+'","status_str":"'+status_str+'","agentKey":"'+agentKey+'","access":"'+access+'","action":"'+action+'"},';
				}
			}
			if(tableData.length > 0){
				tableData = '['+tableData.substring(0,tableData.length-1)+']';//client分页需要这种格式
				//tableData = '{"total":'+dataVal.length+',"rows":['+tableData.substring(0,tableData.length-1)+']}';
			}else {
				tableData = '[]';
			}
			$('#agentTable').bootstrapTable('append', JSON.parse(tableData));
//			if(isJsonString(tableData)){
//				
//				if(ctxTheme=="tech"){
//					action();
//				}
//			}				
		}
	},
    "json");
}



/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
    	tenant: (typeof(formData.tenant)==undefined)?'':formData.tenant,
		partId: (typeof(formData.partId)==undefined)?'':formData.partId,
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
function getData(agentKey){
	console.info("open data tab");
	$.session.set('agentKey',agentKey);
	parent.window.$("a[href^='#tab_data']").attr("url", ctx + "/para/chk/agent/agentData?agentKey=" + agentKey);
	parent.window.$("a[href^='#tab_data']").click();
}


/*跳转更新页面*/
function getRules(agentKey, token){
	console.info("open data tab");
	$.session.set('agentKey',agentKey);
	$.session.set('token',token);
	parent.window.$("a[href^='#tab_agentData']").attr("url", ctx + "/para/chk/agent/agentDataPage?agentKey=" + agentKey + "&token=" + token);
	parent.window.$("a[href^='#tab_agentData']").click();
}


function getTenancies() {
	var datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("tenant", "/para/rules/stg/auth/getTenanices", "engName", "tntNoStr", datass, "nulls", false, false);
	// $("#tenant").on("change", function(){
	// 	getParts($(this).val());
	// });
}

/*读取权限 - 获取参与者列表*/
function getParts(tenant) {
	var datass = {tenant:tenant, start:'0', pageSize:'0'};
	setSelect2("partId", "/para/rules/stg/auth/getParts", "authPartId", "tenantPartName", datass, "nulls", true, false);
}

function accessFormat(value,row,index){
	if(value == "1"){
		return "是";
	}else if(value == "0"){
		return "否";
	}
}
