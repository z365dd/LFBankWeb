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
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<!-- JQUERY -->
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-3.6.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-migrate-3.4.0.min.js" charset="utf-8"></script><script type="text/javascript" src="<%=basePath%>/b_base/util.js" charset="utf-8"></script>
<!-- /JQUERY -->

<!-- DatetimePicker -->
<link href="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<!-- /DatetimePicker -->

<!-- BOOTSTRAP -->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap-style-cms.css"/>
<script type="text/javascript" src="<%=basePath%>/b_base/bootstrap-3.4.1/js/bootstrap.js" charset="utf-8"></script>
<!-- /BOOTSTRAP -->
<!-- checkboxes -->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/checkboxes/css/build.css"/>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/font-awesome/css/font-awesome.min.css"/>
<!-- /checkboxes -->
<!-- Self reference JS-->
<!--<script type="text/javascript" src="<%=basePath%>/b_ide/generate/autoQuery.js" charset="utf-8"></script> 查询 -->
<!--<script type="text/javascript" src="<%=basePath%>/b_ide/generate/autoForm.js" charset="utf-8"></script> 表单 -->
<!--<script type="text/javascript" src="<%=basePath%>/b_ide/generate/autoDatagrid.js" charset="utf-8"></script> 表格 -->
<script type="text/javascript" src="<%=basePath%>/b_ide/generate/autoFrame.js" charset="utf-8"></script><!-- 主页 -->
<!--<script type="text/javascript" src="<%=basePath%>/b_ide/generate/autoHandler.js" charset="utf-8"></script> 格式化后的数据转换成DOM -->
<script type="text/javascript" src="<%=basePath%>/b_ide/generate/autoSave.js" charset="utf-8"></script><!-- 格式化后的数据转换成DOM -->
<script type="text/javascript" src="<%=basePath%>/b_ide/generate/moduleChoose.js" charset="utf-8"></script><!-- 模板选择 -->
<script type="text/javascript" src="<%=basePath%>/b_ide/generate/interfaceChoose.js" charset="utf-8"></script><!-- 接口选择 -->
<!-- LayoutIt bootstrap -->
<!-- table-->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.css"/>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/editable/bootstrap-editable.css"/>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/bootstrap-table-export.js" charset="utf-8"></script>
<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/locale/bootstrap-table-zh-CN.js"/></script>
<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/rgwgit-tableExport.js"/></script>
<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/editable/bootstrap-table-editable.js"/></script>
<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/editable/bootstrap-editable.js"/></script>
<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/filter-control/bootstrap-table-filter-control.js"/></script>
<!-- /table-->

<!-- Validator -->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrapvalidator/css/bootstrapValidator.min.css"/>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrapvalidator/js/bootstrapValidator.min.js" charset="utf-8"></script>
<!-- /Validator -->

<!-- Multiselect -->
<!--  
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/css/bootstrap-multiselect.css"/>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/bootstrap-multiselect-collapsible-groups.js"></script>
-->
<!-- /Multiselect -->

<!-- Multiselect -->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/css/bootstrap-multiselect.css"/>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/jquery.multiselectcus.js"></script>
<!-- /Multiselect -->

<!-- Chart Custom -->
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/ChartCustom.js"></script>
<!-- /Chart Custom -->

<!-- TREE VIEW -->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-treeview-master/src/css/bootstrap-treeview.css"/>
<script src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-treeview-master/src/js/bootstrap-treeview.js"></script>
<!-- /TREE VIEW -->

<!-- TABS -->
 
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/tabs/js/bootstrap-tabs.js"></script>
<!-- 
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/tabs/css/bootstrap-tabs.css"/>
-->
<!-- /TABS -->

<!-- ECHARTS -->
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/echarts-master/dist/echarts.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/echarts-master/dist/demo/demos.js"></script>
<!-- /ECHARTS -->

<!-- ystep -->
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/ystep-master/js/ystep.js"></script>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/ystep-master/css/ystep.css"/>
<!-- /ystep -->

<!-- file Input -->
<link href="<%=basePath%>/b_ide/bootstrap/module/FileInput/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>/b_ide/bootstrap/module/FileInput/js/fileinput.js" type="text/javascript"></script>
<script src="<%=basePath%>/b_ide/bootstrap/module/FileInput/js/locales/zh.js" type="text/javascript"></script>
<!-- /file Input -->

<!-- ace jseditor.jsp -->
<script src="<%=basePath%>/b_ide/bootstrap/module/ace/src-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath%>/b_ide/bootstrap/module/ace/src-noconflict/ext-language_tools.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath%>/b_ide/bootstrap/module/jsformat/jsformat.js" charset="utf-8" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/jsformat/htmlformat.js" charset="utf-8"></script>


