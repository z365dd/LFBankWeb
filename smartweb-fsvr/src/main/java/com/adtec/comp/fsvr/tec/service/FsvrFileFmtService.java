package com.adtec.comp.fsvr.tec.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.comp.fsvr.dto.TfmngFileDtlReqListDTO;
import com.adtec.comp.fsvr.tec.dao.TfsvrFileColChgDtlParaDao;
import com.adtec.comp.fsvr.tec.dao.TfsvrFileDtlParaDao;
import com.adtec.comp.fsvr.tec.dao.TfsvrFileTotParaDao;
import com.adtec.comp.fsvr.tec.entity.TfsvrFileChgParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrFileChgTotDtlDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrFileColChgDtlParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrFileDtlParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrFilePlateDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrFilePlateDelDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrFileTotParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrTemPlateDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrTemPlateDelDO;
import com.adtec.framework.common.util.DataUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

@Service
@Transactional(readOnly = true)
public class FsvrFileFmtService {

	private final static Logger log = LoggerFactory.getLogger(FsvrFileFmtService.class);

	@Autowired
	private TfsvrFileDtlParaDao tfsvrFileDtlParaDao;

	@Autowired
	private TfsvrFileTotParaDao tfsvrFileTotParaDao;
	@Autowired
	private TfsvrFileColChgDtlParaDao tfsvrFileColChgDtlParaDao;

