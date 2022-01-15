package rest.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import rest.menu.Menu;
import rest.menu.MenuItem;

public class AppWindow {

	public JFrame frame;
	private JFrame frame2;
	private JTextField txta;
	private DefaultTableModel tableModel;
	private DefaultTableModel tableModel2;
	private JTable table;
	public List<MenuItem> icecekList = new ArrayList<>();
	private JTable table_1;
	private JTextField textField;
	private JLabel cayLabel;
	Menu menu;
	List<String> menuItemNames = new ArrayList<>();
	Object[] tableObject;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppWindow window = new AppWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppWindow() {
		menu = new Menu();
		menu.fillMenu(new File("menu.txt"));
		tableObject = new Object[menu.icecekList.size()+1];
		tableObject[0] = 0;
		tableModel2 = new DefaultTableModel();
		tableModel2.setColumnCount(tableObject.length);
		int count = 1;
		initialize();
		for (MenuItem i : menu.icecekList) {
			tableModel.addRow(new Object[] {
					"Ýçecek",
					i.getName(),
					i.getPrice()
			});
			tableObject[count] = i.getName();
			count++;
		}
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(1200, 800));
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 11, 1164, 739);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1164, 739);
		
		JPanel p2 = new JPanel();
		p2.setBackground(Color.WHITE);
		tabbedPane.add("Menü",p2);
		p2.setLayout(null);
		
		table = new JTable();
		table.setBackground(Color.LIGHT_GRAY);
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setRowSelectionAllowed(false);
		table.setBounds(10, 62, 319, 638);
		p2.add(table);
		
		tableModel = new DefaultTableModel(
    			new Object[][] {
    			},
    			new String[] {
    					"Tür", "Ýsim", "Fiyat"
    			}
    		);

		table.setModel(tableModel);
		JPanel p3 = new JPanel();
		p3.setBackground(Color.WHITE);
		tabbedPane.add("Menü Ekle/Çýkar",p3);
		p3.setLayout(null);
		
		txta = new JTextField();
		txta.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txta.setBounds(10, 74, 195, 50);
		p3.add(txta);
		txta.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = txta.getText();
				Scanner scanner = new Scanner(s);
				while(scanner.hasNext()) {
					String sN = scanner.next();
					System.out.println(sN);
				}
			}
		});
		btnNewButton.setBounds(220, 74, 99, 50);
		p3.add(btnNewButton);
		
		panel.add(tabbedPane);
		JPanel p1 = new JPanel();
		p1.setBackground(Color.WHITE);
		tabbedPane.add("Ödeme",p1);
		p1.setLayout(null);
		
		table_1 = new JTable();
		table_1.setEnabled(false);
		table_1.setBackground(Color.LIGHT_GRAY);
		table_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table_1.setRowSelectionAllowed(false);
		table_1.setBounds(10, 11, 937, 689);
		table_1.setModel(tableModel2);
		p1.add(table_1);
		
		JButton btnNewButton_1 = new JButton("CAY");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(1008, 0, 89, 23);
		p1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("SODA");
		btnNewButton_2.setBounds(1008, 34, 89, 23);
		p1.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("LATTE");
		btnNewButton_3.setBounds(1008, 68, 89, 23);
		p1.add(btnNewButton_3);
		
		textField = new JTextField();
		textField.setBounds(1008, 102, 86, 20);
		p1.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("ADD");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] obj = tableObject;
				obj[0] = 12;
				tableModel2.addRow(obj);
			}
		});
		btnNewButton_4.setBounds(957, 280, 89, 23);
		p1.add(btnNewButton_4);
		
		cayLabel = new JLabel("0");
		cayLabel.setBounds(1103, 4, 46, 14);
		p1.add(cayLabel);
		
		JButton button_1 = new JButton("1");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame pan = new JFrame();
				pan.setBackground(Color.WHITE);
				pan.getContentPane().setLayout(null);
				pan.setSize(new Dimension(400, 300));
				pan.setVisible(true);
			}
		});
		button_1.setBounds(957, 155, 89, 23);
		p1.add(button_1);
		
		JButton button_2 = new JButton("2");
		button_2.setBounds(1060, 155, 89, 23);
		p1.add(button_2);
	}
}
