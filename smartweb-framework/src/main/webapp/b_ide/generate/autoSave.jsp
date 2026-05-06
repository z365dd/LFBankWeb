<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!-- JSTL  -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<!DOCTYPE html>
<html lang="zh-CN">
<body>
	<div class="">
	<div style="padding: 50px;" ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-4 column">
		</div>
		<div class="col-md-4 column">
			<form id="formId_save" ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="">
				<div class="caption">
					<div class="checkbox checkbox-info">
						<input id="saveHtml" name="saveHtml" type="checkbox" checked>
				      <label for="saveHtml">
				            保存界面
				      </label>
				    </div>
				    
				    <div class="checkbox checkbox-info">
				    	<input id="saveJs" name="saveJs" type="checkbox" checked>
				      <label for="saveJs">
				         保存JS代码
				      </label>
				    </div>
				    
				    <div class="checkbox checkbox-info">
				    	<input id="savePo" name="savePo" type="checkbox" checked>
				      <label for="savePo">
				         保存PO类
				      </label>
				    </div>
				    
				    <div class="checkbox checkbox-info">
				    	<input id="saveAction" name="saveAction" type="checkbox" checked>
				      <label for="saveAction">
				         保存Action类
				      </label>
				    </div>
				</div>
			</form>
		</div>
		<div class="col-md-4 column">
		</div>
	</div>
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-4 column">
		</div>
		<div class="col-md-4 column">
			<button id="btnId_754957" ravo="rainbow_fx" type="button" class="btn btn-block btn-info">
				保存
			</button>
			<!-- <button id="btnId_984420" ravo="rainbow_fx" type="button" class="btn btn-success btn-block">
							保存为HTML
			</button> -->
		</div>
		<div class="col-md-4 column">
		</div>
	</div>
</div>
</body>
</html>
