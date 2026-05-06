package com.adtec.comp.fsvr.tec.web;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.comp.fsvr.dto.TfmngFileConvReqDTO;
import com.adtec.comp.fsvr.dto.TfmngFileDtlReqDTO;
import com.adtec.comp.fsvr.dto.TfmngFileDtlReqListDTO;
import com.adtec.comp.fsvr.tec.entity.TfsvrFileChgParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrFileChgTotDtlDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrFileColChgDtlParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrFileColChgTotParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrFileDtlParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrFileFmtListDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrFilePlateDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrFileTotParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrTemPlateDO;
import com.adtec.comp.fsvr.tec.service.FsvrFileFmtService;
import com.adtec.comp.fsvr.tec.service.TfsvrFileChgParaService;
import com.adtec.comp.fsvr.tec.service.TfsvrFileColChgDtlParaService;
import com.adtec.comp.fsvr.tec.service.TfsvrFileColChgTotParaService;
import com.adtec.comp.fsvr.tec.service.TfsvrFileDtlParaService;
import com.adtec.comp.fsvr.tec.service.TfsvrFileTotParaService;
import com.adtec.comp.fsvr.util.FsvrUtil;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.Dict;
import com.adtec.sys.modules.sys.service.AreaService;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.DictUtils;
import com.alibaba.fastjson.JSON;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "${adminPath}/comp/fsvr/tec/fsvrFileFmt")
public class FsvrFileFmtController extends BaseController{
	@Autowired
	private FsvrFileFmtService fileFmtService;
	
	@Autowired
	private TfsvrFileTotParaService tfsvrFileTotParaService;
	
	@Autowired
	private TfsvrFileDtlParaService tfsvrFileDtlParaService;
	
	@Autowired
	private TfsvrFileChgParaService tfsvrFileChgParaService;
	
	@Autowired
	private TfsvrFileColChgTotParaService tfsvrFileColChgTotParaService;
	
	@Autowired
	private TfsvrFileColChgDtlParaService tfsvrFileColChgDtlParaService;
	
