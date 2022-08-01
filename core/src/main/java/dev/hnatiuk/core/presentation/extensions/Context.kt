package dev.hnatiuk.core.presentation.extensions

import android.content.Context

fun Context.dpToPx(dp: Int): Float {
    return dp * resources.displayMetrics.density
}