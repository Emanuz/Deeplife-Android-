package com.gcme.deeplife.Schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.gcme.deeplife.Models.Schedule;
import com.gcme.deeplife.R;

import java.util.ArrayList;

/**
 * Created by BENGEOS on 3/25/16.
 */
public class ScheduleListFragment extends Fragment {
    private static RecyclerView myRecyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static Context myContext;
    FloatingActionButton add_schedule;

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "kk:mm";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_list,container,false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.schedule_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(mLayoutManager);
        add_schedule = (FloatingActionButton) view.findViewById(R.id.btn_schedule_add);
        myContext = getActivity();
        ArrayList<Schedule> items = com.gcme.deeplife.DeepLife.myDatabase.get_All_Schedule();

        mAdapter = new ScheduleListAdapter(getActivity(),items);
        myRecyclerView.setAdapter(mAdapter);
        myRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        add_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddSchedule.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
