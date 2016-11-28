package com.wdl.util.pdf.pojo;

import java.util.Map;

/**
 * 模板合并类
 * 
 * @author bin
 *
 */
public class FreemarkerTemplate {
	/**
	 * 模板路径
	 */
	private String path;
	/**
	 * 模板名称
	 */
	private String name;
	private String webUrl;
	/**
	 * 数据集
	 */
	private Map<String, Object> data;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

}
