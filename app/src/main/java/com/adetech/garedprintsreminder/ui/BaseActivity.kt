package com.adetech.garedprintsreminder.ui

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.adetech.garedprintsreminder.R

open class BaseActivity : AppCompatActivity() {
    private fun replaceFragment(fragment: Fragment): Unit {
        when {
            supportFragmentManager.findFragmentById(R.id.fragment_container)::class.simpleName == fragment::class.simpleName -> Toast.makeText(this, fragment::class.java.simpleName, Toast.LENGTH_SHORT).show()
            else -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        }

    }


    private fun initFragment(fragment: Fragment): Unit {

        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }
    }
}