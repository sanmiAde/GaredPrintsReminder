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
import kotlinx.android.synthetic.main.fragment_new_order.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddOrderFragment : Fragment() {

    private lateinit var addOrderViewModel: AddOrderViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_new_order, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

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
        when (item.itemId) {
            R.id.id_save_order -> {
                updateDatabase()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateDatabase() {
        fun isTextEmpty(): Pair<Boolean, Boolean> {
            val isNameTxtEmpty: Boolean = TextUtils.isEmpty(name_editTxt.text)
            val isOrderSizeEmpty: Boolean = TextUtils.isEmpty(order_edit_txt.text)
            return Pair(isNameTxtEmpty, isOrderSizeEmpty)
        }
        val (isNameTxtEmpty: Boolean, isOrderSizeEmpty: Boolean) = isTextEmpty()

        fun getUserdata(): Triple<String, Int, Double> {
            val name: String = name_editTxt.text.toString()
            val orderSize: Int = order_edit_txt.text.toString().toInt()
            val sharedPref: SharedPreferences? = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
            //TODO add price settings.
            val jobPrice: Double = sharedPref?.getString(getString(R.string.price_of_job), getString(R.string.pref_default_price))!!.toDouble()
            val totalPrice: Double = jobPrice * orderSize
            return Triple(name, orderSize, totalPrice)
        }

        val (name: String, orderSize: Int, totalPrice: Double) = getUserdata()

        when {
            !isNameTxtEmpty && !isOrderSizeEmpty -> {
                fun formatDate(): String {
                    val date = Date()
                    val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy") as DateFormat
                    val currentDate: String = dateFormat.format(date)
                    return currentDate
                }

                val currentDate: String = formatDate()
                val newOrder = Order(id = 0, name = name, quantity = orderSize, totalPrice = totalPrice, dueDate = currentDate)
                addOrderViewModel.insertOrder(newOrder)
//                when (activity?.parent) {
//                    null -> activity?.setResult(Activity.RESULT_OK, replyIntent)
//                    else -> activity?.parent?.setResult(Activity.RESULT_OK, replyIntent)
//                }
            }
        }
    }

    private fun initViewModel() {
        addOrderViewModel = ViewModelProviders.of(activity!!).get(AddOrderViewModel::class.java)

    }


    companion object {

        private const val ARG_ID: String = "uuid"

        fun newInstance(id: Int): AddOrderFragment {
            val result = AddOrderFragment()
            val args = Bundle()
            args.putSerializable(ARG_ID, id)
            result.arguments = args
            return result
        }
    }
}