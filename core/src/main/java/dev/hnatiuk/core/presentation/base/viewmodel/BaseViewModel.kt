package dev.hnatiuk.core.presentation.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.hnatiuk.core.presentation.utils.SingleLiveEvent

abstract class BaseViewModel<E : Event> : ViewModel() {

    private var _event = SingleLiveEvent<E>()
    val event: LiveData<E> = _event

    open fun onViewLoaded() {
        //no op
    }
}