package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import model.Plan;
import observer.Observable;
import observer.Observer;

/**
 * Map Info View
 * Displays the legend of the map
 * 
 * @author 4IF Group H4144
 * @version 1.0 7 Dec 2021
 */
public class MapInfoView extends JPanel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private int labelPadding = 0;
	private int padding = 0;
	private Plan plan;

	public MapInfoView(Plan plan) {
		this.plan = plan;
		plan.addObserver(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(new java.awt.Color(200, 200, 200));
		g2.fillRect(padding + labelPadding, padding, getWidth() - (2* padding) - 
				labelPadding, getHeight() - 2 * padding - labelPadding);
		int pointWidth = DrawAttributes.getPointWidth();
		g2.setColor(Color.BLACK);
		if (!plan.getRequests().isEmpty()) {

			int ovalW = pointWidth;
			int ovalH = pointWidth;
			
			int x = this.getWidth()/6 - ovalW / 2;
			int y = this.getHeight()/4 - 4 - ovalH / 2;
			g2.fillRoundRect(x,y, ovalW, ovalH, 10, 10);
			
			x = this.getWidth()/4;
			y = this.getHeight()/4 - 2;
			g2.drawString("Depot", x, y);

			x = this.getWidth()/6 - ovalW / 2;
			y = this.getHeight()*2/4 - ovalH / 2;
			g2.fillRect(x,y, ovalW, ovalH);
			
			x = this.getWidth()/4;
			y = this.getHeight()*2/4 + 2;
			g2.setColor(Color.BLACK);
			g2.drawString("Pick Up", x, y);

			x = this.getWidth()/6 - ovalW / 2;
			y = this.getHeight()*3/4 + 4 - ovalH / 2;
			g2.fillOval(x,y, ovalW, ovalH);
			
			x = this.getWidth()/4;
			y = this.getHeight()*3/4 + 6;
			g2.setColor(Color.BLACK);
			g2.drawString("Delivery", x, y);
		}
	}
	
	@Override
	public void update(Observable observed, Object arg) {
		repaint();
	}
	
	

}
