package view;

import java.awt.Graphics;
import java.time.LocalTime;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.CheckPoint;
import model.Path;
import model.Tour;
import observer.Observable;
import observer.Observer;

/**
 * Table View for checkpoints of the tour
 * 
 * @author 4IF Group H4144
 * @version 1.0 1 Dec 2021
 */
public class CheckPointView extends JScrollPane implements Observer {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private Tour tour;

	/**
	 * Constructor for the checkpoint view
	 * @param tour
	 */
	public CheckPointView(Tour tour) {
		super();
		this.tour = tour;
		tour.addObserver(this);
		table = new JTable();
		/**
		 * Listener to synchronize the mapView with the selection
		 */
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {  
			public void valueChanged(ListSelectionEvent e) {  
				int index = table.getSelectedRow();
				for(int i = 0 ; i<tour.getCheckPoints().size() && i<tour.getPathList().size() ; ++i) {
					CheckPoint checkPoint = tour.getCheckPoints().get(i);
					Path path = tour.getPathList().get(i);
					if ( index==i ) {
						checkPoint.setActive(true);
						path.setActive(true);
					}else {
						checkPoint.setActive(false);
						path.setActive(false);
					}
				}
				tour.notifyObservers("mapView");
			}  
		}); 

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		update(null, null);
	}

	public JTable getTable() {
		return table;
	}

	/**
	 * Returns the data to fill this table
	 * @return
	 */
	private Object[][] fillTable() {
		List<CheckPoint> checkPoints = tour.getCheckPoints();
		Object[][] tabRequest;
		if (checkPoints.size()>1) {
			tabRequest = new Object[checkPoints.size()+1][3];
		} else {
			tabRequest = new Object[checkPoints.size()][3];
		}

		String addressTextGrid;
		String arrTextGrid;
		String depTextGrid;
		CheckPoint check;
		String[] line;
		int i;
		for(i = 0 ; i<checkPoints.size();i++) {
			check = checkPoints.get(i);
			addressTextGrid = check.getAddress().getAddress();
			LocalTime t = check.getTime();
			LocalTime t2 = t.minusSeconds(t.getSecond());
			if (i>0) {
				arrTextGrid = t2.toString();
			} else {
				arrTextGrid = "-";
			}
			t = t.plusSeconds(check.getDuration());
			t2 = t.minusSeconds(t.getSecond());
			depTextGrid = t2.toString();
			line = new String[]{addressTextGrid,arrTextGrid,depTextGrid};
			tabRequest[i] =  line;
		}
		if (checkPoints.size()>1) {
			check = checkPoints.get(0);
			addressTextGrid = check.getAddress().getAddress();
			LocalTime t = tour.getTime();
			LocalTime t2 = t.minusSeconds(t.getSecond());
			arrTextGrid = t2.toString();
			depTextGrid = "-";
			line = new String[]{addressTextGrid,arrTextGrid,depTextGrid};
			tabRequest[i] =  line; 
		}
		return tabRequest;
	}

	@Override
	synchronized protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	@Override
	synchronized public void update(Observable observed, Object arg) {
		if(arg == null || !arg.toString().equals("mapView")) {
			table.setModel(new DefaultTableModel(

					fillTable(), 
					new String[] {
							"Address", "Arrival Time", "Departure Time"
					}
					) {
				Class[] columnTypes = new Class[] {
						String.class, String.class, String.class
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
