package deeplife.gcme.com.deeplife.NewsFeed;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import deeplife.gcme.com.deeplife.DeepLife;
import deeplife.gcme.com.deeplife.Models.NewsFeed;
import deeplife.gcme.com.deeplife.R;

/**
 * Created by BENGEOS on 4/16/16.
 */
public class NewsFeedDetail extends AppCompatActivity {
    private String news_id;
    private NewsFeed newsFeed;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.news_feed_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.news_feed_detail_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //collapsing toolbar
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.news_feed_detail_collapsing_toolbar);
        news_id = getIntent().getExtras().getString("news_id");
        newsFeed = DeepLife.myDatabase.get_NewsFeed_by_id(news_id);
        collapsingToolbarLayout.setTitle("Deep Life NewsFeeds");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }
}
