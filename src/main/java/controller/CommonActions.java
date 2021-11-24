package controller;

import java.awt.Component;
import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;

import algorithm.Dijkstra;
import delivery.model.CheckPoint;
import delivery.model.Path;
import delivery.model.Plan;
import delivery.model.Request;
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
	public static void loadMap(Controller c, Plan plan, Tour tour, Component frame) {
		System.out.println("Loading Map...");
		try {
			File file = XMLfileOpener.getInstance().open();
			XMLParser.loadPlan(file, plan);
			tour.clearPath();
			tour.notifyObservers();
			c.setCurrentState(MapLoaded.getInstance());
		} catch (ExceptionXML e) {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, 
					"Invalid XML file.", 
					"Error loading map",
				    JOptionPane.ERROR_MESSAGE);
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
	public static void loadRequest(Controller c, Plan plan, Tour tour, Component frame) {
		System.out.println("Loading Requests...");
		try {
			File file = XMLfileOpener.getInstance().open();
			XMLParser.loadRequests(file, plan);
			plan.notifyObservers();
			tour.clearPath();
			tour.notifyObservers();
			c.setCurrentState(RequestsLoaded.getInstance());
		} catch (ExceptionXML e) {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, 
					"Invalid XML file.", 
					"Error loading requests",
				    JOptionPane.ERROR_MESSAGE);
			plan.clearRequests();
			tour.clearPath();
			tour.notifyObservers();
			plan.notifyObservers();
			c.setCurrentState(MapLoaded.getInstance());
		}
	}
	/**
	 * 
	 * @param c
	 * @param plan
	 * @param tour
	 * @param req
	 */
	public static void addRequest(Controller c, Plan plan, Tour tour) {
		Request req = plan.getRequests().get(plan.getRequests().size() - 1);
		tour.removeLastPath();
		CheckPoint lastCheckPoint = tour.removeLastCheckPoint();
		
		//launch dijkstra for the the pickup and the delivery and the last checkpoint
		List<Integer> predecesorPickupDeparture =  Dijkstra.dijkstra(plan.getIntersections(),req.getPickup().getAddress());
		List<Integer> predecesorDeliveryDeparture = Dijkstra.dijkstra(plan.getIntersections(),req.getDelivery().getAddress());
		List<Integer> predecesorLastIntersectionTour = Dijkstra.dijkstra(plan.getIntersections(), lastCheckPoint.getAddress());
		
		
		Path pathFromLastPontToNewPickup = Dijkstra.createPath(plan.getIntersections(), predecesorLastIntersectionTour, lastCheckPoint.getAddress().getIndex() , req.getPickup().getAddress().getIndex());
		Path pathFromPickupToDelivery = Dijkstra.createPath(plan.getIntersections(), predecesorPickupDeparture, req.getPickup().getAddress().getIndex(), req.getDelivery().getIndex());
		Path pathFromDeliveryToDepot = Dijkstra.createPath(plan.getIntersections(), predecesorDeliveryDeparture, req.getDelivery().getAddress().getIndex(), plan.getDepot().getAddress().getIndex());
		//Create path between last Checkpoint of the last path and the pickup
		//Create path between the pickup and the delivery
		//create path between the delivery and the depot
		//add all the paths
		//update to vew
		
		tour.addPath(pathFromLastPontToNewPickup, lastCheckPoint );
		tour.addPath(pathFromPickupToDelivery, req.getPickup());
		tour.addPath(pathFromDeliveryToDepot, req.getDelivery());
		
		
		tour.notifyObservers();
	}
}
