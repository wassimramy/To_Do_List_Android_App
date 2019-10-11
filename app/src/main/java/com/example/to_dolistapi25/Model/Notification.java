package com.example.to_dolistapi25.Model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.to_dolistapi25.Model.BroadcastReceivers.Notification_0;
import com.example.to_dolistapi25.Model.BroadcastReceivers.Notification_1;
import com.example.to_dolistapi25.Model.BroadcastReceivers.Notification_2;

import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

public class Notification {
    public String notificationTitle, notificationText;
    public long itemID;
    public long notificationDateAndTime;
    public int requestCode;
    public Context context;
    private AlarmManager alarmManager_0, alarmManager_1, alarmManager_2;
    private PendingIntent pendingIntent_0, pendingIntent_1, pendingIntent_2;


    Notification(long itemID, String notificationTitle, String notificationText, long notificationDateAndTime, int requestCode, Context context){

        Intent intent_0, intent_1, intent_2;

        this.notificationTitle = notificationTitle;
        this.notificationText = notificationText;
        this.itemID = itemID;
        this.notificationDateAndTime = notificationDateAndTime;
        this.requestCode = requestCode;
        this.context = context;

        intent_0 = new Intent(context, Notification_0.class); //Create
        pendingIntent_0 = PendingIntent.getBroadcast(context.getApplicationContext(), requestCode, intent_0, 0);
        alarmManager_0 = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        intent_0.putExtra(Model.EXTRA_MESSAGE, itemID);

        intent_1 = new Intent(context, Notification_1.class);
        pendingIntent_1 = PendingIntent.getBroadcast(context.getApplicationContext(), requestCode, intent_1, 0);
        alarmManager_1 = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        intent_2 = new Intent(context, Notification_2.class);
        pendingIntent_2 = PendingIntent.getBroadcast(context.getApplicationContext(), requestCode, intent_2, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager_2 = (AlarmManager) context.getSystemService(ALARM_SERVICE);

    }

     public static Date getTodaysDate (){
        // get a calendar instance, which defaults to "now"
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

     void setAlarm() {
        if (notificationDateAndTime - getTodaysDate().getTime() >= 24*60*60*1000){
            setNotification_0();
        }
        if (notificationDateAndTime - getTodaysDate().getTime() >= 60*60*1000){
            setNotification_1();
        }
        setNotification_2();
    }

    private void setNotification_0() {
        alarmManager_0.set(AlarmManager.RTC, notificationDateAndTime - 24*60*60*1000, pendingIntent_0);
    }

    private void setNotification_1() {
        alarmManager_1.set(AlarmManager.RTC, notificationDateAndTime - 60*60*1000, pendingIntent_1);
    }

    private void setNotification_2() {
        alarmManager_2.set(AlarmManager.RTC, notificationDateAndTime, pendingIntent_2);
    }

    void disableNotifications() {
        alarmManager_0.cancel(pendingIntent_0);
        alarmManager_1.cancel(pendingIntent_1);
        alarmManager_2.cancel(pendingIntent_2);
    }

}