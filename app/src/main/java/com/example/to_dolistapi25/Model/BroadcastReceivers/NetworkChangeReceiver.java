package com.example.to_dolistapi25.Model.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import com.example.to_dolistapi25.View.MainActivity;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive( Context context,  Intent intent) {

        final String action = intent.getAction(); //Get action to identify the event that fired the broadcast receiver

        //Check if a connection change was the cause of firing the broadcast receiver
            if (action.equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {
                if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false)) {
                    new MainActivity().updateRemoteDatabase(); //Update the remote database if there is a Wi-Fi connection
                    Toast.makeText(context, "Wi-Fi is connecting...", Toast.LENGTH_LONG ).show(); //Let the user know that the Wi-Fi is turned on
                } else {
                    Toast.makeText(context, "Wi-Fi is disconnected.", Toast.LENGTH_SHORT ).show(); //Let the user know that the Wi-Fi is turned off
                }
            }
    }
}
