package com.gcme.deeplife.Activities.Win;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gcme.deeplife.Activities.WinViewPager;
import com.gcme.deeplife.Activities.Win_Thank_You;
import com.gcme.deeplife.Database.Database;
import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.Models.Question;
import com.gcme.deeplife.Models.QuestionAnswer;
import com.gcme.deeplife.R;

import java.util.ArrayList;

/**
 * Created by rog on 11/7/2015.
 */
public class WinActivity extends FragmentActivity {

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


    Database dbadapter;
    DeepLife dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winactivity);

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

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }
        });

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
        dbadapter.dispose();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void init(){
        //initialize database files
        dbadapter = new Database(this);
        dbhelper = new DeepLife();


        //set the max number of pages from db
        NUM_PAGES = (dbadapter.count_Questions(DeepLife.Table_QUESTION_LIST,WIN));
        NUM_PAGES++;

        Log.i("Deep Life", "The Page number inside win activity is " + NUM_PAGES + "");

        questions = dbadapter.get_All_Questions(WIN);

        answerchoices = new ArrayList<String>();
        answerchoices.add("Yes");
        answerchoices.add("No");

        answers = new ArrayList<String>();

        //if answer in database
        if(answered_state){
            answered_from_db = dbadapter.get_Answer(DISCIPLE_ID+"",WIN);
            answer_from_db_id = new ArrayList<Integer>();

            for(int i=0; i<NUM_PAGES-1;i++){
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


        /*
        getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

        menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);

        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
                        ? R.string.action_finish
                        : R.string.action_next);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
        */

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                //NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                return true;

            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                return true;

            case R.id.action_next:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                return true;
        }
        */

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

               // return new Win_Thank_You();
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
