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

import org.rogatio.circlead.model.data.Timeslice;

// TODO: Auto-generated Javadoc
/**
 * The Class ObjectUtil is a simple helper for objects.
 */
public class ObjectUtil {

	/**
	 * Cast list to wanted type.
	 *
	 * @param       <T> the generic type
	 * @param clazz the clazz
	 * @param c     the c
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

	public static List<Timeslice> divideSlices(List<Timeslice> slices, int size) {
		//List<Timeslice> list = new ArrayList<Timeslice>();
		for (Timeslice timeslice : slices) {
			timeslice.setAllokation(timeslice.getAllokation()/((double)size));
		}
		return slices;
	}
	
	public static List<Timeslice> createEmptySlices(int size) {
		List<Timeslice> list = new ArrayList<Timeslice>();
		for (int i = 0; i < size; i++) {
			list.add(new Timeslice());
		}
		return list;
	}
	
	public static List<Timeslice> merge(List<Timeslice> slices1, List<Timeslice> slices2) {
		List<Timeslice> list = new ArrayList<Timeslice>();
		if (slices1 == null) {
			return null;
		}
		if (slices2 == null) {
			slices2 = createEmptySlices(slices1.size());
		}
		if (slices1.size() != slices2.size()) {
			return null;
		}
		
		for (int i = 0; i < slices1.size(); i++) {
			Timeslice merge = new Timeslice();
			Timeslice s1 = slices1.get(i);
			Timeslice s2 = slices2.get(i);
			merge.setAllokation(s1.getAllokation()+s2.getAllokation());
			merge.setStart(s1.getStart());
			merge.setEnd(s1.getEnd());
			merge.setFreq(s1.getFreq());
			merge.setSliceStart(s1.getSliceStart());
			list.add(merge);
		}
		
		return list;
	}

}
