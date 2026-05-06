package com.adtec.comp.sign.test.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.comp.sign.dto.FSignChkRecvOthChkReqDTO;
import com.adtec.comp.sign.entity.FSignTPipBusiDO;
import com.adtec.comp.sign.test.service.SignChkOthService;
import com.adtec.comp.sign.test.service.SignFileService;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;


@Controller
@RequestMapping(value = "${adminPath}/comp/sign/test/signChkOth")
public class SignChkOthController extends BaseController {
	@Autowired
	private SignFileService signFileService;
	@Autowired
	private SignChkOthService signChkOthService;

	/**
	 * 返回签约对账页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "signChkOthForm" })
	public String signChkOthForm(HttpServletRequest request, HttpServletResponse response, String OPER_TP) {
		return "starring/comp/sign/test/signChkOthForm";
	}
	
	  /**
	   * 获取业务编号列表 (91)
	   * @param BUSI_NO
	   * @param BUSI_NAME
	   * @param compNo
	   * @return
	   */
	  @RequiresPermissions({"user"})
	  @ResponseBody
	  @RequestMapping(value = {"busiList"})
	  public List<Map<String, Object>> busiList(HttpServletRequest request, HttpServletResponse response){
	  IDataset reqDs = DatasetService.getInstace().getDataset(request);
	  String compNo = reqDs.getString("compNo");
	    FSignTPipBusiDO reqBody = new FSignTPipBusiDO();
	    reqBody.setCompNo(compNo);
	    return this.signChkOthService.busiList(reqBody);
	  }
	// 提交
	@RequiresPermissions("user")
	@RequestMapping(value = "add")
	public void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String CHK_DATE = reqDs.getString("CHK_DATE");
		String FILE_ID = reqDs.getString("FILE_ID");
		String BUSI_NO = reqDs.getString("BUSI_NO");
		
		FSignChkRecvOthChkReqDTO reqBody = new FSignChkRecvOthChkReqDTO();
		reqBody.setCHK_DATE(CHK_DATE);
		
		// 调用文件获取，把文件上传到文件服务器
		reqBody.setREQ_FILE_SET_SEQ(signFileService.getFile(FILE_ID));

		IDataset resDs = signChkOthService.add(reqBody, BUSI_NO);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	
}
