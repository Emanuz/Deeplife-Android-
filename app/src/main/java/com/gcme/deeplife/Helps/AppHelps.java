package com.gcme.deeplife.Helps;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.gcme.deeplife.R;

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
        List<HelpDataType> Helps = new ArrayList<HelpDataType>();
        Helps.add(new HelpDataType(R.drawable.help_1,""));
        Helps.add(new HelpDataType(R.drawable.help_2,""));
        Helps.add(new HelpDataType(R.drawable.help_3,""));

        viewPager.setAdapter(new HelpSlideAdapter(getSupportFragmentManager(),Helps));
    }
}
