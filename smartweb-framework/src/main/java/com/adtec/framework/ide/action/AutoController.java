package com.adtec.framework.ide.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.ide.utilImpl.CstLoadingUtilImpl;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.json.JSONObject;
import com.adtec.framework.json.XML;
import com.adtec.sys.common.web.Servlets;

/**
 * 接口自动生成代码控制拦截器
 * @author chenyl
 *
 */
@Controller
@RequestMapping(value = "b_ide/ideAuto")
public class AutoController extends IdeBaseController {
	
	private static FileFilter filter = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			String name = pathname.getName();
			return (pathname.isFile() && name.substring(name.indexOf(".") + 1).equals("xml"))
					|| (pathname.isDirectory() && !name.equals(".svn"));
		}
	};
	
	/**
	 * Java源代码存放路径
	 */
	private static String SRC_PATH = ParamUtil.getProjectPath() + "/src/main/java/";
	
	/**
	 * 获取模板列表
	 */
	@ResponseBody
	@RequestMapping(value = "getAllModuleName")
	public void getAllModuleName(HttpServletRequest request, HttpServletResponse response){
		try {
			String modulePath = ParamUtil.getWebPath()+"/b_ide/generate/modules";
			log.debug("模板文件路径："+modulePath);
			modulePath = FileUtil.path(modulePath);
			File file = new File(modulePath);
			if(!file.exists()){
				file.mkdirs();
			}
			StringBuffer str = new StringBuffer("{data:[ ");
			// 获得该文件夹内的所有文件   
	        File[] array = file.listFiles();
	        for(int i=0;i<array.length;i++)
	        {   	        	
	            if(array[i].isFile())//如果是文件
	            {   
	            	str.append("{name:'").append(array[i].getName().replace(".jsp", "")).append("',jspFile:'"+array[i].getName()+"'},");   
	            }
	        }
	        String result = str.substring(0, str.length()-1);	//截掉最后一位
	        result += "]}";
	        log.debug("模板列表："+result);
	        returnObjResultJson(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取接口列表并以treeveiw的形式返回
	 */
	@ResponseBody
	@RequestMapping(value = "getAllInterface")
	public void getAllInterface(HttpServletRequest request, HttpServletResponse response){
		try {
			String interfacePath = ParamUtil.getWebPath()+"/b_ide/generate/interface";
			log.debug("接口文件路径："+interfacePath);
			interfacePath = FileUtil.path(interfacePath);
			File file = new File(interfacePath);
			if(!file.exists()){
				file.mkdirs();
			}
			String filePath = FileUtil.path(file.toString());
			String root = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
			File[] list = file.listFiles(filter);
			StringBuffer str = new StringBuffer("{obj:[{text:'"+root+"',nodes:[");
			ShowAllInterfaceNameFile(list, str);
			str.append("]}]}");
			log.debug("接口列表："+str.toString());
			this.returnObjResultJson(response, str.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取单个接口XML文档内容
	 */
	@ResponseBody
	@RequestMapping(value = "getInterfaceXml")
	public void getInterfaceXml(HttpServletRequest request, HttpServletResponse response){
		// 转换请求参数
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String interfaceXmlfile = reqDs.getString("interfaceXmlfile"); // 接口xml文件名
		String realPath = Servlets.getRequest().getRealPath("/")+"b_ide/generate/"+interfaceXmlfile;
		
		String xmll = FileUtil.readFileFromPath(realPath);
		JSONObject xmlJSONObj = XML.toJSONObject(xmll); 
        //设置缩进  
        String jsonPrettyPrintString = xmlJSONObj.toString(4);
       /* returnMsgResultJson(jsonPrettyPrintString);*/
        returnObjResultJson(response, jsonPrettyPrintString);
	}
	
	/*
	 * 保存Po文件
	 */
	@ResponseBody
	@RequestMapping(value = "savePo")
	public void savePo(HttpServletRequest request, HttpServletResponse response){
		String msg="";
		String PO_START = "/**PO START**/";
		String PO_END = "/**PO END**/";
		String PACKAGE = "package ";
		String PUBLIC_CLASS = "public class ";
		
		// 转换请求参数
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String content = reqDs.getString("poContent"); // 文件内容
		String path = reqDs.getString("path"); // 文件路径
					
		while (content != null && content.indexOf(PO_START) > -1) {
			String poPath = "";
			String poName = "";
			String srcPath = SRC_PATH;
			BufferedWriter previewpo = null; 
			int poStart = content.indexOf(PO_START) + PO_START.length();
			int poEnd = content.indexOf(PO_END, poStart);
			String poContent = content.substring(poStart, poEnd);
			System.out.println("poContent=" + poContent);
			try {

				int pStart = poContent.indexOf(PACKAGE) + PACKAGE.length();
				int pEnd = poContent.indexOf(";", pStart);
				poPath = poContent.substring(pStart, pEnd);
				poPath = poPath.replace(".", "/") + "/";
				System.out.println("poPath=" + poPath);

				int cStart = poContent.indexOf(PUBLIC_CLASS) + PUBLIC_CLASS.length();
				int cEnd = poContent.indexOf(" {", cStart);
				poName = poContent.substring(cStart, cEnd);
				poName = poName.trim() + ".java";
				System.out.println("poName=" + poName);

				srcPath += poPath + poName;
				System.out.println("srcPath=" + srcPath);
				FileUtil.createFile(srcPath);
				previewpo = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(srcPath), "UTF-8"));
				previewpo.write(poContent, 0, poContent.length());
				previewpo.flush();
			} catch (IOException e) {
                System.out.println("出现异常");
            } finally {
				if(null != previewpo){
					try {
						previewpo.close();
					} catch (IOException e) {
                System.out.println("出现异常");
            }
				}

			}
			// 获取新的PO代码块
			content = content.substring(poEnd + PO_END.length());
		}

		msg = "SUCCESS";
		this.returnResultJson(response, msg);
	}
	
	
	/*
	 * 保存Action文件
	 */
	@ResponseBody
	@RequestMapping(value = "saveAction")
	public void saveAction(HttpServletRequest request, HttpServletResponse response){
		String srcPath = SRC_PATH;
		String PACKAGE = "package ";
		String PUBLIC_CLASS = "public class ";
		String msg="";
		String actionPath = "";
		String actionName = "";
		BufferedWriter previewpo = null; 
		
		try {
			// 转换请求参数
			IDataset reqDs = DatasetService.getInstace().getDataset(request);
			String content = reqDs.getString("poContent"); // 文件内容
			String path = reqDs.getString("path"); // 文件路径
			
			int pStart = content.indexOf(PACKAGE)+PACKAGE.length();
			int pEnd = content.indexOf(";", pStart);
			actionPath = content.substring(pStart, pEnd);
			actionPath = actionPath.replace(".", "/")+"/";
			System.out.println("actionPath="+actionPath);
						
			int cStart = content.indexOf(PUBLIC_CLASS)+PUBLIC_CLASS.length();
			int cEnd = content.indexOf(" extends ", cStart);
			actionName = content.substring(cStart, cEnd);
			actionName = actionName.trim()+".java";
			System.out.println("actionName="+actionName);
			
			srcPath +=  actionPath + actionName;
			System.out.println("srcPath="+srcPath);
			FileUtil.createFile(srcPath);
			previewpo = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(srcPath),"UTF-8"));
			previewpo.write(content, 0, content.length());
			previewpo.flush();
		} catch (IOException e) {
			msg=e.getMessage();
		}finally{
			if(null != previewpo){
				try {
					previewpo.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			
		}
		
		msg="SUCCESS";
		this.returnResultJson(response, msg);
	}
	
	/**
	 *  保存生成的JSP文件
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "jspSave")
	public void  jspSave(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String msg="";
		try {
			// 转换请求参数
			IDataset reqDs = DatasetService.getInstace().getDataset(request);
			String jspfile = reqDs.getString("jspfile"); // 文件内容
			String path = reqDs.getString("path"); // 文件路径
			jspFileUploads(jspfile,path);
			msg="SUCCESS";
		} catch (IOException e) {
			log.error(e.getMessage());
			msg=e.getMessage();
		}
		this.returnResultJson(response, msg);
	}
	
	/**
	 *  预览生成的JSP文件
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response){
		try {
			// 转换请求参数
			IDataset reqDs = DatasetService.getInstace().getDataset(request);
			String jspfile = reqDs.getString("jspfile"); // 文件内容
			String path = reqDs.getString("path"); // 文件路径
			fileUploads(jspfile, path);
		} catch (IOException e) {
                System.out.println("出现异常");
            }
	}
	
	private void fileUploads(String content,String jsPath)throws IOException{ 
		String js_Path = null;
		BufferedReader brbeg = null;
		BufferedReader brend = null;
		BufferedWriter tomcatPreviewbw = null;
		BufferedReader cstRw = null;
		InputStreamReader jspbeg = null;
		InputStreamReader jspend = null;
		try {
			log.debug("jspbeg="+ParamUtil.getJSPBEG());
			jspbeg = new InputStreamReader(new FileInputStream(ParamUtil.getJSPBEG()), "UTF-8");
			brbeg = new BufferedReader(jspbeg);
			
			log.debug("jspend="+ParamUtil.getJSPEND());
			jspend = new InputStreamReader(new FileInputStream(ParamUtil.getJSPEND()), "UTF-8");
			brend = new BufferedReader(jspend);
			
//			String tomcatPreviewJspPath = FileUtil.path(this.getServletContext().getRealPath(ParamUtil.getPreviewJsp()));
			String tomcatPreviewJspPath = Servlets.getRequest().getRealPath(ParamUtil.getPreviewJsp());
			log.debug("tomcatPreviewJsp="+tomcatPreviewJspPath);
			File tomcatPreviewJsp = new File(tomcatPreviewJspPath);
			if(!tomcatPreviewJsp.exists()){
				tomcatPreviewJsp.createNewFile();
			}
			
			tomcatPreviewbw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tomcatPreviewJsp),"UTF-8"));
			
			String str = null;
			js_Path = ParamUtil.getJsPath()+jsPath.replace(".jsp", ".js");
			js_Path = js_Path.split(ParamUtil.getConfig("web.path"))[1];
			js_Path = "<script type='text/javascript' charset='utf-8' src='<%=basePath%>"+js_Path+"'></script>";
			if (null == brbeg) {
				throw new BaseException(SysErr.E_IO_ERROR, "jspbeg读取失败");
			}
			while((str = brbeg.readLine())!=null){
				tomcatPreviewbw.write(str+"\n");
				if(str.startsWith("<!-- Self reference JS-->")){
					tomcatPreviewbw.write(js_Path);
				}
			}
			
			tomcatPreviewbw.write(content, 0, content.length());
			tomcatPreviewbw.write("\n");
			
			//获取CST放置路径
			String cstFilePath = ParamUtil.getJspPath()+jsPath.replace(".jsp", ".cst");
			log.debug("cstFilePath="+cstFilePath);
			File cstFile = new File(cstFilePath);	
			if(!cstFile.exists())
			{
				FileUtil.createFile(cstFilePath);
			}
			
			cstRw = new BufferedReader(new InputStreamReader(new FileInputStream(cstFilePath), "UTF-8"));
			if (null == brend) {
				throw new BaseException(SysErr.E_IO_ERROR, "CST读取失败");
			}
			str = null;
			tomcatPreviewbw.write(CstLoadingUtilImpl.CST_BEG+"\n");
			while((str = cstRw.readLine())!=null){
				tomcatPreviewbw.write(str+"\n");
			}
			tomcatPreviewbw.write("\n"+CstLoadingUtilImpl.CST_END);

			if (null == brend) {
				throw new BaseException(SysErr.E_IO_ERROR, "jspend读取失败");
			}
			str = null;
			while((str = brend.readLine())!=null){
				tomcatPreviewbw.write(str+"\n");
			}			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
                System.out.println("出现异常");
            }finally{
			if(null != brbeg){
				try {
					brbeg.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if(null != jspbeg){
				try {
					jspbeg.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }

			}
			if(null != brend){
				try {
					brend.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }

			}
			if(null != jspend){
				try {
					jspend.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }

			}
			if(null != cstRw){
				try {
					cstRw.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }

			}
			if(null != tomcatPreviewbw){
				try {
					tomcatPreviewbw.close();
				} catch (Exception e) {
					System.out.println("操作失败");
				}
			}
		}
	}
	
	/**
	 * 展示所有的XML接口文档列表
	 * @param list
	 * @param str
	 */
	private static void ShowAllInterfaceNameFile(File[] list, StringBuffer str) {
		if (null!=list && list.length > 0) {
			for (File e : list) {
				String filePath = FileUtil.path(e.toString());
				String aa = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
				if (e.isDirectory()) {
					str.append("{text:'"+aa+"',").append("nodes:[");
					ShowAllInterfaceNameFile(e.listFiles(filter), str);
					str.append("]},");
				} else if (e.isFile()) {
					str.append("{text:'"+aa).append("'},");
				}
			}
		} else {
			str.append("[");
		}
		str.deleteCharAt(str.length() - 1);
	}
	
	/**
	 * 保存临时jsp文件
	 * @param content
	 * @param path1
	 * @throws Exception
	 */
	private void jspFileUploads(String content,String path1)throws Exception{ 
		/*String sourcePath=  this.getClass().getClassLoader().getResource("/").getPath().toString();*/
		BufferedReader brbeg = null;
		BufferedReader brend = null;
		BufferedWriter previewJspbw = null;
		BufferedReader cstRw = null;
		InputStreamReader jspbeg = null;
		InputStreamReader jspend = null;

		try {
			log.debug("jspbeg="+ParamUtil.getJSPBEG());
			jspbeg = new InputStreamReader(new FileInputStream(ParamUtil.getJSPBEG()), "UTF-8");
			brbeg = new BufferedReader(jspbeg);
			
			log.debug("jspend="+ParamUtil.getJSPEND());
			jspend = new InputStreamReader(new FileInputStream(ParamUtil.getJSPEND()), "UTF-8");
			brend = new BufferedReader(jspend);
			
			String jspPath=ParamUtil.getJspPath();
			String jsPath = ParamUtil.getJsPath();
			String filePath = jspPath+path1;
			String jsFilePath = jsPath+path1.replaceFirst("\\.jsp", "\\.js");
			File file=new File(filePath);
			File jsFile=new File(jsFilePath);

			log.debug("jsFilePath="+jsFilePath);
			if(!jsFile.exists())
			{
				FileUtil.createFile(jsFilePath);
			}

			if(!file.exists())
			{
				FileUtil.createFile(filePath);
			}
			
			//获取JS放置路径
			jsFilePath = jsFilePath.split(ParamUtil.getConfig("web.path"))[1];
			
			previewJspbw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
			
			String str = null;
			if (null == brbeg) {
				throw new BaseException(SysErr.E_IO_ERROR, "jsp读取失败");
			}
			while((str = brbeg.readLine())!=null){
				previewJspbw.write(str+"\n");
				if(str.startsWith("<!-- Self reference JS-->")){
					previewJspbw.write("<script type=\"text/javascript\" src=\"<%=basePath%>"+jsFilePath+"\" charset=\"utf-8\"></script>"+"\n");
				}					
			}
			
			previewJspbw.write(content, 0, content.length());
			previewJspbw.write("\n");
			
			//获取CST放置路径
			String cstFilePath = filePath.replaceFirst("\\.jsp", "\\.cst");
			log.debug("cstFilePath="+cstFilePath);
			File cstFile = new File(cstFilePath);	
			if(!cstFile.exists()){
				FileUtil.createFile(cstFilePath);
			}
			
			cstRw = new BufferedReader(new InputStreamReader(new FileInputStream(cstFilePath), "UTF-8"));
			if (null == cstRw) {
				throw new BaseException(SysErr.E_IO_ERROR, "cst文件读取失败");
			}
			str = null;
			previewJspbw.write(CstLoadingUtilImpl.CST_BEG+"\n");
			while((str = cstRw.readLine())!=null){
				previewJspbw.write(str+"\n");
			}
			previewJspbw.write("\n"+CstLoadingUtilImpl.CST_END);

			if (null == brend) {
				throw new BaseException(SysErr.E_IO_ERROR, "jsp读取失败");
			}
			str = null;
			while((str = brend.readLine())!=null){
				previewJspbw.write(str+"\n");
			}			
			
		} catch (FileNotFoundException e) {
			throw new Exception("file not found exception:"+e.getMessage());
		} catch (IOException e) {
			throw new Exception("ioException:"+e.getMessage());
		}finally{
			if(null != brbeg){
				try {
					brbeg.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }

			}
			if(null != brend){
				try {
					brend.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }

			}
			if(null != cstRw){
				try {
					cstRw.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }

			}
			if(null != jspbeg){
				try {
					jspbeg.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}

			if(null != jspend){
				try {
					jspend.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }

			}
			if(null != previewJspbw) {
				try {
					previewJspbw.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
		}
	}
}
