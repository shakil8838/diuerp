package xyz.xandsoft.diuerp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import xyz.xandsoft.diuerp.R

class MainFragment : Fragment() {

    private lateinit var mainProductList: RecyclerView
    private lateinit var mainProductAdd: FloatingActionButton

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        mainProductAdd.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, ScannerFragment())
                .addToBackStack("")
                .commit()
        }
    }

    private fun init() {

        mainProductList = activity?.findViewById(R.id.main_product_list)!!
        mainProductList.layoutManager = LinearLayoutManager(requireActivity())

        mainProductAdd = requireActivity().findViewById(R.id.main_product_add)
    }

}