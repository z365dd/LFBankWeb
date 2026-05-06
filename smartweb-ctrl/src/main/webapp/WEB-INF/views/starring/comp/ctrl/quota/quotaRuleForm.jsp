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
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-migrate-3.4.0.min.js" charset="utf-8"></script><!-- /JQUERY -->

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
<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/locale/bootstrap-table-zh-CN.js"/></script>
<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/tableExport.js"/></script>
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

<script type="text/javascript" src="<%=basePath%>/b_base/common/smartweb.js"></script>

<!-- Self reference JS-->
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/ctrl/quota/quotaRuleForm.js" charset="utf-8"></script>

<!-- change skin -->
<link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/ctrl/switches/compCommon.js" charset="utf-8"></script>
<script type="text/javascript"
	src="<%=basePath%>/b_base/common/formCheck.js" charset="utf-8"></script>
<link href="<%=basePath%>/b_base/common/formCheck.css" media="all"
	rel="stylesheet" type="text/css" />
<title>Insert title here</title>
</head>
<body>
<div id="messageBox" class="alert alert-success hide"><button data-dismiss="alert" class="close">×</button><span id="messageContent">操作提示信息</span></div>
<!-- view start -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">开关规则定义</h4>
				</div>
				<div class="modal-body">
					<div ravo="rainbow_fx_layout" class="row clearfix" id='ruleCommon'>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id='mCancelBtn'>取消</button>
					<button type="button" class="btn btn-primary" id='mNextBtn'>确定</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
<div class="">
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
					id="formId_286247" style="margin-top: 20px">

					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-6 control-label">
									模型 </label>
								<div class="col-sm-6">
									<select data-role="multiselect" id="MODL_NO" class=""
										name="MODL_NO">

									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-6 control-label">
									子服务码 </label>
								<div class="col-sm-6">
									<select data-role="multiselect" id="SUB_SVC" class=""
										name="SUB_SVC">
										<option value=''>请选择</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class=" col-sm-3 control-label">
									服务码 </label>
								<div class="col-sm-7">
									<select data-role="multiselect" id="SVC_CODE" class=""
										name="SVC_CODE">
										<option value=''>请选择</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3"
									class="col-sm-3 control-label control-label"
									style="visibility: visible">开关规则定义</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" placeholder=""
										id="RULE_EXP" name="RULE_EXP">
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3"
									class="col-sm-3 control-label control-label"
									style="visibility: visible">开关规则定义中文</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" placeholder=""
										id="EXP_DESC" name="EXP_DESC" readonly>
								</div>
							</div>
						</div>
					</div>

					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-6 control-label">
									日累计金额限额 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="AMT_LMT_DAY" name="AMT_LMT_DAY" >
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-6 control-label">
									月累计金额限额 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="AMT_LMT_MONTH" name="AMT_LMT_MONTH" >
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-6 control-label">
									季累计金额限额 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="AMT_LMT_SEASON" name="AMT_LMT_SEASON" >
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-6 control-label">
									年累计金额限额 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="AMT_LMT_YEAR" name="AMT_LMT_YEAR" >
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-6 control-label">
									单笔限额 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="AMT_LMT_SIGL" name="AMT_LMT_SIGL" >
								</div>
							</div>
							
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									日累计笔数限额 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="CNT_LMT_DAY" name="CNT_LMT_DAY" >
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									月累计笔数限额 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="CNT_LMT_MONTH" name="CNT_LMT_MONTH" >
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									季累计笔数限额 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="CNT_LMT_SEASON" name="CNT_LMT_SEASON" >
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									年累计笔数限额 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="CNT_LMT_YEAR" name="CNT_LMT_YEAR" >
								</div>
							</div>
							
						</div>
					</div>
					
					<div ravo="rainbow_fx_layout" class="row clearfix" style="margin:40px 0">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-6 control-label">
									 </label>
								<div class="col-sm-4">
									<shiro:haspermission name="anno">
								<button ravo="rainbow_fx" type="button" class="btn btn-primary "
									contenteditable="false" id="saveBtn">确认</button>
							</shiro:haspermission>
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-3 column"></div>
								<div class="col-md-4 column">
									<shiro:haspermission name="anno">
								<button ravo="rainbow_fx" type="button" class="btn btn-default"
									contenteditable="false" id="cancelBtn">取消</button>
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
