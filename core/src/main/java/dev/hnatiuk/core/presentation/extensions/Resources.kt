package dev.hnatiuk.core.presentation.extensions

import android.graphics.Color
import androidx.annotation.FloatRange

fun Int.withAlpha(@FloatRange(from = 0.0, to = 1.0) factor: Float): Int {
    val alpha = (Color.alpha(this) * factor).toInt()
    val red = Color.red(this)
    val green = Color.green(this)
    val blue = Color.blue(this)
    return Color.argb(alpha, red, green, blue)
}