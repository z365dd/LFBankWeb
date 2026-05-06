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

<!-- Self reference JS-->
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/ctrl/mngdim/fCtrlMngDimMod.js" charset="utf-8"></script>

<script type="text/javascript" src="<%=basePath%>/b_base/common/formCheck.js" charset="utf-8"></script>
<link href="<%=basePath%>/b_base/common/formCheck.css" media="all" rel="stylesheet" type="text/css" />

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
			<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="CtrlModlAddForm" style="margin-top: 20px">
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-12 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								组件号
							</label>
							<div class="col-sm-3">
								<select data-role="multiselect" id="COMP_NO" name="COMP_NO" class="form-control">
								</select>
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-12 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label" style="visibility:visible">
								维度
							</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" placeholder="" id="DIM_KEY"
								name="DIM_KEY" readonly="readonly">
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-12 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								维度描述
							</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" placeholder="" id="DIM_DESC"
								name="DIM_DESC">
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-12 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								维度值表名
							</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" placeholder="" id="TAB_NAME"
								name="TAB_NAME">
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-3 column">
					</div>
					<div class="col-md-2 column">
						<shiro:haspermission name="anno">
							<button ravo="rainbow_fx" type="button" class="btn btn-primary" contenteditable="false"
							id="updateBtn">
								提交
							</button>
						</shiro:haspermission>
					</div>
					<div class="col-md-2 column">
						<shiro:haspermission name="anno">
							<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
							id="cancelBtn">
								返回
							</button>
						</shiro:haspermission>
					</div>
					<div class="col-md-4 column">
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<!--customer_code_beg-->

<!--customer_code_end-->
<!-- view end -->
</body>
</html>
