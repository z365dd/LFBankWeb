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
    <script type="text/javascript"
            src="<%=basePath%>/b_base/jquery-validation/1.19.5/dist/jquery.validate.min.js"></script>

    <!-- Self reference JS-->
    <script type="text/javascript"
            src="<%=basePath%>/b_base/views/starring/pay/com/com.js"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="<%=basePath%>/b_base/views/starring/pay/union/union_para/add.js"
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
                                <div class="col-md-3"></div>
                                <div class="col-md-8">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="year"
                                               class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            年度：
                                        </label>
                                        <div class="col-sm-3">
                                            <input id="year" name="year" type="text"
                                                   class="input-medium Wdate"
                                                   style="width: 200px"
                                                   value=""
                                                   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
                                        </div>
                                        <span style="margin: 1% 0 0 6%;color: red">*  </span>

                                    </div>
                                </div>

                            </div>
                            <div class="col-md-12 column">
                                <div class="col-md-3"></div>
                                <div class="col-md-8">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="strDate"
                                               class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            缴费开始日期：
                                        </label>
                                        <div class="col-sm-3">
                                            <input id="strDate" name="strDate" type="text"
                                                   class="input-medium Wdate"
                                                   style="width: 200px"
                                                   value=""
                                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate: '#F{$dp.$D(\'endDate\')}'});"/>
                                        </div>
                                        <span style="margin: 1% 0 0 6%;color: red">*  </span>

                                    </div>
                                </div>

                            </div>
                            <div class="col-md-12 column">
                                <div class="col-md-3"></div>
                                <div class="col-md-8">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="endDate" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            缴费截止日期：
                                        </label>
                                        <div class="col-sm-3">
                                            <input id="endDate" name="endDate" type="text"
                                                   class="input-medium Wdate"
                                                   style="width: 200px"
                                                   value=""
                                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate: '#F{$dp.$D(\'strDate\')}'});"/>
                                        </div>
                                        <span style="margin: 1% 0 0 6%;color: red">*  </span>

                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 column" >
                                <div class="col-md-3"></div>
                                <div class="col-md-8">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="amt"
                                               class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            缴费金额：
                                        </label>
                                        <div class="col-sm-3">
                                            <input type="text" class="form-control" placeholder="" id="amt"
                                                   name="amt" style="width: 200px;"/>
                                        </div>
                                        <span style="margin: 1% 0 0 6%;color: red">*  </span>
                                        <span id="amtError" style="display:none;color: red; margin-left:2%"></span>
                                    </div>
                                </div>
                            </div>
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
                                    保存
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
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
