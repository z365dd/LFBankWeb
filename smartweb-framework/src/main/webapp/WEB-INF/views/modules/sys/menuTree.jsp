<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="menuHeader" id="menu-${menu.id }">
	<div class="c1"></div>
	<div class="menuTree">
		<div class="c2-1"></div>
		<div class="c2-2">${menu.name }</div>
	</div>
	<c:forEach var="menu2" items="${menu.children }" varStatus="idxStatus">
	<div class="menu2" id="menu2-${menu2.id }" data-parent="#menu-${menu.id }" data-href=".menu3-${menu2.id }">
		<div class="c3-1">${menu2.name }</div>
		<div class="icon-change-right"></div>
	</div>
	
	<div class="list-hidden">
		<ul class="menu3-${menu2.id }">
		<c:forEach var="menu3" items="${menu2.children }" varStatus="idxStatus">
			<li class="c4-1"><div class="menu3-circle-blur"></div>
				<div class="c4-1-2"><a data-href=".menu3-${menu3.id}" href="${fn:indexOf(menu3.menuLink, '://') eq -1 ? ctx : ''}${not empty menu3.menuLink ? menu3.menuLink : '/404'}" target="${not empty menu3.windowVal ? menu3.windowVal : 'mainFrame'}"  id="menu3-${menu3.id}" class="menu3-list-blur" title="${menu3.name }">${menu3.name }</a></div></li>
		</c:forEach>
		</ul>
	</div>
	
	</c:forEach>
</div>
