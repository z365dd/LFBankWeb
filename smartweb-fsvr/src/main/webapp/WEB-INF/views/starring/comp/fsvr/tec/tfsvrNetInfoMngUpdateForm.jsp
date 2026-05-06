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
	src="<%=basePath%>/b_base/views/starring/comp/fsvr/tec/tfsvrNetInfoMngUpdateForm.js"
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
					pourl="" id="tfsvrNetInfoMngForm" style="margin-top: 10px;">
					<div ravo="rainbow_fx_layout" class="row clearfix" id="saveDiv">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									网络区域 </label>
								<div class="col-sm-4">
									<input id="netRegion" name="netRegion" class="form-control "
										maxlength="32" readonly="readonly"> </input>
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									参与者编号 </label>
								<div class="col-sm-4">
									<input id="membId" name="membId" class="form-control "
										maxlength="64" check-empty="true">
								</div>
							</div>
						</div>
					</div>

					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									依赖网络区域 </label>
								<div class="col-sm-4">
									<select data-role="multiselect" id="deponNetRegion" class=""
										name="deponNetRegion" multiple="multiple" data-async="true"
										data-enable-filtering="true"
										data-enable-full-value-filtering="true"
										data-filter-placeholder="搜索" data-max-height="300">
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-6 column"></div>
					</div>
					<div ravo="rainbow_fx_layout_panel" class="panel panel-default">
						<div class="panel-heading">
							<div ravo="rainbow_fx_bj">
								<h4 contenteditable="false">文件存储</h4>
							</div>
						</div>
					</div>
					<div id="tableDiv" name="tableDiv">
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-5 control-label">
										主机名 </label>
									<div class="col-sm-4">
										<input id="fileSvrId" name="fileSvrId" class="form-control "
											maxlength="64" check-empty="true">
									</div>
								</div>
							</div>
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label">
										描述 </label>
									<div class="col-sm-4">
										<input id="svrDesc" name="svrDesc" class="form-control "
											maxlength="512" check-empty="true">
									</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-5 control-label">
										端口 </label>
									<div class="col-sm-4">
										<input id="port" name="port" class="form-control "
											maxlength="10" check-empty="true">
									</div>
								</div>
							</div>
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label">
										状态 </label>
									<div class="col-sm-4">
										<select data-role="multiselect" id="fileSvrStat" class="" name="fileSvrStat"
											data-url="${ctx}/sys/dict/selectData?type=FSVR_STAT"  checkbtn="fileSvrStat"
											data-async="true" blank-item=true>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-5 control-label">
										开通功能 </label>
									<div class="col-sm-4">
										<select data-role="multiselect" id="openSvcFlg" class=""
											name="openSvcFlg"
											data-url="${ctx}/sys/dict/selectData?type=OPEN_SVC_FUNC"
											data-async="true" blank-item="true" checkbtn="openSvcFlg">
											<option value="">选项加载失败</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-6 column">
								
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix" id="rowDiv">
						<div class="col-md-6 column"></div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<div class="col-md-3 column"></div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
											id="addRowBtn">增加一行</button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
											id="reviceRowBtn">确认修改</button>
									</shiro:haspermission>
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<label for="inputEmail3"
									class="col-sm-1 control-label control-label"
									style="visibility: visible; margin-left: 12%"> </label>
								<div class="col-sm-7">
									<table id="table" data-toggle="table" data-first-load="false"
										data-click-to-select="true" data-show-export="false"
										data-show-refresh="false" data-show-toggle="false"
										data-show-columns="false" data-pagination="false"
										data-search="false" data-method="post"
										data-undefined-text="**" data-height="300"
										data-content-type="application/x-www-form-urlencoded"
										ravo="rainbow_fx_bj"
										class="table table-hover table-bordered table-condensed"
										data-single-select="true">
										<thead style="">
											<tr>
												<th data-field="fileSvrId">主机名</th>
												<th data-field="port">端口</th>
												<th data-field="ip" data-visible="false">IP</th>
												<th data-field="svrDesc" data-visible="false">描述</th>
												<th data-field="downloadFilePath" data-visible="false">下载路径</th>
												<th data-field="uploadFilePath" data-visible="false">上传路径</th>
												<th data-field="openSvcFlg" data-visible="false">开通服务</th>
												<th data-field="fileSvrTp" data-visible="false">文件服务器类型</th>
												<th data-field="fileSvrStat" data-visible="false">状态</th>
												<th data-field="commProtGrpTp" data-visible="false">通讯协议类型</th>
												<th data-field="membId" data-visible="false">参与者ID</th>
												<th data-field="action">操作</th>
											</tr>
										</thead>
									</table>
								</div>
							</div>
						</div>
					</div>
				<!-- 	<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									依赖网络区域 </label>
								<div class="col-sm-2">
									<select data-role="multiselect" id="deponNetRegion" class=""
										name="deponNetRegion" multiple="multiple" data-async="true"
										data-enable-filtering="true"
										data-enable-full-value-filtering="true"
										data-filter-placeholder="搜索" data-max-height="300">
									</select>
								</div>
							</div>
						</div>
					</div> -->

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
