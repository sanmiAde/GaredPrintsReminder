package com.adetech.garedprintsreminder.ui.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.data.database.Order
import com.adetech.garedprintsreminder.ui.BaseActivity

class AddOrderActivity : BaseActivity() {

    override fun createFragment(): Fragment {
        val orderId = intent.getIntExtra(ARG_ID, 0)
        return AddOrderFragment.newInstance(orderId)

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_activity)
    }

    companion object {

        private const val ARG_ID: String = "com.adetech.garedprintsreminder.ui.orderGroup.OrderActivity.uuid"
        //Add order activity will be used for editing order and creating new order.
        fun newInstance(context: Context, order: Order?): Intent {
            val intent: Intent = Intent(context, AddOrderActivity::class.java)
            if (order != null) {
                intent.putExtra(ARG_ID, order.id)
            }
            return intent
        }
    }
}