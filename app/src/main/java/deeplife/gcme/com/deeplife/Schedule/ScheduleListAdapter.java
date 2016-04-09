
package deeplife.gcme.com.deeplife.Schedule;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import deeplife.gcme.com.deeplife.Database.DeepLife;
import deeplife.gcme.com.deeplife.MainActivity;
import deeplife.gcme.com.deeplife.Models.Disciples;
import deeplife.gcme.com.deeplife.Models.Schedule;
import deeplife.gcme.com.deeplife.R;
import deeplife.gcme.com.deeplife.SyncService.SyncService;

import java.util.ArrayList;

/**
 * Created by Roger on 3/25/16.
 */
public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static ArrayList<Schedule> ScheduleList;
    private static MyClickListener myClickListener;
    private static Context myContext;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tv_name,tv_disc, tv_time, tv_title, tv_id;

        public DataObjectHolder(View itemView) {
            super(itemView);

            tv_name = (TextView)itemView.findViewById(R.id.schedule_with_name);
            tv_disc = (TextView)itemView.findViewById(R.id.scheduledisciption);
            tv_time = (TextView)itemView.findViewById(R.id.scheduletime);
            tv_title = (TextView)itemView.findViewById(R.id.schedule_title);
            tv_id = (TextView)itemView.findViewById(R.id.schedule_hidden_id);


            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(myContext,"Watch out! You have a schedule.",Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View v) {
            //Toast.makeText(myContext,"Disciple ID: "+ScheduleList.get(getPosition()).getDisciple_Phone(), Toast.LENGTH_LONG).show();
            try{
                ScheduleListAdapter.delete_Dialog(Integer.parseInt(tv_id.getText().toString()));
            }catch (Exception e){

            }
            return true;
        }
    }

    public static void delete_Dialog(final int id) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        long deleted = deeplife.gcme.com.deeplife.DeepLife.myDatabase.remove(DeepLife.Table_SCHEDULES,id);
                        if(deleted!=-1){
                            Toast.makeText(myContext,"Successfully Deleted",Toast.LENGTH_SHORT).show();
                            ContentValues log = new ContentValues();
                            log.put(deeplife.gcme.com.deeplife.Database.DeepLife.LOGS_FIELDS[0],"Schedule");
                            log.put(deeplife.gcme.com.deeplife.Database.DeepLife.LOGS_FIELDS[1], SyncService.Sync_Tasks[4]);
                            log.put(deeplife.gcme.com.deeplife.Database.DeepLife.LOGS_FIELDS[2], id);
                            deeplife.gcme.com.deeplife.DeepLife.myDatabase.insert(deeplife.gcme.com.deeplife.Database.DeepLife.Table_LOGS, log);

                            Intent intent = new Intent(myContext,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            myContext.startActivity(intent);
                            ((Activity) myContext).finish();                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setTitle("Remove Schedule ").setMessage("Are You sure you want to remove this schedule" )
                .setPositiveButton("Yes ", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
    }



    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
    public ScheduleListAdapter(Context context, ArrayList<Schedule> scheduleList){
        this.ScheduleList = scheduleList;
        this.myContext = context;

    }

    @Override
    public int getItemCount() {
        return ScheduleList.size();
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }
    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
        String Dis_id = ScheduleList.get(position).getID();
        String user_phone = ScheduleList.get(position).getDisciple_Phone();
        String time = ScheduleList.get(position).getAlarm_Time();
        String title = ScheduleList.get(position).getTitle();
        String discription = ScheduleList.get(position).getDescription();
        int id = Integer.parseInt(ScheduleList.get(position).getID());
        Disciples disciple = deeplife.gcme.com.deeplife.DeepLife.myDatabase.getDiscipleProfileFromPhone(user_phone);
        if(disciple != null){
            //set the values
            holder.tv_name.setText(disciple.getFull_Name());
            holder.tv_time.setText(time);
            holder.tv_disc.setText(discription);
            holder.tv_title.setText(title);
        }
    }

    public void addItem(Schedule news){
        ScheduleList.add(news);
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
