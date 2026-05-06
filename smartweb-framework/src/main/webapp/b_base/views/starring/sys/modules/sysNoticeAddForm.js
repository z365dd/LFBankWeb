$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_add").show();

	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(proof()){
			submit();
		};
	});

	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});

	$("#dataSelect").hide();
	$('#noteScp').change(function(){
		showOrHiddenData();
		getTreeData();
	})

});

function showOrHiddenData(){
	var noteScp = getI("noteScp");
	if(!noteScp || noteScp=="0"){
		$("#dataSelect").hide();
	}else{
		$("#dataSelect").show();
	}
}

function getTreeData() {
	var noteScp = $("#noteScp").val();
    url = ctx +'/sys/modules/sysNotice/getTreeData?type='+noteScp;
    $.post(url, {}, function (data) {
        var zNodes = data;
        var setting = {
			check: {
				enable: true,
				nocheckInherit: true
			},
			view: {
				selectedMulti: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: function (id, node) {
					tree.checkNode(node, !node.checked, true, true);
					return false;
				}
			}
		};
        // 初始化树结构
        var tree = $.fn.zTree.init($("#treeData"), setting, zNodes);
        // Y代表勾选时,N代表取消勾选 p代表父节点,s代表字节点
        tree.setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };
		var childNodes = tree.transformToArray(tree.getNodes());
		for(var i=0;i<childNodes.length;i++){
			try{
				tree.checkNode(childNodes[i], true, false);
			}catch(e){

			}
		}
        // 默认展开全部节点
        tree.expandAll(true);
    }, "json");
}

function submit(){
	confirmx('是否新增公告消息', function(){
		save();
	});
}

/**
 * 保存函数--保存公告消息新增
 * @returns
 */
function save(){
	var formData = $("#addForm").serializeObject();
	var tree = $.fn.zTree.getZTreeObj("treeData");
	var idArr = [];
	//获取选中的树节点
	var nodes = tree.getCheckedNodes(true);
    for(var i=0; i<nodes.length; i++) {
        idArr.push(nodes[i].id);
    }
    formData.ids=idArr.join(",");
    console.log(formData);
	/*向后台发送参数*/
	$.post(ctx + "/sys/modules/sysNotice/insert", formData,
		function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){
			    var errMsg = "错误信息["+data.message+"]";
				showContent(errMsg,"error");
				return '0';
			}else if(data.msg_type == "success"){
				var successMsg = "新增交易["+data.message+"]";
				showContent(successMsg,"success");
				console.info("新增交易成功");
				cancle();
			}
	}, "json");

}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}
