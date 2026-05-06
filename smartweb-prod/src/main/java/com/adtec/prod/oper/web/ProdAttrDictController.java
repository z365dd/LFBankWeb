package com.adtec.prod.oper.web;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.prod.oper.entity.ProdAttrDictDO;
import com.adtec.prod.oper.entity.ProdAttrDictListDO;
import com.adtec.prod.oper.service.ProdAttrDictService;
import com.adtec.prod.util.ProdStrEnum;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.utils.DictUtils;
import net.sf.json.JSONArray;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/prod/oper/prodAttrDict")
public class ProdAttrDictController extends BaseController {
	@Autowired
	private ProdAttrDictService prodAttrDictService;

	/**
	 * 返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "prodAttrDictList" })
	public String prodAttrDictList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/prodAttrDictList";
	}

	/**
	 * 返回表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "prodAttrDictForm" })
	public String prodAttrDictForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/prodAttrDictForm";
	}

	/**
	 * 新增提交
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "add" })
	public void add(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);

		ProdAttrDictDO prodAttrDictDO = getRequestBody(reqDs);
		if (prodAttrDictService.get(prodAttrDictDO) != null) {
			throw new BaseException(SysErr.E_MESSAGE, "属性"+prodAttrDictDO.getKEY_NO()+"重复！");
		}
		if (prodAttrDictService.getByName(prodAttrDictDO) != null) {
			throw new BaseException(SysErr.E_MESSAGE, "属性名称重复！");
		}
		prodAttrDictService.insert(prodAttrDictDO);

		IDataset resDs = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 修改提交
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "revice" })
	public void revice(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);

		ProdAttrDictDO prodAttrDictDO = getRequestBody(reqDs);
		prodAttrDictService.update(prodAttrDictDO);

		IDataset resDs = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 列表查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "qry" })
	public void qry(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");

		ProdAttrDictDO prodAttrDictDO = getRequestBody(reqDs);
		List<ProdAttrDictDO> list = prodAttrDictService.list(prodAttrDictDO, start, limit);
		int total = prodAttrDictService.getTotal(prodAttrDictDO);
		IDataset resDs = DatasetService.getInstace().getDataset(list, ProdAttrDictDO.class);
		chgDict(resDs, true);
		resDs.setTotalCount(total);

		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 获取详细数据
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getDetail" })
	public void getDetail(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);

		ProdAttrDictDO prodAttrDictDO = prodAttrDictService.get(getRequestBody(reqDs));

		// 引入组件或服务
		String KEY_TP = prodAttrDictDO.getKEY_TP();
		String KEY_NO = prodAttrDictDO.getKEY_NO();
		if ("01".equals(KEY_TP)) {
			Map<String, String> svcPara = prodAttrDictService.getSvcParaDistinct();
			prodAttrDictDO.setQUOTE(svcPara.get(KEY_NO));
		} else if ("02".equals(KEY_TP)) {
			Map<String, String> compPara = prodAttrDictService.getCompParaDistinct();
			prodAttrDictDO.setQUOTE(compPara.get(KEY_NO));
		}

		IDataset resDs = DatasetService.getInstace().getDataset(prodAttrDictDO, ProdAttrDictDO.class);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 删除
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "delete" })
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		ProdAttrDictDO prodAttrDictDO = getRequestBody(reqDs);
		prodAttrDictService.delete(prodAttrDictDO);
		IDataset resDs = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 数字字典转换
	 * @param ds
	 * @param isAction
	 */
	private void chgDict(IDataset ds, boolean isAction){
		if(null==ds){
			return;
		}
		//中文描述
		ds.addColumn("KEY_TP_STR", DatasetColumnType.DS_STRING);
		ds.addColumn("ENTER_TP_STR", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//中文描述
			String KEY_TP = ds.getString("KEY_TP");
			String KEY_NO = ds.getString("KEY_NO");
			String QUOTE = "";
			ds.updateString("KEY_TP_STR", DictUtils.getDictLabel(KEY_TP, "KEY_TP", ""));
			ds.updateString("ENTER_TP_STR", DictUtils.getDictLabel(ds.getString("ENTER_TP"), "ENTER_TP", ""));

			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"Detail('" + KEY_NO + "')\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"Revice('" + KEY_NO + "')\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"Delete('" + KEY_NO + "', '" + KEY_TP + "')\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}

	private ProdAttrDictDO getRequestBody(IDataset reqDs) {
		ProdAttrDictDO prodAttrDictDO = new ProdAttrDictDO();
		prodAttrDictDO.setKEY_NO(reqDs.getString("KEY_NO"));
		prodAttrDictDO.setKEY_NAME(reqDs.getString("KEY_NAME"));
		prodAttrDictDO.setKEY_TP(reqDs.getString("KEY_TP"));
		prodAttrDictDO.setVAL_TP(reqDs.getString("VAL_TP"));
		prodAttrDictDO.setVAL_LEN(reqDs.getString("VAL_LEN"));
		prodAttrDictDO.setENTER_TP(reqDs.getString("ENTER_TP"));
		prodAttrDictDO.setSHORT_RMRK(reqDs.getString("SHORT_RMRK"));
		prodAttrDictDO.setMID_RMRK(reqDs.getString("MID_RMRK"));
		prodAttrDictDO.setLONG_RMRK(reqDs.getString("LONG_RMRK"));
		prodAttrDictDO.setDAC(reqDs.getString("DAC"));

		String LIST_STR = reqDs.getString("row");

		if (LIST_STR != null && !"".equals(LIST_STR)) {
			JSONArray jsonArr = JSONArray.fromObject(LIST_STR);
			List<ProdAttrDictListDO> LIST = JSONArray.toList(jsonArr, ProdAttrDictListDO.class);
			if (LIST != null && LIST.size() > 0) {
				prodAttrDictDO.setLIST(LIST);
			}
		}
		return prodAttrDictDO;
	}
}
