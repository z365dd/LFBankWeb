<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!-- JSTL  -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>模板选择</title>
</head>
<body>
	<div class="" style="margin-left:20px;">
	<div class="path">
		<div ravo="rainbow_fx_layout" class="row clearfix">
			<div class="col-md-12 column col-xs-12 col-sm-12 col-lg-12">
				<div class="clearfix">
				</div>
				<div ravo="rainbow_fx_layout_tab" class="tabbable" id="navigations">
					<!-- Only required for left/right tabs -->
					<ul class="nav nav-pills" data-toggle="tabs" id="moduleChooseTab">
						
					</ul>
					<div class="tab-content" id="moduleChooseTabContent">

					</div>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>
