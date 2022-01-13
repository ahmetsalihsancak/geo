package geotools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import geotools.mission.Missions;
import geotools.mission.Missions.MISSIONS;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import missionapi.classes.Styles.POINT_TYPE;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.awt.event.ActionEvent;

import missionapi.MissionAPI;
import missionapi.classes.LayerBuilder;
import missionapi.classes.PointClass;

import javax.swing.JTable;
import javax.swing.SwingConstants;

public class Frame1 extends JFrame {

	private JPanel contentPane;
	private JTextField latTextField;
	private JTextField lonTextField;
	private JTextField pointNameTextField;
	private JTextField fontNameTextField;
	private JTextField fontSizeTextField;
	private JComboBox<Object> missionComboBox;
	private Missions missionsClass;
	private JTable table;
	private DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 frame = new Frame1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public Frame1() {
		missionsClass = new Missions();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 624, 414);
		contentPane.add(panel);
		panel.setLayout(null);
		
		latTextField = new JTextField();
		latTextField.setText("0");
		latTextField.setBounds(0, 31, 86, 20);
		panel.add(latTextField);
		latTextField.setColumns(10);
		
		lonTextField = new JTextField();
		lonTextField.setText("0");
		lonTextField.setBounds(96, 31, 86, 20);
		panel.add(lonTextField);
		lonTextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("LAT");
		lblNewLabel.setBounds(0, 11, 46, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("LON");
		lblNewLabel_1.setBounds(96, 11, 46, 14);
		panel.add(lblNewLabel_1);
		
		JComboBox<Object> pointTypeComboBox = new JComboBox<Object>();
		pointTypeComboBox.setModel(new DefaultComboBoxModel<Object>(POINT_TYPE.values()));
		pointTypeComboBox.setBounds(192, 30, 86, 22);
		panel.add(pointTypeComboBox);
		
		JLabel lblNewLabel_2 = new JLabel("POINT TYPE");
		lblNewLabel_2.setBounds(192, 11, 86, 14);
		panel.add(lblNewLabel_2);
		
		pointNameTextField = new JTextField();
		pointNameTextField.setBounds(288, 31, 86, 20);
		panel.add(pointNameTextField);
		pointNameTextField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("POINT NAME");
		lblNewLabel_3.setBounds(288, 11, 86, 14);
		panel.add(lblNewLabel_3);
		
		fontNameTextField = new JTextField();
		fontNameTextField.setText("Georgia");
		fontNameTextField.setBounds(384, 31, 86, 20);
		panel.add(fontNameTextField);
		fontNameTextField.setColumns(10);
		
		fontSizeTextField = new JTextField();
		fontSizeTextField.setText("12");
		fontSizeTextField.setBounds(480, 31, 86, 20);
		panel.add(fontSizeTextField);
		fontSizeTextField.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("FONT NAME");
		lblNewLabel_4.setBounds(384, 11, 86, 14);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("FONT SIZE");
		lblNewLabel_5.setBounds(480, 11, 86, 14);
		panel.add(lblNewLabel_5);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double lat = Double.parseDouble(latTextField.getText());
				double lon = Double.parseDouble(lonTextField.getText());
				POINT_TYPE pType = (POINT_TYPE) pointTypeComboBox.getSelectedItem();
				String pointName = pointNameTextField.getText();
				String fontName = fontNameTextField.getText();
				int fontSize = Integer.parseInt(fontSizeTextField.getText());
				APIMapConfig.API.addPoint(pType, lat, lon, pointName, Color.RED, fontName, fontSize);
				APIMapConfig.API.drawTrajectory(Color.BLUE, 2);
				APIMapConfig.API.afterDraw();
				APIMapConfig.testActive = false;
				
				if (tableModel.getRowCount() > 0) {
					for (int i = tableModel.getRowCount()-1; i >= 0; i--) {
						tableModel.removeRow(i);
					}
				}
				
				for (PointClass points : APIMapConfig.API.getPointsList()) {
					tableModel.addRow(new Object[] {
							points.getPointNo(), 
							points.getPointName(), 
							points.getPointType(), 
							points.getLatitude(), 
							points.getLongtitude()
					});
				}
			}
		});
		okButton.setBounds(576, 30, 48, 23);
		panel.add(okButton);

		JLabel lblNewLabel_12 = new JLabel("SELECTED MISSION: ");
		lblNewLabel_12.setBounds(96, 98, 168, 14);
		panel.add(lblNewLabel_12);
		
		missionComboBox = new JComboBox();
		missionComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MISSIONS mType = (MISSIONS) missionComboBox.getSelectedItem();

				if (tableModel.getRowCount() > 0) {
					for (int i = tableModel.getRowCount()-1; i >= 0; i--) {
						tableModel.removeRow(i);
					}
				}
				
				for (PointClass points : missionsClass.getMission(mType)) {
					tableModel.addRow(new Object[] {
							points.getPointNo(), 
							points.getPointName(), 
							points.getPointType(), 
							points.getLatitude(), 
							points.getLongtitude()
					});
				}
				lblNewLabel_12.setText("SELECTED MISSION: " + mType);
			}
		});
		missionComboBox.setModel(new DefaultComboBoxModel<Object>(MISSIONS.values()));
		missionComboBox.setBounds(0, 94, 86, 22);
		panel.add(missionComboBox);
		
		JLabel lblNewLabel_6 = new JLabel("SELECT MISSION");
		lblNewLabel_6.setBounds(0, 69, 182, 14);
		panel.add(lblNewLabel_6);
		
		JButton drawButton = new JButton("DRAW");
		drawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawButtonAction();
			}
		});
		drawButton.setBounds(240, 94, 89, 23);
		panel.add(drawButton);

        tableModel = new DefaultTableModel(
    			new Object[][] {
    			},
    			new String[] {
    					"Point No", "Point Name", "Point Type", "LAT", "LONG"
    			}
    		);
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setBounds(0, 158, 614, 220);
		table.setModel(tableModel);
		panel.add(table);
		
		JLabel lblNewLabel_7 = new JLabel("POINT NO");
		lblNewLabel_7.setBounds(0, 133, 120, 14);
		panel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("POINT NAME");
		lblNewLabel_8.setBounds(120, 133, 120, 14);
		panel.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("POINT TYPE");
		lblNewLabel_9.setBounds(240, 133, 120, 14);
		panel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("LAT");
		lblNewLabel_10.setBounds(360, 133, 120, 14);
		panel.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("LON");
		lblNewLabel_11.setBounds(494, 133, 120, 14);
		panel.add(lblNewLabel_11);
		
		JButton rectTestButton = new JButton("RECT TEST");
		rectTestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				APIMapConfig.API.deleteAllLayersAndPoints();
				APIMapConfig.API.drawRectangle(33, 33, 250, 120, 0, Color.RED);
				APIMapConfig.API.drawRectangle(33, 40, 250, 120, 45, Color.BLUE);
				APIMapConfig.API.drawRectangle(33, 45, 250, 120, 222, Color.ORANGE);
				APIMapConfig.API.drawRectangle(33, 51, 250, 120, 118, Color.MAGENTA);
				APIMapConfig.API.afterDraw();
			}
		});
		rectTestButton.setBounds(10, 389, 89, 23);
		panel.add(rectTestButton);
		
		JButton IRLARtestButton = new JButton("IR LAR TEST");
		IRLARtestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				APIMapConfig.API.deleteAllLayersAndPoints();
				APIMapConfig.API.drawIRLAR(41, 28, Color.RED, 250);
				APIMapConfig.API.drawIRLAR(33, 28, Color.BLUE, 120);
				APIMapConfig.API.drawIRLAR(41, 33, Color.ORANGE, 3000);
				APIMapConfig.API.drawIRLAR(33, 33, Color.MAGENTA, 25);
				APIMapConfig.API.afterDraw();
			}
		});
		IRLARtestButton.setBounds(120, 389, 120, 23);
		panel.add(IRLARtestButton);
		
		JButton IZLARtestButton = new JButton("IZ LAR TEST");
		IZLARtestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				APIMapConfig.API.deleteAllLayersAndPoints();
				APIMapConfig.API.drawIZLAR(33, 33, Color.RED, 2, 150, 25, 0);
				APIMapConfig.API.drawIZLAR(43, 33, Color.RED, 4, 150, 25, 0);
				APIMapConfig.API.drawIZLAR(35, 38, Color.RED, 2, 150, 25, 45);
				APIMapConfig.API.drawIZLAR(43, 38, Color.RED, 2, 250, 100, 0);
				APIMapConfig.API.drawIZLAR(33, 50, Color.RED, 2, 250, 100, 66);
				APIMapConfig.API.afterDraw();
			}
		});
		IZLARtestButton.setBounds(260, 389, 100, 23);
		panel.add(IZLARtestButton);
		
		JButton lineTestButton = new JButton("LINE TEST");
		lineTestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				APIMapConfig.API.deleteAllLayersAndPoints();
				APIMapConfig.API.drawLine(42, 28, 38, 43, Color.RED, 2);
				APIMapConfig.API.drawLine(40, 28, 36, 43, Color.RED, 5);
				APIMapConfig.API.drawLine(38, 28, 34, 43, Color.BLUE, 2);
				APIMapConfig.API.afterDraw();
			}
		});
		lineTestButton.setBounds(381, 389, 89, 23);
		panel.add(lineTestButton);
		
		JButton dashedLineTestButton = new JButton("DASHED LINE TEST");
		dashedLineTestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				APIMapConfig.API.deleteAllLayersAndPoints();
				APIMapConfig.API.drawDashedLine(44, 28, 40, 43, LayerBuilder.getDefaultDotCount(), Color.RED, 2);
				APIMapConfig.API.drawDashedLine(42, 28, 38, 43, LayerBuilder.getDefaultDotCount(), Color.RED, 5);
				APIMapConfig.API.drawDashedLine(40, 28, 36, 43, 20, Color.BLUE, 2);
				APIMapConfig.API.drawDashedLine(38, 28, 34, 43, 10, Color.ORANGE, 2);
				APIMapConfig.API.drawDashedLine(36, 28, 32, 43, 5, Color.MAGENTA, 2);
				APIMapConfig.API.afterDraw();
			}
		});
		dashedLineTestButton.setBounds(494, 389, 120, 23);
		panel.add(dashedLineTestButton);
		
		JButton deleteAllButton = new JButton("DELETE ALL");
		deleteAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				APIMapConfig.API.deleteAllLayersAndPoints();
				APIMapConfig.API.afterDraw();
			}
		});
		deleteAllButton.setBackground(Color.RED);
		deleteAllButton.setForeground(Color.WHITE);
		deleteAllButton.setBounds(480, 94, 134, 23);
		panel.add(deleteAllButton);
	}

	private void drawButtonAction() {
		APIMapConfig.API.deleteAllLayersAndPoints();
		MISSIONS mType = (MISSIONS) missionComboBox.getSelectedItem();
		APIMapConfig.API.drawTrajectory(missionsClass.getMission(mType), Color.RED, 2, APIMapConfig.defaultFontName, APIMapConfig.defaultFontSize);
		APIMapConfig.API.afterDraw();
		APIMapConfig.testActive = false;
	}
	
	public DefaultTableModel getTableModel() {
		return tableModel;
	}
}
