package com.adetech.garedprintsreminder.ui.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.data.database.Order

class OrderListAdapter(context: Context, val longClickHandler: OnLongClickHandler, val clickHanlder: OnItemClickHandler) : RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>() {

    interface OnLongClickHandler {
        fun onItemLongClick(order: Order)
    }

    interface OnItemClickHandler {
        fun onClick(order: Order)
    }

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

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener, View.OnClickListener {

        init {
            itemView.setOnLongClickListener(this)
            itemView.setOnClickListener(this)
        }
        val name: TextView = itemView.findViewById(R.id.name_txt)
        val qauntity: TextView = itemView.findViewById(R.id.order_quantity_txt)

        override fun onLongClick(p0: View?): Boolean {
            val order: Order = orders!![adapterPosition]
            longClickHandler.onItemLongClick(order)
            return true
        }

        override fun onClick(p0: View?) {
            val order: Order = orders!![adapterPosition]
            clickHanlder.onClick(order)
        }

    }
}