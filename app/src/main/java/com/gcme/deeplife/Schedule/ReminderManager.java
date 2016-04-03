package com.gcme.deeplife.Schedule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.gcme.deeplife.Database.DeepLife;

import java.util.Calendar;

public class ReminderManager {

	private Context mContext;
	private AlarmManager mAlarmManager;
	
	public ReminderManager(Context context) {
		mContext = context; 
		mAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	}
	
	public void setReminder(Long taskId, Calendar when) {
		
        Intent i = new Intent(mContext, OnAlarmReceiver.class);
        i.putExtra(DeepLife.SCHEDULES_COLUMN[0], (long)taskId);
		Log.i(DeepLife.TAG,"Row at REMinder manager "+ taskId);
        PendingIntent pi = PendingIntent.getBroadcast(mContext, 0, i, PendingIntent.FLAG_ONE_SHOT);
        
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), pi);
	}
}
