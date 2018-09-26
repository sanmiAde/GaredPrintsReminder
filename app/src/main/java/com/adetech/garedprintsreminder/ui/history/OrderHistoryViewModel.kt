package com.adetech.garedprintsreminder.ui.history

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.adetech.garedprintsreminder.data.Repository

import com.adetech.garedprintsreminder.data.database.OrderGroupedByDate

class OrderHistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository.getDatabase(application)

    fun getOrderHistory(): LiveData<List<OrderGroupedByDate>> {
        return repository.getOrderGroupedByDate(true)
    }

}