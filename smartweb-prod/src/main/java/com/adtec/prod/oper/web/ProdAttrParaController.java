package com.adtec.prod.oper.web;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.prod.oper.entity.ProdAttrDictDO;
import com.adtec.prod.oper.entity.ProdAttrParaDO;
import com.adtec.prod.oper.service.ProdAttrDictService;
import com.adtec.prod.oper.service.ProdAttrParaService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "${adminPath}/prod/oper/prodAttrPara")
public class ProdAttrParaController extends BaseController {
	@Autowired
	private ProdAttrParaService prodAttrParaService;
	@Autowired
	private ProdAttrDictService prodAttrDictService;

	/**
	 * 返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "prodAttrParaList" })
	public String prodAttrList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/prodAttrParaList";
	}

	/**
	 * 返回表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "prodAttrParaForm" })
	public String prodAttrForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/prodAttrParaForm";
	}

	/**
	 * 列表查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "qry" })
	public void qry(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String STAT = reqDs.getString("STAT");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		ProdAttrParaDO prodAttrParaDO = new ProdAttrParaDO();
		prodAttrParaDO.setCOMP_NO(COMP_NO);

		List<ProdAttrParaDO> list = prodAttrParaService.list(prodAttrParaDO);
		List<ProdAttrParaDO> list_single = new ArrayList<ProdAttrParaDO>();
		List<ProdAttrParaDO> list_res = new ArrayList<ProdAttrParaDO>();
		List<String> list_no = new ArrayList<String>();
		for (ProdAttrParaDO DO : list) { // 在次循环分页
			if (!list_no.contains(DO.getCOMP_NO())) {
				list_no.add(DO.getCOMP_NO());
				list_single.add(DO);
			}
		}
		if (list_single.size() >= start && list_single.size()>0) {
			for (int i = start; i <= start + limit; i++) {
				if (list_single.size() < i) {
					break;
				}
				list_res.add(list_single.get(i));
			}
		}
		IDataset resDs = DatasetService.getInstace().getDataset(list_res, ProdAttrParaDO.class);
		resDs.setTotalCount(list_res.size());
		chgDict(resDs);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 删除
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "delete" })
	public void delete(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		ProdAttrParaDO prodAttrParaDO = new ProdAttrParaDO();
		prodAttrParaDO.setCOMP_NO(COMP_NO);
		int rs = prodAttrParaService.delete(prodAttrParaDO);
		IDataset resDs = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");

	}
	
	/**
	 * 列表操作处理
	 */
	private void chgDict(IDataset ds) {
		if (null == ds) {
			return;
		}
		ds.addColumn("ACTION", DatasetColumnType.DS_STRING);
		ds.beforeFirst();
		while (ds.hasNext()) {
			ds.next();
			/* 添加相关操作按钮 */
			StringBuffer action = new StringBuffer();
			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"Detail('" + ds.getString("COMP_NO") + "','"
					+ ds.getString("COMP_NAME") + "')\" >详情</a>");
			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"Revice('" + ds.getString("COMP_NO") + "','"
					+ ds.getString("COMP_NAME") + "')\" >修改</a>");
			/*if (ds.getString("MOD_STAT").equals("01")) {
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"UpdateStat('" + ds.getString("COMP_NO")
						+ "','02')\" >关闭</a>");
			} else if (ds.getString("MOD_STAT").equals("02")) {
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"UpdateStat('" + ds.getString("COMP_NO")
						+ "','01')\" >开通</a>");
			}*/
			action.append(
					"	<a href=\"JavaScript:void(0);\" onClick=\"Delete('" + ds.getString("COMP_NO") + "')\" >删除</a>");
			ds.updateString("ACTION", action.toString());
		}
	}

	/**
	 * 获取组件属性表 存在的所有不重复组件
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getCompList" })
	public void getCompList(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		ProdAttrParaDO prodAttrParaDO = new ProdAttrParaDO();
		List<ProdAttrParaDO> list = prodAttrParaService.list(prodAttrParaDO);
		List<ProdAttrParaDO> list_single = new ArrayList<ProdAttrParaDO>();
		List<String> list_no = new ArrayList<String>();
		for (ProdAttrParaDO DO : list) { // 在次循环分页
			if (!list_no.contains(DO.getCOMP_NO())) {
				list_no.add(DO.getCOMP_NO());
				list_single.add(DO);
			}
		}

		List<Map<String, Object>> maps = new ArrayList();
		Map<String, Object> emptyMap = new HashMap<>(2);
		emptyMap.put("label", "请选择");
		emptyMap.put("value", "");
		maps.add(emptyMap);
		for (ProdAttrParaDO DO : list_single) {
			Map<String, Object> map = new HashMap<>(2);
			map.put("label", DO.getCOMP_NO()+"--"+DO.getCOMP_NAME());
			map.put("value", DO.getCOMP_NO());
			maps.add(map);
		}
		Map<String, Object> m = new HashMap<>();
		m.put("retCode", "0000");
		m.put("list", maps);
		renderString(response, m);

	}

	/**
	 * 新增
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "add" })
	public void add(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String addORupdate = reqDs.getString("addORupdate"); 
		String COMP_NO = reqDs.getString("COMP_NO");
		String COMP_NAME = reqDs.getString("COMP_NAME");
		String DATA_ARR = reqDs.getString("DATA_ARR");
		JSONArray json = JSONArray.fromObject(DATA_ARR);
		List<ProdAttrParaDO> list = JSONArray.toList(json, ProdAttrParaDO.class);
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			if("update".equals(addORupdate)){
				//删除表中数据
				ProdAttrParaDO delDO = new ProdAttrParaDO();
				delDO.setCOMP_NO(COMP_NO);
				prodAttrParaService.delete(delDO,session);
			}
			
			for(ProdAttrParaDO DO : list){
				//设置组件号
				DO.setCOMP_NO(COMP_NO);
				DO.setCOMP_NAME(COMP_NAME);
				//设置 字段SER
				String SER = prodAttrParaService.getSER();
				if("".equals(SER)){
					SER = "1";
				}else{
					System.out.println(SER);
					SER = ""+(Integer.parseInt(SER)+1);
				}
				DO.setSER(SER);
				//设置KEY_TP=03的KEY_NO
				if("03".equals(DO.getKEY_TP())){
				String KEY_NO = prodAttrParaService.get03TP_KEY_NO();
					if("".equals(KEY_NO)){
						KEY_NO = "000000";
					}else{
						KEY_NO =""+(Integer.parseInt(KEY_NO)+1);
						String bef_str = "";
						if(KEY_NO.length()<6){
							for(int i=0;i<6-KEY_NO.length();i++){
								bef_str = bef_str+"0";
							}
						}
						KEY_NO = bef_str + KEY_NO;
					}
					DO.setKEY_NO(KEY_NO);
				}
				//设置标志
				DO.setFLG("Y");
				
				prodAttrParaService.add(DO,session);
			}
			session.endTransaction();	
		} catch (Exception e1) {
			try {
				session.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			e1.printStackTrace();
			try {
				throw e1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		IDataset resDs = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 获取属性列表(KEY_TP=02)
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getKeyTp" })
	public void getKeyTp(HttpServletRequest request, HttpServletResponse response){
		ProdAttrDictDO prodAttrDictDO = new ProdAttrDictDO();
		prodAttrDictDO.setKEY_TP("02");
		List<ProdAttrDictDO> list = prodAttrDictService.list(prodAttrDictDO);
		List<Map<String, Object>> maps = new ArrayList();
		Map<String, Object> emptyMap = new HashMap<>(2);
		emptyMap.put("label", "请选择");
		emptyMap.put("value", "");
		maps.add(emptyMap);
		for (ProdAttrDictDO DO : list) {
			Map<String, Object> map = new HashMap<>(2);
			map.put("label", DO.getKEY_NAME());
			map.put("value", DO.getKEY_NO());
			maps.add(map);
		}
		Map<String, Object> m = new HashMap<>();
		m.put("retCode", "0000");
		m.put("list", maps);
		renderString(response, m);
	}
	
	/**
	 * 获取单个属性详情
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getDict" })
	public void getDict(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		ProdAttrDictDO prodAttrDictDO = new ProdAttrDictDO();
		String KEY_NO = reqDs.getString("KEY_NO");
		prodAttrDictDO.setKEY_NO(KEY_NO);
		ProdAttrDictDO resDO =  prodAttrDictService.get(prodAttrDictDO);
		IDataset resDs = DatasetService.getInstace().getDataset(resDO,ProdAttrDictDO.class);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 根据组件号 查询对应的数据
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getPara" })
	public void getPara(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		ProdAttrParaDO prodAttrParaDO = new ProdAttrParaDO();
		prodAttrParaDO.setCOMP_NO(COMP_NO);
		List<ProdAttrParaDO> list = prodAttrParaService.list(prodAttrParaDO);
		IDataset resDs = DatasetService.getInstace().getDataset(list,ProdAttrParaDO.class);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
}
