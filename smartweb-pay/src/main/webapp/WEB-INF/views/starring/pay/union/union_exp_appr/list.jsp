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
            src="<%=basePath%>/b_base/views/starring/pay/union/union_exp_appr/list.js"
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
                                        <label for="office" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            单位名称：
                                        </label>
                                        <sys:treeselect id="office" name="office.id" value="${user.office.id}" label_name="office.name" label_value="${user.office.name}"
                                                        title="机构" url="/sys/office/treeData?type=2&isAll=false" css_class="input-medium" allow_clear="true" />
                                    </div>
                                </div>
                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="stat" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            审批状态：
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
                                                <option value="01">审批中</option>
                                                <option value="02">审批通过</option>
                                                <option value="03">待补充材料</option>
                                                <option value="04">拒收</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 column">
                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="name" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            申请人姓名：
                                        </label>
                                        <div class="col-sm-6">
                                            <input type="text" class="form-control" placeholder="" id="name"
                                                   name="name" style="width: 225px"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="certNo" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            申请人证件号码：
                                        </label>
                                        <div class="col-sm-6">
                                            <input type="text" class="form-control" placeholder="" id="certNo"
                                                   name="certNo" style="width: 225px"/>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-12 column">
                                <div class="col-md-6 column">
                                    <div ravo="rainbow_fx" class="form-group">
                                        <label for="strDate" class="col-sm-3 control-label control-label"
                                               style="visibility: visible">
                                            开始日期：
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
                                            结束日期：
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
                                <div class="col-md-5 column"></div>
                                <div class="col-md-2 column" style="color:red;margin-top: 0.5%">
                                    最多可查询近三年记录
                                </div>
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
                                <div class="col-md-2 column">
                                    <shiro:haspermission name="anno">
                                        <button
                                                ravo="rainbow_fx"
                                                type="button"
                                                class="btn btn-info pull-left"
                                                contenteditable="false"
                                                id="addBtn"
                                                name="addBtn"
                                        >
                                            新增申请
                                        </button>
                                    </shiro:haspermission>
                                </div>
                            </div>
                        </div>
                    </form>
                    <div id="fileDiv1" class="col-sm-2" hidden>
                        <input id="file1" name="file1" type="file"  accept=".jpg,.png,.pdf"/>
                    </div>
                    <div id="fileDiv2" class="col-sm-2" hidden>
                        <input id="file2" name="file2" type="file"  accept=".jpg,.png,.pdf"/>
                    </div>
                    <div id="fileDiv3" class="col-sm-2" hidden>
                        <input id="file3" name="file3" type="file"  accept=".pdf"/>
                    </div>
                    <div id="fileDiv4" class="col-sm-2" hidden>
                        <input id="file4" name="file4" type="file"  accept=".pdf"/>/>
                    </div>
                    <table id="apprTable"></table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" style="display: none;pointer-events:auto" id="expenseModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 90%;margin-left: 5%">
            <div class="modal-header">
                <h5 class="modal-title">
                    报销金额填写
                </h5>
            </div>
            <div class="modal-body" style="height: 10%">

                <div class="col-md-12 column">
                    <div class="col-md-12 column" style="padding-top: 2%">
                        <label for="amt"
                               class="col-sm-4 control-label control-label"
                               style="visibility: visible;padding-top: 2%">
                            请输入退费金额：
                        </label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" placeholder="" id="amt"
                                   name="amt"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button id="save" type="button" class="btn btn-primary">
                    确定
                </button>
                <button id="cancelBtn" type="button" class="btn btn-primary">
                    返回
                </button>
            </div>
        </div>
    </div>
</div>


</body>
</html>
