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
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
			<div ravo="rainbow_fx_layout_panel" class="panel panel-primary">
				<div class="panel-heading">
					<h4 ravo="rainbow_fx_bj" data-rainbow="caption">
						表格属性
					</h4>
				</div>
				<div class="panel-body">
					<form id="formId_datagrid_attr" ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="">
						<!-- 第一列 -->
						<div class="col-md-2">
							<div class="thumbnail">
								<h5 ravo="rainbow_fx_bj" class="rainbow-select text-center text-info" data-rainbow="caption">
									基本属性
								</h5>
								<div class="caption">
									<div ravo="rainbow_fx" class="form-group form-group-sm">
										<div class="col-sm-10">
											<div class="input-group">
												<div class="input-group-addon"  style="width: 95px">
													<span>
														高度
													</span>
												</div>
												<input name="height" prevtype="text" class="form-control" placeholder="200" type="text">
											</div>
										</div>
									</div>
									
								    <div ravo="rainbow_fx" class="form-group form-group-sm">
										<div class="col-sm-10">
											<div class="input-group">
												<div class="input-group-addon" style="width: 95px">
													<span>
														未定义文本
													</span>
												</div>
												<input name="undefinedText" prevtype="text" class="form-control rainbow-select" placeholder="Rainbow"
												type="text">
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- 第二列 -->	
						<div class="col-md-2">
							<div class="thumbnail">
								<h5 ravo="rainbow_fx_bj" class="rainbow-select text-center text-info" data-rainbow="caption">
									分页设置
								</h5>
								<div class="caption">
									<div ravo="rainbow_fx" class="form-group form-group-sm">
										<div class="col-sm-10">
											<div class="input-group">
												<div class="input-group-addon" style="width: 95px"><!--pageNumber -->
													<span>
														初始化页码
													</span>
												</div>
												<input prevtype="text" name="pageNumber" class="form-control" placeholder="1" type="text">
											</div>
										</div>
									</div>
									
									<div ravo="rainbow_fx" class="form-group form-group-sm">
										<div class="col-sm-10">
											<div class="input-group">
												<div class="input-group-addon" style="width: 95px"><!--pageSize -->
													<span>
														每页数量
													</span>
												</div>
												<input prevtype="text" name="pageSize" class="form-control" placeholder="1" type="text">
											</div>
										</div>
									</div>
									
									<div ravo="rainbow_fx" class="form-group form-group-sm">
										<div class="col-sm-10">
											<div class="input-group">
												<div class="input-group-addon" style="width: 95px"><!--[10, 25, 50, 100, All] -->
													<span>
														分页列表
													</span>
												</div>
												<input prevtype="text" name="pageList" class="form-control" placeholder="[10, 25, All]" type="text">
											</div>
										</div>
									</div>
									
									<div ravo="rainbow_fx" class="form-group form-group-sm">
										<div class="col-sm-10">
											<div class="input-group">
												<div class="input-group-addon" style="width: 95px"><!--[10, 25, 50, 100, All] -->
													<span>
														前一页
													</span>
												</div>
												<input prevtype="text" name="paginationPreText" class="form-control" placeholder="<" type="text">
											</div>
										</div>
									</div>
									
									<div ravo="rainbow_fx" class="form-group form-group-sm">
										<div class="col-sm-10">
											<div class="input-group">
												<div class="input-group-addon" style="width: 95px"><!--[10, 25, 50, 100, All] -->
													<span>
														后一页
													</span>
												</div>
												<input prevtype="text" name="paginationNextText" class="form-control" placeholder=">" type="text">
											</div>
										</div>
									</div>
									
									<div ravo="rainbow_fx" class="form-group form-group-sm">
										<div class="col-sm-10">
											<div class="input-group">
												<div class="input-group-addon" style="width: 95px"><!--[10, 25, 50, 100, All] -->
													<span>
														首页
													</span>
												</div>
												<input prevtype="text" name="paginationFirText" class="form-control" placeholder="<<" type="text">
											</div>
										</div>
									</div>
									
									<div ravo="rainbow_fx" class="form-group form-group-sm">
										<div class="col-sm-10">
											<div class="input-group">
												<div class="input-group-addon" style="width: 95px"><!--[10, 25, 50, 100, All] -->
													<span>
														尾页
													</span>
												</div>
												<input prevtype="text" name="paginationLastText" class="form-control" placeholder=">>" type="text">
											</div>
										</div>
									</div>
									
								</div>
							</div>
						</div>
						
						<!-- 第三列 -->
						<div class="col-md-2">
							<div class="thumbnail">
								<span>基本属性</span> -->
								<h5 ravo="rainbow_fx_bj" class="rainbow-select text-center text-info" data-rainbow="caption">
									基本设置
								</h5>
								<div class="caption">
									<!-- 第二列 -->
									<div class="checkbox checkbox-info">
									  <input id="classes" name="classes" type="checkbox" checked="checked">
								      <label for="classes">
								         	显示表格边框
								      </label>
								    </div>
								    
									<div class="checkbox checkbox-info">
									  <input id="striped" name="striped" type="checkbox">
								      <label for="striped">
								        	 显示条纹
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								      <input id="singleSelect" name="singleSelect" type="checkbox">
								      <label for="singleSelect">
								         	只允许选择一行
								      </label>
								    </div>
								    
								    <!--  
								    <div class="checkbox checkbox-info">
								    	<input id="clickToSelect" name="clickToSelect" type="checkbox">
								      <label for="clickToSelect">
								            单击行时选择checkbox/radiobox
								      </label>
								    </div>
								    -->
								    
								    <div class="checkbox checkbox-info">
								    <input id="toolbar_001" name="toolbar" type="checkbox">
								      <label for="toolbar_001">
								         设置toolbar
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								     <input id="checkboxHeader" name="checkboxHeader" type="checkbox" checked="checked">
								      <label for="checkboxHeader">
								         显示全选checkbox
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								      <input id="firstColCheckbox" name="firstColCheckbox" type="checkbox" checked="checked">
								      <label for="firstColCheckbox">
								         首列显示checkbox
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="firstColRadio" name="firstColRadio" type="checkbox" >
								      <label for="firstColRadio">
								            首列显示radio
								      </label>
								    </div>
								    
								</div>
							</div>
						</div>
						
						
						
							
						<!-- 第四列   可选显示组件-->
						<div class="col-md-2">
							<div class="thumbnail">
								<h5 ravo="rainbow_fx_bj" class="rainbow-select text-center text-info" data-rainbow="caption">
									可选显示组件
								</h5>
								<div class="caption">
									<div class="checkbox checkbox-info">
										<input id="pagination" name="pagination" type="checkbox" checked="checked">
								      <label for="pagination">
								            显示分页
								      </label>
								    </div>
								    
								    <!--  
								    <div class="checkbox checkbox-info">
								    	<input id="search" name="search" type="checkbox" checked="checked">
								      <label for="search">
								         显示查询输入框
								      </label>
								    </div>
								    -->
								    
								    <div class="checkbox checkbox-info">
								    	<input id="showHeader" name="showHeader" type="checkbox" checked="checked">
								      <label for="showHeader">
								         显示表头
								      </label>
								    </div>
								    
								    <!--  
								    <div class="checkbox checkbox-info">
								    	<input id="showColumns" name="showColumns" type="checkbox" checked="checked">
								      <label for="showColumns">
								         显示下拉列表
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="showRefresh" name="showRefresh" type="checkbox" checked="checked">
								      <label for="showRefresh">
								         显示刷新按钮
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="showToggle" name="showToggle" type="checkbox" checked="checked">
								      <label for="showToggle">
								         显示切换按钮 
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="showPaginationSwitch" name="showPaginationSwitch" type="checkbox">
								      <label for="showPaginationSwitch">
								         显示分页按钮
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="cardView" name="cardView" type="checkbox">
								      <label for="cardView">
								         显示card视图
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="detailView" name="detailView" type="checkbox">
								      <label for="detailView">
								         显示export
								      </label>
								    </div>
								    -->
								</div>
							</div>
						</div>
							
							<!-- 第五列 -->
						<div class="col-md-4">
							<div class="thumbnail">
								<h5 ravo="rainbow_fx_bj" class="rainbow-select text-center text-info" data-rainbow="caption">
									组件的显示位置
								</h5>
								<div class="caption">
									
									<!--  
									<div class="form-group">
									  <label class="col-sm-4 control-label">搜索输入框：</label>
									  <div class="col-sm-8">
									  	<div class="radio radio-info radio-inline">
									  		<input id="searchAlignleft" type="radio" name="searchAlign"  value="left">
										  	<label for="searchAlignleft">
											   左
											</label>
									  	</div>
									  	<div class="radio radio-info radio-inline">
									  		<input id="searchAlignright" type="radio" name="searchAlign" checked="checked" value="right">
											<label for="searchAlignright">
											   右
											</label>
									  	</div>
									  </div>
									</div>
									-->
									
									<div class="form-group">
									  <label class="col-sm-4 control-label">按钮组：</label>
									  <div class="col-sm-8">
									  	<div class="radio radio-info radio-inline">
									  		<input id="buttonsAlignL" type="radio" name="buttonsAlign" value="left">
										  	<label for="buttonsAlignL">
											   左
											</label>
										</div>
										<div class="radio radio-info radio-inline">
											<input id="buttonsAlignR" type="radio" name="buttonsAlign" checked="checked" value="right">
											<label for="buttonsAlignR">
											   右
											</label>
										</div>
									  </div>
									</div>
									
									<div class="form-group">
									  <label class="col-sm-4 control-label">toolbar：</label>
									  <div class="col-sm-8">
									  	<div class="radio radio-info radio-inline">
									  		<input id="toolbarAlignL" type="radio" name="toolbarAlign" checked="checked" value="left">
										  	<label for="toolbarAlignL">
											   左
											</label>
										</div>
										<div class="radio radio-info radio-inline">
											<input id="toolbarAlignR" type="radio" name="toolbarAlign" value="right">
											<label for="toolbarAlignR">
											   右
											</label>
										</div>
									  </div>
									</div>
									
									<div class="form-group">
									  <label class="col-sm-4 control-label">分页描述对齐：</label>
									  <div class="col-sm-8">
									  	<div class="radio radio-info radio-inline">
									  		<input id="paginationDetailHAlignL" type="radio" name="paginationDetailHAlign" checked="checked" value="left">
										  	<label for="paginationDetailHAlignL">
											   左
											</label>
										</div>
										<div class="radio radio-info radio-inline">
											<input id="paginationDetailHAlignR" type="radio" name="paginationDetailHAlign"  value="right">
											<label for="paginationDetailHAlignR">
											   右
											</label>
										</div>
									  </div>
									</div>
									
									<div class="form-group">
									  <label class="col-sm-4 control-label">分页左右对齐：</label>
									  <div class="col-sm-8">
									  	<div class="radio radio-info radio-inline">
									  		<input id="paginationHAlignL" type="radio" name="paginationHAlign" value="left">
										  	<label for="paginationHAlignL">
											   左
											</label>
										</div>
										<div class="radio radio-info radio-inline">
											<input id="paginationHAlignR" type="radio" name="paginationHAlign" checked="checked" value="right">
											<label for="paginationHAlignR">
											   右
											</label>
										</div>
									  </div>
									</div>
									
									
									<div class="form-group">
									  <label class="col-sm-4 control-label">分页垂直对齐：</label>
									  <div class="col-sm-8">
									  	<div class="radio radio-info radio-inline">
									  		<input id="paginationVAlignT" type="radio" name="paginationVAlign"  value="top">
										  	<label for="paginationVAlignT">
											   顶部
											</label>
										</div>
										<div class="radio radio-info radio-inline">
											<input id="paginationVAlignB" type="radio" name="paginationVAlign" checked="checked" value="bottom">
											<label for="paginationVAlignB">
											   底部
											</label>
										</div>
										<div class="radio radio-info radio-inline">
											<input id="paginationVAlignBoth" type="radio" name="paginationVAlign"  value="both">
											<label for="paginationVAlignBoth">
											   中间
											</label>
										</div>
									  </div>
									</div>
									
								</div>
							</div>
						</div>
						
					</form>
				</div>
			</div>
			
			<!-- 表格事件BEG -->
			
			<div ravo="rainbow_fx_layout_panel" class="panel panel-primary">
				<div class="panel-heading">
					<h4 ravo="rainbow_fx_bj" data-rainbow="caption">
						表格事件
					</h4>
				</div>
				<div class="panel-body">
					<form id="formId_datagrid_events" ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="">
						<div class="col-md-3">
							<div class="thumbnail">
								<h5 ravo="rainbow_fx_bj" class="rainbow-select text-center text-info" data-rainbow="caption">
									单击事件
								</h5>
								<div class="caption">
									<!--  
									<div class="checkbox checkbox-info">
										<input id="onClickRow" name="onClickRow" type="checkbox" >
								      <label for="onClickRow">
								         	单击一行事件【onClickRow】
								      </label>
								    </div>
								    -->
								    <div class="checkbox checkbox-info">
								    	<input id="onDblClickRow" name="onDblClickRow" type="checkbox" > 
								      <label for="onDblClickRow">
								        双击一行事件【onDblClickRow】
								      </label>
								    </div>
								    <!--  
								    <div class="checkbox checkbox-info">
								    	<input id="onClickCell" name="onClickCell" type="checkbox" >
								      <label for="onClickCell">
								         单击单元格事件【onClickCell】
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onDblClickCell" name="onDblClickCell" type="checkbox" >
								      <label for="onDblClickCell">
								         双击单元格事件【onDblClickCell】
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onExpandRow" name="onExpandRow" type="checkbox" > 
								      <label for="onExpandRow">
								        单击展开细节监听事件【onExpandRow】
								      </label>
								    </div>
								    
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onCollapseRow" name="onCollapseRow" type="checkbox" >
								      <label for="onCollapseRow">
								         单击收起细节监听事件【onCollapseRow】
								      </label>
								    </div>
								    -->
								    
								</div>
							</div>
						</div>
						
						<div class="col-md-3">
							<!--  
							<div class="thumbnail">
								<h5 ravo="rainbow_fx_bj" class="rainbow-select text-center text-info" data-rainbow="caption">
									选择事件
								</h5>
								<div class="caption">
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onCheck" name="onCheck" type="checkbox" >
								      <label for="onCheck">
								         行选择事件【onCheck】
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onUncheck" name="onUncheck" type="checkbox" >
								      <label for="onUncheck">
								         取消行选择事件【onUncheck】
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onCheckAll" name="onCheckAll" type="checkbox" >
								      <label for="onCheckAll">
								         全选事件【onCheckAll】
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onUncheckAll" name="onUncheckAll" type="checkbox" >
								      <label for="onUncheckAll">
								         取消全选事件【onUncheckAll】
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onCheckSome" name="onCheckSome" type="checkbox" >
								      <label for="onCheckSome">
								         部分行选择事件【onCheckSome】
								      </label>
								    </div>
								    
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onUncheckSome" name="onUncheckSome" type="checkbox" >
								      <label for="onUncheckSome">
								         取消部分行选择事件【onUncheckSome】
								      </label>
								    </div>
								</div>
							</div>
							-->
						</div>
						
						<div class="col-md-3">
							<!--  
							<div class="thumbnail">
								<h5 ravo="rainbow_fx_bj" class="rainbow-select text-center text-info" data-rainbow="caption">
									表事件
								</h5>
								<div class="caption">
									
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onLoadSuccess" name="onLoadSuccess" type="checkbox" >
								      <label for="onLoadSuccess">
								         表加载成功事件【onLoadSuccess】
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onLoadError" name="onLoadError" type="checkbox" >
								      <label for="onLoadError">
								         表加载失败事件【onLoadError】
								      </label>
								    </div>
								    
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onPreBody" name="onPreBody" type="checkbox" >
								      <label for="onPreBody">
								         表呈现前事件【onPreBody】
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onPostBody" name="onPostBody" type="checkbox" >
								      <label for="onPostBody">
								         表呈现后事件【onPostBody】
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onResetView" name="onResetView" type="checkbox" >
								      <label for="onResetView">
								         表重置事件【onResetView】
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onRefreshOptions" name="onRefreshOptions" type="checkbox" >
								      <label for="onRefreshOptions">
								         表刷新时在真正摧毁之前的事件【onRefreshOptions】
								      </label>
								    </div>
								</div>
							</div>
							-->
						</div>
						
						<div class="col-md-3">
							<!--  
							<div class="thumbnail">
								<h5 ravo="rainbow_fx_bj" class="rainbow-select text-center text-info" data-rainbow="caption">
									其它
								</h5>
								<div class="caption">
								    <div class="checkbox checkbox-info">
								    	<input id="onColumnSearch" name="onColumnSearch" type="checkbox" >
								      <label for="onColumnSearch">
								         列查询监听事件【onColumnSearch】
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onPageChange" name="onPageChange" type="checkbox" >
								      <label for="onPageChange">
								         表切换分页事件【onPageChange】
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onSearch" name="onSearch" type="checkbox" >
								      <label for="onSearch">
								         表查询事件【onSearch】
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onToggle" name="onToggle" type="checkbox" >
								      <label for="onToggle">
								         表切换视图事件【onToggle】
								      </label>
								    </div>
								    
								    <div class="checkbox checkbox-info">
								    	<input id="onSort" name="onSort" type="checkbox" >
								      <label for="onSort">
								         列排序事件【onSort】
								      </label>
								    </div>
								</div>
							</div>
						</div>
						-->
						
					</form>
				</div>
			</div>
			
			<!-- 表格事件END  -->
			
			
			<!-- 表格列属性BEG -->
			<div ravo="rainbow_fx_layout_panel" class="panel panel-primary">
				<div class="panel-heading">
					<h4 ravo="rainbow_fx_bj" data-rainbow="caption">
						表格列属性
					</h4>
				</div>
				<!-- data-url="http://localhost:8080/smartweb/b_ide/generate/smartweb.json" -->
				<div class="panel-body">
					<table class="table table-hover" 
						id="datagrid_table" 
						data-toggle="table" 
						data-toolbar=""
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
						data-unique-id="ID" 
						ravo="rainbow_fx_bj">
						<thead style="">
							<tr>
								<th data-checkbox="true" data-field="state">
								</th>
								<th data-field="ID">
									ID
								</th>
								<th data-field="a_data_name" data-falign="center" data-valign="center">
									列域名
								</th>
								<th data-field="c_label" data-falign="center" data-valign="center" data-editable="true">
									列标题
								</th>
								
								
								<th data-field="data-sortable"  data-events = "formatterTableSortableEvent" data-formatter="formatterTableSortable">
									可排序
								</th>
								
								<th data-field="data-order"  data-events = "formatterTableOrderEvent" data-formatter="formatterTableOrder">
									降序
								</th>
								
								<th data-field="data-editable"  data-events = "formatterTableEditableEvent" data-formatter="formatterTableEditable">
									可编辑
								</th>
								
								<th data-field="data-formatter"  data-events = "formatterTableFormatterEvent" data-formatter="formatterTableFormatter">
									格式化
								</th>
								
								<th data-field="data-events"  data-width="50px" data-events = "formatterTableEventsEvent" data-formatter="formatterTableEvents">
									格式化后事件
								</th>
								
								<th data-field="data-visible"  data-events = "formatterTableVisibleEvent" data-formatter="formatterTableVisible">
									列隐藏
								</th>
								
								<th data-field="data-width" data-falign="center" data-valign="center" data-editable="true">
									列宽(%或px)
								</th>
								
								<th data-field="data-align" data-events = "eventsAlign" data-formatter="formatterAlign">
									水平对齐
								</th>
								
								<th data-field="data-valign" data-events = "eventsValign" data-formatter="formatterValign">
									竖直对齐
								</th>
								
								<th data-field="data-halign" data-events = "eventsHalign" data-formatter="formatterHalign">
									表头水平对齐
								</th>
								
								<th data-field="data-falign" data-events = "eventsFalign" data-formatter="formatterFalign">
									表脚水平对齐
								</th>
								
								<th data-field="data-checkbox"  data-events = "formatterTableCheckboxEvent" data-formatter="formatterTableCheckbox">
									checkbox
								</th>
								
							</tr>
						</thead>
					</table>
				</div>
			</div>
			
			<!-- 表格列属性END -->
			
		</div>
	</div>
</div>
</body>
</html>
