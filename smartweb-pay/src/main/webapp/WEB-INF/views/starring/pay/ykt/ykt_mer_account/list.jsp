<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!-- JSTL -->
<%@ page language="java" pageEncoding="UTF-8" %>
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
    <script type="text/javascript" src="<%=basePath%>/b_base/jqueryForm.js"
            charset="utf-8"></script>
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
            rel="stylesheet" media="screen"/>

    <!-- JQUERY TREETABLE -->
    <script type="text/javascript" src="<%=basePath%>/b_base/treeTable/jquery.treeTable.js"
            charset="utf-8"></script>
    <link href="<%=basePath%>/b_base/treeTable/themes/default/treeTable.min.css" rel="stylesheet" type="text/css"/>
    <!-- /JQUERY TREETABLE -->

    <!-- BOOTSTRAP -->
    <link type="text/css" rel="stylesheet"
          href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
    <link type="text/css" rel="stylesheet"
          href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap-theme.css"/>
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
            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/dist/locale/bootstrap-table-zh-CN.js"></script>
    <script type="text/javascript"
            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/tableExport.js"></script>
    <!-- /table-->

    <!-- FileInput Css-->
    <%--    <link--%>
    <%--            href="<%=basePath%>/b_ide/bootstrap/module/FileInput/css/fileinput.css"--%>
    <%--            media="all" rel="stylesheet" type="text/css"/>--%>
    <%--    <script src="<%=basePath%>/b_ide/bootstrap/module/Chart/Chart.js"></script>--%>
    <%--    <script--%>
    <%--            src="<%=basePath%>/b_ide/bootstrap/module/Chart/src/Chart.Doughnut.js"></script>--%>
    <%--    <script--%>
    <%--            src="<%=basePath%>/b_ide/bootstrap/module/Treeview/js/bootstrap-treeview.js"></script>--%>
    <%--    <script--%>
    <%--            src="<%=basePath%>/b_ide/bootstrap/module/FileInput/js/fileinput.js"--%>
    <%--            type="text/javascript"></script>--%>
    <%--    <script--%>
    <%--            src="<%=basePath%>/b_ide/bootstrap/module/FileInput/js/locales/zh.js"--%>
    <%--            type="text/javascript"></script>--%>
    <!-- /FileInput -->

    <!-- Validator -->
    <link type="text/css" rel="stylesheet"
          href="<%=basePath%>/b_base/bootstrapValidator/css/bootstrapValidator.min.css"/>
    <script type="text/javascript"
            src="<%=basePath%>/b_base/bootstrapValidator/js/bootstrapValidator.min.js"
            charset="utf-8"></script>
    <!-- /Validator -->

    <!-- Multiselect -->
    <link type="text/css" rel="stylesheet"
          href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/css/bootstrap-multiselect.css"/>
    <script type="text/javascript"
            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js"></script>
    <script type="text/javascript"
            src="<%=basePath%>/b_ide/bootstrap/js/jquery.multiselectcus.js"></script>
    <!-- /Multiselect -->

    <!-- TABS -->
    <%--    <script type="text/javascript"--%>
    <%--            src="<%=basePath%>/b_ide/bootstrap/module/tabs/js/bootstrap-tabs.js"></script>--%>
    <%--    <!-- /TABS -->--%>

    <%--    <!-- ystep -->--%>
    <%--    <script type="text/javascript"--%>
    <%--            src="<%=basePath%>/b_ide/bootstrap/module/ystep-master/js/ystep.js"></script>--%>
    <%--    <link type="text/css" rel="stylesheet"--%>
    <%--          href="<%=basePath%>/b_ide/bootstrap/module/ystep-master/css/ystep.css"/>--%>
    <%--    <!-- /ystep -->--%>

    <%--    <!-- JBox -->--%>
    <%--    <link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css"--%>
    <%--          rel="stylesheet"/>--%>
    <%--    <script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.src.js"--%>
    <%--            type="text/javascript"></script>--%>

    <%--    <!-- UEditor -->--%>
    <%--    <script src="${ctxStatic}/ueditor/ueditor.config.js"--%>
    <%--            type="text/javascript"></script>--%>
    <%--    <script src="${ctxStatic}/ueditor/ueditor.all.min.js"--%>
    <%--            type="text/javascript"></script>--%>
    <%--    <script src="${ctxStatic}/ueditor/ueditor.parse.min.js"--%>
    <%--            type="text/javascript"></script>--%>
    <%--    <script src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"--%>
    <%--            type="text/javascript"></script>--%>

    <%--    <!-- FontIcon -->--%>
    <%--    <link rel="stylesheet"--%>
    <%--          href="${ctxStatic}/mainframe/fonticon/iconfont.css">--%>

    <script type="text/javascript"
            src="<%=basePath%>/b_base/common/smartweb.js"></script>

    <!-- check -->
    <script type="text/javascript"
            src="<%=basePath%>/b_base/common/check.js"></script>
    <script type="text/javascript"
            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/jspdf/libs/base64.js"></script>
    <script type="text/javascript"
            src="<%=basePath%>/b_base/common/formCheck.js"></script>
    <link type="text/css" rel="stylesheet"
          href="<%=basePath%>/b_base/common/formCheck.css"/>
    <!-- change skin -->
    <link
            href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css"
            type="text/css" rel="stylesheet"/>

    <!-- Self reference JS-->
    <script type="text/javascript"
            src="<%=basePath%>/b_base/views/starring/pay/com/com.js"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="<%=basePath%>/b_base/views/starring/pay/ykt/ykt_mer_account/list.js"
            charset="utf-8"></script>

    <style>
        #iframe_list {
            height: 750px !important;
        }
    </style>
    <title>Insert title here</title>
