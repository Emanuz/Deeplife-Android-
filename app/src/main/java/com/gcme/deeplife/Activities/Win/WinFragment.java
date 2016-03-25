package com.gcme.deeplife.Activities.Win;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gcme.deeplife.R;


/**
 * Created by rog on 11/7/2015.
 */


public class WinFragment extends Fragment {

    public static final String ARG_PAGE = "page";

    private int mPageNumber;
    ImageView iv_build_image;

    RadioGroup rdGroup;
    RadioButton rb_yes;
    RadioButton rb_no;
    TextView tv_qdisc, tv_note;

    public static WinFragment create(int pageNumber) {
        WinFragment fragment = new WinFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.winfragment, container, false);

         tv_qdisc = (TextView) rootView.findViewById(R.id.win_question);
        rb_yes = (RadioButton) rootView.findViewById(R.id.rb_yes);
        rb_no = (RadioButton) rootView.findViewById(R.id.rb_no);
        tv_note = (TextView) rootView.findViewById(R.id.win_note);
        iv_build_image = (ImageView) rootView.findViewById(R.id.win_image);

        iv_build_image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.winicon));


        if(getPageNumber()<WinActivity.questions.size()) {
            tv_qdisc.setText(WinActivity.questions.get(getPageNumber()).getDescription());
            tv_note.setText(WinActivity.questions.get(getPageNumber()).getNote());

            //if already answered , update the radio buttons
            if(WinActivity.answers.get(getPageNumber()).equals("Yes")){
                rb_yes.setChecked(true);
                rb_no.setChecked(false);
                WinActivity.mPager.setSwipeable(true);
            }
            else if(WinActivity.answers.get(getPageNumber()).equals("No")){
                rb_no.setChecked(true);
                rb_yes.setChecked(false);
                WinActivity.mPager.setSwipeable(true);

            }

            else{
                Log.i("Deep Life", "else if ... swipe off");
                WinActivity.mPager.setSwipeable(false);
            }

        }


        rb_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WinActivity.answer_index = 0;
                WinActivity.answers.set(mPageNumber, WinActivity.answerchoices.get(WinActivity.answer_index));
                WinActivity.mPager.setSwipeable(true);
            }
        });

        rb_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WinActivity.answer_index = 1;
                WinActivity.answers.set(mPageNumber, WinActivity.answerchoices.get(WinActivity.answer_index));
                WinActivity.mPager.setSwipeable(true);
            }
        });


        //rb_no.setSelected(true);

        return rootView;
    }


    public int getPageNumber() {
        return mPageNumber;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
