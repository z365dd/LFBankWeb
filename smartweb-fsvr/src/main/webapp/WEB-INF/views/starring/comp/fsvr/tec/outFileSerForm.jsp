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
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/fsvr/tec/outFileSerForm.js" charset="utf-8"></script>

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
			<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="formId_299274"
			style="margin-top: 10px">
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label control-label">
								标识号
							</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" placeholder="" id="FILE_SVR_ID"
								name="FILE_SVR_ID" check-empty="true">
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								开通功能
							</label>
							<div class="col-sm-7">
								<select data-role="multiselect" id="OPEN_SVC_FLG" class="" name="OPEN_SVC_FLG"
								data-url="${ctx}/sys/dict/selectData?type=OPEN_SVC_FUNC" data-async="true"
								blank-item="true" checkbtn="OPEN_SVC_FLG">
									<option value="1">
										option 1
									</option>
									<option value="2">
										option 2
									</option>
									<option value="3">
										option 3
									</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-12 column" style="margin-left: 4.4%">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label control-label">
								描述
							</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" placeholder="" id="SVR_DESC" name="SVR_DESC"
								check-empty="true">
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label control-label">
								主机IP
							</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" placeholder="" id="IP" name="IP"
								check-empty="true">
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label control-label">
								端口
							</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" placeholder="" id="PORT" name="PORT"
								check-empty="true">
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label control-label">
								用户名
							</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" placeholder="" id="USER_NO" name="USER_NO">
							</div>
						</div>
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label control-label">
								密码
							</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" placeholder="" id="PWD1" name="PWD1">
							</div>
						</div>
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label control-label">
								上传路径
							</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" placeholder="" id="UPLOAD_PATH"
								name="UPLOAD_PATH">
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class=" col-sm-3 control-label">
								通讯协议
							</label>
							<div class="col-sm-7">
								<select data-role="multiselect" id="COMM_PROT_TP" class="" name="COMM_PROT_TP"
								data-url="${ctx}/sys/dict/selectData?type=COMM_PROT_TP" data-async="true"
								blank-item="true" checkbtn="COMM_PROT_TP">
									<option value="1">
										获取参数失败
									</option>
								</select>
							</div>
						</div>
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label control-label">
								确认密码
							</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" placeholder="" id="PWD2" name="PWD2">
							</div>
						</div>
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label control-label">
								下载路径
							</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" placeholder="" id="DOWNLOAD_PATH"
								name="DOWNLOAD_PATH">
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class=" col-sm-5 control-label">
								断点续传
							</label>
							<div class="col-sm-7">
								<select data-role="multiselect" id="CONT_FLG" class="" name="CONT_FLG"
								data-async="true" blank-item="true" checkbtn="CONT_FLG">
									<option value="">
										--请选择--
									</option>
									<option value="Y">
										是
									</option>
									<option value="N">
										否
									</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label">
								依赖网络区域
							</label>
							<div class="col-sm-7">
								<select data-role="multiselect" id="DEPON_FILE_SVR_ID" class="" name="DEPON_FILE_SVR_ID"
								multiple="multiple" data-async="true" blank-item="true" data-enable-filtering="true"
								data-enable-full-value-filtering="true" data-filter-placeholder="搜索">
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								服务器类型
							</label>
							<div class="col-sm-7">
								<select data-role="multiselect" id="FILE_SVR_TP" name="FILE_SVR_TP">
									<option value="">
										--请选择--
									</option>
									<option value="B1">
										行内系统文件服务器
									</option>
									<option value="B2">
										行外系统文件服务器
									</option>
									
								</select>
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-5 column">
							</div>
							<div class="col-md-4 column">
								<shiro:haspermission name="anno">
									<button ravo="rainbow_fx" type="button" class="btn btn-primary" contenteditable="false"
									id="addBtn" name="addBtn">
										提交
									</button>
								</shiro:haspermission>
							</div>
							<div class="col-md-3 column">
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-3 column">
							</div>
							<div class="col-md-4 column">
								<shiro:haspermission name="anno">
									<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
									id="closeBtn" name="closeBtn">
										返回
									</button>
								</shiro:haspermission>
							</div>
							<div class="col-md-5 column">
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
