<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<html>
<head>
	<title>业务表管理</title>
	<meta name="decorator" content="default"/>
	<!-- BOOTSTRAP -->
	<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
	<!-- /BOOTSTRAP -->	
	
	<!-- Multiselect -->
	<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/css/bootstrap-multiselect.css"/>
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js"></script>
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/jquery.multiselectcus.js"></script>
	<!-- /Multiselect -->
	
	<!-- change skin -->
	<link href="${ctxStatic}/common/smartweb.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css" type="text/css" rel="stylesheet" />	
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#tabDesc").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					$("input[type=checkbox]").each(function(){
						$(this).after("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""
								+($(this).attr("checked")?"1":"0")+"\"/>");
						$(this).attr("name", "_"+$(this).attr("name"));
					});
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/gen/genTable/">业务表列表</a></li>
		<li class="active"><a href="${ctx}/gen/genTable/form?id=${genTable.id}&name=${genTable.name}">业务表<shiro:hasPermission name="gen:genTable:edit">${not empty genTable.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="gen:genTable:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<c:choose>
		<c:when test="${empty genTable.name}">
			<form:form id="inputForm" modelAttribute="genTable" action="${ctx}/gen/genTable/form" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<br/>
				<div class="control-group">
					<label class="control-label">表名:</label>
					<div style="margin-left: 20px; display:inline-block;">
						<select data-role="multiselect" class="input-xxlarge" id="name" class="" name="name"
                                            data-enable-filtering="true" data-url="${ctx}/gen/genTable/selectData?type=Table"
                                            data-async="true">
                        </select>
					</div>
					<div class="form-actions">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="下一步"/>&nbsp;
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
		</c:when>
		<c:otherwise>
			<form:form id="inputForm" modelAttribute="genTable" action="${ctx}/gen/genTable/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<fieldset>
					<legend>基本信息</legend>
					<div class="control-group">
						<label class="control-label">表名:</label>
						<div style="margin-left: 20px; display:inline-block;">
							<form:input path="name" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">说明:</label>
						<div style="margin-left: 20px; display:inline-block;">
							<form:input path="tabDesc" htmlEscape="false" maxlength="200" class="required"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">类名:</label>
						<div style="margin-left: 20px; display:inline-block;">
							<form:input path="procClssName" htmlEscape="false" maxlength="200" class="required"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">父表表名:</label>
						<div style="margin-left: 20px; display:inline-block;">
							<select data-role="multiselect" id="parentTabName" class="" name="parentTabName"
                                            data-enable-filtering="true" data-url="${ctx}/gen/genTable/selectData?type=Table"
                                            data-async="true" blank-item="true" blank-text="无">
                        	</select>
							&nbsp;当前表外键：
							<%-- <form:select path="parentTabOutKey" cssClass="input-xlarge">
								<form:option value="">无</form:option>
								<form:options items="${genTable.columnList}" itemLabel="nameAndComments" itemValue="name" htmlEscape="false"/>
							</form:select> --%>
							<select data-role="multiselect" id="parentTabOutKey" name="parentTabOutKey" class="" 
								data-max-height="300" data-url="">
							    <option value="">无</option>
							    <c:forEach items="${genTable.columnList}" var="column">
								    <option value="${column.name}">${column.name}:${column.tabDesc}</option>
							    </c:forEach>
							</select>	
							<span class="help-inline">如果有父表，请指定父表表名和外键</span>
						</div>
					</div>
					<div class="control-group hide">
						<label class="control-label">备注:</label>
						<div style="margin-left: 20px; display:inline-block;">
							<form:textarea path="rmrk" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
						</div>
					</div>
					<legend>字段列表</legend>
					<div class="control-group">
						<table id="contentTable" class="table table-striped table-bordered table-condensed">
							<thead><tr><th title="数据库字段名">列名</th><th title="默认读取数据库字段备注">说明</th><th title="数据库中设置的字段类型及长度">物理类型</th><th title="实体对象的属性字段类型">Java类型</th>
								<th title="实体对象的属性字段（对象名.属性名|属性名2|属性名3，例如：用户user.id|name|loginName，属性名2和属性名3为Join时关联查询的字段）">Java属性名称 <i class="icon-question-sign"></i></th>
								<th title="是否是数据库主键">主键</th><th title="字段是否可为空值，不可为空字段自动进行空值验证">可空</th><th title="选中后该字段被加入到insert语句里">插入</th>
								<th title="选中后该字段被加入到update语句里">编辑</th><th title="选中后该字段被加入到查询列表里">列表</th>
								<th title="选中后该字段被加入到查询条件里">查询</th><th title="该字段为查询字段时的查询匹配放松">查询匹配方式</th>
								<th title="字段在表单中显示的类型">显示表单类型</th><th title="显示表单类型设置为“下拉框、复选框、点选框”时，需设置字典的类型">字典类型</th><th>排序</th></tr></thead>
							<tbody>
							<c:forEach items="${genTable.columnList}" var="column" varStatus="vs">
								<tr${column.delFlg eq '1'?' class="error" title="已删除的列，保存之后消失！"':''}>
									<td nowrap>
										<input type="hidden" name="columnList[${vs.index}].id" value="${column.id}"/>
										<input type="hidden" name="columnList[${vs.index}].delFlg" value="${column.delFlg}"/>
										<input type="hidden" name="columnList[${vs.index}].genTable.id" value="${column.genTable.id}"/>
										<input type="hidden" name="columnList[${vs.index}].name" value="${column.name}"/>${column.name}
									</td>
									<td>
										<input type="text" name="columnList[${vs.index}].tabDesc" value="${column.tabDesc}" maxlength="200" class="required" style="width:100px;"/>
									</td>
									<td nowrap>
										<input type="hidden" name="columnList[${vs.index}].dbFieldTp" value="${column.dbFieldTp}"/>${column.dbFieldTp}
									</td>
									<td>
										<select data-role="multiselect" name="columnList[${vs.index}].javaTp" class="required input-mini" style="width:85px;*width:75px">
											<c:forEach items="${config.javaTypeList}" var="dict">
												<option value="${dict.value}" ${dict.value==column.javaTp?'selected':''} title="${dict.dictInfo}">${dict.label}</option>
											</c:forEach>
										</select>
									</td>
									<td>
										<input type="text" name="columnList[${vs.index}].javaField" value="${column.javaField}" maxlength="200" class="required input-small"/>
									</td>
									<td>
										<input type="checkbox" name="columnList[${vs.index}].mainKeyFlg" value="1" ${column.mainKeyFlg eq '1' ? 'checked' : ''}/>
									</td>
									<td>
										<input type="checkbox" name="columnList[${vs.index}].nullFlg" value="1" ${column.nullFlg eq '1' ? 'checked' : ''}/>
									</td>
									<td>
										<input type="checkbox" name="columnList[${vs.index}].insertFlg" value="1" ${column.insertFlg eq '1' ? 'checked' : ''}/>
									</td>
									<td>
										<input type="checkbox" name="columnList[${vs.index}].editFlg" value="1" ${column.editFlg eq '1' ? 'checked' : ''}/>
									</td>
									<td>
										<input type="checkbox" name="columnList[${vs.index}].listFlg" value="1" ${column.listFlg eq '1' ? 'checked' : ''}/>
									</td>
									<td>
										<input type="checkbox" name="columnList[${vs.index}].qryFlg" value="1" ${column.qryFlg eq '1' ? 'checked' : ''}/>
									</td>
									<td>
										<select data-role="multiselect" name="columnList[${vs.index}].qryTp" class="required input-mini">
											<c:forEach items="${config.queryTypeList}" var="dict">
												<option value="${fns:escapeHtml(dict.value)}" ${fns:escapeHtml(dict.value)==column.qryTp?'selected':''} title="${dict.dictInfo}">${fns:escapeHtml(dict.label)}</option>
											</c:forEach>
										</select>
									</td>
									<td>
										<select data-role="multiselect" name="columnList[${vs.index}].dpyTp" class="required" style="width:100px;">
											<c:forEach items="${config.showTypeList}" var="dict">
												<option value="${dict.value}" ${dict.value==column.dpyTp?'selected':''} title="${dict.dictInfo}">${dict.label}</option>
											</c:forEach>
										</select>
									</td>
									<td>
										<input type="text" name="columnList[${vs.index}].dictTp" value="${column.dictTp}" maxlength="200" class="input-mini"/>
									</td>
									<td>
										<input type="text" name="columnList[${vs.index}].sort" value="${column.sort}" maxlength="200" class="required input-min digits"/>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</fieldset>
				<div class="form-actions">
					<shiro:hasPermission name="gen:genTable:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</form:form>
		</c:otherwise>
	</c:choose>
</body>
</html>
