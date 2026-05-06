package com.adtec.sys.common.utils;

public class CommonConstants {
	
	/**
	 * 回车换行
	 */
	public static final String CRLF = "\r\n";
	
	public static final String PRE_CRLF = ";\r\n";
	
	/**
	 * 单行注释
	 */
	public static final String SINGLELINE_COMMENT = "//";
	
	/**
	 * 多行注释
	 */
	public static final String MULTILINE_COMMENT = "/*";
	
	/**
	 * 单行注释正则
	 */
	public static final String REG_SINGLELINE_COMMENT = "\\/\\/";
	
	/**
	 * 多行注释正则
	 */
	public static final String REG_MULTILINE_COMMENT = "\\/\\*";
	
	/**
	 * 远程同步方法名
	 */
	public static final String SYN_EXECUTE = "synExecute";

	/**
	 * 远程异步方法名
	 */
	public static final String ASYN_EXECUTE = "asynExecute";
	
	/**
	 * 公有关键字
	 */
	public static final String PUBLIC_RIGHT = "public";
	
	/**
	 * 私有关键字
	 */
	public static final String PRIVATE_RIGHT = "private";

	/**
	 * Windows标签名称
	 */
	public static final String WINDOWS_TAG = "Windows";
   
	/**
	 * 主窗体标签名称
	 */
	public static final String MAINWINDOW_TAG = "control-MainWindow";
	
	/**
	 * 子窗体标签名称
	 */
	public static final String SUBWINDOW_TAG = "control-Window";
	
	public static final String PANEL_TAG = "control-Panel";
	
	public static final String COMBROOT_TAG = "control-CombRoot";
	
	public static final String CALL_TAG = "control-Call";
	/**
	 * Accessory标签的名称
	 */
	public static final String ACCESSORY_TAG = "Accessory";
	
	/**
	 * dataset标签的名称
	 */
	public static final String DATASET_TAG = "control-DataSet";
	
	/**
	 * treedataset
	 */
	public static final String TREEDATASET_TAG = "control-TreeDataSet";
	
	/**
	 * datasets
	 */
	public static final String DATASETS_TAG = "control-DataSets";
	
	/**
	 * Menu标签的名称
	 */
	public static final String MENU_TAG = "control-Menu";
	
	/**
	 * MenuItem标签名称
	 */
	public static final String MENUITEM_TAG = "control-MenuItem";
	
	/**
	 * MenuSeparator标签名称
	 */
	public static final String MENUSEPARATOR_TAG = "control-MenuSeparator";
	
	/**
	 * WindowScripts标签的名称
	 */
	public static final String WINDOWSCRIPTS_TAG = "WindowScripts";
	
	/**
	 * WindowScript标签的名称
	 */
	public static final String WINDOWSCRIPT_TAG = "WindowScript";
	
	/**
	 * 属性名window
	 */
	public static final String PROPERTY_WINDOW = "window";
	
	/**
	 * Call标签下的子标签
	 */
	public static final String DYNPROPS_TAG = "dynProps";
	
	/**
	 * 放置complete事件代码的代码区名称
	 */
	public static final String BUFFER_COMPLETE_EVENT = "$_complete";
	
	/**
	 * window控件的函数声明
	 */
	public final static StringBuilder WINDOW_FUNC_STR = new StringBuilder();
	
	public final static StringBuilder REFPANEL_FUNC_STR = new StringBuilder();
	
	/**
	 * mainwindow complete事件代码片段
	 */
	public final static StringBuilder MAINWINDOW_CodeSnippet  = new StringBuilder();
	
	/**
	 * completeEventInfoStr
	 */
	public final static StringBuilder completeEventInfoStr  = new StringBuilder();
	
	/**
	 * templatePath
	 */
	public static final String TEMPLATEPATH = "templatePath";
	
	/**
	 * 树节点children属性
	 */
	public static final String TREE_NODE_ATTRIBUTE_CHILDREN = "children";
	
	/**
	 * 远程服务类型，用于区分是*.service类型服务还是*.do类型服务
	 */
	public static final String REMOTE_SERVICE_ALIAS= "serviceAlias";
	
	public static final String REMOTE_SERVICE= "service";
	
