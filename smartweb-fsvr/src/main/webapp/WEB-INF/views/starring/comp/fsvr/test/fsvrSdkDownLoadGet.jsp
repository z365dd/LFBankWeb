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
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/fsvr/test/fsvrSdkDownLoadGet.js" charset="utf-8"></script>

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
				<div ravo="rainbow_fx_layout_tab" class="tabbable" id="myTabs">
					<!-- Only required for left/right tabs -->
					<ul class="nav nav-tabs" data-toggle="tabs">
						<li class="active"><a href="#panel1" data-toggle="tab"
							id="tab1" name="tab1"> 文件获取并下载 </a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="panel1">
							<div id="messageBox" class="alert alert-success hide">
								<button data-dismiss="alert" class="close">×</button>
								<span id="messageContent">操作提示信息</span>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-12 column">
									<form ravo="rainbow_fx_layout_bd" class="form-horizontal"
										pourl="" id="formId_683647">
										<div ravo="rainbow_fx_layout_panel"
											class="panel panel-default">
											<div class="panel-heading">
												<div ravo="rainbow_fx_bj">
													<h4 contenteditable="false">输入要素</h4>
												</div>
											</div>
											<div class="panel-body" contenteditable="false">
												<div ravo="rainbow_fx_layout" class="row clearfix">
													<div class="col-md-6 column">
														<div ravo="rainbow_fx" class="form-group">
															<label for="inputEmail3" class="col-sm-5 control-label control-label">
																服务器标识号
															</label>
															<div class="col-sm-4">
																<select data-role="multiselect" id="FILE_SVR_ID" class="" data-enable-filtering="true"
																	name="FILE_SVR_ID" checkbtn="FILE_SVR_ID" data-max-height="300"
																	data-filter-placeholder="搜索">
																	<option value="">请选择</option>
																</select>
															</div>
														</div>
														<div ravo="rainbow_fx" class="form-group">
															<label for="inputEmail3" class="col-sm-5 control-label">
																文件传输类型 </label>
															<div class="col-sm-4">
																<select data-role="multiselect" id="FILE_TRANS_TP" class=""
																	name="FILE_TRANS_TP" data-url="${ctx}/sys/dict/selectData?type=FILE_TRANS_TP" checkbtn="FILE_TRANS_TP">
																	<option value="">请选择</option>
																	<option value="01">同步</option>
																	<option value="02">异步</option>
																</select>
															</div>
														</div>
														<div ravo="rainbow_fx" class="form-group" hidden="true">
															<label for="inputEmail3" class="col-sm-5 control-label">
																本地文件公共目录 </label>
															<div class="col-sm-5">
																<input type="text" class="form-control" maxlength="128"
																	id="LOCAL_FULL_FILE_PATH" name="LOCAL_FULL_FILE_PATH">
															</div>
														</div>
														<div ravo="rainbow_fx" class="form-group" hidden="true">
															<label for="inputEmail3" class="col-sm-5 control-label">
																远程文件公共目录 </label>
															<div class="col-sm-5">
																<input type="text" class="form-control" maxlength="128"
																	id="RMT_FULL_FILE_PATH" name="RMT_FULL_FILE_PATH">
															</div>
														</div>
													</div>
													<div class="col-md-6 column">
													<div ravo="rainbow_fx" class="form-group" hidden="true">
															<label for="inputEmail3" class="col-sm-3 control-label">
																是否走文件管理 </label>
															<div class="col-sm-4">
																<select data-role="multiselect" id="FILE_MNG_FLG" class=""
																	name="FILE_MNG_FLG" checkbtn="FILE_MNG_FLG">
																	<option value="">请选择</option>
																	<option value="N" selected>否</option>
																	<option value="Y">是</option>
																</select>
															</div>
														</div>
													</div>
												</div>

												<div id="commonFile" class="hide">
													<div ravo="rainbow_fx_layout" class="row clearfix" name="fileDivName" style="padding-left:8%;">
														<div class="col-md-4 column">
															<div ravo="rainbow_fx" class="form-group">
																<label for="inputEmail3" class="col-sm-5 control-label">
																	文件名</label>
																<div class="col-sm-5">
																	<input type="text" class="form-control" placeholder=""
																		id="FILE_NAME" name="FILE_NAME" check-empty="true">
																</div>
															</div>
														</div>
														<div class="col-md-2 column">
															<div ravo="rainbow_fx" class="form-group">
																<div class="col-sm-2">
																		<shiro:haspermission name="anno">
																			<button ravo="rainbow_fx" type="button"
																				class="btn btn-default" contenteditable="false"
																				 name="delBtn" style="float: right">删除</button>
																		</shiro:haspermission>
																</div>
															</div>
														</div>
														<div class="col-md-2 column">
															<div class="col-sm-2">
																	<a href="#" name="downLoadBtnFile" target="_self" download="" style="display:none">
																		<shiro:haspermission name="anno">
																			<button ravo="rainbow_fx" type="button"
																				class="btn btn-default" contenteditable="false"
																				 name="loadBtn" style="float: right">下载</button>
																		</shiro:haspermission>
																	</a>
																</div>
														</div>
														<div class="col-md-2 column">
															<div ravo="rainbow_fx" class="form-group">
															
															</div>
														</div>
														<div class="col-md-2 column">
															<div ravo="rainbow_fx" class="form-group" hidden="true">
																<label for="inputEmail3" class="col-sm-4 control-label">
																	文件别名</label>
																<div class="col-sm-6">
																	<input type="text" class="form-control" maxlength="20"
																		id="ALIAS_FILE_NAME" name="ALIAS_FILE_NAME">
																</div>
															</div>
														</div>
														<div class="col-md-2 column">
															<div ravo="rainbow_fx" class="form-group" hidden="true">
																<label for="inputEmail3" class="col-sm-3 control-label">
																	文件子目录</label>
																<div class="col-sm-6">
																	<input type="text" class="form-control" maxlength="42"
																		id="SUB_FILE_PATH" name="SUB_FILE_PATH">
																</div>
															</div>
														</div>
													</div>
												</div>
												<div id="fileDiv"></div>

												<div ravo="rainbow_fx_layout" class="row clearfix">
													<div class="col-md-6 column">
														<div ravo="rainbow_fx" class="form-group">
															<div class="col-md-5 column"></div>
															<div class="col-md-7 column">
																<shiro:haspermission name="anno">
																	<button ravo="rainbow_fx" type="button"
																		class="btn btn-default" contenteditable="false"
																		id="addFile">增加文件</button>
																</shiro:haspermission>
															</div>
														</div>
													</div>
													<div class="col-md-6 column"></div>
												</div>
												<div ravo="rainbow_fx_layout" class="row clearfix">
													<div class="col-md-6 column">
														<div ravo="rainbow_fx" class="form-group">
															<div class="col-md-7 column"></div>
															<div class="col-md-5 column">
																<shiro:haspermission name="anno">
																	<button ravo="rainbow_fx" type="button"
																		class="btn btn-primary" contenteditable="false"
																		id="addBtn">提交</button>
																</shiro:haspermission>
															</div>
														</div>
													</div>
													<div class="col-md-6 column">
														<div ravo="rainbow_fx_layout" class="row clearfix">
															<div class="col-md-3 column"></div>
															<div class="col-md-9 column">
																<shiro:haspermission name="anno">
																	<button ravo="rainbow_fx" type="button"
																		class="btn btn-default" contenteditable="false"
																		id="cancelBtn">关闭</button>
																</shiro:haspermission>
															</div>
														</div>
													</div>
												</div>

											</div>
										</div>
										<div ravo="rainbow_fx_layout_panel"
											class="panel panel-default">
											<div class="panel-heading">
												<div ravo="rainbow_fx_bj">
													<h4 contenteditable="false">返回结果</h4>
												</div>
											</div>
											<div class="panel-body" contenteditable="false">

												<div ravo="rainbow_fx_layout" class="row clearfix">
													<div class="col-md-6 column">
														<div ravo="rainbow_fx" class="form-group">
															<label for="inputEmail3"
																class="col-sm-5 control-label control-label">
																请求流水号</label>
															<div class="col-sm-7">
																<input type="text" class="form-control" placeholder=""
																	id="REQ_SEQ" name="REQ_SEQ">
															</div>
														</div>
														<div ravo="rainbow_fx" class="form-group">
															<label for="inputEmail3"
																class="col-sm-5 control-label control-label">
																文件流水号</label>
															<div class="col-sm-7">
																<input type="text" class="form-control" placeholder=""
																	id="FILE_SET_SEQ" name="FILE_SET_SEQ">
															</div>
														</div>
													</div>
													<div class="col-md-6 column"></div>
												</div>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
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
