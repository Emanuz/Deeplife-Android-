package deeplife.gcme.com.deeplife.Schedule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import deeplife.gcme.com.deeplife.Database.Database;

import java.util.Calendar;

public class ReminderManager {

	private Context myContext;
	private AlarmManager mAlarmManager;
	
	public ReminderManager(Context context) {
		myContext = context;
		mAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	}
	
	public void setReminder(long taskId, Calendar when) {
		
        Intent i = new Intent(myContext, OnAlarmReceiver.class);
        i.putExtra(Database.SCHEDULES_COLUMN[0], (long)taskId);
		Log.i(Database.TAG,"Row at REMinder manager "+ taskId);
        PendingIntent pi = PendingIntent.getBroadcast(myContext, (int)taskId, i, PendingIntent.FLAG_ONE_SHOT);
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), pi);
	}
	public void CancelAlarm(int taskID)
	{
		Intent intent = new Intent(myContext, OnAlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(myContext, taskID, intent,PendingIntent.FLAG_ONE_SHOT);
		AlarmManager alarmManager = (AlarmManager) myContext.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}
}
