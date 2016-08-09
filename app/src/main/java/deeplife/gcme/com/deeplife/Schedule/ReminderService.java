package deeplife.gcme.com.deeplife.Schedule;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import deeplife.gcme.com.deeplife.Database.Database;
import deeplife.gcme.com.deeplife.MainActivity;
import deeplife.gcme.com.deeplife.Models.Schedule;
import deeplife.gcme.com.deeplife.R;

public class ReminderService extends WakeReminderIntentService {

	public ReminderService() {
		super("ReminderService");
			}

	@Override
	void doReminderWork(Intent intent) {
		Log.d("ReminderService", "Doing work.");
		Long rowId = intent.getExtras().getLong(Database.SCHEDULES_COLUMN[0]);
		 Log.i(Database.TAG, "Remainder id " + rowId);
		NotificationManager mgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
						
		Intent notificationIntent = new Intent(this, MainActivity.class);
		notificationIntent.putExtra(Database.SCHEDULES_COLUMN[0], rowId);
		
		PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

		Schedule schedule = deeplife.gcme.com.deeplife.DeepLife.myDatabase.getScheduleWithId(rowId + "");

		if(schedule != null){
			Notification.Builder builder = new Notification.Builder(getApplicationContext())
					.setContentTitle(schedule.getTitle())
					.setContentTitle("This is titile")
					.setContentIntent(pi)
					.setContentText(schedule.getDescription())
					.setContentText("this is the contet")
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
		}


	}
}
