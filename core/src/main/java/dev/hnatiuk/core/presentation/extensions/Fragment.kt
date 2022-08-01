package dev.hnatiuk.core.presentation.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, length).show()
}

fun Fragment.dpToPx(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}