	public static final String REMOTE= "remote";
	
	public static final String FUNCTIONID = "FunctionId";
	
	public static final String BIZ_UPLOADFILE_ATTRIBUTE = "biz_uploadfile_attribute";
	
	public static final String ORIGINAL_FILES_NAMES = "original_files_names";
	/**
	 * 窗体文件的后缀名
	 */
	public static final String WINDOW_SUFIX = ".mw";
	
	/**
	 * 公共窗体文件的后缀名
	 */
	public static final String PWINDOW_SUFIX = ".pw";
		
	/**
	 * RefPanel标签名
	 */
	public static final String REFPANEL_TAG = "control-RefPanel";
	
	/**
	 * 公共窗体在构造时传入的父窗体参数名，父窗体表示引用了公共窗体的那个窗体
	 * 
	 */
	public static final String PARENT_WIN = "ParentWin";
	
	
	/**
	 * import关键字
	 */
	public static final String IMPORT = "import";
	
	/**
	 * include关键字
	 */
	public static final String INCLUDE = "include";
	
	/**
	 * 存放window名称的代码区名
	 */
	public static final String BUFFER_WINDOWNAME = "windowName";

	/**
	 * 请求扩展名(获取子窗体/公共窗体/refpanel分离的代码)
	 */
	public static final String SUBWINDOW_SUFFIX = ".sw";
	
	public  static final String HTML_DOCTYPE = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \" http://www.w3.org/TR/html4/loose.dtd\">";
	
