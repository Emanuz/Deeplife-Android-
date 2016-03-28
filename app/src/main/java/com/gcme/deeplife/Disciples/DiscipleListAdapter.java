package com.gcme.deeplife.Disciples;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcme.deeplife.Activities.Disciple_Profile;
import com.gcme.deeplife.R;

import java.util.ArrayList;

/**
 * Created by BENGEOS on 3/25/16.
 */
public class DiscipleListAdapter extends RecyclerView.Adapter<DiscipleListAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DiscipleObject> DiscipleLists;
    private static MyClickListener myClickListener;
    private static Context myContext;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView FullName,Phone;
        TextView Email;
        ImageView Image;

        public DataObjectHolder(View itemView) {
            super(itemView);
            FullName = (TextView) itemView.findViewById(R.id.txt_disciple_name);
            Phone = (TextView) itemView.findViewById(R.id.txt_disciple_name);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(myContext,Disciple_Profile.class);
            myContext.startActivity(intent);
        }

    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
    public DiscipleListAdapter(Context context, ArrayList<DiscipleObject> discipleLists){
        this.DiscipleLists = discipleLists;
        this.myContext = context;

    }

    @Override
    public int getItemCount() {
        return DiscipleLists.size();
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.disciple_list_item,parent,false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }
    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
//        holder.FullName.setText(DiscipleLists.get(position).getFullName());
//        holder.Phone.setText(DiscipleLists.get(position).getPhone());
//        holder.Email.setText(DiscipleLists.get(position).getEmail());

    }
    public void addItem(DiscipleObject news){
        DiscipleLists.add(news);
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
