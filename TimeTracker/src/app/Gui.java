package app;

import javax.swing.JPanel;
import java.awt.Font;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Gui extends JPanel {
	public JTextField txtStartTime;
	public JTextField txtRunningTime;
	public JTextField txtStopTime;
	public String stdTxt = "0:00:00.000";
	JLabel lblVersion;
	
	public JButton btnStart;
	public JButton btnStop;
	public JButton btnReset;
	public JButton btnClose;
	public JButton btnTest;
	public boolean running;

	public JList<String> lstTimeLog;
	public DefaultListModel<String> lstModel = new DefaultListModel<String>();
	public JButton btnAddToLog;
	public JButton btnClearLog;
	public JButton btnSaveLog;
	public JButton btnReloadLog;
	public JButton btnEditLine;
	public JButton btnDeleteLine;

	public String resPath = "/resources";
	
	public Gui()
	{
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		setLayout(null);
		
		btnStart = new JButton("Start");
		btnStart.setFont(new Font("Arial", Font.BOLD, 20));
		btnStart.setBounds(80, 70, 128, 51);
		add(btnStart);
		
		btnStop = new JButton("Stop");
		btnStop.setFont(new Font("Arial", Font.BOLD, 20));
		btnStop.setBounds(218, 70, 128, 51);
		add(btnStop);
		
		btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Arial", Font.BOLD, 20));
		btnReset.setBounds(359, 70, 128, 51);
		add(btnReset);
		
		btnAddToLog = new JButton("Add to log");
		btnAddToLog.setFont(new Font("Arial", Font.BOLD, 12));
		btnAddToLog.setBounds(80, 141, 95, 23);
		add(btnAddToLog);
		
		btnClearLog = new JButton("Clear log");
		btnClearLog.setFont(new Font("Arial", Font.BOLD, 12));
		btnClearLog.setBounds(181, 141, 95, 23);
		add(btnClearLog);
		
		btnSaveLog = new JButton("Save log");
		btnSaveLog.setFont(new Font("Arial", Font.BOLD, 12));
		btnSaveLog.setBounds(286, 141, 95, 23);
		add(btnSaveLog);
		
		btnReloadLog = new JButton("Reload log");
		btnReloadLog.setFont(new Font("Arial", Font.BOLD, 12));
		btnReloadLog.setBounds(391, 141, 95, 23);
		add(btnReloadLog);
		
		btnEditLine = new JButton("Edit line");
		btnEditLine.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnEditLine.setBounds(327, 397, 89, 23);
		add(btnEditLine);
		
		btnDeleteLine = new JButton("Delete line");
		btnDeleteLine.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnDeleteLine.setBounds(426, 397, 89, 23);
		add(btnDeleteLine);

		btnClose = new JButton();
		btnClose.setBorder(null);
		btnClose.setIcon(new ImageIcon(Gui.class.getResource(resPath + "/images/16x16_Close.png")));
		btnClose.setBounds(542, 7, 16, 16);
		add(btnClose);
		
		btnTest = new JButton();
		btnTest.setBorder(null);
		btnTest.setIcon(new ImageIcon(Gui.class.getResource(resPath + "/images/16x16_Test.png")));
		btnTest.setBounds(522, 390, 16, 16);
		add(btnTest);
		
		txtStartTime = new JTextField();
		txtStartTime.setEditable(false);
		txtStartTime.setHorizontalAlignment(SwingConstants.CENTER);
		txtStartTime.setForeground(Color.GREEN);
		txtStartTime.setBackground(Color.BLACK);
		txtStartTime.setText(stdTxt);
		txtStartTime.setFont(new Font("Arial", Font.BOLD, 26));
		txtStartTime.setBounds(28, 23, 164, 36);
		add(txtStartTime);
		
		txtRunningTime = new JTextField();
		txtRunningTime.setText(stdTxt);
		txtRunningTime.setHorizontalAlignment(SwingConstants.CENTER);
		txtRunningTime.setForeground(Color.GREEN);
		txtRunningTime.setFont(new Font("Arial", Font.BOLD, 26));
		txtRunningTime.setEditable(false);
		txtRunningTime.setColumns(10);
		txtRunningTime.setBackground(Color.BLACK);
		txtRunningTime.setBounds(200, 23, 164, 36);
		add(txtRunningTime);
		
		txtStopTime = new JTextField();
		txtStopTime.setText(stdTxt);
		txtStopTime.setHorizontalAlignment(SwingConstants.CENTER);
		txtStopTime.setForeground(Color.GREEN);
		txtStopTime.setFont(new Font("Arial", Font.BOLD, 26));
		txtStopTime.setEditable(false);
		txtStopTime.setColumns(10);
		txtStopTime.setBackground(Color.BLACK);
		txtStopTime.setBounds(374, 23, 164, 36);
		add(txtStopTime);
		
		lstTimeLog = new JList(lstModel);
		lstTimeLog.setBackground(Color.BLACK);
		lstTimeLog.setForeground(Color.GREEN);
		lstTimeLog.setBounds(28, 175, 510, 215);
		add(lstTimeLog);
		
		lblVersion = new JLabel();
		lblVersion.setFont(new Font("Arial", Font.ITALIC, 9));
		lblVersion.setBounds(10, 401, 120, 14);
		add(lblVersion);
		
	}
}
