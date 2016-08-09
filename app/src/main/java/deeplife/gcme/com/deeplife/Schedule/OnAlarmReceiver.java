package deeplife.gcme.com.deeplife.Schedule;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.util.Log;

import deeplife.gcme.com.deeplife.Database.Database;

public class OnAlarmReceiver extends BroadcastReceiver {

	private static final String TAG = ComponentInfo.class.getCanonicalName();
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "Received wake up from alarm manager.");
		
		long rowid = intent.getExtras().getLong(Database.SCHEDULES_COLUMN[0]);
		
		WakeReminderIntentService.acquireStaticLock(context);

		Intent intent2 = new Intent();
		intent2.putExtra(Database.SCHEDULES_COLUMN[0], rowid);
		intent2.setClass(context,Music_Play.class);
		intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent2);


		Intent i = new Intent(context, ReminderService.class);
		i.putExtra(Database.SCHEDULES_COLUMN[0], rowid);
		context.startService(i);
		 
	}

	public void SetAlarm(Context context, int taskId, long interval)
	{
		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, OnAlarmReceiver.class);
		i.putExtra(Database.SCHEDULES_COLUMN[0], taskId);
		PendingIntent pi = PendingIntent.getBroadcast(context, taskId, i, 0);
		am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis()+interval, pi); // Millisec * Second * Minute
	}

	public void CancelAlarm(Context context, int taskID)
	{
		Intent intent = new Intent(context, OnAlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, taskID, intent,PendingIntent.FLAG_ONE_SHOT);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}


}
