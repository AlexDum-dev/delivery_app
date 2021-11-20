package controller;

import java.io.File;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import delivery.model.Plan;
import delivery.model.XMLParser;
import view.RequestView;
import xml.XMLfileOpener;

public class MapLoaded implements State {
	private static MapLoaded instance = null;
	
	private MapLoaded() {
		
	}
	
	protected static MapLoaded getInstance() {
		if (instance==null) {
			instance = new MapLoaded();
		}
		return instance;
	}

	public void loadMap(Controller c, Plan plan) {
		CommonActions.loadMap(c, plan);
	}
	
	public void loadRequest(Controller c, Plan plan) {
		CommonActions.loadRequest(c, plan);
	}
}
