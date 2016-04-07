package com.gcme.deeplife.Activities.Win;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gcme.deeplife.Activities.WinViewPager;
import com.gcme.deeplife.Activities.Win_Thank_You;
import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.Models.Question;
import com.gcme.deeplife.Models.QuestionAnswer;
import com.gcme.deeplife.R;

import java.util.ArrayList;

/**
 * Created by rog on 11/7/2015.
 */
public class WinActivity extends AppCompatActivity {

    public static WinViewPager mPager;
    private PagerAdapter mPagerAdapter;
    public static final String WIN = "WIN";
    public int NUM_PAGES;
    public static boolean answered_state;
    public static ArrayList<Integer> answer_from_db_id;
    public static ArrayList<Question> questions;
    public static ArrayList<String> answers;
    public static ArrayList<String> answerchoices;
    public static ArrayList<QuestionAnswer> answered_from_db = null;
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
        getSupportActionBar().setTitle("Win");

        mPager = (WinViewPager) findViewById(R.id.win_viewpager);
        mPager.setSwipeable(true);

        Bundle extras = this.getIntent().getExtras();
        answered_state = false;
        if(extras!=null){
            DISCIPLE_ID = Integer.parseInt(extras.getString("disciple_id").toString());
            if(extras.containsKey("answer")) {
                if (extras.getString("answer").toString() != null) {
                    answered_state = true;
                }
            }
        }
        else{
            return;
        }

        clear();
        //initialize data
        init();

        mPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager()));
    }

    private void clear() {
        answers = new ArrayList<String>();
        answered_from_db = new ArrayList<QuestionAnswer>();
        questions = new ArrayList<Question>();
        answers.clear();
        answered_from_db.clear();
        questions.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void init(){

        //set the max number of pages from db
        NUM_PAGES = (com.gcme.deeplife.DeepLife.myDatabase.count_Questions(DeepLife.Table_QUESTION_LIST,WIN));
        NUM_PAGES++;

        Log.i("Deep Life", "The Page number inside win activity is " + NUM_PAGES + "");
        questions = com.gcme.deeplife.DeepLife.myDatabase.get_All_Questions(WIN);
        answerchoices = new ArrayList<String>();
        answerchoices.add("Yes");
        answerchoices.add("No");
        answers = new ArrayList<String>();

        //if answer in database
        if(answered_state){
            answered_from_db = com.gcme.deeplife.DeepLife.myDatabase.get_Answer(DISCIPLE_ID+"",WIN);
            answer_from_db_id = new ArrayList<Integer>();

            for(int i=0; i<(com.gcme.deeplife.DeepLife.myDatabase.count_Questions(DeepLife.Table_QUESTION_LIST,WIN));i++){
                answers.add(answered_from_db.get(i).getAnswer());
                answer_from_db_id.add(Integer.parseInt(answered_from_db.get(i).getId()));
            }
        }

        //if answer not in database
        else {
            for (int i = 0; i < NUM_PAGES - 1; i++) {
                answers.add("");
            }
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
                b.putString("stage", "WIN");
                Fragment win = new Win_Thank_You();
                win.setArguments(b);
                return win;

           }

            return WinFragment.create(position);

            }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return WIN;
        }
    }

}
