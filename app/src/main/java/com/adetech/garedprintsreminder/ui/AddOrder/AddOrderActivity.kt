package com.adetech.garedprintsreminder.ui.AddOrder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.data.database.Order
import com.adetech.garedprintsreminder.ui.BaseActivity
import java.util.*

class AddOrderActivity : BaseActivity() {
    override fun createFragment(): Fragment {
        val orderId = intent.getSerializableExtra(ARG_ID)
                ?: return AddOrderFragment.newInstance(null)

        return AddOrderFragment.newInstance(orderId as UUID)

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment)
    }

    companion object {
        private const val ARG_ID: String = "com.adetech.garedprintsreminder.ui.orderGroup.OrderGroupActivity.id"
        //Todo change parameter to id
        //Todo implement app_bar_main
        fun newInstance(context: Context, order: Order?): Intent {
            val intent: Intent = Intent(context, AddOrderActivity::class.java)
            if (order != null) {
                intent.putExtra(ARG_ID, order.id)
            }
            return intent
        }
    }
}