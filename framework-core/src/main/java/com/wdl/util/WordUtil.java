package com.wdl.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class WordUtil {
	public static Logger logger=Logger.getLogger(WordUtil.class);
	public static void main(String[] args) {
		writeWordFile();
	}
	//以下注释是xml直接转word方法。
	public static boolean writeWordFile(String path,String fileName,String readFile) {
		boolean w = false;
		try {
			if (!"".equals(path)) {
				// 检查目录是否存在
				File fileDir = new File(path);
				if (fileDir.exists()) {
					// 生成临时文件名称
					BufferedInputStream is=new BufferedInputStream(new FileInputStream(new File(readFile)));
					BufferedOutputStream os=new BufferedOutputStream(new FileOutputStream(new File(path+fileName)));
					byte buf[] = new byte[10240];
					int len;
					while(-1!=(len=is.read(buf))){
						os.write(buf,0,len);
					}
					//DirectoryEntry directory = poifs.getRoot();
					//DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
					//System.out.println(documentEntry.getSize());
					os.flush();
					os.close();
					is.close();
					w=true;
				}
			}
			return w;
		} catch (IOException e) {
			throw new RuntimeException(String.format("由HTML[%s]生成Word文档[%s]出现异常", readFile, fileName), e);
		}
	}
	/*public static boolean writeWordFile(String path,String fileName,String readFile) {
		boolean w = false;
		try {
			if (!"".equals(path)) {
				// 检查目录是否存在
				File fileDir = new File(path);
				if (fileDir.exists()) {
					// 生成临时文件名称
					logger.debug(FileUtil.readFile(readFile));
					byte b[] = FileUtil.readFile(readFile)
							.getBytes("utf-8");
					ByteArrayInputStream bais = new ByteArrayInputStream(b);
					POIFSFileSystem poifs = new POIFSFileSystem();
					poifs.createDocument(bais, "WordDocument");
					//DirectoryEntry directory = poifs.getRoot();
					//DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
					//System.out.println(documentEntry.getSize());
					FileOutputStream ostream = new FileOutputStream(path + fileName);
					poifs.writeFilesystem(ostream);
					ostream.flush();
					try{
						bais.close();
					}catch(Exception e){}
					try{
						poifs.close();
					}catch(Exception e){}
					try{
						ostream.close();
					}catch(Exception e){}
					w=true;
				}
			}
			return w;
		} catch (IOException e) {
			throw new RuntimeException(String.format("由HTML[%s]生成Word文档[%s]出现异常", readFile, fileName), e);
		}
	}*/
	public static boolean writeWordFile() {
        boolean w = false;
        String path = "/Users/bin/Desktop/pdf/";
        try {
            if (!"".equals(path)) {
                // 检查目录是否存在
                File fileDir = new File(path);
                if (fileDir.exists()) {
                    // 生成临时文件名称
                    String fileName = "b.doc";
                    byte b[] = FileUtil.readFile("/Users/bin/Desktop/test.html")
                            .getBytes();
                    ByteArrayInputStream bais = new ByteArrayInputStream(b);
                    POIFSFileSystem poifs = new POIFSFileSystem();
                    DirectoryEntry directory = poifs.getRoot();
                    DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
                    System.out.println(documentEntry.getSize());
                    FileOutputStream ostream = new FileOutputStream(path + fileName);
                    poifs.writeFilesystem(ostream);
                    bais.close();
                    ostream.close();
                }
            }
            return w;
        } catch (IOException e) {
			throw new RuntimeException(e);
        }
    }
}
