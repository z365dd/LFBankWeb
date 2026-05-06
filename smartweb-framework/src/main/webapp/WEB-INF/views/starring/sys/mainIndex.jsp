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
<%--<meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">--%>

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
<link href="<%=basePath%>/b_base/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css"
	rel="stylesheet" />
<script src="<%=basePath%>/b_base/jquery-jbox/2.3/jquery.jBox-2.3.src.js"
	type="text/javascript"></script>

<!-- UEditor -->
<script src="<%=basePath%>/b_base/ueditor/ueditor.config.js"
	type="text/javascript"></script>
<script src="<%=basePath%>/b_base/ueditor/ueditor.all.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>/b_base/ueditor/ueditor.parse.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>/b_base/ueditor/lang/zh-cn/zh-cn.js"
	type="text/javascript"></script>

<!-- FontIcon -->
<link rel="stylesheet"
	href="<%=basePath%>/b_base/mainframe/fonticon/iconfont.css">



<!-- check -->
<script type="text/javascript"
	src="<%=basePath%>/b_base/common/check.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/b_base/common/formCheck.js"></script>
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/b_base/common/formCheck.css" />

<!-- Self reference JS-->


<!-- change skin -->
<link
	href="<%=basePath%>/b_base/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'tech'}/index.css"
	type="text/css" rel="stylesheet" />
<link
	href="<%=basePath%>/b_base/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'tech'}/mainIndex.css"
	type="text/css" rel="stylesheet" />
<link
	href="<%=basePath%>/b_base/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'tech'}/step.css"
	type="text/css" rel="stylesheet" />

<title>Insert title here</title>
</head>
<body>
	<div id="messageBox" class="alert alert-success hide">
		<button data-dismiss="alert" class="close">×</button>
		<span id="messageContent">操作提示信息</span>
	</div>
	<!-- view start -->

	<input id="screenReqFlag" name="screenReqFlag" type="hidden" value="${screenReqFlag}">
	<input id="startTime" name="startTime" type="hidden" value="${startTime}">
	<input id="endTime" name="endTime" type="hidden" value="${endTime}">
	<input id="glbl_biz_swfno" name="glbl_biz_swfno" type="hidden" value="${glbl_biz_swfno}">
	<input id="prvpt_cenmd_no" name="prvpt_cenmd_no" type="hidden" value="${prvpt_cenmd_no}">
	<input id="prvpt_mcrsv_no" name="prvpt_mcrsv_no" type="hidden" value="${prvpt_mcrsv_no}">
	<input id="cnsmr_mcrsv_swfno" name="cnsmr_mcrsv_swfno" type="hidden" value="${cnsmr_mcrsv_swfno}">
	<input id="transTmMin" name="transTmMin" type="hidden" value="${transTmMin}">
	<input id="transTmMax" name="transTmMax" type="hidden" value="${transTmMax}">
	<input id="mcrsv_fnct_intfc_ecd" name="mcrsv_fnct_intfc_ecd" type="hidden" value="${mcrsv_fnct_intfc_ecd}">
	<input id="trd_dlwth_retn_cd" name="trd_dlwth_retn_cd" type="hidden" value="${trd_dlwth_retn_cd}">
	<input id="scene_idcd" name="scene_idcd" type="hidden" value="${scene_idcd}">
	<input id="biz_lunch_org_ecd" name="biz_lunch_org_ecd" type="hidden" value="${biz_lunch_org_ecd}">
	<input id="chnl_typ_cd" name="chnl_typ_cd" type="hidden" value="${chnl_typ_cd}">
	<section class="index1">
		<div class="index1-2">
			<div class="k1">
				<div class="k1-1">
					<div class="k1-1">
						<div class="k1-1-1"></div>
						<div class="k1-1-2">快捷入口</div>
					</div>
					<div class="r1">
						<ul style="height: 100%;">
							<li class="r1-1" id="addMore" onclick="addQuickEntry();"><div class="r1-1-6"></div>
								<div class="r1-1-7">
									添加更多
								</div>
							</li>
						</ul>
						<div class="col-sm-4">
							<sys:treeselect2 id="addQuickEntry" name="addQuickEntry"
								value="addQuickEntryValue" label_name="addQuickEntryName"
								label_value="" title="菜单" url="/sys/menu/getSearchMenu"
								allow_clear="true" css_class="form-control input-small"
								disabled="" treesearch_required="false"
								not_allow_select_root="true" not_allow_select_parent="true" input_flag="true">
							</sys:treeselect2>
						</div>
					</div>
				</div>
			</div>
			<div class="k2">
				<div class="k2-1">
					<div class="k1-1-1"></div>
					<div class="k1-1-2">我的任务</div>
					<a href="#" onclick="moreInfo('moreTask')"><div class="k1-1-3"></div></a>
					<div style="width:93%; margin:0px autol;padding:25px 0 0 0">
						<ol id="myTask" class="ui-step ui-step-3">
						</ol>
					</div>
				</div>
			</div>
			<div class="k3"></div>
			<div class="k5">
				<div class="k3-1">
					<div class="k1-1-1"></div>
					<div class="k1-1-2">公告消息</div>
					<a href="#" onclick="moreInfo('moreNitoces')"><div class="k1-1-3"></div></a>
				</div>
				<div class="k1-2">
					<ul id="noticeData">
					</ul>
				</div>
			</div>
			<div class="k5">
				<div class="k3-1">
					<div class="k1-1-1"></div>
					<div class="k1-1-2">流程审批</div>
					<a href="#" onclick="moreInfo('moreFlows')"><div class="k1-1-3"></div></a>
				</div>
				<div class="k1-2">
					<ul id="flowData">
					</ul>
				</div>
			</div>
			<div class="k6">
				<div class="k6-1">
					<div class="k6-1-1"></div>
					<div class="k6-1-2"></div>
					<div class="k6-1-3"></div>
					<div class="k6-1-4"></div>
					<div class="k6-1-5"></div>
				</div>
				<div class="k6-2">
					<div class="k6-2-1">
						<img id="userPhoto" style='height:100%;'
							src="<%=basePath%>/b_base/mainframe/img/${not empty cookie.theme.value ? cookie.theme.value : 'tech'}/t1.png">
					</div>
					<div class="k6-2-2"></div>
					<div class="k6-2-2"></div>
					<div id="userName" class="k6-2-2"></div>
					<div id="roleName" class="k6-2-2"></div>
					<div id="brchName" class="k6-2-2"></div>
					<div id="tntName" class="k6-2-2"></div>
					<div id="legaName" class="k6-2-2"></div>
					<div id="loginIp" class="k6-2-2"></div>
					<div id="loginDate" class="k6-2-2"></div>
				</div>
			</div>
			<input type="hidden" id="pId" value="" />
			<input type="hidden" id="pName" value="" />
		</div>
	</section>

<script type="text/javascript"
	src="<%=basePath%>/b_base/common/smartweb.js"></script>	
	<script type="text/javascript"
	src="<%=basePath%>/b_base/views/starring/sys/mainIndex.js"
	charset="utf-8"></script>
</body>

<!--customer_code_beg-->

<!--customer_code_end-->


<!-- view end -->
</html>
