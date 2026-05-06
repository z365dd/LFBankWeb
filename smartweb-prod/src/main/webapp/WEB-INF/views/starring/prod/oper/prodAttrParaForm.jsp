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
	src="<%=basePath%>/b_base/views/starring/prod/oper/prodAttrParaForm.js"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<%=basePath%>/b_base/views/starring/prod/oper/attrCommon.js"
	charset="utf-8"></script>

<!-- change skin -->
<link
	href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css"
	type="text/css" rel="stylesheet" />

<script type="text/javascript"
	src="<%=basePath%>/b_base/common/formCheck.js" charset="utf-8"></script>

<title>Insert title here</title>
</head>
<body>
	<div id="messageBox" class="alert alert-success hide">
		<button data-dismiss="alert" class="close">×</button>
		<span id="messageContent">操作提示信息</span>
	</div>
	<!-- view start -->
	<div class="">
		<div ravo="rainbow_fx_layout" class="row clearfix">
			<div class="col-md-12 column">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
					id="formId_286247" style="margin-top: 20px">
					<div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-6 column">
								<!-- <div ravo="rainbow_fx" class="form-group" id="commonDiv">
									<label for="inputEmail3"
										class="col-sm-5 control-label control-label"> 组件号 </label>
									<div class="col-sm-4">
										<select data-role="multiselect" id="COMP_NO" class=""
											name="COMP_NO" data-max-height="300" checkbtn="COMP_NO">
											<option value="">请选择</option>
										</select>
									</div>
								</div> -->
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3"
										class="col-sm-5 control-label control-label">组件编号</label>
									<div class="col-sm-4">
										<select data-role="multiselect" id="COMP_NO" class=""
											name="COMP_NO" data-max-height="300" checkbtn="COMP_NO"
											data-url="${ctx}/prod/oper/prodAttr/getComp"
											data-async="true">
											<option value="">请选择</option>
										</select>
									</div>
								</div>
								<!-- <div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class=" col-sm-5 control-label">
										组件名称</label>
									<div class="col-sm-4">
										<input type="text" class="form-control" placeholder=""
											id="COMP_NAME" name="COMP_NAME" check-empty="true">
									</div>
								</div> -->
								<div ravo="rainbow_fx" class="form-group" id="commonDiv">
									<label for="inputEmail3"
										class="col-sm-5 control-label control-label"> 属性类型</label>
									<div class="col-sm-4">
										<select data-role="multiselect" id="KEY_TP" class=""
											name="KEY_TP" data-max-height="300" checkbtn="KEY_TP">
											<option value="">请选择</option>
											<option value="02">技术通用属性</option>
											<option value="03">技术关联属性</option>
										</select>
