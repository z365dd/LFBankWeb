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
	src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/locale/bootstrap-table-zh-CN.js" /></script>
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/tableExport.js" /></script>
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
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.src.js" type="text/javascript"></script>

<script type="text/javascript" src="<%=basePath%>/b_base/common/smartweb.js"></script>

<!-- check -->
<script type="text/javascript" src="<%=basePath%>/b_base/common/check.js"></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/b_base/common/formCheck.css" />

<!-- Self reference JS-->
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/sign/tec/signCommon/signCommon.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/sign/tec/entrSignList.js" charset="utf-8"></script>

<!-- change skin -->
<link href="${ctxStatic}/mainframe/${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=basePath%>/b_base/common/formCheck.js" charset="utf-8"></script>

<title>Insert title here</title>
</head>
<body>
<div class="">
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
		<div ravo="rainbow_fx_layout_tab" class="tabbable" id="tabs-751707">
			<ul class="nav nav-tabs" data-toggle="tabs">
				<li class="active">
					<a href="#panel1" data-toggle="tab" class="" aria-expanded="true"
					id="tab1" name="tab1">
						单位签约查询
					</a>
				</li>
				<li class="">
					<a href="#panel2" data-toggle="tab" aria-expanded="false" class=""
					id="tab2" name="tab2">
						单位签约新增
					</a>
				</li>
			</ul>
			
		    <div id="messageBox" class="alert alert-success hide">
				<button data-dismiss="alert" class="close">×</button>
				<span id="messageContent">操作提示信息</span>
			</div>
			
	<div class="tab-content">
		<div class="tab-pane active in" id="panel1">
			<div class="panel-body" contenteditable="false" id="chkEmpty">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="mainForm">
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									单位编号 </label>
								<div class="input-group-sm  col-sm-4">
									<input type="text" class="form-control" placeholder="" readonly="true"
									id="ENTR_NO" name="ENTR_NO" search_url="/comp/sign/pub/entrData?stat=1">
								</div>
							</div>
						</div>
					</div>
					
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label class=" control-label col-sm-5 control-label">
									业务编号 </label>
								<div class="input-group-sm col-sm-4">
									<input input_type="treesearch" search_url="/comp/sign/pub/busiData?state=1" id="BUSI_NO"
										name="BUSI_NO" type="text" value="" data-msg-required=""
										class="form-control input-small" style="" data-bv-="true"
										readonly="readonly" check-time-empty="">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label class=" control-label col-sm-5 control-label">
									证件类型 </label>
								<div class="input-group-sm  col-sm-4">
									<select data-role="multiselect" id="CERT_TP" class=""
										name="CERT_TP" data-enable-filtering="true"
										data-enable-case-insensitive-filtering="true"
										data-filter-placeholder="搜索" checkbtn="CERT_TP"
										data-url="${ctx}/sys/dict/selectData?type=CERT_TP"
										blank-item="true" data-async="true"
										data-non-selected-text="证件类型">
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label class=" control-label col-sm-5 control-label">
									签约协议号</label>
								<div class="input-group-sm  col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="SIGN_PROT_NO" name="SIGN_PROT_NO" check-empty="">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label class=" control-label col-sm-5 control-label">
									签约状态</label>
								<div class="input-group-sm  col-sm-4">
									<select data-role="multiselect" id="SIGN_STAT" class="" name="SIGN_STAT"  data-max-height="300" checkbtn="SIGN_STAT">
										<option value="">全部</option>
										<option value="0">已签约</option>
										<option value="1">已解约</option>
										<option value="2">暂停</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label class=" control-label col-sm-3 control-label">
									子业务编号 </label>
								<div class="input-group-sm  col-sm-4">
									<select data-role="multiselect" id="SUB_BUSI_NO" class="" name="SUB_BUSI_NO"  data-max-height="300" checkbtn="SUB_BUSI_NO">
										<option value="">请选择</option>
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label class=" control-label col-sm-3 control-label">
									证件号码 </label>
								<div class="input-group-sm  col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="CERT_NO" name="CERT_NO" check-empty="">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label class=" control-label col-sm-3 control-label">
									签约账号</label>
								<div class="input-group-sm  col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="ACCT" name="ACCT" check-empty="">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class=" col-sm-3 control-label" style="margin-left:8px">
								</label>
								<shiro:haspermission name="anno">
									<button ravo="rainbow_fx" type="button" class="btn btn-primary" contenteditable="false" id="qryBtn">
										查询
									</button>
								</shiro:haspermission>
							</div>
						</div>
					</div>
				</form>
			</div>
			<table id="qryTable" data-toggle="table" data-first-load="false" data-url="${ctx}/comp/sign/tec/entrsign/entrSignQry"
			data-click-to-select="true" data-show-export="false" data-show-refresh="false"
			data-show-toggle="false" data-show-columns="false" data-pagination="true"
			data-search="false" data-query-params="queryParams" data-method="post"
			data-undefined-text="**" data-height="500" data-content-type="application/x-www-form-urlencoded"
			ravo="rainbow_fx_bj" class="table table-hover " data-side-pagination="server"
			data-show-header="true" data-striped="true">
				<thead>
					<tr>
						<th data-field="ENTR_NO">
							单位编号
						</th>
						<th data-field="ENTR_NAME">
							单位名称
						</th>
						<th data-field="LEGA_NO">
							法人号
						</th>
						<!-- <th data-field="CERT_TP">
							证件类型
						</th> -->
						<th data-field="CERT_NO">
							证件号码
						</th>
						<th data-field="SIGN_STAT">
							签约状态
						</th>
						<th data-field="ACTION">
							操作
						</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<div class="tab-pane" id="panel2">
		</div>
	</div>
	</div>
	</div>
</div>
</div>
</body>
</html>