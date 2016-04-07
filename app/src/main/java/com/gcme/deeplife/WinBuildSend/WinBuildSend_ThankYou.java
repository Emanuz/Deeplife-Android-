package com.gcme.deeplife.WinBuildSend;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.MainActivity;
import com.gcme.deeplife.R;
import com.gcme.deeplife.SyncService.SyncService;

/**
 * Created by rog on 11/7/2015.
 */

public class WinBuildSend_ThankYou extends Fragment {

    String stage;
    Button finish;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        stage = getArguments().getString("stage");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.winbuild_thankyou,container,false);
        finish = (Button) viewGroup.findViewById(R.id.win_btn_finish);

        handleFinish();

        return viewGroup;
    }

    public void handleFinish(){

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(WinBuildSend.questions.size()>0) {
                    for (int i = 0; i < WinBuildSend.answers.size(); i++) {
                        if (WinBuildSend.questions.get(i).getMandatory().endsWith("1")) {
                            if (WinBuildSend.answers.get(i).equals("No")) {
                                    Toast.makeText(getActivity(), "Thank You!! Please come again and fill mandatory questions!", Toast.LENGTH_LONG).show();
                                    getNextActivity();
                                    return;
                                }
                            }
                    }

                    ContentValues cv_build = new ContentValues();
                    cv_build.put(DeepLife.DISCIPLES_FIELDS[4], stage);
                    long update_state = com.gcme.deeplife.DeepLife.myDatabase.update(DeepLife.Table_DISCIPLES, cv_build, WinBuildSend.DISCIPLE_ID);
                    if (update_state != -1) {
                        ContentValues log = new ContentValues();
                        log.put(DeepLife.LOGS_FIELDS[0],"Disciple");
                        log.put(DeepLife.LOGS_FIELDS[1], SyncService.Sync_Tasks[3]);
                        log.put(DeepLife.LOGS_FIELDS[2], WinBuildSend.DISCIPLE_ID);
                        com.gcme.deeplife.DeepLife.myDatabase.insert(DeepLife.Table_LOGS, log);

                        Toast.makeText(getActivity(), "Successfully Finished this Stage for "+WinBuildSend.disciple.getFull_Name(), Toast.LENGTH_LONG).show();
                        WinBuildSend.answers.clear();
                        WinBuildSend.answer_index = 0;
                        WinBuildSend.answerchoices.clear();
                        WinBuildSend.questions.clear();
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
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finishAffinity();
        getActivity().finish();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
