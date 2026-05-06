/*
 * 系统名称: 
 * 模块名称: 
 * 类 名 称: Field.java
 * 软件版权: 
 * 相关文档:
 * 修改记录:
 * 修改日期      修改人员                     修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */

package com.adtec.framework.impl.share.event.field;

import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

/**
 * 数据集的域信息，内部使用
 * <p>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-20 <br>
 * <br>
 */
public class Field implements Cloneable
{
	private String	name;
	private char	type;

	/**
	 * 设置域的名字和类型
	 * @param name
	 *            字段名
	 * @param type
	 *            字段类型
	 * @see com.adtec.framework.interfaces.share.DatasetColumnType
	 */
	protected Field(String name, char type)
	{
		if (name == null || name.length() == 0) {
			//  修改异常提示，使其更准确
			throw new BaseException(SysErr.E_NO_MESSAGE, "invalid column name[" + name + "]");
			// }
		}
		this.name = name;
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Field clone()
	{
		try {
			return (Field) super.clone();
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Field obj)
	{
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}

		Field other = obj;
		return StringUtil.equals(name, other.name) && (type == other.type);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean weakEquals(Field obj)
	{
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}

		Field other = obj;
		return StringUtil.equals(name, other.name);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "[" + name + "," + type + "]";
	}

	/**
	 * 得到字段名
	 * @return 字段名
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 设置字段名
	 * @param name
	 *            字段名
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 得到字段类型
	 * @return 字段类型
	 * @see com.adtec.framework.interfaces.share.DatasetColumnType
	 */
	public char getType()
	{
		return type;
	}

	/**
	 * 设置字段类型
	 * @param type
	 *            字段类型
	 * @see com.adtec.framework.interfaces.share.DatasetColumnType
	 */
	public void setType(char type)
	{
		this.type = type;
	}
}
