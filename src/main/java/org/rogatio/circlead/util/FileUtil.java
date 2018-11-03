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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.net.FileRetrieve;
import com.itextpdf.tool.xml.net.FileRetrieveImpl;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.itextpdf.tool.xml.pipeline.html.LinkProvider;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;

/**
 * The Class FileUtil is a simple helper for File-IO-Handling.
 * 
 * @author Matthias Wegner
 */
public class FileUtil {

	/** The Constant logger. */
	final static Logger LOGGER = LogManager.getLogger(FileUtil.class);

	/**
	 * Delete recursively a directory.
	 *
	 * @param f the f
	 * @throws Exception the exception
	 */
	public static void deleteRecursive(File f) throws Exception {
		try {
			// check for directory
			if (f.isDirectory()) {
				// list content of directory
				for (File c : f.listFiles()) {
					deleteRecursive(c);
				}
			}
			// delete, whatever is found
			if (!f.delete()) {
				throw new Exception("Delete command returned false for file: " + f);
			}
		} catch (Exception e) {
			throw new Exception("Failed to delete the folder: " + f, e);
		}
	}

	/**
	 * Write json-schema from object. Could be used for schema-validation of the
	 * workitem-json-format (FileSynchronizer)
	 *
	 * @param dir   the dir
	 * @param name  the name
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
			// delete schema if it already exists
			fs.delete();
		}
		try {
			// create new empty file
			fs.createNewFile();
		} catch (IOException e) {
			LOGGER.error(e);
		}
		try {
			// write schema-data to new created file
			schemaMapper.writeValue(fs, jsonSchema);
			LOGGER.info("Schema '" + name + "' written.");
		} catch (JsonGenerationException e) {
			LOGGER.error(e);
		} catch (JsonMappingException e) {
			LOGGER.error(e);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

	public static void generateHTMLFromPDF(String htmlInputFile, String pdfOutputFile)
			throws ParserConfigurationException, IOException, DocumentException {
		String CSS_DIR = new File("reports"+File.separatorChar).getAbsolutePath()+File.separatorChar;
		String RELATIVE_PATH = new File("reports"+File.separatorChar).getAbsolutePath()+File.separatorChar;
		String IMG_PATH = new File("reports"+File.separatorChar).getAbsolutePath()+File.separatorChar;

		// step 1
		Document document = new Document();
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfOutputFile));
		// step 3
		document.open();
		// step 4

		// CSS
		CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(false);
		FileRetrieve retrieve = new FileRetrieveImpl(CSS_DIR);
		cssResolver.setFileRetrieve(retrieve);

		// HTML
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
		htmlContext.setImageProvider(new AbstractImageProvider() {
			public String getImageRootPath() {
				return IMG_PATH;
			}
		});
		htmlContext.setLinkProvider(new LinkProvider() {
			public String getLinkRoot() {
				return RELATIVE_PATH;
			}
		});

		// Pipelines
		PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
		HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
		CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

		// XML Worker
		XMLWorker worker = new XMLWorker(css, true);
		XMLParser p = new XMLParser(worker);
		p.parse(new FileInputStream(htmlInputFile));

		// step 5
		document.close();

	}

	public static void generateHTMLFromPDFSimple(String htmlInputFile, String pdfOutputFile)
			throws ParserConfigurationException, IOException, DocumentException {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfOutputFile));
		document.open();
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(htmlInputFile));
		document.close();

	}
	
	public static void copyFileOrFolder(File source, File dest) throws IOException {
		CopyOption[] options = new CopyOption[] {StandardCopyOption.REPLACE_EXISTING};
		
	    if (source.isDirectory())
	        copyFolder(source, dest, options);
	    else {
	        ensureParentFolder(dest);
	        copyFile(source, dest, options);
	    }
	}
	
	public static void copyFileOrFolder(File source, File dest, CopyOption...  options) throws IOException {
	    if (source.isDirectory())
	        copyFolder(source, dest, options);
	    else {
	        ensureParentFolder(dest);
	        copyFile(source, dest, options);
	    }
	}

	private static void copyFolder(File source, File dest, CopyOption... options) throws IOException {
	    if (!dest.exists())
	        dest.mkdirs();
	    File[] contents = source.listFiles();
	    if (contents != null) {
	        for (File f : contents) {
	            File newFile = new File(dest.getAbsolutePath() + File.separator + f.getName());
	            if (f.isDirectory())
	                copyFolder(f, newFile, options);
	            else
	                copyFile(f, newFile, options);
	        }
	    }
	}

	private static void copyFile(File source, File dest, CopyOption... options) throws IOException {
	    Files.copy(source.toPath(), dest.toPath(), options);
	}

	private static void ensureParentFolder(File file) {
	    File parent = file.getParentFile();
	    if (parent != null && !parent.exists())
	        parent.mkdirs();
	} 
}
