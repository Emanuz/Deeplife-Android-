package com.gcme.deeplife;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcme.deeplife.Activities.AboutDeepLife;
import com.gcme.deeplife.Activities.Splash;
import com.gcme.deeplife.Activities.Under_Construction;
import com.gcme.deeplife.Activities.UserProfile.User_Profile;
import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.Disciples.DiscipleListFragment;
import com.gcme.deeplife.Models.User;
import com.gcme.deeplife.Reports.ReportListFragment;
import com.gcme.deeplife.Schedule.ScheduleListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    CollapsingToolbarLayout collapsingToolbarLayout;
    static ImageView image, btn_navigation_back;
    TextView navigation_name;
    LinearLayout nav_header;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        AlarmReciever alarmReciever = new AlarmReciever();
        alarmReciever.setAlarm(this);*/
        image = (ImageView) findViewById(R.id.image);
        btn_navigation_back = (ImageView) findViewById(R.id.image);
        image.setImageResource(R.drawable.splash);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int user_id = com.gcme.deeplife.DeepLife.myDatabase.get_Top_ID(DeepLife.Table_USER);
        user = com.gcme.deeplife.DeepLife.myDatabase.getUserProfile(user_id+"");
        Log.i(DeepLife.TAG, "User  = " + user_id);
        Log.i(DeepLife.TAG, "User name = " + user.getUser_Name());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        navigation_name = (TextView)header.findViewById(R.id.drawer_textView_name);
        navigation_name.setText(user.getUser_Name());
        Log.i(DeepLife.TAG, "Nav name = " + user.getUser_Name());

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("DEEP LIFE");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        nav_header = (LinearLayout) findViewById(R.id.nav_header_layout);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DiscipleListFragment(), "Disciple List");
        adapter.addFragment(new ScheduleListFragment(), "Schedules");
        adapter.addFragment(new ReportListFragment(), "Report");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.menu_about){
            Intent intent = new Intent(this, AboutDeepLife.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.nav_disciples) {
            viewPager.setCurrentItem(0, true);
        } else if (id == R.id.nav_schedules) {
            viewPager.setCurrentItem(1, true);
        } else if (id == R.id.nav_report) {
            viewPager.setCurrentItem(3, true);
        } else if (id == R.id.nav_learning) {
            intent = new Intent(this, Under_Construction.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            intent = new Intent(this, User_Profile.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            com.gcme.deeplife.DeepLife.myDatabase.Delete_All(DeepLife.Table_USER);
            intent = new Intent(this, Splash.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.nav_send) {
            Uri uri = Uri.parse("http://www.facebook.com"); // missing 'http://' will cause crashed
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        else if (id == R.id.nav_about) {
            intent = new Intent(this, AboutDeepLife.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
