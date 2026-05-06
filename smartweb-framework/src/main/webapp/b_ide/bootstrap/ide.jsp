<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  <!-- JSTL标签库  -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="utf-8">
	<title>SmartWeb可视化布局系统</title>
	<meta name="keywords" content="SmartWeb,SmartWeb,爱SmartWeb,可视化,操作,布局">
	<meta name="description" content="LayoutIt! 可拖放排序在线编辑的SmartWeb可视化布局系统">

	<!-- jquery  -->
	<script type="text/javascript" src="<%=basePath%>/b_base/jquery-3.6.1.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>/b_base/jquery-migrate-3.4.0.min.js" charset="utf-8"></script>
	<!-- jquery  -->
	
	<!-- BOOTSTRAP -->
	<script type="text/javascript" src="<%=basePath%>/b_base/bootstrap-3.4.1/js/bootstrap.js" charset="utf-8"></script>
	<!-- /BOOTSTRAP -->

	<!-- Multiselect -->
	<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/css/bootstrap-multiselect.css"/>
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js"></script>
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/jquery.multiselectcus.js"></script>
	<!-- /Multiselect -->
	
	<!-- bootstrap Css-->
	<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.min.rainbow.css"/>
	<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap-theme.min.css"/>
	
	<!-- FileInput Css-->
	<link href="<%=basePath%>/b_ide/bootstrap/module/FileInput/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
	<script src="<%=basePath%>/b_ide/bootstrap/module/Chart/Chart.js"></script>
    <script src="<%=basePath%>/b_ide/bootstrap/module/Chart/src/Chart.Doughnut.js"></script>
    <script src="<%=basePath%>/b_ide/bootstrap/module/Treeview/js/bootstrap-treeview.js"></script>
	<script src="<%=basePath%>/b_ide/bootstrap/module/FileInput/js/fileinput.js" type="text/javascript"></script>
    <script src="<%=basePath%>/b_ide/bootstrap/module/FileInput/js/locales/zh.js" type="text/javascript"></script>
	<!-- FileInput Js-->

	<!-- DatetimePicker -->
	<script src="<%=basePath%>/b_base/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<!-- /DatetimePicker -->

	<!-- Bootstrap datetime -->
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<link href="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen"/>

	<!-- layoutit -->
	<link rel="stylesheet" href="./css/layoutit.css" >
	<link rel="stylesheet" href="./css/font-awesome.min.css">
	<script type="text/javascript" src="./js/jquery.htmlClean.js"></script>
	<script type="text/javascript" src="./js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="./js/jquery.ui.touch-punch.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/scripts.js"></script>
	<!-- messenger -->
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/messenger.js" charset="utf-8"></script>
	<!-- meshandler -->
	<script type="text/javascript" src="./js/meshandler.js"></script>
	<!-- module - ckeditor -->
	<script type="text/javascript" src="./module/ckeditor/ckeditor.js"></script>
	
	<!-- js Format-->
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/jsformat/jsformat.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/jsformat/htmlformat.js" charset="utf-8"></script>
	<!-- /Format-->
	
	<!-- table-->
	<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.css"/>
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/bootstrap-table-export.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/tableExport.js" charset="utf-8"></script>
	<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/locale/bootstrap-table-zh-CN.js"/></script>
	<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/tableExport.js"/></script>
	<!-- /table-->
	
	<!-- TABS -->
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/tabs/js/bootstrap-tabs.js"></script>
	<!-- /TABS -->
	
	<!-- ystep -->
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/ystep-master/js/ystep.js"></script>
	<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/ystep-master/css/ystep.css"/>
	<!-- /ystep -->

	<!-- JBox -->
	<link href="<%=basePath%>/b_base/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
	<script src="<%=basePath%>/b_base/jquery-jbox/2.3/jquery.jBox-2.3.src.js" type="text/javascript"></script>

	<!-- UEditor -->
	<script src="<%=basePath%>/b_base/ueditor/ueditor.config.js" type="text/javascript"></script>
	<script src="<%=basePath%>/b_base/ueditor/ueditor.all.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>/b_base/ueditor/ueditor.parse.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>/b_base/ueditor/lang/zh-cn/zh-cn.js" type="text/javascript"></script>

	<link href="<%=basePath%>/b_ide/bootstrap/css/bootstrap-selfStyle.css" rel="stylesheet" type="text/css" />
	<style type="">
		body {
		    background-image: url("<%=basePath %>/b_base/jquery-easyui-1.4.2/themes/frame/images/60.png");
		}
	</style>
</head>

<body class="edit" style="min-height: 798px;">
<div class="navbar navbar-inverse navbar-fixed-top navbar-layoutit" style="display: none"><!--  -->
		<div class="navbar-header">
			<button data-target="navbar-collapse" data-toggle="collapse" class="navbar-toggle" type="button">
				<span class="glyphicon-bar"></span>
				<span class="glyphicon-bar"></span>
				<span class="glyphicon-bar"></span>
			</button>
		</div>
		<div class="collapse navbar-collapse">

			<ul class="nav pull-right">
				<li>

					<div class="btn-group btn-donate pull-right"></div>
 
					<div class="btn-group" data-toggle="buttons-radio">
						<!--
						<button role="button" data-toggle="modal" data-target="#feedbackModal" id="feedback" class="btn btn-xs btn-primary"> <i class="glyphicon-comment glyphicon"></i>
							联系我们
						</button> -->
					</div>
				</li>
			</ul>
			<ul class="nav" id="menu-layoutit">
				<li>
					<div class="btn-group" data-toggle="buttons-radio" >
						<button type="button" class="active btn btn-xs btn-primary" id="edit"><i class="glyphicon glyphicon-edit "></i>
							编辑
						</button>
						<button type="button" class="active btn btn-xs btn-primary" id="devpreview">
							<i class="glyphicon-eye-close glyphicon"></i>
							开发
						</button>
						<button type="button" class="active btn btn-xs btn-primary" id="sourcepreview">
							<i class="glyphicon-eye-open glyphicon"></i>
							预览
						</button>
						<button type="button" class="active btn btn-xs btn-primary" id="browserPreview">
							<i class="glyphicon-eye-open glyphicon"></i>
							浏览器预览
						</button>
						
					</div>
					<div class="btn-group" >
						<button type="button" class="active btn btn-xs btn-primary" id="button-download-modal" data-target="#downloadModal" role="button" data-toggle="modal"> <i class="glyphicon-chevron-down glyphicon"></i>
							下载
						</button>
						
						<button type="button" class="active btn btn-xs btn-primary" id="button-download-modal" data-target="#saveModal" role="button" data-toggle="modal"> <i class="glyphicon-chevron-down glyphicon"></i>
							保存
						</button>
						
						<button type="button" class="active btn btn-xs btn-primary" id="button-download-modal"  role="button" data-toggle="modal" onclick="sendMessage('parent');">
							全屏
						</button>
						 <!-- <button class="active btn btn-xs btn-primary"  href="/share/indexV3" role="button" data-toggle="modal" data-target="#shareModal" onclick="aa()"> <i class="glyphicon-share glyphicon"></i>
						分享
					</button> -->
					
						<button class="btn btn-xs btn-primary" href="#clear" id="clear">
							<i class="glyphicon-trash glyphicon"></i>
							清空
						</button>
					</div>
					<div class="btn-group">
						<a href="javascript:void(0);">
						</a>
					</div>
			</li>
		</ul>
				
	</div>
	 <!-- .navbar-collapse -->
</div> 
<!--/.navbar-fixed-top -->

