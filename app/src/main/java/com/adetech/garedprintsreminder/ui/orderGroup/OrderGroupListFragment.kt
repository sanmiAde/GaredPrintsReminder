package com.adetech.garedprintsreminder.ui.orderGroup

import android.app.Activity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.data.Order
import kotlinx.android.synthetic.main.fragment_list_orders.*

class OrderGroupListFragment : Fragment() {

    interface  Contract {
        fun addModel(order: Order?)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_list_orders, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            (activity as Contract).addModel(null)
        }


        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
       text.text =  prefs.getString("number_of_orders", "")

    }

    companion object {
        fun newInstance(): OrderGroupListFragment = OrderGroupListFragment()
    }

}