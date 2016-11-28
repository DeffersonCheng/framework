package com.wdl.wdl.excel;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
/**
 * 
 * @author Defferson.Cheng
 *
 */
public class ExportExcelUtils {
    public interface Filter{
        public Object filt(Object obj,String name);
    }
	/**
	 * 导出excel，将会把excel二进制写入输出流，并且输出流不会close，需要手动flush以及close
	 * @param title：excel文件名称
	 * @param headers：表头
	 * @param dataset：要保存到表格的对象集合
	 * @param attrList：对象要保存的属性
	 * @param out：输出目的地
	 * @param filter 字典之类的过滤器
	 */
	public static void exportExcel(String title, String[] headers, Collection<?> dataset,String properties[], OutputStream out,Filter filter) {
		if (filter==null){
		    filter=new Filter() {
                @Override
                public Object filt(Object obj, String name) {
                    return obj;
                }
            };
		}
		// 声明一个工作薄
		Workbook workbook = new HSSFWorkbook();
		//创建样式
		CellStyle generalCellStyle=createDataStyle(workbook);
		CellStyle headCellStyle=createHeaderStyle(workbook);
		// 生成一个表格
		Sheet sheet = workbook.createSheet(title);
		
		// 设置表格默认列宽度为20
		sheet.setDefaultColumnWidth((short) 20);
		
		// 产生表头
		Row row = sheet.createRow(0);
		
		//插入表头数据
		for (short i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
			//设置样式
			cell.setCellStyle(headCellStyle);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		int index = 1;
		for(Object bean : dataset){
			//得到一个bean，则生成表格的一行
			row = sheet.createRow(index++);
			for(int i=0;i<properties.length;i++){
				PropertyDescriptor pd = null;
				try{
					pd = new PropertyDescriptor(properties[i],bean.getClass());
				}catch (Exception e) {
					throw new RuntimeException("bean中没有属性：" + properties[i]);
				}
				
				//得到bean的属性值
				Object attrValue = null;
				try {
					attrValue = pd.getReadMethod().invoke(bean, (Object[])null);
				} catch (Exception e) {
					throw new RuntimeException("无法获取bean的属性值：" + pd.getName(),e);
				} 
				try {
					attrValue=filter.filt(attrValue, pd.getName());
				} catch (NullPointerException e) {
					attrValue="null";
				}
				//转成字符串
				String cellValue = "";
				if(attrValue instanceof Date){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					cellValue = df.format(attrValue); //把日期转换后赋给cellValue
				}else{
					if(attrValue!=null){
						cellValue = attrValue.toString();
					}
				}
				
				Cell cell = row.createCell(i);
				cell.setCellStyle(generalCellStyle);
				cell.setCellValue(cellValue);
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {	
			throw new RuntimeException(e);
		}
	}
	public static void exportExcelByMap(String title, String[] headers, Collection<? extends Map<String,?>> dataset,String properties[], OutputStream out,Filter filter) {
		if (filter==null){
			filter=new Filter() {
				@Override
				public Object filt(Object obj, String name) {
					return obj;
				}
			};
		}
		// 声明一个工作薄
		Workbook workbook = new HSSFWorkbook();
		//创建样式
		CellStyle generalCellStyle=createDataStyle(workbook);
		CellStyle headCellStyle=createHeaderStyle(workbook);
		// 生成一个表格
		Sheet sheet = workbook.createSheet(title);
		
		// 设置表格默认列宽度为20
		sheet.setDefaultColumnWidth((short) 20);
		
		// 产生表头
		Row row = sheet.createRow(0);
		
		//插入表头数据
		for (short i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
			//设置样式
			cell.setCellStyle(headCellStyle);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		
		int index = 1;
		for(Map<String,?> map : dataset){
			//得到一个bean，则生成表格的一行
			row = sheet.createRow(index++);
			for(int i=0;i<properties.length;i++){
				
				Object attrValue = null;
				attrValue=map.get(properties[i]);
				try {
					attrValue=filter.filt(attrValue, properties[i]);
				} catch (NullPointerException e) {
					attrValue="null";
				}
				//转成字符串
				String cellValue = "";
				if(attrValue instanceof Date){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					cellValue = df.format(attrValue); //把日期转换后赋给cellValue
				}else{
					if(attrValue!=null){
						cellValue = attrValue.toString();
					}else{
						cellValue="null";
					}
				}
				Cell cell = row.createCell(i);
				cell.setCellStyle(generalCellStyle);
				cell.setCellValue(cellValue);
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {	
			throw new RuntimeException(e);
		}
	}
	
	private static CellStyle createHeaderStyle(Workbook workbook){
		// 生成一个样式
		CellStyle style = workbook.createCellStyle();

		// 设置表头的样式
		style.setFillForegroundColor(HSSFColor.ROYAL_BLUE.index); //设置表头背景颜色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 生成表头的字体
		Font font = workbook.createFont();
		font.setFontName("宋体");
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		// 把字体应用到当前的样式
		style.setFont(font);
		return style;
	}
	private static CellStyle createDataStyle(Workbook workbook){
		CellStyle cellStyle=null;
		// 生成数据行的样式
		cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成数据行的字体
		Font font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		cellStyle.setFont(font);
		return cellStyle;
	}

}
