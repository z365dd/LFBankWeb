<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<%@ taglib prefix="comm" tagdir="/WEB-INF/tags/comm" %>
<c:set var="ctx" value="${pageContext.request.contextPath}${fns:getAdminPath()}"/>
<c:set var="maxWidth" value="${fns:getMaxWidth()}"/>
<c:set var="wsUrl" value="${fns:getWebSocketUrl()}"/>
<c:set var="adminPath" value="${fns:getAdminPath()}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/b_base"/>
<c:set var="ctxIde" value="${pageContext.request.contextPath}/b_ide"/>
<c:set var="ctxTheme" value="${not empty fns:getConfig('project.theme') ? fns:getConfig('project.theme') : 'tech'}"/>
<c:set var="authorization" value="${not empty cookie.authorization.value ? cookie.authorization.value : ''}"/>
<c:if test="${not empty viewModel}">
    <c:set var="viewModel" value="${fns:toJson(viewModel)}"/>
</c:if>
<script type="text/javascript">
    var ctxTheme = '${ctxTheme}';
    var ctx = '${ctx}', ctxStatic = '${ctxStatic}', ctxIde = '${ctxIde}';
    var model = {};
    if ('${viewModel}') {
        model = JSON.parse('${viewModel}');
    }
    var viewModel = {};
    var authorization = '${authorization}';
</script>
