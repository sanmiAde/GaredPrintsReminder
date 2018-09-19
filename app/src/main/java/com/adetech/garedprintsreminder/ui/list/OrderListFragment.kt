package com.adetech.garedprintsreminder.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.data.database.Order


class OrderListFragment : Fragment(), OrderListAdapter.OnLongClickHandler {

    private lateinit var orderListViewModel: OrderListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var date: String
    private val TAG: String = this::class.java.simpleName

    override fun onItemLongClick(order: Order) {
        val alertDialogBuilder = AlertDialog.Builder(activity!!)

        alertDialogBuilder.setTitle("Order completed")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ -> orderListViewModel.completeOrder(order) }.setNegativeButton("No") { dialogInterface: DialogInterface?, _: Int -> dialogInterface?.cancel() }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_orders_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)
        val adapter: OrderListAdapter = setupRecyclerView()

        orderListViewModel.getOrdersByDate(date).observe(this, Observer { orders: List<Order>? ->
            adapter.setOrder(orders)
            Log.d(TAG, orders.toString())
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        date = arguments!!.getString(ARG_ID)
        initViewModel()
    }

    private fun initViewModel() {
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel::class.java)

    }

    private fun setupRecyclerView(): OrderListAdapter {
        val adapter = OrderListAdapter(activity!!, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        return adapter
    }

    companion object {
        private const val ARG_ID: String = "uuid"
        fun newInstance(date: String?): OrderListFragment {
            val result = OrderListFragment()
            val args = Bundle()
            args.putSerializable(ARG_ID, date)
            result.arguments = args
            return result
        }
    }
}