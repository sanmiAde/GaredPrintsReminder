package com.adetech.garedprintsreminder.ui.EditOrder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.data.Order

class AddOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_new_order)
    }

    companion object {

        private const val ARG_ID: String = "id"

        fun newInstance(context: Context, order: Order?): Intent{
            val intent: Intent = Intent(context, AddOrderActivity::class.java)
            if(order != null){
                intent.putExtra(ARG_ID, order.id)
            }
            return  intent
        }
    }
}