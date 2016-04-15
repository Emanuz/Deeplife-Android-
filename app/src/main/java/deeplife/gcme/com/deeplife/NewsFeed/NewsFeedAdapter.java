package deeplife.gcme.com.deeplife.NewsFeed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import deeplife.gcme.com.deeplife.Models.NewsFeed;
import deeplife.gcme.com.deeplife.R;

/**
 * Created by BENGEOS-PC on 4/15/2016.
 */
public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.DataObjectHolder>  {

    private static String LOG_TAG = "NewsFeedAdapter";
    private ArrayList<NewsFeed> NewsFeeds;
    private static MyClickListener myClickListener;
    private static Context myContext;

    public NewsFeedAdapter(Context context,ArrayList<NewsFeed> newsFeeds) {
        myContext = context;
        NewsFeeds = newsFeeds;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView Title,Content;
        public DataObjectHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.news_feed_title);
            Content = (TextView) itemView.findViewById(R.id.news_feed_content);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
          return true;
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_feed_item, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
      //  holder.Title.setText((NewsFeeds.get(position).getTitle()));
      //  holder.Content.setText(NewsFeeds.get(position).getContent());
    }
    @Override
    public int getItemCount() {
        return NewsFeeds.size();
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
