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


    <!-- change skin -->
    <link
            href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css"
            type="text/css" rel="stylesheet"/>

    <!-- Self reference JS-->
    <script type="text/javascript"
            src="<%=basePath%>/b_base/views/starring/pay/com/com.js"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="<%=basePath%>/b_base/views/starring/pay/ykt/ykt_chk_qry/list.js"
            charset="utf-8"></script>

    <style>
        #iframe_list {
            height: 750px !important;
        }

        label {
            min-width: 100px;
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
                                <div class="col-md-3 column" style="margin-left: 20%">
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
                                    <label for="startTime" class="col-sm-5 control-label control-label"
                                           style="visibility: visible">
                                        开始日期：
                                    </label>
                                    <input id="startTime" name="startTime" type="text" readonly="readonly"
                                           class="input-medium Wdate col-sm-6"
                                           value=""
                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false, minDate:'{%y-3}-%M-%d',maxDate: '#F{$dp.$D(\'endTime\')}'});"/>
                                </div>
                            </div>
                            <div class="col-md-12 column">
                                <div class="col-md-3 column" style="margin-left: 20%">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="chkStat" class="col-sm-5 control-label control-label"
                                               style="visibility: visible">
                                            对账状态：
                                        </label>
                                        <div class="col-sm-4">
                                            <select
                                                    data-role="multiselect"
                                                    id="chkStat"
                                                    class=""
                                                    name="chkStat"
                                                    data-max-height="300"
                                                    data-button-width="200"
                                                    data-enable-filtering="true"
                                                    data-enable-full-value-filtering="false"
                                                    data-enable-case-insensitive-filtering="true"
                                                    data-filter-placeholder="搜索"
                                                    checkBtn="errStat"
                                            >
                                                <option value="">请选择</option>
                                                <option value="00">待处理</option>
                                                <option value="01">对账成功</option>
                                                <option value="02">对账失败</option>
                                                <option value="03">正在处理</option>
                                            </select>

                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4 column">
                                    <label for="endTime" class="col-sm-5 control-label control-label"
                                           style="visibility: visible">
                                        结束日期：
                                    </label>
                                    <input id="endTime" name="endTime" type="text" readonly="readonly"
                                           class="input-medium Wdate col-sm-6"
                                           value=""
                                    <%--                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false, minDate: '#F{$dp.$D(\'startTime\')}',maxDate:'#F{$dp.$D(\'startTime\',{M:1})}'});"/>--%>
                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false, minDate: '#F{$dp.$D(\'startTime\')}',maxDate: new Date()});"/>
                                </div>

                            </div>
                            <div class="col-md-9 column"></div>
                            <div class="col-md-3 column">
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
                        </div>
                    </form>
                    <table id="chkQueryTable"></table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
