package view;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.Plan;
import model.Request;
import observer.Observable;
import observer.Observer;

/**
 * Table View for all requests
 * 
 * @author 4IF Group H4144
 * @version 1.0 1 Dec 2021
 */
public class RequestView extends JScrollPane implements Observer {

	private static final long serialVersionUID = 1L;
	private JTable table;
    private Plan plan;
	
	public RequestView(Plan plan) {
		super();
		this.plan = plan;
		plan.addObserver(this);
		table = new JTable();
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {  
			   
            public void valueChanged(ListSelectionEvent e) {  
            	int index = table.getSelectedRow();
            	int i = 0;
	        	for(Request req : plan.getRequests()) {
	        		if (index==i) {
	        			req.setActive(true);
	        		}else {
	        			req.setActive(false);
	        		}
	        		++i;
	        	}
	        	plan.notifyObservers("mapView");
            }  
        }); 
	}
	
	public JTable getTable() {
		return table;
	}

	private Object[][] displayRequest() {
		List<Request> requests = plan.getRequests();
		Object[][] tabRequest = new Object[requests.size()][2];
						
		String delivery;
		String pickup;
	
		for(int i = 0 ; i<requests.size();i++) {
			delivery = requests.get(i).getDelivery().getAddress().getAddress();
			pickup = requests.get(i).getPickup().getAddress().getAddress();
			
			String[] line = {pickup, delivery};
			tabRequest[i] =  line; 
			
		}
			
		return tabRequest;
	}

	@Override
	public void update(Observable observed, Object arg) {

		if(arg == null || !arg.toString().equals("mapView")) {
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
			
			public boolean isCellEditable(int r, int l) {
				return false;
			}
		});
		this.setViewportView(table);
		}
	}
}
