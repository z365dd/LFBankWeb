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
            src="<%=basePath%>/b_base/views/starring/pay/ykt/ykt_mer_mng/add.js"
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
                                <label for="busiNo" class="col-sm-3 control-label control-label"
                                       style="visibility: visible">
                                    业务编号：
                                </label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" placeholder="" id="busiNo"
                                           name="busiNo" style="width: 200px"/>
                                </div>
                                <div class="col-md-1" style="color: red;padding-left: 4%" >*</div>
                                <div class="col-md-3" id="busiNoErrTip" style="color: red;">业务编号不能为空</div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <label for="busiName" class="col-sm-3 control-label control-label"
                                       style="visibility: visible">
                                    业务名称：
                                </label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" placeholder="" id="busiName"
                                           name="busiName" style="width: 200px"/>
                                </div>
                                <div class="col-md-1" style="color: red;padding-left: 4%" >*</div>
                                <div class="col-md-3" id="busiNameErrTip" style="color: red;">业务名称不能为空</div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <label for="office" class="col-sm-3 control-label control-label"
                                       style="visibility: visible">
                                    所属机构：
                                </label>
<%--                                <div class="col-sm-2">--%>
                                    <sys:treeselect id="office" css_style="width:200px"
                                                    name="officeId" value="${office.id}"
                                                    label_name="officeName" label_value="${office.name}"
                                                    title="所属机构" url="/sys/office/treeData?type=2" css_class="required"
                                                    treesearch_required="true" />
<%--                                </div>--%>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <label for="payAcct" class="col-sm-3 control-label control-label"
                                       style="visibility: visible">
                                    清算账户：
                                </label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" placeholder="" id="payAcct"
                                           name="payAcct" style="width: 200px"/>
                                </div>
                                <div class="col-md-1" style="color: red;padding-left: 4%" >*</div>
                                <div class="col-md-3" id="acctErrTip" style="color: red;">账户不能为空</div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <label for="payAcctName" class="col-sm-3 control-label control-label"
                                       style="visibility: visible">
                                    清算账户名称：
                                </label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" placeholder="" id="payAcctName"
                                           name="payAcctName" style="width: 200px"/>
                                </div>
                                <div class="col-md-1" style="color: red;padding-left: 4%" >*</div>
                                <div class="col-md-3" id="acctNameErrTip" style="color: red;">账户名称不能为空</div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <label for="clrCycle" class="col-sm-3 control-label control-label"
                                       style="visibility: visible">
                                    清算周期：
                                </label>
                                <div class="col-sm-2">
                                    <select
                                            data-role="multiselect"
                                            id="clrCycle"
                                            class=""
                                            name="clrCycle"
                                            data-max-height="300"
                                            data-button-width="200"
                                            data-enable-filtering="true"
                                            data-enable-full-value-filtering="false"
                                            data-enable-case-insensitive-filtering="true"
                                            data-filter-placeholder="搜索"
                                            checkBtn="openStat"
                                    >
                                        <option value="Y">T1清算</option>
                                        <option value="N">D1清算</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-12 column lineMaginTop" hidden>
                                <div class="col-md-2 column"></div>
                                <label for="sepaFlg" class="col-sm-3 control-label control-label"
                                       style="visibility: visible">
                                    支持拆分缴费：
                                </label>
                                <div class="col-sm-2">
                                    <select
                                            data-role="multiselect"
                                            id="sepaFlg"
                                            class=""
                                            name="sepaFlg"
                                            data-max-height="300"
                                            data-button-width="200"
                                            data-enable-filtering="true"
                                            data-enable-full-value-filtering="false"
                                            data-enable-case-insensitive-filtering="true"
                                            data-filter-placeholder="搜索"
                                            checkBtn="sepaFlg"
                                    >
                                        <option value="N">否</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <label for="addr" class="col-sm-3 control-label control-label"
                                       style="visibility: visible">
                                    所属地区：
                                </label>
                                <div class="col-sm-5" style="display: flex;flex-direction: row;">
                                    <div hidden><input type="text" class="form-control" placeholder="" id="provinceName"
                                                name="provinceName" /></div>
                                    <select data-role="multiselect" id="province" class=""
                                            name="province" data-max-height="300" checkbtn="province">
                                    </select>
                                    <p style="width: 20px; text-align: center">-</p>
                                    <select data-role="multiselect" id="city" class=""
                                            name="city" data-max-height="300" checkbtn="city">
                                    </select>
                                    <p style="width: 20px; text-align: center">-</p>
                                    <input type="text" class="form-control" placeholder=""
                                           data-bv-="true" id="detailedAddress" name="detailedAddress"
                                           check-empty="true"
                                    >
                                </div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <label for="phoneNo" class="col-sm-3 control-label control-label"
                                       style="visibility: visible">
                                    咨询电话：
                                </label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" placeholder="" id="phoneNo"
                                           name="phoneNo" style="width: 200px"/>
                                </div>
                                <div class="col-md-1" style="color: red;padding-left: 4%" >*</div>
                                <div class="col-md-3" id="phoneNoErrTip" style="color: red;">咨询电话不能为空</div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <label for="name" class="col-sm-3 control-label control-label"
                                       style="visibility: visible">
                                    联系人：
                                </label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" placeholder="" id="name"
                                           name="name" style="width: 200px"/>
                                </div>
                                <div class="col-md-1" style="color: red;padding-left: 4%" >*</div>
                                <div class="col-md-3" id="nameErrTip" style="color: red;">联系人不能为空</div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-2 column"></div>
                                <label for="openStat" class="col-sm-3 control-label control-label"
                                       style="visibility: visible">
                                    商户状态：
                                </label>
                                <div class="col-sm-2">
                                    <select
                                            data-role="multiselect"
                                            id="openStat"
                                            class=""
                                            name="openStat"
                                            data-max-height="300"
                                            data-button-width="200"
                                            data-enable-filtering="true"
                                            data-enable-full-value-filtering="false"
                                            data-enable-case-insensitive-filtering="true"
                                            data-filter-placeholder="搜索"
                                            checkBtn="openStat"
                                    >
                                        <option value="Y">上架</option>
                                        <option value="N">下架</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-12 column lineMaginTop">
                                <div class="col-md-4 column"></div>
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
