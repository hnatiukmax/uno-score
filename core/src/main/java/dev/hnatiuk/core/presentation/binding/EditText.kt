package dev.hnatiuk.core.presentation.binding

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

typealias OnValueChanged<T> = (T) -> Unit

fun EditText.bind(
    lifecycleOwner: LifecycleOwner,
    value: MutableLiveData<String>,
    onValueChanged: OnValueChanged<String>
) {
    val watcher = doOnTextChanged { text, _, _, _ ->
        onValueChanged.invoke(text.toString())
    }

    value.observe(lifecycleOwner) {
        if (it != null && text.toString() != it) {
            setText(it)
        }
    }

    lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
        if (event == ON_DESTROY) removeTextChangedListener(watcher)
    })
}