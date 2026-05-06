package com.adtec.framework.impl.share.caseStrategy;

public class CamelCaseStrategy extends BaseCaseStrategy {

	public String getPropertyName(String fieldName) {
		fieldName = fieldName.toLowerCase();
		String[] sa = fieldName.split("_");
		StringBuffer sb = new StringBuffer();
		sb.append(sa[0]);
		for (int i = 1; i < sa.length; i++) {
			sb.append(uppercaseFirstChar(sa[i]));
		}
		return sb.toString();

	}

	public String getFieldName(String propertyName) {
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < propertyName.length(); i++) {
			if (propertyName.charAt(i) >= 'A' && propertyName.charAt(i) <= 'Z') {
				ret.append("_").append(
						(char) (propertyName.charAt(i) - 'A' + 'a'));
			} else {
				ret.append(propertyName.charAt(i));
			}
		}
		return ret.toString();
	}

	private String uppercaseFirstChar(String str) {
		String ret = str;
		int c = str.charAt(0);
		if (c <= 'z' && c >= 'a') {
			c = c - 'a' + 'A';
			ret = (char) c + str.substring(1);
		}
		return ret;
	}

	private String lowercaseFirstChar(String str) {
		String ret = str;
		int c = str.charAt(0);
		if (c <= 'Z' && c >= 'A') {
			c = c - 'A' + 'a';
			ret = (char) c + str.substring(1);
		}
		return ret;
	}
}
