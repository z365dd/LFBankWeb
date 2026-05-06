package com.adtec.sys.common.utils.excel;

import com.adtec.framework.common.util.ClassUtil;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.common.utils.excel.annotation.ExcelField;
import com.adtec.sys.modules.sys.utils.DictUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 使用CVS模式解决XLSX文件，可以有效解决用户模式内存溢出的问题
 * 该模式是POI官方推荐的读取大数据的模式，在用户模式下，数据量较大、Sheet较多、或者是有很多无用的空行的情况
 * ，容易出现内存溢出,用户模式读取Excel的典型代码如下： FileInputStream file=new
 * FileInputStream("c:\\test.xlsx"); Workbook wb=new XSSFWorkbook(file);
 *
 * @author chenyl
 */
public class ExcelUtil {

	private final static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	private static File file;
	private int minColumns;
	private PrintStream output;
	private String sheetName;
	private OPCPackage xssfPackage;
	private static List<SheetBean> sheets;
	/**
	 * 工作表索引
	 */
	private int sheetIndex = -1;

	public List<SheetBean> getSheets() {
		return sheets;
	}

	/**
	 * Creates a new XLSX -> CSV converter
	 *
	 * @param p
	 *
	 * @param pkg        The XLSX package to process
	 * @param output     The PrintStream to output the CSV to
	 * @param minColumns The minimum number of columns to output, or -1 for no
	 *                   minimum
	 */
	public ExcelUtil(OPCPackage p, File uploadFile, PrintStream output, String sheetName, int sheetIndex,
					 int minColumns) {
		xssfPackage = p;
		file = uploadFile;
		this.output = output;
		this.minColumns = minColumns;
		this.sheetIndex = sheetIndex;
		this.sheetName = sheetName;
		sheets = new ArrayList<SheetBean>();
	}

	/**
	 * Parses and shows the content of one sheet using the specified styles and
	 * shared-strings tables.
	 *
	 * @param styles
	 * @param strings
	 * @param sheetInputStream
	 */
	public List<String[]> processSheet(StylesTable styles, ReadOnlySharedStringsTable strings,
									   InputStream sheetInputStream) throws IOException, ParserConfigurationException, SAXException {

		InputSource sheetSource = new InputSource(sheetInputStream);
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxFactory.newSAXParser();
		XMLReader sheetParser = saxParser.getXMLReader();
		XSSFSheetHandler handler = new XSSFSheetHandler(styles, strings, this.minColumns, this.output);
		sheetParser.setContentHandler(handler);
		sheetParser.parse(sheetSource);
		return handler.getRows();
	}

	/**
	 * 初始化这个处理程序 将
	 * @param sheetIndex
	 * @param sheetName
	 * @throws IOException
	 * @throws OpenXML4JException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	private void process() throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
		// OPCPackage p = OPCPackage.open(file, PackageAccess.READ);
		ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xssfPackage);
		XSSFReader xssfReader = new XSSFReader(this.xssfPackage);
		List<String[]> list = null;
		StylesTable styles = xssfReader.getStylesTable();
		XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
		int index = 0;
		while (iter.hasNext()) {
			InputStream stream = iter.next();
			String sheetNameTemp = iter.getSheetName();
			list = processSheet(styles, strings, stream);
			if (this.sheetIndex != -1) {
				if (this.sheetIndex == index) {
					sheets.add(new SheetBean(index, sheetNameTemp, list));
					break;
				}
			} else if (!DataUtil.isNullStr(this.sheetName)) {
				if (this.sheetName.equals(sheetNameTemp)) {
					sheets.add(new SheetBean(index, sheetNameTemp, list));
					break;
				}
			} else {
				sheets.add(new SheetBean(index, sheetNameTemp, list));
			}
			stream.close();
			index++;
		}
		// p.close();
	}

	/**
	 * The type of the data value is indicated by an attribute on the cell. The
	 * value is usually in a "v" element within the cell.
	 */
	enum xssfDataType {
		BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER,
	}

