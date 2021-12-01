package controller;

import java.awt.Component;

import delivery.model.Plan;
import delivery.model.Request;
import delivery.model.Tour;
import view.Window;
/**
 * TourComputed State
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public class TourComputed implements State {
	private static TourComputed instance = null;
	
	private TourComputed() {
		
	}
	
	protected static TourComputed getInstance() {
		if (instance==null) {
			instance = new TourComputed();
		}
		return instance;
	}

	@Override
	public void loadMap(Controller c, Plan plan, Tour tour, Component frame, Window w) {
		CommonActions.loadMap(c, plan, tour, frame, w);
	}
	
	@Override
	public void loadRequest(Controller c, Plan plan, Tour tour, Component frame, Window w) {
		CommonActions.loadRequest(c, plan, tour, frame, w);
	}
	
	@Override
	public void addRequest(ListOfCommands l, Plan plan, Tour tour, String idPickup, String idDelivery, int durationPickup,
			int durationDelivery, Window w) {
		l.add(new AddRequestCommand(tour, plan, idPickup, idDelivery, durationPickup, durationDelivery, w));
	}
	
	@Override
	public void deleteRequest(ListOfCommands l, Plan plan, Tour tour, Request request, Window w) {
		l.add(new ReverseCommand(new AddRequestCommand(plan, tour, request, w)));
	}
}
