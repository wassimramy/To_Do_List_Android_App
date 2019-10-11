package com.example.to_dolistapi25.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.net.*;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import com.example.to_dolistapi25.Model.BaseApplication;
import com.example.to_dolistapi25.Model.Item;
import com.example.to_dolistapi25.Model.ItemDAO;
import com.example.to_dolistapi25.Presenter.MainActivityPresenter;
import com.example.to_dolistapi25.R;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static List<Item> list;
    MainActivityPresenter mainActivityPresenter = new MainActivityPresenter(this);
    public static ItemDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.my_recycler_view);

        mainActivityPresenter.onCreate(); //start the presenter
        dao = ((BaseApplication) getApplication()).getDatabase().itemDAO(); //retrieve the database information from the BaseApplication class to use the Data Access Object variable DAO

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Populate the recyclerView
        mainActivityPresenter.retrieveItems(recyclerView, this);

        // Check Wi-Fi connectivity at the startup since the NetworkChangeReceiver is context registered
        checkConnectivity();
    }

    /* Called when the user taps the Add Item button */
    public void addItem(View view) {
        mainActivityPresenter.startEventEditActivity( this);
    }

    /* Called when the user is connected to a Wi-Fi connection*/
    public void updateRemoteDatabase() {
        mainActivityPresenter.updateRemoteDatabase(dao);
    }

    /* Called to check Wi-Fi connection at startup*/
    public void checkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            /* If the Wi-Fi is turned ON --> Update the remote the database and notify the user*/
            updateRemoteDatabase();
            Toast.makeText(getApplicationContext(), "Wi-Fi is connected.", Toast.LENGTH_SHORT ).show();
        }
        else{
            /* If the Wi-Fi is turned OFF --> Notify the user */
            Toast.makeText(getApplicationContext(), "Wi-Fi is disconnected.", Toast.LENGTH_SHORT ).show();
        }
    }

}
