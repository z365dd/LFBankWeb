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
            src="<%=basePath%>/b_base/views/starring/pay/project/proj_chk/info.js"
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
                    <form id="from" method="post" enctype="multipart/form-data">
                        <div id="top1" ravo="rainbow_fx_layout" class="row clearfix" style="margin: 20px">

                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2"></div>
                                <div class="col-md-8 column"
                                     style="display: flex;flex-direction: row;align-items: center;justify-content: flex-start;">
                                    <label for="custName"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        客户名称：
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" placeholder="" id="custName"
                                               name="custName" style="width: 200px" readonly/>
                                    </div>
                                    <span style="margin: 1% 0 0 6%;color: red">  </span>

                                    <label for="spclAcct"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        专用账号：
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" placeholder="" id="spclAcct"
                                               name="spclAcct" style="width: 200px" readonly/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <div class="col-md-8 column"
                                     style="display: flex;flex-direction: row;align-items: center;justify-content: flex-start;">
                                    <label for="acct" class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        客户账号：
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" placeholder="" id="acct"
                                               name="acct" style="width: 200px" readonly/>
                                    </div>
                                    <span style="margin: 1% 0 0 6%;color: red">  </span>

                                    <label for="spclAcctName"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        专用账户户名：
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" placeholder="" id="spclAcctName"
                                               name="spclAcctName" style="width: 200px" readonly/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <div class="col-md-8 column"
                                     style="display: flex;flex-direction: row;align-items: center;justify-content: flex-start;">
                                    <label for="brchName"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        所属机构：
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" placeholder="" id="brchName"
                                               name="brchName" style="width: 200px" readonly/>
                                    </div>

                                    <span style="margin: 1% 0 0 6%;color: red">  </span>

                                    <label for="inspCyc" class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        巡检频次：
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" placeholder="" id="inspCyc"
                                               name="inspCyc" style="width: 200px" readonly/>
                                    </div>
                                    <span id="input2" style="display:none;margin: 1% 0 0 6%;color: red">*  </span>

                                </div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <div class="col-md-8 column"
                                     style="display: flex;flex-direction: row;align-items: center;justify-content: flex-start;">

                                    <label for="projTp" class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        项目类型：
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" placeholder="" id="projTp"
                                               name="projTp" style="width: 200px" readonly/>
                                    </div>
                                    <span style="margin: 1% 0 0 6%;color: red">  </span>

                                    <label for="agtBusiNo" class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        代理业务号：
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" placeholder="" id="agtBusiNo"
                                               name="agtBusiNo" style="width: 200px" readonly/>
                                    </div>

                                </div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <div class="col-md-8 column"
                                     style="display: flex;flex-direction: row;align-items: center;justify-content: flex-start;">
                                    <label for="inspFileName"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        巡检资料：
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" placeholder="" id="inspFileName"
                                               name="inspFileName" style="width: 200px" readonly/>
                                    </div>
                                    <span style="margin: 1% 0 0 6%;color: red">  </span>

                                    <label for="inspDate"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        巡检日期：
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" placeholder="" id="inspDate"
                                               name="inspDate" style="width: 200px" readonly/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <div class="col-md-8 column"
                                     style="display: flex;flex-direction: row;align-items: center;justify-content: flex-start;">
                                    <label for="inspUserName"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        巡检人员：
                                    </label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" placeholder="" id="inspUserName"
                                               name="inspUserName" style="width: 200px" readonly/>
                                    </div>

                                </div>
                            </div>

                            <div class="col-md-6 column"></div>
                            <div class="col-md-1 column">
                            </div>
                            <div class="col-md-1 column">
                                <button
                                        type="button"
                                        class="btn btn-info pull-left"
                                        contenteditable="false"
                                        id="back"
                                        name="back"
                                        style="margin-top: 50px;"
                                >
                                    返回
                                </button>
                            </div>
                            <div class="col-md-1 column">
                                <button
                                        type="button"
                                        class="btn btn-info pull-left"
                                        contenteditable="false"
                                        id="downloadFile"
                                        name="downloadFile"
                                        style="margin-top: 50px"
                                >
                                    下载
                                </button>
                            </div>
                        </div>
                    </form>

                    <div class="col-md-12 column lineMaginTop">
                        <div class="col-md-3 column"></div>
                        <div class="col-md-4 column">
                            <table id="projInspRecTable"></table>
                        </div>
                    </div>


                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>
