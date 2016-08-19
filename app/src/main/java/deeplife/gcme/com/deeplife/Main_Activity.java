package deeplife.gcme.com.deeplife;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import deeplife.gcme.com.deeplife.Activities.AboutDeepLife;
import deeplife.gcme.com.deeplife.Activities.AddTestimony;
import deeplife.gcme.com.deeplife.Activities.Splash;
import deeplife.gcme.com.deeplife.Activities.Under_Construction;
import deeplife.gcme.com.deeplife.Activities.UserProfile.User_Profile;
import deeplife.gcme.com.deeplife.Adapters.ViewPager_Adapter;
import deeplife.gcme.com.deeplife.Database.Database;
import deeplife.gcme.com.deeplife.Disciples.DiscipleListFragment;
import deeplife.gcme.com.deeplife.Models.Schedule;
import deeplife.gcme.com.deeplife.Models.User;
import deeplife.gcme.com.deeplife.NewsFeed.NewsFeedPage;
import deeplife.gcme.com.deeplife.Schedule.ScheduleListFragment;

/**
 * Created by bengeos on 8/18/16.
 */

public class Main_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final int REQUEST_PHONE_STATE = 11;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 12;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 13;
    private static final int REQUEST_INTERNET= 14;
    private static final int REQUEST_CALL_PHONE= 15;


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private static ImageView image,btn_navigation_back;
    private TextView navigation_name;
    private ImageView nav_image;
    private LinearLayout nav_header;

    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
        }
        btn_navigation_back = (ImageView) findViewById(R.id.image);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        navigation_name = (TextView) header.findViewById(R.id.drawer_textView_name);
        nav_image = (ImageView) header.findViewById(R.id.drawer_main_imageView);

        user = DeepLife.myDatabase.getUserProfile();
        if(user != null && user.getUser_Name() != null && user.getUser_Picture() != null){
            navigation_name.setText(user.getUser_Name());
            nav_image.setImageBitmap(BitmapFactory.decodeFile(user.getUser_Picture()));
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPager_Adapter adapter = new ViewPager_Adapter(getSupportFragmentManager());
        adapter.AddFragment(new NewsFeedPage(),"News Feeds");
        adapter.AddFragment(new DiscipleListFragment(),"Disciples");
        adapter.AddFragment(new ScheduleListFragment(),"Schedules");
        adapter.AddFragment(new NewsFeedPage(),"Share Us");
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Deep Life");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        nav_header = (LinearLayout) findViewById(R.id.nav_header_layout);




    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DeepLife.Slide_Pos = id;
        Intent intent;
        if (id == R.id.nav_news) {
            viewPager.setCurrentItem(0, true);
        } else if (id == R.id.nav_disciples) {
            viewPager.setCurrentItem(1, true);
        } else if (id == R.id.nav_schedules) {
            viewPager.setCurrentItem(2, true);
        } else if (id == R.id.nav_report) {
            viewPager.setCurrentItem(3, true);
        } else if (id == R.id.nav_learning) {
            intent = new Intent(this, Under_Construction.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            intent = new Intent(this, User_Profile.class);
            startActivity(intent);
        } else if (id == R.id.nav_testimony) {
            intent = new Intent(this, AddTestimony.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            ArrayList<Schedule> sch = DeepLife.myDatabase.get_All_Schedule();
            for (Schedule _sch: sch) {
                DeepLife.myReminderManager.CancelAlarm(Integer.valueOf(_sch.getID()));
            }
            DeepLife.myDatabase.Delete_All(Database.Table_USER);
            DeepLife.myDatabase.Delete_All(Database.Table_LOGS);
            DeepLife.myDatabase.Delete_All(Database.Table_DISCIPLES);
            DeepLife.myDatabase.Delete_All(Database.Table_SCHEDULES);
            DeepLife.myDatabase.Delete_All(Database.Table_NEWSFEED);

            intent = new Intent(this, Splash.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_send) {
            Uri uri = Uri.parse("http://www.facebook.com"); // missing 'http://' will cause crashed
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            intent = new Intent(this, AboutDeepLife.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
