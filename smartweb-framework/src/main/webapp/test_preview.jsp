<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%> <!-- JSTL  -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Cache-Control" content="no-store">

<script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}';</script>

<!-- JQUERY -->
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-3.6.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-migrate-3.4.0.min.js" charset="utf-8"></script><!-- /JQUERY -->

<!-- DatetimePicker -->
<script src="<%=basePath%>/b_base/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<!-- /DatetimePicker -->

<!-- Bootstrap datetime -->
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<link href="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen"/>


<!-- BOOTSTRAP -->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap-theme.css"/>
<script type="text/javascript" src="<%=basePath%>/b_base/bootstrap-3.4.1/js/bootstrap.js" charset="utf-8"></script>
<!-- /BOOTSTRAP -->

<!-- LayoutIt bootstrap -->
<!-- table-->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.css"/>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/bootstrap-table-export.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/tableExport.js" charset="utf-8"></script>
<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/locale/bootstrap-table-zh-CN.js"/></script>
<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/tableExport.js"/></script>
<!-- /table-->

<!-- FileInput Css-->
<link href="<%=basePath%>/b_ide/bootstrap/module/FileInput/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>/b_ide/bootstrap/module/Chart/Chart.js"></script>
<script src="<%=basePath%>/b_ide/bootstrap/module/Chart/src/Chart.Doughnut.js"></script>
<script src="<%=basePath%>/b_ide/bootstrap/module/Treeview/js/bootstrap-treeview.js"></script>
<script src="<%=basePath%>/b_ide/bootstrap/module/FileInput/js/fileinput.js" type="text/javascript"></script>
<script src="<%=basePath%>/b_ide/bootstrap/module/FileInput/js/locales/zh.js" type="text/javascript"></script>
<!-- /FileInput -->

<!-- Validator -->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrapValidator/css/bootstrapValidator.min.css"/>
<script type="text/javascript" src="<%=basePath%>/b_base/bootstrapValidator/js/bootstrapValidator.min.js" charset="utf-8"></script>
<!-- /Validator -->

<!-- Multiselect -->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/css/bootstrap-multiselect.css"/>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/jquery.multiselectcus.js"></script>
<!-- /Multiselect -->

<!-- TABS -->
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/tabs/js/bootstrap-tabs.js"></script>
<!-- /TABS -->

<!-- ystep -->
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/ystep-master/js/ystep.js"></script>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/ystep-master/css/ystep.css"/>
<!-- /ystep -->

<!-- JBox -->
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.src.js" type="text/javascript"></script>

<script type="text/javascript" src="<%=basePath%>/b_base/common/smartweb.js"></script>

<!-- check --> 
<script type="text/javascript" src="<%=basePath%>/b_base/common/check.js"></script>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/common/formCheck.css"/>

<!-- Self reference JS-->


<!-- change skin -->
<link href="${ctxStatic}/mainframe/${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css" type="text/css" rel="stylesheet" />

<title>文件预览下载测试</title>
</head>
<body>
<div id="messageBox" class="alert alert-success hide"><button data-dismiss="alert" class="close">×</button><span id="messageContent">操作提示信息</span></div>
<!-- view start -->
<script type="text/javascript">
$(document).ready(function(){

	//表格初始化
	$("#fileTable").bootstrapTable({
		//加载成功监听事件
		onLoadSuccess:function (data) {
			console.info("数据成功装载...");
		}
	});
	$('#preDiv').hide();
	
	$("#downloadBtn").click(function(){
		var rows = $("#fileTable").bootstrapTable('getAllSelections');
		console.info("rows="+rows);
		if(rows!=undefined && rows.length>0){
			var fileIds = "";
			for(var i=0;i<rows.length;i++){
				fileIds += rows[i].fileUrl;
				if(i<rows.length-1){
					fileIds += ",";
				}
			}		
			download(ctx+"/msmall/preview/download", fileIds, "");
		}else{
			alert("请选择要下载的记录");
		}
	});
});

/*预览文件*/
function previewFile(file){
	console.info('预览文件：'+file);
	$.post(ctx+"/msmall/preview/show", { file: file },function(data){ 
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
		 	var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}
		var url = data.dataSetResult[0].data[0].previewUrl;
		var isPdf = data.dataSetResult[0].data[0].isPdf;
		
		/*文件预览*/
		$('#previewer').attr('src',url);
		$('#preDiv').show();
	}, "json");
}

function queryParams(params){
	var paramList = { 
		pgside : 'server',//服务器分页
		pageSize : params.limit,
		start : params.offset+1,
		pageNo : getPage(params),
		sort : params.sort,
		order : params.order
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}
</script>

<div class="container">
	<div ravo="rainbow_fx_layout_panel" class="panel panel-success" id="filePreviewer"
	name="filePreviewer">
		<div class="panel-heading">
			<span>文件预览</span>
		</div>
		<div class="panel-body" contenteditable="false">
			<div class="clearfix">
			</div>
			<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
			<shiro:haspermission name="anno">
				<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
				id="downloadBtn">
					下载
				</button>
			</shiro:haspermission>
		</div>
	</div>
			<table id="fileTable" data-toggle="table" data-first-load="true" data-url="${ctx}/msmall/preview/list"
			data-click-to-select="true" data-show-export="true" data-show-refresh="true"
			data-show-toggle="true" data-show-columns="true" data-pagination="true"
			data-search="true" data-query-params="queryParams" data-method="post" data-undefined-text="**"
			data-height="400" data-content-type="application/x-www-form-urlencoded"
			ravo="rainbow_fx_bj" class="table table-hover" data-side-pagination="server">
				<thead>
					<tr>
						<th data-field="state" data-checkbox="true">
						</th>
						<th data-field="file">
							文件名
						</th>
						<th data-field="fileUrl">
							文件名路径
						</th>
						<th data-field="action">
							操作
						</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="preDiv" style="width: 100%;height:100%;">
			<span>预览效果</span>
			<iframe src="" id="previewer" style="width: 100%;height: 900px;"></iframe>
		</div>
	</div>
</div>
<!--customer_code_beg-->

<!--customer_code_end-->
<!-- view end -->
</body>
</html>
