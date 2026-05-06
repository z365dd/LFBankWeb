$(function(){
	console.info("渠道列表");
	
	/*加载渠道下拉框的数据*/
	setSelect1("CHNL_NO","/comp/ctrl/oper/channel/getChnl","CHNL_NO","CHNL_NAME");
	
	/*点击查阅按钮*/
	$("#qryBtn").click(function(){
		console.info("点击查询按钮.....");
	    $("#qryTable").bootstrapTable('refresh');
	});
	
	/*双击某行直接进入修改页面*/
	/*$("#qryTable").on('dblclick','tbody tr',function(){
		var modlArr = $(this).children();
		var chnlNo = modlArr.eq(0).text();
		var chnlName = modlArr.eq(1).text();
		var updataStr = chnlNo+","+chnlName;
		if(chnlNo!=""){
			updateAction(updataStr);
		}
	});*/
});

/*查询table*/
function queryParams(params){
	var formData = $("#qryForm").serializeObject();
	var paramList = { 
		pgside:'server',//服务器分页
		pageSize:params.limit,
		start:params.offset+1,
		pageNo:getPage(params),
		sort:params.sort,
		order:params.order,
		
		CHNL_NO:getS("CHNL_NO")
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

function delAction(chnlNo){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		$.ajax({
			type : "POST",
			url : ctx + "/comp/ctrl/oper/channel/TParaChnlDel",
			data : {
				CHNL_NO:chnlNo
			},
			dataType : "json",
			success : function(data) {
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var Msg = "错误信息["+data.message+"]";
					showContent(Msg,"error");
				}else{
					var Msg = data.message; 
				    showContent(Msg,"success");
			        $("#qryTable").bootstrapTable('refresh');
				}
			}
		});
	});
}

function updateAction(updateStr){
	console.info(updateStr);
	//encodeURI(encodeURI(updateStr)) 解决url后参数中文乱码
	/*parent.showTabs("#tab_add", ctx+"/comp/ctrl/oper/channel/toUpdate/?str="+encodeURI(encodeURI(updateStr)));
	parent.changeName("update");*/
	console.info(updateStr);
	parent.showTabs("#tab_add", ctx+"/comp/ctrl/oper/channel/toUpdate");
	parent.changeName("update");
	parent.setModData(updateStr);
}
