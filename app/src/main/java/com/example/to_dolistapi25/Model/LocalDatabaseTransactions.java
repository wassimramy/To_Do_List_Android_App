package com.example.to_dolistapi25.Model;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class LocalDatabaseTransactions {

    // Called to update the local database to reflect the changes in the list
    void updateLocalDatabase(Context context, List<Item> list) {
        String fileName = "TodoDB.txt";
        String text;
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE); // Wipe the old "TodoDB.txt" and print the data in the file
            for (int i = 0 ; i < list.size() ; i++){
                text = "id: " + list.get(i).iid + ", item_title: " + list.get(i).itemTitle +
                        ", item_description: " + list.get(i).itemDescription +
                        ", item_date_and_time: " + list.get(i).itemDateAndTime +
                        ", item_status: " + list.get(i).itemStatus + "\n";
                fos.write(text.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Called to populate the list from the local database
    StringBuilder readLocalDatabase(Context context) {
        String fileName = "TodoDB.txt";
        FileInputStream fis = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            stringBuilder = new StringBuilder();
            String text;

            // Append each line to stringBuilder
            while ((text = br.readLine()) != null) {
                stringBuilder.append(text).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder;
    }

    // Called to populate list from the local database
    void populateListFromLocalDatabase(StringBuilder stringBuilder , List<Item> list) {
        parseItemRecord(stringBuilder, list); // populate the list using the data in the stringBuilder
    }

    // Called to parse attributes value to populate the list
    private void parseItemRecord (StringBuilder stringBuilder , List<Item> list){
        int start = 0, end;

        // Break the items and send it to parseItemAttributes() to extract the values
        for (int i = 0 ; i < stringBuilder.length(); i++){
            if (stringBuilder.charAt(i) == '\n'){
                end = i;
                parseItemAttributes(stringBuilder.substring(start, end) , list);
                start = end + 1;
            }
        }
    }

    // Called to extract the values of each item and assign it to the list
    private void parseItemAttributes (String itemRecord , List<Item> list){

        // Extract the id value
        String startAttribute = "id: ";
        String endAttribute = ", item_title: ";
        int start = itemRecord.indexOf(startAttribute);
        int end = itemRecord.indexOf(endAttribute);
        String attributeValue = itemRecord.substring(start + startAttribute.length(), end);
        long id = Long.parseLong(attributeValue);

        // Extract the itemTitle
        startAttribute = endAttribute;
        endAttribute = ", item_description: ";
        start = itemRecord.indexOf(startAttribute);
        end = itemRecord.indexOf(endAttribute);
        attributeValue = itemRecord.substring(start + startAttribute.length(), end);
        String itemTitle = attributeValue;

        // Extract the itemDescription
        startAttribute = endAttribute;
        endAttribute = ", item_date_and_time: ";
        start = itemRecord.indexOf(startAttribute);
        end = itemRecord.indexOf(endAttribute);
        attributeValue = itemRecord.substring(start + startAttribute.length(), end);
        String itemDescription = attributeValue;

        // Extract the itemDateAndTime
        startAttribute = endAttribute;
        endAttribute = ", item_status: ";
        start = itemRecord.indexOf(startAttribute);
        end = itemRecord.indexOf(endAttribute);
        attributeValue = itemRecord.substring(start + startAttribute.length(), end);
        SimpleDateFormat myFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.US);
        Date itemDateAndTime = null;
        try {
            itemDateAndTime = myFormat.parse(attributeValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Extract the itemStatus
        startAttribute = endAttribute;
        start = itemRecord.indexOf(startAttribute);
        attributeValue = itemRecord.substring(start + startAttribute.length());
        boolean itemStatus = Boolean.parseBoolean(attributeValue);

        // Add the extracted item to the list
        list.add(new Item (id, itemTitle, itemDescription, itemDateAndTime, itemStatus));
    }
}
