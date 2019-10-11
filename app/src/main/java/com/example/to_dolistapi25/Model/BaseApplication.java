package com.example.to_dolistapi25.Model;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;

import androidx.room.Room;

public class BaseApplication extends Application {

    private AppDatabase database;
    public static final String CHANNEL_0_ID = "channel_0";
    public static final String CHANNEL_1_ID = "channel_1";
    public static final String CHANNEL_2_ID = "channel_2";

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "ToDoDB")
                .build();

        createNotificationChannels();

        NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);

    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel_0 = new NotificationChannel(
                    CHANNEL_0_ID,
                    "Channel 0",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel_0.setDescription("Notification_0 is fired a day before the item due");

            NotificationChannel channel_1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel_1.setDescription("Notification_1 is fired an hour before the item due");

            NotificationChannel channel_2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel_1.setDescription("Notification_2 is fired when the item is due");

            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(channel_0);
            manager.createNotificationChannel(channel_1);
            manager.createNotificationChannel(channel_2);
        }
    }

    public AppDatabase getDatabase()
    {
        return database;
    }
}
