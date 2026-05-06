package com.adtec.framework.common.util;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.interfaces.share.IDataset;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.util.*;

/**
 * excel模板导出工具
 * @author chenyl
 *
 */
public class XlsPOI {
	public static final String HEAD = "HEAD";
	public static final String TABLE = "TABLE";
	public static final String END = "END";
	private static final String xlsName = ".xls";
	private static final String xlsFloder = "/export/";
	private static final String WEBINF_PATH = ParamUtil.getConfig("web.path");
	private static boolean 		bEvalFlag	=	true;	//	对公式是否进行计算替换标志，当为true时计算替换为数值，为false时为公式，不替换
	private static final String $COL$ = "$COL$";	//表达式中替换为当前列值
	private static final String $ROW$ = "$ROW$";	//表达式中替换为当前行值
	private static final String $TOTAL_PAGE$ = "$TOTAL_PAGE$";	//总页数
	private static final String $NOW_PAGE$ = "$NOW_PAGE$";	//当前页数
	
	public static boolean isbEvalFlag() {
		return bEvalFlag;
	}

	public static void setbEvalFlag(boolean bEvalFlag) {
		XlsPOI.bEvalFlag = bEvalFlag;
	}
	
	/**
	 * 模板为多个sheet，模板名：xxxx_5.xls，其中5代表模板中存在sheet个数
	 * @param xPath		生成的excel文件名的前缀
	 * @param modelPath	模板文件的全路径
	 * @param sheetList	模板文件中每个sheet的"循环开始列_循环开始行"
	 * @param sheetDataList	每个sheet文件的数据列表，每个数据项为一个Map结构数据allDataMap包含了HEAD表头“HeadMap<String,Object>”、TABLE表体“List<Object[]>”、END表尾“EndMap<String,Object>”
	 * @return
	 * @throws Exception
	 */
	public static String xlsExportWithMutiSheet(String xPath, String modelPath, List<String> sheetList, List<Map> sheetDataList) throws BaseException{
		String tarPath		=	"";
		String HB_UUID_NO	=	getOnlyStr(); 			// 取唯一号
		String tarFloder	=	WEBINF_PATH + xlsFloder;

		File 	tFloder		=	new File(tarFloder);
		if( !tFloder.exists() )
			tFloder.mkdir();
		String modelName	=	modelPath.substring(modelPath.lastIndexOf("/")+1,modelPath.lastIndexOf("."));	// 截取模板名
		
		//分析模板名,以确定xls文件内容填充的起始位置
		String[] arr 	=	 modelName.split("_");
		int sheet_num = 0;
		try{
			sheet_num	=	Integer.parseInt(arr[arr.length-1]);
		}catch(Exception e){
			throw new BaseException(SysErr.E_DEFAULT, "模板["+modelName+"]命名不规范!");
		}
		
		String tarName = arr[0]+"_"+ DateUtil.getDate()+"_"+HB_UUID_NO + xlsName;		// 生成excel文件的名称		
		String realPath		=	tarFloder + tarName;// 文件生成后存放的路径
		if(!DataUtil.isNullStr(xPath)){
			realPath = xPath;
		}
		
		if(null==sheetList){
			throw new BaseException(SysErr.E_DEFAULT, "模板文件没有设置sheet的参数!");
		}
		
		if(sheet_num!=sheetList.size()){
			throw new BaseException(SysErr.E_DEFAULT, "模板文件设置sheet的参数个数不匹配!");
		}
		
		ArrayList<Integer[]> shVarList = new ArrayList<Integer[]>();
		for(String sheetVar:sheetList){
			if(sheetVar==null || sheetVar.length()==0 || sheetVar.split("_").length!=2){
				throw new BaseException(SysErr.E_DEFAULT, "模板文件设置sheet的参数["+sheetVar+"]不符合规范!");
			}
			//col_start、row_start
			Integer[] sts = {Integer.parseInt(sheetVar.split("_")[0]),Integer.parseInt(sheetVar.split("_")[1])};
			shVarList.add(sts);
		}
//		List<Map> modelList = new ArrayList<Map>();
		// 创建EXCEL
		HSSFWorkbook 			wb	=	new HSSFWorkbook();
		// 创建部分公共变量
		HSSFFormulaEvaluator eval	=	new HSSFFormulaEvaluator(wb);
		HSSFDataFormat format		=	wb.createDataFormat();
		HSSFCellStyle	dou_CS		=	wb.createCellStyle();
		HSSFCellStyle	int_CS		=	wb.createCellStyle();
		dou_CS.setDataFormat(format.getFormat("#,##0.00;-#,##0.00"));
		int_CS.setDataFormat(format.getFormat("#,##0;-#,##0"));
		
		for(int sheetIdx=0;sheetIdx<sheet_num;sheetIdx++){
			int col_start	= 	shVarList.get(sheetIdx)[0];
			int row_start	= 	shVarList.get(sheetIdx)[1];
			Map modelMap	=	(Map)getModelMap_list(modelPath,col_start,row_start,sheetIdx);
			Map allDataMap 	= 	sheetDataList.get(sheetIdx);//每个sheet的数据内容
			
			Map<String,Object> headData = (Map<String,Object>)allDataMap.get(XlsPOI.HEAD);	//表头数据
			List<Object[]> datas = (List<Object[]>)allDataMap.get(XlsPOI.TABLE);  //表体数据     
			Map<String,Object> endData = (Map<String,Object>)allDataMap.get(XlsPOI.END);	//表尾数据
		    int	totRecNum	=	datas.size();		//	总记录数
			
			HSSFCellStyle	sour_cs		=	null;
			HSSFCellStyle	dest_cs		=	null;
			HSSFCell 		cell		=	null;
			HSSFRow 		row			=	null;
			
			// 设置字体
			HSSFFont[] fontDim		=	(HSSFFont[])modelMap.get("font");
			HSSFFont[] fontNew		=	new	HSSFFont[fontDim.length];
		//	System.out.printf("&&&&&&debug:%1$s\n", "dimnum="+fontDim.length);
			for(int i=0; i<fontDim.length; i++)
			{
				fontNew[i] 	=	wb.createFont();
				if(fontDim[i].getBold()){
					fontNew[i].setBold(true);
				}else{
					fontNew[i].setBold(false);
				}
//				fontNew[i].setBoldweight(fontDim[i].get.getBoldweight());
				fontNew[i].setCharSet(fontDim[i].getCharSet());
				fontNew[i].setColor(fontDim[i].getColor());
				fontNew[i].setFontHeight(fontDim[i].getFontHeight());
				fontNew[i].setFontHeightInPoints(fontDim[i].getFontHeightInPoints());
				fontNew[i].setFontName(fontDim[i].getFontName());
				fontNew[i].setItalic(fontDim[i].getItalic());
				fontNew[i].setStrikeout(fontDim[i].getStrikeout());
				fontNew[i].setTypeOffset(fontDim[i].getTypeOffset());
				fontNew[i].setUnderline(fontDim[i].getUnderline());
			}


			// 设置纸张大小及页边距
			Map marginMap		=	(Map)modelMap.get("marginMap");
			PrintSetup psMod	=	(PrintSetup)marginMap.get("ps");	// 获取纸张大小
		    // 取出行总数与列总数
			int	rowCount		=	(Integer)modelMap.get("rowCount");
			int	colCount		=	(Integer)modelMap.get("colCount");


			// 预先创建明细的CS数组
	    	// 取出表体结构
		    List	tableList			=	(List)modelMap.get("tableList");	// 表体结构只有一行
			HSSFCellStyle[]	detail_cs	=	new HSSFCellStyle[tableList.size()];
			Map detail_map				=	null;
	    	for(int mCount=0;mCount<tableList.size();mCount++)
	    	{
	    		detail_map				=	(Map)tableList.get(mCount);
	    		sour_cs					=	(HSSFCellStyle)detail_map.get("cs");
	    		detail_cs[mCount]		=	wb.createCellStyle();
	    		detail_cs[mCount].cloneStyleFrom(sour_cs);
	    		detail_cs[mCount].setFont(fontNew[sour_cs.getFontIndex()]);
	    	}
			
	    	// 预先创建表头的CS数组
		    List 			headList 	=	(List)modelMap.get("headList");
			HSSFCellStyle[]	head_cs		=	new HSSFCellStyle[headList.size()];
			Map 			head_map	=	null;
			//System.out.printf("555666&&&&&debug:%1$s\n", "headList.size()="+headList.size());
	    	for(int mCount=0;mCount<headList.size();mCount++)
	    	{
	    		head_map				=	(Map)headList.get(mCount);
	    		sour_cs					=	(HSSFCellStyle)head_map.get("cs");
	    		head_cs[mCount]			=	wb.createCellStyle();
	    		head_cs[mCount].cloneStyleFrom(sour_cs);
	    		head_cs[mCount].setFont(fontNew[sour_cs.getFontIndex()]);
	    	}
	    	
	    	
	    	// 预先创建表尾的CS数组
			List 			endList		=	(List)modelMap.get("endList");
			HSSFCellStyle[]	end_cs		=	new HSSFCellStyle[endList.size()];
			Map end_map					=	null;
	    	for(int mCount=0;mCount<endList.size();mCount++)
	    	{
	    		end_map					=	(Map)endList.get(mCount);
	    		sour_cs					=	(HSSFCellStyle)end_map.get("cs");
	    		end_cs[mCount]			=	wb.createCellStyle();
	    		end_cs[mCount].cloneStyleFrom(sour_cs);
	    		end_cs[mCount].setFont(fontNew[sour_cs.getFontIndex()]);
	    	}
			
	  
	    	// 取出页面边距属性
	    	Double	topmargin	=	(Double)modelMap.get("topmargin");
	    	Double	leftmargin	=	(Double)modelMap.get("leftmargin");
	    	Double	bottommargin=	(Double)modelMap.get("bottommargin");
	    	Double	rightmargin	=	(Double)modelMap.get("rightmargin");
	    	Boolean	hori		=	(Boolean)modelMap.get("hori");
	    	Boolean	verti		=	(Boolean)modelMap.get("verti");
	    	
	    	
			List		objList	=	null;
			Iterator	objIt	=	null;

		    int		curr_rec_num=	0;		//	当前记录行数
		    String sheetName = (String)modelMap.get("sheetName");
			System.out.printf("info:%1$s\n", "sheetName="+sheetName ,"totRecNum: "+totRecNum);

			HSSFSheet sheet		=	wb.createSheet(sheetName);
				
			// 页边距设置
			sheet.setMargin(HSSFSheet.TopMargin, topmargin.doubleValue());
			sheet.setMargin(HSSFSheet.LeftMargin, leftmargin.doubleValue());
			sheet.setMargin(HSSFSheet.BottomMargin, bottommargin.doubleValue());
			sheet.setMargin(HSSFSheet.RightMargin, rightmargin.doubleValue());
			sheet.setHorizontallyCenter(hori.booleanValue());
			sheet.setVerticallyCenter(verti.booleanValue());
				
			// 打印页设置
			PrintSetup psNew	=	sheet.getPrintSetup();
			psNew.setPaperSize(psMod.getPaperSize());
			psNew.setDraft(psMod.getDraft());
			psNew.setFitHeight(psMod.getFitHeight());
			psNew.setFitWidth(psMod.getFitWidth());
			psNew.setFooterMargin(psMod.getFooterMargin());
			psNew.setHeaderMargin(psMod.getHeaderMargin());
			psNew.setLandscape(psMod.getLandscape());
			psNew.setLeftToRight(psMod.getLeftToRight());
			psNew.setNoOrientation(psMod.getNoOrientation());
			psNew.setUsePage(psMod.getUsePage());
			psNew.setValidSettings(psMod.getValidSettings());
			psNew.setPageStart(psMod.getPageStart());
			psNew.setCopies(psMod.getCopies());
			psNew.setScale(psMod.getScale());
			psNew.setHResolution(psMod.getHResolution());
			psNew.setNoColor(psMod.getNoColor());
			psNew.setVResolution(psMod.getVResolution());
			psNew.setNotes(psMod.getNotes());

			// 创建所有行及列
			for(int i=0; i<rowCount+totRecNum; i++)
			{
			    row		=	sheet.createRow(i);
			    for(int j=0; j<colCount; j++){
			    	row.createCell(j);
			    }
			}
				
			//	表头合并单元格
			List hmList		=	(List)modelMap.get("hmList");	//	获取要合并的单元格
			int hmLen		=	hmList.size();
		    // 表头合并单元格
			int rowM,colM,rowM2,colM2;
			Map hmMap 		=	null;
		    for(int i=0;i<hmLen;i++)
		    {
		    	hmMap	=	(Map)hmList.get(i);
			    rowM	=	(Integer)hmMap.get("row");
			    colM	=	(Integer)hmMap.get("col");
			    rowM2	=	(Integer)hmMap.get("row2");
			    colM2	=	(Integer)hmMap.get("col2");
			    	
			    System.out.printf("info:%1$s\n", "表头合并单元格,rowM="+rowM+",colM="+colM+",rowM2="+rowM2+",colM2="+colM2);
			    try {
					sheet.addMergedRegion(new CellRangeAddress(rowM, rowM2, colM, colM2));
				} catch (Exception e) {
					e.printStackTrace();
					throw new BaseException(SysErr.E_DEFAULT, "合并表头单元格出现异常!");
				} 
					
			    // 用第一行的CS设置单元格格式，保证边框可以加上 
				int		mCount		=	0;
			    for( ;mCount<headList.size();mCount++ )
			    {
			    	head_map				=	(Map)headList.get(mCount);
			    	if( rowM==(Integer)head_map.get("row") && colM==(Integer)head_map.get("col") )
			    	{
			    		break;
			    	}
			    }
			    //	System.out.printf("info:%1$s", "表头设置合并单元格各个cell格式,mCount="+mCount);
			    for(int j=rowM; j<=rowM2; j++)
			    {
			    	HSSFRow		tmpRow		=	sheet.getRow(j);
			    	for(int k=colM; k<=colM2; k++)
			    	{
			    		HSSFCell	tmpCell	=	tmpRow.getCell(k);
			    		tmpCell.setCellStyle(head_cs[mCount]);
			    	}
			    }

			   }
		    	
			 // 处理表头内容
			 for(int hr=0;hr<headList.size();hr++)
			 {
				Map 	hrMap	=	(Map)headList.get(hr);
				int		iRow	=	(Integer)hrMap.get("row");
				int		iCol	=	(Integer)hrMap.get("col");
				String	strVal	=	(String)hrMap.get("s");
		    	if( null==strVal || "".equals(strVal) ){
		    		continue;
		    	}

		    	// 取出单元格
				row			=	sheet.getRow(iRow);
				cell		=	row.getCell(iCol);
					
				int cellType=	judge_CellType(strVal);
				//System.out.printf("info:%1$s\n", "表头，iRow="+iRow+",iCol="+iCol+",cellType="+cellType+",strVal=["+strVal+"]");

				short font_ind	=	head_cs[hr].getFontIndex();
				if( 2==cellType )	// 如果是表达式，生成表达式处理
				{
					String	expr_name	=	splitExprName(cell, strVal);
					cell.setCellFormula(expr_name);
					if( isbEvalFlag() )
					{
						eval.evaluateInCell(cell);
						cell.setCellType(CellType.NUMERIC);
					}
					else{
						cell.setCellType(CellType.FORMULA);
					}
						
					head_cs[hr].setDataFormat(dou_CS.getDataFormat());
					cell.setCellStyle(head_cs[hr]);
				}
				else if( 5==cellType ) // 如果是[_&& &&_]合并数据元素的暂时不处理
				{

				}
				else				// 如果是数据元素或者常量文本
				{
					String	labelVal	=	strVal;
					if( 1==cellType )	//	如果是数据元素
					{
						String	elem_name  =	splitElemName(strVal);
						String  elem_value =    getElemVal(elem_name,headData,cellType);
						labelVal		   =	replaceElemName(strVal,elem_value);
					}
					if( 3==cellType )	//	如果是数字数据元素
					{
						String	elem_name	=	splitNumName(strVal);
						String  elem_value  =   getElemVal(elem_name,headData,cellType);
						labelVal			=	replaceNumName(strVal,elem_value);
					}
					if( 4==cellType )	//	如果是整型数据元素
					{
						String	elem_name	=	splitIntName(strVal);
						String  elem_value  =   getElemVal(elem_name,headData,cellType);
						labelVal			=	replaceIntName(strVal,elem_value);
					}

					//System.out.printf("info:%1$s", "表头，currPageNum="+currPageNum+",labelVal=["+labelVal+"]");
					dest_cs		=	wb.createCellStyle();
					if( 3==cellType || 4==cellType )
					{
						if( 3==cellType )
						{
							head_cs[hr].setDataFormat(dou_CS.getDataFormat());
						}
						else
						{
							head_cs[hr].setDataFormat(int_CS.getDataFormat());
						}
						head_cs[hr].setFont(wb.getFontAt(font_ind));
						cell.setCellStyle(head_cs[hr]);
						cell.setCellValue(Double.parseDouble(labelVal));
						cell.setCellType(CellType.NUMERIC);
					}
					else
					{
						//	System.out.printf("5555&&&&&&debug:%1$s", "size="+wb.getFontAt(head_cs[hr].getFontIndex()).getFontHeightInPoints());
						cell.setCellStyle(head_cs[hr]);
						if( null==labelVal || "".equals(labelVal.trim()) ){
							labelVal	=	" ";
						}

						cell.setCellValue(labelVal);
					}
				}
			}

		    	//	表体合并单元格,添加单元格的处理放在处理表体中
				List tmList		=	(List)modelMap.get("tmList");
			    int tmLen		=	tmList.size();
			    int rowT,colT,rowT2,colT2;
			    Map tmMap		=	new HashMap();
		    				
		    	
		    	//	处理表体内容
				objList = getDatas(datas,0,datas.size()-1);

			    objIt =	objList.iterator();				
			    int currpg_rec_num	=	0;		//	当前页当前记录数
			    while(objIt.hasNext())
			    {	
			    	/*增加合并单元格*/
			    	for(int i=0;i<tmLen;i++)
			    	{
			    		tmMap	=	(Map)tmList.get(i);
			    		rowT	=	(Integer)tmMap.get("row");
			    		colT	=	(Integer)tmMap.get("col");
				    	rowT2	=	(Integer)tmMap.get("row2");
				    	colT2	=	(Integer)tmMap.get("col2");
				    //	System.out.printf("info:%1$s\n", "表体合并单元格,rowT="+(rowT+currpg_rec_num)+",colT="+colT+",rowT2="+(rowT2+currpg_rec_num)+",colT2="+colT2);
				    	try {
				    		sheet.addMergedRegion(new CellRangeAddress(rowT+currpg_rec_num, rowT2+currpg_rec_num, colT, colT2));
						} catch (Exception e) {
							e.printStackTrace();
							throw new BaseException(SysErr.E_DEFAULT, "合并表体单元格出现异常!");
						}
						
				    	// 用第一行的CS设置单元格格式，保证边框可以加上
						int		mCount		=	0;
				    	for( ;mCount<tableList.size();mCount++ )
				    	{
				    		detail_map		=	(Map)tableList.get(mCount);
				    		if( rowT==(Integer)detail_map.get("row") && colT==(Integer)detail_map.get("col") )
				    		{
				    			break;
				    		}
				    	}
				    //	System.out.printf("info:%1$s\n", "表体设置合并单元格各个cell格式,mCount="+mCount);
				    	for(int j=(rowT+currpg_rec_num); j<=(rowT2+currpg_rec_num); j++)
				    	{
				    		HSSFRow		tmpRow		=	sheet.getRow(j);
				    		for(int k=colT; k<=colT2; k++)
				    		{
				    			HSSFCell	tmpCell	=	tmpRow.getCell(k);
				    			tmpCell.setCellStyle(detail_cs[mCount]);
				    		}
				    	}
				    }
			    	
					//	判断文件中是否还有记录
					Map dataMap			=	null;		    		
			    	dataMap	=	(Map)objIt.next();
			    	
			    	Map 	tMapB		=	null;
			    	String	cellValue	=	"";
			    	// 取出当前行
					row					=	sheet.getRow(row_start+currpg_rec_num);
			    	
			    	for(int mCount=0;mCount<tableList.size();mCount++)
			    	{
			    		tMapB			=	(Map)tableList.get(mCount);
						int		iRow	=	(Integer)tMapB.get("row");
						int		iCol	=	(Integer)tMapB.get("col");

						// 取出明细数据
						cellValue	=	(String)tMapB.get("s");
						if( null==cellValue || "".equals(cellValue) )
							cellValue	=	" ";
						
						// 判断是常量，表达式，还是文件中的数据元素
						int cellType=	judge_CellType(cellValue);
					//	System.out.printf("info:%1$s\n", "表体，cellType="+cellType+",cellValue="+cellValue+",iRow="+(iRow+currpg_rec_num)+",iCol="+iCol+",curr_rec_num="+(curr_rec_num+1));


						// 设置单元格格式
						short font_ind	=	detail_cs[mCount].getFontIndex();
						cell			=	row.getCell(iCol);
						if( (3==cellType || 4==cellType ) && 1==curr_rec_num )
						{
							if( 3==cellType )
							{
								detail_cs[mCount].setDataFormat(dou_CS.getDataFormat());
							}
							else
							{
								detail_cs[mCount].setDataFormat(int_CS.getDataFormat());
							}
							detail_cs[mCount].setFont(wb.getFontAt(font_ind));
							cell.setCellType(CellType.NUMERIC);
						}
						else
						{
							cell.setCellStyle(detail_cs[mCount]);
						}

						// 对单元格进行设置值的处理
						if( 2==cellType ) 					// 如果是表达式，生成表达式处理
						{
							String	expr_name	=	splitExprName(cell, cellValue);
							cell.setCellFormula(expr_name);
							if( isbEvalFlag() )
							{
								eval.evaluateInCell(cell);
								cell.setCellType(CellType.NUMERIC);
							}
							else
								cell.setCellType(CellType.FORMULA);

							detail_cs[mCount].setFont(wb.getFontAt(font_ind));
							detail_cs[mCount].setDataFormat(dou_CS.getDataFormat());
							cell.setCellStyle(detail_cs[mCount]);
						}
						else		//	如果是数据元素或者常量文本
						{
							String	resultCellValue	=	cellValue;
							if( 1==cellType || 3==cellType || 4==cellType )	// 如果是数据元素(文本型或者数字型)
							{
								String	elem_name	=	"";
								if( 1==cellType ){
									elem_name		=	splitElemName(cellValue);
								}
								else
								{		// 3 or 4
									if( 3==cellType )
										elem_name		=	splitNumName(cellValue);
									else
										elem_name		=	splitIntName(cellValue);
								}
								
								String  elem_value  =   " ";
								//	判断是否从datalist中取还是从对象属性中取中取
								if( iCol<col_start )	//	不是从列表中取数据
								{
									elem_value = getElemVal(elem_name,allDataMap,cellType);
								}
								else					//	从列表中取数据
								{
									elem_value = (String)dataMap.get(elem_name);
									
						    		if( null==elem_value || "".equals(elem_value.trim()) )
						    		{
						    			if( 1==cellType)
						    				elem_value	=	" ";
						    			else if( 3==cellType)
						    				elem_value	=	"0.00";
						    			else
						    				elem_value	=	"0";
						    		}
								}							
								
								//	替换数据元素
								if( 1==cellType )
									resultCellValue	=	replaceElemName(cellValue,elem_value);
								else if( 3==cellType )
									resultCellValue	=	replaceNumName(cellValue,elem_value);
								else
									resultCellValue	=	replaceIntName(cellValue,elem_value);
									
							}
							
							//System.out.printf("info:%1$s\n", "表体，iCol="+iCol+",currpg_rec_num="+currpg_rec_num+",resultCellValue=["+resultCellValue+"]");
							if( 3==cellType || 4==cellType )	// 数字(金额)型或整型
								cell.setCellValue(Double.parseDouble(resultCellValue));
							else				// 文本型
								cell.setCellValue(resultCellValue);
						}
						
			    	}	// end for 完成一行明细的生成		    	
			    	currpg_rec_num++;
			    	if(false==objIt.hasNext() ) 	//	跳出明细处理
		    			break;
			    }	// end while 完成一页明细的生成		    	
			
		    	//	表尾合并单元格
				List 	emList	=	(List)modelMap.get("emList");
			    int		emLen	=	emList.size();
			    int rowE,colE,rowE2,colE2;
			    Map emMap = null;
		    	for(int i=0;i<emLen;i++)
		    	{
		    		emMap	=	(Map)emList.get(i);
			    	rowE	=	(Integer)emMap.get("row");
			    	colE	=	(Integer)emMap.get("col");
			    	rowE2	=	(Integer)emMap.get("row2");
			    	colE2	=	(Integer)emMap.get("col2");
			    	//System.out.printf("info:%1$s\n", "表尾合并单元格,rowE="+(rowE+currpg_rec_num)+",colE="+colE+",rowE2="+(rowE2+currpg_rec_num)+",colE2="+colE2);
			    	try {
			    		sheet.addMergedRegion(new CellRangeAddress(rowE+currpg_rec_num, rowE2+currpg_rec_num, colE, colE2));
					} catch (Exception e) {
						e.printStackTrace();
						throw new BaseException(SysErr.E_DEFAULT, "合并表尾单元格出现异常!");
					} 
					
			    	// 用第一行的CS设置单元格格式，保证边框可以加上
					int		mCount		=	0;
			    	for( ;mCount<headList.size();mCount++ )
			    	{
			    		end_map				=	(Map)endList.get(mCount);
			    		if( rowE==(Integer)end_map.get("row") && colE==(Integer)end_map.get("col") )
			    		{
			    			break;
			    		}
			    	}
			    	//System.out.printf("info:%1$s\n", "表尾设置合并单元格各个cell格式,mCount="+mCount);
			    	for(int j=rowE+currpg_rec_num; j<=rowE2+currpg_rec_num; j++)
			    	{
			    		HSSFRow		tmpRow		=	sheet.getRow(j);
			    		for(int k=colE; k<=colE2; k++)
			    		{
			    			HSSFCell	tmpCell	=	tmpRow.getCell(k);
			    			tmpCell.setCellStyle(end_cs[mCount]);
			    		}
			    	}
			    }
		    	
				//	设置表尾
				Map		erMap	=	new HashMap();
				for(int lr=0;lr<endList.size();lr++)
				{
					erMap			=	(Map)endList.get(lr);
					int		iRow	=	(Integer)erMap.get("row");
					int		iCol	=	(Integer)erMap.get("col");
					String	strVal	=	(String)erMap.get("s");
		    		if( null==strVal || "".equals(strVal) )
		    			continue;

		    		// 取出单元格
					row			=	sheet.getRow(iRow+currpg_rec_num);
					cell		=	row.getCell(iCol);
			    		
					int cellType=	judge_CellType(strVal);
					//System.out.printf("info:%1$s", "表尾，iRow="+(iRow+currpg_rec_num)+",iCol="+iCol+",cellType="+cellType+",strVal=["+strVal+"]");

					short font_ind	=	end_cs[lr].getFontIndex();
					if( 2==cellType )	// 如果是表达式，生成表达式处理
					{
						String	expr_name	=	splitExprName(cell, strVal);
						cell.setCellFormula(expr_name);
						System.out.printf("info:%1$s\n", "表尾，iRow="+(iRow+currpg_rec_num)+",iCol="+iCol+",cellType="+cellType+",expr_name=["+expr_name+"]");
						if( isbEvalFlag() )
						{
							eval.evaluateInCell(cell);
							cell.setCellType(CellType.NUMERIC);
						}
						else{
							cell.setCellType(CellType.FORMULA);
						}
						
						end_cs[lr].setDataFormat(dou_CS.getDataFormat());
						cell.setCellStyle(end_cs[lr]);
					}
					else if( 5==cellType ) // 如果是本页小计
					{
						
					}
					else				// 如果是数据元素或者常量文本
					{
						String	labelVal	=	strVal;
						if( 1==cellType )	//	如果是数据元素
						{
							String	elem_name	=	splitElemName(strVal);
							String elem_value   =   getElemVal(elem_name,endData,cellType);
							labelVal			=	replaceElemName(strVal,elem_value);
						}
						if( 3==cellType )	//	如果是数字数据元素
						{
							String	elem_name	=	splitNumName(strVal);
							String elem_value   =   getElemVal(elem_name,endData,cellType);
							labelVal			=	replaceNumName(strVal,elem_value);
						}
						if( 4==cellType )	//	如果是整型数据元素
						{
							String	elem_name	=	splitNumName(strVal);
							String elem_value   =   getElemVal(elem_name,endData,cellType);
							labelVal			=	replaceNumName(strVal,elem_value);
						}
						
						// 把内容设置到单元格中
						if( 3==cellType || 4==cellType )
						{
							if( 3==cellType )
								end_cs[lr].setDataFormat(dou_CS.getDataFormat());
							else
								end_cs[lr].setDataFormat(int_CS.getDataFormat());

							end_cs[lr].setFont(wb.getFontAt(font_ind));
							cell.setCellStyle(end_cs[lr]);
							cell.setCellValue(Double.parseDouble(labelVal));
							cell.setCellType(CellType.NUMERIC);
						}
						else
						{
							cell.setCellStyle(end_cs[lr]);
							if( null==labelVal || "".equals(labelVal.trim()) )
								labelVal	=	" ";
							
							cell.setCellValue(labelVal);
						}
					}
				}
				// 设置表尾结束

				// 设置列宽及列隐藏属性
				Integer[] colWidth	=	(Integer[])modelMap.get("colWidth");
				
				for(int i=0; i<colWidth.length; i++)
				{
					sheet.setColumnWidth(i, colWidth[i]);
				}
				
							
				// 设置行高
				Integer[] rowHeight	=	(Integer[])modelMap.get("rowHeight");
				for(int i=0; i<rowHeight.length; i++)
				{
					row		=	sheet.getRow(i);
				}
		}
		
		// 写文件
        OutputStream out = null;
		try {
			out = new FileOutputStream(realPath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			throw new BaseException(SysErr.E_DEFAULT, "初始化文件流[" + realPath + "]时出现异常!");
		}
		System.out.printf("info:%1$s", realPath+" before write! ");
		try {
			wb.write(out);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("errmsg=" + e.getStackTrace());
			System.out.printf("info:%1$s\n", "errmsg=" + e.getMessage());
			throw new BaseException(SysErr.E_DEFAULT, "生成excel出现异常!");
		} finally {
			if (null != wb) {
				try {
					wb.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}

		}
		
		return realPath;
	}

	// 生成清单excel文件
	// add by chenyl on 2014.2.12
	// 根据数据allDataMap包含了HEAD表头“HeadMap<String,Object>”、TABLE表体“List<Object[]>”、END表尾“EndMap<String,Object>”
	// 模板名919015_1_5_10.xls，分别为清单体的开始列、开始行、每页行数(如果为0则选用默认值10000行一个sheet)

	public static String xlsExportWithPageCut(String xPath,String modelPath,Map allDataMap) throws BaseException
	{
		
		String tarPath		=	"";
		String HB_UUID_NO	=	getOnlyStr(); 			// 取唯一号
		String tarFloder	=	WEBINF_PATH + xlsFloder;

		File 	tFloder		=	new File(tarFloder);
		if( !tFloder.exists() )
			tFloder.mkdir();
		String modelName	=	modelPath.substring(modelPath.lastIndexOf("/")+1,modelPath.lastIndexOf("."));	// 截取模板名
		
		//分析模板名,以确定xls文件内容填充的起始位置
		String[] arr 	=	 modelName.split("_");
		int col_start	= 	0;
		int row_start	= 	0;
		int page_cut    =   0;
		
		try{
			col_start	=	Integer.parseInt(arr[arr.length-3]);
			row_start	=	Integer.parseInt(arr[arr.length-2]);
			page_cut	=	Integer.parseInt(arr[arr.length-1]);
		}catch(Exception e){
			System.out.println("模板["+modelName+"]命名不规范!");
		}
		
		String tarName = arr[0]+"_"+ DateUtil.getDate()+"_"+HB_UUID_NO + xlsName;		// 生成excel文件的名称		
		String realPath		=	tarFloder + tarName;// 文件生成后存放的路径
		if(!DataUtil.isNullStr(xPath)){
			realPath = xPath;
		}
		
		if(page_cut==0){
			page_cut = 10000;
		}
		Map modelMap		=	(Map)getModelMap_list(modelPath,col_start,row_start);
		
		Map<String,Object> headData = (Map<String,Object>)allDataMap.get(XlsPOI.HEAD);	//表头数据
		List<Object[]> datas = (List<Object[]>)allDataMap.get(XlsPOI.TABLE);  //表体数据     
		Map<String,Object> endData = (Map<String,Object>)allDataMap.get(XlsPOI.END);	//表尾数据
	    int	totRecNum	=	datas.size();		//	总记录数
		// 创建EXCEL
		HSSFWorkbook 			wb	=	new HSSFWorkbook();
		
		// 创建部分公共变量
		HSSFFormulaEvaluator eval	=	new HSSFFormulaEvaluator(wb);
		HSSFDataFormat format		=	wb.createDataFormat();
		HSSFCellStyle	dou_CS		=	wb.createCellStyle();
		HSSFCellStyle	int_CS		=	wb.createCellStyle();
		dou_CS.setDataFormat(format.getFormat("#,##0.00;-#,##0.00"));
		int_CS.setDataFormat(format.getFormat("#,##0;-#,##0"));
		HSSFCellStyle	sour_cs		=	null;
		HSSFCellStyle	dest_cs		=	null;
		HSSFCell 		cell		=	null;
		HSSFRow 		row			=	null;
		
		// 设置字体
		HSSFFont[] fontDim		=	(HSSFFont[])modelMap.get("font");
		HSSFFont[] fontNew		=	new	HSSFFont[fontDim.length];
	//	System.out.printf("&&&&&&debug:%1$s\n", "dimnum="+fontDim.length);
		for(int i=0; i<fontDim.length; i++)
		{
			fontNew[i] 	=	wb.createFont();
			if(fontDim[i].getBold()){
				fontNew[i].setBold(true);
			}else{
				fontNew[i].setBold(false);
			}
//			fontNew[i].setBoldweight(fontDim[i].getBoldweight());
			fontNew[i].setCharSet(fontDim[i].getCharSet());
			fontNew[i].setColor(fontDim[i].getColor());
			fontNew[i].setFontHeight(fontDim[i].getFontHeight());
			fontNew[i].setFontHeightInPoints(fontDim[i].getFontHeightInPoints());
			fontNew[i].setFontName(fontDim[i].getFontName());
			fontNew[i].setItalic(fontDim[i].getItalic());
			fontNew[i].setStrikeout(fontDim[i].getStrikeout());
			fontNew[i].setTypeOffset(fontDim[i].getTypeOffset());
			fontNew[i].setUnderline(fontDim[i].getUnderline());
		}


		// 设置纸张大小及页边距
		Map marginMap		=	(Map)modelMap.get("marginMap");
		PrintSetup psMod	=	(PrintSetup)marginMap.get("ps");	// 获取纸张大小
	    // 取出行总数与列总数
		int	rowCount		=	(Integer)modelMap.get("rowCount");
		int	colCount		=	(Integer)modelMap.get("colCount");


		// 预先创建明细的CS数组
    	// 取出表体结构
	    List	tableList			=	(List)modelMap.get("tableList");	// 表体结构只有一行
		HSSFCellStyle[]	detail_cs	=	new HSSFCellStyle[tableList.size()];
		Map detail_map				=	null;
    	for(int mCount=0;mCount<tableList.size();mCount++)
    	{
    		detail_map				=	(Map)tableList.get(mCount);
    		sour_cs					=	(HSSFCellStyle)detail_map.get("cs");
    		detail_cs[mCount]		=	wb.createCellStyle();
    		detail_cs[mCount].cloneStyleFrom(sour_cs);
    		detail_cs[mCount].setFont(fontNew[sour_cs.getFontIndex()]);
    	}
		
    	// 预先创建表头的CS数组
	    List 			headList 	=	(List)modelMap.get("headList");
		HSSFCellStyle[]	head_cs		=	new HSSFCellStyle[headList.size()];
		Map 			head_map	=	null;
		//System.out.printf("555666&&&&&debug:%1$s\n", "headList.size()="+headList.size());
    	for(int mCount=0;mCount<headList.size();mCount++)
    	{
    		head_map				=	(Map)headList.get(mCount);
    		sour_cs					=	(HSSFCellStyle)head_map.get("cs");
    		head_cs[mCount]			=	wb.createCellStyle();
    		head_cs[mCount].cloneStyleFrom(sour_cs);
    		head_cs[mCount].setFont(fontNew[sour_cs.getFontIndex()]);
    	}
    	
    	
    	// 预先创建表尾的CS数组
		List 			endList		=	(List)modelMap.get("endList");
		HSSFCellStyle[]	end_cs		=	new HSSFCellStyle[endList.size()];
		Map end_map					=	null;
    	for(int mCount=0;mCount<endList.size();mCount++)
    	{
    		end_map					=	(Map)endList.get(mCount);
    		sour_cs					=	(HSSFCellStyle)end_map.get("cs");
    		end_cs[mCount]			=	wb.createCellStyle();
    		end_cs[mCount].cloneStyleFrom(sour_cs);
    		end_cs[mCount].setFont(fontNew[sour_cs.getFontIndex()]);
    	}
		
  
    	// 取出页面边距属性
    	Double	topmargin	=	(Double)modelMap.get("topmargin");
    	Double	leftmargin	=	(Double)modelMap.get("leftmargin");
    	Double	bottommargin=	(Double)modelMap.get("bottommargin");
    	Double	rightmargin	=	(Double)modelMap.get("rightmargin");
    	Boolean	hori		=	(Boolean)modelMap.get("hori");
    	Boolean	verti		=	(Boolean)modelMap.get("verti");
    	
    	
		List		objList	=	null;
		Iterator	objIt	=	null;

	    int		curr_rec_num=	0;		//	当前记录行数
    	int 	currPageNum	=	1;		//	当前页数
    	int     totPageNum	=	(totRecNum+page_cut-1)/page_cut;
    	//表体没有数据时，执行一次
    	if(totRecNum==0){
    		totPageNum=1;
    	}
		for(;currPageNum<=totPageNum;currPageNum++)
		{
			System.out.printf("info:%1$s\n", "currPageNum: "+currPageNum);

			HSSFSheet sheet		=	wb.createSheet("第"+currPageNum+"页");
			
			// 页边距设置
			sheet.setMargin(HSSFSheet.TopMargin, topmargin.doubleValue());
			sheet.setMargin(HSSFSheet.LeftMargin, leftmargin.doubleValue());
			sheet.setMargin(HSSFSheet.BottomMargin, bottommargin.doubleValue());
			sheet.setMargin(HSSFSheet.RightMargin, rightmargin.doubleValue());
			sheet.setHorizontallyCenter(hori.booleanValue());
			sheet.setVerticallyCenter(verti.booleanValue());
			
			// 打印页设置
			PrintSetup psNew	=	sheet.getPrintSetup();
			psNew.setPaperSize(psMod.getPaperSize());
			psNew.setDraft(psMod.getDraft());
			psNew.setFitHeight(psMod.getFitHeight());
			psNew.setFitWidth(psMod.getFitWidth());
			psNew.setFooterMargin(psMod.getFooterMargin());
			psNew.setHeaderMargin(psMod.getHeaderMargin());
			psNew.setLandscape(psMod.getLandscape());
			psNew.setLeftToRight(psMod.getLeftToRight());
			psNew.setNoOrientation(psMod.getNoOrientation());
			psNew.setUsePage(psMod.getUsePage());
			psNew.setValidSettings(psMod.getValidSettings());
			psNew.setPageStart(psMod.getPageStart());
			psNew.setCopies(psMod.getCopies());
			psNew.setScale(psMod.getScale());
			psNew.setHResolution(psMod.getHResolution());
			psNew.setNoColor(psMod.getNoColor());
			psNew.setVResolution(psMod.getVResolution());
			psNew.setNotes(psMod.getNotes());

		//	System.out.printf("PrintSetup:%1$s\n", "getPaperSize="+psNew.getPaperSize()+",pagestart="+psNew.getPageStart()+
		 //   ",FooterMargin="+psNew.getFooterMargin()+",HeaderMargin="+psNew.getHeaderMargin());

			// 创建所有行及列
			int needRow = 0;
			if(currPageNum*page_cut<=totRecNum){
				needRow = page_cut;
			}else{
				needRow = currPageNum*page_cut - totRecNum +1;
			}
		    for(int i=0; i<rowCount+needRow; i++)
		    {
		    	row		=	sheet.createRow(i);
		    	for(int j=0; j<colCount; j++)
		    		row.createCell(j);
		    }
			
			//	表头合并单元格
			List hmList		=	(List)modelMap.get("hmList");	//	获取要合并的单元格
		    int hmLen		=	hmList.size();
	        // 表头合并单元格
		    int rowM,colM,rowM2,colM2;
		    Map hmMap 		=	null;
	    	for(int i=0;i<hmLen;i++)
	    	{
	    		hmMap	=	(Map)hmList.get(i);
		    	rowM	=	(Integer)hmMap.get("row");
		    	colM	=	(Integer)hmMap.get("col");
		    	rowM2	=	(Integer)hmMap.get("row2");
		    	colM2	=	(Integer)hmMap.get("col2");
		    	
		    	System.out.printf("info:%1$s\n", "表头合并单元格,rowM="+rowM+",colM="+colM+",rowM2="+rowM2+",colM2="+colM2);
		    	try {
					sheet.addMergedRegion(new CellRangeAddress(rowM, rowM2, colM, colM2));
				} catch (Exception e) {
					e.printStackTrace();
					throw new BaseException(SysErr.E_DEFAULT, "合并表头单元格出现异常!");
				} 
				
		    	// 用第一行的CS设置单元格格式，保证边框可以加上 
				int		mCount		=	0;
		    	for( ;mCount<headList.size();mCount++ )
		    	{
		    		head_map				=	(Map)headList.get(mCount);
		    		if( rowM==(Integer)head_map.get("row") && colM==(Integer)head_map.get("col") )
		    		{
		    			break;
		    		}
		    	}
		    //	System.out.printf("info:%1$s", "表头设置合并单元格各个cell格式,mCount="+mCount);
		    	for(int j=rowM; j<=rowM2; j++)
		    	{
		    		HSSFRow		tmpRow		=	sheet.getRow(j);
		    		for(int k=colM; k<=colM2; k++)
		    		{
		    			HSSFCell	tmpCell	=	tmpRow.getCell(k);
		    			tmpCell.setCellStyle(head_cs[mCount]);
		    		}
		    	}

		    }
	    	
		    // 处理表头内容
		    for(int hr=0;hr<headList.size();hr++)
		    {
				Map 	hrMap	=	(Map)headList.get(hr);
				int		iRow	=	(Integer)hrMap.get("row");
				int		iCol	=	(Integer)hrMap.get("col");
				String	strVal	=	(String)hrMap.get("s");
	    		if( null==strVal || "".equals(strVal) )
	    			continue;

	    		// 取出单元格
				row			=	sheet.getRow(iRow);
				cell		=	row.getCell(iCol);
				
				int cellType=	judge_CellType(strVal);
				//System.out.printf("info:%1$s\n", "表头，iRow="+iRow+",iCol="+iCol+",cellType="+cellType+",strVal=["+strVal+"]");

				short font_ind	=	head_cs[hr].getFontIndex();
				if( 2==cellType )	// 如果是表达式，生成表达式处理
				{
					String	expr_name	=	splitExprName(cell, strVal);
					cell.setCellFormula(expr_name);
					if( isbEvalFlag() )
					{
						eval.evaluateInCell(cell);
						cell.setCellType(CellType.NUMERIC);
					}
					else{
						cell.setCellType(CellType.FORMULA);
					}
					
					head_cs[hr].setDataFormat(dou_CS.getDataFormat());
					cell.setCellStyle(head_cs[hr]);
				}
				else if( 5==cellType ) // 如果是[_&& &&_]合并数据元素的暂时不处理
				{

				}
				else				// 如果是数据元素或者常量文本
				{
					String	labelVal	=	strVal;
					if( 1==cellType )	//	如果是数据元素
					{
						String	elem_name  =	splitElemName(strVal);
						String  elem_value =    getElemVal(elem_name,headData,cellType);
						//处理当前页数、总页数
						if($NOW_PAGE$.equals(elem_name)){
							elem_value = ""+currPageNum;
						}else if($TOTAL_PAGE$.equals(elem_name)){
							elem_value = ""+totPageNum;
						}
						labelVal		   =	replaceElemName(strVal,elem_value);
					}
					if( 3==cellType )	//	如果是数字数据元素
					{
						String	elem_name	=	splitNumName(strVal);
						String  elem_value  =   getElemVal(elem_name,headData,cellType);
						labelVal			=	replaceNumName(strVal,elem_value);
					}
					if( 4==cellType )	//	如果是整型数据元素
					{
						String	elem_name	=	splitIntName(strVal);
						String  elem_value  =   getElemVal(elem_name,headData,cellType);
						labelVal			=	replaceIntName(strVal,elem_value);
					}

					//System.out.printf("info:%1$s", "表头，currPageNum="+currPageNum+",labelVal=["+labelVal+"]");
					dest_cs		=	wb.createCellStyle();
					if( 3==cellType || 4==cellType )
					{
						if( 3==cellType )
						{
							head_cs[hr].setDataFormat(dou_CS.getDataFormat());
						}
						else
						{
							head_cs[hr].setDataFormat(int_CS.getDataFormat());
						}
						head_cs[hr].setFont(wb.getFontAt(font_ind));
						cell.setCellStyle(head_cs[hr]);
						cell.setCellValue(Double.parseDouble(labelVal));
						cell.setCellType(CellType.NUMERIC);
					}
					else
					{
					//	System.out.printf("5555&&&&&&debug:%1$s", "size="+wb.getFontAt(head_cs[hr].getFontIndex()).getFontHeightInPoints());
						cell.setCellStyle(head_cs[hr]);
						if( null==labelVal || "".equals(labelVal.trim()) )
							labelVal	=	" ";

						cell.setCellValue(labelVal);
					}
				}
			}

	    	//	表体合并单元格,添加单元格的处理放在处理表体中
			List tmList		=	(List)modelMap.get("tmList");
		    int tmLen		=	tmList.size();
		    int rowT,colT,rowT2,colT2;
		    Map tmMap		=	new HashMap();
	    				
	    	
	    	//	处理表体内容
			objList = getDatas(datas,(currPageNum-1)*page_cut,currPageNum*page_cut-1);

		    objIt =	objList.iterator();
			
		    int currpg_rec_num	=	0;		//	当前页当前记录数
		    while(objIt.hasNext())
		    {	
		    	/*增加合并单元格*/
		    	for(int i=0;i<tmLen;i++)
		    	{
		    		tmMap	=	(Map)tmList.get(i);
		    		rowT	=	(Integer)tmMap.get("row");
		    		colT	=	(Integer)tmMap.get("col");
			    	rowT2	=	(Integer)tmMap.get("row2");
			    	colT2	=	(Integer)tmMap.get("col2");
			    //	System.out.printf("info:%1$s\n", "表体合并单元格,rowT="+(rowT+currpg_rec_num)+",colT="+colT+",rowT2="+(rowT2+currpg_rec_num)+",colT2="+colT2);
			    	try {
			    		sheet.addMergedRegion(new CellRangeAddress(rowT+currpg_rec_num, rowT2+currpg_rec_num, colT, colT2));
					} catch (Exception e) {
						throw new BaseException(SysErr.E_DEFAULT, "合并表体单元格出现异常!");
					}
					
			    	// 用第一行的CS设置单元格格式，保证边框可以加上
					int		mCount		=	0;
			    	for( ;mCount<tableList.size();mCount++ )
			    	{
			    		detail_map		=	(Map)tableList.get(mCount);
			    		if( rowT==(Integer)detail_map.get("row") && colT==(Integer)detail_map.get("col") )
			    		{
			    			break;
			    		}
			    	}
			    //	System.out.printf("info:%1$s\n", "表体设置合并单元格各个cell格式,mCount="+mCount);
			    	for(int j=(rowT+currpg_rec_num); j<=(rowT2+currpg_rec_num); j++)
			    	{
			    		HSSFRow		tmpRow		=	sheet.getRow(j);
			    		for(int k=colT; k<=colT2; k++)
			    		{
			    			HSSFCell	tmpCell	=	tmpRow.getCell(k);
			    			tmpCell.setCellStyle(detail_cs[mCount]);
			    		}
			    	}
			    }
		    	
				//	判断文件中是否还有记录
				Map dataMap			=	null;		    		
		    	dataMap	=	(Map)objIt.next();
		    	
		    	Map 	tMapB		=	null;
		    	String	cellValue	=	"";
		    	// 取出当前行
				row					=	sheet.getRow(row_start+currpg_rec_num);
		    	
		    	for(int mCount=0;mCount<tableList.size();mCount++)
		    	{
		    		tMapB			=	(Map)tableList.get(mCount);
					int		iRow	=	(Integer)tMapB.get("row");
					int		iCol	=	(Integer)tMapB.get("col");

					// 取出明细数据
					cellValue	=	(String)tMapB.get("s");
					if( null==cellValue || "".equals(cellValue) )
						cellValue	=	" ";
					
					// 判断是常量，表达式，还是文件中的数据元素
					int cellType=	judge_CellType(cellValue);
				//	System.out.printf("info:%1$s\n", "表体，cellType="+cellType+",cellValue="+cellValue+",iRow="+(iRow+currpg_rec_num)+",iCol="+iCol+",curr_rec_num="+(curr_rec_num+1));


					// 设置单元格格式
					short font_ind	=	detail_cs[mCount].getFontIndex();
					cell			=	row.getCell(iCol);
					if( (3==cellType || 4==cellType ) && 1==curr_rec_num )
					{
						if( 3==cellType )
						{
							detail_cs[mCount].setDataFormat(dou_CS.getDataFormat());
						}
						else
						{
							detail_cs[mCount].setDataFormat(int_CS.getDataFormat());
						}
						detail_cs[mCount].setFont(wb.getFontAt(font_ind));
						cell.setCellType(CellType.NUMERIC);
					}
					else
					{
						cell.setCellStyle(detail_cs[mCount]);
					}

					// 对单元格进行设置值的处理
					if( 2==cellType ) 					// 如果是表达式，生成表达式处理
					{
						String	expr_name	=	splitExprName(cell, cellValue);
						cell.setCellFormula(expr_name);
						if( isbEvalFlag() )
						{
							eval.evaluateInCell(cell);
							cell.setCellType(CellType.NUMERIC);
						}
						else
							cell.setCellType(CellType.FORMULA);

						detail_cs[mCount].setFont(wb.getFontAt(font_ind));
						detail_cs[mCount].setDataFormat(dou_CS.getDataFormat());
						cell.setCellStyle(detail_cs[mCount]);
					}
					else		//	如果是数据元素或者常量文本
					{
						String	resultCellValue	=	cellValue;
						if( 1==cellType || 3==cellType || 4==cellType )	// 如果是数据元素(文本型或者数字型)
						{
							String	elem_name	=	"";
							if( 1==cellType ){
								elem_name		=	splitElemName(cellValue);
							}
							else
							{		// 3 or 4
								if( 3==cellType )
									elem_name		=	splitNumName(cellValue);
								else
									elem_name		=	splitIntName(cellValue);
							}
							
							String  elem_value  =   " ";
							//	判断是否从datalist中取还是从对象属性中取中取
							if( iCol<col_start )	//	不是从列表中取数据
							{
								elem_value = getElemVal(elem_name,allDataMap,cellType);
							}
							else					//	从列表中取数据
							{
								elem_value = (String)dataMap.get(elem_name);
								
					    		if( null==elem_value || "".equals(elem_value.trim()) )
					    		{
					    			if( 1==cellType)
					    				elem_value	=	" ";
					    			else if( 3==cellType)
					    				elem_value	=	"0.00";
					    			else
					    				elem_value	=	"0";
					    		}
							}							
							
							//	替换数据元素
							if( 1==cellType )
								resultCellValue	=	replaceElemName(cellValue,elem_value);
							else if( 3==cellType )
								resultCellValue	=	replaceNumName(cellValue,elem_value);
							else
								resultCellValue	=	replaceIntName(cellValue,elem_value);
								
						}
						
						//System.out.printf("info:%1$s\n", "表体，iCol="+iCol+",currpg_rec_num="+currpg_rec_num+",resultCellValue=["+resultCellValue+"]");
						if( 3==cellType || 4==cellType )	// 数字(金额)型或整型
							cell.setCellValue(Double.parseDouble(resultCellValue));
						else				// 文本型
							cell.setCellValue(resultCellValue);
					}
					
		    	}	// end for 完成一行明细的生成		    	
		    	currpg_rec_num++;
		    	if(false==objIt.hasNext() ) 	//	跳出明细处理
	    			break;
		    }	// end while 完成一页明细的生成		    	
		
	    	//	表尾合并单元格
			List 	emList	=	(List)modelMap.get("emList");
		    int		emLen	=	emList.size();
		    int rowE,colE,rowE2,colE2;
		    Map emMap = null;
	    	for(int i=0;i<emLen;i++)
	    	{
	    		emMap	=	(Map)emList.get(i);
		    	rowE	=	(Integer)emMap.get("row");
		    	colE	=	(Integer)emMap.get("col");
		    	rowE2	=	(Integer)emMap.get("row2");
		    	colE2	=	(Integer)emMap.get("col2");
		    	//System.out.printf("info:%1$s\n", "表尾合并单元格,rowE="+(rowE+currpg_rec_num)+",colE="+colE+",rowE2="+(rowE2+currpg_rec_num)+",colE2="+colE2);
		    	try {
		    		sheet.addMergedRegion(new CellRangeAddress(rowE+currpg_rec_num, rowE2+currpg_rec_num, colE, colE2));
				} catch (Exception e) {
					throw new BaseException(SysErr.E_DEFAULT, "合并表尾单元格出现异常!");
				} 
				
		    	// 用第一行的CS设置单元格格式，保证边框可以加上
				int		mCount		=	0;
		    	for( ;mCount<headList.size();mCount++ )
		    	{
		    		end_map				=	(Map)endList.get(mCount);
		    		if( rowE==(Integer)end_map.get("row") && colE==(Integer)end_map.get("col") )
		    		{
		    			break;
		    		}
		    	}
		    	//System.out.printf("info:%1$s\n", "表尾设置合并单元格各个cell格式,mCount="+mCount);
		    	for(int j=rowE+currpg_rec_num; j<=rowE2+currpg_rec_num; j++)
		    	{
		    		HSSFRow		tmpRow		=	sheet.getRow(j);
		    		for(int k=colE; k<=colE2; k++)
		    		{
		    			HSSFCell	tmpCell	=	tmpRow.getCell(k);
		    			tmpCell.setCellStyle(end_cs[mCount]);
		    		}
		    	}
		    }
	    	
			//	设置表尾
			Map		erMap	=	new HashMap();
			for(int lr=0;lr<endList.size();lr++)
			{
				erMap			=	(Map)endList.get(lr);
				int		iRow	=	(Integer)erMap.get("row");
				int		iCol	=	(Integer)erMap.get("col");
				String	strVal	=	(String)erMap.get("s");
	    		if( null==strVal || "".equals(strVal) )
	    			continue;

	    		// 取出单元格
				row			=	sheet.getRow(iRow+currpg_rec_num);
				cell		=	row.getCell(iCol);
		    		
				int cellType=	judge_CellType(strVal);
				//System.out.printf("info:%1$s", "表尾，iRow="+(iRow+currpg_rec_num)+",iCol="+iCol+",cellType="+cellType+",strVal=["+strVal+"]");

				short font_ind	=	end_cs[lr].getFontIndex();
				if( 2==cellType )	// 如果是表达式，生成表达式处理
				{
					String	expr_name	=	splitExprName(cell, strVal);
					cell.setCellFormula(expr_name);
					System.out.printf("info:%1$s\n", "表尾，iRow="+(iRow+currpg_rec_num)+",iCol="+iCol+",cellType="+cellType+",expr_name=["+expr_name+"]");
					if( isbEvalFlag() )
					{
						eval.evaluateInCell(cell);
						cell.setCellType(CellType.NUMERIC);
					}
					else{
						cell.setCellType(CellType.FORMULA);
					}
					
					end_cs[lr].setDataFormat(dou_CS.getDataFormat());
					cell.setCellStyle(end_cs[lr]);
				}
				else if( 5==cellType ) // 如果是本页小计
				{
					
				}
				else				// 如果是数据元素或者常量文本
				{
					String	labelVal	=	strVal;
					if( 1==cellType )	//	如果是数据元素
					{
						String	elem_name	=	splitElemName(strVal);
						String elem_value   =   getElemVal(elem_name,endData,cellType);
						//处理当前页数、总页数
						if($NOW_PAGE$.equals(elem_name)){
							elem_value = ""+currPageNum;
						}else if($TOTAL_PAGE$.equals(elem_name)){
							elem_value = ""+totPageNum;
						}
						labelVal			=	replaceElemName(strVal,elem_value);
					}
					if( 3==cellType )	//	如果是数字数据元素
					{
						String	elem_name	=	splitNumName(strVal);
						String elem_value   =   getElemVal(elem_name,endData,cellType);
						labelVal			=	replaceNumName(strVal,elem_value);
					}
					if( 4==cellType )	//	如果是整型数据元素
					{
						String	elem_name	=	splitNumName(strVal);
						String elem_value   =   getElemVal(elem_name,endData,cellType);
						labelVal			=	replaceNumName(strVal,elem_value);
					}
					
					// 把内容设置到单元格中
					if( 3==cellType || 4==cellType )
					{
						if( 3==cellType )
							end_cs[lr].setDataFormat(dou_CS.getDataFormat());
						else
							end_cs[lr].setDataFormat(int_CS.getDataFormat());

						end_cs[lr].setFont(wb.getFontAt(font_ind));
						cell.setCellStyle(end_cs[lr]);
						cell.setCellValue(Double.parseDouble(labelVal));
						cell.setCellType(CellType.NUMERIC);
					}
					else
					{
						cell.setCellStyle(end_cs[lr]);
						if( null==labelVal || "".equals(labelVal.trim()) )
							labelVal	=	" ";
						
						cell.setCellValue(labelVal);
					}
				}
			}
			// 设置表尾结束

			// 设置列宽及列隐藏属性
			Integer[] colWidth	=	(Integer[])modelMap.get("colWidth");
			
			for(int i=0; i<colWidth.length; i++)
			{
				sheet.setColumnWidth(i, colWidth[i]);
			}
			
						
			// 设置行高
			Integer[] rowHeight	=	(Integer[])modelMap.get("rowHeight");
			for(int i=0; i<rowHeight.length; i++)
			{
				row		=	sheet.getRow(i);
			}
			
			//强制让sheet执行公式
//			sheet.setForceFormulaRecalculation(true);
			
		}	// end for 完成所有页
 
		// 写文件
        OutputStream out = null;
		try {
			out = new FileOutputStream(realPath);
		} catch (FileNotFoundException e1) {
			throw new BaseException(SysErr.E_DEFAULT, "初始化文件流["+realPath+"]时出现异常!");
		} 

		System.out.printf("info:%1$s", realPath+" before write! ");
		try {
			wb.write(out);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("errmsg=" + e.getStackTrace());
			System.out.printf("info:%1$s\n", "errmsg=" + e.getMessage());
			throw new BaseException(SysErr.E_DEFAULT, "生成excel出现异常!");
		} finally {
			if (null != wb) {
				try {
					wb.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
		}
		
		return realPath;
	}

	private static String getElemVal(String elem_name,
			Map<String, Object> headData, int cellType) {
		// TODO Auto-generated method stub
		String val = "";
		//1--字符数据元素 3--数字数据元素 4--整形数据元素
		if("1".equals(cellType)){
			
		}else if("3".equals(cellType)){
			val = "0.00";
		}else if("4".equals(cellType)){
			val = "0";
		}else{
			val =  elem_name; //不处理直接返回数据元素名
		}
		
		if(null!=headData && headData.containsKey(elem_name) && null!=headData.get(elem_name)){
			val = headData.get(elem_name).toString();
		}
		
		return val;
	}

	// 判断表格栏的类型
	// 返回值：0--常量文本 1--数据元素 2--表达式 3--数字数据元素 4--整形数据元素 5--合并数据元素
	private static int judge_CellType(String cellValue) {
		int cell_type = 0;
		String elementStart = "[_&#";
		String elementEnd = "#&_]";
		String exprStart = "[_&@";
		String exprEnd = "@&_]";
		String numStart = "[_&%";
		String numEnd = "%&_]";
		String intStart = "[_&!";
		String intEnd = "!&_]";
		String sumStart = "[_&&";
		String sumEnd = "&&_]";

		if (cellValue.indexOf(elementStart) > -1
				&& cellValue.indexOf(elementEnd) > -1)
			cell_type = 1;
		else if (cellValue.indexOf(exprStart) > -1
				&& cellValue.indexOf(exprEnd) > -1)
			cell_type = 2;
		else if (cellValue.indexOf(numStart) > -1
				&& cellValue.indexOf(numEnd) > -1)
			cell_type = 3;
		else if (cellValue.indexOf(intStart) > -1
				&& cellValue.indexOf(intEnd) > -1)
			cell_type = 4;
		else if (cellValue.indexOf(sumStart) > -1
				&& cellValue.indexOf(sumEnd) > -1)
			cell_type = 5;

		return cell_type;
	}

	// 从表格栏中分离出数据元素名
	private static String splitElemName(String cellValue) {
		String elem_name = "";
		String elementStart = "[_&#";
		String elementEnd = "#&_]";

		int ielem_start = elementStart.length();
		elem_name = cellValue.substring(cellValue.indexOf(elementStart)
				+ ielem_start, cellValue.indexOf(elementEnd));

		return elem_name;
	}

	// 从表格栏中分离出数据元素名
	private static String splitNumName(String cellValue) {
		String elem_name = "";
		String numStart = "[_&%";
		String numEnd = "%&_]";

		int ielem_start = numStart.length();
		elem_name = cellValue.substring(cellValue.indexOf(numStart)
				+ ielem_start, cellValue.indexOf(numEnd));

		return elem_name;
	}

	// 从表格栏中分离出数据元素名
	private static String splitIntName(String cellValue) {
		String elem_name = "";
		String intStart = "[_&!";
		String intEnd = "!&_]";

		int ielem_start = intStart.length();
		elem_name = cellValue.substring(cellValue.indexOf(intStart)
				+ ielem_start, cellValue.indexOf(intEnd));

		return elem_name;
	}

	// 从表格栏中分离出数据元素名
	private static String splitSumName(String cellValue) {
		String elem_name = "";
		String sumStart = "[_&&";
		String sumEnd = "&&_]";

		int ielem_start = sumStart.length();
		elem_name = cellValue.substring(cellValue.indexOf(sumStart)
				+ ielem_start, cellValue.indexOf(sumEnd));

		return elem_name;
	}

	// 替换数据元素
	private static String replaceElemName(String cellValue, String elemValue) {
		String ret_value = cellValue;
		String rep_value = "";
		String elementStart = "[_&#";
		String elementEnd = "#&_]";

		int len_elem_end = elementEnd.length();

		rep_value = cellValue.substring(cellValue.indexOf(elementStart),
				cellValue.indexOf(elementEnd) + len_elem_end);// 获得要替换的数据元素字符串
		ret_value = ret_value.replace(rep_value, elemValue);

		return ret_value;
	}

	private static String replaceNumName(String cellValue, String elemValue) {
		String ret_value = cellValue;
		String rep_value = "";
		String elementStart = "[_&%";
		String elementEnd = "%&_]";

		int len_elem_end = elementEnd.length();

		rep_value = cellValue.substring(cellValue.indexOf(elementStart),
				cellValue.indexOf(elementEnd) + len_elem_end);// 获得要替换的数据元素字符串
		ret_value = ret_value.replace(rep_value, elemValue);

		return ret_value;
	}

	private static String replaceIntName(String cellValue, String elemValue) {
		String ret_value = cellValue;
		String rep_value = "";
		String elementStart = "[_&!";
		String elementEnd = "!&_]";

		int len_elem_end = elementEnd.length();

		rep_value = cellValue.substring(cellValue.indexOf(elementStart),
				cellValue.indexOf(elementEnd) + len_elem_end);// 获得要替换的数据元素字符串
		ret_value = ret_value.replace(rep_value, elemValue);

		return ret_value;
	}

	// 从表格栏中分离出表达式名
	private static String splitExprName(String cellValue) {
		return splitExprName(null,cellValue);
	}
	
	// 从表格栏中分离出表达式名,如果表达式中存在需要替换当前行、当前列参数的值时，cell不为null
	private static String splitExprName(HSSFCell cell,String cellValue) {
		String expr_name = "";
		String exprStart = "[_&@";
		String exprEnd = "@&_]";

		int ielem_start = exprStart.length();
		expr_name = cellValue.substring(cellValue.indexOf(exprStart)
				+ ielem_start, cellValue.indexOf(exprEnd));
		//替换动态的当前行、当前列的参数
		if(null!=cell){
			//替换当前列值
			if(expr_name.indexOf($COL$)>-1){
				expr_name = expr_name.replace($COL$, indexToColumn(cell.getColumnIndex()+1));
			}
			//替换当行值
			if(expr_name.indexOf($ROW$)>-1){
				expr_name = expr_name.replace($ROW$, ""+(cell.getRowIndex()+1));
			}
			System.out.println("exr_name="+expr_name);
		}

		return expr_name;
	}
	
	/**
	 * 用于将Excel表格中列号字母转成列索引，从1对应A开始
	 * @param column	序号
	 * @return			列索引
	 */
	public static int columnToIndex(String column){
		if(!column.matches("[A-Z]+")){
			try{
				throw new Exception("Invalid parameter");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		int index = 0;
		char[] chars = column.toUpperCase().toCharArray();
		for(int i=0;i<chars.length;i++){
			index += ((int)chars[i] - (int)'A' + 1) * (int)Math.pow(26, chars.length -i -1);
		}
		return index;
	}
	
	/**
	 * 用于将Excel表格中列索引号转成列号字母，从A对应1开始
	 * @param index		索引号
	 * @return			列号
	 */
	public static String indexToColumn(int index){
		if(index<=0){
			try{
				throw new Exception("Invalid parameter");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		index--;
		String column = "";
		do{
			if(column.length()>0){
				index--;
			}
			column = ((char)(index%26 + (int)'A')) + column;
			index = (int)((index - index % 26)/26);
		}while(index>0);
		return column;
	}

	//默认读取第一个sheet的模板
	private static Map getModelMap_list(String modelPath, int dataColStart,
			int dataRowStart) throws BaseException {
		return getModelMap_list(modelPath,dataColStart,dataRowStart,0);
	}
	
	// 从带表头表尾的明细模板中读取表头,表体,表尾
	// add by chenyl on 2014.2.12
	private static Map getModelMap_list(String modelPath, int dataColStart,
			int dataRowStart, int sheetIdx) throws BaseException {
		System.out.printf("INFO: %1$s\n",
				"just into getModelMap_list....modelPath=" + modelPath);
		Map modelMap = new HashMap();
		List headList = new ArrayList();
		List tableList = new ArrayList();
		List endList = new ArrayList();
		HSSFSheet sheet = null;
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		try {
			fs = new POIFSFileSystem(new FileInputStream(modelPath));
			wb = new HSSFWorkbook(fs);
			// 取模板第一个工作表
			sheet = wb.getSheetAt(sheetIdx);
			String sheetName = wb.getSheetName(sheetIdx);
			System.out.printf("INFO: %1$s\n", "sheetName=" + sheetName);

			int rowCount = sheet.getLastRowNum() + 1; // 获取行总数
			// 通过比较得到最大的列数作为列总数
			int colCount = 0;
			HSSFRow hssfrow = null;
			for (int i = 0; i < rowCount; i++) {
				hssfrow = sheet.getRow(i);
				if (null != hssfrow && colCount < hssfrow.getLastCellNum())
					colCount = hssfrow.getLastCellNum();
			}
			System.out.printf("INFO: %1$s\n", "rowCount=" + rowCount + ",colCount="
					+ colCount);

			// 获取行高及列宽
			Integer[] rowHeight = new Integer[rowCount];
			Integer[] colWidth = new Integer[colCount];
			// 获取列宽
			for (int i = 0; i < colCount; i++) {
				colWidth[i] = new Integer(sheet.getColumnWidth(i));
			}
			// 获取行高
			for (int i = 0; i < rowCount; i++) {
				hssfrow = sheet.getRow(i);
				if (null != hssfrow)
					rowHeight[i] = new Integer(hssfrow.getHeight());
				else
					rowHeight[i] = rowHeight[0];
			}
			// 获取字体信息
			short fontNum = wb.getNumberOfFonts();
			HSSFFont[] font = new HSSFFont[fontNum + 1];
			for (short t = 0; t <= fontNum; t++) {
				font[t] = wb.getFontAt(t);
			}


			// 获取打印设置
			PrintSetup ps = sheet.getPrintSetup();
			Map marginMap = new HashMap();
			marginMap.put("ps", ps);

			// 获取页面设置
			Double topmargin = new Double(sheet.getMargin(HSSFSheet.TopMargin));
			Double leftmargin = new Double(sheet.getMargin(HSSFSheet.LeftMargin));
			Double bottommargin = new Double(
					sheet.getMargin(HSSFSheet.BottomMargin));
			Double rightmargin = new Double(sheet.getMargin(HSSFSheet.RightMargin));
			Boolean hori = new Boolean(sheet.getHorizontallyCenter());
			Boolean verti = new Boolean(sheet.getVerticallyCenter());

			// 取出合并单元格个数
			int iNum = sheet.getNumMergedRegions();
			List hmList = new ArrayList();
			List tmList = new ArrayList();
			List emList = new ArrayList();
			int row, col, row2, col2;
			CellRangeAddress cellRange = null;
			Map mMap = null;
			for (int i = 0; i < iNum; i++) {
				cellRange = sheet.getMergedRegion(i);
				row = cellRange.getFirstRow();
				col = cellRange.getFirstColumn();
				row2 = cellRange.getLastRow();
				col2 = cellRange.getLastColumn();

				mMap = new HashMap();
				mMap.put("col", col);
				mMap.put("row", row);
				mMap.put("col2", col2);
				mMap.put("row2", row2);

				if (row < dataRowStart)
					hmList.add(mMap);
				else if (row == dataRowStart)
					tmList.add(mMap);
				else
					emList.add(mMap);
			}

			// 数据元素的起止符
			String eleStart = "[_&#";
			String eleEnd = "#&_]";
			int lEleStart = eleStart.length();

			String numStart = "[_&%";
			String numEnd = "%&_]";
			int lNumStart = numStart.length();

			String intStart = "[_&!";
			String intEnd = "!&_]";
			int lIntStart = intStart.length();

			String[] arrList = new String[colCount - dataColStart];
			Map map = null;

			String strTmp = "";
			String eleName = "";
			HSSFCellStyle cellStyle = null;
			for (int i = 0; i < rowCount; i++) {
				hssfrow = sheet.getRow(i);
				if (null == hssfrow)
					break;
				for (int k = 0; k < hssfrow.getLastCellNum(); k++) {
					HSSFCell cell = hssfrow.getCell(k);
					if (null == cell)
						continue;

					strTmp = "";
					eleName = "";
					cellStyle = null;
					//首先设置Cell类型为String
					cell.setCellType(CellType.STRING);
					strTmp = cell.getStringCellValue();
					cellStyle = cell.getCellStyle();
					System.out.printf("INFO: %1$s\n",
							"读取模板信息,行数i=" + i + ",列数k=" + k + ",strTmp=" + strTmp
									+ ",font_ind=" + cellStyle.getFontIndex());

					// 获得数据元素名
					if (strTmp.indexOf(eleStart) > -1
							&& strTmp.indexOf(eleEnd) > -1) {
						eleName = strTmp.substring(strTmp.indexOf(eleStart)
								+ lEleStart, strTmp.indexOf(eleEnd));
					}
					if (strTmp.indexOf(numStart) > -1
							&& strTmp.indexOf(numEnd) > -1) {
						eleName = strTmp.substring(strTmp.indexOf(numStart)
								+ lNumStart, strTmp.indexOf(numEnd));
					}
					if (strTmp.indexOf(intStart) > -1
							&& strTmp.indexOf(intEnd) > -1) {
						eleName = strTmp.substring(strTmp.indexOf(intStart)
								+ lIntStart, strTmp.indexOf(intEnd));
					}
					map = new HashMap();
					map.put("row", i);
					map.put("col", k);
					map.put("s", strTmp);
					map.put("cs", cellStyle);

					if (i < dataRowStart) {
						headList.add(map);
					} else if (i == dataRowStart) {
						tableList.add(map); // tableList只有第一行有
						if (k >= dataColStart && "".equals(eleName) == false) {
							arrList[k - dataColStart] = eleName;
						}
					} else if (i >= dataRowStart) {
						// 对于在表体的中间部分不处理及超出每页行数的不处理
						endList.add(map);
					}
				}

			}

			modelMap.put("marginMap", marginMap);
			modelMap.put("hmList", hmList);
			modelMap.put("tmList", tmList);
			modelMap.put("emList", emList);
			modelMap.put("headList", headList);
			modelMap.put("endList", endList);
			modelMap.put("tableList", tableList);
			modelMap.put("arrList", arrList);

			modelMap.put("rowHeight", rowHeight);
			modelMap.put("colWidth", colWidth);
			modelMap.put("rowCount", rowCount);
			modelMap.put("colCount", colCount);
			modelMap.put("font", font);

			modelMap.put("topmargin", topmargin);
			modelMap.put("leftmargin", leftmargin);
			modelMap.put("bottommargin", bottommargin);
			modelMap.put("rightmargin", rightmargin);
			modelMap.put("hori", hori);
			modelMap.put("verti", verti);

			modelMap.put("sheetName", sheetName);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new BaseException(SysErr.E_DEFAULT, "模板文件不存在！");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			throw new BaseException(SysErr.E_DEFAULT, "读取文件异常！");
		}finally {
			if (null != wb) {
				try {
					wb.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if (null != fs) {
				try {
					fs.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
		}

		return modelMap;
	}

	// 获取唯一值
	private static String getOnlyStr() {
		long time = Calendar.getInstance().getTimeInMillis();
		return "" + time;
	}

	// 获取txt文件里的数据
	private static List<Map> getDatas(List<Object[]> datas, int istartPos,
			int iendPos) throws BaseException {
		List data = new ArrayList();
		int size = datas.size();
		for (int i = istartPos; i <= iendPos && i < size; i++) {
			Object[] data1 = datas.get(i);
			if (data1 == null) {
				continue;
			}
			Map row = new HashMap();
			for (int j = 0; j < data1.length; j++) {
			//	System.out.print("j:"+j);
			//	System.out.println(",data1[j]:"+data1[j]);
				if(data1[j]==null)
					row.put(""+j, "");
				else
					row.put(""+j, data1[j].toString());
			}
			data.add(row);
		}
		return data;// 返回从文本文件中读取内容
	}
	
	//数据库查询出来的DataSet转换成List<Object[]>
	public static List<Object[]> dataset2List(IDataset ds)
			throws BaseException {
		ArrayList<Object[]> dataList = new ArrayList<Object[]>();
		if (null == ds) {
			throw new BaseException(SysErr.E_DEFAULT,
					"DataSet为空，转换异常！");
		}
		int count = ds.getColumnCount();
		ds.beforeFirst();
		while (ds.hasNext()) {
			ds.next();
			Object[] objs = new Object[count];
			for (int i = 0; i < count; i++) {
				objs[i] = ds.getValue(i + 1);
			}
			dataList.add(objs);
		}
		return dataList;
	}


}
