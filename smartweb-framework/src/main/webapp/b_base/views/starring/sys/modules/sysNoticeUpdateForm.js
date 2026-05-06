
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出id*/
	id = $.session.get('id');
	/*从session中移除id*/
	$.session.remove('id');
	$("#id").val(id);
	getDetail(id);

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
	var type = getI("noteScp");
    url = ctx +'/sys/modules/sysNotice/getTreeData?type='+type;
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

        // 默认选择节点
        var ids="";
        var idsArr = getI("ids");
        if(idsArr!=""){
        	ids = idsArr.split(",");
        }
        for(var i=0; i<ids.length; i++) {
            var node = tree.getNodeByParam("id", ids[i]);
            try{
            	tree.checkNode(node, true, false);
            }catch(e){

            }
        }
        // 默认展开全部节点
        tree.expandAll(true);
    }, "json");
}

function submit(){
	confirmx('是否更新公告消息', function(){
		save();
	});
}

/**
 * 保存函数--保存公告消息修改
 * @returns
 */
function save(){
	var formData = $("#sysNoticeForm").serializeObject();
	var tree = $.fn.zTree.getZTreeObj("treeData");
	var idArr = [];
	//获取选中的树节点
	var nodes = tree.getCheckedNodes(true);
    for(var i=0; i<nodes.length; i++) {
        idArr.push(nodes[i].id);
    }
    formData.ids=idArr.join(",");
	/*向后台发送参数*/
	$.post(ctx + "/sys/modules/sysNotice/update", formData,
		function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){
			    var errMsg = "错误信息["+data.message+"]";
				showContent(errMsg,"error");
				return '0';
			}else if(data.msg_type == "success"){
				var successMsg = "修改交易["+data.message+"]";
				showContent(successMsg,"success");
				console.info("修改交易成功");
				cancle();
			}
	}, "json");

}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(id){
	console.info('update sysNotice info......');
	$.post(ctx + "/sys/modules/sysNotice/get", {id:id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){
			var errMsg = "错误信息["+data.message+"]";
			showContent(errMsg,"error");
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#noteTitle').val(jsonObj.noteTitle);
					$('#noteCntt').val(jsonObj.noteCntt);
					$('#ids').val(jsonObj.ids);
					$('#noteScp').multiselect("select", jsonObj.noteScp).multiselect('rebuild');
					$('#rmrk').val(jsonObj.rmrk);
					$('#popFlg').val(jsonObj.popFlg);
					$('#url').val(jsonObj.url);
					$('#linkDesc').val(jsonObj.linkDesc);
					showOrHiddenData();
					getTreeData();
				}
			}

			/*触发校验*/
			$('#noteTitle').blur();
			$('#noteCntt').blur();
			$('#noteScp').blur();

		}
	},
    "json");
}
