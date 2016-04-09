package deeplife.gcme.com.deeplife.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import deeplife.gcme.com.deeplife.R;

/**
 * Created by Rog on 2/21/16.
 */

public class AboutDeepLife extends Activity {
    private Button Finish;
    private TextView Email_Link,FaceBook_Link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_deeplife);
        Finish = (Button) findViewById(R.id.btn_ok);
        Email_Link = (TextView) findViewById(R.id.txt_email);
        FaceBook_Link = (TextView) findViewById(R.id.txt_facebook);
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Email_Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to = "info@cccsea.org";
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
        FaceBook_Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/seadeeplife"));
                startActivity(browserIntent);
            }
        });
    }
}
