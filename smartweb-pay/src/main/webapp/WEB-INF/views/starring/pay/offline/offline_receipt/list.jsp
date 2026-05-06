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
            src="<%=basePath%>/b_base/views/starring/pay/offline/offline_receipt/list.js"
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
                            </div>
                            <div class="col-md-12 column">
                                <div class="col-sm-6">

                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="startTime" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            开始日期：
                                        </label>
                                        <div class="col-sm-3">
                                            <input id="startTime" name="startTime" type="text" readonly="readonly"
                                                   class="input-medium"
                                                   style="width:100%;"
                                                   value=""
                                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false, minDate:'{%y-3}-%M-%d',maxDate: '#F{$dp.$D(\'endTime\')}'});"/>
                                        </div>
                                        <label for="endTime" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            结束日期：
                                        </label>
                                        <div class="col-sm-3">
                                            <input id="endTime" name="endTime" type="text" readonly="readonly"
                                                   class="input-medium Wdate"
                                                   style="width:100%;"
                                                   value=""
                                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false, minDate: '#F{$dp.$D(\'startTime\')}',maxDate: new Date()});"/>
                                        </div>
                                    </div>

                                    <%--                                        <div ravo="rainbow_fx" class="form-group">--%>
                                    <%--                                           --%>
                                    <%--                                        </div>--%>

                                </div>
                                <div class="col-md-6 column" hidden>
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="autoDeduct" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            是否自动扣款：
                                        </label>
                                        <div class="col-sm-5">
                                            <select
                                                    data-role="multiselect"
                                                    id="autoDeduct"
                                                    class=""
                                                    name="autoDeduct"
                                                    data-max-height="300"
                                                    data-button-width="200"
                                                    data-enable-filtering="true"
                                                    data-enable-full-value-filtering="false"
                                                    data-enable-case-insensitive-filtering="true"
                                                    data-filter-placeholder="搜索"
                                                    checkBtn="txStat"
                                            >
                                                <option value="0">否</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 column">
                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="payNo" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            学号：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" placeholder="" id="payNo"
                                                   name="payNo" style="width: 225px;"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="tranTp" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            缴费类型：
                                        </label>
                                        <div class="col-sm-8">
                                            <select
                                                    data-role="multiselect"
                                                    id="tranTp"
                                                    class=""
                                                    name="tranTp"
                                                    data-max-height="300"
                                                    data-button-width="225"
                                                    data-enable-filtering="true"
                                                    data-enable-full-value-filtering="false"
                                                    data-enable-case-insensitive-filtering="true"
                                                    data-filter-placeholder="搜索"
                                                    checkBtn="txStat"
                                            >
                                                <option value="">请选择</option>
                                                <option value="02">缴费</option>
                                                <option value="03">退款</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="col-md-6 column" hidden>
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="selectedData" class="col-sm-3 control-label control-label"
                                           style="visibility: visible">
                                        上传数据：
                                    </label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" placeholder="" id="selectedData"
                                               name="selectedData"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 column">
                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="txStat" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            交易状态：
                                        </label>
                                        <div class="col-sm-8">
                                            <select
                                                    data-role="multiselect"
                                                    id="txStat"
                                                    class=""
                                                    name="txStat"
                                                    data-max-height="300"
                                                    data-button-width="225"
                                                    data-enable-filtering="true"
                                                    data-enable-full-value-filtering="false"
                                                    data-enable-case-insensitive-filtering="true"
                                                    data-filter-placeholder="搜索"
                                                    checkBtn="txStat"
                                            >
                                                <option value="">请选择</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-1 column"></div>
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
                                                id="getPdf"
                                                name="getPdf"
                                        >
                                            导出pdf
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
                                                id="exportExcel"
                                                name="exportExcel"
                                        >
                                            导出excel
                                        </button>
                                    </shiro:haspermission>
                                </div>
                            </div>

                        </div>
                    </form>
                    <table id="payDetailsTable"></table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
