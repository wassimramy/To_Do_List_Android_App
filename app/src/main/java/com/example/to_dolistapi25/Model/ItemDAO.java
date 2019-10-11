package com.example.to_dolistapi25.Model;

import java.util.List;
import java.util.Date;
import androidx.room.*;


@Dao
public interface ItemDAO {

    @Query("SELECT * FROM item")
    List<Item> getAll();

    @Query("SELECT * FROM item WHERE iid == :id")
    Item findByID(long id);

    @Query("UPDATE item SET item_title = :itemTitle , item_description = :itemDescription," +
            " item_date_and_time = :itemDateAndTime, item_status = :itemStatus WHERE iid = :id;")
    void updateItem(long id, String itemTitle, String itemDescription,
                    Date itemDateAndTime, boolean itemStatus);

    @Insert
    void insertAll(Item... items);

    @Delete
    void delete(Item item);
}