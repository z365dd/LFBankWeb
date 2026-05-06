/*
 * 系统名称: 
 * 模块名称:
 * 类 名 称: CommonDatasets.java
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

package com.adtec.framework.impl.share.dataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasets;

/**
 * 多数据集的实现类，此数据集是线程不安全的。
 * <p>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-20 <br>
 * <br>
 */
public class CommonDatasets implements IDatasets
{
	/** 名字到数据集的映射 */
	private Map<String, IDataset>	nameMapResult	= new HashMap<String, IDataset>();
	/** 数据集名字的列表，用来给数据集按照索引排序 */
	private List<String>			names			= new ArrayList<String>();

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasets#getDataset(java.lang.String)
	 */
	public IDataset getDataset(String name)
	{
		return nameMapResult.get(name);
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.cep.event.IEventResult#getResult(int)
	 */
	public IDataset getDataset(int index)
	{
		if (index >= 0 && index < names.size()) {
			String name = names.get(index);
			return nameMapResult.get(name);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.cep.event.IEventResult#getResultCount()
	 */
	public int getDatasetCount()
	{
		return names.size();
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.cep.event.IEventResult#getResultName(int)
	 */
	public String getDatasetName(int index)
	{
		if (index >= 0 && index < names.size()) {
			return names.get(index);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasets#putDataset
	 * (com.adtec.framework.interfaces.share.dataset.IDataset)
	 */
	public void putDataset(IDataset dataset)
	{
		if (dataset == null) {
			throw new BaseException(SysErr.E_NULL_POINTER,
					"input dataset is null");
		}
		String name = dataset.getDatasetName();
		// 空数据集名字，认为是默认数据集，放在第一个。不允许有重复的默认数据集
		if (name == null) {
			if (getDatasetCount() > 0) {
				String firstName = getDataset(0).getDatasetName();
				if (firstName == null) {
					throw new BaseException(SysErr.E_NOT_SUPPORTED,
							"duplicate default dataset");
				}
			}
			names.add(0, null);
			nameMapResult.put(null, dataset);
		} else {
			int index = names.indexOf(name);
			if (index == -1) {
				names.add(name);
			}
			dataset.setDatasetName(name);

			nameMapResult.put(name, dataset);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.adtec.framework.interfaces.share.dataset.IDatasets#clear()
	 */
	public void clear()
	{
		nameMapResult.clear();
		names.clear();
	}
}
