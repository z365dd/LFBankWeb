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

<!-- Self reference JS-->
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/ctrl/empower/empowerRuleForm.js" charset="utf-8"></script>

<!-- change skin -->
<link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/ctrl/switches/compCommon.js" charset="utf-8"></script>
<script type="text/javascript"
	src="<%=basePath%>/b_base/common/formCheck.js" charset="utf-8"></script>
	<script type="text/javascript"
	src="<%=basePath%>/b_base/common/check.js" charset="utf-8"></script>
<link href="<%=basePath%>/b_base/common/formCheck.css" media="all"
	rel="stylesheet" type="text/css" />
	
<title>Insert title here</title>
</head>
<body>
<div id="messageBox" class="alert alert-success hide"><button data-dismiss="alert" class="close">×</button><span id="messageContent">操作提示信息</span></div>
<!-- view start -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">授权规则定义</h4>
				</div>
				<div class="modal-body">
					<div ravo="rainbow_fx_layout" class="row clearfix" id='ruleCommon'>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id='mNextBtn'>确定</button>
					<button type="button" class="btn btn-default" id='mCancelBtn'>取消</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
<div class="">
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
					id="formId_286247" style="margin-top: 20px">
					<div id="commonDiv">
					<div ravo="rainbow_fx_layout" class="row clearfix" id="modelCommonDiv">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-6 control-label">
									组件号 </label>
								<div class="col-sm-6">
									<select data-role="multiselect" id="MODL_NO" class=""
										name="MODL_NO" data-max-height="300" checkbtn="MODL_NO">

									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-6 control-label">
									子服务码 </label>
								<div class="col-sm-6">
									<select data-role="multiselect" id="SUB_SVC" class=""
										name="SUB_SVC" data-max-height="300" checkbtn="SUB_SVC">
										<option value=''>请选择</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class=" col-sm-3 control-label">
									服务码 </label>
								<div class="col-sm-7">
									<select data-role="multiselect" id="SVC_CODE" class=""
										name="SVC_CODE" data-max-height="300" checkbtn="SVC_CODE">
										<option value=''>请选择</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3"
									class="col-sm-3 control-label control-label"
									style="visibility: visible">授权规则定义</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" placeholder="" id="RULE_EXP" name="RULE_EXP" check-empty="true">
									<input type="text" class="form-control hidden" placeholder="" id="EXP_DESC" name="EXP_DESC" >
									<!-- <input type="hidden" class="form-control" placeholder="" id="EXPR_DESC" name="EXPR_DESC" > -->
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3"
									class="col-sm-3 control-label control-label"
									style="visibility: visible">授权规则描述</label>
								<div class="col-sm-6">
									<!-- <input type="text" class="form-control" placeholder="" id="RULE_EXP" name="RULE_EXP">
									<input type="hidden" class="form-control" placeholder="" id="EXP_DESC" name="EXP_DESC" > -->
									<input type="text" class="form-control" placeholder="" id="EXPR_DESC" name="EXPR_DESC" check-empty="true">
								</div>
							</div>
						</div>
					</div>
					
					<!-- <div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3"
									class="col-sm-3 control-label control-label"
									style="visibility: visible">开关规则定义中文</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" placeholder=""
										id="EXP_DESC" name="EXP_DESC" readonly>
								</div>
							</div>
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3"
									class="col-sm-3 control-label control-label"
									style="visibility: visible">规则中文描述</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" placeholder=""
										id="EXPR_DESC" name="EXPR_DESC" >
								</div>
							</div>
						</div>
					</div> -->

					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column" >
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-6 control-label">
									授权方式 </label>
								<div class="col-sm-6">
									<select data-role="multiselect" id="AUTH_MODE" class=""
										name="AUTH_MODE" checkbtn="AUTH_MODE">
										<option value=''>请选择</option>
										<option value='01'>有金额授权</option>
										<option value='02'>无金额授权</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							
						</div>
					</div>
					</div>
				<div id="MoneyPower" >
					<div ravo="rainbow_fx_layout" class="row clearfix" style="border-bottom: solid #ddd 1px;">
						<div class="col-md-6 column" >
							<div ravo="rainbow_fx" class="form-group">
							</div>
						</div>
						<div class="col-md-6 column">
						</div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix" style="margin:10px 0 5px 0" id="moneyDiv">
						
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-6 control-label">
										起始金额（元） </label>
									<div class="col-sm-4">
										<input type="text" class="form-control" placeholder=""
											id="AUTH_AMT1" name="AUTH_AMT1" check-empty="true">
									</div>
								</div>
							</div>
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label">
										结束金额（元） </label>
									<div class="col-sm-4">
										<input type="text" class="form-control" placeholder=""
											id="AUTH_AMT2" name="AUTH_AMT2" check-empty="true">
									</div>
								</div>
							</div>
						</div>
					<div ravo="rainbow_fx_layout" class="row clearfix" style="border-bottom: solid #ddd 1px;" >
						<div class="col-md-6 column" >
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-6 control-label">
									授权级别 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder=""
										id="AUTH_TLR_LVL" name="AUTH_TLR_LVL"  check-empty="">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-6 control-label">
									可选级别 </label>
								<div class="col-sm-4">
								<%-- data-url="${ctx}/sys/dict/selectData?type=AUTH_TLR_LVL" --%>
									<select data-role="multiselect" id="AUTH_TLR_LVL_S" class=""
										name="AUTH_TLR_LVL_S">
									<option value="">请选择</option>
									<option value="1">一级</option>
									<option value="2">二级</option>
									<option value="3">三级</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									授权人数 </label>
								<div class="col-sm-4">
									<input type="number" class="form-control" placeholder=""
										id="AUTH_TLR_NUM" name="AUTH_TLR_NUM" check-empty="true" min="1" max="9">
								</div>
							</div>
