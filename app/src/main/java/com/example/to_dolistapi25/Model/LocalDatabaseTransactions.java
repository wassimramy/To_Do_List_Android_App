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

    void updateLocalDatabase(Context context, List<Item> list) {
        String fileName = "TodoDB.txt";
        String text;
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
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

    void populateListFromLocalDatabase(StringBuilder stringBuilder , List<Item> list) {
        parseItemRecord(stringBuilder, list);
    }

    private void parseItemRecord (StringBuilder stringBuilder , List<Item> list){
        int start = 0, end;
        for (int i = 0 ; i < stringBuilder.length(); i++){
            if (stringBuilder.charAt(i) == '\n'){
                end = i;
                parseItemAttributes(stringBuilder.substring(start, end) , list);
                start = end + 1;
            }
        }
    }

    private void parseItemAttributes (String itemRecord , List<Item> list){

        String startAttribute = "id: ";
        String endAttribute = ", item_title: ";
        int start = itemRecord.indexOf(startAttribute);
        int end = itemRecord.indexOf(endAttribute);
        String attributeValue = itemRecord.substring(start + startAttribute.length(), end);
        long id = Long.parseLong(attributeValue);

        startAttribute = endAttribute;
        endAttribute = ", item_description: ";
        start = itemRecord.indexOf(startAttribute);
        end = itemRecord.indexOf(endAttribute);
        attributeValue = itemRecord.substring(start + startAttribute.length(), end);
        String itemTitle = attributeValue;

        startAttribute = endAttribute;
        endAttribute = ", item_date_and_time: ";
        start = itemRecord.indexOf(startAttribute);
        end = itemRecord.indexOf(endAttribute);
        attributeValue = itemRecord.substring(start + startAttribute.length(), end);
        String itemDescription = attributeValue;

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

        startAttribute = endAttribute;
        start = itemRecord.indexOf(startAttribute);
        attributeValue = itemRecord.substring(start + startAttribute.length());
        boolean itemStatus = Boolean.parseBoolean(attributeValue);

        list.add(new Item (id, itemTitle, itemDescription, itemDateAndTime, itemStatus));
    }
}
