package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class TimeTracker extends JFrame{
	
	// version code: x.y.zzzzzz-aaa => x.y = release version // zzzzzz = date changes made for test version // aaa = running no. for day.
	private String verTxt = "Version: "+ "0.2.190329-000";
	private boolean testMode = false;
	
	private int appFramePosX = 500, appFramePosY = 200;
	private static JFrame appFrame = new JFrame();
	private static Gui gui = new Gui();
	private static TimeHandler time = new TimeHandler();
	private static boolean running = true;
	private boolean timerRunning = false;
	private String logEntry;
	private String logString;
	private String logContent="";
	private int logIndex;
	private String dTxt = "";

    private List<String> list;
	
	public TimeTracker(){
		Thread t = new Thread()
		{
			public void run()
			{
				while(running)
				{
					gui.txtRunningTime.setText(time.durationTracker());
					try {sleep(1);} catch (Exception e) {}
				}
			}
		};
		t.start();
		initFrame();
		actionListeners();
		debug();
	}

	public static void main(String[] args) 	{
		new TimeTracker();
	}
	
	public void initFrame()	{		
		gui.lblVersion.setText(verTxt);
		System.out.println(verTxt);
		appFrame.add(gui);
		appFrame.setSize(565, 435);
		appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    appFrame.setUndecorated(true);
	    appFrame.setLocation(appFramePosX, appFramePosY);
	    appFrame.setVisible(true);
	    gui.lstModel.clear();
	    reloadLog();
	}

	public void readFile(String dir, String file) {
		Path path = Paths.get(dir, file);
		try {
		    gui.lstModel.clear();
			list = Files.readAllLines(path, Charset.defaultCharset());
			list.forEach(line -> gui.lstModel.addElement(line));
		} catch (IOException e) {}
	}
	

	public void writeFile(String dir, String file) {
		logContent = "";
		Path path = Paths.get(dir, file);
		for (int i = 0; i < gui.lstModel.size(); i++) {
			logContent = logContent + gui.lstModel.elementAt(i) + "\n";
		}
		try {
			Files.write(path, logContent.getBytes());
		} catch (IOException e) {}
	}
	
	public void actionListeners()	{
		appFrame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e)
			{
				appFramePosX = e.getX();
				appFramePosY = e.getY();
			}
		});		
		appFrame.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e)
			{
				appFrame.setLocation(e.getXOnScreen()-appFramePosX, e.getYOnScreen()-appFramePosY);
			}
		});
		gui.lstTimeLog.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				logIndex = gui.lstTimeLog.getSelectedIndex();
			}
		});
		
		gui.btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				startTime();
			}
		});
		gui.btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				stopTime();
			}
		});
		gui.btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				resetTime();
			}
		});
		gui.btnAddToLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				addToLog();
			}
		});
		gui.btnClearLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				clearLog();
			}
		});
		gui.btnSaveLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveLog();
			}
		});
		gui.btnReloadLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reloadLog();
			}
		});

		gui.btnEditLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editLine();
			}
		});
		gui.btnDeleteLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteLine();
			}
		});
		gui.btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				closeApp();
			}
		});
		gui.btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				testMode = true;
				debug();
			}
		});
	}
	
	public void startTime()	{
		gui.txtStartTime.setText(time.startTimeTracker());
		gui.txtStopTime.setText(gui.stdTxt);
		timerRunning = true;
	}
	
	public void stopTime()	{
		gui.txtStopTime.setText(time.stopTimeTracker());
		timerRunning = false;
	}
	
	public void resetTime()	{
		gui.txtStartTime.setText(gui.stdTxt);
		gui.txtStopTime.setText(gui.stdTxt);
		gui.txtRunningTime.setText(gui.stdTxt);
		time.durMillis = 0;
	}
	
	public void addToLog()	{
		if (!timerRunning && time.durMillis != 0)
		{
			logEntry = JOptionPane.showInputDialog("What did the time log?");
			logString = "<html>Start: <font color=\"yellow\">" + gui.txtStartTime.getText() + "</font> || Stop: <font color=\"yellow\">" + gui.txtStopTime.getText() + "</font> || Duration: <font color=\"yellow\">" + time.durationTracker() + "</font> || <font color=\"yellow\">" + logEntry;
			gui.lstModel.addElement(logString);
		}
	}
	
	public void clearLog() 	{
		gui.lstModel.clear();
		gui.lstModel.addElement("<html><img src='http://icons.iconarchive.com/icons/treetog/junior/16/document-url-icon.png'</img>TimeTracker log:<i>(" + verTxt + ")</i>");
		logContent = "";
	}

	public void saveLog() {
		String pPath = System.getProperty("user.dir") + "/data";
		writeFile(pPath, "TrackLog.txt");
	}
	
	public void reloadLog() {
		String pPath = System.getProperty("user.dir") + "/data";
		readFile(pPath, "TrackLog.txt");
	}

	public void deleteLine()	{
		gui.lstModel.remove(logIndex);
	}

	public void editLine()	{
		String lineTxtSubstring1 = gui.lstModel.getElementAt(logIndex).substring(0, 170);
		String lineTxtSubstring2 = gui.lstModel.getElementAt(logIndex).substring(170);
		logEntry = JOptionPane.showInputDialog("What did the time log?", lineTxtSubstring2);
		logString = lineTxtSubstring1 + logEntry;
		gui.lstModel.setElementAt(logString,  logIndex );
	}
	
	public void closeApp()	{
		int closeChoice = JOptionPane.showConfirmDialog(appFrame, "save log to file before closing?");
		switch (closeChoice) {
		case 0:
			saveLog();
			System.exit(1);
			break;
		case 1:
			System.exit(1);
			break;
		case 2:
			break;
		}
	}
	
	public void fileExplorer()	{
		JFileChooser file = new JFileChooser();
		File f = new File("C:/");
		file.setCurrentDirectory(f);
		file.showOpenDialog(null);
	}

public void debugOut(String dTxt2) {
	dTxt = dTxt + "\n"+ dTxt2;
}
	
	public void debug()	{

		JFrame debugFrame = new JFrame();
		JTextArea debugTxt = new JTextArea(dTxt);
		JScrollPane scrollBar = new JScrollPane(debugTxt);
		scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollBar.setBounds(50, 50, 100, 100);
		debugFrame.add(scrollBar);
		debugFrame.setSize(575, 400);
		debugFrame.setLocation(495, 620);
		debugFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		if(testMode)
		{
			debugFrame.setVisible(true);
		}
		
	}
	
}
