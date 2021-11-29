package controller;

import java.awt.Component;
import view.Window;
import java.io.File;

import javax.swing.JOptionPane;

import delivery.model.Plan;
import delivery.model.Tour;
import delivery.model.XMLParser;
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
	 * @param frame 
	 */
	public static void loadMap(Controller c, Plan plan, Tour tour, Component frame, Window w) {
		System.out.println("Loading Map...");
		try {
			File file = XMLfileOpener.getInstance().open();
			XMLParser.loadPlan(file, plan);
			tour.clearPath();
			tour.notifyObservers();
			c.setCurrentState(MapLoaded.getInstance());
			w.setLoadRequestButtonTrue();
		} catch (ExceptionXML e) {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, 
					e.getMessage(), 
					"Error loading XML map",
				    JOptionPane.ERROR_MESSAGE);
			w.setLoadRequestButtonFalse();
			w.setComputeTourButtonFalse();
			plan.clearPlan();
			tour.clearPath();
			tour.notifyObservers();
			plan.notifyObservers();
			c.setCurrentState(InitialState.getInstance());
		}
	}

	/**
	 * Loads the requests into a plan
	 * 
	 * @param c the controller
	 * @param plan the plan in which to load the requests
	 * @param tour 
	 * @param frame 
	 */
	public static void loadRequest(Controller c, Plan plan, Tour tour, Component frame, Window w) {
		System.out.println("Loading Requests...");
		try {
			File file = XMLfileOpener.getInstance().open();
			XMLParser.loadRequests(file, plan);
			plan.notifyObservers();
			tour.clearPath();
			tour.notifyObservers();
			c.setCurrentState(RequestsLoaded.getInstance());
			w.setComputeTourButtonTrue();
			
			
		} catch (ExceptionXML e) {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, 
					e.getMessage(), 
					"Error loading XML requests",
				    JOptionPane.ERROR_MESSAGE);
			w.setComputeTourButtonFalse();
			plan.clearRequests();
			tour.clearPath();
			tour.notifyObservers();
			plan.notifyObservers();
			c.setCurrentState(MapLoaded.getInstance());
		}
	}
}
