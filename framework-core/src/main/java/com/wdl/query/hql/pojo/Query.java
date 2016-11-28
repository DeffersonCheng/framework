package com.wdl.query.hql.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester3.annotations.rules.ObjectCreate;
import org.apache.commons.digester3.annotations.rules.SetNext;
import org.apache.commons.digester3.annotations.rules.SetProperty;

@ObjectCreate(pattern = "queryContext/query")
public class Query {
	@SetProperty(attributeName = "id", pattern = "queryContext/query")
	private String id;
	@SetProperty(attributeName = "select", pattern = "queryContext/query")
	private String select;
	@SetProperty(attributeName = "className", pattern = "queryContext/query")
	private String className;
	@SetProperty(attributeName = " join", pattern = "queryContext/query")
	private String join;
	@SetProperty(attributeName = "tableName", pattern = "queryContext/query")
	private String tableName;
	@SetProperty(attributeName = "showPage", pattern = "queryContext/query")
	private String showPage;
	@SetProperty(attributeName = "pageList", pattern = "queryContext/query")
	private Integer[] pageList;
	@SetProperty(attributeName = "pageNum", pattern = "queryContext/query")
	private Integer pageNum;
	@SetProperty(attributeName = "pageSize", pattern = "queryContext/query")
	private Integer pagesize;
	@SetProperty(attributeName = "checkbox", pattern = "queryContext/query")
	private boolean checkbox;

	@SetProperty(attributeName = "order", pattern = "queryContext/query")
	private String order;
	private List<Column> columnList;
	private Map<String,Column> columnMap;

	public Query() {
		pagesize = 20; 
		pageList=new Integer[]{10,20,50};
		columnList=new ArrayList<Column>();
		columnMap=new HashMap<String,Column>();
	}
	@SetNext
	public void addColumn(Column queryColumn) {
		columnList.add(queryColumn);
		columnMap.put(queryColumn.getId()!=null?queryColumn.getId():queryColumn.getKey(),queryColumn);
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getJoin() {
		return join;
	}
	public void setJoin(String join) {
		this.join = join;
	}
	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}


	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

	public Map<String, Column> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, Column> columnMap) {
		this.columnMap = columnMap;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getShowPage() {
		return showPage;
	}
	public void setShowPage(String showPage) {
		this.showPage = showPage;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer[] getPageList() {
		return pageList;
	}
	public void setPageList(Integer[] pageList) {
		this.pageList = pageList;
	}
	public boolean isCheckbox() {
		return checkbox;
	}
	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}
	
	
}