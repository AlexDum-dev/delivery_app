package view;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import delivery.model.Plan;
import delivery.model.Request;
import observer.Observable;
import observer.Observer;


public class RequestView extends JScrollPane implements Observer {

	private JTable table;
    private Plan plan;
    private String lastSelected;
    private java.awt.Color colorOfLastSelected;
	
	public RequestView(Plan plan) {
		super();
		this.plan = plan;
		plan.addObserver(this);
		table = new JTable();
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {  
			   
            public void valueChanged(ListSelectionEvent e) {  
            	int index = table.getSelectedRow();
		    	String pickupRow = table.getValueAt(table.getSelectedRow(), 0).toString();
	        	String deliveryRow = table.getValueAt(table.getSelectedRow(), 1).toString();
	        	System.out.println("request row : "+pickupRow + "\t" + deliveryRow);
	        	for(Request req : plan.getRequests()) {
	        		if ( (req.getPickup().getAddress().getId()).equals(pickupRow)
	        				&& (req.getDelivery().getAddress().getId()).equals(deliveryRow) ) {
	        			req.setActive(true);
	        		}else {
	        			req.setActive(false);
	        		}
	        	}
	        	plan.notifyObservers("mapView");
	        	table.setRowSelectionInterval(index, index);
	        	//table.changeSelection(index, 0, false, false);
            }  
        }); 
		
		//table.setBounds(10, 72, 458, 382);
	}
	
	

	private Object[][] displayRequest() {
		List<Request> requests = plan.getRequests();
		Object[][] tabRequest = new Object[requests.size()][2];
						
		String deliveryTextGrid;
		String pickupTextGrid;
	
		for(int i = 0 ; i<requests.size();i++) {
			deliveryTextGrid = requests.get(i).getDelivery().getAddress().getId();
			pickupTextGrid = requests.get(i).getPickup().getAddress().getId();
			
			String[] line = {pickupTextGrid, deliveryTextGrid};
			tabRequest[i] =  line; 
			
		}
			
		return tabRequest;
	}

	@Override
	public void update(Observable observed, Object arg) {

		if(arg == null || arg.toString().equals("onlyMapView")) {
		System.out.println("RequestView Update methode");
		table.setModel(new DefaultTableModel(
				
				displayRequest(), 
			new String[] {
				"Pick Up", "Delivery"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		this.setViewportView(table);
		}
	}
}
