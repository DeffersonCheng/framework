package com.wdl.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 文件工具类
 * 
 * @author bin
 *
 */
public class FileUtil {
	public static void appendToFile(String path,Object value){
		try {
			BufferedWriter writer=new BufferedWriter(new FileWriter(new File(path), true));
			writer.write(value+"\n");
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 读文件成为字符串
	 * 默认编码utf-8
	 * @param input
	 * @return
	 */
	public static String readFile(InputStream input) {
		return readFile(input,"utf-8");
	}
	/**
	 * 读文件成为字符串
	 * @param input
	 * @return
	 */
	public static String readFile(InputStream input,String encode) {
		BufferedReader buffer = null;
		String laststr = "";
		try {
			buffer = new BufferedReader(new InputStreamReader(input, encode));
			String tempString = null;
			while ((tempString = buffer.readLine()) != null) {
				laststr += tempString;
			}
			buffer.close();
			return laststr;
		} catch (IOException e) {
			throw new RuntimeException("",e);
		} finally {
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	/**
	 * 读取文件内容
	 * 
	 * @param file
	 * @return
	 */
	public static String readFile(String filePath,String encode){
		File file = new File(filePath);
		BufferedReader buffer = null;
		String laststr = "";
		try {
			buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file), encode));
			String tempString = null;
			while ((tempString = buffer.readLine()) != null) {
				laststr += tempString;
			}
			buffer.close();
			return laststr;
		} catch (IOException e) {
			throw new RuntimeException(String.format("读取[%s]文件内容出现异常", file.getName()), e);
		} finally {
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	public static String readFile(String fullFileName) {
		return readFile(fullFileName,"utf-8");
	}
	public static String readHtmlFile(String file) {
		File pdfFile = new File(file);
		try {
			if (!pdfFile.exists() || pdfFile.isDirectory())
				throw new FileNotFoundException();
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(pdfFile));
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
			byte[] temp = new byte[1024];
			int size = 0;
			while ((size = in.read(temp)) != -1) {
				out.write(temp, 0, size);
			}
			in.close();
			return out.toString();
		} catch (IOException ie) {
			ie.printStackTrace();
			throw new RuntimeException(String.format("读取[%s]文件内容出现异常", pdfFile.getName()), ie);
		}
	}
	/**
	 * 删除文件
	 * 
	 * @param src
	 */
	public static void deleteFile(String src) {
		File file = new File(src);
		if (file.exists()) {
			file.delete();
		}
	}
}
