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

    <script type="text/javascript">var ctx = '${ctx}', ctxStatic = '${ctxStatic}';</script>

    <!-- JQUERY -->
    <script type="text/javascript" src="<%=basePath%>/b_base/jquery-3.6.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-migrate-3.4.0.min.js" charset="utf-8"></script><script type="text/javascript" src="<%=basePath%>/b_base/jqueryForm.js" charset="utf-8"></script>
    <!-- /JQUERY -->

    <!-- DatetimePicker -->
    <script src="<%=basePath%>/b_base/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <!-- /DatetimePicker -->

    <!-- Bootstrap datetime -->
    <script type="text/javascript"
            src="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.min.js"
            charset="UTF-8"></script>
    <script type="text/javascript"
            src="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.zh-CN.js"
            charset="UTF-8"></script>
    <link href="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/css/bootstrap-datetimepicker.min.css"
          rel="stylesheet" media="screen"/>


    <!-- BOOTSTRAP -->
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap-theme.css"/>
    <script type="text/javascript" src="<%=basePath%>/b_base/bootstrap-3.4.1/js/bootstrap.js" charset="utf-8"></script>
    <!-- /BOOTSTRAP -->

    <!-- LayoutIt bootstrap -->
    <!-- table-->
    <link type="text/css" rel="stylesheet"
          href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.css"/>
    <script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.js"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/bootstrap-table-export.js"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/tableExport.js"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/locale/bootstrap-table-zh-CN.js"></script>
    <script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/tableExport.js"></script>
    <!-- /table-->

    <!-- FileInput Css-->
    <link href="<%=basePath%>/b_ide/bootstrap/module/FileInput/css/fileinput.css" media="all" rel="stylesheet"
          type="text/css"/>
    <script src="<%=basePath%>/b_ide/bootstrap/module/Chart/Chart.js"></script>
    <script src="<%=basePath%>/b_ide/bootstrap/module/Chart/src/Chart.Doughnut.js"></script>
    <script src="<%=basePath%>/b_ide/bootstrap/module/Treeview/js/bootstrap-treeview.js"></script>
    <script src="<%=basePath%>/b_ide/bootstrap/module/FileInput/js/fileinput.js" type="text/javascript"></script>
    <script src="<%=basePath%>/b_ide/bootstrap/module/FileInput/js/locales/zh.js" type="text/javascript"></script>
    <!-- /FileInput -->

    <!-- Validator -->
    <link type="text/css" rel="stylesheet"
          href="<%=basePath%>/b_base/bootstrapValidator/css/bootstrapValidator.min.css"/>
    <script type="text/javascript" src="<%=basePath%>/b_base/bootstrapValidator/js/bootstrapValidator.min.js"
            charset="utf-8"></script>
    <!-- /Validator -->

    <!-- Multiselect -->
    <link type="text/css" rel="stylesheet"
          href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/css/bootstrap-multiselect.css"/>
    <script type="text/javascript"
            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js"></script>
    <script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/jquery.multiselectcus.js"></script>
    <!-- /Multiselect -->

    <!-- TABS -->
    <script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/tabs/js/bootstrap-tabs.js"></script>
    <!-- /TABS -->

    <!-- ystep -->
    <script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/ystep-master/js/ystep.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/b_ide/bootstrap/module/ystep-master/css/ystep.css"/>
    <!-- /ystep -->

    <!-- JBox -->
    <link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet"/>
    <script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.src.js" type="text/javascript"></script>

    <!-- UEditor -->
    <script src="${ctxStatic}/ueditor/ueditor.config.js" type="text/javascript"></script>
    <script src="${ctxStatic}/ueditor/ueditor.all.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/ueditor/ueditor.parse.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js" type="text/javascript"></script>

    <!-- FontIcon -->
    <link rel="stylesheet" href="${ctxStatic}/mainframe/fonticon/iconfont.css">

    <script type="text/javascript" src="<%=basePath%>/b_base/common/smartweb.js"></script>

    <!-- check -->
    <script type="text/javascript" src="<%=basePath%>/b_base/common/check.js"></script>
    <script type="text/javascript" src="<%=basePath%>/b_base/common/formCheck.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/b_base/common/formCheck.css"/>

    <!-- Self reference JS-->
    <script type="text/javascript" src="<%=basePath%>/b_base/views/starring/scheduler/scheduleLog.js"
            charset="utf-8"></script>

    <!-- change skin -->
    <link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css"
          type="text/css" rel="stylesheet"/>

    <title>Insert title here</title>
    <style>
        #table th, .table td {
            vertical-align: middle;
        }

        .sub-table th {
            border-bottom: 0 solid white;
            background: white;
        !important;
        }

        .errorMsgTd {
            word-wrap: break-word;
            word-break: break-all;
        }
    </style>
