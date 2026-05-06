package com.adtec.prod.oper.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.adtec.prod.oper.entity.BusiDO;
import com.adtec.prod.oper.service.ImportOrExportEntrParaService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/prod/oper/importOrExportEntr")
public class ImportOrExportEntrParaController extends BaseController {
	
	
	@Autowired
	private ImportOrExportEntrParaService service;
	
	@RequiresPermissions("user")
	@RequestMapping(value = "manageData")
	public String manageData(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/importOrExportEntrPara";
	}

	/**
	 * 业务参数导入
	 * @param file
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "importData")
	public void importData(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		 List<String> sqlList = new ArrayList<String>();
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			if (file == null) {
				throw new RuntimeException("导入文件为空!");
			}

			String fileName = file.getOriginalFilename();
			InputStream is = file.getInputStream();

			if (StringUtils.isBlank(fileName)) {
				throw new RuntimeException("导入文件为空！");
			}
			
			if (!fileName.endsWith("txt")){
				 throw new RuntimeException("文件不是txt格式！");
			}
			
			String path = ParamUtil.getConfig("EntrParaPath");
			File fileDir = new File(path);
			if(!fileDir.exists()){
				fileDir.mkdirs();
			}
			File newfile = new File(path+fileName);

			if(!newfile.exists()){
				newfile.createNewFile();
			}
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newfile)));
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String str;
			String line = "\r";
			while (null != (str = br.readLine())) {
				bw.write(str+line);
				bw.flush();
				   if ("".equals(str.trim()) || str.startsWith("--")) {
	                    continue;
	                }
	                if (!str.endsWith(";")) {
	                    throw new RuntimeException("语句不是以分号;结束！");
	                }
	                if (!str.startsWith("INSERT INTO ")) {
	                    throw new RuntimeException("语句不是insert语句！");
	                }
	                sqlList.add(str.substring(0, str.length() - 1));
	
			}

			//文件路径为:
			String absPath = newfile.getAbsolutePath();
			

		} catch (Exception e) {
			logger.error("导入数据失败", e);
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		}finally {
			if(null != bw){
				try {
					bw.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if(null != br){
				try {
					br.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
		}
		service.importData(sqlList);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "导入数据成功");
	}

	/**
	 * 业务参数导出
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "exportData")
	public void exportData(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String BUSI_NO = reqDs.getString("BUSI_NO");
		String path = ParamUtil.getConfig("uploadFile");
//		File fileDir = new File(path);
//		if(!fileDir.exists()){
//			fileDir.mkdirs();
//		}
		long time = new Date().getTime();
		String FileName =  BUSI_NO + "_" + time + ".txt";
		FileName = FileName.replaceAll("\\\\", "")
				.replaceAll("/", "")
				.replaceAll(",", "");
		response.setCharacterEncoding("UTF-8");
		FileInputStream in =null;
		PrintWriter out =null;
		BufferedReader br=null;
		try {
			File file = new File(path+"/"+FileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			file=service.getSQLFile(file,BUSI_NO);
			String fileName = new String((BUSI_NO + time+ ".txt").getBytes(), "utf-8")
					.replaceAll("\r","")
					.replaceAll("\n","")
					.replaceAll("\\\\","");;
			response.reset();// 重置 响应头
			response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");// 下载文件的名称
			response.setContentType("application/x-download");// 告知浏览器下载文件，而不是直接打开，浏览器默认为打开
			response.setCharacterEncoding("utf-8");
			in = new FileInputStream(file);
			//response.resetBuffer();
			
			// 创建输出流
		   out = response.getWriter();
		   
			// 循环将输入流中的内容读取到缓冲区当中
			br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String str = null;
            while((str = br.readLine())!=null){//使用readLine方法，一次读一行
                out.println(str);
            }
            br.close();    
			// 关闭输出流
			out.close();
			// 关闭文件输入流
			in.close();
			
		} catch (Exception e) {
			logger.error("导入数据失败", e);
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		}finally{
			try {
				if(br!=null){
					br.close();
				}
				if(in!=null){
					in.close();
				}
				if(out!=null){
					out.close();
				}
			} catch (IOException e) {
                System.out.println("出现异常");
            }
			
		}
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "导出数据成功");
	}

	/**
	 * 获取业务下拉框数据
	 * 
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getBusiNo" })
	public void getBusiNo(HttpServletRequest request, HttpServletResponse response) {
		List<BusiDO> list = service.getBusiNo(new BusiDO());
		List<Map<String, Object>> maps = new ArrayList();
		Map<String, Object> emptyMap = new HashMap<>(2);
		emptyMap.put("label", "请选择");
		emptyMap.put("value", "");
		maps.add(emptyMap);
		for (BusiDO DO : list) {
			Map<String, Object> map = new HashMap<>(2);
			map.put("label", DO.getBUSI_NO() + "--" + DO.getBUSI_NAME());
			map.put("value", DO.getBUSI_NO());
			maps.add(map);
		}
		Map<String, Object> m = new HashMap<>();
		m.put("retCode", "0000");
		m.put("list", maps);
		renderString(response, m);
	}
/*	
	public static void imp(String fileName) throws Exception {
		  try {
		   System.out.println("导入开始-----");
	       System.out.println("导入结束-----");
		  } catch (Exception e) {
		   System.out.println("导入异常-----");
		   throw e;
		  }
	 }*/
	
	 private static List<String> tableList = new ArrayList<String>();

	    static {
	        tableList.add("SYS_RENT");
	        tableList.add("T_MS_PART");
	        tableList.add("T_MS_PART_NODE");
	        tableList.add("T_MS_SERVICE");
	        tableList.add("T_MS_SERVICE_INSTANCE");
	        tableList.add("T_MS_RULE");
	        tableList.add("T_MS_BLACK_LIST");
	        tableList.add("T_MS_WHITE_LIST");
	        tableList.add("T_MS_FLOW");
	        tableList.add("T_MS_FUSE");
	        tableList.add("T_MS_GREY");
	    }
	
}
