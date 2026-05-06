/*
 * 系统名称: 
 * 模块名称:
 * 类 名 称: IDataset.java
 * 软件版权: 
 * 相关文档:
 * 修改记录:
 * 修改日期              修改人员                     修改说明<BR>
 * ========     ======  ============================================

 */

package com.adtec.framework.interfaces.share;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.interfaces.share.reader.IResultSetReader;
import com.adtec.framework.interfaces.share.writer.IResultSetWriter;

/**
 * 数据集 <code>IDataset</code> 是用于表示事件体最基本的数据结构，他是一个二维表结构。
 * <p>
 * 使用者可以为数据集设置一个名字 {@link #setDatasetName(String)}，在数据集的集合 {@link IDatasets} 中，
 * 用于区分不同的数据集。当然可以不指定数据集的名字，不过在 <code>IDatasets</code> 中没有指定名字的数据集最多只能
 * 有一个，否则可能会出现未知错误。
 * <p>
 * 另外，在一些情况下，在返回应答的时候可能还需要附带一些辅助的字段信息，比如分页的totalCount，IDataset做了这样
 * 的建议，所有的辅助字段都放在一个叫做 {@link #DS_PARAMETERS} 的数据集中，此数据集是单行的，字段个数不限。
 * <p>
 * <code>DatasetService</code>
 * 是IDataset的一个工厂，提供了将一些常用的容器转变成IDataset的工厂方法，所有的IDataset 的实例都必须通过此工厂获得。
 * <p>
 * <blockquote>
 * 
 * <pre>
 * IDataset dataset = DatasetService.getDefaultInstance().getDataset(); // 获取一个空的Dataset
 * ...
 * Map&lt;String, Object&gt; map = ... // Map
 * dataset = DatasetService.getDefaultInstance().getDataset(map); // 根据一个Map生成一个Dataset
 * ...
 * </pre>
 * 
 * </blockquote>
 * <p>
 * <code>IDataset</code> 接口定义了构造和访问一个二维表的行、列信息的基本接口。数据集的行列索引都是从 1 开始的。索引 为 0
 * 的行或者列都作为无效行或者列。
 * <p>
 * <blockquote>
 * 
 * <pre>
 * ...
 * int index = dataset.findColumn(&quot;columnName&quot;);
 * if (index == 0) {
 *  // ...不存在
 * } else {
 *  // ...存在
 * }
 * </pre>
 * 
 * </blockquote> <code>IDataset</code>
 * 的工作方式：在读取数据集的行记录的时候，可能会遇到类型转换失败或者指定的列名不存在的情况，使用者
 * 可以根据需要修改数据集的工作方式，在遇到前面的情况的时候是返回默认值（工作模式为：IDataset.MODE_DEFAULT），
 * 还是抛出运行时异常（工作模式
 * ：IDataset.MODE_EXCEPTION）。只有在IDataset.MODE_DEFAULT模式下，所有设置的默认值才有效，
 * 在默认情况下，数据集采用默认值的方式。
 * <p>
 * <blockquote>
 * 
 * <pre>
 * IDataset dataset = ... // 一个数据集的实例
 * dataset.setMode(IDataset.MODE_DEFAULT);
 * // 尝试获取一个不存在的列，返回String的默认值，为 &quot;&quot;
 * String value = dataset.getString(&quot;notExistColumnName&quot;);
 * dataset.setMode(IDataset.MODE_EXCEPTION);
 * // 抛出异常
 * value = dataset.getString(&quot;notExistColumnName&quot;);
 * </pre>
 * 
 * </blockquote>
 * <p>
 * <code>IDataset</code> 提供了类似于 {@link java.sql.ResultSet}
 * 填充和修改的方式，不过使用起来更加简单。在填充一个数据集
 * 之前必须为其添加元数据，即列信息，每个列包括两个部分的内容，列名和列类型，列名由使用者保证唯一，如果有重名的情况后添加的会覆盖
 * 前面添加的列信息。另外，列信息可以在任何时候添加，就算数据集中已经有了行记录，此时所有行新添加的列采用默认值。
 * <p>
 * <blockquote>
 * 
 * <pre>
 * IDataset dataset = ... // 得到一个新的数据集实例
 * dataset.addColumn(&quot;favor&quot;, IDataset.DS_STRING);	// 第一列
 * dataset.addColumn(&quot;hate&quot;, IDateset.DS_STRING); // 第二列
 * </pre>
 * 
 * </blockquote> 添加行记录之前必须显式的调用 {@link #appendRow()}
 * 方法，使行指针移动到新的一行，否则所有的更新操作都只针对当前行。更新操作可以根据
 * 索引和列名两种方式，列索引时从1开始的，索引如果超出范围，列名如果不存在则会抛出 {@link BaseException} 异常。
 * 没有更新的列采用默认值。
 * <p>
 * <blockquote>
 * 
 * <pre>
 * dataset.appendRow();	// 添加一行到末尾
 * dataset.updateString(1, &quot;apple&quot;);	// 显示指定用String的方式更新
 * dataset.updateString(2, &quot;grape&quot;);	// 使用索引的方式更新
 * ...
 * dataset.appendRow();	// 添加一行到末尾
 * dataset.updateString(&quot;favor&quot;, &quot;pear&quot;);	// 显式指定用String方式更新
 * dataset.updateString(&quot;hate&quot;, &quot;banana&quot;);	// 使用列名的方式更新
 * ...
 * dataset.appendRow();	// 添加一行到末尾
 * dataset.updateValue(&quot;favor&quot;, &quot;tomato&quot;);	// 不显式指定更新的类型，此时会通过反射的方式去尝试识别类型
 * // 比显式的制定类型要慢
 * dataset.updateValue(&quot;hate&quot;, &quot;cucumber&quot;);	// 使用列名的方式更新
 * </pre>
 * 
 * </blockquote> <code>IDataset</code>
 * 在读取行记录的时候，可以采用索引、列名两种方式，索引从1开始，如果超出了范围则会抛出 {@link BaseException}
 * 异常。 不论是哪种方式，都有两类方法：
 * <p>
 * ① 显式的指定默认值，比如方法 {@link #getDouble(int columnIndex, String def)}，如果获取失败则返回默认值
 * <br>
 * ② 使用数据集的默认值，比如方法 {@link #getDouble(int columnIndex)}，数据集的默认值可以通过
 * <code>DatasetService</code> 来获取和修改
 * <p>
 * <blockquote>
 * 
 * <pre>
 * IDataset dataset = ... // 某个实例
 * IDatasetAttribute attr = DatasetService.getDatasetAttribute(dataset);
 * </pre>
 * 
 * </blockquote> 在 <code>IDatasetAttribute</code> 中定义了每种类型所对应的默认值是多少。
 * <p>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-12-9<BR>
 * @see IDatasets
 * @see IDatasetAttribute
 * @see DatasetColumnType
 * @see BaseException
 */
