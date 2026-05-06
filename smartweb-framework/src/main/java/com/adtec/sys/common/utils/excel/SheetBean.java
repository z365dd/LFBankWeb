package com.adtec.sys.common.utils.excel;

import java.util.List;

public class SheetBean {

	private int sheetIndex;
	private String sheetName;
	private List<String[]> rows;

	public SheetBean(int sheetIndex, String sheetName, List<String[]> rows) {
		super();
		this.sheetIndex = sheetIndex;
		this.sheetName = sheetName;
		this.rows = rows;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<String[]> getRows() {
		return rows;
	}

	public void setRows(List<String[]> rows) {
		this.rows = rows;
	}

}
