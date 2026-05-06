/*
 * 系统名称:
 * 模块名称:
 * 类 名 称: PropertyAdaptor.java
 * 软件版权: 
 * 相关文档:
 * 修改记录:
 * 修改日期      修改人员                     修改说明<BR>
 * ========     ======  ============================================
 *   
 * ========     ======  ============================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.adtec.framework.common.util.objectutil;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 * Used to manage dynamic access to a property of a specific class.
 * 
 * 
 */
public class PropertyAdaptor {
	private String _propertyName;

	private Class _propertyType;

	private Method _readMethod;

	private Method _writeMethod;

	PropertyAdaptor(String propertyName, Class propertyType, Method readMethod,
			Method writeMethod) {
		_propertyName = propertyName;
		_propertyType = propertyType;
		_readMethod = readMethod;
		_writeMethod = writeMethod;
	}

	/**
	 * Returns the name of the method used to read the property, or null if the
	 * property is not readable.
	 */
	public String getReadMethodName() {
		return _readMethod == null ? null : _readMethod.getName();
	}

	/**
	 * Returns the name of the method used to write the property, or null if the
	 * property is not writable.
	 */
	public String getWriteMethodName() {
		return _writeMethod == null ? null : _writeMethod.getName();
	}

	public String getPropertyName() {
		return _propertyName;
	}

	public Class getPropertyType() {
		return _propertyType;
	}

	/**
	 * Updates the property of the target object.
	 * 
	 * @param target
	 *            the object to update
	 * @param value
	 *            the value to be stored into the target object property
	 */
	public void write(Object target, Object value) {
		if (_writeMethod == null)
			throw new RuntimeException(target+" Property "
					+ _propertyName + " of object " + target + " is read-only.");

		try {
			_writeMethod.invoke(target, new Object[] { value });

		} catch (Exception ex) {
			throw new RuntimeException(target+" Unable to update property " + _propertyName
							+ " of object " + target + ": " + ex,ex);
		}
	}

	public void smartWrite(Object target, String value) {
		Object convertedValue = convertValueForAssignment(target, value);

		write(target, convertedValue);
	}

	private Object convertValueForAssignment(Object target, String value) {
		if (value == null || _propertyType.isInstance(value))
			return value;

		PropertyEditor e = PropertyEditorManager.findEditor(_propertyType);

		if (e == null) {
			Object convertedValue = instantiateViaStringConstructor(target,
					value);

			if (convertedValue != null)
				return convertedValue;

			throw new RuntimeException("There has no Editor for property " + _propertyName + " of class " + target.getClass());
		}

		try {
			e.setAsText(value);

			return e.getValue();
		} catch (Exception ex) {
			throw new RuntimeException("Unable to convert value [" + value +"] to type [" + _propertyType + "] for property [" + _propertyName + "] of " + target,ex);
		}
	}

	/**
	 * Checks to see if this adaptor's property type has a public constructor
	 * that takes a single String argument.
	 */

	private Object instantiateViaStringConstructor(Object target, String value) {
		try {
			Constructor c = _propertyType
					.getConstructor(new Class[] { String.class });

			return c.newInstance(new Object[] { value });
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Returns true if there's a write method for the property.
	 */
	public boolean isWritable() {
		return _writeMethod != null;
	}

	/**
	 * Reads the property of the target object.
	 * 
	 * @param target
	 *            the object to read a property from
	 */
	public Object read(Object target) {
		if (_readMethod == null)
			throw new RuntimeException(target+ " roperty "+_propertyName+" of object "+target+" is write-only.");

		try {
			return _readMethod.invoke(target, (Object[]) null);

		} catch (Exception ex) {
			throw new RuntimeException(
					"Fail to read property " + _propertyName + " of " + target,ex);
		}
	}

	/**
	 * Returns true if there's a read method for the property.
	 */

	public boolean isReadable() {
		return _readMethod != null;
	}

}
