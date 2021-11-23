package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

/**
 * Static 
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public class DrawAttributes {
	private static List<Color> colorsRequest;
	private static Color colorDepot = Color.GRAY;
	private static Color colorLine = new Color(0,0,0);
	private static Color colorPath = new Color(255,0,255);
	private static Stroke strokeLine = new BasicStroke(2f);
	private static Stroke strokePath = new BasicStroke(5f);
	private static Stroke strokePoint = new BasicStroke(1f);
	private static int pointWidth = 15;
	
	static {
		colorsRequest = new ArrayList<Color>();
		colorsRequest.add(Color.BLUE);
		colorsRequest.add(Color.CYAN);
		colorsRequest.add(Color.MAGENTA);
		colorsRequest.add(Color.GREEN);
		colorsRequest.add(Color.RED);
		colorsRequest.add(Color.ORANGE);
		colorsRequest.add(Color.PINK);
	}
	
	private DrawAttributes() {
		
	}

	public static Color getColorRequest(int i) {
		if (i<colorsRequest.size())
			return colorsRequest.get(i);
		return Color.LIGHT_GRAY;
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

	public static Stroke getStrokePoint() {
		return strokePoint;
	}

	public static int getPointWidth() {
		return pointWidth;
	}
}
