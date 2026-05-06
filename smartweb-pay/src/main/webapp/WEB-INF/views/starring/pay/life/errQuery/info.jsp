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

    <!-- /Validator -->
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

    <script type="text/javascript"
            src="<%=basePath%>/b_base/common/smartweb.js"></script>

    <!-- check -->
    <script type="text/javascript"
            src="<%=basePath%>/b_base/common/check.js"></script>
    <script type="text/javascript"
            src="<%=basePath%>/b_base/common/formCheck.js"></script>
    <link type="text/css" rel="stylesheet"
          href="<%=basePath%>/b_base/common/formCheck.css"/>
    <script type="text/javascript"
            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/jspdf/libs/base64.js"></script>

    <!-- change skin -->
    <link
            href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css"
            type="text/css" rel="stylesheet"/>

    <!-- Self reference JS-->
    <script type="text/javascript"
            src="<%=basePath%>/b_base/views/starring/pay/com/com.js"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="<%=basePath%>/b_base/views/starring/pay/life/errQuery/info.js"
            charset="utf-8"></script>

    <style>
        #iframe_detail {
            height: 400px !important;
        }

        .marginTop {
            margin-top: 20px;
        }

        .fixed-table-body {
            height: 320px !important;
        }

        .textRight {
            text-align: right;
        }
    </style>
    <title>Insert title here</title>
</head>
<body>

<div ravo="rainbow_fx_layout" class="row clearfix">
    <div class="col-md-12 column ">
        <div ravo="rainbow_fx_layout_tab" class="tabbable" id="navigations">
            <div class="tab-content">
                <div class="tab-pane in active" id="panel1" style="height: 500px">
                    <form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="from" hidden>
                        <div id="top1" ravo="rainbow_fx_layout" class="row clearfix" style="margin: 20px">
                            <div class="col-md-12 column">
                                <div class="col-md-4 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="origPlatDate" class="col-sm-2 control-label control-label"
                                               style="visibility: visible">
                                            原平台日期：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" placeholder="" id="origPlatDate"
                                                   name="origPlatDate" readonly/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="origPlatSeq" class="col-sm-2 control-label control-label"
                                               style="visibility: visible">
                                            原平台流水：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" placeholder="" id="origPlatSeq"
                                                   name="origPlatSeq" readonly/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="busiNo" class="col-sm-2 control-label control-label"
                                               style="visibility: visible">
                                            业务号：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" placeholder="" id="busiNo"
                                                   name="busiNo" readonly/>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </form>
                    <div class="col-md-12 column marginTop">
                        <label for="busiInfo" class="col-sm-1 control-label textRight"
                               style="visibility: visible;margin-top: 0.8%">
                            业务：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="busiInfo"
                                   name="busiInfo" readonly/>
                        </div>
                        <label for="chnlInfo" class="col-sm-1 control-label textRight"
                               style="visibility: visible;margin-top: 0.8%">
                            渠道：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="chnlInfo"
                                   name="chnlInfo" readonly/>
                        </div>
                        <label for="payNo" class="col-sm-1 control-label textRight"
                               style="visibility: visible;margin-top: 0.8%">
                            缴费号：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="payNo"
                                   name="payNo" readonly/>
                        </div>
                        <label for="payTime" class="col-sm-1 control-label textRight"
                               style="visibility: visible;margin-top: 0.8%">
                            缴费时间：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="payTime"
                                   name="payTime" readonly/>
                        </div>
                    </div>
                    <div class="col-md-12 column marginTop">
                        <label for="seq" class="col-sm-1 control-label textRight"
                               style="visibility: visible;margin-top: 0.8%">
                            流水号：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="seq"
                                   name="seq" readonly/>
                        </div>
                        <label for="payType" class="col-sm-1 control-label textRight"
                               style="visibility: visible;margin-top: 0.8%">
                            缴费方式：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="payType"
                                   name="payType" readonly/>
                        </div>
                        <label for="acctBank" class="col-sm-1 control-label textRight"
                               style="visibility: visible;margin-top: 0.8%">
                            开户机构：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="acctBank"
                                   name="acctBank" readonly/>
                        </div>
                        <label for="payAcct" class="col-sm-1 control-label textRight"
                               style="visibility: visible">
                            缴费卡号/账号：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="payAcct"
                                   name="payAcct" readonly/>
                        </div>
                    </div>
                    <div class="col-md-12 column marginTop">
                        <label for="cstName" class="col-sm-1 control-label textRight"
                               style="visibility: visible;margin-top: 0.8%">
                            客户名称：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="cstName"
                                   name="cstName" readonly/>
                        </div>

                        <label for="addr" class="col-sm-1 control-label textRight"
                               style="visibility: visible;margin-top: 0.8%">
                            地址：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="addr"
                                   name="addr" readonly/>
                        </div>
                        <label for="payAmt" class="col-sm-1 control-label textRight"
                               style="visibility: visible;margin-top: 0.8%">
                            缴费金额：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="payAmt"
                                   name="payAmt" readonly/>
                        </div>
                        <label for="realAmt" class="col-sm-1 control-label textRight"
                               style="visibility: visible;margin-top: 0.8%">
                            实付金额：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="realAmt"
                                   name="realAmt" readonly/>
                        </div>
                    </div>
                    <div class="col-md-12 column marginTop">
                        <label for="discountAmt" class="col-sm-1 control-label textRight"
                               style="visibility: visible;margin-top: 0.8%">
                            优惠金额：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="discountAmt"
                                   name="discountAmt" readonly/>
                        </div>
                        <label for="feeAmt" class="col-sm-1 control-label textRight"
                               style="visibility: visible">
                            手续费金额：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="feeAmt"
                                   name="feeAmt" readonly/>
                        </div>
                        <label for="desc" class="col-sm-1 control-label textRight"
                               style="visibility: visible;margin-top: 0.8%">
                            差错原因：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="desc"
                                   name="desc" readonly/>
                        </div>
                        <label for="stat" class="col-sm-1 control-label textRight"
                               style="visibility: visible;margin-top: 0.8%">
                            差错状态：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="stat"
                                   name="stat" readonly/>
                        </div>
                    </div>
                    <div class="col-md-12 column marginTop">
                        <label for="hostSeq" class="col-sm-1 control-label textRight"
                               style="visibility: visible">
                            核心流水号：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="hostSeq"
                                   name="hostSeq" readonly/>
                        </div>
                        <label for="chnlSeq" class="col-sm-1 control-label textRight"
                               style="visibility: visible">
                            渠道流水号：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="chnlSeq"
                                   name="chnlSeq" readonly/>
                        </div>
                        <label for="othSeq" class="col-sm-1 control-label textRight"
                               style="ImportOrExportEntrParaServicevisibility: visible">
                            三方流水号：
                        </label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="" id="othSeq"
                                   name="othSeq" readonly/>
                        </div>
                        <div class="col-sm-3">
                            <button type="button"
                                     class="btn btn-info pull-left"
                                     contenteditable="false"
                                     style="margin-left: 35%; width: 8%; min-width: 100px"
                                     id="back"
                                     name="back">返回
                            </button>
                        </div>
                    </div>
                    <hr style="margin-left: 6%;width: 80%;border:1px dashed black;">
                    <table id="errInfoTable"></table>
                </div>
            </div>
        </div>

    </div>
</div>
</body>
</html>