	/**
	 * 返回文件格式管理页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "manage" })
	public String fileFmtManage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/tec/fileFmtManage";
	}
	
	/**
	 * 返回文件格式列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "fileFmtList" })
	public String fileFmtList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/tec/fileFmtList";
	}
	
	/**
	 * 返回文件格式新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "fileFmtAdd" })
	public String fileFmtAdd(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/tec/fileFmtAdd";
	}
	
	/**
	 * 返回文件格式修改页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "fileFmtUpdate" })
	public String fileFmtUpdate(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/tec/fileFmtUpdate";
	}
	
	/**
	 * 返回文件格式详细页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "fileFmtDetail" })
	public String fileFmtDetail(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/tec/fileFmtDetail";
	}
	
	/**
	 * 返回文件格式详细页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "fileFmtInputFixAdd" })
	public String fileFmtInputFixAdd(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/tec/fileFmtInputFixAddForm";
	}
	
	/**
	* 根据模板号查询模板信息数据
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="getTemPlate")
	public void getTemPlate(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String temNo = reqDs.getString("fmtNo");
		if (DataUtil.isNullStr(temNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "模板号不能为空！");
		}		

		TfsvrTemPlateDO obj = fileFmtService.getTemPlate(temNo);
		String jsonStr =JSON.toJSONString(obj);
	    renderDatasetString(response, jsonStr, SysErr.E_SUCCESS, "获取模板信息数据成功");
	}
	
	/**
	* 新增 文件格式配置信息
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="insert")
	public void insert(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		
		//获取转出对象值
		TfmngFileConvReqDTO tfmngFileConvReqDTO = getReqBody(reqDs);
		IDBSession session = DBSessionFactory.getSession();
		try{
			session.beginTransaction();
			String enumConvNo="";
			if("1".equals(tfmngFileConvReqDTO.getIsHaveEnumConv())){
				enumConvNo=tfsvrFileColChgTotParaService.getMaxChgNo();
			}
			//新增模板对应格式信息
			TfsvrFileTotParaDO tfsvrFileTotParaDO=new TfsvrFileTotParaDO();
			tfsvrFileTotParaDO.setBodyDltSym(tfmngFileConvReqDTO.getFmtDltSym());
			tfsvrFileTotParaDO.setBodyFmtFlg(tfmngFileConvReqDTO.getFileFmt());
			
			//是否有报文头
			if("1".equals(tfmngFileConvReqDTO.getIsHaveHead())){
				if("1".equals(tfmngFileConvReqDTO.getIsHaveTail())){
					tfsvrFileTotParaDO.setGrpFlg("11");
				}else{
					tfsvrFileTotParaDO.setGrpFlg("10");
				}
				tfsvrFileTotParaDO.setHeadNum(tfmngFileConvReqDTO.getHeadNum());
				tfsvrFileTotParaDO.setHeadFmtFlg(tfmngFileConvReqDTO.getFileFmt());
				tfsvrFileTotParaDO.setHeadDltSym(tfmngFileConvReqDTO.getFmtDltSym());
			}else{
				if("1".equals(tfmngFileConvReqDTO.getIsHaveTail())){
					tfsvrFileTotParaDO.setGrpFlg("01");
				}else{
					tfsvrFileTotParaDO.setGrpFlg("00");
				}
				tfsvrFileTotParaDO.setHeadNum(Long.valueOf("0"));
			}
			//是否有尾
			if("1".equals(tfmngFileConvReqDTO.getIsHaveTail())){
				tfsvrFileTotParaDO.setTailNum(tfmngFileConvReqDTO.getTailNum());
				tfsvrFileTotParaDO.setTailFmtFlg(tfmngFileConvReqDTO.getFileFmt());
				tfsvrFileTotParaDO.setTailDltSym(tfmngFileConvReqDTO.getFmtDltSym());
			}else{
				tfsvrFileTotParaDO.setTailNum(Long.valueOf("0"));
			}
			//文件编码
			tfsvrFileTotParaDO.setFileCode(tfmngFileConvReqDTO.getFileCode());
			//获取模板文件格式参数
			TfsvrFileTotParaDO tfsvrFileTotParaDOTemp=tfsvrFileTotParaService.get(tfmngFileConvReqDTO.getTempFmtNo());
			if("2".equals(tfsvrFileTotParaDOTemp.getFileTranTp())){
				tfsvrFileTotParaDO.setFileTranTp("1");//1-输入
				tfsvrFileTotParaDO.setFmtName(tfmngFileConvReqDTO.getFmtName() + "输入");
			}else{
				tfsvrFileTotParaDO.setFileTranTp("2");//2-输出
				tfsvrFileTotParaDO.setFmtName(tfmngFileConvReqDTO.getFmtName() + "输出");
			}
			//文件格式类型：1-定长，2-非定长，3-xml
			if("1".equals(tfmngFileConvReqDTO.getFileFmt())||"2".equals(tfmngFileConvReqDTO.getFileFmt())||"3".equals(tfmngFileConvReqDTO.getFileFmt())){
				tfsvrFileTotParaDO.setChgTp("1");
			}else{
				tfsvrFileTotParaDO.setChgTp("2");
				tfsvrFileTotParaDO.setTabName("");
			}
			
			tfsvrFileTotParaDO.setFmtNo(tfsvrFileTotParaService.getMaxFmtNo());
			tfsvrFileTotParaDO.setCompNo(tfsvrFileTotParaDOTemp.getCompNo());
			tfsvrFileTotParaDO.setCompName(tfsvrFileTotParaDOTemp.getCompName());
			
			tfsvrFileTotParaService.insert(tfsvrFileTotParaDO);
			
			
			//新增输入格式而输出模板固定，新增输出模板（配置输出模板取输入模板哪个值）    输入模板固定，不用新增输入模板
			if("2".equals(tfsvrFileTotParaDOTemp.getFileTranTp())){
				String strMax=tfsvrFileTotParaDO.getFmtNo();
				String maxstr=strMax.substring(strMax.length()-9, strMax.length());
				strMax=strMax.substring(0,strMax.length()-9)+FsvrUtil.getStr(maxstr);
				tfsvrFileTotParaDOTemp.setFmtNo(strMax);
				tfsvrFileTotParaDOTemp.setShortRmrk("");
				tfsvrFileTotParaDOTemp.setLongRmrk(tfsvrFileTotParaDOTemp.getLongRmrk()+"-衍生");
				//tfsvrFileTotParaDOTemp.setLongRmrk(tfsvrFileTotParaDOTemp.getLongRmrk()+"-衍生");
				//新增总格式信息
				tfsvrFileTotParaService.insert(tfsvrFileTotParaDOTemp);
				
				//新增明细格式信息
				List<TfsvrFileDtlParaDO> tempDltList=tfsvrFileDtlParaService.list(tfmngFileConvReqDTO.getTempFmtNo());
				if(tempDltList==null || tempDltList.size()==0){
					throw new BaseException(SysErr.E_MESSAGE, "模板对应的明细未配置！");
				}
				for(int k=0;k<tempDltList.size();k++){
					//根据模板内容反找配置的模板序号内容
					TfsvrFileDtlParaDO TfsvrFileDtlParaDoOut=tempDltList.get(k);
					TfsvrFileDtlParaDoOut.setFmtNo(tfsvrFileTotParaDOTemp.getFmtNo());
					
					//List<Map<String,Object>> mapList =new ArrayList<Map<String,Object>>();
					String cloNo="";
					for(int i=0;i<tfmngFileConvReqDTO.getList().size();i++){
						TfmngFileDtlReqDTO tfmngFileDtlReqDTO=tfmngFileConvReqDTO.getList().get(i);
						String serNo=tfmngFileDtlReqDTO.getTempSer();
						String[] arrs=serNo.split("\\.");
						if(tfmngFileDtlReqDTO.getTempFlg().equals(TfsvrFileDtlParaDoOut.getFileFlg())&&arrs[0].equals(String.valueOf(TfsvrFileDtlParaDoOut.getSer()))){
							if("9".equals(TfsvrFileDtlParaDoOut.getFileColTp())){
								   //不做处理	
								}else{
									cloNo+=tfmngFileDtlReqDTO.getSer()+".";
								}
						}
					}

					if(!("").equals(cloNo)){
						TfsvrFileDtlParaDoOut.setColNo(cloNo.substring(0, cloNo.length()-1));
						TfsvrFileDtlParaDoOut.setColKd("1");//1-字段 2-常量
					}else{
						TfsvrFileDtlParaDoOut.setColNo(cloNo);
						TfsvrFileDtlParaDoOut.setColKd("2");//1-字段 2-常量
					}
					if("9".equals(TfsvrFileDtlParaDoOut.getFileColTp())){
						TfsvrFileDtlParaDoOut.setColNo("");
						TfsvrFileDtlParaDoOut.setColKd("1");//1-字段 2-常量
					}
					TfsvrFileDtlParaDoOut.setShortRmrk("");
					if("".equals(TfsvrFileDtlParaDoOut.getChgFlg())){ //Y-是 N-否
						TfsvrFileDtlParaDoOut.setChgFlg("N");
					}
					if("2".equals(tfsvrFileTotParaDOTemp.getBodyFmtFlg())){
						TfsvrFileDtlParaDoOut.setDelFlg("1");
					}else{
						TfsvrFileDtlParaDoOut.setDelFlg("2");
					}
					
					tfsvrFileDtlParaService.insert(TfsvrFileDtlParaDoOut);
				}
				
			}
			
			for(int i=0;i<tfmngFileConvReqDTO.getList().size();i++){
				TfmngFileDtlReqDTO tfmngFileDtlReqDTO=tfmngFileConvReqDTO.getList().get(i);
				TfsvrFileDtlParaDO tfsvrFileDtlParaDO=new TfsvrFileDtlParaDO();
				tfsvrFileDtlParaDO.setFmtNo(tfsvrFileTotParaDO.getFmtNo());
				tfsvrFileDtlParaDO.setFileFlg(tfmngFileDtlReqDTO.getFileFlg());
				tfsvrFileDtlParaDO.setSer(tfmngFileDtlReqDTO.getSer());
				if("2".equals(tfmngFileDtlReqDTO.getFileFlg())){
					tfsvrFileDtlParaDO.setLineNo("0");
				}else if("1".equals(tfmngFileDtlReqDTO.getFileFlg())){
					if("1".equals(tfmngFileConvReqDTO.getIsHaveHead())){
						tfsvrFileDtlParaDO.setLineNo("1");
					}else{
						tfsvrFileDtlParaDO.setLineNo("0");
					}
				}else if("3".equals(tfmngFileDtlReqDTO.getFileFlg())){
					if("1".equals(tfmngFileConvReqDTO.getIsHaveTail())){
						tfsvrFileDtlParaDO.setLineNo("1");
					}else{
						tfsvrFileDtlParaDO.setLineNo("0");
					}
				}
				
				tfsvrFileDtlParaDO.setLineNum(Long.valueOf("0"));
				
				tfsvrFileDtlParaDO.setColName(tfmngFileDtlReqDTO.getColName());
				tfsvrFileDtlParaDO.setFileColTp(tfmngFileDtlReqDTO.getFileColTp());
				if(tfmngFileDtlReqDTO.getColLen()==null||"".equals(tfmngFileDtlReqDTO.getColLen())){
					tfsvrFileDtlParaDO.setColLen(Long.valueOf("0"));
				}else{
					tfsvrFileDtlParaDO.setColLen(tfmngFileDtlReqDTO.getColLen());
				}
				tfsvrFileDtlParaDO.setAlignMeth(tfmngFileDtlReqDTO.getAlignMeth());
				
				//配置输出格式
				if("2".equals(tfsvrFileTotParaDO.getFileTranTp())){
					if("".equals(tfmngFileDtlReqDTO.getTempSer())){
						tfsvrFileDtlParaDO.setColKd("2");//1-字段 2-常量
					}else{
						tfsvrFileDtlParaDO.setColKd("1");//1-字段 2-常量
					}
					tfsvrFileDtlParaDO.setColNo(tfmngFileDtlReqDTO.getTempSer());
					tfsvrFileDtlParaDO.setDefVal("");
					tfsvrFileDtlParaDO.setMidRmrk(tfmngFileDtlReqDTO.getClobFlg());
				}else{
					tfsvrFileDtlParaDO.setColKd("1");
					tfsvrFileDtlParaDO.setColNo(String.valueOf(tfmngFileDtlReqDTO.getSer()));
					tfsvrFileDtlParaDO.setMidRmrk(tfmngFileDtlReqDTO.getClobFlg());
					tfsvrFileDtlParaDO.setShortRmrk(tfmngFileDtlReqDTO.getTempSer());
				}
				
				if("".equals(tfmngFileDtlReqDTO.getChgFlg())){ //Y-是 N-否
					tfsvrFileDtlParaDO.setChgFlg("N");
				} else {
					tfsvrFileDtlParaDO.setChgFlg(tfmngFileDtlReqDTO.getChgFlg());
				}
				if("2".equals(tfmngFileConvReqDTO.getFileFmt())){
					tfsvrFileDtlParaDO.setDltSymTp("1");
				}else{
					tfsvrFileDtlParaDO.setDltSymTp("2");
				}
				tfsvrFileDtlParaService.insert(tfsvrFileDtlParaDO);
				
				//新增内部值转换信息
				if("Y".equals(tfmngFileDtlReqDTO.getChgFlg())){
					TfsvrFileColChgDtlParaDO tfsvrFileColChgDtlParaDO=new TfsvrFileColChgDtlParaDO();
					tfsvrFileColChgDtlParaDO.setChgNo(enumConvNo);
					tfsvrFileColChgDtlParaDO.setChgName(tfmngFileConvReqDTO.getFmtName());
					tfsvrFileColChgDtlParaDO.setFileFlg(tfmngFileDtlReqDTO.getFileFlg());
					tfsvrFileColChgDtlParaDO.setColSer(tfmngFileDtlReqDTO.getSer());
					
					String strList=tfmngFileDtlReqDTO.getList();
					JSONArray jsonArr = JSONArray.fromObject(strList.substring(1, strList.length()-1));
					@SuppressWarnings("unchecked")
					List<TfmngFileDtlReqListDTO> dtlList =JSONArray.toList(jsonArr,TfmngFileDtlReqListDTO.class);
					
					for(int m=0;m<dtlList.size();m++){
						tfsvrFileColChgDtlParaDO.setSer((long) m);
						tfsvrFileColChgDtlParaDO.setInKv(dtlList.get(m).getInKv());
						tfsvrFileColChgDtlParaDO.setOutKv(dtlList.get(m).getOutKv());
						tfsvrFileColChgDtlParaService.insert(tfsvrFileColChgDtlParaDO);
					}
				}
			}
			
			//新增两种格式的模板转换信息
			TfsvrFileChgParaDO tfsvrFileChgParaDO=new TfsvrFileChgParaDO();
			tfsvrFileChgParaDO.setChgNo(tfsvrFileChgParaService.getMaxChgNo());
			tfsvrFileChgParaDO.setChgName(tfmngFileConvReqDTO.getFmtName());
			if("2".equals(tfsvrFileTotParaDOTemp.getFileTranTp())){
				tfsvrFileChgParaDO.setInFmtNo(tfsvrFileTotParaDO.getFmtNo());
				tfsvrFileChgParaDO.setInFmtName(tfsvrFileTotParaDO.getFmtName());
				tfsvrFileChgParaDO.setOutFmtNo(tfsvrFileTotParaDOTemp.getFmtNo());
				tfsvrFileChgParaDO.setOutFmtName(tfsvrFileTotParaDOTemp.getFmtName());
			}else{
				tfsvrFileChgParaDO.setInFmtNo(tfsvrFileTotParaDOTemp.getFmtNo());
				tfsvrFileChgParaDO.setOutFmtNo(tfsvrFileTotParaDO.getFmtNo());
				tfsvrFileChgParaDO.setInFmtName(tfsvrFileTotParaDOTemp.getFmtName());
				tfsvrFileChgParaDO.setOutFmtName(tfsvrFileTotParaDO.getFmtName());
			}
			tfsvrFileChgParaDO.setCompNo(tfsvrFileTotParaDOTemp.getCompNo());
			tfsvrFileChgParaDO.setCompName(tfsvrFileTotParaDOTemp.getCompName());
			if("1".equals(tfmngFileConvReqDTO.getIsHaveEnumConv())){
				tfsvrFileChgParaDO.setMidRmrk(enumConvNo);//暂存枚举转换编号
				if("1".equals(tfsvrFileTotParaDO.getFileTranTp())){
					tfsvrFileChgParaDO.setShortRmrk("1");//枚举模板输入转换 
				}else{
					tfsvrFileChgParaDO.setShortRmrk("2");//枚举模板输出转换
				}
			}else{
				tfsvrFileChgParaDO.setShortRmrk("0");
			}
			tfsvrFileChgParaDO.setModDate(DateUtil.getDate());
			tfsvrFileChgParaDO.setModTime(DateUtil.getTime());
			tfsvrFileChgParaService.insert(tfsvrFileChgParaDO);
			
			//新增内部值总体转换信息
			if("1".equals(tfmngFileConvReqDTO.getIsHaveEnumConv())){
				TfsvrFileColChgTotParaDO tfsvrFileColChgTotParaDO=new TfsvrFileColChgTotParaDO();
				tfsvrFileColChgTotParaDO.setChgNo(enumConvNo);
				tfsvrFileColChgTotParaDO.setChgName(tfmngFileConvReqDTO.getFmtName());
				tfsvrFileColChgTotParaDO.setCompNo(tfsvrFileTotParaDOTemp.getCompNo());
				tfsvrFileColChgTotParaDO.setCompName(tfsvrFileTotParaDOTemp.getCompName());
				
				tfsvrFileColChgTotParaService.insert(tfsvrFileColChgTotParaDO);
			}
			session.endTransaction();
		}catch(Exception e){
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "文件格式配置失败:"+e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "文件格式配置失败:"+e.getMessage());
		}
		
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "文件格式配置成功！");
		
	}
	

	/**
	* 修改 文件格式配置信息
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		
		//获取转出对象值
		TfmngFileConvReqDTO tfmngFileConvReqDTO = getReqBody(reqDs);
		IDBSession session = DBSessionFactory.getSession();
		try{
			session.beginTransaction();
			TfsvrFileChgParaDO chgParaDO = tfsvrFileChgParaService.get(tfmngFileConvReqDTO.getChgNo());
			if (DataUtil.isNullStr(tfmngFileConvReqDTO.getTempFmtNo())){
				throw new BaseException(SysErr.E_DEFAULT, "模板格式号为空！");
			}
			String fileFmtNo = tfmngFileConvReqDTO.getTempFmtNo().equals(chgParaDO.getInFmtNo()) ? chgParaDO.getOutFmtNo() : chgParaDO.getInFmtNo();
			String enumConvNo="";
			if("1".equals(tfmngFileConvReqDTO.getIsHaveEnumConv())){
				if (DataUtil.isNullStr(chgParaDO.getMidRmrk())) {
					enumConvNo=tfsvrFileColChgTotParaService.getMaxChgNo();
				} else {
					enumConvNo=chgParaDO.getMidRmrk();
				}
			}
			//新增模板对应格式信息
			TfsvrFileTotParaDO tfsvrFileTotParaDO=new TfsvrFileTotParaDO();
			tfsvrFileTotParaDO.setBodyDltSym(tfmngFileConvReqDTO.getFmtDltSym());
			tfsvrFileTotParaDO.setBodyFmtFlg(tfmngFileConvReqDTO.getFileFmt());
			//是否有报文头
			if("1".equals(tfmngFileConvReqDTO.getIsHaveHead())){
				if("1".equals(tfmngFileConvReqDTO.getIsHaveTail())){
					tfsvrFileTotParaDO.setGrpFlg("11");
				}else{
					tfsvrFileTotParaDO.setGrpFlg("10");
				}
				tfsvrFileTotParaDO.setHeadNum(tfmngFileConvReqDTO.getHeadNum());
				tfsvrFileTotParaDO.setHeadFmtFlg(tfmngFileConvReqDTO.getFileFmt());
				tfsvrFileTotParaDO.setHeadDltSym(tfmngFileConvReqDTO.getFmtDltSym());
			}else{
				if("1".equals(tfmngFileConvReqDTO.getIsHaveTail())){
					tfsvrFileTotParaDO.setGrpFlg("01");
				}else{
					tfsvrFileTotParaDO.setGrpFlg("00");
				}
				tfsvrFileTotParaDO.setHeadNum(Long.valueOf("0"));
			}
			//是否有尾
			if("1".equals(tfmngFileConvReqDTO.getIsHaveTail())){
				tfsvrFileTotParaDO.setTailNum(tfmngFileConvReqDTO.getTailNum());
				tfsvrFileTotParaDO.setTailFmtFlg(tfmngFileConvReqDTO.getFileFmt());
				tfsvrFileTotParaDO.setTailDltSym(tfmngFileConvReqDTO.getFmtDltSym());
			}else{
				tfsvrFileTotParaDO.setTailNum(Long.valueOf("0"));
			}
			//文件编码
			tfsvrFileTotParaDO.setFileCode(tfmngFileConvReqDTO.getFileCode());
			//获取模板文件格式参数
			TfsvrFileTotParaDO tfsvrFileTotParaDOTemp=tfsvrFileTotParaService.get(tfmngFileConvReqDTO.getTempFmtNo());
			if("2".equals(tfsvrFileTotParaDOTemp.getFileTranTp())){
				tfsvrFileTotParaDO.setFileTranTp("1");//1-输入
				tfsvrFileTotParaDO.setFmtName(tfmngFileConvReqDTO.getFmtName() + "输入");
			}else{
				tfsvrFileTotParaDO.setFileTranTp("2");//2-输出
				tfsvrFileTotParaDO.setFmtName(tfmngFileConvReqDTO.getFmtName() + "输出");
			}
			//文件格式类型：1-定长，2-非定长，3-xml
			if("1".equals(tfmngFileConvReqDTO.getFileFmt())||"2".equals(tfmngFileConvReqDTO.getFileFmt())||"3".equals(tfmngFileConvReqDTO.getFileFmt())){
				tfsvrFileTotParaDO.setChgTp("1");
			}else{
				tfsvrFileTotParaDO.setChgTp("2");
				tfsvrFileTotParaDO.setTabName("");
			}
			
			tfsvrFileTotParaDO.setFmtNo(fileFmtNo);
			tfsvrFileTotParaDO.setCompNo(tfsvrFileTotParaDOTemp.getCompNo());
			tfsvrFileTotParaDO.setCompName(tfsvrFileTotParaDOTemp.getCompName());
			
			tfsvrFileTotParaService.update(tfsvrFileTotParaDO);
			
			
			//新增输入格式而输出模板固定，新增输出模板（配置输出模板取输入模板哪个值）    输入模板固定，不用新增输入模板
			if("2".equals(tfsvrFileTotParaDOTemp.getFileTranTp())){
				
				//重新增加明细格式信息
				List<TfsvrFileDtlParaDO> tempDltList=tfsvrFileDtlParaService.list(tfmngFileConvReqDTO.getTempFmtNo());
				if(tempDltList==null){
					throw new BaseException(SysErr.E_MESSAGE, "模板对应的明细未配置！");
				}
				tfsvrFileDtlParaService.delete(tfmngFileConvReqDTO.getTempFmtNo());
				for(int k=0;k<tempDltList.size();k++){
					//根据模板内容反找配置的模板序号内容
					TfsvrFileDtlParaDO TfsvrFileDtlParaDoOut=tempDltList.get(k);
					TfsvrFileDtlParaDoOut.setFmtNo(tfsvrFileTotParaDOTemp.getFmtNo());
					
					//List<Map<String,Object>> mapList =new ArrayList<Map<String,Object>>();
					String cloNo="";
					for(int i=0;i<tfmngFileConvReqDTO.getList().size();i++){
						TfmngFileDtlReqDTO tfmngFileDtlReqDTO=tfmngFileConvReqDTO.getList().get(i);
						String serNo=tfmngFileDtlReqDTO.getTempSer();
						String[] arrs=serNo.split("\\.");
						if(tfmngFileDtlReqDTO.getTempFlg().equals(TfsvrFileDtlParaDoOut.getFileFlg())&&arrs[0].equals(String.valueOf(TfsvrFileDtlParaDoOut.getSer()))){
							if("9".equals(TfsvrFileDtlParaDoOut.getFileColTp())){
								   //不做处理	
								}else{
									cloNo+=tfmngFileDtlReqDTO.getSer()+".";
								}
						}
					}

					if(!("").equals(cloNo)){
						TfsvrFileDtlParaDoOut.setColNo(cloNo.substring(0, cloNo.length()-1));
						TfsvrFileDtlParaDoOut.setColKd("1");//1-字段 2-常量
					}else{
						TfsvrFileDtlParaDoOut.setColNo(cloNo);
						TfsvrFileDtlParaDoOut.setColKd("2");//1-字段 2-常量
					}
					if("9".equals(TfsvrFileDtlParaDoOut.getFileColTp())){
						TfsvrFileDtlParaDoOut.setColNo("");
						TfsvrFileDtlParaDoOut.setColKd("1");//1-字段 2-常量
					}
					TfsvrFileDtlParaDoOut.setShortRmrk("");
					if("".equals(TfsvrFileDtlParaDoOut.getChgFlg())){
						TfsvrFileDtlParaDoOut.setChgFlg("N");
					}
					if("2".equals(tfsvrFileTotParaDOTemp.getBodyFmtFlg())){
						TfsvrFileDtlParaDoOut.setDelFlg("1");
					}else{
						TfsvrFileDtlParaDoOut.setDelFlg("2");
					}
					
					tfsvrFileDtlParaService.insert(TfsvrFileDtlParaDoOut);
				}
				
			}
			tfsvrFileDtlParaService.delete(fileFmtNo);
			if(!DataUtil.isNullStr(enumConvNo)) {
				tfsvrFileColChgDtlParaService.delete(enumConvNo);
				tfsvrFileColChgTotParaService.delete(enumConvNo);
			}
			for(int i=0;i<tfmngFileConvReqDTO.getList().size();i++){
				TfmngFileDtlReqDTO tfmngFileDtlReqDTO=tfmngFileConvReqDTO.getList().get(i);
				TfsvrFileDtlParaDO tfsvrFileDtlParaDO=new TfsvrFileDtlParaDO();
				tfsvrFileDtlParaDO.setFmtNo(tfsvrFileTotParaDO.getFmtNo());
				tfsvrFileDtlParaDO.setFileFlg(tfmngFileDtlReqDTO.getFileFlg());
				tfsvrFileDtlParaDO.setSer(tfmngFileDtlReqDTO.getSer());
				if("2".equals(tfmngFileDtlReqDTO.getFileFlg())){
					tfsvrFileDtlParaDO.setLineNo("0");
				}else if("1".equals(tfmngFileDtlReqDTO.getFileFlg())){
					if("1".equals(tfmngFileConvReqDTO.getIsHaveHead())){
						tfsvrFileDtlParaDO.setLineNo(String.valueOf(tfmngFileConvReqDTO.getHeadNum()));
					}else{
						tfsvrFileDtlParaDO.setLineNo("0");
					}
				}else if("3".equals(tfmngFileDtlReqDTO.getFileFlg())){
					if("1".equals(tfmngFileConvReqDTO.getIsHaveHead())){
						tfsvrFileDtlParaDO.setLineNo(String.valueOf(tfmngFileConvReqDTO.getHeadNum()));
					}else{
						tfsvrFileDtlParaDO.setLineNo("0");
					}
				}
				
				tfsvrFileDtlParaDO.setLineNum(Long.valueOf("0"));
				
				tfsvrFileDtlParaDO.setColName(tfmngFileDtlReqDTO.getColName());
				tfsvrFileDtlParaDO.setFileColTp(tfmngFileDtlReqDTO.getFileColTp());
				if(tfmngFileDtlReqDTO.getColLen()==null||"".equals(tfmngFileDtlReqDTO.getColLen())){
					tfsvrFileDtlParaDO.setColLen(Long.valueOf("0"));
				}else{
					tfsvrFileDtlParaDO.setColLen(tfmngFileDtlReqDTO.getColLen());
				}
				tfsvrFileDtlParaDO.setAlignMeth(tfmngFileDtlReqDTO.getAlignMeth());
				
				//配置输出格式
				if("2".equals(tfsvrFileTotParaDO.getFileTranTp())){
					if("".equals(tfmngFileDtlReqDTO.getTempSer())){
						tfsvrFileDtlParaDO.setColKd("2");//1-字段 2-常量
					}else{
						tfsvrFileDtlParaDO.setColKd("1");//1-字段 2-常量
					}
					tfsvrFileDtlParaDO.setColNo(tfmngFileDtlReqDTO.getTempSer());
					tfsvrFileDtlParaDO.setDefVal("");
					tfsvrFileDtlParaDO.setMidRmrk(tfmngFileDtlReqDTO.getClobFlg());
				}else{
					tfsvrFileDtlParaDO.setColKd("1");
					tfsvrFileDtlParaDO.setColNo(String.valueOf(tfmngFileDtlReqDTO.getSer()));
					tfsvrFileDtlParaDO.setMidRmrk(tfmngFileDtlReqDTO.getClobFlg());
					tfsvrFileDtlParaDO.setShortRmrk(tfmngFileDtlReqDTO.getTempSer());
				}
				
				if ("".equals(tfmngFileDtlReqDTO.getChgFlg())) {
					tfsvrFileDtlParaDO.setChgFlg("N");
				} else {
					tfsvrFileDtlParaDO.setChgFlg(tfmngFileDtlReqDTO.getChgFlg());
				}
				
				if("2".equals(tfmngFileConvReqDTO.getFileFmt())){
					tfsvrFileDtlParaDO.setDltSymTp("1");
				}else{
					tfsvrFileDtlParaDO.setDltSymTp("2");
				}
				tfsvrFileDtlParaService.insert(tfsvrFileDtlParaDO);
				
				//新增内部值转换信息
				if("Y".equals(tfmngFileDtlReqDTO.getChgFlg())){
					TfsvrFileColChgDtlParaDO tfsvrFileColChgDtlParaDO=new TfsvrFileColChgDtlParaDO();
					tfsvrFileColChgDtlParaDO.setChgNo(enumConvNo);
					tfsvrFileColChgDtlParaDO.setChgName(tfmngFileConvReqDTO.getFmtName());
					tfsvrFileColChgDtlParaDO.setFileFlg(tfmngFileDtlReqDTO.getFileFlg());
					tfsvrFileColChgDtlParaDO.setColSer(tfmngFileDtlReqDTO.getSer());
					
					String strList=tfmngFileDtlReqDTO.getList();
					JSONArray jsonArr = JSONArray.fromObject(strList.substring(1, strList.length()-1));
					@SuppressWarnings("unchecked")
					List<TfmngFileDtlReqListDTO> dtlList =JSONArray.toList(jsonArr,TfmngFileDtlReqListDTO.class);
					
					for(int m=0;m<dtlList.size();m++){
						tfsvrFileColChgDtlParaDO.setSer((long) m);
						tfsvrFileColChgDtlParaDO.setInKv(dtlList.get(m).getInKv());
						tfsvrFileColChgDtlParaDO.setOutKv(dtlList.get(m).getOutKv());
						tfsvrFileColChgDtlParaService.insert(tfsvrFileColChgDtlParaDO);
					}
				}
			}
			
			//新增两种格式的模板转换信息
			if("1".equals(tfmngFileConvReqDTO.getIsHaveEnumConv())){
				chgParaDO.setMidRmrk(enumConvNo);//暂存枚举转换编号
				if("1".equals(tfsvrFileTotParaDO.getFileTranTp())){
					chgParaDO.setShortRmrk("1");//枚举模板输入转换 
				}else{
					chgParaDO.setShortRmrk("2");//枚举模板输出转换
				}
			}else{
				chgParaDO.setShortRmrk("0");
			}
			chgParaDO.setModDate(DateUtil.getDate());
			chgParaDO.setModTime(DateUtil.getTime());
			tfsvrFileChgParaService.update(chgParaDO);
			
			//新增内部值总体转换信息
			if("1".equals(tfmngFileConvReqDTO.getIsHaveEnumConv())){
				TfsvrFileColChgTotParaDO tfsvrFileColChgTotParaDO=new TfsvrFileColChgTotParaDO();
				tfsvrFileColChgTotParaDO.setChgNo(enumConvNo);
				tfsvrFileColChgTotParaDO.setChgName(tfmngFileConvReqDTO.getFmtName());
				tfsvrFileColChgTotParaDO.setCompNo(tfsvrFileTotParaDOTemp.getCompNo());
				tfsvrFileColChgTotParaDO.setCompName(tfsvrFileTotParaDOTemp.getCompName());
				
				tfsvrFileColChgTotParaService.insert(tfsvrFileColChgTotParaDO);
			}
			session.endTransaction();
		}catch(Exception e){
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "文件格式修改失败:"+e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "文件格式转换修改失败:"+e.getMessage());
			
		}
		
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "文件格式修改成功！");
		
	}
	/**
	 * 文件格式配置新增对象
	 */
	public TfmngFileConvReqDTO getReqBody(IDataset reqDs){
		String chgNo = reqDs.getString("chgNo");
		String tempFmtNo = reqDs.getString("tempFmtNo");
		String fmtName = reqDs.getString("fmtName");
		String fileCode = reqDs.getString("fileCode");
		String fileFmt = reqDs.getString("fileFmt");
		String isHaveHead = reqDs.getString("isHaveHead");
		String isHaveTail = reqDs.getString("isHaveTail");
		String fmtDltSym = reqDs.getString("fmtDltSym");
		Long headNum = reqDs.getLong("headNum");
		Long tailNum = reqDs.getLong("tailNum");
		String isHaveEnumConv = reqDs.getString("isHaveEnumConv");
		String list = reqDs.getString("list");
		
				
		TfmngFileConvReqDTO tfmngFileConvReqDTO = new TfmngFileConvReqDTO();
		
		
		JSONArray jsonArr = JSONArray.fromObject(list);
		@SuppressWarnings("unchecked")
		List<TfmngFileDtlReqDTO> dtlList =JSONArray.toList(jsonArr,TfmngFileDtlReqDTO.class);
		
		tfmngFileConvReqDTO.setChgNo(chgNo);
		tfmngFileConvReqDTO.setList(dtlList);
		tfmngFileConvReqDTO.setTempFmtNo(tempFmtNo);
		tfmngFileConvReqDTO.setFmtName(fmtName);
		tfmngFileConvReqDTO.setFileCode(fileCode);
		tfmngFileConvReqDTO.setFileFmt(fileFmt);
		tfmngFileConvReqDTO.setIsHaveHead(isHaveHead);
		tfmngFileConvReqDTO.setIsHaveTail(isHaveTail);
		tfmngFileConvReqDTO.setHeadNum(headNum);
		tfmngFileConvReqDTO.setTailNum(tailNum);
		tfmngFileConvReqDTO.setIsHaveEnumConv(isHaveEnumConv);
		tfmngFileConvReqDTO.setFmtDltSym(fmtDltSym);;
		
		return tfmngFileConvReqDTO;
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
		TfsvrFileFmtListDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrFileFmtListDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TfsvrFileChgParaDO> list = tfsvrFileChgParaService.fileFmtList(obj, start, limit);
		int total =  tfsvrFileChgParaService.getFileFmtTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TfsvrFileChgParaDO.class);
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
		String chgNo = reqDs.getString("chgNo");
		if (DataUtil.isNullStr(chgNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[chgNo]不能空！");
		}		
		TfsvrFileChgTotDtlDO obj = new TfsvrFileChgTotDtlDO();
		TfsvrFileChgParaDO chgParaDO = tfsvrFileChgParaService.get(chgNo);
		//获取模板数据
		TfsvrFileTotParaDO tempTotDO = tfsvrFileTotParaService.get(chgParaDO.getInFmtNo());
		String filtFmtNo = chgParaDO.getOutFmtNo();
		if (DataUtil.isNullStr(tempTotDO.getLongRmrk())){
			obj.setTranTp(tempTotDO.getFileTranTp());
			tempTotDO = tfsvrFileTotParaService.get(chgParaDO.getOutFmtNo());
			filtFmtNo = chgParaDO.getInFmtNo();
		} else {
			obj.setTranTp("2");
		}
		
		obj.setTempFmtNo(tempTotDO.getFmtNo());
		TfsvrTemPlateDO temPlateDO = fileFmtService.getTemPlate(tempTotDO.getFmtNo());
		
		//获取配置的文件格式
		TfsvrFileTotParaDO fileTotDO = tfsvrFileTotParaService.get(filtFmtNo);
		TfsvrFilePlateDO filePlateDO = fileFmtService.getFilePlate(filtFmtNo, chgParaDO, obj);
		obj.setChgNo(chgNo);
		obj.setCompNo(chgParaDO.getCompNo());
		obj.setFmtDltSym(fileTotDO.getBodyDltSym());
		obj.setFileCode(fileTotDO.getFileCode());
		obj.setFileFmt(fileTotDO.getBodyFmtFlg());
		obj.setTempData(temPlateDO);
		obj.setFileData(filePlateDO);
		obj.setTranTp(tempTotDO.getFileTranTp());
		obj.setFmtName(chgParaDO.getChgName());
		String jsonStr =JSON.toJSONString(obj);
	    renderDatasetString(response, jsonStr, SysErr.E_SUCCESS, "明细查询交易成功！");
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
		String chgNo = reqDs.getString("chgNo");
		if (DataUtil.isNullStr(chgNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "chgNo");
		}	
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			TfsvrFileChgParaDO chgParaDO = tfsvrFileChgParaService.get(chgNo);
			//获取模板数据
			String fileFmtNo = chgParaDO.getOutFmtNo();
			String tempFmtNo = chgParaDO.getInFmtNo();
			TfsvrFileTotParaDO tempTotDO = tfsvrFileTotParaService.get(tempFmtNo);
			if (DataUtil.isNullStr(tempTotDO.getLongRmrk())){
				fileFmtNo = chgParaDO.getInFmtNo();
				tempFmtNo = chgParaDO.getOutFmtNo();
				tempTotDO = tfsvrFileTotParaService.get(tempFmtNo);
			}
			if (DataUtil.isNullStr(tempTotDO.getLongRmrk())){
				throw new BaseException(SysErr.E_DEFAULT, "该转换号的模板名为空，无法确认模板格式号！");
			} 
			//删除文件转换参数配置表数据
			tfsvrFileChgParaService.delete(chgNo);
			tfsvrFileTotParaService.delete(fileFmtNo);
			tfsvrFileDtlParaService.delete(fileFmtNo); 
			if ( !DataUtil.isNullStr(chgParaDO.getMidRmrk())){
				tfsvrFileColChgTotParaService.delete(chgParaDO.getMidRmrk());
				tfsvrFileColChgDtlParaService.delete(chgParaDO.getMidRmrk());
			}
			if ("2".equals(tempTotDO.getFileTranTp())){
				tfsvrFileTotParaService.delete(tempFmtNo);
				tfsvrFileDtlParaService.delete(tempFmtNo); 
			}
			session.endTransaction();
		}catch(Exception e){
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "删除交易失败:"+e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "文件格式转换删除失败:"+e.getMessage());
			
		}
		
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
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
		//新增模板名称字段
		ds.addColumn("tempName", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			String tempName = tfsvrFileTotParaService.get(ds.getString("inFmtNo")).getLongRmrk();
			if (DataUtil.isNullStr(tempName)){
				 tempName = tfsvrFileTotParaService.get(ds.getString("outFmtNo")).getLongRmrk();
			}
			ds.updateString("tempName", tempName);
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" +"'"+ds.getString("chgNo")+"'" + ")\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("chgNo")+"'" + ")\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("chgNo")+"'" + ")\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
	/**
	 * 根据List<Map<String,Object>> 中的map里面的key:SER进行排序
	 * @param list
	 * 
	 */
	private static List<Map<String,Object>> sort(List<Map<String,Object>> list) {
		Collections.sort(list, new Comparator<Map<String, Object>>(){
			   public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				   if (o1 == null && o2 == null) {
					   return 0;
				   } else if (o1 == null && o2 != null) {
					   return -1;
				   } else if (o1 != null && o2 == null) {
					   return 1;
				   }
			    String ser1 =o1.get("SER")+"";//ser1是从list里面拿出来的一个
			    String ser2= o2.get("SER")+""; //ser2是从list里面拿出来的第二个    
			    if ( Integer.parseInt(ser1) == Integer.parseInt(ser2) ) {
			    	return 0;
			    } else if ( Integer.parseInt(ser1) < Integer.parseInt(ser2) ) {
			    	return -1;
			    } else {
			    	return 1;
			    }
		   }   
		});	
		return list;
	}
	@ResponseBody
	@RequestMapping(value = "selectDataHeadTail")
	public void getSelectJsonHeadTail(@RequestParam(required=false) String type,@RequestParam(required=false) String isblank,@RequestParam(required=false) String blankText,@RequestParam(required=false) String blankValue, HttpServletResponse response) {
	   /*前台接收value、label、title、selected、disabled*/
	   logger.info("getSelectJsonHeadTail");
	   List<Dict> list = new ArrayList<Dict>();
	   if(!DataUtil.isNullStr(isblank) && "true".equals(isblank)){
	      //单选下拉框需要添加空选项
	      Dict d = new Dict();
	      d.setDictTp(type);
	      d.setLabel("");
	      d.setValue("");
	      list.add(d);
	   }
	   List<Dict> tmpList = DictUtils.getDictList(type);
	   for (int i=0; i<tmpList.size(); i++) {
		   if ( "2".equals(tmpList.get(i).getValue()) ) {
		   } else {
			   list.add(tmpList.get(i));
		   }
	   }
	   Map<String, Object> m = new HashMap<String, Object>();
	   m.put("retCode", "0000");
	   m.put("list", list);
	   renderString(response, m);
	}
	@ResponseBody
	@RequestMapping(value = "selectDataBody")
	public void getSelectJsonBody(@RequestParam(required=false) String type,@RequestParam(required=false) String isblank,@RequestParam(required=false) String blankText,@RequestParam(required=false) String blankValue, HttpServletResponse response) {
	   /*前台接收value、label、title、selected、disabled*/
	   logger.info("getSelectJsonBody");
	   List<Dict> list = new ArrayList<Dict>();
	   if(!DataUtil.isNullStr(isblank) && "true".equals(isblank)){
		      //单选下拉框需要添加空选项
		      Dict d = new Dict();
		      d.setDictTp(type);
		      d.setLabel("");
		      d.setValue("");
		      list.add(d);
		   }
	   List<Dict> tmpList = DictUtils.getDictList(type);
	   for (int i=0; i<tmpList.size(); i++) {
		   if ( "1".equals(tmpList.get(i).getValue()) ) {
			   
		   } else if ( "3".equals(tmpList.get(i).getValue()) ) {
			   
		   } else {
			   list.add(tmpList.get(i));
		   }
	   }
	   Map<String, Object> m = new HashMap<String, Object>();
	   m.put("retCode", "0000");
	   m.put("list", list);
	   renderString(response, m);
	}
}
