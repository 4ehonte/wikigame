package ua.boberproduction.wikigame.util

import android.content.Context
import android.graphics.PorterDuff
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * Display toast message on provided context
 */
fun Context.toast(text: String, length: Int = Toast.LENGTH_SHORT, color: Int = -1) {
    val toast = Toast.makeText(this, text, length)
    // change toast background color, if it was specified
    if (color != -1) toast.view.background.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    toast.show()
}

fun Fragment.toast(text: String, length: Int = Toast.LENGTH_SHORT, color: Int = -1) {
    if (activity != null) {
        activity!!.toast(text, length = length, color = color)
    }
}

fun View.snackbar(text: String, length: Int = Snackbar.LENGTH_SHORT, colorResource: Int = -1) {
    val snackbar = Snackbar.make(this, text, length)
    if (colorResource != -1) snackbar.view.setBackgroundResource(colorResource)
    snackbar.view.setOnClickListener { snackbar.dismiss() }
    snackbar.show()
}