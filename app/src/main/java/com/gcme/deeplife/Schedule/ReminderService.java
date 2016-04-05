package com.gcme.deeplife.Schedule;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.MainActivity;
import com.gcme.deeplife.Models.Schedule;
import com.gcme.deeplife.R;

public class ReminderService extends WakeReminderIntentService {

	public ReminderService() {
		super("ReminderService");
			}

	@Override
	void doReminderWork(Intent intent) {
		Log.d("ReminderService", "Doing work.");
		Long rowId = intent.getExtras().getLong(DeepLife.SCHEDULES_COLUMN[0]);
		 Log.i(DeepLife.TAG, "Remainder id " + rowId);
		NotificationManager mgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
						
		Intent notificationIntent = new Intent(this, MainActivity.class);
		notificationIntent.putExtra(DeepLife.SCHEDULES_COLUMN[0], rowId);
		
		PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

		Schedule schedule = com.gcme.deeplife.DeepLife.myDatabase.getScheduleWithId(rowId + "");

		Notification.Builder builder = new Notification.Builder(getApplicationContext())
				.setContentTitle(schedule.getTitle())
				.setContentIntent(pi)
				.setContentText(schedule.getDescription())
				.setAutoCancel(true)
				.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
				.setWhen(System.currentTimeMillis());

		try{
			Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.ringtone);
			builder.setSound(uri);
		}catch (Exception e){

		}
		builder.setSmallIcon(R.drawable.logoicon_ldpi);

		Notification note = builder.build();

		int id = (int)((long)rowId);
		mgr.notify(id, note);

/*
		Intent intent2 = new Intent(this, Music_Play.class);
		intent2.putExtra(DeepLife.SCHEDULES_COLUMN[0],rowId);

		startActivity(intent2);*/
	}
}
