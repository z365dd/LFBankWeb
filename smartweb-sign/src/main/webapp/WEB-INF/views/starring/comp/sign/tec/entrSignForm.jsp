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
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.src.js"
	type="text/javascript"></script>

<script type="text/javascript"
	src="<%=basePath%>/b_base/common/smartweb.js"></script>

<!-- Self reference JS-->
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/sign/tec/signCommon/signCommon.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/sign/tec/entrSignForm.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/common/formCheck.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/common/check.js" charset="utf-8"></script>
<link href="<%=basePath%>/b_base/common/formCheck.css" media="all" rel="stylesheet" type="text/css" />

<!-- change skin -->
<link href="${ctxStatic}/mainframe/${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css" type="text/css" rel="stylesheet" />

<title>Insert title here</title>
</head>
<body>
<div class="">
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
		    <div id="messageBox" class="alert alert-success hide">
				<button data-dismiss="alert" class="close">×</button>
				<span id="messageContent">操作提示信息</span>
			</div>
			<div class="panel-body" contenteditable="false" id="chkEmpty">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="mainForm">
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-6 column" id="entrDiv">
							<div ravo="rainbow_fx" class="form-group">
								<label class=" control-label col-sm-5 control-label">
									法人号 </label>
								<div class="input-group-sm  col-sm-4">
									<select data-role="multiselect" id="LEGA_NO" class="" name="LEGA_NO"  data-max-height="300" checkbtn="LEGA_NO">
										<option value="">请选择</option>
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group" id="chkEntrNo">
								<label class=" control-label col-sm-5 control-label">
									单位编号 </label>
								<div class="input-group-sm  col-sm-4">
									<input type="text" class="form-control" placeholder="" readonly="true"
									id="ENTR_NO" name="ENTR_NO" search_url="/comp/sign/pub/entrData?stat=1" check-time-empty="true"> 
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label class=" control-label col-sm-5 control-label">
									单位名称 </label>
								<div class="input-group-sm  col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="ENTR_NAME" name="ENTR_NAME" readonly="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label class=" control-label col-sm-5 control-label">
									打印名称 </label>
								<div class="input-group-sm  col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="PRT_NAME" name="PRT_NAME" readonly="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									证件类型 </label>
								<div class="col-sm-4">
									<select data-role="multiselect" id="CERT_TP" class=""
											name="CERT_TP" data-enable-filtering="true"
											data-enable-case-insensitive-filtering="true"
											data-filter-placeholder="搜索" data-async="true"
											data-url="${ctx}/sys/dict/selectData?type=CERT_TP"
											blank-item="true" data-non-selected-text="证件类型">
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									证件号码 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="CERT_NO" name="CERT_NO" readonly="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									签约状态
								</label>
								<div class="col-sm-5">
									<select data-role="multiselect" id="SIGN_STAT"  name="SIGN_STAT"  data-max-height="300" checkbtn="">
										<option value="0">已签约</option>
										<option value="1" disabled="disabled">已解约</option>
										<option value="2" disabled="disabled">暂停</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									联系人 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="CTCT_PER_NAME" name="CTCT_PER_NAME" readonly="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									联系电话 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="PER_TEL_NO" name="PER_TEL_NO" readonly="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									单位电话 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="ENTR_TEL_NO" name="ENTR_TEL_NO" readonly="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									通信地址 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="COMM_ADDR" name="COMM_ADDR" readonly="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									邮箱地址 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="EMAIL_ADDR" name="EMAIL_ADDR" readonly="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									邮编 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="POST_ECD" name="POST_ECD" readonly="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									开通状态
								</label>
								<div class="col-sm-5">
									<select data-role="multiselect" id="OPEN_STAT" name="OPEN_STAT" disabled="disabled" name="OPEN_STAT"  data-max-height="300" checkbtn="OPEN_STAT">
										<option value="1">开通</option>
										<option value="2" disabled="disabled">关闭</option>
										<option value="3" disabled="disabled">删除</option>
									</select>
								</div>
							</div>
						</div>
					</div>
				</form>
				
				<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:20px">
					<div style="border-top: solid #ddd 1px; width: 100%;"></div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix"  style="margin-top:10px">
					<div class="col-md-12 column">
						<div ravo="rainbow_fx" class="form-group">
							<div class="col-sm-4"></div>
							<div class="col-sm-2">
								<shiro:haspermission name="anno">
									<button ravo="rainbow_fx" type="button"
										class="btn btn-default" contenteditable="false"
										id="upBtn" name="upBtn">上一页</button>
								</shiro:haspermission>
							</div>
							<div class="col-sm-2">
								<shiro:haspermission name="anno">
									<button ravo="rainbow_fx" type="button" 
										class="btn btn-default" contenteditable="false"
										id="nextBtn" name="nextBtn">下一页</button>
								</shiro:haspermission>
							</div>
						</div>
					</div>
				</div>
			
			<div id="busiMsg">
				<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:40px">
					<div style="border-top: solid #ddd 1px; width: 5%; float: left"></div>
						<p style="padding: 0 10px; font: 500 15px 微软雅黑; float: left; width: 10%; box-sizing: border-box; position: relative; top: -10px; color: #64aaea;text-align:center;margin: 0 16px;">业务信息</p>
					<div style="border-top: solid #ddd 1px; float: right; width: 82%"></div>
				</div>
				
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="">
					<div ravo="rainbow_fx_layout" class="row clearfix"  id="busiForm">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									业务签约状态
								</label>
								<div class="col-sm-3">
									<select data-role="multiselect" id="SIGN_STAT1"  name="SIGN_STAT1"  data-max-height="300" checkbtn="SIGN_STAT1">
										<option value="0">已签约</option>
										<option value="1" disabled="disabled">已解约</option>
										<option value="2" disabled="disabled">暂停</option>
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									组件号 </label>
								<div class="col-sm-4">
									<select data-role="multiselect" id="COMP_NO" class="" name="COMP_NO" data-max-height="300" checkbtn="COMP_NO">
												
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									业务编号 </label>
								<div class="col-sm-4">
									<input input_type="treesearch" search_url="/comp/sign/pub/busiData?state=1" id="BUSI_NO"
										name="BUSI_NO" type="text" value="" data-msg-required=""
										class="form-control input-small" style="" data-bv-="true"
										readonly="readonly" check-time-empty="true">
										
									<input type="hidden" name="BUSI_NAME" id="BUSI_NAME">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									子业务编号 </label>
								<div class="col-sm-4">
									<select data-role="multiselect" id="SUB_BUSI_NO" class="" name="SUB_BUSI_NO"  data-max-height="300" checkbtn="SUB_BUSI_NO">
										<option value="">请选择</option>
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									签约账户类型 </label>
								<div class="col-sm-5">
									<select data-role="multiselect" id="ACCT_TP" class="" name="ACCT_TP"  data-max-height="300" checkbtn="ACCT_TP">
										<option value="">请选择</option>
										<option value="01">对私存款账号</option>
										<option value="02">对公存款账号</option>
										<option value="10">对私借记卡号</option>
										<option value="11">对私贷记卡号</option>
										<option value="15">对公卡号</option>
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									签约账号 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="ACCT" name="ACCT" check-empty="true" maxlength="64" check-nal="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									签约户名 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="ACCT_NAME" name="ACCT_NAME" check-empty="" maxlength="180">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									签约协议号 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="SIGN_PROT_NO" name="SIGN_PROT_NO" check-empty="" maxlength="64">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									开户机构号 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="OPEN_ACCT_BRCH" name="OPEN_ACCT_BRCH" check-empty="" maxlength="20" check-nal="true">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									开户机构名 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="BANK_NAME" name="BANK_NAME" check-empty="" maxlength="120">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									摘要码 </label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="SUM_CODE" name="SUM_CODE" check-empty="" maxlength="6">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									摘要描述</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="SUM_DESC" name="SUM_DESC" check-empty="" maxlength="360">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-5 control-label">
									开始日期 </label>
								<div class="col-sm-3">
									<input type="text" readonly="readonly" maxlength="20"
									class="form-control input-mini Wdate" value=""
									datetime-skin="twoer" datetime-date-fmt="yyyy-MM-dd"
									datetime-min-date="" datetime-max-date=""
									datetime-is-show-clear="true"
									datetime-is-show-week="true"
									datetime-is-show-today="true" datetime-default-value=""
									datetime-value-fmt="yyyyMMdd" check-time-empty="true"
									check-startTime-one="true"
									data-link-field="val_STR_DATE" id="STR_DATE"
									onclick="WdatePicker({skin: 'twoer',dateFmt: 'yyyy-MM-dd',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('STR_DATE');}});"
									onchange="writeDateValue('STR_DATE');"> <input
									type="hidden" id="val_STR_DATE" value="" name="STR_DATE">
								</div>
							</div>
						</div>
						<div class="col-md-6 column" id="isChkS">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									子业务签约状态
								</label>
								<div class="col-sm-3">
									<select data-role="multiselect" id="SIGN_STAT2"  name="SIGN_STAT2"  data-max-height="300" checkbtn="">
										<option value="0">已签约</option>
										<option value="1" disabled="disabled">已解约</option>
										<option value="2" disabled="disabled">暂停</option>
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									手续费标志 </label>
								<div class="col-sm-4">
									<select data-role="multiselect" id="FEE_TP" class="" name="FEE_TP"  data-max-height="300" checkbtn="FEE_TP">
										<option value="Y">收取</option>
										<option value="N">不收取</option>
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									手续费代码 </label>
								<div class="col-sm-4">
									<select data-role="multiselect" id="FEE_CODE" class="" name="FEE_CODE"  data-max-height="300" checkbtn="">
										<option value="">请选择</option>
										<option value="F001">0.1元/笔</option>
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									手续费转出账号</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="FEE_TF_OUT_ACCT" name="FEE_TF_OUT_ACCT" check-empty="" maxlength="64">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									手续费转出户名</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="FEE_TF_OUT_ACCT_NAME" name="FEE_TF_OUT_ACCT_NAME" check-empty="" maxlength="180">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									手续费转入账号</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="FEE_TF_IN_ACCT" name="FEE_TF_IN_ACCT" check-empty="" maxlength="64">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									手续费转入户名</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="FEE_TF_IN_ACCT_NAME" name="FEE_TF_IN_ACCT_NAME" check-empty="" maxlength="180">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									银行签约协议号</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="BANK_SIGN_PROT_NO" name="BANK_SIGN_PROT_NO" check-empty="" maxlength="60">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									行内客户号</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="BANK_CUST_NO" name="BANK_CUST_NO" check-empty="" maxlength="30">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									是否使用过渡户 </label>
								<div class="col-sm-4">
									<select data-role="multiselect" id="INTRM_ACCT_FLG" class="" name="INTRM_ACCT_FLG"  data-max-height="300" checkbtn="INTRM_ACCT_FLG">
										<option value="Y">使用</option>
										<option value="N">不使用</option>
									</select>
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									过渡账户</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="INTRM_ACCT" name="INTRM_ACCT" check-empty="true" maxlength="64">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									过渡账户名称</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" placeholder="" 
									id="INTRM_ACCT_NAME" name="INTRM_ACCT_NAME" check-empty="true" maxlength="180">
								</div>
							</div>
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									结束日期 </label>
								<div class="col-sm-3">
									<input type="text" readonly="readonly" maxlength="20"
									class="form-control input-mini Wdate" value=""
									datetime-skin="twoer" datetime-date-fmt="yyyy-MM-dd"
									datetime-min-date="" datetime-max-date=""
									datetime-is-show-clear="true"
									datetime-is-show-week="true"
									datetime-is-show-today="true" datetime-default-value=""
									datetime-value-fmt="yyyyMMdd" 
									data-link-field="val_END_DATE" id="END_DATE"
									check-endTime-one="true"
									onclick="WdatePicker({skin: 'twoer',dateFmt: 'yyyy-MM-dd',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('END_DATE');}});"
									onchange="writeDateValue('END_DATE');"> <input
									type="hidden" id="val_END_DATE" value="" name="END_DATE">
								</div>
							</div>
						</div>
						
					   <div ravo="rainbow_fx_layout" class="row clearfix" id="chkForm">
							<div class="col-md-6 column">
								<div id="ctrlMsg">
								</div>
							</div>
							<div class="col-md-6 column">
								<div id="chnlMsg">
								</div>
							</div>
						</div>
						
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-12 column">
								<div ravo="rainbow_fx" class="form-group">
								<div class="col-sm-4"></div>
								<div class="col-sm-2">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
											id="addBtn" name="addBtn">新增</button>
									</shiro:haspermission>
								</div>
								<div class="col-sm-2">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button" 
											class="btn btn-default" contenteditable="false"
											id="modBtn" name="modBtn">确认修改</button>
									</shiro:haspermission>
								</div>
							</div>
						</div>
					</div>
						
					<div ravo="rainbow_fx_layout" class="row clearfix">
						<div class="col-md-11 column">
							<div class="col-md-2 column">
							</div>
							<div class="col-md-9 column" style="margin-top:10px">
								<table id="busiTable" data-toggle="table" data-first-load="false"
									data-url="" data-striped="true"
									data-click-to-select="true" data-show-export="false"
									data-show-refresh="false" data-show-toggle="false"
									data-show-columns="false" data-pagination="false"
									data-search="false" data-method="post"
									data-undefined-text="**" data-height="120"
									data-content-type="application/x-www-form-urlencoded"
									ravo="rainbow_fx_bj" class="table table-hover"
									data-single-select="true" style="border: 1px solid #9EBED7;">
									<thead>
										<tr>
											<th data-field="BUSI_NO">
												业务编号
											</th>
											<th data-field="BUSI_NAME">
												业务名称
											</th>
											<th data-field="SUB_BUSI_NO">
												子业务编号
											</th>
											<th data-field="SUB_BUSI_NAME">
												子业务名称
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
					
					<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:50px">
						<div style="border-top: solid #ddd 1px; width: 100%;"></div>
					</div>
					<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-top:30px">
						<div class="col-md-6 column">
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-5 column"></div>
								<div class="col-md-3 column" style="margin-bottom:15px">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-primary" contenteditable="false"
											id="saveBtn" name="saveBtn">提交</button>
									</shiro:haspermission>
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-3 column"></div>
								<div class="col-md-3 column" style="margin-bottom:15px">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
											id="closeBtn" name="closeBtn">返回</button>
									</shiro:haspermission>
								</div>
							</div>
						</div>
					</div>
					</div>
				</form>
		</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
