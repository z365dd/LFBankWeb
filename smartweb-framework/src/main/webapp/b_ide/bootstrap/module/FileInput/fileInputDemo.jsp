<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<!-- JQUERY -->
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-3.6.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-migrate-3.4.0.min.js" charset="utf-8"></script><!-- /JQUERY -->

<!-- BOOTSTRAP -->
<link rel="stylesheet" type="text/css" href="css/default.css">
<link href="css/fileinput.css" media="all" rel="stylesheet" type="text/css" />	
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap-theme.min.css"/>
<!-- js -->
<link href="<%=basePath%>/b_ide/bootstrap/module/FileInput/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>/b_ide/bootstrap/module/Chart/Chart.js"></script>
<script src="<%=basePath%>/b_ide/bootstrap/module/Chart/src/Chart.Doughnut.js"></script>
<script src="<%=basePath%>/b_ide/bootstrap/module/Treeview/js/bootstrap-treeview.js"></script>
<script src="<%=basePath%>/b_ide/bootstrap/module/FileInput/js/fileinput.js" type="text/javascript"></script>
<script src="<%=basePath%>/b_ide/bootstrap/module/FileInput/js/locales/zh.js" type="text/javascript"></script>
</head>
<body>
<div class="htmleaf-container">
		<div class="container kv-main">
            <div class="page-header">
            <h2>单张上传 <small></h2>
            </div>
            <form enctype="multipart/form-data" id="uploadForm"  method = "post">
                <input id="fileUpload" class="file" type="file" name="fileUpload"  multiple  data-show-preview="true">
            </form>
        </div>
	</div>
	
    <script>
	    $("#fileUpload").fileinput({
	        uploadUrl: "fileUpload.action",
	        allowedFileExtensions : ['jpg', 'png','gif'],
	        showUpload: true,
	        showCaption:true,
	        overwriteInitial: false,
	        maxFileSize: 1000,
	        maxFileCount: 2,
	        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
	        allowedFileTypes: ['image', 'video', 'flash']
	    /*     slugCallback: function(filename) {
	        	console.info(filename);
	            return filename.replace('(', '_').replace(']', '_');
	        }   */
		});
	            
		 </script>
	</body>
</html>
