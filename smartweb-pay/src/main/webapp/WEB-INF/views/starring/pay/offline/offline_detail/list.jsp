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
            src="<%=basePath%>/b_base/views/starring/pay/offline/offline_detail/list.js"
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
                                        <label for="projTp" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            项目类型：
                                        </label>
                                        <div class="col-sm-4">
                                            <select
                                                    data-role="multiselect"
                                                    id="projTp"
                                                    class=""
                                                    name="projTp"
                                                    data-max-height="300"
                                                    data-button-width="225"
                                                    data-enable-filtering="true"
                                                    data-enable-full-value-filtering="false"
                                                    data-enable-case-insensitive-filtering="true"
                                                    data-filter-placeholder="搜索"
                                                    checkBtn="projTp"
                                            >
                                                <option value="00">非自主录入</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 column">
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

                            </div>
                            <div class="col-md-12 column">
                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="stat" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            缴费状态：
                                        </label>
                                        <div class="col-sm-4">
                                            <select
                                                    data-role="multiselect"
                                                    id="stat"
                                                    class=""
                                                    name="stat"
                                                    data-max-height="300"
                                                    data-button-width="225"
                                                    data-enable-filtering="true"
                                                    data-enable-full-value-filtering="false"
                                                    data-enable-case-insensitive-filtering="true"
                                                    data-filter-placeholder="搜索"
                                                    checkBtn="stat"
                                            >
                                                <option value="">请选择</option>
                                            </select>
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
                                        <label for="name" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            姓名：
                                        </label>
                                        <div class="col-sm-6">
                                            <input type="text" class="form-control" placeholder="" id="name"
                                                   name="name" style="width: 225px"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="phoneNo" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            手机号码：
                                        </label>
                                        <div class="col-sm-6">
                                            <input type="text" class="form-control" placeholder="" id="phoneNo"
                                                   name="phoneNo" style="width: 225px"/>
                                        </div>
                                    </div>
                                </div>

                            </div>

                            <div class="col-md-12 column">

                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="strDate" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            缴费开始日期：
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
                                            缴费结束日期：
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
                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="extraField" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            <select
                                                    data-role="multiselect"
                                                    class=""
                                                    style="padding-bottom: 0.5%"
                                                    name="extraField"
                                                    data-max-height="300"
                                                    data-enable-filtering="true"
                                                    data-enable-full-value-filtering="false"
                                                    data-enable-case-insensitive-filtering="true"
                                                    data-filter-placeholder="搜索"
                                                    checkBtn="extraField"
                                            >
                                                <option value="">请选择</option>
                                            </select>
                                        </label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control" placeholder="" id="extraField"
                                                   name="extraField" style="width: 225px"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-6 column" hidden>
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="extraField1" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                        </label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control" placeholder="" id="extraField1"
                                                   name="extraField1"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-6 column" hidden>
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="selectedData" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                        </label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control" placeholder="" id="selectedData"
                                                   name="selectedData"/>
                                        </div>
                                    </div>
                                </div>
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
                                                id="mergeExport"
                                                name="mergeExport"
                                        >
                                            合并导出
                                        </button>
                                    </shiro:haspermission>
                                </div>
                            </div>
                        </div>
                    </form>

                    <hr style="margin-left: 6%;width: 80%;border:1px dashed black;">


                    <div id="top2" ravo="rainbow_fx_layout" class="row clearfix" style="margin: 20px">
                        <div class="col-md-12 column" style="margin-bottom: 4px">
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="totNum" class="col-sm-5 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        缴费总笔数：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="totNum"
                                               readonly
                                               name="totNum"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="succTotNum" class="col-sm-5 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        缴费成功笔数：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="succTotNum"
                                               readonly
                                               name="succTotNum"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="failTotNum" class="col-sm-5 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        待缴费笔数：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="failTotNum" readonly
                                               name="failTotNum"/>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-12 column" style="margin-bottom: 4px">
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="totAmt" class="col-sm-5 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        缴费总金额：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="totAmt"
                                               readonly
                                               name="totAmt"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="succTotAmt" class="col-sm-5 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        缴费成功金额：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="succTotAmt"
                                               readonly
                                               name="succTotAmt"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="failTotAmt" class="col-sm-5 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        待缴费金额：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="failTotAmt" readonly
                                               name="failTotAmt"/>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-12 column" style="margin-bottom: 4px">
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="totPrctlAmt" class="col-sm-5 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        应缴总金额：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="totPrctlAmt"
                                               readonly
                                               name="totPrctlAmt"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="succPrctlAmt" class="col-sm-5 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        应缴成功金额：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="succPrctlAmt"
                                               readonly
                                               name="succPrctlAmt"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="failPrctlAmt" class="col-sm-5 control-label control-label"
                                           style="visibility: visible; text-align: right;padding-top: 2%">
                                        待缴费应缴金额：
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" placeholder="" id="failPrctlAmt" readonly
                                               name="failPrctlAmt"/>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                    <table id="dtlTable"></table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
