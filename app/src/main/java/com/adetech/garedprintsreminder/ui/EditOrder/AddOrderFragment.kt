package com.adetech.garedprintsreminder.ui.EditOrder

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adetech.garedprintsreminder.data.database.Order

class AddOrderFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {

        private const val ARG_ID: String = "id"

        fun newInstance(order: Order?): AddOrderFragment{
            val result = AddOrderFragment()
            if(order != null){
              val args: Bundle = Bundle()

                args.putSerializable(ARG_ID, order.id)
                result.arguments = args

            }

            return  result
        }
    }
}