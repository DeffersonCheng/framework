package com.wdl.query.hql.pojo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConditionOperator {
	private final String operator;
	private static final Map cachedOperators = new HashMap();
	private ConditionOperator(String operator) {
		this.operator = operator;
	}

	public String getString() {
		return operator;
	}
	public static ConditionOperator getOperator(String operator) throws RuntimeException {
		operator = operator.toUpperCase();
		ConditionOperator op = (ConditionOperator) cachedOperators
				.get(operator);
		if (op == null)
			try {
				Field field = ConditionOperator.class.getField(operator);
				op = (ConditionOperator) field.get(null);
				cachedOperators.put(operator, op);
			} catch (Exception e){
				throw new RuntimeException((new StringBuilder()).append("Unknown operator: ").append(operator).toString(), e);
			}
		return op;
	}

	public static final ConditionOperator LIKE = new ConditionOperator("%0 LIKE %1") ;
	public static final ConditionOperator EQ = new ConditionOperator("%0 = %1");
	public static final ConditionOperator NOT_EQ = new ConditionOperator("%0 <> %1");
	public static final ConditionOperator GT = new ConditionOperator("%0 > %1");
	public static final ConditionOperator LT = new ConditionOperator("%0 < %1");
	public static final ConditionOperator GE = new ConditionOperator("%0 >= %1");
	public static final ConditionOperator LE = new ConditionOperator("%0 <= %1");
	public static final ConditionOperator BETWEEN = new ConditionOperator("%0 BETWEEN %1");
	

}