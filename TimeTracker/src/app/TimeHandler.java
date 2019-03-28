package app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeHandler 
{
	public SimpleDateFormat formatFull = new SimpleDateFormat("yyyy-MM-dd HH:mm.ss.SSS");
	public SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm.ss");
	public SimpleDateFormat formatTime2 = new SimpleDateFormat("HH:mm.ss");
	public Date startTime;
	public Date stopTime;
	public Date durTime = new Date();
	public Date nowTime;
	public long durMillis = 0;
	
	public TimeHandler() {
		formatFull.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		formatTime.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		formatTime2.setTimeZone(TimeZone.getTimeZone("UCS"));
	}
	
	public String startTimeTracker() {
		startTime = new Date();
		String retString = formatTime.format(startTime);
		return retString;
	}
	
	public String stopTimeTracker() {
		stopTime = new Date();
		startTime = null;
		String retString = formatTime.format(stopTime);
		return retString;
	}
	
	public String durationTracker(){
		if (startTime != null)
		{
		nowTime = new Date();	
		durMillis = nowTime.getTime() - startTime.getTime();
		} 
		durTime = new Date(durMillis);
		String retString = formatTime2.format(durTime);
		return retString;
		
	}
	
	public void delay(long milliSec)
	{
		try {
			Thread.sleep(milliSec);
		} catch (Exception e) {	}
	}

	
	public void errLogger()
	{
		
	}


}
