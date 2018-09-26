package com.adetech.garedprintsreminder.data

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.adetech.garedprintsreminder.data.database.*
import com.adetech.garedprintsreminder.utils.AppExecutors

class Repository(application: Application, private val appExecutors: AppExecutors) {
    private val orderDao: OrderDao

    private val allOrders: LiveData<List<Order>>


    init {
        val db: OrderRoomDatabase = OrderRoomDatabase.getDatabase(application)
        orderDao = db.orderDao()
        allOrders = orderDao.getOrders(false) // Query data of the main thread

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

    fun getOrder(id: Int) {
        appExecutors.diskIO().execute { orderDao.getOrder(id) }
    }

    fun deleteOrderByDate(date: String) {
        appExecutors.diskIO().execute { orderDao.deleteOrderBydate(date) }
    }


    fun getOrderGroupedByDate(isCompleted: Boolean): LiveData<List<OrderGroupedByDate>> {
        return GetOrderGroupedByDate(orderDao, isCompleted).doInBackground()
    }

    fun getOrderByDate(date: String, isCompleted: Boolean): LiveData<List<Order>> {
        return GetOrderByDate(orderDao, isCompleted).doInBackground(date)
    }

    fun getNumberOfOrdersByDate(date: String, isCompleted: Boolean): LiveData<Int> {
        return GetNumberOfOrdersByDate(orderDao, isCompleted).doInBackground(date)
    }


    class GetOrderGroupedByDate(private val dao: OrderDao, private val isCompleted: Boolean) : AsyncTask<Void, Void, LiveData<List<OrderGroupedByDate>>>() {
        public override fun doInBackground(vararg p0: Void?): LiveData<List<OrderGroupedByDate>> {
            return dao.getOrderGroupByDate(isCompleted)
        }
    }

    class GetNumberOfOrdersByDate(private val dao: OrderDao, private val isCompleted: Boolean) : AsyncTask<String, Void, LiveData<Int>>() {
        public override fun doInBackground(vararg date: String?): LiveData<Int> {
            return dao.getNumberOfOrderBYDate(date[0]!!, isCompleted)
        }
    }

    class GetOrderByDate(private val dao: OrderDao, private val isCompleted: Boolean) : AsyncTask<String, Void, LiveData<List<Order>>>() {
        public override fun doInBackground(vararg date: String?): LiveData<List<Order>> {
            return dao.getOrderByDate(date[0]!!, isCompleted)
        }
    }



    companion object {
        private var instance: Repository? = null

        @Synchronized
        fun getDatabase(application: Application): Repository {
            if (instance == null) {
                val appExecutors: AppExecutors = AppExecutors.getRepository()
                instance = Repository(application, appExecutors)
            }
            return instance!!
        }
    }
}