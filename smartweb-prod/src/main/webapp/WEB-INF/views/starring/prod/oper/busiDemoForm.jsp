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
	src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/locale/bootstrap-table-zh-CN.js" ></script>
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/tableExport.js" ></script>
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

<script type="text/javascript"
	src="<%=basePath%>/b_base/common/smartweb.js"></script>

<!-- check -->
<script type="text/javascript"
	src="<%=basePath%>/b_base/common/checkReviceObj.js"></script>
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/b_base/common/formCheck.css" />

<!-- Self reference JS-->
<script type="text/javascript"
	src="<%=basePath%>/b_base/views/starring/prod/oper/attrCommon.js"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<%=basePath%>/b_base/views/starring/prod/oper/busiDemoForm.js"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<%=basePath%>/b_base/views/starring/prod/oper/modalEntrList.js"
	charset="utf-8"></script>	
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/b_base/views/starring/prod/oper/busiDemoForm.css" />

<!-- change skin -->
<link	href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css"	type="text/css" rel="stylesheet" />

<script type="text/javascript"
	src="<%=basePath%>/b_base/common/formCheck.js" charset="utf-8"></script>
<link href="<%=basePath%>/b_base/common/formCheck.css" media="all"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>/b_base/jquery-preview/js/control.js" charset="utf-8"></script>
<script type="text/javascript"
	src="<%=basePath%>/b_base/jquery-preview/js/jQueryRotate.js"
	charset="utf-8"></script>
<link href="<%=basePath%>/b_base/jquery-preview/css/jquery-preview.css"
	media="all" rel="stylesheet" type="text/css" />
<title>Insert title here</title>
</head>
<body>
	<div id="messageBox" class="alert alert-success hide">
		<button data-dismiss="alert" class="close">×</button>
		<span id="messageContent">操作提示信息</span>
	</div>
	<!-- view start -->
	<!-- 显示的配置页面 -->
	<div class="">
		<div ravo="rainbow_fx_layout" class="row clearfix">
			<div class="col-md-12 column">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
					id="formId_65948" style="margin-top: 20px">
					<input type="hidden" class="form-control" placeholder="" id="keyJson" name="keyJson">
					<input type="hidden" class="form-control" placeholder="" id="entrNo" name="entrNo">
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-4 control-label">
									单位名称 </label>
								<div class="col-sm-4">
									<select data-role="multiselect" id="ENTR_NAME" class=""
										name="ENTR_NAME" data-max-height="300" checkbtn="ENTR_NAME">
										<option value="">请选择</option>
									</select>
									<button ravo="rainbow_fx" type="button"
										class="btn btn-primary " contenteditable="false" id="entrSelectBtn">选择</button>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group" style="display:none">
								<label for="inputEmail3" class=" col-sm-4 control-label">
									单位编号 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="ENTR_NO" name="ENTR_NO" readonly>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-4 control-label">
									产品线</label>
								<div class="col-sm-4">
									<select data-role="multiselect" id="PROD_LINE_CODE" class=""
										name="PROD_LINE_CODE" data-max-height="300" checkbtn="PROD_LINE_CODE">
										<option value="">请选择</option>
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label class="control-label col-sm-4 control-label">
									业务图片 </label>
								<div class="col-sm-6" id="ckfinder">
									<sys:ckfinder input="bgImg" name="bgImg" value=""
												  type="images" upload_path="/prod/field" is_all_user="true"
												  ckfinder_required="false" select_multiple="false"
												  readonly="readonly" max_width="300" max_height="200">
									</sys:ckfinder>
								</div>
							</div>
							
						</div>
						<div class="col-md-6 column">
							
							<div ravo="rainbow_fx" class="form-group">
								<label class=" control-label col-sm-4 control-label">
									业务需求 </label>
								<div class="col-sm-8">
									<sys:ckfinder input="fileArea1" name="fileArea1" value=""
										type="files" upload_path="/prod/application"
										is_all_user="true" ckfinder_required="false"
										select_multiple="true" readonly="readonly" max_width="200"
										max_height="200">
									</sys:ckfinder>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label class=" control-label col-sm-4 control-label">
									业务接口 </label>
								<div class="col-sm-8">
									<sys:ckfinder input="fileArea2" name="fileArea2" value=""
										type="files" upload_path="/prod/application"
										is_all_user="true" ckfinder_required="false"
										select_multiple="true" readonly="readonly" max_width="200"
										max_height="200">
									</sys:ckfinder>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label class=" control-label col-sm-4 control-label">
									其他 </label>
								<div class="col-sm-8">
									<sys:ckfinder input="fileArea3" name="fileArea3" value=""
										type="files" upload_path="/prod/application"
										is_all_user="true" ckfinder_required="false"
										select_multiple="true" readonly="readonly" max_width="200"
										max_height="200">
									</sys:ckfinder>
								</div>
							</div>
						</div>
					</div>
					<div id="prodField"></div>
					<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
						id="">
						<div class="panel-heading">
							<div ravo="rainbow_fx_bj">
								<h4 contenteditable="false">通用属性</h4>
							</div>
						</div>
						<div class="panel-body" contenteditable="false">
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											业务编号 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												data-bv-="true" id="BUSI_NO" name="BUSI_NO"
												check-empty="true">
										</div>
									</div>
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											主管机构
										</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												   data-bv-="true" id="brchName" name="brchName"
												   check-empty="true">
										</div>
									</div>
								</div>
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											业务名称 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												data-bv-="true" id="BUSI_NAME" name="BUSI_NAME"
												check-empty="true">
										</div>
									</div>
								</div>

							</div>
							<div id="commonDiv">
								
							</div>
							<div  id="busiDiv"></div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix"
						style="margin-top: 20px">
						<div class="col-md-4 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-6 control-label">
								</label>
								<div class="col-sm-6">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
												class="btn btn-primary " contenteditable="false" id="addBtn">提交</button>
									</shiro:haspermission>
								</div>
							</div>
						</div>
						<div class="col-md-4 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">
								</label>
								<div class="col-sm-6">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
												class="btn btn-primary " contenteditable="false" id="effect">立即生效</button>
									</shiro:haspermission>
								</div>
							</div>
						</div>
						<div class="col-md-4 column">
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-6 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
											id="cancelBtn">返回</button>
									</shiro:haspermission>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<jsp:include flush="true" page="modalEntrList.jsp"></jsp:include>

	<!--customer_code_beg-->

	<!--customer_code_end-->
	<!-- view end -->
</body>
</html>
