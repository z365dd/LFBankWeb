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
	src="<%=basePath%>/b_base/views/starring/comp/fsvr/tec/tfsvrPtcptParaDetailForm.js"
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
					pourl="" id="tfsvrPtcptParaForm" style="margin-top: 10px;">
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									调用方参与者 </label>
								<div class="col-sm-4">
									<input id="callerId" name="callerId" class="form-control "
										maxlength="64" check-empty="true" readonly="readonly">
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									状态 </label>
								<div class="col-sm-4">
									<select id="stat" name="stat" data-role="multiselect">
										<option value="">--请选择--</option>
										<option value="00">启用</option>
										<option value="01">停止</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									调用方描述 </label>
								<div class="col-sm-4">
									<input id="callerDesc" name="callerDesc" class="form-control "
										maxlength="1024" readonly="readonly">
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									调用方式 </label>
								<div class="col-sm-4">
									<select id="callMeth" name="callMeth" data-role="multiselect"
										checkbtn="callMeth">
										<option value="">--请选择--</option>
										<option value="01">客户端</option>
										<option value="02">服务端</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix" id="serverDiv" style="display:none;">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									端口 </label>
								<div class="col-sm-4">
									<input id="port" name="port" class="form-control "
										maxlength="10" readonly="readonly">
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									可访问网络区域 </label>
								<div class="col-sm-4">
									<select data-role="multiselect" id="netRegion" class=""
										name="netRegion" multiple="multiple" data-async="true"
										data-enable-filtering="true" data-max-height="300"
										data-enable-full-value-filtering="true"
										data-filter-placeholder="搜索">
									</select>
								</div>
							</div>
						</div>
					</div>
					<div id="clientDiv" style="display:none;">
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									用户名 </label>
								<div class="col-sm-4">
									<input id="userNo" name="userNo" class="form-control "
										maxlength="30" readonly="readonly">
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									密码 </label>
								<div class="col-sm-4">
									<input id="pwd" name="pwd" class="form-control " maxlength="80"
										readonly="readonly">
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									是否签名 </label>
								<div class="col-sm-4">
									<select id="attestFlg" name="attestFlg" data-role="multiselect">
										<option value="">--请选择--</option>
										<option value="Y">是</option>
										<option value="N">否</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									是否加密 </label>
								<div class="col-sm-4">
									<select id="encrpFlg" name="encrpFlg" data-role="multiselect">
										<option value="">--请选择--</option>
										<option value="Y">是</option>
										<option value="N">否</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									是否压缩 </label>
								<div class="col-sm-4">
									<select id="reduceFlg" name="reduceFlg" data-role="multiselect">
										<option value="">--请选择--</option>
										<option value="Y">是</option>
										<option value="N">否</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									是否限速 </label>
								<div class="col-sm-4">
									<select id="speedlimFlg" name="speedlimFlg"
										data-role="multiselect">
										<option value="">--请选择--</option>
										<option value="Y">是</option>
										<option value="N">否</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									发送限速大小 </label>
								<div class="col-sm-4">
									<input id="sndSpeedlimSize" name="sndSpeedlimSize"
										class="form-control  digits" maxlength="18"
										readonly="readonly">
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									接收限速大小 </label>
								<div class="col-sm-4">
									<input id="recvSpeedlimSize" name="recvSpeedlimSize"
										class="form-control  digits" maxlength="18"
										readonly="readonly">
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									分包发送文件大小 </label>
								<div class="col-sm-4">
									<input id="subctrctSendFileSize" name="subctrctSendFileSize"
										class="form-control  digits" maxlength="18"
										readonly="readonly">
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									分包接收文件大小 </label>
								<div class="col-sm-4">
									<input id="subctrctRecvFileSize" name="subctrctRecvFileSize"
										class="form-control  digits" maxlength="18"
										readonly="readonly">
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									下载路径 </label>
								<div class="col-sm-4">
									<input id="downloadFilePath" name="downloadFilePath"
										class="form-control " maxlength="256" readonly="readonly">
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									上传路径 </label>
								<div class="col-sm-4">
									<input id="uploadFilePath" name="uploadFilePath"
										class="form-control " maxlength="256" readonly="readonly">
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									是否断点续传 </label>
								<div class="col-sm-4">
									<select id="resumeFlg" name="resumeFlg" data-role="multiselect">
										<option value="">--请选择--</option>
										<option value="Y">是</option>
										<option value="N">否</option>
									</select>
								</div>
							</div>
							
						</div>
						<div class="col-md-6 column">
						
						</div>
					</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-4 column"></div>
						<div class="col-md-2 column">
							<shiro:haspermission name="anno">
								<button ravo="rainbow_fx" type="button" class="btn btn-info"
									contenteditable="false" id="saveBtn">提交</button>
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
