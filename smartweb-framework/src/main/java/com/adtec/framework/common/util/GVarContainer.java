/**
 * 系统名称: SmartWeb平台
 * 模块名称: GVarContainer线程全局变量容器类
 * 类  名  称: GVarContainer.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年12月12日 上午9:55:56<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.framework.common.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenyl
 *
 */
public class GVarContainer {
	@SuppressWarnings("rawtypes")
	private static ConcurrentHashMap Container = new ConcurrentHashMap();

	/**
	 * 根据当前线程的ID将需要传递的对象放置到ID对应的容器中
	 * 
	 * @param name
	 *            需要被传递对象的标识, 之后可以根据标识取得此对象,标识不能为空
	 * @param obj
	 *            需要被传递的对象, 对象不能为空
	 * @throws BaseException
	 *             - obj或name为空时抛出异常
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setVar(String name, Object obj) {
		if (null == obj || null == name) {
			// throw new BaseException( SysErr.E_NULLPOINT, "name or obj is
			// null" );
		}

		Thread th = Thread.currentThread();
		// Hashtable varTab = (Hashtable)Container.get( th.getId( ) );
		ConcurrentHashMap varTab = (ConcurrentHashMap) Container.get(th.getId());

		if (null == varTab) {
			// varTab = new Hashtable( );
			varTab = new ConcurrentHashMap();

			varTab.put(name, obj);
		} else {
			varTab.put(name, obj);
		}

		Container.put(th.getId(), varTab);
	}

	/**
	 * 根据当前线程的ID取得标识所对应的对象， 取得对象前必须确保调用过setVar设置对象
	 * 
	 * @param name
	 *            对象对应的标识，须与在setVar时的相同,标识不能为空
	 * @return 标识对应的对象，如果不存在返回null
	 * @throws BaseException
	 *             - name为空时抛出异常
	 */
	@SuppressWarnings("rawtypes")
	public static Object getVar(String name) {
		if (null == name) {
			// throw new BaseException( SysErr.E_NULLPOINT, "name" );
		}

		Thread th = Thread.currentThread();

		ConcurrentHashMap varTab = (ConcurrentHashMap) Container.get(th.getId());

		if (null == varTab) {
			return null; // 不抛出异常，直接返回空值
			// throw new BaseException( SysErr.E_VTABNOTINIT );
		}

		Object obj = varTab.get(name);

		return obj;
	}

	/**
	 * 清除当前线程ID下对象标识对应的被传递的对象
	 * 
	 * @param name
	 *            对象对应的标识，须与在setVar时的相同,标识不能为空
	 */
	@SuppressWarnings("rawtypes")
	public static void removeVar(String name) {
		Thread th = Thread.currentThread();
		// Hashtable varTab = (Hashtable)Container.get( th.getId( ) );
		ConcurrentHashMap varTab = (ConcurrentHashMap) Container.get(th.getId());

		if (null == varTab) {
			return;
		}

		varTab.remove(name);
	}

	/**
	 * 清除当前线程ID下全部被传递的对象
	 * 
	 * @param name
	 *            对象对应的标识，须与在setVar时的相同,标识不能为空
	 */
	@SuppressWarnings("rawtypes")
	public static void clearVar() {
		Thread th = Thread.currentThread();
		ConcurrentHashMap varTab = (ConcurrentHashMap) Container.get(th.getId());
		if (varTab != null) {
			varTab.clear();
		}

		Container.remove(th.getId());
	}
}
