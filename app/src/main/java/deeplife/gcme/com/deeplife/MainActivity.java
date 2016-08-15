package deeplife.gcme.com.deeplife;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import deeplife.gcme.com.deeplife.Activities.AboutDeepLife;
import deeplife.gcme.com.deeplife.Activities.AddTestimony;
import deeplife.gcme.com.deeplife.Activities.Splash;
import deeplife.gcme.com.deeplife.Activities.Under_Construction;
import deeplife.gcme.com.deeplife.Activities.UserProfile.User_Profile;
import deeplife.gcme.com.deeplife.Database.Database;
import deeplife.gcme.com.deeplife.Disciples.DiscipleListFragment;
import deeplife.gcme.com.deeplife.Models.Schedule;
import deeplife.gcme.com.deeplife.Models.User;
import deeplife.gcme.com.deeplife.NewsFeed.NewsFeedPage;
import deeplife.gcme.com.deeplife.Reports.ReportFragment;
import deeplife.gcme.com.deeplife.Reports.ReportListFragment;
import deeplife.gcme.com.deeplife.Schedule.ScheduleListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static final int REQUEST_PHONE_STATE = 11;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 12;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 13;
    private static final int REQUEST_INTERNET= 14;
    private static final int REQUEST_CALL_PHONE= 15;

    CollapsingToolbarLayout collapsingToolbarLayout;
    static ImageView image, btn_navigation_back;
    TextView navigation_name;
    ImageView nav_image;
    LinearLayout nav_header;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"Check if Permission granted",Toast.LENGTH_LONG).show();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
        }
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
//        }
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
//        }
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_INTERNET);
//        }
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
//        }

        image = (ImageView) findViewById(R.id.image);
        btn_navigation_back = (ImageView) findViewById(R.id.image);
        image.setImageResource(R.drawable.splash);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = deeplife.gcme.com.deeplife.DeepLife.myDatabase.getUserProfile();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        navigation_name = (TextView)header.findViewById(R.id.drawer_textView_name);
        nav_image = (ImageView)header.findViewById(R.id.drawer_main_imageView);


        navigation_name.setText(user.getUser_Name());
        if(user.getUser_Picture()!=null | user.getUser_Picture()!=""){
            nav_image.setImageBitmap(BitmapFactory.decodeFile(user.getUser_Picture()));
        }
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
        viewPager.setCurrentItem(DeepLife.Slide_Pos,true);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Log.i("Test", "Tab: " + bundle.getInt("tab"));
            setTab(bundle.getInt("tab"));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_PHONE_STATE:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Intent intent  = new Intent(MainActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
            case REQUEST_READ_EXTERNAL_STORAGE:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Intent intent  = new Intent(MainActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }
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
        adapter.addFragment(new NewsFeedPage(), "News Feed");
        adapter.addFragment(new DiscipleListFragment(), "Disciple List");
        adapter.addFragment(new ScheduleListFragment(), "Schedules");
        adapter.addFragment(new ReportListFragment(), "Share");
        viewPager.setAdapter(adapter);
    }

    public void setTab(int tab){
        this.viewPager.setCurrentItem(tab,true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
        private final List<String> mFragmentTitleList = new ArrayList<String>();

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
