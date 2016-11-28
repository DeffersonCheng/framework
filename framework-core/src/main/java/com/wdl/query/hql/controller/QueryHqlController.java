package com.wdl.query.hql.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSON;
import com.wdl.base.pojo.PageInfo;
import com.wdl.base.service.BaseService;
import com.wdl.query.hql.pojo.Column;
import com.wdl.query.hql.pojo.Query;
import com.wdl.query.hql.pojo.QueryCondition;
import com.wdl.query.hql.pojo.QueryDefinition;
import com.wdl.util.ClassUtil;
import com.wdl.util.StrUtil;

@Controller
@RequestMapping("/queryController")
public class QueryHqlController {
	@Resource
	private BaseService baseService;

	/**
	 * table通用查询
	 * 
	 * @param parm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryData")
	@ResponseBody
	public Map<String, Object> queryTable(String parm) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		QueryCondition condition = JSON.parseObject(HtmlUtils.htmlUnescape(parm), QueryCondition.class);
		Query query = condition.getQuery() != null ? condition.getQuery()
				: QueryDefinition.getQueryById(condition.getQueryId());
		
		if (query != null && query.getClassName() != null) {
			parseParms(query, condition, map);
			map.put("query", JSON.toJSON(query));
			map.put("columns", JSON.toJSON(query.getColumnList()));
		} else {
			throw new RuntimeException("Query is null or className is not exist!");
		}
		return map;
	}

	public void parseParms(Query query, QueryCondition con, Map<String, Object> map) {
		PageInfo pageInfo = con.getPageInfo() == null ? new PageInfo(query.getPagesize()) : con.getPageInfo();
		Map<String, Object> vals = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("from ");
		hql.append(query.getClassName());
		hql.append(" where 1=1");
		if (query.getJoin() != null) {
			hql.append(" and " + query.getJoin());
		}
		if (con.getConditions() != null) {
			int key_index = 0;
			for (Map<String, Object> parms : con.getConditions()) {
				key_index++;
				if (parms.get("value") != null && !"".equals(parms.get("value"))) {

					String filter = (parms.get("filter") == null ? null : parms.get("filter").toString());
					if (filter == null) {
						String filterKey = (String) parms.get("name");
						if (query.getColumnList() != null) {
							for (Column c : query.getColumnList()) {
								if (filterKey.equals(c.getKey())) {
									filter = (c.getOper() == null ? "LIKE" : c.getOper());
									break;
								}
							}
						}
						if (filter == null) {
							filter = "like";
						}
					}
					filter = filter.toLowerCase();
					// hql中查询使用不支持":sub.name"的方式，需将占位变量替换下
					String skey = parms.get("name").toString().replace(".", "_");
					if ("EQ".toLowerCase().equals(filter)) {
						hql.append(" and ");
						hql.append(parms.get("name").toString());
						hql.append("=:");
						hql.append(skey + key_index);
						vals.put(skey + key_index, parseValue(query.getClassName(), parms.get("name").toString(),
								parms.get("value"), filter));
					} else if ("GT".toLowerCase().equals(filter)) {
						hql.append(" and ");
						hql.append(parms.get("name").toString());
						hql.append(">:");
						hql.append(skey + key_index);
						vals.put(skey + key_index, parseValue(query.getClassName(), parms.get("name").toString(),
								parms.get("value"), filter));
					} else if ("GE".toLowerCase().equals(filter)) {
						hql.append(" and ");
						hql.append(parms.get("name").toString());
						hql.append(">=:");
						hql.append(skey + key_index);
						vals.put(skey + key_index, parseValue(query.getClassName(), parms.get("name").toString(),
								parms.get("value"), filter));
					} else if ("LT".toLowerCase().equals(filter)) {
						hql.append(" and ");
						hql.append(parms.get("name").toString());
						hql.append("<:");
						hql.append(skey + key_index);
						vals.put(skey + key_index, parseValue(query.getClassName(), parms.get("name").toString(),
								parms.get("value"), filter));
					} else if ("LE".toLowerCase().equals(filter)) {
						hql.append(" and ");
						hql.append(parms.get("name").toString());
						hql.append("<=:");
						hql.append(skey + key_index);
						vals.put(skey + key_index, parseValue(query.getClassName(), parms.get("name").toString(),
								parms.get("value"), filter));
					} else if ("LIKE".toLowerCase().equals(filter)) {
						hql.append(" and ");
						hql.append(parms.get("name").toString());
						hql.append(" like :");
						hql.append(skey + key_index);
						vals.put(skey + key_index, parseValue(query.getClassName(), parms.get("name").toString(),
								parms.get("value"), filter));
					} else if ("BETWEEN".toLowerCase().equals(filter)) {
						String[] keys = parms.get("value").toString().split(",");
						if (keys.length == 1) {
							hql.append(" and ");
							hql.append(parms.get("name").toString());
							hql.append(" >= :");
							hql.append(skey + key_index);
							vals.put(skey + key_index,
									parseValue(query.getClassName(), parms.get("name").toString(), keys[0], filter));
						} else if (keys.length == 2) {
							hql.append(" and ");
							hql.append(parms.get("name").toString());
							if (StrUtil.isEmpty(keys[0])) {
								hql.append(" <= :");
								hql.append(skey + key_index);
								vals.put(skey + key_index, parseValue(query.getClassName(),
										parms.get("name").toString(), keys[1], filter));
							} else {
								hql.append(" between :");
								hql.append(skey + key_index);
								vals.put(skey + key_index, parseValue(query.getClassName(),
										parms.get("name").toString(), keys[0], filter));
								key_index++;
								hql.append(" and :");
								hql.append(skey + key_index);
								vals.put(skey + key_index, parseValue(query.getClassName(),
										parms.get("name").toString(), keys[1], filter));
							}

						}
					} else if ("IN".toLowerCase().equals(filter)){
						String[] keys = parms.get("value").toString().split(",");
						if (keys.length > 0) {
							hql.append(" and ");
							hql.append(parms.get("name").toString());
							hql.append(String.format(" in (%s)", parms.get("value").toString().startsWith("'")&&parms.get("value").toString().endsWith("'")?parms.get("value").toString():"'"+parms.get("value").toString()+"'"));
						}
					}
				}
			}
		}

		pageInfo.setCount(baseService.count("select count(*) " + hql.toString(), vals).intValue());
		// 排序
		String sortInfo = con.getSortInfo() != null ? con.getSortInfo() : query.getOrder();
		if (sortInfo != null) {
			if (sortInfo.indexOf("_") > -1) {
				sortInfo = sortInfo.replace("_", ".");
				hql.append(" order by ");
				hql.append(sortInfo);
			} else {
				hql.append(" order by ");
				hql.append(sortInfo);
			}
		}
		String select = buildSelect(query);
		if ("true".equals(query.getShowPage())) {
			List list = baseService.getPageMapByHql(select + hql.toString(), vals, pageInfo.getPageNum(),
					pageInfo.getPageSize());
			map.put("objList", list);
		} else {
			map.put("objList", baseService.getPageMapByHql(select + hql.toString(), vals));
		}
		map.put("pageInfo", pageInfo);
	}

	public Object parseValue(String className, String name, Object value, String oper) {
		Object obj = null;
		if (name.contains(".")) {
			String[] keys = name.split("\\.");
			String[] classNames = className.split(",");
			for (String c : classNames) {
				if (c.contains(" " + keys[0])) {
					className = c.replace(" " + keys[0], "");
					className.trim();
				}
			}
			className = ClassUtil.getFieldType(className, keys[1]);
		} else {
			className = ClassUtil.getFieldType(className, name);
		}
		if ("java.lang.String".equals(className)) {
			obj = value == null ? null : value.toString();
		} else if ("java.lang.Integer".equals(className)) {
			obj = Integer.parseInt(value.toString());
		} else if ("java.lang.Long".equals(className)) {
			obj = Long.parseLong(value.toString());
		} else if ("java.lang.Double".equals(className)) {
			obj = Double.parseDouble(value.toString());
		} else if ("java.lang.Float".equals(className)) {
			obj = Float.parseFloat(value.toString());
		} else if ("java.util.Date".equals(className)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				obj = sdf.parse(value.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if ("like".toLowerCase().equals(oper)) {
			obj = "%" + obj + "%";
		}
		return obj;
	}

	private String buildSelect(Query query) {
		String select = "";
		select = "select ";
		List<Column> columns = query.getColumnList();
		List<Column> cols = new ArrayList<Column>();
		for (Column c : columns) {
			if (exist(query.getClassName(), c.getKey())) {
				cols.add(c);
			}
		}
		for (int i = 0; i < cols.size(); i++) {
			if (i != cols.size() - 1) {
				select += cols.get(i).getKey() + " as " + cols.get(i).getKey().replace(".", "_") + ",";
			} else {
				select += cols.get(i).getKey() + " as " + cols.get(i).getKey().replace(".", "_") + " ";
			}
		}
		return select;
	}

	private boolean exist(String className, String key) {
		Object obj = null;
		if (key.contains(".")) {
			String[] keys = key.split("\\.");
			String[] classNames = className.split(",");
			for (String c : classNames) {
				if (c.contains(" " + keys[0])) {
					className = c.replace(" " + keys[0], "");
					className.trim();
				}
			}
			obj = ClassUtil.getFieldColumnAnnotation(className, keys[1]);
		} else {
			if (className.contains(",")) {
				return false;
			}
			obj = ClassUtil.getFieldColumnAnnotation(className, key);
		}
		return obj != null;
	}
	

	public static void main(String[] args) {
		String str = "1,";
		String[] d = str.split(",");
		System.out.println(123);
	}
}
