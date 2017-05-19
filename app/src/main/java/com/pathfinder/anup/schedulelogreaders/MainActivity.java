package com.pathfinder.anup.schedulelogreaders;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends Activity implements View.OnClickListener {

    Button btn_start, btn_cancel, btn_oneTimer;
    ScheduleManagerBroadcastReceiver alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = this.getApplicationContext();
        alarmManager = new ScheduleManagerBroadcastReceiver();
        startRepeatingLogsAlarm(context);



        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        btn_oneTimer = (Button) findViewById(R.id.btn_oneTime);
        btn_oneTimer.setOnClickListener(this);


        /*try {
            java.lang.Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder log=new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line);
            }
            TextView tv = (TextView)findViewById(R.id.textview);
            tv.setText(log.toString());
        } catch (IOException e) {
        }*/
    }

    @Override
    public void onClick(View view) {
        Context context = this.getApplicationContext();
        switch (view.getId()){
            /*case R.id.btn_start:
                startRepeatingAlarm(context);
                break;*/
            case R.id.btn_cancel:

                cancelAlarm(context);
                break;
            case R.id.btn_oneTime:
                //startOneTimer(context);
                crashApp();
        }
    }

    private void startRepeatingLogsAlarm(Context context){
        if(alarmManager != null){
            alarmManager.setAlaram(context);
        } else {
            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelAlarm(Context context){
        if(alarmManager != null){
            alarmManager.cancelAlaram(context);
        }else {
            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    private void crashApp(){
        throw new RuntimeException("APP CRASHED");
    }

    /*private void startOneTimer(Context context){
        if(alarmManager != null){
            alarmManager.setOneTimer(context);
        } else {
            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }*/
}
