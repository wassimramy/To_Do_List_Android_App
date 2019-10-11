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

            currentTime = Notification.getTodaysDate().getTime(); //Get the current time
            getNotificationDetails(); //Get the information of the event causing the broadcast receiver to fire
            Intent contentIntent = new Intent (context, ItemEditActivity.class); //Create an intent to open the item in the ItemEditActivity and show all its data
            contentIntent.putExtra(Model.EXTRA_MESSAGE, itemID ); //Send the itemID to the ItemEditActivity to fetch the respective item
            PendingIntent contentPendingIntent = PendingIntent.getActivity
                    (context, 2, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Set the notification attributes
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
            notificationManager.notify(2, notification); //Fire the notification
    }

    //Called to retrieve the information of the event causing the broadcast receiver to fire
    public void getNotificationDetails(){

        //Go through all the events listed in the notification list
        for (int i = 0 ; i < Model.notificationsList.size(); i++){

            //Check whether item due date is within a minute of the broadcast receiver is fired
         if (currentTime - Model.notificationsList.get(i).notificationDateAndTime < 60*1000
                 && currentTime - Model.notificationsList.get(i).notificationDateAndTime > 0){
             itemID = Model.notificationsList.get(i).itemID; //Retrieve the itemID
             notificationTitle = Model.notificationsList.get(i).notificationTitle; //Retrieve the notificationTitle
             notificationText = Model.notificationsList.get(i).notificationText; //Retrieve the notificationText
         }
        }
    }

}