<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%> <!-- JSTL  -->
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
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Cache-Control" content="no-store">

<script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}';</script>

<!-- JQUERY -->
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-3.6.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-migrate-3.4.0.min.js" charset="utf-8"></script><script type="text/javascript" src="<%=basePath%>/b_base/jqueryForm.js" charset="utf-8"></script>
<!-- /JQUERY -->

<!-- DatetimePicker -->
<script src="<%=basePath%>/b_base/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<!-- /DatetimePicker -->

<!-- Bootstrap datetime -->
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<link href="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen"/>


<!-- BOOTSTRAP -->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap-theme.css"/>
<script type="text/javascript" src="<%=basePath%>/b_base/bootstrap-3.4.1/js/bootstrap.js" charset="utf-8"></script>
<!-- /BOOTSTRAP -->

<!-- LayoutIt bootstrap -->
<!-- table-->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.css"/>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/bootstrap-table-export.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/tableExport.js" charset="utf-8"></script>
<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/locale/bootstrap-table-zh-CN.js"/></script>
<script type="text/javascript"  src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/tableExport.js"/></script>
<!-- /table-->

<!-- FileInput Css-->
<link href="<%=basePath%>/b_ide/bootstrap/module/FileInput/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>/b_ide/bootstrap/module/Chart/Chart.js"></script>
<script src="<%=basePath%>/b_ide/bootstrap/module/Chart/src/Chart.Doughnut.js"></script>
<script src="<%=basePath%>/b_ide/bootstrap/module/Treeview/js/bootstrap-treeview.js"></script>
<script src="<%=basePath%>/b_ide/bootstrap/module/FileInput/js/fileinput.js" type="text/javascript"></script>
<script src="<%=basePath%>/b_ide/bootstrap/module/FileInput/js/locales/zh.js" type="text/javascript"></script>
<!-- /FileInput -->

<!-- Validator -->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrapValidator/css/bootstrapValidator.min.css"/>
<script type="text/javascript" src="<%=basePath%>/b_base/bootstrapValidator/js/bootstrapValidator.min.js" charset="utf-8"></script>
<!-- /Validator -->

<!-- Multiselect -->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/css/bootstrap-multiselect.css"/>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/jquery.multiselectcus.js"></script>
<!-- /Multiselect -->

<!-- TABS -->
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/tabs/js/bootstrap-tabs.js"></script>
<!-- /TABS -->

<!-- ystep -->
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/ystep-master/js/ystep.js"></script>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/ystep-master/css/ystep.css"/>
<!-- /ystep -->

<!-- JBox -->
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.src.js" type="text/javascript"></script>

<!-- UEditor -->
<script src="${ctxStatic}/ueditor/ueditor.config.js" type="text/javascript"></script>
<script src="${ctxStatic}/ueditor/ueditor.all.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/ueditor/ueditor.parse.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js" type="text/javascript"></script>

<!-- FontIcon -->
<link rel="stylesheet" href="${ctxStatic}/mainframe/fonticon/iconfont.css">

<script type="text/javascript" src="<%=basePath%>/b_base/common/smartweb.js"></script>


<!-- check --> 
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/para/center/checkForUptCnt.js"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/common/formCheck.js"></script>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/common/formCheck.css"/>

<!-- Self reference JS-->
<script type="text/javascript" src="<%=basePath%>/b_base/views/starring/para/center/centerAddForm.js" charset="utf-8"></script>

<!-- change skin -->
<link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css" type="text/css" rel="stylesheet" />

