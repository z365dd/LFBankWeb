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
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-migrate-3.4.0.min.js" charset="utf-8"></script><script type="text/javascript" src="<%=basePath%>/b_base/jqueryForm.js" charset="utf-8"></script>
<!-- /JQUERY -->

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
<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/locale/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/tableExport.js"></script>
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

<!-- UEditor -->
<script src="${ctxStatic}/ueditor/ueditor.config.js" type="text/javascript"></script>
<script src="${ctxStatic}/ueditor/ueditor.all.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/ueditor/ueditor.parse.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js" type="text/javascript"></script>

<!-- FontIcon -->
<link rel="stylesheet" href="${ctxStatic}/mainframe/fonticon/iconfont.css">

<script type="text/javascript" src="<%=basePath%>/b_base/common/smartweb.js"></script>

<!-- check --> 
<script type="text/javascript" src="<%=basePath%>/b_base/common/check.js"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/common/formCheck.js"></script>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/common/formCheck.css"/>

<!-- Self reference JS-->
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/ctrl/oper/fCtrlTParaDayForm.js" charset="utf-8"></script>

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
			<form ravo="rainbow_fx_layout_bd" class="form-horizontal breadcrumb" pourl=""
			id="addForm" style="margin-top:10px;">
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="  col-sm-6 control-label">
								平台编号
							</label>
							<div class="col-sm-4">
								<select id="platNo" name="platNo" data-role="multiselect" data-url="${ctx}/comp/ctrl/oper/fCtrlTParaDay/getPlatNo"
								blank-item="true" blank-text="--请选择--" checkbtn="platNo" class="">
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="  col-sm-6 control-label">
								平台状态
							</label>
							<div class="col-sm-4">
								<select id="platStat" name="platStat" data-role="multiselect"
								checkbtn="platStat" class="" disable>
								<option value="">未知</option>
								<option value="1">正常</option>
								<option value="2">正在日切</option>
								<option value="3">日切失败</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class=" control-label  control-label  control-label col-sm-6 control-label">
								平台日期
							</label>
							<div class="input-group-sm   col-sm-4">
								<input type="text" readonly="readonly" maxlength="20" class="form-control input-mini Wdate"
								value="" datetime-skin="twoer" datetime-date-fmt="yyyy-MM-dd" datetime-min-date=""
								datetime-max-date="" datetime-is-show-clear="true" datetime-is-show-week="true"
								datetime-is-show-today="true" datetime-default-value="" datetime-value-fmt="yyyyMMdd"
								data-link-field="val_platDate" id="platDate"
								onchange="writeDateValue('platDate');" check-time-empty="true" >
								<input type="hidden" id="val_platDate" value="" name="platDate">
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class=" control-label  control-label  control-label col-sm-6 control-label">
								日切日期
							</label>
							<div class="input-group-sm   col-sm-4">
								<input type="text" readonly="readonly" maxlength="20" class="form-control input-mini Wdate"
								value="" datetime-skin="twoer" datetime-date-fmt="yyyy-MM-dd" datetime-min-date=""
								datetime-max-date="" datetime-is-show-clear="true" datetime-is-show-week="true"
								datetime-is-show-today="true" datetime-default-value="" datetime-value-fmt="yyyyMMdd"
								data-link-field="val_newPlatDate" id="newPlatDate" onclick="WdatePicker({skin: 'twoer',dateFmt: 'yyyy-MM-dd',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('newPlatDate');}});"
								onchange="writeDateValue('newPlatDate');" check-time-empty="true"  style="background-color:white">
								<input type="hidden" id="val_newPlatDate" value="" name="newPlatDate">
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="column col-md-3">
					</div>
					<div class="column col-md-1">
						<shiro:haspermission name="anno">
							<button ravo="rainbow_fx" type="button" class="btn btn-info" contenteditable="false"
							id="saveBtn" disabled="true">
								日切
							</button>
						</shiro:haspermission>
					</div>
					<div class="col-md-2 column">
						<shiro:haspermission name="anno">
							<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
							id="cancleBtn">
								关闭
							</button>
						</shiro:haspermission>
					</div>
					<div class="col-md-2 column">
					</div>
					<div class="col-md-2 column">
					</div>
					<div class="col-md-2 column">
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