	/**
	 * 使用xssf_sax_API处理Excel,请参考：
	 * http://poi.apache.org/spreadsheet/how-to.html#xssf_sax_api
	 * <p/>
	 * Also see Standard ECMA-376, 1st edition, part 4, pages 1928ff, at
	 * http://www.ecma-international.org/publications/standards/Ecma-376.htm
	 * <p/>
	 * A web-friendly version is http://openiso.org/Ecma/376/Part4
	 */
	class XSSFSheetHandler extends DefaultHandler {

		/**
		 * Table with styles
		 */
		private StylesTable stylesTable;

		/**
		 * Table with unique strings
		 */
		private ReadOnlySharedStringsTable sharedStringsTable;

		/**
		 * Destination for data
		 */
		private final PrintStream output;

		/**
		 * Number of columns to readOnceASheet starting with leftmost
		 */
		private final int minColumnCount;

		// Set when V start element is seen
		private boolean vIsOpen;

		// Set when cell start element is seen;
		// used when cell close element is seen.
		private xssfDataType nextDataType;

		// Used to format numeric cell values.
		private short formatIndex;
		private String formatString;
		private final DataFormatter formatter;

		private int thisColumn = -1;
		// The last column printed to the output stream
		private int lastColumnNumber = -1;

		// Gathers characters as they are seen.
		private StringBuffer value;
		private String[] record;
		private List<String[]> rows = new ArrayList<String[]>();
		private boolean isCellNull = false;

