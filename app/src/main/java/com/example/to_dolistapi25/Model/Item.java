package com.example.to_dolistapi25.Model;

import androidx.room.*;

import java.util.Date;


@Entity
public class Item {

    //Item constructor
    public Item(long iid, String itemTitle, String itemDescription, Date itemDateAndTime, boolean itemStatus){
        this.iid = iid;
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemDateAndTime = itemDateAndTime;
        this.itemStatus = itemStatus;
    }

    //The primary key of Item is long to store the time stamp when item is created
    //iid is not auto generated for easier synchronization between local and remote databases
    @PrimaryKey
    public long iid;

    //Store itemTitle attribute value in item_value column
    @ColumnInfo(name = "item_title")
    public String itemTitle;

    //Store itemDescription attribute value in item_description column
    @ColumnInfo(name = "item_description")
    public String itemDescription;

    //Store itemDateAndTime attribute value in item_date_and_time column
    @ColumnInfo(name = "item_date_and_time")
    public Date itemDateAndTime;

    //Store itemStatus attribute value in item_status column
    @ColumnInfo(name = "item_status")
    public boolean itemStatus;
}