<div class="container">
	<div class="row">
		<div class="">
			<div class="sidebar-nav">
				<ul class="nav nav-list accordion-group">
					<li class="nav-header">
						<div class="pull-right popover-info">
							<i class="glyphicon glyphicon-question-sign"></i>
							<div class="popover fade right">
								<div class="arrow"></div>
								<h3 class="popover-title">帮助</h3>
								<div class="popover-content">
									在这里设置你的栅格布局, 栅格总数默认为12, 用空格分割每列的栅格值.
								</div>
							</div>
						</div>
						<i class="glyphicon-plus glyphicon"></i>
						布局设置
					</li>
					<li class="rows" id="estRows">

						<div class="lyrow ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon-remove glyphicon"></i>
								布局
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<div class="preview">
								<input type="text" value="12" class="form-control"></div>
							<div class="view">
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-12 column"></div>
								</div>
							</div>
						</div>

						<div class="lyrow ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon-remove glyphicon"></i>
								布局
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<div class="preview">
								<input type="text" value="6 6" class="form-control"></div>
							<div class="view">
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-6 column"></div>
									<div class="col-md-6 column"></div>
								</div>
							</div>
						</div>

						<div class="lyrow ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon-remove glyphicon"></i>
								布局
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<div class="preview">
								<input type="text" value="8 4" class="form-control"></div>
							<div class="view">
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-8 column"></div>
									<div class="col-md-4 column"></div>
								</div>
							</div>
						</div>

						<div class="lyrow ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon-remove glyphicon"></i>
								布局
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<div class="preview">
								<input type="text" value="4 4 4" class="form-control"></div>
							<div class="view">
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-4 column"></div>
									<div class="col-md-4 column"></div>
									<div class="col-md-4 column"></div>
								</div>
							</div>
						</div>

						<div class="lyrow ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon-remove glyphicon"></i>
								布局
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<div class="preview">
								<input type="text" value="2 6 4" class="form-control"></div>
							<div class="view">
								<div ravo="rainbow_fx_layout" class="row clearfix">
									<div class="col-md-2 column"></div>
									<div class="col-md-6 column"></div>
									<div class="col-md-4 column"></div>
								</div>
							</div>
						</div>

					</li>
				</ul>

				<ul class="nav nav-list accordion-group">
					<li class="nav-header">
						<i class="glyphicon glyphicon-plus"></i>
						基本组件
						<div class="pull-right popover-info">
							<i class="glyphicon glyphicon-question-sign "></i>
							<div class="popover fade right">
								<div class="arrow"></div>
								<h3 class="popover-title">帮助</h3>
								<div class="popover-content">
									将组件元素拖放入你需要放入的栅格列中。之后，你可以设置该元素的样式。
								</div>
							</div>
						</div>
					</li>
					<li class="boxes" id="elmBase">
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">标题栏</div>
							<div class="view">
								<div ravo="rainbow_fx_bj">
									<h3  contenteditable="false">h3. 这是一套可视化布局系统.</h3>
								</div>
							</div>
						</div>
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">段落</div>
							<div class="view">
								<div ravo="rainbow_fx_bj">
									<p> <em>Git</em>
										是一个分布式的版本控制系统，最初由 <strong>Linus Torvalds</strong>
										编写，用作Linux内核代码的管理。在推出后，Git在其它项目中也取得了很大成功，尤其是在
										<small>Ruby</small>
										社区中。
									</p>
								</div>
							</div>
						</div>
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">地址</div>
							<div class="view">
								<address ravo="rainbow_fx_bj" contenteditable="true"> <strong>Twitter, Inc.</strong>
									<br>
									795 Folsom Ave, Suite 600
									<br>
									San Francisco, CA 94107
									<br> <abbr title="Phone">P:</abbr>
									(123) 456-7890
								</address>
							</div>
						</div>
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">引用块</div>
							<div class="view clearfix">
								<blockquote ravo="rainbow_fx_bj" contenteditable="true">
									<p>
										Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.
									</p>
								</blockquote>
							</div>
						</div>
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">无序列表</div>
							<div class="view">
								<ul ravo="rainbow_fx_bj" contenteditable="true">
									<li>Lorem ipsum dolor sit amet</li>
									<li>Consectetur adipiscing elit</li>
									<li>Integer molestie lorem at massa</li>
									<li>Facilisis in pretium nisl aliquet</li>
									<li>Nulla volutpat aliquam velit</li>
									<li>Faucibus porta lacus fringilla vel</li>
									<li>Aenean sit amet erat nunc</li>
									<li>Eget porttitor lorem</li>
								</ul>
							</div>
						</div>
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">有序列表</div>
							<div class="view">
								<ol ravo="rainbow_fx_bj" contenteditable="true">
									<li>Lorem ipsum dolor sit amet</li>
									<li>Consectetur adipiscing elit</li>
									<li>Integer molestie lorem at massa</li>
									<li>Facilisis in pretium nisl aliquet</li>
									<li>Nulla volutpat aliquam velit</li>
									<li>Faucibus porta lacus fringilla vel</li>
									<li>Aenean sit amet erat nunc</li>
									<li>Eget porttitor lorem</li>
								</ol>
							</div>
						</div>
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">详细描述</div>
							<div class="view">
								<dl ravo="rainbow_fx_bj" contenteditable="true">
									<dt>Description lists</dt>
									<dd>A description list is perfect for defining terms.</dd>
									<dt>Euismod</dt>
									<dd>
										Vestibulum id ligula porta felis euismod semper eget lacinia odio sem nec elit.
									</dd>
									<dd>Donec id elit non mi porta gravida at eget metus.</dd>
									<dt>Malesuada porta</dt>
									<dd>Etiam porta sem malesuada magna mollis euismod.</dd>
									<dt>Felis euismod semper eget lacinia</dt>
									<dd>
										Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.
									</dd>
								</dl>
							</div>
						</div>
						<div class="box box-element ui-draggable">
							<a href="#close" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default"  role="button"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">表格</div>
							<div class="view" id="biaoge">
								<table id="table" 
									   data-toggle="table"
									   data-first-load="true"
								       data-url="data1.json"
								       data-click-to-select="true"
								       data-show-export="true"
						               data-show-refresh="true"
							           data-show-toggle="true"
							           data-show-columns="true"
							           data-show-export="true"
								       data-pagination="true"
								       data-search="true"
								       data-query-params="queryParams"
								       data-method="post"
								       data-undefined-text="**"
								       data-height="300"
								       data-content-type="application/x-www-form-urlencoded"
								       ravo="rainbow_fx_bj" >
								    <thead>
									    <tr>
									        <th data-field="state" data-checkbox="true"></th>
									        <th data-field="id">ID</th>
									        <th data-field="invited_name">受邀人名称</th>
									        <th data-field="invited_phone">受邀人手机</th>
									        <th data-field="invited_email">受邀人邮箱</th>
									        <th data-field="action" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
									    </tr>
								    </thead>
								</table>
							</div>
						</div>
						
						<!-- 标签 -->
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">标签</div>
							<div class="view">
								<span  ravo="rainbow_fx_bj" class="label label-default" contenteditable="true">标签</span>
							</div>
						</div>
						<!-- /标签  -->
						
						<!-- a标签 -->
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<div class="preview">a标签</div>
							<div class="view">
								<a ravo="rainbow_fx" href="javascript:void(0);">a标签</a>
							</div>
						</div>
						<!-- /标签  -->						
						
						
						<!--  按钮     -->
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<div class="preview">按钮</div>
							<div class="view" id="button">
								<shiro:haspermission name="anno">
								<button ravo="rainbow_fx"  type="button"  class="btn btn-default"  contenteditable="true">
								按钮
								</button>
								</shiro:haspermission>
							</div>
						</div>
						<!--  按钮   END  -->
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<div class="preview">图片</div>
							<div class="view">
								<img ravo="rainbow_fx" alt="140x140" src="./file/default.jpg">
							</div>
						</div>
					</li>
				</ul>
				<ul class="nav nav-list accordion-group">
					<li class="nav-header">
						<i class="glyphicon glyphicon-plus"></i>
						工具组件
						<div class="pull-right popover-info">
							<i class="glyphicon glyphicon-question-sign "></i>
							<div class="popover fade right">
								<div class="arrow"></div>
								<h3 class="popover-title">帮助</h3>
								<div class="popover-content">
									将组件元素拖放入你需要放入的栅格列中。之后，你可以设置该组件的样式。
								</div>
							</div>
						</div>
					</li>
					<li class="boxes" id="elmComponents">
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<div class="preview">按钮组</div>
							<div class="view" id="btn_group">
								<div ravo="rainbow_fx_anz" class="btn-group">
									<button class="btn btn-default" type="button">
										<i class="glyphicon glyphicon-align-left"></i>
										左
									</button>
									<button class="btn btn-default" type="button">
										<i class="glyphicon glyphicon-align-center"></i>
										中
									</button>
									<button class="btn btn-default" type="button">
										<i class="glyphicon glyphicon-align-right"></i>
										右
									</button>
									<button class="btn btn-default" type="button">
										<i class="glyphicon glyphicon-align-justify"></i>
										全
									</button>
								</div>
							</div>
						</div>

						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<div class="preview">按钮工具栏</div>
							<div  class="view" id="btn_toolbar">
								<div ravo="rainbow_fx" class="btn-toolbar">
								  <div class="btn-group">
									<button class="btn btn-default" type="button">1</button>
									<button class="btn btn-default" type="button">2</button>
									<button class="btn btn-default" type="button">3</button>
								  </div>
								  <div class="btn-group">
									<button class="btn btn-default" type="button">4</button>
									<button class="btn btn-default" type="button">5</button>
								  </div>
								  <div class="btn-group">
									<button class="btn btn-default" type="button">6</button>		
								  </div>
								</div>
							</div>
						</div>
						
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<div class="preview">下拉菜单</div>
							<div class="view" id="down_menu">
								<div ravo="rainbow_fx_xlcd" class="btn-group">
									<button class="btn btn-default">帮助</button>
									<button data-toggle="dropdown" class="btn btn-default dropdown-toggle">
										<span class="caret"></span>
									</button>
									<ul class="dropdown-menu" >
										<li>
											<a href="javascript:void(0);">操作</a>
										</li>
										<li class="disabled">
											<a href="javascript:void(0);">另一操作</a>
										</li>
										<li class="divider"></li>
										<li>
											<a href="javascript:void(0);">其它</a>
										</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<div class="preview">导航</div>
							<div class="view" id="navigations">
								<ul ravo="rainbow_fx_bj" class="nav nav-tabs" contenteditable="true">
									<li class="active">
										<a href="javascript:void(0);">首页</a>
									</li>
									<li>
										<a href="javascript:void(0);">简介</a>
									</li>
									<li class="disabled">
										<a href="javascript:void(0);">信息</a>
									</li>
								</ul>
							</div>
						</div>
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<div class="preview">路径导航</div>
							<div class="view" id="path_navigation">
								<ul ravo="rainbow_fx_bj" class="breadcrumb">
									<li>
										<a href="javascript:void(0);" contenteditable="true">Home</a>
									</li>
									<li>
										<a href="javascript:void(0);" contenteditable="true">Library</a>
									</li>
									<li class="active" contenteditable="true">Data</li>
								</ul>

							</div>
						</div>
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<div class="preview">分页</div>
							<div class="view" id="paging">
								<ul ravo="rainbow_fx_bj" class="pagination" contenteditable="true">
									<li>
										<a href="javascript:void(0);">Prev</a>
									</li>
									<li>
										<a href="javascript:void(0);">1</a>
									</li>
									<li>
										<a href="javascript:void(0);">2</a>
									</li>
									<li>
										<a href="javascript:void(0);">3</a>
									</li>
									<li>
										<a href="javascript:void(0);">4</a>
									</li>
									<li>
										<a href="javascript:void(0);">5</a>
									</li>
									<li>
										<a href="javascript:void(0);">Next</a>
									</li>
								</ul>
							</div>
						</div>
						
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">翻页</div>
							<div class="view" id="turnPage">
								<nav ravo="rainbow_fx_bj">
								  <ul class="pager">
								    <li><a href="#">Previous</a></li>
								    <li><a href="#">Next</a></li>
								  </ul>
								</nav>
							</div>
						</div>						
						
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">巨幕</div>
							<div class="view" id="jumbotron">
								<div ravo="rainbow_fx_bj" class="jumbotron">
									<h1>Hello, world!</h1>
									<p>
										This is a template for a simple marketing or informational website. It includes a large callout called the hero unit and three supporting pieces of content. Use it as a starting point to create something more unique.
									</p>
									<p>
										<a class="btn btn-primary btn-large" href="javascript:void(0);">Learn more</a>
									</p>
								</div>
							</div>
						</div>
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">页头</div>
							<div class="view">
								<div ravo="rainbow_fx_bj" class="page-header">
									<h1 contenteditable="true">
										Example page header
										<small>Subtext for header</small>
									</h1>
								</div>
							</div>
						</div>
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">文本</div>
							<div class="view">
								<h2 contenteditable="true">Heading</h2>
								<p contenteditable="true">
									Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui.
								</p>
								<p>
									<a class="btn" href="javascript:void(0);" contenteditable="true">View details »</a>
								</p>
							</div>
						</div>
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default "data-toggle="modal" data-target="#editorModal">编辑</button>
							</span>
							<div class="preview">缩略图</div>
							<div class="view" id="thumbnail">
							  <div ravo="rainbow_fx_bj_slt">
								<div  class="row">
									<div class="col-md-4">
										<div class="thumbnail">
											<img alt="300x200" src="./file/default(11).jpg">
											<div class="caption" >
												<h3>美景</h3>
												<p>
													西江河畔!
												</p>
												<p>
													<a class="btn btn-primary" href="javascript:void(0);">Action</a>
													<a class="btn" href="javascript:void(0);">Action</a>
												</p>
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="thumbnail">
											<img alt="300x200" src="./file/default(12).jpg">
											<div class="caption">
												<h3>玩泥沙</h3>
												<p>
													波海公园游玩!
												</p>
												<p>
													<a class="btn btn-primary" href="javascript:void(0);">Action</a>
													<a class="btn" href="javascript:void(0);">Action</a>
												</p>
											</div>
										</div>
									</div>
									<div class="col-md-4">
										<div class="thumbnail">
											<img alt="300x200" src="./file/default(10).jpg">
											<div class="caption">
												<h3>快乐</h3>
												<p>
													波海公园游玩!
												</p>
												<p>
													<a class="btn btn-primary" href="javascript:void(0);">Action</a>
													<a class="btn" href="javascript:void(0);">Action</a>
												</p>
											</div>
										</div>
									</div>
								</div>
							</div>
						  </div>
						</div>
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
						<div class="preview">进度条</div>
							<div class="view" id="progressbar">
								<div ravo="rainbow_fx" class="progress">
									 <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
									    <span class="sr-only">60%</span>
									 </div>
								</div>
							</div>
						</div>
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">媒体</div>
							<div class="view" id="media">
							 <div ravo="rainbow_fx_bj_media">
								<div  class="media">
								    <div class="media-left">
										<a href="javascript:void(0);" class="pull-left">
											<img src="./file/default(4).jpg" class="media-object">
										</a>
									</div>
									<div class="media-body" contenteditable="true">
										<h4 class="media-heading">Nested media heading</h4>
										Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.
										<div class="media">
										    <div class="media-left">
												<a href="javascript:void(0);" class="pull-left">
													<img src="./file/default(4).jpg" class="media-object">
												</a>
											</div>
											<div class="media-body" contenteditable="true">
												<h4 class="media-heading">Nested media heading</h4>
												Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.
											</div>
										</div>
									</div>
								  </div>
							  </div>
							</div>
						</div>
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" data-toggle="modal" data-target="#editorModal" href="javascript:void(0);">编辑</button>
							</span>
							<div class="preview">列表组</div>
							<div class="view" id="listGroup">
								<div ravo="rainbow_fx_bj_listGroup" class="list-group">
								  <a href="javascript:void(0);" class="list-group-item">Cras justo odio</a>
								  <a href="javascript:void(0);" class="list-group-item">Dapibus ac facilisis in</a>
								  <a href="javascript:void(0);" class="list-group-item">Morbi leo risus</a>
								  <a href="javascript:void(0);" class="list-group-item">Porta ac consectetur ac</a>
								  <a href="javascript:void(0);" class="list-group-item">Vestibulum at eros</a>
								</div>
							</div>
						</div>
						<div class="lyrow ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								面板
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							
							<div class="preview">面板</div>
							<div class="view" id="panel">
								<div ravo="rainbow_fx_layout_panel" class="panel panel-default">
									<div class="panel-heading">
										<div ravo="rainbow_fx_remove" class="row clearfix">
											<div class="col-md-12 column">
											</div>
										</div>
									</div>
									<div class="panel-body" contenteditable="true">
										<div ravo="rainbow_fx_remove" class="row clearfix">
											<div class="col-md-12 column">
											</div>
										</div>
									</div>
									<div class="panel-footer" contenteditable="true">
										<div ravo="rainbow_fx_remove" class="row clearfix">
											<div class="col-md-12 column">
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</li>
				</ul>
				<ul class="nav nav-list accordion-group">
					<li class="nav-header">
						<i class="glyphicon glyphicon-plus"></i>
						统计图表
						<div class="pull-right popover-info">
							<i class="glyphicon glyphicon-question-sign "></i>
							<div class="popover fade right">
								<div class="arrow"></div>
								<h3 class="popover-title">帮助</h3>
								<div class="popover-content">
									将组件元素拖放入你需要放入的栅格列中。之后，你可以设置该组件的样式。
								</div>
							</div>
						</div>
					</li>
					<li class="boxes" id="elmComponents">
					<!-- 饼状图 -->
					<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">饼状图</div>
							<div class="view" id="canvas_bing">
								<div ravo="rainbow_fx" class="form-group">
	                               <canvas style="margin-left: auto;margin-right: auto;" action="" beg="" end=""></canvas>
								</div>
							</div>
						</div>	
					<!-- 环状图 -->
					    <div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">环状图</div>
							<div class="view" id="canvas_huan">
								<div ravo="rainbow_fx" class="form-group">
	                                <canvas style="margin-left: auto;margin-right: auto;" action="" beg="" end=""></canvas>
								</div>
							</div>
						</div>	
						<!-- 地极图 -->
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">地极图</div>
							<div class="view" id="canvas_diji">
								<div ravo="rainbow_fx" class="form-group">
								<canvas style="margin-left: auto;margin-right: auto;" action="" beg="" end=""></canvas>
								</div>
							</div>
						</div>	
						<!-- 曲线图 -->
						 <div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">曲线图</div>
							<div class="view" id="canvas_quxian">
								<div ravo="rainbow_fx" class="form-group">
									<canvas style="margin-left: auto;margin-right: auto;" action="" beg="" end=""></canvas>
								</div>
							</div>
						</div>	
						<!-- 雷达图 -->
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">雷达图</div>
							<div class="view" id="canvas_leida">
								<div ravo="rainbow_fx" class="form-group">
									<canvas style="margin-left: auto;margin-right: auto;" action="" beg="" end=""></canvas>
								</div>
							</div>
						</div>	
						<!-- 柱状图 -->
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
							</span>
							<div class="preview">柱状图</div>
							<div class="view" id="canvas_zhu">
								<div ravo="rainbow_fx" class="form-group">
	                              <canvas style="margin-left: auto;margin-right: auto;" action="" beg="" end=""></canvas>
								</div>
							</div>
						</div>	
					</li>
					</ul>
				<ul class="nav nav-list accordion-group">
					<li class="nav-header">
						<i class="glyphicon glyphicon-plus"></i>
						JavaScript
						<div class="pull-right popover-info">
							<i class="glyphicon glyphicon-question-sign "></i>
							<div class="popover fade right">
								<div class="arrow"></div>
								<h3 class="popover-title">帮助</h3>
								<div class="popover-content">
									将组件元素拖放入你需要放入的栅格列中。之后，你可以设置该javascript组件的样式。
								</div>
							</div>
						</div>
					</li>
					<li class="boxes mute" id="elmJS">
					
						<div class="lyrow ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon-remove glyphicon"></i>
								模态
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<div class="preview">模态框</div>
							<div class="view" id="modal">
								<div ravo="rainbow_fx_layout_modal" class="row clearfix" modal-header-show="true" modal-header-close="true" modal-header-title="hello" modal-id="modalId_001" modal-width="">
									<div class="col-md-12 column"></div>
								</div>
							</div>
						</div>
					
						<div class="box box-element ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<span class="configuration">
								<button class="btn btn-xs btn-default" href="javascript:void(0);"  data-target="#editorModal" data-toggle="modal">编辑</button>
								<!-- <a class="btn btn-xs btn-default" href="javascript:void(0);" rel="navbar-inverse">反转</a> -->
								<!--a class="btn btn-xs btn-default" href="#" rel="navbar-static-top">Static top</a>
							<a class="btn btn-mini" href="#" rel="navbar-fixed-top">Navbar fixed top</a>
							<a class="btn btn-mini" href="#" rel="navbar-fixed-bottom">Navbar fixed bottom</a-->
						</span>
						<div class="preview">导航栏</div>
						<div class="view" id="navbar">

							<nav ravo="rainbow_fx_bj" class="navbar navbar-default" role="navigation">
								<!-- Brand and toggle get grouped for better mobile display -->
								<div class="navbar-header">
									<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
										<span class="sr-only">Toggle navigation</span>
										<span class="icon-bar"></span>
										<span class="icon-bar"></span>
										<span class="icon-bar"></span>
									</button>
									<a class="navbar-brand" href="javascript:void(0);">Brand</a>
								</div>

								<!-- Collect the nav links, forms, and other content for toggling -->
								<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
									<ul class="nav navbar-nav">
										<li class="active">
											<a href="javascript:void(0);">Link</a>
										</li>
										<li>
											<a href="javascript:void(0);">Link</a>
										</li>
										<li class="dropdown">
											<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">
												Dropdown <b class="caret"></b>
											</a>
											<ul class="dropdown-menu">
												<li>
													<a href="javascript:void(0);">Action</a>
												</li>
												<li>
													<a href="javascript:void(0);">Another action</a>
												</li>
												<li>
													<a href="javascript:void(0);">Something else here</a>
												</li>
												<li class="divider"></li>
												<li>
													<a href="javascript:void(0);">Separated link</a>
												</li>
												<li class="divider"></li>
												<li>
													<a href="javascript:void(0);">One more separated link</a>
												</li>
											</ul>
										</li>
									</ul>
									
									<ul class="nav navbar-nav navbar-right">
										<li>
											<a href="javascript:void(0);">Link</a>
										</li>
										<li class="dropdown">
											<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">
												Dropdown
												<b class="caret"></b>
											</a>
											<ul class="dropdown-menu">
												<li>
													<a href="javascript:void(0);">Action</a>
												</li>
												<li>
													<a href="javascript:void(0);">Another action</a>
												</li>
												<li>
													<a href="javascript:void(0);">Something else here</a>
												</li>
												<li class="divider"></li>
												<li>
													<a href="javascript:void(0);">Separated link</a>
												</li>
											</ul>
										</li>
									</ul>
								</div>
								<!-- /.navbar-collapse -->
							</nav>

						</div>
					</div>
					<div class="lyrow ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon glyphicon-remove"></i>
							删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<span class="configuration"></span>
						<div class="preview">选项卡</div>
						<div class="view" id = "tab">
							<div ravo="rainbow_fx_layout_tab" class="tabbable" id="myTabs">
								<!-- Only required for left/right tabs -->
								<ul class="nav nav-tabs" data-toggle="tabs">
									<li class="active">
										<a href="#tab1" data-toggle="tab" >Section 1</a>
									</li>
									<li>
										<a href="#tab2" data-toggle="tab" >Section 2</a>
									</li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="tab1">
										<div ravo="rainbow_fx_remove" class="row clearfix">
											<div class="col-md-12 column">
											</div>
										</div>
									</div>
									<div class="tab-pane" id="tab2">
										<div ravo="rainbow_fx_remove" class="row clearfix">
											<div class="col-md-12 column">
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="box box-element ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon glyphicon-remove"></i>
							删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<span class="configuration">
								<button class="btn btn-xs btn-default"  role="button"  data-target="#editorModal" data-toggle="modal">编辑</button>
						</span>
						<div class="preview" >提示框</div>
						<div class="view" id="hint">
							<div ravo="rainbow_fx_bj_hint" class="alert alert-success alert-dismissable" contenteditable="true">
								<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
								<h4>注意!</h4>
								<strong>Warning!</strong>
								Best check yo self, you're not looking too good.
								<a href="javascript:void(0);" class="alert-link">alert link</a>
							</div>
						</div>
					</div>
					<div class="lyrow ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon glyphicon-remove"></i>
							删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<div class="preview">手风琴切换</div>
						<div class="view" id="fold">
							<div ravo="rainbow_fx_layout_fold" class="panel-group" id="myAccordion">
							    <!-- begin -->
								<div class="panel panel-default">
									<div class="panel-heading">
										<a class="panel-title" data-toggle="collapse" data-parent="#myAccordion" href="#collapseOne" contenteditable="true">Collapsible Group Item #1</a>
									</div>
									<div id="collapseOne" class="panel-collapse collapse in">
										<div class="panel-body" contenteditable="true">
											<div ravo="rainbow_fx_remove" class="row clearfix">
												<div class="col-md-12 column">
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<a class="panel-title" data-toggle="collapse" data-parent="#myAccordion" href="#collapseTwo" contenteditable="true">Collapsible Group Item #2</a>
									</div>
									<div id="collapseTwo" class="panel-collapse collapse">
										<div class="panel-body" contenteditable="true">
										<div ravo="rainbow_fx_remove" class="row clearfix">
												<div class="col-md-12 column">
												</div>
											</div>
										</div>
									</div>
								</div>
						        <!-- end -->
							</div>
						</div>
					</div>
					<div class="box box-element ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon glyphicon-remove"></i>
							删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<div class="preview">幻灯片</div>
						<div class="view" id="slidel">
							<div ravo="rainbow_fx_slidel" class="carousel slide" id="myCarousel">
								<ol class="carousel-indicators">
									<li class="active" data-slide-to="0" data-target="#myCarousel"></li>
									<li data-slide-to="1" data-target="#myCarousel" class=""></li>
									<li data-slide-to="2" data-target="#myCarousel" class=""></li>
								</ol>
								<div class="carousel-inner">
									<div class="item active">
										<img alt="" src="./file/default(5).jpg">
										<div class="carousel-caption">
										  <span class="view">
											<h4>First Thumbnail label</h4>
											<p data-target="#editorModal" data-toggle="modal">
												Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
											</p>
										  </span>
										</div>
									</div>
									<div class="item">
										<img alt="" src="./file/default(6).jpg">
										<div class="carousel-caption">
										    <span class="view">
											<h4>Second Thumbnail label</h4>
											<p data-target="#editorModal" data-toggle="modal">
												Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
											</p>
											</span>
										</div>
									</div>
									<div class="item">
										<img alt="" src="./file/default(7).jpg">
										<div class="carousel-caption">
										 <span class="view">
											<h4>Third Thumbnail label</h4>
											<p data-target="#editorModal" data-toggle="modal">
												Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
											</p>
											</span>
										</div>
									</div>
								</div>

								<a class="left carousel-control" href="javascript:void(0);myCarousel" data-slide="prev">
									<span class="glyphicon glyphicon-chevron-left"></span>
								</a>
								<a class="right carousel-control" href="javascript:void(0);myCarousel" data-slide="next">
									<span class="glyphicon glyphicon-chevron-right"></span>
								</a>
							</div>

						</div>
					</div>
				</li>
			</ul>
			
			<ul class="nav nav-list accordion-group">
				<li class="nav-header">
					<i class="glyphicon glyphicon-plus"></i>
					表单组件
					<div class="pull-right popover-info">
						<i class="glyphicon glyphicon-question-sign "></i>
						<div class="popover fade right">
							<div class="arrow"></div>
							<h3 class="popover-title">帮助</h3>
							<div class="popover-content">
								将组件元素拖放入你需要放入的栅格列中。之后，你可以设置该javascript组件的样式。
							</div>
						</div>
					</div>
				</li>
				<li class="boxes mute" id="elmJS">
					<!-- 表单 -->
					<div class="lyrow ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon-remove glyphicon"></i>
							表单
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<div class="preview">表单</div>
						<div class="view" id="form">
							<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="">
								<div ravo="rainbow_fx_remove" class="row clearfix">
									<div class="col-md-12 column">
									</div>
								</div>
							</form>
						</div>
					</div>
					<!-- /表单 -->
					<!-- 日期时间组件 -->
					<div class="box box-element ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon glyphicon-remove"></i>
							删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<div class="preview">日期时间组件</div>
						<div class="view" id="datetime">
							<div ravo="rainbow_fx" class="form-group">
                             	<label class="col-sm-4 control-label">日期时间</label>
								<div class='input-group-sm col-sm-8'>
									<input type="text" readonly="readonly" maxlength="20" class="form-control input-mini Wdate" value=""
										datetime-skin="twoer" 
										datetime-date-fmt="yyyy-MM-dd HH:mm:ss" 
										datetime-min-date="" 
										datetime-max-date="" 
										datetime-is-show-clear="true" 
										datetime-is-show-week="true" 
										datetime-is-show-today="true" 
										datetime-default-value=""
										datetime-value-fmt="yyyyMMddHHmmss" 
										data-link-field="val_input1" />
									<input type="hidden" id="val_input1" value="" />
								</div>
							</div>
						</div>
					</div>
				<!-- 日期时间组件 -->
					<!-- 搜索树组件-->
					<div class="box box-element ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon glyphicon-remove"></i>
							删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<div class="preview">搜索树组件</div>
						<div class="view" id="treesearch">
							<div ravo="rainbow_fx" class="form-group">
								<label class="col-sm-4 control-label">归属公司：</label>
								<div class="col-sm-8">
									<input input_type="sys:treeselect" readOnly="true" id="company" name="company.id" value="${user.company.id}" label_name="company.name" label_value="${user.company.name}" 
				title="公司" url="/sys/office/treeData?type=1" css_class="form-control input-small" allow_clear="true"/>
								</div>
							</div>
						</div>
					</div>
					<!-- /搜索树组件  -->	
					<!-- 图标选择组件 add by chenyl 20180612-->
					<div class="box box-element ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon glyphicon-remove"></i>
							删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<div class="preview">图标选择组件</div>
						<div class="view" id="iconselect">
							<div ravo="rainbow_fx" class="form-group">
								<label class="col-sm-2 control-label">图标:</label>
								<div class="col-sm-2">
									无&nbsp;<input type="button" value="选择" class="disabled" input_type="sys:iconselect" readOnly="true" icon_id="icon" icon_name="icon" icon_value="" icon_url="" icon_css="" icon_required="false" />
								</div>
							</div>
						</div>
					</div>
					<!-- /图标选择组件  -->
					<!-- 文件管理组件 add by chenyl 20180620-->
					<div class="box box-element ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon glyphicon-remove"></i>
							删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<div class="preview">文件管理组件</div>
						<div class="view" id="ckfinder">
							<div ravo="rainbow_fx" class="form-group">
								<label class="col-sm-2 control-label">文件管理:</label>
								<div class="col-sm-2">
									无&nbsp;<input type="button" value="文件管理" class="disabled" input_type="sys:ckfinder"
									 readOnly="true" ckfinder_id="nameImage" ckfinder_name="photo" ckfinder_value="" 
									 ckfinder_type="files" ckfinder_upload_path="/custom" ckfinder_year_path="false" ckfinder_month_path="false" ckfinder_is_all_user="false" 
									 ckfinder_select_multiple="false" ckfinder_readonly="false" ckfinder_max_width="200"
									 ckfinder_max_height="200" ckfinder_required="false" />
								</div>
							</div>
						</div>
					</div>
					<!-- /文件管理组件  -->	
					<!--  隐藏域    -->
					<div class="box box-element ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon glyphicon-remove"></i>
							删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<div class="preview">隐藏域</div>
						<div class="view" id="input_hidden">
							<input type="button" ravo="rainbow_fx" value="隐藏域" class="disabled" input_type="hidden" readOnly="true" hidden_id="hid" hidden_name="hid" hidden_value="" hidden_required="false" />
						</div>
					</div>
					<!--  隐藏域   END  -->
					<!-- 输入框 -->
					<div class="box box-element ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon glyphicon-remove"></i>
							删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<div class="preview">输入框</div>
						<div class="view">
							<div ravo="rainbow_fx" class="form-group">
								<label for="inputEmail3" class="col-sm-4 control-label">Rainbow：</label>
								<div class="col-sm-8">
									<input type="text"  class="form-control"  placeholder="Rainbow">
								</div>
							</div>
						</div>
					</div>
					<!-- /输入框  -->
					<!-- 文本域 -->
					<div class="box box-element ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon glyphicon-remove"></i>
							删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<!-- <span class="configuration">
							<a class="btn btn-xs btn-default" href="javascript:void(0);" rel="form-inline">嵌入</a>
						</span> -->
						<div class="preview">文本域</div>
						<div class="view">
								<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-4 control-label">Email:</label>
									<div class="col-sm-8">
										<textarea class="form-control" rows="3"></textarea>
									</div>
								</div>
						</div>
					</div>
					<!-- /文本域  -->
					
					<!-- 富文本编辑器UEditor add by chenyl 20180618 -->
					<div class="box box-element ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon glyphicon-remove"></i>
								删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
								拖动
						</span>
						<div class="preview">富文本编辑器</div>
						<div class="view" id="ueditor">
							<div ravo="rainbow_fx" class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label">UEditor:</label>
									<div class="col-sm-4">
										<img input_type="ueditor" ue_id="ueContainer" ue_name="ueContainer" ue_value="" ue_style="" alt="140x140" src="./file/ueditor.png" />
									</div>
							</div>
						</div>
					</div>
					<!-- /富文本编辑器UEditor -->
					
					<!-- 下拉列表  -->
					<div class="box box-element ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon glyphicon-remove"></i>
							删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<!--  
						<div class="preview">下拉列表</div>
						<div class="view" id="select">
					    	<div ravo="rainbow_fx" class="form-group">
							    <label for="inputEmail3" class="col-sm-4 control-label" >选项：</label>
								<select data-role="multiselect"  class="col-sm-8">
						        </select>
					    	</div>
						</div>
						-->
						<div class="preview">下拉列表</div>
						<div class="view" id="select">
					    	<div ravo="rainbow_fx" class="form-group">
							    <label for="inputEmail3" class="col-sm-4 control-label" >选项：</label>
							    <div class="col-sm-7">
									<select data-role="multiselect">
										<option value="1">
											option 1
										</option>
										<option value="2">
											option 2
										</option>
										<option value="3">
											option 3
										</option>
							        </select>
						        </div>
					    	</div>
						</div>
					</div>					
					<!-- \下拉列表  -->
					
					<!-- 多选框  -->
					<div class="box box-element ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon glyphicon-remove"></i>
							删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<div class="preview">多选框</div>
						<div class="view" id="checkbox">
							<div ravo="rainbow_fx_checkbox" class="form-group">
								<label for="inputEmail3" class="col-sm-4 control-label" >多选框：</label>
								<div class="col-sm-8">
									<div class="checkbox">
									  <label style="visibility:visible">
									    <input type="checkbox" value=""/>Option one
									  </label>
									</div>
									<div class="checkbox">
									  <label style="visibility:visible">
									    <input type="checkbox" value="">
									    Option two
									  </label>
									</div>
									<div class="checkbox">
									  <label style="visibility:visible">
									    <input type="checkbox" value="">
									    Option three
									  </label>
									</div>
									<div class="checkbox">
									  <label style="visibility:visible">
									    <input type="checkbox" value="">
									    Option four
									  </label>
									</div>
								</div>
							</div>
						</div>
					</div>	
					<!-- \多选框  -->
					
					<!-- 单选框  -->
					<div class="box box-element ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon glyphicon-remove"></i>
							删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<div class="preview">单选框</div>
						<div class="view" id="radio">
							<div ravo="rainbow_fx_radio" class="form-group">
								<label for="inputEmail3" class="col-sm-4 control-label">单选框：</label>
								<div class="col-sm-8">
									<div class="radio">
									  <label style="visibility:visible">
									    <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" >
									    Option one
									  </label>
									</div>
									<div class="radio">
									  <label style="visibility:visible">
									    <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
									    Option two
									  </label>
									</div>
									<div class="radio">
									  <label style="visibility:visible">
									    <input type="radio" name="optionsRadios" id="optionsRadios3" value="option3">
									    Option three
									  </label>
									</div><div class="radio">
									  <label style="visibility:visible">
									    <input type="radio" name="optionsRadios" id="optionsRadios3" value="option4">
									    Option four
									  </label>
									</div>
								</div>
							</div>
						</div>
					</div>	
					<!-- \单选框  -->
					<!-- 日期时间选择器 -->
					<div class="box box-element ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon glyphicon-remove"></i>
							删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<div class="preview">日期时间选择器</div>
						<div class="view" id='time'>
							<div ravo="rainbow_fx" class="form-group">
                             	<label for="dtp_input1" class="col-md-4 control-label">年/月/日/时/分</label>
								<div class='col-md-8'>
									<div class="input-group date form_datetime" data-toggle="datatimepiker"
										data-date-language="zh-CN"
										data-date-weekstart="0"
										data-date-autoclose="true"
										data-start-view="2"
										data-date-today-btn="linked"
										data-date-today-highlight="true"										
										data-link-field="dtp_input1"
										data-date-format="yyyy-mm-dd - HH:ii p"
										data-link-format="yyyy-mm-dd - HH:ii p">
										<input class="form-control" size="16" type="text" value=""
											readonly> <span class="input-group-addon"><span
											class="glyphicon glyphicon-remove"></span></span> <span
											class="input-group-addon"><span
											class="glyphicon glyphicon-th"></span></span>
									</div>
									<input type="hidden" id="dtp_input1" value="" />
								</div>
							</div>
						</div>
					</div>
				<!-- 日期时间选择器 -->
				<!-- 图片上传 -->
					<div class="box box-element ui-draggable">
						<a href="javascript:void(0);" class="remove label label-danger">
							<i class="glyphicon glyphicon-remove"></i>
							删除
						</a>
						<span class="drag label label-default">
							<i class="glyphicon glyphicon-move"></i>
							拖动
						</span>
						<div class="preview">文件上传</div>
						<div class="view" id="fileInput">
							<div ravo="rainbow_fx" class="form-group">
                              <label class="col-sm-4 control-label">文件上传：</label>
	                           <div class='fileInput'>
	                                <input id="file" class="file" type="file" multiple/>
                               </div>
							</div>
						</div>
					</div>
				<!-- 图片上传 -->
				
				</li>	
			</ul>
			<ul class="nav nav-list accordion-group">
					<li class="nav-header">
						<div class="pull-right popover-info">
							<i class="glyphicon glyphicon-question-sign"></i>
							<div class="popover fade right">
								<div class="arrow"></div>
								<h3 class="popover-title">帮助</h3>
								<div class="popover-content">
									将组件元素拖放入你需要放入的栅格列中。之后，你可以设置该javascript组件的样式。
								</div>
							</div>
						</div>
						<i class="glyphicon-plus glyphicon"></i>
						常用模版
					</li>
					<li class="boxes mute" id="estRows">

						<div class="lyrow ui-draggable">
							<a href="javascript:void(0);" class="remove label label-danger">
								<i class="glyphicon-remove glyphicon"></i>
								移除
							</a>
							<span class="drag label label-default">
								<i class="glyphicon glyphicon-move"></i>
								拖动
							</span>
							<div class="preview">常用布局1</div>
							<div class="view">
								<div class="path">
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
			<form ravo="rainbow_fx_layout_bd" class="form-horizontal breadcrumb" pourl=""
			id="formId_72355">
				<div class="clearfix">
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-3 column">
						<div ravo="rainbow_fx" class="form-group">
							<label class="col-sm-4 control-label">
								开始日期
							</label>
							<div class="input-group-sm col-sm-8">
								<input type="text" readonly="readonly" maxlength="20" class="form-control input-mini Wdate"
								value="" datetime-skin="twoer" datetime-date-fmt="yyyy-MM-dd HH:mm:ss"
								datetime-min-date="" datetime-max-date="" datetime-is-show-clear="true"
								datetime-is-show-week="true" datetime-is-show-today="true" datetime-default-value=""
								datetime-value-fmt="yyyyMMddHHmmss" data-link-field="val_datetime_598565"
								id="datetime_598565" onclick="WdatePicker({skin: 'twoer',dateFmt: 'yyyy-MM-dd HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('datetime_598565');}});"
								onchange="writeDateValue('datetime_598565');">
								<input type="hidden" id="val_datetime_598565" value="">
							</div>
						</div>
					</div>
					<div class="col-md-3 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-4 control-label">
								勾兑类型
							</label>
							<select data-role="multiselect" class="col-sm-8" id="selectId_490625"
							data-url="${ctx}/sys/dict/selectData?type=gdway">
							</select>
						</div>
					</div>
					<div class="col-md-3 column">
					</div>
					<div class="col-md-3 column">
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-3 column">
						<div ravo="rainbow_fx" class="form-group">
							<label class="col-sm-4 control-label">
								结束日期
							</label>
							<div class="input-group-sm col-sm-8">
								<input type="text" readonly="readonly" maxlength="20" class="form-control input-mini Wdate"
								value="" datetime-skin="twoer" datetime-date-fmt="yyyy-MM-dd HH:mm:ss"
								datetime-min-date="" datetime-max-date="" datetime-is-show-clear="true"
								datetime-is-show-week="true" datetime-is-show-today="true" datetime-default-value=""
								datetime-value-fmt="yyyyMMddHHmmss" data-link-field="val_datetime_689180"
								id="datetime_689180" onclick="WdatePicker({skin: 'twoer',dateFmt: 'yyyy-MM-dd HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('datetime_689180');}});"
								onchange="writeDateValue('datetime_689180');">
								<input type="hidden" id="val_datetime_689180" value="">
							</div>
						</div>
					</div>
					<div class="col-md-3 column">
						<div ravo="rainbow_fx" class="form-group">
							<label for="inputEmail3" class="col-sm-4 control-label">
								勾兑流水
							</label>
							<div class="col-sm-8" style="padding-left:0px">
								<input type="text" class="form-control" placeholder="勾兑流水">
							</div>
						</div>
					</div>
					<div class="col-md-3 column">
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-3 column">
								<shiro:haspermission name="anno">
									<button ravo="rainbow_fx" type="button" class="btn false btn-success"
									contenteditable="true" id="btnId_211888">
										查询
									</button>
								</shiro:haspermission>
							</div>
							<div class="col-md-3 column">
								<shiro:haspermission name="anno">
									<button ravo="rainbow_fx" type="button" class="btn btn-warning" contenteditable="true"
									id="btnId_742002">
										导出
									</button>
								</shiro:haspermission>
							</div>
							<div class="col-md-3 column">
							</div>
							<div class="col-md-3 column">
							</div>
						</div>
					</div>
					<div class="col-md-3 column">
					</div>
				</div>
			</form>
			<table id="table" data-toggle="table" data-url="data1.json" data-click-to-select="true"
			data-show-export="false" data-show-refresh="false" data-show-toggle="false"
			data-show-columns="false" data-pagination="true" data-search="false" data-query-params="queryParams"
			data-method="post" data-undefined-text="**" data-height="300" data-content-type="application/x-www-form-urlencoded"
			ravo="rainbow_fx_bj" class="table table-hover">
				<thead>
					<tr>
						<th data-field="state" data-checkbox="true">
						</th>
						<th data-field="id" data-title="日期">
						</th>
						<th data-field="invited_name" data-title="价差收入余额（万元）">
						</th>
						<th data-field="invited_phone" data-title="发生额（万元）（利息摊销收益）">
						</th>
						<th data-field="invited_email" data-title="发生原因(利息摊销、卖出、到期)">
						</th>
						<th data-field="action">
						</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
							</div>
						</div>
					</li>
				</ul>
		</div>
	</div>
	<!--/span-->
	<div class="demo ui-sortable" style="min-height: 728px;">
	</div> 
	<!--/span-->
	<div id="download-layout">
		<div class="container">
		</div>
	</div>
	<!--/row-->
	
	<script type="text/javascript">
		$(document).ready(function(){
			// alert($('#download-layout').html());	
			
		});
	</script>
