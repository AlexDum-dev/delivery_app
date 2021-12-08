package controller;

import java.awt.Component;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import model.Plan;
import model.Tour;
import model.XMLParser;
import model.XMLParserException;
import view.Window;
import xml.ExceptionXML;
import xml.XMLfileOpener;

/**
 * Utility class for common actions
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public class CommonActions {
	private CommonActions() {
		
	}
	
	/**
	 * Loads the map into a plan
	 * 
	 * @param c the controller
	 * @param plan the plan in which to load the map
	 * @param tour 
	 * @param w the window 
	 */
	public static void loadMap(Controller c, Plan plan, Tour tour, Window w) {
		try {
			File file = XMLfileOpener.getInstance().open();
			try {
				XMLParser.loadPlan(file, plan);
				w.clearButtons();
				w.setLoadRequestButtonEnabled(true);
				c.setCurrentState(MapLoaded.getInstance());
				w.getMessage().setText("Map loaded!");
			} catch (XMLParserException | IOException e) {
				JOptionPane.showMessageDialog(w.getFrame(), 
						e.getMessage(), 
						"Error loading XML map",
					    JOptionPane.ERROR_MESSAGE);
				w.clearButtons();
				plan.clearPlan();
				w.getMessage().setText("Please load a map.");
				c.setCurrentState(InitialState.getInstance());
			}
			w.setLoadMapButtonEnabled(true);
			w.getMapView().adjustZoom();
			plan.notifyObservers();
			tour.clearPath();
			tour.notifyObservers();
		} catch (ExceptionXML e) {
		}
	}

	/**
	 * Loads the requests into a plan
	 * 
	 * @param c the controller
	 * @param plan the plan in which to load the requests
	 * @param tour 
	 * @param w the window 
	 */
	public static void loadRequest(Controller c, Plan plan, Tour tour, Window w) {
		try {
			File file = XMLfileOpener.getInstance().open();
			try {
				XMLParser.loadRequests(file, plan);
				w.clearButtons();
				w.setComputeTourButtonEnabled(true);
				w.getMessage().setText("Requests loaded!");
				c.setCurrentState(RequestsLoaded.getInstance());
			} catch (XMLParserException | IOException e) {
				JOptionPane.showMessageDialog(w.getFrame(), 
						e.getMessage(), 
						"Error loading XML requests",
					    JOptionPane.ERROR_MESSAGE);
				w.clearButtons();
				plan.clearRequests();
				w.getMessage().setText("Please load the requests.");
				c.setCurrentState(MapLoaded.getInstance());
			}
			w.getMapView().adjustZoom();
			plan.notifyObservers();
			tour.clearPath();
			tour.notifyObservers();
			w.setLoadMapButtonEnabled(true);
			w.setLoadRequestButtonEnabled(true);
		} catch (ExceptionXML e) {
		} 
	}
	
	public static Integer inputNumber(Window w, String title, String msg) {
		Integer number = null;
		String numString = "0";
		boolean enterOK = false;
		
		while (numString!=null && !enterOK) {
			numString = (String) JOptionPane.showInputDialog(
	                w.getFrame(),
	                title,
	                msg,
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                null,
	                numString);
			
			try {
				number = Integer.parseInt(numString);
				if (number>=0) {
					enterOK = true;
				} else {
					number = null;
					enterOK = false;
				}
			} catch(NumberFormatException e) {
				number = null;
				enterOK = false;
			}
		}
		return number;
	}
}
