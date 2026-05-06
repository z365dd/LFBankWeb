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
<script type="text/javascript" src="<%=basePath%>/b_base/jqueryForm.js"
	charset="utf-8"></script>
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
	src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/locale/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/tableExport.js"></script>
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

<!-- UEditor -->
<script src="${ctxStatic}/ueditor/ueditor.config.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/ueditor/ueditor.all.min.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/ueditor/ueditor.parse.min.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"
	type="text/javascript"></script>

<!-- FontIcon -->
<link rel="stylesheet"
	href="${ctxStatic}/mainframe/fonticon/iconfont.css">

<script type="text/javascript"
	src="<%=basePath%>/b_base/common/smartweb.js"></script>

<!-- check -->
<script type="text/javascript"
	src="<%=basePath%>/b_base/common/check.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/b_base/common/formCheck.js"></script>
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/b_base/common/formCheck.css" />

<!-- Self reference JS-->
<script type="text/javascript"
	src="<%=basePath%>/b_base/views/starring/comp/sign/test/signFuncCustMsgList.js"
	charset="utf-8"></script>

<!-- change skin -->
<link
	href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css"
	type="text/css" rel="stylesheet" />

<title>Insert title here</title>
</head>
<body>
	<div id="messageBox" class="alert alert-success hide">
		<button data-dismiss="alert" class="close">×</button>
		<span id="messageContent">操作提示信息</span>
	</div>
	<!-- view start -->
	<div class="bigBox">
		<div ravo="rainbow_fx_layout" class="row clearfix">
			<div class="col-md-12 column">
				<div ravo="rainbow_fx_layout_tab" class="tabbable" id="navigations">
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
							class="" aria-expanded="false" id="tab1" name="tab1">
								客户签约信息查询 </a></li>
						<li class=""><a href="#panel2" data-toggle="tab"
							aria-expanded="true" class="" id="tab2" name="tab2"> 客户签约详情</a>
						</li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane in active" id="panel1">
							<div id="messageBox" class="alert alert-success hide" style="">
								<button data-dismiss="alert" class="close">×</button>
								<span id="messageContent"> 操作提示信息 </span>
							</div>
							<form ravo="rainbow_fx_layout_bd" class="form-horizontal"
								pourl="" id="formId_350660">
								<div ravo="rainbow_fx_layout" class="row clearfix"
									style="margin-top: 20px;">
									<div class="col-md-5 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label"
												style="visibility: visible"> 签约协议类型 </label>
											<div class="col-sm-4  btn-group">
												<select data-role="multiselect" id="SIGN_PROT_TP_ID"
													name="SIGN_PROT_TP_ID" class=""
													data-url="${ctx}/comp/sign/test/signFuncCust/getSignTpPara"
													data-bv-="true" data-max-height="300" data-enable-filtering="true" data-filter-placeholder="搜索">
												</select>
											</div>
										</div>
									</div>
									<div class="col-md-5 column"></div>
									<div class="col-md-2 column"></div>
								</div>
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-5 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3"
												class="col-sm-5 control-label control-label"
												style="visibility: visible"> 签约协议号 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="SIGN_PROT_NO" name="SIGN_PROT_NO">
											</div>
										</div>
									</div>
									<div class="col-md-5 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-3 control-label"
												style="visibility: visible"> 签约状态 </label>
											<div class="col-sm-4  btn-group">
												<select data-role="multiselect" id="SIGN_STAT" class=""
													name="SIGN_STAT" data-max-height="300" checkbtn="SIGN_STAT">
													<option value="A">全部</option>
													<option value="00">已签约</option>
													<option value="10">已解约</option>
													<option value="20">待生效</option>
												</select>
											</div>
										</div>
									</div>
									<div class="col-md-2 column"></div>
								</div>
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-5 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3"
												class="col-sm-5 control-label control-label"
												style="visibility: visible"> 签约账/卡号 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="ACCT" name="ACCT">
											</div>
										</div>
									</div>
									<div class="col-md-5 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3"
												class="col-sm-3 control-label control-label"
												style="visibility: visible"> 第三方客户号 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="OTH_CUST_NO" name="OTH_CUST_NO">
											</div>
										</div>
									</div>
									<div class="col-md-2 column">
										<div ravo="rainbow_fx" class="form-group">
											<div class="input-group-sm  col-sm-3">
												<shiro:haspermission name="anno">
													<button ravo="rainbow_fx" type="button"
														class="btn btn-info pull-left" contenteditable="false"
														id="qryBtn" name="qryBtn">查询</button>
												</shiro:haspermission>
											</div>
										</div>
									</div>
								</div>
								<!-- <div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label class="control-label col-sm-3 control-label">
											交易开始时间
										</label>
										<div class="input-group-sm  col-sm-4">
											<input type="text" readonly="readonly" maxlength="20" class="form-control input-mini Wdate"
											value="" datetime-skin="twoer" datetime-date-fmt="HH:mm:ss" datetime-min-date=""
											datetime-max-date="" datetime-is-show-clear="true" datetime-is-show-week="true"
											datetime-is-show-today="true" datetime-default-value="" datetime-value-fmt="HHmmss"
											data-link-field="val_SIGN_STR_DATE" id="SIGN_STR_DATE" onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('SIGN_STR_DATE');}});"
											onchange="writeDateValue('SIGN_STR_DATE');" check-time-empty="true">
											<input type="hidden" id="val_SIGN_STR_DATE" value="" name="SIGN_STR_DATE">
										</div>
									</div>
								</div>
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label class="control-label col-sm-4 control-label">
											交易结束时间
										</label>
										<div class="input-group-sm  col-sm-4">
											<input type="text" readonly="readonly" maxlength="20" class="form-control input-mini Wdate"
											value="" datetime-skin="twoer" datetime-date-fmt="HH:mm:ss" datetime-min-date=""
											datetime-max-date="" datetime-is-show-clear="true" datetime-is-show-week="true"
											datetime-is-show-today="true" datetime-default-value="" datetime-value-fmt="HHmmss"
											data-link-field="val_SIGN_END_DATE" id="SIGN_END_DATE" onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('SIGN_END_DATE');}});"
											onchange="writeDateValue('SIGN_END_DATE');" check-time-empty="true">
											<input type="hidden" id="val_SIGN_END_DATE" value="" name="SIGN_END_DATE">
										</div>
									</div>
								</div>
							</div> -->
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-sm-12">
										<table id="table" data-toggle="table" data-first-load="false"
											data-url="${ctx}/comp/sign/test/signFuncCust/qry"
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
													<th data-field="SIGN_PROT_TP_ID">签约协议类型</th>
													<th data-field="SIGN_PROT_NO">签约协议号</th>
													<th data-field="OTH_CUST_NO">第三方客户号</th>
													<th data-field="ACCT_NAME">账户名称</th>
													<th data-field="ACCT">账/卡号</th>
													<th data-field="PHONE_NO">联系电话</th>
													<th data-field="SIGN_STAT">状态</th>
													<th data-field="ERR_TP">差错类型</th>
													<th data-field="ACTION">操作</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
							</form>
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
