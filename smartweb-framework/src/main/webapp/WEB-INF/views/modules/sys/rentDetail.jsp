<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<html>
<head>
    <title>租户详情</title>
    <meta name="decorator" content="default"/>
    <!-- UEditor -->
    <script src="${ctxStatic}/ueditor/ueditor.config.js" type="text/javascript"></script>
    <script src="${ctxStatic}/ueditor/ueditor.all.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/ueditor/ueditor.parse.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js" type="text/javascript"></script>
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
            var hasMsMall = '${hasMsMall}';
            if(hasMsMall=='YES'&&$("#id").val()!=""){
                var ue = UE.getEditor('description');
                $("#bgImg").val('${imgUrl}');
                bgImgPreview();
                /* 实例后在ready方(组件渲染后事件)中设置内容 */
                ue.ready(function() {
                    ue.setContent('${description}');
                });
                $('#stat').multiselect("select", '${rent.stat}').multiselect('disable');
            }
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/sys/rent/list?id=${fns:getUser().rent.id}&parentIdList=${fns:getUser().rent.parentIdList}">租户列表</a></li>
    <li class="active"><a href="${ctx}/sys/rent/detail?id=${rent.id}&parent.id=${rent.parent.id}">租户详情</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="rent" action="#" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <div class="control-group">
        <label class="control-label" for="parent">上级租户:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="parent" value="${rent.parent.name}" readonly/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="name">租户名称:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="name" value="${rent.name}" readonly/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="name">租户编码:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="engName" value="${rent.engName}" readonly/>
        </div>
    </div>
    <c:if test="${hasMsMall=='YES' }">
        <div class="control-group">
            <label class="control-label">
                背景图:
            </label>
            <div id="ckfinder" style="margin-left: 180px">
                <sys:ckfinder input="bgImg" name="bgImg" value="" type="images" upload_path="/msmall/rent"
                              is_all_user="false" ckfinder_required="false" select_multiple="false" readonly="readonly"
                              max_width="200" max_height="200">
                </sys:ckfinder>
            </div>
        </div>
    </c:if>
    <div class="control-group hide">
        <label class="control-label" for="name">默认寻址地址:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="url" value="${rent.url}" readonly/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">租户状态:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <%-- <form:select path="stat" class="input-medium">
                <form:options items="${fns:getDictList('OPEN_STAT')}" itemLabel="label" itemValue="value" htmlEscape="false" disabled="true"/>
            </form:select> --%>
           	<select data-role="multiselect" id="stat" name="stat" class="" data-bv-notempty="true"
				data-async="true" blank-item="false" checkbtn="stat" blank-text="--请选择--" 
				check-empty="true"  data-max-height="300" data-url="${ctx}/sys/dict/selectData?type=OPEN_STAT">
			</select>	
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="name">备注:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input type="text" id="rmrk" value="${rent.rmrk}" readonly/>
        </div>
    </div>
    <c:if test="${hasMsMall=='YES' }">
        <div class="control-group">
            <label class=" control-label">
                描述信息:
            </label>
            <div style="margin-left: 20px; display:inline-block;" id="ueditor">
                <script type="text/plain" id="description" name="description" style="height:300px;width: 600px">
                </script>
            </div>
        </div>
    </c:if>
    <div class="form-actions">
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>