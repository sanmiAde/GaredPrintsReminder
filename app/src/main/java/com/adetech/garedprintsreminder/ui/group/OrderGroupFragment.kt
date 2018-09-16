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
import kotlinx.android.synthetic.main.actitvity_recyclerview.*
import kotlinx.android.synthetic.main.fragment_list_orders_group.*
import java.util.*

class OrderGroupFragment : Fragment() {

    private lateinit var orderGroupListViewModel: OrderGroupModel
    private lateinit var recyclerView: RecyclerView
    private val orderGroupFragment: String = "orderGroupFragment"

    interface  Contract {
        fun addModel(order: Order?)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_list_orders_group, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter: OrderGroupAdapter = setupRecyclerView()

        orderGroupListViewModel.getOrderGroupedByDate().observe(activity!!, Observer { it ->
            Log.d(orderGroupFragment, it.toString())
        })

        //Create new order. Null parameter is used to determine if a new order is to be created or a an order is to be created.
        fab.setOnClickListener {
            (activity as Contract).addModel(null)
        }
    }

    private fun initViewModel() {
        orderGroupListViewModel = ViewModelProviders.of(activity!!).get(OrderGroupModel::class.java)

    }

    private fun setupRecyclerView(): OrderGroupAdapter {
        val adapter = OrderGroupAdapter(activity!!)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity!!)

        return adapter
    }

    companion object {
        fun newInstance(): OrderGroupFragment = OrderGroupFragment()
    }

}