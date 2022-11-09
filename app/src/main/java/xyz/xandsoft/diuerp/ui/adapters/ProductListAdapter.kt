package xyz.xandsoft.diuerp.ui.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import xyz.xandsoft.diuerp.R
import xyz.xandsoft.diuerp.repositories.datamodels.ProductDataModel
import xyz.xandsoft.diuerp.ui.activities.MainActivity
import xyz.xandsoft.diuerp.ui.fragments.AddProductFragment
import xyz.xandsoft.diuerp.ui.fragments.SingleProductFragment

class ProductListAdapter(
    private val context: Context,
    private val productModelList: List<ProductDataModel>
) :
    Adapter<ProductListAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_single_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productName.text = productModelList[position].product_name
        holder.productInStock.text = productModelList[position].in_stock.toString()
        holder.productPrice.text = productModelList[position].product_price.toString()

        holder.itemView.setOnClickListener {
            val theBundle = Bundle()
            theBundle.putString("pid", productModelList[position].id.toString())
            val singleProductFragment = SingleProductFragment()
            singleProductFragment.arguments = theBundle
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, singleProductFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return productModelList.size
    }


    class ProductViewHolder(itemView: View) : ViewHolder(itemView) {

        var productName: TextView
        var productPrice: TextView
        var productInStock: TextView

        init {
            productName = itemView.findViewById(R.id.product_name)
            productPrice = itemView.findViewById(R.id.product_price)
            productInStock = itemView.findViewById(R.id.product_instock)
        }

    }
}