let node_num;
$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_add").show();

	$("#cacheMode").multiselect("disable");
	node_num = 3;
	nodeAdd("zkNode", node_num);
	nodeAdd("rsNode", node_num);

	/*添加节点*/
	$("a[data-add$='node']").click(function(){
		let num = 1;
		let dataAdd = $(this).data("add");
		let nodeType = dataAdd.split("_")[0];
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
	$('#cancleBtn').click(function(){
		cancle();
	});

	/*初始展开所有输入框*/
	$(function() {
		$('#collapseOne').collapse('show');
		$('#collapseTwo').collapse('show');
		$('#collapseThree').collapse('show')
		chgHeight(1700);
		/* 2019-03-29 add by zengxj 加入判断是否只有一个缓存中心*/
		// let count = getCenterSum();
		// if (count > 0) {
		// 	alertx('已存在缓存中心', function(){
		// 	});
		// }
	});
});

/*获取缓存中心数量*/
function getCenterSum(){
	let countInt = 0;
	$.ajaxSettings.async = false;
	$.post(ctx + "/para/center/getCenterSum", {},
			function(data){ 
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				    let errMsg = "错误信息["+data.message+"]"; 
					showContent(errMsg,"error");
					return '0';
				}else if(data.msg_type == "success"){
					let count = data.dataSetResult[0].data[0].count;
					countInt = parseInt(count);
					console.log(countInt);
				}
		}, "json");
	$.ajaxSettings.async = true;
	return countInt;
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*改变内容高度*/
function chgHeight(h){
	let Height =$(document.body).height() + h;
	$(window.parent.document).find("#tab_add").find('iframe').height(Height);
}

/*恢复原高度*/
function resetHeight(){
	$(window.parent.document).find("#tab_add").find('iframe').height(600);
}

function rmNode(ele){
	//nodeinfo
	$(ele).closest(".nodeinfo").remove();
}

function hide(ele){
	$(ele).closest(".nodeinfo").find(".nodeShow").slideToggle();
}

function nodeAdd(nodeType, num){
	for (var i=0;i<num;i++) {
		$("."+nodeType+"-add:first").clone().appendTo($("."+nodeType));
		$("."+nodeType+"-add:last").val("");
		$("."+nodeType+"-add:last").show();
	}
}

let htmlDel = "<a href='javascript:;' class='btn  btn-xs btn-default delSlave' data-toggle='tooltip' data-placement='top' onclick='delSlave(this)'><span class='glyphicon glyphicon-minus' style='margin-top:5px'>删除从节点</span></a>";
function addSlave(ele) {
	var parent = $(ele).parent().parent().parent(".slaveNode");
	parent.find(".slaveNode-add:first").clone().appendTo(parent);
	$(ele).closest(".slaveNode").find(".slaveNode-add:last").find("input").val("");
	$(ele).closest(".slaveNode").find(".slaveNode-add:last").find(".slaveBtn").html(htmlDel);
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
	$.post(ctx + "/para/center/insert", formData,
		function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){
				let errMsg = "错误信息["+data.message+"]";
				showContent(errMsg,"error");
				return '0';
			}else if(data.msg_type == "success"){
				let successMsg = "新增交易["+data.message+"]";
				showContent(successMsg,"success");
				console.info("新增交易成功");
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
		let _slave = $(ele).find(".slaveNode").find(".slaveNode-add");
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