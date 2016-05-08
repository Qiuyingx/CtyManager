package com.yard.core.util;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * 
* @Title: ClassUtil.java
* @Description:反射工具类
* @author Wangchaoyong   
* @date 下午2:34:57 
* @version V1.0
 */
@SuppressWarnings({"rawtypes"})
public class ClassUtil {

	/**
	 * 通过反射，获得指定类的父类泛型参数的实际类型，即获得UserDao extends BaseDao<User>中的User.
	 * 
	 * @param clazz
	 *            反射的class对象
	 * @param index
	 *            泛型参数索引
	 * @return 父类泛型参数对应实际类
	 * @author maiYo
	 */
	public static Class getGenericClassTpye(Class clazz, int index) {
		// 得到泛型父类,包含泛型参数信息,如:Class<User>
		Type genericClassType = clazz.getGenericSuperclass();
		// 所有泛型必须实现ParameterizedType接口，如没实现则不是泛型，直接返回Object.class
		if (!(genericClassType instanceof ParameterizedType)) {
			return Object.class;
		}
		// 得到泛型实际参数中对应的Class,如Class<User>中的User,参数可为多个，所以定义为数组
		Type params[] = ((ParameterizedType) genericClassType)
				.getActualTypeArguments();
		if (index >= params.length || index < 0) {
			throw new RuntimeException("Param Index Out Of Range");
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];

	}

	/**
	 * 通过反射，获得指定类的父类泛型参数的第一个实际类型，即获得UserDao extends BaseDao<User>中的User.
	 * 
	 * @param clazz
	 *            反射的class对象
	 * @return 父类泛型参数对应实际类
	 * @author wangchaoyong
	 */
	public static Class getGenericClassTpye(Class clazz) {
		// 得到泛型父类,包含泛型参数信息,如:Class<User>
		Type genericClassType = clazz.getGenericSuperclass();
		// 所有泛型必须实现ParameterizedType接口，如没实现则不是泛型，直接返回Object.class
		if (!(genericClassType instanceof ParameterizedType)) {
			return Object.class;
		}
		// 得到泛型实际参数中对应的Class,如Class<User>中的User,参数可为多个，所以定义为数组
		Type params[] = ((ParameterizedType) genericClassType)
				.getActualTypeArguments();
		if (!(params.length > 0 && params != null)) {
			return Object.class;
		}
		if (!(params[0] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[0];

	}
}
