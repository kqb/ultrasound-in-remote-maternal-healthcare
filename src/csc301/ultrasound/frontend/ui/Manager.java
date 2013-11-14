package csc301.ultrasound.frontend.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import java.awt.GridBagLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;

import javax.swing.JTabbedPane;

import csc301.ultrasound.model.UserInformation;

import java.awt.GridBagConstraints;

/**
 * Manager interface, it contains the tables for the information of all 
 * radiologists and fieldworkers.
 */
public class Manager extends JFrame {

	private JPanel contentPane;
	private static Connection dbConnection;
	private JTable fwTable;
	private JTable rTable;
	private UserInformation fwModel;
	private UserInformation rModel;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Manager frame = new Manager(dbConnection);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Manager(Connection dbConnection) {
		this.dbConnection = dbConnection;
		buildUI();
		
	}
	public void buildUI(){
		setTitle("Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JButton btnNewButton = new JButton("Update");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateMainTable();
			}
		});
		contentPane.add(btnNewButton, "cell 0 0");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, "cell 0 1,grow");
		
		JPanel fwPanel = new JPanel();
		tabbedPane.addTab("Fieldworkers", null, fwPanel, null);
		GridBagLayout gbl_fwPanel = new GridBagLayout();
		gbl_fwPanel.columnWidths = new int[]{0, 0};
		gbl_fwPanel.rowHeights = new int[]{0, 0};
		gbl_fwPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_fwPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		fwPanel.setLayout(gbl_fwPanel);
		
		fwTable = new JTable();
		GridBagConstraints gbc_fwTable = new GridBagConstraints();
		gbc_fwTable.fill = GridBagConstraints.BOTH;
		gbc_fwTable.gridx = 0;
		gbc_fwTable.gridy = 0;
		fwPanel.add(fwTable, gbc_fwTable);
		
		JPanel rPanel = new JPanel();
		tabbedPane.addTab("Radiologists", null, rPanel, null);
		GridBagLayout gbl_rPanel = new GridBagLayout();
		gbl_rPanel.columnWidths = new int[]{0, 0};
		gbl_rPanel.rowHeights = new int[]{0, 0};
		gbl_rPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_rPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		rPanel.setLayout(gbl_rPanel);
		
		rTable = new JTable();
		GridBagConstraints gbc_rTable = new GridBagConstraints();
		gbc_rTable.fill = GridBagConstraints.BOTH;
		gbc_rTable.gridx = 0;
		gbc_rTable.gridy = 0;
		rPanel.add(rTable, gbc_rTable);
		
		this.setExtendedState( this.getExtendedState()|JFrame.MAXIMIZED_BOTH );

	}
	
	private void updateMainTable()
	{
		if (dbConnection != null)
			fwModel = new UserInformation(5, dbConnection);
			rModel = new UserInformation(4, dbConnection);
			rTable.setModel(rModel);
			fwTable.setModel(fwModel);
	}

}
