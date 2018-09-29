/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The Class ObjectUtil is a simple helper for objects.
 */
public class ObjectUtil {

	/**
	 * Cast list to wanted type.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param c the c
	 * @return the list
	 */
	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
		if (c == null) {
			return null;
		}
		List<T> r = new ArrayList<T>(c.size());
		for (Object o : c)
			r.add(clazz.cast(o));
		return r;
	}

	/**
	 * Checks if is list not null and empty.
	 *
	 * @param list the list
	 * @return true, if is list not null and empty
	 */
	public static boolean isListNotNullAndEmpty(List<?> list) {
		if (list != null) {
			if (list.size() > 0) {
				return true;
			}
		}
		return false;
	}

}
