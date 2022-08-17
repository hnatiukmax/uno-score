package dev.hnatiuk.core.presentation.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.os.postDelayed
import dev.hnatiuk.core.presentation.extensions.inputMethodManager

private const val DEFAULT_SHOW_KEYBOARD_DELAY = 400L

fun Context.hideKeyboard(view: EditText) {
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    view.clearFocus()
}

fun Context.showKeyboard(view: EditText) {
    view.requestFocus()
    view.setSelection(view.length())
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.showKeyboardWithDelay(view: EditText, delay: Long = DEFAULT_SHOW_KEYBOARD_DELAY) {
    Handler(Looper.getMainLooper()).postDelayed(delay) {
        showKeyboard(view)
    }
}