package com.adtec.comp.tseq.test.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.adtec.comp.tseq.dto.TSeqTranGetSeqReqDTO;
import com.adtec.comp.tseq.test.form.seqNo;
import com.adtec.comp.tseq.test.service.GetSeqNumService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.starring_seq.SeqTran;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.common.web.BaseController;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "${adminPath}/comp/tseq/test/getSeqNum")
public class GetSeqNumController extends BaseController{
	@Autowired
	private GetSeqNumService getSeqNumService;
	
	
	
	/**
	 * 返回获取流水号页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getSeqNumForm" })
	public String getFlowNumForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/tseq/test/getSeqNumForm";
	}

	
	/**
	 * 获取流水号
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "add" })
	public void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String SEQ_CRT_ID = reqDs.getString("SEQ_CRT_ID");
		
		TSeqTranGetSeqReqDTO reqBody = new TSeqTranGetSeqReqDTO();
		reqBody.setSEQ_CRT_ID(SEQ_CRT_ID);

		IDataset resDs = getSeqNumService.add(reqBody); 
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 获取缓存流水号
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getCacheSeq" })
	public void getCacheSeq(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		IDataset resDs = DatasetService.getInstace().getDataset();
		String seqCrtID = reqDs.getString("SEQ_CRT_ID");
		
		ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> seqCrtMapList = SeqTran.getSeqCrtMapList();
		ConcurrentHashMap<String,Object> seqCrtMap = seqCrtMapList.get(seqCrtID);
		if(seqCrtMap==null){
			throw new BaseException(SysErr.E_MESSAGE, "缓存数据为空");
		}
		List<seqNo> list = new ArrayList<seqNo>();
		
		for(int i=0;i<SeqTran.NUM;i++){
			ConcurrentHashMap<String,Object> tmpMap = (ConcurrentHashMap<String, Object>) seqCrtMap.get((i+1)+"");
			seqNo seq=new seqNo();
			
			@SuppressWarnings("unchecked")
			ConcurrentLinkedQueue<String> seqNoList = (ConcurrentLinkedQueue<String>) tmpMap.get(SeqTran.KEY_SEQ_NO_LIST);
			String modDt=(String) tmpMap.get("modDt");
			String seqNo="";
			Iterator iterator = seqNoList.iterator();
			while (iterator.hasNext()) {
				seqNo+=iterator.next()+"|";
			}
			seq.setSerNo(String.valueOf(i));
			seq.setSeqNoStr(seqNo);
			seq.setNum(String.valueOf(seqNoList.size()));
			seq.setModDt(modDt);
			
			list.add(seq);
		}
		
		String jsonStr =JSON.toJSONString(list);
	    renderDatasetString(response, jsonStr, SysErr.E_SUCCESS, "获取缓存流水号信息数据成功");
	}
	
	/**
	 * 获取流水号
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getSeqNo" })
	public void getSeqNo(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		IDataset resDs = DatasetService.getInstace().getDataset();
		String seqCrtID = reqDs.getString("SEQ_CRT_ID");
		
		String seqCrtSeq="";
		try {
			seqCrtSeq = SeqTran.getOutSysSeq(seqCrtID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(SysErr.E_MESSAGE, "获取流水失败:"+e.getMessage());
		}
		seqNo seq=new seqNo();
		seq.setSerNo(seqCrtSeq);
		String jsonStr =JSON.toJSONString(seq);
	    renderDatasetString(response, jsonStr, SysErr.E_SUCCESS, "获取流水号数据成功");
	}
	
	
	
}
