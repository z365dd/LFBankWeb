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
            src="<%=basePath%>/b_base/views/starring/pay/offline/offline_refund/list.js"
            charset="utf-8"></script>

    <style>
        #iframe_list {
            height: 750px !important;
        }

        .fixed-table-body {
            height: 400px !important;
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
                    <form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="from" method="post">
                        <div id="top1" ravo="rainbow_fx_layout" class="row clearfix" style="margin: 20px">
                            <div class="col-md-12 column">
                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="busiNo" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            业务编号：
                                        </label>
                                        <div class="col-sm-5">
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
                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="projName" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            收费项目：
                                        </label>
                                        <div class="col-sm-4">
                                            <select
                                                    data-role="multiselect"
                                                    id="projName"
                                                    class=""
                                                    name="projName"
                                                    data-max-height="300"
                                                    data-button-width="225"
                                                    data-enable-filtering="true"
                                                    data-enable-full-value-filtering="false"
                                                    data-enable-case-insensitive-filtering="true"
                                                    data-filter-placeholder="搜索"
                                                    checkBtn="projName"
                                            >
                                                <option value="">请选择</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 column">

                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="oweMonth" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            收费周期：
                                        </label>
                                        <div class="col-sm-6">
                                            <input type="text" class="form-control" placeholder="" id="oweMonth"
                                                   name="oweMonth" style="width: 225px"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="payNo" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            学号：
                                        </label>
                                        <div class="col-sm-6">
                                            <input type="text" class="form-control" placeholder="" id="payNo"
                                                   name="payNo" style="width: 225px"/>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="col-md-12 column">

                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="strDate" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            开始日期：
                                        </label>
                                        <div class="col-sm-6">
                                            <input id="strDate" name="strDate" type="text" readonly="readonly"
                                                   class="input-medium Wdate" style="width:225px"
                                                   value=""
                                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true, minDate:'{%y-3}-%M-%d',maxDate: '#F{$dp.$D(\'endDate\')}'});"/>
                                        </div>

                                    </div>
                                </div>
                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="endDate" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            结束日期：
                                        </label>
                                        <div class="col-sm-6">
                                            <input id="endDate" name="endDate" type="text" readonly="readonly"
                                                   class="input-medium Wdate" style="width:225px"
                                                   value=""
                                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true, minDate: '#F{$dp.$D(\'strDate\')}',maxDate:new Date()});"/>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="col-md-12 column">
                                <div class="col-md-6 column"></div>
                                <div class="col-md-2 column"></div>
                                <div class="col-md-1 column">
                                    <shiro:haspermission name="anno">
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
                                    </shiro:haspermission>
                                </div>
                                <div class="col-md-1 column">
                                    <shiro:haspermission name="anno">
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
                                    </shiro:haspermission>
                                </div>
                                <div class="col-md-1 column">
                                    <shiro:haspermission name="anno">
                                        <button
                                                ravo="rainbow_fx"
                                                type="button"
                                                class="btn btn-info pull-left"
                                                contenteditable="false"
                                                id="batRefund"
                                                name="batRefund"
                                        >
                                            批量退款
                                        </button>
                                    </shiro:haspermission>
                                </div>
                            </div>
                        </div>
                    </form>

                    <table id="table"></table>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" style="display: none;" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog" style="width: 100%">
        <div class="modal-content" style="width:100%">
            <div class="modal-header">
                <h5 class="modal-title">
                    批量退款列表
                </h5>
            </div>
            <div class="modal-body" style="height:50%">
                <form id="preRefundForm" method="post" enctype="multipart/form-data">
                    <table id="preRefundTable"></table>
                </form>
            </div>
            <div class="modal-footer">
                <button id="sendMsgBtn" type="button" class="btn btn-primary">
                    退款
                </button>
                <button id="cancelBtn" type="button" class="btn btn-primary">
                    返回
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div>
</div>

<div class="modal fade" style="display: none;pointer-events:auto" id="refundModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 90%;margin-left: 5%">
            <div class="modal-header">
                <h5 class="modal-title">
                    退款金额填写
                </h5>
            </div>
            <div class="modal-body" style="height: 20%">

                <div class="col-md-12 column">
                    <div class="col-md-12 column" style="padding-top: 2%">
                        <label for="payAcct"
                               class="col-sm-4 control-label control-label"
                               style="visibility: visible;padding-top: 2%">
                            退款卡号：
                        </label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" placeholder="" id="payAcct"
                                   name="payAcct" readonly/>
                        </div>
                    </div>
                    <div class="col-md-12 column" style="padding-top: 2%">
                        <label for="payAcctName"
                               class="col-sm-4 control-label control-label"
                               style="visibility: visible;padding-top: 2%">
                            持卡人姓名：
                        </label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" placeholder="" id="payAcctName"
                                   name="payAcctName" readonly/>
                        </div>
                    </div>
                    <div class="col-md-12 column" style="padding-top: 2%">
                        <label for="rfndAmt"
                               class="col-sm-4 control-label control-label"
                               style="visibility: visible;padding-top: 2%">
                            请输入退费金额：
                        </label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" placeholder="" id="rfndAmt"
                                   name="rfndAmt"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button id="save" type="button" class="btn btn-primary">
                    保存至批量退款列表
                </button>
                <button id="cancelBtn1" type="button" class="btn btn-primary">
                    返回
                </button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" style="display: none;" id="msgModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 500px;margin-left: 10%">
            <div class="modal-header">
                <h5 class="modal-title" id="importDtl">
                    发送短信验证码
                </h5>
            </div>
            <div class="modal-body" style="height: 5%">
                <form id="refundForm" method="post" enctype="multipart/form-data">
                    <div class="col-md-12 column">
                        <!-- 隐藏参数 传参使用 -->
                        <div hidden>
                            <label for="vrfyNoCrtId"
                                   class="col-sm-5 control-label control-label"
                                   style="visibility: visible;padding-top: 2%">
                                验证码生成标识号 ：
                            </label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" placeholder="" id="vrfyNoCrtId"
                                       name="vrfyNoCrtId"/>
                            </div>
                        </div>

                        <label for="vrfyNo"
                               class="col-sm-5 control-label control-label"
                               style="visibility: visible;padding-top: 2%">
                            请输入短信验证码：
                        </label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="" id="vrfyNo"
                                   name="vrfyNo"/>
                        </div>

                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="refundConfm" type="button" class="btn btn-primary">退费</button>
                <button id="cancelBtn2" type="button" class="btn btn-primary">
                    返回
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div>
</div>

</body>
</html>
