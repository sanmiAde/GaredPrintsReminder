package com.adetech.garedprintsreminder.ui.history

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.data.database.OrderGroupedByDate


class OrderHistoryAdapter(context: Context) : RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var orders: List<OrderGroupedByDate>? = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view: View = inflater.inflate(R.layout.fragment_history_item, parent, false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int = orders?.size ?: 0

    fun setOrder(order: List<OrderGroupedByDate>?) {
        orders = order
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.orderQuantity.text = orders?.get(position)?.quantityGroup.toString()
        holder.dueDate.text = orders?.get(position)?.dueDate
        holder.price.text = orders?.get(position)?.totalPrice.toString()

    }

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderQuantity: TextView = view.findViewById(R.id.quantityTxt)
        val price: TextView = view.findViewById(R.id.priceTxt)
        val dueDate: TextView = view.findViewById(R.id.due_date_txt)

    }
}