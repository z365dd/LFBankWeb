/**
 * 
 */
package com.adtec.sys.modules.gen.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.common.mapper.JaxbMapper;
import com.adtec.sys.common.utils.FreeMarkers;
import com.adtec.sys.modules.gen.entity.GenCategory;
import com.adtec.sys.modules.gen.entity.GenConfig;
import com.adtec.sys.modules.gen.entity.GenScheme;
import com.adtec.sys.modules.gen.entity.GenTable;
import com.adtec.sys.modules.gen.entity.GenTableColumn;
import com.adtec.sys.modules.gen.entity.GenTemplate;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 代码生成工具类
 * 
 * @version 2013-11-16
 */
public class GenUtils {

	private final static Logger logger = LoggerFactory.getLogger(GenUtils.class);

	/**
	 * 初始化列属性字段
	 * @param genTable
	 */
	public static void initColumnField(GenTable genTable){
		for (GenTableColumn column : genTable.getColumnList()){
			
			// 如果是不是新增列，则跳过。
			if (StringUtil.isNotBlank(column.getId())){
				continue;
			}
			
			// 设置字段说明
			if (StringUtil.isBlank(column.getTabDesc())){
				column.setTabDesc(column.getName());
			}
			
			/*20200706 add by chenyl for 新增对数据库字段类型对应的转换java类型*/
			// 设置java类型
			if (StringUtil.startsWithIgnoreCase(column.getDbFieldTp(), "CHAR")
					|| StringUtil.startsWithIgnoreCase(column.getDbFieldTp(), "VARCHAR")
					|| StringUtil.startsWithIgnoreCase(column.getDbFieldTp(), "NARCHAR")
					|| StringUtil.startsWithIgnoreCase(column.getDbFieldTp(), "VARCHAR2")){
				column.setJavaTp("String");
			}else if (StringUtil.startsWithIgnoreCase(column.getDbFieldTp(), "DATETIME")
					|| StringUtil.startsWithIgnoreCase(column.getDbFieldTp(), "DATE")
					|| StringUtil.startsWithIgnoreCase(column.getDbFieldTp(), "TIMESTAMP")){
				// 20180928 mod by chenyl for 日期时间使用String保存
				column.setJavaTp("String");
//				column.setJavaType("java.util.Date");
				column.setDpyTp("dateselect");
			}else if (StringUtil.startsWithIgnoreCase(column.getDbFieldTp(), "BIGINT")
					|| StringUtil.startsWithIgnoreCase(column.getDbFieldTp(), "INT")
					|| StringUtil.startsWithIgnoreCase(column.getDbFieldTp(), "INTEGER")
					|| StringUtil.startsWithIgnoreCase(column.getDbFieldTp(), "NUMBER")
					|| StringUtil.startsWithIgnoreCase(column.getDbFieldTp(), "FLOAT")
					|| StringUtil.startsWithIgnoreCase(column.getDbFieldTp(), "DECIMAL")
					|| StringUtil.startsWithIgnoreCase(column.getDbFieldTp(), "DOUBLE")){
				// 如果是浮点型
				String[] ss = StringUtil.split(StringUtil.substringBetween(column.getDbFieldTp(), "(", ")"), ",");
				if (ss != null && ss.length == 2 && Integer.parseInt(ss[1])>0){
					column.setJavaTp("Double");
				}
				// 如果是整形
				else if (ss != null && ss.length == 1 && Integer.parseInt(ss[0])<=10){
					column.setJavaTp("Integer");
				}
				// 长整形
				else{
					column.setJavaTp("Long");
				}
			}
			
			// 设置java字段名
			column.setJavaField(StringUtil.toCamelCase(column.getName()));
			
			// 是否是主键
			column.setMainKeyFlg(genTable.getPkList().contains(column.getName())?"1":"0");

			/*20181018 mod by chenyl for 默认基础设置数据不允许插入*/
			// 插入字段
			if (!StringUtil.equalsIgnoreCase(column.getName(), "id")
					&& !StringUtil.equalsIgnoreCase(column.getName(), "crtr")
					&& !StringUtil.equalsIgnoreCase(column.getName(), "crt_time")
					&& !StringUtil.equalsIgnoreCase(column.getName(), "uptr")
					&& !StringUtil.equalsIgnoreCase(column.getName(), "upt_time")
					&& !StringUtil.equalsIgnoreCase(column.getName(), "del_flg")){
				column.setInsertFlg("1");
			}
			
			
			/*20181018 add by chenyl for 最后修改者、最后修改时间默认在不可编辑*/
			// 编辑字段
			if (!StringUtil.equalsIgnoreCase(column.getName(), "id")
					&& !StringUtil.equalsIgnoreCase(column.getName(), "crtr")
					&& !StringUtil.equalsIgnoreCase(column.getName(), "crt_time")
					&& !StringUtil.equalsIgnoreCase(column.getName(), "uptr")
					&& !StringUtil.equalsIgnoreCase(column.getName(), "upt_time")
					&& !StringUtil.equalsIgnoreCase(column.getName(), "del_flg")){
				column.setEditFlg("1");
			}

			/*20181018 add by chenyl for 最后修改者默认在列表中显示*/
			// 列表字段
			if (StringUtil.equalsIgnoreCase(column.getName(), "name")
					|| StringUtil.equalsIgnoreCase(column.getName(), "title")
					|| StringUtil.equalsIgnoreCase(column.getName(), "rmrk")
					|| StringUtil.equalsIgnoreCase(column.getName(), "uptr")
					|| StringUtil.equalsIgnoreCase(column.getName(), "upt_time")){
				column.setListFlg("1");
			}
			
			// 查询字段
			if (StringUtil.equalsIgnoreCase(column.getName(), "name")
					|| StringUtil.equalsIgnoreCase(column.getName(), "title")){
				column.setQryFlg("1");
			}
			
			// 查询字段类型
			if (StringUtil.equalsIgnoreCase(column.getName(), "name")
					|| StringUtil.equalsIgnoreCase(column.getName(), "title")){
				column.setQryTp("like");
			}

			// 设置特定类型和字段名
			
			// 用户
			if (StringUtil.startsWithIgnoreCase(column.getName(), "user_id")){
//				column.setJavaType(User.class.getName());
//				column.setJavaField(column.getJavaField().replaceAll("Id", ".id|name"));
				column.setDpyTp("userselect");
			}
			// 部门
			else if (StringUtil.startsWithIgnoreCase(column.getName(), "brch_id")){
//				column.setJavaType(Office.class.getName());
//				column.setJavaField(column.getJavaField().replaceAll("Id", ".id|name"));
				column.setDpyTp("officeselect");
			}
			// 区域
			else if (StringUtil.startsWithIgnoreCase(column.getName(), "region_id")){
//				column.setJavaType(Area.class.getName());
//				column.setJavaField(column.getJavaField().replaceAll("Id", ".id|name"));
				column.setDpyTp("areaselect");
			}
			// 角色
			else if (StringUtil.startsWithIgnoreCase(column.getName(), "role_id")) {
				column.setDpyTp("roleselect");
			}
			// 创建者、更新者
			else if (StringUtil.startsWithIgnoreCase(column.getName(), "crtr")
					|| StringUtil.startsWithIgnoreCase(column.getName(), "uptr")){
//				column.setJavaType(User.class.getName());
//				column.setJavaField(column.getJavaField() + ".id");
				column.setDpyTp("userselect");
			}
			// 创建时间、更新时间
			else if (StringUtil.startsWithIgnoreCase(column.getName(), "crt_time")
					|| StringUtil.startsWithIgnoreCase(column.getName(), "upt_time")){
				column.setDpyTp("dateselect");
			}
			// 备注、内容
			else if (StringUtil.equalsIgnoreCase(column.getName(), "rmrk")
					|| StringUtil.equalsIgnoreCase(column.getName(), "noteCntt")){
				column.setDpyTp("textarea");
			}
			// 删除标记
			else if (StringUtil.equalsIgnoreCase(column.getName(), "del_flg")){
				/*20181018 mod by chenyl for 修改删除标志的默认选择组件为select*/
//				column.setShowType("radiobox");
				column.setDpyTp("select");
				column.setDictTp("del_flg");
			}
		}
	}
	
