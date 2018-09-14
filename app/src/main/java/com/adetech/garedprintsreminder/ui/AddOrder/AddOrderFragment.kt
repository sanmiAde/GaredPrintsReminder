package com.adetech.garedprintsreminder.ui.AddOrder

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adetech.garedprintsreminder.R
import java.util.*

class AddOrderFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_new_order, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {

        private const val ARG_ID: String = "id"

        fun newInstance(id: UUID?): AddOrderFragment {
            val result = AddOrderFragment()
            val args: Bundle = Bundle()
            args.putSerializable(ARG_ID, id)
            result.arguments = args
            return result
        }
    }
}