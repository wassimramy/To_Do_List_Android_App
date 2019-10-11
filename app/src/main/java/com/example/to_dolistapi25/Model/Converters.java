package com.example.to_dolistapi25.Model;

import androidx.room.TypeConverter;
import java.util.Date;

//Converters class to convert some datatypes not supported by either Java or SQL
class Converters {

    //Convert Long type to Date
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    //Convert Date type to Long
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}