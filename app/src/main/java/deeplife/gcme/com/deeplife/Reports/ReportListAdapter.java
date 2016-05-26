package deeplife.gcme.com.deeplife.Reports;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import deeplife.gcme.com.deeplife.Database.Database;
import deeplife.gcme.com.deeplife.MainActivity;
import deeplife.gcme.com.deeplife.Models.ReportItem;
import deeplife.gcme.com.deeplife.Models.Schedule;
import deeplife.gcme.com.deeplife.R;
import deeplife.gcme.com.deeplife.SyncService.SyncService;

import java.util.ArrayList;

/**
 * Created by BENGEOS on 3/27/16.
 */
public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    public static ArrayList<ReportItem> ReportLists;
    private static MyClickListener myClickListener;
    private static Context myContext;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Question;
        EditText Value;
        Button btnUp,btnDown;

        public DataObjectHolder(View itemView) {
            super(itemView);
            Question = (TextView) itemView.findViewById(R.id.txt_question);
            Value = (EditText) itemView.findViewById(R.id.txt_value);
            Value.setText("0");
            btnUp = (Button) itemView.findViewById(R.id.btn_inc);
            btnUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int val = Integer.valueOf(Value.getText().toString());
                    val = val+1;
                    ReportLists.get(getAdapterPosition()).setValue(""+val);
                    Value.setText("" + val);
                }
            });
            btnDown = (Button) itemView.findViewById(R.id.btn_dec);
            btnDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int val = Integer.valueOf(Value.getText().toString());
                    if(val>0){
                        val = val-1;
                        Value.setText(""+val);
                        ReportLists.get(getAdapterPosition()).setValue(""+val);
                    }
                    Value.setText(""+val);
                }
            });
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
    public ReportListAdapter(Context context, ArrayList<ReportItem> discipleLists){
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
         holder.Question.setText(ReportLists.get(position).getQuestion());
//        holder.Phone.setText(DiscipleLists.get(position).getPhone());
//        holder.Email.setText(DiscipleLists.get(position).getEmail());

    }
    public void addItem(ReportItem news){
        ReportLists.add(news);
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