<title>Insert title here</title>
</head>
<body>
<div id="messageBox" class="alert alert-success hide"><button data-dismiss="alert" class="close">×</button><span id="messageContent">操作提示信息</span></div>
<!-- view start -->
<div class="">
	<div ravo="rainbow_fx_layout" class="row clearfix">
		<div class="col-md-12 column">
			<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl="" id="addForm">
				<div ravo="rainbow_fx_layout_fold" class="panel-group" id="myAccordion">
					<div class="panel panel-default">
						<div class="panel-heading">
							<span class="panel-title collapsed">基本信息</span>
							<a class="btn  btn-xs btn-primary" data-toggle="collapse" data-parent="#myAccordion"
							href="#collapseOne" contenteditable="false" aria-expanded="false">
								显示/隐藏	
							</a>
						</div>
						<div id="collapseOne" class="panel-collapse collapse" aria-expanded="false"
						style="height: 0px;">
							<div class="panel-body" contenteditable="false">
								<div ravo="rainbow_fx_layout" class="row clearfix" >
									<div class="col-md-12 column">
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label control-label"
											style="visibility:visible">
												中文名称：
											</label>
											<div class="col-sm-2">
												<input type="text" class="form-control" placeholder="" id="chName" name="chName"
												 check-minlength="2" maxlength="10" check-chinese="true" check-empty="true">
											</div>
										</div>
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label control-label"
											style="visibility:visible">
												英文名称：
											</label>
											<div class="col-sm-2">
												<input type="text" class="form-control" placeholder="" id="engName" name="engName"
												 check-minlength="2" maxlength="18" check-alphanumericsymbols="true" check-empty="true">
											</div>
										</div>
										<div ravo="rainbow_fx_checkbox" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label">
												缓存模式：
											</label>
											<div class="col-sm-5" >
												<input type="text" name="cacheMode" style="display: none;" value="00">
												<select data-role="multiselect" id="cacheMode" name="cacheMode" >
													<option value="00">
														ZooKeeper + Redis
													</option>
												</select>
											</div>
										</div>
										<!-- <div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label control-label"
											style="visibility:visible">
												配置文件路径：
											</label>
											<div class="col-sm-2">
												<input type="text" class="form-control" placeholder="/home/cachecenter/" id="basePath" name="basePath"
												 value="" check-empty="true">
											</div>
										</div> -->
										<div ravo="rainbow_fx" class="form-group">
											<label for="inputEmail3" class="col-sm-5 control-label control-label"
												   style="visibility:visible">
												是否为默认中心：
											</label>
											<div class="col-sm-2">
												<select data-role="multiselect" id="defaValFlg" name="defaValFlg" checkBtn="defaValFlg">
													<option value="">
														请选择
													</option>
													<option value="Y">
														是
													</option>
													<option value="N">
														否
													</option>
												</select>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<span class="panel-title collapsed">ZooKeeper配置信息</span>
							<a class="btn  btn-xs btn-primary" data-toggle="collapse" data-parent="#myAccordion"
							href="#collapseTwo" contenteditable="false" aria-expanded="false">
								显示/隐藏	
							</a>
							<span class="panel-title collapsed" style="margin-left:10px">提示：每个节点地址与端口组合都不能相同</span>
						</div>
						<div id="collapseTwo" class="panel-collapse collapse" aria-expanded="false">
							<div class="panel-body" contenteditable="false">
								<div ravo="rainbow_fx_layout" class="row clearfix" id="zkInfo">
									<div class="col-md-12 column zkNode">
										<div ravo="rainbow_fx_layout" class="row clearfix zkNode-add" data-node="zk_0" style="display: none;">
											<div class="col-md-12 column zk_add_1 nodeinfo">
												<div ravo="rainbow_fx_layout" class="row clearfix">
													<div class="col-md-1 column">
													</div>
													<div class="col-md-10 column">
														<p>
															<span class="label panel-title" style="font-size:14px;color:#328ee3">节点:</span>
															<button ravo="rainbow_fx" type="button" class="btn btn-primary" contenteditable="false" onclick="hide(this)">
																显示/隐藏
															</button>
															<button ravo="rainbow_fx" type="button" class='btn btn-xs btn-default' onclick="rmNode(this)">
																<span class='glyphicon glyphicon-minus' style='margin-top:5px'></span>删除节点
															</button>
														</p>
													</div>
													<div class="col-md-1 column">
													</div>
												</div>
												<div ravo="rainbow_fx_layout" class="row clearfix zk_node_info nodeShow">
													<div class="col-md-12 column zk_add_1">
														<input type="text" style="display:none" class="form-control" placeholder="" id="id" name="id" >
														<div ravo="rainbow_fx_layout" class="row clearfix">
															<div class="col-md-12 column">
																<div ravo="rainbow_fx_layout" class="row clearfix">
																	<div class="col-md-1 column">
																	</div>
																	<div class="col-md-4 column"> 
																		<div ravo="rainbow_fx" class="form-group">
																			<label for="inputEmail3" class="col-sm-3 control-label">
																				节点地址：
																			</label>
																			<div class="col-sm-5">
																				<input type="text" class="form-control" placeholder="" id="ip" name="ip" check-ipaddress="true" >
																			</div>
																		</div>
																	</div>
																	<div class="col-md-4 column">
																		<div ravo="rainbow_fx" class="form-group">
																			<label for="inputEmail3" class="col-sm-3 control-label">
																				节点端口：
																			</label>
																			<div class="col-sm-5">
																				<input type="text" class="form-control" placeholder="" id="port" name="port" min="1" max="65535" check-integer="true">
																			</div>
																		</div>
																	</div>
																	<div class="col-md-3 column">
																		<div ravo="rainbow_fx" class="form-group" style="display:none">
																			<label for="inputEmail3" class="col-sm-3 control-label">
																				节点序号：
																			</label>
																			<div class="col-sm-5">
																				<input type="text" class="form-control" placeholder="" id="zkId" name="zkId">
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div ravo="rainbow_fx_layout" class="row clearfix" >
										<div class="col-md-1 column">
										</div>
										<div class="col-md-10 column">
											<a href="javascript:;" name="add" class="btn btn-xs btn-primary" data-add="zkNode_node"; data-toggle="tooltip" data-placement="top"><span class="glyphicon glyphicon-plus" style="margin-top:5px">添加节点</span></a>
										</div>
										<div class="col-md-1 column">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<span class="panel-title collapsed">Redis配置信息</span>
							<a class="btn  btn-xs btn-primary" data-toggle="collapse" data-parent="#myAccordion"
							href="#collapseThree" contenteditable="false" aria-expanded="false">
								显示/隐藏	
							</a>
							<span class="panel-title collapsed" style="margin-left:10px">提示：所有IP与端口不能有相同的组合</span>
						</div>
						<div id="collapseThree" class="panel-collapse collapse" aria-expanded="false">
							<div class="panel-body" contenteditable="false">
								<div ravo="rainbow_fx_layout" class="row clearfix" id="rsInfo">
									<div class="col-md-12 column rsNode">
										<div ravo="rainbow_fx_layout" class="row clearfix" style="display: none">
											<div class="col-md-1 column">
											</div>
											<div class="col-md-4 column">
												<div ravo="rainbow_fx" class="form-group">
													<label for="inputEmail3" class="col-sm-4 control-label">
														节点超时时间(毫秒)：
													</label>
													<div class="col-sm-4">
														<input type="text" class="form-control" placeholder="" id="rsTimeOutTime" name="rsTimeOutTime" min="1" max="65535" data-bv-="true" check-integer="true">
													</div>
												</div>
											</div>
											
											<div class="col-md-2 column">
												<div ravo="rainbow_fx_checkbox" class="form-group">
													<label for="inputEmail3" class="col-sm-1 control-label">
													</label>
													<div class="col-sm-8">
														<div class="checkbox">
															<label style="visibility:visible">
																<input type="checkbox" checked="checked" id="rsClstrSwitchFlg" name="rsClstrSwitchFlg" value="Y">
																集群模式
															</label>
														</div>
													</div>
												</div>
											</div>
											<div class="col-md-2 column">
												<div ravo="rainbow_fx_checkbox" class="form-group">
													<label for="inputEmail3" class="col-sm-1 control-label">
													</label>
													<div class="col-sm-8">
														<div class="checkbox">
															<label style="visibility:visible">
																<input type="checkbox" checked="checked" id="rsPrtctSwitchFlg" name="rsPrtctSwitchFlg" value="N">
																保护模式
															</label>
														</div>
													</div>
												</div>
											</div>
											<div class="col-md-3 column">
											</div>
										</div>

										<div ravo="rainbow_fx_layout" class="row clearfix rsNode-add nodeinfo" data-node="rs_0" style="display: none;">
											<div class="col-md-12 column ">
												<div ravo="rainbow_fx_layout" class="row clearfix" id="nodeTitle1">
													<div class="col-md-1 column">
													</div>
													<div class="col-md-10 column">
														<p>
															<span class="label panel-title" style="font-size:14px;color:#328ee3">节点:</span>
															<button ravo="rainbow_fx" type="button" class="btn btn-primary" contenteditable="false" onclick="hide(this)">
																显示/隐藏
															</button>
															<button ravo="rainbow_fx" type="button" class='btn btn-xs btn-default' onclick="rmNode(this)">
																<span class='glyphicon glyphicon-minus' style='margin-top:5px'></span>删除节点
															</button>
														</p>
													</div>
													<div class="col-md-1 column">
													</div>
												</div>
												<div ravo="rainbow_fx_layout" class="row clearfix nodeShow" data-nodeinfo="rs_0">
													<div class="col-md-12 column">
														<div ravo="rainbow_fx_layout" class="row clearfix">
															<div class="col-md-1 column">
															</div>
															<div class="col-md-1 column">
																<p><span class="label pull-right" style="font-size:12px;color:#328ee3;margin-top:10px">主节点</span></p>
															</div>
															<div class="col-md-4 column">
																<div ravo="rainbow_fx" class="form-group">
																	<label for="inputEmail3" class="col-sm-4 control-label">
																		节点地址：
																	</label>
																	<div class="col-sm-5">
																		<input type="text" class="form-control" placeholder="" id="rsIp" name="rsIp" check-ipaddress="true" >
																	</div>
																</div>
															</div>
															<div class="col-md-4 column">
																<div ravo="rainbow_fx" class="form-group">
																	<label for="inputEmail3" class="col-sm-4 control-label">
																		节点端口：
																	</label>
																	<div class="col-sm-5">
																		<input type="text" class="form-control" placeholder="" id="rsPort" name="rsPort" min="1" max="65535" data-bv-="true" check-integer="true">
																	</div>
																</div>
															</div>
															<div class="col-md-2 column">
															</div>
														</div>
														<div ravo="rainbow_fx_layout" class="row clearfix" style="display: none">
															<div class="col-md-2 column">
															</div>
															<div class="col-md-4 column">
																<div ravo="rainbow_fx" class="form-group">
																	<label for="inputEmail3" class="col-sm-4 control-label">
																		哈希槽：
																	</label>
																	<div class="col-sm-5">
																		<input type="text" class="form-control" placeholder="不能小于上一个哈希槽值" id="startSlot" name="startSlot" check-empty="true" data-bv-="true" check-integer-pos-neg="true">
																	</div>
																</div>
															</div>
															<div class="col-md-4 column">
																<div ravo="rainbow_fx" class="form-group">
																	<label for="inputEmail3" class="col-sm-4 control-label" style="padding-right:30px">
																		~
																	</label>
																	<div class="col-sm-5">
																		<input type="text" class="form-control" placeholder="不能大于16383" id="endSlot" name="endSlot" check-empty="true" data-bv-="true" min="1" max="16383" check-integer="true">
																	</div>
																</div>
															</div>
															<div class="col-md-2 column">
															</div>
														</div>
														<div class="slaveNode">
															<div ravo="rainbow_fx_layout" class="row clearfix slaveNode-add" data-slavenode="rs_0" data-slave="slave_1">
																<div class="col-md-1 column">
																</div>
																<div class="col-md-1 column">
																	<p><span class="label pull-right" contenteditable="true" ravo="rainbow_fx_bj" style="font-size:12px;color:#328ee3;margin-top:10px">从节点</span></p>
																</div>
																<div class="col-md-4 column">
																	<div ravo="rainbow_fx" class="form-group">
																		<label for="inputEmail3" class="col-sm-4 control-label">
																			节点地址：
																		</label>
																		<div class="col-sm-5">
																			<input type="text" class="form-control" placeholder="" id="rsSlaveIp" name="rsSlaveIp" check-ipaddress="true" >
																		</div>
																	</div>
																</div>
																<div class="col-md-4 column">
																	<div ravo="rainbow_fx" class="form-group">
																		<label for="inputEmail3" class="col-sm-4 control-label">
																			节点端口：
																		</label>
																		<div class="col-sm-5">
																			<input type="text" class="form-control" placeholder="" id="rsSlavePort" name="rsSlavePort" min="1" max="65535" data-bv-="true" check-integer="true">
																		</div>
																	</div>
																</div>
																<div class="col-md-1 column slaveBtn">
																	<a href="javascript:;" class="btn btn-xs btn-primary" data-toggle="tooltip" data-placement="top" onclick="addSlave(this)"><span class="glyphicon glyphicon-plus" style="margin-top:5px">添加从节点</span></a>
																</div>
																<div class="col-md-1 column">
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div ravo="rainbow_fx_layout" class="row clearfix">
										<div class="col-md-1 column">
										</div>
										<div class="col-md-10 column">
											<a href="javascript:;" name="add" class="btn btn-xs btn-primary" data-add="rsNode_node" data-toggle="tooltip" data-placement="top"><span class="glyphicon glyphicon-plus" style="margin-top:5px">添加主节点</span></a>
										</div>
										<div class="col-md-3 column">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div ravo="rainbow_fx_layout" class="row clearfix" >
					<div class="col-md-4 column">
					<input type="hidden" id="zkJson" name='zkJson' value="">
					<input type="hidden" id="rsJson" name='rsJson' value="">
					</div>
					<div class="col-md-4 column">
						<div ravo="rainbow_fx_layout" class="row clearfix" >
							<div class="col-md-6 column">
								<button ravo="rainbow_fx" type="button" class="btn btn-primary" contenteditable="false" id="saveBtn">
									保存
								</button>
							</div>
							<div class="col-md-6 column">
								<button ravo="rainbow_fx" type="button" class="btn btn-default" contenteditable="false" id="cancleBtn">
									返回
								</button>
							</div>
						</div>
					</div>
					<div class="col-md-4 column">
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<!--customer_code_beg-->

<!--customer_code_end-->
<!-- view end -->
</body>
</html>
