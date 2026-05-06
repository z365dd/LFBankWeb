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
            src="<%=basePath%>/b_base/views/starring/pay/ykt/ykt_para_mng/add.js"
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
                            <div ravo="rainbow_fx" class="form-group">
                                <label for="busiNo" class="col-sm-5 control-label control-label"
                                       style="visibility: visible">
                                    业务编号：
                                </label>
                                <div class="col-sm-4" style="padding-left: 0">
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
                                            required
                                    >
                                        <option value="">请选择</option>
                                    </select>
                                    <span style="color: red">  *</span>
                                </div>

                            </div>
                            <div ravo="rainbow_fx" class="form-group">
                                <label for="busiName" class="col-sm-5 control-label control-label"
                                       style="visibility: visible">
                                    业务名称：
                                </label>
                                <input id="busiName" name="busiName" type="text" class="input-medium"
                                       value="" readonly/>
                            </div>
                            <div ravo="rainbow_fx" class="form-group">
                                <label for="tranAmt" class="col-sm-5 control-label control-label"
                                       style="visibility: visible">
                                    单笔限额：
                                </label>
                                <input id="tranAmt" name="tranAmt" required="true" type="text"
                                       class="input-medium"
                                       value=""/>
                                <span style="color: red">  *</span>
                                <span id="tranAmtErrTip" style="color: red;display:none">请输入正确的单笔限额</span>
                            </div>
                            <div ravo="rainbow_fx" class="form-group">
                                <label for="dayAmt" class="col-sm-5 control-label control-label" style="visibility: visible">
                                    日累计限额：
                                </label>
                                <input id="dayAmt" name="dayAmt" type="text" required="true" class="input-medium"
                                       value=""/>
                                <span style="color: red">  *</span>
                                <span id="dayAmtErrTip" style="color: red;display:none">请输入正确的日累计限额</span>
                            </div>

                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-5 column"></div>
                                <div class="col-md-1 column">
                                    <button
                                            type="submit"
                                            class="btn btn-info pull-left"
                                            contenteditable="false"
                                            id="save"
                                            name="save"
                                            style="margin-top: 50px"
                                    >
                                        提交
                                    </button>
                                </div>
                                <div class="col-md-1 column">
                                    <button
                                            type="button"
                                            class="btn btn-info pull-left"
                                            contenteditable="false"
                                            id="back"
                                            name="back"
                                            style="margin-top: 50px"
                                    >
                                        返回
                                    </button>
                                </div>
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
