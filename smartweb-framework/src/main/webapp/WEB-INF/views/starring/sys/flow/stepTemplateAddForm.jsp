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
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/sys/flow/stepTemplateAddForm.js" charset="utf-8"></script>

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
			<form ravo="rainbow_fx_layout_bd" class="form-horizontal breadcrumb" pourl=""
			id="addForm" style="margin-top:10px;">
				<div ravo="rainbow_fx" class="form-group">
					<label for="inputEmail3" class="col-sm-3 control-label">
						中文名称
					</label>
					<div class="col-sm-4">
						<input id="name" name="name" class="form-control" maxlength="32" check-empty="true"
						type="text" check-chinesedigitalsymbols="true">
					</div>
				</div>
				<div ravo="rainbow_fx" class="form-group">
					<label for="inputEmail3" class="col-sm-3 control-label">
						英文名称
					</label>
					<div class="col-sm-4">
						<input id="engName" name="engName" class="form-control" maxlength="32" check-empty="true"
						type="text" check-character="true">
					</div>
				</div>
				<div ravo="rainbow_fx" class="form-group">
					<label for="inputEmail3" class="col-sm-3 control-label">
						流程模板
					</label>
					<div class="col-sm-7 btn-group col-sm-4">
						<select data-role="multiselect" id="flowTmplId" class="" name="flowTmplId"
						checkbtn="flowTmplId" data-async="false" data-url="${ctx}/sys/flow/template/selectData?stat=2"
						blank-item="true" blank-text="--请选择流程模板--">
						</select>
					</div>
				</div>
				<div ravo="rainbow_fx" class="form-group">
					<label for="inputEmail3" class="col-sm-3 control-label" id="pre">
						上一步
					</label>
					<div class="col-sm-7 btn-group col-sm-4">
						<select data-role="multiselect" id="prvStepSer" class="" name="prvStepSer"
						data-url="${ctx}/sys/flow/stepTemplate/selectData" data-async="false" blank-item="true"
						blank-value="0" blank-text="总流程开始">
						</select>
					</div>
				</div>
				<div ravo="rainbow_fx" class="form-group">
					<label for="inputEmail3" class=" col-sm-3 control-label">
						通过标准
					</label>
					<div class="col-sm-7 btn-group col-sm-4">
						<select data-role="multiselect" id="flowApprFlg" class="" name="flowApprFlg"
						data-enable-filtering="true" data-url="${ctx}/sys/dict/selectData?type=FLOW_APPR_FLG"
						data-async="false" checkbtn="flowApprFlg" blank-item="true" blank-text="--请选择通过标准--">
						</select>
					</div>
				</div>
				<div ravo="rainbow_fx" class="form-group">
					<label for="inputEmail3" class="col-sm-3 control-label control-label"
					id="succNumLabel">
						通过用户比例(%)
					</label>
					<div class="col-sm-2">
						<input type="text" class="form-control" placeholder="" id="succNum"
						name="succNum" value="" maxlength="3" max="100" min="1">
					</div>
				</div>
				<div ravo="rainbow_fx" class="form-group">
					<label for="inputEmail3" class="col-sm-3 control-label control-label"
					style="visibility:visible">
						最长处理时间(单位:天，0表示不限制)
					</label>
					<div class="col-sm-2">
						<input type="text" class="form-control" placeholder="" name="dayNum" id="dayNum"
						check-empty="true" maxlength="10" value="0" nexttype="checkbox" data-bv-="true"
						data-original-title="" title="">
					</div>
				</div>
				<div ravo="rainbow_fx" class="form-group">
					<label for="inputEmail3" class=" col-sm-3 control-label">
						超时处理
					</label>
					<div class="col-sm-7 btn-group col-sm-4">
						<select data-role="multiselect" id="flowTimeOutFlg" class="" name="flowTimeOutFlg"
						data-url="${ctx}/sys/dict/selectData?type=FLOW_TIME_OUT_FLG" data-async="false"
						blank-item="true" data-enable-filtering="false" blank-text="--请选择超时处理--">
						</select>
					</div>
				</div>
				<div ravo="rainbow_fx" class="form-group">
					<label for="inputEmail3" class="col-sm-3 control-label" style="visibility:visible">
						超时处理用户类型
					</label>
					<div class="col-sm-7 btn-group col-sm-4">
						<select data-role="multiselect" id="timeOutFlowUserTp" class="" name="timeOutFlowUserTp"
						checkbtn="" data-bv-="true" data-enable-filtering="false" data-enable-full-value-filtering="true"
						data-url="${ctx}/sys/dict/selectData?type=TIME_OUT_FLOW_USER_TP" blank-item="true"
						data-async="false" blank-text="--请选择超时处理用户类型--">
						</select>
					</div>
				</div>
				<div ravo="rainbow_fx" class="form-group">
					<label class="control-label  control-label col-sm-3 control-label" style="visibility:visible">
						超时处理用户
					</label>
					<div class="       col-sm-4">
						<sys:treeselect id="timeOutProcUserId" name="timeOutProcUserId" value="timeOutProcUserIdValue"
						label_name="timeOutProcUserIdName" label_value="" title="用户" url="/sys/office/treeData?type=3"
						allow_clear="true" css_class="form-control input-small" treesearch_required="false">
						</sys:treeselect>
					</div>
				</div>
				<div ravo="rainbow_fx" class="form-group">
					<label for="inputEmail3" class="col-sm-3 control-label" style="visibility:visible">
						审批用户类型
					</label>
					<div class="col-sm-7 btn-group col-sm-4">
						<select data-role="multiselect" id="apprFlowUserTp" class="" name="apprFlowUserTp"
						checkbtn="apprFlowUserTp" data-async="false" blank-item="true" data-url="${ctx}/sys/dict/selectData?type=APPR_FLOW_USER_TP"
						data-bv-="true" blank-text="--请选择审批用户类型--">
						</select>
					</div>
				</div>
				<div ravo="rainbow_fx" class="form-group">
					<label class="control-label col-sm-3 control-label" style="visibility:visible">
						审批用户
					</label>
					<div class="     col-sm-4">
						<sys:treeselect id="apprUserId" name="apprUserId" value="applyUserIdValue"
						label_name="apprUserIdName" label_value="" title="用户" url="/sys/office/treeData?type=3"
						allow_clear="true" css_class="form-control input-small" treesearch_required="true">
						</sys:treeselect>
					</div>
				</div>
				<div ravo="rainbow_fx" class="form-group">
					<label for="inputEmail3" class="col-sm-3 control-label">
						步骤结束处理类
					</label>
					<div class="col-sm-7 btn-group col-sm-4">
						<select data-role="multiselect" id="flowProcClssTp" class="" name="flowProcClssTp"
						data-url="${ctx}/sys/dict/selectData?type=FLOW_PROC_CLSS_TP" data-async="false"
						blank-item="true" blank-text="--请选择步骤结束处理类--">
						</select>
					</div>
				</div>
				<div ravo="rainbow_fx" class="form-group">
					<label for="inputEmail3" class=" col-sm-3 control-label" style="visibility:visible">
						描述信息
					</label>
					<div class="  col-sm-4">
						<textarea class="form-control" rows="3" id="rmrk" name="rmrk" maxlength="80">
						</textarea>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="column col-md-3">
					</div>
					<div class="col-md-2 column">
						<shiro:haspermission name="anno">
							<button ravo="rainbow_fx" type="button" class="btn btn-info" contenteditable="false"
							id="saveBtn">
								保存
							</button>
						</shiro:haspermission>
					</div>
					<div class="col-md-2 column">
						<shiro:haspermission name="anno">
							<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
							id="cancleBtn">
								取消
							</button>
						</shiro:haspermission>
					</div>
					<div class="col-md-2 column">
					</div>
					<div class="col-md-2 column">
					</div>
					<div class="column col-md-1">
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
