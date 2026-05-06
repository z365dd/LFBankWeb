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
    <script type="text/javascript"
            src="<%=basePath%>/b_base/common/smartweb.js"></script>

    <!-- check -->
    <script type="text/javascript"
            src="<%=basePath%>/b_base/common/check.js"></script>
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
            src="<%=basePath%>/b_base/views/starring/pay/life/receiptQueryPrint/list.js"
            charset="utf-8"></script>
    <!--pdf导出-->
<%--    <script type="text/javascript"--%>
<%--            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/jspdf/jspdf.js"--%>
<%--            charset="utf-8"></script>--%>
<%--    <script type="text/javascript"--%>
<%--            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/bootstrap-table-export.js"--%>
<%--            charset="utf-8"></script>--%>
<%--    <script type="text/javascript"--%>
<%--            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/tableExport.js"--%>
<%--            charset="utf-8"></script>--%>
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
                    <form id="from" ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" method="post">
                        <div id="top1" ravo="rainbow_fx_layout" class="row clearfix" style="margin: 20px">
                            <div class="col-md-12 column">
                                <div class="col-md-3 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="busiNo" class="col-sm-5 control-label control-label"
                                               style="visibility: visible">
                                            业务编号：
                                        </label>
                                        <div class="col-sm-4">
                                            <select
                                                    data-role="multiselect"
                                                    id="busiNo"
                                                    class=""
                                                    name="busiNo"
                                                    data-max-height="300"
                                                    data-button-width="200"
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
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="strDate" class="col-sm-5 control-label control-label"
                                               style="visibility: visible">
                                            开始日期：
                                        </label>
                                        <input id="strDate" name="strDate" type="text" readonly="readonly"
                                               class="col-sm-5 input-medium Wdate"
                                               value=""
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'{%y-1}-%M-%d',maxDate:'#F{$dp.$D(\'endDate\')}'});"/>
                                    </div>
                                </div>
                                <div class="col-md-3 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="chnlNo" class="col-sm-5 control-label control-label"
                                               style="visibility: visible">
                                            渠道号：
                                        </label>
                                        <div class="col-sm-4">
                                            <select
                                                    data-role="multiselect"
                                                    id="chnlNo"
                                                    class=""
                                                    name="chnlNo"
                                                    data-max-height="300"
                                                    data-button-width="200"
                                                    data-enable-filtering="true"
                                                    data-enable-full-value-filtering="false"
                                                    data-enable-case-insensitive-filtering="true"
                                                    data-filter-placeholder="搜索"
                                                    checkBtn="chnlNo"
                                            >
                                                <option value="">请选择</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>



                            </div>
                            <div class="col-md-12 column">
                                <div class="col-md-3 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="busiName" class="col-sm-5 control-label control-label"
                                               style="visibility: visible">
                                            业务名称：
                                        </label>
                                        <div class="col-sm-6">
                                            <input type="text" class="form-control" placeholder="" id="busiName"
                                                   name="busiName" style="width:200px" readonly/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="endDate" class="col-sm-5 control-label control-label"
                                               style="visibility: visible">
                                            结束日期：
                                        </label>
                                        <input id="endDate" name="endDate" type="text" readonly="readonly"
                                               class="col-sm-5 input-medium Wdate"
                                               value=""
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'strDate\')}'});"/>
                                    </div>
                                </div>

                                <!-- 这个是为了补充删除机构号之后的空余-->
                                <div class="col-md-2 column"></div>
                                <div class="col-md-2 column"></div>
                                <div class="col-md-3">
                                    <div class="col-md-3"></div>
                                    <div class="col-md-8" style="color: red;margin-bottom: 2%" >最多可查询近3年记录</div>
                                </div>
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
                            </div>
                        </div>
                    </form>
                    <hr style="margin-left: 6%;width: 72%;border:1px dashed black;">
                    <div id="top2" ravo="rainbow_fx_layout" class="row clearfix" style="margin: 20px">
                        <div class="col-md-12 column" style="margin-bottom: 4px">
                            <div class="col-md-3 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="busiNameInfo" class="col-sm-5 control-label control-label"
                                           style="visibility: visible; text-align: right;margin-top: 3%">
                                        业务名称：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="busiNameInfo"
                                               readonly
                                               style="width:200px"
                                               name="busiNameInfo"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="instName" class="col-sm-5 control-label control-label"
                                           style="visibility: visible; text-align: right;margin-top: 2.4%">
                                        机构名称：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="instName"
                                               readonly
                                               name="instName"/>
                                    </div>
                                </div>
                            </div>
<%--                            <div class="col-md-3 column">--%>
<%--                                <div ravo="rainbow_fx" class="form-group">--%>
<%--                                    <label for="txDateInfo" class="col-sm-5 control-label control-label"--%>
<%--                                           style="visibility: visible; text-align: right;margin-top: 3%">--%>
<%--                                        缴费日期：--%>
<%--                                    </label>--%>
<%--                                    <div class="col-sm-6">--%>
<%--                                        <input type="text" class="form-control" placeholder="" id="txDateInfo" readonly--%>
<%--                                               name="txDateInfo"/>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </div>--%>
                        </div>
                        <div class="col-md-12 column">
                            <div class="col-md-3 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="totalNum" class="col-sm-5 control-label control-label"
                                           style="visibility: visible; text-align: right;margin-top: 3%">
                                        总笔数：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="totalNum"
                                               readonly
                                               name="totalNum"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="totalAmt" class="col-sm-5 control-label control-label"
                                           style="visibility: visible; text-align: right;margin-top: 2.4%">
                                        总金额：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="totalAmt"
                                               readonly
                                               name="totalAmt"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2 column"></div>
                            <div class="col-md-1 column">
                                <button
                                        ravo="rainbow_fx"
                                        type="button"
                                        class="btn btn-info pull-left"
                                        style="margin-top: 5px;display: none"
                                        contenteditable="false"
                                        id="print"
                                        name="print"
                                >
                                    打印
                                </button>
                            </div>
                        </div>
                    </div>

                    <table id="payInfoTable"></table>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
