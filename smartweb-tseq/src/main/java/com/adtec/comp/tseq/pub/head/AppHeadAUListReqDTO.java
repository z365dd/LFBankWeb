package com.adtec.comp.tseq.pub.head;

import com.alibaba.fastjson.annotation.JSONField;

public class AppHeadAUListReqDTO {
	@JSONField(name="AUTH_TLR_NO")
	private String AUTH_TLR_NO;
	@JSONField(name="AUTH_BRCH")
	private String AUTH_BRCH;
	public String getAUTH_TLR_NO() {
		return AUTH_TLR_NO;
	}
	public void setAUTH_TLR_NO(String aUTH_TLR_NO) {
		AUTH_TLR_NO = aUTH_TLR_NO;
	}
	public String getAUTH_BRCH() {
		return AUTH_BRCH;
	}
	public void setAUTH_BRCH(String aUTH_BRCH) {
		AUTH_BRCH = aUTH_BRCH;
	}
	
	
}
