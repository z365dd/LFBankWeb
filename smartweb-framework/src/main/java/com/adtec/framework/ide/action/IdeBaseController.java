package com.adtec.framework.ide.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.common.util.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 前端编辑器控制基础类
 * @author chenyl
 *
 */
public class IdeBaseController {
    /**
     * 日志对象
     */
    protected final static Logger log = LoggerFactory.getLogger(IdeBaseController.class);
    
	/**
	 * 响应码
	 */
	private String code;
	/**
	 * 响应信息
	 */
	private String msg;
	
	/**
	 * 将响应信息都封装到map中
	 * 
	 * retCode  响应编码
	 * retMsg   响应信息
	 * total    响应记录数<可选>
	 * rows     响应列表<可选>
	 * 
	 * 适用于响应移动终端app
	 * 
	 */
	private Map<String, Object> map = new HashMap<String, Object>();
	
	/**
	 * @description:List,Message转换为JSON数据,(json数据进行了封装,异步获取数据时list=data.obj,Message=data.msg)
	 * @param: list:集合对象,Message:提示信息
	 * @return: void 
	 */
	protected  void returnResultJson(HttpServletResponse response, List list,String... Message){  
		  PrintWriter pw = null;
		  try{
			  pw = response.getWriter();
			  JSONObject resultmessage =  JsonUtil.generate(list,Message);
	          response.setCharacterEncoding("UTF-8");
	          response.setContentType("application/json");
	          response.setHeader("Cache-Control", "no-cache");
	          pw.write(resultmessage.toString());
	          log.info("-----"+resultmessage.toString());
		  }
		  catch(Exception e){
			  log.error("异常："+e.getMessage());
			  if(null != pw){
				  pw.write("系统异常，请联系管理员");
			  }
		  }
		  finally{
			  if(null != pw){
				  pw.flush();
				  try {
					  pw.close();
				  } catch (Exception e) {
					  System.out.println("操作失败");
				  }
			  }
		  }
	} 
	
	/**
	 * @description:Object转换为JSON数据(json数据进行了封装,异步获取数据时obj=data.obj)
	 * @param: Object:对象,Message:提示信息
	 * @return: void 
	 */
	protected  void returnResultJson(HttpServletResponse response, Object obj){
		  PrintWriter pw = null;
		  try{
			  pw = response.getWriter();
			  JSONObject resultmessage =  JsonUtil.generate(obj);
	          response.setCharacterEncoding("UTF-8");
	          response.setContentType("application/json");
	          response.setHeader("Cache-Control", "no-cache");
	          pw.write(resultmessage.toString());
	          log.info("-----"+resultmessage.toString());
		  }
		  catch(Exception e){
			  log.error("异常："+e.getMessage());
			  if(null != pw){
				  pw.write("系统异常，请联系管理员");
			  }
		  }
		  finally{
			  if(null != pw){
				  pw.flush();
				  try {
					  pw.close();
				  } catch (Exception e) {
					  System.out.println("操作失败");
				  }
			  }
		  }
	}
	
