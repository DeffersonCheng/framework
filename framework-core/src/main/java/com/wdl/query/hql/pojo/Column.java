package com.wdl.query.hql.pojo;

import org.apache.commons.digester3.annotations.rules.ObjectCreate;
import org.apache.commons.digester3.annotations.rules.SetProperty;

/**
 * 列
 * 
 * @author bin
 * 
 */
@ObjectCreate(pattern = "queryContext/query/column")
public class Column {

	/**
	 * id
	 */
	@SetProperty(attributeName = "id", pattern = "queryContext/query/column")
	private String id;
	/**
	 * 主键
	 */
	@SetProperty(attributeName = "key", pattern = "queryContext/query/column")
	private String key;

	/**
	 * 显示值
	 */
	@SetProperty(attributeName = "header", pattern = "queryContext/query/column")
	private String header;
	/**
	 * 宽度：暂时是百分比浮点数
	 */
	@SetProperty(attributeName = "width", pattern = "queryContext/query/column")
	private String width;
	/**
	 * 对齐方式 left right center
	 */
	@SetProperty(attributeName = "align", pattern = "queryContext/query/column")
	private String align;
	/**
	 * 查询方式
	 */
	@SetProperty(attributeName = "oper", pattern = "queryContext/query/column")
	private String oper;
	/**
	 * 是否在列表显示
	 */
	@SetProperty(attributeName = "hidden", pattern = "queryContext/query/column")
	private Boolean hidden;
	/**
	 * 回调
	 */
	@SetProperty(attributeName = "callback", pattern = "queryContext/query")
	private String callback;
	/**
	 * 是否可排序
	 */
	@SetProperty(attributeName = "sortAble", pattern = "queryContext/query")
	private boolean sortAble;

	public Column() {
		align = "center";
		hidden = false;
		sortAble = false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean getSortAble() {
		return sortAble;
	}

	public void setSortAble(boolean sortAble) {
		this.sortAble = sortAble;
	}

}
