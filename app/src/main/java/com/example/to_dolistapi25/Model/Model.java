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

    // Called to populate the notificationsList
    private void checkDues(Context context) {

        // Go through all the items list and check the due date
        for (int i = 0 ; i < list.size() ; i++){

            Notification notification = new Notification(list.get(i).iid, list.get(i).itemTitle,
                    list.get(i).itemDescription, list.get(i).itemDateAndTime.getTime(), i, context);

            // If we still behind the due, set an Alarm.
            if (list.get(i).itemDateAndTime.after(Notification.getTodaysDate()) && !list.get(i).itemStatus){
                notification.setAlarm();
            }
            notificationsList.add(notification);
        }
    }

    // Called when starting populating new Items to disable all previous alarms and reset them according to the new item list later
    private void disableAlarms() {
        for (int i = 0 ; i < notificationsList.size() ; i++){
            notificationsList.get(i).disableNotifications();
        }
    }

    // Called when starting the MainActivity
    public void mainActivityStartup (Context context){

        // Check whether
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
        itemManipulation.deleteItem(value, item, list);
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
