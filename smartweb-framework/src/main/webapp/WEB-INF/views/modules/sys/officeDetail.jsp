<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>

<html>
<head>
    <title>机构详情</title>
    <meta name="decorator" content="default"/>
    
	<!-- BOOTSTRAP -->
	<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
	<!-- /BOOTSTRAP -->	
	
	<!-- Multiselect -->
	<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/css/bootstrap-multiselect.css"/>
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js"></script>
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/jquery.multiselectcus.js"></script>
	<!-- /Multiselect -->
	
	<!-- change skin -->
	<link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css" type="text/css" rel="stylesheet" />
    
    <script type="text/javascript">
        $(document).ready(function() {
        	$('#brchTp').multiselect("select", ${office.brchTp}).multiselect('disable');
        	$('#validSwitchFlg').multiselect("select", ${office.validSwitchFlg}).multiselect('disable');
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/sys/office/list?id=${fns:getUser().office.id}&parentIdList=${fns:getUser().office.parentIdList}">机构列表</a></li>
    <li class="active"><a href="${ctx}/sys/office/detail?id=${office.id}&parent.id=${office.parent.id}">机构详情</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="office" action="#" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <div class="control-group">
        <label class="control-label" for="parent">上级机构:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="parent" value="${office.parent.name}" readonly/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="area">归属区域:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="area" value="${office.area.name}" readonly/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="name">机构名称:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="name" value="${office.name}" readonly/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="brchCode">机构编码:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="brchCode" value="${office.brchCode}" readonly/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">机构类型:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <%-- <form:select path="brchTp" class="input-medium">
                <form:options items="${fns:getDictList('BRCH_TP')}" itemLabel="label" itemValue="value" htmlEscape="false" disabled="true"/>
            </form:select> --%>
			<select data-role="multiselect" id="brchTp" name="brchTp" class="" data-bv-notempty="true"
				data-async="true" blank-item="false" checkbtn="brchTp" blank-text="--请选择--" 
				check-empty="true"  data-max-height="300" data-url="${ctx}/sys/dict/selectData?type=BRCH_TP">
			</select>            
        </div>
    </div>
    <div class="control-group" style="display: none;">
        <label class="control-label">是否可用:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <%-- <form:select path="validSwitchFlg">
                <form:options items="${fns:getDictList('VALID_SWITCH_FLG')}" itemLabel="label" itemValue="value" htmlEscape="false" disabled="true"/>
            </form:select> --%>
			<select data-role="multiselect" id="validSwitchFlg" name="validSwitchFlg" class="" data-bv-notempty="true"
				data-async="true" blank-item="false" checkbtn="validSwitchFlg" blank-text="--请选择--" 
				check-empty="true"  data-max-height="300" data-url="${ctx}/sys/dict/selectData?type=VALID_SWITCH_FLG">
			</select>	            
            <span class="help-inline">“是”代表此账号允许登陆，“否”则表示此账号不允许登陆</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="oneRspbtPer">主负责人:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="oneRspbtPer" value="${office.oneRspbtPer.name}" readonly/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="twoRspbtPer">副负责人:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="twoRspbtPer" value="${office.twoRspbtPer.name}" readonly/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">联系地址:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="ctctAddr" value="${office.ctctAddr}" readonly/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="postEcd">邮政编码:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="postEcd" value="${office.postEcd}" readonly/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="rspbtPer">负责人:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="rspbtPer" value="${office.rspbtPer}" readonly/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="telNo">电话:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="telNo" value="${office.telNo}" readonly/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="faxNo">传真:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="faxNo" value="${office.faxNo}" readonly/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="email">邮箱:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="email" value="${office.email}" readonly/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <form:textarea path="rmrk" htmlEscape="false" rows="3" maxlength="100" class="input-xlarge" disabled="true"/>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>