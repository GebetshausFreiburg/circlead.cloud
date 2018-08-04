/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.util;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;

// TODO: Auto-generated Javadoc
/**
 * The Class FileUtils.
 */
public class FileUtil {

	/** The Constant logger. */
	final static Logger logger = LogManager.getLogger(FileUtil.class);

	/**
	 * Delete recursive.
	 *
	 * @param f the f
	 * @throws Exception the exception
	 */
	public static void deleteRecursive(File f) throws Exception {
		try {
			if (f.isDirectory()) {
				for (File c : f.listFiles()) {
					deleteRecursive(c);
				}
			}
			if (!f.delete()) {
				throw new Exception("Delete command returned false for file: " + f);
			}
		} catch (Exception e) {
			throw new Exception("Failed to delete the folder: " + f, e);
		}
	}

	/**
	 * Write schema.
	 *
	 * @param dir the dir
	 * @param name the name
	 * @param clazz the clazz
	 */
	@SuppressWarnings("unchecked")
	public static void writeSchema(File dir, String name, @SuppressWarnings("rawtypes") Class clazz) {
		ObjectMapper schemaMapper = new ObjectMapper();
		schemaMapper.enable(SerializationFeature.INDENT_OUTPUT);
		JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(schemaMapper);
		JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(clazz);

		File fs = new File(dir.toString() + File.separatorChar + name + ".schema.json");
		if (fs.exists()) {
			fs.delete();
		}
		try {
			fs.createNewFile();
		} catch (IOException e) {
			logger.error(e);
		}
		try {
			schemaMapper.writeValue(fs, jsonSchema);
			logger.info("Schema '"+name+"' written.");
		} catch (JsonGenerationException e) {
			logger.error(e);
		} catch (JsonMappingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
	}
}
