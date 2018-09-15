package com.adetech.garedprintsreminder.ui.group

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.adetech.garedprintsreminder.data.Repository
import com.adetech.garedprintsreminder.data.database.Order
import java.util.*


class OrderGroupListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository.getDatabase(application)

    fun getOrders(): LiveData<Map<Date, Int>>? {

        return Transformations.switchMap(repository.getAllOrders()) { orders: List<Order> -> convertToMap(orders) }
    }

    /**
     * Transforms list to hashmap for order group screen
     * @param order Total order in database
     * @return LiveData<Map<Date, Int>>
     *
     */
    private fun convertToMap(orders: List<Order>): LiveData<Map<Date, Int>> {
        val mutableLiveData = MutableLiveData<Map<Date, Int>>()
        mutableLiveData.value = orders.map { it.dueDate to it.quantity }.toMap()

        return mutableLiveData
    }
}