	/**
	 * 获取模板路径
	 * @return
	 */
	public static String getTemplatePath(){
		try{
			File file = new DefaultResourceLoader().getResource("").getFile();
			if(file != null){
				return file.getAbsolutePath() + File.separator + StringUtil.replaceEach(GenUtils.class.getName(), 
						new String[]{"util."+GenUtils.class.getSimpleName(), "."}, new String[]{"template", File.separator});
			}			
		}catch(Exception e){
			logger.error("{}", e);
		}

		return "";
	}
	
	/**
	 * XML文件转换为对象
	 * @param fileName
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fileToObject(String fileName, Class<?> clazz){
		try {
			String pathName = "/templates/modules/gen/" + fileName;
//			logger.debug("File to object: {}", pathName);
			Resource resource = new ClassPathResource(pathName); 
			InputStream is = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			if(null == br){
				throw new BaseException(SysErr.E_IO_ERROR, "XML文件读取失败");
			}
			StringBuilder sb = new StringBuilder();  
			while (true) {
				String line = br.readLine();
				if (line == null){
					break;
				}
				sb.append(line).append("\r\n");
			}
			if (is != null) {
				is.close();
			}
			if (br != null) {
				br.close();
			}
//			logger.debug("Read file content: {}", sb.toString());
			return (T) JaxbMapper.fromXml(sb.toString(), clazz);
		} catch (IOException e) {
			logger.warn("Error file convert: {}", e.getMessage());
		}
//		String pathName = StringUtil.replace(getTemplatePath() + "/" + fileName, "/", File.separator);
//		logger.debug("file to object: {}", pathName);
//		String content = "";
//		try {
//			content = FileUtil.readFileToString(new File(pathName), "utf-8");
////			logger.debug("read config content: {}", content);
//			return (T) JaxbMapper.fromXml(content, clazz);
//		} catch (IOException e) {
//			logger.warn("error convert: {}", e.getMessage());
//		}
		return null;
	}
	
	/**
	 * 获取代码生成配置对象
	 * @return
	 */
	public static GenConfig getConfig(){
		return fileToObject("config.xml", GenConfig.class);
	}