		/**
		 * Accepts objects needed while parsing.
		 *
		 * @param styles  Table of styles
		 * @param strings Table of shared strings
		 * @param cols    Minimum number of columns to show
		 * @param target  Sink for output
		 */
		public XSSFSheetHandler(StylesTable styles, ReadOnlySharedStringsTable strings, int cols, PrintStream target) {
			this.stylesTable = styles;
			this.sharedStringsTable = strings;
			this.minColumnCount = cols;
			this.output = target;
			this.value = new StringBuffer();
			this.nextDataType = xssfDataType.NUMBER;
			this.formatter = new DataFormatter();
			record = new String[this.minColumnCount];
			// 每次读取前都清空行集合
			rows.clear();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
		 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */
		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {

			if ("inlineStr".equals(name) || "v".equals(name)) {
				vIsOpen = true;
				// Clear contents cache
				value.setLength(0);
			}
			// c => cell
			else if ("c".equals(name)) {
				// Get the cell reference
				// r => 单元格列坐标
				String r = attributes.getValue("r");
				int firstDigit = -1;
				for (int c = 0; c < r.length(); ++c) {
					if (Character.isDigit(r.charAt(c))) {
						firstDigit = c;
						break;
					}
				}
				thisColumn = nameToColumn(r.substring(0, firstDigit));

				// Set up defaults.
				this.nextDataType = xssfDataType.NUMBER;
				this.formatIndex = -1;
				this.formatString = null;
				String cellType = attributes.getValue("t");
				String cellStyleStr = attributes.getValue("s");
				if ("b".equals(cellType))
					nextDataType = xssfDataType.BOOL;
				else if ("e".equals(cellType))
					nextDataType = xssfDataType.ERROR;
				else if ("inlineStr".equals(cellType))
					nextDataType = xssfDataType.INLINESTR;
				else if ("s".equals(cellType))
					nextDataType = xssfDataType.SSTINDEX;
				else if ("str".equals(cellType))
					nextDataType = xssfDataType.FORMULA;
				else if (cellStyleStr != null) {
					// It's a number, but almost certainly one
					// with a special style or format
					int styleIndex = Integer.parseInt(cellStyleStr);
					XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
					this.formatIndex = style.getDataFormat();
					this.formatString = style.getDataFormatString();
					if (this.formatString == null)
						this.formatString = BuiltinFormats.getBuiltinFormat(this.formatIndex);
				}
			}

		}

		/*
		 * (non-Javadoc)
		 *
		 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
		 * java.lang.String, java.lang.String)
		 */
		@Override
		public void endElement(String uri, String localName, String name) throws SAXException {

			String thisStr = null;

			// v => contents of a cell
			if ("v".equals(name)) {
				// Process the value contents as required.
				// Do now, as characters() may be called more than once
				switch (nextDataType) {

					case BOOL:
						char first = value.charAt(0);
						thisStr = first == '0' ? "FALSE" : "TRUE";
						break;

					case ERROR:
						thisStr = "\"ERROR:" + value.toString() + '"';
						break;

					case FORMULA:
						// A formula could result in a string value,
						// so always add double-quote characters.
						thisStr = '"' + value.toString() + '"';
						break;

					case INLINESTR:
						// TODO: have seen an example of this, so it's untested.
						XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
						thisStr = '"' + rtsi.toString() + '"';
						break;

					case SSTINDEX:
						String sstIndex = value.toString();
						try {
							int idx = Integer.parseInt(sstIndex);
							XSSFRichTextString rtss = new XSSFRichTextString(sharedStringsTable.getEntryAt(idx));
							thisStr = rtss.toString();
						} catch (NumberFormatException ex) {
							output.println("Failed to parse SST index '" + sstIndex + "': " + ex.toString());
						}
						break;

					case NUMBER:
						String n = value.toString();
						// 判断是否是日期格式
						if (HSSFDateUtil.isADateFormat(this.formatIndex, n)) {
							Double d = Double.parseDouble(n);
							Date date = HSSFDateUtil.getJavaDate(d);
							thisStr = formateDateToString(date);
						} else if (this.formatString != null)
							thisStr = formatter.formatRawCellContents(Double.parseDouble(n), this.formatIndex,
									this.formatString);
						else
							thisStr = n;
						break;

					default:
						thisStr = "(TODO: Unexpected type: " + nextDataType + ")";
						break;
				}

				// Output after we've seen the string contents
				// Emit commas for any fields that were missing on this row
				if (lastColumnNumber == -1) {
					lastColumnNumber = 0;
				}
				// 判断单元格的值是否为空
				if (thisStr == null || "".equals(isCellNull)) {
					isCellNull = true;// 设置单元格是否为空值
				}
				record[thisColumn] = thisStr.trim();
				// Update column
				if (thisColumn > -1)
					lastColumnNumber = thisColumn;

			} else if ("row".equals(name)) {

				// Print out any missing commas if needed
				if (minColumns > 0) {
					// Columns are 0 based
					if (lastColumnNumber == -1) {
						lastColumnNumber = 0;
					}
					// 判断是否空行
					boolean isEmptyRow = true;
					for (int i = 0; i < record.length; i++) {
						if (record[i] != null) {
							isEmptyRow = false;
							break;
						}
					}
					if (!isEmptyRow) {
						// if (!isCellNull || StringUtils.isNotEmpty(record[0]) ||
						// StringUtils.isNotEmpty(record[1])){
						rows.add(record.clone());
						isCellNull = false;
						for (int i = 0; i < record.length; i++) {
							record[i] = null;
						}
					}
				}
				lastColumnNumber = -1;
			}

		}

		public List<String[]> getRows() {
			return rows;
		}

		public void setRows(List<String[]> rows) {
			this.rows = rows;
		}

		/**
		 * Captures characters only if a suitable element is open. Originally was just
		 * "v"; extended for inlineStr also.
		 */
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (vIsOpen) {
				value.append(ch, start, length);
			}
		}

		/**
		 * Converts an Excel column name like "C" to a zero-based index.
		 *
		 * @param name
		 * @return Index corresponding to the specified name
		 */
		private int nameToColumn(String name) {
			int column = -1;
			for (int i = 0; i < name.length(); ++i) {
				int c = name.charAt(i);
				column = (column + 1) * 26 + c - 'A';
			}
			return column;
		}

		private String formateDateToString(Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期
			return sdf.format(date);

		}

	}

