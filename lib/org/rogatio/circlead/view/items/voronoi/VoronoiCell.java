package org.rogatio.circlead.view.items.voronoi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.rogatio.circlead.view.items.DefaultCell;
import org.rogatio.circlead.view.items.ICell;
import org.rogatio.circlead.view.items.graph.GraphCanvas;
import org.rogatio.circlead.view.items.graph.GraphCell;

import de.alsclo.voronoi.graph.Edge;
import de.alsclo.voronoi.graph.Point;

/**
 * The Class VoronoiCell.
 */
public class VoronoiCell extends DefaultCell implements Shape {
	/** The path. */
	private GeneralPath path = new GeneralPath();

	/** The edges. */
	private ArrayList<Edge> edges;

	/** The points. */
	private List<Point> points = new ArrayList<Point>();

	/** The center. */
	private Point center;

	/**
	 * Gets the edges.
	 *
	 * @return the edges
	 */
	public ArrayList<Edge> getEdges() {
		return edges;
	}

	/**
	 * Gets the points.
	 *
	 * @return the points
	 */
	public List<Point> getPoints() {
		return points;
	}

	/**
	 * Gets the center.
	 *
	 * @return the center
	 */
	public Point getCenter() {
		return center;
	}

	protected Color background = new Color(255, 255, 255, 0);
	
	protected Graphics2D graphics;
	
	public void setGraphics(Graphics2D g) {
		this.graphics = g;
	}
	
	@Override
	public Object create() {
		
		double POINT_SIZE = 5.0;
		
		if (this.getCenter() instanceof CellPoint) {
			Color c = background;
			c = (Color) this.getDataCell().getData("color");
			this.graphics.setStroke(new BasicStroke());
			this.graphics.setPaint(c);
			this.graphics.fill(this);
		} else {
			
		}
		
		graphics.setPaint(background);
		double size = 0.5;
		graphics.fillOval((int) Math.round(this.getCenter().x - (int) (size * POINT_SIZE / 2)),
				(int) Math.round(this.getCenter().y - (int) (size * POINT_SIZE / 2)), (int) (size * POINT_SIZE),
				(int) (size * POINT_SIZE));
		
		return null;
	}
	
	/**
	 * Gets the data cell.
	 *
	 * @return the data cell
	 */
	public ICell getDataCell() {
		if (center instanceof CellPoint) {
			CellPoint c = (CellPoint)center;
			return c.getCell();
		}
		return null;
	}
	
	/**
	 * Instantiates a new voronoi cell.
	 *
	 * @param center the center
	 * @param edges  the edges
	 */
	public VoronoiCell(Point center, ArrayList<Edge> edges) {
		
		this.center = center;

		for (Edge edge : edges) {
			Point a = edge.getA().getLocation();
			Point b = edge.getB().getLocation();

			if (!points.contains(a)) {
				points.add(a);
			}
			if (!points.contains(b)) {
				points.add(b);
			}
		}

		this.edges = edges;

		if (points.size() == 0) {
			return;
		}

		points = sortVertices(points);

		if (points.size() > 0) {
			path.moveTo(points.get(0).x, points.get(0).y);
			for (int i = 1; i < points.size(); i++) {
				Point p = points.get(i);
				path.lineTo(p.x, p.y);
			}
			path.closePath();

			double cx = path.getBounds2D().getCenterX();
			double cy = path.getBounds2D().getCenterY();
			double zoom = 0.98;

			AffineTransform tr = new AffineTransform();
			tr.translate(-cx, -cy);
			path.transform(tr);
			
			tr = new AffineTransform();
			tr.scale(zoom, zoom);
			path.transform(tr);
			
			tr = new AffineTransform();
			tr.translate(+cx, +cy);
			path.transform(tr);
			
		}

	}

	/**
	 * Find centroid.
	 *
	 * @param points the points
	 * @return the point
	 */
	private Point findCentroid(List<Point> points) {

		int x = 0;
		int y = 0;
		for (Point p : points) {
			x += p.x;
			y += p.y;
		}
		int cx = x / points.size();
		int cy = y / points.size();
		return new Point(cx, cy);
	}

	/**
	 * Sort vertices.
	 *
	 * @param points the points
	 * @return the list
	 */
	private List<Point> sortVertices(List<Point> points) {
		Point center = findCentroid(points);
		Collections.sort(points, (a, b) -> {
			double a1 = (Math.toDegrees(Math.atan2(a.x - center.x, a.y - center.y)) + 360) % 360;
			double a2 = (Math.toDegrees(Math.atan2(b.x - center.x, b.y - center.y)) + 360) % 360;
			return (int) (a1 - a2);
		});
		return points;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Shape#getBounds()
	 */
	@Override
	public java.awt.Rectangle getBounds() {
		return path.getBounds();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Shape#getBounds2D()
	 */
	@Override
	public Rectangle2D getBounds2D() {
		return path.getBounds2D();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Shape#contains(double, double)
	 */
	@Override
	public boolean contains(double x, double y) {
		return path.contains(x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Shape#contains(java.awt.geom.Point2D)
	 */
	@Override
	public boolean contains(Point2D p) {
		return path.contains(p);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Shape#intersects(double, double, double, double)
	 */
	@Override
	public boolean intersects(double x, double y, double w, double h) {
		return path.intersects(x, y, w, h);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Shape#intersects(java.awt.geom.Rectangle2D)
	 */
	@Override
	public boolean intersects(Rectangle2D r) {
		return path.intersects(r);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Shape#contains(double, double, double, double)
	 */
	@Override
	public boolean contains(double x, double y, double w, double h) {
		return path.contains(x, y, w, h);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Shape#contains(java.awt.geom.Rectangle2D)
	 */
	@Override
	public boolean contains(Rectangle2D r) {
		return path.contains(r);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Shape#getPathIterator(java.awt.geom.AffineTransform)
	 */
	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		return path.getPathIterator(at);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Shape#getPathIterator(java.awt.geom.AffineTransform, double)
	 */
	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return path.getPathIterator(at, flatness);
	}
}
