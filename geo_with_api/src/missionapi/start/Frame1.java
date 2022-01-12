package missionapi.start;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import missionapi.classes.Styles.POINT_TYPE;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.awt.event.ActionEvent;

import missionapi.classes.Missions;
import missionapi.classes.Missions.MISSIONS;
import missionapi.classes.PointClass;

import javax.swing.JTable;

public class Frame1 extends JFrame {

	private JPanel contentPane;
	private JTextField latTextField;
	private JTextField lonTextField;
	private JTextField pointNameTextField;
	private JTextField fontNameTextField;
	private JTextField fontSizeTextField;
	private JComboBox missionComboBox;
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
		setBounds(100, 100, 660, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 624, 389);
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
				APIDeneme.API.addPoint(pType, lat, lon, pointName, Color.RED, fontName, fontSize);
				APIDeneme.API.drawTrajectory(Color.BLUE, 2);
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
		missionComboBox.setModel(new DefaultComboBoxModel(MISSIONS.values()));
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
		drawButton.setBounds(271, 94, 89, 23);
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
	}

	private void drawButtonAction() {
		MISSIONS mType = (MISSIONS) missionComboBox.getSelectedItem();
		APIDeneme.API.drawTrajectory(missionsClass.getMission(mType), Color.RED, 2);
	}
	
	public DefaultTableModel getTableModel() {
		return tableModel;
	}
}
