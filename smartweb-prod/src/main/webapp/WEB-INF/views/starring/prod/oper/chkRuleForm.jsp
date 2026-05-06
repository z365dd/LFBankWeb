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
	src="<%=basePath%>/b_base/views/starring/prod/oper/chkRuleForm.js"
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
					id="formId_70596">
					<div ravo="rainbow_fx_layout" class="row clearfix"
						style="margin-top: 15px">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3"
									class="col-sm-2 control-label control-label"> 对账规则编号 <font color="red">*</font></label>
								<div class="col-sm-2">
									<input type="text" class="form-control" placeholder=""
										id="ruleId" name="ruleId" disabled="true" check-empty="true">
								</div>
							</div>
						</div>
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-2  control-label">
									规则名称  <font color="red">*</font></label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										data-toggle="popover" data-container="body" id="ruleName"
										name="ruleName" check-empty="true">
								</div>
							</div> 
						</div>
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class=" col-sm-2 control-label">
									对账类型  <font color="red">*</font></label>
								<div class="col-sm-4">
<%--									<select data-role="multiselect" id="chkTp" name="chkTp"--%>
<%--										class="" data-async="true">--%>
<%--										<option value="0">不对账</option>--%>
<%--										<option value="1">两方对账</option>--%>
<%--										<option value="2">三方对账</option>--%>
<%--									</select>--%>
									<select data-role="multiselect" id="chkTp" name="chkTp" data-url="${ctx}/sys/dict/selectData?type=CHK_TP" data-max-height="300" blank-item="true" data-async="true"
											data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="chkTp">
									</select>
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
						id="chkCtrlDiv">
						<div class="panel-heading">
							<div ravo="rainbow_fx_bj" class="panel-header">
								<h5 contenteditable="false" class="">对账控制参数</h5>
							</div>
						</div>
						<div ravo="rainbow_fx_bj" class="panel-body">
							<div ravo="rainbow_fx_layout" class="row clearfix"
								id="chkParaDiv">
								<div class="col-md-12 column">
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-12 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class=" col-sm-2 control-label">
													对账发起方式 </label>
												<div class="col-sm-4">
<%--													<select data-role="multiselect" id="chkSndGrpFlg"--%>
<%--														name="chkSndGrpFlg" multiple="multiple"--%>
<%--														data-all-selected-text="已选全部" data-n-selected-text="个已选"--%>
<%--														class="" data-async="true">--%>
<%--														<option value="1">自动对账</option>--%>
<%--														<option value="2">柜面发起对账</option>--%>
<%--														<option value="3">第三方发起对账</option>--%>
<%--													</select>--%>
													<div class="col-sm-8">
														<select data-role="multiselect" id="chkSndGrpFlg" name="chkSndGrpFlg" data-max-height="300"  data-button-width="300" data-url="${ctx}/sys/dict/selectData?type=CHK_SND_GRP_FLG"
																data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true"
																data-filter-placeholder="搜索" multiple="multiple" data-select-all-text="全部选择" data-include-select-all-option="true" data-non-selected-text="请选择"
																data-all-selected-text="已选全部" data-n-selected-text="个已选"
																data-number-displayed="2" multiple="multiple" checkbtn="chkSndGrpFlg">
														</select>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class=" col-sm-4 control-label">
													对账周期类型 </label>
												<div class="col-sm-4">
<%--													<select data-role="multiselect" id="chkCycTp"--%>
<%--														name="chkCycTp" class="" data-async="true">--%>
<%--														<option value="T">天</option>--%>
<%--													</select>--%>
													<select data-role="multiselect" id="chkCycTp" name="chkCycTp" data-url="${ctx}/sys/dict/selectData?type=CHK_CYC_TP" data-max-height="300" blank-item="true" data-async="true"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="chkCycTp">
													</select>
												</div>
											</div>
										</div>
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class=" col-sm-4 control-label">
													对账周期 </label>
												<div class="col-sm-4">
<%--													<select data-role="multiselect" id="chkCyc" name="chkCyc"--%>
<%--														class="" data-async="true">--%>
<%--														<option value="0">当天对账</option>--%>
<%--														<option value="1">对账最大日期T-1天</option>--%>
<%--														<option value="2">对账最大日期T-2天</option>--%>
<%--													</select>--%>
													<select data-role="multiselect" id="chkCyc" name="chkCyc" data-url="${ctx}/sys/dict/selectData?type=CHK_CYC" data-max-height="300" blank-item="true" data-async="true"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="chkCyc">
													</select>
												</div>
											</div>
										</div>
									</div>
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label id="LAB_strTime" for="inputEmail3"
													class=" control-label col-sm-4 control-label">
													对账开始时间 </label>
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
													class=" control-label col-sm-4 control-label">
													对账终止时间 </label>
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
												<label for="inputEmail3" class=" col-sm-2 control-label">
													上日失败继续对账 </label>
												<div class="col-sm-4">
