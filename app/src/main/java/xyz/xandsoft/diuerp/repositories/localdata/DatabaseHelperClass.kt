package xyz.xandsoft.diuerp.repositories.localdata

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelperClass(private val context: Context) {

    val databaseHelper = DatabaseHelper()

    fun insertIntoDatabase() {
        val writableDatabase = databaseHelper.writableDatabase
        val contentValues = ContentValues()
        writableDatabase.insert(databaseHelper.TABLE_NAME, null, contentValues)

    }

    inner class DatabaseHelper : SQLiteOpenHelper(context, "accounts_db", null, 1) {

        val TABLE_NAME = "product_table"
        val COLUMN_ID = "id"
        val COLUMN_PRODUCT_NAME = "product_name"
        val COLUMN_PRODUCT_PRICE = "product_price"
        val COLUMN_PRODUCT_INSTOCK = "in_stock"

        override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
            val query =
                "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "$COLUMN_PRODUCT_NAME VARCHAR(128), $COLUMN_PRODUCT_PRICE INTEGER, $COLUMN_PRODUCT_INSTOCK INTEGER);"
            sqLiteDatabase?.execSQL(query)
        }

        override fun onUpgrade(sqliteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {

        }

    }
}