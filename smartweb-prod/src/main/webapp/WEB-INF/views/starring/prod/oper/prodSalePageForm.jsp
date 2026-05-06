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
	src="<%=basePath%>/b_base/views/starring/prod/oper/attrCommon.js"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<%=basePath%>/b_base/views/starring/prod/oper/prodSalePageForm.js"
	charset="utf-8"></script>


<!-- change skin -->
<link	href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css"	type="text/css" rel="stylesheet" />

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
					<div ravo="rainbow_fx_layout" class="row clearfix" id="qryDiv">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									产品线 </label>
								<div class="col-sm-7">
									<select data-role="multiselect" id="PROD_LINE_CODE" class=""
										name="PROD_LINE_CODE" data-max-height="300">
										<option value="">请选择</option>
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									原子产品 </label>
								<div class="col-sm-7">
									<select data-role="multiselect" id="ATOM_PROD_CODE" class=""
										name="ATOM_PROD_CODE" data-max-height="300">
										<option value="">请选择</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3"
									class="col-sm-3 control-label control-label">可售产品</label>
								<div class="col-sm-4">
									<select data-role="multiselect" id="SALE_PROD_CODE" class=""
										name="SALE_PROD_CODE" data-max-height="300">
										<option value="">请选择</option>
									</select>
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
									style="visibility: visible; margin-left: 12%; margin-top: 2%;">
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-12 column">
											<shiro:haspermission name="anno">
												<button ravo="rainbow_fx" type="button"
													class="btn btn-default" contenteditable="false" id="upBtn">上移</button>
											</shiro:haspermission>
										</div>
									</div>
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-12 column">
											<shiro:haspermission name="anno">
												<button ravo="rainbow_fx" type="button"
													class="btn btn-default" contenteditable="false"
													id="downBtn" style="margin-top: 20px;">下移</button>
											</shiro:haspermission>
										</div>
									</div>
								</label>
								<div class="col-sm-8">
									<table id="table1" data-height="350" data-toggle="table" data-first-load="false"
										data-click-to-select="true" data-show-export="false"
										data-show-refresh="false" data-show-toggle="false"
										data-show-columns="false" data-pagination="false"
										data-search="false" data-method="post"
										data-undefined-text="**"
										data-content-type="application/x-www-form-urlencoded"
										ravo="rainbow_fx_bj" class="table table-hover"
										data-single-select="true" style="border: 1px solid #9EBED7">
										<thead style="">
											<tr>
												<th data-field="ATOM_PROD_CODE">归属方</th>
												<th data-field="KEY_NO">属性</th>
												<th data-field="KEY_NAME">属性名称</th>
												<th data-field="KEY_TP">控件类型</th>
												<th data-field="LINE_SER">行</th>
												<th data-field="COL_SER">列</th>
												<th data-field="KEY_FLG">属性合并</th>
												<th data-field="ACTION">操作</th>
												<th data-field="FLG" data-visible="false">是否隐藏</th>
												<th data-field="COMP_NO" data-visible="false">组件号</th>
												<th data-field="VAL_TP" data-visible="false">值类型</th>
												<th data-field="VAL_LEN" data-visible="false">值长度</th>
												<th data-field="KEY_TP" data-visible="false">键类型</th>
												<th data-field="INPUT_FLG" data-visible="false">输入标志</th>
												<th data-field="INPUT_FLG" data-visible="false">输入标志</th>
												<th data-field="CTRL_LIST" data-visible="false">CTRL_LIST</th>
												<th data-field="SUB_LIST" data-visible="false">SUB_LIST</th>
											</tr>
										</thead>
									</table>
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
					<div id="ifAddDiv">
						<div ravo="rainbow_fx_layout" class="row clearfix"
							style="margin-bottom: 20px">
							<div class="col-md-12 column">
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<label for="inputEmail3"
										class="col-sm-1 control-label control-label"
										style="visibility: visible; margin-left: 12%"></label>
									<div class="col-sm-8">
										<table id="table2" data-toggle="table" data-first-load="false"
											data-click-to-select="true" data-show-export="false"
											data-show-refresh="false" data-show-toggle="false"
											data-show-columns="false" data-pagination="false"
											data-search="false" data-method="post"
											data-undefined-text="**" data-height="180"
											data-content-type="application/x-www-form-urlencoded"
											ravo="rainbow_fx_bj" class="table table-hover"
											data-single-select="true" style="border: 1px solid #9EBED7;">
											<thead style="">
												<tr>
													<th data-field="ATOM_PROD_CODE">归属方</th>
													<th data-field="KEY_NO">属性</th>
													<th data-field="KEY_NAME">属性名称</th>
													<th data-field="KEY_TP">控件类型</th>
													<th data-field="KEY_FLG" data-visible="false">属性合并</th>
													<th data-field="COMP_NO" data-visible="false">组件号</th>
													<th data-field="VAL_TP" data-visible="false">值类型</th>
													<th data-field="VAL_LEN" data-visible="false">值长度</th>
													<th data-field="KEY_TP" data-visible="false">键类型</th>
													<th data-field="INPUT_FLG" data-visible="false">输入标志</th>
													<th data-field="INPUT_FLG" data-visible="false">输入标志</th>
													<th data-field="CTRL_LIST" data-visible="false">CTRL_LIST</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix"
							style="margin-top: 20px">
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-5 control-label">
									</label>
									<div class="col-sm-4">
										<shiro:haspermission name="anno">
											<button ravo="rainbow_fx" type="button"
												class="btn btn-primary " contenteditable="false"
												id="mergeBtn">合并</button>
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
												id="nmergeBtn">不合并</button>
										</shiro:haspermission>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div id="setDiv">
						<div ravo="rainbow_fx_layout" class="row clearfix" id="tableDiv">
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-5 control-label">
										行 </label>
									<div class="col-sm-4">
										<input type="text" class="form-control" placeholder=""
											id="LINE_SER" name="LINE_SER" check-empty="true">
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-5 control-label">
										是否隐藏</label>
									<div class="col-sm-7">
										<select data-role="multiselect" id="FLG" class="" name="FLG"
											data-max-height="300">
											<option value="">请选择</option>
											<option value="Y">隐藏</option>
											<option value="N">不隐藏</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3"
										class="col-sm-3 control-label control-label">列</label>
									<div class="col-sm-4">
										<input type="text" class="form-control" placeholder=""
											id="COL_SER" name="COL_SER" check-empty="true">
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3"
										class="col-sm-3 control-label control-label"></label>
									<div class="col-sm-4">
										<shiro:haspermission name="anno">
											<button ravo="rainbow_fx" type="button"
												class="btn btn-primary" contenteditable="false" id="setBtn"
												name="setBtn">确认设置</button>
										</shiro:haspermission>
									</div>
								</div>
							</div>
						</div>

						<div id="prodAttrDictDiv"></div>
					</div>

					<div ravo="rainbow_fx_layout" class="row clearfix"
						style="margin-top: 20px">
						<div class="col-md-6 column">
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
				</form>
			</div>
		</div>
	</div>
	<!--customer_code_beg-->

	<!--customer_code_end-->
	<!-- view end -->
</body>
</html>
