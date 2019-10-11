package com.example.to_dolistapi25.Model.BroadcastReceivers;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.to_dolistapi25.Model.Model;
import com.example.to_dolistapi25.Model.Notification;
import com.example.to_dolistapi25.R;
import com.example.to_dolistapi25.View.ItemEditActivity;

import static com.example.to_dolistapi25.Model.BaseApplication.CHANNEL_2_ID;

public class Notification_2 extends BroadcastReceiver {

    public long itemID;
    public String notificationTitle, notificationText;
    public long currentTime = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

            currentTime = Notification.getTodaysDate().getTime();
            getNotificationDetails();
            Intent contentIntent = new Intent (context, ItemEditActivity.class);
            contentIntent.putExtra(Model.EXTRA_MESSAGE, itemID );
            PendingIntent contentPendingIntent = PendingIntent.getActivity
                    (context, 2, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            android.app.Notification notification = new NotificationCompat.Builder(context, CHANNEL_2_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Due now")
                    .setContentText(notificationTitle + " // " + notificationText)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_REMINDER)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setContentIntent(contentPendingIntent)
                    .setAutoCancel(true)
                    .build();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(2, notification);
    }

    public void getNotificationDetails(){
        for (int i = 0 ; i < Model.notificationsList.size(); i++){
         if (currentTime - Model.notificationsList.get(i).notificationDateAndTime < 60*1000
                 && currentTime - Model.notificationsList.get(i).notificationDateAndTime > 0){
             itemID = Model.notificationsList.get(i).itemID;
             notificationTitle = Model.notificationsList.get(i).notificationTitle;
             notificationText = Model.notificationsList.get(i).notificationText;
         }
        }
    }

}