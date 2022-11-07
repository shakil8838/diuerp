package xyz.xandsoft.diuerp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import xyz.xandsoft.diuerp.R
import xyz.xandsoft.diuerp.repositories.datamodels.ProductDataModel
import xyz.xandsoft.diuerp.repositories.localdata.DatabaseHelperClass
import xyz.xandsoft.diuerp.ui.adapters.ProductListAdapter

class MainFragment : Fragment() {

    private lateinit var mainProductList: RecyclerView
    private lateinit var mainProductAdd: FloatingActionButton
    private lateinit var productListAdapter: ProductListAdapter

    private lateinit var databaseHelperClass: DatabaseHelperClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        mainProductAdd.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, ScannerFragment())
                .addToBackStack("")
                .commit()
        }

        viewLifecycleOwner.lifecycleScope.launch {

            val cursor = databaseHelperClass.getAllItems()
            val list = ArrayList<ProductDataModel>()

            while (cursor.moveToNext()) {
                list.add(
                    ProductDataModel(
                        cursor.getInt(cursor.getColumnIndex(databaseHelperClass.DatabaseHelper().COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(databaseHelperClass.DatabaseHelper().COLUMN_PRODUCT_CODE)),
                        cursor.getString(cursor.getColumnIndex(databaseHelperClass.DatabaseHelper().COLUMN_PRODUCT_NAME)),
                        cursor.getInt(cursor.getColumnIndex(databaseHelperClass.DatabaseHelper().COLUMN_PRODUCT_PRICE)),
                        cursor.getInt(cursor.getColumnIndex(databaseHelperClass.DatabaseHelper().COLUMN_PRODUCT_INSTOCK)),
                        cursor.getString(cursor.getColumnIndex(databaseHelperClass.DatabaseHelper().COLUMN_PRODUCT_BUYING_DATE)),
                        cursor.getInt(cursor.getColumnIndex(databaseHelperClass.DatabaseHelper().COLUMN_TOTAL_PURCHASE_AMOUNT))
                    )
                )
            }



        }
    }

    private fun init() {

        mainProductList = activity?.findViewById(R.id.main_product_list)!!
        mainProductList.layoutManager = LinearLayoutManager(requireActivity())

        mainProductAdd = requireActivity().findViewById(R.id.main_product_add)

        databaseHelperClass = DatabaseHelperClass(requireActivity())
    }

}