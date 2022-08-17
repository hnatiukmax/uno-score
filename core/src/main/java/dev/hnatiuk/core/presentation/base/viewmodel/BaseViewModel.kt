package dev.hnatiuk.core.presentation.base.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.hnatiuk.core.presentation.utils.SingleLiveEvent

abstract class BaseViewModel<E : Event> : ViewModel() {

    private val _event = SingleLiveEvent<E>()
    private val _onShowToastMessage = SingleLiveEvent<Message>()

    val event: LiveData<E> = _event
    val onShowToastMessage: LiveData<Message> = _onShowToastMessage

    protected fun processEvent(event: E) {
        _event.value = event
    }

    protected fun postMessage(message: Message) {
        _onShowToastMessage.value = message
    }

    open fun onViewLoaded() {
        //no op
    }

    sealed class Message {

        data class StringMessage(val value: String) : Message()

        data class ResourceMessage(
            @StringRes val resId: Int,
            val args: List<String> = emptyList()
        ) : Message()
    }

    companion object {

        val String.asMessage get() = Message.StringMessage(this)

        val Int.asResourceMessage get() = Message.ResourceMessage(this)
    }
}