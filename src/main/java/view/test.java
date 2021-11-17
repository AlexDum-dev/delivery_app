package view;

import java.awt.EventQueue;

import javax.swing.*;

import delivery.model.CheckPoint;
import delivery.model.CheckPointType;
import delivery.model.Intersection;
import delivery.model.Request;

import java.awt.*;
import java.io.File;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;
import java.awt.Panel;
import javax.swing.GroupLayout;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class test {

	private JFrame frame;
	private JTable table;

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 866, 562);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnLoadMap = new JButton("Load Map");
		btnLoadMap.setBounds(12, 12, 134, 27);
		btnLoadMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				int response = fileChooser.showOpenDialog(null);
				if(response == JFileChooser.APPROVE_OPTION) {
					File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
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
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(null);
				int response = fileChooser.showOpenDialog(null);
				if(response == JFileChooser.APPROVE_OPTION) {
					File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
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
		
		Panel panel = new Panel();
		panel.setBounds(10, 72, 458, 382);
		frame.getContentPane().add(panel);
		
		//test Ajout de tableau :
		/*JPanel TextGrid = new JPanel();
		GridLayout gl = new GridLayout(25,40);
		TextGrid.setLayout(gl);
		*/
		Intersection inter = new Intersection("1234", 3, 4);
		Intersection inter2 = new Intersection("12345", 3, 4);
		Intersection inter3 = new Intersection("123456", 3, 4);
		CheckPoint pick = new CheckPoint(CheckPointType.PICKUP,inter,10);
		CheckPoint deliv = new CheckPoint(CheckPointType.DELIVERY,inter2,10);
		
		CheckPoint pick1 = new CheckPoint(CheckPointType.PICKUP,inter,10);
		CheckPoint deliv1 = new CheckPoint(CheckPointType.DELIVERY,inter2,10);
		
		CheckPoint pick2 = new CheckPoint(CheckPointType.PICKUP,inter,10);
		CheckPoint deliv2 = new CheckPoint(CheckPointType.DELIVERY,inter3,10);
		
		Request req = new Request(deliv,pick);
		Request req1 = new Request(deliv,pick);
		Request req2 = new Request(deliv,pick);
		
		List<Request> listReq = new ArrayList();
		listReq.add(req);listReq.add(req1);listReq.add(req2);
		
	
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(480, 12, 365, 65);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
				
				RequestView.displayRequest(listReq), 
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
	}
}
