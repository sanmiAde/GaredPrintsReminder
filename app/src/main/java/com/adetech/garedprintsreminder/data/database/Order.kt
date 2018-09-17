package com.adetech.garedprintsreminder.data.database


import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 *
 * @param name : Customer name
 * @param uuid: Unique uuid of customer
 * @param quantity ; Job quantity
 * @param dueDate : Due date
 * @param price Price od job
 *
 */
@Entity(tableName = "order_table")
data class Order(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val name: String,
        val quantity: Int,
        val totalPrice: Double = 0.0,
        val dueDate: String)

data class OrderGroupedByDate(val quantityGroup: Int, val dueDate: String)

