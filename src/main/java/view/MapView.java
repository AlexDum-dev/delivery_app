package view;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;

import java.awt.event.MouseWheelEvent;

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
	double maxLat ;
	double minLat ;
	double maxLon ;
	double minLon ;

	public MapView(Plan plan, Tour tour) {
		this.plan = plan;
		this.tour = tour;
		plan.addObserver(this);
		tour.addObserver(this);
		this.maxLat = plan.getMaxLatitude();
		this.minLat = plan.getMinLatitude();
		this.maxLon = plan.getMaxLongitude();
		this.minLon = plan.getMinLongitude();
		this.addMouseWheelListener(e -> {
            System.out.println("MouseWheelListenerDemo.mouseWheelMoved");

            // If wheel rotation value is a negative it means rotate up, while
            // positive value means rotate down

            int x = e.getX();
			int y = e.getY();
			System.out.println(x + "," + y);
			
            if (e.getWheelRotation() < 0) {
                System.out.println("Rotated Up... " + e.getWheelRotation());
				zoomIn(x,y,90);
            } else {
                System.out.println("Rotated Down... " + e.getWheelRotation());
				zoomIn(x,y,110);
            }
        });

	}

	

	@Override
	protected void paintComponent(Graphics graph) {
		super.paintComponent(graph);
		Graphics2D g = (Graphics2D) graph;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		/*this.maxLat = plan.getMaxLatitude();
		this.minLat = plan.getMinLatitude();
		this.maxLon = plan.getMaxLongitude();
		this.minLon = plan.getMinLongitude();*/
		System.out.println("Constructor "+plan.getIntersections().size());
		xScale = (double) getWidth()  / (maxLat - minLat);
		yScale = (double) getHeight() / (maxLon - minLon);
		System.out.println("maxLat : "+maxLat);
		System.out.println("minLat : "+minLat);
		System.out.println("maxLon : "+maxLon);
		System.out.println("minLon : "+minLon);
		System.out.println("xScale : "+xScale);
		System.out.println("yScale : "+yScale);

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

	public double getLatitudeFromY(int y) {
		return maxLat - (y/xScale);
	}
	public double getLongitudeFromX(int x) {
		return maxLon - (((double) getWidth()-x)/yScale);
	}
	
	
	public void zoomIn (int x , int y ,int p) {
//		//zoom = 172;
//		int zoom = (int)(getHeight() * p /100);
//		System.out.println("Zoom = "+ zoom);
//		int x1 = x-zoom/2;
////		if(x1<0) x1 =0;
////		if (x1>getWidth()) {x1=getWidth();}
//		
//		int x2 = x+zoom/2;
////		if (x2>getWidth()) {x2=getWidth();}
////		if(x2<0) {x2 = 0;}
//		
//		int y1 = y-zoom/2;
////		if(y1<0) y1 = 0;
////		if (y1>getHeight()) {y1=getHeight();}
//		
//		int y2 = y+zoom/2;
////		if (y2<0) y2=0;
////		if (y2>getHeight()) {y2=getHeight();}
//		
//		this.setMaxLat(maxLat - (y1/(xScale)));//y1
//		this.setMaxLon(maxLon - (((double) getWidth()-x2)/(yScale))); // la premiere valeur X1
//		this.setMinLat( maxLat - (y2/xScale));
//		this.setMinLon( maxLon - ((double) getWidth()-x1)/yScale); // la premiere valeur X1
		
		double centLat = (minLat+maxLat)/2;
		double centLon = (minLon+maxLon)/2;
		double lenLat = (maxLat-minLat)*p/100.0;
		double lenLon = (maxLon-minLon)*p/100.0;
		double pointLat = getLatitudeFromY(y);
		double pointLon = getLongitudeFromX(x);
		
		centLat += (pointLat-centLat)*(1-p/100.0);
		centLon += (pointLon-centLon)*(1-p/100.0);
		
		minLat = centLat-lenLat/2;
		maxLat = centLat+lenLat/2;
		minLon = centLon-lenLon/2;
		maxLon = centLon+lenLon/2;
		
		this.update(plan, null);
	}
	
	
	public double getMaxLat() {
		return maxLat;
	}



	public void setMaxLat(double maxLat) {
		this.maxLat = maxLat;
	}



	public double getMinLat() {
		return minLat;
	}



	public void setMinLat(double minLat) {
		this.minLat = minLat;
	}



	public double getMaxLon() {
		return maxLon;
	}



	public void setMaxLon(double maxLon) {
		this.maxLon = maxLon;
	}



	public double getMinLon() {
		return minLon;
	}



	public void setMinLon(double minLon) {
		this.minLon = minLon;
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
		
		System.out.println("x1 = "+x1+"\t y1 = "+y1);
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



	public int weightLatitude(double coord, double max, double xScale) {
		return (int) ((max - coord) * xScale + padding);

	}

	public int weightLongitude(double coord, double max, double yScale) {
		return (int) ((max - coord) * yScale + padding);
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
	


	public void zoomOut() {
		this.maxLat = plan.getMaxLatitude();
		this.minLat = plan.getMinLatitude();
		this.maxLon = plan.getMaxLongitude();
		this.minLon = plan.getMinLongitude();
	}
	@Override
	public void update(Observable observed, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("MapView Update methode");
		
		repaint();
	}




}