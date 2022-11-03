package xyz.xandsoft.diuerp.utils

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ProgressBar.showProgressbar() {
    visibility = View.VISIBLE
}

fun ProgressBar.hideProgressbar() {
    visibility = View.GONE
}

fun Context.showDatePicker(textView: TextView) {

    val mCalendar: Calendar = Calendar.getInstance()
    val mDatePickerDialListener: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            mCalendar.set(Calendar.YEAR, year)
            mCalendar.set(Calendar.MONTH, month)
            mCalendar.set(Calendar.DAY_OF_MONTH, day)
            val myFormat = "MM-dd-yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            textView.text = sdf.format(mCalendar.time)
        }
    DatePickerDialog(
        this, mDatePickerDialListener,
        mCalendar[Calendar.YEAR], mCalendar[Calendar.MONTH],
        mCalendar[Calendar.DAY_OF_MONTH]
    ).show()
}