	/**
	 * 根据分类获取模板列表
	 * @param config
	 * @param category
	 * @param isChildTable 是否是子表
	 * @return
	 */
	public static List<GenTemplate> getTemplateList(GenConfig config, String category, boolean isChildTable){
		List<GenTemplate> templateList = Lists.newArrayList();
		if (config !=null && config.getCategoryList() != null && category !=  null){
			for (GenCategory e : config.getCategoryList()){
				if (category.equals(e.getValue())){
					List<String> list = null;
					if (!isChildTable){
						list = e.getTemplate();
					}else{
						list = e.getChildTableTemplate();
					}
					if (list != null){
						for (String s : list){
							if (StringUtil.startsWith(s, GenCategory.CATEGORY_REF)){
								templateList.addAll(getTemplateList(config, StringUtil.replace(s, GenCategory.CATEGORY_REF, ""), false));
							}else{
								GenTemplate template = fileToObject(s, GenTemplate.class);
								if (template != null){
									templateList.add(template);
								}
							}
						}
					}
					break;
				}
			}
		}
		return templateList;
	}
	
	/**
	 * 获取数据模型
	 * @param genScheme
	 * @return
	 */
	public static Map<String, Object> getDataModel(GenScheme genScheme){
		Map<String, Object> model = Maps.newHashMap();
		
		model.put("packName", StringUtil.lowerCase(genScheme.getPackName()));
		model.put("lastPackName", StringUtil.substringAfterLast((String)model.get("packName"),"."));
		model.put("modlName", StringUtil.lowerCase(genScheme.getModlName()));
		model.put("subModlName", StringUtil.lowerCase(genScheme.getSubModlName()));
		model.put("procClssName", StringUtil.uncapitalize(genScheme.getGenTable().getProcClssName()));
		model.put("ProcClssName", StringUtil.capitalize(genScheme.getGenTable().getProcClssName()));
		
		model.put("funcName", genScheme.getFuncName());
		model.put("funcNameAbbr", genScheme.getFuncNameAbbr());
		model.put("funcCrtr", StringUtil.isNotBlank(genScheme.getFuncCrtr())?genScheme.getFuncCrtr():UserUtils.getUser().getName());
		model.put("functionVersion", DateUtil.getDate());
		
		model.put("urlPrefix", model.get("modlName")+(StringUtil.isNotBlank(genScheme.getSubModlName())
				?"/"+StringUtil.lowerCase(genScheme.getSubModlName()):"")+"/"+model.get("procClssName"));
		model.put("viewPrefix", //StringUtil.substringAfterLast(model.get("packName"),".")+"/"+
				model.get("urlPrefix"));
		model.put("permissionPrefix", model.get("modlName")+(StringUtil.isNotBlank(genScheme.getSubModlName())
				?":"+StringUtil.lowerCase(genScheme.getSubModlName()):"")+":"+model.get("procClssName"));
		
		model.put("dbType", ParamUtil.getConfig("datasource.type"));

		model.put("table", genScheme.getGenTable());
		
		/*20200705 add by chenyl for 新增检查是否存在对应的页面组件:图片管理器*/
		List<GenTableColumn> columnList = genScheme.getGenTable().getColumnList();
		for(GenTableColumn column:columnList){
			if("fileselect".equals(column.getDpyTp())){
				model.put("hasFileSelect", true);
				break;
			}
		}
		
		/*20200119 add by chenyl for 添加主表pk中的字段总数*/
		model.put("pkCount", genScheme.getGenTable().getPkList().size());
		
		/*20200304 add by chenyl for 添加controller中生成的PATH*/
		String path = (ParamUtil.getJspPath().split(ParamUtil.getConfig("web.view.prefix"))[1])+"/"+((model.get("viewPrefix")+".jsp").replace(ParamUtil.getConfig("web.view.suffix"),""));
		model.put("PATH", path);
		
		model.put("jspPath", ParamUtil.getJspPath());
		
		model.put("jsPath", ParamUtil.getJsPath());
		
		model.put("jsFilePath", ParamUtil.getJsPath().split(ParamUtil.getWebPath())[1]);				
		
		return model;
	}
	
