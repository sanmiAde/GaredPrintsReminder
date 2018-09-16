package com.adetech.garedprintsreminder.ui.group

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.data.database.Order
import com.adetech.garedprintsreminder.ui.BaseActivity
import com.adetech.garedprintsreminder.ui.add.AddOrderActivity
import com.adetech.garedprintsreminder.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class OrderGroupActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, OrderGroupFragment.Contract {

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.orders, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
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
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    /***
     * @param
     */
    private fun replaceFragment(fragment: Fragment): Unit {

        when {
            supportFragmentManager.findFragmentById(R.id.fragment_container)::class.simpleName == fragment::class.simpleName -> Toast.makeText(this, fragment::class.java.simpleName, Toast.LENGTH_SHORT).show()
            else -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        }

    }


    override fun addModel(order: Order?) {

        startActivity( AddOrderActivity.newInstance(this,  null))
    }
}
