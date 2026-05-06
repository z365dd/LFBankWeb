<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!-- JSTL -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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


    <!-- BOOTSTRAP -->
    <link type="text/css" rel="stylesheet"
          href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
    <link type="text/css" rel="stylesheet"
          href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap-theme.css"/>
    <script type="text/javascript"
            src="<%=basePath%>/b_base/bootstrap-3.4.1/js/bootstrap.js"
            charset="utf-8"></script>
    <!-- /BOOTSTRAP -->


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
            src="<%=basePath%>/b_base/views/starring/pay/life/merTranDetails/info.js"
            charset="utf-8"></script>

    <style>
        #iframe_list {
            height: 750px !important;
        }

        .marginTop {
            margin-top: 20px;
        }

        .textRight {
            text-align: right;
        }

        .modal-dialog {
            /*width: 40%;*/
            width: 400px;
            min-width: 400px;
        }
    </style>
    <title>Insert title here</title>
</head>
<body>

<div ravo="rainbow_fx_layout" class="row clearfix">
    <div class="col-md-12 column marginTop">
        <div class="col-md-12 column marginTop">
            <label for="busiInfo" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.5%">
                业务：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="busiInfo"
                       name="busiInfo" readonly/>
            </div>
            <label for="cstNo" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.5%">
                缴费号：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="cstNo"
                       name="cstNo" readonly/>
            </div>

        </div>
        <div class="col-md-12 column marginTop">
            <label for="payAcct" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.5%">
                缴费卡号/账号：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="payAcct"
                       name="payAcct" readonly/>
            </div>
            <label for="arrearsDates" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.5%">
                欠费时段：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="arrearsDates"
                       name="arrearsDates" readonly/>
            </div>
        </div>
        <div class="col-md-12 column marginTop">

            <label for="payDate" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.5%">
                缴费日期：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="payDate"
                       name="payDate" readonly/>
            </div>
            <label for="payTime" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.5%">
                缴费时间：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="payTime"
                       name="payTime" readonly/>
            </div>
        </div>
        <div class="col-md-12 column marginTop">
            <label for="cstName" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.5%">
                客户名称：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="cstName"
                       name="cstName" readonly/>
            </div>

            <label for="addr" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.5%">
                地址：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="addr"
                       name="addr" readonly/>
            </div>
        </div>

        <div class="col-md-12 column marginTop">
            <label for="payAmt" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.5%">
                缴费金额：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="payAmt"
                       name="payAmt" readonly/>
            </div>
            <label for="realAmt" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.5%">
                实付金额：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="realAmt"
                       name="realAmt" readonly/>
            </div>

        </div>

        <div class="col-md-12 column marginTop">
            <label for="discountAmt" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.5%">
                优惠金额：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="discountAmt"
                       name="discountAmt" readonly/>
            </div>
            <label for="feeAmt" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.5%">
                手续费：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="feeAmt"
                       name="feeAmt" readonly/>
            </div>
        </div>
        <div class="col-md-12 column marginTop">

            <label for="bankDctAmt" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                银行优惠：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="bankDctAmt"
                       name="bankDctAmt" readonly/>
            </div>
            <label for="mertDctAmt" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                商户优惠：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="mertDctAmt"
                       name="mertDctAmt" readonly/>
            </div>
        </div>
        <div class="col-md-12 column marginTop">
            <label for="payType" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.5%">
                缴费方式：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="payType"
                       name="payType" readonly/>
            </div>
            <label for="txStat" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.5%">
                交易状态：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="txStat"
                       name="txStat" readonly/>
            </div>
        </div>
        <div class="col-md-12 column marginTop">
            <label for="refundStat" class="col-sm-2 control-label textRight"
                   style="visibility: visible;margin-top: 0.5%">
                退费状态：
            </label>
            <div class="col-sm-2">
                <input type="text" class="form-control" placeholder="" id="refundStat"
                       name="refundStat" readonly/>
            </div>
        </div>
        <div class="col-md-12 column marginTop">
            <div class="col-md-2 column"></div>
            <div class="col-md-2 column">
                <button type="button"
                        class="btn btn-info pull-left"
                        contenteditable="false"
                        style="margin-left:15%; width: 8%; min-width: 100px"
                        id="refund"
                        name="refund">退费
                </button>
            </div>
            <div class="col-md-2 column"></div>
            <div class="col-md-2 column">
                <button type="button"
                        class="btn btn-info pull-left"
                        contenteditable="false"
                        style="margin-left: 15%; width: 8%; min-width: 100px"
                        id="back"
                        name="back">返回
                </button>
            </div>
        </div>
    </div>
</div>
<%--<div class="modal fade" style="display: none" id="handleModal" tabindex="-1" role="dialog"--%>
<%--     aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">--%>
<%--    <div class="modal-dialog">--%>
<%--        <div class="modal-content">--%>
<%--            <div class="modal-header">--%>
<%--                <h4 class="modal-title" id="myModalLabel">--%>
<%--                    请输入验证码--%>
<%--                </h4>--%>
<%--            </div>--%>
<%--            <div class="modal-body">--%>
<%--                <div id="pwdMessage">--%>
<%--                    <p style="font-size: 15px;color: rgb(204, 192, 204);" id="phoneNoTipMsg">验证码已发送</p>--%>
<%--                    <p style="display: none" id="key"></p>--%>
<%--                </div>--%>
<%--                <div class="row clearfix">--%>
<%--                    <label class="col-sm-4 control-label" for="validateCode"--%>
<%--                           style="padding-right: 20px">请输入验证码:</label>--%>
<%--                    <div class="col-sm-5">--%>
<%--                        <input type="text" class="form-control" id="validateCode"--%>
<%--                               placeholder="验证码" required>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <div class="modal-footer">--%>
<%--                <button id="requireBtn" type="button" class="btn btn-primary">--%>
<%--                    确认--%>
<%--                </button>--%>
<%--                <button id="cancelBtn" type="button" class="btn btn-primary">--%>
<%--                    取消--%>
<%--                </button>--%>
<%--            </div>--%>
<%--        </div><!-- /.modal-content -->--%>
<%--    </div><!-- /.modal-dialog -->--%>
<%--</div><!-- /.modal -->--%>
<div class="modal fade" style="display: none" id="handleModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="myModalLabel">
                    系统提示
                </h5>
            </div>
            <div class="modal-body">
                <h4 class="modal-title" id="myModalLabel1">
                    该功能暂未开放
                </h4>
            </div>
            <div class="modal-footer">
<%--                <button id="requireBtn" type="button" class="btn btn-primary">--%>
<%--                    确认--%>
<%--                </button>--%>
                <button id="cancelBtn" type="button" class="btn btn-primary">
                    返回
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>
