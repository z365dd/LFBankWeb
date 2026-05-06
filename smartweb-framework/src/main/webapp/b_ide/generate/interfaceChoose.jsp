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
<title>接口选择</title>
</head>
<body>
<div class="" style="margin-left:20px;">
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-2 column">
			<div ravo="rainbow_fx_layout_panel" class="panel panel-success">
				<div class="panel-heading">
					<h5 ravo="rainbow_fx_bj" data-rainbow="caption">
						接口列表
					</h5>
				</div>
				<div class="panel-body">
					<div ravo="rainbow_fx_bj_treeView">
						<div data-toggle="treeview" id="interfaceTree">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-10 column">
			<div ravo="rainbow_fx_layout_panel" class="panel panel-success">
				<div class="panel-heading">
					<h5 ravo="rainbow_fx_bj" data-rainbow="caption" id="interfaceTitle">
						接口XML文件预览
					</h5>
				</div>
				<div class="panel-body">
					<pre id="editorXml" style="min-height: 600px">
					</pre>
				</div>
			</div>
			
		</div>
	</div>
</div>

</body>
</html>
