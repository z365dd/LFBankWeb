/**
* 系统名称: SmartWeb平台
* 模块名称: comp.tseq.oper拦截器模块
* 功能描述: 流水号生成器控制类
* 类 名 称  : TTseqSeqCrtController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200422<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.tseq.oper.web;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.comp.tseq.oper.entity.TTseqSeqCrtDO;
import com.adtec.comp.tseq.oper.entity.TseqTParaRelatSysDO;
import com.adtec.comp.tseq.oper.entity.TseqTPipCompParaDO;
import com.adtec.comp.tseq.oper.service.TTseqSeqCrtService;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.service.AreaService;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.DictUtils;

/**
 * 流水号生成器Controller
 * @author zhengjt
 * @version 20200422
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/tseq/oper/tTseqSeqCrt")
public class TTseqSeqCrtController extends BaseController {

	@Autowired
	private TTseqSeqCrtService tTseqSeqCrtService;
	
	/*首页页面路径*/
	private String PATH = "starring/comp/tseq/oper/tTseqSeqCrt";
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
	@RequestMapping(value ={"tTseqSeqCrtList"})
	public String tTseqSeqCrtList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tTseqSeqCrtAdd"})
	public String tTseqSeqCrtAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tTseqSeqCrtUpdate"})
	public String tTseqSeqCrtUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tTseqSeqCrtDetail"})
	public String tTseqSeqCrtDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"DetailForm";
	}
	
	/**
	 *  关联系统下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getRelatSys" })
	public void getRelatSys(HttpServletRequest request, HttpServletResponse response){
	List<TseqTParaRelatSysDO> list = tTseqSeqCrtService.relatSysList(new TseqTParaRelatSysDO());
	  List<Map<String, Object>> maps = new ArrayList();
	  Map<String, Object> emptyMap = new HashMap<>(2);
	        emptyMap.put("label", "--请选择--");
	        emptyMap.put("value", "");
	        maps.add(emptyMap);
	        for(TseqTParaRelatSysDO DO : list){
	         Map<String, Object> map = new HashMap<>(2);
	         map.put("label", DO.getRelatSys());
	         map.put("value", DO.getRelatSys());
	         maps.add(map);
	        }
	        Map<String, Object> m = new HashMap<>();
	        m.put("retCode", "0000");
	        m.put("list", maps);
	        renderString(response, m);
	 }
	
	/**
	 *  流水生成器ID下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getSeqCrtId" })
	public void getSeqCrtId(HttpServletRequest request, HttpServletResponse response){
	List<TTseqSeqCrtDO> list = tTseqSeqCrtService.list(new TTseqSeqCrtDO());
	  List<Map<String, Object>> maps = new ArrayList();
	  Map<String, Object> emptyMap = new HashMap<>(2);
	        emptyMap.put("label", "--请选择--");
	        emptyMap.put("value", "");
	        maps.add(emptyMap);
	        for(TTseqSeqCrtDO DO : list){
	         Map<String, Object> map = new HashMap<>(2);
	         map.put("label", DO.getSeqCrtId()+"-"+DO.getSeqCrtName());
	         map.put("value", DO.getSeqCrtId());
	         maps.add(map);
	        }
	        Map<String, Object> m = new HashMap<>();
	        m.put("retCode", "0000");
	        m.put("list", maps);
	        renderString(response, m);
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
		String SEQ_CRT_NAME = reqDs.getString("seqCrtName");
		String SEQ_CRT_ID = reqDs.getString("seqCrtId");
		String OUT_SYS = reqDs.getString("outSys");
		String OUT_SUB_SYS = reqDs.getString("outSubSys");
		String RESP_SEQ_LEN = reqDs.getString("respSeqLen");
		String MIN_VAL = reqDs.getString("minVal");
		String MAX_VAL = reqDs.getString("maxVal");
		String SEQ_LEN = reqDs.getString("seqLen");
		String RESET_CYC = reqDs.getString("resetCyc");
		String DIGIT_FLG = reqDs.getString("digitFlg");
		String EFFT_FLG = reqDs.getString("efftFlg");
		String STAR_EXPR = reqDs.getString("starExpr");
		String REPT_INSPT_STAT = reqDs.getString("reptInsptStat");
	
		TTseqSeqCrtDO reqBody = new TTseqSeqCrtDO();
		reqBody.setOutSys(OUT_SYS);
		reqBody.setSeqCrtName(SEQ_CRT_NAME);
		reqBody.setSeqCrtId(SEQ_CRT_ID);
		reqBody.setOutSubSys(OUT_SUB_SYS);
		reqBody.setRespSeqLen(Long.parseLong(RESP_SEQ_LEN));
		reqBody.setMinVal(MIN_VAL);
		reqBody.setMaxVal(MAX_VAL);
		reqBody.setSeqLen(Long.parseLong(SEQ_LEN));
		reqBody.setUseNum((long) 50);
		if (!DataUtil.isNullStr(RESET_CYC)) {
			reqBody.setResetCyc(Long.parseLong(RESET_CYC));
		}
		reqBody.setDigitFlg(DIGIT_FLG);
		reqBody.setEfftFlg(EFFT_FLG);
		reqBody.setStarExpr(STAR_EXPR);
		reqBody.setReptInsptStat(REPT_INSPT_STAT);
		reqBody.setModDt(DateUtil.getDateTime("yyyyMMddHHmmss"));
		reqBody.setRegDt(DateUtil.getDateTime("yyyyMMddHHmmss"));
		//TTseqSeqCrtDO obj = DatasetService.getInstace().getObject(reqDs, TTseqSeqCrtDO.class);
		if(tTseqSeqCrtService.insert(reqBody)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "新增交易失败！");
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
		String SEQ_CRT_NAME = reqDs.getString("seqCrtName");
		String SEQ_CRT_ID = reqDs.getString("seqCrtId");
		String OUT_SYS = reqDs.getString("outSys");
		String OUT_SUB_SYS = reqDs.getString("outSubSys");
		String RESP_SEQ_LEN = reqDs.getString("respSeqLen");
		String MIN_VAL = reqDs.getString("minVal");
		String MAX_VAL = reqDs.getString("maxVal");
		String SEQ_LEN = reqDs.getString("seqLen");
		String RESET_CYC = reqDs.getString("resetCyc");
		String DIGIT_FLG = reqDs.getString("digitFlg");
		String EFFT_FLG = reqDs.getString("efftFlg");
		String STAR_EXPR = reqDs.getString("starExpr");
		String REPT_INSPT_STAT = reqDs.getString("reptInsptStat");
	
		TTseqSeqCrtDO reqBody = new TTseqSeqCrtDO();
		reqBody.setOutSys(OUT_SYS);
		reqBody.setSeqCrtName(SEQ_CRT_NAME);
		reqBody.setSeqCrtId(SEQ_CRT_ID);
		reqBody.setOutSubSys(OUT_SUB_SYS);
		reqBody.setRespSeqLen(Long.parseLong(RESP_SEQ_LEN));
		reqBody.setMinVal(MIN_VAL);
		reqBody.setMaxVal(MAX_VAL);
		reqBody.setUseNum((long) 50);
		reqBody.setSeqLen(Long.parseLong(SEQ_LEN));
		if (!DataUtil.isNullStr(RESET_CYC)) {
			reqBody.setResetCyc(Long.parseLong(RESET_CYC));
		}
		reqBody.setDigitFlg(DIGIT_FLG);
		reqBody.setEfftFlg(EFFT_FLG);
		reqBody.setStarExpr(STAR_EXPR);
		reqBody.setReptInsptStat(REPT_INSPT_STAT);
		reqBody.setModDt(DateUtil.getDateTime("yyyyMMddHHmmss"));
		if(tTseqSeqCrtService.update(reqBody)){
			tTseqSeqCrtService.updateStat(SEQ_CRT_ID);
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "更新交易失败！");
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
		TTseqSeqCrtDO obj = DatasetService.getInstace().getObject(reqDs, TTseqSeqCrtDO.class);
		if(tTseqSeqCrtService.delete(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "删除交易失败！");
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
		TTseqSeqCrtDO obj = DatasetService.getInstace().getObject(reqDs, TTseqSeqCrtDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TTseqSeqCrtDO> list = tTseqSeqCrtService.list(obj, start, limit);
		int total = tTseqSeqCrtService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TTseqSeqCrtDO.class);
		chgDict(resDs, true);
		resDs.setTotalCount(total);
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
		String seqCrtId = reqDs.getString("seqCrtId");
		if (DataUtil.isNullStr(seqCrtId)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[seqCrtId]不能空！");
		}		

		TTseqSeqCrtDO obj = tTseqSeqCrtService.get(seqCrtId);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, TTseqSeqCrtDO.class);
		chgDict(resDs, false);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "明细查询交易成功！");
	}
	
	/**
	* 获取最大流水号长度
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="getMaxNodeLen")
	public void getMaxNodeLen(HttpServletRequest request, HttpServletResponse response) {
		TseqTPipCompParaDO compParaDO = new TseqTPipCompParaDO();
		compParaDO.setCompNo("999205");
		compParaDO.setKeyNo("MaxNodeNum");
		TseqTPipCompParaDO obj = tTseqSeqCrtService.get(compParaDO);
		if (obj == null) {
			renderString(response, ""); 
		} else {
			renderString(response, obj.getKv().length()); 
		}
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
		//新增外部系统中文描述列
		ds.addColumn("outSysStr", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新外部系统中文描述
			String outSys = ds.getString("outSys");
			ds.updateString("outSysStr", DictUtils.getDictLabels(outSys, "", outSys));
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" +"'"+ds.getString("seqCrtId")+"'" + ")\" >详细</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("seqCrtId")+"'" + ")\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("seqCrtId")+"'" + ")\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
	
	
}