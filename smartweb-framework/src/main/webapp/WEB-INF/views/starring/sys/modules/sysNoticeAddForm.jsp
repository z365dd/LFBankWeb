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
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/sys/modules/sysNoticeAddForm.js" charset="utf-8"></script>

<!-- change skin -->
<link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'tech'}/index.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/mainframe/${not empty cookie.theme.value ? cookie.theme.value : 'tech'}/zujian.css" type="text/css" rel="stylesheet" />
<%@ include file="/WEB-INF/views/include/treeview.jsp" %>
<title>Insert title here</title>
</head>
<body>
<div id="messageBox" class="alert alert-success hide"><button data-dismiss="alert" class="close">×</button><span id="messageContent">操作提示信息</span></div>
<!-- view start -->

<div class="">
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
			<div ravo="rainbow_fx_layout_panel" class="panel panel-default">
				<div class="panel-body" contenteditable="false">
					<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="addForm">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-4 control-label">
								公告标题
							</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" placeholder="标题" check-empty="true"
								maxlength="30" id="noteTitle" name="noteTitle">
							</div>
						</div>
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class=" col-sm-4 control-label">
								公告内容
							</label>
							<div class="col-sm-4">
								<textarea class="form-control" rows="10" id="noteCntt" name="noteCntt" check-time-empty="true"
								maxlength="600">
								</textarea>
							</div>
						</div>
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-4 control-label">
								公告方式
							</label>
							<div class="col-sm-8">
								<select data-role="multiselect" id="noteScp" class="" name="noteScp" check-empty="true"
								data-url="${ctx}/sys/dict/selectData?type=NOTE_SCP" checkbtn="true"
								blank-item="true">
								</select>
							</div>
						</div>
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-4 control-label">
								是否弹窗
							</label>
							<div class="col-sm-8">
								<select data-role="multiselect" id="popupFlg" class="" name="popupFlg" >
									<option value="Y">是</option>
									<option value="N">否</option>
								</select>
							</div>
						</div>
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-4 control-label">
								跳转链接
							</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" placeholder="添加链接描述"
									   data-bv-="true" id="url" name="url" style="width: 300px;">
							</div>
						</div>
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-4 control-label">
								链接描述
							</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" placeholder="添加链接描述"
									   data-bv-="true" id="linkDesc" name="linkDesc" style="width: 300px;">
							</div>
						</div>
						<div ravo="rainbow_fx" class="form-group" id="dataSelect">
							<label class="control-label  control-label col-sm-4 control-label" style="visibility:visible">
								公告范围
							</label>
							<div class="col-sm-4">
								<div style="height:300px; margin-left: 10px; overflow-y: scroll; overflow-x: scroll;">
									<div id="treeData" class="ztree"></div>
									<input type="hidden" name="ids" value="">
								</div>
							</div>
						</div>						
					</form>
				</div>
			</div>
			<div ravo="rainbow_fx_layout_panel" class="panel panel-default">
				<div class="panel-heading">
				</div>
				<div class="panel-body" contenteditable="false">
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-2 column">
						</div>
						<div class="col-md-2 column">
						</div>
						<div class="col-md-2 column">
							<shiro:haspermission name="anno">
								<button ravo="rainbow_fx" type="button" class="btn btn-info" contenteditable="false"
								id="saveBtn">
									保存
								</button>
							</shiro:haspermission>
						</div>
						<div class="col-md-2 column">
							<shiro:haspermission name="anno">
								<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
								id="cancleBtn">
									取消
								</button>
							</shiro:haspermission>
						</div>
						<div class="col-md-2 column">
						</div>
						<div class="col-md-2 column">
						</div>
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
