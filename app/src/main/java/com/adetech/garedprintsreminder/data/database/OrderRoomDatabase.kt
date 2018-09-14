package com.adetech.garedprintsreminder.data.database

import android.arch.persistence.room.*
import android.content.Context

@Database(entities = [Order::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class OrderRoomDatabase: RoomDatabase() {
    abstract  fun orderDao(): OrderDao


    companion object {
        private var  instance : OrderRoomDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): OrderRoomDatabase {
            if(instance == null){
                instance = Room.databaseBuilder(context.applicationContext, OrderRoomDatabase::class.java, "word_database").build()
            }
            return  instance!!
        }
    }
}