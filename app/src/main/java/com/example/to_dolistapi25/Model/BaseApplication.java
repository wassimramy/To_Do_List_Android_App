package com.example.to_dolistapi25.Model;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;

import androidx.room.Room;

import com.example.to_dolistapi25.Model.BroadcastReceivers.NetworkChangeReceiver;

public class BaseApplication extends Application {

    private AppDatabase database;
    public static final String CHANNEL_0_ID = "channel_0";
    public static final String CHANNEL_1_ID = "channel_1";
    public static final String CHANNEL_2_ID = "channel_2";

    @Override
    public void onCreate() {
        super.onCreate();
        //Instantiate the database
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "ToDoDB")
                .build();

        //Create notification channels
        createNotificationChannels();

        //Instantiate the BroadcastReceiver for the connectivity change
        NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter intentFilter = new IntentFilter(); //Instantiate intentFilter
        intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION); //Send the action identifier
        registerReceiver(networkChangeReceiver, intentFilter); //Register the intent with the BroadcastReceiver (networkChangeReceiver)
    }

    //Called when the app starts to create notification channels to handle notifications for all Android SDKs
    private void createNotificationChannels() {
        //Check the SDK version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //Instantiate channel_0
            NotificationChannel channel_0 = new NotificationChannel(
                    CHANNEL_0_ID,
                    "Channel 0",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel_0.setDescription("Notification_0 is fired a day before the item due");

            //Instantiate channel_1
            NotificationChannel channel_1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel_1.setDescription("Notification_1 is fired an hour before the item due");

            //Instantiate channel_2
            NotificationChannel channel_2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel_1.setDescription("Notification_2 is fired when the item is due");

            //Instantiate notificationManager to create the notification channels
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel_0); //Create channel_0
            notificationManager.createNotificationChannel(channel_1); //Create channel_1
            notificationManager.createNotificationChannel(channel_2); //Create channel_2
        }
    }

    //Called by the Data Access Object in both ItemEditActivity & MainActivity to write to the database
    public AppDatabase getDatabase()
    {
        return database;
    }
}
