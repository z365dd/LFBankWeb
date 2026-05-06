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
            src="<%=basePath%>/b_base/views/starring/pay/offline/offline_proj/update.js"
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
                                        <label for="busiNo" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            业务编号：
                                        </label>
                                        <div class="col-sm-3">
                                            <input type="text" class="form-control" placeholder="" id="busiNo"
                                                   name="busiNo" style="width: 200px;" readonly/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 column">
                                <div class="col-md-3"></div>
                                <div class="col-md-8">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <div>
                                            <label for="busiName"
                                                   class="col-sm-3 control-label control-label"
                                                   style="visibility: visible">
                                                业务名称：
                                            </label>
                                            <div class="col-sm-3">
                                                <input type="text" class="form-control" placeholder="" id="busiName"
                                                       name="busiName" style="width: 200px;" readonly/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-12 column">
                                <div class="col-md-3"></div>
                                <div class="col-md-8" hidden>
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="projName"
                                               class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            原收费项目名称：
                                        </label>
                                        <div class="col-sm-3">
                                            <input type="text" class="form-control" placeholder="" id="origProjName"
                                                   name="origProjName" style="width: 200px;"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-8">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="projName"
                                               class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            收费项目名称：
                                        </label>
                                        <div class="col-sm-3">
                                            <input type="text" class="form-control" placeholder="" id="projName"
                                                   name="projName" style="width: 200px;"/>
                                        </div>
                                        <span style="margin: 1% 0 0 6%;color: red">*  </span>
                                        <span id="projNameError" style="display:none;color: red; margin-left:2%">请输入2-20位的收费项目名称</span>
                                    </div>
                                </div>

                            </div>
                            <div class="col-md-12 column">
                                <div class="col-md-3"></div>
                                <div class="col-md-8">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="projDesc"
                                               class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            收费项目描述：
                                        </label>
                                        <div class="col-sm-3">
                                            <input type="text" class="form-control" placeholder="" id="projDesc"
                                                   name="projDesc" style="width: 200px;"/>
                                        </div>
                                        <span style="margin: 1% 0 0 6%;color: red">*  </span>
                                        <span id="projDescError" style="display:none;color: red; margin-left:2%">请输入2-20位的收费项目描述</span>
                                    </div>
                                </div>

                            </div>
                            <div class="col-md-12 column">
                                <div class="col-md-3"></div>
                                <div class="col-md-8">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="projTp" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            项目类型：
                                        </label>
                                        <div class="col-sm-3">
                                            <input type="text" class="form-control" placeholder="" id="projTp"
                                                   name="projTp" style="width: 200px;" readonly/>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--
                            <div id="oweMonthDiv" class="col-md-12 column" hidden>
                                <div class="col-md-3"></div>
                                <div class="col-md-8">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="oweMonth"
                                               class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            收费周期：
                                        </label>
                                        <div class="col-sm-3">
                                            <input type="text" class="form-control" placeholder="" id="oweMonth"
                                                   name="oweMonth" style="width: 200px;"/>
                                        </div>
                                        <span style="margin: 1% 0 0 6%;color: red">*  </span>
                                        <span id="oweMonthError" style="display:none;color: red; margin-left:2%">自主录入请输入收费周期</span>
                                    </div>
                                </div>
                            </div>
                            <div id="amtDiv" class="col-md-12 column" hidden>
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
                                        <span id="amtError" style="display:none;color: red; margin-left:2%">自主录入请输入缴费金额</span>
                                    </div>
                                </div>
                            </div>
                            -->
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
