package deeplife.gcme.com.deeplife.Disciples;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import deeplife.gcme.com.deeplife.Activities.Disciple_Profile;
import deeplife.gcme.com.deeplife.Database.Database;
import deeplife.gcme.com.deeplife.MainActivity;
import deeplife.gcme.com.deeplife.Models.Disciples;
import deeplife.gcme.com.deeplife.Models.Schedule;
import deeplife.gcme.com.deeplife.R;
import deeplife.gcme.com.deeplife.SyncService.SyncService;

import java.util.ArrayList;

/**
 * Created by BENGEOS on 3/25/16.
 */
public class DiscipleListAdapter extends RecyclerView.Adapter<deeplife.gcme.com.deeplife.Disciples.DiscipleListAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Disciples> DiscipleLists;
    private static MyClickListener myClickListener;
    private static Context myContext;
    int build_progress_percent; //for  assigning maximum percent for the progress bar
    boolean checked = false; //for checking whether the progress thread is called. It must be called only once.

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView FullName,Phone;
        TextView Email;
        TextView id;
        TextView build_percent;
        TextView build_phase;
        ImageView discipleImage;
        ImageView phoneIcon;
        ProgressBar progress;
        public DataObjectHolder(View itemView) {
            super(itemView);
            FullName = (TextView) itemView.findViewById(R.id.txt_disciple_name);
            Phone = (TextView) itemView.findViewById(R.id.txt_disciple_phone);
            build_percent = (TextView) itemView.findViewById(R.id.disciple_progress_percent);
            build_phase = (TextView) itemView.findViewById(R.id.disciple_build);
            id = (TextView) itemView.findViewById(R.id.disciple_hidden_id);
            discipleImage = (ImageView) itemView.findViewById(R.id.list_profile_pic);
            phoneIcon = (ImageView) itemView.findViewById(R.id.disciple_list_phone_icon);

            progress = (ProgressBar) itemView.findViewById(R.id.progressBar);

            phoneIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+Phone.getText()));
                    myContext.startActivity(intent);
                }
            });
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(myContext,Disciple_Profile.class);
            Bundle b = new Bundle();
            b.putString("id",id.getText().toString());
            intent.putExtras(b);
            myContext.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            DiscipleListAdapter.delete_Dialog(Integer.parseInt(id.getText().toString()), FullName.getText().toString(), Phone.getText().toString());
            return true;
        }
    }

    public static void delete_Dialog(final int id,final String name, final String phone) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Toast.makeText(myContext,"delete: "+id,Toast.LENGTH_LONG).show();
                        long deleted = deeplife.gcme.com.deeplife.DeepLife.myDatabase.remove(Database.Table_DISCIPLES,id);
                        if(deleted!=-1){
                            Log.i("SyncService", "Adding Delete Log -> \n");
                            ContentValues log = new ContentValues();
                            log.put(deeplife.gcme.com.deeplife.Database.Database.LOGS_FIELDS[0], SyncService.Sync_Tasks[2]);
                            log.put(deeplife.gcme.com.deeplife.Database.Database.LOGS_FIELDS[1], SyncService.Sync_Tasks[0]);
                            log.put(deeplife.gcme.com.deeplife.Database.Database.LOGS_FIELDS[2], phone);
                            long val = deeplife.gcme.com.deeplife.DeepLife.myDatabase.insert(deeplife.gcme.com.deeplife.Database.Database.Table_LOGS,log);

                            ArrayList<Schedule> schedules = deeplife.gcme.com.deeplife.DeepLife.myDatabase.get_Schedule_With_User(phone);

                            for(int i=0;i<schedules.size();i++){
                                int schedule_id = Integer.parseInt(schedules.get(i).getID());
                                long result = deeplife.gcme.com.deeplife.DeepLife.myDatabase.remove(Database.Table_SCHEDULES,schedule_id);
                                if(result!=-1)
                                        Log.i(Database.TAG,"Schedule with id " + result +" removed");
                            }
                            Toast.makeText(myContext,"Disciple Deleted all the schedules ",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(myContext,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            myContext.startActivity(intent);
                            ((Activity) myContext).finish();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setTitle("Delete Disciple ").setMessage("Do you want to delete you disciple "+name)
                .setPositiveButton("Delete ", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener)
                .show();
    }


    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
    public DiscipleListAdapter(Context context, ArrayList<Disciples> discipleLists){
        this.DiscipleLists = discipleLists;
        this.myContext = context;

    }

    @Override
    public int getItemCount() {
        return DiscipleLists.size();
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.disciple_list_item, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }



    @Override
    public void onBindViewHolder(deeplife.gcme.com.deeplife.Disciples.DiscipleListAdapter.DataObjectHolder holder, int position) {
        String disciple_phase = DiscipleLists.get(position).getBuild_Phase();
        Log.i(Database.TAG, disciple_phase);
        holder.FullName.setText((DiscipleLists.get(position).getFull_Name()));
        holder.Phone.setText("+"+DiscipleLists.get(position).getCountry()+DiscipleLists.get(position).getPhone());
        holder.id.setText(DiscipleLists.get(position).getId());
        if(DiscipleLists.get(position).getPicture() !=null) {
            holder.discipleImage.setImageBitmap(BitmapFactory.decodeFile(DiscipleLists.get(position).getPicture()));
        }
        holder.build_phase.setText(disciple_phase);

        switch (disciple_phase){
            case "Added":
                build_progress_percent = 0;
                holder.build_phase.setBackgroundColor(Color.RED);
                holder.build_percent.setText("0%");
                break;
            case "WIN":
                build_progress_percent = 25;
                holder.build_phase.setBackgroundColor(myContext.getResources().getColor(R.color.colorSecondary));
                holder.build_percent.setText("25%");
                break;
            case "BUILD":
                build_progress_percent = 60;
                holder.build_phase.setBackgroundColor(myContext.getResources().getColor(R.color.colorPrimary));
                holder.build_percent.setText("60%");
                break;
            case "SEND":
                build_progress_percent = 100;
                holder.build_phase.setBackgroundColor(Color.GREEN);
                holder.build_percent.setText("100%");
                break;
            default:
                build_progress_percent = 0;
                holder.build_percent.setText("0%");
                break;
        }
        holder.progress.setProgress(build_progress_percent);
    }


    public void addItem(Disciples news){
        DiscipleLists.add(news);
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
