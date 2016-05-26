package deeplife.gcme.com.deeplife.Schedule;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import deeplife.gcme.com.deeplife.Database.Database;
import deeplife.gcme.com.deeplife.Models.Schedule;
import deeplife.gcme.com.deeplife.R;

/**
 * Created by Roger on 4/3/2016.
 */

public class Music_Play extends Activity {

    Button btn_stop;
    TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_play);
        this.setFinishOnTouchOutside(false);

        Long rowId = this.getIntent().getExtras().getLong(Database.SCHEDULES_COLUMN[0]);

        btn_stop = (Button) findViewById(R.id.music_play);
        tv_title = (TextView) findViewById(R.id.tv_music_play);

        Schedule schedule = deeplife.gcme.com.deeplife.DeepLife.myDatabase.getScheduleWithId(rowId+"");
        tv_title.setText(schedule.getTitle());

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.ringtone);
        mediaPlayer.start();

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                finish();
            }
        });

    }
}
