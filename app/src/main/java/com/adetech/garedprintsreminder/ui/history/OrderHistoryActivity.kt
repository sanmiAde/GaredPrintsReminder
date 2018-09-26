package com.adetech.garedprintsreminder.ui.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.ui.BaseActivity

class OrderHistoryActivity : BaseActivity() {
    override fun createFragment(): Fragment {
        return OrderHistoryFragment.newInstance()
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_activity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_activity)
    }

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, OrderHistoryActivity::class.java)
        }
    }
}