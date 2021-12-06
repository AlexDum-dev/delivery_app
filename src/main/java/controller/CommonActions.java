package controller;

import java.awt.Component;
import view.Window;
import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;

import algorithm.Dijkstra;
import model.CheckPoint;
import model.Path;
import model.Plan;
import model.Request;
import model.Tour;
import model.XMLParser;
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
			w.getMapView().zoomOut();
			w.setComputeTourButtonFalse();
			w.setAddRequestFalse();
			w.setMessageVisible(w.getMessage1(), false);
			w.setMessageVisible(w.getMessage2(), false);
			w.setDeleteButton(false);
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
			w.setAddRequestFalse();
			w.setMessageVisible(w.getMessage1(), false);
			w.setMessageVisible(w.getMessage2(), false);
			w.setDeleteButton(false);
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
			w.getMapView().zoomOut();
			w.setAddRequestFalse();
			w.setMessageVisible(w.getMessage1(), false);
			w.setMessageVisible(w.getMessage2(), false);
			w.setDeleteButton(false);
			
		} catch (ExceptionXML e) {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, 
					e.getMessage(), 
					"Error loading XML requests",
				    JOptionPane.ERROR_MESSAGE);
			w.setComputeTourButtonFalse();
			w.setAddRequestFalse();
			w.setMessageVisible(w.getMessage1(), false);
			w.setMessageVisible(w.getMessage2(), false);
			w.setDeleteButton(false);
			plan.clearRequests();
			tour.clearPath();
			tour.notifyObservers();
			plan.notifyObservers();
			c.setCurrentState(MapLoaded.getInstance());
			w.getMapView().zoomOut();
		}
	}
}
