<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- JSTL标签库  -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath =  path;
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>屏幕模式</title>
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	<script src="js/html5shiv.js"></script>
	<![endif]-->

<!-- Fav and touch icons -->
<%-- <script type="text/javascript" src="<%=basePath%>/b_base/jquery-1.8.3.min.js" charset="utf-8"></script> --%>
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-3.6.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-migrate-3.4.0.min.js" charset="utf-8"></script><link rel="stylesheet" type="text/css" href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.css" />

<link rel="stylesheet" type="text/css" href="<%=basePath%>/b_ide/bootstrap/css/headlinebox.css" />

<script type="text/javascript" src="<%=basePath%>/b_base/bootstrap-3.4.1/js/bootstrap.min.js" charset="utf-8"></script>

<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/messenger.js" charset="utf-8"></script>

<!-- <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" /> -->


</head>

<body style="background-color: #2B7CAC;">
	<div class="container">
		<div class="row clearfix">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 column">
				<nav class="navbar navbar-default navbar-fixed-top navbar-inverse" role="navigation" style="">
					<div class="nav navbar-nav navbar-lift" id="bs-example-navbar-collapse-1">
						<a class="navbar-brand" href="javascript:void(0);" style="font-size:20px;">
						<img alt="big" src="<%=basePath%>/b_ide/bootstrap/file/logo.png" style="width: 60px; height: 20px;float : left;margin-right: 5px;" />
						SmartWeb</a>
					</div>
					<div class="nav navbar-nav navbar-lift"
						id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav navbar-right">
							<li id="1" class="active"><a href="javascript:void(0);"
								onclick="resizeCanvas('bigpc')" > <img alt="big"
									src="./file/kf001.png"
									data-toggle="tooltip" data-placement="right" title="台式"
									/>
							</a></li>
							<li id="2"><a href="javascript:void(0);"
								onclick="resizeCanvas('pc')"> <img alt="pc"
									src="./file/kf002.png"
									data-toggle="tooltip" data-placement="right" title="笔记本"
									/>
							</a></li>
							<li id="3"><a href="javascript:void(0);"
								onclick="resizeCanvas('pad')"> <img alt="pad"
									src="./file/kf003.png"
									data-toggle="tooltip" data-placement="right" title="平板"
									 />
							</a></li>
							<li id="4"><a href="javascript:void(0);"
								onclick="resizeCanvas('phone')"> <img alt="phone"
									src="./file/kf004.png"
									data-toggle="tooltip" data-placement="right" title="手机"
									 />
							</a></li>

						</ul>
					</div>
					<div class="collapse navbar-collapse"
						id="bs-example-navbar-collapse-2">
						<ul class="nav navbar-nav navbar-right" style="padding-left: 50px;padding-right: 5px;">
							<li id="帮助"><a href="<%=basePath %>/b_ide/help/jsp/all.jsp" target="_blank"> <img alt="phone" src="./file/help.png"	style="width: 30px; height: 30px;" /></a>
							</li>

						</ul>
						<ul class="nav navbar-nav navbar-right">
							<li id="5" class="active"><a href="javascript:void(0);"
								onclick="resizeCanvas('edit')">编辑</a></li>
							<li id="6"><a href="javascript:void(0);"
								onclick="resizeCanvas('devpreview')">开发</a></li>
							<li id="7" ><a href="javascript:void(0);"
								onclick="resizeCanvas('JSEditer')">JS编辑</a></li>
							<li id="13" ><a href="javascript:void(0);"
								onclick="resizeCanvas('CSTEditer')">CST编辑</a></li>
							<li id="12"><a href="javascript:void(0);"
								onclick="resizeCanvas('browserPreview')">预览</a></li>

						</ul>
						<ul class="nav navbar-nav navbar-right" style="padding-right: 50px;" id="bs-example-navbar-collapse-3">
							<li id="8"><a href="javascript:void(0);"
								onclick="resizeCanvas('download')">下载</a></li>
							<li id="9"><a href="javascript:void(0);"
								onclick="resizeCanvas('save')">保存</a></li>
							<li id="10" class="active"><a href="javascript:void(0);"
								onclick="resizeCanvas('modal')">全屏</a></li>
							<li id="11"><a href="javascript:void(0);"
								onclick="resizeCanvas('clear')">清空</a></li>
						</ul>
					</div>

				</nav>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var messenger;
		$(function() {
			messenger = new Messenger('model', 'faceui');
			messenger.listen(function(msg) {
				alert("收到消息: " + msg);
			});

			messenger.addTarget(window.parent, 'Parent');

		});
		function resizeCanvas(name) {
			if (name == 'bigpc') {
				$("#1").attr("class", "active");
				$("#2").attr("class", "");
				$("#3").attr("class", "");
				$("#4").attr("class", "");
				messenger.targets['Parent'].send(name);
			} else if (name == 'pc') {
				$("#1").attr("class", "");
				$("#2").attr("class", "active");
				$("#3").attr("class", "");
				$("#4").attr("class", "");
				messenger.targets['Parent'].send(name);
			} else if (name == 'pad') {
				$("#1").attr("class", "");
				$("#2").attr("class", "");
				$("#3").attr("class", "active");
				$("#4").attr("class", "");
				messenger.targets['Parent'].send(name);
			} else if (name == 'phone') {
				$("#1").attr("class", "");
				$("#2").attr("class", "");
				$("#3").attr("class", "");
				$("#4").attr("class", "active");
				messenger.targets['Parent'].send(name);
			} else if (name == 'edit') {
				$("#5").attr("class", "active");
				$("#6").attr("class", "");
				$("#7").attr("class", "");
				$("#12").attr("class", "");
				$("#13").attr("class", "");
				messenger.targets['Parent'].send(name);
			} else if (name == 'devpreview') {
				$("#5").attr("class", "");
				$("#6").attr("class", "active");
				$("#7").attr("class", "");
				$("#12").attr("class", "");
				$("#13").attr("class", "");
				messenger.targets['Parent'].send(name);
			} else if (name == 'JSEditer') {
				$("#5").attr("class", "");
				$("#6").attr("class", "");
				$("#7").attr("class", "active");
				$("#12").attr("class", "");
				$("#13").attr("class", "");
				messenger.targets['Parent'].send(name);
			} else if (name == 'CSTEditer') {
				$("#5").attr("class", "");
				$("#6").attr("class", "");
				$("#7").attr("class", "");
				$("#12").attr("class", "");
				$("#13").attr("class", "active");
				messenger.targets['Parent'].send(name);
			}  else if (name == 'browserPreview') {
				$("#5").attr("class", "");
				$("#6").attr("class", "");
				$("#7").attr("class", "");
				$("#12").attr("class", "active");
				$("#13").attr("class", "");
				messenger.targets['Parent'].send(name);
			} else if (name == 'download') {
				$("#8").attr("class", "active");
				$("#9").attr("class", "");
				$("#10").attr("class", "");
				$("#11").attr("class", "");
				messenger.targets['Parent'].send(name);
			} else if (name == 'save') {
				$("#8").attr("class", "");
				$("#9").attr("class", "active");
				$("#10").attr("class", "");
				$("#11").attr("class", "");
				messenger.targets['Parent'].send(name);
			} else if (name == 'modal') {
				$("#8").attr("class", "");
				$("#9").attr("class", "");
				$("#10").attr("class", "active");
				$("#11").attr("class", "");
				messenger.targets['Parent'].send(name);
			} else if (name == 'clear') {
				$("#8").attr("class", "");
				$("#9").attr("class", "");
				$("#10").attr("class", "");
				$("#11").attr("class", "active");
				messenger.targets['Parent'].send(name);
			}else if (name == 'IE') {
				/* new ActiveXObject("Wscript.Shell").run("D:\\Browser\\Mozilla Firefox\\firefox.exe"); */
				alert(name);
			}else if (name == 'Opera') {
				alert(name);
			}else if (name == 'Chrome') {
				alert(name);
			}else if (name == 'Firefox') {
				alert(name);
			}else if (name == 'Safari') {
				alert(name);
			}
		}
		 $(function () { $("[data-toggle='tooltip']").tooltip(); });
	</script>
</body>