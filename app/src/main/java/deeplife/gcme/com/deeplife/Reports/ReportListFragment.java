package deeplife.gcme.com.deeplife.Reports;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import deeplife.gcme.com.deeplife.DeepLife;
import deeplife.gcme.com.deeplife.Models.ReportItem;
import deeplife.gcme.com.deeplife.R;
import deeplife.gcme.com.deeplife.SyncService.SyncService;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by BENGEOS on 3/27/16.
 */
public class ReportListFragment extends Fragment {
    private static RecyclerView myRecyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static Context myContext;
    private FloatingActionButton SendReport;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_list_page,container,false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.report_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<ReportItem> items = DeepLife.myDatabase.get_All_Report();
        mAdapter = new ReportListAdapter(getActivity(),items);
        myRecyclerView.setAdapter(mAdapter);
        myContext = getActivity();
        SendReport = (FloatingActionButton) view.findViewById(R.id.btn_report_send);
        SendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ReportItem> ReportLists = ReportListAdapter.ReportLists;
                Calendar cal = Calendar.getInstance();
                for (int i = 0; i < ReportLists.size(); i++) {
                    ContentValues cv = new ContentValues();
                    cv.put(deeplife.gcme.com.deeplife.Database.Database.REPORT_FIELDS[0], ReportLists.get(i).getReport_ID());
                    cv.put(deeplife.gcme.com.deeplife.Database.Database.REPORT_FIELDS[1], ReportLists.get(i).getValue());
                    cv.put(deeplife.gcme.com.deeplife.Database.Database.REPORT_FIELDS[2], cal.getTime().toString());
                    Long val = DeepLife.myDatabase.insert(deeplife.gcme.com.deeplife.Database.Database.Table_Reports, cv);
                    if (val > 0) {
                        ContentValues log = new ContentValues();
                        log.put(deeplife.gcme.com.deeplife.Database.Database.LOGS_FIELDS[0], "Report");
                        log.put(deeplife.gcme.com.deeplife.Database.Database.LOGS_FIELDS[1], SyncService.Sync_Tasks[5]);
                        log.put(deeplife.gcme.com.deeplife.Database.Database.LOGS_FIELDS[2], val);
                        long x = deeplife.gcme.com.deeplife.DeepLife.myDatabase.insert(deeplife.gcme.com.deeplife.Database.Database.Table_LOGS, log);

                    }
                }
                Show_Dialog("Your report has sent successfully!");
            }
        });
        return view;
    }
    public static void update_view(){

    }
    public static void Show_Dialog(String message) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setTitle(R.string.app_name).setMessage(message)
                .setPositiveButton("Ok ", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener)
                .show();
    }
}
