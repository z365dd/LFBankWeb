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
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/prod/oper/cloudpay/payconf/payFaceConfAddForm.js" charset="utf-8"></script>

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
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<input type="hidden" id="colJson" name="colJson"/>
					<div class="col-md-2 column"></div>
					<div class="col-md-8 column" id="payConf">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								可售产品
							</label>
							<div class="col-sm-4">
								<select data-role="multiselect" id="saleProdCode" class="" name="saleProdCode" data-max-height="300"  checkBtn="saleProdCode"
									data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" 
									data-filter-placeholder="搜索">
									<option value="">
										请选择
									</option>
								</select>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-2 column"></div>
							<div class="col-md-4 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label">
											查询服务</label>
									<div class="col-sm-4 btn-group">
										<select data-role="multiselect" id="qrySvcCode" class="" name="qrySvcCode" data-max-height="300" 
											data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" >
										<option value="">
											请选择
										</option>
									</select>
									</div>
								</div>
							</div>
							<div class="col-md-4 column" >
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label" style="padding-left:1%;">
											缴费服务</label>
									<div class="col-sm-4 btn-group">
										<select data-role="multiselect" id="paySvcCode" class="" name="paySvcCode" data-max-height="300" checkBtn="paySvcCode"
											data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" >
										<option value="">
											请选择
										</option>
									</select>
									</div>
								</div>
							</div>
							<div class="col-md-1 column">
							</div>
						</div>
					</div>
				</div>
				
				
				<div ravo="rainbow_fx_layout" class="row clearfix"
					style="border-top: solid #ddd 1px; ">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group"></div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group"></div>
					</div>
				</div>
				
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-2 column"></div>
					<div class="col-md-8 column">
						<div ravo="rainbow_fx_layout" class="row clearfix" id="colDiv">
							<div class="col-md-2 column"></div>
							<div class="col-md-4 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label">
											界面属性</label>
									<div class="col-sm-4 btn-group">
										<select data-role="multiselect" id="keyNo" class="" name="keyNo" check-empty="true" data-max-height="300" 
											data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索"
											checkBtn="keyNo">
										<option value="">
											请选择
										</option>
									</select>
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label">
											订单金额</label>
									<div class="col-sm-4 btn-group">
<%--										<select data-role="multiselect" id="ordAmtFlg" class="" name="ordAmtFlg" check-empty="true" data-max-height="300" --%>
<%--											data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" --%>
<%--											checkBtn="ordAmtFlg">--%>
<%--										<option value="">--%>
<%--											请选择--%>
<%--										</option>--%>
<%--										<option value="Y">--%>
<%--											是--%>
<%--										</option>--%>
<%--										<option value="N">--%>
<%--											否--%>
<%--										</option>--%>
<%--									</select>--%>
										<select data-role="multiselect" id="ordAmtFlg" name="ordAmtFlg" data-url="${ctx}/sys/dict/selectData?type=ORD_AMT_FLG" data-max-height="300" blank-item="true"
												data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="ordAmtFlg">
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-4 column" >
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label" style="padding-left:1%;">
											录入标志</label>
									<div class="col-sm-4 btn-group">
<%--										<select data-role="multiselect" id="enterFlg" class="" name="enterFlg" data-max-height="300" --%>
<%--											data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" --%>
<%--											checkBtn="enterFlg">--%>
<%--										<option value="">--%>
<%--											请选择--%>
<%--										</option>--%>
<%--										<option value="01">--%>
<%--											可输--%>
<%--										</option>--%>
<%--										<option value="02">--%>
<%--											不可输--%>
<%--										</option>--%>
<%--									</select>--%>
										<select data-role="multiselect" id="enterFlg" name="enterFlg" data-url="${ctx}/sys/dict/selectData?type=ENTER_FLG" data-max-height="300" blank-item="true"
												data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="enterFlg">
										</select>
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label" style="padding-left:1%;">
											是否触发查询</label>
									<div class="col-sm-4 btn-group">
										<select data-role="multiselect" id="trgQryFlg" class="" name="trgQryFlg" data-max-height="300" 
											data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" 
											checkBtn="trgQryFlg">
										<option value="">
											请选择
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
							<div class="col-md-1 column">
								<button ravo="rainbow_fx" type="button" class="btn btn-info" contenteditable="false"
									id="addBtn">
									添加
								</button>
							</div>
							<div class="col-md-1 column">
							</div>
						</div>
						
						<div ravo="rainbow_fx_layout" class="row clearfix"
							style="margin-bottom: 20px; margin-top: 20px">
							<div class="col-md-12 column">
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<label for="inputEmail3"
										class="col-sm-1 control-label control-label"
										style="visibility: visible; margin-left: 10%"></label>
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
													<th data-field="keyNo">属性</th>
													<th data-field="keyName">属性名称</th>
													<th data-field="enterFlg" data-formatter="enterFlgFormat">录入标志</th>
													<th data-field="ordAmtFlg" data-formatter="flgFormat">是否为订单金额</th>
													<th data-field="trgQryFlg" data-formatter="flgFormat">是否触发查询</th>
													<th data-field="action">操作</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
							</div>
						</div>
						
						
						
					</div>
					<div class="col-md-2 column"></div>
				</div>
				
				
				
				<div ravo="rainbow_fx_layout" class="row clearfix"
					style="border-top: solid #ddd 1px; ">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group"></div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group"></div>
					</div>
				</div>
				
				<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:20px">
					<div class="col-md-3 column"></div>
					<div class="col-md-8 column">
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-2 column">
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
							<div class="col-md-2 column">
							</div>
						</div>
					</div>
					<div class="col-md-1 column"></div>
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
