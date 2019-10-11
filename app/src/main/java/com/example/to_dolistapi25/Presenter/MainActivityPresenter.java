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

    //Instantiate the presenter
    public MainActivityPresenter(MainActivity view) {
        this.view = view;
        this.model = new Model();
    }

    public void onCreate() {
        model = new Model();
    }

    //Called to display the items stored in the database in the recyclerView
    private void updateRecyclerView (RecyclerView recyclerView, Context context){
        //Retrieve the position of the item clicked in the recycleView and send it to startItemEditActivity to show the respective item information
        ToDoAdapter toDoAdapter = new ToDoAdapter(context, model.list, position -> startItemEditActivity(position, context));
        recyclerView.setAdapter(toDoAdapter); //Update the recyclerView
    }

    //Called to display the ItemEditActivity
    private void startItemEditActivity(int position, Context context) {
        Intent intent = new Intent(context, ItemEditActivity.class); //Start a new intent for the ItemEditActivity
        intent.putExtra(model.EXTRA_MESSAGE, model.list.get(position).iid ); //Send the ID value of the item clicked to the ItemEditActivity to fetch its data
        context.startActivity(intent); //Start the ItemEditActivity
    }

    /* Called when the user taps the Add Item button */
    public void startItemEditActivity(Context context) {
        long value = 0; //Send ID value of 0 to the ItemEditActivity which signifies a new item is requested to be added
        Intent intent = new Intent(context, ItemEditActivity.class); //Start a new intent for the ItemEditActivity
        intent.putExtra(model.EXTRA_MESSAGE, value ); //Send the ID value
        context.startActivity(intent); //Start the ItemEditActivity
    }

    //Called to populate the recyclerView in the MainActivity
    public void retrieveItems (RecyclerView recyclerView, Context context){
        model.mainActivityStartup(context); //Calls mainActivityStartup() in the model view to retrieve the items from the database
        updateRecyclerView(recyclerView, context); //Update the recyclerView in the MainActivity with the newly retrieved items
    }

    //Called when the Wi-Fi is turned on to update the remote database
    public void updateRemoteDatabase(ItemDAO dao){
        model.updateRemoteDatabase(dao); //Calls updateRemoteDatabase() in the model view to update the remote database
    }

}
