<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%> <!-- JSTL  -->

	<div class="menuda show">
	    <div class="menuimg"></div>
<!-- 加载各级菜单 -->
		
		<c:forEach items="${menu['mainMenu']}" var="firstMenu" varStatus="idxStatus">
			<div class="menuModel2 menuShow">
				<div class="menuModel3">
					<div class="menu3icon" style="background: url(${ctxStatic }/mainframe/img/${ctxTheme}/allMenuIcon.png) no-repeat 0px ${idxStatus.index>16?-25*2:idxStatus.index*-25}px">
					</div>
					<div class="menu3title">${firstMenu.name }</div>
					<c:forEach items="${menu[firstMenu.id]}" var="secondMenu" varStatus="idxStatus">
					<div class="menuModel4 menuShow">
						<ul>
							<li class="menu4li">
								<div class="secondMenu-click">
									${secondMenu.name }
									<ul>
										<c:forEach items="${menu[secondMenu.id]}" var="thirdMenu" varStatus="idxStatus">
										<li class="menu4li2 menuShow">
											<div class="menu4icon"></div>
											<div class="menu4title"><a class="menuTarget" href="${fn:indexOf(thirdMenu.menuLink, '://') eq -1 ? ctx : ''}${not empty thirdMenu.menuLink ? thirdMenu.menuLink : '/404'}" target="${not empty thirdMenu.windowVal ? thirdMenu.windowVal : 'mainFrame'}"  menuPath="${firstMenu.name }->${secondMenu.name}->${thirdMenu.name}">${thirdMenu.name }</a></div>
										</li>
										</c:forEach>
									</ul>
								</div>
							</li>
						</ul>
					</div>
					</c:forEach>
					<div class="menuline"></div>
				</div>
				
			</div>

		</c:forEach>

	</div>

