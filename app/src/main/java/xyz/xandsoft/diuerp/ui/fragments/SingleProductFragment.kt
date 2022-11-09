package xyz.xandsoft.diuerp.ui.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import xyz.xandsoft.diuerp.R
import xyz.xandsoft.diuerp.repositories.datamodels.SellDataModel
import xyz.xandsoft.diuerp.repositories.localdata.DatabaseHelperClass
import xyz.xandsoft.diuerp.ui.adapters.SellListAdapter
import xyz.xandsoft.diuerp.utils.showDatePicker
import xyz.xandsoft.diuerp.utils.showShortToast

class SingleProductFragment : Fragment() {

    private val TAG = "SingleProductFragment"

    private lateinit var productId: String

    private lateinit var totalSells: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var singleSellBtn: FloatingActionButton

    val sellingList = ArrayList<SellDataModel>()

    private lateinit var databaseHelperClass: DatabaseHelperClass

    private lateinit var theDialog: Dialog
    private lateinit var sellListAdapter: SellListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

        loadAdapter()

        singleSellBtn.setOnClickListener {

            val sellingQuantity: EditText = theDialog.findViewById(R.id.dialog_selling_quantity)
            val sellingPrice: EditText = theDialog.findViewById(R.id.dialog_selling_price)
            val sellingDate: EditText = theDialog.findViewById(R.id.dialog_selling_date)
            val sellingBtn: Button = theDialog.findViewById(R.id.dialog_selling_submit_btn)

            theDialog.create()
            theDialog.show()

            sellingDate.setOnFocusChangeListener { view, b ->
                if (view.isFocused) {
                    activity?.showDatePicker(sellingDate)
                }
            }

            sellingDate.setOnClickListener {
                activity?.showDatePicker(sellingDate)
            }

            sellingBtn.setOnClickListener {
                val quantity = sellingQuantity.text.toString().trim()
                val price = sellingPrice.text.toString().trim()
                val date = sellingDate.text.toString().trim()

                if (quantity.isNullOrEmpty() || price.isNullOrEmpty() || date.isNullOrEmpty()) {
                    requireActivity().showShortToast("All fields are requires")
                } else {
                    insertIntoSell(Integer.parseInt(quantity), Integer.parseInt(price), date)
                }
            }
        }

    }

    private fun insertIntoSell(quantity: Int, price: Int, date: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            databaseHelperClass.insertIntoSellTable(
                SellDataModel(
                    null,
                    productId,
                    quantity.toString(),
                    price.toString(),
                    (quantity * price).toString(),
                    date
                )
            )
            theDialog.dismiss()
            loadAdapter()
        }

    }

    private fun init(view: View) {

        productId = arguments?.getString("pid").toString()

        totalSells = view.findViewById(R.id.single_total_sells)
        recyclerView = view.findViewById(R.id.single_product_list)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        databaseHelperClass = DatabaseHelperClass(requireActivity())

        singleSellBtn = view.findViewById(R.id.single_sell_btn)

        theDialog = Dialog(requireActivity())
        theDialog.setContentView(R.layout.layout_dialog_sell)
    }

    @SuppressLint("Range")
    private fun loadAdapter() {

        sellingList.clear()

        viewLifecycleOwner.lifecycleScope.launch {

            val amountCursor =
                databaseHelperClass.runRAWQuery(
                    "SELECT SUM(" +
                            "${databaseHelperClass.DatabaseHelper().COLUMN_SELL_AMOUNT}) FROM " +
                            "${databaseHelperClass.DatabaseHelper().SELL_TABLE} WHERE " +
                            "${databaseHelperClass.DatabaseHelper().COLUMN_PRODUCT_ID} = $productId;"
                )

            if (amountCursor.moveToFirst()) {
                val sellAmount = "Total sell:\n ${amountCursor.getInt(0)} Taka"
                totalSells.text = sellAmount
            }

            val cursor =
                databaseHelperClass.runRAWQuery(
                    "SELECT * FROM " +
                            "${databaseHelperClass.DatabaseHelper().SELL_TABLE} WHERE " +
                            "${databaseHelperClass.DatabaseHelper().COLUMN_PRODUCT_ID} = $productId;"
                )

            while (cursor.moveToNext()) {
                sellingList.add(
                    SellDataModel(
                        cursor.getInt(cursor.getColumnIndex(databaseHelperClass.DatabaseHelper().COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(databaseHelperClass.DatabaseHelper().COLUMN_PRODUCT_ID)),
                        cursor.getString(cursor.getColumnIndex(databaseHelperClass.DatabaseHelper().COLUMN_SELLING_QUANTITY)),
                        cursor.getString(cursor.getColumnIndex(databaseHelperClass.DatabaseHelper().COLUMN_SELLING_PRICE)),
                        cursor.getString(cursor.getColumnIndex(databaseHelperClass.DatabaseHelper().COLUMN_SELL_AMOUNT)),
                        cursor.getString(cursor.getColumnIndex(databaseHelperClass.DatabaseHelper().COLUMN_SELL_DATE))
                    )
                )
            }

            sellListAdapter = SellListAdapter(requireActivity(), sellingList)
            recyclerView.adapter = sellListAdapter
            sellListAdapter.notifyDataSetChanged()

        }
    }
}