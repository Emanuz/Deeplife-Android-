package deeplife.gcme.com.deeplife.Schedule;

import android.app.Activity;
import android.content.ContentValues;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import deeplife.gcme.com.deeplife.Database.Database;
import deeplife.gcme.com.deeplife.DeepLife;
import deeplife.gcme.com.deeplife.Models.Schedule;
import deeplife.gcme.com.deeplife.R;
import deeplife.gcme.com.deeplife.SyncService.SyncService;

/**
 * Created by Roger on 4/3/2016.
 */

public class Music_Play extends Activity {

    private Button btn_cancel, btn_snooze;
    private TextView tv_title;
    private Spinner spn_Alarm_Type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_play);
        this.setFinishOnTouchOutside(false);

        final long rowId = this.getIntent().getExtras().getLong(Database.SCHEDULES_COLUMN[0]);

        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_snooze = (Button) findViewById(R.id.btn_snooze);
        tv_title = (TextView) findViewById(R.id.tv_music_play);
        spn_Alarm_Type = (Spinner) findViewById(R.id.spn_alarm_type);

        final Schedule schedule = deeplife.gcme.com.deeplife.DeepLife.myDatabase.getScheduleWithId(rowId+"");
        tv_title.setText(schedule.getTitle());

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.ringtone);
        mediaPlayer.start();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                DeepLife.myReminderManager.CancelAlarm((int)rowId);
                finish();
            }
        });
        btn_snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                DateFormat df = new SimpleDateFormat(DeepLife.DATE_TIME_FORMAT);
                Calendar cal = Calendar.getInstance();
                try {
                    cal.setTime(df.parse(schedule.getAlarm_Time()));
                    if(spn_Alarm_Type.getSelectedItem().toString().equals("Every Hour")){
                        cal.add(Calendar.HOUR,1);
                        DeepLife.myReminderManager.setReminder((int)rowId,cal);
                    }else if(spn_Alarm_Type.getSelectedItem().toString().equals("Every Day")){
                        cal.add(Calendar.DAY_OF_MONTH,1);
                        DeepLife.myReminderManager.setReminder((int)rowId,cal);
                    }else if(spn_Alarm_Type.getSelectedItem().toString().equals("Every Week")){
                        cal.add(Calendar.DAY_OF_MONTH,7);
                        DeepLife.myReminderManager.setReminder((int)rowId,cal);
                    }
                    ContentValues cv = new ContentValues();
                    cv.put(Database.SCHEDULES_FIELDS[2],df.format(cal.getTime()));
                    cv.put(Database.SCHEDULES_FIELDS[3],spn_Alarm_Type.getSelectedItem().toString());
                    long x = DeepLife.myDatabase.update(Database.Table_SCHEDULES,cv,(int)rowId);
                    if(x != -1){
                        ContentValues log = new ContentValues();
                        log.put(deeplife.gcme.com.deeplife.Database.Database.LOGS_FIELDS[0], "Schedule");
                        log.put(deeplife.gcme.com.deeplife.Database.Database.LOGS_FIELDS[1], SyncService.Sync_Tasks[7]);
                        log.put(deeplife.gcme.com.deeplife.Database.Database.LOGS_FIELDS[2], rowId);
                        DeepLife.myDatabase.insert(Database.Table_LOGS,log);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                finish();
            }
        });

    }
}
