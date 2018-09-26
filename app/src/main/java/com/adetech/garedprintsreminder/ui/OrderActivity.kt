package com.adetech.garedprintsreminder.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.data.database.Order
import com.adetech.garedprintsreminder.ui.add.AddOrderActivity
import com.adetech.garedprintsreminder.ui.group.OrderGroupFragment
import com.adetech.garedprintsreminder.ui.history.OrderHistoryActivity
import com.adetech.garedprintsreminder.ui.list.OrderListFragment
import com.adetech.garedprintsreminder.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class OrderActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, OrderGroupFragment.Contract, OrderListFragment.Contract {

    companion object {
        const val editRequestCode: Int = 234
        const val addRequestCode: Int = 134
    }


    override fun createFragment(): Fragment {
        return OrderGroupFragment.newInstance()
    }

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initDrawerLayout()
    }

    private fun initDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_settings -> {
                intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_home ->{
                replaceFragment(OrderGroupFragment.newInstance())
            }
            R.id.nav_history -> {
                intent = OrderHistoryActivity.newInstance(this)
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    /***
     * @param
     */
    private fun replaceFragment(fragment: Fragment): Unit {

        if (supportFragmentManager.findFragmentById(R.id.fragment_container)::class.simpleName != fragment::class.simpleName) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
        }
    }


    override fun addModel(order: Order?) {
        startActivity( AddOrderActivity.newInstance(this,  null))
    }

    override fun listModelByDate(date: String?) {
        replaceFragment(OrderListFragment.newInstance(date))
    }

    override fun editModel(order: Order?) {
        startActivityForResult(AddOrderActivity.newInstance(this, order), editRequestCode)
    }

    //    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode != Activity.RESULT_OK) {
//            return
//        }
//
//        if (requestCode == REQUEST_DATE) {
//            val date = data!!.getSerializableExtra(DatePickerFragment.EXTRA_DATE) as Date
//            //TODO collect data
//            updateDate(date)
//        }
//    }
}
