<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>图片选择</title>
	<meta name="decorator" content="blank"/>
    <style type="text/css">
    	.page-header {clear:both;margin:0 20px;padding-top:20px;}
    	.the-icons {padding:25px 10px 15px;list-style:none;}
		.the-icons li {float:left;width:22%;line-height:25px;margin:2px 5px;cursor:pointer;}
		.the-icons i {margin:1px 5px;font-size:16px;} .the-icons li:hover {background-color:#efefef;}
        .the-icons li.active {background-color:#0088CC;color:#ffffff;}
        .the-icons li:hover i{font-size:20px;}
    </style>
    <script type="text/javascript">
	    $(document).ready(function(){
	    	$("#images li").click(function(){
	    		$("#images li").removeClass("active");
	    		$("#images li i").removeClass("icon-white");
	    		$(this).addClass("active");
	    		$(this).children("i").addClass("icon-white");
	    		$("#image").val($(this).text());
	    	});
	    	$("#images li").each(function(){
	    		if ($(this).text()=="${value}"){
	    			$(this).click();
	    		}
	    	});
	    	$("#images li").dblclick(function(){
	    		top.$.jBox.getBox().find("button[value='ok']").trigger("click");
	    	});
	    });
    </script>
</head>
<body>
<input type="hidden" id="image" value="${value}" />
<div id="images">
		<h2 class="page-header"> 一级菜单图片  </h2>
		<ul class="the-icons">
  			<li><img src="${ctxStatic}/mainframe/img/first_menu/nav01.png" />nav01.png</li>
  			<li><img src="${ctxStatic}/mainframe/img/first_menu/nav02.png" />nav02.png</li>
  			<li><img src="${ctxStatic}/mainframe/img/first_menu/nav03.png" />nav03.png</li>
  			<li><img src="${ctxStatic}/mainframe/img/first_menu/nav04.png" />nav04.png</li>
  			<li><img src="${ctxStatic}/mainframe/img/first_menu/nav05.png" />nav05.png</li>
  			<li><img src="${ctxStatic}/mainframe/img/first_menu/nav06.png" />nav06.png</li>
		</ul>

	<br/><br/>
</div>
</body>