<%--										<select data-role="multiselect" id="KEY_TP" name="KEY_TP" data-url="${ctx}/sys/dict/selectData?type=KEY_TP" data-max-height="300" blank-item="true"--%>
<%--												data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="KEY_TP">--%>
<%--										</select>--%>
									</div>
								</div>

							</div>
							<div class="col-md-6 column"></div>
						</div>

						<!-- div1 -->
						<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
							id="pan1Div">
							<div class="panel-heading">
								<div ravo="rainbow_fx_bj">
									<h4 contenteditable="false">技术通用属性</h4>
								</div>
							</div>
							<div class="panel-body" contenteditable="false">
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-6 column">
										<div ravo="rainbow_fx" class="form-group" id="commonDiv">
											<label for="inputEmail3"
												class="col-sm-5 control-label control-label"> 属性列表 </label>
											<div class="col-sm-4">
												<select data-role="multiselect" id="KEY_NO" class=""
													name="KEY_NO" data-max-height="300" checkbtn="KEY_NO"
													data-url="${ctx}/prod/oper/prodAttrPara/getKeyTp">
													<option value="">请选择</option>
												</select>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>

						<!-- div2 -->
						<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
							id="pan2Div">
							<div class="panel-heading">
								<div ravo="rainbow_fx_bj">
									<h4 contenteditable="false">URL</h4>
								</div>
							</div>
							<div class="panel-body" contenteditable="false">
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-6 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class=" col-sm-5 control-label">
												属性名称</label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="KEY_NAME" name="KEY_NAME">
											</div>
										</div>

										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class=" col-sm-5 control-label">
												url</label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="KV" name="KV">
											</div>
										</div>

										<!-- <div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class=" col-sm-5 control-label">
												说明</label>
											<div class="col-sm-7">
												<input type="text" class="form-control" placeholder=""
													id="KEY_DESC" name="KEY_DESC">
											</div>
										</div> -->

									</div>
								</div>
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-12 column" style="margin-left: 4.3%">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-2  control-label">
												说明 </label>
											<div class="col-sm-7">
												<input type="text" class="form-control" placeholder=""
													data-toggle="popover" data-container="body" id=KEY_DESC
													name="KEY_DESC">
											</div>
										</div>
									</div>
								</div>

							</div>
						</div>


						<div ravo="rainbow_fx_layout" class="row clearfix"
							id="rowCommonDiv">
							<div class="col-md-6 column"></div>
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group" id='rowBtn'>
									<div class="col-md-3 column"></div>
									<div class="col-md-2 column">
										<%-- <shiro:haspermission name="anno">
											<button ravo="rainbow_fx" type="button"
												class="btn btn-default" contenteditable="false"
												id="addBtnRow">确定修改</button>
										</shiro:haspermission> --%>
									</div>
									<div class="col-md-2 column">
										<shiro:haspermission name="anno">
											<button ravo="rainbow_fx" type="button"
												class="btn btn-default" contenteditable="false"
												id="addBtnRow">增加一行</button>
										</shiro:haspermission>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div ravo="rainbow_fx_layout" class="row clearfix"
						style="margin-bottom: 20px">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<label for="inputEmail3"
									class="col-sm-1 control-label control-label"
									style="visibility: visible; margin-left: 12%"></label>
								<div class="col-sm-7">
									<table id="table" data-height="200" data-toggle="table"
										data-first-load="false" data-click-to-select="true"
										data-show-export="false" data-show-refresh="false"
										data-show-toggle="false" data-show-columns="false"
										data-pagination="false" data-search="false" data-method="post"
										data-undefined-text="**"
										data-content-type="application/x-www-form-urlencoded"
										ravo="rainbow_fx_bj" class="table table-hover"
										data-single-select="true" style="border: 1px solid #9EBED7;">
										<thead style="">
											<tr>
												<th data-field="COMP_NO" data-visible="false">模型号</th>
												<th data-field="KEY_NO" data-visible="false">属性编号</th>
												<th data-field="KEY_NAME">属性名称</th>
												<th data-field="KEY_TP" data-visible="false">属性类型</th>
												<th data-field="ENTER_TP" data-visible="false">控件类型</th>
												<th data-field="KEY_TP_STR">属性类型</th>
												<th data-field="ENTER_TP_STR">控件类型</th>
												<th data-field="KV">键值</th>
												<th data-field="ACTION">操作</th>
											</tr>
										</thead>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix"
						style="margin-top: 20px">
						<div class="col-md-6 column" id="addDiv">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
								</label>
								<div class="col-sm-4">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-primary " contenteditable="false" id="addBtn">提交</button>
									</shiro:haspermission>
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-3 column"></div>
								<div class="col-md-4 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
											id="cancelBtn">返回</button>
									</shiro:haspermission>
								</div>
							</div>
						</div>
					</div>

					<div ravo="rainbow_fx_layout" class="row clearfix"
						style="border-top: solid #ddd 1px;">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group"></div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group"></div>
						</div>
					</div>
					<div id="prodAttrDictDiv"></div>
				</form>
			</div>
		</div>
	</div>
	<!--customer_code_beg-->

	<!--customer_code_end-->
	<!-- view end -->
</body>
</html>
