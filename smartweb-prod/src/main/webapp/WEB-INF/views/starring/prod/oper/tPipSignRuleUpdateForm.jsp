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
	src="<%=basePath%>/b_base/views/starring/prod/oper/tPipSignRuleUpdateForm.js"
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
	<input id="limParaList" name="limParaList" class="form-control "
		type="hidden"></input>
	<div class="">
		<div ravo="rainbow_fx_layout" class="row clearfix">
			<div class="col-md-12 column text-left">
				<form ravo="rainbow_fx_layout_bd" class="breadcrumb form-horizontal"
					pourl="" id="tPipSignRuleForm" style="margin-top: 10px;">
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3"
									class="col-sm-2 control-label control-label"> 规则ID </label>
								<div class="col-sm-4">
									<div class="">
										<div class="col-sm-4">
											<input id="ruleId" name="ruleId" class="form-control"
												readonly="readonly">
										</div>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3"
									class="col-sm-2 control-label control-label"> 规则描述 </label>
								<div class="col-sm-8">
									<div class="">
										<div class="col-sm-4">
											<input id="ruleDesc" name="ruleDesc" class="form-control"
												maxlength="360" check-empty="true">
										</div>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">
									签约标志 </label>
								<div class="col-sm-8">
									<div class="">
										<div class="col-sm-7">
<%--											<select data-role="multiselect" id="signFlg" class=""--%>
<%--												name="signFlg" check-empty="true" data-bv-notempty="true"--%>
<%--												data-bv-notempty-message="选项不能为空!" blank-item="true"--%>
<%--												blank-text="--请选择--">--%>
<%--												<option value="Y">需要签约</option>--%>
<%--												<option value="N">不需要签约</option>--%>
<%--											</select>--%>
											<select data-role="multiselect" id="signFlg" name="signFlg" data-url="${ctx}/sys/dict/selectData?type=SIGN_FLG" data-max-height="300" blank-item="false"
													data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="signFlg">
											</select>
										</div>
									</div>
								</div>
							</div>

							<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
								id="signDiv">
								<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
									id="signTpDiv">
									<div ravo="rainbow_fx_radio" class="form-group">
										<label for="inputEmail3" class=" col-sm-2 control-label"
											style="visibility: visible"> 签约类型 </label>
										<div class="col-sm-8">
											<div class="radio-inline">
												<label style="visibility: visible"> <input
													id="signTp" type="radio" value="01" name="signTp"
													data-bv-="true" class=""> 开通签约
												</label>
											</div>
											<div class="radio-inline">
												<label style="visibility: visible"> <input
													id="signTp" type="radio" value="02" name="signTp"
													data-bv-="true" class=""> 限额签约
												</label>
											</div>
											<div class="radio-inline">
												<label style="visibility: visible"> <input
													id="signTp" type="radio" value="03" name="signTp"
													data-bv-="true" class=""> 开通和限额签约
												</label>
											</div>
										</div>
									</div>
									<div ravo="rainbow_fx_radio" class="form-group">
										<label for="inputEmail3" class=" col-sm-2 control-label">
											通知类型 </label>
										<div class="col-sm-8">
											<div class="radio-inline">
												<label style="visibility: visible"> <input
													id="noteTp" type="radio" value="01" name="noteTp"
													data-bv-="true" class=""> 银行方单独签约
												</label>
											</div>
											<div class="radio-inline">
												<label style="visibility: visible"> <input
													id="noteTp" type="radio" value="02" name="noteTp"
													data-bv-="true" class=""> 银行签约并通知委托单位
												</label>
											</div>
										</div>
									</div>
								</div>
								<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
									id="signCtrlDiv">
									<div class="panel-heading">
										<div ravo="rainbow_fx_bj">
											<h5 contenteditable="false">签约控制配置</h5>
										</div>
									</div>
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-12 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-2 control-label">
													是否校验账户信息 </label>
												<div class="col-sm-7">
<%--													<select data-role="multiselect" id="vrfyAcctNameFlg"--%>
<%--														name="vrfyAcctNameFlg" class="">--%>
<%--														<option value="N">否</option>--%>
<%--														<option value="Y">是</option>--%>
<%--													</select>--%>
													<select data-role="multiselect" id="vrfyAcctInfoFlg" name="vrfyAcctInfoFlg" data-url="${ctx}/sys/dict/selectData?type=VRFY_ACCT_INFO_FLG" data-max-height="300" blank-item="false"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="vrfyAcctInfoFlg">
													</select>
												</div>
											</div>
										</div>
									</div>
									<div ravo="rainbow_fx_layout" class="row clearfix" id="signAcctCtrlDiv">
										<div class="col-md-12 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-2 control-label">
													允许签约账户状态 </label>
												<div class="col-sm-7">
													<select data-role="multiselect" id="acctStatList" class=""
														name="acctStatList" multiple="multiple"
														data-enable-filtering="true"
														data-enable-full-value-filtering="false"
														data-enable-case-insensitive-filtering="true"
														data-filter-placeholder="搜索" data-select-all-text="全部选择"
														data-include-select-all-option="true"
														data-number-displayed="2"
														data-url="${ctx}/sys/dict/selectData?type=ACCT_STAT">
													</select> <label class="label label-warning"> (不选择，允许全部状态) </label>
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-2 control-label">
													是否校验户名 </label>
												<div class="col-sm-7">
