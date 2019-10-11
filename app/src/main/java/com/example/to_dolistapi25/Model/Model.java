package com.example.to_dolistapi25.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.DAY_OF_YEAR;


public class Model {

    public static List<Item> list;
    static List<Notification> notificationsList;
    public static final String EXTRA_MESSAGE = "0";
    public Item item = null;
    private LocalDatabaseTransactions localDatabaseTransactions = new LocalDatabaseTransactions();
    private RemoteDatabaseTransactions remoteDatabaseTransactions = new RemoteDatabaseTransactions();
    private ItemManipulation itemManipulation = new ItemManipulation();

    private void checkDues(Context context) {
        for (int i = 0 ; i < list.size() ; i++){
            if (list.get(i).itemDateAndTime.after(Notification.getTodaysDate()) && !list.get(i).itemStatus){
                Notification notification = new Notification(list.get(i).iid, list.get(i).itemTitle,
                        list.get(i).itemDescription, list.get(i).itemDateAndTime.getTime(), i, context);
                notification.setAlarm();
                notificationsList.add(notification);
            }
            else{
                Notification notification = new Notification(list.get(i).iid, list.get(i).itemTitle,
                        list.get(i).itemDescription, list.get(i).itemDateAndTime.getTime(), i, context);
                notificationsList.add(notification);
            }
        }
    }

    private void disableAlarms() {
        for (int i = 0 ; i < notificationsList.size() ; i++){
            notificationsList.get(i).disableNotifications();
        }
    }

    public void mainActivityStartup (Context context){
        if (notificationsList != null){
            disableAlarms();
        }
        list = new ArrayList<>();
        notificationsList = new ArrayList<>();
        localDatabaseTransactions.populateListFromLocalDatabase(localDatabaseTransactions.readLocalDatabase(context), list);
        checkDues(context);
    }

    public Item itemEditActivityStartup (long value){
        if (value == 0){
            item = new Item(0, "New Item Title", "New Item Description",
                    getTomorrowsDate(), false);
        }
        else {
            searchItemFromLocalDatabase(value);
        }
        return item;
    }

    private Date getTomorrowsDate (){
        // get a calendar instance, which defaults to "now"
        Calendar calendar = Calendar.getInstance();
        // add one day to the date/calendar
        calendar.add(DAY_OF_YEAR, 1);
        // now get "tomorrow"
        return calendar.getTime();
    }

    public void updateRemoteDatabase(ItemDAO dao) {
        new Thread(() -> remoteDatabaseTransactions.updateRemoteDatabase(dao, list)).start();
    }

    private void updateLocalDatabase(Context context) {
        localDatabaseTransactions.updateLocalDatabase(context, list);
    }

    public void deleteItem (long value, Context context){
        itemManipulation.deleteItem(value, context, item, list);
        updateLocalDatabase(context);
    }

    public void saveItem (long value, Context context){

        itemManipulation.saveItem(value, item, list);
        updateLocalDatabase(context);
    }

    private void searchItemFromLocalDatabase(long value) {
        item = itemManipulation.searchItemFromLocalDatabase(value, list);
    }
}
