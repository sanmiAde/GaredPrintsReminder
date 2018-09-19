package com.adetech.garedprintsreminder.ui.utils

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.widget.Toast


/**
 * Creates a new alert dialog that confirms from user if an action should be carried out.
 * @param title action to be shown on screen
 * @param context current activity
 * @param completeOrder function to be executed.
 *
 */
fun completeOrderDialog(title: String, toastMessage: String, context: Context, completeOrder: () -> Unit) {
    val alertDialogBuilder = AlertDialog.Builder(context)

    alertDialogBuilder.setTitle(title)
    alertDialogBuilder.setPositiveButton("Yes") { _, _ -> completeOrder(); Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show() }.setNegativeButton("No") { dialogInterface: DialogInterface?, _: Int -> dialogInterface?.cancel() }

    val alertDialog: AlertDialog = alertDialogBuilder.create()
    alertDialog.show()
}