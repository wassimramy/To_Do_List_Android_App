package com.example.to_dolistapi25.View;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Calendar;
import android.view.View;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import com.example.to_dolistapi25.Model.Model;
import com.example.to_dolistapi25.Presenter.ItemEditActivityPresenter;
import com.example.to_dolistapi25.R;

public class ItemEditActivity extends AppCompatActivity {

    public EditText itemDescription;
    public EditText itemTitle;
    public EditText itemDate;
    public EditText itemTime;
    public CheckBox itemStatus;
    public Calendar calendar;
    ItemEditActivityPresenter itemEditActivityPresenter = new ItemEditActivityPresenter(this);
    public long value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);

        itemTitle = findViewById(R.id.itemTitle);
        itemDescription = findViewById(R.id.itemDescription);
        itemDate = findViewById(R.id.itemDate);
        itemTime = findViewById(R.id.itemTime);
        itemStatus = findViewById(R.id.itemStatus);

        // Get the Intent that started this activity and extract the itemID
        Intent intent = getIntent();

        value = intent.getLongExtra(Model.EXTRA_MESSAGE, value);
        itemEditActivityPresenter.itemEditActivityStartup(value); //Call the activity startup function to present the item details in the ItemEditActivity
        calendar = Calendar.getInstance(); //Initialize Date and Time pickers

        final DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            // TODO Auto-generated method stub
            calendar.set(Calendar.YEAR, year); //Pick year from the picker
            calendar.set(Calendar.MONTH, month); //Pick month from the picker
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth); //Pick day from the picker
            itemEditActivityPresenter.updateEventDate(); //Send the date picked by the user to updateEventDate() to format it before presenting it in the ItemEditActivity
        };

        final TimePickerDialog.OnTimeSetListener time = (view, hour, minute) -> {
            int am_pm = 0;
            // TODO Auto-generated method stub
            calendar.set(Calendar.HOUR, hour); //Pick hour from the picker
            calendar.set(Calendar.MINUTE, minute); //Pick minute from the picker
            calendar.set(Calendar.AM_PM, am_pm); //Pick am/pm from the picker
            itemEditActivityPresenter.updateEventTime(); //Send the time picked by the user to updateEventTime() to format it before presenting it in the ItemEditActivity
        };

        //Show the Date picker when the user clicks on the itemDate
        itemDate.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            new DatePickerDialog( ItemEditActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        //Show the Time picker when the user clicks on the itemTime
        itemTime.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            new TimePickerDialog( ItemEditActivity.this, time, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false).show();
        });
    }

    /* Called when the user taps the Save button */
    public void saveItem(View view) {
        itemEditActivityPresenter.saveItem(value, this);
    }

    /* Called when the user taps the Cancel button */
    public void cancelItem(View view) {
        itemEditActivityPresenter.cancelItem(this);
    }

    /* Called when the user taps the Delete button */
    public void deleteItem(View view) {
        itemEditActivityPresenter.deleteItem(value, this);
    }
}