</div>
<!--/.fluid-container-->
</div>
<script type="text/javascript">

		function saveLayout(){
			return;
			$.ajax({  
				type: "POST",  
				url: "/build_v3/saveLayout",  
				data: { 'layout-v3': $('.demo').html() },  
				success: function(data) {
					//updateButtonsVisibility();
				}
			});
		}
		
		//下载代码
		function downloadLayout(){
			$.ajax({  
				type: "POST",  
				url: "/build_v3/downloadLayout",  
				data: { 'layout-v3': $('#download-layout').html() },  
				success: function(data) { window.location.href = '/build_v3/download'; }
			});
		}
		
		function downloadHtmlLayout(){
			$.ajax({  
				type: "POST",  
				url: "/build_v3/downloadLayout",  
				data: { 'layout-v3': $('#download-layout').html() },  
				success: function(data) { window.location.href = '/build_v3/downloadHtml'; }
			});
		}

		function undoLayout() {
			
			$.ajax({  
				type: "POST",  
				url: "/build_v3/getPreviousLayout",  
				data: { },  
				success: function(data) {
					undoOperation(data);
				}
			});
		}

		function redoLayout() {
			
			$.ajax({  
				type: "POST",  
				url: "/build_v3/getPreviousLayout",  
				data: { },  
				success: function(data) {
					redoOperation(data);
				}
			});
		}

		$(document).on('hidden.bs.modal', function (e) {
		    $(e.target).removeData('bs.modal');
		});

		$('body').on('click', '#continue-share-non-logged', function () {
	   		 $('#share-not-logged').hide();
			 $('#share-logged').removeClass('hide');
	    	 $('#share-logged').show();
		});

		$('body').on('click', '#continue-download-non-logged', function () {
	   		 $('#download-not-logged').hide();
			 $('#download').removeClass('hide');
			 $('#download').show();
			 $('#downloadhtml').removeClass('hide');
			 $('#downloadhtml').show();
			 $('#download-logged').removeClass('hide');
	    	 $('#download-logged').show();
		});

		
    </script>

