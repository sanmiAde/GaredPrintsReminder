package com.adetech.garedprintsreminder.data



import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 *
 * @param name : Customer name
 * @param orderQuantity ; Job quantity
 * @param dueDate : Due date
 * @param price Price od job
 *
 */
@Entity(tableName = "order_table")
data class Order(
        @PrimaryKey(autoGenerate = true)
        val id: UUID,
        val name: String,
        val orderQuantity: Int,
        val price: Double,
        val dueDate: Date)
