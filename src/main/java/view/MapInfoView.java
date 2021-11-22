package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JPanel;

import delivery.model.Plan;
import observer.Observable;
import observer.Observer;

public class MapInfoView extends JPanel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private int labelPadding = 0;
	private int padding = 0;
	private Plan plan;

	public MapInfoView(Plan plan) {
		this.plan = plan;
		plan.addObserver(this);
	}
	public void colorBackground (Color color) {
		Graphics g = this.getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(color);
		//fill the rect
		g2.fillRect(padding + labelPadding, padding, getWidth() - (2* padding) - 
				labelPadding, getHeight() - 2 * padding - labelPadding);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		System.out.println("PrintK =========");
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(new java.awt.Color(200, 200, 200));
		g2.fillRect(padding + labelPadding, padding, getWidth() - (2* padding) - 
				labelPadding, getHeight() - 2 * padding - labelPadding);
		int pointWidth = DrawAttributes.getPointWidth();
		if (!plan.getRequests().isEmpty()) {
			
			int x = this.getWidth()/2 - 125 - pointWidth / 2;
			int y = this.getHeight()/2 - 8 - pointWidth / 2;
			int ovalW = pointWidth*2;
			int ovalH = pointWidth*2;
			g2.setColor(DrawAttributes.getColorPickUp());
			g2.fillOval(x,y, ovalW, ovalH);
	
			x = this.getWidth()/2  - pointWidth / 2;
			y = this.getHeight()/2 - 8 - pointWidth / 2;
			g2.setColor(DrawAttributes.getColorDelivery());
			g2.fillOval(x,y, ovalW, ovalH);
		}
	}
	
	@Override
	public void update(Observable observed, Object arg) {
		// TODO Auto-generated method stub
		
		repaint();
	}

}
