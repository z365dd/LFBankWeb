package com.adtec.framework.impl.share.caseStrategy;

public class NormalCaseStrategy extends BaseCaseStrategy {

	public String getPropertyName(String fieldName) {
		return fieldName.toLowerCase();
	}

	public String getFieldName(String propertyName) {
		return propertyName;
	}

}
