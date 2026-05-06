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
    <%--    <link type="text/css" rel="stylesheet"--%>
    <%--          href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.css"/>--%>
    <%--    <script type="text/javascript"--%>
    <%--            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.js"--%>
    <%--            charset="utf-8"></script>--%>
    <%--    <script type="text/javascript"--%>
    <%--            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/bootstrap-table-export.js"--%>
    <%--            charset="utf-8"></script>--%>
    <%--    <script type="text/javascript"--%>
    <%--            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/tableExport.js"--%>
    <%--            charset="utf-8"></script>--%>
    <%--    <script type="text/javascript"--%>
    <%--            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/dist/locale/bootstrap-table-zh-CN.js"></script>--%>
    <%--    <script type="text/javascript"--%>
    <%--            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/tableExport.js"></script>--%>
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
    <%--    <link type="text/css" rel="stylesheet"--%>
    <%--          href="<%=basePath%>/b_base/bootstrapValidator/css/bootstrapValidator.min.css"/>--%>
    <%--    <script type="text/javascript"--%>
    <%--            src="<%=basePath%>/b_base/bootstrapValidator/js/bootstrapValidator.min.js"--%>
    <%--            charset="utf-8"></script>--%>
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
    <script type="text/javascript"
            src="<%=basePath%>/b_ide/bootstrap/module/tabs/js/bootstrap-tabs.js"></script>
    <!-- /TABS -->

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
            src="<%=basePath%>/b_base/views/starring/pay/life/clearRule/com.js"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="<%=basePath%>/b_base/views/starring/pay/life/clearRule/add.js"
            charset="utf-8"></script>

    <style>
        #iframe_list {
            height: 750px !important;
        }

        .fixed-table-body {
            height: 400px !important;
        }

        .lineMaginTop {
            margin-top: 10px;
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
                                <div class="col-md-2 column"></div>
                                <div class="col-md-8 column">
                                    <label for="BUSI_NO" class="col-sm-3 control-label control-label"
                                           style="visibility: visible">
                                        业务编号：
                                    </label>
                                    <div class="col-sm-3">
                                        <select
                                                data-role="multiselect"
                                                id="BUSI_NO"
                                                class=""
                                                name="BUSI_NO"
                                                data-max-height="300"
                                                data-button-width="100%"
                                                data-enable-filtering="true"
                                                data-enable-full-value-filtering="false"
                                                data-enable-case-insensitive-filtering="true"
                                                data-filter-placeholder="搜索"
                                                checkBtn="busiNo"
                                                required
                                        >
                                            <option value="">请选择</option>
                                        </select>
                                    </div>
                                    <label for="busiName" class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        业务名称：
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" placeholder="" id="busiName"
                                               name="busiName" readonly/>
                                    </div>
                                </div>

                                <div class="col-md-2 column"></div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <div class="col-md-8 column">
                                    <label for="CLR_TP" class="col-sm-3 control-label control-label"
                                           style="visibility: visible">
                                        清算类型：
                                    </label>
                                    <div class="col-sm-3">
                                        <select
                                                data-role="multiselect"
                                                id="CLR_TP"
                                                class=""
                                                name="CLR_TP"
                                                data-max-height="300"
                                                data-button-width="100%"
                                                data-enable-filtering="true"
                                                data-enable-full-value-filtering="false"
                                                data-enable-case-insensitive-filtering="true"
                                                data-filter-placeholder="搜索"
                                                checkBtn="clrTp"
                                                required
                                        >
                                            <option value="0">汇总清算一笔</option>
                                            <option value="2">按手续费清算</option>
                                            <option value="3">按渠道分别清算</option>
                                        </select>
                                    </div>
                                    <label for="CHK_TP" class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        对账类型：
                                    </label>
                                    <div class="col-sm-3">
                                        <select
                                                data-role="multiselect"
                                                id="CHK_TP"
                                                class=""
                                                name="CHK_TP"
                                                data-max-height="300"
                                                data-button-width="100%"
                                                data-enable-filtering="true"
                                                data-enable-full-value-filtering="false"
                                                data-enable-case-insensitive-filtering="true"
                                                data-filter-placeholder="搜索"
                                                checkBtn="chkTp"
                                                required
                                        >
                                            <option value="1">行内对账</option>
                                            <option value="2">三方对账</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-2 column"></div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <div class="col-md-8 column">
                                    <label for="RFND_TP" class="col-sm-3 control-label control-label"
                                           style="visibility: visible">
                                        退款类型：
                                    </label>
                                    <div class="col-sm-3">
                                        <select
                                                data-role="multiselect"
                                                id="RFND_TP"
                                                class=""
                                                name="RFND_TP"
                                                data-max-height="300"
                                                data-button-width="100%"
                                                data-enable-filtering="true"
                                                data-enable-full-value-filtering="false"
                                                data-enable-case-insensitive-filtering="true"
                                                data-filter-placeholder="搜索"
                                                checkBtn="rfndTp"
                                                required
                                        >
                                            <option value="1">单位户直接退费</option>
                                            <option value="2">内部户退费</option>
                                            <option value="3">垫款户退费</option>
                                            <option value="4">不允许退费</option>
                                        </select>
                                    </div>
                                    <label for="NOTE_TP" class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        通知类型：
                                    </label>
                                    <div class="col-sm-3">
                                        <select
                                                data-role="multiselect"
                                                id="NOTE_TP"
                                                class=""
                                                name="NOTE_TP"
                                                data-max-height="300"
                                                data-button-width="100%"
                                                data-enable-filtering="true"
                                                data-enable-full-value-filtering="false"
                                                data-enable-case-insensitive-filtering="true"
                                                data-filter-placeholder="搜索"
                                                checkBtn="noteTp"
                                                <%--按钮必选校验--%>
                                                required
                                        >
                                            <option value="1">对账完成通知三方</option>
                                            <option value="2">对账完成不通知三方</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-2 column"></div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <div class="col-md-8 column" style="display: flex;flex-direction: row;align-items: center;justify-content: flex-start;">
                                    <label for="TEMP_ACCT" class="col-sm-3 control-label control-label"
                                           style="visibility: visible">
                                        垫款账号：
                                    </label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" placeholder="" id="TEMP_ACCT"
                                               name="TEMP_ACCT"/>
                                    </div>
                                    <div id="showAcct" class="col-md-3" style="color: red;display: flex; flex-direction: row;">
                                        <div style="margin: 2% 4% 0 0;">*  </div>
                                        <div id="acctErrInfo" style="display:none">请填写垫款账号</div>
                                    </div>
                                    <%--                                    <div id="acctErrInfo" class="col-md-3" style="color:red;display:none">请填写垫款账号</div>--%>
                                </div>
                                <div class="col-md-2 column"></div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <div class="col-md-8 column"  style="display: flex;flex-direction: row;align-items: center;justify-content: flex-start;">
                                    <label for="TEMP_ACCT_NAME" class="col-sm-3 control-label control-label"
                                           style="visibility: visible">
                                        垫款账户名称：
                                    </label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" placeholder="" id="TEMP_ACCT_NAME"
                                               name="TEMP_ACCT_NAME"/>
                                    </div>
                                    <div id="showAcctName" class="col-md-3" style="color: red;display: flex; flex-direction: row;">
                                        <div style="margin: 2% 4% 0 0;">*  </div>
                                        <div id="acctNameErrInfo" style="display:none">请填写垫款账户名称</div>
                                    </div>
                                </div>
                                <div class="col-md-2 column"></div>
                            </div>
                            <div class="col-md-7 column"></div>
                            <div class="col-md-2 column">
                                <button
                                        type="submit"
                                        class="btn btn-info pull-left"
                                        contenteditable="false"
                                        id="save"
                                        name="save"
                                        style="width: 150px; margin-top: 50px"
                                >
                                    保存
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
