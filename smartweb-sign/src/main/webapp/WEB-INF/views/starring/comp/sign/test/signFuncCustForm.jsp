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
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/comp/sign/test/signFuncCustForm.js" charset="utf-8"></script>

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
			<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="custSignForm"
			style="margin-top: 20px">
				<div id="custSignDiv">
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-5 control-label" style="visibility: visible">
								签约协议类型ID
							</label>
							<div class="col-sm-4  btn-group">
								<select data-role="multiselect" id="SIGN_PROT_TP_ID" name="SIGN_PROT_TP_ID" data-async="true"
								class="" data-url="${ctx}/comp/sign/test/signFuncCust/getSignTpPara" checkbtn="SIGN_PROT_TP_ID"
								data-bv-="true" data-max-height="300" data-enable-filtering="true" data-filter-placeholder="搜索">
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class=" col-sm-3 control-label" style="visibility:visible">
								签约协议类型
							</label>
							<div class="col-sm-4  btn-group">
								<select data-role="multiselect" id="SIGN_TP" name="SIGN_TP" class="" >
									<option value="">
										--请选择--
									</option>
									<option value="01">
										开通签约
									</option>
									<option value="02">
										限额签约
									</option>
									<option value="03">
										开通签约+限额签约
									</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout_panel" class="panel panel-default">
					<div class="panel-heading">
						<div ravo="rainbow_fx_bj">
							<h5 contenteditable="false">
								客户信息
							</h5>
						</div>
					</div>
					<div class="panel-body" contenteditable="false">
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-5 control-label control-label">
										签约协议号
									</label>
									<div class="col-sm-4">
										<input type="text" class="form-control" placeholder=""  id="SIGN_PROT_NO"
										name="SIGN_PROT_NO" check-empty="true" maxlength="120" check-alphanumericSymbols="true">
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-5 control-label">
										第三方客户号
									</label>
									<div class="col-sm-4">
										<input type="text" class="form-control" placeholder="" id="OTH_CUST_NO"
										name="OTH_CUST_NO" maxlength="120" check-alphanumericSymbols="true">
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
										账/卡号
									</label>
									<div class="col-sm-4">
										<input type="text" class="form-control" placeholder="" id="ACCT" name="ACCT" check-empty="true">
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-5 control-label">
										证件类型
									</label>
									<div class="col-sm-4">
										<select data-role="multiselect" id="CERT_TP" class="" name="CERT_TP" data-async="true"
										data-url="${ctx}/sys/dict/selectData?type=SIGN_CERT_TP" blank-item="true" data-max-height="300">
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label">
										账户名
									</label>
									<div class="col-sm-4">
										<input type="text" class="form-control" placeholder="" id="ACCT_NAME"
										name="ACCT_NAME" maxlength="180">
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label">
										证件号码
									</label>
									<div class="col-sm-4">
										<input type="text" class="form-control" placeholder="" id="CERT_NO" name="CERT_NO" maxlength="60" check-NAL="true">
									</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-5 control-label">
										手机号码
									</label>
									<div class="col-sm-4">
										<input type="text" class="form-control" placeholder="" id="PHONE_NO"
										name="PHONE_NO" check-telephone="true">
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-5 control-label control-label">
										通信地址
									</label>
									<div class="col-sm-4">
										<input type="text" class="form-control" placeholder="" id="COMM_ADDR"
										name="COMM_ADDR" maxlength="80">
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
										E_MAIL
									</label>
									<div class="col-sm-4">
										<input type="text" class="form-control" placeholder="" id="EMAIL_ADDR"
										name="EMAIL_ADDR" check-mail="true" maxlength="240">
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label class="control-label col-sm-5 control-label">
										协议书失效日期
									</label>
									<div class="input-group-sm  col-sm-4">
										<!-- <input type="text" readonly="readonly" maxlength="20" class="form-control
										input-mini Wdate" value="" datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
										datetime-min-date="" datetime-max-date="" datetime-is-show-clear="true"
										datetime-is-show-week="true" datetime-is-show-today="true" datetime-default-value=""
										datetime-value-fmt="HHmmss" data-link-field="val_PROT_END_DATE" id="PROT_END_DATE"
										onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01
										00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek:
										true,isShowToday: true,onpicked:function(dp){ var newVal=d p.cal.getNewDateStr();
										console.info(newVal); writeDateValue('PROT_END_DATE');}});" onchange="writeDateValue('PROT_END_DATE');"
										check-time-empty="true"> <input type="hidden"
										id="val_PROT_END_DATE" value="" name="PROT_END_DATE"> -->
										<input type="text" readonly="readonly" class="form-control input-mini Wdate" check-startTime-own="true"
										value="" datetime-skin="twoer" datetime-date-fmt="yyyy-MM-dd" datetime-min-date=""
										datetime-max-date="" datetime-is-show-clear="true" datetime-is-show-week="true"
										datetime-is-show-today="true" datetime-default-value="" datetime-value-fmt="yyyy-MM-dd"
										data-link-field="val_PROT_END_DATE" id="PROT_END_DATE" onclick="WdatePicker({skin: 'twoer',dateFmt: 'yyyy-MM-dd',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('PROT_END_DATE');}});"
										onchange="writeDateValue('PROT_END_DATE');">
										<input type="hidden" value="" name="PROT_END_DATE">
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-5 control-label">
										签约合同号
									</label>
									<div class="col-sm-4">
										<input type="text" class="form-control" placeholder="" id="SIGN_CTRCT_NO"
										name="SIGN_CTRCT_NO" maxlength="120" check-alphanumericSymbols="true">
									</div>
								</div>
							</div>
							<div class="col-md-6 column">
							</div>
						</div>
					</div>
				</div>
				</div>
				<div ravo="rainbow_fx_layout_panel" class="panel panel-default" id="signTp01">
					<div class="panel-heading">
						<div ravo="rainbow_fx_bj">
							<h5 contenteditable="false">
								开通渠道
							</h5>
						</div>
					</div>
					<div class="panel-body" contenteditable="false">
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-5 control-label" style="visibility: visible">
										开通渠道
									</label>
									<div class="col-sm-4  btn-group">
										<select data-role="multiselect" id="CHNL_NO1" class="" name="CHNL_NO1" data-select-all-text="全选"
										multiple="multiple" data-include-select-all-option="true" data-max-height="300" data-n-selected-text="已选中"
										data-url="${ctx}/comp/sign/test/signFuncCust/getChnlNo" data-all-selected-text="全部选中" data-non-selected-text="未选中"
										data-enable-filtering="true" data-filter-placeholder="搜索">
										</select>
											<!-- <option value="000000">
												全渠道
											</option> -->
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-6 column">
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout_panel" class="panel panel-default" id="signTp02">
					<div class="panel-heading">
						<div ravo="rainbow_fx_bj">
							<h5 contenteditable="false">
								限额设置
							</h5>
						</div>
					</div>
					<div class="panel-body" contenteditable="false">
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-5 control-label" style="visibility: visible">
										渠道
									</label>
									<div class="col-sm-4  btn-group">
										<select data-role="multiselect" id="CHNL_NO2" class="" name="CHNL_NO2" blank-item="true" data-async="true"
										data-max-height="300" data-url="${ctx}/comp/sign/test/signFuncCust/getChnlNo" checkbtn="CHNL_NO2">
										</select>
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-5 control-label">
										限制笔数
									</label>
									<div class="col-sm-4">
										<input type="text" class="form-control" placeholder="" id="LIM_NUM" name="LIM_NUM" maxlength="19" 
										check-empty="true" check-integer="true">
									</div>
								</div>
							</div>
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label" style="visibility: visible">
										限额类型
									</label>
									<div class="col-sm-4  btn-group">
										<select data-role="multiselect" id="LIM_FLG" class="" name="LIM_FLG" data-max-height="300" checkbtn="LIM_FLG">
											<option value="">
												请选择
											</option>
											<option value="00">
												单笔
											</option>
											<option value="01">
												日
											</option>
											<option value="02">
												旬
											</option>
											<option value="03">
												月
											</option>
											<option value="04">
												季
											</option>
											<option value="05">
												年
											</option>
										</select>
									</div>
								</div>
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label">
										限制额度
									</label>
									<div class="col-sm-4">
										<input type="text" class="form-control" placeholder="" id="LIM_AMT" name="LIM_AMT" checkNumLen="16,2" check-empty="true">
									</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix" id="rowDiv">
							<div class="col-md-6 column">
							</div>
							<div class="col-md-6 column">
								<div ravo="rainbow_fx" class="form-group">
									<div class="col-md-3 column"></div>
										<div class="col-md-2 column">
											<shiro:haspermission name="anno">
												<button ravo="rainbow_fx" type="button"
													class="btn btn-default" contenteditable="false"
													id="addRowBtn">增加一行</button>
											</shiro:haspermission>
										</div>
										<div class="col-md-2 column">
											<shiro:haspermission name="anno">
												<button ravo="rainbow_fx" type="button"
													class="btn btn-default" contenteditable="false"
													id="reviceRowBtn">确认修改</button>
											</shiro:haspermission>
										</div>
								</div>
							</div>
						</div>
						<div ravo="rainbow_fx_layout" class="row clearfix" style="margin-bottom: 20px">
							<div class="col-md-12 column">
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<label for="inputEmail3" class="col-sm-1 control-label control-label"
									style="visibility: visible; margin-left: 12%">
									</label>
									<div class="col-sm-7">
										<table id="table" data-toggle="table" data-first-load="false" data-click-to-select="true"
										data-show-export="false" data-show-refresh="false" data-show-toggle="false"
										data-show-columns="false" data-pagination="false" data-search="false" data-method="post"
										data-undefined-text="**" data-height="200" data-content-type="application/x-www-form-urlencoded"
										ravo="rainbow_fx_bj" class="table table-hover table-bordered table-condensed"
										data-single-select="true">
											<thead style="">
												<tr>
													<th data-field="CHNL_NO" data-visible="false">
														渠道
													</th>
													<th data-field="CHNL_NO_STR">
														渠道
													</th>
													<th data-field="LIM_FLG" data-visible="false">
														限额类型
													</th>
													<th data-field="LIM_FLG_STR">
														限额类型
													</th>
													<th data-field="LIM_NUM">
														限制笔数
													</th>
													<th data-field="LIM_AMT">
														限制额度
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
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="column col-md-6">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3"
									class="col-sm-8 control-label control-label"
									style="visibility: visible"></label>
								<div class="col-sm-3">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-info" contenteditable="false" id="sendData"
											name="sendData">提交</button>
									</shiro:haspermission>
								</div>
							</div>
						</div>
						<div class="col-md-6 column">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3"
									class="col-sm-1 control-label control-label"
									style="visibility: visible"></label>
								<div class="col-sm-4">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
											id="cancelBtn" name="cancelBtn">返回</button>
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
