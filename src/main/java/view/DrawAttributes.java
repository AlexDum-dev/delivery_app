package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

/**
 * Static 
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public class DrawAttributes {
	private static Color colorPickUp = new Color(204,0,0);
	private static Color colorDelivery= new Color(0,153,0);
	private static Color colorDepot = new Color(0,0,128);
	private static Color colorLine = new Color(0,0,0);
	private static Color colorPath = new Color(255,0,255);
	private static Stroke strokeLine = new BasicStroke(2f);
	private static Stroke strokePath = new BasicStroke(5f);
	private static int pointWidth = 15;
	
	private DrawAttributes() {
		
	}
	
	public static Color getColorPickUp() {
		return colorPickUp;
	}
	
	public static Color getColorDelivery() {
		return colorDelivery;
	}
	
	public static Color getColorDepot() {
		return colorDepot;
	}
	
	public static Color getColorLine() {
		return colorLine;
	}

	public static Color getColorPath() {
		return colorPath;
	}

	public static Stroke getStrokeLine() {
		return strokeLine;
	}

	public static Stroke getStrokePath() {
		return strokePath;
	}

	public static int getPointWidth() {
		return pointWidth;
	}
}
