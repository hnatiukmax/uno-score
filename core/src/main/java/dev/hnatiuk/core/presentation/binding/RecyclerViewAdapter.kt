package dev.hnatiuk.core.presentation.binding

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import dev.hnatiuk.core.presentation.recyclerview.AsyncListDiffDelegationAdapter

fun <T : Any> AsyncListDiffDelegationAdapter<T>.bind(
    lifecycleOwner: LifecycleOwner,
    items: LiveData<List<T>>
) {
    items.observe(lifecycleOwner) {
        this.items = it
    }
}