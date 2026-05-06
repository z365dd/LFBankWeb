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
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/para/rules/stgGrantForm.js" charset="utf-8"></script>

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
				<div class="panel-heading">
					<div ravo="rainbow_fx_bj">
						<h3 class="" contenteditable="false">
							存储规则授权
						</h3>
					</div>
				</div>
				<div class="panel-body" contenteditable="false">
					<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="grantForm">
						<div ravo="rainbow_fx_layout" class="row clearfix" style="margin: 0px 0px 50px 0px">
							<input type="text" class="form-control" placeholder="" id="id" name="id" style="display: none;">
							<input type="text" class="form-control" placeholder="" id="tntNo" name="tntNo" style="display: none;">
							<div class="col-md-1 column">
							</div>
							<div class="col-md-8 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">
										中文名称：
									</label>
									<div class="col-sm-5">
										<input type="text" class="form-control" placeholder="" id="chName" name="chName" readonly="readonly"
										check-empty="true" check-minlength="2" maxlength="8" check-chinese="true">
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">
										英文名称：
									</label>
									<div class="col-sm-5">
										<input type="text" class="form-control" placeholder="" id="engName" name="engName" readonly="readonly"
										check-empty="true" check-minlength="2" maxlength="16" check-alphanumericsymbols="true">
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">
										所属缓存中心：
									</label>
									<div class="col-sm-5">
										<select data-role="multiselect" id="center" class="" name="center" checkbtn="ture">
											<option value="#">
												请选择
											</option>
										</select>
									</div>
									<input type="hidden" class="form-control" placeholder=""  name="cacheCentrId" id="cacheCentrId">
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<input type="hidden" class="form-control" placeholder="" id="storgRuleTp" name="storgRuleTp">
									<label for="storgRuleTp" class="col-sm-2 control-label">
										存储规则类型：
									</label>
									<div class="col-sm-5">
										<select data-role="multiselect" id="storgRuleType" class="" name="storgRuleType"
												data-url="${ctx}/sys/dict/selectData?type=STORG_RULE_TP" data-async="true" disabled="disabled">
										</select>
									</div>
								</div>
								<div class="storgRuleTpParam">
									<div ravo="rainbow_fx" class="form-group">
	<%--									<input type="text" class="form-control" placeholder=""  name="readAuthLvl" style="display: none;">--%>
										<label for="inputEmail3" class="col-sm-2 control-label">
											读取等级：
										</label>
										<div class="col-sm-5">
											<select data-role="multiselect" id="readAuthLvl" class="" name="readAuthLvl">
												<option value="00">公共</option>
												<option value="01">租户</option>
											</select>
										</div>
									</div>
									<div ravo="rainbow_fx" class="form-group authlist">
										<label for="inputEmail3" class="col-sm-2 control-label">
											租户列表：
										</label>
										<div class="col-sm-1 readAuthListInput">
											<input type="text" class="form-control" placeholder="" id="readAuthListInput" name="readAuthListInput" disabled="disabled">
										</div>
										<input type="text" class="form-control" placeholder="" id="readListStr" name="readListStr" style="display: none;">
										<div class="col-sm-5 readAuthList" style="display: none;">
											<select data-role="multiselect" id="readAuthList" class="" name="readAuthList" multiple="multiple"
												data-select-all-text="全部选择" data-include-select-all-option="true"
												data-non-selected-text="请选择"  data-max-height="300"
												data-all-selected-text="已选全部" data-n-selected-text="个已选" data-number-displayed="5"
												checkbtn="ture"
												data-enable-filtering="true" data-enable-full-value-filtering="false" data-filter-placeholder="搜索">
											</select>
										</div>
									</div>
									<div ravo="rainbow_fx" class="form-group" style="display: none">
										<label for="inputEmail3" class="col-sm-2 control-label">
											可写参与者列表：
										</label>
										<input type="text" class="form-control" placeholder="" id="writeListStr" name="writeListStr" style="display: none;">
										<div class="col-sm-5">
											<select data-role="multiselect" id="writeAuthList" class="" name="writeAuthList" multiple="multiple"
												data-select-all-text="全部选择" data-include-select-all-option="true"
												data-non-selected-text="请选择"  data-max-height="300"
												data-all-selected-text="已选全部" data-n-selected-text="个已选" data-number-displayed="5"
												checkbtn="ture"
												data-enable-filtering="true" data-enable-full-value-filtering="false" data-filter-placeholder="搜索">
											</select>
										</div>
									</div>
								</div>
								<div class="storgRuleTpCache">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-2 control-label">
											租户列表：
										</label>
										<div class="col-sm-1 readAuthListInput">
											<input type="text" class="form-control" placeholder="" id="readAuthListInput" name="readAuthListInput" disabled="disabled">
										</div>
										<div class="col-sm-5 readAuthList" style="display: none;">
											<select data-role="multiselect" id="tntNolist" class="" name="tntNolist"
													data-select-all-text="全部选择" data-include-select-all-option="true"
													data-non-selected-text="请选择"  data-max-height="300"
													data-all-selected-text="已选全部" data-n-selected-text="个已选" data-number-displayed="5"
													checkbtn="ture"
													data-enable-filtering="true" data-enable-full-value-filtering="false" data-filter-placeholder="搜索">
											</select>
										</div>
									</div>
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-2 control-label">
											参与者列表：
										</label>
										<div class="col-sm-5">
											<select data-role="multiselect" id="partList" class="" name="partList"
													data-select-all-text="全部选择" data-include-select-all-option="true"
													data-non-selected-text="请选择"  data-max-height="300"
													data-all-selected-text="已选全部" data-n-selected-text="个已选" data-number-displayed="5"
													checkbtn="ture"
													data-enable-filtering="true" data-enable-full-value-filtering="false" data-filter-placeholder="搜索">
											</select>
										</div>
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">
										唯一索引组合：
									</label>
									<div class="col-sm-5">
										<input type="text" class="form-control" placeholder="" id="uniqKey" name="uniqKey" readonly="readonly">
									</div>
								</div>
							</div>
							<div class="col-md-3 column">
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix" >
							<div class="col-md-4 column" style="margin: 20px 0px 0px 0px">
								<div ravo="rainbow_fx_layout" class="row clearfix" >
									<div class="col-md-4 column">
									</div>
									<div class="col-md-2 column">
										<button ravo="rainbow_fx" type="button" class="btn btn-primary" contenteditable="false" id="saveBtn">
											确定
										</button>
									</div>
									<div class="col-md-2 column">
									</div>
									<div class="col-md-2 column">
										<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false" id="cancleBtn">
											返回
										</button>
									</div>
									<div class="col-md-2 column">
									</div>
								</div>
							</div>
							<div class="col-md-8 column">
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
