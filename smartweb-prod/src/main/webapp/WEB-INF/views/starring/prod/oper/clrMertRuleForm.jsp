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
	src="<%=basePath%>/b_base/views/starring/prod/oper/clrMertRuleForm.js"
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
			<div class="col-md-12 column" id="all">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
					id="tpipClrMertRuleForm">
					<div ravo="rainbow_fx_layout" class="row clearfix"
						style="margin-top: 15px">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3"
									class="col-sm-2 control-label control-label"> 清算规则编号  <font color="red">*</font></label>
								<div class="col-sm-2">
									<input type="text" class="form-control" placeholder=""
										id="ruleId" name="ruleId" readonly="readonly" check-empty="true">
								</div>
							</div>
						</div>
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3"
									class="col-sm-2 control-label control-label"> 规则名称  <font color="red">*</font></label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="ruleName" name="ruleName" check-empty="true">
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">
									清算方式  <font color="red">*</font></label>
								<div class="col-sm-4">
									<select data-role="multiselect" id="clrMeth" name="clrMeth"
										data-async="true" class="" checkbtn="clrMeth">
										<option value="00">不清算</option>
										<option value="01">本金清算</option>
									</select>
									<%-- <select data-role="multiselect" id="clrMeth" name="clrMeth" data-url="${ctx}/sys/dict/selectData?type=CLR_METH" data-max-height="300" blank-item="false" data-async="true"
											data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="clrMeth">
									</select> --%>
								</div>
							</div>
						</div>
						<div class="col-md-12 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">
										清算维度类型 </label>
									<div class="col-sm-4">
<%--										<select data-role="multiselect" id="clrDimTp"--%>
<%--											name="clrDimTp" class="" data-async="true">--%>
<%--											<option value="01">单商户清算</option>--%>
<%--											<option value="11">多商户独立清算</option>--%>
<%--										</select>--%>
										<select data-role="multiselect" id="clrDimTp" name="clrDimTp" data-url="${ctx}/sys/dict/selectData?type=CLR_DIM_TP" data-max-height="300" blank-item="false" data-async="true"
												data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="clrDimTp">
										</select>
									</div>
								</div>
							</div>
					</div>
					<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
						id="clrCtrlDiv">
						<div class="panel-heading">
							<div ravo="rainbow_fx_bj">
								<h5 contenteditable="false">清算控制参数</h5>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix"
							style="margin-top: 10px">

							<div class="col-md-12 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">
										批次是否合并清算 </label>
									<div class="col-sm-4">
<%--										<select data-role="multiselect" id="batProcFlg"--%>
<%--											name="batProcFlg" class="" data-async="true">--%>
<%--											<option value="1">联机清算</option>--%>
<%--											<option value="2">联机+批量合并清算</option>--%>
<%--										</select>--%>
										<select data-role="multiselect" id="batProcFlg" name="batProcFlg" data-url="${ctx}/sys/dict/selectData?type=BAT_PROC_FLG" data-max-height="300" blank-item="false" data-async="true"
												data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="batProcFlg">
										</select>
									</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-12 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">
										清算发起方标志 </label>
									<div class="col-sm-4">
<%--										<select data-role="multiselect" multiple="multiple"--%>
<%--											id="clrSndGrpFlg" name="clrSndGrpFlg"--%>
<%--											data-all-selected-text="已选全部" data-n-selected-text="个已选"--%>
<%--											class="" data-async="true">--%>
<%--											<option value="1">自动清算</option>--%>
<%--											<option value="2">柜面发起清算</option>--%>
<%--										</select>--%>
										<div class="col-sm-8">
											<select data-role="multiselect" id="clrSndGrpFlg" name="clrSndGrpFlg" data-max-height="300"  data-button-width="300" data-url="${ctx}/sys/dict/selectData?type=CLR_SND_GRP_FLG"
													data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true"
													data-filter-placeholder="搜索" multiple="multiple" data-select-all-text="全部选择" data-include-select-all-option="true" data-non-selected-text="请选择"
													data-all-selected-text="已选全部" data-n-selected-text="个已选"
													data-number-displayed="2" multiple="multiple" checkbtn="clrSndGrpFlg">
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">
										清算周期类型 </label>
									<div class="col-sm-4">