<%--													<select data-role="multiselect" id="chkSwitchFlg" name="chkSwitchFlg"--%>
<%--														data-async="true" class="">--%>
<%--														<option value="0">否</option>--%>
<%--														<option value="1">是</option>--%>
<%--													</select>--%>
													<select data-role="multiselect" id="chkSwitchFlg" name="chkSwitchFlg" data-url="${ctx}/sys/dict/selectData?type=CHK_SWITCH_FLG" data-max-height="300" blank-item="true" data-async="true"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="chkSwitchFlg">
													</select>
												</div>
											</div>
										</div>
									</div>
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-12 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class=" col-sm-2 control-label">
													自动差错处理</label>
												<div class="col-sm-4">
<%--													<select data-role="multiselect" id="errSwitchFlg" name="errSwitchFlg"--%>
<%--														data-async="true" class="">--%>
<%--														<option value="0">否</option>--%>
<%--														<option value="1">是</option>--%>
<%--													</select>--%>
													<select data-role="multiselect" id="errSwitchFlg" name="errSwitchFlg" data-url="${ctx}/sys/dict/selectData?type=ERR_SWITCH_FLG" data-max-height="300" blank-item="true" data-async="true"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="errSwitchFlg">
													</select>
												</div>
											</div>
										</div>
									</div>
									<div ravo="rainbow_fx_layout" class="row clearfix"
										id="twoChkDiv">
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class=" col-sm-4 control-label">
													推送对账文件到第三方方式 </label>
												<div class="col-sm-4">
<%--													<select data-role="multiselect" id="chkRsltPushFlg"--%>
<%--														name="chkRsltPushFlg" class="" data-async="true">--%>
<%--														<option value="0">不推送</option>--%>
<%--														<option value="1">文件</option>--%>
<%--														<option value="2">文件 通知报文</option>--%>
<%--													</select>--%>
													<select data-role="multiselect" id="chkRsltPushFlg" name="chkRsltPushFlg" data-url="${ctx}/sys/dict/selectData?type=CHK_RSLT_PUSH_FLG" data-max-height="300" blank-item="true" data-async="true"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="chkRsltPushFlg">
													</select>
												</div>
											</div>
										</div>
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3"
													class="col-sm-4 control-label control-label">
													推送对账文件名称 </label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="chkRsltFileName" name="chkRsltFileName"
														data-toggle="popover" data-container="body" data-placement="right"
								data-content="YYYY-当前年份   MM-当前月份  DD-当前日期  HH-当前小时  mm-当前分钟  ss-当前秒数 "
								title="自动转换字符" data-trigger="focus">
												</div>
											</div>
										</div>
									</div>

									<div ravo="rainbow_fx_layout" class="row clearfix"
										id="threeChkDiv">
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class=" col-sm-4 control-label">
													获取第三方对账文件标志 </label>
												<div class="col-sm-4">
<%--													<select data-role="multiselect" id="chkFileDownloadFlg"--%>
<%--														name="chkFileDownloadFlg" class="" data-async="true">--%>
<%--														<option value="1">文件</option>--%>
<%--														<option value="2">文件 通知报文</option>--%>
<%--													</select>--%>
													<select data-role="multiselect" id="chkFileDownloadFlg" name="chkFileDownloadFlg" data-url="${ctx}/sys/dict/selectData?type=CHK_FILE_DOWNLOAD_FLG" data-max-height="300" blank-item="true" data-async="true"
															data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="chkFileDownloadFlg">
													</select>
												</div>
											</div>
										</div>
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3"
													class="col-sm-4 control-label control-label"
													style="visibility: visible"> 获取第三方对账文件名称 </label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="othChkFileName" name="othChkFileName">
												</div>
											</div>
										</div>
									</div>
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-12 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3"
													class="col-sm-2 control-label control-label">
													客户化bean名 </label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="bean" name="bean">
												</div>
											</div>
										</div>
									</div>

									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label id="LAB_strTime" for="inputEmail3"
													class=" control-label col-sm-4 control-label">
													对账编号 </label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														data-toggle="popover" data-container="body" id="chkNo"
														name="chkNo">
												</div>
											</div>
										</div>
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label id="LAB_endTime" for="inputEmail3"
													class=" control-label col-sm-4 control-label">
													对账勾兑规则号 </label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														data-toggle="popover" data-container="body" id="chkRuleId"
														name="chkRuleId">
												</div>
											</div>
										</div>
									</div>

									<div class="col-md-12 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3"
												class="col-sm-2 control-label control-label">
												上一步骤对账规则编号 </label>
											<div class="col-sm-2">
												<input type="text" class="form-control" placeholder=""
													id="upRuleId" name="upRuleId" >
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout_panel" class="panel panel-default">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-3 column"></div>
								<div class="col-md-3 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
											id="submit_btn">提交</button>
									</shiro:haspermission>
								</div>
								<div class="col-md-3 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
											id="close_btn">关闭</button>
									</shiro:haspermission>
								</div>
								<div class="col-md-3 column"></div>
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
