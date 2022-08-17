package dev.hnatiuk.core.presentation.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    requireContext().toast(message, length)
}

fun Fragment.dpToPx(value: Int): Int {
    return requireContext().dpToPx(value)
}