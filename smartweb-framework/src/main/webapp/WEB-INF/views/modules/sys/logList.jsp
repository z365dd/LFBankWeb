<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日志管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(function(){
			$("#btnSubmit").click(function () {
				$("#pageNo").val(1);
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
		/**
		 * @param modalTitle 标题
		 * @param modalSelect modal选择符 如'#myModal'
		 * @param modalData html代码字符串
		 * @param modalCallback modal回调函数
		 * @returns
		 */
		function moadlDataControl(modalTitle, modalSelect, modalData, width, height, useSubmitBtn) {
		   $("#modalTitle").text(modalTitle);
		   if("" != width) {
			   $(".modal-dialog").width(width);
		   }
		   if("" != height) {
			   $(".modal-body").height(height);
		   }
		   /* 初始化模态框首页数据 */
		   $(modalSelect + " " + ".modal-body").html(modalData);

		   /* 是否显示提交按钮 */
		   if(useSubmitBtn != undefined && !useSubmitBtn) {
			   $(modalSelect + " " + ".btnSubmit").hide();
		   } else {
			   $(modalSelect + " " + ".btnSubmit").show();
		   }
		   /* 关闭模态框时,清空数据 */
		   $(modalSelect).on('hidden.bs.modal', function () {
			   $(modalSelect + " " + ".modal-body").empty();
				$("#myModal").css("display","none");
		   });  
		}
		function paramsItem(label, value) {
			var item = "<div class='form-group' style='margin-left:100px;'>" 
						+ "<label><font size='2'>" + label + " :</font></label>"
						+ "<span class='help-inline'><font size='2'>" + value + "</font></span>"
						+ "</div>";
			return item;
		}
		function renderParams(uri, params) {
			var protocolType = $("#protocolType").val();
			var formElement = "<div class='' style=''><form class='form-horizontal' pourl='' id='paramsForm' style='margin-top:20px;'></form></div>";
			$("#myModal").css("display","block");
			$("#myModal .modal-body").css("overflow-y","scroll");
			//定义模态框数据
		    moadlDataControl("提交参数", "#myModal", formElement, "100%", "", false);
			var paramArr = params.split("&");
			if(paramArr[0] != "") {
				$("#paramsForm").append("<div class='form-group' style='margin-left:75px;'><font size='2'>{</font><div>")
				for(var i = 0; i < paramArr.length; i++) {
					var paramItem = paramArr[i].split("=");
					if(i == paramArr.length - 1) {
						$("#paramsForm").append(paramsItem("\"" + paramItem[0] + "\"", "\"" + paramItem[1] + "\""));
					} else {
						$("#paramsForm").append(paramsItem("\"" + paramItem[0] + "\"", "\"" + paramItem[1] + "\","));
					}
				}
				$("#paramsForm").append("<div class='form-group' style='margin-left:75px;'><font size='2'>}</font><div>")
			}
			$('#myModal').modal('show');
		}
	</script>
</head>
<body>
	<form:form id="searchForm" action="${ctx}/sys/log/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
			<label>操作流水：</label><div style="margin-left:4px; display:inline-block;"><input id="seq" name="seq" type="text" class="input-medium" value="${log.seq}"/></div>
			<label>操作菜单：</label><div style="margin-left:4px; display:inline-block;"><input id="infoTitle" name="infoTitle" type="text" class="input-medium" value="${log.infoTitle}"/></div>
			<label>用户：</label><div style="margin-left:4px; display:inline-block;"><input id="crtr" name="crtr" type="text" class="input-medium" value="${log.crtr}"/></div>
			<label>URI：</label><div style="margin-left:4px; display:inline-block;"><input id="reqUrl" name="reqUrl" type="text" class="input-medium" value="${log.reqUrl}"/></div>
		</div><div style="margin-top:8px;">
			<label>日期范围：&nbsp;</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" class="input-medium Wdate"
				value="<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			<label>&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input id="endDate" name="endDate" type="text" readonly="readonly" class="input-medium Wdate"
				value="<fmt:formatDate value="${log.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>&nbsp;&nbsp;
			&nbsp;<label for="javaExctByteData"><input id="javaExctByteData" name="javaExctByteData" type="checkbox"${log.javaExctByteData eq '1'?' checked':''} value="1"/>只查询异常信息</label>
			&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>操作流水</th><th>操作菜单</th><th>操作用户</th><th>所在机构</th><th>所在租户</th><th>URI</th><!-- <th>提交参数</th> --><th>提交方式</th><th>操作者IP</th><th>操作时间</th><th>信息</th></tr></thead>
		<tbody><%request.setAttribute("strEnter", "\n");request.setAttribute("strTab", "\t");%>
		<c:forEach items="${page.list}" var="log" varStatus="status">
			<tr>
				<td>${log.seq}</td>
				<td>${log.infoTitle}</td>
				<td>${log.createByName}</td>
				<td>${log.createByBrchName}</td>
				<td>${log.createByTntName}</td>
				<td><strong>${log.reqUrl}</strong></td>
				<td>${log.reqMeth}</td>
				<td>${log.termIp}</td>
				<td>${log.crtTime}</td>
				<td><c:if test="${not empty log.javaExctByteData}">
					<a role="button" data-toggle="collapse" href="#exception_${ status.index}" aria-expanded="false" aria-controls="exception_${ status.index}">
					  异常信息
					</a>
				</c:if></td>
			</tr>
			<c:if test="${not empty log.javaExctByteData}"><tr>
				<td colspan="9" style="word-wrap:break-word;word-break:break-all;">
					<div class="collapse" id="exception_${ status.index}">
					  <div class="well">
					    ${fn:replace(fn:replace(fns:escapeHtml(fns:toString(log.javaExctByteData)), strEnter, '<br/>'), strTab, '&nbsp; &nbsp; ')}
					  </div>
					</div>
				</td>
			</tr></c:if>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<!-- 模态框（Modal） -->
	<div id="myModal" class="modal fade" tabindex="-1" style="display: none" role="dialog" aria-labelledby="modalTitle" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
	    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="modalTitle"></h4>
            </div>
            <div class="modal-body" style="margin-top:20px;height:360px;"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btnSubmit">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>