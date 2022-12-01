package dev.hnatiuk.uno_score.presentation.pages.base.inputdialog

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.extensions.getOrException
import dev.hnatiuk.uno_score.R
import javax.inject.Inject

sealed class InputDialogEvent : Event {

    data class OnCloseWithResult(
        val requestKey: String,
        val value: String,
        val payload: Parcelable? = null
    ) : InputDialogEvent()
}

@HiltViewModel
class InputDialogViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<InputDialogEvent>() {

    val args = savedStateHandle.getOrException<InputDialogArgs>(INPUT_DIALOG_ARG)

    val title = MutableLiveData(args.title)
    val inputHint = MutableLiveData(args.inputHint)
    val confirmButtonText = MutableLiveData(args.positiveButton)

    val inputValue = MutableLiveData(args.inputDefaultValue)

    fun onApplyClick() {
        val value = inputValue.value
        when {
            value.isNullOrBlank() -> postMessage(R.string.input_dialog_empty_hint.asResourceMessage)
            else -> processEvent(
                InputDialogEvent.OnCloseWithResult(
                    requestKey = args.requestKey,
                    value = value,
                    payload = args.payload
                )
            )
        }
    }

    companion object {

        const val INPUT_DIALOG_ARG = "EDIT_FINAL_SCORE_ARG"
    }
}