<%--										<select data-role="multiselect" id="clrCycTp"--%>
<%--											name="clrCycTp" class="" data-async="true">--%>
<%--											<option value="T">天</option>--%>
<%--											<!-- <option value="W">周</option>--%>
<%--										<option value="M">月</option> -->--%>
<%--										</select>--%>
										<select data-role="multiselect" id="clrCycTp" name="clrCycTp" data-url="${ctx}/sys/dict/selectData?type=CLR_CYC_TP" data-max-height="300" blank-item="false" data-async="true"
												data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="clrCycTp">
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">
										清算周期 </label>
									<div class="col-sm-4">
<%--										<select data-role="multiselect" id="clrCyc" name="clrCyc"--%>
<%--											class="" data-async="true">--%>
<%--											<option value="0">当日清算</option>--%>
<%--											<option value="1">清算最大日期T-1天</option>--%>
<%--											<option value="2">清算最大日期T-2天</option>--%>
<%--										</select>--%>
										<select data-role="multiselect" id="clrCyc" name="clrCyc" data-url="${ctx}/sys/dict/selectData?type=CLR_CYC" data-max-height="300" blank-item="false" data-async="true"
												data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="clrCyc">
										</select>
									</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label id="LAB_strTime" for="inputEmail3"
										class="col-sm-4 control-label"> 清算开始时间 </label>
									<div class="input-group-sm col-sm-4">
										<input type="text" readonly="readonly" maxlength="20"
											class="form-control input-mini Wdate" value=""
											datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
											datetime-min-date="" datetime-max-date=""
											datetime-is-show-clear="true" datetime-is-show-week="true"
											datetime-is-show-today="false" datetime-default-value=""
											datetime-value-fmt="HHmmss" data-link-field="val_strTime"
											id="strTime"
											onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: false,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('strTime');}});"
											onchange="writeDateValue('strTime');"> <input
											type="hidden" id="val_strTime" name="strTime" value="">
									</div>
								</div>
							</div>
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label id="LAB_endTime" for="inputEmail3"
										class="col-sm-4 control-label"> 清算终止时间 </label>
									<div class="input-group-sm col-sm-4">
										<input type="text" readonly="readonly" maxlength="20"
											class="form-control input-mini Wdate" value=""
											datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
											datetime-min-date="" datetime-max-date=""
											datetime-is-show-clear="true" datetime-is-show-week="true"
											datetime-is-show-today="false" datetime-default-value=""
											datetime-value-fmt="HHmmss" data-link-field="val_endTime"
											id="endTime"
											onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: false,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('endTime');}});"
											onchange="writeDateValue('endTime');"> <input
											type="hidden" id="val_endTime" name="endTime" value="">
									</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-12 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3"
										class="col-sm-2 control-label control-label">客户化bean名</label>
									<div class="col-sm-2">
										<input type="text" class="form-control" placeholder=""
											id="bean" name="bean">
									</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-12 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3"
										class="col-sm-2 control-label control-label">自动清算渠道号</label>
									<div class="col-sm-2">
										<input type="text" class="form-control" placeholder=""
											id="autoClrChnlNo" name="autoClrChnlNo">
									</div>
								</div>
							</div>
						</div>
					</div>

					<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
						id="acctDiv">
						<div class="panel-heading">
							<div ravo="rainbow_fx_bj">
								<h5 contenteditable="false">清算账号参数</h5>
							</div>
						</div>
						
						<div ravo="rainbow_fx_layout" class="row clearfix"
							style="margin-top: 10px">
							<div class="col-md-12 column">
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3"
											class="col-sm-4 control-label control-label"> 商户号 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												id="mertNo" name="mertNo">
										</div>
									</div>
								</div>
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3"
											class="col-sm-4 control-label control-label"> 商户名称</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												id="mertName" name="mertName">
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-12 column">
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3"
											class="col-sm-4 control-label control-label"> 上级商户号 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												id="upMertNo" name="upMertNo">
										</div>
									</div>
								</div>
								<div class="col-md-6 column">
								</div>
							</div>
						</div>
						
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-12 column">
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3"
											class="col-sm-4 control-label control-label"> 单位账号 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												id="entrAcct" name="entrAcct">
										</div>
									</div>
								</div>
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3"
											class="col-sm-4 control-label control-label"> 单位账号名称 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												id="entrAcctName" name="entrAcctName">
										</div>
									</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-12 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">
										单位账号所属行标志 </label>
									<div class="col-sm-4">
