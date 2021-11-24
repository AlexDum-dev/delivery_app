package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import controller.Controller;
import delivery.model.Plan;
import delivery.model.Tour;

public class Window {

	private JFrame frame;
	private RequestView requestView;
	private CheckPointView checkPointView;
	private MapView mapView;
	private MapInfoView mapInfoView;

	/**
	 * Create the application.
	 * @param tour 
	 */
	public Window(Plan p, Tour t, Controller c) {
		initialize(p, t, c);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Plan p, Tour t, Controller c) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1100, 780);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mapView = new MapView(p, t);
		mapView.setBounds(20,45,690,690);
		frame.getContentPane().add(mapView);
		//mapView.colorBackground(java.awt.Color.WHITE);
		SwingUtilities.updateComponentTreeUI(frame);
		
		requestView = new RequestView(p);
		requestView.setBounds(746, 49, 300, 200);
		frame.getContentPane().add(requestView);

		mapInfoView = new MapInfoView(p);  
		mapInfoView.setBounds(746,259,300,120);
		frame.getContentPane().add(mapInfoView);
		
		checkPointView = new CheckPointView(t);
		checkPointView.setBounds(746, 389, 300, 300);
		frame.getContentPane().add(checkPointView);
		
		JButton btnLoadMap = new JButton("Load Map");
		btnLoadMap.setBounds(12, 12, 134, 27);
		btnLoadMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.loadMap();
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnLoadMap);
		
		JButton btnLoadRequest = new JButton("Load Request");
		btnLoadRequest.setBounds(176, 12, 134, 27);
		btnLoadRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.loadRequest();
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
		
		frame.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}
}
