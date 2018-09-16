package com.adetech.garedprintsreminder.data

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import com.adetech.garedprintsreminder.AppExecutors
import com.adetech.garedprintsreminder.data.database.Order
import com.adetech.garedprintsreminder.data.database.OrderDao
import com.adetech.garedprintsreminder.data.database.OrderGroupedByDate
import com.adetech.garedprintsreminder.data.database.OrderRoomDatabase

class Repository(application: Application, private val appExecutors: AppExecutors) {
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

    fun getOrder(id: Int) {
        appExecutors.diskIO().execute { orderDao.getOrder(id) }
    }

    fun getOrderGroupedByDate(): LiveData<List<OrderGroupedByDate>> {
        return GetOrdeGroupedByDate(orderDao).doInBackground()
    }

    class GetOrdeGroupedByDate(private val dao: OrderDao) : AsyncTask<Void, Void, LiveData<List<OrderGroupedByDate>>>() {
        public override fun doInBackground(vararg p0: Void?): LiveData<List<OrderGroupedByDate>> {
            return dao.getOrdeGroupByDate()
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