	/**
	 * @description:Boolean转换为JSON数据(json数据进行了封装,异步获取数据时flag=data.flag)
	 * @param: flag:判断信息
	 * @return: void 
	 */
	protected void returnResultJson(HttpServletResponse response, Boolean flag){
		  PrintWriter pw = null;
		  try{
			  pw = response.getWriter();
			  JSONObject resultmessage =  JsonUtil.generate(flag);
	          response.setCharacterEncoding("UTF-8");
	          response.setContentType("application/json");
	          response.setHeader("Cache-Control", "no-cache");
	          pw.write(resultmessage.toString());
	          log.info("-----"+resultmessage.toString());
		  }
		  catch(Exception e){
			  log.error("异常："+e.getMessage());
			  if(null != pw){
				  pw.write("系统异常，请联系管理员");
			  }
		  }
		  finally{
		  	if(null != pw){
				  pw.flush();
				try {
					pw.close();
				} catch (Exception e) {
					System.out.println("操作失败");
				}
			}
			  
			 
		  }  
	} 
	/**
	 * @description:List转换为JSON数据,zTree树形结构
	 * @param: list:集合
	 * @return: void 
	 */
	protected void returnResultJsonTree(HttpServletResponse response, List list){
		  PrintWriter pw = null;
		  try{
			  pw = response.getWriter();
			  JSONArray resultmessage =  JsonUtil.generateTree(list);
	          response.setCharacterEncoding("UTF-8");
	          response.setContentType("application/json");
	          response.setHeader("Cache-Control", "no-cache");
	          pw.write(resultmessage.toString());
	          log.info("-----"+resultmessage.toString());
		  }
		  catch(Exception e){
			  log.error("异常："+e.getMessage());
			  if(null != pw){
				  pw.write("系统异常，请联系管理员");
			  }
		  }
		  finally{
		  	if(null != pw){
				pw.flush();
				try {
					pw.close();
				} catch (Exception e) {
					System.out.println("操作失败");
				}
			}

		  }  
	} 
	/**
	 * @description:Object转换为JSON数据,不进行封装
	 * @param: Object:对象 
	 * @return: void 
	 */
	protected  void returnObjResultJson(HttpServletResponse response, Object obj){
		  PrintWriter pw = null;
		  try{
			  pw = response.getWriter();
			  JSONObject resultmessage =  JsonUtil.generateObj(obj);
	          response.setCharacterEncoding("UTF-8");
	          response.setContentType("application/json");
	          response.setHeader("Cache-Control", "no-cache");
	          pw.write(resultmessage.toString());
	          log.info("-----"+resultmessage.toString());
		  }
		  catch(Exception e){
			  log.error("异常："+e.getMessage());
			  if(null != pw){
				  pw.write("系统异常，请联系管理员");
			  }
		  }
		  finally{
			  if(null != pw){
				  pw.flush();
				  try {
					  pw.close();
				  } catch (Exception e) {
					  System.out.println("操作失败");
				  }
			  }
		  }		  
	}
	
	
	/**
	 * @description:easyui表格分页(已封装)
	 * @param: list:list数据集合,num:数据总数
	 * @return: void 
	 */
	protected void returnEasyUIResultJson(HttpServletResponse response, List list,Integer num){
		  PrintWriter pw = null;
		  try{
			  pw = response.getWriter();
			  JSONObject resultmessage =  JsonUtil.generateEasyUI(list,num);
	          response.setCharacterEncoding("UTF-8");
	          response.setContentType("application/json");
	          response.setHeader("Cache-Control", "no-cache");
	          pw.write(resultmessage.toString());
	          log.info("-----"+resultmessage.toString());
		  }
		  catch(Exception e){
			  log.error("异常："+e.getMessage());
			  if(null != pw){
				  pw.write("系统异常，请联系管理员");
			  }
		  }
		  finally{
		  	if(null != pw){
				pw.flush();
				pw.close();
			}

		  }		
	} 
	
	/**
	 * Map转换成为JSON数据格式
	 * @param map
	 * @return void
	 */
	protected void returnMap2Json(HttpServletResponse response, Map<String, Object> map){
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			JSONObject resultmessage =  JsonUtil.generateMap(map);
			response.setCharacterEncoding("UTF-8");
	        response.setContentType("application/json");
	        response.setHeader("Cache-Control", "no-cache");
	        pw.write(resultmessage.toString());
	        log.info("-----"+resultmessage.toString());
		} catch (Exception e) {
			log.error("异常："+e.getMessage());
			if(null != pw){
				pw.write("系统异常，请联系管理员");
			}
		}
		finally{
			if(null != pw){
				pw.flush();
				try {
					pw.close();
				} catch (Exception e) {
					System.out.println("操作失败");
				}
			}
		}
	}
	/**
	 * 将数据封装成图表中的饼图、环状图、地极图所需要的json格式
	 * @param obj
	 */
	protected void returnChartPieJson(HttpServletResponse response, Object obj){
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			JSONArray resultmessage =  JsonUtil.generateObjToChartPie(obj);
			response.setCharacterEncoding("UTF-8");
	        response.setContentType("application/json");
	        response.setHeader("Cache-Control", "no-cache");
	        pw.write(resultmessage.toString());
	        log.info("-----"+resultmessage.toString());
		} catch (Exception e) {
			log.error("异常："+e.getMessage());
			if(null != pw){
				pw.write("系统异常，请联系管理员");
			}
		}
		finally{
			if(null != pw){
				pw.flush();
				try {
					pw.close();
				} catch (Exception e) {
					System.out.println("操作失败");
				}
			}
		}
	}
	/**
	 * 将数据封装成图表中的柱状图、雷达图、曲线图所需的json格式
	 * @param obj
	 */
	protected void returnChartBarJson(HttpServletResponse response, List objList){
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			JSONObject resultmessage =  JsonUtil.generateObjListToChartBar(objList);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
	        response.setHeader("Cache-Control", "no-cache");
	        pw.write(resultmessage.toString());
	        log.info("-----"+resultmessage.toString());
		} catch (Exception e) {
			log.error("异常："+e.getMessage());
			if(null != pw){
				pw.write("系统异常，请联系管理员");
			}
		}
		finally{
			if(null != pw){
				pw.flush();
				try {
					pw.close();
				} catch (Exception e) {
					System.out.println("操作失败");
				}
			}
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	
}
