package com.gcme.deeplife.Fragments;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.gcme.deeplife.Adapters.ReportItems_Adapter;
import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.Models.ReportItem;
import com.gcme.deeplife.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by BENGEOS on 2/17/16.
 */

public class Report_Page extends Fragment {
    private static List<ReportItem> Reports;
    public Report_Page(){
        Reports = new ArrayList<ReportItem>();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.report_page, container, false);
        ListView ReportLists = (ListView) view.findViewById(R.id.report_items);
        Reports = new ArrayList<ReportItem>();
//        for(int i=0;i< DeepLife.REPORTS_FIELDS.length;i++){
//            Reports.add(new ReportItem("","Exposing through mass means" +
//                    ""+i,i));
//        }
        ReportLists.setAdapter(new ReportItems_Adapter(getActivity(), Reports));
        Button btn_Report = (Button) view.findViewById(R.id.btn_send_report);
        btn_Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save_Report();
                Toast.makeText(getActivity(), "Saved to database", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static void Update_Report_Value(int pos,int value){
        Reports.get(pos).setValue(value);
    }
    public void Save_Report(){
        com.gcme.deeplife.DeepLife.myDatabase.Delete_All(DeepLife.Table_Reports);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = sdf.format(new Date());
        ContentValues cv = new ContentValues();
//        cv.put(DeepLife.REPORTS_FIELDS[0], currentDate);
//        for(int i=0;i<DeepLife.REPORTS_FIELDS.length - 1;i++){
//            cv.put(DeepLife.REPORTS_FIELDS[i+1],Reports.get(i).getValue());
//        }
        com.gcme.deeplife.DeepLife.myDatabase.insert(DeepLife.Table_Reports,cv);
        ContentValues cv1 = new ContentValues();
        cv1.put(DeepLife.LOGS_FIELDS[0],"Send_Report");
        cv1.put(DeepLife.LOGS_FIELDS[1], com.gcme.deeplife.DeepLife.myDatabase.get_Top_ID(DeepLife.Table_Reports));
        com.gcme.deeplife.DeepLife.myDatabase.insert(DeepLife.Table_LOGS,cv1);
    }
}
