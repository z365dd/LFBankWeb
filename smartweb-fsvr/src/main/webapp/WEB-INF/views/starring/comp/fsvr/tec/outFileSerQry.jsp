<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- JSTL  -->
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

<script type="text/javascript">
	var ctx = '${ctx}', ctxStatic = '${ctxStatic}';
</script>

<!-- JQUERY -->
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-3.6.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-migrate-3.4.0.min.js" charset="utf-8"></script>
<!-- /JQUERY -->

<!-- DatetimePicker -->
<script src="<%=basePath%>/b_base/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<!-- /DatetimePicker -->

<!-- Bootstrap datetime -->
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.min.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
<link
	href="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen" />


<!-- BOOTSTRAP -->
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.css" />
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap-theme.css" />
<script type="text/javascript"
	src="<%=basePath%>/b_base/bootstrap-3.4.1/js/bootstrap.js"
	charset="utf-8"></script>
<!-- /BOOTSTRAP -->

<!-- LayoutIt bootstrap -->
<!-- table-->
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.css" />
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.js"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/bootstrap-table-export.js"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/tableExport.js"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/locale/bootstrap-table-zh-CN.js" /></script>
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/tableExport.js" /></script>
<!-- /table-->

<!-- FileInput Css-->
<link
	href="<%=basePath%>/b_ide/bootstrap/module/FileInput/css/fileinput.css"
	media="all" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>/b_ide/bootstrap/module/Chart/Chart.js"></script>
<script
	src="<%=basePath%>/b_ide/bootstrap/module/Chart/src/Chart.Doughnut.js"></script>
<script
	src="<%=basePath%>/b_ide/bootstrap/module/Treeview/js/bootstrap-treeview.js"></script>
<script
	src="<%=basePath%>/b_ide/bootstrap/module/FileInput/js/fileinput.js"
	type="text/javascript"></script>
<script
	src="<%=basePath%>/b_ide/bootstrap/module/FileInput/js/locales/zh.js"
	type="text/javascript"></script>
<!-- /FileInput -->

<!-- Validator -->
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/b_base/bootstrapValidator/css/bootstrapValidator.min.css" />
<script type="text/javascript"
	src="<%=basePath%>/b_base/bootstrapValidator/js/bootstrapValidator.min.js"
	charset="utf-8"></script>
<!-- /Validator -->

<!-- Multiselect -->
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/css/bootstrap-multiselect.css" />
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/js/jquery.multiselectcus.js"></script>
<!-- /Multiselect -->

<!-- TABS -->
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/module/tabs/js/bootstrap-tabs.js"></script>
<!-- /TABS -->

<!-- ystep -->
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/module/ystep-master/js/ystep.js"></script>
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/b_ide/bootstrap/module/ystep-master/css/ystep.css" />
<!-- /ystep -->

<!-- JBox -->
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css"
	rel="stylesheet" />
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.src.js"
	type="text/javascript"></script>

<script type="text/javascript"
	src="<%=basePath%>/b_base/common/smartweb.js"></script>

<!-- check -->
<script type="text/javascript"
	src="<%=basePath%>/b_base/common/check.js"></script>
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/b_base/common/formCheck.css" />

<!-- Self reference JS-->
<script type="text/javascript"
	src="<%=basePath%>/b_base/views/starring/comp/fsvr/tec/outFileSerQry.js"
	charset="utf-8"></script>

<!-- change skin -->
<link	href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css"	type="text/css" rel="stylesheet" />

<script type="text/javascript"
	src="<%=basePath%>/b_base/common/formCheck.js" charset="utf-8"></script>

<title>Insert title here</title>
</head>
<body>

	<!-- view start -->
	<div class="">
		<div ravo="rainbow_fx_layout" class="row clearfix">
			<div class="col-md-12 column">
				<div ravo="rainbow_fx_layout_tab" class="tabbable" id="tabs-999600">
					<!-- Only required for left/right tabs -->

					<ul class="nav nav-tabs" data-toggle="tabs">
						<li class="dropdown hide pull-right tabdrop"><a
							padding="10px 10px 10px 10px" class="dropdown-toggle"
							data-toggle="dropdown" href="#"> <i
								class="glyphicon glyphicon-align-justify"> </i> <b class="caret">
							</b>
						</a>
							<ul class="dropdown-menu">
							</ul></li>
						<li class="active"><a href="#panel1" data-toggle="tab"
							class="" id="tab1" name="tab1" aria-expanded="true">
								外部文件服务器查询 </a></li>
						<li class=""><a href="#panel2" data-toggle="tab"
							aria-expanded="false" class="" id="tab2" name="tab2">
								外部文件服务器新增 </a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane in active" id="panel1">
							<div id="messageBox" class="alert alert-success hide">
								<button data-dismiss="alert" class="close">×</button>
								<span id="messageContent">操作提示信息</span>
							</div>
							<form ravo="rainbow_fx_layout_bd" class="form-horizontal"
								pourl="" id="formId_460824" style="margin-top: 10px">
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-3 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3"
												class="col-sm-4 control-label control-label"> 标识号 </label>
											<div class="col-sm-8">
												<input type="text" class="form-control" placeholder=""
													id="FILE_SVR_ID" name="FILE_SVR_ID">
											</div>
										</div>
									</div>
									<div class="col-md-3 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3"
												class="col-sm-4 control-label control-label"> 主机IP </label>
											<div class="col-sm-8">
												<input type="text" class="form-control" placeholder=""
													id="IP" name="IP">
											</div>
										</div>
									</div>
									<div class="col-md-3 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-4 control-label">
												状态 </label>
											<div class="col-sm-8">
												<select data-role="multiselect" id="STAT" class=""
													name="STAT"
													data-url="${ctx}/sys/dict/selectData?type=FSVR_STAT"
													data-async="true" blank-item=true>
												</select>
											</div>
										</div>
									</div>
									<div class="col-md-3 column">
										<div ravo="rainbow_fx_layout" class="row clearfix">
											<div class="col-md-8 column">
												<shiro:haspermission name="anno">
													<button ravo="rainbow_fx" type="button"
														class="btn btn-primary" contenteditable="false"
														id="qryBtn" name="qryBtn">查询</button>
												</shiro:haspermission>
											</div>
										</div>
									</div>
								</div>
							</form>
							<table id="table" data-toggle="table" data-first-load="true"
								data-url="${ctx}/comp/fsvr/tec/fileTec/qry"
								data-click-to-select="false" data-show-export="false"
								data-show-refresh="false" data-show-toggle="false"
								data-show-columns="false" data-pagination="true"
								data-search="false" data-query-params="queryParams"
								data-method="post" data-undefined-text="**" data-height="500"
								data-content-type="application/x-www-form-urlencoded"
								ravo="rainbow_fx_bj" class="table table-hover"
								data-checkbox-header="false" data-sortable="false"
								data-side-pagination="server" data-striped="true">
								<thead style="">
									<tr>
										<th data-field="FILE_SVR_ID">标识号</th>
										<th data-field="IP">主机IP</th>
										<th data-field="PORT">端口</th>
										<th data-field="SVR_DESC">描述</th>
										<th data-field="STAT">状态</th>
										<th data-field="ACTION">操作</th>
									</tr>
								</thead>
							</table>
						</div>
						<div class="tab-pane in" id="panel2"></div>
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
