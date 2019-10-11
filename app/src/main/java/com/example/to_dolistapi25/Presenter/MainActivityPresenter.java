package com.example.to_dolistapi25.Presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ViewDebug;

import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolistapi25.Model.ItemDAO;
import com.example.to_dolistapi25.Model.Model;
import com.example.to_dolistapi25.View.ItemEditActivity;
import com.example.to_dolistapi25.View.MainActivity;


public class MainActivityPresenter {

    private MainActivity view;
    private Model model;

    public MainActivityPresenter(MainActivity view) {
        this.view = view;
        this.model = new Model();
    }

    public void onCreate() {
        model = new Model();
    }

    private void updateRecyclerView (RecyclerView recyclerView, Context context){
        ToDoAdapter mAdapter = new ToDoAdapter(context, model.list, position -> startEventEditActivity(position, context));
        recyclerView.setAdapter(mAdapter);
    }

    private void startEventEditActivity(int position, Context context) {
        Intent intent = new Intent(context, ItemEditActivity.class);
        intent.putExtra(model.EXTRA_MESSAGE, model.list.get(position).iid );
        context.startActivity(intent);
    }

    public void startEventEditActivity(Context context) {
        long value = 0;
        Intent intent = new Intent(context, ItemEditActivity.class);
        intent.putExtra(model.EXTRA_MESSAGE, value );
        context.startActivity(intent);
    }

    public void retrieveItems (RecyclerView recyclerView, Context context){
        model.mainActivityStartup(context);
        updateRecyclerView(recyclerView, context);
    }

    public void updateRemoteDatabase(ItemDAO dao){
        model.updateRemoteDatabase(dao);
    }

}