</head>
<body>
<div id="messageBox" class="alert alert-success hide">
    <button data-dismiss="alert" class="close">×</button>
    <span id="messageContent">操作提示信息</span></div>
<!-- view start -->
<div class="">
    <div ravo="rainbow_fx_layout" class="row clearfix">
        <div class="col-md-12 column">
            <div ravo="rainbow_fx_layout_panel" class="panel panel-default">
                <div class="panel-heading">
                    <div ravo="rainbow_fx_bj">
                        <h3 class="" contenteditable="false">
                            ${beanName} 的运行日志
                        </h3>
                    </div>
                </div>
                <div class="panel-body" contenteditable="false">
                    <form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="searchForm">
                        <div ravo="rainbow_fx_layout" class="row clearfix">
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label class="col-sm-4 control-label">
                                        开始时间
                                    </label>
                                    <div class="input-group-sm col-sm-8">
                                        <input type="text" readonly="readonly" maxlength="20"
                                               class="form-control input-mini Wdate"
                                               value="" datetime-skin="twoer" datetime-date-fmt="yyyy-MM-dd HH:mm:ss"
                                               datetime-min-date="" datetime-max-date="" datetime-is-show-clear="true"
                                               datetime-is-show-week="true" datetime-is-show-today="true"
                                               datetime-default-value=""
                                               datetime-value-fmt="yyyy-MM-dd HH:mm:ss" data-link-field="val_strTime"
                                               id="strTime"
                                               onclick="WdatePicker({skin: 'twoer',dateFmt: 'yyyy-MM-dd HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('strTime');}});"
                                               onchange="writeDateValue('strTime');">
                                        <input type="hidden" id="val_strTime" value="" name="strTime">
                                    </div>
                                </div>
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="inputEmail3" class="col-sm-4 control-label">
                                        流水号
                                    </label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" placeholder="请输入流水号" name="platSeq"
                                               id="platSeq">
                                    </div>
                                </div>
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="inputEmail3" class="col-sm-4 control-label">
                                        状态
                                    </label>
                                    <div class="col-sm-7">
                                        <select data-role="multiselect" id="succSwitchFlg" class=""
                                                name="succSwitchFlg">
                                            <option value="">
                                                请选择
                                            </option>
                                            <option value="0">
                                                成功
                                            </option>
                                            <option value="1">
                                                失败
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 column">
                                <div ravo="rainbow_fx" class="form-group">
                                    <label class="col-sm-4 control-label">
                                        结束时间
                                    </label>
                                    <div class="input-group-sm col-sm-8">
                                        <input type="text" readonly="readonly" maxlength="20"
                                               class="form-control input-mini Wdate"
                                               value="" datetime-skin="twoer" datetime-date-fmt="yyyy-MM-dd HH:mm:ss"
                                               datetime-min-date="" datetime-max-date="" datetime-is-show-clear="true"
                                               datetime-is-show-week="true" datetime-is-show-today="true"
                                               datetime-default-value=""
                                               datetime-value-fmt="yyyy-MM-dd HH:mm:ss" data-link-field="val_endTime"
                                               id="endTime"
                                               onclick="WdatePicker({skin: 'twoer',dateFmt: 'yyyy-MM-dd HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('endTime');}});"
                                               onchange="writeDateValue('endTime');">
                                        <input type="hidden" id="val_endTime" value="" name="endTime">
                                    </div>
                                </div>
                                <div ravo="rainbow_fx" class="form-group">
                                    <label for="inputEmail3" class="col-sm-4 control-label">
                                        IP地址
                                    </label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" placeholder="请输入IP地址" name="ip"
                                               id="ip">
                                    </div>
                                </div>
                                <shiro:haspermission name="anno">
                                    <button ravo="rainbow_fx" type="button" class="btn btn-default"
                                            contenteditable="false"
                                            id="btnSearch">
                                        查询
                                    </button>
                                </shiro:haspermission>
                            </div>
                            <div class="col-md-2 column">
                            </div>
                            <div class="col-md-2 column">
                            </div>
                        </div>
                    </form>
                    <input type="hidden" id="beanName" value="${beanName}">
                    <table id="scheduleTable"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<!--customer_code_beg-->

<!--customer_code_end-->
<!-- view end -->
</body>
</html>
