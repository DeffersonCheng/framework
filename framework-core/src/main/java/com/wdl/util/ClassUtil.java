package com.wdl.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

public class ClassUtil {
	public static List<Field> getFieldByClassName(Class c) {
		List<Field> list = new ArrayList<Field>();
		try {
			if (c.getDeclaredFields() != null) {
				for (Field f : c.getDeclaredFields()) {
					list.add(f);
				}
			}
			while (c.getSuperclass() != null && c.getSuperclass() != Object.class) {
				c = c.getSuperclass();
				list.addAll(getFieldByClassName(c));
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String getFieldType(String className, String fieldName) {
		String _type = "";
		try {
			List<Field> list = ClassUtil.getFieldByClassName(Class.forName(className));
			if (list != null) {
				for (Field f : list) {
					if (fieldName.equals(f.getName())) {
						_type = f.getType().getName();
						break;
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return _type;
	}

	public static Object getFieldValue(Object obj, String fieldName) {
		Object val = null;
		try {
			Class clazz = obj.getClass();
			List<Field> list = ClassUtil.getFieldByClassName(clazz);
			if (list != null) {
				for (Field f : list) {
					if (fieldName.equals(f.getName())) {
						String upperName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
						Method method = clazz.getMethod("get" + upperName);
						val = method.invoke(obj);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	public static Object getFieldColumnAnnotation(String className, String fieldName) {
		Object field = null;
		try {
			List<Field> list = ClassUtil.getFieldByClassName(Class.forName(className));
			if (list != null) {
				for (Field f : list) {
					if (fieldName.equals(f.getName())) {
						field= f.getAnnotation(Column.class);
						break;
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return field;
	}
}
