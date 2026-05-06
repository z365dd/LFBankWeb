
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出id*/
	temp_id = $.session.get('id');
	if(temp_id != null && temp_id!="" && temp_id!=undefined) {
		$.session.set('sys_notice_id', temp_id);
	}
	else {
		temp_id = $.session.get('sys_notice_id')
	}
	$("#sysNoticeId").val(temp_id);
	id = $("#sysNoticeId").val();
	/*从session中移除id*/
	$.session.remove('id');
	$("#id").val(id);
	getDetail(id);

	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});


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

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(id){
	console.info('get sysNotice info......');
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
					// $('#LINK_URL').attr('href', jsonObj.url);
					$('#LINK_URL').text(jsonObj.linkDesc);
					// 正则判断URL是否以http或者https开头
					// /http:\/\/.+/
					if(jsonObj.url.match(/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/)) {
						$("#LINK_URL").click(function () {
							addTabPage(jsonObj.linkDesc, jsonObj.url)
						});
					}
					else{
						$("#LINK_URL").click(function () {
							addTabPage(jsonObj.linkDesc,ctx+jsonObj.url)
						});
					}
					showOrHiddenData();
					getTreeData();
				}
				$('#noteScp').multiselect("disable");
			}

		}
	},
    "json");
}
function addTabPage(title, url, closeable, $this, refresh){
	// parent.$('.tab_unselect').each(function (){
	// 	var name = $(this).attr("name");
	// 	if("通用缴费详细信息"==name){
	// 		$(this).find("a")[0].click();
	// 	}
	// });
	top.$.fn.jerichoTab.addTab({
		tabFirer: $this,
		title: title,
		closeable: closeable == undefined,
		data: {
			dataType: 'iframe',
			dataLink: url
		}
	}).loadData(refresh != undefined);
}
