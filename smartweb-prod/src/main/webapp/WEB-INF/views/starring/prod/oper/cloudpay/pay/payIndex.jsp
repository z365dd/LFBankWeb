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
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Cache-Control" content="no-store">

<script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}';</script>

<!-- JQUERY -->
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-3.6.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-migrate-3.4.0.min.js" charset="utf-8"></script><script type="text/javascript" src="<%=basePath%>/b_base/jqueryForm.js" charset="utf-8"></script>
<!-- /JQUERY -->

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

<!-- LayoutIt bootstrap -->
<!-- table-->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.css"/>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/bootstrap-table-export.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/tableExport.js" charset="utf-8"></script>
<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/locale/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/tableExport.js"></script>
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
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/css/bootstrap-multiselect.css"/>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/jquery.multiselectcus.js"></script>
<!-- /Multiselect -->

<!-- TABS -->
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/tabs/js/bootstrap-tabs.js"></script>
<!-- /TABS -->

<!-- ystep -->
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/ystep-master/js/ystep.js"></script>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/ystep-master/css/ystep.css"/>
<!-- /ystep -->

<!-- JBox -->
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.src.js" type="text/javascript"></script>

<!-- UEditor -->
<script src="${ctxStatic}/ueditor/ueditor.config.js" type="text/javascript"></script>
<script src="${ctxStatic}/ueditor/ueditor.all.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/ueditor/ueditor.parse.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js" type="text/javascript"></script>

<!-- FontIcon -->
<link rel="stylesheet" href="${ctxStatic}/mainframe/fonticon/iconfont.css">

<script type="text/javascript" src="<%=basePath%>/b_base/common/smartweb.js"></script>

<!-- check --> 
<script type="text/javascript" src="<%=basePath%>/b_base/common/check.js"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/common/formCheck.js"></script>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/common/formCheck.css"/>

<!-- Self reference JS-->
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/prod/oper/cloudpay/pay/payIndex.js" charset="utf-8"></script>

<!-- change skin -->
<link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css" type="text/css" rel="stylesheet" />

<title>Insert title here</title>
</head>
<body>
<div id="messageBox" class="alert alert-success hide"><button data-dismiss="alert" class="close">×</button><span id="messageContent">操作提示信息</span></div>
<!-- view start -->


