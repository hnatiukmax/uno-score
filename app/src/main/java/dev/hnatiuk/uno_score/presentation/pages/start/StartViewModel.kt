package dev.hnatiuk.uno_score.presentation.pages.start

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import javax.inject.Inject

sealed class StartEvent : Event

@HiltViewModel
class StartViewModel @Inject constructor() : BaseViewModel<StartEvent>()