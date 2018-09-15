package com.adetech.garedprintsreminder.ui.orderGroup

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.adetech.garedprintsreminder.data.Repository
import com.adetech.garedprintsreminder.data.database.Order


class OrderGroupListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository.getDatabase(application)

    fun getOrders(): LiveData<List<Order>> {
        return repository.getAllOrders()
    }
}