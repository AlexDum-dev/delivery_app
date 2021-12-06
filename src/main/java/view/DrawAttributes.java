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
	private static Color colorActive = new Color(0, 0, 255);
	private static Color ColorPathActive = new Color(255, 102, 0);



	private static Stroke strokeLine = new BasicStroke(2f);
	private static Stroke strokePath = new BasicStroke(5f);
	private static Stroke strokePoint = new BasicStroke(1f);
	private static Stroke strokeActive = new BasicStroke(5f);
	private static int pointWidth = 15;




	static {
		colorsRequest = new ArrayList<Color>();
		colorsRequest.add(Color.YELLOW);
		colorsRequest.add(Color.CYAN);
		colorsRequest.add(Color.MAGENTA);
		colorsRequest.add(Color.GREEN);
		colorsRequest.add(Color.RED);
		colorsRequest.add(Color.ORANGE);
		colorsRequest.add(Color.PINK);
		colorsRequest.add(Color.BLUE);
		colorsRequest.add(new Color(153,0,0));  // Very dark red
		colorsRequest.add(new Color(255,102,102)); // Very light RED
		colorsRequest.add(new Color(51,204,255)); // very light blue
		colorsRequest.add(new Color(0,0,153)); //very dark blue
		colorsRequest.add(new Color(102,255,102)); // very light GREEN
		colorsRequest.add(new Color(0,102,0)); //very dark GREEN
		colorsRequest.add(new Color(255,255,204)); //very light yellow
		colorsRequest.add(new Color(255,204,0)); // dark YELLOW
		colorsRequest.add(new Color(255,153,0)); // light ORANGE
		colorsRequest.add(new Color(255,204,51)); // gold
		colorsRequest.add(new Color(153,102,0)); // light brown
		colorsRequest.add(new Color(102,51,0)); // brown
		colorsRequest.add(new Color(51,0,0)); // dark brown
		colorsRequest.add(new Color(102,0,153)); // purple
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

	public static Color getColorActive() {
		return colorActive;
	}

	public static Stroke getStrokeActive() {
		return strokeActive;
	}
	public static Color getColorPathActive() {
		return ColorPathActive;
	}
}
