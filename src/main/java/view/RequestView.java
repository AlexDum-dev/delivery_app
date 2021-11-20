package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import delivery.model.Request;

import java.awt.*;


public class RequestView {
	
	static Object[][] displayRequest(List<Request> requests) {
		
		Object[][] tabRequest = new Object[requests.size()][4];
						
		String deliveryTextGrid;
		String pickupTextGrid;
		String pickupTimeTextGrid;
		String deliveryTimeTextGrid;
	
		for(int i = 0 ; i<requests.size();i++) {
			pickupTimeTextGrid = "-";
			deliveryTimeTextGrid = "-";
			deliveryTextGrid = requests.get(i).getDelivery().getAddress().getId();
			pickupTextGrid = requests.get(i).getPickup().getAddress().getId();
			if(requests.get(i).getPickup().getTime() != null || requests.get(i).getDelivery().getTime() != null) {
				pickupTimeTextGrid = requests.get(i).getPickup().getTime().toString();
				deliveryTimeTextGrid = requests.get(i).getDelivery().getTime().toString();
			}
			
			
			String[] line = {deliveryTextGrid, pickupTextGrid, pickupTimeTextGrid, deliveryTimeTextGrid};
			tabRequest[i] =  line; 
			
		}
			
		return tabRequest;
	}
}
