<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	<link rel="icon" href="${ctxStatic}/mainframe/img/favicon.ico" type="image/x-icon"/>
	<script src="${ctxStatic}/jquery-3.6.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${ctxStatic}/jquery-migrate-3.4.0.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${ctxStatic}/bootstrap-3.4.1/js/bootstrap.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctxIde }/bootstrap/js/bootstrap-multiselect.js"></script>
	<script type="text/javascript" src="${ctxIde }/bootstrap/js/bootstrap-multiselect-collapsible-groups.js"></script>
	<script type="text/javascript" src="${ctxIde }/bootstrap/js/jquery.multiselectcus.js"></script>
	<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/mustache.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/smartweb.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctxStatic}/common/check.js"></script>
	<script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}', ctxIde='${ctxIde}';</script>
	<link href="${ctxStatic}/common/smartweb.css" type="text/css" rel="stylesheet" />
	
	<link href="${ctxStatic}/mainframe/css_${ctxTheme}/login.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript">
		$(document).ready(function() {
			/* 20181022  修复下方版本标识div 遮住登陆按钮BUG ENG*/
			$('#maxWidth').val(window.screen.width);	//屏幕分辨率的宽
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					rent: {required: "请选择租户."},
					username: {required: "请填写用户名."},
					password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorPlacement: function(error, element) {
					$("#loginError").html('');
					error.appendTo($("#loginError").parent()); 
				},
	            submitHandler: function (form) {
	            	$('#encyptPasswd').val(encryptRSA($('#password').val()));
					// if($("#rememberMe").attr('checked')){
	            	// 	setCookie("username",$("#username").val(),7);
	            	// 	setCookie("password",window.btoa($("#password").val()),7);
	            	// }else{
					clearCookie("username");
					clearCookie("password");
	            	// }
	                form.submit();
	            }
			});
			$(document).keydown(function(event){
				if(event.keyCode ==13){
					$('#loginForm').submit();
					return false;
				}
			});
			
			$(".login-btn").click(function(){
				$('#loginForm').submit();
			})
			
			//登录框大小调整
			var pageHeight = $(window).height();
			var inputHeight = pageHeight*0.58*0.78*0.34*0.389;
			// var rememberMe = pageHeight*0.58*0.05;
			var btnArea = pageHeight*0.58*0.21;
			$(".k2-2-1,.k2-2-2,.yzm").css("height",inputHeight+"px");
			// $(".k1-3").css("height",rememberMe+"px");
			$(".k1-4").css("height",btnArea+"px");
			$(".login-btn").css("lineHeight",btnArea*0.25+"px");
			
			 $(window).resize(function(){
				 var pageHeight = $(window).height();
				var inputHeight = pageHeight*0.51*0.78*0.34*0.389;
				// var rememberMe = pageHeight*0.51*0.05;
				var btnArea = pageHeight*0.51*0.21;
				$(".k2-2-1,.k2-2-2,.yzm").css("height",inputHeight+"px");
				// $(".k1-3").css("height",rememberMe+"px");
				$(".k1-4").css("height",btnArea+"px");
				$(".login-btn").css("lineHeight",btnArea*0.25+"px");
			 })
			 
			 if(getCookie("username") && getCookie("password")){
				 $("#username").val(getCookie("username"));
				 $("#password").val(window.atob(getCookie("password")));
			 }
		});
		
		//设置cookie
		function setCookie(cname, cvalue, exdays) {
		    var d = new Date();
		    d.setTime(d.getTime() + (exdays*24*60*60*1000));
		    var expires = "expires="+d.toUTCString();
		    document.cookie = cname + "=" + cvalue + "; " + expires+"; path=/";//path=/是根路径
		}
		
		//获取cookie
		function getCookie(cname) {
		    var name = cname + "=";
		    var ca = document.cookie.split(';');
		    for(var i=0; i<ca.length; i++) {
		        var c = ca[i];
		        while (c.charAt(0)==' ') c = c.substring(1);
		        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
		    }
		    return undefined;
		}
		//清除cookie
		function clearCookie(name) {
		    setCookie(name, "", -1);
		}
		
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
	
</head>
<body>
	<div class="back-img1"></div>
	<div class="back-img2"></div>
	<section class="back">
	    <div class="k1">
	        <div class="k1-1">
	            <div class="logoimg">
	                <img src="${ctxStatic}/mainframe/img/${ctxTheme}/adtec-login-logo.png" alt="">
	            </div>
	            <div class="login-line"></div>
	            <div class="login-name">
	               	${fns:getConfig('productName')}
	            </div>
	        </div>
	        <form id="loginForm" action="${ctx}/login" method="post" novalidate="novalidate">
	            <div class="k1-2">
	                <div class="k2-2">
	                    <div class="k2-2-1" style="height: 44.3889px;">
	                        <div class="login-user-icon"></div>
<%--	                        <input type="hidden" id="maxWidth" name="maxWidth" value="1366">--%>
	                        <input type="text" class="login-user required" id="username" name="username" autocomplete="off" value="${username}" >
	                    </div>
	                    <div class="k2-2-2" style="height: 44.3889px;">
	                        <div class="login-password-icon"></div>
	                        <input class="login-pw required" type="password" id="password" autocomplete="new-password">
	                        <input type="hidden" id="encyptPasswd" name="password">
	                    </div>
						<c:if test="${isValidateCodeLogin}">
							<div class="validateCode">
								<sys:validateCode name="validateCode"
									inputCssStyle="margin-bottom:0;" />
							</div>
						</c:if>	                    
	                </div>
<%--	                <div class="k1-3" style="height: 21.514px;">--%>
<%--	                	<label for="rememberMe" title="下次不需要再登录" class="remember"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''} /> <span>记住我（公共场所慎用）</span></label>--%>
<%--	                </div>--%>
	                <div class="k1-4" style="height: 90.3588px;">
                        <div class="error-message">
                            <div class="login-error" id="loginError" style="backgroud:red;margin-left: 2%">${message}</div>
                        </div>
	                    <div class="k1-4-1">
	                        <span class="login-btn">登录</span>
	                    </div>
	                </div>
	                <div class="clear"></div>
	            </div>
	        </form>
	    </div>
		
		<div class="k2"></div>
<%--		<div class="k2">&copy; ${fns:getConfig('copyrightYear')}  ADTEC CO.,Ltd . All rights reserved.</div>--%>
	</section>
	
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
</body>
</html>