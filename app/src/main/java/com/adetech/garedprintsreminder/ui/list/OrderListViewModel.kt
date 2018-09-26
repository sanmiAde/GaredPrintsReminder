package com.adetech.garedprintsreminder.ui.list

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.adetech.garedprintsreminder.data.Repository
import com.adetech.garedprintsreminder.data.database.Order

class OrderListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository.getDatabase(application)

    fun getOrdersByDate(date: String, isCompleted: Boolean): LiveData<List<Order>> {
        return repository.getOrderByDate(date, isCompleted)
    }

    fun completeOrder(order: Order): Unit {
        repository.updateOrder(order)
    }

}