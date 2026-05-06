package com.adtec.prod.oper.web;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adtec.framework.impl.db.session.DBSessionFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.prod.dto.FActQryAcctInfoReqDTO;
import com.adtec.prod.oper.dao.ProdAttrDao;
import com.adtec.prod.oper.definition.saleprod.service.TPipSaleProdService;
import com.adtec.prod.oper.entity.TParaRelatSys;
import com.adtec.prod.oper.entity.TPipBusiDO;
import com.adtec.prod.oper.entity.TPipBusiParaDO;
import com.adtec.prod.oper.entity.TPipBusiParaDOTemp;
import com.adtec.prod.oper.service.BusiDemoService;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "${adminPath}/prod/oper/busiDemo")
public class BusiDemoController extends BaseController {
	@Autowired
	private BusiDemoService busiDemoService;

	@Autowired
	private ProdAttrDao prodAttrDao;
	@Autowired
	private TPipSaleProdService tPipSaleProdService;
	@Autowired
	private OfficeService officeService;

	/**
	 * 返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "busiDemoList" })
	public String busiDemoList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/busiDemoList";
	}
	/**
	 * 返回新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "busiDemoForm" })
	public String busiDemoForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/busiDemoForm";
	}

	/*以下为20200117代码*/
	/**
	 * 返回公共属性页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "busiCommon" })
	public String busiCommon(HttpServletRequest request, HttpServletResponse response){
		IDataset reqs = DatasetService.getInstace().getDataset(request);
		String prodLineCode = reqs.getString("saleProdCode");
		if(!StringUtil.isEmpty(prodLineCode)){
			return "starring/prod/oper/busiDemoCommon";
		}else{
			return "";
		}
	}

	/**
	 * 获取下拉框渠道
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getChnl" })
	public void getChnl(HttpServletRequest request, HttpServletResponse response){
		IDataset resBody = busiDemoService.getgetChnl();
		setResponseDataset(request, response, resBody, SysErr.E_SUCCESS, "交易成功");
	}

	@RequestMapping(value = { "getInOutBankFlg" })
	public void getInOutBankFlg(HttpServletRequest request, HttpServletResponse response){
		IDataset resBody = busiDemoService.getInOutBankFlg();
		setResponseDataset(request, response, resBody, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 获取下拉框机构
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getBrch" })
	public void getBrch(HttpServletRequest request, HttpServletResponse response){
		IDataset resBody = busiDemoService.getgetBrch();
		setResponseDataset(request, response, resBody, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 获取下拉框关联系统编号
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "selectRelatSysNo" })
	public void selectRelatSysNo(HttpServletRequest request, HttpServletResponse response){

		ResultSet rs = null;
		List<TParaRelatSys> list = Lists.newArrayList();
		try {
			rs = prodAttrDao.getSelectRelatSysNo();
			while (rs.next()) {
				TParaRelatSys tParaRelatSys = new TParaRelatSys();
				tParaRelatSys.setRelatSys(rs.getString("relat_sys"));
				tParaRelatSys.setSysName(rs.getString("sys_name"));
				list.add(tParaRelatSys);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(null != rs){
				try {
					rs.close();
				} catch (SQLException throwables) {
					System.out.println("sql异常");
				}
			}
		}

		IDataset resBody = DatasetService.getInstace().getDataset(list, TParaRelatSys.class);
		setResponseDataset(request, response, resBody, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 生成业务编号
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getBusiNo" })
	public void getBusiNo(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String SALE_PROD_CODE = reqDs.getString("SALE_PROD_CODE");

		TPipBusiDO tPipBusiDO = new TPipBusiDO();
		tPipBusiDO.setBusiNo(busiDemoService.getBusiNo(SALE_PROD_CODE));
		IDataset resDs = DatasetService.getInstace().getDataset(tPipBusiDO, TPipBusiDO.class);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");

	}

	@RequestMapping(value = { "getBrchName" })
	public void getBrchName(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String SALE_PROD_CODE = reqDs.getString("SALE_PROD_CODE");
		String brchId = tPipSaleProdService.get(SALE_PROD_CODE).getBrchId();
		TPipBusiDO tPipBusiDO = new TPipBusiDO();
		tPipBusiDO.setBrchName(officeService.get(brchId).getName());
		IDataset resDs = DatasetService.getInstace().getDataset(tPipBusiDO, TPipBusiDO.class);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 列表查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "qry" })
	public void qry(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String entrNo = reqDs.getString("entrNo");
		String entrName = reqDs.getString("entrName");
		String openStat = reqDs.getString("openStat");
		String saleProdCode = reqDs.getString("saleProdCode");
		String busiName = reqDs.getString("busiName");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		TPipBusiDO tPipBusiDO = new TPipBusiDO();
		tPipBusiDO.setEntrNo(entrNo);
		tPipBusiDO.setEntrName(entrName);
		tPipBusiDO.setOpenStat(openStat);
		tPipBusiDO.setSaleProdCode(saleProdCode);
		tPipBusiDO.setBusiName(busiName);
		IDataset resDs = busiDemoService.qry(tPipBusiDO, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 列表查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "qryHaveFeeClr" })
	public void qryHaveFeeClr(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TPipBusiDO tPipBusiDO = new TPipBusiDO();
		List<TPipBusiDO> busiList = busiDemoService.qryHaveFeeClr(tPipBusiDO, 0, 0);
		IDataset resDs = DatasetService.getInstace().getDataset(busiList,TPipBusiDO.class);
		resDs.setTotalCount(busiList.size());
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 列表查询
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = { "qryForMsmall" })
	public void qryForMsmall(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String entrNo = reqDs.getString("entrNo");
		String openStat = reqDs.getString("openStat");
		String saleProdCode = reqDs.getString("saleProdCode");
		String brchTp = reqDs.getString("brchTp");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		TPipBusiDO tPipBusiDO = new TPipBusiDO();
		tPipBusiDO.setEntrNo(entrNo);
		tPipBusiDO.setOpenStat(openStat);
		tPipBusiDO.setSaleProdCode(saleProdCode);
		tPipBusiDO.setBrchTp(brchTp);

		List<TPipBusiDO> busiList = busiDemoService.qryForMsmall(tPipBusiDO, start, limit);
		int total = busiDemoService.getTotalForMsmall(tPipBusiDO);
		IDataset resDs = DatasetService.getInstace().getDataset(busiList,TPipBusiDO.class);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 列表查询
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = { "qryCompSvcForMsmall" })
	public void qryCompSvcForMsmall(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String entrNo = reqDs.getString("entrNo");
		String busiNo = reqDs.getString("busiNo");
		String openStat = reqDs.getString("openStat");
		String saleProdCode = reqDs.getString("saleProdCode");
		String brchTp = reqDs.getString("brchTp");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		TPipBusiDO tPipBusiDO = new TPipBusiDO();
		tPipBusiDO.setEntrNo(entrNo);
		tPipBusiDO.setBusiNo(busiNo);
		tPipBusiDO.setOpenStat(openStat);
		tPipBusiDO.setSaleProdCode(saleProdCode);
		tPipBusiDO.setBrchTp(brchTp);

		List<TPipBusiDO> busiList = busiDemoService.qryCompSvcForMsmall(tPipBusiDO, start, limit);
		int total = busiDemoService.getTotalCompSvcForMsmall(tPipBusiDO);
		IDataset resDs = DatasetService.getInstace().getDataset(busiList,TPipBusiDO.class);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 查询有文件信息的业务列表
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = { "qryHasFileForMsmall" })
	public void qryHasFileForMsmall(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String entrNo = reqDs.getString("entrNo");
		String openStat = reqDs.getString("openStat");
		String saleProdCode = reqDs.getString("saleProdCode");
		String brchTp = reqDs.getString("brchTp");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		TPipBusiDO tPipBusiDO = new TPipBusiDO();
		tPipBusiDO.setEntrNo(entrNo);
		tPipBusiDO.setOpenStat(openStat);
		tPipBusiDO.setSaleProdCode(saleProdCode);
		tPipBusiDO.setBrchTp(brchTp);

		List<TPipBusiDO> busiList = busiDemoService.qryHasFileForMsmall(tPipBusiDO, start, limit);
		int total = busiDemoService.getTotalHasFileForMsmall(tPipBusiDO);
		IDataset resDs = DatasetService.getInstace().getDataset(busiList,TPipBusiDO.class);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 属性列表查询
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = { "qryParaForMsmall" })
	public void qryParaForMsmall(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String busiNo = reqDs.getString("busiNo");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		TPipBusiParaDO tPipBusiParaDO = new TPipBusiParaDO();
		tPipBusiParaDO.setBusiNo(busiNo);
		IDataset resDs = busiDemoService.qryParaForMsmall(tPipBusiParaDO, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 文件列表查询
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = { "qryFileForMsmall" })
	public void qryFileForMsmall(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String busiNo = reqDs.getString("busiNo");
		String entrNo = reqDs.getString("entrNo");
		String openStat = reqDs.getString("openStat");
		String saleProdCode = reqDs.getString("saleProdCode");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		TPipBusiDO tPipBusiDO = new TPipBusiDO();
		tPipBusiDO.setBusiNo(busiNo);
		tPipBusiDO.setEntrNo(entrNo);
		tPipBusiDO.setOpenStat(openStat);
		tPipBusiDO.setSaleProdCode(saleProdCode);

		List<TPipBusiDO> busiList = busiDemoService.qryFileForMsmall(tPipBusiDO, start, limit);

		int total = busiDemoService.getFileTotal(tPipBusiDO);
		IDataset resDs = DatasetService.getInstace().getDataset(busiList,TPipBusiDO.class);
		resDs.setTotalCount(total);

		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}


	/**
	 * 获取详细数据
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getDetail" })
	public void getDetail(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String busiNo = reqDs.getString("busiNo");
		TPipBusiDO tPipBusiDO = new TPipBusiDO();
		tPipBusiDO.setBusiNo(busiNo);
		IDataset resDs = busiDemoService.getDetail(tPipBusiDO);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 获取详细数据
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = { "getDetailForMsmall" })
	public void getDetailForMsmall(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String busiNo = reqDs.getString("busiNo");
		TPipBusiDO tPipBusiDO = new TPipBusiDO();
		tPipBusiDO.setBusiNo(busiNo);

		TPipBusiDO obj = busiDemoService.getDetailForMsmall(tPipBusiDO);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, TPipBusiDO.class);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 提交
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "add" })
	public void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		String busiNo = reqDs.getString("BUSI_NO");
		String busiName = reqDs.getString("BUSI_NAME");
		String prodNo = reqDs.getString("PROD_NO");
		String entrNo = reqDs.getString("ENTR_NO");
		// 业务参数
		List<TPipBusiParaDOTemp> KEY_LIST = new ArrayList<>();
		String PROD_DATA_STR = reqDs.getString("PROD_DATA_STR");
		
		//文件路径
		String FILE_PATH1 = reqDs.getString("FILE_PATH1");
		String FILE_PATH2 = reqDs.getString("FILE_PATH2");
		String FILE_PATH3 = reqDs.getString("FILE_PATH3");
		String operTy = reqDs.getString("OPER_TP");
		TPipBusiDO tPipBusiDO = new TPipBusiDO();
		try{
			// 清算机构
			String brchId = reqDs.getString("brchId");
			if (brchId == null || "".equals(brchId)) {
				brchId = UserUtils.getUser().getOffice().getId();
			}
			tPipBusiDO.setBrchId(brchId);
			tPipBusiDO.setBgImg(reqDs.getString("bgImg"));
			tPipBusiDO.setOpenStat("Y");
			tPipBusiDO.setBusiNo(busiNo);
			tPipBusiDO.setBusiName(busiName);
			tPipBusiDO.setSaleProdCode(prodNo);
			tPipBusiDO.setEntrNo(entrNo);
			tPipBusiDO.setBusiDesc(reqDs.getString("busiDesc"));
			//tPipBusiDO.setOpenGrpChnlNo(reqDs.getString("openGrpChnlNo"));

			// 无需清算 00 使用过渡户 01 其他模式 02
			String clrTp = reqDs.getString("clrTp");
			tPipBusiDO.setClrTp(clrTp);
			// 不检验 00 检查 01 其他模式检查 02
			String signPat = reqDs.getString("signPat");
			tPipBusiDO.setSignPat(signPat);
			// 不对账 00 两方对账 01 三方对账 02
			String chkPat = reqDs.getString("chkPat");
			tPipBusiDO.setChkPat(chkPat);
			// 不收取手续费 00 按成功金额比例收取 01 按成功笔数收取 02 其他模式 03
			String clrFeeTp = reqDs.getString("clrFeeTp");
			tPipBusiDO.setFeeTp(clrFeeTp);

			tPipBusiDO.setBusiTp(reqDs.getString("busiTp"));
			if("00".equals(reqDs.getString("busiTp"))){
				tPipBusiDO.setRelatSys(reqDs.getString("relatSysNo"));
			}

			if(!"".equals(PROD_DATA_STR)){
				JSONArray jsonArrProd = JSONArray.fromObject(PROD_DATA_STR);
				KEY_LIST = JSONArray.toList(jsonArrProd, TPipBusiParaDOTemp.class);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BaseException(SysErr.E_MESSAGE, "拼接请求报文出错！");
		}

		IDataset resDs = busiDemoService.add(tPipBusiDO,FILE_PATH1,FILE_PATH2,FILE_PATH3,operTy,KEY_LIST,reqDs);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "Enable" })
	public void Enable(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String busiNo = reqDs.getString("busiNo");
		busiDemoService.Enable(busiNo);
		IDataset resDs = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "Stopping" })
	public void Stopping(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String busiNo = reqDs.getString("busiNo");
		busiDemoService.Stopping(busiNo);
		IDataset resDs = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "delete" })
	public void delete(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String busiNo = reqDs.getString("busiNo");
		String openStat = reqDs.getString("openStat");
		if ("Y".equals(openStat)) {
			throw new BaseException("该业务已启用，不能删除");
		}
		busiDemoService.delete(busiNo);
		IDataset resDs = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 业务编号归属单位编号查询
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "entrSignData")
	public void entrSignData(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String BUSI_NO = reqDs.getString("BUSI_NO");

		IDataset resDs = busiDemoService.listEntrData(BUSI_NO);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 账户基本信息查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "factQryAcctInfo" })
	public void factQryAcctInfo(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		IDataset resDs = DatasetService.getInstace().getDataset();
		FActQryAcctInfoReqDTO obj = DatasetService.getInstace().getMBCObject(reqDs, FActQryAcctInfoReqDTO.class);
		try {
			resDs = busiDemoService.factQryAcctInfo(obj);
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "查询成功");
		} catch (Exception e) {
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "查询失败" + e);
		}
	}

}
