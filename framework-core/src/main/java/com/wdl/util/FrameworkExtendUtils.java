package com.wdl.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author hehuan.
 * @Since
 * @Version  
 * -----------------------------------------------------------------
 *  开发人员[Email] |  修改时间    |     修改方法        | 修改原因         | 版本       
 *  ----------------------------------------------------------------
 *  
 *
 */
public class FrameworkExtendUtils {

	public static Map<String,Object> specialParam = new HashMap<String,Object>();
	public static Map<String,Object> specialImpClaNameMap = new HashMap<String,Object>();
	
	static {
		specialParam.put("special_1", "special_order");
		specialImpClaNameMap.put("special_1", "com.eigpay.query.hql.controller.OrderQueryControllerExtends");
		specialParam.put("special_2", "special_interpreterBacklog");
		specialImpClaNameMap.put("special_2", "com.experian.sqlextends.InterpreterBacklogControllerExtends");
	}
}
