$(document).ready(function(){
	parent.window.$("#iframe_data").show();
	/*从session中拿出id*/
	engName = $.session.get('engName');
	/*从session中移除id*/
	$.session.remove('engName');
	$("#engName").val(engName);
	$("h3").html(engName);
	/* table初始化 */
	tableClick("table");
	/*取消按钮*/
	$("button[id^='cancle']").click(function(){
		cancle();
	});
	qryList();
	$(function() {
		$("#table").bootstrapTable('refresh');
	});
	
	$("#listBtn").click(function(){
		if(proof()){
			qryList();
		}
	})
});


function qryList(){
	$('#table').bootstrapTable("removeAll");
	var tableData = "";
	// ${ctx}/para/chk/data/dataList
	var formData = $("#qryForm").serializeObject();
	$.post(ctx + "/para/chk/data/dataList", {engName: formData.engName, unixKey: formData.unixKey}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			var dataVal =data.dataSetResult[0].data;
			if(dataVal!="[]"){
				for(var i=0;i<dataVal.length;i++){
					var redisKey = dataVal[i].redisKey;
					var unixKey = dataVal[i].unixKey;
					var cacheSeq = dataVal[i].cacheSeq;
					var action = "<a href='javascript:;' onClick='getData(this)'>数据详情</a>"
					tableData += '{"redisKey":"'+redisKey+'","unixKey":"'+unixKey+'","cacheSeq":"'+cacheSeq+'","action":"'+action+'"},';
				}
			}
			if(tableData.length > 0){
				tableData = '['+tableData.substring(0,tableData.length-1)+']';//client分页需要这种格式
			} else {
				tableData = '[]';
			}
			$('#table').bootstrapTable('append', JSON.parse(tableData));
		}
	},
    "json");
}


/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#qryForm").serializeObject();
    var paramList = {
		enname: (typeof(formData.enname)===undefined)?'':formData.enname,
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

function getData(obj) {
	var $reviceRowData= reviceRowData(obj);
	var redisKey = $reviceRowData.redisKey;
	console.info("open detail tab");
	$.session.set('redisKey',redisKey);
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/para/chk/data/dataDetail?redisKey=" + redisKey);
	parent.window.$("a[href^='#tab_detail']").click();
}

function cancle(){
	parent.window.$("a[href^='#tab_rules']").click();
}