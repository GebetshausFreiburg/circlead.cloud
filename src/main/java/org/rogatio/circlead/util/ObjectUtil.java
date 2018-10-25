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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

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
		// List<Timeslice> list = new ArrayList<Timeslice>();
		for (Timeslice timeslice : slices) {
			timeslice.setAllokation(timeslice.getAllokation() / ((double) size));
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
			merge.setAllokation(s1.getAllokation() + s2.getAllokation());
			merge.setStart(s1.getStart());
			merge.setEnd(s1.getEnd());
			merge.setFreq(s1.getFreq());
			merge.setUnitValue(s1.getUnitValue());
			merge.setSliceStart(s1.getSliceStart());
			list.add(merge);
		}

		return list;
	}

	public static List<Timeslice> merge(Map<String, List<Timeslice>> dataMap) {
		List<Timeslice> list = null;
		
		Vector<String> keys = new Vector<String>(dataMap.keySet());
		for (String key : keys) {
			List<Timeslice> dataset = dataMap.get(key);
			list = merge(dataset, list);
		}
		
		return list;
	}
	
	public static Map<String, List<Timeslice>> sort(Map<String, List<Timeslice>> dataMap) {
		Map<Double, String> sortedMap = new TreeMap<Double, String>();

		Vector<String> keys = new Vector<String>(dataMap.keySet());

		for (String key : keys) {
			double sum = 0;
			List<Timeslice> dataset = dataMap.get(key);
			for (Timeslice timeslice : dataset) {
				sum += timeslice.getAllokation();
			}
			sortedMap.put(sum, key);
		}
		
		Map<String, List<Timeslice>> map = new LinkedHashMap<String, List<Timeslice>>();
		
		Vector<Double> k = new Vector<Double>(sortedMap.keySet());
		for (Double d : k) {
//			System.out.println(d+": "+sortedMap.get(d));
			map.put(sortedMap.get(d), dataMap.get(sortedMap.get(d)));
		}
		
		return map;
	}

}
