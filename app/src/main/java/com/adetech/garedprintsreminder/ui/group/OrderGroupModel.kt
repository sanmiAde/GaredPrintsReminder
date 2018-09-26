package com.adetech.garedprintsreminder.ui.group

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.adetech.garedprintsreminder.data.Repository
import com.adetech.garedprintsreminder.data.database.Order
import com.adetech.garedprintsreminder.data.database.OrderGroupedByDate
import java.util.*


class OrderGroupModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository.getDatabase(application)

    /**
     * Gets order grouped by date.
     * @return Livedata containing list of orderGroupedByDate objects.
     */
    fun getOrderGroupedByDate(isCompleted: Boolean): LiveData<List<OrderGroupedByDate>> {
        return repository.getOrderGroupedByDate(isCompleted)
    }

//    fun deleteOrderByDate(date: String) {
//        repository.deleteOrderByDate(date)
//    }


}


