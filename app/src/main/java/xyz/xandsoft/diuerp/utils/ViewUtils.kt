package xyz.xandsoft.diuerp.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

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