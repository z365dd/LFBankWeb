console.log('busiDemoList.js');

/*
 * 主页面全局变量saveOrRev 默认add为新增，revice为修改，detail详细，用于子页面判断
 */
var SAVE_OR_REV = 'add';

$(function() {
	//单位编号
	setSelect1("entrNo","/prod/oper/entrDemo/qry","entrNo","entrName",null,true);
	setSelect1("saleProdCode","/prod/oper/definition/saleprod/tPipSaleProd/list","saleProdCode","saleProdDesc",null,true);
	/* 查询按钮点击进行查询 */
	$('#qryBtn').click(function() {
		freshTable('table');
	});
	freshTable('table');
	/* tab1点击 */
	$('#tab1').click(function() {
		goTop();
		$('#tab2').text('业务新增');
	});

	/* tab2点击 */
	$('#tab2').click(function() {
		SAVE_OR_REV = "add";
		$('#tab2').text('业务新增');
		ifr('panel2', '/prod/oper/busiDemo/busiDemoForm',3000);
	});
});
/* 查询table */
function queryParams(params) {
	var paramList = {
		pgside : 'server',// 服务器分页
		pageSize : params.limit,
		start : params.offset + 1,
		pageNo : getPage(params),
		sort : params.sort,
		order : params.order,
		// TODO
		entrNo : getS('entrNo'),
		entrName : getI('entrName'),
		openStat:getS('openStat'),
		busiName:getI('busiName'),
		saleProdCode:getS('saleProdCode')
	};
	return paramList;
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		return params.offset / params.limit + 1;
	}
}


/* tab1点击执行函数 */
function tab1() {
	$('#tab1').click();
	$(document).scrollTop(0);
	$('#tab2').text('业务新增');
	freshTable('table');
}

/* 详细 */
function Detail(busiNo) {
	getDetail(busiNo, "detail");
}

/* 修改 */
function Revice(busiNo) {
	getDetail(busiNo, "revice");
}

/* 获取详细数据,打开tab2传入数据 */
function getDetail(busiNo, tp) {
	$.ajax({
		url:ctx + "/prod/oper/busiDemo/getDetail", 
		type:"GET",
		dataType:"json",
		data:{
			busiNo:busiNo
		},
		async:true,
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var successMsg = "获取数据["+data.message+"]"; 
				showTip(successMsg,"success");
				
				var Data = data.dataSetResult[0].data[0];
				
				Data = JSON.parse(Data.tPipBusiDO);
				
				$('#tab2').click();
				
				if(tp=="detail"){
					$('#tab2').text('业务详细');
					SAVE_OR_REV = "detail";
				}else if(tp=="revice"){
					$('#tab2').text('业务修改');
					SAVE_OR_REV = "revice";
				}
				console.info("----------------SAVE_OR_REV跳转页面前-------------"+SAVE_OR_REV);
				$("#panel2").find('iframe').load(function() {
					$("#panel2").find('iframe')[0].contentWindow.setData(Data);
				});
				
			}
		}
	});
}


/* 启用*/
function Enable(busiNo) {
		$.ajax({
			url:ctx + "/prod/oper/busiDemo/Enable",
			type:"POST",
			dataType:"json",
			data:{
				busiNo:busiNo
			},
			async:true,
			success:function(data) {
				if (data.returnCode !== undefined
						&& "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					var successMsg = "启用["+data.message+"]"; 
					showContent(successMsg,"success");
					freshTable('table');
				}
			}
		});
	
}

/*停用*/
function Stopping(busiNo) {
		$.ajax({
			url:ctx + "/prod/oper/busiDemo/Stopping",
			type:"POST",
			dataType:"json",
			data:{
				busiNo:busiNo
			},
			async:true,
			success:function(data) {
				if (data.returnCode !== undefined
						&& "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					var successMsg = "停用["+data.message+"]"; 
					showContent(successMsg,"success");
					freshTable('table');
				}
			}
		});
	
}

/* 删除 */
function Delete(busiNo, openStat) {
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		$.ajax({
			url:ctx + "/prod/oper/busiDemo/delete",
			type:"POST",
			dataType:"json",
			data:{
				busiNo:busiNo,
				openStat:openStat
			},
			async:true,
			success:function(data) {
				if (data.returnCode !== undefined
						&& "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					var successMsg = "删除["+data.message+"]"; 
					showContent(successMsg,"success");
					freshTable('table');
				}
			}
		});
	});
	
}

