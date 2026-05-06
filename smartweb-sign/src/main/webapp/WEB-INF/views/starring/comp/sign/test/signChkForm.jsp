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
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/sign/test/signChkForm.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/common/formCheck.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/common/check.js" charset="utf-8"></script>
<link href="<%=basePath%>/b_base/common/formCheck.css" media="all" rel="stylesheet" type="text/css" />

<!-- change skin -->
<link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css" type="text/css" rel="stylesheet" />

<title>Insert title here</title>
</head>
<body>

	<!-- view start -->
	<div class="">
		<div ravo="rainbow_fx_layout" class="row clearfix">
			<div ravo="rainbow_fx_layout_tab" class="tabbable" id="tabs-999600">
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
						class="" id="tab1" name="tab1" aria-expanded="true">
							签约检查 </a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane in active" id="panel1">
						<div id="messageBox" class="alert alert-success hide">
							<button data-dismiss="alert" class="close">×</button>
							<span id="messageContent">操作提示信息</span>
						</div>
						<form ravo="rainbow_fx_layout_bd" class="form-horizontal"
							pourl="" id="formId_460824" style="margin-top: 10px">
							<div class="col-md-12 column">
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-6 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label">
												业务编号 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="BUSI_NO" name="BUSI_NO"
													check-empty="true" maxlength="14">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label">
												子业务编号 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="SUB_BUSI_NO" name="SUB_BUSI_NO"
													check-empty="true" maxlength="14">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label">
												单位编号 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="ENTR_NO" name="ENTR_NO"
													check-empty="true" maxlength="14">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label">
												账/卡号 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="OLD_ACCT" name="OLD_ACCT"
													check-empty="true" maxlength="64">
											</div>
										</div>
										<!-- <div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label">
												账户类型 </label>
											<div class="col-sm-4">
												<select data-role="multiselect" id="OPEN_STAT"
													name="OPEN_STAT" class="">
													<option value="01">对私存款账号</option>
													<option value="02">对公存款账号</option>
													<option value="10">对私卡号</option>
												</select>
											</div>
										</div> -->
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label">
												账户名称 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="ACCT_NAME" name="ACCT_NAME" maxlength="120">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label">
												开户行号 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="BANK" name="BANK" maxlength="20">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label">
												开户行名 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="BANK_NAME" name="BANK_NAME" maxlength="120">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label">
												电话 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="PER_TEL_NO" name="PER_TEL_NO" maxlength="30">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label">
												证件类型 </label>
											<div class="col-sm-4">
												<select data-role="multiselect" id="CERT_TP" class=""
													name="CERT_TP" data-enable-filtering="true"
													data-enable-case-insensitive-filtering="true"
													data-filter-placeholder="搜索"
													data-url="${ctx}/sys/dict/selectData?type=CERT_TP"
													blank-item="true" data-non-selected-text="证件类型">
												</select>
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label">
												证件号码 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="CERT_NO" name="CERT_NO" maxlength="60">
											</div>
										</div>
									</div>
									<div class="col-md-6 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-3 control-label">
												第三方客户号 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="OTH_CUST_NO" name="OTH_CUST_NO"
													check-empty="true" maxlength="64">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-3 control-label">
												第三方单位编号 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="ENTR_ACCT" name="ENTR_ACCT" maxlength="64">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-3 control-label">
												协议号 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="SIGN_PROT_NO" name="SIGN_PROT_NO" maxlength="120">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-3 control-label">
												对方账号 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="OPP_ACCT" name="OPP_ACCT" maxlength="64">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-3 control-label">
												对方账号户名 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="OPP_ACCT_NAME" name="OPP_ACCT_NAME" maxlength="120">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-3 control-label">
												对方开户行号 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="OPP_BANK" name="OPP_BANK" maxlength="20">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-3 control-label">
												对方开户行名 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="OPP_BANK_NAME" name="OPP_BANK_NAME" maxlength="120">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-3 control-label">
												交易金额 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="TRAN_AMT" name="TRAN_AMT" maxlength="16">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-3 control-label">
												渠道号 </label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													id="CHNL_NO" name="CHNL_NO" maxlength="6">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-3 control-label">
												新旧账卡号支持 </label>
											<div class="col-sm-4">
												<select data-role="multiselect" id="FLG"
													name="FLG" class="">
													<option value="Y">是</option>
													<option value="N">否</option>
												</select>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top: 20px;margin-bottom: 20px">
								<div class="col-md-4 column"></div>
								<div class="col-md-2 column">
										<shiro:haspermission name="anno">
											<button ravo="rainbow_fx" type="button" class="btn btn-primary"
												contenteditable="false" id="submitBtn" name="submitBtn">提交</button>
										</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
										<shiro:haspermission name="anno">
											<button ravo="rainbow_fx" type="button" class="btn btn-default"
												contenteditable="false" id="closeBtn" name="closeBtn">关闭</button>
										</shiro:haspermission>
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
