package com.adetech.garedprintsreminder.ui.group

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.adetech.garedprintsreminder.R
import java.util.*

class OrderGroupAdapter(context: Context) : RecyclerView.Adapter<OrderGroupAdapter.OrderViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var orders: Map<Date, Int>? = emptyMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemview: View = inflater.inflate(R.layout.fragment_list_order_item, parent, false)
        return OrderViewHolder(itemview)
    }

    fun setOrder(order: Map<Date, Int>?) {
        orders = order
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = orders?.size ?: 0

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentOrderDate: Date? = orders?.keys?.toTypedArray()?.get(position)
        val currentOrderQuantity: Int? = orders?.get(currentOrderDate)

        holder.orderQuantity.text = currentOrderQuantity.toString()
        holder.dueDate.text = currentOrderDate.toString()

    }

    class OrderViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val orderQuantity: TextView = itemview.findViewById(R.id.order_quantity_txt)
        val dueDate: TextView = itemview.findViewById(R.id.due_date_txt)
    }
}