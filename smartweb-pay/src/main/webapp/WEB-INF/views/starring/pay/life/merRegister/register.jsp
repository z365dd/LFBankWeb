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
    <meta http-equiv="Cache" content="no-cache">
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
            src="<%=basePath%>/b_base/views/starring/pay/life/merRegister/register.js"
            charset="utf-8"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            // 禁止浏览器记住密码
            const userAgent = navigator.userAgent
            if (userAgent.indexOf("Chrome") !== -1 && userAgent.indexOf("Safari") !== -1 && userAgent.indexOf("Edg") === -1) {
                $("#pwd").addClass("txtPassword")
                $("#requirePwd").addClass("txtPassword")
            } else {
                $("#pwd").focus(() => {
                    $("#requirePwd").attr("type", "password")
                })
                $("#pwd").focus(() => {
                    $("#requirePwd").attr("type", "password")
                })
            }
        })
    </script>
    <style>
        #iframe_list {
            height: 750px !important;
        }

        .txtPassword {
            -webkit-text-security: disc;
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
                    <form id="inputForm" ravo="rainbow_fx_layout_bd" class="form-horizontal"
                          action="${ctx}/merRegister/data/register"
                          method="post" id="from">
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
                            <label for="loginName" class="col-sm-5 control-label control-label"
                                   style="visibility: visible">
                                登录名称：
                            </label>
                            <input id="loginName" name="loginName" required="true" type="text"
                                   class="input-medium"
                                   value=""/>
                            <span style="color: red">  *</span>
                            <span id="loginNameErrTip" style="color: red;display:none">登录名称仅支持英文和数字</span>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="pwd" class="col-sm-5 control-label control-label" style="visibility: visible">
                                密码：
                            </label>
                            <input id="pwd" name="pwd" type="text" required="true" class="input-medium"
                                   value=""/>
                            <span style="color: red">  *</span>
                            <span id="pwdErrTip" style="color: red;display:none">密码长度至少为6位</span>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="requirePwd" class="col-sm-5 control-label control-label"
                                   style="visibility: visible">
                                确认密码：
                            </label>
                            <input id="requirePwd" name="requirePwd" type="text" required="true"
                                   class="input-medium "
                                   value=""/>
                            <span style="color: red">  *</span>
                            <span id="requirePwdErrTip" style="color: red;display:none">两次密码必须一致</span>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="realName" class="col-sm-5 control-label control-label"
                                   style="visibility: visible">
                                真实姓名：
                            </label>
                            <input id="realName" name="realName" type="text" class="input-medium "
                                   value="" required/>
                            <span style="color: red">  *</span>
                            <span id="nameNoErrTip" style="color: red;display:none">真实姓名不能超过20位</span>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="certNo" class="col-sm-5 control-label control-label"
                                   style="visibility: visible">
                                身份证号：
                            </label>
                            <input id="certNo" name="certNo" type="text" class="input-medium "
                                   value="" required/>
                            <span style="color: red">  *</span>
                            <span id="certNoErrTip" style="color: red;display:none">身份证号必须为18位</span>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="phoneNo" class="col-sm-5 control-label control-label"
                                   style="visibility: visible">
                                手机号：
                            </label>
                            <input id="phoneNo" name="phoneNo" phone="true" type="text" class="input-medium "
                                   value="" required/>
                                <span style="color: red">  *</span>
                                <span id="phoneNoErrTip" style="color: red;display:none">手机号必须为11位</span>
                        </div>
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="email" class="col-sm-5 control-label control-label" style="visibility: visible">
                                邮箱：
                            </label>
                            <input id="email" name="email" type="text" class="input-medium "
                                   value=""/>
                            <span id="emailErrTip" style="color: red;display:none">邮箱中必须包含@</span>
                        </div>
                        <div ravo="rainbow_fx" class="form-group" style="display:flex;justify-content: center;">
                            <button ravo="rainbow_fx" type="submit"
                                    class="btn visibility:visible btn-primary"
                                    style="align-content: center; width: 100px"
                                    contenteditable="false" id="sumbitBtn" name="sumbitBtn" onclick="submitInfo">
                                提交
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
