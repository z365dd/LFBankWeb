$(document).ready(function(){
	parent.window.$("#iframe_agentData").show();
	/*从session中拿出id*/
	agentKey = $.session.get('agentKey');
	rule = $.session.get('rules');
	token = $.session.get('token');
	/*从session中移除id*/
	$.session.remove('agentKey');
	$.session.remove('rules');
	var agentSeq = agentKey.split("_")[3];
	var ip = agentKey.split("_")[4];
	var port = agentKey.split("_")[5];
	var ipAndPort = ip+":"+port;
	$("h3").html(ipAndPort+"_"+agentSeq);
	$("#agentKey").val(agentKey);
	$("#rule").val(rule);
	$("#token").val(token);
	getRules(agentKey);
	
	$("#listBtn").click(function(){
		if(proof()){
//			$("#table").bootstrapTable('refresh');
//			$("#table").bootstrapTable('removeAll');
			queryList();
		}
	})
	
	$("#rules").on('change',function(){
		if ($(this).val() != "") {
			var unixKey = $(this).val().split("&@&");
			console.log(unixKey[1]);
			getUnixKey(unixKey[1]);
		}else {
			getUnixKey("");
		}
	});
	
	$("#uniqueValue").change(function(){
		if ($(this).val() != ""){
			$("#uniqueKey").attr("checkbtn","checkbtn");
			$("#uniqueKey").multiselect('rebuild');
		}else {
			$("#uniqueKey").removeAttr("checkbtn");
			$("#uniqueKey").multiselect('rebuild');
		}
	})
	
	$("#uniqueKey").on('change', function(){
		if ($(this).val() != ""){
			$("#uniqueValue").attr("check-empty","true");
		}else {
			$("#uniqueValue").removeAttr("check-empty");
		}
	});
	/*取消按钮*/
	$("button[id^='cancle']").click(function(){
		cancle();
	});
});


function queryList(){
	$("#table").bootstrapTable("removeAll");
	var tableData = "";
	// ${ctx}/para/chk/data/dataList
	var formData = $("#qryForm").serializeObject();
	$.post(ctx + "/para/chk/agent/agentDataList", {
		rules: (typeof(formData.rules)===undefined)?'':formData.rules,
		agentKey: (typeof(formData.agentKey)===undefined)?'':formData.agentKey,
		uniqueKey: (typeof(formData.uniqueKey)==undefined)?'':formData.uniqueKey,
		uniqueValue: (typeof(formData.uniqueValue)==undefined)?'':formData.uniqueValue,
		token: (typeof(formData.token)==undefined)?'':formData.token
			}, function(data){
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var errMsg = "错误信息["+data.message+"]"; 
					showContent(errMsg,"error");	
				}else {
					var dataVal =data.dataSetResult[0].data;
					if(dataVal!="[]"){
						for(var i=0;i<dataVal.length;i++){
							var paramType = dataVal[i].paramType;
							var unixKey = dataVal[i].unixKey;
							var cacheSeq = dataVal[i].cacheSeq;
							var redisSeq = dataVal[i].redisSeq;
							var action = dataVal[i].action;
							tableData += '{"paramType":"'+paramType+'","unixKey":"'+unixKey+'","cacheSeq":"'+cacheSeq+'","redisSeq":"'+redisSeq+'","action":"'+action+'"},';
						}
					}
					if(tableData.length > 0){
						tableData = '['+tableData.substring(0,tableData.length-1)+']';//client分页需要这种格式
						//tableData = '{"total":'+dataVal.length+',"rows":['+tableData.substring(0,tableData.length-1)+']}';
					}else {
						tableData = '[]';
					}
					$('#table').bootstrapTable('load', JSON.parse(tableData));
				}
			},
    "json");
}

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#qryForm").serializeObject();
    var paramList = {
    	rules: (typeof(formData.rules)===undefined)?'':formData.rules,
		agentKey: (typeof(formData.agentKey)===undefined)?'':formData.agentKey,
		uniqueKey: (typeof(formData.uniqueKey)==undefined)?'':formData.uniqueKey,
		uniqueValue: (typeof(formData.uniqueValue)==undefined)?'':formData.uniqueValue,
		token: (typeof(formData.token)==undefined)?'':formData.token,
        pgside: 'server',/*服务器分页*/
        pageSize: params.limit,
        start: params.offset + 1,
        pageNo: getPage(params),
        sort: params.sort,
        order: params.order
	};
	return paramList;
}

function getRules(agentKey){
	var datass = {agentKey:agentKey, start:'0', pageSize:'0'};
	setSelect2("rules", "/para/chk/agent/agentRulesList", "rulesUnixKey", "chName", datass, "nulls", false, false);
}

function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

function getData(agentKey, token) {
	console.info("open detail tab");
	$.session.set('agentKey',agentKey);
	$.session.set('token',token);
	parent.window.$("a[href^='#tab_data']").attr("url", ctx + "/para/chk/agent/agentData?agentKey=" + encodeURIComponent(agentKey) + "&token=" + token);
	parent.window.$("a[href^='#tab_data']").click();
}


function getUnixKey(unixKey){
	var str = [];
	var keys = unixKey.split(",");
	for (var i=0;i<keys.length;i++) {
//		str += "<opion value='"+keys[i]+"'>"+keys[i]+"</option>";
		str[i] = {
			label : keys[i],
			value : keys[i]
		}
//		console.log(str);
	};
	str.unshift({
		label : '请选择',
		value : ''
	});
	$("#uniqueKey").multiselect('dataprovider', str);
	$("#uniqueKey").multiselect('rebuild');
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}