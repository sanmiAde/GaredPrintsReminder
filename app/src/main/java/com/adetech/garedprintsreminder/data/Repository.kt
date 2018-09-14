package com.adetech.garedprintsreminder.data

import android.app.Application
import android.arch.lifecycle.LiveData
import com.adetech.garedprintsreminder.AppExecutors
import com.adetech.garedprintsreminder.data.database.Order
import com.adetech.garedprintsreminder.data.database.OrderDao
import com.adetech.garedprintsreminder.data.database.OrderRoomDatabase

class Repository(private val application: Application, private val appExecutors: AppExecutors) {
    private val orderDao: OrderDao
    private val allOrders: LiveData<List<Order>>

    init {
        val db: OrderRoomDatabase = OrderRoomDatabase.getDatabase(application)
        orderDao = db.orderDao()
        allOrders = orderDao.getOrders() // Query data of the main thread
    }

    /**
     * Get all orders
     */
    fun getAllOrders(): LiveData<List<Order>> = allOrders

    fun insertOrder(order: Order) {
        appExecutors.diskIO().execute { orderDao.insert(order) }
    }

    fun updateOrder(order: Order) {
        appExecutors.diskIO().execute { orderDao.update(order) }
    }

    fun deleteOrder(order: Order) {
        appExecutors.diskIO().execute { orderDao.deleteOrder(order) }
    }

//    companion object {
//        private var  instance : OrderRoomDatabase? = null
//
//        @Synchronized
//        fun getDatabase(context: Context): OrderRoomDatabase {
//            if(instance == null){
//                instance = Room.databaseBuilder(context.applicationContext, OrderRoomDatabase::class.java, "word_database").build()
//            }
//            return  instance!!
//        }
//    }
}