	/**
	 * 生成到文件
	 * @param tpl
	 * @param model
	 * @param isReplaceFile
	 * @return
	 */
	public static String generateToFile(GenTemplate tpl, Map<String, Object> model, boolean isReplaceFile){
		// 获取生成文件
		String fileName = "";
		// 20180501 add by chenyl for 对dataset方式生成的页面、页面js、页面cust添加js文件的引用名称
		if(tpl.getName().toLowerCase().indexOf("view")==0){
			String jsFileName = StringUtil.uncapitalize(tpl.getName().substring("view".length()));
			jsFileName = model.get("procClssName") + StringUtil.capitalize(jsFileName);
			// 20180501 add by chenyl for 提取JSPBEG模板、JSPEND模板
			BufferedReader brbeg = null;
			BufferedReader brend = null;
			InputStreamReader isrbeg = null;
			InputStreamReader isrend = null;
			StringBuffer jspbeg = new StringBuffer();
			try {
				isrbeg = new InputStreamReader(new FileInputStream(ParamUtil.getJSPBEG()), "UTF-8");
				brbeg = new BufferedReader(isrbeg);
				if(null == brbeg){
					throw new BaseException(SysErr.E_IO_ERROR, "文件读取失败");
				}
				isrend = new InputStreamReader(new FileInputStream(ParamUtil.getJSPEND()), "UTF-8");
				brend = new BufferedReader(isrend);
				// 处理JSPBEG文件
				String str = null;
				while ((str = brbeg.readLine()) != null) {
					jspbeg.append(str + "\n");
					if (str.startsWith("<!-- Self reference JS-->")) {
						jspbeg.append("<script type=\"text/javascript\" src=\"<%=basePath%>" + model.get("jsFilePath") + "/");
						if(!DataUtil.isNullStr(""+model.get("modlName"))){
							jspbeg.append(model.get("modlName") + "/");
						}
						if(!DataUtil.isNullStr(""+model.get("subModlName"))){
							jspbeg.append(model.get("subModlName") + "/");
						}
						jspbeg.append(jsFileName + ".js\"");
						jspbeg.append(" charset=\"utf-8\"></script>" + "\n");
					}
				}
				model.put("JSPBEG", jspbeg.toString());
				if(null == brend){
					throw new BaseException(SysErr.E_IO_ERROR, "文件读取失败");
				}
				str = null;
				StringBuffer jspend = new StringBuffer();
				while ((str = brend.readLine()) != null) {
					jspend.append(str + "\n");
				}
				model.put("JSPEND", jspend.toString());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
                System.out.println("出现异常");
            } finally {
				if (null != brbeg) {
					try {
						brbeg.close();
					} catch (IOException e) {
                System.out.println("出现异常");
            }
				}
				if (null != isrbeg) {
					try {
						isrbeg.close();
					} catch (IOException e) {
                System.out.println("出现异常");
            }
				}
				if (null != brend) {
					try {
						brend.close();
					} catch (IOException e) {
                System.out.println("出现异常");
            }
				}
				if (null != isrend) {
					try {
						isrend.close();
					} catch (IOException e) {
                System.out.println("出现异常");
            }
				}
			}
			//dataset方式生成的web端文件不不需要再添加项目路径
			fileName = StringUtil.replaceEach(FreeMarkers.renderString(tpl.getFilePath() + "/", model), 
					new String[]{"//", "/", "."}, new String[]{File.separator, File.separator, File.separator})
			+ FreeMarkers.renderString(tpl.getFileName(), model);
		}else{
			fileName = ParamUtil.getProjectPath() + File.separator 
					+ StringUtil.replaceEach(FreeMarkers.renderString(tpl.getFilePath() + "/", model), 
							new String[]{"//", "/", "."}, new String[]{File.separator, File.separator, File.separator})
					+ FreeMarkers.renderString(tpl.getFileName(), model);
		}
		logger.debug(" fileName === " + fileName);
		// 获取生成文件内容
		String content = FreeMarkers.renderString(StringUtil.trimToEmpty(tpl.getContent()), model);
		logger.debug(" content === \r\n" + content);
		// 如果选择替换文件，则删除原文件
		if (isReplaceFile){
			FileUtil.deleteFile(fileName);
		}
		// 创建并写入文件
		if (FileUtil.createFile(fileName)){
			FileUtil.writeToFile(fileName, content, true);
			logger.debug(" file create === " + fileName);
			return "生成成功："+fileName+"<br/>";
		}else{
			logger.debug(" file extents === " + fileName);
			return "文件已存在："+fileName+"<br/>";
		}
	}
}
