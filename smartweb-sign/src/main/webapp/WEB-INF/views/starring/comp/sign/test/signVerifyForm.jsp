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
	src="<%=basePath%>/b_base/views/starring/comp/sign/test/signVerifyForm.js"
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
	<div class="">
		<div ravo="rainbow_fx_layout" class="row clearfix">
			<div class="col-md-12 column">
				<div ravo="rainbow_fx_layout_tab" class="tabbable" id="myTabs">
					<!-- Only required for left/right tabs -->
					<ul class="nav nav-tabs" data-toggle="tabs">
						<li class="active"><a href="#panel1" data-toggle="tab"
							id="tab1" name="tab1"> 签约校验 </a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="panel1">
							<div id="messageBox" class="alert alert-success hide">
								<button data-dismiss="alert" class="close">×</button>
								<span id="messageContent">操作提示信息</span>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-12 column">
									<form ravo="rainbow_fx_layout_bd" class="form-horizontal"
										pourl="" id="formId_683647">
										<div ravo="rainbow_fx_layout_panel"
											class="panel panel-default">
											<div class="panel-heading">
												<div ravo="rainbow_fx_bj">
													<h4 contenteditable="false">提交查询</h4>
												</div>
											</div>
											<div class="panel-body" contenteditable="false">
												<div ravo="rainbow_fx_layout" class="row clearfix">
													<div class="col-md-6 column">
														<div ravo="rainbow_fx" class="form-group">
															<label for="inputEmail3"
																class="col-sm-5 control-label control-label">
																签约协议类型 </label>
															<div class="col-sm-4">
																<select data-role="multiselect" id="SIGN_PROT_TP_ID"
																	name="SIGN_PROT_TP_ID" class=""
																	data-url="${ctx}/comp/sign/test/signFuncCust/getSignTpPara"
																	checkbtn="SIGN_PROT_TP_ID" data-bv-="true"
																	data-max-height="300">
																</select>
															</div>
														</div>
														<div ravo="rainbow_fx" class="form-group">
															<label for="inputEmail3"
																class="col-sm-5 control-label control-label">
																签约协议号</label>
															<div class="col-sm-4">
																<input type="text" class="form-control" placeholder=""
																	id="SIGN_PROT_NO" name="SIGN_PROT_NO"
																	check-empty='true'>
															</div>
														</div>
														<div ravo="rainbow_fx" class="form-group">
															<label for="inputEmail3"
																class="col-sm-5 control-label control-label">
																第三方客户号 </label>
															<div class="col-sm-4">
																<input type="text" class="form-control" placeholder=""
																	id="OTH_CUST_NO" name="OTH_CUST_NO">
															</div>
														</div>
													</div>
													<div class="col-md-6 column"></div>
												</div>
												<div ravo="rainbow_fx_layout" class="row clearfix">
													<div class="col-md-6 column">
														<div ravo="rainbow_fx" class="form-group">
															<label for="inputEmail3"
																class="col-sm-5 control-label control-label">
																账/卡号 </label>
															<div class="col-sm-4">
																<input type="text" class="form-control" placeholder=""
																	id="ACCT" name="ACCT">
															</div>
														</div>
														<div ravo="rainbow_fx" class="form-group">
															<label for="inputEmail3" class="col-sm-5 control-label">
																证件类型 </label>
															<div class="col-sm-4">
																<select data-role="multiselect" id="CERT_TP" class=""
																	name="CERT_TP" data-async="true"
																	data-url="${ctx}/sys/dict/selectData?type=SIGN_CERT_TP"
																	blank-item="true" data-max-height="300">
																</select>
															</div>
														</div>
													</div>
													<div class="col-md-6 column">

														<div ravo="rainbow_fx" class="form-group">
															<label for="inputEmail3"
																class="col-sm-3 control-label control-label">
																账户名</label>
															<div class="col-sm-4">
																<input type="text" class="form-control" placeholder=""
																	id="ACCT_NAME" name="ACCT_NAME">
															</div>
														</div>
														<div ravo="rainbow_fx" class="form-group">
															<label for="inputEmail3" class="col-sm-3 control-label">
																证件号码</label>
															<div class="col-sm-4">
																<input type="text" class="form-control" placeholder=""
																	id="CERT_NO" name="CERT_NO">
															</div>
														</div>
													</div>
												</div>
												<div ravo="rainbow_fx_layout" class="row clearfix">
													<div class="col-md-6 column">
														<div ravo="rainbow_fx" class="form-group">
															<div class="col-md-5 column"></div>
															<div class="col-md-4 column">
																<shiro:haspermission name="anno">
																	<button ravo="rainbow_fx" type="button"
																		class="btn btn-info" contenteditable="false"
																		id="addBtn">提交</button>
																</shiro:haspermission>
															</div>
															<div class="col-md-3 column">
																<shiro:haspermission name="anno">
																	<button ravo="rainbow_fx" type="button"
																		class="btn btn-default" contenteditable="false"
																		id="cancelBtn">关闭</button>
																</shiro:haspermission>
															</div>
														</div>
													</div>
													<div class="col-md-6 column">
														<div ravo="rainbow_fx_layout" class="row clearfix">
														</div>
													</div>
												</div>

											</div>
										</div>
										<div ravo="rainbow_fx_layout_panel"
											class="panel panel-default">
											<div class="panel-heading">
												<div ravo="rainbow_fx_bj">
													<h4 contenteditable="false">返回信息</h4>
												</div>
											</div>
											<div class="panel-body" contenteditable="false">
												<div ravo="rainbow_fx_layout" class="row clearfix">
													<div class="col-md-6 column">
														<div ravo="rainbow_fx" class="form-group">
															<label for="inputEmail3" class="col-sm-5 control-label">
																状态 </label>
															<div class="col-sm-4">
																<select data-role="multiselect" id="STAT" class=""
																	name="STAT" data-async="true" blank-item="true"
																	data-max-height="300">
																	<option value="">未知</option>
																	<option value="S">成功</option>
																	<option value="F">失败</option>
																</select>
															</div>
														</div>
														<div ravo="rainbow_fx" class="form-group" style="display:none;">
															<label for="inputEmail3" class="col-sm-5 control-label"
																style="visibility: visible"> 渠道 </label>
															<div class="col-sm-4  btn-group">
																<select data-role="multiselect" id="CHNL_NO2" class=""
																	name="CHNL_NO2" blank-item="true" data-async="true"
																	data-max-height="300"
																	data-url="${ctx}/comp/sign/test/signFuncCust/getChnlNo">
																</select>
															</div>
														</div>
													</div>
													<div class="col-md-6 column"></div>
												</div>
												<div ravo="rainbow_fx_layout" class="row clearfix"
													style="margin-bottom: 20px">
													<div class="col-md-12 column">
														<div ravo="rainbow_fx_layout" class="row clearfix">
															<label for="inputEmail3"
																class="col-sm-1 control-label control-label"
																style="visibility: visible; margin-left: 12%">签约信息</label>
															<div class="col-sm-7">
																<table id="table" data-toggle="table"
																	data-first-load="false" data-click-to-select="true"
																	data-show-export="false" data-show-refresh="false"
																	data-show-toggle="false" data-show-columns="false"
																	data-pagination="false" data-search="false"
																	data-method="post" data-undefined-text="**"
																	data-height="300"
																	data-content-type="application/x-www-form-urlencoded"
																	ravo="rainbow_fx_bj"
																	class="table table-hover table-bordered table-condensed"
																	data-single-select="true">
																	<thead style="">
																		<tr>
																			<th data-field="CHNL_NO">渠道</th>
																			<th data-field="LIM_FLG">限额类型</th>
																			<th data-field="LIM_NUM">限制笔数</th>
																			<th data-field="LIM_AMT">限制额度</th>
																		</tr>
																	</thead>
																</table>
															</div>
														</div>
													</div>
												</div>
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
	</div>
</body>
</html>
