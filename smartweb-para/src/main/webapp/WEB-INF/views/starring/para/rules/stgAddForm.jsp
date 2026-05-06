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
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/para/rules/stgAddForm.js" charset="utf-8"></script>

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
						<h4 class="" contenteditable="false">
							存储规则
						</h4>
					</div>
				</div>
				<div class="panel-body" contenteditable="false">
					<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="addForm">
						<div ravo="rainbow_fx_layout" class="row clearfix" id="colForm">
							<div class="col-md-3 column">
							</div>
							<div class="col-md-8 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">
										中文名称：
									</label>
									<div class="col-sm-5">
										<input type="text" class="form-control" placeholder="" id="chName" name="chName"
										check-empty="true" check-minlength="2" maxlength="8" >
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">
										英文名称：
									</label>
									<div class="col-sm-5">
										<input type="text" class="form-control" placeholder="" id="engName" name="engName"
										check-empty="true" check-minlength="2" maxlength="16" check-alphanumericsymbols="true">
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">
										表名：
									</label>
									<div class="col-sm-5">
										<input type="text" class="form-control" placeholder="" id="tabName" name="tabName"
										check-empty="true" check-minlength="2" maxlength="20" check-alphanumericsymbols="true">
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="syncFlg" class="col-sm-2 control-label">
										同数据库标志：
									</label>
									<div class="col-sm-5">
										<select data-role="multiselect" id="sameDbFlg" class="" name="sameDbFlg"
												data-url="${ctx}/sys/dict/selectData?type=SAME_DB_FLG">
										</select>
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group divSrcDataSrc">
									<label for="inputEmail3" class="col-sm-2 control-label">
										来源数据源：
									</label>
									<div class="col-sm-5">
										<input type="text" class="form-control" placeholder="" id="srcDataSrc" name="srcDataSrc"
										  check-alphanumericsymbols="true">
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="busiNoFlg" class="col-sm-2 control-label">
										业务编号标志：
									</label>
									<div class="col-sm-5">
										<select data-role="multiselect" id="busiNoFlg" class="" name="busiNoFlg"
												data-url="${ctx}/sys/dict/selectData?type=BUSI_NO_FLG">
										</select>
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group" id="divcacheCertrId">
									<label for="inputEmail3" type="hidden" class="col-sm-2 control-label" >
										所属缓存中心：
									</label>
									<div class="col-sm-5">
										<select data-role="multiselect" id="cacheCentrId" class="" name="cacheCentrId">
											<option selected="selected" value="1">缓存节点
											</option>
										</select>
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group" id="divstorgRuleTp">
									<label for="storgRuleTp" type="hidden" class="col-sm-2 control-label">
										存储规则类型：
									</label>
									<div class="col-sm-5">
										<select data-role="multiselect" id="storgRuleTp" class="" name="storgRuleTp">
												<option value="00">参数规则
												</option>
										</select>
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group readAuthLevel" id="divreadAuthLvl">
									<input type="hidden" class="form-control" placeholder="" id="readAuthLvl" name="readAuthLvl" value="00">
									<label for="inputEmail3"  class="col-sm-2 control-label">
										读取等级：
									</label>
									<div class="col-sm-5">
										<select  data-role="multiselect" id="readAuthLvl_str" class="" name="readAuthLvl_str"
												 data-async="true">
												 <option value="00">公共
												 </option>
										</select>
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-2 control-label">
										唯一索引组合：
									</label>
									<div class="col-sm-5">
										<input type="text" class="form-control" placeholder="" maxlength="64" id="uniqKey" name="uniqKey" readonly="readonly">
									</div>
								</div>
							</div>
							<div class="col-md-1 column">
								<input type="hidden" id="colJson" name='colJson' value="">
							</div>
						</div>
					</form>
				</div>
			</div>
			<div ravo="rainbow_fx_layout_panel" class="panel panel-default">
				<div class="panel-heading">
					<div ravo="rainbow_fx_bj">
						<h4 class="" contenteditable="false">
							数据信息
						</h4>
					</div>
				</div>
				<div class="panel-body" contenteditable="false">
					<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" >
						<div id="colDiv">
							<div ravo="rainbow_fx_layout" class="row clearfix colInfo">
								<div class="col-md-1 column">
								</div>
								<div class="col-md-3 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											列名：
										</label>
										<div class="col-sm-8">
											<input type="text" class="form-control" placeholder="" id="colId" name="colId" check-empty="true" check-minlength="2" check-alphanumericsymbols="true"  maxlength="30">
										</div>
									</div>
								</div>
								<div class="col-md-3 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											中文描述：
										</label>
										<div class="col-sm-8">
											<input type="text" class="form-control" placeholder="" id="colName" name="colName" check-empty="true"  check-minlength="2" maxlength="16">
										</div>
									</div>
								</div>
								<div class="col-md-5 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											数据类型：
										</label>
										<div class="col-sm-8">
											<select data-role="multiselect" id="dataTp" class="" name="dataTp" data-url="${ctx}/sys/dict/selectData?type=DATA_TP" blank-item="true" blank-text="请选择" checkBtn="dataTp">
											</select>
										</div>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix colInfo">
								<div class="col-md-1 column">
								</div>
								<div class="col-md-3 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">
											长度：
										</label>
										<div class="col-sm-8">
											<input type="text" class="form-control" placeholder="" id="length" name="length" >
										</div>
									</div>
								</div>
								
								<div class="col-md-6 column check" style="margin:0px 0px 0px -50px" >
									<div ravo="rainbow_fx_checkbox" class="form-group divCheckbox">
										<label for="inputEmail3" class="col-sm-1 control-label">
										</label>
										<div class="col-sm-3">
											<div class="checkbox-inline">
												<label style="visibility:visible">
													<input type="checkbox" value="Y" id="isNotNull" name="isNotNull">
													是否可空
												</label>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="checkbox-inline">
												<label style="visibility:visible">
													<input type="checkbox" class="uniqueKey" value="Y" id="isUnique" name="isUnique">
													唯一索引
												</label>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-2 column">
								</div>
							</div>
						</div>	
						<div ravo="rainbow_fx_layout col" class="row clearfix">
							<div class="col-md-4 column">
							</div>
							<div class="col-md-3 column add">
								<a href="javascript:;" name="add" class="btn  btn-xs btn-primary addParam"  data-toggle="tooltip" data-placement="top"  ><span class="glyphicon glyphicon-plus" style="margin-top:5px">添加字段</span></a>
								
							</div>
							<div class="col-md-3 column edit">
								<a href="javascript:;" name="add" class="btn  btn-xs btn-primary editParam"  data-toggle="tooltip" data-placement="top"><span class="glyphicon glyphicon-edit" style="margin-top:5px">确认修改</span></a>
							</div>
							<div class="col-md-2 column">
							</div>
						</div>
						
						<div ravo="rainbow_fx_layout" class="row clearfix"
							style="margin-bottom: 20px; margin-top: 20px">
							<div class="col-md-12 column">
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<label for="inputEmail3"
										class="col-sm-1 control-label control-label"
										style="visibility: visible; margin-left: 12%"></label>
									<div class="col-sm-7">
										<table id="table" data-toggle="table" data-first-load="false"
											data-click-to-select="true" data-show-export="false"
											data-show-refresh="false" data-show-toggle="false"
											data-show-columns="false" data-pagination="false"
											data-search="false" data-method="post"
											data-undefined-text="**" data-height="300"
											data-content-type="application/x-www-form-urlencoded"
											ravo="rainbow_fx_bj" class="table table-hover"
											data-single-select="true" style="border: 1px solid #9EBED7;">
											<thead style="">
												<tr>
													<th data-field="colNo">列名</th>
													<th data-field="colName">中文描述</th>
													<th data-field="dataTp">数据类型</th>
													<th data-field="colLen">长度</th>
													<th data-field="nullFlg" data-visible="false">是否可空</th>
													<th data-field="uniqFlg" data-visible="false">唯一索引</th>
													<th data-field="colSer" data-visible="false">序号</th>
													<th data-field="action">操作</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
							</div>
						</div>
						
						<div ravo="rainbow_fx_layout" class="row clearfix" >
							<div class="col-md-4 column">
							</div>
							<div class="col-md-4 column" style="margin: 20px 0px 0px 0px">
								<div ravo="rainbow_fx_layout" class="row clearfix" >
									<div class="col-md-4 column">
									</div>
									<div class="col-md-3 column">
										<button ravo="rainbow_fx" type="button" class="btn btn-primary" contenteditable="false" id="saveBtn">
											提交
										</button>
									</div>
									<div class="col-md-3 column">
										<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false" id="cancleBtn">
											返回
										</button>
									</div>
									<div class="col-md-2 column">
									</div>
								</div>
							</div>
							<div class="col-md-4 column">
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
