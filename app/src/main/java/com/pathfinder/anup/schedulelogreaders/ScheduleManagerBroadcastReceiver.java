package com.pathfinder.anup.schedulelogreaders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Anup on 5/10/2017.
 */

public class ScheduleManagerBroadcastReceiver extends BroadcastReceiver {

    public static final String ONE_TIME = "onr_time";
    PeriodicLogUtils logUtils;

    @Override
    public void onReceive(Context context, Intent intent) {

        logUtils = new PeriodicLogUtils();
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, ONE_TIME);
        wakeLock.acquire();

        //
        Bundle extras = intent.getExtras();
        StringBuilder message = new StringBuilder();
        StringBuilder logs = new StringBuilder();

        if(extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)){
            //intent is getting by one timer
            message.append("One time timer");
        }

        logs = logUtils.readLogs();
        logUtils.writeToFile(logs.toString());
        Format formatter = new SimpleDateFormat("hh:mm:ss a");
        message.append(formatter.format(new Date()));

        //
        Toast.makeText(context, message+ " "+logs, Toast.LENGTH_LONG).show();
        wakeLock.release();
    }

    public void setAlaram(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ScheduleManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        //repeat after 30 seconds
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000*30, pendingIntent);
    }

    public void cancelAlaram(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ScheduleManagerBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    /*public void setOneTimer(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ScheduleManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.TRUE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
    }*/


}
