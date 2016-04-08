package com.gcme.deeplife.Helps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by BENGEOS on 2/19/16.
 */
public class HelpSlideAdapter extends FragmentStatePagerAdapter {
    private List<HelpDataType> Helps;
    private int Size;
    public HelpSlideAdapter(FragmentManager fm,List<HelpDataType> helps) {
        super(fm);
        Helps = helps;
        Size = helps.size();
    }
    @Override
    public Fragment getItem(int position) {
        Bundle b = new Bundle();
        b.putString("Title", Helps.get(position).getTitle());
        if(position == 0){
            b.putInt("isStart", 1);
        }else{
            b.putInt("isStart", 0);
        }
        if(position == Size-1){
            b.putInt("isEnd", 1);
        }else{
            b.putInt("isEnd", 0);
        }
        b.putInt("Image", Helps.get(position).getImageID());

        Fragment Content = new HelpContent();
        Content.setArguments(b);
        return Content;
    }

    @Override
    public int getCount() {

        return Size;
    }

   /* @Override
    public CharSequence getPageTitle(int position) {

       // return Helps.get(position).getTitle();
        return "";
    }*/
}
