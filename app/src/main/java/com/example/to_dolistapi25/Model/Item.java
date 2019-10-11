package com.example.to_dolistapi25.Model;

import androidx.room.*;

import java.util.Date;


@Entity
public class Item {

    public Item(long iid, String itemTitle, String itemDescription, Date itemDateAndTime, boolean itemStatus){
        this.iid = iid;
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemDateAndTime = itemDateAndTime;
        this.itemStatus = itemStatus;
    }

    @PrimaryKey
    public long iid;

    @ColumnInfo(name = "item_title")
    public String itemTitle;

    @ColumnInfo(name = "item_description")
    public String itemDescription;

    @ColumnInfo(name = "item_date_and_time")
    public Date itemDateAndTime;

    @ColumnInfo(name = "item_status")
    public boolean itemStatus;
}