</head>
<body>

<div ravo="rainbow_fx_layout" class="row clearfix">
    <div class="col-md-12 column">
        <div ravo="rainbow_fx_layout_tab" class="tabbable" id="navigations">
            <div class="tab-content">
                <div class="tab-pane in active" id="panel1">
                    <form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="from">
                        <div id="top1" ravo="rainbow_fx_layout" class="row clearfix" style="margin: 20px">
                            <div class="col-md-12 column">
                                <div class="col-md-4 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="busiNo" class="col-sm-5 control-label control-label"
                                               style="visibility: visible">
                                            业务名称：
                                        </label>
                                        <div class="col-sm-6">
                                            <select
                                                    data-role="multiselect"
                                                    id="busiNo"
                                                    class=""
                                                    name="busiNo"
                                                    data-max-height="300"
                                                    data-button-width="225"
                                                    data-enable-filtering="true"
                                                    data-enable-full-value-filtering="false"
                                                    data-enable-case-insensitive-filtering="true"
                                                    data-filter-placeholder="搜索"
                                                    checkBtn="busiNo"
                                            >
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4 column">
                                    <label for="endTime" class="col-sm-5 control-label control-label"
                                           style="visibility: visible">
                                        清算日期：
                                    </label>
                                    <input id="endTime" name="endTime" type="text" readonly="readonly"
                                           class="input-medium Wdate col-sm-6"
                                           value=""
                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false, minDate: '{%y-3}-%M-%d',maxDate: '%y-%M-%d'});"/>
                                </div>
                                <div class="col-md-1 column">
                                    <div class="col-md-3"></div>
                                    <button
                                            ravo="rainbow_fx"
                                            type="button"
                                            class="btn btn-info pull-left"
                                            contenteditable="false"
                                            id="qryBtn"
                                            name="qryBtn"
                                    >
                                        查询
                                    </button>
                                </div>
                                <div class="col-md-1 column">
                                    <button
                                            ravo="rainbow_fx"
                                            type="button"
                                            class="btn btn-info pull-left"
                                            contenteditable="false"
                                            id="export"
                                            name="export"
                                    >
                                        导出
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                    <!--
                    <hr style="margin-left: 6%;width: 80%;border:1px dashed black;">
                    <div id="top2" ravo="rainbow_fx_layout" class="row clearfix" style="margin: 20px">
                        <div class="col-md-12 column" style="margin-bottom: 4px">
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="sumNum" class="col-sm-6 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        缴费总笔数：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="sumNum"
                                               readonly
                                               name="sumNum"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="sumTranAmt" class="col-sm-6 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        缴费总金额：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="sumTranAmt"
                                               readonly
                                               name="sumTranAmt"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="sumRealAmt" class="col-sm-5 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        清算总金额：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="sumRealAmt" readonly
                                               name="sumRealAmt"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12 column" style="margin-bottom: 4px">
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="sumMerSubsidy" class="col-sm-6 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        商户补贴总金额：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="sumMerSubsidy"
                                               readonly
                                               name="sumMerSubsidy"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="sumBankSubsidy" class="col-sm-6 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        银行补贴总金额：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="sumBankSubsidy"
                                               readonly
                                               name="sumBankSubsidy"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="sumFeeAmt" class="col-sm-5 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        手续费总金额：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="sumFeeAmt" readonly
                                               name="sumFeeAmt"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12 column" style="margin-bottom: 4px">

                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="sumMualAmt" class="col-sm-6 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        差错补录：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="sumMualAmt"
                                               readonly
                                               name="sumMualAmt"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="sumOthAmt" class="col-sm-6 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        码牌总金额：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="sumOthAmt"
                                               readonly
                                               name="sumOthAmt"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="sumRfndAmt" class="col-sm-5 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        退费总金额：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="sumRfndAmt"
                                               readonly
                                               name="sumRfndAmt"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    -->
                    <table id="merAccountTable"></table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