public interface IDataset extends IDatasetMetaData, IResultSetReader, IResultSetWriter,
		DatasetColumnType
{
	/** 默认数据集的名字，如果在一个Datasets中没有指定默认数据集，则认为第一个数据集为默认数据集 */
	public static final String	DS_DEFAULT_NAME	= "PLATFORM_DEFAULT_DATASET_NAME";

	/** 表示辅助字段的Dataset的名字，此Dataset是单记录 */
	public static final String	DS_PARAMETERS	= "PLATFORM_PARAMETER_DATASET_NAME";

	/**
	 * 获取本数据集的名字
	 * @return 数据集的名字，可以为null
	 */
	String getDatasetName();

	/**
	 * 设置数据集的名字
	 * @param name
	 *            数据集的名字
	 */
	void setDatasetName(String name);

	/**
	 * 得到分页查询得出来的总记录数，与当前记录数无关
	 */
	int getTotalCount();

	/**
	 * 设置分页查询得出来的总记录数，与当前记录的条数无关
	 */
	void setTotalCount(int totalCount);

	/**
	 * 获取数据集元数据
	 * @return 数据集源数据
	 */
	public IDatasetMetaData getMetaData();

	/**
	 * 删除指定的行，如果指定的行不存在则会抛出BaseException
	 * @param 行索引
	 */
	public void deleteRow(int rowIndex);

}
