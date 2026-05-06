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
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/sign/tec/paraEntrForm.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/sign/tec/signCommon/signCommon.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/common/formCheck.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/common/check.js" charset="utf-8"></script>
<link href="<%=basePath%>/b_base/common/formCheck.css" media="all" rel="stylesheet" type="text/css" />

<!-- change skin -->
<link href="${ctxStatic}/mainframe/${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css" type="text/css" rel="stylesheet" />

<title>Insert title here</title>
</head>
<body>
	<div id="messageBox" class="alert alert-success hide">
		<button data-dismiss="alert" class="close">×</button>
		<span id="messageContent">操作提示信息</span>
	</div>
	<!-- view start -->
	<div class="">
		<div ravo="rainbow_fx_layout" class="row clearfix" id="infoDiv">
			<div class="col-md-12 column">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
					id="formId_299274" style="margin-top: 10px">
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									法人号 </label>
								<div class="col-sm-3">
									<select data-role="multiselect" id="LEGA_NO" class=""
										name="LEGA_NO" checkbtn="LEGA_NO">
										<option value="">请选择</option>
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									单位编号 </label>
								<div class="col-sm-2">
									<input type="text" class="form-control" placeholder=""
										id="ENTR_NO" name="ENTR_NO" check-time-empty="true" readonly="readonly">
								</div>
								<div class="col-sm-3">
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-1 column"></div>
										<div class="col-md-11 column">
											<shiro:haspermission name="anno">
												<button ravo="rainbow_fx" type="button" 
													class="btn btn-default" contenteditable="false" id="crtBtn">生成</button>
											</shiro:haspermission>
										</div>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									单位名称 </label>
								<div class="col-sm-8">
									<input type="text" class="form-control" placeholder=""
										id="ENTR_NAME" name="ENTR_NAME"
										check-empty="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									打印名称 </label>
								<div class="col-sm-8">
									<input type="text" class="form-control" placeholder=""
										id="PRT_NAME" name="PRT_NAME"
										check-empty="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									证件类型 </label>
								<div class="col-sm-5">
									<select data-role="multiselect" id="CERT_TP" class=""
										name="CERT_TP" data-enable-filtering="true"
										data-enable-case-insensitive-filtering="true"
										data-filter-placeholder="搜索" checkbtn="CERT_TP"
										data-url="${ctx}/sys/dict/selectData?type=CERT_TP"
										blank-item="true" data-async="true"
										data-non-selected-text="证件类型">
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									证件号码 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="CERT_NO" name="CERT_NO"
										check-empty="true" check-idCard="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									开通状态 </label>
								<div class="col-sm-3">
									<select data-role="multiselect" id="OPEN_STAT" name="OPEN_STAT"
										class="" checkbtn="OPEN_STAT">
										<option value="1">开通</option>
										<option value="2">关闭</option>
										<option value="3">删除</option>
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									联系人</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="CTCT_PER_NAME" name="CTCT_PER_NAME"
										check-empty="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									个人电话</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" placeholder=""
										id="PER_TEL_NO" name="PER_TEL_NO"
										check-empty="true" maxlength="20" check-empty="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									单位电话</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" placeholder=""
										id="ENTR_TEL_NO" name="ENTR_TEL_NO"
										check-empty="true" maxlength="20" check-empty="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									通信地址</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" placeholder=""
										id="COMM_ADDR" name="COMM_ADDR"
										check-empty="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									邮编</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" placeholder=""
										id="POST_ECD" name="POST_ECD"
										check-empty="true" maxlength="6">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									邮箱地址</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="EMAIL_ADDR" name="EMAIL_ADDR"
										check-empty="true" check-mail="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									行内客户号</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="BANK_CUST_NO" name="BANK_CUST_NO"
										check-empty="true">
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top: 20px">
						<div class="col-md-3 column"></div>
						<div class="col-md-2 column">
							<shiro:haspermission name="anno">
								<button ravo="rainbow_fx" type="button" class="btn btn-primary"
									contenteditable="false" id="submitBtn" name="submitBtn">提交</button>
							</shiro:haspermission>
						</div>
						<div class="col-md-2 column">
							<shiro:haspermission name="anno">
								<button ravo="rainbow_fx" type="button" class="btn btn-default"
									contenteditable="false" id="closeBtn" name="closeBtn">返回</button>
							</shiro:haspermission>
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
