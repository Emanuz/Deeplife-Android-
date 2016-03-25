package com.gcme.deeplife.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gcme.deeplife.Fragments.Report_Page;
import com.gcme.deeplife.Models.ReportItem;
import com.gcme.deeplife.R;

import java.util.List;
/**
 * Created by BENGEOS on 2/19/16.
 */
public class ReportItems_Adapter extends BaseAdapter {
    private Context myContext;
    private List<ReportItem> Reports;
    public ReportItems_Adapter(Context context, List<ReportItem> reports){
        myContext = context;
        Reports = reports;
    }
    @Override
    public int getCount() {
        return Reports.size();
    }

    @Override
    public Object getItem(int i) {
        return Reports.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.report_page_item,null);
        Button btn_Up,btn_Down;
        final TextView value,title;
        value = (TextView) view.findViewById(R.id.txt_value);
        value.setText(""+Reports.get(i).getValue());
        btn_Up = (Button) view.findViewById(R.id.btn_inc);
        btn_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = Integer.parseInt(value.getText().toString());
                x = x+1;
                value.setText(""+x);
                Report_Page.Update_Report_Value(i, x);
            }
        });
        btn_Down = (Button) view.findViewById(R.id.btn_dec);
        btn_Down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = Integer.parseInt(value.getText().toString());
                x = x-1;
                value.setText(""+x);
                Report_Page.Update_Report_Value(i, x);
            }
        });
        return view;
    }
}
