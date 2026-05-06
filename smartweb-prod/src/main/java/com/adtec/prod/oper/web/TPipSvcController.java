/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod.oper拦截器模块
* 功能描述: 服务控制类
* 类 名 称  : TPipSvcController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200108<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.web;

import com.adtec.prod.oper.entity.*;
import com.adtec.prod.oper.service.ProdAttrService;
import com.adtec.prod.oper.service.TPipAttrSvcService;
import com.adtec.prod.oper.service.TPipSvcService;
import com.adtec.framework.impl.share.dataset.CommonDatasets;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasets;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.service.AreaService;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务Controller
 * @author zh
 * @version 20200108
 */
@Controller
@RequestMapping(value = "${adminPath}/prod/oper/tPipSvc")
public class TPipSvcController extends BaseController {
	@Autowired
	private ProdAttrService prodAttrService;
	@Autowired
	private TPipAttrSvcService tPipAttrSvcService;

	@Autowired
	private TPipSvcService tPipSvcService;
	
	/*首页页面路径*/
	private String PATH = (ParamUtil.getJspPath().split(ParamUtil.getConfig("web.view.prefix"))[1])+"/"+("prod/oper/tPipSvc.jsp".replace(ParamUtil.getConfig("web.view.suffix"),""));
	/**
	 * 进入管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"manage", ""})
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"Manage";
	}
	
	/**
	 * 列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tPipSvcList"})
	public String tPipSvcList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tPipSvcAdd"})
	public String tPipSvcAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tPipSvcUpdate"})
	public String tPipSvcUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tPipSvcDetail"})
	public String tPipSvcDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"DetailForm";
	}
	
	/**
	* 新增交易
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="insert")
	public void insert(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TPipSvcDO obj = DatasetService.getInstace().getObject(reqDs, TPipSvcDO.class);
		String rowPara = reqDs.getString("rowPara");
		String rowScen = reqDs.getString("rowScen");

		if (tPipSvcService.get(obj) != null) {
			throw new BaseException(SysErr.E_MESSAGE, "新增数据服务码已存在！");
		}

		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();

			if(tPipSvcService.insert(obj) && tPipAttrSvcService.insert(obj, rowPara, rowScen)){
				setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
			}else{
				setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "新增交易失败！");
			}
		}catch (Exception e) {
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		}finally {
			try {
				session.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	* 修改交易
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TPipSvcDO obj = DatasetService.getInstace().getObject(reqDs, TPipSvcDO.class);
		obj.setCompNo(reqDs.getString("temp1"));
		String rowPara = reqDs.getString("rowPara");
		String rowScen = reqDs.getString("rowScen");

		IDBSession session = DBSessionFactory.getSession();
		ResultSet rs = null;
		try {
			session.beginTransaction();

			// 属性原来的场景
			TPipSvcCompScenDO tPipSvcCompScenDO = new TPipSvcCompScenDO();
			tPipSvcCompScenDO.setCompNo(obj.getCompNo());
			tPipSvcCompScenDO.setSvcCode(obj.getSvcCode());
			List<TPipSvcCompScenDO> list = tPipAttrSvcService.listScen(tPipSvcCompScenDO, 0, 0);

			// 提交上来的场景
			List<TPipSvcCompScenDO> list2 = JSON.parseArray(rowScen, TPipSvcCompScenDO.class);

			// 被原子产品引用的场景
			rs = session.getResultSet("select b.* from t_pip_atom_prod a inner join t_pip_atom_prod_svc b on a.atom_prod_code=b.atom_prod_code where a.comp_no=? and b.svc_code=?",
					obj.getCompNo(), obj.getSvcCode());
			while (rs.next()) {
				String sceneNo = rs.getString("scene_no");
				boolean isErr = true;
				for (TPipSvcCompScenDO tPipSvcCompScenDO2 : list2) {
					if (sceneNo.equals(tPipSvcCompScenDO2.getSceneNo())) {
						isErr = false;
						break;
					}
				}

				if (isErr) {
					// 提交上来的场景中没有包含，比较原来是否有保存
					for (TPipSvcCompScenDO tPipSvcCompScenDO3 : list) {
						// 有保存原子产品的场景
						if (sceneNo.equals(tPipSvcCompScenDO3.getSceneNo())) {
							throw new BaseException(SysErr.E_MESSAGE, "不能删除被原子产品引用的场景");
						}
					}
				}
			}

			if(tPipSvcService.update(obj) && tPipAttrSvcService.delete(obj) && tPipAttrSvcService.insert(obj, rowPara, rowScen)){
				setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
			}else{
				setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "更新交易失败！");
			}
		}catch (Exception e) {
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		}finally {
			try {
				session.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				session.closeResultSetAndStatement(rs);
				DBSessionFactory.closeSession(session);
			} catch (SQLException e) {
				logger.error("关闭删除数据异常：" + e.getMessage());
			}
		}
	} 
	
	/**
	* 删除交易
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TPipSvcDO obj = DatasetService.getInstace().getObject(reqDs, TPipSvcDO.class);

		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();

			if(tPipSvcService.delete(obj) && tPipAttrSvcService.delete(obj)){
				setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
			}else{
				setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "删除交易失败！");
			}
		}catch (Exception e) {
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		}finally {
			try {
				session.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	* 列表查询
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="list")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TPipSvcDO obj = DatasetService.getInstace().getObject(reqDs, TPipSvcDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TPipSvcDO> list = tPipSvcService.list(obj, start, limit);
		int total = tPipSvcService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TPipSvcDO.class);
		chgDict(resDs, true);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	}

	/**
	 * 列表查询
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value="listForMsmall")
	public void listForMsmall(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TPipSvcDO obj = DatasetService.getInstace().getObject(reqDs, TPipSvcDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TPipSvcDO> list = tPipSvcService.list(obj, start, limit);
		int total = tPipSvcService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TPipSvcDO.class);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	}

	@ResponseBody
	@RequestMapping(value="listScenForMsmall")
	public void listScenForMsmall(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);

		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");

		TPipSvcCompScenDOForMsmall tPipSvcCompScenDO = new TPipSvcCompScenDOForMsmall();
		tPipSvcCompScenDO.setCompNo(reqDs.getString("compNo"));
		tPipSvcCompScenDO.setSvcCode(reqDs.getString("svcCode"));
		tPipSvcCompScenDO.setSaleProdCode(reqDs.getString("saleProdCode"));
		List<TPipSvcCompScenDOForMsmall> list = tPipAttrSvcService.listScenForMsmall(tPipSvcCompScenDO, start, limit);
		resDs = DatasetService.getInstace().getDataset(list, TPipSvcCompScenDO.class);
		resDs.setTotalCount(tPipAttrSvcService.getTotalForMsmall(tPipSvcCompScenDO));

		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	}

	/**
	* 明细查询
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="get")
	public void get(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TPipSvcDO tPipSvcDO = DatasetService.getInstace().getObject(reqDs, TPipSvcDO.class);
		TPipSvcDO obj = tPipSvcService.get(tPipSvcDO);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, TPipSvcDO.class);

		resDs.setDatasetName("tPipSvcDODs");

		String compNo = reqDs.getString("compNo");
		String svcCode = reqDs.getString("svcCode");
		TPipCompSvcParaDO tPipCompSvcParaDO = new TPipCompSvcParaDO();
		tPipCompSvcParaDO.setCompNo(compNo);
		tPipCompSvcParaDO.setSvcCode(svcCode);
		List<TPipCompSvcParaDO> list = tPipAttrSvcService.listPara(tPipCompSvcParaDO, 0, 0);
		List<TPipCompSvcParaDOTemp> listTemp = new ArrayList<>();
		for (TPipCompSvcParaDO t : list) {
			TPipCompSvcParaDOTemp tPipCompSvcParaDOTemp = new TPipCompSvcParaDOTemp();
			tPipCompSvcParaDOTemp.setKEY_NO(t.getKeyNo());
			tPipCompSvcParaDOTemp.setKEY_NAME(t.getKeyName());
			listTemp.add(tPipCompSvcParaDOTemp);
		}
		IDataset tPipCompSvcParaDODs = DatasetService.getInstace().getDataset(listTemp, TPipCompSvcParaDOTemp.class);
		tPipCompSvcParaDODs.setDatasetName("tPipCompSvcParaDODs");

		TPipSvcCompScenDO tPipSvcCompScenDO = new TPipSvcCompScenDO();
		tPipSvcCompScenDO.setCompNo(compNo);
		tPipSvcCompScenDO.setSvcCode(svcCode);
		List<TPipSvcCompScenDO> list2 = tPipAttrSvcService.listScen(tPipSvcCompScenDO, 0, 0);
		IDataset tPipSvcCompScenDODs = DatasetService.getInstace().getDataset(list2, TPipSvcCompScenDO.class);
		tPipSvcCompScenDODs.setDatasetName("tPipSvcCompScenDODs");

		IDatasets resDss = new CommonDatasets();
		resDss.putDataset(resDs);
		resDss.putDataset(tPipCompSvcParaDODs);
		resDss.putDataset(tPipSvcCompScenDODs);
//		chgDict(resDs, false);
		setResponseDataset(request, response, resDss, SysErr.E_SUCCESS, "明细查询交易成功！");
	}

	@RequestMapping(value = { "Enable" })
	public void Enable(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TPipSvcDO tPipSvcDO = DatasetService.getInstace().getObject(reqDs, TPipSvcDO.class);
		tPipSvcService.Enable(tPipSvcDO);
		IDataset resDs = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	@RequestMapping(value = { "Stopping" })
	public void Stopping(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TPipSvcDO tPipSvcDO = DatasetService.getInstace().getObject(reqDs, TPipSvcDO.class);
		tPipSvcService.Stopping(tPipSvcDO);
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
		SystemService systemService = SpringContextHolder.getBean("systemService");
		OfficeService officeService = SpringContextHolder.getBean("officeService");
		AreaService areaService = SpringContextHolder.getBean("areaService");
		//新增组件号中文描述列
		ds.addColumn("compNoStr", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		List<ProdAttrDO> list = prodAttrService.list(new ProdAttrDO());
		while(ds.hasNext()){
			ds.next();
			//更新组件号中文描述
			String compNo = ds.getString("compNo");
			String svcCode = ds.getString("svcCode");
			String openStat = ds.getString("openStat");
			for (ProdAttrDO prodAttrDO : list) {
				if (compNo.equals(prodAttrDO.getCOMP_NO())) {
					ds.updateString("compNoStr", prodAttrDO.getCOMP_NAME());
					break;
				}
			}
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail('" + compNo + "', '" + svcCode + "')\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update('" + compNo + "', '" + svcCode + "')\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del('" + compNo + "', '" + svcCode + "')\" >删除</a>");
				if("Y".equals(openStat)){
					action.append("<a href=\"#JavaScript:void(0);\" onClick=\"Stopping('" + compNo + "', '" + svcCode + "')\">停用</a> ");
					ds.updateString("openStat", "启用");
				}else{
					action.append("<a href=\"#JavaScript:void(0);\" onClick=\"Enable('" + compNo + "', '" + svcCode + "')\">启用</a> ");
					ds.updateString("openStat", "停用");
				}
				ds.updateString("action", action.toString());
			}
		}
	}
}