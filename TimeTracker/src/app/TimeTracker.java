package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TimeTracker extends JFrame{
	
	// version code: x.y.zzzzzz-aaa => x.y = release version // zzzzzz = date changes made for testversion // aaa = running no. for day.
	private String verTxt = "Version: "+ "0.1.190327-005";
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
	
	public TimeTracker()	{
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
		readFile("data", "TrackLog.txt");
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

	}
	
	public void readFile(String dir, String file) {
		String directory = dir;
		String fileName = file;
		Path path = Paths.get(directory, fileName);
	
		try {  
		    List<String> list = Files.readAllLines(path);
		    gui.lstModel.clear();
		    list.forEach(line -> gui.lstModel.addElement(line));
		} catch (IOException e) {}
	}

	public void writeFile(String dir, String file) {
		String directory = dir;
		String fileName = file;

		Path path = Paths.get(directory, fileName);

		for (int i = 0; i < gui.lstModel.size(); i++) {
			logContent = "" + logContent + gui.lstModel.elementAt(i) + "\n";
		}
		
		try {
			Files.write(path, logContent.getBytes() );
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
		gui.btnDeleteLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.lstModel.remove(logIndex);
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
			logString = "<html>Start: <font color=\"yellow\">" + time.startTimeTracker() + "</font> || Stop: <font color=\"yellow\">" + time.stopTimeTracker() + "</font> || Duration: <font color=\"yellow\">" + time.durationTracker() + "</font> || <font color=\"yellow\">" + logEntry;
			gui.lstModel.addElement(logString);
		}
	}
	public void clearLog() 	{
		gui.lstModel.clear();
//		gui.lstModel.addElement("<html><img src=\"image\16x16_Close.png\"TimeTracker log:<i>(" + verTxt + ")</i>");
		gui.lstModel.addElement("<html><img src='http://icons.iconarchive.com/icons/treetog/junior/16/document-url-icon.png'</img>TimeTracker log:<i>(" + verTxt + ")</i>");
		logContent = "";
	}

	public void saveLog() {
		writeFile("data", "TrackLog.txt");
	}
	
	public void reloadLog() {
		readFile("data", "TrackLog.txt");
	}
	
	public void closeApp()
	{
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

	public void debug()
	{	
		String dTxt = "";
		JFrame debugFrame = new JFrame();
		JPanel debugPanel = new JPanel();
		JTextField debugTxt = new JTextField(dTxt);
		debugPanel.add(debugTxt);
		debugFrame.add(debugPanel);
		debugFrame.setSize(575, 400);
		debugFrame.setLocation(495, 620);
		debugFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		if(testMode)
		{
			debugFrame.setVisible(true);
		}
	}
	
}
