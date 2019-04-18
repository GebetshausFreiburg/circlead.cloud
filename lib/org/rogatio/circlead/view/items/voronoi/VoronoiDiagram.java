package org.rogatio.circlead.view.items.voronoi;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.rogatio.circlead.view.items.CellType;

import de.alsclo.voronoi.Voronoi;
import de.alsclo.voronoi.graph.Edge;
import de.alsclo.voronoi.graph.Point;
import de.alsclo.voronoi.graph.Vertex;

public class VoronoiDiagram extends Voronoi {

	/* (non-Javadoc)
	 * @see de.alsclo.voronoi.Voronoi#relax()
	 */
	@Override
	public VoronoiDiagram relax() {
		Map<Point, Set<Edge>> edges = new HashMap<>();
		getGraph().getSitePoints().forEach(p -> edges.put(p, new HashSet<>()));
		getGraph().edgeStream().forEach(e -> {
			edges.get(e.getSite1()).add(e);
			edges.get(e.getSite2()).add(e);
		});
		List<Point> newPoints = getGraph().getSitePoints().stream().map(site -> {
			Set<Vertex> vertices = Stream
					.concat(edges.get(site).stream().map(Edge::getA), edges.get(site).stream().map(Edge::getB))
					.collect(Collectors.toSet());
			if (vertices.isEmpty() || vertices.contains(null)) {
				return site;
			} else {
				double avgX = vertices.stream().mapToDouble(v -> v.getLocation().x).average().getAsDouble();
				double avgY = vertices.stream().mapToDouble(v -> v.getLocation().y).average().getAsDouble();
				return new Point(avgX, avgY);
			}
		}).collect(Collectors.toList());
		return new VoronoiDiagram(newPoints);
	}

	/** The cells. */
	private ArrayList<VoronoiCell> cells = new ArrayList<VoronoiCell>();
	
	/**
	 * Gets the cells.
	 *
	 * @return the cells
	 */
	public ArrayList<VoronoiCell> getCells() {
		return cells;
	}
	
	/**
	 * Instantiates a new voronoi extended.
	 *
	 * @param points the points
	 */
	public VoronoiDiagram(Collection<Point> points) {
		super(points);
		
		for (Point site : this.getGraph().getSitePoints()) {

			ArrayList<Edge> edges = new ArrayList<Edge>();
			this.getGraph().edgeStream().filter(e -> e.getA() != null && e.getB() != null).forEach(e -> {
				Edge edge = null;
				if (site.x == e.getSite1().x && site.y == e.getSite1().y) {
					edge = e;
				}
				if (site.x == e.getSite2().x && site.y == e.getSite2().y) {
					edge = e;
				}
				if (edge != null) {
					edges.add(edge);
				}

			});

			VoronoiCell cell = new VoronoiCell(site, edges);

			if (site instanceof CellPoint) {
				CellPoint cp = (CellPoint)site;
				if (cp.getCell().getType()==CellType.ROLE) {
					cell = new RoleCell(site, edges);
				}
				if (cp.getCell().getType()==CellType.ACTIVITY) {
					cell = new ActivityCell(site, edges);
				}
				if (cp.getCell().getType()==CellType.GATEWAY) {
					cell = new GatewayCell(site, edges);
				}
				if (cp.getCell().getType()==CellType.EVENT_START) {
					cell = new EventStartCell(site, edges);
				}
				if (cp.getCell().getType()==CellType.EVENT_END) {
					cell = new EventEndCell(site, edges);
				}
			}
			
			cells.add(cell);
			
		}
	}
	
}
