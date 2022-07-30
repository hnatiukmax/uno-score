package dev.hnatiuk.uno_score.presentation.pages.score

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import javax.inject.Inject

sealed class ScoreEvent : Event

@HiltViewModel
class ScoreViewModel @Inject constructor() : BaseViewModel<ScoreEvent>()