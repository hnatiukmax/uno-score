package dev.hnatiuk.uno_score.presentation.pages.base

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.extensions.getOrException
import javax.inject.Inject

sealed class TwoActionDialogEvent : Event {

    data class OnCloseWithConfirmResult(val payload: Parcelable?) : TwoActionDialogEvent()

    object OnCloseWithCancelResult : TwoActionDialogEvent()
}

@HiltViewModel
class TwoActionDialogViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<TwoActionDialogEvent>() {

    private val args = savedStateHandle.getOrException<TwoActionDialogArgs>(TWO_ACTION_DIALOG_ARG)

    val description = MutableLiveData(args.description)
    val confirmButtonText = MutableLiveData(args.confirmButtonText)
    val cancelButtonText = MutableLiveData(args.cancelButtonText)

    fun onConfirmClick() {
        processEvent(TwoActionDialogEvent.OnCloseWithConfirmResult(args.payload))
    }

    fun onCancelClick() {
        processEvent(TwoActionDialogEvent.OnCloseWithCancelResult)
    }

    companion object {

        const val TWO_ACTION_DIALOG_ARG = "TWO_ACTION_DIALOG_ARG"
    }
}