console.log('portManageForm.js');

/*主页面全局变量saveOrRev
默认add为新增，revice为修改
用于子页面判断*/
var SAVE_OR_REV = "add";

$(function(){
	/*加载模型下拉框的数据*/
	//setSelect1("COMP_NO","/comp/ctrl/oper/svccode/getModlNo","COMP_NO","COMP_NAME",null,false);
	
	/*查询*/
	$("#qryBtn").click(function(){
		freshTable("table");
	});
	
	/*tab1点击*/
	$('#tab1').click(function(){
		$('#tab2').text('端口新增');
		SAVE_OR_REV = "add";
	});
	
	/*tab2点击*/
	$('#tab2').click(function(){
		$('#tab2').text('端口新增');
		SAVE_OR_REV = "add";
		ifr('panel2','comp/fsvr/tec/fileTec/portManageForm');
		setIfrHeight1('panel2',300);
	});
	
});

/*tab1点击执行函数*/
function tab1(){
	$('#tab1').click();
	goTop();
	$("#qryBtn").click();
}

/*查询table*/
function queryParams(params){
	if(getS('COMP_NO')==""){
		var $compName = "";
	}else{
		var $compName = getST('COMP_NO');
	}
	var paramList = { 
		pgside:'server',//服务器分页
		pageSize:params.limit,
		start:params.offset+1,
		pageNo:getPage(params),
		sort:params.sort,
		order:params.order,
//		TODO
		PORT:getI('PORT'),
		COMP_NO:getS('COMP_NO'),
		COMP_NAME:$compName
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}


//修改调用
function Revice(compNo,port){
	$('#tab2').click().text('端口修改');
	SAVE_OR_REV = "revice";
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.setVal(compNo,port);
	});
}

//删除调用
function Delete(compNo){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		$.ajax({
			url:ctx + "/comp/fsvr/tec/fileTec/portDelete", 
			type:"GET",
			dataType:"json",
			data:{
				COMP_NO:compNo
			},
			async:true,
			success:function(data) {
				if (data.returnCode !== undefined
						&& "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					var successMsg = "删除数据["+data.message+"]"; 
					showContent(successMsg,"success");
					freshTable("table");
				}
			}
		});
	});
}




