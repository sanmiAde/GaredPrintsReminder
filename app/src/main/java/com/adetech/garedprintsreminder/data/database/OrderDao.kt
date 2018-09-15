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

//    @Delete
//    fun deleteAll()

    @Delete
    fun deleteOrder(order: Order)

}