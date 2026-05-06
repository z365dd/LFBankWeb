$(function(){
	console.info("维度列表");
	
	/*加载模型下拉框的数据*/
	setSelect1("COMP_NO","/comp/ctrl/oper/svccode/getModlNo","COMP_NO","COMP_NAME");
	
	/*模型下拉改变则重新加载服务码下拉框*/
	$('#COMP_NO').change(function(){
		svcCodeS('DIM_KEY',getS('COMP_NO'));
	});
	
	/*点击查阅按钮*/
	$("#qryBtn").click(function(){
		console.info("点击查询按钮.....");
	    $("#qryTable").bootstrapTable('refresh');
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
		
		COMP_NO:getS("COMP_NO"),
		DIM_KEY:getS("DIM_KEY")
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

function delAction(delstr){
	console.info(delstr);
	var COMP_NO = delstr.split(",")[0];
	var DIM_KEY = delstr.split(",")[1];
	var DIM_DESC = delstr.split(",")[2];
	var TAB_NAME = delstr.split(",")[3];
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		$.ajax({
			type : "POST",
			url : ctx + "/comp/ctrl/oper/mngdim/FCtrlMngDimDel",
			data : {
				COMP_NO:COMP_NO,
				DIM_KEY:DIM_KEY,
				DIM_DESC:DIM_DESC,
				TAB_NAME:TAB_NAME
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
	parent.showTabs("#tab_add", ctx+"/comp/ctrl/oper/mngdim/toUpdate");
	parent.changeName("update");
	parent.setModData(updateStr);
}

//加载维度下拉框
function svcCodeS(Name,Val){
	var Data={
			COMP_NO:Val
		}
	setSelect2("DIM_KEY","/comp/ctrl/oper/mngdim/getDim","DIM_KEY","DIM_DESC",Data,Val);
}