<div id='MoneyPowerBtn'>
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-3 column"></div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false" id="addBtn">新增</button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false" id="reviceBtn">确认修改</button>
									</shiro:haspermission>
								</div>
							</div>
</div>
						</div>
					</div>
					<div class="col-md-6 column">
					   <div ravo="rainbow_fx" class="form-group"></div>
					</div>
					<div class="col-md-6 column" style="margin-top:17px;">
						<div ravo="rainbow_fx" class="form-group">
							<div class="col-md-2 column"></div>
								
								<div class="col-md-2 column">
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false" id="upBtn">上移</button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false" id="downBtn">下移</button>
									</shiro:haspermission>
								</div>
							</div>
						</div>
					</div>

					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-12 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3"
									class="col-sm-3 control-label control-label"
									style="visibility: visible"></label>
								<div class="col-sm-7" >
										<table id="table" data-toggle="table" data-first-load="false"
											 data-click-to-select="true"
											data-show-export="false" data-show-refresh="false"
											data-show-toggle="false" data-show-columns="false"
											data-pagination="false" data-search="false"
											 data-method="post"
											data-undefined-text="**" data-height="300"
											data-content-type="application/x-www-form-urlencoded"
											ravo="rainbow_fx_bj" class="table table-hover"
											 data-single-select="true" style="border:1px solid #9EBED7;">
											<thead style="" >
												<tr>
													<!-- <th data-field="state" data-checkbox="true"></th>
													<th data-field="ORDER_NO" >序号</th> -->
													<th data-field="MIN_AMT">起始金额</th>
													<th data-field="MAX_AMT">结束金额</th>
													<th data-field="AUTH_TLR_LVL">授权级别</th>
													<th data-field="AUTH_TLR_NUM">授权人数</th>
													<th data-field="ACTION">操作</th>
												</tr>
											</thead>
										</table>
									</div>
							</div>
						</div>
					</div>
				</div>			
					
				<div ravo="rainbow_fx_layout" class="row clearfix" id="noMoneyPower">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-6 control-label">
								授权级别 </label>
							<div class="col-sm-4">
								<input type="text" class="form-control" placeholder=""
									id="noAUTH_TLR_LVL" name="noAUTH_TLR_LVL" check-password="false" check-empty="">
							</div>
						</div>
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-6 control-label">
								可选级别 </label>
							<div class="col-sm-4">
								<select data-role="multiselect" id="noAUTH_TLR_LVL_S" class=""
									name="noAUTH_TLR_LVL_S">
									<option value="">请选择</option>
									<option value="1">一级</option>
									<option value="2">二级</option>
									<option value="3">三级</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">
								授权人数 </label>
							<div class="col-sm-4">
								<input type="number" class="form-control" placeholder=""
									id="noAUTH_TLR_NUM" name="noAUTH_TLR_NUM" check-empty="true" min="1" max="9">
							</div>
						</div>

					</div>
				</div>
					
					<div ravo="rainbow_fx_layout" class="row clearfix" style="margin:40px 0">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-6 control-label">
									 </label>
								<div class="col-sm-4">
									<shiro:haspermission name="anno">
								<button ravo="rainbow_fx" type="button" class="btn btn-primary "
									contenteditable="false" id="saveBtn">提交</button>
							</shiro:haspermission>
								</div>
							</div>
							
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-3 column"></div>
								<div class="col-md-4 column">
									<shiro:haspermission name="anno">
								<button ravo="rainbow_fx" type="button" class="btn btn-default"
									contenteditable="false" id="cancelBtn">返回</button>
								</shiro:haspermission>
								</div>
								
							</div>
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
