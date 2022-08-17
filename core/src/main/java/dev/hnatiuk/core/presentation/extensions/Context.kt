package dev.hnatiuk.core.presentation.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.DimenRes

val Context.inputMethodManager: InputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

fun Context.dpToPx(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}

fun Context.dimen(@DimenRes resId: Int): Float {
    return resources.getDimension(resId)
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}