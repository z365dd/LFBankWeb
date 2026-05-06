$(document).ready(function(){
	parent.window.$("#iframe_update").show();
	$(function() {
		$('#collapseOne').collapse('show');
		$('#collapseTwo').collapse('show');
		$('#collapseThree').collapse('show');
		chgHeight(1800);
		dynamicDom();
	});
	
	/*从session中拿出id*/
	id = $.session.get('id');
	/*从session中移除id*/
	$.session.remove('id');
	$("#id").val(id);
	getDetail(id);
	$("#cacheMode").multiselect("disable");
	
	/*添加节点*/
	$("a[data-add$='node']").click(function(){
		var num = 1;
		var dataAdd = $(this).data("add");
		var nodeType = dataAdd.split("_")[0];
		nodeAdd(nodeType, num);
		chgHeight(100);
	})

	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(proof() && getZkJson() && getRsJson() && chkBasePath()){
			save();
		};
	});
	
	/*取消按钮*/
	$("button[id^='cancle']").click(function(){
		cancle();
	});
	
});

/*获取缓存中心详情信息*/
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
						addNode("zkNode", jsonObj, dataSetName)
					}
					if (dataSetName === "rsDs") {
						addNode("rsNode", jsonObj, dataSetName)
					}
					if (dataSetName == "rsSlaveDs") {
						addNode("rsNode", jsonObj, dataSetName);
					}
				}
			}
		}
	},
    "json");
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*改变内容高度*/
function chgHeight(h){
	var Height =$(document.body).height() + h;
	$(window.parent.document).find("#tab_update").find('iframe').height(Height);
}


/*缓存中心信息*/
function centerInfo(jsonObj){
	$("#centerId").val(jsonObj.id)
	$('#chName').val(jsonObj.chName);
	$('#engName').val(jsonObj.engName);
	$('#basePath').val(jsonObj.basePath);
}

function addNode(nodeType, jsonObj, dataSetName) {
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
	if (masterDiv.find(".slaveNode-add:visible").length >= 2) {
		last.find(".slaveBtn").html(htmlDel);
	}
}

function hide(ele){
	$(ele).closest(".nodeinfo").find(".nodeShow").slideToggle();
}

function rmNode(ele){
	$(ele).closest(".nodeinfo").remove();
}

function nodeAdd(nodeType, num){
	for (var i=0;i<num;i++) {
		$("."+nodeType+"-add:first").clone().appendTo($("."+nodeType));
		$("."+nodeType+"-add:last").val("");
		$("."+nodeType+"-add:last").show();
		$("."+nodeType+"-add:last").find(".slaveNode-add").show();
	}
}

let htmlDel = "<a href='javascript:;' class='btn  btn-xs btn-default delSlave' data-toggle='tooltip' data-placement='top' onclick='delSlave(this)'><span class='glyphicon glyphicon-minus' style='margin-top:5px'>删除从节点</span></a>";
function addSlave(ele) {
	var parent = $(ele).parent().parent().parent(".slaveNode");
	parent.find(".slaveNode-add:first").clone().appendTo(parent);
	parent.find(".slaveNode-add:last").find("input").val("");
	parent.find(".slaveNode-add:last").find(".slaveBtn").html(htmlDel);
	parent.find(".slaveNode-add:last").show();
}

/*删除从节点*/
function delSlave(ele){
	$(ele).closest(".slaveNode-add").remove();
	chgHeight(0);
}

/**
 * 保存函数--保存缓存中心信息新增
 */
function save(){
	let formData = $("#addForm").serializeObject();
	/*向后台发送参数*/
	$.post(ctx + "/para/center/update", formData,
		function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){
				let errMsg = "错误信息["+data.message+"]";
				showContent(errMsg,"error");
				return '0';
			}else if(data.msg_type == "success"){
				let successMsg = "交易["+data.message+"]";
				showContent(successMsg,"success");
				console.info("交易成功");
				cancle();
			}
		}, "json");
}


function getZkJson() {
	var flag = true;
	var _zknode = $(".zkNode").find(".nodeShow:visible");
	if (_zknode == undefined || _zknode.length == 0 || _zknode.length%2 == 0) {
		showTip("zookeeper节点数必须为单数","error");
		return false;
	}
	// arr.forEach(function(ele,i){ if(ele == '你要对比的值'){ 在删除它 }})
	let zkStr = "{";
	_zknode.each(function(i, ele){
		if ($(ele).find("#ip").val() == "" || $(ele).find("#port").val() == "") {
			showTip("zookeeper节点信息不能为空","error");
			flag = false;
			return false;
		}
		var ip = $(ele).find("#ip").val();
		var port = $(ele).find("#port").val();
		let myId = i+1
		let jsonStr = "\""+myId+"\":{\"zkId\":"+myId+",\"ip\":\""+ip+"\",\"port\":\""+port+"\"}";
		zkStr += jsonStr + ",";
	});
	var zkJson = zkStr.substring(0, zkStr.length-1)+"}";
	$("#zkJson").val(zkJson);
	return flag;
}

function getRsJson() {
	var flag = true;
	var _rsnode = $(".rsNode").find(".nodeShow:visible");
	if (_rsnode == undefined || _rsnode.length == 0) {
		showTip("redis节点信息不能为空","error");
		return false;
	}
	let rsStr = "{";
	_rsnode.each(function (i, ele) {
		var ip = $(ele).find("#rsIp").val();
		var port = $(ele).find("#rsPort").val();
		if (ip == "" || $(ele).find("#rsPort").val() == "") {
			showTip("redis主节点信息不能为空","error");
			flag = false;
			return false;
		}
		let jsonStr = "\""+i+"\":{\"ip\":\""+ip+"\",\"port\":\""+port+"\"";
		// let _slave = $(this).find("div[data-slavenode='"+rsNode+"']");
		let _slave = $(ele).find(".slaveNode").find(".slaveNode-add:visible");
		if (_slave !== undefined) {
			jsonStr += ",\"Slave\":[";
			let slaveStr = 	"";
			_slave.each(function(i, slaveEle){
				let rsSlaveIp = $(slaveEle).find("input[name='rsSlaveIp']").val();
				let rsSlavePort = $(slaveEle).find("input[name='rsSlavePort']").val();
				if (rsSlaveIp == "" || rsSlavePort == "") {
					showTip("redis从节点信息不能为空","error");
					flag = false;
					return false;
				}
				slaveStr += "{\"ip\":\""+rsSlaveIp+"\",\"port\":\""+rsSlavePort+"\"},";
			});
			jsonStr += slaveStr.substring(0, slaveStr.length-1) + "]";
		}
		rsStr += jsonStr + "},";
	});
	rsJson = rsStr.substring(0, rsStr.length-1)+"}";
	$("#rsJson").val(rsJson);
	return flag;
}

function chkBasePath() {
	var basePath = $("#basePath").val();
	if (!basePath.endsWith("/")) {
		showTip("路径需以\"/\"结束", "error");
		return false;
	} else {
		return true;
	}
}