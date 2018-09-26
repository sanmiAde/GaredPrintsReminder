package com.adetech.garedprintsreminder.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.adetech.garedprintsreminder.R

abstract class BaseActivity : AppCompatActivity() {


    protected abstract fun createFragment(): Fragment

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())

        initFragment()
    }

    protected open fun initFragment() {

        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, createFragment()).setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out).commit()
        }
    }
}