<%--													<select data-role="multiselect" id="vrfyAcctNameFlg"--%>
<%--														name="vrfyAcctNameFlg" class="">--%>
<%--														<option value="N">否</option>--%>
<%--														<option value="Y">是</option>--%>
<%--													</select>--%>
													<select data-role="multiselect" id="vrfyAcctNameFlg" name="vrfyAcctNameFlg" data-url="${ctx}/sys/dict/selectData?type=VRFY_ACCT_NAME_FLG" data-max-height="300" blank-item="false"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="vrfyAcctNameFlg">
													</select>
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class=" col-sm-2 control-label">
													是否校验证件 </label>
												<div class="col-sm-7">
<%--													<select data-role="multiselect" id="vrfyCertFlg"--%>
<%--														name="vrfyCertFlg" class="">--%>
<%--														<option value="N">否</option>--%>
<%--														<option value="Y">是</option>--%>
<%--													</select>--%>
													<select data-role="multiselect" id="vrfyCertFlg" name="vrfyCertFlg" data-url="${ctx}/sys/dict/selectData?type=VRFY_CERT_FLG" data-max-height="300" blank-item="false"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="vrfyCertFlg">
													</select>
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class=" col-sm-2 control-label">
													是否校验手机号码 </label>
												<div class="col-sm-7">
<%--													<select data-role="multiselect" id="vrfyPhoneFlg"--%>
<%--														name="vrfyPhoneFlg" class="">--%>
<%--														<option value="N">否</option>--%>
<%--														<option value="Y">是</option>--%>
<%--													</select>--%>
													<select data-role="multiselect" id="vrfyPhoneFlg" name="vrfyPhoneFlg" data-url="${ctx}/sys/dict/selectData?type=VRFY_PHONE_FLG" data-max-height="300" blank-item="false"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="vrfyPhoneFlg">
													</select>
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class=" col-sm-2 control-label">
													是否检验开户机构 </label>
												<div class="col-sm-7">
<%--													<select data-role="multiselect" id="vrfyModBrchFlg"--%>
<%--														name="vrfyModBrchFlg" class="">--%>
<%--														<option value="N">否</option>--%>
<%--														<option value="Y">是</option>--%>
<%--													</select>--%>
													<select data-role="multiselect" id="vrfyOpenAcctBrchFlg" name="vrfyOpenAcctBrchFlg" data-url="${ctx}/sys/dict/selectData?type=VRFY_OPEN_ACCT_BRCH_FLG" data-max-height="300" blank-item="false"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="vrfyOpenAcctBrchFlg">
													</select>
												</div>
											</div>
										</div>
									</div>
									<div ravo="rainbow_fx_layout" class="row clearfix" >
										<div class="col-md-12 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class=" col-sm-2 control-label">
													修改签约机构控制 </label>
												<div class="col-sm-7">
<%--													<select data-role="multiselect" id="vrfyModBrchFlg"--%>
<%--														name="vrfyModBrchFlg" class="">--%>
<%--														<option value="N">否</option>--%>
<%--														<option value="Y">是</option>--%>
<%--													</select>--%>
													<select data-role="multiselect" id="vrfyModBrchFlg" name="vrfyModBrchFlg" data-url="${ctx}/sys/dict/selectData?type=VRFY_MOD_BRCH_FLG" data-max-height="300" blank-item="false"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="vrfyModBrchFlg">
													</select>
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class=" col-sm-2 control-label">
													解约机构控制 </label>
												<div class="col-sm-7">
