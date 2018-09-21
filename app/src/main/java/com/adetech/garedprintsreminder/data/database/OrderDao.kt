package com.adetech.garedprintsreminder.data.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import java.util.*


@Dao
interface OrderDao {

    @Query("SELECT * FROM order_table ORDER BY id ASC")
    fun getOrders(): LiveData<List<Order>>

    @Query("SELECT * FROM order_table WHERE id =:id")
    fun getOrder(id: Int): Order

    @Insert
    fun insert(order: Order)

    @Update(onConflict=OnConflictStrategy.ROLLBACK)
    fun update(order: Order)

    @Query("DELETE fROM order_table WHERE dueDate =:date")
    fun deleteOrderBydate(date: String)

    @Delete
    fun deleteOrder(order: Order)

    @Query("SELECT dueDate, COUNT(quantity) as quantityGroup FROM order_table GROUP BY dueDate ORDER BY dateStamp ASC")
    fun getOrderGroupByDate(): LiveData<List<OrderGroupedByDate>>

    @Query("SELECT * FROM order_table WHERE dueDate =:dueDate ORDER BY name COLLATE NOCASE ASC")
    fun getOrderByDate(dueDate: String): LiveData<List<Order>>

}