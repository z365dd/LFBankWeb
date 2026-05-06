<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  <!-- JSTL  -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>模板02</title>
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
<!-- Self reference JS-->
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
<!-- 
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/css/bootstrap-multiselect.css"/>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/bootstrap-multiselect-collapsible-groups.js"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/jquery.multiselectcus.js"></script>
-->
<!-- /Multiselect -->
<!-- Multiselect -->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/css/bootstrap-multiselect.css"/>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/jquery.multiselectcus.js"></script>
<!-- /Multiselect -->

<script type="text/javascript" src="<%=basePath%>/b_base/common/smartweb.js"></script>


</head>
<body>
<div class="">
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
			<div ravo="rainbow_fx_layout_panel" class="panel panel-success">
				<div class="panel-heading">
					<h5 ravo="rainbow_fx_bj" data-rainbow="caption">
						信息查询
					</h5>
				</div>
				<div class="panel-body">
					<table data-sortable="true" data-sort-name="userId" data-side-pagination="server"
					xmlpath="" pourl="com.adtec.db.entity.User"
					data-toolbar="#webar" class="table table-hover" id="_table" data-toggle="table"
					data-url="" data-click-to-select="true" data-show-export="true"
					data-show-refresh="true" data-show-toggle="true" data-show-columns="true"
					data-pagination="true" data-search="false" data-query-params="queryParams"
					data-method="post" data-undefined-text="**" data-height="300" data-content-type="application/x-www-form-urlencoded"
					ravo="rainbow_fx_bj">
						<thead style="">
							<tr>
								<th data-field="state" data-checkbox="true">
								</th>
								<th data-formatter="" data-sort-name="userId" data-sortable="true" data-field="userId">
									userId
								</th>
								<th data-sort-name="userName" data-sortable="true" data-align="" data-field="userName">
									userName
								</th>
								<th data-field="userPwd">
									userPwd
								</th>
								<th data-field="orgCode">
									orgCode
								</th>
								<th data-field="roleId">
									roleId
								</th>
								<th data-field="grade">
									grade
								</th>
								<th data-field="telephone">
									telephone
								</th>
								<th data-field="email">
									email
								</th>
								<th data-field="createDate">
									createDate
								</th>
								<th data-field="status">
									status
								</th>
								<th data-field="remark1">
									remark1
								</th>
								<th data-field="remark2">
									remark2
								</th>
								<th data-field="orgName">
									orgName
								</th>
								<th data-field="roleName">
									roleName
								</th>
								<th data-field="createDateStr">
									createDateStr
								</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
			<div ravo="rainbow_fx_layout_panel" class="panel panel-warning">
				<div class="panel-heading">
					<h5 ravo="rainbow_fx_bj" data-rainbow="caption">
						表单编辑
					</h5>
				</div>
				<div class="panel-body">
					<form id="formId_744971" ravo="rainbow_fx_layout_bd" class="form-horizontal"
					pourl="com.adtec.db.entity.User">
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">
										名字：
									</label>
									<div class="col-sm-7">
										<input id="input2"data-bv-notempty-message="输入不为空!" data-bv-notempty="true" name="userId"
										class="form-control" placeholder="Rainbow" type="text">
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">
										编号：
									</label>
									<div class="col-sm-7">
										<input id="input3"name="userName" data-bv-stringlength-message="输入长度为4-8!" data-bv-stringlength-max="8"
										data-bv-stringlength-min="4" data-bv-stringlength="true" class="form-control"
										placeholder="Rainbow" type="text">
									</div>
								</div>
								<div ravo="rainbow_fx_radio" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">
										单选框：
									</label>
									<div class="col-sm-7">
										<div class="radio">
											<label value="1" style="visibility:visible">
												<input id="optionsRadio0"
												value="optio" name="optionsRadios" type="radio">
												男
											</label>
										</div>
										<div class="radio">
											<label value="2" style="visibility:visible">
												<input  id="optionsRadio1"
												value="optio" name="optionsRadios" type="radio">
												女
											</label>
										</div>
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">
										选项：
									</label>
									<select id="selectId_337784" data-role="multiselect" class="col-sm-7">
										<option value="11111">
											1
										</option>
										<option value="22222">
											2
										</option>
										<option value="33333">
											3
										</option>
										<option value="44444">
											4
										</option>
									</select>
								</div>
							</div>
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">
										手机号码：
									</label>
									<div class="col-sm-7">
										<input id="input4"data-bv-telephone-message="请输入正确的手机号!" data-bv-telephone="true"
										name="telephone" class="form-control" placeholder="Rainbow" type="text">
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">
										Rainbow：
									</label>
									<div class="col-sm-7">
										<input name="roleId" class="form-control" placeholder="Rainbow" type="text">
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">
										选项：
									</label>
									<select id="selectId_507686" data-role="multiselect" class="col-sm-7">
									</select>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">
										Email：
									</label>
									<div class="col-sm-7">
										<textarea class="form-control" rows="3">
										</textarea>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-2 column">
		</div>
		<div class="col-md-2 column">
			<button title="" data-original-title="" id="btnId_750740" ravo="rainbow_fx"
			type="button" class="btn btn-block btn-primary">
				确定
			</button>
		</div>
		<div class="col-md-2 column">
			<button id="btnId_313336" ravo="rainbow_fx" type="button" class="btn btn-default btn-block">
				修改
			</button>
		</div>
		<div class="col-md-2 column">
			<button id="btnId_82523" ravo="rainbow_fx" type="button" class="btn btn-primary btn-block">
				删除
			</button>
		</div>
		<div class="col-md-2 column">
			<button id="btnId_744012" ravo="rainbow_fx" type="button" class="btn btn-default btn-block">
				取消
			</button>
		</div>
		<div class="col-md-2 column">
		</div>
	</div>
	<div id="webar" ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
			<form id="formId_979188" ravo="rainbow_fx_layout_bd" class="form-inline"
			pourl="com.adtec.db.entity.User">
				<div ravo="rainbow_fx" class="form-group">
					<label for="inputEmail3" class="">
						Rainbow：
					</label>
					<input name="userId" id="input22" class="form-control" placeholder="请输入userId" type="text">
					<input name="userName" id="input1" class="form-control" placeholder="请输入userName" type="text">
				</div>
				<button title="" data-original-title="" id="btnId_84886" ravo="rainbow_fx"
				type="button" class="btn btn-warning">
					查询
				</button>
			</form>
		</div>
	</div>
</div>

</body>
</html>
