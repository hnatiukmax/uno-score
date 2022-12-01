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
import dev.hnatiuk.uno_score.R.color
import dev.hnatiuk.uno_score.domain.entity.Score
import dev.hnatiuk.uno_score.presentation.navigation.showSetFinalScoreInputDialog
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
        applicationRouter.showSetFinalScoreInputDialog(CUSTOM_FINAL_SCORE_REQUEST_KEY)
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
        return IntProgression
            .fromClosedRange(MIN_SCORE_RANGE_START, MAX_SCORE_RANGE_START, step = SCORE_RANGE_STEP)
            .map { Score(it) }
    }

    private fun composeFinalScoreSuggestion(suggestions: List<Score>): List<FinalScoreSuggestionItem> {
        val scoreColors = listOf(color.uno_red, color.uno_yellow, color.uno_blue, color.uno_green)
        return suggestions
            .take(scoreColors.size)
            .sortedByDescending { score -> score.value }
            .zip(scoreColors)
            .map { FinalScoreSuggestionItem.ScoreItem(it.first, it.second) }
            .plus(FinalScoreSuggestionItem.CustomItem)
    }

    companion object {

        const val CUSTOM_FINAL_SCORE_REQUEST_KEY = "CUSTOM_FINAL_SCORE_REQUEST_KEY"

        private const val MIN_SCORE_RANGE_START = 100
        private const val MAX_SCORE_RANGE_START = 500
        private const val SCORE_RANGE_STEP = 100
    }
}