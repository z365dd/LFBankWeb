var paramType = "";
$(document).ready(function(){
	
	chgHeight(800);
	
	parent.window.$("#iframe_data").show();
	/*从session中拿出id*/
	var agentKey = $.session.get('agentKey');
	var token = $.session.get('token');
	console.log("agentKey:"+agentKey);
	/*从session中移除id*/
	/*agentKey%&%rules%&%unixKey%&%uniqueVal*/
	$.session.remove('agentKey');
	$("#agentKey").val(agentKey);
	$("#token").val(token);
	var agtKeys = agentKey.split("!@#");
	/*head_smartweb_V1.0_agent0000004859*/
	var agtInfo = agtKeys[0];
	/*rules*/
	var rules = agtKeys[1];
	/*agentSeq*/
	var agentSeq = agtInfo.split("_")[3];
	var ip =  agtInfo.split("_")[4];
	var port = agtInfo.split("_")[5];
	var ipAndPort = ip+":"+port;
	console.log("agentSeq:"+agentSeq);
	$("#h3_agentSeq").html(ipAndPort+"_"+agentSeq);
//	getData(agentKey);
	
	$("#uniqueKey").val(agtKeys[2]);
	getRules();
	$('#rules').multiselect("select", rules).multiselect('rebuild');
	$('#rules').multiselect("disable")
	
	
	/*取消按钮*/
	$("button[id^='cancle']").click(function(){
		cancle(agtInfo, rules);
	});
	
	$("#detailTable").bootstrapTable('refresh');
	
});

function getRules(){
	var datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("rules", "/para/rules/stg/list", "engName", "chName", datass, "nulls", true, false);
}


/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		agentKey: (typeof(formData.agentKey)==undefined)?'':formData.agentKey,
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

function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}



function getData(agentKey) {
	var url = ctx + "/para/chk/agent/agentDataDetail";
	//向后台发送参数
	$.post(url,
			{ 
				agentKey : agentKey,
			},
			function(data){ 
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var errMsg = "错误信息["+data.message+"]"; 
					showContent(errMsg,"error");
					return '0';
				}else if(data.msg_type == "success"){
					for(var i = 0 ; i < data.dataSetResult.length; i++){
						for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
							var jsonObj = data.dataSetResult[i].data[j];
							console.log(jsonObj);
							copyDiv(jsonObj);
							if (j >= 12) {
								chgHeight(50);
							}
						}
					}
				}
			}, "json");
}


function copyDiv(jsonObj){
	var keys = jsonObj.key;
	var values = jsonObj.object;
	var _keys = keys.split("&@&");
	var _values = values.split("&@&");
	for (var i=0;i<_keys.length;i++) {
		if (i == _keys.length -1) {
			return true;
		}
		var key = _keys[i];
		var dataInfo =  $("div[data-info^='data_']:last").data("info");
		var x = dataInfo.split("_")[1];
		x++;
		var divObj = $("div[data-info^='data_']:last");
		var html = "<div ravo='rainbow_fx_layout' class='row clearfix' data-info='data_"+x+"' style='margin:10px 0px;'>"+divObj.html()+"</div>";
		divObj.after(html);
		$("div[data-info='data_"+x+"'").find("input[id='key']").attr("data-info", "data_"+x);
		$("div[data-info='data_"+x+"'").find("input[id^='value']").attr("data-info", "data_"+x);
		$("div[data-info='data_"+x+"'").find("input[id^='value']").attr("id", "value_"+key);
		
		$("div[data-info='data_"+x+"'").find("input[id='key']").val(key);
		$("div[data-info='data_"+x+"'").find("input[id='value_"+key+"']").val(_values[i]);
	}
}

function cancle(agentKey, rules){
	$.session.set('agentKey', agentKey);
	$.session.set('rules', rules);
	parent.window.$("a[href^='#tab_agentData']").attr("url", ctx + "/para/chk/agent/agentDataPage?agentKey=" + agentKey);
	parent.window.$("a[href^='#tab_agentData']").click();
}

/*改变内容高度*/
function chgHeight(h) {
	var Height =$(document.body).height() + h;
	$(window.parent.document).find("#tab_data").find('iframe').height(Height);
}