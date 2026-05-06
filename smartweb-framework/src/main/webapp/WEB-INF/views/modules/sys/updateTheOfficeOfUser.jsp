<%--
  Created by IntelliJ IDEA.
  User: Leize
  Date: 2018-11-13
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>用户所属机构变更</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">

    </script>
</head>
<body>
    <ul class="nav nav-tabs">
        <li><a href="${ctx}/sys/user/list">用户列表</a></li>
        <li class="active"><a href="${ctx}/sys/user/updateOfficeForm?userId=${user.id}">用户机构变更</a></li>
    </ul><br/>
    <form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/updateOffice" method="post" class="form-horizontal">
        <form:hidden path="id"/>
        <div class="control-group">
            <label class="control-label">姓名:</label>
            <div style="margin-left: 20px; display:inline-block;">
                <input id="oldName" name="oldName" type="hidden" value="${user.name}">
                <form:input path="name" htmlEscape="false" maxlength="7" class="" disabled="true"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">当前归属机构:</label>
            <div style="margin-left: 20px; display:inline-block;">
                <form:input path="office.name" htmlEscape="false" maxlength="50" class="" disabled="true"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">目标机构:</label>
            <div style="margin-left: 25px; display:inline-block;">
                <sys:treeselect id="office" name="office.id" value="${user.office.id}" label_name="office.name" label_value="${user.office.name}"
                                title="部门" url="/sys/office/treeData?type=2&isAll=false" css_class="required"/>
            </div>
        </div>
        <div class="form-actions">
            <shiro:hasPermission name="sys:office:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
        </div>
    </form:form>
</body>
</html>