	/**
	 * 获取模板数据
	 * 
	 * @param netRegion
	 * @return
	 */
	public TfsvrTemPlateDO getTemPlate(String temNo) {
		TfsvrTemPlateDO tfsvrTemPlateDO = new TfsvrTemPlateDO();

		TfsvrFileDtlParaDO tfsvrFileDtlParaDO = new TfsvrFileDtlParaDO();
		tfsvrFileDtlParaDO.setFmtNo(temNo);
		tfsvrFileDtlParaDO.setFileFlg("1");
		List<TfsvrTemPlateDelDO> headList = new ArrayList<TfsvrTemPlateDelDO>();
		List<TfsvrTemPlateDelDO> bodyList = new ArrayList<TfsvrTemPlateDelDO>();

		// 获取头
		List<TfsvrFileDtlParaDO> headDtlList = tfsvrFileDtlParaDao.getTempHeadList(tfsvrFileDtlParaDO, 0, 0);
		if (headDtlList != null) {
			for (int i = 0; i < headDtlList.size(); i++) {
				TfsvrTemPlateDelDO tfsvrFileDtlParaDO1 = new TfsvrTemPlateDelDO();
				tfsvrFileDtlParaDO1.setSer(headDtlList.get(i).getSer());
				tfsvrFileDtlParaDO1.setFileFlg(headDtlList.get(i).getFileFlg());
				tfsvrFileDtlParaDO1.setColName(headDtlList.get(i).getColName());
				tfsvrFileDtlParaDO1.setFileColTp(headDtlList.get(i).getFileColTp());
				headList.add(tfsvrFileDtlParaDO1);
			}
		}

		// 获取尾
		tfsvrFileDtlParaDO.setFileFlg("3");
		List<TfsvrFileDtlParaDO> tailDtlList = tfsvrFileDtlParaDao.getTempHeadList(tfsvrFileDtlParaDO, 0, 0);
		if (tailDtlList != null) {
			for (int i = 0; i < tailDtlList.size(); i++) {
				TfsvrTemPlateDelDO tfsvrFileDtlParaDO1 = new TfsvrTemPlateDelDO();
				tfsvrFileDtlParaDO1.setSer(tailDtlList.get(i).getSer());
				tfsvrFileDtlParaDO1.setFileFlg(tailDtlList.get(i).getFileFlg());
				tfsvrFileDtlParaDO1.setColName(tailDtlList.get(i).getColName());
				tfsvrFileDtlParaDO1.setFileColTp(tailDtlList.get(i).getFileColTp());
				headList.add(tfsvrFileDtlParaDO1);
			}
		}

		// 获取体
		tfsvrFileDtlParaDO.setFileFlg("2");
		List<TfsvrFileDtlParaDO> bodyDtlList = tfsvrFileDtlParaDao.getTempHeadList(tfsvrFileDtlParaDO, 0, 0);
		if (bodyDtlList != null) {
			for (int i = 0; i < bodyDtlList.size(); i++) {
				TfsvrTemPlateDelDO tfsvrFileDtlParaDO1 = new TfsvrTemPlateDelDO();
				tfsvrFileDtlParaDO1.setSer(bodyDtlList.get(i).getSer());
				tfsvrFileDtlParaDO1.setFileFlg(bodyDtlList.get(i).getFileFlg());
				tfsvrFileDtlParaDO1.setColName(bodyDtlList.get(i).getColName());
				tfsvrFileDtlParaDO1.setFileColTp(bodyDtlList.get(i).getFileColTp());
				bodyList.add(tfsvrFileDtlParaDO1);
			}
		}

		tfsvrTemPlateDO.setHeadList(headList);
		tfsvrTemPlateDO.setBodyList(bodyList);

		// 拼接字符串
		String str = "";
		TfsvrFileTotParaDO tfsvrFileTotParaDO = tfsvrFileTotParaDao.get(temNo);
		String grpFlg = tfsvrFileTotParaDO.getGrpFlg();

		// 头
		if ("1".equals(grpFlg.substring(0, 1))) {
			String headFmtFlg = tfsvrFileTotParaDO.getHeadFmtFlg();
			String headDltSym = tfsvrFileTotParaDO.getHeadDltSym();
			for (int i = 0; i < headDtlList.size(); i++) {
				TfsvrFileDtlParaDO tfsvrFileDtlParaDO1 = headDtlList.get(i);
				// 定长
				if ("1".equals(headFmtFlg)) {

				} else if ("2".equals(headFmtFlg)) {
					// 非定长
					if (i == headDtlList.size() - 1) {
						str += tfsvrFileDtlParaDO1.getColName();
					} else {
						str += tfsvrFileDtlParaDO1.getColName() + headDltSym;
					}
				} else if ("3".equals(headFmtFlg)) {
					// xml

				} else {

				}
			}
		}

		// 体
		String bodyFmtFlg = tfsvrFileTotParaDO.getBodyFmtFlg();
		String bodyDltSym = tfsvrFileTotParaDO.getBodyDltSym();
		str += "\n";
		for (int i = 0; i < bodyDtlList.size(); i++) {
			TfsvrFileDtlParaDO tfsvrFileDtlParaDO1 = bodyDtlList.get(i);
			// 定长
			if ("1".equals(bodyFmtFlg)) {

			} else if ("2".equals(bodyFmtFlg)) {
				// 非定长
				if (i == bodyDtlList.size() - 1) {
					str += tfsvrFileDtlParaDO1.getColName();
				} else {
					str += tfsvrFileDtlParaDO1.getColName() + bodyDltSym;
				}
			} else if ("3".equals(bodyFmtFlg)) {
				// xml

			} else {

			}
		}

		// 尾
		if ("1".equals(grpFlg.substring(1, 2))) {
			str += "\n";
			String tailFmtFlg = tfsvrFileTotParaDO.getTailFmtFlg();
			String tailDltSym = tfsvrFileTotParaDO.getTailDltSym();
			for (int i = 0; i < headDtlList.size(); i++) {
				TfsvrFileDtlParaDO tfsvrFileDtlParaDO1 = headDtlList.get(i);
				// 定长
				if ("1".equals(tailFmtFlg)) {

				} else if ("2".equals(tailFmtFlg)) {
					// 非定长
					if (i == headDtlList.size() - 1) {
						str += tfsvrFileDtlParaDO1.getColName();
					} else {
						str += tfsvrFileDtlParaDO1.getColName() + tailDltSym;
					}
				} else if ("3".equals(tailFmtFlg)) {
					// xml

				} else {

				}
			}
		}

		tfsvrTemPlateDO.setTemStr(str);

		return tfsvrTemPlateDO;
	}

