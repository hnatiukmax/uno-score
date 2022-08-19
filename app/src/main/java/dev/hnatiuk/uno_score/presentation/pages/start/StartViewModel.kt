package dev.hnatiuk.uno_score.presentation.pages.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.navigation.ApplicationRouter
import dev.hnatiuk.uno_score.BuildConfig
import dev.hnatiuk.uno_score.R
import dev.hnatiuk.uno_score.domain.entity.Score
import dev.hnatiuk.uno_score.presentation.pages.editscore.EditFinalScoreDialog
import dev.hnatiuk.uno_score.presentation.pages.score.ScoreFragment
import dev.hnatiuk.uno_score.presentation.recyclerview.items.FinalScoreSuggestionItem
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class StartEvent : Event

@HiltViewModel
class StartViewModel @Inject constructor(
    private val applicationRouter: ApplicationRouter
) : BaseViewModel<StartEvent>() {

    private val _version = MutableLiveData(composeVersion())
    private val _finalScoreSuggestionsData = MutableLiveData<List<Score>>()

    val version: LiveData<String> = _version
    val finalScoreSuggestionsData = _finalScoreSuggestionsData.map(::composeFinalScoreSuggestion)

    fun onFinalScoreSelected(score: Score) {
        applicationRouter.navigateTo(ScoreFragment.screen(score.value))
    }

    fun onCustomFinalScoreSelected() {
        applicationRouter.showDialog(EditFinalScoreDialog.screen(isEditMode = false))
    }

    override fun onViewLoaded() {
        viewModelScope.launch {
            _finalScoreSuggestionsData.value = getFinalScoreSuggestions()
        }
    }

    private fun composeVersion(): String {
        return "${BuildConfig.VERSION_NAME}-${BuildConfig.BUILD_TYPE}"
            .takeIf { BuildConfig.DEBUG }
            .orEmpty()
    }

    private fun getFinalScoreSuggestions(): List<Score> {
        return listOf(
            Score(100),
            Score(200),
            Score(300),
            Score(400),
            Score(500)
        )
    }

    private fun composeFinalScoreSuggestion(suggestions: List<Score>): List<FinalScoreSuggestionItem> {
        val scoreColors =
            listOf(R.color.uno_red, R.color.uno_yellow, R.color.uno_blue, R.color.uno_green)
        return suggestions
            .take(scoreColors.size)
            .sortedByDescending { score -> score.value }
            .zip(scoreColors)
            .map { FinalScoreSuggestionItem.ScoreItem(it.first, it.second) }
            .plus(FinalScoreSuggestionItem.CustomItem)
    }
}