<%--										<select data-role="multiselect" id="entrAcctBankFlg"--%>
<%--											name="entrAcctBankFlg" class="" dcheckbtn="entrAcctBankFlg"--%>
<%--											ata-async="true">--%>
<%--											<option value="00">行内</option>--%>
<%--											<option value="01">行外</option>--%>
<%--										</select>--%>
										<select data-role="multiselect" id="entrAcctBankFlg" name="entrAcctBankFlg" data-url="${ctx}/sys/dict/selectData?type=ENTR_ACCT_BANK_FLG" data-max-height="300" blank-item="false" data-async="true"
												data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="entrAcctBankFlg">
										</select>
									</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-12 column">
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3"
											class="col-sm-4 control-label control-label"> 单位账号行号 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												id="entrAcctBank" name="entrAcctBank">
										</div>
									</div>
								</div>
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3"
											class="col-sm-4 control-label control-label"> 单位账号银行名称
										</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												id="entrAcctBankName" name="entrAcctBankName">
										</div>
									</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-12 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">
										过渡账户标志 </label>
									<div class="col-sm-4">
<%--										<select data-role="multiselect" id="intrmAcctFlg"--%>
<%--											name="intrmAcctFlg" class="" dcheckbtn="intrmAcctFlg"--%>
<%--											ata-async="true">--%>
<%--											<option value="N">否</option>--%>
<%--											<option value="Y">是</option>--%>
<%--										</select>--%>
										<select data-role="multiselect" id="intrmAcctFlg" name="intrmAcctFlg" data-url="${ctx}/sys/dict/selectData?type=INTRM_ACCT_FLG" data-max-height="300" blank-item="false" data-async="true"
												data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="intrmAcctFlg">
										</select>
									</div>
								</div>
							</div>
						</div>
						
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-12 column">
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3"
											class="col-sm-4 control-label control-label"> 过渡账户 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												id="intrmAcct" name="intrmAcct">
										</div>
									</div>
								</div>
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3"
											class="col-sm-4 control-label control-label"> 过渡账户名称 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												id="intrmAcctName" name="intrmAcctName">
										</div>
									</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-12 column">
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3"
											class="col-sm-4 control-label control-label"> 摘要码 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												id="sumCode" name="sumCode">
										</div>
									</div>
								</div>
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3"
											class="col-sm-4 control-label control-label"> 摘要描述 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												id="sumDesc" name="sumDesc">
										</div>
									</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-12 column">
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3"
											class="col-sm-4 control-label control-label"> 过账摘要码 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												id="postingSumCode" name="postingSumCode">
										</div>
									</div>
								</div>
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3"
											class="col-sm-4 control-label control-label"> 过账摘要码描述 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												id="postingSumDesc" name="postingSumDesc">
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-12 column">
								<div class="col-md-8 column"></div>
								<div class="col-md-4 column">
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
							style="margin-top: 20px">
							<div class="col-md-12 column">
							<div class="col-md-2 column">
							</div>
							<div class="col-md-7 column">
								<table id="table" class="table table-hover"
									data-toggle="table" data-first-load="false"
									data-click-to-select="true" data-show-export="false"
									data-show-refresh="false" data-show-toggle="false"
									data-show-columns="false" data-pagination="true"
									data-search="false"
									data-method="post" data-undefined-text="**" data-height="200"
									data-content-type="application/x-www-form-urlencoded"
									ravo="rainbow_fx_bj" class="table table-hover"
									data-checkbox-header="false" data-sortable="false"
									data-striped="true" data-single-select="true">
									<thead style="">
										<tr>
											<th data-field=mertNo>商户号</th>
											<th data-field="mertName">商户名称</th>
											<th data-field="upMertNo">上级商户号</th>
											<th data-field="action">操作</th>
										</tr>
									</thead>
								</table>
							</div>
							
							<div class="col-md-3 column">
							</div>
							</div>
						</div>
					</div>

					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-3 column"></div>
						<div class="col-md-3 column">
							<shiro:haspermission name="anno">
								<button ravo="rainbow_fx" type="button" class="btn btn-default"
									contenteditable="false" id="submit_btn">提交</button>
							</shiro:haspermission>
						</div>
						<div class="col-md-3 column">
							<shiro:haspermission name="anno">
								<button ravo="rainbow_fx" type="button" class="btn btn-default"
									contenteditable="false" id="close_btn">关闭</button>
							</shiro:haspermission>
						</div>
						<div class="col-md-3 column"></div>
					</div>
				</form>
			</div>
		</div>
		<!--customer_code_beg-->

		<!--customer_code_end-->
		<!-- view end -->
</body>
</html>
