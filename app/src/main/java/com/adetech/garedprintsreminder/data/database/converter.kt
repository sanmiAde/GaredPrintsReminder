package com.adetech.garedprintsreminder.data.database

import android.arch.persistence.room.TypeConverter
import java.util.*

object DateConverter {

    @TypeConverter
    @JvmStatic
    fun fromTimeStamp(value: Long?): Date? {
        return if(value == null) null else Date(value)
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): Long?{
        return date?.time
    }
}

object UUIDConverter {
    @TypeConverter
    @JvmStatic
    fun fromUUIDToString(value: UUID?): String? {
        return value?.toString()
    }

    @TypeConverter
    @JvmStatic
    fun fromStringToUUID(value: String?): UUID? {
        return UUID.fromString(value)
    }
}
