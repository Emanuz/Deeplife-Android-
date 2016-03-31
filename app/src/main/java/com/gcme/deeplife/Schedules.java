package com.gcme.deeplife;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.gcme.deeplife.Models.Disciples;

import java.util.ArrayList;

public class Schedules extends Fragment {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Button newsBut;
    private ListView lv_disciple;

    public Schedules() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_schedules, container, false);
        return view;

    }


    public class MyDiscipleListAdapter extends BaseAdapter
    {
        Context context;
        ArrayList<Disciples> disciples;
        public MyDiscipleListAdapter(Context context,ArrayList<Disciples> disciple)
        {
            this.context = context;
            disciples = disciple;
        }


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return disciples.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return convertView;
        }
    }



}
