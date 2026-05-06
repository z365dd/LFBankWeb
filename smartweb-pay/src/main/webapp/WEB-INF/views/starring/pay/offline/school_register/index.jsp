<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <script type="text/javascript">
        var ctx = '${ctx}', ctxStatic = '${ctxStatic}';
    </script>
    <script type="text/javascript" src="${ctx}/b_base/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/b_base/bootstrap-3.4.1/js/bootstrap.js"></script>
    <link rel="stylesheet" href="${ctx}/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
    <script type="text/javascript" src="${ctx}/b_base/views/starring/pay/offline/school_register/index.js"></script>
</head>
<body>
<div class="tabbable" id="myTabs">
    <ul class="nav nav-tabs" data-toggle="tabs">
        <li class="active"><a href="#tab_list" data-toggle="tab" url="${ctx}/school_register/page/list">学校商户查询</a></li>
        <li><a href="#tab_add" data-toggle="tab" url="${ctx}/school_register/page/add">学校商户新增</a></li>
        <li><a href="#tab_update" data-toggle="tab" url="${ctx}/school_register/page/update">学校商户修改</a></li>
    </ul>
    <div id="comDiv" hidden></div>
    <div class="tab-content">
        <div class="tab-pane active" id="tab_list"><iframe id="iframe_list" style="width:100%;height:1200px;border:0"></iframe></div>
        <div class="tab-pane" id="tab_add"><iframe id="iframe_add" style="width:100%;height:1200px;border:0"></iframe></div>
        <div class="tab-pane" id="tab_update"><iframe id="iframe_update" style="width:100%;height:1200px;border:0"></iframe></div>
    </div>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <script type="text/javascript">
        var ctx = '${ctx}', ctxStatic = '${ctxStatic}';
    </script>
    <script type="text/javascript" src="${ctx}/b_base/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/b_base/bootstrap-3.4.1/js/bootstrap.js"></script>
    <link rel="stylesheet" href="${ctx}/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
    <script type="text/javascript" src="${ctx}/b_base/views/starring/pay/offline/school_register/index.js"></script>
</head>
<body>
<div class="tabbable" id="myTabs">
    <ul class="nav nav-tabs" data-toggle="tabs">
        <li class="active"><a href="#tab_list" data-toggle="tab" url="${ctx}/school_register/page/list">学校商户查询</a></li>
        <li><a href="#tab_add" data-toggle="tab" url="${ctx}/school_register/page/add">学校商户新增</a></li>
        <li><a href="#tab_update" data-toggle="tab" url="${ctx}/school_register/page/update">学校商户修改</a></li>
    </ul>
    <div id="comDiv" hidden></div>
    <div class="tab-content">
        <div class="tab-pane active" id="tab_list"><iframe id="iframe_list" style="width:100%;height:1200px;border:0"></iframe></div>
        <div class="tab-pane" id="tab_add"><iframe id="iframe_add" style="width:100%;height:1200px;border:0"></iframe></div>
        <div class="tab-pane" id="tab_update"><iframe id="iframe_update" style="width:100%;height:1200px;border:0"></iframe></div>
    </div>
</div>
</body>
</html>
