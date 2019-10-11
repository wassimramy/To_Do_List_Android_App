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

        mainActivityPresenter.onCreate();
        dao = ((BaseApplication) getApplication()).getDatabase().itemDAO();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mainActivityPresenter.retrieveItems(recyclerView, this);

        // At activity startup we manually check the internet status and change
        // the text status
        checkConnectivity();
    }

    /** Called when the user taps the Add Item button */
    public void addItem(View view) {
        mainActivityPresenter.startEventEditActivity( this);
    }

    public void updateRemoteDatabase() {
        mainActivityPresenter.updateRemoteDatabase(dao);
    }

    public void checkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            updateRemoteDatabase();
            Toast.makeText(getApplicationContext(), "Wi-Fi is connected.", Toast.LENGTH_SHORT ).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Wi-Fi is disconnected.", Toast.LENGTH_SHORT ).show();
        }
    }

}
