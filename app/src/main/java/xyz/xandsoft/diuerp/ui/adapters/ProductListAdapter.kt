package xyz.xandsoft.diuerp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import xyz.xandsoft.diuerp.R
import xyz.xandsoft.diuerp.repositories.datamodels.ProductDataModel

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

    }

    override fun getItemCount(): Int {
        return productModelList.size
    }


    class ProductViewHolder(itemView: View) : ViewHolder(itemView) {

        private var productName: TextView
        private var productPrice: TextView
        private var productInStock: TextView

        init {
            productName = itemView.findViewById(R.id.product_name)
            productPrice = itemView.findViewById(R.id.product_price)
            productInStock = itemView.findViewById(R.id.product_instock)
        }

    }
}