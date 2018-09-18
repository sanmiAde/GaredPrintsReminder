package com.adetech.garedprintsreminder.ui.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.data.database.Order

class OrderListAdapter(context: Context) : RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var orders: List<Order>? = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView: View = inflater.inflate(R.layout.fragment_list_item, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun getItemCount(): Int = orders?.size ?: 0

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.name.text = orders?.get(position)?.name.toString()
        holder.qauntity.text = orders?.get(position)?.quantity.toString()
    }

    fun setOrder(order: List<Order>?) {
        orders = order
        notifyDataSetChanged()
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_txt)
        val qauntity: TextView = itemView.findViewById(R.id.order_quantity_txt)
    }
}