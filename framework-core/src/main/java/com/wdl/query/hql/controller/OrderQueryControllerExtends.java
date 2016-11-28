package com.wdl.query.hql.controller;

import java.util.Map;

import org.hibernate.internal.CriteriaImpl.OrderEntry;

/**
 * @Author   HEHUAN<hehuan@eigpay.com>
 * @Since    2016-07-29
 * @Version  v1.0
 * -------------------------------------------------------------------------------
 *  开发人员[Email]            |   修改时间     |     修改方法        | 修改原因         | 版本       
 *  ------------------------------------------------------------------------------
 *  何焕<hehuan@eigpay.com>      2016-07-29                    新增整个类         v1.0
 *
 */
public class OrderQueryControllerExtends extends QueryControllerExtends {

	@Override
	protected String getSqlString(Map<String,Object> paraMap) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select id AS \"id\",version,orderid AS \"orderId\",usernickname AS \"userNickName\",opername AS \"operName\",chinesename AS \"chineseName\",reportconfigname AS \"reportConfigName\",reportconfigid AS \"reportConfigId\",reportamount AS \"reportAmount\",status,create_date_time AS \"createDateTime\",ishumanreview AS \"isHumanReview\" ");
		getCommonString(paraMap,sqlBuilder);
		return sqlBuilder.toString();
	}
	
	

	@Override
	protected String getCountSqlString(Map<String, Object> paraMap) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select count(*) ");
		getCommonString(paraMap,sqlBuilder);
		return sqlBuilder.toString();
	}
	
	
	private void getCommonString(Map<String, Object> paraMap,StringBuilder sqlBuilder) {
		sqlBuilder.append("from td_order ");
		sqlBuilder.append("where 1=1");
		String orderid = (String)paraMap.get("orderId");
		if(orderid != null && orderid.length() > 0) {
			sqlBuilder.append(" and orderId = '" + orderid + "'");
		}
		String chineseName = (String)paraMap.get("chineseName");
		if(chineseName != null && chineseName.length() > 0) {
			sqlBuilder.append(" and chineseName like '%" + chineseName + "%'");
		}
		String reportConfigName = (String)paraMap.get("reportConfigName");
		if(reportConfigName != null && reportConfigName.length() > 0) {
			sqlBuilder.append(" and reportConfigName like '%" + reportConfigName + "%'");
		}
	}

	@Override
	protected Class getClazz() {
		return null;
	}

}
