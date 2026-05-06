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
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/prod/oper/definition/adapter/tPipSaleProdAdapterUpdateForm.js" charset="utf-8"></script>

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
				id="tPipSaleProdAdapterForm" style="margin-top:10px;">
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-3 column"></div>
					<input type="hidden" class="form-control" placeholder="" id="keyJson" name="keyJson">
					<input type="hidden" class="form-control" placeholder="" id="id" name="id">
					<div class="col-md-8 column" id="form">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								产品线
							</label>
							<div class="col-sm-5">
								<select data-role="multiselect" id="prodLineCode" class="" name="prodLineCode" data-max-height="300"
										data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkBtn="prodLineCode">
									<option value="">
										请选择
									</option>
								</select>
							</div>
						</div>
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								可售产品名称
							</label>
							<div class="col-sm-5">
								<select data-role="multiselect" id="saleProdCode" class="" name="saleProdCode" data-max-height="300"
										data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkBtn="saleProdCode">
									<option value="">
										请选择
									</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-3 column"></div>
				</div>	
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-2 column"></div>
					<div class="col-md-9 column">	
						<div ravo="rainbow_fx_layout" class="row clearfix"
						style="margin-bottom: 20px; margin-top: 20px">
							<div class="col-md-12 column">
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<label for="inputEmail3"
										class="col-sm-1 control-label control-label"
										style="visibility: visible; "></label>
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
													<th data-field="keyTypeStr" >属性类型</th>
													<th data-field="keyNo">属性</th>
													<th data-field="keyName" >属性名称</th>
													<th data-field="keyTpStr">控件类型</th>
													<th data-field="action">操作</th>
													<th data-field="keyTp" data-visible="false">控件类型</th>
													<th data-field="enterTp" data-visible="false">属性类型</th>
													<th data-field="valTp" data-visible="false">值类型</th>
													<th data-field="valLen" data-visible="false">值长度</th>
													<th data-field="ser" data-visible="false">序号</th>
													<th data-field="vslFlg" data-visible="false">是否隐藏</th>
													<th data-field="defaKv" data-visible="false">默认值</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-1 column"></div>
				</div>
				<div id="keyDiv" style="margin-bottom:20px; display:none">
				<div ravo="rainbow_fx_layout" class="row clearfix"
					style="border-top: solid #ddd 1px;">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group"></div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group"></div>
					</div>
				</div>
				
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-1 column"></div>
					<div class="col-md-9 column">
						<div ravo="rainbow_fx_layout" class="row clearfix"  style="margin-top:20px">
							<div class="col-md-2 column">
								<div style="display:none">
									<select  data-role="multiselect" id="keyType" name="keyType"
											class="" check-empty="true" data-bv-notempty="true" data-bv-notempty-message="选项不能为空!" data-async="false"
											blank-item="true" blank-text="--请选择--" 
											data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true"
											data-filter-placeholder="搜索" data-max-height="300"
											data-url="${ctx}/prod/oper/prodAttr/selectData2?type=KEY_TP">
									</select>
								</div>
							</div>
							<div class="col-md-9 column">
								<label for="inputEmail3" class="col-sm-2 control-label">
									属性
								</label>
								<div class="col-sm-3">
									<input readonly="readonly" type="text" class="form-control" placeholder="" id="keyNo" name="keyNo">
								</div>
								<label for="inputEmail3" class="col-sm-2 control-label">
									属性名称
								</label>
								<div class="col-sm-3">
									<input readonly="readonly" type="text" class="form-control" placeholder="" id="keyName" name="keyName">
								</div>
							</div>
							<div class="col-md-1 column"></div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:20px">
							<div class="col-md-2 column"></div>
							<div class="col-md-9 column">
								<label for="inputEmail3" class="col-sm-2 control-label">
									数据类型
								</label>
								<div class="col-sm-3">
									<select data-role="multiselect" id="valTp" name="valTp"
											class="" check-empty="true" data-bv-notempty="true" data-bv-notempty-message="选项不能为空!" data-async="false"
											blank-item="true" blank-text="--请选择--" data-enable-filtering="true"
											data-enable-full-value-filtering="true" data-filter-placeholder="搜索" data-max-height="300"
											data-url="${ctx}/sys/dict/selectData?type=VAL_TP" checkbtn="VAL_TP">
									</select>
								</div>
								<label for="inputEmail3" class="col-sm-2 control-label">
									最大长度
								</label>
								<div class="col-sm-3">
									<input readonly="readonly" type="text" class="form-control" placeholder="" id="valLen" name="valLen">
								</div>
							</div>
							<div class="col-md-1 column"></div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:20px">
							<div class="col-md-2 column"></div>
							<div class="col-md-9 column">
								<label for="inputEmail3" class="col-sm-2 control-label">
									输入方式
								</label>
								<div class="col-sm-3">
									<select data-role="multiselect" id="keyTp" name="keyTp"
											class="" check-empty="true" data-bv-notempty="true" data-bv-notempty-message="选项不能为空!" data-async="false"
											blank-item="true" blank-text="--请选择--" data-enable-filtering="true"
											data-enable-full-value-filtering="true" data-filter-placeholder="搜索" data-max-height="300"
											data-url="${ctx}/sys/dict/selectData?type=ENTER_TP">
									</select>
								</div>
							</div>
							<div class="col-md-1 column"></div>
						</div>
						
						<div ravo="rainbow_fx_layout" class="row clearfix " id="tableDiv" style="margin-top:20px; display:none">
							<div class="col-md-3 column"></div>
							<div class="col-md-8 column">
								<div ravo="rainbow_fx_layout" class="row clearfix"
								style="margin-bottom: 20px; margin-top: 20px;">
									<div class="col-md-12 column">
										<div ravo="rainbow_fx_layout" class="row clearfix">
											<label for="inputEmail3"
												class="col-sm-1 control-label control-label"
												style="visibility: visible; "></label>
											<div class="col-sm-7">
												<table id="keyTable" data-toggle="table" data-first-load="false"
													data-click-to-select="true" data-show-export="false"
													data-show-refresh="false" data-show-toggle="false"
													data-show-columns="false" data-pagination="false"
													data-search="false" data-method="post"
													data-undefined-text="**" data-height="200"
													data-content-type="application/x-www-form-urlencoded"
													ravo="rainbow_fx_bj" class="table table-hover"
													data-single-select="true" style="border: 1px solid #9EBED7;">
													<thead style="">
														<tr>
															<th data-field="keyType" data-width="200px">名称</th>
															<th data-field="keyValue" data-width="200px">值</th>
														</tr>
													</thead>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-1 column"></div>
						</div>
						
						<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:20px">
							<div class="col-md-2 column"></div>
							<div class="col-md-9 column">
								<label for="inputEmail3" class="col-sm-2 control-label">
									是否隐藏
								</label>
								<div class="col-sm-3">
