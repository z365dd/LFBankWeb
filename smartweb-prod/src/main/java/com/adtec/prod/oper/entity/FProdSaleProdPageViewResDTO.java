package com.adtec.prod.oper.entity;

import java.util.ArrayList;
import java.util.List;

public class FProdSaleProdPageViewResDTO {
	private List<FProdSaleProdPageViewResListDTO> LIST = new ArrayList<FProdSaleProdPageViewResListDTO>();

	public List<FProdSaleProdPageViewResListDTO> getLIST() {
		return LIST;
	}

	public void setLIST(List<FProdSaleProdPageViewResListDTO> LIST) {
		this.LIST = LIST;
	}
//	public void toDataPool() {
//		DtaInfo dtaInfo = DtaInfo.getInstance();
//		SvcLogic svcLogic = (SvcLogic) PoolOperate.getResData(dtaInfo.getDrqInfo().getdDtaName(), PoolOperate.LOGIC,
//				dtaInfo.getSvcName());
//		String outElemName = svcLogic.getOutElemName();
//		CompSDO body = ElemUtil.getRootSDO(outElemName);
//
//		for (int list = 0; list < fProdSaleProdPageViewResListDTOList.size(); list++) {
//			FProdSaleProdPageViewResListDTO fProdSaleProdPageViewResListDTO = fProdSaleProdPageViewResListDTOList
//					.get(list);
//			CompSDO itemList = (CompSDO) body.getValue("LIST", list);
//
//			itemList.setValue("ATOM_PROD_CODE", fProdSaleProdPageViewResListDTO.getAtomProdCode());
//			itemList.setValue("ATOM_PROD_DESC", fProdSaleProdPageViewResListDTO.getAtomProdDesc());
//
//			List<FProdSaleProdPageViewResListKeyListDTO> fProdSaleProdPageViewResListKeyListDTOList = fProdSaleProdPageViewResListDTO
//					.getFProdSaleProdPageViewResListKeyListDTOList();
//			for (int listKeyList = 0; listKeyList < fProdSaleProdPageViewResListKeyListDTOList.size(); listKeyList++) {
//				FProdSaleProdPageViewResListKeyListDTO fProdSaleProdPageViewResListKeyListDTO = fProdSaleProdPageViewResListKeyListDTOList
//						.get(listKeyList);
//				CompSDO itemListKeyList = (CompSDO) itemList.getValue("KEY_LIST", listKeyList);
//
//				itemListKeyList.setValue("KEY_NO", fProdSaleProdPageViewResListKeyListDTO.getKeyNo());
//				itemListKeyList.setValue("KEY_NAME", fProdSaleProdPageViewResListKeyListDTO.getKeyName());
//				itemListKeyList.setValue("LINE_SER", fProdSaleProdPageViewResListKeyListDTO.getLineSer());
//				itemListKeyList.setValue("COL_SER", fProdSaleProdPageViewResListKeyListDTO.getColSer());
//				itemListKeyList.setValue("FLG", fProdSaleProdPageViewResListKeyListDTO.getFlg());
//				itemListKeyList.setValue("VAL_TP", fProdSaleProdPageViewResListKeyListDTO.getValTp());
//				itemListKeyList.setValue("VAL_LEN", fProdSaleProdPageViewResListKeyListDTO.getValLen());
//				itemListKeyList.setValue("KEY_TP", fProdSaleProdPageViewResListKeyListDTO.getKeyTp());
//				itemListKeyList.setValue("INPUT_FLG", fProdSaleProdPageViewResListKeyListDTO.getInputFlg());
//				itemListKeyList.setValue("DEFA_KV", fProdSaleProdPageViewResListKeyListDTO.getDefaKv());
//				itemListKeyList.setValue("KEY_FLG",fProdSaleProdPageViewResListKeyListDTO.getKeyFlg());
//
//				List<FProdSaleProdPageViewResListKeyListCtrlListDTO> fProdSaleProdPageViewResListKeyListCtrlListDTOList = fProdSaleProdPageViewResListKeyListDTO
//						.getFProdSaleProdPageViewResListKeyListCtrlListDTOList();
//				for (int listKeyListCtrlList = 0; listKeyListCtrlList < fProdSaleProdPageViewResListKeyListCtrlListDTOList
//						.size(); listKeyListCtrlList++) {
//					FProdSaleProdPageViewResListKeyListCtrlListDTO fProdSaleProdPageViewResListKeyListCtrlListDTO = fProdSaleProdPageViewResListKeyListCtrlListDTOList
//							.get(listKeyListCtrlList);
//					CompSDO itemListKeyListCtrlList = (CompSDO) itemListKeyList.getValue("CTRL_LIST", listKeyListCtrlList);
//
//					itemListKeyListCtrlList.setValue("KEY_NO",
//							fProdSaleProdPageViewResListKeyListCtrlListDTO.getKeyNo());
//					itemListKeyListCtrlList.setValue("KEY_NAME",
//							fProdSaleProdPageViewResListKeyListCtrlListDTO.getKeyName());
//					itemListKeyListCtrlList.setValue("KV", fProdSaleProdPageViewResListKeyListCtrlListDTO.getKv());
//				}
//
//			}
//
//		}
//
//	}

}