<%--													<select data-role="multiselect" id="vrfyCanclBrchFlg"--%>
<%--														name="vrfyCanclBrchFlg" class="">--%>
<%--														<option value="N">否</option>--%>
<%--														<option value="Y">是</option>--%>
<%--													</select>--%>
													<select data-role="multiselect" id="vrfyCanclBrchFlg" name="vrfyCanclBrchFlg" data-url="${ctx}/sys/dict/selectData?type=VRFY_CANCL_BRCH_FLG" data-max-height="300" blank-item="false"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="vrfyCanclBrchFlg">
													</select>
												</div>
											</div>
										</div>
									</div>
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-12 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-2 control-label">
													是否发送动账通知 </label>
												<div class="col-sm-7">
													<select data-role="multiselect" id="acctJrnlNoteFlg" name="acctJrnlNoteFlg" data-url="${ctx}/sys/dict/selectData?type=VRFY_ACCT_INFO_FLG" data-max-height="300" blank-item="false"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="vrfyAcctInfoFlg">
													</select>
												</div>
											</div>
										</div>
									</div>
									<div ravo="rainbow_fx_layout" class="row clearfix" id="acctJrnlNotCtrlDiv">
										<div class="col-md-12 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-2 control-label">
													组件号 </label>
												<div class="col-sm-7">
													<select data-role="multiselect" id="compNo" name="compNo"  data-max-height="300" blank-item="false"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="compNo">
													</select>
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-2 control-label">
													服务码 </label>
												<div class="col-sm-7">
													<select data-role="multiselect" id="modlSvcCode" name="modlSvcCode"  data-max-height="300" blank-item="false" data-non-selected-text="--请先选择组件号--"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="modlSvcCode">
													</select>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
									id="limParaDiv">
									<div class="panel-heading">
										<div ravo="rainbow_fx_bj">
											<h5 contenteditable="false">默认限额配置</h5>
										</div>
									</div>
									<div ravo="rainbow_fx_layout" class="row clearfix" id="limDiv">
										<div class="col-md-12 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-2 control-label">
													渠道 </label>
												<div class="col-sm-7">
													<select data-role="multiselect" id="chnlNo" name="chnlNo"
														class="" data-enable-filtering="true"
														data-enable-full-value-filtering="false"
														data-enable-case-insensitive-filtering="true"
														data-filter-placeholder="搜索" data-select-all-text="全部选择"
														data-include-select-all-option="true"
														data-max-height="300" data-number-displayed="2">
													</select>
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-2 control-label">
													限额类型 </label>
												<div class="col-sm-7">
<%--													<select data-role="multiselect" id="limTp" name="limTp"--%>
<%--														class="">--%>
<%--														<option value="">--请选择--</option>--%>
<%--														<option value="00">单笔</option>--%>
<%--														<option value="01">日</option>--%>
<%--														<option value="02">旬</option>--%>
<%--														<option value="03">月</option>--%>
<%--														<option value="04">季</option>--%>
<%--														<option value="05">年</option>--%>
<%--													</select>--%>
													<select data-role="multiselect" id="limTp" name="limTp" data-url="${ctx}/sys/dict/selectData?type=LIM_TP" data-max-height="300" blank-item="true"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索">
													</select>
												</div>
											</div>
											<div ravo="rainbow_fx_layout" class="row clearfix">
												<div class="col-md-1 column"></div>
												<div class="col-md-3 column">
													<div ravo="rainbow_fx" class="form-group">
														<label for="inputEmail3"
															class="col-sm-4 control-label control-label">
															最大限制笔数 </label>
														<div class="col-sm-4">
															<input type="number" class="form-control" id="limNum"
																name="limNum">
														</div>
													</div>
												</div>
												<div class="col-md-1 column"></div>
												<div class="col-md-3 column">
													<div ravo="rainbow_fx" class="form-group">
														<label for="inputEmail3"
															class="col-sm-4 control-label control-label">
															最大限制额度 </label>
														<div class="col-sm-4">
															<input type="text" class="form-control" id="limAmt"
																name="limAmt">
														</div>
													</div>
												</div>
											</div>

											<div ravo="rainbow_fx" class="form-group">
												<div class="col-md-12 column">
													<div class="col-md-6 column"></div>
													<div class="col-md-6 column">
														<shiro:haspermission name="anno">
															<button ravo="rainbow_fx" type="button"
																class="btn btn-default" contenteditable="false"
																id="addTabBtn">增加一行</button>
														</shiro:haspermission>
														<shiro:haspermission name="anno">
															<button ravo="rainbow_fx" type="button"
																class="btn btn-default" contenteditable="false"
																id="setTabBtn">确认设置</button>
														</shiro:haspermission>
													</div>
												</div>
											</div>
											<div ravo="rainbow_fx_layout" class="row clearfix"
												style="margin-bottom: 20px; margin-top: 20px">
												<div class="col-md-12 column">
													<div ravo="rainbow_fx_layout" class="row clearfix">
														<label for="inputEmail3"
															class="col-sm-1 control-label control-label"
															style="visibility: visible; margin-left: 1%"></label>
														<div class="col-sm-7">
															<table id="limTable" ravo="rainbow_fx_bj"
																class="table table-hover" data-toggle="table"
																data-first-load="false" data-click-to-select="true"
																data-show-export="false" data-show-refresh="false"
																data-show-toggle="false" data-show-columns="false"
																data-pagination="false" data-search="false"
																data-method="post" data-undefined-text="**"
																data-height="300"
																data-content-type="application/x-www-form-urlencoded"
																data-single-select="true"
																style="border: 1px solid #9EBED7;">
																<thead style="">
																	<tr>
																		<th data-field="chnlNo" data-visible="false">渠道的值</th>
																		<th data-field="chnlNoFlg">渠道</th>
																		<th data-field="limTp" data-visible="false">
																			限额类型</th>
																		<th data-field="limTpStr">限额类型</th>
																		<th data-field="limNum">最大限制笔数</th>
																		<th data-field="limAmt">最大限制额度</th>
																		<th data-field="ACTION">操作</th>
																	</tr>
																</thead>
															</table>
														</div>
													</div>
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
