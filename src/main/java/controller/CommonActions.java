package controller;

import java.io.File;

import delivery.model.Plan;
import delivery.model.XMLParser;
import xml.XMLfileOpener;

public class CommonActions {
	private CommonActions() {
		
	}
	
	public static void loadMap(Controller c, Plan plan) {
		System.out.println("Loading Map...");
		try {
			File file = XMLfileOpener.getInstance().open();
			XMLParser.loadPlan(file, plan);
			c.setCurrentState(MapLoaded.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			plan.clearPlan();
			plan.notifyObservers();
			c.setCurrentState(InitialState.getInstance());
		}
	}
	
	public static void loadRequest(Controller c, Plan plan) {
		System.out.println("Loading Requests...");
		try {
			File file = XMLfileOpener.getInstance().open();
			XMLParser.loadRequests(file, plan);
			plan.notifyObservers();
			c.setCurrentState(RequestsLoaded.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			plan.clearRequests();
			plan.notifyObservers();
			c.setCurrentState(MapLoaded.getInstance());
		}
	}
}