	static {
		//
		completeEventInfoStr.append("//completeEventInfo定义");
		completeEventInfoStr.append(CRLF);
		completeEventInfoStr.append("var completeEventInfo = {");
		completeEventInfoStr.append(CRLF);
		completeEventInfoStr.append("hasBindDataSet : false ,");
		completeEventInfoStr.append(CRLF);
		completeEventInfoStr.append("widgetCount : 0,");
		completeEventInfoStr.append(CRLF);
		completeEventInfoStr.append("dsCount :0,");
		completeEventInfoStr.append(CRLF);
		completeEventInfoStr.append("hasFireComplete : false");
		completeEventInfoStr.append(CRLF);
		completeEventInfoStr.append(" };");
		completeEventInfoStr.append(CRLF);
		
		
		//close
//		WINDOW_FUNC_STR.append("var close = function(){	");
//		WINDOW_FUNC_STR.append(CRLF);
//		WINDOW_FUNC_STR.append("self.close();");
//		WINDOW_FUNC_STR.append(CRLF);
//		WINDOW_FUNC_STR.append("};");
//		WINDOW_FUNC_STR.append(CRLF);
	    //show
		WINDOW_FUNC_STR.append("var show = function(){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("self.show();");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//hide
		WINDOW_FUNC_STR.append("var hide = function(){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("self.hide();");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//setTitle
		WINDOW_FUNC_STR.append("var setTitle = function(title){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("self.setTitle(title);");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//getTitle
		WINDOW_FUNC_STR.append("var getTitle = function(){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.getTitle();");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//mask 
		WINDOW_FUNC_STR.append("var maskWin  = function(msg,msgCls){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.maskWin(msg,msgCls);");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		// unmask
		WINDOW_FUNC_STR.append("var unmaskWin  = function(){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.unmaskWin();");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//setHtml 
		WINDOW_FUNC_STR.append("var setHtml  = function(html){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.setHtml(html);");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//insertComponent
		WINDOW_FUNC_STR.append("var insertComponent  = function(comp ,index){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.insertComponent(comp ,index);");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//doLayout
		WINDOW_FUNC_STR.append("var doLayout  = function(shallow ,force){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.doLayout(shallow ,force);");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//add
		WINDOW_FUNC_STR.append("var add  = function(component){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.add(component);");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//remove 
		WINDOW_FUNC_STR.append("var remove  = function(component,autoDestroy){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.remove(component,autoDestroy);");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//getWidth
		WINDOW_FUNC_STR.append("var getWidth  = function(){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.getWidth();");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//getHeight
		WINDOW_FUNC_STR.append("var getHeight  = function(){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.getHeight();");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//setWidth 
		WINDOW_FUNC_STR.append("var setWidth  = function(width){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.setWidth(width);");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//setHeight 
		WINDOW_FUNC_STR.append("var setHeight  = function(height){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.setHeight(height);");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//getId
		WINDOW_FUNC_STR.append("var getId  = function(){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.getId();");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//getCmpName 
		WINDOW_FUNC_STR.append("var getCmpName  = function(){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.getCmpName();");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//isVisible 
		WINDOW_FUNC_STR.append("var isVisible  = function(){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.isVisible();");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//isEnable 
		WINDOW_FUNC_STR.append("var isEnabled  = function(){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.isEnabled();");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//setVisible 
		WINDOW_FUNC_STR.append("var setVisible  = function(visible){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.setVisible(visible);");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//setEnabled
		WINDOW_FUNC_STR.append("var setEnabled  = function(enable){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.setEnabled(enable);");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		//focus 
		WINDOW_FUNC_STR.append("var focus  = function(){	");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("return self.focus();");
		WINDOW_FUNC_STR.append(CRLF);
		WINDOW_FUNC_STR.append("};");
		WINDOW_FUNC_STR.append(CRLF);
		
		
		//添加mainwindow代码片段
		MAINWINDOW_CodeSnippet.append("if(!completeEventInfo.hasBindDataSet){");
		MAINWINDOW_CodeSnippet.append(CRLF);
		MAINWINDOW_CodeSnippet.append(CRLF).append("completeEventInfo.hasFireComplete = true;")
		.append(CRLF);
		MAINWINDOW_CodeSnippet.append("    if($_complete){");
		MAINWINDOW_CodeSnippet.append(CRLF);
		MAINWINDOW_CodeSnippet.append("       $_complete();");
		MAINWINDOW_CodeSnippet.append(CRLF);
		MAINWINDOW_CodeSnippet.append("    }");
		MAINWINDOW_CodeSnippet.append("}");
		MAINWINDOW_CodeSnippet.append(CRLF);
		
		initRefPanelFunc();
	}
	
	public static void initRefPanelFunc(){
//		REFPANEL_FUNC_STR.append("var close = function(){");
//		REFPANEL_FUNC_STR.append(CRLF);
//		REFPANEL_FUNC_STR.append("self.close();");
//		REFPANEL_FUNC_STR.append(CRLF);
//		REFPANEL_FUNC_STR.append("};");
//		REFPANEL_FUNC_STR.append(CRLF);
		
		REFPANEL_FUNC_STR.append("var setTitle = function(title){");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("self.setTitle(title);");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("};");
		REFPANEL_FUNC_STR.append(CRLF);
		
		REFPANEL_FUNC_STR.append("var getTitle  = function(){");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("return self.getTitle();");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("};");
		REFPANEL_FUNC_STR.append(CRLF);
		
		REFPANEL_FUNC_STR.append("var setHtml = function(html){");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("  self.setHtml(html);");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("};");
		REFPANEL_FUNC_STR.append(CRLF);
		
		REFPANEL_FUNC_STR.append("var doLayout = function(){");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("  self.doLayout();");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("};");
		REFPANEL_FUNC_STR.append(CRLF);
		
		REFPANEL_FUNC_STR.append("var shallow = function(){");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("  self.shallow();");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("};");
		REFPANEL_FUNC_STR.append(CRLF);
		
		REFPANEL_FUNC_STR.append("var force = function(){");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("  self.force();");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("};");
		REFPANEL_FUNC_STR.append(CRLF);
		
		REFPANEL_FUNC_STR.append("var getId = function(){");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("  return self.getId();");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("};");
		REFPANEL_FUNC_STR.append(CRLF);
		
		REFPANEL_FUNC_STR.append("var add = function(component){");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("  self.add(component);");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("};");
		REFPANEL_FUNC_STR.append(CRLF);
		
		REFPANEL_FUNC_STR.append("var remove = function(component,autoDestroy){");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("  self.remove(component,autoDestroy);");
		REFPANEL_FUNC_STR.append(CRLF);
		REFPANEL_FUNC_STR.append("};");
		REFPANEL_FUNC_STR.append(CRLF);
	}
	
	
}
