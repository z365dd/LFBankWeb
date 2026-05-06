console.log('entrDemoList.js');

/*
 * 主页面全局变量saveOrRev 默认add为新增，revice为修改，detail详细，用于子页面判断
 */
var SAVE_OR_REV = 'add';

$(function() {
	/* 查询按钮点击进行查询 */
	$('#qryBtn').click(function() {
		freshTable('table');
	});

	/* tab1点击 */
	$('#tab1').click(function() {
		goTop();
		$('#tab2').text('单位新增');
		SAVE_OR_REV = "add";
	});

	/* tab2点击 */
	$('#tab2').click(function() {
		SAVE_OR_REV = "add";
		$('#tab2').text('单位新增');
		ifr('panel2', '/prod/oper/entrDemo/entrDemoForm');
	});
	
	
});
function tab1(){
	$('#tab1').click();
	goTop();
}


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
		ENTR_NO : getI('ENTR_NO'),
		ENTR_NAME : getI('ENTR_NAME'),
		OPEN_STAT : getS('OPEN_STAT'),
		BUSI_NO : getI('BUSI_NO'),
		PROD_NO : getS('PROD_NO')
	};
	return paramList;
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		return params.offset / params.limit + 1;
	}
}

/* 修改状态发送后台 */
// Y开通 N关闭
function UpdateStat(entrNo, openStat) {
	
	Ewin.confirm({
		  title : "操作提示",
		  message : "是否修改状态？"
		 }).on(function(e) {
		  if (!e) {
		   return;
		  }
	
	$.post(ctx + "/prod/oper/entrDemo/UpdateStat", {
		ENTR_NO : entrNo,
		OPEN_STAT : openStat
	}, function(data) {
		if (data.returnCode !== undefined && "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
		} else {
			console.log(data.message);
			var successMsg = '';
			if (openStat == "N") {
				successMsg = "关闭[" + data.message + "]";
			} else {
				successMsg = "开通[" + data.message + "]";
			}
			showContent(successMsg, "success");

			freshTable('table');
		}
	}, "json");
		 })
}

/* tab1点击执行函数 */
function tab1() {
	$('#tab1').click();
	$(document).scrollTop(0);
	$('#tab2').text('单位新增');
	freshTable('table');
}

/* 详细 */
function Detail(entrNo) {
	getDetail(entrNo, "detail");
}

/* 修改 */
function Revice(entrNo) {
	getDetail(entrNo, "revice");
}

/* 获取详细数据,打开tab2传入数据 */
function getDetail(entrNo, tp) {
	$.ajax({
		url:ctx + "/prod/oper/entrDemo/getDetail",
		type:"GET",
		dataType:"json",
		data:{
			ENTR_NO:entrNo
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
				/*Data = JSON.parse(Data.FProdEntrQryDtlRes);*/
				
				$('#tab2').click();
				
				if(tp=="detail"){
					$('#tab2').text('单位详细');
					SAVE_OR_REV = "detail";
				}else if(tp=="revice"){
					$('#tab2').text('单位修改');
					SAVE_OR_REV = "revice";
				}
				$("#panel2").find('iframe').load(function() {
					$("#panel2").find('iframe')[0].contentWindow.setData(Data);
				});
				
			}
		}
	});
}
/*删除*/
function Delete(entrNo){
	goTop();
	Ewin.confirm({
		  title : "操作提示",
		  message : "确定删除吗？"
		 }).on(function(e) {
		  if (!e) {
		   return;
		  }
			$.post(ctx + "/prod/oper/entrDemo/delete", {
				ENTR_NO : entrNo
			}, function(data) {
				if (data.returnCode !== undefined && "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					showContent(data.message, "success");
					freshTable('table');
				}
			}, "json");
		  })
}