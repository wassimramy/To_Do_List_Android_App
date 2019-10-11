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
        // Capture the layout's TextView and set the string as its text
        view.itemTitle.setHint(item.itemTitle);
        view.itemDescription.setHint(item.itemDescription);
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        view.itemDate.setHint(sdf.format(item.itemDateAndTime));
        myFormat = "hh:mm a z";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        view.itemTime.setHint(sdf.format(item.itemDateAndTime));
        view.itemStatus.setChecked(item.itemStatus);
    }

    public void itemEditActivityStartup (long value){
        Item item = model.itemEditActivityStartup(value);
        presentData(item);
    }

    public void updateEventDate() {

        //String myFormat = "MM/dd/yyyy G 'at' HH:mm:ss z";
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        view.itemDate.setText(sdf.format(view.myCalendar.getTime()));
    }

    public void updateEventTime() {

        //String myFormat = "MM/dd/yyyy G 'at' HH:mm:ss z";
        String myFormat = "hh:mm a z";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        view.itemTime.setText(sdf.format(view.myCalendar.getTime()));
    }

    public void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        view.finish();
    }

    public void retrieveDataFromItemEditActivity() {

        if (view.itemTitle.getText().toString().isEmpty()){
            view.itemTitle.setText(view.itemTitle.getHint());
        }
        else {
            view.itemTitle.setText(view.itemTitle.getText().toString().replace('\n', ' '));
        }

        if (view.itemDescription.getText().toString().isEmpty()){
            view.itemDescription.setText(view.itemDescription.getHint());
        }
        else {
            view.itemDescription.setText(view.itemDescription.getText().toString().replace('\n', ' '));
        }

        if (view.itemDate.getText().toString().isEmpty()){
            view.itemDate.setText(view.itemDate.getHint());
        }

        if (view.itemTime.getText().toString().isEmpty()){
            view.itemTime.setText(view.itemTime.getHint());
        }
        updateItem();
    }

        private void updateItem() {
            model.item.itemTitle = view.itemTitle.getText().toString();
            model.item.itemDescription = view.itemDescription.getText().toString();
            model.item.itemDateAndTime = formatDateAndTime();
            model.item.itemStatus = view.itemStatus.isChecked();
        }

    public Date formatDateAndTime() {

        SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a z");
        Date formattedDateAndTime;

        try {
            formattedDateAndTime = myFormat.parse( view.itemDate.getText().toString().concat( " " +
                    view.itemTime.getText().toString()));
        } catch (ParseException e) {
            // get a calendar instance, which defaults to "now"
            Calendar calendar = Calendar.getInstance();
            // add one day to the date/calendar
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            // now get "tomorrow"
            formattedDateAndTime = calendar.getTime();
        }
        return formattedDateAndTime;
    }

    public void saveItem(long value, Context context) {
        retrieveDataFromItemEditActivity();
        model.saveItem(value, context);
        startMainActivity(context);
        //dao.updateItem(item.iid, itemTitle.getText().toString(), itemDescription.getText().toString(),formattedDateAndTime, itemStatus.isChecked());
    }

    public void cancelItem(Context context) {
        startMainActivity(context);
    }

    public void deleteItem(long value, Context context) {
        model.deleteItem(value, context);
        startMainActivity(context);
    }

}
