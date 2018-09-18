package com.adetech.garedprintsreminder.ui.group

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.data.database.OrderGroupedByDate

class OrderGroupAdapter(context: Context, val clickhandler: onItemClickhandler) : RecyclerView.Adapter<OrderGroupAdapter.OrderViewHolder>() {

    interface onItemClickhandler {
        fun onItemClick(date: String?)
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var orders: List<OrderGroupedByDate>? = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemViev: View = inflater.inflate(R.layout.fragment_order_item, parent, false)
        return OrderViewHolder(itemViev)
    }

    fun setOrder(order: List<OrderGroupedByDate>?) {
        orders = order
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = orders?.size ?: 0

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {

        holder.orderQuantity.text = orders?.get(position)?.quantityGroup.toString()
        holder.dueDate.text = orders?.get(position)?.dueDate.toString()
    }


    inner class OrderViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview), View.OnClickListener {
        override fun onClick(view: View?) {
            val position: Int = adapterPosition
            val date: String? = orders?.get(position)?.dueDate

            clickhandler.onItemClick(date)
        }


        init {
            itemView.setOnClickListener(this)
        }
        val orderQuantity: TextView = itemview.findViewById(R.id.order_quantity_txt)
        val dueDate: TextView = itemview.findViewById(R.id.due_date_txt)
    }
}