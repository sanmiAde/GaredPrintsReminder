package com.adetech.garedprintsreminder.ui.AddOrder

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.adetech.garedprintsreminder.data.Repository
import com.adetech.garedprintsreminder.data.database.Order
import java.util.*

class AddOrderViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository.getDatabase(application)

    fun insertOrder(order: Order) {
        repository.insertOrder(order)
    }

    fun updateOrder(order: Order) {
        repository.updateOrder(order)
    }

    fun getOrder(id: UUID) {
        repository.getOrder(id)
    }


}