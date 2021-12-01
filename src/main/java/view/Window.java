package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import controller.Controller;
import delivery.model.CheckPoint;
import delivery.model.CheckPointType;
import delivery.model.Intersection;
import delivery.model.Plan;
import delivery.model.Request;
import delivery.model.Tour;


import javax.swing.*;
import java.awt.*;

public class Window {

	private JFrame frame;
	private RequestView requestView;
	private CheckPointView checkPointView;
	private MapView mapView;
	private MapInfoView mapInfoView;
	private JButton btnLoadMap = new JButton("Load Map");
	private JButton btnLoadRequest = new JButton("Load Request");
	private JButton btnComputeTour = new JButton("Compute Tour");
	private JButton btnStopComputing = new JButton("Stop Computing");
	private JButton btnAddRequestTest = new JButton("Add Request");
	private JButton btnDeleteRequestTest = new JButton("Delete Request");
	private CheckPoint pick;
	private CheckPoint d;



	/**
	 * Create the application.
	 * @param tour 
	 */
	public Window(Plan p, Tour t, Controller c) {
		initialize(p, t, c);
		setStopComputingButtonFalse();
		setComputeTourButtonFalse();
		setLoadRequestButtonFalse();
		setAddRequestFalse();
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
		
		
		btnLoadMap.setBounds(12, 12, 134, 27);
		btnLoadMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.loadMap();
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnLoadMap);
		
		
		btnLoadRequest.setBounds(176, 12, 134, 27);
		btnLoadRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.loadRequest();
			}
		});
		frame.getContentPane().add(btnLoadRequest);
		
		btnComputeTour.setBounds(334, 12, 134, 27);
		btnComputeTour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.computeTour();
			}
		});
		frame.getContentPane().add(btnComputeTour);

		
		btnStopComputing.setBounds(492, 12, 134, 27);
		btnStopComputing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.stopTour();
			}
		});
		frame.getContentPane().add(btnStopComputing);
		
		
		btnAddRequestTest.setBounds(650, 12, 134, 27);
		btnAddRequestTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Test appui bouton");
				c.addRequest("25321359", "26317214", 10, 11);
			}
		});
		frame.getContentPane().add(btnAddRequestTest);
		
		btnDeleteRequestTest.setBounds(850, 12, 134, 27);
		btnDeleteRequestTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Test appui bouton delete");
				c.deleteRequest("25321359", "26317214");
			}
		});
		frame.getContentPane().add(btnDeleteRequestTest);
		
		
				
		frame.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}
	
	public void setLoadMapButtonFalse() {
		btnLoadMap.setEnabled(false);


	}
	
	public void setLoadRequestButtonFalse() {
		btnLoadRequest.setEnabled(false);
		

	}
	public void setComputeTourButtonFalse() {
		btnComputeTour.setEnabled(false);
		

	}
	public void setStopComputingButtonFalse() {
		btnStopComputing.setEnabled(false);
	}
	
	public void setAddRequestFalse() {
		btnAddRequestTest.setEnabled(false);
	}
	
	public void setLoadMapButtonTrue() {
		btnLoadMap.setEnabled(true);
	}
	
	public void setLoadRequestButtonTrue() {
		btnLoadRequest.setEnabled(true);
		

	}
	public void setComputeTourButtonTrue() {
		btnComputeTour.setEnabled(true);
		

	}
	public void setStopComputingButtonTrue() {
		btnStopComputing.setEnabled(true);
	}
	
	public void setAddRequestTrue() {
		btnAddRequestTest.setEnabled(true);
	}

	
	
	
}
