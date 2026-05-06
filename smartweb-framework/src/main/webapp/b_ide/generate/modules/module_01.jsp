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


<title>Insert title here</title>
</head>
<body>
<!-- view start -->
<div class="">
	<div class="path">
		<div ravo="rainbow_fx_layout" class="row clearfix">
			<div class="col-md-12 column col-xs-12 col-sm-12 col-lg-12">
				<div class="clearfix">
				</div>
				<div ravo="rainbow_fx_layout_tab" class="tabbable" id="navigations">
					<!-- Only required for left/right tabs -->
					<ul class="nav nav-tabs" data-toggle="tabs" id="myTab_Module02">
						<li class="active">
							<a href="#tab1_Module02" data-toggle="tab" contenteditable="true" aria-expanded="true"
							title="业务渠道开通列表">
								业务渠道开通列表
							</a>
						</li>
						<li class="">
							<a href="#tab2_Module02" data-toggle="tab" contenteditable="true" aria-expanded="false"
							title="业务渠道开通添加">
								业务渠道开通添加
							</a>
						</li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="tab1_Module02">
							<form ravo="rainbow_fx_layout_bd" class="form-horizontal breadcrumb" pourl=""
							id="qryForm">
								<div class="clearfix">
								</div>
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="column col-md-5">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-4 control-label">
												业务编号
											</label>
											<div class="col-sm-8">
												<div class="input-group">
													<input type="text" class="form-control" placeholder="业务编号" id="BUSI_NO"
													name="BUSI_NO" nexttype="button">
												</div>
											</div>
										</div>
									</div>
									<div class="column col-md-5">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-4 control-label">
												业务名称
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control" placeholder="业务名称" readonly="readonly"
												name="BUSI_NAME" id="BUSI_NAME">
											</div>
										</div>
									</div>
									<div class="column col-md-1">
									</div>
									<div class="column col-md-1">
									</div>
								</div>
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="column col-md-5">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-4 control-label">
												渠道编号
											</label>
											<div class="col-sm-8">
												<div class="input-group">
													<input type="text" class="form-control" placeholder="渠道编号" id="CHNL_NO"
													name="CHNL_NO" nexttype="button">
												</div>
											</div>
										</div>
									</div>
									<div class="column col-md-5">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-4 control-label">
												渠道名称
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control" placeholder="渠道名称" name="CHNL_NAME"
												id="CHNL_NAME" readonly="readonly">
											</div>
										</div>
									</div>
									<div class="column col-md-1">
									</div>
									<div class="column col-md-1">
									</div>
								</div>
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="column col-md-5">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-4 control-label">
												开通状态
											</label>
											<select data-role="multiselect" class="col-sm-8" id="OPEN_STAT" name="OPEN_STAT">
												<option value="0">开通</option>
												<option value="1">关闭</option>
											</select>
										</div>
									</div>
									<div class="column col-md-1">
									</div>
									<div class="column col-md-5">
										<div ravo="rainbow_fx_layout" class="row clearfix">
											<div class="col-md-3 column">
													<button ravo="rainbow_fx" type="button" class="btn false btn-info"
													contenteditable="true" id="qryBtn" data-original-title="" title="">
														查询
													</button>
											</div>
											<div class="col-md-3 column">
													<button ravo="rainbow_fx" type="button" class="btn btn-warning" contenteditable="true"
													id="deleteBtn" data-original-title="" title="">
														删除
													</button>
											</div>
											<div class="col-md-3 column">
													<button ravo="rainbow_fx" type="button" class="btn btn-warning" contenteditable="true"
													id="editBtn" data-original-title="" title="">
														修改
													</button>
											</div>
											<div class="col-md-3 column">
											</div>
										</div>
									</div>
									<div class="column col-md-1">
									</div>
								</div>
							</form>
							<table id="table" data-toggle="table" data-first-load="false" data-url=""
							data-click-to-select="true" data-show-export="false" data-show-refresh="false"
							data-show-toggle="false" data-show-columns="false" data-pagination="true"
							data-search="false" data-query-params="queryParams" data-method="post"
							data-undefined-text="**" data-height="500" data-content-type="application/x-www-form-urlencoded"
							ravo="rainbow_fx_bj" class="table table-hover" data-side-pagination="server"
							data-striped="true">
								<thead>
									<tr>
										<th data-field="stat" data-radio="true">
										</th>
										<th data-field="SEQ_NO">
											序号
										</th>
										<th data-field="BUSI_NO">
											业务编号
										</th>
										<th data-field="CHNL_NO">
											渠道编号
										</th>
										<th data-field="OPEN_STAT" data-visible="false" data-switchable="false">
											开通状态值
										</th>
										<th data-field="OPEN_STAT_STR">
											开通状态
										</th>
										<th data-field="RMRK">
											说明
										</th>
									</tr>
								</thead>
							</table>
						</div>
						<div class="tab-pane" id="tab2_Module02">
							<form ravo="rainbow_fx_layout_bd" class="form-horizontal breadcrumb" pourl=""
							id="editForm">
								<div class="clearfix">
								</div>
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-5 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-4 control-label">
												业务编号
											</label>
											<div class="col-sm-8">
												<div class="input-group">
													<input type="text" class="form-control" placeholder="业务编号" id="EDIT_BUSI_NO"
													name="BUSI_NO" nexttype="button">
												</div>
											</div>
										</div>
									</div>
									<div class="column col-md-5">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-4 control-label">
												业务名称
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control" placeholder="业务名称" id="EDIT_BUSI_NAME"
												name="BUSI_NAME" readonly="readonly">
											</div>
										</div>
									</div>
									<div class="column col-md-1">
										
									</div>
									<div class="column col-md-1">
									</div>
								</div>
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-5 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-4 control-label">
												渠道编号
											</label>
											<div class="col-sm-8">
												<div class="input-group">
													<input type="text" class="form-control" placeholder="渠道编号" id="EDIT_CHNL_NO"
													name="CHNL_NO" nexttype="button">
												</div>
											</div>
										</div>
									</div>
									<div class="column col-md-5">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-4 control-label">
												渠道名称
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control" placeholder="渠道名称" id="EDIT_CHNL_NAME"
												name="CHNL_NAME" readonly="readonly">
											</div>
										</div>
									</div>
									<div class="column col-md-1">
									</div>
									<div class="column col-md-1">
									</div>
								</div>
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-5 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-4 control-label">
												开通状态
											</label>
											<select data-role="multiselect" class="col-sm-8" id="EDIT_OPEN_STAT" name="OPEN_STAT">
												<option value="0">开通</option>
												<option value="1">关闭</option>
											</select>
										</div>
									</div>
									<div class="column col-md-5">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-4 control-label">
												提示信息
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control" placeholder="提示信息" name="ALERT_MSG"
												id="EDIT_ALERT_MSG">
											</div>
										</div>
									</div>
									<div class="column col-md-1">
									</div>
									<div class="column col-md-1">
									</div>
								</div>
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="column col-md-5">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-4 control-label">
												说明
											</label>
											<div class="col-sm-8">
												<textarea class="form-control" rows="3" id="EDIT_RMRK" name="RMRK">
												</textarea>
											</div>
										</div>
									</div>
									<div class="column col-md-5">
									</div>
									<div class="column col-md-1">
									</div>
									<div class="column col-md-1">
									</div>
								</div>
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-3 column">
									</div>
									<div class="col-md-3 column">
									</div>
									<div class="col-md-3 column">
										<div ravo="rainbow_fx_layout" class="row clearfix">
											<div class="col-md-3 column">
													<button ravo="rainbow_fx" type="button" class="btn false btn-info"
													contenteditable="true" id="commitBtn" data-original-title="" title="">
														保存
													</button>
											</div>
											<div class="col-md-3 column">
													<button ravo="rainbow_fx" type="button" class="btn btn-default"
													contenteditable="true" id="cancelBtn" data-original-title="" title="" onclick="history.go(-1)">
														返回
													</button>
											</div>
											<div class="col-md-3 column">
											</div>
											<div class="col-md-3 column">
											</div>
										</div>
									</div>
									<div class="col-md-3 column">
									</div>
								</div>
							</form>
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
