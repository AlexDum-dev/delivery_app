package view;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import delivery.model.CheckPoint;
import delivery.model.Request;
import delivery.model.Path;
import delivery.model.Tour;
import observer.Observable;
import observer.Observer;


public class CheckPointView extends JScrollPane implements Observer {

	private JTable table;
    private Tour tour;
	
	public CheckPointView(Tour tour) {
		super();
		this.tour = tour;
		tour.addObserver(this);
		table = new JTable();
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {  
			   
            public void valueChanged(ListSelectionEvent e) {  
            	int index = table.getSelectedRow();
		    	String pickupRow = table.getValueAt(table.getSelectedRow(), 0).toString();
	        	String deliveryRow = table.getValueAt(table.getSelectedRow(), 1).toString();
	        	System.out.println("request row : "+pickupRow + "\t" + deliveryRow);
	        	for(int i = 0 ; i<tour.getCheckPoint().size() && i<tour.getPath().size() ; ++i) {
	        		CheckPoint checkPoint = tour.getCheckPoint().get(i);
	        		Path path = tour.getPath().get(i);
	        		if ( (checkPoint.getAddress().getId()).equals(pickupRow) ) {
	        			checkPoint.setActive(true);
	        			path.setActive(true);
	        		}else {
	        			checkPoint.setActive(false);
	        			path.setActive(false);
	        		}
	        	}
	        	tour.notifyObservers("mapView");
	        	table.setRowSelectionInterval(index, index);
	        	//table.changeSelection(index, 0, false, false);
            }  
        }); 
		
	}

	private Object[][] displayCheckPoint() {
		List<CheckPoint> checkPoints = tour.getCheckPoint();
		Object[][] tabRequest;
		if (checkPoints.size()>1) {
			tabRequest = new Object[checkPoints.size()+1][2];
		} else {
			tabRequest = new Object[checkPoints.size()][2];
		}

		String addressTextGrid;
		String timeTextGrid;
		CheckPoint check;
		String[] line;
		int i;
		for(i = 0 ; i<checkPoints.size();i++) {
			check = checkPoints.get(i);
			addressTextGrid = check.getAddress().getId();
			timeTextGrid = check.getTime().toString();
			line = new String[]{addressTextGrid,timeTextGrid};
			tabRequest[i] =  line; 
			
		}
		if (checkPoints.size()>1) {
			check = checkPoints.get(0);
			addressTextGrid = check.getAddress().getId();
			timeTextGrid = tour.getTime().toString();
			line = new String[]{addressTextGrid,timeTextGrid};
			tabRequest[i] =  line; 
		}
		return tabRequest;
	}

	@Override
	public void update(Observable observed, Object arg) {
		if(arg == null || arg.toString().equals("onlyMapView")) {
			System.out.println("CheckPointView Update methode");
		table.setModel(new DefaultTableModel(
				
				displayCheckPoint(), 
			new String[] {
				"Address", "Time"
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