	/**
	 * 获取文件转换数据
	 * 
	 * @param netRegion
	 * @return
	 */
	public TfsvrFilePlateDO getFilePlate(String fileFmtNo, TfsvrFileChgParaDO chgParaDO, TfsvrFileChgTotDtlDO obj) {
		TfsvrFilePlateDO tfsvrFilePlateDO = new TfsvrFilePlateDO();

		TfsvrFileDtlParaDO tfsvrFileDtlParaDO = new TfsvrFileDtlParaDO();
		tfsvrFileDtlParaDO.setFmtNo(fileFmtNo);
		tfsvrFileDtlParaDO.setFileFlg("1");
		List<TfsvrFilePlateDelDO> headList = new ArrayList<TfsvrFilePlateDelDO>();
		List<TfsvrFilePlateDelDO> bodyList = new ArrayList<TfsvrFilePlateDelDO>();
		int enumNum = 0;
		// 获取头
		List<TfsvrFileDtlParaDO> headDtlList = tfsvrFileDtlParaDao.getTempHeadList(tfsvrFileDtlParaDO, 0, 0);
		if (headDtlList != null) {
			for (int i = 0; i < headDtlList.size(); i++) {
				TfsvrFilePlateDelDO tfsvrFileDtlParaDO1 = new TfsvrFilePlateDelDO();
				tfsvrFileDtlParaDO1.setSer(headDtlList.get(i).getSer());
				tfsvrFileDtlParaDO1.setFileFlg(headDtlList.get(i).getFileFlg());
				tfsvrFileDtlParaDO1.setColName(headDtlList.get(i).getColName());
				tfsvrFileDtlParaDO1.setFileColTp(headDtlList.get(i).getFileColTp());
				tfsvrFileDtlParaDO1.setColLen(headDtlList.get(i).getColLen());
				tfsvrFileDtlParaDO1.setAlignMeth(headDtlList.get(i).getAlignMeth());
				tfsvrFileDtlParaDO1.setDltSymTp(headDtlList.get(i).getDltSymTp());
				tfsvrFileDtlParaDO1.setChgFlg(headDtlList.get(i).getChgFlg());
				if ( "1".equals(obj.getTranTp())){
					tfsvrFileDtlParaDO1.setTempSer(headDtlList.get(i).getShortRmrk());
				} else {
					tfsvrFileDtlParaDO1.setTempSer(headDtlList.get(i).getColNo());
				}
				if ("1".equals(headDtlList.get(i).getColKd())){////1-字段 2-常量
					tfsvrFileDtlParaDO1.setTempFlg(headDtlList.get(i).getFileFlg());
					TfsvrFileDtlParaDO tempDtl = tfsvrFileDtlParaDao.get(obj.getTempFmtNo(), "1", Long.parseLong(tfsvrFileDtlParaDO1.getTempSer()));
					if (tempDtl != null ) {
						tfsvrFileDtlParaDO1.setTempColName(tempDtl.getColName());
					}
				} else {
					tfsvrFileDtlParaDO1.setTempColName("");
					tfsvrFileDtlParaDO1.setTempFlg("");
				}

				tfsvrFileDtlParaDO1.setClobFlg(headDtlList.get(i).getMidRmrk());
				if ("Y".equals(tfsvrFileDtlParaDO1.getChgFlg())) {
					enumNum++;
					TfsvrFileColChgDtlParaDO tfsvrFileColChgDtlParaDO = new TfsvrFileColChgDtlParaDO();
					tfsvrFileColChgDtlParaDO.setChgNo(chgParaDO.getMidRmrk());
					tfsvrFileColChgDtlParaDO.setFileFlg(tfsvrFileDtlParaDO1.getFileFlg());
					tfsvrFileColChgDtlParaDO.setSer(null);
					tfsvrFileColChgDtlParaDO.setColSer(tfsvrFileDtlParaDO1.getSer());
					List<TfsvrFileColChgDtlParaDO> chgDtlList = tfsvrFileColChgDtlParaDao
							.list(tfsvrFileColChgDtlParaDO);
					List<TfmngFileDtlReqListDTO> dtlList = new ArrayList<TfmngFileDtlReqListDTO>();
					for (int j = 0; j < chgDtlList.size(); j++) {
						TfmngFileDtlReqListDTO tempDTO = new TfmngFileDtlReqListDTO();
						tempDTO.setInKv(chgDtlList.get(j).getInKv());
						tempDTO.setOutKv(chgDtlList.get(j).getOutKv());
						tempDTO.setAction("<a onclick='deleteRow(this)'>删除</a>");
						dtlList.add(tempDTO);
					}
					JSONArray jsonArr = JSONArray.parseArray(JSON.toJSONString(dtlList));
					String list = jsonArr.toString();
					tfsvrFileDtlParaDO1.setList(list);
				}
				headList.add(tfsvrFileDtlParaDO1);
			}
			obj.setHeadNum(headList.size());
		} else {
			obj.setHeadNum(0);
		}

		// 获取尾
		tfsvrFileDtlParaDO.setFileFlg("3");
		List<TfsvrFileDtlParaDO> tailDtlList = tfsvrFileDtlParaDao.getTempHeadList(tfsvrFileDtlParaDO, 0, 0);
		if (tailDtlList != null) {
			for (int i = 0; i < tailDtlList.size(); i++) {
				TfsvrFilePlateDelDO tfsvrFileDtlParaDO1 = new TfsvrFilePlateDelDO();
				tfsvrFileDtlParaDO1.setSer(tailDtlList.get(i).getSer());
				tfsvrFileDtlParaDO1.setFileFlg(tailDtlList.get(i).getFileFlg());
				tfsvrFileDtlParaDO1.setColName(tailDtlList.get(i).getColName());
				tfsvrFileDtlParaDO1.setFileColTp(tailDtlList.get(i).getFileColTp());
				tfsvrFileDtlParaDO1.setColLen(tailDtlList.get(i).getColLen());
				tfsvrFileDtlParaDO1.setAlignMeth(tailDtlList.get(i).getAlignMeth());
				tfsvrFileDtlParaDO1.setDltSymTp(tailDtlList.get(i).getDltSymTp());
				tfsvrFileDtlParaDO1.setTempFlg(tailDtlList.get(i).getFileFlg());
				tfsvrFileDtlParaDO1.setChgFlg(tailDtlList.get(i).getChgFlg());
				if ( "1".equals(obj.getTranTp())){
					tfsvrFileDtlParaDO1.setTempSer(tailDtlList.get(i).getShortRmrk());
				} else {
					tfsvrFileDtlParaDO1.setTempSer(tailDtlList.get(i).getColNo());
				}
				if ("1".equals(tailDtlList.get(i).getColKd())){////1-字段 2-常量
					tfsvrFileDtlParaDO1.setTempFlg(tailDtlList.get(i).getFileFlg());
					TfsvrFileDtlParaDO tempDtl = tfsvrFileDtlParaDao.get(obj.getTempFmtNo(), "3", Long.parseLong(tfsvrFileDtlParaDO1.getTempSer()));
					if (tempDtl != null ) {
						tfsvrFileDtlParaDO1.setTempColName(tempDtl.getColName());
					}
				} else {
					tfsvrFileDtlParaDO1.setTempColName("");
					tfsvrFileDtlParaDO1.setTempFlg("");
				}
				tfsvrFileDtlParaDO1.setClobFlg(tailDtlList.get(i).getMidRmrk());
				if ("Y".equals(tfsvrFileDtlParaDO1.getChgFlg())) {
					enumNum++;
					TfsvrFileColChgDtlParaDO tfsvrFileColChgDtlParaDO = new TfsvrFileColChgDtlParaDO();
					tfsvrFileColChgDtlParaDO.setChgNo(chgParaDO.getMidRmrk());
					tfsvrFileColChgDtlParaDO.setFileFlg(tfsvrFileDtlParaDO1.getFileFlg());
					tfsvrFileColChgDtlParaDO.setColSer(tfsvrFileDtlParaDO1.getSer());
					List<TfsvrFileColChgDtlParaDO> chgDtlList = tfsvrFileColChgDtlParaDao
							.list(tfsvrFileColChgDtlParaDO);
					List<TfmngFileDtlReqListDTO> dtlList = new ArrayList<TfmngFileDtlReqListDTO>();
					for (int j = 0; j < chgDtlList.size(); j++) {
						TfmngFileDtlReqListDTO tempDTO = new TfmngFileDtlReqListDTO();
						tempDTO.setInKv(chgDtlList.get(j).getInKv());
						tempDTO.setOutKv(chgDtlList.get(j).getOutKv());
						tempDTO.setAction("<a onclick='deleteRow(this)'>删除</a>");
						dtlList.add(tempDTO);
					}
					JSONArray jsonArr = JSONArray.parseArray(JSON.toJSONString(dtlList));
					String list = jsonArr.toString();
					tfsvrFileDtlParaDO1.setList(list);
				}
				headList.add(tfsvrFileDtlParaDO1);
			}
			obj.setBodyNum(tailDtlList.size());
		} else {
			obj.setTailNum(0);
		}

		// 获取体
		tfsvrFileDtlParaDO.setFileFlg("2");
		List<TfsvrFileDtlParaDO> bodyDtlList = tfsvrFileDtlParaDao.getTempHeadList(tfsvrFileDtlParaDO, 0, 0);
		if (bodyDtlList != null) { 
			for (int i = 0; i < bodyDtlList.size(); i++) {
				TfsvrFilePlateDelDO tfsvrFileDtlParaDO1 = new TfsvrFilePlateDelDO();
				tfsvrFileDtlParaDO1.setSer(bodyDtlList.get(i).getSer());
				tfsvrFileDtlParaDO1.setFileFlg(bodyDtlList.get(i).getFileFlg());
				tfsvrFileDtlParaDO1.setColName(bodyDtlList.get(i).getColName());
				tfsvrFileDtlParaDO1.setFileColTp(bodyDtlList.get(i).getFileColTp());
				tfsvrFileDtlParaDO1.setColLen(bodyDtlList.get(i).getColLen());
				tfsvrFileDtlParaDO1.setAlignMeth(bodyDtlList.get(i).getAlignMeth());
				tfsvrFileDtlParaDO1.setDltSymTp(bodyDtlList.get(i).getDltSymTp());
				tfsvrFileDtlParaDO1.setTempFlg(bodyDtlList.get(i).getFileFlg());
				tfsvrFileDtlParaDO1.setChgFlg(bodyDtlList.get(i).getChgFlg());
				if ( "1".equals(obj.getTranTp())){
					tfsvrFileDtlParaDO1.setTempSer(bodyDtlList.get(i).getShortRmrk());
				} else {
					tfsvrFileDtlParaDO1.setTempSer(bodyDtlList.get(i).getColNo());
				}
				if ("1".equals(bodyDtlList.get(i).getColKd())){////1-字段 2-常量
					tfsvrFileDtlParaDO1.setTempFlg(bodyDtlList.get(i).getFileFlg());
					TfsvrFileDtlParaDO tempDtl = tfsvrFileDtlParaDao.get(obj.getTempFmtNo(), "2", Long.parseLong(tfsvrFileDtlParaDO1.getTempSer()));
					if (tempDtl != null ) {
						tfsvrFileDtlParaDO1.setTempColName(tempDtl.getColName());
					}
				} else {
					tfsvrFileDtlParaDO1.setTempColName("");
					tfsvrFileDtlParaDO1.setTempFlg("");
				}
				tfsvrFileDtlParaDO1.setClobFlg(bodyDtlList.get(i).getMidRmrk());
				if ("Y".equals(tfsvrFileDtlParaDO1.getChgFlg())) {
					enumNum++;
					TfsvrFileColChgDtlParaDO tfsvrFileColChgDtlParaDO = new TfsvrFileColChgDtlParaDO();
					tfsvrFileColChgDtlParaDO.setChgNo(chgParaDO.getMidRmrk());
					tfsvrFileColChgDtlParaDO.setFileFlg(tfsvrFileDtlParaDO1.getFileFlg());
					tfsvrFileColChgDtlParaDO.setColSer(tfsvrFileDtlParaDO1.getSer());
					List<TfsvrFileColChgDtlParaDO> chgDtlList = tfsvrFileColChgDtlParaDao
							.list(tfsvrFileColChgDtlParaDO);
					List<TfmngFileDtlReqListDTO> dtlList = new ArrayList<TfmngFileDtlReqListDTO>();
					for (int j = 0; j < chgDtlList.size(); j++) {
						TfmngFileDtlReqListDTO tempDTO = new TfmngFileDtlReqListDTO();
						tempDTO.setInKv(chgDtlList.get(j).getInKv());
						tempDTO.setOutKv(chgDtlList.get(j).getOutKv());
						tempDTO.setAction("<a onclick='deleteRow(this)'>删除</a>");
						dtlList.add(tempDTO);
					}
					JSONArray jsonArr = JSONArray.parseArray(JSON.toJSONString(dtlList));
					String list = jsonArr.toString();
					tfsvrFileDtlParaDO1.setList(list);
				}
				bodyList.add(tfsvrFileDtlParaDO1);
			}
			obj.setBodyNum(bodyList.size());
		} else {
			obj.setBodyNum(0);
		}
		obj.setEnumNum(enumNum);
		
		tfsvrFilePlateDO.setHeadList(headList);
		tfsvrFilePlateDO.setBodyList(bodyList);

		return tfsvrFilePlateDO;
	}
}
