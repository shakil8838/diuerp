package xyz.xandsoft.diuerp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import xyz.xandsoft.diuerp.R


class AddProductFragment : Fragment() {

    private val TAG = "AddProductFragment"

    private lateinit var addName: EditText
    private lateinit var addPrice: EditText
    private lateinit var addInStock: EditText
    private lateinit var addDate: EditText
    private lateinit var addTotalAmount: EditText

    private lateinit var addBtn: Button

    private lateinit var productCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

    }

    private fun init(view: View) {

        productCode = arguments?.getString("pCode").toString()

        Log.e(TAG, "init: $productCode")

        val addPCode: TextView = view.findViewById(R.id.add_pcode)
        addPCode.text = productCode

        addName = view.findViewById(R.id.add_pname)
        addPrice = view.findViewById(R.id.add_pprice)
        addInStock = view.findViewById(R.id.add_pinstock)
        addDate = view.findViewById(R.id.add_date)
        addTotalAmount = view.findViewById(R.id.add_total_amount)

        addBtn = view.findViewById(R.id.add_btn)
    }

}