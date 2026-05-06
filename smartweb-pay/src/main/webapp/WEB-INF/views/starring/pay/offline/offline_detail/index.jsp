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

    <!-- change skin -->
    <link
            href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css"
            type="text/css" rel="stylesheet"/>

    <!-- Self reference JS-->
    <script type="text/javascript"
            src="<%=basePath%>/b_base/views/starring/pay/offline/offline_detail/index.js"
            charset="utf-8"></script>

    <style>
        #iframe_list {
            height: 1000px !important;
        }
    </style>
    <title>Insert title here</title>
</head>
<body>

<div ravo="rainbow_fx_layout" class="row clearfix">
    <div class="col-md-12 column">
        <div ravo="rainbow_fx_layout_tab" class="tabbable" id="myTabs">
            <!-- Only required for left/right tabs -->
            <ul class="nav nav-tabs" data-toggle="tabs">
                <li class="active">
                    <a id="tPipAtomProdList" href="#tab_list" data-toggle="tab" contenteditable="false"
                       title="缴费明细列表查询" aria-expanded="true" url="${ctx}/offline_detail/page/list">
                        缴费明细列表查询
                    </a>
                </li>
                <li>
                    <a href="#tab_add" data-toggle="tab" aria-expanded="false" url="${ctx}/offline_detail/page/add">
                        缴费明细新增
                    </a>
                </li>
                <li>
                    <a href="#tab_update" data-toggle="tab" aria-expanded="false" url="${ctx}/offline_detail/page/modify">
                        缴费明细修改
                    </a>
                </li>
                <li>
                    <a href="#tab_info" data-toggle="tab" aria-expanded="false" url="${ctx}/offline_detail/page/info">
                        缴费明细详情
                    </a>
                </li>
            </ul>
            <div id="comDiv" hidden></div>
            <div id="comQuery" hidden></div>
            <div class="tab-content">
                <div class="tab-pane active" id="tab_list">
                    <iframe id="iframe_list" name="iframe_list" src="" style="width: 100%; height: 1500px; border: 0px;"
                            scrolling="no" frameborder="no">
                    </iframe>
                </div>
                <div id="tab_add" class="tab-pane">
                    <iframe id="iframe_add" name="iframe_add" src=""
                            style="width: 100%; height: 1300px; border: 0px;" scrolling="no" frameborder="no"></iframe>
                </div>
                <div id="tab_update" class="tab-pane">
                    <iframe id="iframe_update" name="iframe_update" src=""
                            style="width: 100%; height: 1300px; border: 0px;" scrolling="no" frameborder="no"></iframe>
                </div>
                <div id="tab_info" class="tab-pane">
                    <iframe id="iframe_info" name="iframe_info" src=""
                            style="width: 100%; height: 1300px; border: 0px;" scrolling="no" frameborder="no"></iframe>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
