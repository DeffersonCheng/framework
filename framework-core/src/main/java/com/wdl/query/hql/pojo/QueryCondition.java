package com.wdl.query.hql.pojo;

import java.util.List;
import java.util.Map;

import com.wdl.base.pojo.PageInfo;

public class QueryCondition {
	//
	private PageInfo pageInfo;
	private List<Map<String, Object>> conditions;
	private String queryId;
	private Query query;
	private String sortInfo;
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	public List<Map<String, Object>> getConditions() {
		return conditions;
	}
	public void setConditions(List<Map<String, Object>> conditions) {
		this.conditions = conditions;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public Query getQuery() {
		return query;
	}
	public void setQuery(Query query) {
		this.query = query;
	}
	public String getSortInfo() {
		return sortInfo;
	}
	public void setSortInfo(String sortInfo) {
		this.sortInfo = sortInfo;
	}
	
}