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
            src="<%=basePath%>/b_base/views/starring/pay/offline/offline_refund/info.js"
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


                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-1"></div>
                                <div class="col-md-10 column">
                                    <label for="busiName"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        业务名称：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="busiName"
                                               name="busiName" readonly/>
                                    </div>
                                    <label for="busiNo"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible" hidden>
                                        业务编号：
                                    </label>
                                    <div class="col-sm-3" hidden>
                                        <input type="text" class="form-control" placeholder="" id="busiNo"
                                               name="busiNo" readonly/>
                                    </div>

                                </div>
                                <div class="col-md-1"></div>
                            </div>

                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-1"></div>
                                <div class="col-md-10 column">
                                    <label for="platDate"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        缴费日期：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="platDate"
                                               name="platDate" readonly/>
                                    </div>
                                    <label for="platSeq"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        缴费流水：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="platSeq"
                                               name="platSeq" readonly/>
                                    </div>
                                </div>
                                <div class="col-md-1"></div>
                            </div>

                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-1 column"></div>
                                <div class="col-md-10 column"  style="display: flex;flex-direction: row;align-items: center;justify-content: flex-start;">
                                    <label for="projName"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        收费项目：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="projName"
                                               name="projName" readonly/>
                                    </div>
                                    <label for="oweMonth" class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        收费周期：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="oweMonth"
                                               name="oweMonth" readonly/>
                                    </div>
                                </div>
                                <div class="col-md-1 column"></div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-1"></div>
                                <div class="col-md-10 column">
                                    <label for="strDate"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        缴费开始日期：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="strDate"
                                               name="strDate" readonly/>
                                    </div>
                                    <label for="endDate"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        缴费截止日期：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="endDate"
                                               name="endDate" readonly/>
                                    </div>
                                </div>
                                <div class="col-md-1"></div>
                            </div>

                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-1 column"></div>
                                <div class="col-md-10 column"  style="display: flex;flex-direction: row;align-items: center;justify-content: flex-start;">
                                    <label for="name"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        姓名：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="name"
                                               name="name" readonly/>
                                    </div>
                                    <label for="phoneNo" class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        手机号码：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="例如 202312" id="phoneNo"
                                               name="phoneNo" readonly/>
                                    </div>
                                </div>
                                <div class="col-md-1 column"></div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-1 column"></div>
                                <div class="col-md-10 column"  style="display: flex;flex-direction: row;align-items: center;justify-content: flex-start;">
                                    <label for="totAmt"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        缴费金额：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="totAmt"
                                               name="totAmt" readonly/>
                                    </div>
                                    <label for="certNo"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        证件号码：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="certNo"
                                               name="certNo" readonly/>
                                    </div>
                                </div>
                                <div class="col-md-1 column"></div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-1 column"></div>
                                <div class="col-md-10 column"  style="display: flex;flex-direction: row;align-items: center;justify-content: flex-start;">
                                    <label for="major"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        专业：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="major"
                                               name="major" readonly/>
                                    </div>
                                    <label for="stuClass"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        班级：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="stuClass"
                                               name="stuClass" readonly/>
                                    </div>
                                </div>
                                <div class="col-md-1 column"></div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-1 column"></div>
                                <div class="col-md-10 column"  style="display: flex;flex-direction: row;align-items: center;justify-content: flex-start;">
                                    <label for="stuId"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        学号：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="stuId"
                                               name="stuId" readonly/>
                                    </div>

                                    <label for="vrfyNoCrtId"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible" hidden>
                                        隐藏短信关联码——传参使用：
                                    </label>
                                    <div class="col-sm-3" hidden>
                                        <input type="text" class="form-control" placeholder="" id="vrfyNoCrtId"
                                               name="vrfyNoCrtId" readonly/>
                                    </div>

                                    <label for="dctAmt"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        优惠金额：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="dctAmt"
                                               name="dctAmt" readonly/>
                                    </div>

                                </div>

                                <div class="col-md-1 column"></div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-1 column"></div>
                                <div class="col-md-10 column"  style="display: flex;flex-direction: row;align-items: center;justify-content: flex-start;">
                                    <label for="feeAmt"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        手续费：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="feeAmt"
                                               name="feeAmt" readonly/>
                                    </div>
                                    <label for="lateFeeAmt"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        滞纳金：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="lateFeeAmt"
                                               name="lateFeeAmt" readonly/>
                                    </div>
                                </div>
                                <div class="col-md-1 column"></div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-1 column"></div>
                                <div class="col-md-10 column"
                                     style="display: flex;flex-direction: row;align-items: center;justify-content: flex-start;">
                                    <label for="operStat" class="col-sm-2 control-label" style="visibility: visible">
                                        支付方式：</label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <select data-role="multiselect" id="operStat"
                                                class=""
                                                name="operStat"
                                                data-button-width="143"
                                                disabled="disabled">
                                            <option value="00">线上缴费</option>
                                            <option value="01">线下本行扫码支付</option>
                                            <option value="02">线下现金</option>
                                        </select>
                                    </div>
                                    <label for="prctlAmt"
                                           class="col-sm-2 control-label control-label"
                                           style="visibility: visible">
                                        应缴金额：
                                    </label>
                                    <div class="col-sm-3" style="width: 200px">
                                        <input type="text" class="form-control" placeholder="" id="prctlAmt"
                                               name="prctlAmt" readonly/>
                                    </div>
                                    <div class="col-md-1 column"></div>
                                </div>
                            </div>
                            <div class="col-md-6 column"></div>
                            <div class="col-md-1 column">
                                <button
                                        type="button"
                                        class="btn btn-info pull-left"
                                        contenteditable="false"
                                        id="refund"
                                        name="refund"
                                        style="margin-top: 50px"
                                        hidden
                                >
                                    退费
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
<div class="modal fade" style="display: none;" id="refundModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 500px;margin-left: 10%">
            <div class="modal-header">
                <h5 class="modal-title" id="importDtl">
                    发送短信验证码
                </h5>
            </div>
            <div class="modal-body" style="height: 5%">
                <form id="refundForm" method="post" enctype="multipart/form-data">
                    <div class="col-md-12 column">

                        <label for="vrfyNo"
                               class="col-sm-5 control-label control-label"
                               style="visibility: visible;padding-top: 2%">
                            请输入短信验证码：
                        </label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="" id="vrfyNo"
                                   name="vrfyNo" />
                        </div>

                        <button id="refundMoney" type="button">退费</button>
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

<div class="modal fade" style="display: none;" id="refundDtlModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 500px;margin-left: 10%">
            <div class="modal-header">
                <h5 class="modal-title">
                    退费明细确认页面
                </h5>
            </div>
            <div class="modal-body" style="height: 10%">

                <div class="col-md-12 column">
                    <div class="col-sm-1"></div>
                    <div id="relateInfo" class="col-sm-9" style="white-space: pre-line;font-weight: bold">
                    </div>
                    <div class="col-sm-2"></div>
                </div>
                <div class="col-md-12 column">

                </div>
                </form>
            </div>
            <div class="modal-footer">
                <div class="col-md-12 column">
                    <button id="confirm" type="button" class="col-sm-5 btn btn-primary">我已知晓,继续退费</button>
                    <button id="cancelBtn1" type="button" class="btn btn-primary">
                        返回
                    </button>
                </div>

            </div>
        </div><!-- /.modal-content -->
    </div>
</div>

</body>
</html>
