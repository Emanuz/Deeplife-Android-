package com.gcme.deeplife.Disciples;

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
import android.widget.Toast;

import com.gcme.deeplife.Activities.AddDisciple;
import com.gcme.deeplife.DeepLife;
import com.gcme.deeplife.Models.Disciples;
import com.gcme.deeplife.R;

import java.util.ArrayList;

/**
 * Created by BENGEOS on 3/25/16.
 */
public class DiscipleListFragment extends Fragment {
    private static RecyclerView myRecyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static Context myContext;

    FloatingActionButton add_disciple;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.disciple_list_page,container,false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(mLayoutManager);
        add_disciple = (FloatingActionButton) view.findViewById(R.id.disciple_list_add_disciple);
        myContext = getActivity();
        ArrayList<Disciples> items = DeepLife.myDatabase.getDisciples();

        mAdapter = new DiscipleListAdapter(getActivity(),items);
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


        add_disciple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DeepLife.myDatabase.count(com.gcme.deeplife.Database.DeepLife.Table_DISCIPLES) < 1) {
                    Toast.makeText(myContext, "No disciple Found. \n Please add your disciples first.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Intent intent = new Intent(getActivity(), AddDisciple.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

}
