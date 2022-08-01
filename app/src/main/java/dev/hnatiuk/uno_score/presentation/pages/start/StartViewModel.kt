package dev.hnatiuk.uno_score.presentation.pages.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.navigation.ApplicationRouter
import dev.hnatiuk.uno_score.domain.entity.Score
import dev.hnatiuk.uno_score.domain.repository.SettingsRepository
import dev.hnatiuk.uno_score.presentation.pages.score.ScoreFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class StartEvent : Event

@HiltViewModel
class StartViewModel @Inject constructor(
    private val applicationRouter: ApplicationRouter,
    private val settingsRepository: SettingsRepository
) : BaseViewModel<StartEvent>() {

    val finalScoreData = MutableLiveData<String>()
    val finalScoreSuggestionsData = MutableLiveData<List<Score>>()

    override fun onViewLoaded() {
        viewModelScope.launch {
            finalScoreSuggestionsData.value = settingsRepository.getFinalScoreSuggestions()
            finalScoreData.value = settingsRepository.getDefaultFinalScore().value.toString()
        }
    }

    fun onFinalScoreSelected(score: Score) {
        finalScoreData.value = score.value.toString()
    }

    fun onScoreDown() {
        changeFinalScoreFor(FINAL_SCORE_PERIOD * -1)
    }

    fun onScoreUp() {
        changeFinalScoreFor(FINAL_SCORE_PERIOD)
    }

    fun onStartClick() {
        val score = finalScoreData.value?.toIntOrNull() ?: return
        applicationRouter.navigateTo(ScoreFragment.screen(score))
    }

    private fun changeFinalScoreFor(value: Int) {
        finalScoreData.value = finalScoreData.value
            ?.toIntOrNull()
            ?.plus(value)
            ?.toString()
            ?: finalScoreData.value
    }

    companion object {

        private const val FINAL_SCORE_PERIOD = 50
    }
}