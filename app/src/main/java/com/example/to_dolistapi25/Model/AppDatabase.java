package com.example.to_dolistapi25.Model;

import androidx.room.*;

@Database(entities = {Item.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDAO itemDAO();
}
