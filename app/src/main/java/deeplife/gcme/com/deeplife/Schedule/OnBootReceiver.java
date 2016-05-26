package deeplife.gcme.com.deeplife.Schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import deeplife.gcme.com.deeplife.DeepLife;
import deeplife.gcme.com.deeplife.Models.Schedule;

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
				SimpleDateFormat format = new SimpleDateFormat(ScheduleListFragment.DATE_TIME_FORMAT);

				try {
					java.util.Date date = format.parse(dateTime);
                    if(date.before(cal.getTime())){
                        Log.e("OnBootReceiver", "Alarm time already passed!!");
                    }
                    else {
                        cal.setTime(date);
                        reminderMgr.setReminder(rowId, cal);
                    }
				} catch (java.text.ParseException e) {
					Log.e("OnBootReceiver", e.getMessage(), e);
				}
				

		}
	}
}

