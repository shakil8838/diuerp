package xyz.xandsoft.diuerp.repositories.datamodels

data class SellDataModel(
    val id: Int?,
    val productId: String,
    val sellingQuantity: String,
    val sellingPrice: String,
    val sellingAmount: String,
    val sellingDate: String
)
