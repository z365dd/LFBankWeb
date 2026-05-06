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
<title>错误信息页面</title>
<!-- BOOTSTRAP -->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap-theme.css"/>
<script type="text/javascript" src="<%=basePath%>/b_base/bootstrap-3.4.1/js/bootstrap.js" charset="utf-8"></script>
<!-- /BOOTSTRAP -->
</head>
<body>
	<div class="" style="margin-left:20px;">
		<div ravo="rainbow_fx_layout" class="row clearfix">
			<div class="col-md-12 column">
				<div ravo="rainbow_fx_layout_panel" class="panel panel-danger">
					<div class="panel-heading">
						<h5 ravo="rainbow_fx_bj" data-rainbow="caption">自动生成页面错误.</h5>
					</div>
					<div class="panel-body">
						<h3 contenteditable="true" class="rainbow-select text-error">
							错误信息：请选择接口和模板！
						</h3>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
