package com.adtec.prod.util;

import com.adtec.prod.oper.entity.BusiDemoDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreviewHandler implements Runnable{
	private final static Logger logger = LoggerFactory.getLogger(PreviewHandler.class);
	private BusiDemoDO file;
	private String realPath;
	public PreviewHandler(BusiDemoDO file,String realPath){
		this.file = file;
		this.realPath = realPath;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