<div class="modal fade" role="dialog" id="editorModal">
  <div class="modal-header"> <a class="close" data-dismiss="modal">×</a>
    <h3>编辑</h3>
  </div>
  <div class="modal-body">
    <p>
      <textarea id="contenteditor"></textarea>
    </p>
  </div>
  <div class="modal-footer"> <a id="savecontent" class="btn btn-primary" data-dismiss="modal">保存</a> <a class="btn" data-dismiss="modal">关闭</a> </div>
</div>
<div class="modal fade" id="downloadModal" tabindex="-1" role="dialog" aria-labelledby="downloadModalLabel" aria-hidden="true" style="display: none;">
<div class="modal-dialog">
<div class="modal-content">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h4 class="modal-title">下载</h4>
	</div>
	<div class="modal-body">

		<div id="download-logged" class="">
			<div class="alert alert-info">已在下面生成干净的HTML, 可以复制粘贴代码到你的body内<br>
				请使用bootstrap3.0.1
			</div>
			<p>
				<textarea></textarea>
			</p>
		</div>

	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	</div>
</div>
<!-- /.modal-content -->
</div>
<!-- /.modal-dialog -->

<script type="text/javascript">
	downloadLayoutSrc();
	$('#modal-download-sign-in-button').click(function(event){
		$('.help-inline').hide();
		var form = $('#boxDownloadLoginForm');
		var ajaxLoginUrl = '/login/ajaxLogin';
		jQuery.ajax({
	         type : "post",
	         dataType : "json",
	         url : ajaxLoginUrl,
	         data : form.serialize(),
	         success: function(response) {
	        	 if(response.success){
	        		 $('#download-not-logged').hide();
	        		 $('#download').removeClass('hide');
	        		 $('#download').show();
	        		 $('#downloadhtml').removeClass('hide');
	        		 $('#downloadhtml').show();
	        		 $('#download-logged').removeClass('hide');
		        	 $('#download-logged').show();
	        	 }else {
		        	if(response.errors && response.errors.length > 0){
		        		$.each(response.errors, function(i, item) {
		                    $('#'+item.field+'-download-error').html(item.error);
		                    $('#'+item.field+'-download-error').show();
		        		}); 
			        } 
		         }
			},
			error: function(response) {
				
			}
		});
		
		return false;
	});
