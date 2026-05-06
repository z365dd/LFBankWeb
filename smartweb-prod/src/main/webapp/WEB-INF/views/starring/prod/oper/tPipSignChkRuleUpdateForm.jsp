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
	src="<%=basePath%>/b_base/views/starring/prod/oper/tPipSignChkRuleUpdateForm.js"
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
	<div class="">
		<div ravo="rainbow_fx_layout" class="row clearfix">
			<div class="col-md-12 column">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal breadcrumb"
					pourl="" id="tPipSignChkRuleForm" style="margin-top: 10px;">
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">
									规则ID </label>
								<div class="col-sm-4">
									<input id="ruleId" name="ruleId" class="form-control "
										maxlength="64" check-empty="true" readonly="readonly">
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">
									规则描述 </label>
								<div class="col-sm-4">
									<input id="ruleDesc" name="ruleDesc" class="form-control "
										maxlength="360" check-empty="true">
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">
									签约对账类型 </label>
								<div class="col-sm-4">
<%--									<select data-role="multiselect" id="signChkTp" class=""--%>
<%--										name="signChkTp" check-empty="true" data-bv-notempty="true"--%>
<%--										data-bv-notempty-message="选项不能为空!" blank-item="true"--%>
<%--										blank-text="--请选择--">--%>
<%--										<option value="01">不核对</option>--%>
<%--										<option value="02">银行为准</option>--%>
<%--										<option value="03">第三方核对</option>--%>
<%--									</select>--%>
									<select data-role="multiselect" id="signChkTp" name="signChkTp" data-url="${ctx}/sys/dict/selectData?type=SIGN_CHK_TP" data-max-height="300" blank-item="false" data-async="true"
											data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="signChkTp">
									</select>
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix" id="chkDiv">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx_layout" class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">
									签约对账发起方类型 </label>
								<div class="col-sm-4">
<%--									<select data-role="multiselect" id="signChkSndTp" class=""--%>
<%--										name="signChkSndTp" check-empty="true" data-bv-notempty="true"--%>
<%--										data-bv-notempty-message="选项不能为空!" blank-item="true"--%>
<%--										blank-text="--请选择--">--%>
<%--										<option value="01">自动</option>--%>
<%--										<option value="02">渠道发起</option>--%>
<%--									</select>--%>
									<select data-role="multiselect" id="signChkSndTp" name="signChkSndTp" data-url="${ctx}/sys/dict/selectData?type=SIGN_CHK_SND_TP" data-max-height="300" blank-item="false"
											data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="signChkSndTp">
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix" id="autoDiv">
								<div class="col-md-12 column">
									<div ravo="rainbow_fx" class="form-group">
										<label class="control-label col-sm-2 control-label"
											style="visibility: visible"> 自动对账开始时间 </label>
										<div class="input-group-sm   col-sm-2">
											<input type="text" readonly="readonly" maxlength="20"
												class="form-control input-mini Wdate" value=""
												datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
												datetime-min-date="" datetime-max-date=""
												datetime-is-show-clear="true" datetime-is-show-week="true"
												datetime-is-show-today="true" datetime-default-value=""
												datetime-value-fmt="HHmmss"
												data-link-field="val_autoChkStrTime" id="autoChkStrTime"
												onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: false,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('autoChkStrTime');}});"
												onchange="writeDateValue('autoChkStrTime');"> <input
												type="hidden" id="val_autoChkStrTime" value=""
												name="autoChkStrTime">
										</div>
									</div>

									<div ravo="rainbow_fx_layout" class="row clearfix"
										id="autoDivTwo">
										<div class="col-md-12 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-2 control-label">
													推送对账结果文件标志 </label>
												<div class="col-sm-4">
<%--													<select data-role="multiselect" id="chkRsltFileFlg"--%>
<%--														class="" name="chkRsltFileFlg" check-empty="true"--%>
<%--														data-bv-notempty="true" data-bv-notempty-message="选项不能为空!"--%>
<%--														blank-item="true" blank-text="--请选择--">--%>
<%--														<option value="">请选择</option>--%>
<%--														<option value="02">文件与通知报文</option>--%>
<%--													</select>--%>
													<select data-role="multiselect" id="chkRsltFileFlg" name="chkRsltFileFlg" data-url="${ctx}/sys/dict/selectData?type=CHK_RSLT_FILE_FLG" data-max-height="300" blank-item="true"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="chkRsltFileFlg">
													</select>
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-2 control-label">
													推送对账结果文件名称 </label>
												<div class="col-sm-4">
													<input id="chkRsltFileName" name="chkRsltFileName"
														class="form-control " maxlength="64">
												</div>
											</div>
										</div>
									</div>


									<div ravo="rainbow_fx_layout" class="row clearfix"
										id="autoDivThree">
										<div class="col-md-12 column">

											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-2 control-label">
													第三方文件获取类型 </label>
												<div class="col-sm-4">
<%--													<select data-role="multiselect" id="othFileGetTp" class=""--%>
<%--														name="othFileGetTp" check-empty="true"--%>
<%--														data-bv-notempty="true" data-bv-notempty-message="选项不能为空!"--%>
<%--														blank-item="true" blank-text="--请选择--">--%>
<%--														<option value="">请选择</option>--%>
<%--														<option value="03">通知报文</option>--%>
<%--													</select>--%>
													<select data-role="multiselect" id="othFileGetTp" name="othFileGetTp" data-url="${ctx}/sys/dict/selectData?type=OTH_FILE_GET_TP" data-max-height="300" blank-item="true"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="othFileGetTp">
													</select>
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-2 control-label">
													第三方对账文件名称 </label>
												<div class="col-sm-4">
													<input id="othChkFileName" name="othChkFileName"
														class="form-control " maxlength="64">
												</div>
											</div>
										</div>
									</div>

								</div>

							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-2 column"></div>
						<div class="col-md-2 column">
							<shiro:haspermission name="anno">
								<button ravo="rainbow_fx" type="button" class="btn btn-info"
									contenteditable="false" id="saveBtn">保存</button>
							</shiro:haspermission>
						</div>
						<div class="col-md-2 column">
							<shiro:haspermission name="anno">
								<button ravo="rainbow_fx" type="button" class="btn btn-default"
									contenteditable="false" id="cancleBtn">返回</button>
							</shiro:haspermission>
						</div>
						<div class="col-md-2 column"></div>
						<div class="col-md-2 column"></div>
						<div class="col-md-2 column"></div>
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
