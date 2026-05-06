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
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/sys/flow/flowManage.js" charset="utf-8"></script>

<!-- change skin -->
<link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css" type="text/css" rel="stylesheet" />

<title>Insert title here</title>
</head>
<body>
<div id="messageBox" class="alert alert-success hide"><button data-dismiss="alert" class="close">×</button><span id="messageContent">操作提示信息</span></div>
<!-- view start -->
<div class="">
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
			<div ravo="rainbow_fx_layout_tab" class="tabbable" id="navigations">
				<!-- Only required for left/right tabs -->
				<ul class="nav nav-tabs" data-toggle="tabs">
					<li class="dropdown hide pull-right tabdrop">
						<a padding="10px 10px 10px 10px" class="dropdown-toggle" data-toggle="dropdown"
						href="#">
							<i class="glyphicon glyphicon-align-justify">
							</i>
							<b class="caret">
							</b>
						</a>
						<ul class="dropdown-menu">
						</ul>
					</li>
					<li class="active">
						<a id="categoryList" href="#tab_list" data-toggle="tab" contenteditable="false"
						title="流程列表" aria-expanded="true" url="${ctx}/sys/flow/flow/flowList"
						class="">
							流程列表
						</a>
					</li>
					<li class="">
						<a href="#tab_add" data-toggle="tab" aria-expanded="false" url="${ctx}/sys/flow/flow/flowAdd"
						class="">
							流程添加
						</a>
					</li>
					<li class="">
						<a href="#tab_update" data-toggle="tab" aria-expanded="false" url="${ctx}/sys/flow/flow/flowUpdate"
						class="">
							流程更新
						</a>
					</li>
					<li class="">
						<a href="#tab_detail" data-toggle="tab" aria-expanded="false" url="${ctx}/sys/flow/flow/flowDetail"
						class="">
							流程详情
						</a>
					</li>
					<li class="">
						<a href="#tab_apply" data-toggle="tab" aria-expanded="false" url="${ctx}/sys/flow/flow/flowApply"
						class="">
							流程审批
						</a>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane in active" id="tab_list">
						<iframe id="iframe_list" name="iframe_list" src="" style="width: 100%; height: 800px; border: 0px;"
						scrolling="no" frameborder="no">
						</iframe>
					</div>
					<div id="tab_add" class="tab-pane">
						<iframe id="iframe_add" name="iframe_add" src="" style="width: 100%; height: 1300px; border: 0px;"
						scrolling="no" frameborder="no">
						</iframe>
					</div>
					<div id="tab_update" class="tab-pane">
						<iframe id="iframe_update" name="iframe_update" src="" style="width: 100%; height: 1300px; border: 0px;"
						scrolling="no" frameborder="no">
						</iframe>
					</div>
					<div id="tab_detail" class="tab-pane">
						<iframe id="iframe_detail" name="iframe_detail" src="" style="width: 100%; height: 1300px; border: 0px;"
						scrolling="no" frameborder="no">
						</iframe>
					</div>
					<div id="tab_apply" class="tab-pane">
						<iframe id="iframe_apply" name="iframe_apply" src="" style="width: 100%; height: 1300px; border: 0px;"
						scrolling="no" frameborder="no">
						</iframe>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--customer_code_beg-->

<!--customer_code_end-->
<!-- view end -->
</body>
</html>
