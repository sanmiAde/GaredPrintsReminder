package com.adetech.garedprintsreminder.ui.history

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.data.database.OrderGroupedByDate
import kotlinx.android.synthetic.main.recycler_view.*

class OrderHistoryFragment : Fragment() {

    private lateinit var orderHistoryViewModel: OrderHistoryViewModel
    private lateinit var recyclerView: RecyclerView
    private val TAG: String = this::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_history, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)

        val adapter: OrderHistoryAdapter = setupRecyclerView()
        orderHistoryViewModel.getOrderHistory().observe(this, Observer { orders: List<OrderGroupedByDate>? ->

            if (orders?.size == 0) {
                emptytxt.text = "History Not Found"
                recyclerView.visibility = View.INVISIBLE
                emptytxt.visibility = View.VISIBLE
            } else {
                emptytxt.visibility = View.INVISIBLE
                recyclerView.visibility = View.VISIBLE
                adapter.setOrder(orders)
            }
        })
    }

    private fun initViewModel() {
        orderHistoryViewModel = ViewModelProviders.of(this).get(OrderHistoryViewModel::class.java)

    }

    private fun setupRecyclerView(): OrderHistoryAdapter {
        val adapter = OrderHistoryAdapter(activity!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        return adapter
    }

    companion object {

        fun newInstance(): OrderHistoryFragment {
            return OrderHistoryFragment()
        }
    }
}