package deeplife.gcme.com.deeplife.Helps;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import deeplife.gcme.com.deeplife.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by BENGEOS on 2/19/16.
 */
public class AppHelps extends FragmentActivity {

    public static HelpViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity);

        viewPager = (HelpViewPager) findViewById(R.id.help_viewpage);
        viewPager.setSwipeable(true);
        viewPager.setAdapter(new HelpSlideAdapter(getSupportFragmentManager()));
    }
}
