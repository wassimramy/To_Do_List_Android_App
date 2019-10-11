package com.example.to_dolistapi25.Model.BroadcastReceivers;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.to_dolistapi25.Model.Model;
import com.example.to_dolistapi25.Model.Notification;
import com.example.to_dolistapi25.R;
import com.example.to_dolistapi25.View.ItemEditActivity;

import static com.example.to_dolistapi25.Model.BaseApplication.CHANNEL_0_ID;

public class Notification_0 extends BroadcastReceiver {

    public long itemID;
    public String notificationTitle, notificationText;
    public long currentTime = Notification.getTodaysDate().getTime();

    @Override
    public void onReceive(Context context, Intent intent) {

        getNotificationDetails(); //Get the information of the event causing the broadcast receiver to fire
        Intent contentIntent = new Intent (context, ItemEditActivity.class); //Create an intent to open the item in the ItemEditActivity and show all its data
        contentIntent.putExtra(Model.EXTRA_MESSAGE, itemID ); //Send the itemID to the ItemEditActivity to fetch the respective item
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Set the notification attributes
        android.app.Notification notification = new NotificationCompat.Builder(context, CHANNEL_0_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Due tomorrow")
                .setContentText(notificationTitle + " // " + notificationText)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(contentPendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager  = NotificationManagerCompat.from(context);
        notificationManager.notify(0, notification); //Fire the notification


    }

    //Called to retrieve the information of the event causing the broadcast receiver to fire
    public void getNotificationDetails(){

        //Go through all the events listed in the notification list
        for (int i = 0 ; i < Model.notificationsList.size(); i++){

            //Check whether item due date is within a minute of the broadcast receiver is fired
            if (currentTime + 24*60*60*1000 - Model.notificationsList.get(i).notificationDateAndTime < 60*1000
                    && currentTime + 24*60*60*1000 - Model.notificationsList.get(i).notificationDateAndTime > 0){
                itemID = Model.notificationsList.get(i).itemID; //Retrieve the itemID
                notificationTitle = Model.notificationsList.get(i).notificationTitle; //Retrieve the notificationTitle
                notificationText = Model.notificationsList.get(i).notificationText; //Retrieve the notificationText
            }
        }
    }
}