package com.adetech.garedprintsreminder.ui.group

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.data.database.Order
import kotlinx.android.synthetic.main.fragment_list_orders.*
import java.util.*

class OrderGroupListFragment : Fragment() {

    private lateinit var orderGroupListViewModel: OrderGroupListViewModel
    private val orderGroupFragment: String = "orderGroupFragment"

    interface  Contract {
        fun addModel(order: Order?)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_list_orders, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderGroupListViewModel.getOrders()?.observe(activity!!, Observer { order: Map<Date, Int>? ->
            text.text = order?.toString()
            Log.d(orderGroupFragment, order.toString())
        })
        //Create new order. Null parameter is used to determine if a new order is to be created or a an order is to be created.
        fab.setOnClickListener {
            (activity as Contract).addModel(null)
        }
    }

    private fun initViewModel() {
        orderGroupListViewModel = ViewModelProviders.of(activity!!).get(OrderGroupListViewModel::class.java)

    }

    companion object {
        fun newInstance(): OrderGroupListFragment = OrderGroupListFragment()
    }

}