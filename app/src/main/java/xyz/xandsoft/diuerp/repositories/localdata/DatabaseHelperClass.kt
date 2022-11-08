package xyz.xandsoft.diuerp.repositories.localdata

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.xandsoft.diuerp.repositories.datamodels.ProductDataModel

class DatabaseHelperClass(private val context: Context) {

    val databaseHelper = DatabaseHelper()

    val writableDatabase = databaseHelper.writableDatabase
    val readableDatabase = databaseHelper.readableDatabase

    suspend fun insertIntoProductTable(productDataModel: ProductDataModel) {
        withContext(Dispatchers.IO) {
            val contentValues = ContentValues()
            contentValues.put(
                databaseHelper.COLUMN_PRODUCT_CODE,
                productDataModel.productCode
            )
            contentValues.put(
                databaseHelper.COLUMN_PRODUCT_NAME,
                productDataModel.product_name
            )
            contentValues.put(
                databaseHelper.COLUMN_PRODUCT_PRICE,
                productDataModel.product_price
            )
            contentValues.put(
                databaseHelper.COLUMN_PRODUCT_INSTOCK,
                productDataModel.in_stock
            )
            contentValues.put(
                databaseHelper.COLUMN_PRODUCT_BUYING_DATE,
                productDataModel.buying_date
            )
            contentValues.put(
                databaseHelper.COLUMN_TOTAL_PURCHASE_AMOUNT,
                productDataModel.total_purchase_amount
            )
            writableDatabase.insert(databaseHelper.PRODUCT_TABLE_NAME, null, contentValues)
        }
    }

    suspend fun insertIntoSellTable() {
        withContext(Dispatchers.IO) {
            val contentValues = ContentValues()
            writableDatabase.insert(databaseHelper.SELL_TABLE, null, contentValues)
        }
    }

    suspend fun getAllItems(): Cursor {

        val retrieveTables = arrayOf(
            databaseHelper.COLUMN_ID,
            databaseHelper.COLUMN_PRODUCT_CODE,
            databaseHelper.COLUMN_PRODUCT_NAME,
            databaseHelper.COLUMN_PRODUCT_PRICE,
            databaseHelper.COLUMN_PRODUCT_INSTOCK,
            databaseHelper.COLUMN_PRODUCT_BUYING_DATE,
            databaseHelper.COLUMN_TOTAL_PURCHASE_AMOUNT
        )

        return withContext(Dispatchers.IO) {
            readableDatabase.query(
                databaseHelper.PRODUCT_TABLE_NAME,
                retrieveTables,
                null,
                null,
                null,
                null,
                null
            )
        }
    }

    suspend fun runRAWQuery(query: String): Cursor {
        return withContext(Dispatchers.IO) {
            readableDatabase.rawQuery(query, null)
        }
    }


    inner class DatabaseHelper : SQLiteOpenHelper(context, "accounts_db", null, 1) {

        val PRODUCT_TABLE_NAME = "product_table"
        val COLUMN_ID = "id"
        val COLUMN_PRODUCT_NAME = "product_name"
        val COLUMN_PRODUCT_CODE = "product_code"
        val COLUMN_PRODUCT_PRICE = "product_price"
        val COLUMN_PRODUCT_INSTOCK = "in_stock"
        val COLUMN_PRODUCT_BUYING_DATE = "buying_date"
        val COLUMN_TOTAL_PURCHASE_AMOUNT = "total_purchase_amount"

        val SELL_TABLE = "sell_table"
        val COLUMN_PRODUCT_ID = "product_id"
        val COLUMN_SELLING_QUANTITY = "selling_quantity"
        val COLUMN_SELLING_PRICE = "selling_price"
        val COLUMN_SELL_AMOUNT = "selling_amount"

        override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
            val queryProductTable =
                "CREATE TABLE $PRODUCT_TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "$COLUMN_PRODUCT_NAME VARCHAR(128), $COLUMN_PRODUCT_CODE VARCHAR(56), $COLUMN_PRODUCT_PRICE INTEGER, " +
                        "$COLUMN_PRODUCT_INSTOCK INTEGER, $COLUMN_PRODUCT_BUYING_DATE VARCHAR(10), " +
                        "$COLUMN_TOTAL_PURCHASE_AMOUNT INTEGER);"

            val querySellTable =
                "CREATE TABLE $SELL_TABLE($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "$COLUMN_PRODUCT_ID INTEGER, $COLUMN_SELLING_QUANTITY INTEGER, " +
                        "$COLUMN_SELLING_PRICE INTEGER, $COLUMN_SELL_AMOUNT INTEGER);"

            sqLiteDatabase?.execSQL(queryProductTable)
            sqLiteDatabase?.execSQL(querySellTable)
        }

        override fun onUpgrade(sqliteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {

        }

    }
}