</script>

</div>

<div class="modal fade" id="shareModal" tabindex="-1" role="dialog" aria-labelledby="shareModalLabel" aria-hidden="true"></div>
<div class="modal fade" id="feedbackModal" tabindex="-1" role="dialog" aria-labelledby="feedbackModalLabel" aria-hidden="true">
<div class="modal-dialog">
<div class="modal-content">
	<div class="modal-header">
		<!--
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h4 class="modal-title">联系我们</h4> -->
	</div>
	<div class="modal-body">
		<div id="download-logged" class="">
			<div class="alert alert-info">
				有任何问题，可邮件联系
				<a href="javascript:void(0);">就不告诉你</a> ，哈哈
			</div>
		</div>

	</div>
</div>
<!-- /.modal-content -->
</div>

<!-- /.modal-dialog -->
</div>

<script type="text/javascript">
	(function(){
		/* $("#downloadModal textarea").val(); */
	})();
	
	var messenger = new Messenger('ide', 'faceui');

    messenger.listen(function (msg) {
    	
    	if( typeof msg=="string"){
       		if(msg=="modal")
        	{
        		sendMessage('parent');
        	}else if(msg=="clear")
        	{
        		$("#clear").click();
        	}else if(msg=="edit")
        	{
        		$("#edit").click();
        	}else if(msg=="devpreview")
        	{
        		$("#devpreview").click();
        	}else if(msg=="JSEditer")
        	{
        		$("#JSEditer").click();
        	}else if(msg=="download")
        	{
        		$("[data-target='#downloadModal']").click();
        	}else if(msg.indexOf('.jsp') >= 0){
        		saveScript(msg);
        	}
    	}else if(msg.name ==='save'){
    		saveJspFile(msg);
    	}else if(msg.name === "browserPreview")
    	{
    		saveLayoutSrc(msg);
    		sendMessage('parent','open');
        }else if(msg.remark=="popovermark"){
    		
    	}else{
	        msghandler(msg);
    	}
    	
    });

    messenger.addTarget(window.parent, 'parent');

    function sendMessage(name,map) {
    	messenger.targets[name].send(map);
    }

    function sendAll() {
        messenger.send("message from iframe1: to all");
    } 
    
    function startjseditor(v,id){
    	console.info(IDVALUE +" id的值");
    	if(IDVALUE === "undefined" || IDVALUE === ""){
    		alert("标识 ID 为空，请赋值！");
    		return;
    	}
    	var message = {};
    	message.numark = v;
    	console.info("startjseditor-=-=message.numark-=-=-"+message.numark+" "+$("#"+id).text()+" "+id);
    	message.act = 'requestJSET';
    	message.quesbtn = $("#"+id).text();
    	message.idname = IDVALUE;
    	$(this).popover('hide');
    	sendMessage('parent',message);
    }
    
    
</script>
</body>
</html>