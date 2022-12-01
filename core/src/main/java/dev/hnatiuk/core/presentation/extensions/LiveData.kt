package dev.hnatiuk.core.presentation.extensions

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import dev.hnatiuk.core.presentation.utils.StringResource

fun <T> LiveData<T>.getOrException(): T {
    return value ?: throw IllegalArgumentException("Value is null")
}

fun <T> MutableLiveData<T>.replace(block: (T?) -> T): T? {
    val oldValue = value
    value = block(oldValue)
    return oldValue
}

fun LiveData<Boolean>.reverse() = map { it.not() }

fun LiveData<StringResource>.asStringLiveData(context: Context): LiveData<String> {
    return map { context.getString(it.resId, it.args) }
}

fun MutableLiveData<String>.clear() {
    value = null
}