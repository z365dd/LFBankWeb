package com.adtec.prod.oper.web;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.prod.oper.definition.atom.entity.TPipAtomProdDO;
import com.adtec.prod.oper.definition.atom.service.TPipAtomProdService;
import com.adtec.prod.oper.entity.ProdAttrDO;
import com.adtec.prod.oper.service.ProdAttrService;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.Dict;
import com.adtec.sys.modules.sys.utils.DictUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/prod/oper/prodAttr")
public class ProdAttrController extends BaseController{
	@Autowired
	private ProdAttrService prodAttrService;
	@Autowired
	private TPipAtomProdService tPipAtomProdService;
	
	/**
	 * 返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "prodAttrList" })
	public String prodAttrList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/prodAttrList";
	}

	/**
	 * 返回表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "prodAttrForm" })
	public String prodAttrForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/prodAttrForm";
	}

	/**
	 * 新增提交 after
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "add" })
	public void add(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String COMP_NAME = reqDs.getString("COMP_NAME");
		String PROD_COMP_TP = reqDs.getString("PROD_COMP_TP");
		ProdAttrDO prodAttrDO = new ProdAttrDO();
		prodAttrDO.setCOMP_NO(COMP_NO);
		prodAttrDO.setCOMP_NAME(COMP_NAME);
		prodAttrDO.setPROD_COMP_TP(PROD_COMP_TP);
		prodAttrDO.setOPEN_STAT("N");

		ProdAttrDO prodAttrDO1 = new ProdAttrDO();
		prodAttrDO1.setCOMP_NO(COMP_NO);
		List<ProdAttrDO> list = prodAttrService.list(prodAttrDO1);
		if(list.size()>=1){
			throw new BaseException(SysErr.E_DEFAULT, "该组件编号已存在，新增失败！");
		}
		ProdAttrDO temp = prodAttrService.getByName(prodAttrDO);
		if (temp != null) {
			throw new BaseException(SysErr.E_DEFAULT, "该组件名称已存在，新增失败！");
		}
		prodAttrService.insert(prodAttrDO);
		IDataset resDs = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 修改提交after
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "revice" })
	public void revice(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String COMP_NAME = reqDs.getString("COMP_NAME");
		String PROD_COMP_TP = reqDs.getString("PROD_COMP_TP");
		String STAT = reqDs.getString("OPEN_STAT");
		ProdAttrDO prodAttrDO = new ProdAttrDO();
		prodAttrDO.setCOMP_NO(COMP_NO);
		prodAttrDO.setCOMP_NAME(COMP_NAME);
		prodAttrDO.setPROD_COMP_TP(PROD_COMP_TP);
		prodAttrDO.setOPEN_STAT(STAT);

		TPipAtomProdDO tPipAtomProdDO = new TPipAtomProdDO();
		tPipAtomProdDO.setCompNo(COMP_NO);
		List<TPipAtomProdDO> tPipAtomProdDOList = tPipAtomProdService.list(tPipAtomProdDO);
		if (tPipAtomProdDOList.size() > 0) {
			ProdAttrDO prodAttrDO1 = new ProdAttrDO();
			prodAttrDO1.setCOMP_NO(COMP_NO);
			List<ProdAttrDO> list = prodAttrService.list(prodAttrDO1);
			for (ProdAttrDO prodAttrDO2 : list) {
				if (!prodAttrDO2.getPROD_COMP_TP().equals(PROD_COMP_TP)) {
					throw new BaseException(SysErr.E_DEFAULT, "该组件被原子产品引用，不能修改类型！");
				}
			}
		}

		prodAttrService.update(prodAttrDO);
		IDataset resDs = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 列表查询after
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "qry" })
	public void qry(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String PROD_COMP_TP = reqDs.getString("PROD_COMP_TP");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		ProdAttrDO prodAttrDO = new ProdAttrDO();
		prodAttrDO.setCOMP_NO(COMP_NO);
		prodAttrDO.setPROD_COMP_TP(PROD_COMP_TP);
		List<ProdAttrDO> list = prodAttrService.qry(prodAttrDO,start,limit);
		IDataset resDs = DatasetService.getInstace().getDataset(list,ProdAttrDO.class);
		resDs.setTotalCount(prodAttrService.getTotal(prodAttrDO));
		chgDict(resDs);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	/**
	 * 列表操作处理
	 */
	private void chgDict(IDataset ds) {
		if(null==ds){
			return;
		}
		ds.addColumn("ACTION", DatasetColumnType.DS_STRING);
		ds.addColumn("FLG_STR", DatasetColumnType.DS_STRING);
		ds.addColumn("STAT_STR", DatasetColumnType.DS_STRING);

		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//中文描述
			ds.updateString("FLG_STR", DictUtils.getDictLabel(ds.getString("PROD_COMP_TP"), "PROD_COMP_TP", ""));

			if("Y".equals(ds.getString("OPEN_STAT"))){
				ds.updateString("STAT_STR", "开通");
			}else if("N".equals(ds.getString("OPEN_STAT"))){
				ds.updateString("STAT_STR", "关闭");
			}

			/*添加相关操作按钮*/
			StringBuffer action = new StringBuffer();
			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"Detail('" + ds.getString("COMP_NO") + "','"+ds.getString("COMP_NAME")+"','"+ds.getString("PROD_COMP_TP")+"','"+ds.getString("OPEN_STAT")+"')\" >详情</a>");
			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"Revice('" + ds.getString("COMP_NO") + "','"+ds.getString("COMP_NAME")+"','"+ds.getString("PROD_COMP_TP")+"','"+ds.getString("OPEN_STAT")+"')\" >修改</a>");
			if(ds.getString("OPEN_STAT").equals("Y")){
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"UpdateStat('"+ds.getString("COMP_NO")+"','N')\" >关闭</a>");
			}else if(ds.getString("OPEN_STAT").equals("N")){
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"UpdateStat('"+ds.getString("COMP_NO")+"','Y')\" >开通</a>");
			}
			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"Delete('"+ds.getString("COMP_NO")+"')\" >删除</a>");
			ds.updateString("ACTION", action.toString());
		}
	}

	/**
	 * 获取组件列表lable-value
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getComp" })
	public void getComp(HttpServletRequest request, HttpServletResponse response) {
		List<ProdAttrDO> list = prodAttrService.qry(new ProdAttrDO(),0,0);

		List<Map<String, Object>> maps = new ArrayList();;
		Map<String, Object> emptyMap = new HashMap<>(2);
		emptyMap.put("label", "请选择");
		emptyMap.put("value", "");
		maps.add(emptyMap);
		for(ProdAttrDO DO : list){
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
	 * 删除after
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "delete" })
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		ProdAttrDO prodAttrDO = new ProdAttrDO();
		prodAttrDO.setCOMP_NO(COMP_NO);
		prodAttrService.delete(prodAttrDO);
		IDataset resDs = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 修改状态
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "updateStat" })
	public void updateStat(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String STAT = reqDs.getString("OPEN_STAT");
		ProdAttrDO prodAttrDO = new ProdAttrDO();
		prodAttrDO.setCOMP_NO(COMP_NO);
		prodAttrDO.setOPEN_STAT(STAT);
		prodAttrService.updateStat(prodAttrDO);
		IDataset resDs = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	@RequestMapping(value = "selectData")
	public void selectData(@RequestParam(required=false) String type, @RequestParam(required=false) String isblank, @RequestParam(required=false) String blankText, @RequestParam(required=false) String blankValue, HttpServletResponse response) {
		List<Dict> list = new ArrayList<Dict>();
		if(!DataUtil.isNullStr(isblank) && "true".equals(isblank)){
			//单选下拉框需要添加空选项
			Dict d = new Dict();
			d.setDictTp(type);
			d.setLabel(blankText);
			d.setValue(blankValue);
			list.add(d);
		}

		list.addAll(DictUtils.getDictList(type));
		for (int i=0; i<list.size(); i++) {
			if ("01".equals(list.get(i).getValue()) || "04".equals(list.get(i).getValue())) {
				list.remove(i);
			}
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("retCode", "0000");
		m.put("list", list);
		renderString(response, m);
	}

	@RequestMapping(value = "selectData2")
	public void selectData2(@RequestParam(required=false) String type, @RequestParam(required=false) String isblank, @RequestParam(required=false) String blankText, @RequestParam(required=false) String blankValue, HttpServletResponse response) {
		List<Dict> list = new ArrayList<Dict>();
		if(!DataUtil.isNullStr(isblank) && "true".equals(isblank)){
			//单选下拉框需要添加空选项
			Dict d = new Dict();
			d.setDictTp(type);
			d.setLabel(blankText);
			d.setValue(blankValue);
			list.add(d);
		}

		list.addAll(DictUtils.getDictList(type));
		for (int i=0; i<list.size(); i++) {
			if ("03".equals(list.get(i).getValue())) {
				list.remove(i);
			}
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("retCode", "0000");
		m.put("list", list);
		renderString(response, m);
	}

}
