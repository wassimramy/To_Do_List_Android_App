package com.example.to_dolistapi25.Model;

import java.util.List;

class RemoteDatabaseTransactions {

    //Called to start updating remote database
    void updateRemoteDatabase(ItemDAO dao, List<Item> list) {

        List<Item> listRemoteDatabase = dao.getAll();

        //Check whether there any records in the database or not
        if (listRemoteDatabase == null){
            addItemsToRemoteDatabase(dao, list); //If not, add the locally saved items to the remote database
        }
        //Check whether both local and remote databases share the same records or not
        else if (!list.equals(listRemoteDatabase)){
            checkDifferencesInRemoteDatabase(dao, listRemoteDatabase, list); //If not, check the differences
        }
    }

    //Called to check the differences between remote and local databases
    private void checkDifferencesInRemoteDatabase(ItemDAO dao, List<Item> listRemoteDesktop, List<Item> list) {
        deleteItemsFromRemoteDatabase (dao, listRemoteDesktop, list); //Delete the items from the remote database that does not exist in the local database
        addOrEditAnItemInRemoteDatabase(dao, list); //Add or edit an item to the remote database
    }

    //Called to check if an item exist in the remote database
    private void addOrEditAnItemInRemoteDatabase(ItemDAO dao, List<Item> list) {
        for (int i = 0 ; i < list.size(); i++){
            Item returnedItem = dao.findByID(list.get(i).iid);
            if (returnedItem == null){
                dao.insertAll(list.get(i)); //If not, insert the new item to the remote database
            }
            else {
                dao.updateItem(list.get(i).iid, list.get(i).itemTitle, list.get(i).itemDescription,
                        list.get(i).itemDateAndTime, list.get(i).itemStatus); //If yes, update all the values of a given item
            }
        }
    }

    //Called if there is no records in the database
    private void addItemsToRemoteDatabase(ItemDAO dao, List<Item> list) {
        for (int i = 0 ; i < list.size(); i++){
            dao.insertAll(list.get(i));
        }
    }

    //Called to delete an item from the remote database if it is not present in the local database
    private void deleteItemsFromRemoteDatabase(ItemDAO dao,List<Item> listRemoteDatabase, List<Item> list) {
        int found = 0;
        for (int i = 0 ; i < listRemoteDatabase.size(); i++){
            for (int j = 0 ; j < list.size(); j++){
                if (listRemoteDatabase.get(i).iid == list.get(j).iid ){
                    found = 1;
                }
            }
            if (found == 0){
                dao.delete(listRemoteDatabase.get(i));
            } else {
                found = 0;
            }
        }
    }
}
