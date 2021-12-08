package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import model.CheckPoint;
import model.CheckPointType;
import model.Intersection;
import model.Path;
import model.Plan;
import model.Request;
import model.Segment;
import model.Tour;
import observer.Observable;
import observer.Observer;

/**
 * Map View
 * Displays the map of the city
 * and the delivery tour
 * 
 * @author 4IF Group H4144
 * @version 1.0 7 Dec 2021
 */
public class MapView extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private Tour tour;
	private Plan plan;
	double xScale;
	double yScale;
	int zoomLevel;
	double xMax;
	double xMin;
	double yMax;
	double yMin;
	CheckPoint pickupToAdd = null;

	/**
	 * Constructor of the MapView
	 * 
	 * @param plan
	 * @param tour
	 * @param c
	 * @param msgRoadName
	 */
	public MapView(Plan plan, Tour tour, Controller c, JLabel msgRoadName) {
		this.plan = plan;
		this.tour = tour;
		plan.addObserver(this);
		tour.addObserver(this);
		this.xMax = 1;
		this.xMin = 0;
		this.yMax = 1;
		this.yMin = 0;
		this.zoomLevel = 0;
		setMouseEvents(plan, c, msgRoadName);
	}

	/**
	 * Set the mouse events for this map view
	 * 
	 * @param plan
	 * @param c the controller
	 * @param msgRoadName a label to display road names
	 */
	private void setMouseEvents(Plan plan, Controller c, JLabel msgRoadName) {
		this.addMouseWheelListener(e -> {
			// If wheel rotation value is a negative it means rotate up, while
			// positive value means rotate down

			int x = e.getX();
			int y = e.getY();

			if (e.getWheelRotation() < 0) {
				if ( zoomLevel < 300) {
					zoomLevel += 10;
					zoom(x,y,90);
				}
			} else {
				if(zoomLevel>= -30) {
					zoomLevel -= 10;
					zoom(x,y,110);
				}else {
					adjustZoom();
				}
			}

			repaint();
		});

		setMouseAdapter(plan, c, msgRoadName);
	}

	/**
	 * Sets the mouse adapter component of this map view
	 * 
	 * @param plan
	 * @param c the controller
	 * @param msgRoadName a label to display road names
	 */
	private void setMouseAdapter(Plan plan, Controller c, JLabel msgRoadName) {
		MouseAdapter mouseAdapter = new MouseAdapter() { 
			@Override
			public void mouseMoved(MouseEvent e) { 
				int x = e.getX();
				int y = e.getY();
				Segment seg = null;
				double min = Double.MAX_VALUE;
				for (Intersection i : plan.getIntersections()) {
					int x1 = weightLongitude(i.getLongitude());
					int y1 = getHeight() - weightLatitude(i.getLatitude()); 
					for (Segment s : i.getSegments()) {
						int x2 = weightLongitude(s.getDestination().getLongitude());
						int y2 = getHeight() - 
								weightLatitude(s.getDestination().getLatitude()); 
						if ( isBetween(x1,y1,x2,y2,x,y,20) ) {
							double newMin = calculeSegment(x1,y1,x2,y2,x,y);
							if (newMin < min  ) {
								min = newMin;
								seg = s;
							}
						}

					}
				}
				if (min < 20 && seg != null ) {
					msgRoadName.setText(seg.getName());
				} else {
					msgRoadName.setText("");
				}
			}
			private boolean isBetween(int ax, int ay, int bx, int by, 
					int cx, int cy, int eps) {
				double distanceAC = Math.sqrt( Math.pow(ax - cx, 2) + 
						Math.pow(ay - cy, 2) );
				
				double distanceBC = Math.sqrt( Math.pow(bx - cx, 2) + 
						Math.pow(by - cy, 2) );
				
				double distanceAB = Math.sqrt( Math.pow(bx - ax, 2) + 
						Math.pow(by - ay, 2) );
				
				if ( Math.abs( distanceAB - distanceBC - distanceAC ) < eps) {
					return true;
				}
				return false;
			}
			private double calculeSegment(int ax, int ay, int bx, int by, 
					int cx, int cy) {
				double m = 0;
				if ((bx-ax) != 0) {
					m = (by-ay)/(bx-ax);
				}

				double b = ay - m*ax;
				return Math.abs(cy - (m*cx +b));
			}
			@Override
			public void mouseClicked(MouseEvent e) { 
				int x = e.getX();
				int y = getHeight() - e.getY();
				double lat = translateToLatitude(y/yScale+yMin);
				double lon = translateToLongitude(x/xScale+xMin);
				c.clickOnMap(lat, lon);
			}


		};
		this.addMouseListener(mouseAdapter); 
		addMouseMotionListener(mouseAdapter);
	}

	@Override
	protected void paintComponent(Graphics graph) {
		super.paintComponent(graph);
		Graphics2D g = (Graphics2D) graph;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		xScale = (double) getWidth()  / (xMax - xMin);
		yScale = (double) getHeight() / (yMax - yMin);
		g.setColor(Color.WHITE);

		g.fillRect(0, 0, getWidth(), getHeight());

		drawSegments(g);

		for (Path path : tour.getPathList()) {
			drawRoute ( g,  path);
		}
		for (Path path : tour.getPathList()) {
			if(path.isActive()) {
				drawRoute ( g,  path);
			}
		}
		drawRequests(g);


	}

	private void drawSegments(Graphics2D g) {
		g.setStroke(DrawAttributes.getStrokeLine());
		g.setColor(DrawAttributes.getColorLine());
		for (Intersection i : plan.getIntersections()) {
			int x1 = weightLongitude(i.getLongitude());
			int y1 = weightLatitude(i.getLatitude());
			for (Segment s : i.getSegments()) {
				int x2 = weightLongitude(s.getDestination().getLongitude());
				int y2 = weightLatitude(s.getDestination().getLatitude()); 
				if(s.isActive()) {
					g.setColor(DrawAttributes.getColorPathActive());
				}else {
					g.setColor(Color.BLACK);
				}
				g.drawLine(x1, getHeight()-y1, x2, getHeight()-y2);
			}
		}
	}

	private void drawRequests(Graphics2D g) {
		int i = 0;
		for(Request r : plan.getRequests()) {
			Intersection p = r.getPickup().getAddress();
			Intersection d = r.getDelivery().getAddress();
			g.setColor(DrawAttributes.getColorRequest(i));
			drawPoint(g, p, r.getPickup().getType(), r.getPickup().isActive());
			g.setColor(DrawAttributes.getColorRequest(i));
			drawPoint(g, d, r.getDelivery().getType(), r.getDelivery().isActive());
			++i;
		}

		if (plan.getDepot()!=null) {
			Intersection depot = plan.getDepot().getAddress();
			g.setColor(Color.BLACK);
			drawPoint(g , depot, plan.getDepot().getType(), plan.getDepot().isActive());
		}

		if (pickupToAdd!=null) {
			Intersection pk = pickupToAdd.getAddress();
			g.setColor(Color.WHITE);
			drawPoint(g , pk, pickupToAdd.getType(), true);
		}
	}

	public void drawPoint(Graphics2D g, Intersection point, 
			CheckPointType checkPointType, boolean active) {
		int pointWidth = DrawAttributes.getPointWidth();
		int x = weightLongitude(point.getLongitude());
		int y = weightLatitude(point.getLatitude()); 
		int x1 = x - pointWidth / 2;
		int y1 = getHeight() - y - pointWidth / 2;

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
		if(active) {
			g.setStroke(DrawAttributes.getStrokeActive());
			g.setColor(DrawAttributes.getColorActive());
		}else {
			g.setStroke(DrawAttributes.getStrokePoint());
			g.setColor(Color.BLACK);
		}

	}

	public void drawRoute(Graphics2D g, Path path) {
		if(path.isActive()) {
			g.setColor(DrawAttributes.getColorPathActive());
		}else {
			g.setColor(DrawAttributes.getColorPath());
		}
		g.setStroke(DrawAttributes.getStrokePath());
		for (Segment s : path.getPath()) {
			int x1 = weightLongitude(s.getOrigin().getLongitude());
			int y1 = weightLatitude(s.getOrigin().getLatitude()); 
			int x2 = weightLongitude(s.getDestination().getLongitude());
			int y2 = weightLatitude(s.getDestination().getLatitude()); 
			g.drawLine(x1,getHeight() - y1,x2,getHeight() - y2);
		}

	}
	
	public double translateToCoordsX(double lon) {
		return Math.toRadians(lon);

	}
	public double translateToCoordsY(double lat) {
		return 5.0/4 * Math.log(Math.tan(Math.PI/4+2*Math.toRadians(lat)/5));
	}

	public double translateToLongitude(double x) {
		return Math.toDegrees(x);
	}
	public double translateToLatitude(double d) {
		return Math.toDegrees(5.0/4 * Math.atan(Math.sinh(4*d/5.0)));
	}

	public int weightLatitude(double lat) {
		return (int) ((translateToCoordsY(lat) - yMin) * yScale);

	}

	public int weightLongitude(double lon) {
		return (int) ((translateToCoordsX(lon) - xMin) * xScale);
	}

	/**
	 * Adjusts the zoom so it fits the whole map.
	 */
	public void adjustZoom() {
		this.xMax = translateToCoordsX(plan.getMaxLongitude());
		this.xMin = translateToCoordsX(plan.getMinLongitude());
		this.yMax = translateToCoordsY(plan.getMaxLatitude());
		this.yMin = translateToCoordsY(plan.getMinLatitude());

		if (xMax-xMin>yMax-yMin) {
			double centLon = (yMax+yMin)/2;
			double widthLon = (xMax-xMin);
			this.yMin = centLon - widthLon / 2;
			this.yMax = centLon + widthLon / 2;
		} else {
			double centLat = (xMax+xMin)/2;
			double widthLat = (yMax-yMin);
			this.xMin = centLat - widthLat / 2;
			this.xMax = centLat + widthLat / 2;
		}
		this.zoomLevel = 0;
	}

	/**
	 * Zooms in the map view
	 * 
	 * @param x coordinate from which to zoom
	 * @param y coordinate from which to zoom
	 * @param p zoom percentage
	 */
	public void zoom(int x , int y ,int p) {
		double centX = (xMin+xMax)/2;
		double centY = (yMin+yMax)/2;
		double lenX = (xMax-xMin)*p/100.0;
		double lenY = (yMax-yMin)*p/100.0;

		centX += ((x/xScale+xMin)-centX)*(1-p/100.0);
		centY -= ((y/yScale+yMin)-centY)*(1-p/100.0);

		xMin = centX-lenX/2;
		xMax = centX+lenX/2;
		yMin = centY-lenY/2;
		yMax = centY+lenY/2;
	}

	@Override
	public void update(Observable observed, Object arg) {
		repaint();
	}

	public CheckPoint getPickupToAdd() {
		return pickupToAdd;
	}

	public void setPickupToAdd(CheckPoint pickupToAdd) {
		this.pickupToAdd = pickupToAdd;
	}
}