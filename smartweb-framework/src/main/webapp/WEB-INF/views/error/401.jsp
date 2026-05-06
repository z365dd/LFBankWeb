<%
response.setStatus(401);

//获取异常类
Throwable ex = Exceptions.getThrowable(request);

// 如果是异步请求或是手机端，则直接返回信息
if (Servlets.isAjaxRequest(request)) {
	if (ex!=null && StringUtil.startsWith(ex.getMessage(), "msg:")){
		out.print(StringUtil.replace(ex.getMessage(), "msg:", ""));
	}else{
		out.print("未登录或登录超时。请重新登录，谢谢！");
	}
}

//输出异常信息页面
else {
%>
<%@page import="com.adtec.sys.common.web.Servlets"%>
<%@page import="com.adtec.sys.common.utils.Exceptions"%>
<%@page import="com.adtec.framework.common.util.StringUtil"%>
<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>401 - 未登录或登录超时</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body>
	<div class="container-fluid">
		<div class="page-header"><h1>未登录或登录超时.</h1></div>
		<%
			if (ex!=null && StringUtil.startsWith(ex.getMessage(), "msg:")){
				out.print("<div>"+StringUtil.replace(ex.getMessage(), "msg:", "")+" <br/> <br/></div>");
			}
		%>
		<script>
		try{
			top.$.jBox.closeTip();
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = ctx;
		}catch(e){}
		</script>
	</div>
</body>
</html>
<%
} out = pageContext.pushBody();
%>