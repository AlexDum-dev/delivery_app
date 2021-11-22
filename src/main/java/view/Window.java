package view;

import java.awt.EventQueue;

import javax.swing.*;

import delivery.model.CheckPoint;
import delivery.model.CheckPointType;
import delivery.model.Intersection;
import delivery.model.Plan;
import delivery.model.Request;
import delivery.model.XMLParser;
import delivery.model.XMLParserException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import controller.Controller;

public class Window {

	private JFrame frame;
	private RequestView requestView;
	private MapView mapView;
	private MapView panel;

	/**
	 * Create the application.
	 */
	public Window(Plan p, Controller c) {
		initialize(p, c);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Plan p, Controller c) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1100, 780);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		requestView = new RequestView(p);
		requestView.setBounds(746, 49, 300, 200);
		frame.getContentPane().add(requestView);
		
		mapView = new MapView(p);
		mapView.setBounds(20,45,690,690);
		frame.getContentPane().add(mapView);
		//mapView.colorBackground(java.awt.Color.WHITE);
		SwingUtilities.updateComponentTreeUI(frame);
		

		panel = new MapView(new Plan());  
		panel.setBounds(746,259,300,65);
		frame.getContentPane().add(panel);
		//panel.colorBackground(new java.awt.Color(200, 200, 200, 200));
		//panel.printK();
		
		JButton btnLoadMap = new JButton("Load Map");
		btnLoadMap.setBounds(12, 12, 134, 27);
		btnLoadMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.loadMap(p);
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnLoadMap);
		
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
			}
		});
		frame.getContentPane().add(btnComputeTour);
		
		frame.setVisible(true);
	}
}
