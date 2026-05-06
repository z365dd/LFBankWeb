<%@ page contentType="text/html;charset=UTF-8" %>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Cache-Control" content="no-store">
<script src="${ctxStatic}/jquery-3.6.1.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctxStatic}/jquery-migrate-3.4.0.min.js" type="text/javascript" charset="utf-8"></script>
<!--
<link href="${ctxStatic}/bootstrap-3.4.1/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.css" type="text/css" rel="stylesheet" />
-->
<link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/bootstrap-3.4.1/js/bootstrap.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.css" type="text/css" rel="stylesheet"/>
<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet"/>
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<!-- Multiselect -->
<link type="text/css" rel="stylesheet"
      href="${pageContext.request.contextPath}/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/css/bootstrap-multiselect.css"/>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/b_ide/bootstrap/js/jquery.multiselectcus.js"></script>
<!-- /Multiselect -->
<!-- add by lijb -->
<script type="text/javascript" src="${ctxStatic}/bootstrap-modal/bootstrap-modal.js" charset="utf-8"></script>
<!-- notification -->
<script type="text/javascript" src="${ctxStatic}/sockjs/sockjs.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/stomp/stomp.js" charset="utf-8"></script>
<link type="text/css" rel="stylesheet" href="${ctxStatic}/notification/notification.css">
<script type="text/javascript" src="${ctxStatic}/notification/notification.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/stomp/message-notification.js" charset="utf-8"></script>
<!-- /notification -->
<script type="text/javascript" src="${ctxStatic}/common/formHelper.js" charset="utf-8"></script>
<!-- /add by lijb -->
<!-- FontIcon -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/b_base/mainframe/fonticon/iconfont.css">
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/mustache.js" type="text/javascript"></script>
<link href="${ctxStatic}/common/smartweb.css" type="text/css" rel="stylesheet"/>
<script src="${ctxStatic}/common/smartweb.js" type="text/javascript"></script>
<!-- check -->
<script type="text/javascript" src="${pageContext.request.contextPath}/b_base/common/check.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/b_base/common/formCheck.css"/>
<script type="text/javascript">var ctx = '${ctx}', ctxStatic = '${ctxStatic}', ctxIde = '${ctxIde}';</script>
<link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css" type="text/css" rel="stylesheet" />
