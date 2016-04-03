package com.gcme.deeplife.Schedule;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gcme.deeplife.R;

/**
 * Created by Roger on 4/3/2016.
 */

public class Music_Play extends Activity {

    Button btn_stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_play);
        btn_stop = (Button) findViewById(R.id.music_play);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.jadhiel);
        mediaPlayer.start();

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
            }
        });

    }
}
