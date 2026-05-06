<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String webapp = request.getContextPath();
	String basePath =  webapp;
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JSEditer</title>
<!-- jQuery引入 -->
<script type="text/javascript"
	src="<%=basePath%>/b_base/jquery-1.8.3.min.js" charset="utf-8">
	
</script>

<!--Ztree引入-->
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/b_base/ztree/css/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript"
	src="<%=basePath%>/b_base/ztree/js/jquery.ztree.all-3.5.min.js"></script>



<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/icon.css" />
<script type="text/javascript"
	src="<%=basePath%>/b_base/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>

<!-- messenger -->
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/js/messenger.js" charset="utf-8"></script>

<!-- backspace key handler -->
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/js/backspace.js" charset="utf-8">
	
</script>


<style>
.nav_tools {
	height: 26px;
	background-color: #F4F4F4;
}
</style>

</head>

<body class="easyui-layout">

	<div data-options="region:'west',split:true,title:'扩展区'"
		style="width: 200px; padding: 10px; background-color: #FFFFFF;">
		<ul id="treeDemo" class="ztree" style="over-flow: auto;"></ul>
	</div>
	<div
		data-options="region:'east',split:true,collapsed:true,title:'待定'"
		style="width: 200px; padding: 10px;">
		<p><span>预留</span></p>
	</div>

	<div data-options="region:'center',title:'编辑区',iconCls:'icon-edit'">
		<iframe id="CSTEditerWindow" scrolling="yes" frameborder="0"
			src="<%=webapp%>/b_ide/bootstrap/module/ace/csteditor.jsp"
			style="width: 100%; height: 100%;"></iframe>
	</div>

	<script type="text/javascript">
	
	var messenger;
	$(function() {
		messenger = new Messenger('cstEditer', 'faceui');
		var CSTEditerWindow=document.getElementById('CSTEditerWindow');
		messenger.listen(function(msg) {
			if(typeof msg=="string"){
				if(msg==="savescs_cst"){
					sendMessage('Parent',msg);
				}else if(msg==="saverr_cst"){
					sendMessage('Parent',msg);
				}else if(msg==="cstchange"){
					sendMessage('Parent',msg);
				}else{
					consols.info("....");
				}
			}else{
				if(msg.message==="CSTURL"){
					var cstUrl=msg.value;
					console.info(msg.value+"====-=-=-=");
					sendMessage('CSTEditerWindow',msg);
				}
				else
					if(msg.message==="CSTSAVE"){
					console.info("cstwin");
					sendMessage('CSTEditerWindow',msg);
				}else if(msg.message==="SETURL"){
					console.info("SETURL");
					sendMessage('CSTEditerWindow',msg);
				}else{
					console.info("..*..");
				}
			}
		});
		
		messenger.addTarget(window.parent, 'Parent');
		
		messenger.addTarget(CSTEditerWindow.contentWindow, 'CSTEditerWindow');
		
		$(document).keypress(function(e){
            if(e.altKey && e.shiftKey&&e.which ==74){
            	e.preventDefault();
                e.stopPropagation();
                sendMessage('CSTEditerWindow','format');
            }
            else if (e.ctrlKey && e.which == 115) {
                e.preventDefault();
                e.stopPropagation();
                sendMessage('CSTEditerWindow','save');
            }
        });
		
	});
	
	
	  function sendMessage(name,changes) {
	        messenger.targets[name].send(changes);
	    }
	  
	  
	
		var setting = {
			view: {
				showIcon: showIconForTree
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onDblClick: zTreeOnDblClick
			}
		};

		var zNodes =[
			{ id:1, pId:0, name:"JQuery常用方法", open:true},
			
				{ id:11, pId:1, name:"ajax",open:true},
					{ id:111, pId:11, name:"$.post(url,[data],[callback],[type])"},
					{ id:112, pId:11, name:"$.get(url,[data],[callback],[type])"},
					{ id:113, pId:11, name:"load(url,[data],[callback])"},
					{ id:114, pId:11, name:"$.ajax(url,[settings])"},
					
				{ id:12, pId:1, name:"工具"},
					{ id:121, pId:12, name:"$.each(obj,[callback])"},
					{ id:122, pId:12, name:"$.parseJSON(json)"},
					{ id:123, pId:12, name:"$.browser"},
			
				{ id:13, pId:1, name:"Deferred"},
					{ id:131, pId:13, name:"def.done(donCal,[donCal])"},
					{ id:132, pId:13, name:"def.fail(failCal)"},
				
			
				{ id:14, pId:1, name:"Callbacks"},
			
					{ id:141, pId:14, name:"callbacks.add(callbacks)"},
					{ id:142, pId:14, name:"callbacks.disable()"},
					{ id:143, pId:14, name:"callbacks.empty()"},
					{ id:144, pId:14, name:"callbacks.remove(callbacks)"},
			
				{ id:15, pId:1, name:"Event对象"},
			
					{ id:151, pId:15, name:"e.preventDefault()"},
					{ id:152, pId:15, name:"e.stopPropagation()"},
					
			
				{ id:16, pId:1, name:"事件"},
			
					{ id:161, pId:16, name:"on(events,[selector],[data],fn)"},
					{ id:162, pId:16, name:"bind(type,[data],fn)"},
					{ id:163, pId:16, name:"unbind(type,[data|fn])"},
					{ id:164, pId:16, name:"keydown([[data],fn])"},
				
				
					
			{ id:2, pId:0, name:"组件", open:true},
				{ id:20, pId:2, name:"表格"},
					{ id:202, pId:20, name:"表格初始化"},
					{ id:200, pId:20, name:"常用模版"},
						{ id:2001, pId:200, name:"queryParams(params)"},
						{ id:2002, pId:200, name:"queryParams(params) for Limit"},
						{ id:2003, pId:200, name:"rowStyle(row, index)"},
						{ id:2004, pId:200, name:"cellStyle(value, row, index)"},
					{ id:204, pId:20, name:"属性(选项Option)"},
						{ id:2041, pId:204, name:"classes"},
						{ id:2042, pId:204, name:"height"},
						{ id:2043, pId:204, name:"undefinedText"},
						{ id:2044, pId:204, name:"striped"},
						{ id:2045, pId:204, name:"sortName"},
						{ id:2046, pId:204, name:"sortOrder"},
						{ id:2047, pId:204, name:"iconsPrefix"},
						{ id:2048, pId:204, name:"iconSize"},
						{ id:2049, pId:204, name:"icons"},
						{ id:20410, pId:204, name:"columns(列)"},
						{ id:20411, pId:204, name:"data(shu)"},
						{ id:20412, pId:204, name:"dataField"},
						{ id:20413, pId:204, name:"ajax"},
						{ id:20414, pId:204, name:"method"},
						{ id:20415, pId:204, name:"url"},
						{ id:20416, pId:204, name:"cache"},
						{ id:20417, pId:204, name:"contentType"},
						{ id:20418, pId:204, name:"dataType"},
						{ id:20419, pId:204, name:"ajaxOptions"},
						{ id:20420, pId:204, name:"queryParams"},
						{ id:20421, pId:204, name:"queryParamsType"},
						{ id:20422, pId:204, name:"responseHandler"},
						{ id:20423, pId:204, name:"pagination"},
						{ id:20424, pId:204, name:"onlyInfoPagination"},
						{ id:20425, pId:204, name:"sidePagination"},
						{ id:20426, pId:204, name:"pageNumber"},
						{ id:20427, pId:204, name:"pageSize"},
						{ id:20428, pId:204, name:"pageList"},
						{ id:20429, pId:204, name:"selectItemName"},
						{ id:20430, pId:204, name:"smartDisplay"},
						{ id:20431, pId:204, name:"search"},
						{ id:20432, pId:204, name:"strictSearch"},
						{ id:20433, pId:204, name:"searchText"},
						{ id:20434, pId:204, name:"searchTimeOut"},
						{ id:20435, pId:204, name:"trimOnSearch"},
						{ id:20436, pId:204, name:"showHeader"},
						{ id:20437, pId:204, name:"showFooter"},
						{ id:20438, pId:204, name:"showColumns"},
						{ id:20439, pId:204, name:"showRefresh"},
					    { id:20440, pId:204, name:"showToggle"},
						{ id:20441, pId:204, name:"showPaginationSwitch"},
						{ id:20442, pId:204, name:"minimumCountColumns"},
						{ id:20443, pId:204, name:"idField"},
						{ id:20444, pId:204, name:"uniqueId"},
						{ id:20445, pId:204, name:"cardView"},
						{ id:20446, pId:204, name:"detailView"},
						{ id:20447, pId:204, name:"detailFormatter"},
						{ id:20448, pId:204, name:"searchAlign"},
						{ id:20449, pId:204, name:"buttonsAlign"},
						{ id:20450, pId:204, name:"toolbarAlign"},
						{ id:20451, pId:204, name:"paginationVAlign"},
						{ id:20452, pId:204, name:"paginationHAlign"},
						{ id:20453, pId:204, name:"paginationDetailHAlign"},
						{ id:20454, pId:204, name:"paginationFirText"},
						{ id:20455, pId:204, name:"paginationPreText"},
						{ id:20456, pId:204, name:"paginationNextText"},
						{ id:20457, pId:204, name:"paginationFirText"},
						{ id:20458, pId:204, name:"clickToSelect"},
						{ id:20459, pId:204, name:"singleSelect"},
						{ id:20460, pId:204, name:"toolbar"},
						{ id:20461, pId:204, name:"checkboxHeader"},
						{ id:20462, pId:204, name:"maintainSelected"},
						{ id:20463, pId:204, name:"sortable"},
						{ id:20464, pId:204, name:"silentSort"},
						{ id:20465, pId:204, name:"rowStyle"},
						{ id:20466, pId:204, name:"rowAttributes"},
						{ id:20467, pId:204, name:"locale"},
				{ id:206, pId:20, name:"列选项"},
					{ id:2061, pId:206, name:"radio(单选框)"},
					{ id:2062, pId:206, name:"checkbox(多选框)"},
					{ id:2063, pId:206, name:"field(域)"},
					{ id:2064, pId:206, name:"title(列标题)"},
					{ id:2065, pId:206, name:"titleTooltip(列提示框)"},
					{ id:2066, pId:206, name:"class(列类名)"},
					{ id:2067, pId:206, name:"rowspan(跨行)"},
					{ id:2068, pId:206, name:"colspan(跨列)"},
					{ id:2069, pId:206, name:"align(对齐列数据)"},
					{ id:20610, pId:206, name:"halign(对齐表格头部)"},
					{ id:20611, pId:206, name:"falign(对齐表格脚部)"},
					{ id:20612, pId:206, name:"valign(对齐单元格数据)"},
					{ id:20613, pId:206, name:"width(列宽（%或px）)"},
					{ id:20614, pId:206, name:"sortable(是否排序)"},
					{ id:20615, pId:206, name:"order(排序方式)"},
					{ id:20616, pId:206, name:"visible(列显示)"},
					{ id:20617, pId:206, name:"cardVisible(列card视图显示)"},
					{ id:20618, pId:206, name:"switchable(列开关)"},
					{ id:20619, pId:206, name:"clickToSelect(单击列式选择radiobox/checkbox)"},
					{ id:20620, pId:206, name:"formatter(列格式化)"},
					{ id:20621, pId:206, name:"footerFormatter(列脚部格式化)"},
					{ id:20622, pId:206, name:"events(单元格事件)"},
					{ id:20623, pId:206, name:"sorter(自定义排序方法)"},
					{ id:20624, pId:206, name:"sortName(排序字段名)"},
					{ id:20625, pId:206, name:"cellStyle(单元格风格设计)"},
					{ id:20626, pId:206, name:"searchable(可被查询)"},
					{ id:20627, pId:206, name:"searchFormatter(查询数据格式化)"},
				{ id:205, pId:20, name:"事件(Option选项)"},
						{ id:2051, pId:205, name:"onAll(name, args)"},
						{ id:2052, pId:205, name:"onClickRow(row, $element)"},
						{ id:2053, pId:205, name:"onDblClickRow(row, $element)"},
						{ id:2054, pId:205, name:"onClickCell(field, value, row, $element)"},
						{ id:2055, pId:205, name:"onDblClickCell(field, value, row, $element)"},
						{ id:2056, pId:205, name:"onSort(name, order)"},
						{ id:2057, pId:205, name:"onCheck(row, $element)"},
						{ id:2058, pId:205, name:"onUncheck(row, $element)"},
						{ id:2059, pId:205, name:"onCheckAll(row, $element)"},
						{ id:20510, pId:205, name:"onUncheckAll(row, $element)"},
						{ id:20511, pId:205, name:"onCheckSome(row, $element)"},
						{ id:20512, pId:205, name:"onUncheckSome(row, $element)"},
						{ id:20513, pId:205, name:"onLoadSuccess(row, $element)"},
						{ id:20514, pId:205, name:"onLoadError(row, $element)"},
						{ id:20515, pId:205, name:"onColumnSwitch(field, checked)"},
						{ id:20516, pId:205, name:"onColumnSearch(field, text)"},
						{ id:20517, pId:205, name:"onPageChange(number, size)"},
						{ id:20518, pId:205, name:"onSearch(text)"},
						{ id:20519, pId:205, name:"onToggle(cardView)"},
						{ id:20520, pId:205, name:"onPreBody(data)"},
						{ id:20521, pId:205, name:"onPostBody()"},
						{ id:20522, pId:205, name:"onPostHeader()"},
						{ id:20523, pId:205, name:"onExpandRow(index, row, $detail)"},
						{ id:20524, pId:205, name:"onCollapseRow(index, row)"},
						{ id:20525, pId:205, name:"onRefreshOptions(options)"},
						{ id:20526, pId:205, name:"onResetView()"},
				{ id:201, pId:20, name:"事件(Jquery选项)"},
						{ id:2011, pId:201, name:"onAll(name, args)"},
						{ id:2012, pId:201, name:"onClickRow(row, $element)"},
						{ id:2013, pId:201, name:"onDblClickRow(row, $element)"},
						{ id:2014, pId:201, name:"onClickCell(field, value, row, $element)"},
						{ id:2015, pId:201, name:"onDblClickCell(field, value, row, $element)"},
						{ id:2016, pId:201, name:"onSort(name, order)"},
						{ id:2017, pId:201, name:"onCheck(row, $element)"},
						{ id:2018, pId:201, name:"onUncheck(row, $element)"},
						{ id:2019, pId:201, name:"onCheckAll(row, $element)"},
						{ id:20110, pId:201, name:"onUncheckAll(row, $element)"},
						{ id:20111, pId:201, name:"onCheckSome(row, $element)"},
						{ id:20112, pId:201, name:"onUncheckSome(row, $element)"},
						{ id:20113, pId:201, name:"onLoadSuccess(row, $element)"},
						{ id:20114, pId:201, name:"onLoadError(row, $element)"},
						{ id:20115, pId:201, name:"onColumnSwitch(field, checked)"},
						{ id:20116, pId:201, name:"onColumnSearch(field, text)"},
						{ id:20117, pId:201, name:"onPageChange(number, size)"},
						{ id:20118, pId:201, name:"onSearch(text)"},
						{ id:20119, pId:201, name:"onToggle(cardView)"},
						{ id:20120, pId:201, name:"onPreBody(data)"},
						{ id:20121, pId:201, name:"onPostBody()"},
						{ id:20122, pId:201, name:"onPostHeader()"},
						{ id:20123, pId:201, name:"onExpandRow(index, row, $detail)"},
						{ id:20124, pId:201, name:"onCollapseRow(index, row)"},
						{ id:20125, pId:201, name:"onRefreshOptions(options)"},
						{ id:20126, pId:201, name:"onResetView()"},
			{ id:203, pId:20, name:"方法"},
				  		{ id:2031, pId:203, name:"getOptions"},
				  		{ id:2032, pId:203, name:"getSelections"},
				  		{ id:2033, pId:203, name:"getAllSelections"},
				  		{ id:2034, pId:203, name:"getData"},
				  		{ id:2035, pId:203, name:"getRowByUniqueId(id)"},
				  		{ id:2036, pId:203, name:"load(data)"},
				  		{ id:2037, pId:203, name:"append(data)"},
				  		{ id:2038, pId:203, name:"prepend(data)"},
				  		{ id:2039, pId:203, name:"remove(field,value)"},
				  		{ id:20310, pId:203, name:"removeAll"},
				  		{ id:20311, pId:203, name:"removeByUniqueId(id)"},
				  		{ id:20312, pId:203, name:"insertRow(index,row)"},
				  		{ id:20313, pId:203, name:"updateRow(index,row)"},
				  		{ id:20314, pId:203, name:"updateByUniqueId(id,row)"},
				  		{ id:20315, pId:203, name:"showRow(index,isIdField)"},
				  		{ id:20316, pId:203, name:"hideRow(index,isIdField)"},
				  		{ id:20317, pId:203, name:"refresh(params)"},
				  		{ id:20318, pId:203, name:"refreshOptions(options)"},
				  		{ id:20319, pId:203, name:"resetView(params)"},
				  		{ id:20320, pId:203, name:"mergeCells(index,field,rowspan,clospan)"},
				  		{ id:20321, pId:203, name:"checkAll"},
				  		{ id:20322, pId:203, name:"uncheckAll"},
				  		{ id:20323, pId:203, name:"check(index)"},
				  		{ id:20324, pId:203, name:"uncheck(index)"},
				  		{ id:20325, pId:203, name:"checkBy(field,value)"},
				  		{ id:20326, pId:203, name:"uncheckBy(field,value)"},
				  		{ id:20327, pId:203, name:"showColumn(field)"},
				  		{ id:20328, pId:203, name:"hideCoulumn(field)"},
				  		{ id:20329, pId:203, name:"expandRow(index)"},
				  		{ id:20330, pId:203, name:"collapseRow(index)"},
			{ id:21, pId:2, name:"下拉列表"},
				{ id:301, pId:21, name:"初始化"},
						{ id:3001, pId:301, name:"创建multiselect"},
						{ id:3002, pId:301, name:"销毁multiselect"},
						{ id:3003, pId:301, name:"刷新控件选项"},
						{ id:3004, pId:301, name:"重构下拉列表"},
						{ id:3005, pId:301, name:"数组选定选项"},
						{ id:3006, pId:301, name:"数组取消选项"},
						{ id:3007, pId:301, name:"选择所有可见选项"},
						{ id:3008, pId:301, name:"取消被选可见选项"}, 
						{ id:3009, pId:301, name:"更新按钮的title和text"},
						{ id:30010, pId:301, name:"改变Options配置"},
						{ id:30011, pId:301, name:"禁用下拉列表"},
						{ id:30012, pId:301, name:"启用下拉列表"},
						{ id:30013, pId:301, name:"数组创建select"},
				{ id:401, pId:21, name:"配置选项"},
						{ id:4001, pId:401, name:"enableClickableOptGroups"},
						{ id:4002, pId:401, name:"enableCollapsibleOptGroups"},
						{ id:4003, pId:401, name:"disableIfEmpty"},
						{ id:4004, pId:401, name:"disabledText"},
						{ id:4005, pId:401, name:"buttonWidth"},
						{ id:4006, pId:401, name:"dropRight"},
						{ id:4007, pId:401, name:"dropUp"},
						{ id:4008, pId:401, name:"maxHeight"},
						{ id:4009, pId:401, name:"buttonClass"},
						{ id:40010, pId:401, name:"inheritClass"},
						{ id:40011, pId:401, name:"nonSelectedText"},
						{ id:40012, pId:401, name:"numberDisplayed"},
						{ id:40013, pId:401, name:"nSelectedText"},
						{ id:40014, pId:401, name:"allSelectedText"},
						{ id:40015, pId:401, name:"delimiterText"},
						{ id:40016, pId:401, name:"selectedClass"},
						{ id:40017, pId:401, name:"enableFiltering"},
						{ id:40018, pId:401, name:"enableCaseInsensitiveFiltering"},
						{ id:40019, pId:401, name:"enableFullValueFiltering"},
						{ id:40020, pId:401, name:"filterBehavior"},
						{ id:40021, pId:401, name:"filterPlaceholder"},
						{ id:40022, pId:401, name:"includeSelectAllOption"},
						{ id:40023, pId:401, name:"selectAllJustVisible"},
						{ id:40024, pId:401, name:"selectAllText"},
						{ id:40025, pId:401, name:"selectAllValue"},
						{ id:40026, pId:401, name:"selectAllName"},
				{ id:501, pId:21, name:"常用方法事件"},
						{ id:5001, pId:501, name:"onChange"},
						{ id:5002, pId:501, name:"onDropdownShow"},	
						{ id:5003, pId:501, name:"onDropdownShown"},	
						{ id:5004, pId:501, name:"onDropdownHidden"},	
						{ id:5005, pId:501, name:"buttonText"},	
						{ id:5006, pId:501, name:"buttonTitle"},	
						{ id:5007, pId:501, name:"optionLabel"},	
						{ id:5008, pId:501, name:"optionClass"},
						{ id:5009, pId:501, name:"onSelectAll"},
						{ id:50010, pId:501, name:"onInitialized"},
			{ id:22, pId:2, name:"表单"},	
					{ id:220, pId:22, name:"表单校验"},
						 { id:2201, pId:220, name:"bootstrpValidator({})"},	
			{ id:23, pId:2, name:"日期选择器"},
				{ id:230, pId:23, name:"常用方法"},
				 { id:2301, pId:230, name:"日期改变"}	
		];
		function showIconForTree(treeId, treeNode) {
			return !treeNode.isParent;
		};

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});
		
 		function zTreeOnDblClick(event, treeId, treeNode){
 			console.info("ztreeOndblClikc  "+treeNode.id);
 			var zid = treeNode.id+"";
 			sendMessage('CSTEditerWindow',zid);
 			
		}

	</script>
</body>
</html>
