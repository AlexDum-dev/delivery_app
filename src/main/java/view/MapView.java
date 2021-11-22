package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import delivery.model.Intersection;
import delivery.model.Path;
import delivery.model.Plan;
import delivery.model.Segment;
import observer.Observable;
import observer.Observer;


public class MapView extends JPanel implements Observer{



	private static final long serialVersionUID = 1L;
	private int labelPadding = 0;
	/**change the line color to the best you want;*/
	private Color lineColor = new Color(0,0,0);
	private Color pointColorPickUp = new Color(204,0 ,0 );
	private Color pointColorDelivery= new Color(0,153 ,0 );
	private Color gridColor = new Color(200, 200, 200, 200);
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	private static int pointWidth = 15;
	private int numberYDivisions = 0;
	private Plan plan;
	private int padding = 0;
	double xScale;
	double yScale;


	/**
	 * Math_Graph is a constructor method
	 * @returns List scores;
	 */
	public MapView(Plan plan) {
		this.plan = plan;
		plan.addObserver(this);
		System.out.println("TEST constructeur map");
		//this.xScale = ((double) getHeight() - 2 * padding - labelPadding) / (Intersection.getMaxLongitude(plan.getIntersections()) - Intersection.getMinLongitude(plan.getIntersections()));
		//this.yScale = ((double) getHeight() - 2 * padding - labelPadding) / (Intersection.getMaxLatitude(plan.getIntersections()) - Intersection.getMinLatitude(plan.getIntersections()));
	}



	@Override
	protected void paintComponent(Graphics g) {
		System.out.println("Test fonction paintComponent");
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		double maxLat = plan.getMaxLatitude();
		double minLat = plan.getMinLatitude();
		double maxLon = plan.getMaxLongitude();
		double minLon = plan.getMinLongitude();
		xScale = ((double) getWidth() - 2 * padding - labelPadding) / (maxLat - minLat);
		yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxLon - minLon);

		g2.setColor(Color.WHITE);
		//fill the rect
		g2.fillRect(padding + labelPadding, padding, getWidth() - (2* padding) - 
				labelPadding, getHeight() - 2 * padding - labelPadding);
		g2.setColor(Color.BLUE);

		Stroke oldStroke = g2.getStroke(); //keep in memory the stroke ("state") to draw points

		g2.setColor(pointColorPickUp);
		g2.setStroke(GRAPH_STROKE);
		g2.setColor(lineColor);
		for (Intersection i : plan.getIntersections()) {
			int x1 = weightLatitude(i.getLatitude(), maxLat, xScale); 
			int y1 = weightLongitude(i.getLongitude(), maxLon, yScale);
			for (Segment s : i.getSegments()) {
				int x2 = weightLatitude(s.getDestination().getLatitude(), maxLat, xScale); 
				int y2 = weightLongitude(s.getDestination().getLongitude(), maxLon, yScale);
				g2.drawLine(getWidth() - y1,x1,getWidth() -  y2,x2);
			}
		}
		g2.setStroke(oldStroke);
		
		if (!plan.getIntersections().isEmpty()) {
			List <Segment> listSeg = plan.getIntersections().get(0).getSegments();
			Path path = new Path(listSeg);
			drawRoute ( g2,  maxLat,  maxLon, path);
		}
		loadRequests(g2, maxLat, maxLon);
		
		
	}

	public void loadRequests(Graphics2D g2, double maxLat, double maxLon) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);



		List<delivery.model.Request> requests = this.plan.getRequests();
		List<Point> graphPoints2 = new ArrayList<>();
		for(int i = 0 ; i<requests.size();i++) {
			int x1 = weightLatitude(requests.get(i).getPickup().getAddress().getLatitude(), maxLat, xScale); 
			int y1 = weightLongitude(requests.get(i).getPickup().getAddress().getLongitude(), maxLon, yScale);

			int x2 = weightLatitude(requests.get(i).getDelivery().getAddress().getLatitude(), maxLat, xScale); 
			int y2 = weightLongitude(requests.get(i).getDelivery().getAddress().getLongitude(), maxLon, yScale);
			graphPoints2.add(new Point(getWidth() - y1, x1));
			graphPoints2.add(new Point(getWidth() - y2, x2));
		}

		for (int i = 0; i < graphPoints2.size(); i++) {
			if (i % 2 == 0) {
				//pickup color
				g2.setColor(pointColorPickUp);
			}else {
				g2.setColor(pointColorDelivery);
			}
			int x = graphPoints2.get(i).x - pointWidth / 2;
			int y = graphPoints2.get(i).y - pointWidth / 2;
			int ovalW = pointWidth;
			int ovalH = pointWidth;
			g2.fillOval(x, y, ovalW, ovalH);
		}
		g2.fillOval(770, 390, pointWidth, pointWidth);
		
		
	}
	
	public int weightLatitude(double coord, double max, double yScale) {
		return (int) ((max - coord) * yScale + padding);

	}

	public int weightLongitude(double coord, double max, double xScale) {
		return (int) ((max - coord) * xScale + padding);
	}

	
	public void drawRoute(Graphics2D g2, double maxLat, double maxLon,Path path) {
			g2.setColor(pointColorPickUp);
			g2.setStroke(GRAPH_STROKE);
			g2.setColor(new java.awt.Color(51, 153, 255));
			for (Segment s : path.getPath()) {
				int x1 = weightLatitude(s.getOrigin().getLatitude(), maxLat, xScale); 
				int y1 = weightLongitude(s.getOrigin().getLongitude(), maxLon, yScale);
				int x2 = weightLatitude(s.getDestination().getLatitude(), maxLat, xScale); 
				int y2 = weightLongitude(s.getDestination().getLongitude(), maxLon, yScale);
				g2.drawLine(getWidth() - y1,x1,getWidth() -  y2,x2);
			}
	}
	
	
	@Override
	public void update(Observable observed, Object arg) {
		// TODO Auto-generated method stub
		repaint();
	}




}