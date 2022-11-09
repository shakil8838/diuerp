package xyz.xandsoft.diuerp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import xyz.xandsoft.diuerp.R
import xyz.xandsoft.diuerp.repositories.datamodels.SellDataModel

class SellListAdapter(private val context: Context, private val sellList: List<SellDataModel>) :
    Adapter<SellListAdapter.SellViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellViewHolder {
        return SellViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_single_sell, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SellViewHolder, position: Int) {
        holder.sellDate.text = sellList[position].sellingDate
        holder.sellPrice.text = sellList[position].sellingPrice
        holder.sellQuantity.text = sellList[position].sellingQuantity
        holder.sellAmount.text = sellList[position].sellingAmount
    }

    override fun getItemCount(): Int = sellList.size

    class SellViewHolder(itemView: View) : ViewHolder(itemView) {

        lateinit var sellDate: TextView
        lateinit var sellPrice: TextView
        lateinit var sellQuantity: TextView
        lateinit var sellAmount: TextView

        init {
            sellDate = itemView.findViewById(R.id.single_sell_date)
            sellPrice = itemView.findViewById(R.id.single_sell_price)
            sellQuantity = itemView.findViewById(R.id.single_sell_quantity)
            sellAmount = itemView.findViewById(R.id.single_sell_amount)
        }
    }
}