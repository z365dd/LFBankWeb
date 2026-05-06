package com.adtec.comp.fsvr.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TFmngTranSearchResDTO  {
	private long FILE_NUM;
	//返回文件名称
	private ArrayList<TFmngTranSearchResListDTO> LIST = new ArrayList<TFmngTranSearchResListDTO>();
	public long getFILE_NUM() {
		return FILE_NUM;
	}
	public void setFILE_NUM(long fILE_NUM) {
		FILE_NUM = fILE_NUM;
	}
	public ArrayList<TFmngTranSearchResListDTO> getLIST() {
		return LIST;
	}
	public void setLIST(ArrayList<TFmngTranSearchResListDTO> lIST) {
		LIST = lIST;
	}
	@Override
	public String toString() {
		return "TFmngTranSearchResDTO [FILE_NUM=" + FILE_NUM + ", LIST=" + LIST + "]";
	}
	
}
