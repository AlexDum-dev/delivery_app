package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.runner.Request;
import org.xml.sax.SAXException;

import delivery.model.Intersection;
import delivery.model.Plan;
import delivery.model.Segment;
import delivery.model.XMLParser;
import delivery.model.XMLParserException;
 

public class MapView extends JPanel{

    
 
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
        System.out.println("TEEST constructeur map");
        //this.xScale = ((double) getHeight() - 2 * padding - labelPadding) / (Intersection.getMaxLongitude(plan.getIntersections()) - Intersection.getMinLongitude(plan.getIntersections()));
        //this.yScale = ((double) getHeight() - 2 * padding - labelPadding) / (Intersection.getMaxLatitude(plan.getIntersections()) - Intersection.getMinLatitude(plan.getIntersections()));
    }

   

	@Override
    protected void paintComponent(Graphics g) {
		System.out.println("Test fonction paintComponent");
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Map<String,Intersection> mapIntersection = plan.getIntersections();
        
         xScale = ((double) getWidth() - 2 * padding - labelPadding) / (Plan.getMaxLatitude(plan.getIntersections()) - Plan.getMinLatitude(plan.getIntersections()));
         yScale = ((double) getHeight() - 2 * padding - labelPadding) / (Plan.getMaxLongitude(plan.getIntersections()) - Plan.getMinLongitude(plan.getIntersections()));
        List<Point> graphPoints = new ArrayList<>();
        Set<String> setInterId = plan.getIntersections().keySet();
        for (String id : setInterId) {
            int x1 = weightLatitude(mapIntersection.get(id).getLatitude(), xScale); 
            int y1 = weightLongitude(mapIntersection.get(id).getLongitude(), yScale);
            //System.out.println("Create point  : x "+x1+" y "+y1);
        	//int x1 = (int) ((Intersection.getMaxLongitude(plan.getIntersections()) - mapIntersection.get(id).getLongitude()) * xScale + padding);
        	//int y1 = (int) ((Intersection.getMaxLatitude(plan.getIntersections()) - mapIntersection.get(id).getLatitude()) * yScale + padding);
            graphPoints.add(new Point(getWidth() - y1, getHeight() - x1));
        }

        g2.setColor(Color.WHITE);
	    //fill the rect
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2* padding) - 
        		labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLUE);
        
        /*
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 -
            		labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (mapIntersection.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinScore() + (getMaxScore() - getMinScore()) * 
                		((i * 8.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 6, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }
       

        for (int i = 0; i < mapIntersection.size(); i++) {
            if (mapIntersection.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (scores.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((scores.size() / 8.0)) + 3)) == 0) { 
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
            
        }
		
		*/
   
        //g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        //g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() -
        //		padding, getHeight() - padding - labelPadding);
        
        Stroke oldStroke = g2.getStroke(); //keep in memory the stroke ("state") to draw points
        
        g2.setColor(pointColorPickUp);
        g2.setStroke(GRAPH_STROKE);
        List<Double[]> listLineDouble = Plan.getAllLine(plan.getSegments());
        List<Point[]> listLinePoint = weightAllPoint(listLineDouble, xScale, yScale); //
        g2.drawLine(getWidth() - 5,getHeight() - 5,getWidth() -  5,getHeight() - 100);
        g2.drawLine(getWidth() - 5,5,getWidth() -  5,100);
        g2.setColor(lineColor);
        for(Point[] line : listLinePoint) {
        	//System.out.println("Create line : x1 "+(int) line[0].getX()+" y1 "+ (int) line[0].getY() + " x2 "+ (int) line[1].getX() + " y2 "+ line[1].getY());
        	
        	//g2.drawLine(((int) line[0].getX()),((int) line[0].getY()),((int) line[1].getX()),((int) line[1].getY()));
        	//g2.drawLine(((int) line[0].getY()),((int) line[0].getX()),((int) line[1].getY()),((int) line[1].getX()));
        	//g2.drawLine(getWidth() - ((int) line[0].getX()),getHeight() - ((int) line[0].getY()),getWidth() -  ((int) line[1].getX()),getHeight() - ((int) line[1].getY()));
        	g2.drawLine(getWidth() - ((int) line[0].getY()),((int) line[0].getX()),getWidth() -  ((int) line[1].getY()),((int) line[1].getX()));
        	
        	
        	//System.out.println("X1 : "+line[0].getX()+" Y1 "+line[0].getY()+" X2 : "+line[1].getX()+" Y2 "+line[1].getY());
        }
        g2.setStroke(oldStroke);
        /*
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }
        
        */
       /* g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }*/
        /*  Requests */
        
		List<delivery.model.Request> requests = plan.getRequests();
		List<Point> graphPoints2 = new ArrayList<>();
		for(int i = 0 ; i<requests.size();i++) {
			 int x1 = weightLatitude(requests.get(i).getPickup().getAddress().getLatitude(), xScale); 
	         int y1 = weightLongitude(requests.get(i).getPickup().getAddress().getLongitude(), yScale);
	         
	         int x2 = weightLatitude(requests.get(i).getDelivery().getAddress().getLatitude(), xScale); 
	         int y2 = weightLongitude(requests.get(i).getDelivery().getAddress().getLongitude(), yScale);
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
		
        
        
    }
