package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JPanel;

import delivery.model.CheckPointType;
import delivery.model.Intersection;
import delivery.model.Path;
import delivery.model.Plan;
import delivery.model.Request;
import delivery.model.Segment;
import delivery.model.Tour;
import observer.Observable;
import observer.Observer;


public class MapView extends JPanel implements Observer{



	private static final long serialVersionUID = 1L;
	private int labelPadding = 0;
	private Tour tour;
	private Plan plan;
	private int padding = 0;
	double xScale;
	double yScale;

	public MapView(Plan plan, Tour tour) {
		this.plan = plan;
		this.tour = tour;
		plan.addObserver(this);
		tour.addObserver(this);
	}



	@Override
	protected void paintComponent(Graphics graph) {
		super.paintComponent(graph);
		Graphics2D g = (Graphics2D) graph;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		double maxLat = plan.getMaxLatitude();
		double minLat = plan.getMinLatitude();
		double maxLon = plan.getMaxLongitude();
		double minLon = plan.getMinLongitude();
		xScale = ((double) getWidth() - 2 * padding - labelPadding) / (maxLat - minLat);
		yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxLon - minLon);

		g.setColor(Color.WHITE);
		//fill the rect
		g.fillRect(padding + labelPadding, padding, getWidth() - (2* padding) - 
				labelPadding, getHeight() - 2 * padding - labelPadding);
		
		g.setStroke(DrawAttributes.getStrokeLine());
		g.setColor(DrawAttributes.getColorLine());
		for (Intersection i : plan.getIntersections()) {
			int x1 = weightLatitude(i.getLatitude(), maxLat, xScale); 
			int y1 = weightLongitude(i.getLongitude(), maxLon, yScale);
			for (Segment s : i.getSegments()) {
				int x2 = weightLatitude(s.getDestination().getLatitude(), maxLat, xScale); 
				int y2 = weightLongitude(s.getDestination().getLongitude(), maxLon, yScale);
				g.drawLine(getWidth() - y1,x1,getWidth() -  y2,x2);
			}
		}
		
		for (Path path : tour.getPath()) {
			drawRoute ( g,  maxLat,  maxLon, path);
		}
		for (Path path : tour.getPath()) {
			if(path.isActive()) {
				drawRoute ( g,  maxLat,  maxLon, path);
			}
		}
		loadRequests(g, maxLat, maxLon);
		
		
	}

	public void loadRequests(Graphics2D g, double maxLat, double maxLon) {
		int i = 0;
		
		for(Request r : plan.getRequests()) {
			Intersection p = r.getPickup().getAddress();
			Intersection d = r.getDelivery().getAddress();
			g.setColor(DrawAttributes.getColorRequest(i));
			drawPoint(g, maxLat, maxLon, p, r.getPickup().getType(),r.getPickup().isActive());
			g.setColor(DrawAttributes.getColorRequest(i));
			drawPoint(g, maxLat, maxLon, d, r.getDelivery().getType(),r.getDelivery().isActive());
			++i;
		}
		
		if (plan.getDepot()!=null) {
			Intersection depot = plan.getDepot().getAddress();
			g.setColor(Color.BLACK);
			drawPoint(g , maxLat, maxLon, depot, plan.getDepot().getType(),plan.getDepot().isActive());
		}
	}
	
	public void drawPoint(Graphics2D g, double maxLat, double maxLon, 
			Intersection point, CheckPointType checkPointType , boolean active) {
		int pointWidth = DrawAttributes.getPointWidth();
		int x = weightLatitude(point.getLatitude(), maxLat, xScale); 
		int y = weightLongitude(point.getLongitude(), maxLon, yScale);
		int x1 = getWidth() - y - pointWidth / 2;
		int y1 = x - pointWidth / 2;
		
		
		switch (checkPointType) {
		case DEPOT:
			g.fillRoundRect(x1, y1, pointWidth, pointWidth, 5, 5);
			setColor(g,active);
			g.drawRoundRect(x1, y1, pointWidth, pointWidth, 5, 5);
			break;
		case PICKUP:
			g.fillRect(x1, y1, pointWidth, pointWidth);
			setColor(g,active);
			g.drawRect(x1, y1, pointWidth, pointWidth);
			break;
		case DELIVERY:
			g.fillOval(x1, y1, pointWidth, pointWidth);
			setColor(g,active);
			g.drawOval(x1, y1, pointWidth, pointWidth);
			break;
		}
		
	}
	
	private void setColor(Graphics2D g, boolean active) {
		// TODO Auto-generated method stub
		if(active) {
			g.setStroke(DrawAttributes.getStrokeActive());
			g.setColor(DrawAttributes.getColorActive());
		}else {
			g.setStroke(DrawAttributes.getStrokePoint());
			g.setColor(Color.BLACK);
		}
		
	}



	public int weightLatitude(double coord, double max, double yScale) {
		return (int) ((max - coord) * yScale + padding);

	}

	public int weightLongitude(double coord, double max, double xScale) {
		return (int) ((max - coord) * xScale + padding);
	}

	
	public void drawRoute(Graphics2D g, double maxLat, double maxLon,Path path) {
		if(path.isActive()) {
			g.setColor(DrawAttributes.getColorPathActive());
		}else {
			g.setColor(DrawAttributes.getColorPath());
		}
		g.setStroke(DrawAttributes.getStrokePath());
		for (Segment s : path.getPath()) {
			int x1 = weightLatitude(s.getOrigin().getLatitude(), maxLat, xScale); 
			int y1 = weightLongitude(s.getOrigin().getLongitude(), maxLon, yScale);
			int x2 = weightLatitude(s.getDestination().getLatitude(), maxLat, xScale); 
			int y2 = weightLongitude(s.getDestination().getLongitude(), maxLon, yScale);
			g.drawLine(getWidth() - y1,x1,getWidth() -  y2,x2);
		}
			
	}
	



	@Override
	public void update(Observable observed, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("MapView Update methode");
		repaint();
	}




}