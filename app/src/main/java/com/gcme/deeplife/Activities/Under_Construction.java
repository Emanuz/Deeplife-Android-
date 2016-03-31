package com.gcme.deeplife.Activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gcme.deeplife.R;

/**
 * Created by Roger on 3/31/2016.
 */


public class Under_Construction extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.learning_tool);
        setSupportActionBar((Toolbar) findViewById(R.id.learning_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.learning_collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Learning Tools");

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

    }
}
