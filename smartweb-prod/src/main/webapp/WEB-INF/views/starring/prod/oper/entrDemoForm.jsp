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
          href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.css"/>
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
            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/locale/bootstrap-table-zh-CN.js"/>
    </script>
    <script type="text/javascript"
            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/tableExport.js"/>
    </script>
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
	src="<%=basePath%>/b_base/views/starring/prod/oper/entrDemoForm.js"
	charset="utf-8"></script>

    <!-- change skin -->
    <link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css"
          type="text/css" rel="stylesheet"/>

    <script type="text/javascript"
            src="<%=basePath%>/b_base/common/formCheck.js" charset="utf-8"></script>
    <link href="<%=basePath%>/b_base/common/formCheck.css" media="all"
          rel="stylesheet" type="text/css"/>
    <script type="text/javascript"
            src="<%=basePath%>/b_base/jquery-preview/js/control.js" charset="utf-8"></script>
    <script type="text/javascript"
            src="<%=basePath%>/b_base/jquery-preview/js/jQueryRotate.js"
            charset="utf-8"></script>
    <link href="<%=basePath%>/b_base/jquery-preview/css/jquery-preview.css"
          media="all" rel="stylesheet" type="text/css"/>
    <title>Insert title here</title>
</head>
<body>
	<div id="messageBox" class="alert alert-success hide">
		<button data-dismiss="alert" class="close">×</button>
		<span id="messageContent">操作提示信息</span>
	</div>

	<input type="text" class="form-control hide" placeholder=""
		id="BUSI_NO" name="BUSI_NO">
	<!-- view start -->
	<!-- 显示的配置页面 -->
	<div class="">
		<!-- <div ravo="rainbow_fx_layout" class="row clearfix"
			style="position: relative;">
			<div id="leftGo" class=""></div>
			<div id="rightGo" class=""></div>
			<div class="col-md-12 column"
				style="width: 1000px; overflow: hidden; margin-left: 70px; padding: 0">
				<div class="collapse navbar-collapse" id="myStep"
					style="padding: 0; position: relative;">
					<div class="ystep2" style="padding-bottom: 20px; display: block">
					</div>
				</div>
			</div>
		</div> -->
    <div ravo="rainbow_fx_layout" class="row clearfix">
        <div ravo="rainbow_fx_layout_tab" class="tabbable" id=""
             style="border: 0;">
            <!-- Only required for left/right tabs -->
            <ul class="nav nav-tabs hide" data-toggle="tabs" id="tranConfig_tab"
                data-show-header="true">
                <li class="dropdown hide pull-right tabdrop"><a
                        padding="10px 10px 10px 10px" class="dropdown-toggle"
                        data-toggle="dropdown" href="#"> <i
                        class="glyphicon glyphicon-align-justify"> </i> <b class="caret">
                </b>
                </a>
                    <ul class="dropdown-menu">
                    </ul>
                </li>
                <li class="active"><a href="#baseMsg" data-toggle="tab"
                                      aria-expanded="false"> 单位基本信息 </a></li>
                <li class=""><a href="#signMsg" data-toggle="tab" id=""
                                aria-expanded="false" class=""> 业务签约信息 </a></li>
                <li class=""><a href="#clearMsg" data-toggle="tab"
                                id="CHECK_RANGE" aria-expanded="false" class=""> 单位清算信息 </a></li>
                <li class=""><a href="#feeMsg" data-toggle="tab"
                                id="CHECK_RULE" aria-expanded="false"> 单位手续费配置 </a></li>
                <li class=""><a href="#chkMsg" data-toggle="tab"
                                id="CHECK_ELEM" aria-expanded="false"> 单位对账信息 </a></li>
                <li class=""><a href="#preview" data-toggle="tab" id="PREVIEW"
                                aria-expanded="false"> 配置完成预览 </a></li>
            </ul>
            <div class="tab-content" id="contentMain">
                <div class="tab-pane" id="baseMsg">
                    <form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
                          id="formId_730834">
                        <div ravo="rainbow_fx_layout_panel" class="panel panel-default">
                            <div class="panel-heading">
                                <div ravo="rainbow_fx_bj">
                                    <h4 contenteditable="false">单位基本信息</h4>
                                </div>
                            </div>
                            <div class="panel-body" contenteditable="false">
                                <div ravo="rainbow_fx_layout" class="row clearfix">
                                    <div class="col-md-6 column">
                                        <div ravo="rainbow_fx" class="form-group" id="ENTR_NO_DIV">
                                            <label for="inputEmail3" class="col-sm-5 control-label">
                                                单位编号 </label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" placeholder=""
                                                       data-bv-="true" id="ENTR_NO1" name="ENTR_NO1"
                                                       check-empty="true" maxlength="30"
                                                       oninput="value=value.replace(/[^\d]/g,'')"
                                                       check-fixedLength="30">
                                            </div>
                                        </div>
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="ENTR_NATURE" class="col-sm-5 control-label">
                                                单位性质 </label>
                                            <div class="col-sm-4">
                                                <select data-role="multiselect" id="ENTR_NATURE" name="ENTR_NATURE"
                                                        class="" check-empty="true" data-bv-notempty="true"
                                                        data-bv-notempty-message="选项不能为空!" data-async="true"
                                                        blank-item="true" blank-text="--请选择--"
                                                        data-enable-filtering="true"
                                                        data-enable-full-value-filtering="true"
                                                        data-filter-placeholder="搜索" data-max-height="300"
                                                        data-url="${ctx}/sys/dict/selectData?type=ENTR_NATURE"
                                                        checkbtn="ENTR_NATURE">
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6 column">
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="inputEmail3" class="col-sm-3 control-label">
                                                单位名称 </label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" placeholder=""
                                                       data-bv-="true" id="ENTR_NAME" name="ENTR_NAME"
                                                       check-empty="true">
                                            </div>
                                        </div>
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="busiTypeId" class="col-sm-3 control-label">
                                                业务类别 </label>
                                            <div class="col-sm-4">
                                               <select data-role="multiselect" id="busiTypeId" class=""
                                                        name="busiTypeId" data-max-height="300" checkbtn="busiTypeId">
                                                    <option value="">请选择</option>
                                                </select>
                                            </div>
                                        </div>
                                        
                                    </div>
                                </div>
                                <div ravo="rainbow_fx_layout" class="row clearfix">
                                    <div class="col-md-6 column">
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="inputEmail3" class="col-sm-5 control-label">
                                                单位证件类型 </label>
                                            <div class="col-sm-4">
                                                <select data-role="multiselect" id="ENTR_CERT_TP" class=""
                                                        name="ENTR_CERT_TP" data-async="false"
                                                        data-url="${ctx}/sys/dict/selectData?type=ENTR_CERT_TP"
                                                        blank-item="true" data-max-height="300" checkbtn="ENTR_CERT_TP">
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6 column">
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="inputEmail3" class="col-sm-3 control-label">
                                                单位证件号码</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" placeholder=""
                                                       data-bv-="true" id="ENTR_CERT_NO" name="ENTR_CERT_NO"
                                                       check-empty="true">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div ravo="rainbow_fx_layout" class="row clearfix">
                                    <div class="col-md-6 column">
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="inputEmail3" class="col-sm-5 control-label">
                                                法人证件类型 </label>
                                            <div class="col-sm-4">
                                                <select data-role="multiselect" id="LEGA_CERT_TP" class=""
                                                        name="LEGA_CERT_TP" data-async="false"
                                                        data-url="${ctx}/sys/dict/selectData?type=CERT_TP"
                                                        blank-item="true" data-max-height="300"
                                                        check-empty="true"
                                                >
                                                </select>
                                            </div>
                                        </div>
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="inputEmail3" class="col-sm-5 control-label">
                                                法人名称</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" placeholder=""
                                                       data-bv-="true" id="LEGA_NAME" name="LEGA_NAME"
                                                       check-empty="true"
                                                >
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6 column">
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="inputEmail3" class="col-sm-3 control-label">
                                                法人证件号码</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" placeholder=""
                                                       data-bv-="true" id="LEGA_CERT_NO" name="LEGA_CERT_NO"
                                                       check-empty="true"
                                                >
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div ravo="rainbow_fx_layout" class="row clearfix">
                                    <div class="col-md-12 column">
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="inputEmail3" class="col-sm-2 control-label"
                                                   style="margin-left: 4.2%">
                                                单位地址</label>
                                            <div class="col-sm-7" style="display: flex;flex-direction: row;">
                                                <%--                                                <input type="text" class="form-control" placeholder=""--%>
                                                <%--                                                       data-bv-="true" id="ENTR_ADDR" name="ENTR_ADDR" maxlength="80">--%>
                                                <select data-role="multiselect" id="province" class=""
                                                        name="province" data-max-height="300" checkbtn="province">
                                                    <option value="">请选择</option>
                                                </select>
                                                <p style="width: 20px; text-align: center">-</p>
                                                <select data-role="multiselect" id="city" class=""
                                                        name="city" data-max-height="300" checkbtn="city">
                                                    <option value="">请选择</option>
                                                </select>
                                                <p style="width: 20px; text-align: center">-</p>
                                                <input type="text" class="form-control" placeholder=""
                                                       data-bv-="true" id="detailedAddress" name="detailedAddress"
                                                       check-empty="true"
                                                >
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div ravo="rainbow_fx_layout" class="row clearfix">
                                    <div class="col-md-12 column">
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="COMN_DESC" class="col-sm-2 control-label"
                                                   style="margin-left: 4.2%">
                                                单位描述</label>
                                            <div class="col-sm-7">
                                                <input type="text" class="form-control" placeholder=""
                                                       data-bv-="true" id="COMN_DESC" name="COMN_DESC" maxlength="100">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div ravo="rainbow_fx_layout" class="row clearfix">
                                    <div class="col-md-6 column">
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="ENTR_TEL_NO" class="col-sm-5 control-label">
                                                单位电话</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" placeholder=""
                                                       data-bv-="true" id="ENTR_TEL_NO" name="ENTR_TEL_NO">
                                            </div>
                                        </div>
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="CTCT_PER_NAME" class="col-sm-5 control-label">
                                                联系人</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" placeholder=""
                                                       data-bv-="true" id="CTCT_PER_NAME" name="CTCT_PER_NAME">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6 column">
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="EMAIL" class="col-sm-3 control-label">
                                                电子邮件</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" placeholder=""
                                                       data-bv-="true" id="EMAIL" name="EMAIL">
                                            </div>
                                        </div>
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="CTCT_PHONE_NO" class="col-sm-3 control-label">
                                                联系电话</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" placeholder=""
                                                       data-bv-="true" id="CTCT_PHONE_NO" name="CTCT_PHONE_NO">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="imgDiv">
                                    <div ravo="rainbow_fx" class="form-group" id="">
                                        <div class="col-md-6 column">
                                            <label class="control-label col-sm-5 control-label">
                                                单位图片 </label>
                                            <div class=" col-sm-6" id="ckfinder">
                                                <sys:ckfinder input="url" name="url" value=""
                                                              type="images" upload_path="/prod/field" is_all_user="true"
                                                              ckfinder_required="false" select_multiple="false"
                                                              readonly="readonly" max_width="300" max_height="200">
                                                </sys:ckfinder>
                                            </div>
                                        </div>
                                        <div class="col-md-1 column"></div>
                                        <div class="col-md-4 column">

                                        </div>
                                        <div class="col-md-2 column"></div>
                                        <div class="col-md-1 column"></div>
                                    </div>
                                </div>
                                <div ravo="rainbow_fx_layout" class="row clearfix">
                                    <div class="col-md-6 column">
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="addBtn" class="col-sm-5 control-label"></label>
                                            <div class="col-sm-4">
                                                <shiro:haspermission name="anno">
                                                    <button ravo="rainbow_fx" type="button"
                                                            class="btn btn-default" contenteditable="false"
                                                            id="addBtn" name="addBtn">提交
                                                    </button>
                                                </shiro:haspermission>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6 column">
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="cancelBtn" class="col-sm-3 control-label"></label>
                                            <div class="col-sm-4">
                                                <shiro:haspermission name="anno">
                                                    <button ravo="rainbow_fx" type="button"
                                                            class="btn btn-default" contenteditable="false"
                                                            id="cancelBtn" name="cancelBtn">返回
                                                    </button>
                                                </shiro:haspermission>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%-- <div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-2 column"></div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false" id="nextBtn1"
											name="nextBtn1">下一步</button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false" id="saveBtn1"
											name="saveBtn1">暂存</button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
											id="cancelBtn1" name="cancelBtn">取消</button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column"></div>
								<div class="col-md-2 column"></div>
							</div> --%>
                    </form>
                </div>
                <div class="tab-pane" id="signMsg">
                    <form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
                          id="formId_782344" style="margin-top: 30px">
                        <div ravo="rainbow_fx_layout" class="row clearfix">
                            <div class="col-md-6 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="ENTR_NO_SIGN" class="col-sm-5 control-label">
                                        单位编号 </label>
                                    <div class="col-sm-4">
                                        <input type="text" class="form-control" placeholder=""
                                               data-bv-="true" id="ENTR_NO_SIGN" name="ENTR_NO_SIGN"
                                               disabled check-empty="true">
                                    </div>
                                </div>
                                <div ravo="rainbow_fx_radio" class="form-group">
                                    <label for="inputEmail3"
                                           class=" control-label  col-sm-5 control-label"
                                           style="visibility: visible">是否需要签约 </label>
                                    <div class="col-sm-4">
                                        <div class="radio-inline">
                                            <label style="visibility: visible"> <input
                                                    id="optionsRadio0" type="radio" value="1" name="SIGN_FLG"
                                                    class="" checkbtn="SIGN_FLG">是
                                            </label>
                                        </div>
                                        <div class="radio-inline">
                                            <label style="visibility: visible"> <input
                                                    id="optionsRadio1" type="radio" value="0" name="SIGN_FLG"
                                                    class="" checkbtn="SIGN_FLG">否
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 column"></div>
                        </div>

							<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
								id="signCommonDiv">
								<div class="panel-heading">
									<div ravo="rainbow_fx_bj">
										<h4 contenteditable="false">业务参数</h4>
									</div>
								</div>
								<div class="panel-body" contenteditable="false">

                                <div ravo="rainbow_fx_layout" class="row clearfix">
                                    <div class="col-md-6 column">
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="RULE_NAME" class="col-sm-5 control-label">
                                                签约规则名称</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" placeholder=""
                                                       id="RULE_NAME" name="RULE_NAME" check-empty="true">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6 column"></div>
                                </div>
                                <div ravo="rainbow_fx_layout" class="row clearfix"
                                     id="signTpDiv">
                                    <div class="col-md-6 column">
                                        <div ravo="rainbow_fx_radio" class="form-group">
                                            <label for="inputEmail3"
                                                   class=" control-label  col-sm-5 control-label"
                                                   style="visibility: visible">签约类型</label>
                                            <div class="col-sm-7">
                                                <div class="radio-inline">
                                                    <label style="visibility: visible"> <input
                                                            id="optionsRadio0" type="radio" value="01" name="SIGN_TP"
                                                            class="" checkbtn="SIGN_TP">开通签约
                                                    </label>
                                                </div>
                                                <div class="radio-inline">
                                                    <label style="visibility: visible"> <input
                                                            id="optionsRadio1" type="radio" value="02" name="SIGN_TP"
                                                            class="" checkbtn="SIGN_TP">限额签约
                                                    </label>
                                                </div>
                                                <div class="radio-inline">
                                                    <label style="visibility: visible"> <input
                                                            id="optionsRadio1" type="radio" value="03" name="SIGN_TP"
                                                            class="" checkbtn="SIGN_TP">开通签约+限额签约
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6 column"></div>
                                </div>
                                <div ravo="rainbow_fx_layout" class="row clearfix"
                                     id="ifCustDiv">
                                    <div class="col-md-6 column">
                                        <div ravo="rainbow_fx_radio" class="form-group">
                                            <label for="inputEmail3"
                                                   class=" control-label  col-sm-5 control-label"
                                                   style="visibility: visible">允许客户自定义限额</label>
                                            <div class="col-sm-4">
                                                <div class="radio-inline">
                                                    <label style="visibility: visible"> <input
                                                            id="optionsRadio0" type="radio" value="Y"
                                                            name="CUST_DEF_LIM_FLG" class=""
                                                            checkbtn="CUST_DEF_LIM_FLG">是
                                                    </label>
                                                </div>
                                                <div class="radio-inline">
                                                    <label style="visibility: visible"> <input
                                                            id="optionsRadio1" type="radio" value="N"
                                                            name="CUST_DEF_LIM_FLG" class=""
                                                            checkbtn="CUST_DEF_LIM_FLG">否
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6 column"></div>
                                </div>
                            </div>
                        </div>
                        <div ravo="rainbow_fx_layout_panel" class="panel panel-default"
                             id="limitSetDiv">
                            <div class="panel-heading">
                                <div ravo="rainbow_fx_bj">
                                    <h5 contenteditable="false">限额基础设置</h5>
                                </div>
                            </div>
                            <div class="panel-body" contenteditable="false">
                                <div ravo="rainbow_fx_layout" class="row clearfix"
                                     id="tableSignDiv1">
                                    <div class="col-md-6 column">
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="inputEmail3" class="col-sm-5 control-label"
                                                   style="visibility: visible">渠道 </label>
                                            <div class="col-sm-4  btn-group">
                                                <select data-role="multiselect" id="CHNL_NO" class=""
                                                        name="CHNL_NO" data-max-height="300" checkbtn="CHNL_NO">
                                                    <option value="">请选择</option>
                                                    <!-- <option value="000001">000001</option> -->
                                                </select>
                                            </div>
                                        </div>
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="inputEmail3" class="col-sm-5 control-label">
                                                最大限制笔数</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" placeholder=""
                                                       id="LIM_NUM" name="LIM_NUM" check-empty="true"
                                                       check-integer="true">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6 column">
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="inputEmail3" class="col-sm-3 control-label"
                                                   style="visibility: visible">限额类型 </label>
                                            <div class="col-sm-4  btn-group">
                                                <select data-role="multiselect" id="LIM_FLG" class=""
                                                        name="LIM_FLG" data-max-height="300" checkbtn="LIM_FLG">
                                                    <option value="">请选择</option>
                                                    <option value="00">单笔</option>
                                                    <option value="01">日</option>
                                                    <option value="02">旬</option>
                                                    <option value="03">月</option>
                                                    <option value="04">季</option>
                                                    <option value="05">年</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div ravo="rainbow_fx" class="form-group">
                                            <label for="inputEmail3" class="col-sm-3 control-label">
                                                最大限制额度</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" placeholder=""
                                                       id="LIM_AMT" name="LIM_AMT" check-empty="true"
                                                       check-money="true">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div ravo="rainbow_fx_layout" class="row clearfix" id="">
                                    <div class="col-md-6 column"></div>
                                    <div class="col-md-6 column">
                                        <div ravo="rainbow_fx" class="form-group">
                                            <div class="col-md-3 column"></div>
                                            <div class="col-md-2 column">
                                                <shiro:haspermission name="anno">
                                                    <button ravo="rainbow_fx" type="button"
                                                            class="btn btn-default" contenteditable="false"
                                                            id="addRowBtnSign1">增加一行
                                                    </button>
                                                </shiro:haspermission>
                                            </div>
                                            <div class="col-md-2 column">
                                                <shiro:haspermission name="anno">
                                                    <button ravo="rainbow_fx" type="button"
                                                            class="btn btn-default" contenteditable="false"
                                                            id="reviceRowBtnSign1">确认修改
                                                    </button>
                                                </shiro:haspermission>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div ravo="rainbow_fx_layout" class="row clearfix"
                                     style="margin-bottom: 20px">
                                    <div class="col-md-12 column">
                                        <div ravo="rainbow_fx_layout" class="row clearfix">
                                            <label for="inputEmail3"
                                                   class="col-sm-1 control-label control-label"
                                                   style="visibility: visible; margin-left: 12%">限额设置</label>
                                            <div class="col-sm-7">
                                                <table id="tableSign1" data-toggle="table"
                                                       data-first-load="false" data-click-to-select="true"
                                                       data-show-export="false" data-show-refresh="false"
                                                       data-show-toggle="false" data-show-columns="false"
                                                       data-pagination="false" data-search="false"
                                                       data-method="post" data-undefined-text="**"
                                                       data-height="300"
                                                       data-content-type="application/x-www-form-urlencoded"
                                                       ravo="rainbow_fx_bj"
                                                       class="table table-hover table-bordered table-condensed"
                                                       data-single-select="true">
                                                    <thead style="">
                                                    <tr>
                                                        <th data-field="CHNL_NO">渠道</th>
                                                        <th data-field="LIM_FLG" data-visible="false">限额类型</th>
                                                        <th data-field="LIM_FLG_STR">限额类型</th>
                                                        <th data-field="LIM_NUM">限制笔数</th>
                                                        <th data-field="LIM_AMT">限制额度</th>
                                                        <th data-field="ACTION">操作</th>
                                                    </tr>
                                                    </thead>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

							<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
								id="signConDiv">
								<div class="panel-heading">
									<div ravo="rainbow_fx_bj">
										<h5 contenteditable="false">签约控制配置</h5>
									</div>
								</div>
								<div class="panel-body" contenteditable="false">
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label"
													style="visibility: visible">允许签约账户状态 </label>
												<div class="col-sm-4  btn-group">
													<select data-role="multiselect" id="ACCT_STAT_LIST"
														class="" name="ACCT_STAT_LIST" data-max-height="300"
														checkbtn="ACCT_STAT_LIST" multiple="multiple"
														data-url="${ctx}/sys/dict/selectData?type=ACCT_STAT"
														data-async="true" data-include-select-all-option="true">
														<option value="">请选择</option>
													</select>
												</div>
											</div>
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-5 control-label"
													style="visibility: visible">对公跨法人</label>
												<div class="col-sm-7">
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="radio" value="Y"
															name="VRFY_ENTR_CUST_FLG" class=""
															checkbtn="VRFY_ENTR_CUST_FLG">是
														</label>
													</div>
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="N"
															name="VRFY_ENTR_CUST_FLG" class=""
															checkbtn="VRFY_ENTR_CUST_FLG">否
														</label>
													</div>
												</div>
											</div>
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-5 control-label"
													style="visibility: visible">校验户名</label>
												<div class="col-sm-7">
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="radio" value="Y"
															name="VRFY_ACCT_NAME_FLG" class=""
															checkbtn="VRFY_ACCT_NAME_FLG">是
														</label>
													</div>
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="N"
															name="VRFY_ACCT_NAME_FLG" class=""
															checkbtn="VRFY_ACCT_NAME_FLG">否
														</label>
													</div>
												</div>
											</div>
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-5 control-label"
													style="visibility: visible">校验手机号码</label>
												<div class="col-sm-7">
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="radio" value="Y"
															name="VRFY_PHONE_FLG" class="" checkbtn="VRFY_PHONE_FLG">是
														</label>
													</div>
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="N"
															name="VRFY_PHONE_FLG" class="" checkbtn="VRFY_PHONE_FLG">否
														</label>
													</div>
												</div>
											</div>
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-5 control-label"
													style="visibility: visible">修改签约机构控制</label>
												<div class="col-sm-7">
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="radio" value="Y"
															name="VRFY_MOD_BRCH_FLG" class=""
															checkbtn="VRFY_MOD_BRCH_FLG">原机构
														</label>
													</div>
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="N"
															name="VRFY_MOD_BRCH_FLG" class=""
															checkbtn="VRFY_MOD_BRCH_FLG">不控制
														</label>
													</div>
												</div>
											</div>
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-5 control-label"
													style="visibility: visible">第三方客户签约标志</label>
												<div class="col-sm-7">
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="radio" value="Y"
															name="OTH_CUST_SIGN_FLG" class=""
															checkbtn="OTH_CUST_SIGN_FLG">是
														</label>
													</div>
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="N"
															name="OTH_CUST_SIGN_FLG" class=""
															checkbtn="OTH_CUST_SIGN_FLG">否
														</label>
													</div>
												</div>
											</div>
										</div>
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class=" col-sm-3 control-label">
													允许签约凭证状态</label>
												<div class="col-sm-4">
													<select data-role="multiselect" id="VCH_STAT_LIST" class=""
														name="VCH_STAT_LIST" data-max-height="300"
														checkbtn="VCH_STAT_LIST" multiple="multiple"
														data-include-select-all-option="true">
														<option value='1'>正常</option>
														<option value='2'>未激活</option>
														<option value='3'>挂失</option>
													</select>
												</div>
											</div>
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-3 control-label"
													style="visibility: visible">个人跨机构</label>
												<div class="col-sm-7">
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="radio" value="Y"
															name="VRFY_PER_CUST_FLG" class=""
															checkbtn="VRFY_PER_CUST_FLG">是
														</label>
													</div>
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="N"
															name="VRFY_PER_CUST_FLG" class=""
															checkbtn="VRFY_PER_CUST_FLG">否
														</label>
													</div>
												</div>
											</div>
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-3 control-label"
													style="visibility: visible">校验证件</label>
												<div class="col-sm-7">
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="radio" value="Y"
															name="VRFY_CERT_FLG" class="" checkbtn="VRFY_CERT_FLG">是
														</label>
													</div>
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="N"
															name="VRFY_CERT_FLG" class="" checkbtn="VRFY_CERT_FLG">否
														</label>
													</div>
												</div>
											</div>
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-3 control-label"
													style="visibility: visible">账户签约校验</label>
												<div class="col-sm-7">
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="radio" value="01"
															name="VRFY_ACCT_CARD_FLG" class=""
															checkbtn="VRFY_ACCT_CARD_FLG">账号或卡单独
														</label>
													</div>
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="02"
															name="VRFY_ACCT_CARD_FLG" class=""
															checkbtn="VRFY_ACCT_CARD_FLG">账号与卡一起
														</label>
													</div>
												</div>
											</div>
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-3 control-label"
													style="visibility: visible">解约机构控制</label>
												<div class="col-sm-7">
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="radio" value="Y"
															name="VRFY_CANCL_BRCH_FLG" class=""
															checkbtn="VRFY_CANCL_BRCH_FLG">原机构
														</label>
													</div>
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="N"
															name="VRFY_CANCL_BRCH_FLG" class=""
															checkbtn="VRFY_CANCL_BRCH_FLG">不控制
														</label>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

                        <div ravo="rainbow_fx_layout" class="row clearfix"
                             style="border-top: solid #ddd 1px;">
                            <div class="col-md-6 column">
                                <div ravo="rainbow_fx" class="form-group"></div>
                            </div>
                            <div class="col-md-6 column">
                                <div ravo="rainbow_fx" class="form-group"></div>
                            </div>
                        </div>

                        <div id="signTableDiv">
                            <div ravo="rainbow_fx_layout" class="row clearfix"
                                 id="tableBtnSign">
                                <div class="col-md-6 column"></div>
                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <div class="col-md-3 column"></div>
                                        <div class="col-md-2 column">
                                            <shiro:haspermission name="anno">
                                                <button ravo="rainbow_fx" type="button"
                                                        class="btn btn-default" contenteditable="false"
                                                        id="addRowBtnSign2">增加一行
                                                </button>
                                            </shiro:haspermission>
                                        </div>
                                        <div class="col-md-2 column">
                                            <shiro:haspermission name="anno">
                                                <button ravo="rainbow_fx" type="button"
                                                        class="btn btn-default" contenteditable="false"
                                                        id="reviceRowBtnSign2">确认修改
                                                </button>
                                            </shiro:haspermission>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div ravo="rainbow_fx_layout" class="row clearfix"
                                 style="margin-bottom: 20px">
                                <div class="col-md-12 column">
                                    <div ravo="rainbow_fx_layout" class="row clearfix">
                                        <label for="inputEmail3"
                                               class="col-sm-1 control-label control-label"
                                               style="visibility: visible; margin-left: 12%"></label>
                                        <div class="col-sm-7">
                                            <table id="tableSign2" data-toggle="table"
                                                   data-first-load="false" data-click-to-select="true"
                                                   data-show-export="false" data-show-refresh="false"
                                                   data-show-toggle="false" data-show-columns="false"
                                                   data-pagination="false" data-search="false"
                                                   data-method="post" data-undefined-text="**"
                                                   data-height="300"
                                                   data-content-type="application/x-www-form-urlencoded"
                                                   ravo="rainbow_fx_bj"
                                                   class="table table-hover table-bordered table-condensed"
                                                   data-single-select="true">
                                                <thead style="">
                                                <tr>
                                                    <th data-field="SIGN_FLG_STR">是否需要签约</th>
                                                    <th data-field="SIGN_FLG" data-visible="false"></th>
                                                    <th data-field="RULE_NAME">签约规则名称</th>

															<th data-field="SIGN_TP" data-visible="false"></th>
															<th data-field="CUST_DEF_LIM_FLG" data-visible="false"></th>
															<th data-field="LIMIT_SET" data-visible="false"></th>

															<th data-field="ACCT_STAT_LIST" data-visible="false"></th>
															<th data-field="VCH_STAT_LIST" data-visible="false"></th>
															<th data-field="VRFY_ENTR_CUST_FLG" data-visible="false"></th>
															<th data-field="VRFY_PER_CUST_FLG" data-visible="false"></th>
															<th data-field="VRFY_ACCT_NAME_FLG" data-visible="false"></th>
															<th data-field="VRFY_CERT_FLG" data-visible="false"></th>
															<th data-field="VRFY_PHONE_FLG" data-visible="false"></th>
															<th data-field="VRFY_ACCT_CARD_FLG" data-visible="false"></th>
															<th data-field="VRFY_MOD_BRCH_FLG" data-visible="false"></th>
															<th data-field="VRFY_CANCL_BRCH_FLG" data-visible="false"></th>
															<th data-field="OTH_CUST_SIGN_FLG" data-visible="false"></th>

															<th data-field="ACTION">操作</th>
														</tr>
													</thead>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-2 column"></div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
                                            id="backBtnSign" name="backBtnSign">上一步
                                    </button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
                                            id="nextBtnSign" name="nextBtnSign">下一步
                                    </button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
                                            id="saveBtnSign" name="saveBtnSign">暂存
                                    </button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
                                            id="cancelBtnSign" name="cancelBtnSign">取消
                                    </button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column"></div>
							</div>
						</form>
					</div>

					<div class="tab-pane" id="clearMsg">
						<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
							id="formId_780166" style="margin-top: 30px">
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-5 control-label">
											单位编号 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												data-bv-="true" id="ENTR_NO3" name="ENTR_NO3" disabled
												check-empty="true">
										</div>
									</div>
									<div ravo="rainbow_fx_radio" class="form-group">
										<label for="inputEmail3"
											class=" control-label  col-sm-5 control-label"
											style="visibility: visible"> 清算类型 </label>
										<div class="col-sm-4">
											<div class="radio-inline">
												<label style="visibility: visible"> <input
													id="optionsRadio0" type="radio" value="1" name="CLR_TP"
													class="" checkbtn="CLR_TP">按业务清算
												</label>
											</div>
											<div class="radio-inline">
												<label style="visibility: visible"> <input
													id="optionsRadio2" type="radio" value="0" name="CLR_TP"
													class="" checkbtn="CLR_TP">不清算
												</label>
											</div>
										</div>
									</div>
								</div>

								<div class="col-md-6 column"></div>
							</div>

							<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
								id="clrComDiv">
								<div class="panel-heading">
									<div ravo="rainbow_fx_bj">
										<h4 contenteditable="false">业务参数</h4>
									</div>
								</div>
								<div class="panel-body" contenteditable="false">

									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													清算维度 </label>
												<div class="col-sm-4">
													<select data-role="multiselect" id="CLR_DIM_TP" class=""
														name="CLR_DIM_TP" data-max-height="300"
														checkbtn="CLR_DIM_TP">
														<option value="">请选择</option>
														<option value="1">集中清算</option>
														<option value="2">子单位清算</option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-md-6 column">
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-3 control-label"
													style="visibility: visible"> 清算标志 </label>
												<div class="col-sm-4">
													<div class="radio">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="radio" value="1" name="CLR_METH"
															class="" checkbtn="CLR_METH">本金清算
														</label>
													</div>
													<div class="radio">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="2" name="CLR_METH"
															class="" checkbtn="CLR_METH">本金和手续费统一清算
														</label>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
								id="clrAttrDiv">
								<div class="panel-heading">
									<div ravo="rainbow_fx_bj">
										<h5 contenteditable="false">清算参数</h5>
									</div>
								</div>
								<div class="panel-body" contenteditable="false">
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													清算账户/客户账户 </label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="ENTR_ACCT" name="ENTR_ACCT" check-empty="true">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													客户账户行号</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="ENTR_ACCT_BANK" name="ENTR_ACCT_BANK">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													过渡账户</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="INTRM_ACCT" name="INTRM_ACCT">
												</div>
											</div>
										</div>
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													摘要码</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="POSTING_SUM_CODE" name="POSTING_SUM_CODE">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													摘要描述</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="POSTING_SUM_DESC" name="POSTING_SUM_DESC">
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix" id="feeDiv0">
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-5 control-label">
											清算周期 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												id="CLR_CYC" name="CLR_CYC">
										</div>
									</div>
									<div ravo="rainbow_fx" class="form-group">
										<label class="control-label col-sm-5 control-label">
											自动清算时间（起始） </label>
										<div class="input-group-sm  col-sm-4">
											<input type="text" readonly="readonly" maxlength="20"
												class="form-control input-mini Wdate" value=""
												datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
												datetime-min-date="" datetime-max-date=""
												datetime-is-show-clear="true" datetime-is-show-week="true"
												datetime-is-show-today="true" datetime-default-value=""
												datetime-value-fmt="HHmmss" data-link-field="val_STR_TIME3"
												id="STR_TIME3"
												onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('STR_TIME3');}});"
												onchange="writeDateValue('STR_TIME3');"> <input
												type="hidden" id="val_STR_TIME3" value="" name="STR_TIME3">
										</div>
									</div>
									<div ravo="rainbow_fx_checkbox" class="form-group">
										<label for="inputEmail3"
											class=" control-label  col-sm-5 control-label"
											style="visibility: visible">批量清算模式</label>
										<div class="col-sm-6">
											<div class="radio-inline">
												<label style="visibility: visible"> <input
													id="optionsRadio0" type="radio" value="1"
													name="BAT_PROC_FLG" class="">统一处理
												</label>
											</div>
											<div class="radio-inline">
												<label style="visibility: visible"> <input
													id="optionsRadio1" type="radio" value="2"
													name="BAT_PROC_FLG" class="">批次处理
												</label>
											</div>
										</div>
									</div>
									<div ravo="rainbow_fx_checkbox" class="form-group">
										<label for="inputEmail3"
											class=" control-label  col-sm-5 control-label"
											style="visibility: visible">总分核对标志</label>
										<div class="col-sm-4">
											<div class="radio">
												<label style="visibility: visible"> <input
													id="optionsRadio0" type="radio" value="1" name="VRFY_FLG"
													class="">本金核对
												</label>
											</div>
											<div class="radio">
												<label style="visibility: visible"> <input
													id="optionsRadio1" type="radio" value="2" name="VRFY_FLG"
													class="">本金+手续费核对
												</label>
											</div>
											<div class="radio">
												<label style="visibility: visible"> <input
													id="optionsRadio1" type="radio" value="3" name="VRFY_FLG"
													class="">不核对
												</label>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-6 column">
									<div ravo="rainbow_fx_checkbox" class="form-group">
										<label for="inputEmail3"
											class=" control-label  col-sm-3 control-label"
											style="visibility: visible">清算账务记账方式 </label>
										<div class="col-sm-7">
											<div class="checkbox-inline">
												<label style="visibility: visible"> <input
													id="optionsRadio0" type="checkbox" value="1"
													name="CLR_SND_GRP_FLG" class="">自动清算
												</label>
											</div>
											<div class="checkbox-inline">
												<label style="visibility: visible"> <input
													id="optionsRadio1" type="checkbox" value="2"
													name="CLR_SND_GRP_FLG" class="">柜面发起清算
												</label>
											</div>
										</div>
									</div>
									<div ravo="rainbow_fx" class="form-group">
										<label class="control-label col-sm-3 control-label">
											自动清算时间（结束） </label>
										<div class="input-group-sm  col-sm-4">
											<input type="text" readonly="readonly" maxlength="20"
												class="form-control input-mini Wdate" value=""
												datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
												datetime-min-date="" datetime-max-date=""
												datetime-is-show-clear="true" datetime-is-show-week="true"
												datetime-is-show-today="true" datetime-default-value=""
												datetime-value-fmt="HHmmss" data-link-field="val_END_TIME3"
												id="END_TIME3"
												onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('END_TIME3');}});"
												onchange="writeDateValue('END_TIME3');"> <input
												type="hidden" id="val_END_TIME3" value="" name="END_TIME3">
										</div>
									</div>
								</div>
							</div>

							<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
								id="feeDiv1">
								<div class="panel-heading">
									<div ravo="rainbow_fx_bj">
										<h5 contenteditable="false">手续费参数</h5>
									</div>
								</div>
								<div class="panel-body" contenteditable="false">
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-6 column">
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-5 control-label"
													style="visibility: visible">手续费计算方式</label>
												<div class="col-sm-7">
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="radio" value="1"
															name="FEE_CALT_FLG3" class="" checkbtn="FEE_CALT_FLG3">计算（以本地为准）
														</label>
													</div>
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="2"
															name="FEE_CALT_FLG3" class="" checkbtn="FEE_CALT_FLG3">计算（以单位为准）
														</label>
													</div>
													<!-- <div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="3"
															name="FEE_CALT_FLG3" class="" checkbtn="FEE_CALT_FLG3">不计算
														</label>
													</div> -->
												</div>
											</div>
										</div>
										<div class="col-md-6 column"></div>
									</div>
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-6 column">
											<div ravo="rainbow_fx_checkbox" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-5 control-label"
													style="visibility: visible">手续费收取方式</label>
												<div class="col-sm-7">
													<div class="checkbox-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="checkbox" value="1"
															name="FEE_SND_GRP_FLG3" class="">自动收取
														</label>
													</div>
													<div class="checkbox-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="checkbox" value="2"
															name="FEE_SND_GRP_FLG3" class="">柜面发起收取
														</label>
													</div>
												</div>
											</div>
										</div>
										<div class="col-md-6 column"></div>
									</div>
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label class="control-label col-sm-5 control-label">
													自动收取时间（开始） </label>
												<div class="input-group-sm  col-sm-4">
													<input type="text" readonly="readonly" maxlength="20"
														class="form-control input-mini Wdate" value=""
														datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
														datetime-min-date="" datetime-max-date=""
														datetime-is-show-clear="true" datetime-is-show-week="true"
														datetime-is-show-today="true" datetime-default-value=""
														datetime-value-fmt="HHmmss"
														data-link-field="val_FEE_COLT_STR_TIME3"
														id="FEE_COLT_STR_TIME3"
														onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('FEE_COLT_STR_TIME3');}});"
														onchange="writeDateValue('FEE_COLT_STR_TIME3');">
													<input type="hidden" id="val_FEE_COLT_STR_TIME3" value=""
														name="FEE_COLT_STR_TIME3">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													手续费付款账号</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="FEE_TF_OUT_ACCT3" name="FEE_TF_OUT_ACCT3">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													手续费收款账号</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="FEE_TF_IN_ACCT3" name="FEE_TF_IN_ACCT3">
												</div>
											</div>
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-5 control-label"
													style="visibility: visible">批量标志</label>
												<div class="col-sm-7">
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="radio" value="1"
															name="BAT_PROC_FEE_FLG3" class="">统一处理
														</label>
													</div>
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="2"
															name="BAT_PROC_FEE_FLG3" class="">批次处理
														</label>
													</div>
												</div>
											</div>
										</div>
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label class="control-label col-sm-3 control-label">
													自动收取时间（结束） </label>
												<div class="input-group-sm  col-sm-4">
													<input type="text" readonly="readonly" maxlength="20"
														class="form-control input-mini Wdate" value=""
														datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
														datetime-min-date="" datetime-max-date=""
														datetime-is-show-clear="true" datetime-is-show-week="true"
														datetime-is-show-today="true" datetime-default-value=""
														datetime-value-fmt="HHmmss"
														data-link-field="val_FEE_COLT_END_TIME3"
														id="FEE_COLT_END_TIME3"
														onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('FEE_COLT_END_TIME3');}});"
														onchange="writeDateValue('FEE_COLT_END_TIME3');">
													<input type="hidden" id="val_FEE_COLT_END_TIME3" value=""
														name="FEE_COLT_END_TIME3">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													手续费摘要码</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="FEE_SUM_CODE3" name="FEE_SUM_CODE3" maxlength="6">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													手续费摘要描述</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="FEE_SUM_DESC3" name="FEE_SUM_DESC3">
												</div>
											</div>
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-3 control-label"
													style="visibility: visible">手续费方向</label>
												<div class="col-sm-7">
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="radio" value="1"
															name="FEE_PAY_FLG3" class="">单位支付
														</label>
													</div>
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="2"
															name="FEE_PAY_FLG3" class="">银行支付
														</label>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div id="feeDiv2">
										<div ravo="rainbow_fx_layout" class="row clearfix">
											<div class="col-md-6 column">
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class=" col-sm-5 control-label">
														计算方式 </label>
													<div class="col-sm-4">
														<select data-role="multiselect" id="FEE_CALT_METH3"
															class="" name="FEE_CALT_METH3" data-max-height="300"
															checkbtn="FEE_CALT_METH3">
															<option value=''>请选择</option>
															<option value='1'>按成功笔数收取</option>
															<option value='2'>按总笔数收取</option>
															<option value='3'>按成功金额比例</option>
															<option value='4'>按总金额收取</option>
															<option value='5'>成功笔数按档次</option>
															<option value='6'>总笔数按档次</option>
															<option value='7'>成功金额按档次</option>
															<option value='8'>总金额按档次</option>
														</select>
													</div>
												</div>
											</div>
											<div class="col-md-6 column"></div>
										</div>
										<div ravo="rainbow_fx_layout" class="row clearfix"
											style="padding-left: 4.4%" id="FEE_RULE_EXPR_DIV3">
											<div class="col-md-5 column">
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														第一档 </label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_STAT1" name="LEVEL_STAT1">
													</div>
													<label for="inputEmail3" class="col-sm-1 control-label">
														——</label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_END1" name="LEVEL_END1">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														第二档 </label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_STAT2" name="LEVEL_STAT2">
													</div>
													<label for="inputEmail3" class="col-sm-1 control-label">
														——</label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_END2" name="LEVEL_END2">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														第三档 </label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_STAT3" name="LEVEL_STAT3">
													</div>
													<label for="inputEmail3" class="col-sm-1 control-label">
														——</label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_END3" name="LEVEL_END3">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														第四档 </label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_STAT4" name="LEVEL_STAT4">
													</div>
													<label for="inputEmail3" class="col-sm-1 control-label">
														——</label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_END4" name="LEVEL_END4">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														第五档 </label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_STAT5" name="LEVEL_STAT5">
													</div>
													<label for="inputEmail3" class="col-sm-1 control-label">
														——</label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_END5" name="LEVEL_END5">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														第六档 </label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_STAT6" name="LEVEL_STAT6">
													</div>
													<label for="inputEmail3" class="col-sm-1 control-label">
														——</label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_END6" name="LEVEL_END6">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														第七档 </label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_STAT7" name="LEVEL_STAT7">
													</div>
													<label for="inputEmail3" class="col-sm-1 control-label">
														——</label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_END7" name="LEVEL_END7">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														第八档 </label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_STAT8" name="LEVEL_STAT8">
													</div>
													<label for="inputEmail3" class="col-sm-1 control-label">
														——</label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_END8" name="LEVEL_END8">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														第九档 </label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_STAT9" name="LEVEL_STAT9">
													</div>
													<label for="inputEmail3" class="col-sm-1 control-label">
														——</label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_END9" name="LEVEL_END9">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														第十档 </label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_STAT10" name="LEVEL_STAT10">
													</div>
													<label for="inputEmail3" class="col-sm-1 control-label">
														——</label>
													<div class="col-sm-2">
														<input type="text" class="form-control" placeholder=""
															id="LEVEL_END10" name="LEVEL_END10">
													</div>
												</div>
											</div>
											<div class="col-md-2 column">
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														每笔（元）</label>
													<div class="col-sm-5">
														<input type="text" class="form-control" placeholder=""
															id="SINGLE1" name="SINGLE1">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														每笔（元）</label>
													<div class="col-sm-5">
														<input type="text" class="form-control" placeholder=""
															id="SINGLE1" name="SINGLE2">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														每笔（元）</label>
													<div class="col-sm-5">
														<input type="text" class="form-control" placeholder=""
															id="SINGLE1" name="SINGLE3">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														每笔（元）</label>
													<div class="col-sm-5">
														<input type="text" class="form-control" placeholder=""
															id="SINGLE1" name="SINGLE4">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														每笔（元）</label>
													<div class="col-sm-5">
														<input type="text" class="form-control" placeholder=""
															id="SINGLE1" name="SINGLE5">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														每笔（元）</label>
													<div class="col-sm-5">
														<input type="text" class="form-control" placeholder=""
															id="SINGLE1" name="SINGLE6">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														每笔（元）</label>
													<div class="col-sm-5">
														<input type="text" class="form-control" placeholder=""
															id="SINGLE1" name="SINGLE7">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														每笔（元）</label>
													<div class="col-sm-5">
														<input type="text" class="form-control" placeholder=""
															id="SINGLE1" name="SINGLE8">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														每笔（元）</label>
													<div class="col-sm-5">
														<input type="text" class="form-control" placeholder=""
															id="SINGLE1" name="SINGLE8">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														每笔（元）</label>
													<div class="col-sm-5">
														<input type="text" class="form-control" placeholder=""
															id="SINGLE1" name="SINGLE10">
													</div>
												</div>
											</div>
											<div class="col-md-4 column">
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-3 control-label">
														收费百分比</label>
													<div class="col-sm-4">
														<input type="text" class="form-control" placeholder=""
															id="CHARGE_P1" name="CHARGE_P1">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-3 control-label">
														收费百分比</label>
													<div class="col-sm-4">
														<input type="text" class="form-control" placeholder=""
															id="CHARGE_P2" name="CHARGE_P2">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-3 control-label">
														收费百分比</label>
													<div class="col-sm-4">
														<input type="text" class="form-control" placeholder=""
															id="CHARGE_P3" name="CHARGE_P3">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-3 control-label">
														收费百分比</label>
													<div class="col-sm-4">
														<input type="text" class="form-control" placeholder=""
															id="CHARGE_P4" name="CHARGE_P4">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-3 control-label">
														收费百分比</label>
													<div class="col-sm-4">
														<input type="text" class="form-control" placeholder=""
															id="CHARGE_P5" name="CHARGE_P5">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-3 control-label">
														收费百分比</label>
													<div class="col-sm-4">
														<input type="text" class="form-control" placeholder=""
															id="CHARGE_P6" name="CHARGE_P6">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-3 control-label">
														收费百分比</label>
													<div class="col-sm-4">
														<input type="text" class="form-control" placeholder=""
															id="CHARGE_P7" name="CHARGE_P7">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-3 control-label">
														收费百分比</label>
													<div class="col-sm-4">
														<input type="text" class="form-control" placeholder=""
															id="CHARGE_P8" name="CHARGE_P8">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-3 control-label">
														收费百分比</label>
													<div class="col-sm-4">
														<input type="text" class="form-control" placeholder=""
															id="CHARGE_P9" name="CHARGE_P9">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-3 control-label">
														收费百分比</label>
													<div class="col-sm-4">
														<input type="text" class="form-control" placeholder=""
															id="CHARGE_P10" name="CHARGE_P10">
													</div>
												</div>
											</div>
										</div>

										<div ravo="rainbow_fx_layout" class="row clearfix">
											<div class="col-md-6 column">
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														最低收费金额</label>
													<div class="col-sm-4">
														<input type="text" class="form-control" placeholder=""
															id="MIN_FEE3" name="MIN_FEE3" check-money="true">
													</div>
												</div>
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-5 control-label">
														手续费精度</label>
													<div class="col-sm-4">
														<select data-role="multiselect" id="FEE_PRCSN_TP" class=""
															name="FEE_PRCSN_TP3" data-max-height="300"
															checkbtn="FEE_PRCSN_TP3">
															<option value="">请选择</option>
															<option value="1">元</option>
															<option value="2">分</option>
														</select>
													</div>
												</div>
											</div>
											<div class="col-md-6 column">
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-3 control-label">
														最高收费金额</label>
													<div class="col-sm-4">
														<input type="text" class="form-control" placeholder=""
															id="MAX_FEE3" name="MAX_FEE3" check-money="true">
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix"
								style="border-top: solid #ddd 1px;">
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group"></div>
								</div>
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group"></div>
								</div>
							</div>

							<div id="table3Div">
								<div ravo="rainbow_fx_layout" class="row clearfix"
									id="tableBtn3">
									<div class="col-md-6 column"></div>
									<div class="col-md-6 column">
										<div ravo="rainbow_fx" class="form-group">
											<div class="col-md-3 column"></div>
											<div class="col-md-2 column">
												<shiro:haspermission name="anno">
													<button ravo="rainbow_fx" type="button"
														class="btn btn-default" contenteditable="false"
                                                        id="addRowBtn3">增加一行
                                                </button>
												</shiro:haspermission>
											</div>
											<div class="col-md-2 column">
												<shiro:haspermission name="anno">
													<button ravo="rainbow_fx" type="button"
														class="btn btn-default" contenteditable="false"
                                                        id="reviceRowBtn3">确认修改
                                                </button>
												</shiro:haspermission>
											</div>
										</div>
									</div>
								</div>

								<div ravo="rainbow_fx_layout" class="row clearfix"
									style="margin-bottom: 20px">
									<div class="col-md-12 column">
										<div ravo="rainbow_fx_layout" class="row clearfix">
											<label for="inputEmail3"
												class="col-sm-1 control-label control-label"
												style="visibility: visible; margin-left: 12%"></label>
											<div class="col-sm-7">
												<table id="table3" data-toggle="table"
													data-first-load="false" data-click-to-select="true"
													data-show-export="false" data-show-refresh="false"
													data-show-toggle="false" data-show-columns="false"
													data-pagination="false" data-search="false"
													data-method="post" data-undefined-text="**"
													data-height="300"
													data-content-type="application/x-www-form-urlencoded"
													ravo="rainbow_fx_bj"
													class="table table-hover table-bordered table-condensed"
													data-single-select="true">
													<thead style="">
														<tr>
															<th data-field="INTRM_ACCT">过渡账户</th>
															<th data-field="ENTR_ACCT">客户账户</th>
															<th data-field="ENTR_ACCT_BANK">行号</th>

															<th data-field="INTRM_ACCT_FLG" data-visible="false"></th>
															<th data-field="FEE_CLR_METH" data-visible="false"></th>

															<th data-field="CLR_DIM_TP" data-visible="false"></th>
															<th data-field="CLR_METH" data-visible="false"></th>
															<th data-field="CLR_CYC" data-visible="false"></th>
															<th data-field="POSTING_SUM_CODE" data-visible="false"></th>
															<th data-field="POSTING_SUM_DESC" data-visible="false"></th>
															<!-- <th data-field="CLR_TASK_FLG" data-visible="false"></th> -->
															<th data-field="CLR_SND_GRP_FLG" data-visible="false"></th>
															<th data-field="STR_TIME" data-visible="false"></th>
															<th data-field="END_TIME" data-visible="false"></th>
															<th data-field="BAT_PROC_FLG" data-visible="false"></th>
															<!-- <th data-field="CHK_NEED_CLR_FLG" data-visible="false"></th> -->
															<th data-field="VRFY_FLG" data-visible="false"></th>
															<th data-field="FEE_CALT_METH" data-visible="false"></th>

															<th data-field="FEE_CALT_FLG" data-visible="false"></th>
															<th data-field="FEE_SND_GRP_FLG" data-visible="false"></th>
															<th data-field="FEE_COLT_STR_TIME" data-visible="false"></th>
															<th data-field="FEE_COLT_END_TIME" data-visible="false"></th>
															<th data-field="FEE_TF_OUT_ACCT" data-visible="false"></th>
															<th data-field="FEE_SUM_CODE" data-visible="false"></th>
															<th data-field="FEE_TF_IN_ACCT" data-visible="false"></th>
															<th data-field="FEE_SUM_DESC" data-visible="false"></th>
															<th data-field="BAT_PROC_FEE_FLG" data-visible="false"></th>
															<th data-field="FEE_PAY_FLG" data-visible="false"></th>
															<th data-field="FEE_RULE_EXPR" data-visible="false"></th>
															<th data-field="MIN_FEE" data-visible="false"></th>
															<th data-field="MAX_FEE" data-visible="false"></th>
															<th data-field="FEE_PRCSN_TP" data-visible="false"></th>
															<th data-field="ACTION">操作</th>
														</tr>
													</thead>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-2 column"></div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false" id="backBtn3"
                                            name="backBtn3">上一步
                                    </button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false" id="nextBtn3"
                                            name="nextBtn3">下一步
                                    </button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false" id="saveBtn3"
                                            name="saveBtn3">暂存
                                    </button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
                                            id="cancelBtn3" name="cancelBtn3">取消
                                    </button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column"></div>
							</div>
						</form>
					</div>
					<div class="tab-pane" id="feeMsg">
						<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
							id="formId_358398" style="margin-top: 30px">
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-5 control-label">
											单位编号 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												data-bv-="true" id="ENTR_NO4" name="ENTR_NO4" disabled
												check-empty="true">
										</div>
									</div>
									<div ravo="rainbow_fx_radio" class="form-group">
										<label for="inputEmail3"
											class=" control-label  col-sm-5 control-label"
											style="visibility: visible"> 手续费类型</label>
										<div class="col-sm-4">
											<div class="radio-inline">
												<label style="visibility: visible"> <input
													id="optionsRadio1" type="radio" value="1" name="FEE_TP"
													class="" checkbtn="FEE_TP">按业务清算
												</label>
											</div>
											<div class="radio-inline">
												<label style="visibility: visible"> <input
													id="optionsRadio2" type="radio" value="0" name="FEE_TP"
													class="" checkbtn="FEE_TP">不收取手续费
												</label>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-6 column"></div>
							</div>

							<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
								id="feeComDiv">
								<div class="panel-heading">
									<div ravo="rainbow_fx_bj">
										<h4 contenteditable="false">业务参数</h4>
									</div>
								</div>
								<div class="panel-body" contenteditable="false">
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-6 column">
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-5 control-label"
													style="visibility: visible"> 手续费清算方式 </label>
												<div class="col-sm-4">
													<div class="radio">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="1"
															name="FEE_CLR_METH4" class="" checkbtn="FEE_CLR_METH4">手续费单独清算
														</label>
													</div>
													<div class="radio">
														<label style="visibility: visible"> <input
															id="optionsRadio2" type="radio" value="2"
															name="FEE_CLR_METH4" class="" checkbtn="FEE_CLR_METH4">手续费不清算
														</label>
													</div>
												</div>
											</div>
										</div>
										<div class="col-md-6 column">
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-3 control-label"
													style="visibility: visible"> 手续费计算方式 </label>
												<div class="col-sm-4">
													<div class="radio">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="radio" value="1"
															name="FEE_CALT_FLG4" class="">计算（以本地为准）
														</label>
													</div>
													<div class="radio">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="2"
															name="FEE_CALT_FLG4" class="">计算（以单位为准）
														</label>
													</div>
													<!-- <div class="radio">
														<label style="visibility: visible"> <input
															id="optionsRadio2" type="radio" value="3"
															name="FEE_CALT_FLG4" class="">不计算
														</label>
													</div> -->
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx_layout_panel" class="panel panel-default"
								id="feeMsgMin">
								<div class="panel-heading">
									<div ravo="rainbow_fx_bj">
										<h5 contenteditable="false">手续费清算参数</h5>
									</div>
								</div>
								<div class="panel-body" contenteditable="false">
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-6 column">
											<div ravo="rainbow_fx_checkbox" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-5 control-label"
													style="visibility: visible"> 手续费收取方式 </label>
												<div class="col-sm-7">
													<div class="checkbox-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="checkbox" value="1"
															name="FEE_SND_GRP_FLG4" class="">自动收取
														</label>
													</div>
													<div class="checkbox-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio2" type="checkbox" value="2"
															name="FEE_SND_GRP_FLG4" class="">柜面发起收取
														</label>
													</div>
												</div>
											</div>
										</div>
										<div class="col-md-6 column"></div>
									</div>

									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label class="control-label col-sm-5 control-label">
													自动收取时间（开始） </label>
												<div class="input-group-sm  col-sm-4">
													<input type="text" readonly="readonly" maxlength="20"
														class="form-control input-mini Wdate" value=""
														datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
														datetime-min-date="" datetime-max-date=""
														datetime-is-show-clear="true" datetime-is-show-week="true"
														datetime-is-show-today="true" datetime-default-value=""
														datetime-value-fmt="HHmmss"
														data-link-field="val_FEE_COLT_STR_TIME4"
														id="FEE_COLT_STR_TIME4"
														onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('FEE_COLT_STR_TIME4');}});"
														onchange="writeDateValue('FEE_COLT_STR_TIME4');">
													<input type="hidden" id="val_FEE_COLT_STR_TIME4" value=""
														name="FEE_COLT_STR_TIME4">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													手续费付款账号</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="FEE_TF_OUT_ACCT4" name="FEE_TF_OUT_ACCT4">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													手续费收款账号</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="FEE_TF_IN_ACCT4" name="FEE_TF_IN_ACCT4">
												</div>
											</div>
										</div>
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label class="control-label col-sm-3 control-label">
													自动收取时间（结束） </label>
												<div class="input-group-sm  col-sm-4">
													<input type="text" readonly="readonly" maxlength="20"
														class="form-control input-mini Wdate" value=""
														datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
														datetime-min-date="" datetime-max-date=""
														datetime-is-show-clear="true" datetime-is-show-week="true"
														datetime-is-show-today="true" datetime-default-value=""
														datetime-value-fmt="HHmmss"
														data-link-field="val_FEE_COLT_END_TIME4"
														id="FEE_COLT_END_TIME4"
														onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('FEE_COLT_END_TIME4');}});"
														onchange="writeDateValue('FEE_COLT_END_TIME4');">
													<input type="hidden" id="val_FEE_COLT_END_TIME4" value=""
														name="FEE_COLT_END_TIME4">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													手续费摘要码</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="FEE_SUM_CODE4" name="FEE_SUM_CODE4">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													手续费摘要描述</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="FEE_SUM_DESC4" name="FEE_SUM_DESC4">
												</div>
											</div>
										</div>
									</div>

									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-6 column">
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-5 control-label"
													style="visibility: visible">批量标志 </label>
												<div class="col-sm-7">
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="radio" value="1"
															name="BAT_PROC_FEE_FLG4" class="">统一处理
														</label>
													</div>
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="2"
															name="BAT_PROC_FEE_FLG4" class="">批次处理
														</label>
													</div>
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class=" col-sm-5 control-label">
													计算方式 </label>
												<div class="col-sm-4">
													<select data-role="multiselect" id="FEE_CALT_METH4"
														class="" name="FEE_CALT_METH4" data-max-height="300">
														<option value=''>请选择</option>
														<option value='1'>按成功笔数收取</option>
														<option value='2'>按总笔数收取</option>
														<option value='3'>按成功金额比例</option>
														<option value='4'>按总金额收取</option>
														<option value='5'>成功笔数按档次</option>
														<option value='6'>总笔数按档次</option>
														<option value='7'>成功金额按档次</option>
														<option value='8'>总金额按档次</option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-md-6 column">
											<div ravo="rainbow_fx_radio" class="form-group">
												<label for="inputEmail3"
													class=" control-label  col-sm-3 control-label"
													style="visibility: visible">手续费方向 </label>
												<div class="col-sm-7">
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio0" type="radio" value="1"
															name="FEE_PAY_FLG4" class="">单位支付
														</label>
													</div>
													<div class="radio-inline">
														<label style="visibility: visible"> <input
															id="optionsRadio1" type="radio" value="2"
															name="FEE_PAY_FLG4" class="">银行支付
														</label>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div ravo="rainbow_fx_layout" class="row clearfix"
										style="padding-left: 4.4%" id="FEE_RULE_EXPR_DIV4">
										<div class="col-md-5 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													第一档 </label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_STAT1" name="LEVEL_STAT1">
												</div>
												<label for="inputEmail3" class="col-sm-1 control-label">
													——</label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_END1" name="LEVEL_END1">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													第二档 </label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_STAT2" name="LEVEL_STAT2">
												</div>
												<label for="inputEmail3" class="col-sm-1 control-label">
													——</label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_END2" name="LEVEL_END2">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													第三档 </label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_STAT3" name="LEVEL_STAT3">
												</div>
												<label for="inputEmail3" class="col-sm-1 control-label">
													——</label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_END3" name="LEVEL_END3">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													第四档 </label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_STAT4" name="LEVEL_STAT4">
												</div>
												<label for="inputEmail3" class="col-sm-1 control-label">
													——</label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_END4" name="LEVEL_END4">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													第五档 </label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_STAT5" name="LEVEL_STAT5">
												</div>
												<label for="inputEmail3" class="col-sm-1 control-label">
													——</label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_END5" name="LEVEL_END5">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													第六档 </label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_STAT6" name="LEVEL_STAT6">
												</div>
												<label for="inputEmail3" class="col-sm-1 control-label">
													——</label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_END6" name="LEVEL_END6">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													第七档 </label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_STAT7" name="LEVEL_STAT7">
												</div>
												<label for="inputEmail3" class="col-sm-1 control-label">
													——</label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_END7" name="LEVEL_END7">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													第八档 </label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_STAT8" name="LEVEL_STAT8">
												</div>
												<label for="inputEmail3" class="col-sm-1 control-label">
													——</label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_END8" name="LEVEL_END8">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													第九档 </label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_STAT9" name="LEVEL_STAT9">
												</div>
												<label for="inputEmail3" class="col-sm-1 control-label">
													——</label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_END9" name="LEVEL_END9">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													第十档 </label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_STAT10" name="LEVEL_STAT10">
												</div>
												<label for="inputEmail3" class="col-sm-1 control-label">
													——</label>
												<div class="col-sm-2">
													<input type="text" class="form-control" placeholder=""
														id="LEVEL_END10" name="LEVEL_END10">
												</div>
											</div>
										</div>
										<div class="col-md-2 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													每笔（元）</label>
												<div class="col-sm-5">
													<input type="text" class="form-control" placeholder=""
														id="SINGLE1" name="SINGLE1">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													每笔（元）</label>
												<div class="col-sm-5">
													<input type="text" class="form-control" placeholder=""
														id="SINGLE1" name="SINGLE2">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													每笔（元）</label>
												<div class="col-sm-5">
													<input type="text" class="form-control" placeholder=""
														id="SINGLE1" name="SINGLE3">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													每笔（元）</label>
												<div class="col-sm-5">
													<input type="text" class="form-control" placeholder=""
														id="SINGLE1" name="SINGLE4">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													每笔（元）</label>
												<div class="col-sm-5">
													<input type="text" class="form-control" placeholder=""
														id="SINGLE1" name="SINGLE5">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													每笔（元）</label>
												<div class="col-sm-5">
													<input type="text" class="form-control" placeholder=""
														id="SINGLE1" name="SINGLE6">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													每笔（元）</label>
												<div class="col-sm-5">
													<input type="text" class="form-control" placeholder=""
														id="SINGLE1" name="SINGLE7">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													每笔（元）</label>
												<div class="col-sm-5">
													<input type="text" class="form-control" placeholder=""
														id="SINGLE1" name="SINGLE8">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													每笔（元）</label>
												<div class="col-sm-5">
													<input type="text" class="form-control" placeholder=""
														id="SINGLE1" name="SINGLE8">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													每笔（元）</label>
												<div class="col-sm-5">
													<input type="text" class="form-control" placeholder=""
														id="SINGLE1" name="SINGLE10">
												</div>
											</div>
										</div>
										<div class="col-md-4 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													收费百分比</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="CHARGE_P1" name="CHARGE_P1">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													收费百分比</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="CHARGE_P2" name="CHARGE_P2">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													收费百分比</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="CHARGE_P3" name="CHARGE_P3">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													收费百分比</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="CHARGE_P4" name="CHARGE_P4">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													收费百分比</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="CHARGE_P5" name="CHARGE_P5">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													收费百分比</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="CHARGE_P6" name="CHARGE_P6">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													收费百分比</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="CHARGE_P7" name="CHARGE_P7">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													收费百分比</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="CHARGE_P8" name="CHARGE_P8">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													收费百分比</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="CHARGE_P9" name="CHARGE_P9">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													收费百分比</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="CHARGE_P10" name="CHARGE_P10">
												</div>
											</div>
										</div>
									</div>

									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													最低收费金额</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="MIN_FEE4" name="MIN_FEE4">
												</div>
											</div>
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-5 control-label">
													手续费精度</label>
												<div class="col-sm-4">
													<select data-role="multiselect" id="FEE_PRCSN_TP" class=""
														name="FEE_PRCSN_TP4" data-max-height="300">
														<option value="">请选择</option>
														<option value="1">元</option>
														<option value="2">分</option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-md-6 column">
											<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													最高收费金额</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														id="MAX_FEE4" name="MAX_FEE4">
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div ravo="rainbow_fx_layout" class="row clearfix"
								style="border-top: solid #ddd 1px;">
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group"></div>
								</div>
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group"></div>
								</div>
							</div>
							<div id="table4Div">
								<div ravo="rainbow_fx_layout" class="row clearfix"
									id="tableBtn4">
									<div class="col-md-6 column"></div>
									<div class="col-md-6 column">
										<div ravo="rainbow_fx" class="form-group">
											<div class="col-md-3 column"></div>
											<div class="col-md-2 column">
												<shiro:haspermission name="anno">
													<button ravo="rainbow_fx" type="button"
														class="btn btn-default" contenteditable="false"
                                                        id="addRowBtn4">增加一行
                                                </button>
												</shiro:haspermission>
											</div>
											<div class="col-md-2 column">
												<shiro:haspermission name="anno">
													<button ravo="rainbow_fx" type="button"
														class="btn btn-default" contenteditable="false"
                                                        id="reviceRowBtn4">确认修改
                                                </button>
												</shiro:haspermission>
											</div>
										</div>
									</div>
								</div>

								<div ravo="rainbow_fx_layout" class="row clearfix"
									style="margin-bottom: 20px">
									<div class="col-md-12 column">
										<div ravo="rainbow_fx_layout" class="row clearfix">
											<label for="inputEmail3"
												class="col-sm-1 control-label control-label"
												style="visibility: visible; margin-left: 12%"></label>
											<div class="col-sm-7">
												<table id="table4" data-toggle="table"
													data-first-load="false" data-click-to-select="true"
													data-show-export="false" data-show-refresh="false"
													data-show-toggle="false" data-show-columns="false"
													data-pagination="false" data-search="false"
													data-method="post" data-undefined-text="**"
													data-height="300"
													data-content-type="application/x-www-form-urlencoded"
													ravo="rainbow_fx_bj"
													class="table table-hover table-bordered"
													data-single-select="true">

													<thead style="">
														<tr>
															<th data-field=FEE_TF_OUT_ACCT>付款账户</th>
															<th data-field="FEE_TF_IN_ACCT">收款账户</th>
															<th data-field="ACTION">操作</th>

															<th data-field="FEE_CLR_METH" data-visible="false"></th>
															<th data-field="FEE_CALT_FLG" data-visible="false"></th>
															<th data-field="FEE_COLT_STR_TIME" data-visible="false"></th>
															<th data-field="FEE_COLT_END_TIME" data-visible="false"></th>
															<th data-field="FEE_TF_OUT_ACCT" data-visible="false"></th>
															<th data-field="FEE_SUM_CODE" data-visible="false"></th>
															<th data-field="FEE_TF_IN_ACCT" data-visible="false"></th>
															<th data-field="FEE_SUM_DESC" data-visible="false"></th>
															<th data-field="BAT_PROC_FEE_FLG" data-visible="false"></th>
															<th data-field="FEE_PAY_FLG" data-visible="false"></th>
															<th data-field="FEE_RULE_EXPR" data-visible="false"></th>
															<th data-field="MIN_FEE" data-visible="false"></th>
															<th data-field="MAX_FEE" data-visible="false"></th>
															<th data-field="FEE_PRCSN_TP" data-visible="false"></th>
															<th data-field="FEE_CALT_METH" data-visible="false"></th>
														</tr>
													</thead>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-2 column"></div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false" id="backBtn4"
                                            name="backBtn4">上一步
                                    </button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false" id="nextBtn4"
                                            name="nextBtn4">下一步
                                    </button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false" id="saveBtn4"
                                            name="saveBtn4">暂存
                                    </button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
                                            id="cancelBtn4" name="cancelBtn4">取消
                                    </button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column"></div>
							</div>
						</form>
					</div>
					<div class="tab-pane" id="chkMsg">
						<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
							id="formId_145196" style="margin-top: 30px">
							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-5 control-label">
											单位编号 </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" placeholder=""
												data-bv-="true" id="ENTR_NO5" name="ENTR_NO5" disabled
												check-empty="true">
										</div>
									</div>

									<div ravo="rainbow_fx" class="form-group">
										<label for="inputEmail3" class="col-sm-5 control-label">
											对账类型</label>
										<div class="col-sm-4">
											<select data-role="multiselect" id="CHK_FLG" class=""
												name="CHK_TP" data-max-height="300" checkbtn="CHK_TP">
												<option value="">请选择</option>
												<option value="1">三方对账</option>
												<option value="2">两方对账</option>
												<option value="0">不对账</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-6 column"></div>
							</div>
							<div ravo="rainbow_fx_layout" class="row clearfix"
								style="border-top: solid #ddd 1px;">
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group"></div>
								</div>
								<div class="col-md-6 column">
									<div ravo="rainbow_fx" class="form-group"></div>
								</div>
							</div>

							<div id='chkDiv'>
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-6 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label">
												对账维度</label>
											<div class="col-sm-4">
												<select data-role="multiselect" id="CHK_DIM_TP" class=""
													name="CHK_DIM_TP" data-max-height="300">
													<option value="">请选择</option>
													<option value="1">集中对账</option>
													<option value="2">子单位对账</option>
												</select>
											</div>
										</div>
									</div>
									<div class="col-md-6 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-3 control-label">
												下周期对账</label>
											<div class="col-sm-4">
												<select data-role="multiselect" id="CONT_FLG" class=""
													name="CONT_FLG" data-max-height="300" checkbtn="CONT_FLG">
													<option value="">请选择</option>
													<option value="Y">是</option>
													<option value="N">否</option>
												</select>
											</div>
										</div>
									</div>
								</div>

								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-6 column">
										<div ravo="rainbow_fx_checkbox" class="form-group">
											<label for="inputEmail3"
												class=" control-label  col-sm-5 control-label"
												style="visibility: visible"> 对账发起方式</label>
											<div class="col-sm-7">
												<div class="checkbox-inline">
													<label style="visibility: visible"> <input
														id="optionsRadio1" type="checkbox" value="1"
														name="CHK_SND_GRP_FLG" class="">自动对账
													</label>
												</div>
												<div class="checkbox-inline">
													<label style="visibility: visible"> <input
														id="optionsRadio2" type="checkbox" value="2"
														name="CHK_SND_GRP_FLG" class="">柜面发起对账
													</label>
												</div>
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label">
												对账周期（天）</label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													data-bv-="true" id="CHK_CYC" name="CHK_CYC">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label class="control-label col-sm-5 control-label">
												自动清算时间（起始） </label>
											<div class="input-group-sm  col-sm-4">
												<input type="text" readonly="readonly" maxlength="20"
													class="form-control input-mini Wdate" value=""
													datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
													datetime-min-date="" datetime-max-date=""
													datetime-is-show-clear="true" datetime-is-show-week="true"
													datetime-is-show-today="true" datetime-default-value=""
													datetime-value-fmt="HHmmss" data-link-field="val_STR_TIME5"
													id="STR_TIME5"
													onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('STR_TIME5');}});"
													onchange="writeDateValue('STR_TIME5');"> <input
													type="hidden" id="val_STR_TIME5" value="" name="STR_TIME5">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label class="control-label col-sm-5 control-label">
												月末开始时间 </label>
											<div class="input-group-sm  col-sm-4">
												<input type="text" readonly="readonly" maxlength="20"
													class="form-control input-mini Wdate" value=""
													datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
													datetime-min-date="" datetime-max-date=""
													datetime-is-show-clear="true" datetime-is-show-week="true"
													datetime-is-show-today="true" datetime-default-value=""
													datetime-value-fmt="HHmmss"
													data-link-field="val_MONTH_END_STR_TIME"
													id="MONTH_END_STR_TIME"
													onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('MONTH_END_STR_TIME');}});"
													onchange="writeDateValue('MONTH_END_STR_TIME');"> <input
													type="hidden" id="val_MONTH_END_STR_TIME" value=""
													name="MONTH_END_STR_TIME">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label class="control-label col-sm-5 control-label">
												季末开始时间 </label>
											<div class="input-group-sm  col-sm-4">
												<input type="text" readonly="readonly" maxlength="20"
													class="form-control input-mini Wdate" value=""
													datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
													datetime-min-date="" datetime-max-date=""
													datetime-is-show-clear="true" datetime-is-show-week="true"
													datetime-is-show-today="true" datetime-default-value=""
													datetime-value-fmt="HHmmss"
													data-link-field="val_QUARTER_END_STR_TIME"
													id="QUARTER_END_STR_TIME"
													onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('QUARTER_END_STR_TIME');}});"
													onchange="writeDateValue('QUARTER_END_STR_TIME');">
												<input type="hidden" id="val_QUARTER_END_STR_TIME" value=""
													name="QUARTER_END_STR_TIME">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label class="control-label col-sm-5 control-label">
												年末开始时间 </label>
											<div class="input-group-sm  col-sm-4">
												<input type="text" readonly="readonly" maxlength="20"
													class="form-control input-mini Wdate" value=""
													datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
													datetime-min-date="" datetime-max-date=""
													datetime-is-show-clear="true" datetime-is-show-week="true"
													datetime-is-show-today="true" datetime-default-value=""
													datetime-value-fmt="HHmmss"
													data-link-field="val_YEAR_END_STR_TIME"
													id="YEAR_END_STR_TIME"
													onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('YEAR_END_STR_TIME');}});"
													onchange="writeDateValue('YEAR_END_STR_TIME');"> <input
													type="hidden" id="val_YEAR_END_STR_TIME" value=""
													name="YEAR_END_STR_TIME">
											</div>
										</div>
										<div ravo="rainbow_fx_radio" class="form-group">
											<label for="inputEmail3"
												class=" control-label  col-sm-5 control-label"
												style="visibility: visible">对账文件获取/推送模式</label>
											<div class="col-sm-7">
												<div class="radio">
													<label style="visibility: visible"> <input
														id="optionsRadio1" type="radio" value="0"
														name="CHK_RSLT_PUSH_FLG" class="">不推送
													</label>
												</div>
												<div class="radio">
													<label style="visibility: visible"> <input
														id="optionsRadio2" type="radio" value="1"
														name="CHK_RSLT_PUSH_FLG" class="">文件
													</label>
												</div>
												<div class="radio">
													<label style="visibility: visible"> <input
														id="optionsRadio2" type="radio" value="2"
														name="CHK_RSLT_PUSH_FLG" class="">文件+通知报文
													</label>
												</div>
											</div>
										</div>
									</div>
									<div class="col-md-6 column">
										<div ravo="rainbow_fx_radio" class="form-group">
											<label for="inputEmail3"
												class=" control-label  col-sm-3 control-label"
												style="visibility: visible">柜面对账模式 </label>
											<div class="col-sm-7">
												<div class="radio-inline">
													<label style="visibility: visible"> <input
														id="optionsRadio0" type="radio" value="1" name="ASYNC_FLG"
														class="">同步
													</label>
												</div>
												<div class="radio-inline">
													<label style="visibility: visible"> <input
														id="optionsRadio1" type="radio" value="2" name="ASYNC_FLG"
														class="">异步
													</label>
												</div>
											</div>
										</div>
										<div ravo="rainbow_fx_checkbox" class="form-group">
											<label for="inputEmail3"
												class=" control-label  col-sm-3 control-label"
												style="visibility: visible"> 处理标志</label>
											<div class="col-sm-7">
												<div class="checkbox-inline">
													<label style="visibility: visible"> <input
														id="optionsRadio1" type="checkbox" value="N"
														name="MONTH_END_FLG" class="">月末
													</label>
												</div>
												<div class="checkbox-inline">
													<label style="visibility: visible"> <input
														id="optionsRadio2" type="checkbox" value="N"
														name="QUARTER_END_FLG" class="">季末
													</label>
												</div>
												<div class="checkbox-inline">
													<label style="visibility: visible"> <input
														id="optionsRadio2" type="checkbox" value="N"
														name="YEAR_END_FLG" class="">年末
													</label>
												</div>
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label class="control-label col-sm-3 control-label">
												自动清算时间（结束） </label>
											<div class="input-group-sm  col-sm-4">
												<input type="text" readonly="readonly" maxlength="20"
													class="form-control input-mini Wdate" value=""
													datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
													datetime-min-date="" datetime-max-date=""
													datetime-is-show-clear="true" datetime-is-show-week="true"
													datetime-is-show-today="true" datetime-default-value=""
													datetime-value-fmt="HHmmss" data-link-field="val_END_TIME5"
													id="END_TIME5"
													onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('END_TIME5');}});"
													onchange="writeDateValue('END_TIME5');"> <input
													type="hidden" id="val_END_TIME5" value="" name="END_TIME5">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label class="control-label col-sm-3 control-label">
												月末结束时间 </label>
											<div class="input-group-sm  col-sm-4">
												<input type="text" readonly="readonly" maxlength="20"
													class="form-control input-mini Wdate" value=""
													datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
													datetime-min-date="" datetime-max-date=""
													datetime-is-show-clear="true" datetime-is-show-week="true"
													datetime-is-show-today="true" datetime-default-value=""
													datetime-value-fmt="HHmmss"
													data-link-field="val_MONTH_END_END_TIME"
													id="MONTH_END_END_TIME"
													onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('MONTH_END_END_TIME');}});"
													onchange="writeDateValue('MONTH_END_END_TIME');"> <input
													type="hidden" id="val_MONTH_END_END_TIME" value=""
													name="MONTH_END_END_TIME">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label class="control-label col-sm-3 control-label">
												季末结束时间 </label>
											<div class="input-group-sm  col-sm-4">
												<input type="text" readonly="readonly" maxlength="20"
													class="form-control input-mini Wdate" value=""
													datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
													datetime-min-date="" datetime-max-date=""
													datetime-is-show-clear="true" datetime-is-show-week="true"
													datetime-is-show-today="true" datetime-default-value=""
													datetime-value-fmt="HHmmss"
													data-link-field="val_QUARTER_END_END_TIME"
													id="QUARTER_END_END_TIME"
													onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('QUARTER_END_END_TIME');}});"
													onchange="writeDateValue('QUARTER_END_END_TIME');">
												<input type="hidden" id="val_QUARTER_END_END_TIME" value=""
													name="QUARTER_END_END_TIME">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label class="control-label col-sm-3 control-label">
												年末结束时间 </label>
											<div class="input-group-sm  col-sm-4">
												<input type="text" readonly="readonly" maxlength="20"
													class="form-control input-mini Wdate" value=""
													datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
													datetime-min-date="" datetime-max-date=""
													datetime-is-show-clear="true" datetime-is-show-week="true"
													datetime-is-show-today="true" datetime-default-value=""
													datetime-value-fmt="HHmmss"
													data-link-field="val_YEAR_END_END_TIME"
													id="YEAR_END_END_TIME"
													onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('YEAR_END_END_TIME');}});"
													onchange="writeDateValue('YEAR_END_END_TIME');"> <input
													type="hidden" id="val_YEAR_END_END_TIME" value=""
													name="YEAR_END_END_TIME">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-3 control-label">
												对账文件格式</label>
											<div class="col-sm-4">
												<input type="text" class="form-control" placeholder=""
													data-bv-="true" id="CHK_FILE_NAME" name="CHK_FILE_NAME">
											</div>
										</div>
									</div>
								</div>
							</div>

							<div ravo="rainbow_fx_layout" class="row clearfix">
								<div class="col-md-2 column"></div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false" id="backBtn5"
                                            name="backBtn5">上一步
                                    </button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false" id="nextBtn5"
                                            name="nextBtn5">下一步
                                    </button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false" id="saveBtn5"
                                            name="saveBtn5">暂存
                                    </button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column">
									<shiro:haspermission name="anno">
										<button ravo="rainbow_fx" type="button"
											class="btn btn-default" contenteditable="false"
                                            id="cancelBtn5" name="cancelBtn5">取消
                                    </button>
									</shiro:haspermission>
								</div>
								<div class="col-md-2 column"></div>
							</div>
						</form>
					</div>
					<div class="tab-pane active in" id="preview">
						<div ravo="rainbow_fx_layout_panel" class="panel panel-default">
							<div class="panel-heading">
								<div ravo="rainbow_fx_bj">
									<h4 contenteditable="false">预览</h4>
								</div>
							</div>
							<div class="panel-body" contenteditable="false" id="previewDiv">
								<div ravo="rainbow_fx_layout_panel"
									class="panel panel-default preSmallBox" id="detailPanel1">
									<div class="preTitle1 preTitle2 " State='1'>
										<div ravo="rainbow_fx_bj">
											<img class='preCross preDisNone preRotate180'
												src="<%=basePath%>/b_base/jquery-preview/image/cross.png" />
											<img class='preDown'
												src="<%=basePath%>/b_base/jquery-preview/image/down_arrow_64px_1205405_easyicon.net.png" />
											<h5 contenteditable="false">单位基本信息</h5>
										</div>
									</div>
									<div class="panel-body preContent1" contenteditable="false"
										id="detail1"></div>
								</div>



								<div ravo="rainbow_fx_layout_panel"
									class="panel panel-default preSmallBox preWidth5"
									id="detailPanelSign" style="display: block">
									<div class="preTitle1" State='0'>
										<div ravo="rainbow_fx_bj">
											<img class='preCross'
												src="<%=basePath%>/b_base/jquery-preview/image/cross.png" />
											<img class='preDown preDisNone'
												src="<%=basePath%>/b_base/jquery-preview/image/down_arrow_64px_1205405_easyicon.net.png" />
											<h5 contenteditable="false">业务签约信息</h5>
										</div>
									</div>
									<div class="panel-body preContent1 preHeight0"
										contenteditable="false" id="detail3">
										<div ravo="rainbow_fx_layout" class="row clearfix">
											<div class="col-md-12 column">
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3"
														class="col-sm-1 control-label control-label"
														style="visibility: visible"></label>
													<div class="col-sm-9">
														<table id="tableDetailSign" data-toggle="table"
															data-first-load="false" data-click-to-select="true"
															data-show-export="false" data-show-refresh="false"
															data-show-toggle="false" data-show-columns="false"
															data-pagination="false" data-search="false"
															data-query-params="queryParams" data-method="post"
															data-undefined-text="**" data-height="200"
															data-content-type="application/x-www-form-urlencoded"
															ravo="rainbow_fx_bj" class="table table-hover"
															data-single-select="true" data-striped="true">
															<thead style="">
																<tr>
																	<th data-field="SIGN_FLG_STR">是否需要签约</th>
																	<th data-field="SIGN_FLG" data-visible="false"></th>
																	<th data-field="RULE_NAME">签约规则名称</th>
																</tr>
															</thead>
														</table>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>

								<div ravo="rainbow_fx_layout_panel"
									class="panel panel-default preSmallBox preWidth5"
									id="detailPanel3" style="display: block">
									<div class="preTitle1" State='0'>
										<div ravo="rainbow_fx_bj">
											<img class='preCross'
												src="<%=basePath%>/b_base/jquery-preview/image/cross.png" />
											<img class='preDown preDisNone'
												src="<%=basePath%>/b_base/jquery-preview/image/down_arrow_64px_1205405_easyicon.net.png" />
											<h5 contenteditable="false">单位清算信息</h5>
										</div>
									</div>
									<div class="panel-body preContent1 preHeight0"
										contenteditable="false" id="detail3">
										<div ravo="rainbow_fx_layout" class="row clearfix">
											<div class="col-md-12 column">
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3"
														class="col-sm-1 control-label control-label"
														style="visibility: visible"></label>
													<div class="col-sm-9">
														<table id="tableDetail3" data-toggle="table"
															data-first-load="false" data-click-to-select="true"
															data-show-export="false" data-show-refresh="false"
															data-show-toggle="false" data-show-columns="false"
															data-pagination="false" data-search="false"
															data-query-params="queryParams" data-method="post"
															data-undefined-text="**" data-height="200"
															data-content-type="application/x-www-form-urlencoded"
															ravo="rainbow_fx_bj" class="table table-hover"
															data-single-select="true" data-striped="true">
															<thead style="">
																<tr>
																	<th data-field="INTRM_ACCT">过渡账户</th>
																	<th data-field="ENTR_ACCT">客户账户</th>
																	<th data-field="ENTR_ACCT_BANK">行号</th>
																</tr>
															</thead>
														</table>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>

								<div ravo="rainbow_fx_layout_panel"
									class="panel panel-default preSmallBox preWidth5"
									id="detailPanel4" style="display: block">
									<div class="preTitle1" State='0'>
										<div ravo="rainbow_fx_bj">
											<img class='preCross'
												src="<%=basePath%>/b_base/jquery-preview/image/cross.png" />
											<img class='preDown preDisNone'
												src="<%=basePath%>/b_base/jquery-preview/image/down_arrow_64px_1205405_easyicon.net.png" />
											<h5 contenteditable="false">单位手续费配置</h5>
										</div>
									</div>
									<div class="panel-body preContent1 preHeight0"
										contenteditable="false" id="detail4">
										<div ravo="rainbow_fx_layout" class="row clearfix">
											<div class="col-md-12 column">
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3"
														class="col-sm-1 control-label control-label"
														style="visibility: visible"></label>
													<div class="col-sm-9">
														<table id="tableDetail4" data-toggle="table"
															data-first-load="false" data-click-to-select="true"
															data-show-export="false" data-show-refresh="false"
															data-show-toggle="false" data-show-columns="false"
															data-pagination="false" data-search="false"
															data-query-params="queryParams" data-method="post"
															data-undefined-text="**" data-height="200"
															data-content-type="application/x-www-form-urlencoded"
															ravo="rainbow_fx_bj" class="table table-hover"
															data-single-select="true" data-striped="true">
															<thead style="">
																<tr>
																	<th data-field=FEE_TF_OUT_ACCT>付款账户</th>
																	<th data-field="FEE_TF_IN_ACCT">收款账户</th>
																</tr>
															</thead>
														</table>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div ravo="rainbow_fx_layout_panel"
									class="panel panel-default preSmallBox preWidth5"
									id="detailPanel5" style="display: block">
									<div class="preTitle1" State='0'>
										<div ravo="rainbow_fx_bj">
											<img class='preCross'
												src="<%=basePath%>/b_base/jquery-preview/image/cross.png" />
											<img class='preDown preDisNone'
												src="<%=basePath%>/b_base/jquery-preview/image/down_arrow_64px_1205405_easyicon.net.png" />
											<h5 contenteditable="false">单位对账信息</h5>
										</div>
									</div>
									<div class="panel-body preContent1 preHeight0"
										contenteditable="false" id="detail5"></div>
								</div>

								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-2 column"></div>
									<div class="col-md-2 column">
										<shiro:haspermission name="anno">
											<button ravo="rainbow_fx" type="button"
												class="btn btn-default" contenteditable="false"
                                                id="backBtn6" name="backBtn6">上一步
                                        </button>
										</shiro:haspermission>
									</div>
									<div class="col-md-2 column">
										<shiro:haspermission name="anno">
											<button ravo="rainbow_fx" type="button"
												class="btn btn-default" contenteditable="false"
                                                id="saveBtn6" name="saveBtn6">完成
                                        </button>
										</shiro:haspermission>
									</div>
									<div class="col-md-2 column">
										<shiro:haspermission name="anno">
											<button ravo="rainbow_fx" type="button"
												class="btn btn-default" contenteditable="false"
                                                id="cancelBtn6" name="cancelBtn6">取消全部
                                        </button>
										</shiro:haspermission>
									</div>
									<div class="col-md-2 column"></div>
									<div class="col-md-2 column"></div>
								</div>
							</div>
						</div>
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
