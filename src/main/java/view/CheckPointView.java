package view;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import delivery.model.CheckPoint;
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
