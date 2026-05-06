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
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/ctrl/oper/ctrlTParaDataSynConfigUpdateForm.js" charset="utf-8"></script>

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
			id="ctrlTParaDataSynConfigForm" style="margin-top:10px;">
			<input type="hidden" id="stepNo" name="stepNo" />
								
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label">
								来源类型
							</label>
							<div class="col-sm-4">
								<select id="srcTp" name="srcTp" data-role="multiselect"  data-async="true"  checkbtn="srcTp">
									<option value="">--请选择--</option>
								<option value="1">数据源</option>
								<option value="2">文件</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								更新类型
							</label>
							<div class="col-sm-4">
								<select id="uptTp" name="uptTp" data-role="multiselect"  data-async="true"  checkbtn="uptTp"  >
								<option value="">--请选择--</option>
								<option value="1">全量</option>
								<option value="2">增量</option></select>
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label">
								来源表名
							</label>
							<div class="col-sm-4">
								<input id="srcTabName" name="srcTabName" class="form-control "  maxlength="120"   >
								</input>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								来源数据源
							</label>
							<div class="col-sm-4">
								<select id="srcDataSrc" name="srcDataSrc" data-role="multiselect"  data-async="true"  
								 data-url="${ctx}/comp/ctrl/oper/ctrlTParaDataSource/getList"  >
								</select>
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label">
								来源表描述
							</label>
							<div class="col-sm-4">
								<input id="srcTabDesc" name="srcTabDesc" class="form-control "  maxlength="360"   >
								</input>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								关联系统
							</label>
							<div class="col-sm-4">
								<select id="relatSys" name="relatSys" data-role="multiselect"  data-async="true"  
								data-url="${ctx}/comp/ctrl/oper/ctrlTParaRelatSys/getRelatSys" >
								</select>
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label">
								是否同库
							</label>
							<div class="col-sm-4">
								<select id="sameDbFlg" name="sameDbFlg" data-role="multiselect"  data-async="true"  checkbtn="sameDbFlg"  >
								<option value="">--请选择--</option>
								<option value="Y">是</option>
								<option value="N">否</option></select>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								目的数据源
							</label>
							<div class="col-sm-4">
								<select id="dstDataSrc" name="dstDataSrc" data-role="multiselect"  data-async="true" 
								data-url="${ctx}/comp/ctrl/oper/ctrlTParaDataSource/getList" >
								</select>
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label">
								目标表名
							</label>
							<div class="col-sm-4">
								<input id="dstTabName" name="dstTabName" class="form-control "  maxlength="120" >
								</input>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								目的表描述
							</label>
							<div class="col-sm-4">
								<input id="dstTabDesc" name="dstTabDesc" class="form-control "  maxlength="360"   >
								</input>
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-4 column">
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
								返回
							</button>
						</shiro:haspermission>
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
