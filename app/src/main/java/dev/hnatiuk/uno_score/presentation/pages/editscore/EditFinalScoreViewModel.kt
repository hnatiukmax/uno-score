package dev.hnatiuk.uno_score.presentation.pages.editscore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.extensions.getOrException
import dev.hnatiuk.uno_score.domain.repository.GameRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class EditFinalScoreEvent : Event {

    data class OnCloseWithResult(val score: Int) : EditFinalScoreEvent()

    object OnClose : EditFinalScoreEvent()
}

@HiltViewModel
class EditFinalScoreViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gameRepository: GameRepository
) : BaseViewModel<EditFinalScoreEvent>() {

    private val args = savedStateHandle.getOrException<EditFinalScoreArgs>(EDIT_FINAL_SCORE_ARG)

    val newFinalScore = MutableLiveData<String>()

    init {
        if (args.isEditMode) {
            newFinalScore.value = args.oldScore?.toString().orEmpty()
        }
    }

    fun onApplyClick() {
        val newScore = newFinalScore.value?.toIntOrNull() ?: return
        if (args.isEditMode) {
            viewModelScope.launch {
                gameRepository.setFinalScore(
                    gameId = args.gameId ?: return@launch,
                    score = newScore
                )
                processEvent(EditFinalScoreEvent.OnClose)
            }
        } else {
            processEvent(EditFinalScoreEvent.OnCloseWithResult(newScore))
        }
    }

    companion object {

        const val EDIT_FINAL_SCORE_ARG = "EDIT_FINAL_SCORE_ARG"
    }
}