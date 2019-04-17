// Export graphs to visualize processes
// Version: v1.1
// Author: Matthias Wegner

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.view.items.graph.GraphCanvas;
import org.rogatio.circlead.view.items.voronoi.VoronoiCanvas;

        Repository repository = Repository.getInstance();
		
		GraphCanvas canvas = new GraphCanvas();
		canvas.init();
		canvas.layout();
		canvas.export("exports" + File.separatorChar + "processgraph.svg");

		VoronoiCanvas vc = new VoronoiCanvas(canvas.getCells());
		vc.setBounds(canvas.getBounds());
		vc.setGraphCanvas(canvas);
		vc.layout();
		vc.export("exports" + File.separatorChar + "voronoigraph.svg");

return "sucessfully used graph.export"