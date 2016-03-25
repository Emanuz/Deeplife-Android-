package com.gcme.deeplife;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gcme.deeplife.Models.Disciples;

import java.util.ArrayList;
import java.util.List;

public class DiscipleList extends Fragment {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Button newsBut;
    private ListView lv_disciple;

    public DiscipleList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_disciples_list, container, false);


        lv_disciple = (ListView) view.findViewById(R.id.ls_disciple);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            lv_disciple.setNestedScrollingEnabled(true);
        }
        Disciples d = new Disciples();
        d.setBuild_Phase("Build");
        d.setCountry("Ethiopia");
        d.setEmail("roger@gmail.com");
        d.setFull_Name("Roger Mulgueta");
        d.setGender("Male");
        d.setId("1");
        d.setPhone("0911773469");

        List<Disciples> disciplesList = new ArrayList<Disciples>();
        disciplesList.add(d);
        disciplesList.add(d);
        disciplesList.add(d);
        disciplesList.add(d);
        disciplesList.add(d);
        disciplesList.add(d);
        disciplesList.add(d);
        disciplesList.add(d);
        disciplesList.add(d);
        disciplesList.add(d);

        lv_disciple.setAdapter(new MyDiscipleListAdapter(getContext(), (ArrayList<Disciples>) disciplesList));
        return view;

    }


    public class MyDiscipleListAdapter extends BaseAdapter
    {
        Context context;
        ArrayList<Disciples> disciples;
        public MyDiscipleListAdapter(Context context,ArrayList<Disciples> disciple)
        {
            this.context = context;
            disciples = disciple;
        }


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return disciples.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflate = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflate.inflate(R.layout.dislist,null);

            ImageView dialer = (ImageView) convertView.findViewById(R.id.disciple_phoneimage);
            ImageView iv_profilepic = (ImageView) convertView.findViewById(R.id.list_profile_pic);

            TextView tv_name=(TextView)convertView.findViewById(R.id.userN);
            TextView tv_phone=(TextView)convertView.findViewById(R.id.userphone);
            TextView tv_build_phase=(TextView)convertView.findViewById(R.id.userbuild);


            final String namee = disciples.get(position).getFull_Name();
            final String phonee = disciples.get(position).getPhone();
            final String buildd = disciples.get(position).getBuild_Phase();
            final int id = Integer.parseInt(disciples.get(position).getId());
            final String idstring = disciples.get(position).getId();
            final String picture = disciples.get(position).getPicture();

            tv_name.setText(namee);
            tv_phone.setText(phonee);
            tv_build_phase.setText(buildd);

            if(picture!=null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                iv_profilepic.setImageBitmap(BitmapFactory.decodeFile(picture, options));
                iv_profilepic.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }

            return convertView;
        }
    }

}
