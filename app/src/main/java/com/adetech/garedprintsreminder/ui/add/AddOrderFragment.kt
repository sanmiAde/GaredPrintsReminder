package com.adetech.garedprintsreminder.ui.add


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.*
import com.adetech.garedprintsreminder.R
import com.adetech.garedprintsreminder.data.database.Order
import kotlinx.android.synthetic.main.fragment_add_order.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import android.app.Activity
import android.content.Intent
import android.widget.Toast


class AddOrderFragment : Fragment() {


    private lateinit var addOrderViewModel: AddOrderViewModel
    private lateinit var order: Order
    private var shouldUpdate: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_add_order, container, false)

    private fun initOrderData(): Date {
        val serializedOrder = arguments?.getSerializable(ARG_ID)
        val date: Date
        if (serializedOrder == null) {
            date = Date()

        } else {
            order = serializedOrder as Order
            date = convertStringToDate(order.dueDate)
            name_editTxt.setText(order.name)
            order_edit_txt.setText(order.quantity.toString())
            val price: Double = order.totalPrice.toString().toDouble() / order.quantity.toString().toDouble()
            price_editTxt.setText(price.toString())
            shouldUpdate = true

        }
        return date
    }

    private fun initDatePicker(date: Date) {
        updateDate(date)
        due_date_picker.setOnClickListener {
            val manager = fragmentManager
            //Todo collect info from list screen
            val dialog = DatePickerFragment.newInstance(convertStringToDate(due_date_picker.text.toString()))
            dialog.setTargetFragment(AddOrderFragment@ this, REQUEST_DATE)
            dialog.show(manager, DIALOG_DATE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        val date = initOrderData()
        //dueDateButton = view.findViewById(R.id.due_date_picker)
        initDatePicker(date)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.fragment_new_order, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.id_save_order -> {
                //Use a fab button
                updateDatabase()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_DATE) {
            val date = data!!.getSerializableExtra(DatePickerFragment.EXTRA_DATE) as Date
            //TODO collect data
            updateDate(date)

        }
    }

    private fun isNotTextEmpty(): Boolean {
        val isNameTxtEmpty: Boolean = TextUtils.isEmpty(name_editTxt.text.trim())
        val isOrderSizeEmpty: Boolean = TextUtils.isEmpty(order_edit_txt.text.trim())
        val isPriceEmpty: Boolean = TextUtils.isEmpty(price_editTxt.text.trim())
        //TODO fix text utils.
        return !(isNameTxtEmpty && isOrderSizeEmpty && isPriceEmpty)
    }

    inline fun String.toDouble(): Double = java.lang.Double.parseDouble(this)

    private fun getUserdata(): Triple<String, Int, Double> {
        val name: String = name_editTxt.text.toString()
        val orderSize: Int = order_edit_txt.text.toString().toInt()
        val price: Double = price_editTxt.text.toString().toDouble()
        val totalPrice: Double = orderSize * price

        return Triple(name, orderSize, totalPrice)
    }

    private fun formatDate(date: Date): String {
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        return dateFormat.format(date)
    }

    private fun isScheduleFull(date: String) {


//        if(currentNumberOfOrders > maxOrder){
//            Toast.makeText(activity!!, "You have a lot of orders due", Toast.LENGTH_LONG).show()
//        }
    }

    private fun convertStringToDate(date: String): Date {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.parse(date)
    }

    private fun updateDatabase() {

        when {
            isNotTextEmpty() -> {
                val (name: String, orderSize: Int, totalPrice: Double) = getUserdata()
                saveOrder(name, orderSize, totalPrice)
                finishFragment()
            }
            else -> Toast.makeText(activity!!, "Please input data.", Toast.LENGTH_SHORT).show()
        }
    }

    fun finishFragment() {
        val intent = Intent()
        activity!!.setResult(Activity.RESULT_OK, intent)
        activity!!.finish()
    }

    private fun setupSharedPreferences() {
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        //TODO fix shared preference not return the right figure
        val maxOrder: Int = sharedPref?.getString(getString(R.string.number_of_orders), getString(R.string.pref_default_max_order))!!.toInt()


    }


    private fun saveOrder(name: String, orderSize: Int, totalPrice: Double) {
        val dateStamp: Date = convertStringToDate(due_date_picker.text.toString())
        val currentDate: String = formatDate(dateStamp)
        if (shouldUpdate) {
            val updatedOrder = order.copy(name = name, quantity = orderSize, totalPrice = totalPrice, dueDate = currentDate)
            addOrderViewModel.updateOrder(updatedOrder)
        } else {
            val newOrder = Order(id = 0, name = name, quantity = orderSize, totalPrice = totalPrice, dueDate = currentDate, dateStamp = dateStamp)
            addOrderViewModel.insertOrder(newOrder)
        }
        //if null insert oder else update order
    }

    private fun initViewModel() {
        addOrderViewModel = ViewModelProviders.of(activity!!).get(AddOrderViewModel::class.java)
    }

    private fun updateDate(date: Date) {
        due_date_picker.text = formatDate(date)
    }

    companion object {
        private const val ARG_ID: String = "uuid"
        private const val DIALOG_DATE = "DialogDate"
        private const val REQUEST_DATE = 0

        fun newInstance(order: Order?): AddOrderFragment {
            val result = AddOrderFragment()
            val args = Bundle()
            args.putSerializable(ARG_ID, order)
            result.arguments = args
            return result
        }
    }
}