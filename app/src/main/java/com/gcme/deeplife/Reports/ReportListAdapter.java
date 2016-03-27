package com.gcme.deeplife.Reports;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcme.deeplife.Disciples.DiscipleObject;
import com.gcme.deeplife.R;

import java.util.ArrayList;

/**
 * Created by BENGEOS on 3/27/16.
 */
public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<ReportObject> ReportLists;
    private static MyClickListener myClickListener;
    private static Context myContext;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView FullName,Phone;
        TextView Email;
        ImageView Image;

        public DataObjectHolder(View itemView) {
            super(itemView);
            FullName = (TextView) itemView.findViewById(R.id.txt_question);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // myClickListener.onItemClick(getAdapterPosition(), v);
            // Intent intent = new Intent(myContext,NewsFeed_Detail.class);
            // myContext.startActivity(intent);
        }

    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
    public ReportListAdapter(Context context, ArrayList<ReportObject> discipleLists){
        this.ReportLists = discipleLists;
        this.myContext = context;

    }
    @Override
    public int getItemCount() {
        return ReportLists.size();
    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_list_item,parent,false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }
    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
//        holder.FullName.setText(DiscipleLists.get(position).getFullName());
//        holder.Phone.setText(DiscipleLists.get(position).getPhone());
//        holder.Email.setText(DiscipleLists.get(position).getEmail());

    }
    public void addItem(ReportObject news){
        ReportLists.add(news);
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
