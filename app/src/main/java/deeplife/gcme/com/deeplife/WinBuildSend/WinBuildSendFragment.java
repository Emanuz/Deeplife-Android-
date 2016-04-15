package deeplife.gcme.com.deeplife.WinBuildSend;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import deeplife.gcme.com.deeplife.Database.Database;
import deeplife.gcme.com.deeplife.R;


/**
 * Created by rog on 11/7/2015.
 */


public class WinBuildSendFragment extends Fragment {

    public static final String ARG_PAGE = "page";
    public static final String STAGE_MAIN = "stage";
    private int mPageNumber;
    private String stage;

    ImageView iv_build_image;

    RadioButton rb_yes;
    RadioButton rb_no;
    TextView tv_qdisc, tv_note;

    public static WinBuildSendFragment create(int pageNumber, String stage) {
        WinBuildSendFragment fragment = new WinBuildSendFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putString(STAGE_MAIN, stage);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        stage = getArguments().getString(STAGE_MAIN);
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

        switch (stage){
            case "WIN":
                iv_build_image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.winicon));
                break;
            case "BUILD":
                iv_build_image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.buildicon));
                break;
            case "SEND":
                iv_build_image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sendicon));
                break;
        }

        if(getPageNumber()<WinBuildSend.questions.size()) {
            tv_qdisc.setText(WinBuildSend.questions.get(getPageNumber()).getDescription());
            tv_note.setText(WinBuildSend.questions.get(getPageNumber()).getNote());

            if(getPageNumber()<WinBuildSend.answers.size()){
                if (WinBuildSend.answers.get(getPageNumber()).equals("Yes")) {
                    rb_yes.setChecked(true);
                    rb_no.setChecked(false);
                    WinBuildSend.mPager.setSwipeable(true);
                } else if (WinBuildSend.answers.get(getPageNumber()).equals("No")) {
                    rb_no.setChecked(true);
                    rb_yes.setChecked(false);
                    WinBuildSend.mPager.setSwipeable(true);
                } else {
                    WinBuildSend.mPager.setSwipeable(false);
                }
            }


        rb_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WinBuildSend.answer_index = 0;
                WinBuildSend.answers.set(mPageNumber, WinBuildSend.answerchoices.get(WinBuildSend.answer_index));
                WinBuildSend.mPager.setSwipeable(true);
            }
        });

        rb_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(Database.TAG,"Answer size " +WinBuildSend.answers.size() + "\n Question size = " + WinBuildSend.questions.size());
                WinBuildSend.answer_index = 1;
                WinBuildSend.answers.set(mPageNumber, WinBuildSend.answerchoices.get(WinBuildSend.answer_index));
                WinBuildSend.mPager.setSwipeable(true);
            }
        });

        }
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
