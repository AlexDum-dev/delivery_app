package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import delivery.model.Plan;

public class Window {

	private JFrame frame;
	private Plan plan;
	private RequestView requestView;
	private MapView mapView;

	/**
	 * Create the application.
	 */
	public Window(Plan p, Controller c) {
		plan = p;
		initialize(p, c);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Plan p, Controller c) {
		frame = new JFrame();
		frame.setBounds(100, 100, 866, 562);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		requestView = new RequestView(p);
		requestView.setBounds(526, 12, 328, 447);
		frame.getContentPane().add(requestView);
		
		JButton btnLoadMap = new JButton("Load Map");
		btnLoadMap.setBounds(12, 12, 134, 27);
		btnLoadMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Click on Loading Map...");
				c.loadMap(p);
			}
		});
		mapView = new MapView(p);
		mapView.setBounds(10, 72, 458, 382);
		frame.getContentPane().add(mapView);
		SwingUtilities.updateComponentTreeUI(frame);
		
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnLoadMap);

		frame.getContentPane().add(requestView);
		
		JButton btnLoadRequest = new JButton("Load Request");
		btnLoadRequest.setBounds(176, 12, 134, 27);
		btnLoadRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.loadRequest(p);
			}
		});
		frame.getContentPane().add(btnLoadRequest);
		
		JButton btnComputeTour = new JButton("Compute Tour");
		btnComputeTour.setBounds(334, 12, 134, 27);
		btnComputeTour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.computeTour();
			}
		});
		frame.getContentPane().add(btnComputeTour);
		

		
		//test Ajout de tableau :
		/*JPanel TextGrid = new JPanel();
		GridLayout gl = new GridLayout(25,40);
		TextGrid.setLayout(gl);
		*/
		
		frame.setVisible(true);
		
	}
}
