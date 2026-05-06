$(function(){
	console.info("模型组件列表");
	
	/*加载模型下拉框的数据*/
	setSelect1("COMP_NO","/comp/ctrl/oper/svccode/getModlNo","COMP_NO","COMP_NAME");
	
	/*点击查阅按钮*/
	$("#qryBtn").click(function(){
		console.info("点击查询按钮.....");
	    $("#qryTable").bootstrapTable('refresh');
	});
	
	/*双击某行直接进入修改页面*/
	$("#qryTable").on('dblclick','tbody tr',function(){
		var modlArr = $(this).children();
		var modlNo = modlArr.eq(1).text();
		var modlName = modlArr.eq(2).text();
		var modlType = modlArr.eq(3).text();
		var updateStr = modlNo+","+modlName+","+modlType;
		console.info(updateStr);
		if(modlNo!=""){
			updateAction(updateStr);
		}
	});
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
		COMP_NO:getS("COMP_NO")
//		MODL_NAME:formData.modlName,
//		MODL_TYPE:formData.modlTypeSel
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

function delAction(modlno){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		$.ajax({
			type : "POST",
			url : ctx + "/comp/ctrl/oper/CtrlCompDel",
			data : {
				COMP_NO:modlno
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
	parent.showTabs("#tab_add", ctx+"/comp/ctrl/oper/toUpdate/?str="+encodeURI(encodeURI(updateStr)));
	parent.changeName("update");
}
