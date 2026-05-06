$(document).ready(function(){
	parent.window.$("#iframe_detail").show();
	$(function() {
		$('#collapseOne').collapse('show');
		$('#collapseTwo').collapse('show');
		$('#collapseThree').collapse('show');
		chgHeight(2000);
	});
	/*从session中拿出id*/
	id = $.session.get('id');
	/*从session中移除id*/
	$.session.remove('id');
	$("#id").val(id);
	getDetail(id);
	$("#cacheMode").multiselect("disable");
	/*取消按钮*/
	$("button[id^='cancle']").click(function(){
		cancle();
	});
});

function getDetail(id){
	console.info('get center info......');
	$.post(ctx + "/para/center/get", {id:id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				console.log("dataSetName:"+data.dataSetResult[i].dataSetName);
				var dataSetName = data.dataSetResult[i].dataSetName;
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
//					console.log(jsonObj);
					if (dataSetName === "centerDs") {
						centerInfo(jsonObj);
					}
					if (dataSetName === "zkDs") {
						nodeAdd("zkNode", jsonObj, dataSetName);
					}
					if (dataSetName === "rsDs") {
						nodeAdd("rsNode", jsonObj, dataSetName);
					}
					if (dataSetName == "rsSlaveDs") {
						nodeAdd("rsNode", jsonObj, dataSetName);
					}
				}
			}
		}
	},
    "json");
}

/*缓存中心信息*/
function centerInfo(jsonObj){
	$('#chName').val(jsonObj.chName);
	$('#engName').val(jsonObj.engName);
	$('#basePath').val(jsonObj.basePath);
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*改变内容高度*/
function chgHeight(h){
	var Height =$(document.body).height() + h;
	$(window.parent.document).find("#tab_detail").find('iframe').height(Height);
}

function nodeAdd(nodeType, jsonObj, dataSetName) {
	if (nodeType == "rsNode" && jsonObj.mainNodeId != "0" && dataSetName == "rsSlaveDs") {
		slaveAdd(jsonObj);
		return;
	} else if (nodeType == "rsNode" && jsonObj.mainNodeId == "0" && dataSetName == "rsSlaveDs") {
		return;
	} else if (nodeType == "rsNode" && jsonObj.mainNodeId != "0" && dataSetName == "rsDs" ) {
		return;
	}
	$("."+nodeType+"-add:first").clone().appendTo("."+nodeType);
	$("."+nodeType+"-add:last").show();
	const last = $("." + nodeType + "-add:last");
	if(nodeType == "zkNode"){
		last.find("input[id='id']").val(jsonObj.id);
		last.find("input[id='ip']").val(jsonObj.ip);
		last.find("input[id='port']").val(jsonObj.port);
	}else if (nodeType == "rsNode") {
		last.find("input[id='id']").val(jsonObj.id);
		last.find(".slaveNode").attr("data-master", jsonObj.id)
		last.find("input[id='rsIp']").val(jsonObj.ip);
		last.find("input[id='rsPort']").val(jsonObj.port);
	}
}

function slaveAdd(jsonObj) {
	const masterDiv = $(".rsNode").find("div[data-master='"+jsonObj.mainNodeId+"']");
	masterDiv.find(".slaveNode-add:first").clone().appendTo(masterDiv);
	const last = masterDiv.find(".slaveNode-add:last");
	last.find("input[id='id']").val(jsonObj.id);
	last.find("input[id='rsSlaveIp']").val(jsonObj.ip);
	last.find("input[id='rsSlavePort']").val(jsonObj.port);
	last.show();
}

function hide(ele){
	$(ele).closest(".nodeinfo").find(".nodeShow").slideToggle();
}