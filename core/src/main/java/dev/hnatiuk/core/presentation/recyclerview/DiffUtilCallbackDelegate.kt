package dev.hnatiuk.core.presentation.recyclerview

import androidx.recyclerview.widget.DiffUtil

abstract class DiffUtilCallbackDelegate<T>: DiffUtil.ItemCallback<T>() {

    abstract fun isForViewType(data: T): Boolean
}