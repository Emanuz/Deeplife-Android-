package com.gcme.deeplife.Disciples;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gcme.deeplife.Activities.Disciple_Profile;
import com.gcme.deeplife.Database.Database;
import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.MainActivity;
import com.gcme.deeplife.Models.Disciples;
import com.gcme.deeplife.R;

import java.util.ArrayList;

/**
 * Created by BENGEOS on 3/25/16.
 */
public class DiscipleListAdapter extends RecyclerView.Adapter<DiscipleListAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Disciples> DiscipleLists;
    private static MyClickListener myClickListener;
    private static Context myContext;
    private static Database myDB;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView FullName,Phone;
        TextView Email;
        TextView id;
        ImageView discipleImage;
        ImageView phoneIcon;

        public DataObjectHolder(View itemView) {
            super(itemView);
            FullName = (TextView) itemView.findViewById(R.id.txt_disciple_name);
            Phone = (TextView) itemView.findViewById(R.id.txt_disciple_phone);
            id = (TextView) itemView.findViewById(R.id.disciple_hidden_id);
            discipleImage = (ImageView) itemView.findViewById(R.id.disciple_image_icon);
            phoneIcon = (ImageView) itemView.findViewById(R.id.disciple_list_phone_icon);

            phoneIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + Phone.getText()));
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
           Toast.makeText(myContext, "Long click on id= "+id.getText().toString(), Toast.LENGTH_SHORT).show();
            DiscipleListAdapter.delete_Dialog(Integer.parseInt(id.getText().toString()),FullName.getText().toString());
            return true;
        }
    }

    public static void delete_Dialog(final int id,final String name) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        long deleted = myDB.remove(DeepLife.Table_DISCIPLES,id);
                        if(deleted!=-1){
                            Toast.makeText(myContext,"Successfully Deleted",Toast.LENGTH_SHORT).show();
        /*                    ContentValues values = new ContentValues();
                            Cursor cursor = myDB.get_value_by_ID(DeepLife.Table_DISCIPLES, id + "");
                            String piclocation = cursor.getString(cursor.getColumnIndex(DeepLife.DISCIPLES_COLUMN[7]));
                            if(piclocation.toString() !=null) {
                                boolean delete = new File(piclocation).delete();
                                if (delete) {
                                    Log.i(DeepLife.TAG, "Profile Picture deletedFile deleted");
                                    Toast.makeText(myContext, "Disciple deleted with profile picture", Toast.LENGTH_SHORT).show();
                                }
                            }*/
                            myDB.dispose();
                            Intent intent = new Intent(myContext,MainActivity.class);
                            myContext.startActivity(intent);
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
        myDB = new Database(myContext);

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

        holder.FullName.setText((DiscipleLists.get(position).getFull_Name()));
        holder.Phone.setText(DiscipleLists.get(position).getPhone());
        holder.id.setText(DiscipleLists.get(position).getId());

        if(DiscipleLists.get(position).getPicture() !=null) {
            holder.discipleImage.setImageBitmap(BitmapFactory.decodeFile(DiscipleLists.get(position).getPicture()));
        }

    }
    public void addItem(Disciples news){
        DiscipleLists.add(news);
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
