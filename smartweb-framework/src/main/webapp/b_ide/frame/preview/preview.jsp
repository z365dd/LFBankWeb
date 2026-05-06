<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<!DOCTYPE html>
<html style="padding-bottom: 54px;"><head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">

    <title>preview</title>
    <meta name="description" content="preview"> 
    <meta name="keywords" content="preview">
    <link rel="Shortcut icon" href="<%=basePath%>/b_base/dark/images/icon/logo.png">
    <link href="<%=basePath%>/b_ide/frame/preview/css/demo.css" rel="stylesheet" media="all">
    <!--[if IE]>
			
			<style type="text/css">			
				li.purchase a {
					padding-top: 5px;
					background-position: 0px -4px;
				}
				
				li.remove_frame a {
					padding-top: 5px;
					background-position: 0px -3px;
				}						
			</style>
			
		<![endif]-->
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-3.6.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-migrate-3.4.0.min.js" charset="utf-8"></script><link href="<%=basePath%>/b_base/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="<%=basePath%>/b_base/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/messenger.js" charset="utf-8"></script>
   <script type="text/javascript">

        var theme_list_open = false;

        $(document).ready(function () {
            function fixHeight() {
                var headerHeight = $("#switcher").height();
                $("#iframe").attr("height", $(window).height()-54+ "px");
            }
            $(window).resize(function () {
                fixHeight();
            }).resize();

            $('.icon-monitor').addClass('active');

            $(".icon-mobile-3").click(function () {
                $("#by").css("overflow-y", "auto");
                $('#iframe-wrap').removeClass().addClass('mobile-width-3');
                $('.icon-tablet,.icon-mobile-1,.icon-monitor,.icon-mobile-2,.icon-mobile-3').removeClass('active');
                $(this).addClass('active');
                return false;
            });

            $(".icon-mobile-2").click(function () {
                $("#by").css("overflow-y", "auto");
                $('#iframe-wrap').removeClass().addClass('mobile-width-2');
                $('.icon-tablet,.icon-mobile-1,.icon-monitor,.icon-mobile-2,.icon-mobile-3').removeClass('active');
                $(this).addClass('active');
                return false;
            });

            $(".icon-mobile-1").click(function () {
                $("#by").css("overflow-y", "auto");
                $('#iframe-wrap').removeClass().addClass('mobile-width');
                $('.icon-tablet,.icon-mobile,.icon-monitor,.icon-mobile-2,.icon-mobile-3').removeClass('active');
                $(this).addClass('active');
                return false;
            });

            $(".icon-tablet").click(function () {
                $("#by").css("overflow-y", "auto");
                $('#iframe-wrap').removeClass().addClass('tablet-width');
                $('.icon-tablet,.icon-mobile-1,.icon-monitor,.icon-mobile-2,.icon-mobile-3').removeClass('active');
                $(this).addClass('active');
                return false;
            });

            $(".icon-monitor").click(function () {
                $("#by").css("overflow-y", "hidden");
                $('#iframe-wrap').removeClass().addClass('full-width');
                $('.icon-tablet,.icon-mobile-1,.icon-monitor,.icon-mobile-2,.icon-mobile-3').removeClass('active');
                $(this).addClass('active');
                return false;
            });
        });
    </script>

</head>
<body id="by" style="overflow-y: hidden" class=" pace-done"><div class="pace  pace-inactive"><div class="pace-progress" data-progress-text="100%" data-progress="99" style="width: 100%;">
  <div class="pace-progress-inner"></div>
</div>
<div class="pace-activity"></div></div>
    <div id="switcher">
        <div class="center">
		    <ul>
			  <li style="float : left;margin-top: 18px;">
			    <a class="navbar-brand" href="javascript:void(0);">
				  <img alt="big" src="<%=basePath%>/b_ide/bootstrap/file/logo.png" style="width: 60px; height: 20px;float : left;margin-right: 5px;" />
				SmartWeb</a>
			  </li>
			</ul>
            <ul>
                <div id="Device">
                    <li class="device-monitor">
                    <a href="javascript:">
                        <div class="icon-monitor active"></div>
                    </a></li>
                    <li class="device-mobile">
                    <a href="javascript:">
                        <div class="icon-tablet"></div>
                    </a></li>
                    <li class="device-mobile">
                    <a href="javascript:">
                        <div class="icon-mobile-1"></div>
                    </a></li>
                    <li class="device-mobile-2">
                    <a href="javascript:">
                        <div class="icon-mobile-2"></div>
                    </a></li>
                    <li class="device-mobile-3">
                    <a href="javascript:">
                        <div class="icon-mobile-3"></div>
                    </a></li>
                </div>
            </ul>
        </div>
    </div>
	
    <div id="iframe-wrap">
        <iframe id="iframe" src="<%=basePath%>/b_ide/bootstrap/previewContent.jsp" frameborder="0" width="100%" height="569px">
        </iframe>
        
    </div>
<script type="text/javascript">
		var messenger;
		$(function() {
			messenger = new Messenger('preview', 'faceui');
			messenger.listen(function(msg) {
				if(msg=='open'){
					document.location.reload(); 
				}
			});

			messenger.addTarget(window.parent, 'Parent');

		});
	</script>
 </body>
</html>