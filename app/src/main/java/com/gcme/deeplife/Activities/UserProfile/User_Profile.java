package com.gcme.deeplife.Activities.UserProfile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gcme.deeplife.Database.Database;
import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.ImageProcessing.ImageProcessing;
import com.gcme.deeplife.R;

import java.util.ArrayList;

public class User_Profile extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView profile_image;
    ListView lv_schedule;
    TextView tv_build, tv_name, tv_phone, tv_gender,tv_email;
    ImageButton imageButton;
    ImageView profile_pic;
    Button btn_complet;
    String user_id;
    ArrayList<String> schedule_list;

    private Bitmap theBitmap = null;

    //Database
    Database dbadapter;
    DeepLife dbhelper;


    private String mCurrentPhotoPath;
    private String newCurrentPhotoPath;
    public final static int CHANGE_PIC = 1;
    private String TAG = "Deep Life";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_page);
        setSupportActionBar((Toolbar) findViewById(R.id.user_profile_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.user_profile_collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Roger Mulugeta");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        profile_image = (ImageView) findViewById(R.id.user_profile_image);

        profile_image.setImageBitmap(BitmapFactory.decodeFile(ImageProcessing.DEFAULT_PATH));

//        profile_image.setImageBitmap(BitmapFactory.decodeFile(ImageProcessing.DEFAULT_PATH));


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.menu_user_profile_edit){
            Intent intent = new Intent(User_Profile.this,User_Profile_Edit.class);
            startActivity(intent);
            return true;
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }


}
