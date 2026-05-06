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
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-migrate-3.4.0.min.js" charset="utf-8"></script><!-- /JQUERY -->

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

<script type="text/javascript" src="<%=basePath%>/b_base/common/smartweb.js"></script>

<!-- check -->
<script type="text/javascript" src="<%=basePath%>/b_base/common/check.js"></script>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/common/formCheck.css"/>


<!-- Self reference JS-->
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/ctrl/test/fCtrlTranExmList.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/ctrl/test/fCtrlTranPub.js" charset="utf-8"></script>

<script type="text/javascript" src="<%=basePath%>/b_base/common/formCheck.js" charset="utf-8"></script>
<link href="<%=basePath%>/b_base/common/formCheck.css" media="all" rel="stylesheet" type="text/css" />
	
<!-- change skin -->
<link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css" type="text/css" rel="stylesheet" />

<style type="text/css">
.title {
	border-bottom: solid #DDDDDD 1px;
	margin: 10px;
	padding: 10px;
	color: #329CEA;
}
</style>
<title>Insert title here</title>
</head>
<body>
<div id="messageBox" class="alert alert-success hide"><button data-dismiss="alert" class="close">×</button><span id="messageContent">操作提示信息</span></div>
<!-- view start -->
<div class="">
	<div ravo="rainbow_fx_layout" class="row clearfix" id="page1">
		<div class="col-md-12 column">
			<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="formId_52088" style="margin-top:10px;">
				<div ravo="rainbow_fx_layout" class="row clearfix" id="pubSel">
					<div class="col-md-12 column">
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-1 column">
							</div>
							<div class="col-md-3 column">
								<div ravo="rainbow_fx" class="form-group">
									<label class="col-sm-4 control-label">
										组件号
									</label>
									<div class="input-group-sm  col-sm-8">
										<select data-role="multiselect" id="COMP_NO" name="COMP_NO" class="" data-max-height="300" checkbtn="COMP_NO">
											<option value="">
												请选择
											</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-3 column">
								<div ravo="rainbow_fx" class="form-group">
									<label class="col-sm-4 control-label">
										服务码
									</label>
									<div class="input-group-sm  col-sm-8">
										<select data-role="multiselect" id="SVC_CODE" name="SVC_CODE" class="" data-max-height="300" checkbtn="SVC_CODE">
											<option value="">
												请选择
											</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-4 column">
								<div ravo="rainbow_fx" class="form-group">
									<label class="col-sm-4 control-label">
										子服务码
									</label>
									<div class="input-group-sm  col-sm-8">
										<select data-role="multiselect" id="SUB_SVC_CODE" name="SUB_SVC_CODE" class="" data-max-height="300" checkbtn="SUB_SVC_CODE">
											<option value="">
												请选择
											</option>
										</select>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
		
		<!-- 公用维度新增 -->				
		<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:100px">
			<div style="border-top: solid #ddd 1px; width: 5%; float: left"></div>
				<p style="padding: 0 10px; font: 500 15px 微软雅黑; float: left; width: 10%; box-sizing: border-box; position: relative; top: -10px; color: #64aaea;text-align:center;margin: 0 16px;">公用维度添加</p>
			<div style="border-top: solid #ddd 1px; float: right; width: 82%"></div>
		</div>
		<div ravo="rainbow_fx_layout" class="row clearfix" id="addPubDim">
			<div class="col-md-12 column">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="" style="margin-top:0px;">
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:15px">
								<div class="col-md-1 column">
								</div>
								<div class="col-md-3 column">
									<div ravo="rainbow_fx" class="form-group">
										<label class="col-sm-4 control-label">
											维度名称
										</label>
										<div class="input-group-sm  col-sm-8">
											<select data-role="multiselect" id="PUB_DIM_KEY" name="PUB_DIM_KEY" class="" data-max-height="300" checkbtn="PUB_DIM_KEY">
												<option value="">
													请选择
												</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-4 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-3 control-label" style="visibility:visible">
											维度值
										</label>
										<div class="col-sm-6">
											<input type="text" class="form-control" placeholder="" id="PUB_DIM_KV"
											name="PUB_DIM_KV" check-empty="true">
										</div>
									</div>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
										id="addBtn1">
											新增
										</button>
									</shiro:haspermission>
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
										id="modBtn1">
											确认修改
										</button>
									</shiro:haspermission>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div ravo="rainbow_fx_layout" class="row clearfix form-horizontal">
			 <div ravo="rainbow_fx_layout" class="row clearfix">
				<div class="col-md-1 column">
				</div>
				<div class="col-md-8 column" style="margin-top:10px">
					<table id="pubDimTable" data-toggle="table" data-first-load="false"
						data-click-to-select="true" data-show-export="false"
						data-show-refresh="false" data-show-toggle="false"
						data-show-columns="false" data-pagination="false"
						data-search="false" data-method="post"
						data-undefined-text="**" data-height="200"
						data-content-type="application/x-www-form-urlencoded"
						ravo="rainbow_fx_bj" class="table table-hover"
						data-single-select="true" data-striped="true"
						style="border: 1px solid #9EBED7;">
						<thead>
							<tr>
								<th data-field="PUB_DIM_KEY">
									维度名称
								</th>
								<th data-field="PUB_DIM_KV">
									维度值
								</th>
								<th data-field="ACTION">
									操作
								</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		
		<!-- 私有维度添加 -->
		<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:20px">
			<div style="border-top: solid #ddd 1px; width: 5%; float: left"></div>
				<p style="padding: 0 10px; font: 500 15px 微软雅黑; float: left; width: 10%; box-sizing: border-box; position: relative; top: -10px; color: #64aaea;text-align:center;margin: 0 16px;">私有维度添加</p>
			<div style="border-top: solid #ddd 1px; float: right; width: 82%"></div>
		</div>
		<div ravo="rainbow_fx_layout" class="row clearfix" id="addPriDim">
			<div class="col-md-12 column">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="" style="margin-top:0px;">
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:15px">
								<div class="col-md-1 column">
								</div>
								<div class="col-md-3 column">
									<div ravo="rainbow_fx" class="form-group">
										<label class="col-sm-4 control-label">
											维度名称
										</label>
										<div class="input-group-sm  col-sm-8">
											<select data-role="multiselect" id="PRI_DIM_KEY" name="PRI_DIM_KEY" class="" data-max-height="300" checkbtn="PRI_DIM_KEY">
												<option value="">请选择</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-4 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-3 control-label" style="visibility:visible">
											维度值
										</label>
										<div class="col-sm-6">
											<input type="text" class="form-control" placeholder="" id="PRI_DIM_KV"
											name="PRI_DIM_KV" check-empty="true">
										</div>
									</div>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
										id="addBtn2">
											新增
										</button>
									</shiro:haspermission>
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
										id="modBtn2">
											确认修改
										</button>
									</shiro:haspermission>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div ravo="rainbow_fx_layout" class="row clearfix form-horizontal">
			 <div ravo="rainbow_fx_layout" class="row clearfix">
				<div class="col-md-1 column">
				</div>
				<div class="col-md-8 column" style="margin-top:10px">
					<table id="priDimTable" data-toggle="table" data-first-load="false"
						data-click-to-select="true" data-show-export="false"
						data-show-refresh="false" data-show-toggle="false"
						data-show-columns="false" data-pagination="false"
						data-search="false" data-method="post"
						data-undefined-text="**" data-height="200"
						data-content-type="application/x-www-form-urlencoded"
						ravo="rainbow_fx_bj" class="table table-hover"
						data-single-select="true" data-striped="true"
						style="border: 1px solid #9EBED7;">
						<thead>
							<tr>
								<th data-field="PRI_DIM_KEY">
									私有维度名称
								</th>
								<th data-field="PRI_DIM_KV">
									维度值
								</th>
								<th data-field="ACTION">
									操作
								</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		
		<!-- 授权柜员 -->
		<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:20px">
			<div style="border-top: solid #ddd 1px; width: 5%; float: left"></div>
				<p style="padding: 0 10px; font: 500 15px 微软雅黑; float: left; width: 10%; box-sizing: border-box; position: relative; top: -10px; color: #64aaea;text-align:center;margin: 0 16px;">授权柜员添加</p>
			<div style="border-top: solid #ddd 1px; float: right; width: 82%"></div>
		</div>
		<div ravo="rainbow_fx_layout" class="row clearfix" id="addTrl">
			<div class="col-md-12 column">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="" style="margin-top:0px;">
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:15px">
								<div class="col-md-1 column">
								</div>
								<div class="col-md-3 column">
									<div ravo="rainbow_fx" class="form-group">
										<label class="col-sm-4 control-label">
											授权柜员号
										</label>
										<div class="input-group-sm  col-sm-8">
											<select data-role="multiselect" id="TLR_NO" name="TLR_NO" class="" data-max-height="300" checkbtn=""TLR_NO"">
												<option value="">请选择</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-4 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-3 control-label" style="visibility:visible">
											姓名
										</label>
										<div class="col-sm-6">
											<input type="text" class="form-control" placeholder="" id="TLR_NAME"
											name="TLR_NAME" check-empty="true">
											<input type="hidden" class="form-control" placeholder="" id="BRCH"
											name="BRCH">
										</div>
									</div>
								</div>
							</div>
							
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-1 column">
								</div>
								<div class="col-md-4 column" style="">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-3 control-label" style="visibility:visible">
											级别
										</label>
										<div class="col-sm-6">
											<input type="text" class="form-control" placeholder="" id="TLR_LVL"
											name="TLR_LVL" check-empty="true">
										</div>
									</div>
								</div>
								<div class="col-md-2 column" style="margin-left:1px">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
										id="addBtn3">
											新增
										</button>
									</shiro:haspermission>
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
										id="modBtn3">
											确认修改
										</button>
									</shiro:haspermission>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div ravo="rainbow_fx_layout" class="row clearfix form-horizontal">
			 <div ravo="rainbow_fx_layout" class="row clearfix">
				<div class="col-md-1 column">
				</div>
				<div class="col-md-8 column" style="margin-top:10px">
					<table id="tlrTable" data-toggle="table" data-first-load="false"
						data-click-to-select="true" data-show-export="false"
						data-show-refresh="false" data-show-toggle="false"
						data-show-columns="false" data-pagination="false"
						data-search="false" data-method="post"
						data-undefined-text="**" data-height="200"
						data-content-type="application/x-www-form-urlencoded"
						ravo="rainbow_fx_bj" class="table table-hover"
						data-single-select="true" style="border: 1px solid #9EBED7;" data-striped="true">
						<thead>
							<tr>
								<th data-field="TLR_NO">
									授权柜员号
								</th>
								<th data-field="TLR_NAME">
									姓名
								</th>
								<th data-field="TLR_LVL">
									级别
								</th>
								<th data-field="BRCH">
									机构号
								</th>
								<th data-field="ACTION">
									操作
								</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		
		<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:20px">
			<div style="border-top: solid #ddd 1px; width: 5%; float: left"></div>
				<p style="padding: 0 10px; font: 500 15px 微软雅黑; float: left; width: 10%; box-sizing: border-box; position: relative; top: -10px; color: #64aaea;text-align:center;margin: 0 16px;">交易金额</p>
			<div style="border-top: solid #ddd 1px; float: right; width: 82%"></div>
		</div>
		<div ravo="rainbow_fx_layout" class="row clearfix">
			<div class="col-md-12 column">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="" >
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:15px">
								<div class="col-md-1 column">
								</div>
								<div class="col-md-3 column">
									<div ravo="rainbow_fx" class="form-group">
										<label class="col-sm-5 control-label">
											交易金额
										</label>
										<div class="col-sm-6">
											<input type="text" class="form-control" placeholder="0.00" id="TRAN_AMT"
											name="TRAN_AMT">
										</div>
									</div>
								</div>
							 </div>
						 </div>
					 </div>
				 </form>
			 </div>
		 </div>
		
		<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:20px">
			<div style="border-top: solid #ddd 1px; width: 100%;"></div>
		</div>
		<div ravo="rainbow_fx_layout" class="row clearfix">
			<div class="col-md-12 column">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="" style="margin-top:10px;">
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:15px;">
								<div class="col-md-1 column">
								</div>
								<div class="col-md-3 column">
									<div ravo="rainbow_fx" class="form-group">
										<label class="col-sm-5 control-label">
											当前案列名：
										</label>
										<div class="col-sm-6">
											<input type="text" class="form-control" placeholder="" id="case_Name"
											name="case_Name" readonly="readonly">
											
											<input class="form-control" type="hidden" id="case_No" name="case_No" value="">
										</div>
									</div>
								</div>
							 </div>
							
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-1 column">
								</div>
								<div class="col-md-1 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
										id="addBtn">
											载入案例
										</button>
									</shiro:haspermission>
								</div>
								<div class="col-md-1 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
										id="saveBtn">
											保存案例
										</button>
									</shiro:haspermission>
								</div>
								<div class="col-md-1 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
										id="saveBtn1">
											另存为案例
										</button>
									</shiro:haspermission>
								</div>
								<div class="col-md-1 column" style="margin-left:3px">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
										id="delBtn">
											删除案例
										</button>
									</shiro:haspermission>
								</div>
								<div class="col-md-1 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false"
										id="testBtn">
											测试
										</button>
									</shiro:haspermission>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		
		<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:60px">
			<div style="border-top: solid #ddd 1px; width: 5%; float: left"></div>
				<p style="padding: 0 10px; font: 500 15px 微软雅黑; float: left; width: 10%; box-sizing: border-box; position: relative; top: -10px; color: #64aaea;text-align:center;margin: 0 16px;">交易结果</p>
			<div style="border-top: solid #ddd 1px; float: right; width: 82%"></div>
		</div>
		<div ravo="rainbow_fx_layout" class="row clearfix">
			<div class="col-md-12 column">
				<div class="col-md-1 column">
					</div>
					<div class="col-md-10 column" id="resMsg">
						<label class="control-label">
						     最终检查结果:
						</label>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:5px">
					<div class="col-md-1 column">
					</div>
					<div class="col-md-3 column">
						<div ravo="rainbow_fx" class="form-group">
							<label class="col-sm-5 control-label">
								结果明细：
							</label>
							<div class="col-sm-6">
								
							</div>
						</div>
					</div>
				 </div>
			</div>
			<div ravo="rainbow_fx_layout" class="row clearfix form-horizontal">
			  <div ravo="rainbow_fx_layout" class="row clearfix">
				<div class="col-md-1 column">
				</div>
				<div class="col-md-8 column" style="margin-top:10px">
					<table id="rsTable" data-toggle="table" data-first-load="false"
						data-url="" data-striped="true"
						data-click-to-select="true" data-show-export="false"
						data-show-refresh="false" data-show-toggle="false"
						data-show-columns="false" data-pagination="false"
						data-search="false" data-method="post"
						data-undefined-text="**" data-height="300"
						data-content-type="application/x-www-form-urlencoded"
						ravo="rainbow_fx_bj" class="table table-hover"
						data-single-select="true" style="border: 1px solid #9EBED7;">
						<thead>
							<tr>
								<th data-field="TP_DESC">
									类型
								</th>
								<th data-field="EXPR_DESC">
									规则
								</th>
								<th data-field="STAT">
									检查结果
								</th>
							</tr>
						</thead>
					</table>
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
