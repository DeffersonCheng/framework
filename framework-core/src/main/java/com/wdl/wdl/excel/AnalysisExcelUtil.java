package com.wdl.wdl.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.wdl.util.StrUtil;
/**
 * excel解析工具类 使用POI
 * @author Defferson.Cheng
 *
 */
public class AnalysisExcelUtil{
	/**
	 * 解析输入流的excel成为数组
	 * 注：
	 * 1.内容前后的包括回车空白字符会被去除
	 * 2.空行会被无视
	 * 3.每行列数不固定所以记住有可能下标超出
	 * 4.值不会出现null的现象空值会处理成为空字符串
	 * @param inputStream
	 * @return 返回表格 二维数组
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static String[][] analysisExcel(InputStream inputStream) throws EncryptedDocumentException, InvalidFormatException, IOException{
		Workbook workBook = null;
		workBook=WorkbookFactory.create(inputStream);
		Sheet sheet = workBook.getSheetAt(0);
		return analysisSheet(sheet);
	}
	private static String[][] analysisSheet(Sheet sheet){
		List<String[]> table=new LinkedList<String[]>();
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			String[] row=analysisRow(sheet.getRow(i));
			if (row!=null) table.add(row);
		}
		return table.toArray(new String[table.size()][]);
	}
	private static String[] analysisRow(Row row){
		if (row==null) return null;
		List<String> cellList=new LinkedList<String>();
		boolean isValidRow=false;
		for(int i=0;i<row.getLastCellNum();i++){
			Cell cell=row.getCell(i);
			if (cell==null) cellList.add("");
			else {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String value=cell.getStringCellValue();
				if (null!=value) {
					value=StrUtil.trimEx(value);
					cellList.add(value);
				}else{
					cellList.add("");
				}
				if (null!=value&&!"".equals(value)) isValidRow=true; 
			}
		}
		if (isValidRow) return cellList.toArray(new String[cellList.size()]);
		return null;
	}
	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
		File file=new File("c:/Users/dcpho/Desktop/经典模式.xls");
		System.out.println(file.exists());
		System.out.println(file.getAbsolutePath());
		FileInputStream input=new FileInputStream(file);
		String[][] table=analysisExcel(input);
		for (int i = 0; i < table.length; i++) {
			System.out.printf("%-10d", i);
			for (int j = 0; j < table[i].length; j++) {
				System.out.printf("%d%-10s", j,table[i][j]);
			}
			System.out.println();
		}
	}
}