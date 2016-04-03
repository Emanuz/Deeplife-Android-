package com.gcme.deeplife.Schedule;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.media.MediaPlayer;
import android.util.Log;

import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.R;

public class OnAlarmReceiver extends BroadcastReceiver {

	private static final String TAG = ComponentInfo.class.getCanonicalName();
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "Received wake up from alarm manager.");
		
		long rowid = intent.getExtras().getLong(DeepLife.SCHEDULES_COLUMN[0]);
		
		WakeReminderIntentService.acquireStaticLock(context);

        notify(context);

        Intent i = new Intent(context, ReminderService.class);
		i.putExtra(DeepLife.SCHEDULES_COLUMN[0], rowid);
		context.startService(i);
		 
	}

	public void SetAlarm(Context context)
	{
		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, OnAlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 10, pi); // Millisec * Second * Minute
	}

	public void CancelAlarm(Context context)
	{
		Intent intent = new Intent(context, OnAlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}

    public void notify(Context context) {


        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("DeepLife Schedule ").setMessage("You have a schedule with ?")
                .setPositiveButton("OK ", dialogClickListener)
                .show();
    }

}
