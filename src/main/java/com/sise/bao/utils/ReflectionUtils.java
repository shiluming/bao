package com.sise.bao.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 鍙嶅皠鐨刄til鍑芥暟闆嗗悎.
 * 
 * 鎻愪緵璁块棶绉佹湁鍙橀噺,鑾峰彇娉涘瀷绫诲瀷Class,鎻愬彇闆嗗悎涓厓绱犵殑灞炴��,杞崲瀛楃涓插埌瀵硅薄绛塙til鍑芥暟.
 * 
 */
public class ReflectionUtils {

	private static Logger logger = LoggerFactory
			.getLogger(ReflectionUtils.class);

	/**
	 * 鐩存帴璇诲彇瀵硅薄灞炴�у��, 鏃犺private/protected淇グ绗�, 涓嶇粡杩噂etter鍑芥暟.
	 */
	public static Object getFieldValue(final Object object,
			final String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.error("涓嶅彲鑳芥姏鍑虹殑寮傚父{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 鐩存帴璁剧疆瀵硅薄灞炴�у��, 鏃犺private/protected淇グ绗�, 涓嶇粡杩噑etter鍑芥暟.
	 */
	public static void setFieldValue(final Object object,
			final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			logger.error("涓嶅彲鑳芥姏鍑虹殑寮傚父:{}", e.getMessage());
		}
	}

	/**
	 * 鐩存帴璋冪敤瀵硅薄鏂规硶, 鏃犺private/protected淇グ绗�.
	 */
	public static Object invokeMethod(final Object object,
			final String methodName, final Class<?>[] parameterTypes,
			final Object[] parameters) {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		if (method == null)
			throw new IllegalArgumentException("Could not find method ["
					+ methodName + "] on target [" + object + "]");

		method.setAccessible(true);

		try {
			return method.invoke(object, parameters);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 寰幆鍚戜笂杞瀷, 鑾峰彇瀵硅薄鐨凞eclaredField.
	 * 
	 * 濡傚悜涓婅浆鍨嬪埌Object浠嶆棤娉曟壘鍒�, 杩斿洖null.
	 */
	protected static Field getDeclaredField(final Object object,
			final String fieldName) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field涓嶅湪褰撳墠绫诲畾涔�,缁х画鍚戜笂杞瀷
			}
		}
		return null;
	}

	/**
	 * 寮鸿璁剧疆Field鍙闂�.
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers())
				|| !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 寰幆鍚戜笂杞瀷,鑾峰彇瀵硅薄鐨凞eclaredMethod.
	 * 
	 * 濡傚悜涓婅浆鍨嬪埌Object浠嶆棤娉曟壘鍒�, 杩斿洖null.
	 */
	protected static Method getDeclaredMethod(Object object, String methodName,
			Class<?>[] parameterTypes) {

		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
				// Method涓嶅湪褰撳墠绫诲畾涔�,缁х画鍚戜笂杞瀷
			}
		}
		return null;
	}

	/**
	 * 閫氳繃鍙嶅皠,鑾峰緱Class瀹氫箟涓０鏄庣殑鐖剁被鐨勬硾鍨嬪弬鏁扮殑绫诲瀷. 濡傛棤娉曟壘鍒�, 杩斿洖Object.class. eg. public UserDao
	 * extends HibernateDao<User>
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be
	 *         determined
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 閫氳繃鍙嶅皠,鑾峰緱瀹氫箟Class鏃跺０鏄庣殑鐖剁被鐨勬硾鍨嬪弬鏁扮殑绫诲瀷. 濡傛棤娉曟壘鍒�, 杩斿洖Object.class.
	 * 
	 * 濡俻ublic UserDao extends HibernateDao<User,Long>
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be
	 *         determined
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(final Class clazz,
			final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName()
					+ "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of "
					+ clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName()
					+ " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * 灏嗗弽灏勬椂鐨刢hecked exception杞崲涓簎nchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(
			Exception e) {
		if (e instanceof IllegalAccessException
				|| e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException)
			return new IllegalArgumentException("Reflection Exception.", e);
		else if (e instanceof InvocationTargetException)
			return new RuntimeException("Reflection Exception.",
					((InvocationTargetException) e).getTargetException());
		else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}
}
