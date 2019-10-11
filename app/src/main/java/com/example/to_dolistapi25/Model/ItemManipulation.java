package com.example.to_dolistapi25.Model;

import android.content.Context;


import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


class ItemManipulation {

    private static final AtomicLong LAST_TIME_MS = new AtomicLong();

    private static long createUniqueTimeStamp() {
        long now = System.currentTimeMillis();
        while(true) {
            long lastTime = LAST_TIME_MS.get();
            if (lastTime >= now)
                now = lastTime+1;
            if (LAST_TIME_MS.compareAndSet(lastTime, now))
                return now;
        }
    }

    void saveItem (long value, Item item, List<Item> list){
        if (value == 0){
            item.iid = createUniqueTimeStamp();
            list.add(item);
        }
        else {
            updateItemInList(item, list);
        }
    }

    private void updateItemInList(Item item, List<Item> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).iid == item.iid) {
                list.get(i).itemTitle = item.itemTitle;
                list.get(i).itemDescription = item.itemDescription;
                list.get(i).itemDateAndTime = item.itemDateAndTime;
                list.get(i).itemStatus = item.itemStatus;
            }
        }
    }

    void deleteItem(long value, Context context, Item item, List<Item> list){
        if (value != 0) {
            list.remove(item);
        }
    }

    Item searchItemFromLocalDatabase(long value, List<Item> list) {
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).iid == value){
                return list.get(i);
            }
        }
        return null;
    }
}
