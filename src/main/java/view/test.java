package view;

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

import javax.swing.GroupLayout;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class test {

	private JFrame frame;
	private JTable table;
	private Plan plan;
	private MapView mapView;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		plan = new Plan();
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Sample Graph");
		frame.setBounds(100, 100, 1100, 780);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(746, 49, 300, 200);
		frame.getContentPane().add(scrollPane);
		
		/*File file = new File("C:\\Users\\PC Lenovo\\Desktop\\fichiersXML2020\\smallMap.xml");
		try {
			XMLParser.loadPlan(file, plan);
			mapView = new MapView(plan);
			System.out.println("fdfffff");
			mapView.setBounds(10, 72, 458, 382);
			frame.getContentPane().add(mapView);
		} catch (NumberFormatException | ParserConfigurationException | IOException | SAXException
				| XMLParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		JFileChooser fileChooser = new JFileChooser();
		JButton btnLoadMap = new JButton("Load Map");
		btnLoadMap.setBounds(12, 12, 134, 27);
		btnLoadMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int response = fileChooser.showOpenDialog(null);
				if(response == JFileChooser.APPROVE_OPTION) {
					File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
					System.out.println("file name : "+file.toString());
					
					try {
						XMLParser.loadPlan(file, plan);
						
						mapView = new MapView(plan);
						//left ,up ,width, height
						mapView.setBounds(20,45,690,690);
						
						System.out.println("fdfffff");
						frame.getContentPane().add(mapView);
						SwingUtilities.updateComponentTreeUI(frame);
						System.out.println("TEEST");
					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println(file);
				}
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnLoadMap);
		
		JButton btnLoadRequest = new JButton("Load Request");
		btnLoadRequest.setBounds(176, 12, 134, 27);
		btnLoadRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int response2 = fileChooser.showOpenDialog(null);
				if(response2 == JFileChooser.APPROVE_OPTION) {
					File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
					try {
						XMLParser.loadRequests(file, plan);
						
						frame.getContentPane().add(scrollPane);
						List<Request> requests = plan.getRequests();
						mapView.loadRequests();
						//SwingUtilities.updateComponentTreeUI(frame);

					        
					        
						table = new JTable();
						//table.setBounds(0, 0, 458, 382);
						table.setModel(new DefaultTableModel(
								
								Plan.displayRequest(requests), 
							new String[] {
								"PickUp", "Delivery", "PTime", "DTime"
							}
						) {
							Class[] columnTypes = new Class[] {
								String.class, String.class, Long.class, Long.class
							};
							public Class getColumnClass(int columnIndex) {
								return columnTypes[columnIndex];
							}
						});
						scrollPane.setViewportView(table);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(file);
				}
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
		

		
		//test Ajout de tableau :
		/*JPanel TextGrid = new JPanel();
		GridLayout gl = new GridLayout(25,40);
		TextGrid.setLayout(gl);
		*/
		
		
		
	}
}
