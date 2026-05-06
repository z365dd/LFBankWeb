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
	src="<%=basePath%>/b_base/views/starring/comp/fsvr/om/flowQry.js"
	charset="utf-8"></script>

<!-- change skin -->
<link	href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css"	type="text/css" rel="stylesheet" />
<script type="text/javascript"
	src="<%=basePath%>/b_base/common/formCheck.js" charset="utf-8"></script>
<link href="<%=basePath%>/b_base/common/formCheck.css" media="all"
	rel="stylesheet" type="text/css" />
<title>Insert title here</title>
</head>
<body>
	<!-- view start -->
	<div class="">
		<div ravo="rainbow_fx_layout" class="row clearfix">
			<div class=" column">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
					id="formId_175337" style="margin-top: 20px">
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label class=" control-label col-sm-5 control-label">
									文件流水号 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="TRAN_SEQ" name="TRAN_SEQ">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									开始日期 </label>
								<div class="col-sm-4">
									<input type="text" readonly="readonly" maxlength="20"
										class="form-control input-mini Wdate" value=""
										datetime-skin="twoer" datetime-date-fmt="yyyy-MM-dd"
										datetime-min-date="" datetime-max-date=""
										datetime-is-show-clear="true" datetime-is-show-week="true"
										datetime-is-show-today="true" datetime-default-value=""
										datetime-value-fmt="yyyyMMdd" data-link-field="val_STR_DATE"
										id="STR_DATE"
										onclick="WdatePicker({skin: 'twoer',dateFmt: 'yyyy-MM-dd',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('STR_DATE');}});"
										onchange="writeDateValue('STR_DATE');"> <input
										type="hidden" id="val_STR_DATE" value="" name="STR_DATE">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label id="LAB_BUSI_NO" class="col-sm-5 control-label">
									调用组件 </label>
								<div class="col-sm-4">
									<!-- <input type="text" class="form-control" placeholder=""
										id="COMP_NO" name="COMP_NO"> -->
									<select data-role="multiselect" id="COMP_NO" class=""
										name="COMP_NO" data-max-height="300" data-enable-filtering="true" data-filter-placeholder="搜索"
										>
										<option value="1">option 1</option>
										<option value="2">option 2</option>
										<option value="3">option 3</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class=" col-sm-3 control-label">
									文件交易状态 </label>
								<div class="col-sm-4">
									<select data-role="multiselect" id="FILE_TRAN_STAT" class="" name="FILE_TRAN_STAT"
										data-max-height="300" data-async="true" data-url="${ctx}/comp/fsvr/om/qryCount/selectData?type=FILE_TRAN_STAT" blank-item=true checkbtn="FILE_TRAN_STAT">
										<option value="01">全部</option>
										<option value="02">成功</option>
										<option value="03">失败</option>
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									结束日期 </label>
								<div class="col-sm-4">
									<input type="text" readonly="readonly" maxlength="20"
										class="form-control input-mini Wdate" value=""
										datetime-skin="twoer" datetime-date-fmt="yyyy-MM-dd"
										datetime-min-date="" datetime-max-date=""
										datetime-is-show-clear="true" datetime-is-show-week="true"
										datetime-is-show-today="true" datetime-default-value=""
										datetime-value-fmt="yyyyMMdd" data-link-field="val_END_DATE"
										id="END_DATE"
										onclick="WdatePicker({skin: 'twoer',dateFmt: 'yyyy-MM-dd',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('END_DATE');}});"
										onchange="writeDateValue('END_DATE');"> <input
										type="hidden" id="val_END_DATE" value="" name="END_DATE">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label id="LAB_BUSI_NO" class="col-sm-3 control-label">
									业务编号</label>
								<div class="col-sm-4">
									<div class="input-append">
										<input input_type="treesearch" search_url="/comp/fsvr/om/qryCount/busiList" id="companyName" 
											name="companyName" type="text" value="" data-msg-required=""
											class="form-control input-small" style="" data-bv-="true"
											readonly="readonly">
									</div>
								</div>
								<div class="col-sm-5">
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-2 column"></div>
										<div class="col-md-5 column">
											<shiro:haspermission name="anno">
												<button ravo="rainbow_fx" type="button"
													class="btn btn-primary" contenteditable="false" id="qryBtn"
													name="qryBtn">查询</button>
											</shiro:haspermission>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
				<table id="table" data-toggle="table" data-first-load="false"
					data-url="${ctx}/comp/fsvr/om/qryCount/getFlowQry"
					data-click-to-select="true" data-show-export="false"
					data-show-refresh="false" data-show-toggle="false"
					data-show-columns="false" data-pagination="true"
					data-search="false" data-query-params="queryParams"
					data-method="post" data-undefined-text="**" data-height="500"
					data-content-type="application/x-www-form-urlencoded"
					ravo="rainbow_fx_bj" class="table table-hover"
					data-side-pagination="server" data-striped="true">
					<thead style="">
						<tr>
							<th data-field="TRAN_DATE">交易日期</th>
							<th data-field="COMP_NO">组件号</th>
							<th data-field="BUSI_NO">业务编号</th>
							<th data-field="TRAN_SEQ">交易流水号</th>
							<th data-field="RET_CODE">返回码</th>
							<th data-field="RET_MSG">返回信息</th>
							<th data-field="ACTION">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<!--customer_code_beg-->

	<!--customer_code_end-->
	<!-- view end -->
</body>
</html>
