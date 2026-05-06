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
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/tseq/oper/tTseqSeqCrtAddForm.js" charset="utf-8"></script>

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
			id="addForm" style="margin-top: 10px;">
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label control-label">
								流水号生成器名称 <font color="red">*</font>
							</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" placeholder="" id="seqCrtName"
								name="seqCrtName" check-empty="true" maxlength="30">
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
								外部系统号 <font color="red">*</font>
							</label>
							<div class="col-sm-4">
								<select id="outSys" name="outSys" data-role="multiselect" data-url="${ctx}/comp/tseq/oper/tTseqSeqCrt/getRelatSys"
								data-max-height="300" blank-item="true" blank-text="--请选择--" checkbtn="outSys">
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								外部子系统号  
							</label>
							<div class="col-sm-4">
								<input id="outSubSys" name="outSubSys" class="form-control " maxlength="10"
								check-alphanumericSymbols="true">
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label">
								节点号长度 <font color="red">*</font>
							</label>
							<div class="col-sm-4">
								<input id="nodeLen" name="nodeLen" class="form-control  number"
								check-integer="true" maxlength="10" check-empty="true" readonly="readonly">
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								流水号长度 <font color="red">*</font>
							</label>
							<div class="col-sm-4">
								<input id="seqNoLen" name="seqNoLen" class="form-control  number" maxlength="10"
								check-integer="true" check-empty="true">
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label">
								流水号总长度 <font color="red">*</font>
							</label>
							<div class="col-sm-4">
								<input id="respSeqLen" name="respSeqLen" class="form-control  number"
								data-toggle="popover" data-container="body" data-placement="right" data-content="节点号长度+流水号长度"
								title="流水号总长度" data-trigger="focus"
								check-integer="true" maxlength="19" check-empty="true" readonly="readonly">
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								步长 <font color="red">*</font>
							</label>
							<div class="col-sm-4">
								<input id="seqLen" name="seqLen" class="form-control  number" maxlength="19"
								check-integer="true" check-empty="true">
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label">
								最大值 <font color="red">*</font>
							</label>
							<div class="col-sm-4">
								<input id="maxVal" name="maxVal" class="form-control " maxlength="19"
								check-empty="true" check-integer="true">
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								最小值 <font color="red">*</font>
							</label>
							<div class="col-sm-4">
								<input id="minVal" name="minVal" class="form-control " maxlength="19"
								check-empty="true" check-integer="true">
							</div>
						</div>
					</div>
				</div>
				
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label">
								是否使用表达式 <font color="red">*</font>
							</label>
							<div class="col-sm-4">
								<select id="efftFlg" name="efftFlg" data-role="multiselect" checkbtn="efftFlg">
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
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								表达式 
							</label>
							<div class="col-sm-4">
								<input id="starExpr" name="starExpr" class="form-control " type="text"
								maxlength="512" data-toggle="popover" data-container="body" data-placement="right"
								data-content="YYYY-当前年份  YY-当前年份后2位  MM-当前月份  DD-当前日期  hh-当前小时（24小时制） mm-当前分钟  ss-当前秒数  SSS-当前毫秒  SEQ-流水号"
								title="支持样式" data-trigger="focus" check-alphanumericSymbols="true">
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx_radio" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label">
								流水用完是否重置 <font color="red">*</font>
							</label>
							<div class="col-sm-5">
								<div class="radio radio-inline">
									<label style="visibility: visible">
										<input id="reptInsptStat0" type="radio" value="Y" name="reptInsptStat"
										class="" checkbtn="reptInsptStat">
										是
									</label>
								</div>
								<div class="radio radio-inline">
									<label style="visibility: visible">
										<input id="reptInsptStat1" type="radio" value="N" name="reptInsptStat"
										class="" checkbtn="reptInsptStat">
										否
									</label>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
					<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								重置周期 <font color="red">*</font>
							</label>
							<div class="col-sm-4">
								<input id="resetCyc" name="resetCyc" class="form-control number" maxlength="19" check-empty="true"
								value="0" data-toggle="popover" data-container="body" data-placement="right" data-content="以天为单位,值为0时，不重置"
								title="提示" data-trigger="focus" check-integer-pos-neg="true">
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label">
								是否纯数字 <font color="red">*</font>
							</label>
							<div class="col-sm-5">
								<div class="radio radio-inline">
									<label style="visibility: visible">
										<input id="digitFlg0" type="radio" value="Y" name="digitFlg" class=""
										checkbtn="digitFlg">
										是
									</label>
								</div>
								<div class="radio radio-inline">
									<label style="visibility: visible">
										<input id="digitFlg1" type="radio" value="N" name="digitFlg" class=""
										checkbtn="digitFlg">
										否
									</label>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								流水号生成器ID <font color="red">*</font>
							</label>
							<div class="col-sm-4">
								<input id="seqCrtId" name="seqCrtId" class="form-control " maxlength="60"
								check-empty="true" readonly="readonly">
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:10px;">
					<div class="column col-md-4">
					</div>
					<div class="col-md-2 column">
						<shiro:haspermission name="anno">
							<button ravo="rainbow_fx" type="button" class="btn btn-info" contenteditable="false"
							id="saveBtn">
								提交
							</button>
						</shiro:haspermission> 
					</div>
					<div class="col-md-2 column"> 
						<shiro:haspermission name="anno">
							<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
							id="cancleBtn">
								关闭
							</button>
						</shiro:haspermission>
					</div>
					<div class="col-md-2 column">
					</div>
					<div class="col-md-2 column">
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
