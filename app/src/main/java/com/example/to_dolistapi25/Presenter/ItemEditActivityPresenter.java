package com.example.to_dolistapi25.Presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.to_dolistapi25.Model.Item;
import com.example.to_dolistapi25.Model.Model;
import com.example.to_dolistapi25.View.ItemEditActivity;
import com.example.to_dolistapi25.View.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.to_dolistapi25.View.MainActivity.list;
import static java.util.Calendar.DAY_OF_YEAR;

public class ItemEditActivityPresenter {

    private ItemEditActivity view;
    private Model model;


    public ItemEditActivityPresenter(ItemEditActivity view) {
        this.view = view;
        this.model = new Model();
    }

    public void presentData (Item item){
        // Capture the layout's Title & Description TextViews and set the item details as their hints
        view.itemTitle.setHint(item.itemTitle);
        view.itemDescription.setHint(item.itemDescription);
        String format = "MM/dd/yyyy"; //Date format
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        view.itemDate.setHint(sdf.format(item.itemDateAndTime)); //Extract the date from the itemDateAndTime value and project it to itemDate
        format = "hh:mm a z"; //Time format
        sdf = new SimpleDateFormat(format, Locale.US);
        view.itemTime.setHint(sdf.format(item.itemDateAndTime)); //Extract the time from the itemDateAndTime value and project it to itemTime
        view.itemStatus.setChecked(item.itemStatus); //Retrieve the status of the item and project it to the checkbox
    }

    //Called when the ItemEditActivity is created
    public void itemEditActivityStartup (long value){
        Item item = model.itemEditActivityStartup(value); //Retrieve an item from the database or create a new item
        presentData(item); //Present the retrieved item from the previous method
    }

    //Called when the Date is picked in the ItemEditActivity
    public void updateEventDate() {
        String format = "MM/dd/yyyy"; //Date format
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        view.itemDate.setText(sdf.format(view.calendar.getTime())); //Display the picked date in itemDate after formatting it
    }

    //Called when the Time is picked in the ItemEditActivity
    public void updateEventTime() {
        String format = "hh:mm a z"; //Time format
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        view.itemTime.setText(sdf.format(view.calendar.getTime())); //Display the picked time in itemTime after formatting it
    }

    //Called to get back to the MainActivity after the user finishes editing
    private void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); //Set flags for the new activity
        context.startActivity(intent); //Start the MainActivity
        view.finish(); //Close the ItemEditActivity
    }

    //Called to retrieve the data displayed in the ItemEditActivity
    private void retrieveDataFromItemEditActivity() {

        //If itemTitle is empty, set the old value (displayed in the hint view) as its new value
        if (view.itemTitle.getText().toString().isEmpty()){
            view.itemTitle.setText(view.itemTitle.getHint());
        }
        //If itemTitle is not empty, replace '\n' to eliminate parsing errors
        else {
            view.itemTitle.setText(view.itemTitle.getText().toString().replace('\n', ' '));
        }

        //If itemDescription is empty, set the old value (displayed in the hint view)  as its new value
        if (view.itemDescription.getText().toString().isEmpty()){
            view.itemDescription.setText(view.itemDescription.getHint());
        }
        //If itemTitle is not empty, replace '\n' to eliminate parsing errors
        else {
            view.itemDescription.setText(view.itemDescription.getText().toString().replace('\n', ' '));
        }

        //If itemDate is empty, set the old value (displayed in the hint view) as its new value
        if (view.itemDate.getText().toString().isEmpty()){
            view.itemDate.setText(view.itemDate.getHint());
        }

        //If itemTime is empty, set the old value (displayed in the hint view) as its new value
        if (view.itemTime.getText().toString().isEmpty()){
            view.itemTime.setText(view.itemTime.getHint());
        }

    }
        //Called to replace the old item information with the values displayed in ItemEditActivity
        private void updateItem() {
            model.item.itemTitle = view.itemTitle.getText().toString(); //Retrieve the itemTitle
            model.item.itemDescription = view.itemDescription.getText().toString(); //Retrieve the itemDescription
            model.item.itemDateAndTime = formatDateAndTime(); //Retrieve the combined data and time values
            model.item.itemStatus = view.itemStatus.isChecked(); //Retrieve the status checkbox value
        }

    //Called to combine the itemDate and itemTime values together to save it in the database
    private Date formatDateAndTime() {

        SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a z"); //Date and time format
        Date formattedDateAndTime;

        try {
            //Concatenate both itemDate & itemTime and format the output
            formattedDateAndTime = myFormat.parse( view.itemDate.getText().toString().concat( " " +
                    view.itemTime.getText().toString()));
        } catch (ParseException e) {
            //If the itemDate and itemTime were not formatted correctly for any reason, return tomorrow's date
            // get a calendar instance, which defaults to "now"
            Calendar calendar = Calendar.getInstance();
            // add one day to the date/calendar
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            // now get "tomorrow"
            formattedDateAndTime = calendar.getTime();
        }
        return formattedDateAndTime;
    }

    //Called after the user finishes editing item information and wants to get back to the MainActivity
    public void saveItem(long value, Context context) {
        retrieveDataFromItemEditActivity(); //Retrieve the item details and update the item details
        updateItem(); //Replace the old item information with the values displayed in ItemEditActivity
        model.saveItem(value, context); //Replace the old item information with the values displayed in ItemEditActivity
        startMainActivity(context); //Call the startMainActivity() to get back to the MainActivity
    }

    //Called when the user taps the Cancel button
    public void cancelItem(Context context) {
        startMainActivity(context); //Call the startMainActivity() to get back to the MainActivity
    }

    //Called when the user taps the Delete button
    public void deleteItem(long value, Context context) {
        model.deleteItem(value, context); //Calls deleteItem() in the model view to delete this item from the database
        startMainActivity(context); //Call the startMainActivity() to get back to the MainActivity
    }

}
