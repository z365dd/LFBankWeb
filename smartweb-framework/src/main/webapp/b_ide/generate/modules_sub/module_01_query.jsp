<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!-- JSTL  -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
</head>
<body style="margin: 0px 0px">


<div class="">
	
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
		<!-- 表单属性BEG -->
		<div ravo="rainbow_fx_layout_panel" class="panel panel-primary">
				<div class="panel-heading">
					<h4 ravo="rainbow_fx_bj" data-rainbow="caption">
						查询条件表单属性
					</h4>
				</div>
				<div class="panel-body">
					<form class="form-inline" role="form" id="formId_query">
					
					  <div class="form-group">
					    <div class="input-group">
					      <div class="input-group-addon"> <span class="glyphicon glyphicon-th-list"></span>ToolBar </div>
					      	<select id="form_query_toolbar" name="form_query_toolbar" data-role="multiselect" class="col-sm-7">
								<option value="true">
									是
								</option>
								<option value="false">
									否
								</option>
							</select>
					    </div>
					  </div>
					  
					  <div class="form-group">
					    <div class="input-group">
					      <div class="input-group-addon"> <span class="glyphicon glyphicon-th-list"></span>排列方式 </div>
					      <select id="form_query_rank" name="form_query_rank" data-role="multiselect" class="col-sm-7">
									<option value="form-horizontal">
										默认
									</option>
									<option value="form-inline">
										内联
									</option>
									<option value="form-horizontal">
										水平
									</option>
								</select>
					    </div>
					  </div>
					  
					  <div class="form-group">
					    <div class="input-group">
					      <div class="input-group-addon"><span class="glyphicon glyphicon-zoom-in"></span>尺寸</div>
				     	   <select id="form_query_size" name="form_query_size" data-role="multiselect" class="col-sm-7 rainbow-select">
								<option value="">
									默认
								</option>
								<option value="input-lg">
									大
								</option>
								<option value="input-sm">
									小
								</option>
							</select>
					    </div>
					  </div>
					  
					  <div class="form-group">
					    <div class="input-group">
					      <div class="input-group-addon">列数</div>
				     	   <select id="form_query_colNum" name="form_query_colNum" data-role="multiselect" class="col-sm-7">
									<option value="1">
										1列
									</option>
									<option value="2">
										2列
									</option>
									<option value="3">
										3列
									</option>
									<option value="4">
										4列
									</option>
									<option value="5">
										5列
									</option>
									<option value="6">
										6列
									</option>
								</select>
					    </div>
					  </div>
					  
					  <div class="form-group">
					    <div class="input-group">
					      <div class="input-group-addon">左占</div>
				     	   <select id="form_query_leftOcc" name="form_query_leftOcc" data-role="multiselect" class="col-sm-7">
									<option value="0">
										col-0
									</option>
									<option value="1">
										col-1
									</option>
									<option value="2">
										col-2
									</option>
									<option value="3">
										col-3
									</option>
									<option value="4">
										col-4
									</option>
									<option value="5">
										col-5
									</option>
									<option value="6">
										col-6
									</option>
									<option value="7">
										col-7
									</option>
									<option value="8">
										col-8
									</option>
									<option value="9">
										col-9
									</option>
									<option value="10">
										col-10
									</option>
									<option value="11">
										col-11
									</option>
									<option value="12">
										col-12
									</option>
								</select>
					    </div>
					  </div>
					  
					  <div class="form-group">
					    <div class="input-group">
					      <div class="input-group-addon">
					      	右占
					      </div>
				     	   <select id="form_query_rightOcc" name="form_query_rightOcc" data-role="multiselect" class="col-sm-7">
									<option value="0">
										col-0
									</option>
									<option value="1">
										col-1
									</option>
									<option value="2">
										col-2
									</option>
									<option value="3">
										col-3
									</option>
									<option value="4">
										col-4
									</option>
									<option value="5">
										col-5
									</option>
									<option value="6">
										col-6
									</option>
									<option value="7">
										col-7
									</option>
									<option value="8">
										col-8
									</option>
									<option value="9">
										col-9
									</option>
									<option value="10">
										col-10
									</option>
									<option value="11">
										col-11
									</option>
									<option value="12">
										col-12
									</option>
								</select>
					    </div>
					  </div>
					</form>
				</div>
			</div>
			
			<div ravo="rainbow_fx_layout_panel" class="panel panel-primary">
				<div class="panel-heading">
					<h4 ravo="rainbow_fx_bj" data-rainbow="caption">
						查询表单字段属性
					</h4>
				</div>
				<div class="panel-body">
					<table class="table table-hover" 
						id="table_query" 
						data-toggle="table"
						data-toolbar="#querytoolbar"
						data-click-to-select="false" 
						data-show-export="true" 
						data-show-refresh="true"
						data-show-toggle="true" 
						data-show-columns="true" 
						data-pagination="true"
						data-search="true" 
						data-detail-view="true"
						data-query-params="queryParams" 
						data-method="post" 
						data-undefined-text="**"
						data-unique-id="ID" 
						ravo="rainbow_fx_bj">
						<thead style="">
							<tr>
								<th data-checkbox="true" data-field="state">
								</th>
								<th data-field="ID">
									ID
								</th>
								<th data-field="a_data_name">
									字段名
								</th>
								<th data-field="a_data_type">
									字段类型
								</th>
								<th data-field="a_data_length">
									字段长度
								</th>
								<th data-field="a_data_dictionary">
									数据字典
								</th>
								<th data-field="c_label" data-falign="center" data-valign="center" data-editable="true">
									标签
								</th>
								<th data-field="c_type"  data-events = "" data-formatter="formatterQueryType">
									组件类型
								</th>
								
								<th data-field="c_readonly"  data-events = "" data-formatter="formatterQueryReadOnly">
									只读
								</th>
								
								<th data-field="c_disabled"  data-events = "" data-formatter="formatterQueryDisabled">
									禁用
								</th>
								
								<th data-field="c_row"  data-falign="center" data-valign="center" data-editable="true" data-sortable="true">
									行
								</th>
								
								<th data-field="c_rank"  data-events = "eventQueryRank" data-formatter="formatterQueryRank">
									排列
								</th>
								<!-- <th data-field="c-type" data-formatter="cTypeFormatter" data-filter-control="select">
									组件类型
								</th> -->
							</tr>
						</thead>
					</table>
				</div>
			</div>
		
		<!-- 表单属性END -->
			
			<div ravo="rainbow_fx_layout_panel" class="panel panel-primary">
				<div class="panel-heading">
					<h4 ravo="rainbow_fx_bj" data-rainbow="caption">
						查询按钮接口绑定
					</h4>
				</div>
				<div class="panel-body">
					<table class="table table-hover" 
						id="table_query_buttons" 
						data-toggle="table"
						data-click-to-select="false" 
						data-show-export="false" 
						data-show-refresh="false"
						data-show-toggle="false" 
						data-show-columns="false" 
						data-pagination="true"
						data-search="false" 
						data-detail-view="true"
						data-query-params="queryParams" 
						data-method="post" 
						data-undefined-text="**"
						data-unique-id="btnId" 
						ravo="rainbow_fx_bj">
						<thead style="">
							<tr>
								<th data-field="btnId">
									按钮ID
								</th>
								<th data-field="btnName">
									按钮名称
								</th>
								<th data-field="interface"  data-events = "" data-formatter="formatterQueryButtonTran">
									交易绑定
								</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<!-- 查询按钮接口绑定 -->
		</div>
	</div>
</div>

<div id="querytoolbar" >
	<label for="inputEmail3" class="control-label">
		查询按钮：
	</label>
	<select id="query_buttons" data-role="multiselect" multiple='multiple'>
		<option value="query">
			查询按钮
		</option>
		<option value="delete">
			删除按钮
		</option>
		<option value="update">
			修改按钮
		</option>
	</select>
</div>

<div>
</div>

</body>
</html>
