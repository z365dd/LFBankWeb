package com.adtec.framework.impl.share.caseStrategy;

public class MBCCaseStrategy extends BaseCaseStrategy {

	public String getPropertyName(String fieldName) {
		return fieldName.toUpperCase();
	}

	public String getFieldName(String propertyName) {
		return propertyName;
	}

}
