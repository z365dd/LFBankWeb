console.log('relatSysList.js');

/*
 * 主页面全局变量saveOrRev 默认add为新增，revice为修改，detail详细，用于子页面判断
 */
var SAVE_OR_REV = 'add';

$(function() {
	/*setSelect2("PROD_NO", "/comp/prod/oper/prodSalePage/qry",
			"SALE_PROD_CODE", "SALE_PROD_DESC", {
			PROD_LINE_CODE : getS("PROD_LINE_CODE")
			}, '1', null, true);*/
	
	parent.window.$("#iframe_list").show();
	
	/* 查询按钮点击进行查询 */
	$('#qryBtn').click(function() {
		freshTable('table');
	});

	
	/* 关闭按钮点击关闭页面 */
	$("#cancelBtn").click(function(){
		closeBtnToDo();
	});
	
	/* 新增按钮点击 */
	$('#addBtn').click(function() {
		SAVE_OR_REV = "add";
		parent.window.$("a[href^='#tab_add']").click(); 
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
	
		STAT : getS('STAT'),
		RELAT_SYS : getI('RELAT_SYS'),
		SYS_TP : getS('SYS_TP')
	};
	return paramList;
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		return params.offset / params.limit + 1;
	}
}



/* 详细 */
function Detail(relatSys) {
	getDetail(relatSys, "detail");
}

/* 修改 */
function Revice(relatSys) { 
	getRevice(relatSys, "revice");
}

/* 获取详细数据,打开tab2传入数据 */
function getRevice(relatSys, tp) {
	$.ajax({
		url:ctx + "/comp/ctrl/oper/relatSys/getDetail", 
		type:"GET",
		dataType:"json",
		data:{
			RELAT_SYS:relatSys
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
				parent.window.$("a[href^='#tab_mod']").click();
				var dataStr = JSON.stringify(Data);
				$.session.set("relatSysData",dataStr); 
				
			}
		}
	});
}

/* 获取详细数据,打开tab2传入数据 */
function getDetail(relatSys, tp) {
	$.ajax({
		url:ctx + "/comp/ctrl/oper/relatSys/getDetail", 
		type:"GET",
		dataType:"json",
		data:{
			RELAT_SYS:relatSys
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
				
				SAVE_OR_REV = "detail";
				parent.window.$("a[href^='#tab_det']").click();
				var dataStr = JSON.stringify(Data);
				$.session.set("relatSysData",dataStr); 
			
				/*
				 * parent.window.$("#tab_det").find('iframe').load(function() {
					parent.$("#tab_det").find('iframe')[0].contentWindow.setData(Data); 
				});*/
				
			}
		}
	});
}
/*删除*/
function Delete(relatSys){
	goTop();
	Ewin.confirm({
		  title : "操作提示",
		  message : "确定删除吗？"
		 }).on(function(e) {
		  if (!e) {
		   return;
		  }
			$.post(ctx + "/comp/ctrl/oper/relatSys/delete", {
				RELAT_SYS : relatSys
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