	/**
	 * 读取文件 内容
	 * @param uploadFile	excel文件
	 * @param sheetName		读取的sheet名称
	 * @param minColumns	最少读取的列数，-1代表没有最少列数限制
	 * @return	List<SheetBean> 读取的shet列表
	 */
	public static List<SheetBean> readExcel(File uploadFile, String sheetName, int minColumns) {
		OPCPackage p = null;
		try {
			p = OPCPackage.open(uploadFile, PackageAccess.READ);
			if (uploadFile.getName().toLowerCase().endsWith(".xlsx")
					|| uploadFile.getName().toLowerCase().endsWith(".xlsm")) {
				ExcelUtil xlsx2csv = new ExcelUtil(p, file, System.out, sheetName, -1, minColumns);
				xlsx2csv.process();
			} else {
				logger.error("文件格式错误，fileName的扩展名只能是xlsx或xlsm。");
				throw new BaseException(SysErr.E_MESSAGE, "文件格式错误，fileName的扩展名只能是xlsx或xlsm。");
			}
		} catch (Exception e) {
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		} finally {
			if(null != p){
				try {
					p.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}

		}
		return sheets;
	}

	/**
	 * 读取excel文件
	 * @param uploadFile	excel文件
	 * @param sheetIndex	excel文件sheet 索引
	 * @param minColumns	最少读取的列数，-1代表没有最少列数限制
	 * @return	sheets		读取的shet列表
	 * @throws Exception
	 */
	public static List<SheetBean> readExcel(File uploadFile, int sheetIndex, int minColumns) throws Exception {
		OPCPackage p = null;
		try {
			p = OPCPackage.open(uploadFile, PackageAccess.READ);
			if (uploadFile.getName().toLowerCase().endsWith(".xlsx")
					|| uploadFile.getName().toLowerCase().endsWith(".xlsm")) {
				ExcelUtil xlsx2csv = new ExcelUtil(p, file, System.out, null, sheetIndex, minColumns);
				xlsx2csv.process();
			} else {
				logger.error("文件格式错误，fileName的扩展名只能是xlsx或xlsm。");
				throw new BaseException(SysErr.E_MESSAGE, "文件格式错误，fileName的扩展名只能是xlsx或xlsm。");
			}
		} catch (Exception e) {
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		} finally {
			try {
				p.close();
			} catch (IOException e) {
                System.out.println("出现异常");
            }
		}
		return sheets;
	}

	/**
	 * 读取excel文件
	 * @param uploadFile	excel文件
	 * @param minColumns	最少读取的列数，-1代表没有最少列数限制
	 * @return	sheets		读取的shet列表
	 * @throws Exception
	 */
	public static List<SheetBean> readExcel(File uploadFile, int minColumns) throws Exception {
		OPCPackage p = null;
		try {
			p = OPCPackage.open(uploadFile, PackageAccess.READ);
			if (uploadFile.getName().toLowerCase().endsWith(".xlsx")
					|| uploadFile.getName().toLowerCase().endsWith(".xlsm")) {
				ExcelUtil xlsx2csv = new ExcelUtil(p, file, System.out, null, -1, minColumns);
				xlsx2csv.process();
			} else {
				logger.error("文件格式错误，fileName的扩展名只能是xlsx或xlsm。");
				throw new BaseException(SysErr.E_MESSAGE, "文件格式错误，fileName的扩展名只能是xlsx或xlsm。");
			}
		} catch (Exception e) {
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		} finally {
			try {
				p.close();
			} catch (IOException e) {
                System.out.println("出现异常");
            }
		}
		return sheets;
	}

	/**
	 * 读取excel文件，返回数据并以指定Bean作为存储单元
	 * @param uploadFile	excel文件
	 * @param sheetName		读取的sheet名称
	 * @param minColumns	最少读取的列数，-1代表没有最少列数限制
	 * @param sheetList excel解析后的每个sheet的数据集合
	 * @param titleRow	excel中标题所在的行，从0开始计算，-1表示没有标题
	 * @param dataStart	数据开始行，从0开始计算
	 * @param dataEnd	数据结束行，从0开始计算，-1表示一直读取到结束
	 * @param cls 导入对象类型，对应class里面的属性或方法使用了@ExcelField注解
	 * @param groups 导入分组
	 * @return
	 * @throws Exception
	 */
	public static <E> List<E> readExcel2(File uploadFile, String sheetName, int minColumns,  int titleRow, int dataStart, int dataEnd, Class<E> cls, int... groups) throws Exception {
		return getDataList(readExcel(uploadFile, sheetName, minColumns), titleRow, dataStart, dataEnd, cls, groups);
	}

	/**
	 * 读取excel文件，返回数据并以指定Bean作为存储单元
	 * @param uploadFile	excel文件
	 * @param sheetIndex	excel文件sheet 索引
	 * @param minColumns	最少读取的列数，-1代表没有最少列数限制
	 * @param sheetList excel解析后的每个sheet的数据集合
	 * @param titleRow	excel中标题所在的行，从0开始计算，-1表示没有标题
	 * @param dataStart	数据开始行，从0开始计算
	 * @param dataEnd	数据结束行，从0开始计算，-1表示一直读取到结束
	 * @param cls 导入对象类型，对应class里面的属性或方法使用了@ExcelField注解
	 * @param groups 导入分组
	 * @return
	 * @throws Exception
	 */
	public static <E> List<E> readExcel2(File uploadFile, int sheetIndex, int minColumns,  int titleRow, int dataStart, int dataEnd, Class<E> cls, int... groups) throws Exception {
		return getDataList(readExcel(uploadFile, sheetIndex, minColumns), titleRow, dataStart, dataEnd, cls, groups);
	}

	/**
	 * 读取excel文件，返回数据并以指定Bean作为存储单元
	 * @param uploadFile	excel文件
	 * @param minColumns	最少读取的列数，-1代表没有最少列数限制
	 * @param sheetList excel解析后的每个sheet的数据集合
	 * @param titleRow	excel中标题所在的行，从0开始计算，-1表示没有标题
	 * @param dataStart	数据开始行，从0开始计算
	 * @param dataEnd	数据结束行，从0开始计算，-1表示一直读取到结束
	 * @param cls 导入对象类型，对应class里面的属性或方法使用了@ExcelField注解
	 * @param groups 导入分组
	 * @return
	 * @throws Exception
	 */
	public static <E> List<E> readExcel2(File uploadFile, int minColumns,  int titleRow, int dataStart, int dataEnd, Class<E> cls, int... groups) throws Exception {
		return getDataList(readExcel(uploadFile, minColumns), titleRow, dataStart, dataEnd, cls, groups);
	}

	/**
	 * 获取导入数据列表
	 * @param sheetList excel解析后的每个sheet的数据集合
	 * @param titleRow	excel中标题所在的行，从0开始计算，-1表示没有标题
	 * @param dataStart	数据开始行，从0开始计算
	 * @param dataEnd	数据结束行，从0开始计算，-1表示一直读取到结束
	 * @param cls 导入对象类型，对应class里面的属性或方法使用了@ExcelField注解
	 * @param groups 导入分组
	 */
	public static <E> List<E> getDataList(List<SheetBean> sheetList, int titleRow, int dataStart, int dataEnd, Class<E> cls, int... groups) throws InstantiationException, IllegalAccessException{
		List<Object[]> annotationList = Lists.newArrayList();
		// Get annotation field 
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs){
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type()==0 || ef.type()==2)){
				if (groups!=null && groups.length>0){
					boolean inGroup = false;
					for (int g : groups){
						if (inGroup){
							break;
						}
						for (int efg : ef.groups()){
							if (g == efg){
								inGroup = true;
								annotationList.add(new Object[]{ef, f});
								break;
							}
						}
					}
				}else{
					annotationList.add(new Object[]{ef, f});
				}
			}
		}
		// Get annotation method
		Method[] ms = cls.getDeclaredMethods();
		for (Method m : ms){
			ExcelField ef = m.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type()==0 || ef.type()==2)){
				if (groups!=null && groups.length>0){
					boolean inGroup = false;
					for (int g : groups){
						if (inGroup){
							break;
						}
						for (int efg : ef.groups()){
							if (g == efg){
								inGroup = true;
								annotationList.add(new Object[]{ef, m});
								break;
							}
						}
					}
				}else{
					annotationList.add(new Object[]{ef, m});
				}
			}
		}
		// Field sorting
		Collections.sort(annotationList, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				if (o1 == null && o2 == null) {
					return 0;
				} else if (o1 == null && o2 != null) {
					return -1;
				} else if (o1 != null && o2 == null) {
					return 1;
				}
				return new Integer(((ExcelField)o1[0]).sort()).compareTo(
						new Integer(((ExcelField)o2[0]).sort()));
			};
		});
		//log.debug("Import column count:"+annotationList.size());
		// Get excel data
		List<E> dataList = Lists.newArrayList();
		int sheetIdx = 0;
		int rowIdx = 0;
		if(null!=sheetList && !sheetList.isEmpty()){
			for(SheetBean sb:sheetList){
				if(logger.isDebugEnabled()){
					logger.debug("读取excel数据,sheetIdex["+sheetIdx+"], sheetName["+sb.getSheetName()+"], 记录行数["+sb.getRows().size()+"]");
				}
				List<String[]> rows = sb.getRows();
				String[] tileList = null;
				if(-1==dataEnd){
					dataEnd = rows.size();
				}
				for(String[] row:rows){
					if(rowIdx==titleRow){
						// 存在需要解析的标题
						tileList = row;
						if(logger.isDebugEnabled()){
							StringBuffer titleStr = new StringBuffer();
							for(String d:tileList){
								titleStr.append(d+", ");
							}
							logger.debug("读取excel的数据标题["+titleStr.toString()+"]");
						}
					}

					if(rowIdx>=dataStart && rowIdx<=dataEnd && null!=row && row.length>0){
						// 读取数据
						E e = (E)cls.newInstance();
						int column = 0;
						StringBuilder strb = new StringBuilder();
						for (Object[] os : annotationList){
							if(column>(row.length-1)){
								break;
							}
							Object val = row[column++];
							if (val != null){
								ExcelField ef = (ExcelField)os[0];
								// If is dict type, get dict value
								if (StringUtils.isNotBlank(ef.dictType())){
									val = DictUtils.getDictValue(val.toString(), ef.dictType(), "");
								}
								// Get param type and type cast
								Class<?> valType = Class.class;
								if (os[1] instanceof Field){
									valType = ((Field)os[1]).getType();
								}else if (os[1] instanceof Method){
									Method method = ((Method)os[1]);
									if ("get".equals(method.getName().substring(0, 3))){
										valType = method.getReturnType();
									}else if("set".equals(method.getName().substring(0, 3))){
										valType = ((Method)os[1]).getParameterTypes()[0];
									}
								}
								//log.debug("Import value type: ["+i+","+column+"] " + valType);
								try {
									if (valType == String.class){
										String s = String.valueOf(val.toString());
										if(StringUtils.endsWith(s, ".0")){
											val = StringUtils.substringBefore(s, ".0");
										}else{
											val = String.valueOf(val.toString());
										}
									}else if (valType == Integer.class){
										val = Double.valueOf(val.toString()).intValue();
									}else if (valType == Long.class){
										val = Double.valueOf(val.toString()).longValue();
									}else if (valType == Double.class){
										val = Double.valueOf(val.toString());
									}else if (valType == Float.class){
										val = Float.valueOf(val.toString());
									}else if (valType == Date.class){
										val = DateUtil.getJavaDate((Double)val);
									}else{
										if (ef.fieldType() != Class.class){
											val = ef.fieldType().getMethod("getValue", String.class).invoke(null, val.toString());
										}else{
											val = Class.forName(ExcelUtil.class.getName().replaceAll(ExcelUtil.class.getSimpleName(),
													"fieldtype."+valType.getSimpleName()+"Type")).getMethod("getValue", String.class).invoke(null, val.toString());
										}
									}
								} catch (Exception ex) {
									logger.warn("Get cell value ["+(rowIdx-dataStart+1)+","+column+"] error: " + ex.toString());
									val = null;
								}
								// set entity value
								if (os[1] instanceof Field){
									ClassUtil.invokeSetter(e, ((Field)os[1]).getName(), val);
								}else if (os[1] instanceof Method){
									String mthodName = ((Method)os[1]).getName();
									if ("get".equals(mthodName.substring(0, 3))){
										mthodName = "set"+StringUtils.substringAfter(mthodName, "get");
									}
									ClassUtil.invokeMethod(e, mthodName, new Class[] {valType}, new Object[] {val});
								}
							}
							strb.append(val+", ");
						}
						dataList.add(e);
						if(logger.isDebugEnabled()){
							logger.debug("Read success: ["+(rowIdx-dataStart+1)+"] "+strb.toString());
						}
					}
					rowIdx++;
				}
				sheetIdx++;
				rowIdx = 0; // 重置行下标
			}
		}
		return dataList;
	}
}