package com.wdl.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.Subcriteria;

public class ObjectUtil {
	/**
	 * 根据属性名获取getter setter方法名
	 * 
	 * @param field
	 *            字段名
	 * @param prefix
	 *            前缀
	 * @return
	 */
	public static String methodName(String field, String prefix) {
		if (field == null)
			return null;
		if (field.length() > 1 && Character.isUpperCase(field.charAt(1)))
			return prefix + field;
		else
			return prefix + field.substring(0, 1).toUpperCase()
					+ field.substring(1);
	}

	/**
	 * 根据属性名获取值
	 * 
	 * @param obj
	 *            对象
	 * @param field
	 *            字段名
	 * @return
	 */
	public static Object getValueByKey(Object obj, String field) {
		try {
			Method method = null;
			if (obj == null || StrUtil.isBlank(field))
				return null;
			String[] fieldArr = field.split("[.]");
			for (String str : fieldArr) {
				method = obj.getClass().getMethod(methodName(str, "get"));
				obj = method.invoke(obj);
			}
			// System.out.println("value:"+value);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}
	public static Object setValueByKey(Object obj, String field,Object value) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = null;
		if (obj == null || StrUtil.isBlank(field))
			return null;
		String[] fieldArr = field.split("[.]");
		for (String str : fieldArr) {
			method = obj.getClass().getMethod(methodName(str, "set"),value.getClass());
			obj = method.invoke(obj,value);
		}
		// System.out.println("value:"+value);
		return obj;
	}

	/**
	 * 将对象object特定方法的返回值（
	 * 
	 * @param object
	 *            对象
	 * @param method
	 *            方法
	 * @param format
	 *            格式
	 * @return
	 */
	public static String ObjectToString(Object obj, String field, String format)
			throws Exception {
		try {
			Method method = null;
			if (obj == null || StrUtil.isBlank(field))
				return null;
			String[] fieldArr = field.split("[.]");
			for (String str : fieldArr) {
				if (method != null)
					obj = method.invoke(obj);
				method = obj.getClass().getMethod(methodName(str, "get"));
			}
			// System.out.println("value:"+value);
			return ObjectToString(obj, method, format);

		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 将对象object特定方法的返回值（主要是get方法）按照format格式转化为字符串类型
	 * 
	 * @param object
	 *            对象
	 * @param method
	 *            方法
	 * @param format
	 *            格式
	 * @return
	 */
	public static String ObjectToString(Object object, Method method,
			String format) throws Exception {
		if (object == null || method == null)
			return null;
		// 时间类型
		if (method.getReturnType().getName().equals(Date.class.getName())) {
			if (StrUtil.isEmpty(format))
				return DateUtil.format((Date) method.invoke(object));
			else
				return DateUtil.format((Date) method.invoke(object), format);
		}

		return method.invoke(object).toString();

	}

	public static DetachedCriteria getCriteriaWithAlias(
			DetachedCriteria criteria, String columnName) {
		if (columnName.indexOf(".") == -1)
			return criteria;
		String[] nameArr = columnName.split("[.]");
		for (int index = 0; index < nameArr.length - 1; index++) {
			String str = nameArr[index];
			if (index > 0
					&& !isExistAlias((DetachedCriteria) criteria, ""
							+ nameArr[index - 1] + "." + str + "")) {
				criteria.createAlias("" + nameArr[index - 1] + "." + str + "",
						"" + str + "", DetachedCriteria.LEFT_JOIN);
			}
			if (index == 0 && !isExistAlias((DetachedCriteria) criteria, str)) {
				criteria.createAlias("" + str + "", "" + str + "",
						DetachedCriteria.LEFT_JOIN);
			}
		}
		return criteria;
	}

	@SuppressWarnings("unused")
	public static boolean isExistAlias(DetachedCriteria impl, String path) {
		try {
			Field field = DetachedCriteria.class.getDeclaredField("impl");
			field.setAccessible(true);
			CriteriaImpl criteriaImpl = (CriteriaImpl) field.get(impl);
			Iterator iterator = criteriaImpl.iterateSubcriteria();
			for (; iterator.hasNext();) {
				Subcriteria subcriteria = (Subcriteria) iterator.next();
				if (subcriteria.getPath().equals(path)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取类实例的属性值
	 * 
	 * @param clazz
	 *            类名
	 * @param includeParentClass
	 *            是否包括父类的属性值
	 * @return 类名.属性名=属性类型
	 */
	public static Object getClassFields(String className,String attr,
			boolean includeParentClass) {
		Class clazz=null;
		try {
			clazz = Class.forName(className);
			Map<String, Class> map = new HashMap<String, Class>();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				map.put(field.getName(), field.getType());
			}
			if (includeParentClass)
				getParentClassFields(map, clazz.getSuperclass());
			if(map.get(attr)!=null){
				return map.get(attr).getCanonicalName();
			}else{
				return null;
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("获取"+className+"错误！！！");
		}
	}

	/**
	 * 获取类实例的父类的属性值
	 * 
	 * @param map
	 *            类实例的属性值Map
	 * @param clazz
	 *            类名
	 * @return 类名.属性名=属性类型
	 */
	private static Map<String, Class> getParentClassFields(
			Map<String, Class> map, Class clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			map.put(field.getName(), field.getType());
		}
		if (clazz.getSuperclass() == null) {
			return map;
		}
		getParentClassFields(map, clazz.getSuperclass());
		return map;
	}

	/**
	 * 获取类实例的方法
	 * 
	 * @param clazz
	 *            类名
	 * @param includeParentClass
	 *            是否包括父类的方法
	 * @return List
	 */
	public static List<Method> getMothds(Class clazz, boolean includeParentClass) {
		List<Method> list = new ArrayList<Method>();
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			list.add(method);
		}
		if (includeParentClass) {
			getParentClassMothds(list, clazz.getSuperclass());
		}
		return list;
	}

	/**
	 * 获取类实例的父类的方法
	 * 
	 * @param list
	 *            类实例的方法List
	 * @param clazz
	 *            类名
	 * @return List
	 */
	private static List<Method> getParentClassMothds(List<Method> list,
			Class clazz) {
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			list.add(method);
		}
		if (clazz.getSuperclass() == Object.class) {
			return list;
		}
		getParentClassMothds(list, clazz.getSuperclass());
		return list;
	}

	public static void main(String[] args) {
		System.out.println(getClassFields("com.eigpay.base.entity.DemoEntity","id", true));
	}
}
