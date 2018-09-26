package com.adetech.garedprintsreminder.ui.list

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.data.database.Order
import com.adetech.garedprintsreminder.ui.OrderActivity
import com.adetech.garedprintsreminder.utils.completeOrderDialog
import kotlinx.android.synthetic.main.recycler_view.*


class OrderListFragment : Fragment(), OrderListAdapter.OnLongClickHandler, OrderListAdapter.OnItemClickHandler {

    private lateinit var orderListViewModel: OrderListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var date: String
    private val TAG: String = this::class.java.simpleName

    interface Contract {
        fun editModel(order: Order?)
    }

    override fun onItemLongClick(order: Order) {
        completeOrderDialog("Order completed", "Order Completed", activity!!) {
            orderListViewModel.completeOrder(order.copy(isCompleted = true))
        }
    }

    override fun onClick(order: Order) {
        (activity as OrderListFragment.Contract).editModel(order)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_orders_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)
        val adapter: OrderListAdapter = setupRecyclerView()

        orderListViewModel.getOrdersByDate(date, false).observe(this, Observer { orders: List<Order>? ->
            if (orders?.size == 0) {
                emptytxt.text = "No Orders"
                recyclerView.visibility = View.INVISIBLE
                emptytxt.visibility = View.VISIBLE
            } else {
                emptytxt.visibility = View.INVISIBLE
                recyclerView.visibility = View.VISIBLE
                adapter.setOrder(orders)

            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        date = arguments!!.getString(ARG_ID)
        initViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OrderActivity.editRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(activity!!, "Order saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViewModel() {
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel::class.java)

    }

    private fun setupRecyclerView(): OrderListAdapter {
        val adapter = OrderListAdapter(activity!!, this, this)
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