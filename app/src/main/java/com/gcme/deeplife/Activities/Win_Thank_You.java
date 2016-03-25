package com.gcme.deeplife.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gcme.deeplife.Activities.Build.BuildActivity;
import com.gcme.deeplife.Activities.Send.SendActivity;
import com.gcme.deeplife.Activities.Win.WinActivity;
import com.gcme.deeplife.Database.Database;
import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.MainActivity;
import com.gcme.deeplife.R;

/**
 * Created by rog on 11/7/2015.
 */

public class Win_Thank_You extends Fragment {

    String stage;
    Button finish;
    Database db;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        db = new Database(getActivity());
        stage = getArguments().getString("stage");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.winbuild_thankyou,container,false);
        finish = (Button) viewGroup.findViewById(R.id.win_btn_finish);

        switch (stage){
            case "WIN":
                handleWin();
                break;
            case "BUILD":
                handleBuild();
                break;
            case "SEND":
                handleSend();
                break;
        }

        return viewGroup;
    }
    public void handleWin(){

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(WinActivity.answers.size()>0) {
                    for (int i = 0; i < WinActivity.answers.size(); i++) {

                        if(i<WinActivity.questions.size()) {
                            if (WinActivity.questions.get(i).getMandatory().endsWith("1")) {
                                if (WinActivity.answers.get(i) == "No") {
                                    for(int j=0;j<WinActivity.answers.size();j++) {
                                        if(!WinActivity.answered_state) {
                                            ContentValues cv = new ContentValues();
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[0], WinActivity.DISCIPLE_ID);
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[1], j);
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[2], WinActivity.answers.get(j));
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[3], "Added");


                                            long check = db.insert(DeepLife.Table_QUESTION_ANSWER, cv);
                                            if (check != -1)
                                                cv.clear();
                                        }

                                        else if(WinActivity.answered_state){
                                            ContentValues cv = new ContentValues();
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[0], WinActivity.DISCIPLE_ID);
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[1], j);
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[2], WinActivity.answers.get(j));
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[3], "Added");


                                            long check = db.update(DeepLife.Table_QUESTION_ANSWER, cv, WinActivity.answer_from_db_id.get(j));
                                            if (check != -1)
                                                cv.clear();
                                        }
                                    }
                                    Toast.makeText(getActivity(), "Thank You!! Please come again and fill mandatory questions!", Toast.LENGTH_LONG).show();
                                    getNextActivity();
                                    return;
                                }
                            }
                        }

                        ContentValues cv = new ContentValues();
                        cv.put(DeepLife.QUESTION_ANSWER_FIELDS[0], WinActivity.DISCIPLE_ID);
                        cv.put(DeepLife.QUESTION_ANSWER_FIELDS[1], i);
                        cv.put(DeepLife.QUESTION_ANSWER_FIELDS[2], WinActivity.answers.get(i));
                        cv.put(DeepLife.QUESTION_ANSWER_FIELDS[3], "WIN");

                        long check = db.insert(DeepLife.Table_QUESTION_ANSWER, cv);
                    }

                    ContentValues cv_build = new ContentValues();
                    cv_build.put(DeepLife.DISCIPLES_FIELDS[4], "WIN");
                    long update_state = db.update(DeepLife.Table_DISCIPLES, cv_build, WinActivity.DISCIPLE_ID);
                    if (update_state != -1) {
                        Toast.makeText(getActivity(), "Successfully Finished Win Stage!", Toast.LENGTH_LONG).show();
                        WinActivity.answers.clear();
                        WinActivity.answer_index = 0;
                        WinActivity.answerchoices.clear();
                        WinActivity.questions.clear();

                        getNextActivity();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Sorry No question available", Toast.LENGTH_LONG).show();
                    getNextActivity();
                }
            }
        });

    }

    public void handleBuild(){

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(BuildActivity.answers.size()>0) {
                    for (int i = 0; i < BuildActivity.answers.size(); i++) {

                        if(i<BuildActivity.questions.size()) {
                            if (BuildActivity.questions.get(i).getMandatory().endsWith("1")) {
                                if (BuildActivity.answers.get(i) == "No") {
                                    for(int j=0;j<BuildActivity.answers.size();j++) {
                                        if(!BuildActivity.answered_state) {
                                            ContentValues cv = new ContentValues();
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[0], BuildActivity.DISCIPLE_ID);
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[1], j);
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[2], BuildActivity.answers.get(j));
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[3], "WIN");


                                            long check = db.insert(DeepLife.Table_QUESTION_ANSWER, cv);
                                            cv.clear();
                                        }

                                        else if(BuildActivity.answered_state){
                                            ContentValues cv = new ContentValues();
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[0], BuildActivity.DISCIPLE_ID);
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[1], j);
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[2], BuildActivity.answers.get(j));
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[3], "WIN");


                                            long check = db.update(DeepLife.Table_QUESTION_ANSWER, cv, BuildActivity.answer_from_db_id.get(j));
                                            cv.clear();
                                        }
                                    }
                                    Toast.makeText(getActivity(), "Thank You!! Please come again and fill mandatory questions!", Toast.LENGTH_LONG).show();
                                    getNextActivity();
                                    return;
                                }
                            }
                        }


                        ContentValues cv = new ContentValues();
                        cv.put(DeepLife.QUESTION_ANSWER_FIELDS[0], BuildActivity.DISCIPLE_ID);
                        cv.put(DeepLife.QUESTION_ANSWER_FIELDS[1], i);
                        cv.put(DeepLife.QUESTION_ANSWER_FIELDS[2], BuildActivity.answers.get(i));
                        cv.put(DeepLife.QUESTION_ANSWER_FIELDS[3], "BUILD");

                        long check = db.insert(DeepLife.Table_QUESTION_ANSWER, cv);

                    }

                    ContentValues cv_build = new ContentValues();
                    cv_build.put(DeepLife.DISCIPLES_FIELDS[4], "BUILD");
                    long update_state = db.update(DeepLife.Table_DISCIPLES, cv_build, BuildActivity.DISCIPLE_ID);
                    if (update_state != -1) {
                        Toast.makeText(getActivity(), "Successfully Finished Build Stage!", Toast.LENGTH_LONG).show();
                        BuildActivity.answers.clear();
                        BuildActivity.answer_index = 0;
                        BuildActivity.answerchoices.clear();
                        BuildActivity.questions.clear();

                        getNextActivity();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Sorry No question available", Toast.LENGTH_LONG).show();
                    getNextActivity();
                }
            }
        });
    }

    public void handleSend(){

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SendActivity.answers.size()>0) {
                    for (int i = 0; i < SendActivity.answers.size(); i++) {

                        if(i<SendActivity.questions.size()) {
                            if (SendActivity.questions.get(i).getMandatory().endsWith("1")) {
                                if (SendActivity.answers.get(i) == "No") {
                                    for(int j=0;j<SendActivity.answers.size();j++) {
                                        if(!SendActivity.answered_state) {
                                            ContentValues cv = new ContentValues();
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[0], SendActivity.DISCIPLE_ID);
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[1], j);
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[2], SendActivity.answers.get(j));
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[3], "BUILD");


                                            long check = db.insert(DeepLife.Table_QUESTION_ANSWER, cv);

                                            cv.clear();
                                        }

                                        else if(SendActivity.answered_state){
                                            ContentValues cv = new ContentValues();
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[0], SendActivity.DISCIPLE_ID);
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[1], j);
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[2], SendActivity.answers.get(j));
                                            cv.put(DeepLife.QUESTION_ANSWER_FIELDS[3], "BUILD");


                                            long check = db.update(DeepLife.Table_QUESTION_ANSWER, cv, SendActivity.answer_from_db_id.get(j));

                                            cv.clear();
                                        }
                                    }
                                    Toast.makeText(getActivity(), "Thank You!! Please come again and fill mandatory questions!", Toast.LENGTH_LONG).show();
                                    getNextActivity();
                                    return;
                                }
                            }
                        }


                        ContentValues cv = new ContentValues();
                        cv.put(DeepLife.QUESTION_ANSWER_FIELDS[0], SendActivity.DISCIPLE_ID);
                        cv.put(DeepLife.QUESTION_ANSWER_FIELDS[1], i);
                        cv.put(DeepLife.QUESTION_ANSWER_FIELDS[2], SendActivity.answers.get(i));
                        cv.put(DeepLife.QUESTION_ANSWER_FIELDS[3], "SEND");

                        long check = db.insert(DeepLife.Table_QUESTION_ANSWER, cv);

                    }

                    ContentValues cv_build = new ContentValues();
                    cv_build.put(DeepLife.DISCIPLES_FIELDS[4], "SEND");
                    long update_state = db.update(DeepLife.Table_DISCIPLES, cv_build, SendActivity.DISCIPLE_ID);
                    if (update_state != -1) {
                        Toast.makeText(getActivity(), "Successfully Finished Send Stage!", Toast.LENGTH_LONG).show();
                        SendActivity.answers.clear();
                        SendActivity.answer_index = 0;
                        SendActivity.answerchoices.clear();
                        SendActivity.questions.clear();

                        getNextActivity();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Sorry No question available", Toast.LENGTH_LONG).show();

                    getNextActivity();
                }
            }
        });
    }

    public void getNextActivity(){
        Intent intent = new Intent(getActivity(),MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        db.dispose();
    }
}
