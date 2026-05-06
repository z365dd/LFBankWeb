<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!-- JSTL  -->
<%@ page language="java" import="java.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>单位选择</title>
</head>
<body>
	<div class="modal" id="modalEntrList">
		<div class="modal-dialog" style="">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						×
					</button>
					<h4 class="modal-title" id="modalDetailLabel" contenteditable="false">
						单位列表(双击选中)
					</h4>
				</div>
				<div class="modal-body">
					<form ravo="rainbow_fx_layout_bd" class="form-horizontal breadcrumb" pourl=""
					id="entrListForm" style="margin-top:10px;">
						<div ravo="rainbow_fx_layout" class="row clearfix">
							<div class="col-md-5 column">
								<div ravo="rainbow_fx" class="form-group">
									<div class="col-sm-12">
										<input type="text" class="form-control" placeholder="单位名称" id="modalEntrName"
											name="modalEntrName" autocomplete="off">
									</div>
								</div>
							</div>
							<div class="col-md-4 column">
								<shiro:haspermission name="anno">
									<button ravo="rainbow_fx" type="button" class="btn btn-info" contenteditable="false"
									id="modalQryBtn" data-original-title="" title="">
										查询
									</button>
									<button ravo="rainbow_fx" type="button" class="btn btn-info" contenteditable="false"
									id="modalResetBtn" data-original-title="" title="">
										重置
									</button>									
								</shiro:haspermission>
							</div>
						</div>
					</form>
					<table class="table table-hover" id="entrListTable" data-side-pagination="server"
					data-toggle="table" data-url="${ctx }/prod/oper/entrDemo/qry"
					ravo="rainbow_fx_bj" data-height="" data-undefined-text="" data-pagination="true"
					data-striped="true" data-single-select="true" data-query-params="queryParams2"
					data-first-load="true">
						<thead style="">
							<tr>
								<th data-field="entrNo">
									单位编号
								</th>
								<th data-field="entrName">
									单位名称
								</th>
								<th data-field="statStr">
									状态
								</th>								
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>	