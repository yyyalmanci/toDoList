package com.falanapp.todolist.database

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long): Date {
        return java.sql.Date(timestamp)
    }

    @TypeConverter
    fun toTimeStamp(date: Date): Long {
        return date.time
    }

}
