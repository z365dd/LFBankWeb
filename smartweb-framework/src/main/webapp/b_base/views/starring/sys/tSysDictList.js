$(document).ready(function(){
	parent.window.$("#iframe_list").show();

	$("#listBtn").click(function(){
		/*重置搜索下标，跳转到第一页*/
		var $preClick = $("#tSysDictTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
        $("#tSysDictTable").bootstrapTable('refresh');
	});

	/*初始化表格显示列长度20*/
	// $("#tSysDictTable").on('load-success.bs.table', function (data) {
	// 	SmartWeb.swJS.index.init({id:'tSysDictTable'},{dftlen:20});
	// });

	loadData();
	initTable();
});

function initTable(){
	var url = ctx + "/sys/tSysDict/list";
	var columns = assembleColumns();
	var myConfig = SmartWeb.bootstrapTable.constructor('#tSysDictTable', columns, url, queryParams, _myInitSubTable,"","","",true);
	myConfig = SmartWeb.bootstrapTable.extendConfig(myConfig,{height: 500,pageSize:10,pageList: [10, 20, 50, 100]})
	SmartWeb.bootstrapTable.init(myConfig);
}

function assembleColumns(){
	var assembleColumns = [
		{field: 'dictTp',title: '页面参数类型'},
		{field: 'dictInfo',title: '页面参数描述'},
		{field: 'action',title: '操作'}
	];
	return assembleColumns;
}

function _myInitSubTable(index, row,  $detail) {
	console.log('_myInitSubTable ...... row=');
	var dictTp = row.dictTp;
	// 注意这个'table'不是一个id，他在任何情况下不需要改变
	var cur_table = $detail.html('<table id="' + row.id + '" class="table sub-table"></table>').find('table');
	cur_table.attr('id', row.id);
	$(cur_table).bootstrapTable({
		url:ctx + "/sys/tSysDict/listVal",
		method:'post',
		queryParams:{
			dictTp:dictTp
		},
		pagination: false,
		clickToSelect: true,
		detailView: false,
		uniqueId: "id",
		striped: false,
		sidePagination: "server",			//分页方式：client客户端分页，server服务端分页（*）
		pageNumber: 1,						//初始化加载第一页，默认第一页
		pageSize: 10,						//每页的记录行数（*）
		pageList: [10, 20, 50],			//可供选择的每页的行数（*）
		columns: subTableColumns(),
		showHeader: true,
		onLoadSuccess: function(data){
			// if(ctxTheme=="tech"){
				SmartWeb.bootstrapTable.formatAction(row.id);
			// }
		}
	});
	setSubLen(row.id);
}

function setSubLen(id){
	$("#"+id).on('load-success.bs.table', function (data) {
		SmartWeb.swJS.index.init({id:id},{dftlen:20});
	});
}

var subTableColumns = function(){
	var subtableColumns = [
		{field: 'dictLabel',title: '标签名称'},
		{field: 'dictVal',title: '标签值'},
		{field: 'sort',title: '排序值'},
		{field: 'action',title: '操作'}
	];
	return subtableColumns;
}

function loadData(){
	$.post(ctx + "/sys/tSysDict/list", {}, function(data) {
		if (data.returnCode !== undefined && "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
		} else {
			var dataVal =data.dataSetResult[0].data;
			var optionStr = "{'label':'--请选择--','value':''},";
			var optionStr2 = "{'label':'--请选择--','value':''},";
			if(dataVal != "[]"){
				for(var i=0;i<dataVal.length;i++){
					var dictTp = dataVal[i].dictTp;
					var dictInfo = dataVal[i].dictInfo;
					optionStr += "{'label':'"+dictTp+"','value':'"+dictTp+"'},";
					optionStr2 += "{'label':'"+dictInfo+"','value':'"+dictInfo+"'},";
				}
			}
			if(optionStr.length > 0){
				optionStr = "["+optionStr.substring(0,optionStr.length-1)+"]";
				$('select[id="dictTp"]').multiselect('dataprovider',eval('('+optionStr+')') );
			}
			if(optionStr2.length > 0){
				optionStr2 = "["+optionStr2.substring(0,optionStr2.length-1)+"]";
				$('select[id="dictInfo"]').multiselect('dataprovider',eval('('+optionStr2+')') );
			}
		}
	}, "json");
}

/*分页查询传参方法*/
function queryParams(params){
	var formData = $("#listForm").serializeObject();
    var paramList = {
		dictTp: (typeof(formData.dictTp)==undefined)?'':formData.dictTp,
		dictInfo: (typeof(formData.dictInfo)==undefined)?'':formData.dictInfo,
        pgside: 'server',/*服务器分页*/
        pageSize: params.limit,
        start: params.offset + 1,
        pageNo: getPage(params),
        sort: params.sort,
        order: params.order
	};
	return paramList;
}

function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

/*跳转更新页面*/
function update(dictTp){
	console.info("open Update tab");
	var para = "";
	para = para + "?";
	$.session.set('dictTp',dictTp);
	para = para + "dictTp="+dictTp;
	parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/sys/tSysDict/tSysDictUpdate" + para);
	parent.window.$("a[href^='#tab_update']").click();
}

/*跳转明细页面*/
function detail(dictTp){
	console.info("open detail tab");
	var para = "";
	para = para + "?";
	$.session.set('dictTp',dictTp);
	para = para + "dictTp="+dictTp;
	parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/sys/tSysDict/tSysDictDetail" + para);
	parent.window.$("a[href^='#tab_detail']").click();
}

/*删除操作*/
function del(dictTp){
	confirmx("是否确定删除该页面参数信息", function(){
		var url = ctx + "/sys/tSysDict/delete";
		//向后台发送参数
		$.post(url,
				{ 
					dictTp:dictTp

				},
				function(data){ 
					if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
						var errMsg = "错误信息["+data.message+"]"; 
						showContent(errMsg,"error");
						return '0';
					}else if(data.msg_type == "success"){
						var successMsg = "删除信息["+data.message+"]"; 
						showContent(successMsg,"success");
						$("#listBtn").click();
					}
				}, "json");
	});
}