<title>自动化生成交易</title>

<style type="text/css">
</style>
</head>
<body style="padding-top: 75px; overflow-x : hidden;background-color: #fff">
<div class="">

	<!-- 菜单栏 BEG -->
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
			<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
			  <div class="container-fluid">
			    <!-- Brand and toggle get grouped for better mobile display -->
			    <div class="navbar-header">
			      <a class="navbar-brand" >AUTOTRADE</a>
			    </div>
			    
			    <!-- Collect the nav links, forms, and other content for toggling -->
			    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-2">
			    	<!-- <form class="navbar-form " role="search"> -->
					    <div class="ystep1" style="margin-top: -10px;padding-bottom: 20px"></div>
			    	 <!-- </form> -->
			    </div>
			    
			    <div class="collapse navbar-collapse vertical-center" id="bs-example-navbar-collapse-1" style="left:60%">
					<form class="navbar-form navbar-right" role="search"> 
						<button id="prestep" ravo="rainbow_fx"  type="button" class="btn btn-default">
							上一步
						</button>
						
						<button id="nextstep" ravo="rainbow_fx"  type="button" class="btn btn-default">
							下一步
						</button>
						   
						<button id="btnId_293329" ravo="rainbow_fx"  type="button" class="btn btn-default">
							界面预览
						</button>
						
						<button id="btnId_ace" ravo="rainbow_fx"  type="button" class="btn btn-default">
							代码预览
						</button>
						
						<button id="btnId_ace_po" ravo="rainbow_fx"  type="button" class="btn btn-default">
							PO预览
						</button>
						
						<button id="btnId_ace_action" ravo="rainbow_fx"  type="button" class="btn btn-default">
							ACTION预览
						</button>
						
						<button id="btnId_html" ravo="rainbow_fx"  type="button" class="btn btn-default">
							HTML预览
						</button>
						
						<button id="btnId_save" ravo="rainbow_fx"  type="button" class="btn btn-default">
							保存
						</button>
			        </form> 
			    </div><!-- /.navbar-collapse -->
			  </div><!-- /.container-fluid -->
			</nav>
		</div>
	</div>
	<!-- 菜单栏 END -->
	
	<!--  步骤栏  BEG-->
	<!-- <div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-4 column">
			<div ravo="rainbow_fx_layout" class="row clearfix">
				
			</div>
		</div>
		<div class="col-md-6 column">
			<div style="height:90px" class="column">
				<div class="ystep1"></div>
				</div>
		</div>
		<div class="col-md-2 column">
			<div ravo="rainbow_fx_layout" class="row clearfix">
				<div class="col-md-3 column">
					<button id="prestep" class="step-button">
						<i aria-hidden="true"
							class="glyphicon glyphicon-arrow-left"></i>
					</button>
					<div class="step-text-p">上一步</div>
				</div>
				<div class="col-md-3 column">
					<button id="nextstep" class="step-button">
						<i aria-hidden="true"
							class="glyphicon glyphicon-arrow-right"></i>
					</button>
					<div class="step-text-p">下一步</div>
				</div>
			</div>
		</div>
	</div> -->
	<!--  步骤栏  END-->
	
	<!--  工作区  BEG-->
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
			<div ravo="rainbow_fx_layout_tab" class="tabbable" id="navigations">
				<ul data-closeable="true" style="display: none;" data-show-header="true"
				class="nav nav-tabs" data-toggle="tabs" id="tabs-505781">
					<li class="dropdown pull-right tabdrop hide">
						<a padding="10px 10px 10px 10px" class="dropdown-toggle" data-toggle="dropdown"
						href="#">
							<i class="glyphicon glyphicon-align-justify">
							</i>
							<b class="caret">
							</b>
						</a>
						<ul class="dropdown-menu">
						</ul>
					</li>
					<li class="active">
						<a aria-expanded="false" href="#panel-182507" data-toggle="tab">
							step1
						</a>
					</li>
					<li class="">
						<a aria-expanded="false" href="#panel-182506" data-toggle="tab">
							step2
						</a>
					</li>
					<li class="">
						<a aria-expanded="false" href="#panel-883310" data-toggle="tab">
							step3
						</a>
					</li>
					<li>
						<div>
						</div>
					</li>
					<li class="">
						<a aria-expanded="false" href="#panel-371915" data-toggle="tab" contenteditable="true">
							step4
						</a>
					</li>
					<li>
						<div>
						</div>
					</li>
					<li class="">
						<a aria-expanded="true" href="#panel-405592" data-toggle="tab" contenteditable="true">
							step5
						</a>
					</li>
					<li class="">
						<a aria-expanded="true" href="#panel-883311" data-toggle="tab" contenteditable="true">
							step6
						</a>
					</li>
				</ul>
				<div class="tab-content">
					<!-- 接口选择 -->
					<div class="tab-pane active" id="panel-182507">
						<jsp:include flush="true" page="interfaceChoose.jsp"></jsp:include>
					</div>
					<!-- 模板选择 -->
					<div class="tab-pane" id="panel-182506">
						<jsp:include flush="true" page="moduleChoose.jsp"></jsp:include>
					</div>
					<!-- 查询条件  -->
					<div class="tab-pane" id="panel-883310">
						<jsp:include flush="true" page="error.jsp"></jsp:include> 
					</div>
					<!-- 查询表格 -->
					<div class="tab-pane" id="panel-371915">
						<jsp:include flush="true" page="error.jsp"></jsp:include>
					</div>
					<!-- 生成表单  -->
					<div class="tab-pane" id="panel-405592">
						<jsp:include flush="true" page="error.jsp"></jsp:include> 
					</div>
					<div class="tab-pane" id="panel-883311">
						<jsp:include flush="true" page="autoSave.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--  工作区  END-->
