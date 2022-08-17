package dev.hnatiuk.core.presentation.extensions

import android.text.method.ScrollingMovementMethod
import android.widget.TextView

fun TextView.makeScrollable(horizontalScroll: Boolean) {
    movementMethod = ScrollingMovementMethod()
    setHorizontallyScrolling(horizontalScroll)
}