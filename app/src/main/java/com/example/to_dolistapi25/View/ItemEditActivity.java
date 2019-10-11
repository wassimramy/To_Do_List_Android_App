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
    public Calendar myCalendar;
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

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        value = intent.getLongExtra(Model.EXTRA_MESSAGE, value);
        itemEditActivityPresenter.itemEditActivityStartup(value);
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            itemEditActivityPresenter.updateEventDate();
        };

        final TimePickerDialog.OnTimeSetListener time = (view, hour, minute) -> {
            int am_pm = 0;
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.HOUR, hour);
            myCalendar.set(Calendar.MINUTE, minute);
            myCalendar.set(Calendar.AM_PM, am_pm);
            itemEditActivityPresenter.updateEventTime();
        };


        itemDate.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            new DatePickerDialog( ItemEditActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        itemTime.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            new TimePickerDialog( ItemEditActivity.this, time, myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), false).show();
        });
    }
    public void saveItem(View view) {
        itemEditActivityPresenter.saveItem(value, this);
    }
    public void cancelItem(View view) {
        itemEditActivityPresenter.cancelItem(this);
    }

    public void deleteItem(View view) {
        itemEditActivityPresenter.deleteItem(value, this);
    }
}
