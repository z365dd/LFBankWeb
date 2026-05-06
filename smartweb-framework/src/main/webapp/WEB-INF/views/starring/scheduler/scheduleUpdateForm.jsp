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
    <script type="text/javascript" src="<%=basePath%>/b_base/cronGen/cronGen.js"></script>
    <script type="text/javascript" src="<%=basePath%>/b_base/common/formHelper.js"></script>
    <script type="text/javascript" src="<%=basePath%>/b_base/views/starring/scheduler/scheduleCommon.js"
            charset="utf-8"></script>
    <script type="text/javascript" src="<%=basePath%>/b_base/views/starring/scheduler/scheduleUpdateForm.js"
            charset="utf-8"></script>

    <!-- change skin -->
    <link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css"
          type="text/css" rel="stylesheet"/>

    <title>Insert title here</title>
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
                        <h4 contenteditable="false">
                            任务修改
                        </h4>
                    </div>
                </div>
                <div class="panel-body" contenteditable="false">
                    <form ravo="rainbow_fx_layout_bd" class="form-horizontal breadcrumb" pourl=""
                          id="scheduleForm">
                        <input type="hidden" name="id" id="id" value="${s.id}">
                        <%--<div ravo="rainbow_fx" class="form-group">
                            <label for="modlCode" class="col-sm-4 control-label">
                                模块代码
                            </label>
                            <div class="col-sm-2">
                                <input id="modlCode" name="modlCode" class="form-control" maxlength="20">
                            </div>
                        </div>--%>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="name" class="col-sm-4 control-label">
                                中文名
                            </label>
                            <div class="col-sm-2">
                                <input id="name" name="name" class="form-control" maxlength="200" check-empty="true"
                                       placeholder="请输入中文名称" value="${s.name}">
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="beanName" class="col-sm-4 control-label">
                                处理类
                            </label>
                            <div class="col-sm-2">
                                <input id="beanName" name="beanName" class="form-control" maxlength="256"
                                       check-empty="true" value="${s.beanName}" placeholder="请输入任务处理类名称" readonly>
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="useCron" class="col-sm-4 control-label">
                                是否使用CRON表达式
                            </label>
                            <div class="col-sm-2">
                                <select data-role="multiselect" id="useCron" class=""
                                        data-url="${ctx}/sys/dict/selectData?type=OPEN_SWITCH_FLG"
                                        data-async="false" name="useCron" data-bv-notempty="true"
                                        data-bv-notempty-message="选项不能为空!" data-value="${s.useCron}">
                                </select>
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="cronExpr" class="col-sm-4 control-label">
                                CRON表达式
                            </label>
                            <div class="col-sm-2">
                                <input id="cronExpr" name="cronExpr" class="form-control" maxlength="255"
                                       placeholder="请输入Cron表达式" value="${s.cronExpr}">
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="nextExecTime" class="col-sm-4 control-label">
                                最近5次执行时间
                            </label>
                            <div class=" col-sm-2">
								<textarea class="form-control" rows="5" id="nextExecTime" readonly="readonly">
								</textarea>
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="timeUnitTp" class="col-sm-4 control-label">
                                任务执行时间单位
                            </label>
                            <div class="col-sm-2">
                                <select id="timeUnitTp" name="timeUnitTp" data-role="multiselect"
                                        data-url="${ctx}/sys/dict/selectData?type=TIME_UNIT_TP"
                                        blank-item="false" blank-text="--请选择--" class="" data-value="${s.timeUnitTp}">
                                </select>
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="intvlTime" class="col-sm-4 control-label">
                                时间间隔
                            </label>
                            <div class="col-sm-2">
                                <div class="input-group">
                                    <input type="text" id="intvlTime" name="intvlTime" class="form-control"
                                           placeholder="请输入执行间隔" value="5" maxlength="10"
                                           aria-describedby="timeUnit" check-empty="true">
                                    <span class="input-group-addon" id="timeUnit">秒</span>
                                </div>
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="planExecMeth" class="col-sm-4 control-label">
                                任务执行方式
                            </label>
                            <div class="col-sm-2">
                                <select id="planExecMeth" name="planExecMeth" data-role="multiselect"
                                        data-url="${ctx}/sys/dict/selectData?type=PLAN_EXEC_METH" blank-item="false"
                                        blank-text="--请选择--" data-value="0">
                                </select>
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="numKv" class="col-sm-4 control-label">
                                并行数
                            </label>
                            <div class="col-sm-2">
                                <input id="numKv" name="numKv" value="${s.numKv}" class="form-control" maxlength="3"
                                       placeholder="请输入并行数" check-empty="true">
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="strTime" class="col-sm-4 control-label">
                                开始时间
                            </label>
                            <div class="input-group-sm col-sm-2">
                                <input type="text" readonly="readonly" maxlength="20"
                                       class="form-control input-mini Wdate"
                                       value="00:00" datetime-skin="twoer" datetime-date-fmt="HH:mm"
                                       datetime-min-date=""
                                       datetime-max-date="" datetime-is-show-clear="true" datetime-is-show-week="false"
                                       datetime-is-show-today="false" datetime-default-value=""
                                       datetime-value-fmt="HH:mm"
                                       data-link-field="val_strTime" id="strTime"
                                       onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: false,isShowToday: false,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('strTime');}});"
                                       onchange="writeDateValue('strTime');">
                                <input type="hidden" id="val_strTime" value="00:00" name="strTime">
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="endTime" class="col-sm-4 control-label">
                                结束时间
                            </label>
                            <div class="input-group-sm col-sm-2">
                                <input type="text" readonly="readonly" maxlength="20"
                                       class="form-control input-mini Wdate"
                                       value="23:59" datetime-skin="twoer" datetime-date-fmt="HH:mm"
                                       datetime-min-date=""
                                       datetime-max-date="" datetime-is-show-clear="true" datetime-is-show-week="false"
                                       datetime-is-show-today="false" datetime-default-value=""
                                       datetime-value-fmt="HH:mm"
                                       data-link-field="val_endTime" id="endTime"
                                       onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: false,isShowToday: false,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('endTime');}});"
                                       onchange="writeDateValue('endTime');">
                                <input type="hidden" id="val_endTime" value="23:59" name="endTime">
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="planExecTp" class="col-sm-4 control-label">
                                执行日期选择方式
                            </label>
                            <div class="col-sm-2">
                                <select id="planExecTp" name="planExecTp" data-role="multiselect"
                                        data-url="${ctx}/sys/dict/selectData?type=PLAN_EXEC_TP"
                                        blank-item="false" blank-text="--请选择--" data-value="${s.planExecTp}">
                                </select>
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="procDateParaVal" class="col-sm-4 control-label">
                                每周
                            </label>
                            <div class="col-sm-2">
                                <select id="procDateParaVal" name="procDateParaVal" data-role="multiselect"
                                        multiple="multiple" data-select-all-text="全部选择"
                                        data-include-select-all-option="true"
                                        data-non-selected-text="请选择" data-max-height="300" data-all-selected-text="已选全部"
                                        data-n-selected-text="个已选" data-number-displayed="5" checkbtn="ture"
                                        data-enable-filtering="true"
                                        data-enable-full-value-filtering="true" data-filter-placeholder="搜索">
                                </select>
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="efftTime" class="col-sm-4 control-label">
                                生效时间
                            </label>
                            <div class="input-group-sm col-sm-2">
                                <input type="text" readonly="readonly" maxlength="20"
                                       class="form-control input-mini Wdate"
                                       value="${s.efftTime}" datetime-skin="twoer"
                                       datetime-date-fmt="yyyy-MM-dd HH:mm:ss"
                                       datetime-min-date="" datetime-max-date="" datetime-is-show-clear="true"
                                       datetime-is-show-week="true" datetime-is-show-today="true"
                                       datetime-default-value=""
                                       datetime-value-fmt="yyyy-MM-dd HH:mm:ss" data-link-field="val_efftTime"
                                       id="efftTime"
                                       onclick="WdatePicker({skin: 'twoer',dateFmt: 'yyyy-MM-dd HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('efftTime');}});"
                                       onchange="writeDateValue('efftTime');" check-time-empty="true">
                                <input type="hidden" id="val_efftTime" value="${s.efftTime}" name="efftTime">
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="invlTime" class="col-sm-4 control-label">
                                失效时间
                            </label>
                            <div class="input-group-sm col-sm-2">
                                <input type="text" readonly="readonly" maxlength="20"
                                       class="form-control input-mini Wdate"
                                       value="${s.invlTime}" datetime-skin="twoer"
                                       datetime-date-fmt="yyyy-MM-dd HH:mm:ss"
                                       datetime-min-date="" datetime-max-date="" datetime-is-show-clear="true"
                                       datetime-is-show-week="true" datetime-is-show-today="true"
                                       datetime-default-value=""
                                       datetime-value-fmt="yyyy-MM-dd HH:mm:ss" data-link-field="val_invlTime"
                                       id="invlTime"
                                       onclick="WdatePicker({skin: 'twoer',dateFmt: 'yyyy-MM-dd HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('invlTime');}});"
                                       onchange="writeDateValue('invlTime');" check-time-empty="true">
                                <input type="hidden" id="val_invlTime" value="${s.invlTime}" name="invlTime">
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="runSwitchFlg" class="col-sm-4 control-label">
                                启动时执行
                            </label>
                            <div class="col-sm-2">
                                <select id="runSwitchFlg" name="runSwitchFlg" data-role="multiselect"
                                        data-url="${ctx}/sys/dict/selectData?type=RUN_SWITCH_FLG" blank-item="false"
                                        blank-text="--请选择--" data-value="${s.runSwitchFlg}">
                                </select>
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="openSwitchFlg" class="col-sm-4 control-label">
                                是否启用
                            </label>
                            <div class="col-sm-2">
                                <select id="openSwitchFlg" name="openSwitchFlg" data-role="multiselect"
                                        data-url="${ctx}/sys/dict/selectData?type=OPEN_SWITCH_FLG" blank-item="false"
                                        blank-text="--请选择--" data-value="${s.openSwitchFlg}">
                                </select>
                            </div>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="rmrk" class="col-sm-4 control-label">
                                任务说明
                            </label>
                            <div class=" 0   col-sm-4">
								<textarea id="rmrk" name="rmrk" rows="6" class="form-control" maxlength="256"
                                          data-bv-="true">
								</textarea>
                            </div>
                        </div>
                        <div ravo="rainbow_fx_layout" class="row clearfix">
                            <div class="col-md-2 column">
                            </div>
                            <div class="col-md-2 column">
                            </div>
                            <div class="col-md-2 column">
                                <shiro:haspermission name="anno">
                                    <button ravo="rainbow_fx" type="button" class="btn btn-info" contenteditable="false"
                                            id="saveBtn">
                                        保存
                                    </button>
                                </shiro:haspermission>
                            </div>
                            <div class="col-md-2 column">
                                <shiro:haspermission name="anno">
                                    <button ravo="rainbow_fx" type="button" class="btn btn-default"
                                            contenteditable="false"
                                            id="cancelBtn">
                                        返回
                                    </button>
                                </shiro:haspermission>
                            </div>
                            <div class="col-md-2 column">
                            </div>
                            <div class="col-md-2 column">
                            </div>
                        </div>
                    </form>
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
