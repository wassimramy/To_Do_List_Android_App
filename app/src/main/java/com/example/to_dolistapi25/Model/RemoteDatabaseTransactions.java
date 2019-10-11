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

    private void checkDifferencesInRemoteDatabase(ItemDAO dao, List<Item> listRemoteDesktop, List<Item> list) {
        deleteItemsFromRemoteDatabase (dao, listRemoteDesktop, list);
        addOrEditAnItemInRemoteDatabase(dao, list);
    }

    private void addOrEditAnItemInRemoteDatabase(ItemDAO dao, List<Item> list) {
        for (int i = 0 ; i < list.size(); i++){
            Item returnedItem = dao.findByID(list.get(i).iid);
            if (returnedItem == null){
                dao.insertAll(list.get(i));
            }
            else {
                dao.updateItem(list.get(i).iid, list.get(i).itemTitle, list.get(i).itemDescription,
                        list.get(i).itemDateAndTime, list.get(i).itemStatus);
            }
        }
    }

    private void addItemsToRemoteDatabase(ItemDAO dao, List<Item> list) {
        for (int i = 0 ; i < list.size(); i++){
            dao.insertAll(list.get(i));
        }
    }

    private void deleteItemsFromRemoteDatabase(ItemDAO dao,List<Item> listRemoteDesktop, List<Item> list) {
        int found = 0;
        for (int i = 0 ; i < listRemoteDesktop.size(); i++){
            for (int j = 0 ; j < list.size(); j++){
                if (listRemoteDesktop.get(i).iid == list.get(j).iid ){
                    found = 1;
                }
            }
            if (found == 0){
                dao.delete(listRemoteDesktop.get(i));
            } else {
                found = 0;
            }
        }
    }
}
