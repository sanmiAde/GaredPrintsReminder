package com.adetech.garedprintsreminder.data.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import java.util.*


@Dao
interface OrderDao {

    @Query("SELECT * FROM order_table WHERE isCompleted =:isCompleted ORDER BY id ASC")
    fun getOrders(isCompleted: Boolean): LiveData<List<Order>>

    @Query("SELECT * FROM order_table WHERE id =:id")
    fun getOrder(id: Int): Order

    @Insert
    fun insert(vararg order: Order)

    @Update(onConflict=OnConflictStrategy.ROLLBACK)
    fun update(order: Order)

    @Query("DELETE fROM order_table WHERE dueDate =:date")
    fun deleteOrderBydate(date: String)

    @Delete
    fun deleteOrder(order: Order)

    @Query("SELECT dueDate, COUNT(quantity) as quantityGroup , SUM(totalPrice) as totalPrice FROM order_table WHERE isCompleted =:isCompleted  GROUP BY dueDate ORDER BY dateStamp ASC")
    fun getOrderGroupByDate(isCompleted: Boolean): LiveData<List<OrderGroupedByDate>>

    @Query("SELECT * FROM order_table WHERE dueDate =:dueDate AND isCompleted =:isCompleted ORDER BY name COLLATE NOCASE ASC")
    fun getOrderByDate(dueDate: String, isCompleted: Boolean): LiveData<List<Order>>

    @Query("SELECT COUNT(id) from order_table WHERE dueDate =:date AND isCompleted=:isCompleted ")
    fun getNumberOfOrderBYDate(date: String, isCompleted: Boolean): LiveData<Int>
}