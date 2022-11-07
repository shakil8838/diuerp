package xyz.xandsoft.diuerp.repositories.datamodels

import androidx.annotation.Nullable

data class ProductDataModel(
    val id: Int?,
    val productCode: String,
    val product_name: String,
    val product_price: Int,
    val in_stock: Int,
    val buying_date: String,
    val total_purchase_amount: Int
)
