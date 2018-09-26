package com.adetech.garedprintsreminder.ui.add

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.adetech.garedprintsreminder.data.Repository
import com.adetech.garedprintsreminder.data.database.Order


class AddOrderViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository.getDatabase(application)

    fun insertOrder(order: Order) {
        repository.insertOrder(order)
    }

    fun updateOrder(order: Order) {
        repository.updateOrder(order)
    }

    fun getOrder(id: Int) {
        repository.getOrder(id)
    }

//    fun getNumberOfOrdersByDate(date: String): LiveData<Int> {
//        return repository.getNumberOfOrdersByDate(date)
//    }

}