</div>

<div id="myModal" class="modal  bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg" style="width: 90%">
    <div class="modal-content" >
	   	  <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="myModalLabel">预览</h4>
	      </div>
	      <div class="modal-body" id="domContent" style="margin-left:20px;" >
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn-primary">保存</button>
	      </div>
    </div>
  </div>
</div>

<!-- JS预览  -->
<div id="aceModal" class="modal  bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg" style="width: 90%">
    <div class="modal-content" >
	   	  <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" >JS文件预览</h4>
	      </div>
	      <div class="modal-body"  >
	      	<pre id="editor" style="min-height: 600px">
			</pre>
	      </div>
    </div>
  </div>
</div>

<!-- po预览 -->
<div id="acePoModal" class="modal  bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg" style="width: 90%">
    <div class="modal-content" >
	   	  <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" >PO文件预览</h4>
	      </div>
	      <div class="modal-body"  >
	      	<pre id="editorPo" style="min-height: 600px">
			</pre>
	      </div>
    </div>
  </div>
</div>

<!-- action预览 -->
<div id="aceActionModal" class="modal  bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg" style="width: 90%">
    <div class="modal-content" >
	   	  <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" >ACTION文件预览</h4>
	      </div>
	      <div class="modal-body"  >
	      	<pre id="editorAction" style="min-height: 600px">
			</pre>
	      </div>
    </div>
  </div>
</div>

<!-- html预览 -->
<div id="htmlModal" class="modal  bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg" style="width: 90%">
    <div class="modal-content" >
	   	  <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" >HTML内容预览</h4>
	      </div>
	      <div class="modal-body"  >
	      	<pre id="editorHtml" style="min-height: 600px ">
			</pre>
	      </div>
    </div>
  </div>
</div>

<div id="saveModal" class="modal  bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg" style="width: 60%">
    <div class="modal-content" >
	   	  <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" >保存</h4>
	      </div>
	      <div class="modal-body"  >
	      	<form id="formId_save" ravo="rainbow_fx_layout_bd" class="form-horizontal"pourl="">
				<div ravo="rainbow_fx" class="form-group">
					<label for="inputEmail3" class="control-label col-sm-4">
						保存路径：
					</label>
					<div class="col-sm-4">
						<input class="form-control" name="savePath" placeholder="默认路径是WebRoot" type="text">
					</div>
				</div>
				<div ravo="rainbow_fx" class="form-group">
					<label for="inputEmail3" class="control-label col-sm-4">
						保存文件名称：
					</label>
					<div class="col-sm-4">
						<input class="form-control" name="saveFileName" placeholder="" type="text">
					</div>
				</div>
				<div ravo="rainbow_fx" class="form-group">
					<label for="inputEmail3" class="control-label col-sm-4">
						Title：
					</label>
					<div class="col-sm-4">
						<input class="form-control" name="titleName" placeholder="" type="text">
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix">
					<div class="col-md-3 column">
					</div>
					<div class="col-md-3 column rainbow-select">
						<button id="btnId_754957" ravo="rainbow_fx" type="button" class="btn btn-block btn-primary">
							保存为JSP
						</button>
					</div>
					<div class="col-md-3 column">
						
					</div>
					<div class="col-md-3 column">
					</div>
				</div>
			</form>
	      </div>
    </div>
  </div>
</div>

<div hidden="true" id="domsave">
</div>

</body>
</html>
