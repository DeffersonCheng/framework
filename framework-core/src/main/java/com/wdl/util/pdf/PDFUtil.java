package com.wdl.util.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.pdf.BaseFont;
import com.wdl.util.pdf.pojo.FreemarkerTemplate;
import com.wdl.util.pdf.pojo.ImageTag;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * PDF工具类
 * 
 * @author bin
 *
 */
public class PDFUtil {
	/**
	 * 合并多个html模板
	 * 
	 * @param templates(模板对象集合)
	 * @param output(输出地址)
	 */
	public static void joinTemplate(List<FreemarkerTemplate> templates, String output,Class<?> templateLoader) {
		if (templates != null) {
			PrintWriter pw = null;
			FreemarkerTemplate temp1 = null;
			try {
				pw = new PrintWriter(new File(output), "UTF-8");
				for (FreemarkerTemplate temp : templates) {
				    if(temp==null) continue;
					temp1 = temp;
					Map<String, Object> map = temp.getData();
					map.put("image", new ImageTag());
					map.put("webUrl", temp.getWebUrl());
					Configuration cfg = new Configuration();
					cfg.setClassForTemplateLoading(templateLoader, temp.getPath());
					Template tmpl = cfg.getTemplate(temp.getName(), "UTF-8");
					tmpl.process(temp.getData(), pw);
				}
				pw.flush();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(String.format("合并HTML模板异常，模块名为【%s】，模板URL为【%s】，模板路径为【%s】", temp1.getName(), temp1.getWebUrl(), temp1.getPath()),e);
			} finally {
				pw.close();
			}
		}
	}

	/**
	 * html转换pdf
	 * 
	 * @param inputFile(html文件)
	 * @param outputFile(生成pdf文件)
	 * @param fonts(需要设置的字体)
	 * @param resourcesUrl(静态资源路径)
	 * @return
	 */
	public static boolean convertHtmlToPdf(String inputFile, String outputFile, String[] fonts) throws Exception{
			OutputStream os = new FileOutputStream(outputFile);
			ITextRenderer renderer = new ITextRenderer();
			ITextFontResolver fontResolver = renderer.getFontResolver();
			if (fonts != null) {
				for (String font : fonts) {
					fontResolver.addFont(font, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
				}
			}
			String url = new File(inputFile).toURI().toURL().toString();
			renderer.setDocument(url);
			renderer.layout();
			renderer.createPDF(os);
			os.flush();
			os.close();
			return true;
	}

	public boolean convertHtmlToPdf(String inputFile, String outputFile) throws Exception {

		OutputStream os = new FileOutputStream(outputFile);
		ITextRenderer renderer = new ITextRenderer();
		String url = new File(inputFile).toURI().toURL().toString();
		renderer.setDocument(url);
		// 解决中文支持问题
		ITextFontResolver fontResolver = renderer.getFontResolver();
		fontResolver.addFont("/Users/bin/Desktop/Project/html5/temp/font/simsun.ttf", BaseFont.IDENTITY_H,
				BaseFont.NOT_EMBEDDED);
		// 解决图片的相对路径问题
		renderer.getSharedContext().setBaseURL("file:/Users/bin/Desktop/Project/html5/temp");
		renderer.layout();
		renderer.createPDF(os);
		os.flush();
		os.close();
		return true;
	}

	public static void main(String[] args) {
		PDFUtil html2Pdf = new PDFUtil();
		try {
		    String[] font = {"E:\\m2_workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp6\\wtpwebapps\\bulletin-web-admin\\chackTemplates\\resource\\font\\simsun.ttc"};
		    PDFUtil.convertHtmlToPdf("D:\\htmltopdf\\GeneralHeaderBuilder.html",
					"D:\\htmltopdf\\GeneralHeaderBuilder.pdf",font);
			System.out.println("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
