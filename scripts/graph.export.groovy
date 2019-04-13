// Export graphs to visualize processes
// Version: v1.0.0
// Author: Matthias Wegner

import java.io.File;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.view.graph.ProcessGraph;
import org.rogatio.circlead.view.graph.VoronoiGraph;

        Repository repository = Repository.getInstance();
		
		ProcessGraph v = new ProcessGraph();
		v.addRoles(repository.getRoles());
		v.addProcesses(repository.getActivities());	
		v.layout();
		v.exportSvg("exports"+ File.separatorChar +"processgraph.svg");
		
		VoronoiGraph g = new VoronoiGraph(v);
		g.exportPng("exports"+ File.separatorChar +"voronoigraph.png");

return "sucessfully used graph.export"