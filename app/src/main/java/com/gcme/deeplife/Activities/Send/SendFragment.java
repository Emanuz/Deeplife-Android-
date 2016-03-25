package com.gcme.deeplife.Activities.Send;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gcme.deeplife.Activities.Win.WinActivity;
import com.gcme.deeplife.R;

/**
 * Created by rog on 11/7/2015.
 */


public class SendFragment extends Fragment {

    public static final String ARG_PAGE = "page";

    private int mPageNumber;
    ImageView iv_build_image;

    RadioGroup rdGroup;
    RadioButton rb_yes;
    RadioButton rb_no;
    TextView tv_qdisc, tv_note;

    public static SendFragment create(int pageNumber) {
        SendFragment fragment = new SendFragment();
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

        iv_build_image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sendicon));


       if(getPageNumber()<SendActivity.questions.size()) {
            tv_qdisc.setText(SendActivity.questions.get(getPageNumber()).getDescription());
            tv_note.setText(SendActivity.questions.get(getPageNumber()).getNote());

            //if already answered , update the radio buttons
            if(SendActivity.answers.get(getPageNumber()).equals("Yes")){
                rb_yes.setChecked(true);
                rb_no.setChecked(false);
                SendActivity.mPager.setSwipeable(true);
            }
            else if(SendActivity.answers.get(getPageNumber()).equals("No")){
                rb_no.setChecked(true);
                rb_yes.setChecked(false);
                SendActivity.mPager.setSwipeable(true);

            }

            else{
                WinActivity.mPager.setSwipeable(false);
            }

        }


        rb_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendActivity.answer_index = 0;
                SendActivity.answers.set(mPageNumber, SendActivity.answerchoices.get(SendActivity.answer_index));
                SendActivity.mPager.setSwipeable(true);
            }
        });

        rb_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendActivity.answer_index = 1;
                SendActivity.answers.set(mPageNumber, SendActivity.answerchoices.get(SendActivity.answer_index));

                SendActivity.mPager.setSwipeable(true);
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
