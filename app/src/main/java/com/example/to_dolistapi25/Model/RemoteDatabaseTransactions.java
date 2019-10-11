package com.example.to_dolistapi25.Model;

import java.util.List;

class RemoteDatabaseTransactions {

    void updateRemoteDatabase(ItemDAO dao, List<Item> list) {

        List<Item> listRemoteDesktop = dao.getAll();

        if (listRemoteDesktop == null){
            addItemsToRemoteDatabase(dao, list);
        }
        else if (!list.equals(listRemoteDesktop)){
            checkDifferencesInRemoteDatabase(dao, listRemoteDesktop, list);
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
