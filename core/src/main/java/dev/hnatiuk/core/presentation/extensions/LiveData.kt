package dev.hnatiuk.core.presentation.extensions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.replace(block: (T?) -> T): T? {
    val oldValue = value
    value = block(oldValue)
    return oldValue
}