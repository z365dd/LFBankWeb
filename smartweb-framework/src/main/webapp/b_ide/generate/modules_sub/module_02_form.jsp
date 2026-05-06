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
	
	<div class="row clearfix">
		<div class="col-md-12 column">
		<!-- 表单属性BEG -->
		<div  class="panel panel-primary">
				<div class="panel-heading">
					<h4  data-rainbow="caption">
						表单属性
					</h4>
				</div>
				<div class="panel-body">
					<form class="form-inline" role="form">
					  <div class="form-group">
					    <div class="input-group">
					      <div class="input-group-addon"> <span class="glyphicon glyphicon-th-list"></span>排列方式 </div>
					      <!-- <input class="form-control" type="email" placeholder="Enter email"> -->
					      <select id="selectId_form_attr_rank" data-role="multiselect" class="col-sm-7">
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
				     	   <select id="selectId_form_attr_size" data-role="multiselect" class="col-sm-7 rainbow-select">
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
				     	   <select id="selectId_form_attr_colNum" data-role="multiselect" class="col-sm-7">
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
				     	   <select id="selectId_form_attr_leftOcc" data-role="multiselect" class="col-sm-7">
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
				     	   <select id="selectId_form_attr_rightOcc" data-role="multiselect" class="col-sm-7">
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
			
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4  data-rainbow="caption">
						字段属性
					</h4>
				</div>
				<div class="panel-body">
					<table class="table table-hover" 
						id="table_form" 
						data-toggle="table"
						data-toolbar="#formtoolbar"
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
						>
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
								<th data-field="c_type"  data-events = "" data-formatter="formatterType">
									组件类型
								</th>
								
								<th data-field="c_readonly"  data-events = "" data-formatter="formatterReadOnly">
									只读
								</th>
								
								<th data-field="c_disabled"  data-events = "" data-formatter="formatterDisabled">
									禁用
								</th>
								
								<th data-field="c_must"  data-events = "" data-formatter="formatterMust">
									必输
								</th>
								
								<th data-field="c_row"  data-falign="center" data-valign="center" data-editable="true" data-sortable="true">
									行
								</th>
								
								<th data-field="c_rank"  data-events = "eventRank" data-formatter="formatterRank">
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
			
		</div>
	</div>
</div>

<div id="formtoolbar" >
	<label for="inputEmail3" class="control-label">
		表单按钮：
	</label>
	<select id="selectId_906550" data-role="multiselect" multiple='multiple'>
		<option value="add">
			新增按钮
		</option>
		<option value="remove">
			删除按钮
		</option>
		<option value="update">
			修改按钮
		</option>
		<option value="cancel">
			修改按钮
		</option>
	</select>
</div>

					<!-- <form id="form_row"  class="form-horizontal">
						<div class="col-md-3">
							<div class="thumbnail">
								
								<div class="caption">
									<div  class="form-group form-group-sm">         
					 					<div class="col-sm-11">
					 						<div class="input-group col-sm-11">         
					 							<div class="input-group-addon" style="width: 95px">        
					 								<span>           
					 									内容     
					 								</span>          
					 							</div>             
					 							<textarea class="form-control " style="height: 103px" name="content" >
					 							</textarea>        
					 						</div>               
					 					</div>
					 				</div>  
									
								</div>
							</div>
						</div>
						
						<div class="col-md-3">
							<div class="thumbnail">
								<div class="caption">
								
									<div class="form-group form-group-sm">
										<div class="col-sm-11">
									    <div class="input-group">
									       <div class="checkbox checkbox-info">
												<input id="notEmpty" name="notEmpty" type="checkbox" >
												<label for="notEmpty">
													不为空
												</label>
										    </div>
										    </div>
									    </div>
									</div>
									
									<div class="form-group form-group-sm">
										<div class="col-sm-11">
									    <div class="input-group">
									       <div class="checkbox checkbox-info">
												<input id="circle" name="circle" type="checkbox">
												<label for="circle">
													圆角
												</label>
										    </div>
										    </div>
									    </div>
									</div>
									
									<div class="form-group form-group-sm">
										<div class="col-sm-11">
									    <div class="input-group">
									       <div class="checkbox checkbox-info">
												<input id="inline" name="inline" type="checkbox">
												<label for="inline">
													内联
												</label>
										    </div>
										    </div>
									    </div>
									</div>
								
									<div class="form-group form-group-sm">
										<div class="col-sm-11">
									    <div class="input-group">
									       <div class="input-group-addon" style="width: 125px">颜色</div>
								     	   <select  name="colors" data-role="multiselect" class="rainbow-select">
												<option value="">
													Default
												</option>
												<option value="checkbox-primary">
													Primary
												</option>
												<option value="checkbox-success">
													Success
												</option>
												<option value="checkbox-info" selected="selected">
													Info
												</option>
												<option value="checkbox-warning">
													Warning
												</option>
												<option value="checkbox-danger">
													danger
												</option>
											</select>
									    </div>
									</div>
									</div>
								</div>
							</div>
						</div>
					</form> -->

<div>
</div>

</body>
</html>