<%--									<select data-role="multiselect" id="vslFlg" class="" name="vslFlg" checkbtn="vslFlg">--%>
<%--										<option value="N">--%>
<%--											否--%>
<%--										</option>--%>
<%--										<option value="Y">--%>
<%--											是--%>
<%--										</option>--%>
<%--									</select>--%>
									<select data-role="multiselect" id="vslFlg" name="vslFlg" data-url="${ctx}/sys/dict/selectData?type=VSL_FLG" data-max-height="300" blank-item="false"
											data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="vslFlg">
									</select>
								</div>
								<label for="inputEmail3" class="col-sm-2 control-label">
									默认值
								</label>
								<div class="col-sm-3">
									<input type="text" class="form-control" placeholder="" id="defaKv" name="defaKv">
								</div>
							</div>
							<div class="col-md-1 column"></div>
						</div>
						
						<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:20px">
							<div class="col-md-5 column"></div>
							<div class="col-md-5 column">
								<shiro:haspermission name="anno">
									<button ravo="rainbow_fx" type="button" class="btn btn-info" contenteditable="false"
									id="settingBtn" style="margin-left:20px">
										确认设置
									</button>
								</shiro:haspermission>
							</div>
							<div class="col-md-2 column"></div>
						</div>
					</div>
					<div class="col-md-1 column"></div>
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
