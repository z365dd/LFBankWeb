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
            src="<%=basePath%>/b_base/views/starring/pay/offline/offline_proj/list.js"
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
                                                    data-button-width="250"
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
                                <div class="col-md-2"></div>
                                <div class="col-md-1 column">
                                    <button
                                            ravo="rainbow_fx"
                                            type="button"
                                            class="btn btn-info pull-left"
                                            contenteditable="false"
                                            id="download"
                                            name="download"
                                    >
                                        下载模板
                                    </button>
                                </div>
                                <div class="col-md-1 column"></div>
                                <div class="col-md-1 column">
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
                        </div>
                    </form>
                    <table id="projTable"></table>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<div class="modal fade" style="display: none;" id="importModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content" style="width:100%;margin-left:10%">
            <div class="modal-header">
                <h5 class="modal-title" id="importDtl">
                    导入明细
                </h5>
            </div>
            <div class="modal-body" style="height: 28%">
                <form id="importForm" method="post" enctype="multipart/form-data">
                    <div class="col-md-8 column" hidden>
                        <label for="BUSI_NO"
                               class="col-sm-2 control-label control-label"
                               style="visibility: visible">
                            隐藏业务——传参使用：
                        </label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="" id="BUSI_NO"
                                   name="BUSI_NO"/>
                        </div>
                        <label for="BUSI_NAME"
                               class="col-sm-2 control-label control-label"
                               style="visibility: visible">
                            隐藏业务名称——传参使用：
                        </label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="" id="BUSI_NAME"
                                   name="BUSI_NAME"/>
                        </div>
                        <label for="PROJ_NAME"
                               class="col-sm-2 control-label control-label"
                               style="visibility: visible">
                            隐藏缴费项名称-传参使用：
                        </label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="" id="PROJ_NAME"
                                   name="PROJ_NAME"/>
                        </div>
                    </div>
                    <div class="col-md-8 column" hidden>
                        <label for="PROJ_TP"
                               class="col-sm-2 control-label control-label"
                               style="visibility: visible">
                            隐藏收费类型——传参使用：
                        </label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="" id="PROJ_TP"
                                   name="PROJ_TP"/>
                        </div>
                        <label for="PROJ_DESC"
                               class="col-sm-2 control-label control-label"
                               style="visibility: visible">
                            隐藏项目描述-传参使用：
                        </label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="" id="PROJ_DESC"
                                   name="PROJ_DESC"/>
                        </div>
                    </div>
                    <div class="col-md-8 column" hidden>
                        <label for="FILE_NAME"
                               class="col-sm-2 control-label control-label"
                               style="visibility: visible">
                            隐藏收费类型——传参使用：
                        </label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="" id="FILE_NAME"
                                   name="FILE_NAME"/>
                        </div>
                    </div>
                    <div class="col-md-12 column" style="padding-top: 2%">
                        <label for="oweMonth"
                               class="col-sm-4 control-label control-label"
                               style="visibility: visible;padding-top: 2%">
                            请输入收费周期：
                        </label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" placeholder="" id="oweMonth"
                                   name="oweMonth"/>
                        </div>
                    </div>
                    <div class="col-md-12 column">
                        <span class="col-sm-5" id="errTip" name="errTip" style="color:red;padding-top: 2%">收费周期不能为空</span>
                    </div>
                    <div class="col-md-12 column" style="padding-top: 2%">
                        <label for="strDate"
                               class="col-sm-4 control-label control-label"
                               style="visibility: visible;padding-top: 2%">
                            缴费开始日期：
                        </label>
                        <div class="col-sm-5">
                            <input id="strDate" name="strDate" type="text" readonly="readonly"
                                   class="input-medium Wdate" style="width:150px"
                                   value=""
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true, minDate:'{%y-3}-%M-%d',maxDate: '#F{$dp.$D(\'endDate\')}'});"
                                   required />
                        </div>
                    </div>
                    <div class="col-md-12 column" style="padding-top: 2%">
                        <label for="endDate"
                               class="col-sm-4 control-label control-label"
                               style="visibility: visible;padding-top: 2%">
                            缴费截止日期：
                        </label>
                        <div class="col-sm-5">
                            <input id="endDate" name="endDate" type="text" readonly="readonly"
                                   class="input-medium Wdate" style="width:150px"
                                   value=""
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true, minDate: '#F{$dp.$D(\'strDate\')}'});"
                                    required />
                        </div>
                    </div>

                    <div class="col-md-12 column" style="padding-top: 3%">
                        <div class="col-sm-8">
                            <input id="file" type="file" name="file" accept=".xls,.xlsx"/>
                        </div>
                        <div class="col-sm-3">
                            <button id="importFile" type="button">提交</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="cancelBtn" type="button" class="btn btn-primary">
                    返回
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div>
</div>

<div class="modal fade" style="display: none;pointer-events:auto" id="lastModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 90%;margin-left: 5%">
            <div class="modal-header">
                <h5 class="modal-title">
                    按照上次账单导入
                </h5>
            </div>
            <div class="modal-body" style="height: 20%">
                <form id="repeatForm" method="post" enctype="multipart/form-data">
                    <div class="col-md-12 column">
                        <label for="OWE_MONTH"
                               class="col-sm-6 control-label control-label"
                               style="visibility: visible;padding-top: 2%">
                            请输入您要导入的收费周期：
                        </label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" placeholder="" id="OWE_MONTH"
                                   name="OWE_MONTH"/>
                        </div>
                        <div class="col-sm-1"></div>
                        <button id="repeat" type="button" style="padding-top:1%">提交</button>
                    </div>
                    <div class="col-md-12 column">
                        <div class="col-md-12 column" style="padding-top: 2%">
                            <label for="STR_DATE"
                                   class="col-sm-6 control-label control-label"
                                   style="visibility: visible;padding-top: 2%">
                                缴费开始日期：
                            </label>
                            <div class="col-sm-4">
                                <input id="STR_DATE" name="STR_DATE" type="text" readonly="readonly"
                                       class="input-medium Wdate" style="width:150px"
                                       value=""
                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true, minDate:'{%y-3}-%M-%d',maxDate: '#F{$dp.$D(\'END_DATE\')}'});"
                                       required />
                            </div>
                        </div>
                        <div class="col-md-12 column" style="padding-top: 2%">
                            <label for="END_DATE"
                                   class="col-sm-6 control-label control-label"
                                   style="visibility: visible;padding-top: 2%">
                                缴费截止日期：
                            </label>
                            <div class="col-sm-4">
                                <input id="END_DATE" name="END_DATE" type="text" readonly="readonly"
                                       class="input-medium Wdate" style="width:150px"
                                       value=""
                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true, minDate: '#F{$dp.$D(\'STR_DATE\')}'});"
                                       required />
                            </div>
                        </div>
                    </div>

<%--                    <div class="col-md-12 column">--%>
<%--&lt;%&ndash;                        <div class="col-sm-2"></div>&ndash;%&gt;--%>
<%--                        <span class="col-sm-5" id="errTip1" name="errTip1" style="color:red;padding-top: 2%">收费周期不能为空</span>--%>
<%--                    </div>--%>
                </form>
            </div>
            <div class="modal-footer">
                <button id="cancelBtn1" type="button" class="btn btn-primary">
                    返回
                </button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
