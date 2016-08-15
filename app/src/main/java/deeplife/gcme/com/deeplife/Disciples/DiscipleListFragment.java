package deeplife.gcme.com.deeplife.Disciples;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import deeplife.gcme.com.deeplife.Activities.AddDisciple;
import deeplife.gcme.com.deeplife.DeepLife;
import deeplife.gcme.com.deeplife.Models.Disciples;
import deeplife.gcme.com.deeplife.R;

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
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 11);
        }
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
                Intent intent = new Intent(getActivity(), AddDisciple.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
