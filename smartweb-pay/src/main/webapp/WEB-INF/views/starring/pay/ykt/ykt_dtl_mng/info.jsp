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

    <!-- LayoutIt bootstrap -->
    <!-- table-->
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
            src="<%=basePath%>/b_base/views/starring/pay/ykt/ykt_dtl_mng/info.js"
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

        label {
            min-width: 140px;
        }
    </style>
    <title>Insert title here</title>
</head>
<body>

<div ravo="rainbow_fx_layout" class="row clearfix">
    <div class="col-md-12 column marginTop">
        <div class="col-md-12 column marginTop">
            <label for="busiInfo" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                业务：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="busiInfo"
                       name="busiInfo" readonly/>
            </div>
            <label for="chnlInfo" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                渠道：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="chnlInfo"
                       name="chnlInfo" readonly/>
            </div>
        </div>
        <div class="col-md-12 column marginTop">
            <label for="payNo" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                一卡通账号：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="payNo"
                       name="payNo" readonly/>
            </div>
            <label for="payAcct" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                交易银行卡号：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="payAcct"
                       name="payAcct" readonly/>
            </div>

        </div>
        <div class="col-md-12 column marginTop">
            <label for="payDate" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                交易日期：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="payDate"
                       name="payDate" readonly/>
            </div>
            <label for="payTime" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                交易时间：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="payTime"
                       name="payTime" readonly/>
            </div>
        </div>
        <div class="col-md-12 column marginTop">
            <label for="arrearsDates" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                欠费时段：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="arrearsDates"
                       name="arrearsDates" readonly/>
            </div>
            <label for="payType" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                交易方式：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="payType"
                       name="payType" readonly/>
            </div>
        </div>
        <div class="col-md-12 column marginTop">
            <label for="cstName" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                客户名称：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="cstName"
                       name="cstName" readonly/>
            </div>

            <label for="addr" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                地址：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="addr"
                       name="addr" readonly/>
            </div>
        </div>

        <div class="col-md-12 column marginTop">
            <label for="payAmt" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                交易金额：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="payAmt"
                       name="payAmt" readonly/>
            </div>
            <label for="realAmt" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                实付金额：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="realAmt"
                       name="realAmt" readonly/>
            </div>
        </div>
        <div class="col-md-12 column marginTop">

            <label for="discountAmt" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                优惠金额：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="discountAmt"
                       name="discountAmt" readonly/>
            </div>
            <label for="feeAmt" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                手续费：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="feeAmt"
                       name="feeAmt" readonly/>
            </div>
        </div>
        <div class="col-md-12 column marginTop">

            <label for="bankDctAmt" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                银行优惠：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="bankDctAmt"
                       name="bankDctAmt" readonly/>
            </div>
            <label for="mertDctAmt" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                商户优惠：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="mertDctAmt"
                       name="mertDctAmt" readonly/>
            </div>
        </div>
        <div class="col-md-12 column marginTop">
            <label for="txStat" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                交易状态：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="txStat"
                       name="txStat" readonly/>
            </div>
            <label for="autoDeduct" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                是否自动扣款：
            </label>
           <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="autoDeduct"
                       name="autoDeduct" readonly/>
            </div>
        </div>
        <div class="col-md-12 column marginTop">
            <label for="hostSeq" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                核心流水号：
            </label>
            <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="hostSeq"
                       name="hostSeq" readonly/>
            </div>
            <label for="chnlSeq" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                渠道流水号：
            </label>
            <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="chnlSeq"
                       name="chnlSeq" readonly/>
            </div>

        </div>
        <div class="col-md-12 column marginTop">
            <label for="othSeq" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                三方流水号：
            </label>
            <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="othSeq"
                       name="othSeq" readonly/>
            </div>
            <label for="acctBank" class="col-sm-1 control-label textRight"
                   style="visibility: visible;margin-top: 0.8%">
                开户机构：
            </label>
            <div class="col-sm-2" style="width: 200px">
                <input type="text" class="form-control" placeholder="" id="acctBank"
                       name="acctBank" readonly/>
            </div>
        </div>
        <div class="col-md-12 column marginTop">
            <button type="button"
                    class="btn btn-info pull-left"
                    contenteditable="false"
                    style="margin-left: 35%; width: 8%; min-width: 100px"
                    id="back"
                    name="back">返回
            </button>
        </div>
    </div>
</div>
</body>
</html>
