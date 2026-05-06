console.log('ptcptManageForm.js');

/*主页面全局变量saveOrRev
默认add为新增，revice为修改
用于子页面判断*/
var SAVE_OR_REV = "add";

$(function(){
	/*查询*/
	$("#qryBtn").click(function(){
		freshTable("table");
	});
	
	/*tab1点击*/
	$('#tab1').click(function(){
		$('#tab2').text('调用方新增');
		SAVE_OR_REV = "add";
	});
	
	/*tab2点击*/
	$('#tab2').click(function(){
		$('#tab2').text('调用方新增');
		SAVE_OR_REV = "add";
		ifr('panel2','comp/fsvr/tec/fileTec/ptcptManageForm');
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
	var paramList = { 
		pgside:'server',//服务器分页
		pageSize:params.limit,
		start:params.offset+1,
		pageNo:getPage(params),
		sort:params.sort,
		order:params.order,
		FILE_PTCPT_NO:getI('FILE_PTCPT_NO'),
		PTCPT_GRP_TP:getS('PTCPT_GRP_TP'),
		STAT:getS('STAT')
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}


//修改调用
function Revice(filePtcptNo){
	$('#tab2').click().text('调用方修改');
	SAVE_OR_REV = "revice";
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.getDetail(filePtcptNo);
	});
}

//删除调用
function Delete(filePtcptNo){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		$.ajax({
			url:ctx + "/comp/fsvr/tec/fileTec/ptcptDelete", 
			type:"GET",
			dataType:"json",
			data:{
				FILE_PTCPT_NO:filePtcptNo
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

//详细调用
function Detail(filePtcptNo){
	$('#tab2').click().text('调用方详细');
	SAVE_OR_REV = "detail";
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.getDetail(filePtcptNo);
	});
}

//修改状态 启用 停用 
function ChangeStat(filePtcptNo, stat){
	$.ajax({
		url:ctx + "/comp/fsvr/tec/fileTec/ptcptRevice", 
		type:"GET",
		dataType:"json",
		data:{
			TRAN_TP:"02",
			FILE_PTCPT_NO:filePtcptNo,
			STAT:stat
		},
		async:true,
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var successMsg = "修改状态["+data.message+"]"; 
				showContent(successMsg,"success");
				freshTable("table");
			}
		}
	});
}