/* creating the method createAndShowGui in the main method, where we create the frame too and pack it in the panel*/
    private static void createAndShowGui() throws NumberFormatException, ParserConfigurationException, IOException, SAXException, XMLParserException {
    	/*
        List<Double> scores = new ArrayList<>();
        Random random = new Random();
        int maxDataPoints = 20;
        int maxScore = 8;
        for (int i = 0; i < maxDataPoints; i++) {
            scores.add((double) random.nextDouble() * maxScore);
       
        }
        */
        
    	JFrame frame = new JFrame("((int) line[0].getY()),((int) line[0].getX()),((int) line[1].getY()),((int) line[1].getX())");
    	frame.setBounds(100, 100, 866, 562);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Get Points :
        File file = new File("C:\\\\Users\\\\PC Lenovo\\\\Desktop\\\\fichiersXML2020\\\\largeMap.xml");
        File fileR = new File("C:\\\\Users\\\\PC Lenovo\\\\Desktop\\\\fichiersXML2020\\\\requestsLarge7.xml");
        Plan plan = new Plan();
        XMLParser.loadPlan(file, plan);
        XMLParser.loadRequests(fileR, plan);
	    /* Main panel */
        MapView mainPanel = new MapView(plan);
        mainPanel.setPreferredSize(new Dimension(1300, 1200));
        frame.setBounds(100, 100, 1100, 780);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
        
		
       /* creating the frame */
        
        frame.getContentPane().add(mainPanel);
        frame.setBounds(100, 100, 800, 800);
      	frame.setResizable(false);
      	frame.setLocationRelativeTo(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
      /*the main method runs createAndShowGui*/
    public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            try {
				createAndShowGui();
			} catch (NumberFormatException | ParserConfigurationException | IOException | SAXException
					| XMLParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
      });}
    
    public int weightLatitude(double coord, double yScale) {
    	return (int) ((Plan.getMaxLatitude(plan.getIntersections()) - coord) * yScale + padding);
  
    }
    
    public int weightLongitude(double coord, double xScale) {
    	return (int) ((Plan.getMaxLongitude(plan.getIntersections()) - coord) * xScale + padding);
    }
    
    public List<Point[]> weightAllPoint(List<Double[]> listLineDouble, double xScale, double yScale) {
    	List<Point[]> listLinePoint = new ArrayList<Point[]>();
    	for(Double[] line : listLineDouble) {
    		Point origin = new Point(weightLatitude(line[0],xScale),weightLongitude(line[1], yScale));
    		Point destination = new Point(weightLatitude(line[2],xScale),weightLongitude(line[3], yScale));
    		
    		Point[] lineArray = {origin, destination};
    		
    		listLinePoint.add(lineArray);
    	}
    	
    	return listLinePoint;
    }



	public void loadRequests() {
		// TODO Auto-generated method stub
		Graphics g = this.getGraphics();
		Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
        
        
        List<delivery.model.Request> requests = this.plan.getRequests();
		List<Point> graphPoints2 = new ArrayList<>();
		for(int i = 0 ; i<requests.size();i++) {
			 int x1 = weightLatitude(requests.get(i).getPickup().getAddress().getLatitude(), xScale); 
	         int y1 = weightLongitude(requests.get(i).getPickup().getAddress().getLongitude(), yScale);
	         
	         int x2 = weightLatitude(requests.get(i).getDelivery().getAddress().getLatitude(), xScale); 
	         int y2 = weightLongitude(requests.get(i).getDelivery().getAddress().getLongitude(), yScale);
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
	        
	        this.revalidate();
	        //this.repaint();
		
	}

    
    
    
}