function addVal(dictTp, dictInfo){
	console.info("open add tab");
	var para = "";
	para = para + "?";
	$.session.set('dictTp',dictTp);
	$.session.set('dictInfo',dictInfo);
	para = para + "dictTp="+dictTp;
	parent.window.$("a[href^='#tab_valadd']").attr("url", ctx + "/sys/tSysDict/tSysDictValAdd" + para);
	parent.window.$("a[href^='#tab_valadd']").click();
}

/*跳转更新页面*/
function updateVal(dictTp, dictVal){
	console.info("open Update tab");
	var para = "";
	para = para + "?";
	$.session.set('dictTp',dictTp);
	$.session.set('dictVal',dictVal);
	para = para + "dictTp="+dictTp;
	parent.window.$("a[href^='#tab_valupdate']").attr("url", ctx + "/sys/tSysDict/tSysDictValUpdate" + para);
	parent.window.$("a[href^='#tab_valupdate']").click();
}

/*跳转明细页面*/
function detailVal(dictTp, dictVal){
	console.info("open detail tab");
	var para = "";
	para = para + "?";
	$.session.set('dictTp',dictTp);
	$.session.set('dictVal',dictVal);
	para = para + "dictTp="+dictTp;
	parent.window.$("a[href^='#tab_valdetail']").attr("url", ctx + "/sys/tSysDict/tSysDictValDetail" + para);
	parent.window.$("a[href^='#tab_valdetail']").click();
}

/*删除操作*/
function delVal(dictTp, dictVal){
	confirmx("是否确定删除该页面参数信息", function(){
		var url = ctx + "/sys/tSysDict/deleteVal";
		//向后台发送参数
		$.post(url,
			{
				dictTp:dictTp,
				dictVal:dictVal
			},
			function(data){
				if(data.returnCode!==undefined && "0000"!=data.returnCode){
					var errMsg = "错误信息["+data.message+"]";
					showContent(errMsg,"error");
					return '0';
				}else if(data.msg_type == "success"){
					var successMsg = "删除信息["+data.message+"]";
					showContent(successMsg,"success");
					$("#listBtn").click();
				}
			}, "json");
	});
}
