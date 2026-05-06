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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.comp.tseq.oper.entity.TTseqJrnlDO;
import com.adtec.comp.tseq.oper.entity.TTseqSeqCrtDO;
import com.adtec.comp.tseq.oper.service.TTseqSeqResetService;
import com.adtec.framework.common.util.DataUtil;
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

/**
 * 流水号生成器Controller
 * @author zhengjt
 * @version 20200422
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/tseq/oper/tTseqSeqReset")
public class TTseqSeqResetController extends BaseController {

	@Autowired
	private TTseqSeqResetService tTseqSeqResetService;
	
	/*首页页面路径*/
	private String PATH = "starring/comp/tseq/oper/tTseqSeqReset";
	
	/**
	 * 进入重置管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"resetManage", ""})
	public String resetManage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/tseq/oper/tTseqSeqResetManage";
	}
	
	/**
	 * 进入重置列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tTseqSeqResetList"})
	public String tTseqSeqResetList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/tseq/oper/tTseqSeqResetList";
	}
	
	/**
	 * 进入重置修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tTseqSeqResetForm"})
	public String tTseqSeqResetForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/tseq/oper/tTseqSeqResetForm"; 
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
		String RESET_VAL = reqDs.getString("resetVal");
	
		TTseqSeqCrtDO reqBody = new TTseqSeqCrtDO();
		reqBody.setOutSys(OUT_SYS);
		reqBody.setSeqCrtName(SEQ_CRT_NAME);
		reqBody.setSeqCrtId(SEQ_CRT_ID);
		reqBody.setOutSubSys(OUT_SUB_SYS);
		reqBody.setRespSeqLen(Long.parseLong(RESP_SEQ_LEN));
		reqBody.setMinVal(MIN_VAL);
		reqBody.setMaxVal(MAX_VAL);
		reqBody.setSeqLen(Long.parseLong(SEQ_LEN));
		if (!DataUtil.isNullStr(RESET_CYC)) {
			reqBody.setResetCyc(Long.parseLong(RESET_CYC));
		}
		reqBody.setDigitFlg(DIGIT_FLG);
		reqBody.setEfftFlg(EFFT_FLG);
		reqBody.setStarExpr(STAR_EXPR);
		reqBody.setReptInsptStat(REPT_INSPT_STAT);
		reqBody.setResetVal(RESET_VAL);
		if(tTseqSeqResetService.update(reqBody)){
			tTseqSeqResetService.updateStat(SEQ_CRT_ID);
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "更新交易失败！");
		}
	} 
	/**
	* 重置列表查询
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="resetList")
	public void resetList(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TTseqSeqCrtDO obj = DatasetService.getInstace().getObject(reqDs, TTseqSeqCrtDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TTseqSeqCrtDO> list = tTseqSeqResetService.list(obj, start, limit);
		int total = tTseqSeqResetService.getTotal(obj);
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

		TTseqSeqCrtDO obj = tTseqSeqResetService.get(seqCrtId);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, TTseqSeqCrtDO.class);
		chgDict(resDs, false);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "明细查询交易成功！");
	}
	

	/**
	 * 重置列表数字字典转换
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
		//新增当前最大流水号
		ds.addColumn("maxSeqNum", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新当前最大流水号
			String maxSeqNum = "";
			TTseqJrnlDO jrnlDO = new TTseqJrnlDO();
			jrnlDO.setSeqCrtId(ds.getString("seqCrtId"));
			List<TTseqJrnlDO> list = tTseqSeqResetService.seqJrnlList(jrnlDO);
			if (list != null && list.size() !=0 ) {
				maxSeqNum = list.get(0).getCurSeq();
			}
			ds.updateString("maxSeqNum", maxSeqNum);
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("seqCrtId")+"','" + maxSeqNum +"')\" >修改</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}