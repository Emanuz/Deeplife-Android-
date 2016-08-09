package deeplife.gcme.com.deeplife.Schedule;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import deeplife.gcme.com.deeplife.Database.Database;
import deeplife.gcme.com.deeplife.DeepLife;
import deeplife.gcme.com.deeplife.Models.Schedule;
import deeplife.gcme.com.deeplife.R;

/**
 * Created by Roger on 4/3/2016.
 */

public class Music_Play extends Activity {

    private Button btn_cancel, btn_snooze;
    private TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_play);
        this.setFinishOnTouchOutside(false);

        final long rowId = this.getIntent().getExtras().getLong(Database.SCHEDULES_COLUMN[0]);

        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_snooze = (Button) findViewById(R.id.btn_snooze);
        tv_title = (TextView) findViewById(R.id.tv_music_play);

        Schedule schedule = deeplife.gcme.com.deeplife.DeepLife.myDatabase.getScheduleWithId(rowId+"");
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
                Calendar cal = Calendar.getInstance();
                DeepLife.myReminderManager.setReminder((int)rowId,cal);
                finish();
            }
        });

    }
}
