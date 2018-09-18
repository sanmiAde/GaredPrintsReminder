package com.adetech.garedprintsreminder.ui.group

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.data.database.Order
import com.adetech.garedprintsreminder.data.database.OrderGroupedByDate
import kotlinx.android.synthetic.main.fragment_orders_group.*

class OrderGroupFragment : Fragment(), OrderGroupAdapter.onItemClickhandler {
    override fun onItemClick(date: String?) {
        (activity as Contract).listModelByDate(date)
    }

    private lateinit var orderGroupListViewModel: OrderGroupModel
    private lateinit var recyclerView: RecyclerView
    private val TAG: String = this::class.java.simpleName

    interface  Contract {
        fun addModel(order: Order?)
        fun listModelByDate(date: String?)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_orders_list, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        val adapter: OrderGroupAdapter = setupRecyclerView()
        orderGroupListViewModel.getOrderGroupedByDate().observe(this, Observer { orders: List<OrderGroupedByDate>? ->
            adapter.setOrder(orders)
            Log.d(TAG, orders.toString())
        })

        //Create new order. Null parameter is used to determine if a new order is to be created or a an order is to be created.
        fab.setOnClickListener {
            (activity as Contract).addModel(null)
        }
    }

    private fun initViewModel() {
        orderGroupListViewModel = ViewModelProviders.of(this).get(OrderGroupModel::class.java)

    }

    private fun setupRecyclerView(): OrderGroupAdapter {
        val adapter = OrderGroupAdapter(activity!!, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        return adapter
    }

    companion object {
        fun newInstance(): OrderGroupFragment = OrderGroupFragment()
    }

}