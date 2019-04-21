/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view.report;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.util.PropertyUtil;
import org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine;

public class IndexVoronoi extends DefaultReport {

	private final static Logger LOGGER = LogManager.getLogger(IndexVoronoi.class);

	public IndexVoronoi() {
		this.setName("Index Voronoi");
		this.setDescription("Übersicht aller Prozesse in organischer Zelldarstellung");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.DefaultReport#render(org.rogatio.circlead.control.
	 * synchronizer.ISynchronizer)
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		@SuppressWarnings("unused")
		ISynchronizerRendererEngine renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
			element.appendText("Not implementes for Atlassian Synchronizer");
		}

		if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
			try {
				ClassLoader scl = ClassLoader.getSystemClassLoader();
				Class<?> clazzC = scl.loadClass("org.rogatio.circlead.view.items.cellgroup.CellgroupCanvas");

				Constructor<?> c = clazzC.getConstructor();
				Object cellgroupCanvas = c.newInstance();
				Method initMethod = clazzC.getMethod("init");
				initMethod.invoke(cellgroupCanvas);

				Method layoutMethod = clazzC.getMethod("layout");
				layoutMethod.invoke(cellgroupCanvas);

				Method exportMethod = clazzC.getMethod("export", String.class);
				exportMethod.invoke(cellgroupCanvas, PropertyUtil.getInstance().getWebserverDirectory()
						+ File.separatorChar + "" + "processgraph.svg");

				Class<?> clazz = scl.loadClass("org.rogatio.circlead.view.items.voronoi.VoronoiCanvas");

				c = clazz.getConstructor(clazzC);
				Object voronoiCanvas = c.newInstance(cellgroupCanvas);

				layoutMethod = clazz.getMethod("layout");
				layoutMethod.invoke(voronoiCanvas);

				exportMethod = clazz.getMethod("export", String.class);
				exportMethod.invoke(voronoiCanvas, PropertyUtil.getInstance().getWebserverDirectory()
						+ File.separatorChar + "" + "voronoigraph.svg");

				//element.appendElement("img").attr("src", "voronoigraph.svg");
				
				try {
					String svg = new String(Files.readAllBytes(Paths.get(PropertyUtil.getInstance().getWebserverDirectory() + File.separatorChar + "voronoigraph.svg")));
					element.append(svg);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} catch (ClassNotFoundException e) {
				LOGGER.error(e);
			} catch (NoSuchMethodException e) {
				LOGGER.error(e);
			} catch (IllegalAccessException e) {
				LOGGER.error(e);
			} catch (IllegalArgumentException e) {
				LOGGER.error(e);
			} catch (InvocationTargetException e) {
				LOGGER.error(e);
				e.printStackTrace();
			} catch (InstantiationException e) {
				LOGGER.error(e);
			}

//			GraphCanvas canvas = new GraphCanvas();
//			canvas.init();
//			canvas.layout();
//			canvas.export(PropertyUtil.getInstance().getWebserverDirectory() + File.separatorChar + "processgraph.svg");
//
//			VoronoiCanvas vc = new VoronoiCanvas(canvas);
//			vc.layout();
//			vc.export(PropertyUtil.getInstance().getWebserverDirectory() + File.separatorChar + "voronoigraph.svg");
//			
//			element.appendElement("img").attr("style", "position: absolute;").attr("width", "100%").attr("src", "voronoigraph.svg");
		}

		return element;
	}

}
