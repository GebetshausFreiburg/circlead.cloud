/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.util;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;
import org.rogatio.circlead.model.data.Timeslice;

import com.sun.codemodel.JCodeModel;

/**
 * The Class ObjectUtil is a simple helper for objects.
 * 
 * @author Matthias Wegner
 */
public class ObjectUtil {

	private final static Logger LOGGER = LogManager.getLogger(ObjectUtil.class);

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

	/**
	 * Divide slices.
	 *
	 * @param slices the slices
	 * @param size   the size
	 * @return the list
	 */
	public static List<Timeslice> divideSlices(List<Timeslice> slices, int size) {
		// List<Timeslice> list = new ArrayList<Timeslice>();
		for (Timeslice timeslice : slices) {
			timeslice.setAllokation(timeslice.getAllokation() / ((double) size));
		}
		return slices;
	}

	/**
	 * Creates the empty slices.
	 *
	 * @param size the size
	 * @return the list
	 */
	public static List<Timeslice> createEmptySlices(int size) {
		List<Timeslice> list = new ArrayList<Timeslice>();
		for (int i = 0; i < size; i++) {
			list.add(new Timeslice());
		}
		return list;
	}

	/**
	 * Merge.
	 *
	 * @param slices1 the slices 1
	 * @param slices2 the slices 2
	 * @return the list
	 */
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

	/**
	 * Merge.
	 *
	 * @param dataMap the data map
	 * @return the list
	 */
	public static List<Timeslice> merge(Map<String, List<Timeslice>> dataMap) {
		List<Timeslice> list = null;

		Vector<String> keys = new Vector<String>(dataMap.keySet());
		for (String key : keys) {
			List<Timeslice> dataset = dataMap.get(key);
			list = merge(dataset, list);
		}

		return list;
	}

	/**
	 * Sort.
	 *
	 * @param dataMap the data map
	 * @return the map
	 */
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

	/**
	 * Sort by value.
	 *
	 * @param     <K> the key type
	 * @param     <V> the value type
	 * @param map the map
	 * @return the map
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	/**
	 * Convert to html color.
	 *
	 * @param color the color
	 * @return the string
	 */
	public static String convertToHtmlColor(Color color) {
		return "#" + Integer.toHexString(color.getRGB()).substring(2) + "";
	}

	public static void createPojoZipFromJson(String jsonSource, String className, String packageDir, String zipFile) {
		JCodeModel codeModel = new JCodeModel();
		GenerationConfig config = new DefaultGenerationConfig() {
			@Override
			public boolean isGenerateBuilders() { // set config option by overriding method
				return true;
			}

			public SourceType getSourceType() {
				return SourceType.JSON;
			}
		};

		try {
			File f = Files.createTempDirectory("json2pojo").toFile();
			SchemaMapper mapper = new SchemaMapper(
					new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
			mapper.generate(codeModel, className, packageDir, jsonSource);

			codeModel.build(f);

			FileUtil.zipDirectory(f.toString(), zipFile);
		} catch (IOException e) {
			LOGGER.error(e);
		}

	}

	public static void createPojoDirFromJson(String jsonSource, String className, String packageDir, String targetDir) {
		JCodeModel codeModel = new JCodeModel();
		GenerationConfig config = new DefaultGenerationConfig() {
			@Override
			public boolean isGenerateBuilders() { // set config option by overriding method
				return true;
			}

			public SourceType getSourceType() {
				return SourceType.JSON;
			}
		};

		try {
			File f = Files.createTempDirectory("json2pojo").toFile();
			SchemaMapper mapper = new SchemaMapper(
					new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
			mapper.generate(codeModel, className, packageDir, jsonSource);

			codeModel.build(f);

			FileUtil.copyFileOrFolder(f, new File(targetDir));
		} catch (IOException e) {
			LOGGER.error(e);
		}

	}

}
