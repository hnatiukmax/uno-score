package dev.hnatiuk.core.presentation.extensions

import android.widget.EditText

fun EditText.doOnEditorAction(requiredActionId: Int, action: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == requiredActionId) {
            action()
            return@setOnEditorActionListener true
        }
        return@setOnEditorActionListener false
    }
}