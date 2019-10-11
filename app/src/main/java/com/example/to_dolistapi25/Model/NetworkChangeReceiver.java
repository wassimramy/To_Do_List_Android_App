package com.example.to_dolistapi25.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import com.example.to_dolistapi25.View.MainActivity;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive( Context context,  Intent intent) {

        final String action = intent.getAction();
            if (action.equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {
                if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false)) {
                    new MainActivity().updateRemoteDatabase();
                    Toast.makeText(context, "Wi-Fi is connecting...", Toast.LENGTH_LONG ).show();
                } else {
                    Toast.makeText(context, "Wi-Fi is disconnected.", Toast.LENGTH_SHORT ).show();
                }
            }
    }
}