<div class="">
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
			<div ravo="rainbow_fx_layout_panel" class="panel panel-default">
				<div class="panel-body" contenteditable="false">
					<div class="clearfix">
					</div>
					<form id="listForm" ravo="rainbow_fx_layout_bd" class="form-horizontal breadcrumb">
						<input type="hidden" class="form-control" id="payNo" name="payNo">
						<div class="panel panel-default" >
							<div class="panel-heading">
								<a class="panel-title" data-toggle="collapse" data-parent="#myAccordion"
								href="#collapse_1" contenteditable="false">
									缴费产品
								</a>
							</div>
							<div id="collapse_1" class="panel-collapse collapse in">
								<div class="panel-body" contenteditable="false">
									<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:10px;">
										<div class="col-md-12 column">
											<div ravo="rainbow_fx_layout" class="row clearfix">
												<div class="col-md-4 column">
												</div>
												<div class="col-md-4 column">
													<div ravo="rainbow_fx" class="form-group">
														<label for="inputEmail3" class="col-sm-3 control-label">
															可售产品
														</label>
														<div class="col-sm-4">
															<select data-role="multiselect" id="saleProdCode" class="" name="saleProdCode" data-max-height="300"
																data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" 
																data-filter-placeholder="搜索">
																<option value="">
																	请选择
																</option>
															</select>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="panel panel-default" >
							<div class="panel-heading">
								<a class="panel-title" data-toggle="collapse" data-parent="#myAccordion"
								href="#collapse_2" contenteditable="false">
									缴费单位
								</a>
							</div>
							<div id="collapse_2" class="panel-collapse collapse in">
								<div class="panel-body" contenteditable="false">
									<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:10px;">
										<div class="col-md-12 column">
											<div ravo="rainbow_fx_layout" class="row clearfix">
												<div class="col-md-4 column">
												</div>
												<div class="col-md-4 column">
													<div ravo="rainbow_fx" class="form-group">
														<label for="inputEmail3" class="col-sm-3 control-label">
															缴费单位
														</label>
														<div class="col-sm-4">
															<select data-role="multiselect" id="busiNo" class="" name="busiNo" data-max-height="300"
																data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" 
																data-filter-placeholder="搜索">
																<option value="">
																	请选择
																</option>
															</select>
														</div>
													</div>
													<div ravo="rainbow_fx" class="form-group xf">
														<label for="inputEmail3" class="col-sm-3 control-label">
															缴费时间
														</label>
														<div class="col-sm-4">
															<select data-role="multiselect" id="projNo" class="" data-url="${ctx}/prod/oper/cloudpay/pay/listPayTime?busiNo=0300300001"
																name="projNo" data-max-height="300" checkbtn="projNo" >
																<option value="">请选择</option>
															</select>
														</div>
													</div>
													<div ravo="rainbow_fx" class="form-group online">
														<label for="inputEmail3" class="col-sm-3 control-label">
															缴费时间
														</label>
														<div class="col-sm-4">
															<input type="text" readonly="readonly" maxlength="20"
														class="form-control input-mini Wdate" value=""
														datetime-skin="twoer" datetime-date-fmt="yyyy-MM"
														datetime-min-date="" datetime-max-date=""
														datetime-is-show-clear="true" datetime-is-show-week="true"
														datetime-is-show-today="false" datetime-default-value=""
														datetime-value-fmt="yyyyMMddHHmmss" data-link-field="val_end_time"
														id="end_time"
														onclick="WdatePicker({skin: 'twoer',dateFmt: 'yyyy-MM',minDate: '1900-01',maxDate: '2099-12',isShowClear: true,isShowWeek: true,isShowToday: false,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('end_time');}});"
														onchange="writeDateValue('end_time');"> <input
														type="hidden" id="val_end_time" name="end_time" value="">
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="panel panel-default pay" >
							<div class="panel-heading">
								<a class="panel-title" data-toggle="collapse" data-parent="#myAccordion"
								href="#collapse_3" contenteditable="false">
									缴费信息
								</a>
							</div>
							<div id="collapse_3" class="panel-collapse collapse in">
								<div class="panel-body" contenteditable="false">
									<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:10px;">
										<div class="col-md-12 column">
											<div ravo="rainbow_fx_layout" class="row clearfix">
												<div class="col-md-4 column">
												</div>
												<div class="col-md-4 column add-conf">
													<div ravo="rainbow_fx" class="form-group pro">
														<label for="inputEmail3" class="col-sm-3 control-label">
															缴费项
														</label>
														<div class="col-sm-4">
															<select data-role="multiselect" id="pro" class="" name="pro" data-max-height="300"
																data-select-all-text="全部选择" data-include-select-all-option="true"
																data-non-selected-text="请选择" data-all-selected-text="已选全部"
																data-n-selected-text="个已选"  multiple="multiple" 
																data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" 
																data-filter-placeholder="搜索">
															</select>
														</div>
													</div>
													<div ravo="rainbow_fx" class="form-group add-conf-1" > <!-- style="dispaly:none" -->
														<label for="inputEmail3" class="col-sm-3 control-label">
															隐藏域
														</label>
														<div class="col-sm-6">
															<input type="text" class="form-control add-conf-1-1" id="hidden" name="">
														</div>
														<div class="col-sm-2">
															<shiro:haspermission name="anno">
																<button ravo="rainbow_fx" type="button" class="btn btn-danger" 
																		contenteditable="false" onclick="rmKey(this);">
																	删除
																	<i class="glyphicon glyphicon-remove" style="padding-right:2px"></i>
																</button>
															</shiro:haspermission>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:10px;">
										<div class="col-md-12 column">
											<div ravo="rainbow_fx_layout" class="row clearfix">
												<div class="col-md-5 column">
												</div>
												<div class="col-md-2 column">
													<shiro:haspermission name="anno">
														<button id="payBtn" ravo="rainbow_fx" type="button" class="btn btn-info" data-original-title="" title="" onclick="jump()">
															立即缴费
														</button>
													</shiro:haspermission>
												</div>	
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

<!--customer_code_beg-->

<!--customer_code_end-->


<!-- view end -->
</body>
</html>
