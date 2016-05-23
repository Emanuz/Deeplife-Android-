package deeplife.gcme.com.deeplife.NewsFeed;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

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
    private ImageView NewsImage;
    private TextView Title,Content,PubDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.news_feed_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.news_feed_detail_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String image_path = getIntent().getExtras().getString("news_image_path");
        NewsImage = (ImageView) findViewById(R.id.news_feed_detail_image);
        Title = (TextView) findViewById(R.id.news_feed_detail_title);
        Content = (TextView) findViewById(R.id.news_feed_detail_content);
        PubDate = (TextView) findViewById(R.id.news_feed_detail_date);
        //collapsing toolbar
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.news_feed_detail_collapsing_toolbar);
        news_id = getIntent().getExtras().getString("news_id");
        newsFeed = DeepLife.myDatabase.get_NewsFeed_by_id(news_id);
        collapsingToolbarLayout.setTitle("Deep Life NewsFeeds");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        //Toast.makeText(this, "Image Path:\n" + news_id, Toast.LENGTH_LONG).show();
        Glide.with(this).load(image_path).into(NewsImage);
        if(newsFeed != null){
            Title.setText(""+newsFeed.getTitle());
            Content.setText(""+newsFeed.getContent());
            PubDate.setText(""+newsFeed.getPubDate());
        }

    }
}
