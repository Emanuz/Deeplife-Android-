package com.gcme.deeplife.WinBuildSend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gcme.deeplife.Activities.WinViewPager;
import com.gcme.deeplife.DeepLife;
import com.gcme.deeplife.Models.Disciples;
import com.gcme.deeplife.Models.Question;
import com.gcme.deeplife.R;

import java.util.ArrayList;

/**
 * Created by Roger on 4/7/2016.
 */
public class WinBuildSend extends AppCompatActivity {

    public static Disciples disciple = null;
    public static String disciple_stage;
    public static String temp_stage;
    public static WinViewPager mPager;
    public int NUM_PAGES;
    public static boolean answered_state;
    public static ArrayList<Question> questions;
    public static ArrayList<String> answers;
    public static ArrayList<String> answerchoices;
    public static int answer_index = 0;
    public static int DISCIPLE_ID;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winactivity);
        toolbar = (Toolbar) findViewById(R.id.winactivity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = this.getIntent().getExtras();
        if(extras!=null){
            DISCIPLE_ID = Integer.parseInt(extras.getString("disciple_id").toString());
        }
        else{
            return;
        }

        disciple = DeepLife.myDatabase.getDiscipleProfile(DISCIPLE_ID + "");
        disciple_stage = disciple.getBuild_Phase();
        mPager = (WinViewPager) findViewById(R.id.win_viewpager);
        mPager.setSwipeable(true);

        getSupportActionBar().setTitle(disciple.getBuild_Phase());

        switch (disciple_stage){
            case "Added":
                temp_stage = "WIN";
                break;
            case "WIN":
                temp_stage = "BUILD";
                break;
            case "BUILD":
                temp_stage = "SEND";
                break;
            default:
                return;
        }

        handleInit();
        mPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager()));

    }

    public void handleInit(){
        NUM_PAGES = (com.gcme.deeplife.DeepLife.myDatabase.count_Questions(com.gcme.deeplife.Database.DeepLife.Table_QUESTION_LIST,temp_stage));
        NUM_PAGES++;
        questions = com.gcme.deeplife.DeepLife.myDatabase.get_All_Questions(temp_stage);
        //answer choices
        answerchoices = new ArrayList<String>();
        answerchoices.add("Yes");
        answerchoices.add("No");

        answers = new ArrayList<String>();


        for (int i = 0; i<NUM_PAGES - 1; i++){
            answers.add("");
        }



        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            if(position==NUM_PAGES-1){
                Bundle b = new Bundle();
                b.putString("stage", temp_stage);
                Fragment win = new WinBuildSend_ThankYou();
                win.setArguments(b);
                return win;

            }

            return WinBuildSendFragment.create(position,temp_stage);

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return temp_stage;
        }
    }
}
