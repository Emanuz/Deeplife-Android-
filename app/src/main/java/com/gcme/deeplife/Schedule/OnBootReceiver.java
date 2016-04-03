package com.gcme.deeplife.Schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.util.Log;

import com.gcme.deeplife.DeepLife;
import com.gcme.deeplife.Models.Schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OnBootReceiver extends BroadcastReceiver {
	
	private static final String TAG = ComponentInfo.class.getCanonicalName();
	
	@Override
	public void onReceive(Context context, Intent intent) {

		ReminderManager reminderMgr = new ReminderManager(context);

		ArrayList<Schedule> cursor = DeepLife.myDatabase.get_All_Schedule();
		int max = cursor.size();
		int i=0;
		if(i<max) {
				Log.d(TAG, "Adding alarm from boot.");

				Long rowId = Long.parseLong(cursor.get(i).getID());
				String dateTime = cursor.get(i).getAlarm_Time();

				Calendar cal = Calendar.getInstance();
				SimpleDateFormat format = new SimpleDateFormat(Schedules.DATE_TIME_FORMAT);
				
				try {
					java.util.Date date = format.parse(dateTime);
					cal.setTime(date);
					
					reminderMgr.setReminder(rowId, cal); 
				} catch (java.text.ParseException e) {
					Log.e("OnBootReceiver", e.getMessage(), e);
				}
				

		}
	}
}

