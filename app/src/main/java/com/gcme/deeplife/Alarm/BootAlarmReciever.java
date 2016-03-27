package com.gcme.deeplife.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by BENGEOS on 3/27/16.
 */
public class BootAlarmReciever extends BroadcastReceiver {
    AlarmReciever alarm = new AlarmReciever();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            alarm.setAlarm(context);
        }
    }
}
