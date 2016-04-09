package deeplife.gcme.com.deeplife.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import deeplife.gcme.com.deeplife.Models.Country;
import deeplife.gcme.com.deeplife.R;

import java.util.ArrayList;

/**
 * Created by BENGEOS on 4/5/16.
 */
public class Countries_Adapter extends ArrayAdapter<Country> {
    private Context myContext;
    private ArrayList<Country> object;
    public Countries_Adapter(Context context, int txtViewResourceId, ArrayList<Country> objects) {
        super(context, txtViewResourceId, objects);
        myContext = context;
        this.object = objects;
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomView(position, cnvtView, prnt);
    }
    @Override
    public View getView(int pos, View cnvtView, ViewGroup prnt) {
        return getCustomView(pos, cnvtView, prnt);
    }
    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mySpinner = inflater.inflate(R.layout.countries_spinner, parent, false);
        TextView main_text = (TextView) mySpinner.findViewById(R.id.spinner_text);
        main_text.setText(object.get(position).getName());
        return mySpinner;
    }

}
