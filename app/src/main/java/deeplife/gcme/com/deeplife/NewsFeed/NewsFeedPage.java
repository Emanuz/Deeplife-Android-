package deeplife.gcme.com.deeplife.NewsFeed;

import android.content.Context;
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
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.jar.Manifest;

import deeplife.gcme.com.deeplife.DeepLife;
import deeplife.gcme.com.deeplife.Disciples.DiscipleListAdapter;
import deeplife.gcme.com.deeplife.Models.Disciples;
import deeplife.gcme.com.deeplife.Models.NewsFeed;
import deeplife.gcme.com.deeplife.R;

/**
 * Created by BENGEOS-PC on 4/15/2016.
 */
public class NewsFeedPage extends Fragment {

    private static RecyclerView myRecyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static Context myContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_feed_page,container,false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(mLayoutManager);
        myContext = getActivity();
        ArrayList<NewsFeed> items = DeepLife.myDatabase.getAllNewsFeeds();
        mAdapter = new NewsFeedAdapter(getActivity(),items);
        myRecyclerView.setAdapter(mAdapter);
        return view;
    }
}
