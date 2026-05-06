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
	src="<%=basePath%>/b_base/views/starring/comp/fsvr/tec/fileFmtInputFixAddForm.js"
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
			<div class="col-md-12 column" style="margin-top: 15px">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
					id="addForm">
					<div id="commDiv">
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-4 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">
										文件格式转换名称 </label>
									<div class="col-sm-6">
										<input type="text" class="form-control" id="fmtName"
											name="fmtName" check-empty="true">
									</div>
								</div>
							</div>
							<div class="col-md-4 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">
										组件 </label>
									<div class="col-sm-7">
										<select data-role="multiselect" id="compNo" name="compNo"
											data-max-height='300' checkbtn="compNo">
											<option value="">请选择</option>
											<option value="1">批量模型</option>
											<option value="2">签约元件</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-4 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">
										模板名称 </label>
									<div class="col-sm-7">
										<select data-role="multiselect" id="tempFmtNo"
											name="tempFmtNo" data-max-height='300' checkbtn="tempFmtNo">
											<option value="">请选择</option>
											<option value="">请先选择组件</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout_panel" class="panel panel-default">
							<div class="panel-heading">
								<div ravo="rainbow_fx_bj">
									<h4 contenteditable="false" class="">报文整体格式配置</h4>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-4 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">
										格式类型 </label>
									<div class="col-sm-7">
										<select data-role="multiselect" id="fileFmt" name="fileFmt"
											checkbtn="fileFmt">
											<option value="">请选择</option>
											<option value="1">定长</option>
											<option value="2">非定长</option>
											<option value="3">xml</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-4 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">
										文件编码 </label>
									<div class="col-sm-7">
										<select data-role="multiselect" id="fileCode" name="fileCode">
											<option value="">请选择</option>
											<option value="GBK">GBK</option>
											<option value="UTF-8">UTF-8</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-4 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">
										分隔符 </label>
									<div class="col-sm-7">
										<input type="text" class="form-control" id="fmtDltSym"
											name="fmtDltSym" check-empty="true">
									</div>
								</div>
							</div>
						</div>
					</div>
					<div id='confDiv'>
						<div ravo="rainbow_fx_layout_panel" class="panel panel-default">
							<div class="panel-heading">
								<div ravo="rainbow_fx_bj">
									<h4 contenteditable="false" class="">模板文件格式示例</h4>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-3 column">
								<div class="col-md-5 column"></div>
							</div>
							<div class="col-md-8 column">
								<div ravo="rainbow_fx" class="form-group">
									<div class="col-sm-11">
										<textarea class="form-control" id="tempFileFmtShow"
											name="tempFileFmtShow" rows="5" readonly="readonly">
								</textarea>
									</div>
								</div>
							</div>
						</div>
						<div id='headDiv'>
							<div ravo="rainbow_fx_layout_panel" class="panel panel-default">
								<div class="panel-heading">
									<div ravo="rainbow_fx_bj">
										<h4 contenteditable="false" class="">报文头尾格式配置</h4>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-4 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											位置 </label>
										<div class="col-sm-7">
											<select data-role="multiselect" id="flg_head" class=""
												name="flg_head" checkbtn="flg_head">
												<option value="">请选择</option>
												<option value="1">头</option>
												<option value="3">尾</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-4 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											字段名称 </label>
										<div class="col-sm-6">
											<input type="text" class="form-control" name="colName_head"
												id="colName_head" check-empty="true"
												 data-toggle="popover"  data-container="body"  data-placement="right"
												data-content="头：根节点.字段名  尾：根节点.字段名"
												title="xml报文头尾字段名称格式：" data-trigger="focus">
										</div>
									</div>
								</div>
								<div class="col-md-4 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											字段类型 </label>
										<div class="col-sm-7">
											<select data-role="multiselect" id="colTp_head"
												name="colTp_head" class="" checkbtn="colTp_head">
												<option value="">请选择</option>
												<option value="1">整型</option>
												<option value="2">浮点型</option>
												<option value="3">字符串</option>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-4 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											对齐方式 </label>
										<div class="col-sm-7">
											<select data-role="multiselect" id="alignMeth_head"
												name="alignMeth_head" class="" checkbtn="alignMeth_head">
												<option value="">请选择</option>
												<option value="1">左对齐，右补0</option>
												<option value="2">左对齐，右补空格</option>
												<option value="3">右对齐，左补0</option>
												<option value="4">右对齐，左补空格</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-4 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											长度 </label>
										<div class="col-sm-6">
											<input type="text" class="form-control" id="colLen_head"
												name="colLen_head" check-empty="true">
										</div>
									</div>
								</div>
								<div class="col-md-4 column"></div>
							</div>
							<div style="display: none;">
								<select data-role="multiselect" id="tempFlg_head" class=""
									name="tempFlg_head">
									<option value=""></option>
									<option value="1">头</option>
									<option value="3">尾</option>
								</select> <input type="text" class="form-control" id="tempSer_head"
									name="tempSer_head"><input type="text"
									class="form-control" id="chgList_head" name="chgList_head" />
									<input type="text"
									class="form-control" id="tempColName_head" name="tempColName_head" />
									<select data-role="multiselect" id="clobFlg_head" class=""
											name="clobFlg_head">
											<option value="0" selected>否</option>
											<option value="1">是</option>
										</select>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix"
								style="margin-bottom: 10px;">
								<div class="col-md-4 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											是否转换 </label>
										<div class="col-sm-2">
											<select data-role="multiselect" id="chgFlg_head"
												name="chgFlg_head" class="" checkbtn="chgFlg_head">
												<option value="">请选择</option>
												<option value="1">是</option>
												<option value="0" selected="selected">否</option>
											</select>
										</div>
										<div class="column col-md-2" id="chgBtnDiv_head"
											style="display: none">
											<shiro:haspermission name="anno">
												<button ravo="rainbow_fx" type="button"
													class="btn btn-default" contenteditable="false"
													id="reviceChgBtn">修改转换值</button>
											</shiro:haspermission>
										</div>
									</div>
								</div>
								<div class="column col-md-4">
									<div ravo="rainbow_fx" class="form-group" id="colChgDiv_head"
										style="display: none;">
										<div class="col-sm-4">
										</div>
										<div class="col-sm-6">
										<span name="colChgFlgTip2" class="promptScript">将多个字段转换成一个字段组成json串</span>
										<span name="colChgFlgTip1" class="promptScript">将json串拆解为多个字段</span>
										</div>
									</div>
								</div>
								<div class="column col-md-4">
									<div ravo="rainbow_fx" class="form-group">
										<div class="column col-md-3">
											<shiro:haspermission name="anno">
												<button ravo="rainbow_fx" type="button"
													class="btn btn-default" contenteditable="false"
													id="addRowBtn">新增</button>
											</shiro:haspermission>
										</div>
										<div class="col-md-3 column">
											<shiro:haspermission name="anno">
												<button ravo="rainbow_fx" type="button"
													class="btn btn-default" contenteditable="false"
													id="reviceRowBtn">确定修改</button>
											</shiro:haspermission>
										</div>
										<div class="column col-md-3">
											<shiro:haspermission name="anno">
												<button ravo="rainbow_fx" type="button"
													class="btn btn-default" contenteditable="false" id="cleanInputBtn">取消选中</button>
											</shiro:haspermission>
										</div>
										<%-- <div class="column col-md-3">
											<shiro:haspermission name="anno">
												<button ravo="rainbow_fx" type="button"
													class="btn btn-default" contenteditable="false" id="upBtn">上移</button>
											</shiro:haspermission>
										</div>
										<div class="column col-md-3">
											<shiro:haspermission name="anno">
												<button ravo="rainbow_fx" type="button"
													class="btn btn-default" contenteditable="false"
													id="downBtn">下移</button>
											</shiro:haspermission>
										</div> --%>
									</div>
								</div>

							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-2 column">
									<table id="tmplTable_head" data-toggle="table"
										data-first-load="false" data-url=""
										data-click-to-select="true" data-show-export="false"
										data-show-refresh="false" data-show-toggle="false"
										data-show-columns="false" data-pagination="false"
										data-search="false" data-query-params="queryParams"
										data-method="post" data-undefined-text="**" data-height="300"
										data-content-type="application/x-www-form-urlencoded"
										ravo="rainbow_fx_bj" class="table table-hover">
										<thead style="">
											<tr>
												<th data-field="action"></th>
												<th data-field="flgStr">模板位置</th>
												<th data-field="flg" data-visible="false">模板位置</th>
												<th data-field="ser" data-visible="false">模板序号</th>
												<th data-field="colName">字段名称</th>
												<th data-field="colTp" data-visible="false">字段类型</th>
											</tr>
										</thead>
									</table>
								</div>
								<div class="col-md-10 column">
									<table id="table_head" data-toggle="table"
										data-first-load="false" data-url=""
										data-click-to-select="true" data-show-export="false"
										data-show-refresh="false" data-show-toggle="false"
										data-show-columns="false" data-pagination="false"
										data-search="false" data-query-params="queryParams"
										data-method="post" data-undefined-text="**" data-height="300"
										data-content-type="application/x-www-form-urlencoded"
										ravo="rainbow_fx_bj" class="table table-hover">
										<thead style="">
											<tr>
												<th data-field="flgStr">位置</th>
												<th data-field="flg" data-visible="false">位置</th>
												<th data-field="ser">序号</th>
												<th data-field="colName">字段名称</th>
												<th data-field="colLen" data-visible="false">字段长度</th>
												<th data-field="alignMethStr" data-visible="false">对齐标志</th>
												<th data-field="alignMeth" data-visible="false">对齐标志的值</th>
												<th data-field="colTp">字段类型</th>
												<th data-field="chgFlgStr">是否转换</th>
												<th data-field="chgFlg" data-visible="false">是否转换的值</th>
												<th data-field="clobFlg" data-visible="false">是否大字段</th>
												<th data-field="tempFlgStr">模板位置</th>
												<th data-field="tempColName">模板值</th>
												<th data-field="tempFlg" data-visible="false">模板位置</th>
												<th data-field="tempSer" data-visible="false">模板序号</th>
												<th data-field="list" data-visible="false">转换值枚举列表</th>
												<th data-field="action">操作</th>
											</tr>
										</thead>
									</table>
								</div>
							</div>
						</div>
						<div id="bodyDiv">
							<div ravo="rainbow_fx_layout_panel" class="panel panel-default">
								<div class="panel-heading">
									<div ravo="rainbow_fx_bj">
										<h4 contenteditable="false" class="">报文体格式配置</h4>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-4 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											位置 </label>
										<div class="col-sm-7">
											<select data-role="multiselect" id="flg_body" class=""
												name="flg_body" checkbtn="flg_body">
												<option value="2">体</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-4 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											字段名称 </label>
										<div class="col-sm-6">
											<input type="text" class="form-control" name="colName_body"
												id="colName_body" check-empty="true"
												 data-toggle="popover"  data-container="body"  data-placement="right"
												data-content="体：根节点.体节点.字段名"
												title="xml报文体字段名称格式：" data-trigger="focus">
										</div>
									</div>
								</div>
								<div class="col-md-4 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											字段类型 </label>
										<div class="col-sm-7">
											<select data-role="multiselect" id="colTp_body"
												name="colTp_body" class="" checkbtn="colTp_body">
												<option value="">请选择</option>
												<option value="1">整型</option>
												<option value="2">浮点型</option>
												<option value="3">字符串</option>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-4 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											对齐方式 </label>
										<div class="col-sm-7">
											<select data-role="multiselect" id="alignMeth_body"
												name="alignMeth_body" class="" checkbtn='alignMeth_body'>
												<option value="">请选择</option>
												<option value="1">左对齐，右补0</option>
												<option value="2">左对齐，右补空格</option>
												<option value="3">右对齐，左补0</option>
												<option value="4">右对齐，左补空格</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-4 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											长度 </label>
										<div class="col-sm-6">
											<input type="text" class="form-control" id="colLen_body"
												name="colLen_body" check-empty="true">
										</div>
									</div>
								</div>
								<div class="col-md-4 column">
									<div style="display: none;">
										<select data-role="multiselect" id="tempFlg_body" class=""
											name="tempFlg_body">
											<option value=""></option>
											<option value="2">体</option>
										</select> <input type="text" class="form-control" id="tempSer_body"
											name="tempSer_body"> <input type="text"
											class="form-control" id="chgList_body" name="chgList_body" />
											<input type="text"
											class="form-control" id="tempColName_body" name="tempColName_body" />
											<select data-role="multiselect" id="clobFlg_body" class=""
											name="clobFlg_body">
											<option value="0" selected>否</option>
											<option value="1">是</option>
										</select>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix"
								style="margin-bottom: 10px;">
								<div class="col-md-4 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											是否转换 </label>
										<div class="col-sm-2">
											<select data-role="multiselect" id="chgFlg_body"
												name="chgFlg_body" class="" checkbtn='chgFlg_body'>
												<option value="">请选择</option>
												<option value="1">是</option>
												<option value="0" selected="selected">否</option>
											</select>
										</div>
										<div class="column col-md-2" id="chgBtnDiv_body"
											style="display: none">
											<shiro:haspermission name="anno">
												<button ravo="rainbow_fx" type="button"
													class="btn btn-default" contenteditable="false"
													id="reviceChgBtnBody">修改转换值</button>
											</shiro:haspermission>
										</div>
									</div>
								</div>
								<div class="column col-md-4">
									<div ravo="rainbow_fx" class="form-group" id="colChgDiv_body"
										style="display: none;">
										<div class="col-sm-4">
										</div>
										<div class="col-sm-6">
										<span name="colChgFlgTip2" class="promptScript">将多个字段转换成一个字段组成json串</span>
										<span name="colChgFlgTip1" class="promptScript">将json串拆解为多个字段</span>
										</div>
									</div>
								</div>
								<div class="column col-md-4">
									<div class="column col-md-3">
										<shiro:haspermission name="anno">
											<button ravo="rainbow_fx" type="button"
												class="btn btn-default" contenteditable="false"
												id="addRowBtnBody">新增</button>
										</shiro:haspermission>
									</div> 
									<div class="col-md-3 column">
										<shiro:haspermission name="anno">
											<button ravo="rainbow_fx" type="button"
												class="btn btn-default" contenteditable="false"
												id="reviceRowBtnBody">确定修改</button>
										</shiro:haspermission>
									</div>
									<div class="column col-md-3">
											<shiro:haspermission name="anno">
												<button ravo="rainbow_fx" type="button"
													class="btn btn-default" contenteditable="false" id="cleanInputBtnBody">取消选中</button>
											</shiro:haspermission>
										</div>
									<%-- <div class="column col-md-3">
										<shiro:haspermission name="anno">
											<button ravo="rainbow_fx" type="button"
												class="btn btn-default" contenteditable="false"
												id="upBtnBody">上移</button>
										</shiro:haspermission>
									</div>
									<div class="column col-md-3">
										<shiro:haspermission name="anno">
											<button ravo="rainbow_fx" type="button"
												class="btn btn-default" contenteditable="false"
												id="downBtnBody">下移</button>
										</shiro:haspermission>
									</div> --%>
								</div>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-2 column">
									<table id="tmplTable_body" data-toggle="table"
										data-first-load="false" data-url=""
										data-click-to-select="true" data-show-export="false"
										data-show-refresh="false" data-show-toggle="false"
										data-show-columns="false" data-pagination="false"
										data-search="false" data-query-params="queryParams"
										data-method="post" data-undefined-text="**" data-height="300"
										data-content-type="application/x-www-form-urlencoded"
										ravo="rainbow_fx_bj" class="table table-hover">
										<thead style="">
											<tr>
												<th data-field="action"></th>
												<th data-field="flgStr" data-visible="false">模板位置</th>
												<th data-field="flg" data-visible="false">模板位置</th>
												<th data-field="ser" data-visible="false">模板序号</th>
												<th data-field="colName">字段名称</th>
												<th data-field="colTp" data-visible="false">字段类型</th>
											</tr>
										</thead>
									</table>
								</div>
								<div class="col-md-10 column">
									<table id="table_body" data-toggle="table"
										data-first-load="false" data-url=""
										data-click-to-select="true" data-show-export="false"
										data-show-refresh="false" data-show-toggle="false"
										data-show-columns="false" data-pagination="false"
										data-search="false" data-query-params="queryParams"
										data-method="post" data-undefined-text="**" data-height="300"
										data-content-type="application/x-www-form-urlencoded"
										ravo="rainbow_fx_bj" class="table table-hover">
										<thead style="">
											<tr>
												<th data-field="flgStr">位置</th>
												<th data-field="flg" data-visible="false">位置</th>
												<th data-field="ser">序号</th>
												<th data-field="colName">字段名称</th>
												<th data-field="colLen" data-visible="false">字段长度</th>
												<th data-field="alignMethStr" data-visible="false">对齐标志</th>
												<th data-field="alignMeth" data-visible="false">对齐标志的值</th>
												<th data-field="colTp">字段类型</th>
												<th data-field="chgFlgStr">是否转换</th>
												<th data-field="chgFlg" data-visible="false">是否转换的值</th>
												<th data-field="clobFlg" data-visible="false">是否大字段</th>
												<th data-field="tempColName">模板值</th>
												<th data-field="tempFlg" data-visible="false">模板位置</th>
												<th data-field="tempSer" data-visible="false">模板序号</th>
												<th data-field="list" data-visible="false">转换值枚举列表</th>
												<th data-field="action">操作</th>
											</tr>
										</thead>
									</table>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-3 column">
								<div class="clearfix"></div>
							</div>
							<div class="col-md-9 column"></div>
						</div>
						<div ravo="rainbow_fx_layout_panel" class="panel panel-default">
							<div class="panel-heading">
								<div ravo="rainbow_fx_bj">
									<h4 contenteditable="false" class="">配置文件格式示例</h4>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-3 column">
								<div class="col-md-5 column"></div>
								<shiro:haspermission name="anno">
									<button ravo="rainbow_fx" type="button" class="btn btn-default"
										contenteditable="false" id="showBtn">显示配置的文件格式</button>
								</shiro:haspermission>
							</div>
							<div class="col-md-8 column">
								<div ravo="rainbow_fx" class="form-group">
									<div class="col-sm-11">
										<textarea class="form-control" id="fileFmtShow"
											name="fileFmtShow" rows="5" readonly="readonly">
								</textarea>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix"
						style="margin-bottom: 10px;">
						<div class="col-md-4 column"></div>
						<div class="column col-md-2">
							<shiro:haspermission name="anno">
								<button ravo="rainbow_fx" type="button" class="btn btn-default"
									contenteditable="false" id="saveBtn">保存</button>
							</shiro:haspermission>
						</div>
						<div class="column col-md-4">
							<shiro:haspermission name="anno">
								<button ravo="rainbow_fx" type="button" class="btn btn-default"
									contenteditable="false" id="cancelBtn">取消</button>
							</shiro:haspermission>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!--modal_code_beg-->
	<div class="modal fade text-center" id="modalDiv" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 60%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">转换值</h4>
				</div>
				<div class="modal-body">
					<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
						id="formId_794098">
						<input type="text" class="form-control" id="targetFlg"
							name="targetFlg" style="display: none;">
						<div ravo="rainbow_fx_layout" class="row clearfix"
							style="margin-bottom: 10px;">
							<div class="col-md-5 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-5 control-label">
										内部值 </label>
									<div class="col-sm-5">
										<input id="inKv" name="inKv" class="form-control ">
									</div>
								</div>
							</div>
							<div class="col-md-5 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label">
										外部值 </label>
									<div class="col-sm-5">
										<input id="outKv" name="" outKv"" class="form-control ">
									</div>
								</div>
							</div>
							<div class="column col-md-1">
								<shiro:haspermission name="anno">
									<button ravo="rainbow_fx" type="button" class="btn btn-default"
										contenteditable="false" id="addRowBtnModal">新增</button>
								</shiro:haspermission>
							</div>
						</div>
						<table id="table_modal" data-toggle="table"
							data-first-load="false" data-click-to-select="true"
							data-show-export="false" data-show-refresh="false"
							data-show-toggle="false" data-show-columns="false"
							data-search="false" data-query-params="queryParams"
							data-method="post" data-undefined-text="**" data-height="300"
							data-content-type="application/x-www-form-urlencoded"
							ravo="rainbow_fx_bj" class="table table-hover">
							<!-- data-side-pagination="server" data-striped="true" data-pagination="true" -->
							<thead style="">
								<tr>
									<th data-field="inKv">内部值</th>
									<th data-field="outKv">外部值</th>
									<th data-field="action">操作</th>
								</tr>
							</thead>
						</table>
					</form>
					<div class="modal-footer" style="margin-top: 15px">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="modalAddBtn">
							确定</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	</div>
	<!--modal_code_end-->
	<!-- view end -->
</body>
</html>
