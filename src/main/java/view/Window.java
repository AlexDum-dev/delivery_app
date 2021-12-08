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

/**
 * Window main class
 * 
 * @author 4IF Group H4144
 * @version 1.0 7 Dec 2021
 */
public class Window {
	private JFrame frame;
	private RequestView requestView;
	private CheckPointView checkPointView;
	private MapView mapView;
	private MapInfoView mapInfoView;
	private JButton btnLoadMap;
	private JButton btnLoadRequest;
	private JButton btnComputeTour;
	private JButton btnStopComputing;
	private JButton btnUndo;
	private JButton btnRedo;
	private JButton btnAddRequest;
	private JButton btnDeleteRequest;
	private JButton btnModifyTourUp;
	private JButton btnModifyTourDown;
	private JLabel message;
	private JLabel roadName;

	public Window(Plan p, Tour t, Controller c) {
		btnLoadMap = new JButton("Load Map");
		btnLoadRequest = new JButton("Load Request");
		btnComputeTour = new JButton("Compute Tour");
		btnStopComputing = new JButton("Stop Computing");
		btnUndo = new JButton("Undo");
		btnRedo = new JButton("Redo");
		btnAddRequest = new JButton("", new ImageIcon("plus.png"));
		btnDeleteRequest = new JButton("", new ImageIcon("minus.png"));
		btnModifyTourUp = new BasicArrowButton(BasicArrowButton.NORTH);
		btnModifyTourDown = new BasicArrowButton(BasicArrowButton.SOUTH);
		clearButtons();
		message=new JLabel("Please load a map.");
		roadName=new JLabel("");
		initialize(p, t, c);
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

		initMapView(c, p, t);

		initRequestView(c, p);

		initMapInfoView(p);

		initCheckPointView(c, t);

		initButtonBar(c);

		roadName.setBounds(746, 700, 300, 30);
		frame.getContentPane().add(roadName);

		message.setBounds(746, 15, 300, 30);
		frame.getContentPane().add(message);

		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
	}

	private void initMapView(Controller c, Plan p, Tour t) {
		mapView = new MapView(p, t, c, roadName);

		mapView.setBounds(20,45,690,690);
		frame.getContentPane().add(mapView);
		//mapView.colorBackground(java.awt.Color.WHITE);
		SwingUtilities.updateComponentTreeUI(frame);
	}

	private void initRequestView(Controller c, Plan p) {
		requestView = new RequestView(p);
		requestView.setBounds(746, 49, 300, 200);
		frame.getContentPane().add(requestView);

		btnAddRequest.setBounds(1016, 249, 30, 30);
		btnAddRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.addRequest();
			}
		});
		frame.getContentPane().add(btnAddRequest);
		btnDeleteRequest.setBounds(976, 249, 30, 30);
		btnDeleteRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = requestView.getTable().getSelectedRow();
				if (index>=0) {
					Request r = p.getRequests().get(index);
					c.deleteRequest(r);
				}
			}
		});
		frame.getContentPane().add(btnDeleteRequest);
		setDeleteButtonEnabled(false);
	}

	private void initMapInfoView(Plan p) {
		mapInfoView = new MapInfoView(p);  
		mapInfoView.setBounds(746,289,300,90);
		frame.getContentPane().add(mapInfoView);
	}

	private void initCheckPointView(Controller c, Tour t) {
		checkPointView = new CheckPointView(t);
		checkPointView.setBounds(746, 389, 300, 270);
		frame.getContentPane().add(checkPointView);

		btnModifyTourUp.setBounds(1016, 659, 30, 30);
		btnModifyTourUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = checkPointView.getTable().getSelectedRow();
				if (index>1 && index<t.getCheckPoints().size()) {
					c.modifyRequest(index, index-1);
				}
			}
		});
		btnModifyTourDown.setBounds(976, 659, 30, 30);
		btnModifyTourDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = checkPointView.getTable().getSelectedRow();
				if (index>0 && index<t.getCheckPoints().size()-1) {
					c.modifyRequest(index, index+1);
				}
			}
		});
		frame.getContentPane().add(btnModifyTourUp);
		frame.getContentPane().add(btnModifyTourDown);
	}

	private void initButtonBar(Controller c) {
		btnLoadMap.setBounds(12, 12, 130, 27);
		btnLoadMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.loadMap();
			}
		});
		frame.getContentPane().add(btnLoadMap);

		setLoadMapButtonEnabled(true);

		btnLoadRequest.setBounds(152, 12, 130, 27);
		btnLoadRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.loadRequest();
			}
		});
		frame.getContentPane().add(btnLoadRequest);

		btnComputeTour.setBounds(292, 12, 130, 27);
		btnComputeTour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.computeTour();
			}
		});
		frame.getContentPane().add(btnComputeTour);


		btnStopComputing.setBounds(432, 12, 130, 27);
		btnStopComputing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.stopTour();
			}
		});
		frame.getContentPane().add(btnStopComputing);

		btnUndo.setBounds(572, 12, 70, 27);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.undo();
			}
		});
		frame.getContentPane().add(btnUndo);

		btnRedo.setBounds(652, 12, 70, 27);
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.redo();
			}
		});
		frame.getContentPane().add(btnRedo);
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

	public void setDoButtonsEnabled(boolean b) {
		btnUndo.setEnabled(b);
		btnRedo.setEnabled(b);
	}

	public void clearButtons() {
		setLoadMapButtonEnabled(false);
		setLoadRequestButtonEnabled(false);
		setComputeTourButtonEnabled(false);
		setStopComputingButtonEnabled(false);
		setAddRequestEnabled(false);
		setDeleteButtonEnabled(false);
		setModifyButtonsEnabled(false);
		setDoButtonsEnabled(false);
	}
}
