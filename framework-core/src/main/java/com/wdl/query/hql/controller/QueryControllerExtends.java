package com.wdl.query.hql.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wdl.base.pojo.PageInfo;
import com.wdl.base.service.BaseService;
import com.wdl.query.hql.pojo.Query;
import com.wdl.query.hql.pojo.QueryCondition;

/**
 * @Author HEHUAN.
 * @Since 2016-07-29
 * @Version  1.0
 * -------------------------------------------------------------------------------
 *  开发人员[Email]           |  修改时间          |     修改方法        |    修改原因         | 版本       
 *  ------------------------------------------------------------------------------
 *  何焕<hehuan@eigpay.com>     2016-07-29      新增类                       新增类和方法        v1.0
 *
 */
public abstract class QueryControllerExtends {

	public void doExtend(BaseService baseService,
									 Query query, 
									 QueryCondition con, Map<String, Object> map) {
		PageInfo pageInfo = con.getPageInfo() == null ? new PageInfo(query.getPagesize()) : con.getPageInfo();
		Map<String,Object> paraMap = new HashMap<String,Object>();
		if (con.getConditions() != null) {
			for (Map<String, Object> parms : con.getConditions()) {
				String key = (String)parms.get("name");
				String val = (String)parms.get("value");
				if(key != null && key.trim().length() > 0 && val != null && val.trim().length() > 0) {
					paraMap.put(key.trim(), val.trim());
				}
			}
		}
		int count  = baseService.countBySql(getCountSqlString(paraMap)).intValue();
		pageInfo.setCount(count);
		// 排序
		StringBuilder sql = new StringBuilder();
		sql.append(getSqlString(paraMap));
		String sortInfo = con.getSortInfo() != null ? con.getSortInfo() : query.getOrder();
		if (sortInfo != null) {
			if (sortInfo.indexOf("_") > -1) {
				sortInfo = sortInfo.replace("_", ".");
				sql.append(" order by ");
				sql.append(sortInfo);
			} else {
				sql.append(" order by ");
				sql.append(sortInfo);
			}
		}
		if ("true".equals(query.getShowPage())) {
			List<?> list = baseService.findMapBySql(sql.toString(),pageInfo.getPageNum(), pageInfo.getPageSize());
			map.put("objList", list);
		} else {
			map.put("objList", baseService.findMapBySql(sql.toString()));
		}
		map.put("pageInfo", pageInfo);
	}
	
	protected abstract String getSqlString(Map<String,Object> paraMap);
	protected abstract String getCountSqlString(Map<String,Object> paraMap);
	protected abstract Class<?> getClazz();
}
