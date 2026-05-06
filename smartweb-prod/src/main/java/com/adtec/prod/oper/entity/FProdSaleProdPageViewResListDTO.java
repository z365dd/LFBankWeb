package com.adtec.prod.oper.entity;

import java.util.ArrayList;
import java.util.List;

public class FProdSaleProdPageViewResListDTO {
	private String ATOM_PROD_CODE;
	private String ATOM_PROD_DESC;

	private List<FProdSaleProdPageViewResListKeyListDTO> KEY_LIST = new ArrayList<FProdSaleProdPageViewResListKeyListDTO>();

	public String getATOM_PROD_CODE() {
		return ATOM_PROD_CODE;
	}

	public void setATOM_PROD_CODE(String ATOM_PROD_CODE) {
		this.ATOM_PROD_CODE = ATOM_PROD_CODE;
	}

	public String getATOM_PROD_DESC() {
		return ATOM_PROD_DESC;
	}

	public void setATOM_PROD_DESC(String ATOM_PROD_DESC) {
		this.ATOM_PROD_DESC = ATOM_PROD_DESC;
	}

	public List<FProdSaleProdPageViewResListKeyListDTO> getKEY_LIST() {
		return KEY_LIST;
	}

	public void setKEY_LIST(List<FProdSaleProdPageViewResListKeyListDTO> KEY_LIST) {
		this.KEY_LIST = KEY_LIST;
	}
}
