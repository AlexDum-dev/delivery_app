package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicArrowButton;

import controller.Controller;
import model.Plan;
import model.Request;
import model.Tour;

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
	private JButton btnUndo = new JButton("Undo");
	private JButton btnRedo = new JButton("Redo");
	private JButton btnAddRequest = new JButton("", new ImageIcon("plus.png"));
	private JButton btnDeleteRequest = new JButton("", new ImageIcon("minus.png"));
	private JButton btnModifyTourUp = new BasicArrowButton(BasicArrowButton.NORTH);
	private JButton btnModifyTourDown = new BasicArrowButton(BasicArrowButton.SOUTH);
	private JLabel message=new JLabel("Please load a map.");
	private JLabel roadName=new JLabel("");



	/**
	 * Create the application.
	 * @param tour 
	 */
	public Window(Plan p, Tour t, Controller c) {
		initialize(p, t, c);
		setStopComputingButtonEnabled(false);
		setComputeTourButtonEnabled(false);
		setLoadRequestButtonEnabled(false);
		setAddRequestEnabled(false);
	}

	public Window() {
		super();
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

		roadName.setBounds(746, 700, 300, 30);
		frame.getContentPane().add(roadName);
		
		mapView = new MapView(p, t, c, roadName);
		
		mapView.setBounds(20,45,690,690);
		frame.getContentPane().add(mapView);
		//mapView.colorBackground(java.awt.Color.WHITE);
		SwingUtilities.updateComponentTreeUI(frame);
		
		requestView = new RequestView(p);
		requestView.setBounds(746, 49, 300, 200);
		frame.getContentPane().add(requestView);

		btnAddRequest.setBounds(1016, 249, 30, 30);
		btnAddRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Test press button add");
				c.addRequest();
			}
		});
		frame.getContentPane().add(btnAddRequest);
		btnDeleteRequest.setBounds(976, 249, 30, 30);
		btnDeleteRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Test press button delete");
				int index = requestView.getTable().getSelectedRow();
				System.out.println(index);
				if (index>=0) {
					Request r = p.getRequests().get(index);
					c.deleteRequest(r.getPickup().getAddress().getId(),
							r.getDelivery().getAddress().getId());
				}
			}
		});
		frame.getContentPane().add(btnDeleteRequest);
		setDeleteButtonEnabled(false);
		
		mapInfoView = new MapInfoView(p);  
		mapInfoView.setBounds(746,289,300,90);
		frame.getContentPane().add(mapInfoView);
		
		checkPointView = new CheckPointView(t);
		checkPointView.setBounds(746, 389, 300, 270);
		frame.getContentPane().add(checkPointView);

		btnModifyTourUp.setBounds(1016, 659, 30, 30);
		btnModifyTourUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Modify Up");
				int index = checkPointView.getTable().getSelectedRow();
				if (index>1 && index<t.getCheckPoint().size()) {
					c.modifyRequest(index, index-1);
				}
			}
		});
		btnModifyTourDown.setBounds(976, 659, 30, 30);
		btnModifyTourDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Modify Down");
				int index = checkPointView.getTable().getSelectedRow();
				if (index>0 && index<t.getCheckPoint().size()-1) {
					c.modifyRequest(index, index+1);
				}
			}
		});
		setModifyButtonsEnabled(false);
		frame.getContentPane().add(btnModifyTourUp);
		frame.getContentPane().add(btnModifyTourDown);
		
		btnLoadMap.setBounds(12, 12, 100, 27);
		btnLoadMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.loadMap();
			}
		});
		frame.getContentPane().add(btnLoadMap);
		
		
		btnLoadRequest.setBounds(132, 12, 100, 27);
		btnLoadRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.loadRequest();
			}
		});
		frame.getContentPane().add(btnLoadRequest);
		
		btnComputeTour.setBounds(252, 12, 100, 27);
		btnComputeTour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.computeTour();
			}
		});
		frame.getContentPane().add(btnComputeTour);

		
		btnStopComputing.setBounds(372, 12, 100, 27);
		btnStopComputing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.stopTour();
			}
		});
		frame.getContentPane().add(btnStopComputing);
		
		btnUndo.setBounds(492, 12, 100, 27);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.undo();
			}
		});
		frame.getContentPane().add(btnUndo);

		btnRedo.setBounds(612, 12, 100, 27);
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.redo();
			}
		});
		frame.getContentPane().add(btnRedo);
		
		message.setBounds(746, 15, 300, 30);
		frame.getContentPane().add(message);
		
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}
	
	public JLabel getMessage() {
		return message;
	}
	public JLabel getRoadName() {
		return roadName;
	}

	public MapView getMapView() {
		return mapView;
	}
	
	public void setAddRequestEnabled(boolean b) {
		btnAddRequest.setEnabled(b);
	}
	
	public void setDeleteButtonEnabled(boolean b) {
		btnDeleteRequest.setEnabled(b);
	}
	
	public void setModifyButtonsEnabled(boolean b) {
		btnModifyTourUp.setEnabled(b);
		btnModifyTourDown.setEnabled(b);
	}
	
	public void setLoadMapButtonEnabled(boolean b) {
		btnLoadMap.setEnabled(b);
	}
	
	public void setLoadRequestButtonEnabled(boolean b) {
		btnLoadRequest.setEnabled(b);
	}
	
	public void setComputeTourButtonEnabled(boolean b) {
		btnComputeTour.setEnabled(b);
	}
	
	public void setStopComputingButtonEnabled(boolean b) {
		btnStopComputing.setEnabled(b);
	}
}
