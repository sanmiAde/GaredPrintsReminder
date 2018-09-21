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



class AddOrderFragment : Fragment() {

    private lateinit var addOrderViewModel: AddOrderViewModel
    private lateinit var order: Order



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_order, container, false)

        return view
    }

    private fun initOrderData(): Date {
        val serializedOrder = arguments?.getSerializable(ARG_ID)
        val date: Date
        if (serializedOrder == null) {
            date = Date()

        } else {
            order = serializedOrder as Order
            date = convertStringToDate(order.dueDate)!!
            name_editTxt.setText(order.name)
            order_edit_txt.setText(order.quantity.toString())

        }
        return date
    }

    private fun initDatePicker(view: View, date: Date) {
        updateDate(date)
        due_date_picker.setOnClickListener {
            val manager = fragmentManager
            //Todo collect info from list screen
            val dialog = DatePickerFragment.newInstance(convertStringToDate(due_date_picker.text.toString())!!)
            dialog.setTargetFragment(AddOrderFragment@ this, REQUEST_DATE)
            dialog.show(manager, DIALOG_DATE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        val date = initOrderData()
        //dueDateButton = view.findViewById(R.id.due_date_picker)
        initDatePicker(view, date)
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

    private fun isTextEmpty(): Pair<Boolean, Boolean> {
        val isNameTxtEmpty: Boolean = TextUtils.isEmpty(name_editTxt.text)
        val isOrderSizeEmpty: Boolean = TextUtils.isEmpty(order_edit_txt.text)
        return Pair(isNameTxtEmpty, isOrderSizeEmpty)
    }

    private fun getUserdata(): Triple<String, Int, Double> {
        val name: String = name_editTxt.text.toString()
        val orderSize: Int = order_edit_txt.text.toString().toInt()
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        //TODO add price settings. And implement business logic
        val jobPrice: Double = sharedPref?.getString(getString(R.string.price_of_job), getString(R.string.pref_default_price))!!.toDouble()
        val totalPrice: Double = jobPrice * orderSize
        return Triple(name, orderSize, totalPrice)
    }

    private fun formatDate(date: Date): String {
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy") as DateFormat
        return dateFormat.format(date)
    }

    private fun convertStringToDate(date: String): Date {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.parse(date)
    }

    private fun updateDatabase() {
        val (isNameTxtEmpty: Boolean, isOrderSizeEmpty: Boolean) = isTextEmpty()
        val (name: String, orderSize: Int, totalPrice: Double) = getUserdata()
        when {
            !isNameTxtEmpty && !isOrderSizeEmpty -> {
                saveOrder(name, orderSize, totalPrice)
//                when (activity?.parent) {
//                    null -> activity?.setResult(Activity.RESULT_OK, replyIntent)
//                    else -> activity?.parent?.setResult(Activity.RESULT_OK, replyIntent)
//                }
            }
        }
    }


    fun saveOrder(name: String, orderSize: Int, totalPrice: Double) {
        //TODO check is order aguement is null
        val currentDate: String = formatDate(convertStringToDate(due_date_picker.text.toString()))
        if (order == null) {
            val newOrder = Order(id = 0, name = name, quantity = orderSize, totalPrice = totalPrice, dueDate = currentDate)
            addOrderViewModel.insertOrder(newOrder)
        } else {
            val updatedOrder = order.copy(name = name, quantity = orderSize, totalPrice = totalPrice, dueDate = currentDate)
            addOrderViewModel.updateOrder(updatedOrder)
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