package com.adetech.garedprintsreminder.ui.add

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import com.adetech.garedprintsreminder.R
import java.util.*


class DatePickerFragment : DialogFragment() {

    private lateinit var datePicker: DatePicker

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        fun getData(): Triple<Int, Int, Int> {
            val date: Date = arguments?.getSerializable(ARG_DATE) as Date

            val calendar: Calendar = Calendar.getInstance()
            calendar.time = date
            val year: Int = calendar.get(Calendar.YEAR)
            val month: Int = calendar.get(Calendar.MONTH)
            val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
            return Triple(year, month, day)
        }

        val (year: Int, month: Int, day: Int) = getData()

        val view: View = LayoutInflater.from(activity!!).inflate(R.layout.dialog_date, null)
        datePicker = view.findViewById(R.id.dialog_date_picker)
        datePicker.init(year, month, day, null)

        return AlertDialog.Builder(activity!!).setView(view).setTitle(R.string.date_picker_title).setPositiveButton(android.R.string.ok) { dialogInterface, i ->
            val dueYear: Int = datePicker.year
            val dueMonth: Int = datePicker.month
            val dueDay: Int = datePicker.dayOfMonth

            val date: Date = GregorianCalendar(dueYear, dueMonth, dueMonth).time
            sendResult(Activity.RESULT_OK, date)
        }.create()
    }

    private fun sendResult(resultCode: Int, date: Date) {
        if (targetFragment == null) {
            return
        }

        val intent: Intent = Intent()
        intent.putExtra(EXTRA_DATE, date)

        targetFragment!!.onActivityResult(targetRequestCode, resultCode, intent)
    }

    companion object {
        public const val EXTRA_DATE: String = "com.adetech.garedprintsreminder.ui.add.DatePickerFragment.date"
        private const val ARG_DATE = "date"

        fun newInstance(date: Date): DatePickerFragment {
            val args = Bundle()
            args.putSerializable(ARG_DATE, date)
            val fragment = DatePickerFragment()
            fragment.arguments = args
            return fragment
        }

    }
}